package com.qingyun.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.HistoryDealDao;
import com.qingyun.entity.HistoryDeal;
import com.qingyun.utils.PageUtils;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-02-08 17:52:07
 */
 @Service
public class HistoryDealService {

	@Autowired
	HistoryDealDao historyDealDao;

    /**
     * @Description 查询历史交易
     * @author 张立增
     * @Date 2020年2月9日 下午8:06:28
     */
	public String historyDealList(String page, String limit, String inputSearch, String startDate, String endDate, String buySellFlag) {
		try {
			Integer startIndex = PageUtils.getStartIndex(page, limit);
			Map<String, Object> data = new HashedMap<String, Object>();
	        data.put("startIndex",startIndex);
	        data.put("pageSize", Integer.parseInt(limit));
	        data.put("inputSearch", inputSearch);
	        data.put("startDate", startDate);
	        data.put("endDate", endDate);
	        data.put("buySellFlag", buySellFlag);
			List<HistoryDeal> list = historyDealDao.historyDealList(data);
			Integer totalListSize = historyDealDao.getTotalListSize(data);   //得到所有的数量
			Object object = PageUtils.makeObject(list,totalListSize);
			return JSONObject.toJSONString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}  
	
	
	
	
	
	    
}

