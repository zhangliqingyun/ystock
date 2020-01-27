package com.qingyun.entity;

import java.util.Date;

/**
 * @Description 股票基础数据
 * @author 张立增
 * @Date 2020年1月8日 下午10:12:19
 */
public class BasicStock {

	private Integer id;           //主键                                     
	private String stockCode;   //股票代码                               
	private String stockName;   //股票名称                               
	private String appearAddrCode;  //上市地方代码（sh：上海，sz:深圳，s_sh：大盘指数）  
	private String appearAddrName;  //上市地方名称  
	private Date addDate;     //录入系统时间     
	
	public BasicStock() {
		super();
	}
	
	public BasicStock(Integer id, String stockCode, String stockName, String appearAddrCode, String appearAddrName,
			Date addDate) {
		super();
		this.id = id;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.appearAddrCode = appearAddrCode;
		this.appearAddrName = appearAddrName;
		this.addDate = addDate;
	}



	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getStockCode() {
		return stockCode;
	}
	
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	public Date getAddDate() {
		return addDate;
	}
	
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	public String getAppearAddrCode() {
		return appearAddrCode;
	}
	
	public void setAppearAddrCode(String appearAddrCode) {
		this.appearAddrCode = appearAddrCode;
	}
	
	public String getAppearAddrName() {
		return appearAddrName;
	}
	
	public void setAppearAddrName(String appearAddrName) {
		this.appearAddrName = appearAddrName;
	}
	
	
	
	
	
	
}
