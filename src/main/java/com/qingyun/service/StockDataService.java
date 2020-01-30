package com.qingyun.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.BasicStockDao;
import com.qingyun.dao.StockDataDao;
import com.qingyun.entity.BasicStock;
import com.qingyun.entity.Message;
import com.qingyun.entity.StockData;
import com.qingyun.utils.FileUtils;
import com.qingyun.utils.PageUtils;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-01-28 17:07:39
 */
 @Service
public class StockDataService{

	@Autowired
	StockDataDao stockDataDao;
	
	@Autowired
	BasicStockDao basicStockDao;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @Description 查询股票数据集合的方法
     * @author 张立增
     * @Date 2020年1月29日 下午10:22:14
     */
	public String stockDataList(String page, String limit, String stockName) {
		Integer startIndex = PageUtils.getStartIndex(page, limit);
		Map<String, Object> data = new HashedMap<String, Object>();
        data.put("startIndex",startIndex);
        data.put("pageSize", Integer.parseInt(limit));
        data.put("stockName", stockName);
		List<StockData> list = stockDataDao.stockDataList(data);
		Integer totalListSize = stockDataDao.getTotalListSize(stockName);   //得到所有的数量
		Object object = PageUtils.makeObject(list,totalListSize);
		return JSONObject.toJSONString(object);
	}

	/**
	 * @Description 根据ids删除股票数据
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	public String deleteStockDataByids(String ids) {
		Message message = new Message();
		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {
				stockDataDao.deleteStockDataByid(idArr[i]);
			}
			message.setType(Message.OK);
			message.setMsg("删除股票数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("删除股票数据错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description 添加股票数据
	 * @author 张立增
	 * @Date 2020年1月29日 下午11:41:32
	 */
	public String addStockData(StockData stockData) {
		Message message = new Message();
		try {
			Map<String, Object> params = new HashedMap<String, Object>();
			params.put("basicStockId",stockData.getBasicStockId());
			params.put("dataDate", stockData.getDataDate());
			List<StockData> list = stockDataDao.findByBasicStockIdAndDataDate(params); 
			if(null != list && list.size() > 0) {
				message.setType(Message.ERROR);
				message.setMsg(list.get(0).getStockName()+"已经存在【"+formatter.format(stockData.getDataDate())+"】数据");
			}else {
				List<BasicStock> basicStockList = basicStockDao.findById(stockData.getBasicStockId());
				if(null != basicStockList && basicStockList.size() > 0) {
					stockData.setStockCode(basicStockList.get(0).getStockCode());
					stockData.setStockName(basicStockList.get(0).getStockName());
					BigDecimal diffPrice = stockData.getNowPrice().subtract(stockData.getLastPrice()); //与上次的差值
					stockData.setDiffPrice(diffPrice);
					stockDataDao.addStockData(stockData);
					message.setType(Message.OK);
					message.setMsg("添加股票信息成功");
				}else {
					message.setType(Message.ERROR);
					message.setMsg("查询不到此股票的基础信息，请确认信息的准确性");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("添加股票信息错误");
		}
		return JSONObject.toJSONString(message);
	}

    /**
     * @Description 模板下载
     * @author 张立增
     * @Date 2020年1月30日 下午5:36:56
     */
	public void downTemplate(HttpServletResponse response) {
		InputStream in = null;
        OutputStream out = null;
        try
        {
    	   response.setCharacterEncoding("UTF-8");
    	   response.reset();
    	   String fileName = "股票数据导入模板.xlsx";
           response.addHeader("Content-Disposition", " attachment;filename=" +  new String(fileName.getBytes(), "iso-8859-1"));
           response.setContentType("application/octet-stream");
           in = this.getClass().getClassLoader().getResourceAsStream("static/tempxls/"+fileName);    //获取文件输入流
           int len = 0;
           byte[] buffer = new byte[1024];
           out = response.getOutputStream();
           while ((len = in.read(buffer)) > 0) {
              out.write(buffer, 0, len);
           }
        } catch (Exception e){
        	e.printStackTrace();
        } finally {
           if (in != null) {
               try {
                   in.close();
               } catch (Exception e){
                   throw new RuntimeException(e);
               }
           }
           if (out != null) {
	           try {
		  	  	 out.close();
		  	  } catch (IOException e) {
		  	  	 e.printStackTrace();
		  	  }
           }    
        }
	}

    /**
     * @Description 上传股票数据
     * @author 张立增
     * @Date 2020年1月30日 下午6:04:04
     */
	public String uploadStockData(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		Message message = new Message();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			InputStream fileInput = file.getInputStream(); // 输入流
			String fileName = file.getOriginalFilename();
			Workbook workBook = FileUtils.getWorkBook(fileInput, fileName);
			if (workBook != null) {
				List<StockData> stockDataList = new ArrayList<StockData>();
				message = parseFileAndSetList(workBook,stockDataList,fileName); // 解析和存储信息到list中
				if (message.getType() == 200) { // 解析没有问题才保存到数据库中
					if(stockDataList != null && stockDataList.size() > 0) { // 有数值则添加
						message = checkAndSaveStockDataList(stockDataList); //检查和保存股票数据 
					}else {
						message.setType(100);
						message.setMsg(fileName + "解析不到合法的记录，不进行导入操作");
					}
				}
			} else {
				message.setType(100);
				message.setMsg(fileName + "为空，上传解析失败");
			}
		} catch (Exception e) {
			message.setType(Message.ERROR);
			message.setMsg("上传解析文件错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description 检查和保存股票数据 
	 * @author 张立增
	 * @Date 2020年1月30日 下午7:42:05
	 */
	private Message checkAndSaveStockDataList(List<StockData> stockDataList) {
		/**
		 * 校验股票数据重复先暂时不做
		 */
		//List<StockData> existStockDataList = stockDataDao.findStockDataAll();  //查询所有的股票数据
		stockDataDao.saveStockData(stockDataList);
		stockDataDao.updateBasicStockId();       //更新basicStockId值
		return new Message(Message.OK,"数据导入成功");
	}

	/**
	 * @Description  解析和存储信息到list中
	 * @author 张立增
	 * @Date 2020年1月30日 下午6:12:47
	 */
	private Message parseFileAndSetList(Workbook workBook, List<StockData> stockDataList, String fileName) {
		Message message = new Message();
		try {
			for (int sheetNum = 0; sheetNum < workBook.getNumberOfSheets(); sheetNum++) {
				Sheet sheet = workBook.getSheetAt(sheetNum);
				if (sheet.getLastRowNum() == 0 && sheet.getPhysicalNumberOfRows() == 0) {
					continue;
				}
				message = checkSheetColTitle(sheet,stockDataList,fileName);
				if (message.getType() != 200) { // 有一个sheet不符合判断此解析文档无效
					return message;
				}
			}
		} catch (Exception e) {
			return new Message(Message.ERROR, fileName + "文件上传解析失败");
		}
		return message;
	}

	/**
	 * @Description  检查文件列头
	 * @author 张立增 
	 * @Date 2020年1月30日 下午6:14:00
	 */
	private Message checkSheetColTitle(Sheet sheet, List<StockData> stockDataList, String fileName) {
		Message message = new Message();
		try {
			if (checkColTitle(sheet)) { // 判断excel的列标题是否和模板一致
				message = parseSheet(sheet, stockDataList, fileName);
			} else {
				message.setType(100);
				message.setMsg(fileName + "文件中的列标题顺序或内容不符合要求，请参照模板填写，上传解析失败!");
			}
		} catch (Exception e) {
			return new Message(Message.ERROR, fileName + "存在为空的sheet，建议删除空sheet,文件上传解析失败");
		}
		return message;
	}

	/**
	 * @Description 开始解析excel行数据的方法
	 * @author 张立增
	 * @Date 2020年1月30日 下午6:20:59
	 */
	private Message parseSheet(Sheet sheet, List<StockData> stockDataList, String fileName) {
		Message message = new Message();
		int temp = 0;
		try {
			for (int rowNum = 1; rowNum <= sheet.getPhysicalNumberOfRows(); ++rowNum) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				temp = rowNum;
				message = parseRow(row, stockDataList,rowNum,fileName); // 解析新的模板行
				if (message.getType() != 200) { // 有一个sheet不符合判断此解析文档无效
					return message;
				}
			}
		} catch (Exception e) {
			return new Message(Message.ERROR, fileName + (temp + 1) + "行文本内容不符合模板要求解析失败");
		}
		return message;
	}

	/**
	 * @Description 解析每一行excel数据的方法
	 * @author 张立增
	 * @return 
	 * @Date 2020年1月30日 下午6:22:48
	 */
	private Message parseRow(Row row, List<StockData> stockDataList, int rowNum, String fileName)  throws Exception{
		String stockCode = FileUtils.parseCell(row.getCell(0)).trim(); // 股票代码
		String stockName = FileUtils.parseCell(row.getCell(1)).trim(); // 股票名称
		String dataDate = FileUtils.parseCell(row.getCell(2)).trim(); // 交易日期
		String nowPrice = FileUtils.parseCell(row.getCell(3)).trim(); // 当前价格
		String lastPrice = FileUtils.parseCell(row.getCell(4)).trim(); // 上次价格
		if(null != stockCode && stockCode.length() > 0 && null != stockName && stockName.length() > 0 && null != dataDate && dataDate.length() > 0 
		  && null != nowPrice && nowPrice.length() > 0 && null != lastPrice && lastPrice.length() > 0) {
			StockData stockData = new StockData();
			if(stockCode.indexOf(".") != -1) {
				stockCode = stockCode.split("\\.")[0];
			}
			stockData.setStockCode(stockCode);
			stockData.setStockName(stockName);
			stockData.setDataDate(formatter.parse(dataDate));
			stockData.setNowPrice(new BigDecimal(nowPrice));
			stockData.setLastPrice(new BigDecimal(lastPrice));
			BigDecimal diffPrice = stockData.getNowPrice().subtract(stockData.getLastPrice()); //与上次的差值
			stockData.setDiffPrice(diffPrice);
			stockDataList.add(stockData);
			return new Message(Message.OK, fileName + "文件解析成功");
		}else {
			return new Message(Message.ERROR, fileName + (rowNum + 1) + "行文本内容存在为空的情况，解析失败");
		}
	}

	/**
	 * @Description 判断excel的列标题是否和模板一致
	 * @author 张立增
	 * @Date 2020年1月30日 下午6:15:50
	 */
	private boolean checkColTitle(Sheet sheet) {
		String stockCode = FileUtils.parseCell(sheet.getRow(0).getCell(0)).trim(); // 股票代码
		String stockName = FileUtils.parseCell(sheet.getRow(0).getCell(1)).trim(); // 股票名称
		String dataDate = FileUtils.parseCell(sheet.getRow(0).getCell(2)).trim(); // 交易日期
		String nowPrice = FileUtils.parseCell(sheet.getRow(0).getCell(3)).trim(); // 当前价格
		String lastPrice = FileUtils.parseCell(sheet.getRow(0).getCell(4)).trim(); // 上次价格
		if ("股票代码".equals(stockCode) && "股票名称".equals(stockName) && "交易日期".equals(dataDate)
				&& "当前价格".equals(nowPrice) && "上次价格".equals(lastPrice)) { // 此为固定的列头，再判断后续的列头与数据字典是否一致
			return true;
		} else {
			return false;
		}
	}
	 
}

