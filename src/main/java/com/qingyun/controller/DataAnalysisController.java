package com.qingyun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.qingyun.service.DataAnalysisService;

/**
 * @Description 数据分析控制层
 * @author 张立增
 * @Date 2020年1月31日 下午5:52:24
 */
@RestController
@RequestMapping("/dataAnalysis")
public class DataAnalysisController {

    @Autowired
	DataAnalysisService dataAnalysisService;
	
    /**
     * @Description 跳转到宏观一览页面
     * @author 张立增
     * @Date 2020年1月31日 下午6:00:23
     */
    @RequestMapping(value = "/macroIndex")
	public ModelAndView macroIndex() {
		ModelAndView mv = new ModelAndView("pages/dataanalysis/macroindex");
		return mv;
	}
    
    /**
     * @Description 宏观一览列表数据
     * @author 张立增
     * @Date 2020年1月31日 下午6:21:49
     */
    @RequestMapping(value = "/macroDataList", method = { RequestMethod.POST, RequestMethod.GET})
	public String macroDataList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");    
		String limit = request.getParameter("limit");
		String stockName = request.getParameter("stockName");
		return dataAnalysisService.macroDataList(page,limit,stockName);
	}

    /**
     * @Description 跳转到数据态势页面
     * @author 张立增
     * @Date 2020年1月31日 下午6:00:23
     */
    @RequestMapping(value = "/situaIndex")
	public ModelAndView situaIndex() {
		ModelAndView mv = new ModelAndView("pages/dataanalysis/situaindex");
		return mv;
	}
    
    /**
     * @Description 按数据态势按月份统计
     * @author 张立增
     * @Date 2020年2月1日 下午7:22:35
     */
    @RequestMapping(value = "/monthDataSitua", method = { RequestMethod.POST, RequestMethod.GET})
	public String monthDataSitua(HttpServletRequest request) throws Exception {
		String startDate = request.getParameter("startDate");    
		String endDate = request.getParameter("endDate");
		String basicStockId = request.getParameter("basicStockId");
		return dataAnalysisService.monthDataSitua(startDate,endDate,basicStockId);
	}
    
    /**
     * @Description 按数据态势按年度统计
     * @author 张立增
     * @Date 2020年2月1日 下午10:32:38
     */
    @RequestMapping(value = "/yearDataSitua", method = { RequestMethod.POST, RequestMethod.GET})
	public String yearDataSitua(HttpServletRequest request) throws Exception {
		String startDate = request.getParameter("startDate");    
		String endDate = request.getParameter("endDate");
		String basicStockId = request.getParameter("basicStockId");
		return dataAnalysisService.yearDataSitua(startDate,endDate,basicStockId);
	}
 
    /**
     * @Description 按数据态势按季度统计
     * @author 张立增
     * @Date 2020年2月1日 下午10:36:02
     */
    @RequestMapping(value = "/quarterDataSitua", method = { RequestMethod.POST, RequestMethod.GET})
   	public String quarterDataSitua(HttpServletRequest request) throws Exception {
   		String startDate = request.getParameter("startDate");    
   		String endDate = request.getParameter("endDate");
   		String basicStockId = request.getParameter("basicStockId");
   		return dataAnalysisService.quarterDataSitua(startDate,endDate,basicStockId);
   	}
    
    
    
    
    
    
    
	
}
