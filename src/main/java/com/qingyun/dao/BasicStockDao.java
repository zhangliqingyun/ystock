package com.qingyun.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qingyun.entity.BasicStock;

@Repository
public interface BasicStockDao {

	List<BasicStock> basicStockList(Map<String, Object> data);

	Integer getTotalListSize(String stockName);

	void deleteStockByid(String id);

	void addBasicStock(BasicStock basicStock);

	
	
}
