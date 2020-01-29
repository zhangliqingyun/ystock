package com.qingyun.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingyun.entity.StockData;

/**
 * @Description 股票数据dao层
 * @author 张立增
 * @Date 2020年1月28日 下午5:46:18
 */
@Repository
public interface StockDataDao {

	List<StockData> getMaxDateStockData();

	void saveStockData(List<StockData> insertList);

	void deleteByBasicStockId(String basicStockId);

	List<StockData> stockDataList(Map<String, Object> data);

	Integer getTotalListSize(@Param("stockName")String stockName);

	void deleteStockDataByid(String id);

	List<StockData> findByBasicStockIdAndDataDate(Map<String, Object> params);

	void addStockData(StockData stockData);


}
