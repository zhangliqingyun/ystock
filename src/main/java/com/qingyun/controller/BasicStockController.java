package com.qingyun.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.entity.BasicStock;
import com.qingyun.service.BasicStockService;

/** 
 * @Description 股票基础数据控制层
 * @author 张立增
 * @Date 2020年1月6日 下午8:12:28
 */
@RestController
@RequestMapping("/basicStock")
public class BasicStockController {

	@Autowired
	BasicStockService basicStockService;
	
	/**
	 * @Description 跳转到股票列表页面的方法
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("pages/basicstock/index");
		return mv;
	}
	
	/**
	 * @Description 查询股票列表的方法
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping(value = "/basicStockList", method = { RequestMethod.POST, RequestMethod.GET})
	public String basicStockList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");    
		String limit = request.getParameter("limit");
		String stockName = request.getParameter("stockName");
		return basicStockService.basicStockList(page,limit,stockName);
	}
	
	/**
	 * @Description 根据ids删除股票信息
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping(value = "/deleteStockByids", method = { RequestMethod.POST, RequestMethod.GET})
	public String deleteStockByids(HttpServletRequest request) throws Exception {
		String ids = request.getParameter("ids");    
		return basicStockService.deleteStockByids(ids);
	}
	
	/**
     * @Description 跳转到添加股票界面
     * @author 张立增
     * @Date 2020年1月8日 下午3:33:32
     */
	@RequestMapping("/addPage")
	public ModelAndView addPage() {
		ModelAndView mv = new ModelAndView("pages/basicstock/add");
		return mv;
	}

	/**
	 * @Description 添加股票信息
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	@RequestMapping(value = "/addBasicStock", method = { RequestMethod.POST, RequestMethod.GET})
	public String addBasicStock(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");    
		BasicStock basicStock = JSONObject.parseObject(data, BasicStock.class);
		return basicStockService.addBasicStock(basicStock);
	}
	
    /**
     * @Description 校验股票股票信息
     * @author 张立增
     * @Date 2020年1月27日 下午8:33:49
     */
	@RequestMapping(value = "/checkBasicStock", method = { RequestMethod.POST, RequestMethod.GET})
	public String checkBasicStock(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");    
		BasicStock basicStock = JSONObject.parseObject(data, BasicStock.class);
		return basicStockService.checkBasicStock(basicStock);
	}
	
	/**
	 * @Description 查询所有的股票记录
	 * @author 张立增
	 * @Date 2020年1月29日 下午11:12:09
	 */
	@RequestMapping(value = "/basicStockAllList", method = { RequestMethod.POST, RequestMethod.GET})
	public String basicStockAllList(HttpServletRequest request) throws Exception {
		return basicStockService.basicStockAllList();
	}
	
	
	

	
}
