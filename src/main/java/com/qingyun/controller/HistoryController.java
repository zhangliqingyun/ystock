package com.qingyun.controller;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.entity.StockData;
import com.qingyun.service.HistoryService;
import com.qingyun.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-01-28 17:07:39
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
	HistoryService historyService;
    
    /**
	* @Description 跳转到主页面的方法
	* @author 张立增[zhanglizeng] Tel：18860126570
	* @createDate 2020-01-28 17:07:39
	*/
    @RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("pages/history/index");
		return mv;
	}
    

	/**
	 * @Description 跳转到批量导入数据页面
	 * @author 张立增
	 * @Date 2020年1月30日 下午4:39:20
	 */
    @RequestMapping(value = "/batchUploadPage")
	public ModelAndView batchUploadPage() {
		ModelAndView mv = new ModelAndView("pages/history/batchupload");
		return mv;
	}
	

    /**
     * @Description 上传股票数据
     * @author 张立增
     * @Date 2020年1月30日 下午6:04:04
     */
	@RequestMapping("/uploadStockData")  
	public String uploadfile(@RequestParam("file") MultipartFile file, HttpServletRequest request,HttpServletResponse response) {
		return historyService.uploadStockData(file,request,response);
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
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String stockCode = request.getParameter("stockCode");
		return historyService.stockDataList(page,limit,startDate,endDate,stockCode);
	}

	/**
	 * @Description 查询所有的股票记录
	 * @author 张立增
	 * @Date 2020年1月29日 下午11:12:09
	 */
	@RequestMapping(value = "/basicStockAllList", method = { RequestMethod.POST, RequestMethod.GET})
	public String basicStockAllList(HttpServletRequest request) throws Exception {
		return historyService.basicStockAllList();
	}







}
