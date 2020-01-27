package com.qingyun.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.BasicStockDao;
import com.qingyun.entity.BasicStock;
import com.qingyun.entity.Message;
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
			List<BasicStock> list = basicStockDao.findByStockCodeAndAppearAddrCode(basicStock.getStockCode(),basicStock.getAppearAddrCode()); //通过股票代码和股票上市地点查询股票
			if(null != list && list.size() > 0) {
				message.setType(Message.ERROR);
				message.setMsg("已经存在记录【"+basicStock.getStockCode()+" "+basicStock.getStockName()+"】");
			}else {
				basicStock.setAddDate(new Date());
				basicStockDao.addBasicStock(basicStock);
				message.setType(Message.OK);
				message.setMsg("添加股票信息成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("添加股票信息错误");
		}
		return JSONObject.toJSONString(message);
	}

    /**
     * @Description 校验股票股票信息
     * @author 张立增
     * @Date 2020年1月27日 下午8:33:49
     */
	public String checkBasicStock(BasicStock basicStock) {
		Message message = new Message();
		String basicStockCode = basicStock.getAppearAddrCode()+basicStock.getStockCode();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		HttpGet httpGet = new HttpGet("http://hq.sinajs.cn/list="+basicStockCode);// 创建Get请求
		CloseableHttpResponse response = null;// 响应模型
		try {
			response = httpClient.execute(httpGet);// 由客户端执行(发送)Get请求
			HttpEntity responseEntity = response.getEntity();// 从响应模型中获取响应实体
			if (responseEntity != null) {
				String responseString = EntityUtils.toString(responseEntity);
				String[] strArr = responseString.split("=");
				String stockMessageTemp = strArr[1];
				String[] stockMessageArr = stockMessageTemp.split(";");
				String stockMessage = stockMessageArr[0];
				if(null == stockMessage || stockMessage.length() <= 2 ) {
					message.setType(Message.ERROR);
					message.setMsg(basicStock.getAppearAddrName()+",股票代码："+basicStock.getStockCode()+"不存在，请确认信息的准确性");
				}else {
					String[] messageInfo = stockMessage.split(",");
					String stockName = messageInfo[0].substring(1);
					message.setType(Message.OK);
					message.setMsg(stockName);	
				}
			}else {
				message.setType(Message.ERROR);
				message.setMsg("校验股票响应信息错误");
			}
		} catch (ClientProtocolException e) {
			message.setType(Message.ERROR);
			message.setMsg("校验股票信息错误");
			e.printStackTrace();
		} catch (ParseException e) {
			message.setType(Message.ERROR);
			message.setMsg("校验股票信息错误");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("校验股票信息错误");
		} finally {
			try {
				if (httpClient != null) {// 释放资源
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	   }
	   return JSONObject.toJSONString(message);
	}
    
}
