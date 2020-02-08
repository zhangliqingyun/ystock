package com.qingyun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.entity.HoldStock;
import com.qingyun.service.HoldStockService;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-02-08 17:52:07
 */
@RestController
@RequestMapping("/holdStock")
public class HoldStockController {

    @Autowired
    HoldStockService holdStockService;
    
    /**
     * @Description 跳转到持仓股票页面
     * @author 张立增
     * @Date 2020年2月8日 下午7:42:26
     */
    @RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("pages/holdstock/index");
		return mv;
	}
    
    /**
     * @Description 查询持仓股票记录
     * @author 张立增
     * @Date 2020年2月8日 下午7:42:45
     */
    @RequestMapping(value = "/holdStockList", method = { RequestMethod.POST, RequestMethod.GET})
	public String holdStockList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");    
		String limit = request.getParameter("limit");
		String inputSearch = request.getParameter("inputSearch");
		return holdStockService.holdStockList(page,limit,inputSearch);
	}
    
    /**
     * @Description 跳转到买入股票页面
     * @author 张立增
     * @Date 2020年2月8日 下午7:42:26
     */
    @RequestMapping("/buyPage")
	public ModelAndView buyPage() {
		ModelAndView mv = new ModelAndView("pages/holdstock/buy");
		return mv;
	}
    
    /**
     * @Description 跳转到卖出股票页面
     * @author 张立增
     * @Date 2020年2月8日 下午7:42:26
     */
    @RequestMapping("/sellPage")
	public ModelAndView sellPage() {
		ModelAndView mv = new ModelAndView("pages/holdstock/sell");
		return mv;
	}
    
    /**
     * @Description 保存购买股票记录
     * @author 张立增
     * @Date 2020年2月8日 下午11:35:11
     */
	@RequestMapping(value = "/saveBuyData", method = { RequestMethod.POST, RequestMethod.GET})
	public String saveBuyData(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");    
		HoldStock holdStock = JSONObject.parseObject(data, HoldStock.class);
		return holdStockService.saveBuyData(holdStock);
	}
    
    
  
   
}
