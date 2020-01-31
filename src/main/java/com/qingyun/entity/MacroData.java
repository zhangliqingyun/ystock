package com.qingyun.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

/**
 * @Description 宏观一览实体类
 * @author 张立增
 * @Date 2020年1月31日 下午6:11:20
 */
public class MacroData {

	private String stockCode;     //股票代码
	private String stockName;     //股票名称
	private BigDecimal averagePrice;//平均股价
	private BigDecimal nowPrice;//当前股价
	private Date nowPriceDate;//当前股价日期
	private BigDecimal lowPrice;//历史最低
	private Date lowPriceDate;//历史最低日期
	private BigDecimal highPrice;//历史最高
	private Date highPriceDate;//历史最高日期
	private BigDecimal firstPrice;//首发股价
	private Date firstPriceDate;//首发股价日期
	private BigDecimal nowAverageDiff;//当前与平均差值
	private BigDecimal nowLowDiff;//当前与最低差值
	private BigDecimal nowHighDiff;//当前与最高差值
	private BigDecimal nowFirstDiff;//当前与首发差值
	
	public MacroData() {
		super();
	}

	public MacroData(String stockCode, String stockName, BigDecimal averagePrice, BigDecimal nowPrice,
			Date nowPriceDate, BigDecimal lowPrice, Date lowPriceDate, BigDecimal highPrice, Date highPriceDate,
			BigDecimal firstPrice, Date firstPriceDate, BigDecimal nowAverageDiff, BigDecimal nowLowDiff,
			BigDecimal nowHighDiff, BigDecimal nowFirstDiff) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.averagePrice = averagePrice;
		this.nowPrice = nowPrice;
		this.nowPriceDate = nowPriceDate;
		this.lowPrice = lowPrice;
		this.lowPriceDate = lowPriceDate;
		this.highPrice = highPrice;
		this.highPriceDate = highPriceDate;
		this.firstPrice = firstPrice;
		this.firstPriceDate = firstPriceDate;
		this.nowAverageDiff = nowAverageDiff;
		this.nowLowDiff = nowLowDiff;
		this.nowHighDiff = nowHighDiff;
		this.nowFirstDiff = nowFirstDiff;
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

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}

	public Date getNowPriceDate() {
		return nowPriceDate;
	}

	public void setNowPriceDate(Date nowPriceDate) {
		this.nowPriceDate = nowPriceDate;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Date getLowPriceDate() {
		return lowPriceDate;
	}

	public void setLowPriceDate(Date lowPriceDate) {
		this.lowPriceDate = lowPriceDate;
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	public Date getHighPriceDate() {
		return highPriceDate;
	}

	public void setHighPriceDate(Date highPriceDate) {
		this.highPriceDate = highPriceDate;
	}

	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	public Date getFirstPriceDate() {
		return firstPriceDate;
	}

	public void setFirstPriceDate(Date firstPriceDate) {
		this.firstPriceDate = firstPriceDate;
	}

	public BigDecimal getNowAverageDiff() {
		return nowAverageDiff;
	}

	public void setNowAverageDiff(BigDecimal nowAverageDiff) {
		this.nowAverageDiff = nowAverageDiff;
	}

	public BigDecimal getNowLowDiff() {
		return nowLowDiff;
	}

	public void setNowLowDiff(BigDecimal nowLowDiff) {
		this.nowLowDiff = nowLowDiff;
	}

	public BigDecimal getNowHighDiff() {
		return nowHighDiff;
	}

	public void setNowHighDiff(BigDecimal nowHighDiff) {
		this.nowHighDiff = nowHighDiff;
	}

	public BigDecimal getNowFirstDiff() {
		return nowFirstDiff;
	}

	public void setNowFirstDiff(BigDecimal nowFirstDiff) {
		this.nowFirstDiff = nowFirstDiff;
	}
	
	
	
	
	
	
	
}
