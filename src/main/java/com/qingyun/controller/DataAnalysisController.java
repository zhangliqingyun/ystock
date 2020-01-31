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
    
	
}
