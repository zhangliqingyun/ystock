package com.qingyun.service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.BasicStockDao;
import com.qingyun.dao.HistoryDao;
import com.qingyun.dao.StockDataDao;
import com.qingyun.entity.*;
import com.qingyun.utils.FileUtils;
import com.qingyun.utils.PageUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-01-28 17:07:39
 */
 @Service
public class HistoryService {

	@Autowired
	HistoryDao historyDao;

	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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
				List<History> historyList = new ArrayList<History>();
				message = parseFileAndSetList(workBook,historyList,fileName); // 解析和存储信息到list中
				if (message.getType() == 200) { // 解析没有问题才保存到数据库中
					if(historyList != null && historyList.size() > 0) { // 有数值则添加
						message = checkAndSaveStockDataList(historyList); //检查和保存股票数据
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
	private Message checkAndSaveStockDataList(List<History> historyList) {
		/**
		 * 校验股票数据重复先暂时不做
		 */
		//List<StockData> existStockDataList = stockDataDao.findStockDataAll();  //查询所有的股票数据
		historyDao.deleteByCode(historyList.get(0).getStockCode());
		historyDao.saveStockData(historyList);
		return new Message(Message.OK,"数据导入成功");
	}

	/**
	 * @Description  解析和存储信息到list中
	 * @author 张立增
	 * @Date 2020年1月30日 下午6:12:47
	 */
	private Message parseFileAndSetList(Workbook workBook, List<History> historyList, String fileName) {
		Message message = new Message();
		try {
			for (int sheetNum = 0; sheetNum < workBook.getNumberOfSheets(); sheetNum++) {
				Sheet sheet = workBook.getSheetAt(sheetNum);
				if (sheet.getLastRowNum() == 0 && sheet.getPhysicalNumberOfRows() == 0) {
					continue;
				}
				message = checkSheetColTitle(sheet,historyList,fileName);
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
	private Message checkSheetColTitle(Sheet sheet, List<History> historyList, String fileName) {
		Message message = new Message();
		try {
			/*if (checkColTitle(sheet)) { // 判断excel的列标题是否和模板一致*/
				message = parseSheet(sheet, historyList, fileName);
		/*	} else {
				message.setType(100);
				message.setMsg(fileName + "文件中的列标题顺序或内容不符合要求，请参照模板填写，上传解析失败!");
			}*/
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
	private Message parseSheet(Sheet sheet, List<History> historyList, String fileName) {
		Message message = new Message();
		int temp = 0;
		try {
			for (int rowNum = 2; rowNum <= sheet.getPhysicalNumberOfRows(); ++rowNum) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				temp = rowNum;
				message = parseRow(row, historyList,rowNum,fileName); // 解析新的模板行
				if (message.getType() != 200) { // 有一个sheet不符合判断此解析文档无效
					return message;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	private Message parseRow(Row row, List<History> historyList, int rowNum, String fileName)  throws Exception{
		String stockCode = FileUtils.parseCell(row.getCell(0)).trim(); // 证券代码
		String stockName = FileUtils.parseCell(row.getCell(1)).trim(); // 证券名称
		String dataDate = FileUtils.parseCell(row.getCell(2)).trim(); // 交易时间
		String openPrice = FileUtils.parseCell(row.getCell(3)).trim(); // 开盘价
		String highPrice = FileUtils.parseCell(row.getCell(4)).trim(); // 最高价
		String lowPrice = FileUtils.parseCell(row.getCell(5)).trim(); // 最低价
		String nowPrice = FileUtils.parseCell(row.getCell(6)).trim(); // 收盘价
		String diffPrice = FileUtils.parseCell(row.getCell(7)).trim(); // 涨跌
		String diffRate = FileUtils.parseCell(row.getCell(8)).trim(); // 涨跌幅%
		String num = FileUtils.parseCell(row.getCell(9)).trim(); // 成交量
		String price = FileUtils.parseCell(row.getCell(10)).trim(); // 成交额

		if(null != stockCode && stockCode.length() > 0 && null != stockName && stockName.length() > 0 && null != dataDate && dataDate.length() > 0 
		  && null != nowPrice && nowPrice.length() > 0 && null != openPrice && openPrice.length() > 0 && null != highPrice && highPrice.length() > 0 && null != lowPrice && lowPrice.length() > 0 && null != nowPrice && nowPrice.length() > 0 && null != diffPrice && diffPrice.length() > 0 && null != diffRate && diffRate.length() > 0 && null != num && num.length() > 0 && null != price && price.length() > 0) {
			History history = new History();
			history.setStockCode(stockCode);
			history.setStockName(stockName);
			history.setDataDate(formatter.parse(dataDate));
			history.setOpenPrice(new BigDecimal(openPrice));
			history.setHighPrice(new BigDecimal(highPrice));
			history.setLowPrice(new BigDecimal(lowPrice));
			history.setNowPrice(new BigDecimal(nowPrice));
			history.setDiffPrice(new BigDecimal(diffPrice));
			history.setDiffRate(new BigDecimal(diffRate));
			history.setOpenPrice(new BigDecimal(openPrice));
			history.setNum(new BigDecimal(num));
			history.setPrice(new BigDecimal(price));
			historyList.add(history);
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
		String stockCode = FileUtils.parseCell(sheet.getRow(0).getCell(0)).trim(); // 证券代码
		String stockName = FileUtils.parseCell(sheet.getRow(0).getCell(1)).trim(); // 证券名称
		String dataDate = FileUtils.parseCell(sheet.getRow(0).getCell(2)).trim(); // 交易时间
		String openPrice = FileUtils.parseCell(sheet.getRow(0).getCell(3)).trim(); // 开盘价
		String highPrice = FileUtils.parseCell(sheet.getRow(0).getCell(4)).trim(); // 最高价
		String lowPrice = FileUtils.parseCell(sheet.getRow(0).getCell(5)).trim(); // 最低价
		String nowPrice = FileUtils.parseCell(sheet.getRow(0).getCell(6)).trim(); // 收盘价
		String diffPrice = FileUtils.parseCell(sheet.getRow(0).getCell(7)).trim(); // 涨跌
		String diffRate = FileUtils.parseCell(sheet.getRow(0).getCell(8)).trim(); // 涨跌幅%
		String num = FileUtils.parseCell(sheet.getRow(0).getCell(9)).trim(); // 成交量
		String price = FileUtils.parseCell(sheet.getRow(0).getCell(10)).trim(); // 成交额
		if ("证券代码".equals(stockCode) && "证券名称".equals(stockName) && "交易日期".equals(dataDate)
				&& "开盘价".equals(openPrice) && "最高价".equals(highPrice)&& "最低价".equals(lowPrice) && "收盘价".equals(nowPrice)&& "涨跌".equals(diffPrice) && "成交额".equals(diffRate)&& "成交量".equals(num) && "涨跌幅%".equals(price)) { // 此为固定的列头，再判断后续的列头与数据字典是否一致
			return true;
		} else {
			return false;
		}
	}

    public String stockDataList(String page, String limit, String startDate, String endDate, String stockCode) {
		Integer startIndex = PageUtils.getStartIndex(page, limit);
		Map<String, Object> data = new HashedMap<String, Object>();
		data.put("startIndex",startIndex);
		data.put("pageSize", Integer.parseInt(limit));
		data.put("startDate", startDate);
		data.put("endDate", endDate);
		data.put("stockCode", stockCode);
		Integer totalListSize = 0;
		List<HistoryRespon> list = historyDao.stockDataList(data);
		if(null != list && list.size() > 0){
			totalListSize = historyDao.getTotalListSize(stockCode);   //得到所有的数量
			List<HistoryRespon> newList = historyDao.getNewStockDataList(data);
			Map<String, HistoryRespon> map = newList.stream().collect(Collectors.toMap(HistoryRespon::getStockCode, obj -> obj));
		    for(HistoryRespon historyRespon : list){
				if(map.containsKey(historyRespon.getStockCode())){
					HistoryRespon temp = map.get(historyRespon.getStockCode());
					historyRespon.setDataDate(temp.getDataDate());
					historyRespon.setNowPrice(temp.getNowPrice());
					historyRespon.setNewNum(temp.getNewNum());
					historyRespon.setNewPrice(temp.getNewPrice());
				}
			}
		}

		Object object = PageUtils.makeObject(list,totalListSize);
		return JSONObject.toJSONString(object);

	}

	public String basicStockAllList() {
		try {
			List<History> list = historyDao.basicStockAll();
			return JSONObject.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

