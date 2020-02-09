package com.qingyun.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingyun.entity.HistoryDeal;
import com.qingyun.entity.MacroData;
import com.qingyun.entity.SituaData;

/**
 * @Description 数据分析dao层
 * @author 张立增
 * @Date 2020年1月31日 下午6:33:34
 */
@Repository
public interface HistoryDealDao {

	void saveHistoryDeal(HistoryDeal historyDeal);

	Integer findIdByStockCodeAndDate(Map<String, Object> params);

	void deleteById(@Param("id")Integer id);

	List<HistoryDeal> historyDealList(Map<String, Object> data);

	Integer getTotalListSize(Map<String, Object> data);

	

}
