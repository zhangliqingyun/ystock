package com.qingyun.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.BasicStockDao;
import com.qingyun.dao.HoldStockDao;
import com.qingyun.entity.BasicStock;
import com.qingyun.entity.HoldStock;
import com.qingyun.entity.Message;
import com.qingyun.utils.PageUtils;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-02-08 17:52:07
 */
 @Service
public class HoldStockService {

    @Autowired
    HoldStockDao holdStockDao;
    
    @Autowired
    BasicStockDao basicStockDao;

    /**
     * @Description 查询持仓股票记录
     * @author 张立增
     * @Date 2020年2月8日 下午7:42:45
     */
	public String holdStockList(String page, String limit, String inputSearch) {
		try {
			Integer startIndex = PageUtils.getStartIndex(page, limit);
			Map<String, Object> data = new HashedMap<String, Object>();
	        data.put("startIndex",startIndex);
	        data.put("pageSize", Integer.parseInt(limit));
	        data.put("inputSearch", inputSearch);
			List<HoldStock> list = holdStockDao.holdStockList(data);
			Integer totalListSize = holdStockDao.getTotalListSize(inputSearch);   //得到所有的数量
			Object object = PageUtils.makeObject(list,totalListSize);
			return JSONObject.toJSONString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * @Description 保存购买股票记录
     * @author 张立增
     * @Date 2020年2月8日 下午11:35:11
     */
	public String saveBuyData(HoldStock holdStock) {
		Message message = new Message();
		try {
			List<BasicStock> list = basicStockDao.findById(holdStock.getBasicStockId()); 
			if(null != list && list.size() > 0) {
				holdStock.setStockCode(list.get(0).getStockCode());
				holdStock.setStockName(list.get(0).getStockName());
				holdStock.setAddDate(new Date());
				holdStockDao.saveBuyData(holdStock);
				message.setType(Message.OK);
				message.setMsg("保存购买股票记录成功");
			}else {
				message.setType(Message.ERROR);
				message.setMsg("查询不到此股票的基础信息，请确认信息的准确性");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("保存购买股票记录错误");
		}
		return JSONObject.toJSONString(message);
	}
 
    
}

