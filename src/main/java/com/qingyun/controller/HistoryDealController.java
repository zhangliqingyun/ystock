package com.qingyun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.qingyun.service.HistoryDealService;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-02-08 17:52:07
 */
@RestController
@RequestMapping("/historyDeal")
public class HistoryDealController {

    @Autowired
    HistoryDealService historyDealService;
    
    
    /**
     * @Description 跳转到历史成交页面
     * @author 张立增
     * @Date 2020年2月9日 下午7:26:56
     */
    @RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("pages/historydeal/index");
		return mv;
	}
    
    /**
     * @Description 查询历史交易
     * @author 张立增
     * @Date 2020年2月9日 下午8:06:28
     */
    @RequestMapping(value = "/historyDealList", method = { RequestMethod.POST, RequestMethod.GET})
  	public String historyDealList(HttpServletRequest request) throws Exception {
  		String page = request.getParameter("page");    
  		String limit = request.getParameter("limit");
  		String inputSearch = request.getParameter("inputSearch");
  		String startDate = request.getParameter("startDate");
  		String endDate = request.getParameter("endDate");
  		String buySellFlag = request.getParameter("buySellFlag");
  		return historyDealService.historyDealList(page,limit,inputSearch,startDate,endDate,buySellFlag);
  	}
    
    /**
     * @Description 跳转到盈亏一览页面
     * @author 张立增
     * @Date 2020年2月23日 下午6:37:16
     */
    @RequestMapping("/profitLossIndex")
 	public ModelAndView profitLossIndex() {
 		ModelAndView mv = new ModelAndView("pages/historydeal/profitlossindex");
 		return mv;
 	}
    
    /**
     * @Description 盈亏一览汇总数据
     * @author 张立增
     * @Date 2020年2月23日 下午6:46:26
     */
    @RequestMapping(value = "/profitLossList", method = { RequestMethod.POST, RequestMethod.GET})
  	public String profitLossList(HttpServletRequest request) throws Exception {
  		String page = request.getParameter("page");    
  		String limit = request.getParameter("limit");
  		String inputSearch = request.getParameter("inputSearch");
  		String startDate = request.getParameter("startDate");
  		String endDate = request.getParameter("endDate");
  		String statisticalMethods = request.getParameter("statisticalMethods");
  		return historyDealService.profitLossList(page,limit,inputSearch,startDate,endDate,statisticalMethods);
  	}
    
    /**
     * @Description 得到总盈亏金额
     * @author 张立增
     * @Date 2020年2月23日 下午8:14:38
     */
    @RequestMapping(value = "/getTotalMoney", method = { RequestMethod.POST, RequestMethod.GET})
  	public String getTotalMoney(HttpServletRequest request) throws Exception {
  		String inputSearch = request.getParameter("inputSearch");
  		String startDate = request.getParameter("startDate");
  		String endDate = request.getParameter("endDate");
  		return historyDealService.getTotalMoney(inputSearch,startDate,endDate);
  	}
    
    
    
    
    
    
}
