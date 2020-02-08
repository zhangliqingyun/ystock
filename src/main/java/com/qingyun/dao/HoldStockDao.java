package com.qingyun.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingyun.entity.HoldStock;
import com.qingyun.entity.MacroData;
import com.qingyun.entity.SituaData;

/**
 * @Description 数据分析dao层
 * @author 张立增
 * @Date 2020年1月31日 下午6:33:34
 */
@Repository
public interface HoldStockDao {

	List<HoldStock> holdStockList(Map<String, Object> data);

	Integer getTotalListSize(@Param("inputSearch")String inputSearch);

	void saveBuyData(HoldStock holdStock);

	

}
