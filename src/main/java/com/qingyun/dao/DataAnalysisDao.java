package com.qingyun.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qingyun.entity.MacroData;
import com.qingyun.entity.SituaData;

/**
 * @Description 数据分析dao层
 * @author 张立增
 * @Date 2020年1月31日 下午6:33:34
 */
@Repository
public interface DataAnalysisDao {

	List<MacroData> avgMacroDataList(Map<String, Object> data);

	List<MacroData> nowMacroDataList(Map<String, Object> data);

	List<MacroData> maxMacroDataList(Map<String, Object> data);

	List<MacroData> minMacroDataList(Map<String, Object> data);

	List<MacroData> firstMacroDataList(Map<String, Object> data);

	List<SituaData> monthDataSitua(Map<String, Object> params);

	List<SituaData> yearDataSitua(Map<String, Object> params);

	List<SituaData> quarterDataSitua(Map<String, Object> params);

}
