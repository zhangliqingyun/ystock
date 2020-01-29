package com.qingyun.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingyun.entity.BasicStock;

@Repository
public interface BasicStockDao {

	List<BasicStock> basicStockList(Map<String, Object> data);

	Integer getTotalListSize(@Param("stockName")String stockName);

	void deleteStockByid(String id);

	void addBasicStock(BasicStock basicStock);

	List<BasicStock> findByStockCodeAndAppearAddrCode(@Param("stockCode")String stockCode, @Param("appearAddrCode")String appearAddrCode);

	List<BasicStock> basicStockAll();

	List<BasicStock> findById(@Param("id")Integer id);

	
	
}
