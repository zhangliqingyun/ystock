package com.qingyun.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.BasicStockDao;
import com.qingyun.entity.BasicStock;
import com.qingyun.entity.Message;
import com.qingyun.entity.User;
import com.qingyun.utils.PageUtils;


@Service
public class BasicStockService {

    @Autowired
    BasicStockDao basicStockDao;

	/**
	 * @Description 查询股票列表的方法
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	public String basicStockList(String page, String limit, String stockName) {
		Integer startIndex = PageUtils.getStartIndex(page, limit);
		Map<String, Object> data = new HashedMap<String, Object>();
        data.put("startIndex",startIndex);
        data.put("pageSize", Integer.parseInt(limit));
        data.put("stockName", stockName);
		List<BasicStock> list = basicStockDao.basicStockList(data);
		Integer totalListSize = basicStockDao.getTotalListSize(stockName);   //得到所有的数量
		Object object = PageUtils.makeObject(list,totalListSize);
		return JSONObject.toJSONString(object);
	}

	/**
	 * @Description 根据ids删除股票信息
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	public String deleteStockByids(String ids) {
		Message message = new Message();
		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {
				basicStockDao.deleteStockByid(idArr[i]);
			}
			message.setType(Message.OK);
			message.setMsg("删除股票成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("删除股票错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description 添加股票信息
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	public String addBasicStock(BasicStock basicStock) {
		Message message = new Message();
		try {
			basicStockDao.addBasicStock(basicStock);
			message.setType(Message.OK);
			message.setMsg("添加股票信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("添加股票信息错误");
		}
		return JSONObject.toJSONString(message);
	}
    
}
