package com.qingyun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    
    
}
