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
	private String appearAddr;  //上市地方（sh：上海，sz:深圳）  
	private Date addDate;     //录入系统时间     
	
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
	public String getAppearAddr() {
		return appearAddr;
	}
	public void setAppearAddr(String appearAddr) {
		this.appearAddr = appearAddr;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	
	
	
	
}
