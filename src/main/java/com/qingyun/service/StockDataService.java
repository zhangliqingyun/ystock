package com.qingyun.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.BasicStockDao;
import com.qingyun.dao.StockDataDao;
import com.qingyun.entity.BasicStock;
import com.qingyun.entity.Message;
import com.qingyun.entity.StockData;
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
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
	 
}

