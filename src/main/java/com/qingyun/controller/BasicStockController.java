package com.qingyun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	
}
