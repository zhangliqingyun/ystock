package com.qingyun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingyun.dao.BasicStockDao;


@Service
public class BasicStockService {

    @Autowired
    BasicStockDao basicStockDao;
    
}
