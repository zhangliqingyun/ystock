package com.qingyun.dao;

import com.qingyun.entity.History;
import com.qingyun.entity.HistoryRespon;
import com.qingyun.entity.StockData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description 股票数据dao层
 * @author 张立增
 * @Date 2020年1月28日 下午5:46:18
 */
@Repository
public interface HistoryDao {

	void saveStockData(List<History> insertList);


	void deleteByCode(@Param("stockCode") String stockCode);

    List<History> basicStockAll();

	List<HistoryRespon> stockDataList(Map<String, Object> data);

	Integer getTotalListSize(@Param("stockCode")String stockCode);

	List<HistoryRespon> getNewStockDataList(Map<String, Object> data);
}
