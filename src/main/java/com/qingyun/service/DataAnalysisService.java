package com.qingyun.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.DataAnalysisDao;
import com.qingyun.entity.MacroData;
import com.qingyun.entity.SituaData;
import com.qingyun.utils.PageUtils;

/**
 * @Description 数据分析Service层
 * @author 张立增
 * @Date 2020年1月31日 下午5:52:24
 */
@Service
public class DataAnalysisService {
	
    @Autowired
    DataAnalysisDao dataAnalysisDao;

    /**
     * @Description 宏观一览列表数据
     * @author 张立增
     * @Date 2020年1月31日 下午6:21:49
     */
	public String macroDataList(String page, String limit, String stockName) {
		try {
			Integer startIndex = PageUtils.getStartIndex(page, limit);
			Map<String, Object> data = new HashedMap<String, Object>();
	        data.put("startIndex",startIndex);
	        data.put("pageSize", Integer.parseInt(limit));
	        data.put("stockName", stockName);
			List<MacroData> avgList = dataAnalysisDao.avgMacroDataList(data);   //获取平均数据
			if(null != avgList && avgList.size() > 0) {
				List<MacroData> nowList = dataAnalysisDao.nowMacroDataList(data);   //获取当前数据
				List<MacroData> maxList = dataAnalysisDao.maxMacroDataList(data);   //获取最大数据
				List<MacroData> minList = dataAnalysisDao.minMacroDataList(data);   //获取最小数据
				List<MacroData> firstList = dataAnalysisDao.firstMacroDataList(data);   //获取首发数据
				for(int i = 0;i < avgList.size();i++) {
					String stockCode = avgList.get(i).getStockCode();
					for(int j = 0;j < nowList.size();j++) {
						if(stockCode.equals(nowList.get(j).getStockCode())) {
							avgList.get(i).setNowPrice(nowList.get(j).getNowPrice());
							avgList.get(i).setNowPriceDate(nowList.get(j).getNowPriceDate());
						}
					}
					for(int k = 0;k < maxList.size();k++) {
						if(stockCode.equals(maxList.get(k).getStockCode())) {
							avgList.get(i).setHighPrice(maxList.get(k).getHighPrice());
							avgList.get(i).setHighPriceDate(maxList.get(k).getHighPriceDate());
						}
					}
					for(int m = 0;m < minList.size();m++) {
						if(stockCode.equals(minList.get(m).getStockCode())) {
							avgList.get(i).setLowPrice(minList.get(m).getLowPrice());
							avgList.get(i).setLowPriceDate(minList.get(m).getLowPriceDate());
						}
					}
					for(int n = 0;n < firstList.size();n++) {
						if(stockCode.equals(firstList.get(n).getStockCode())) {
							avgList.get(i).setFirstPrice(firstList.get(n).getFirstPrice());
							avgList.get(i).setFirstPriceDate(firstList.get(n).getFirstPriceDate());
						}
					}
					BigDecimal nowPrice = avgList.get(i).getNowPrice();
					if(null != nowPrice) {
						BigDecimal averagePrice = avgList.get(i).getAveragePrice();
						if(null != averagePrice) {
							avgList.get(i).setNowAverageDiff(nowPrice.subtract(averagePrice));
						}
						BigDecimal lowPrice = avgList.get(i).getLowPrice();
						if(null != lowPrice) {
							avgList.get(i).setNowLowDiff(nowPrice.subtract(lowPrice));
						}
						BigDecimal highPrice = avgList.get(i).getHighPrice();
						if(null != highPrice) {
							avgList.get(i).setNowHighDiff(nowPrice.subtract(highPrice));
						}
						BigDecimal firstPrice = avgList.get(i).getFirstPrice();
						if(null != firstPrice) {
							avgList.get(i).setNowFirstDiff(nowPrice.subtract(firstPrice));
						}
					}
				}
				List<MacroData> basicStockList = dataAnalysisDao.basicStockAll();
				Object object = PageUtils.makeObject(avgList,basicStockList.size());
				return JSONObject.toJSONString(object);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * @Description 按数据态势按月份统计
     * @author 张立增
     * @Date 2020年2月1日 下午7:22:35
     */
	public String monthDataSitua(String startDate, String endDate, String basicStockId) {
		try {
			Map<String, Object> params = new HashedMap<String, Object>();
			params.put("startDate",startDate);
			params.put("endDate", endDate);
			params.put("basicStockId", basicStockId);
			List<SituaData> list = dataAnalysisDao.monthDataSitua(params);
			return JSONObject.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * @Description 按数据态势按年度统计
     * @author 张立增
     * @Date 2020年2月1日 下午10:32:38
     */
	public String yearDataSitua(String startDate, String endDate, String basicStockId) {
		try {
			Map<String, Object> params = new HashedMap<String, Object>();
			params.put("startDate",startDate);
			params.put("endDate", endDate);
			params.put("basicStockId", basicStockId);
			List<SituaData> list = dataAnalysisDao.yearDataSitua(params);
			return JSONObject.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * @Description 按数据态势按季度统计
     * @author 张立增
     * @Date 2020年2月1日 下午10:36:02
     */
	public String quarterDataSitua(String startDate, String endDate, String basicStockId) {
		try {
			Map<String, Object> params = new HashedMap<String, Object>();
			params.put("startDate",startDate);
			params.put("endDate", endDate);
			params.put("basicStockId", basicStockId);
			List<SituaData> list = dataAnalysisDao.quarterDataSitua(params);
			return JSONObject.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    
}
