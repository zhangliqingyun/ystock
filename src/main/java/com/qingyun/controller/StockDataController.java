package com.qingyun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.entity.StockData;
import com.qingyun.service.StockDataService;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-01-28 17:07:39
 */
@RestController
@RequestMapping("/stockData")
public class StockDataController{

    @Autowired
    StockDataService stockDataService;
    
    /**
	* @Description 跳转到主页面的方法
	* @author 张立增[zhanglizeng] Tel：18860126570
	* @createDate 2020-01-28 17:07:39
	*/
    @RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("pages/stockdata/index");
		return mv;
	}
    
    /**
     * @Description 查询股票数据集合的方法
     * @author 张立增
     * @Date 2020年1月29日 下午10:22:14
     */
    @RequestMapping(value = "/stockDataList", method = { RequestMethod.POST, RequestMethod.GET})
	public String stockDataList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");    
		String limit = request.getParameter("limit");
		String stockName = request.getParameter("stockName");
		return stockDataService.stockDataList(page,limit,stockName);
	}
    
    /**
     * @Description 跳转到添加股票页面的方法
     * @author 张立增
     * @Date 2020年1月29日 下午10:39:52
     */
    @RequestMapping(value = "/addPage")
	public ModelAndView addPage() {
		ModelAndView mv = new ModelAndView("pages/stockdata/add");
		return mv;
	}
    
	/**
	 * @Description 根据ids删除股票数据
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping(value = "/deleteStockDataByids", method = { RequestMethod.POST, RequestMethod.GET})
	public String deleteStockDataByids(HttpServletRequest request) throws Exception {
		String ids = request.getParameter("ids");    
		return stockDataService.deleteStockDataByids(ids);
	}
	
	/**
	 * @Description 添加股票数据
	 * @author 张立增
	 * @Date 2020年1月29日 下午11:41:32
	 */
	@RequestMapping(value = "/addStockData", method = { RequestMethod.POST, RequestMethod.GET})
	public String addStockData(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");    
		StockData stockData = JSONObject.parseObject(data, StockData.class);
		return stockDataService.addStockData(stockData);
	}
	
	/**
	 * @Description 跳转到批量导入数据页面
	 * @author 张立增
	 * @Date 2020年1月30日 下午4:39:20
	 */
    @RequestMapping(value = "/batchUploadPage")
	public ModelAndView batchUploadPage() {
		ModelAndView mv = new ModelAndView("pages/stockdata/batchupload");
		return mv;
	}
	
    /**
     * @Description 模板下载
     * @author 张立增
     * @Date 2020年1月30日 下午5:36:56
     */
    @RequestMapping(value={"/downTemplate"}, produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public void downTemplate(HttpServletRequest request, HttpServletResponse response){
		 response.setCharacterEncoding("UTF-8");
		 stockDataService.downTemplate(response);
	}
    
    /**
     * @Description 上传股票数据
     * @author 张立增
     * @Date 2020年1月30日 下午6:04:04
     */
	@RequestMapping("/uploadStockData")  
	public String uploadfile(@RequestParam("file") MultipartFile file, HttpServletRequest request,HttpServletResponse response) {
		return stockDataService.uploadStockData(file,request,response);
	}
    
	
	
	
    
   
   
}
