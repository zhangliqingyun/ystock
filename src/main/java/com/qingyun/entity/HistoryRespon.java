package com.qingyun.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 股票数据表
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-01-28 17:07:39
 */
public class HistoryRespon implements Serializable {
	private static final long serialVersionUID = 1L;
    private String stockCode;         //股票代码
    private String stockName;         //股票名称
    private Date dataDate;            //最近交易时间
    private BigDecimal nowPrice;         //最近股价
    private BigDecimal avgPrice;         //平均股价
	private BigDecimal diffPrice;         //总的涨额
    private BigDecimal diffRate;         //总的涨幅%
	private BigDecimal num;              //总的成交手数
    private BigDecimal everyDayNum;      //每天平均成交手数
    private BigDecimal newNum;           //最新成交手数
    private BigDecimal price;            //总的成交量
    private BigDecimal everyDayPrice;    //每天平均成交量
    private BigDecimal newPrice;         //最近成交量
    private BigDecimal upPrice;          //上涨时总的成交量
    private BigDecimal downPrice;        //下跌时总的成交量
    private BigDecimal centerPrice;      //不涨不跌总的成交量
    private BigDecimal upDownPrice;      //上涨与下跌总成交量差值
    private BigDecimal upDownCenterPrice; //上涨与下跌、平的成交量差值
    private BigDecimal upForDownPriceRate; //上涨总量/下跌总量%
    private BigDecimal downForUpPriceRate; //下跌总量/上涨总量%
    private BigDecimal upDownCenterForUpPriceRate; //上涨与下跌、平的成交量差值/上涨时总成交量%
    private BigDecimal upDownCenterForDownPriceRate; //上涨与下跌、平的成交量差值/下跌时总成交量%

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

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public BigDecimal getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}

	public BigDecimal getDiffPrice() {
		return diffPrice;
	}

	public void setDiffPrice(BigDecimal diffPrice) {
		this.diffPrice = diffPrice;
	}

	public BigDecimal getDiffRate() {
		return diffRate;
	}

	public void setDiffRate(BigDecimal diffRate) {
		this.diffRate = diffRate;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getNewNum() {
		return newNum;
	}

	public void setNewNum(BigDecimal newNum) {
		this.newNum = newNum;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getEveryDayPrice() {
		return everyDayPrice;
	}

	public void setEveryDayPrice(BigDecimal everyDayPrice) {
		this.everyDayPrice = everyDayPrice;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}

	public BigDecimal getUpPrice() {
		return upPrice;
	}

	public void setUpPrice(BigDecimal upPrice) {
		this.upPrice = upPrice;
	}

	public BigDecimal getDownPrice() {
		return downPrice;
	}

	public void setDownPrice(BigDecimal downPrice) {
		this.downPrice = downPrice;
	}

	public BigDecimal getCenterPrice() {
		return centerPrice;
	}

	public void setCenterPrice(BigDecimal centerPrice) {
		this.centerPrice = centerPrice;
	}

	public BigDecimal getUpDownPrice() {
		return upDownPrice;
	}

	public void setUpDownPrice(BigDecimal upDownPrice) {
		this.upDownPrice = upDownPrice;
	}

	public BigDecimal getUpDownCenterPrice() {
		return upDownCenterPrice;
	}

	public void setUpDownCenterPrice(BigDecimal upDownCenterPrice) {
		this.upDownCenterPrice = upDownCenterPrice;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public BigDecimal getEveryDayNum() {
		return everyDayNum;
	}

	public void setEveryDayNum(BigDecimal everyDayNum) {
		this.everyDayNum = everyDayNum;
	}

	public BigDecimal getUpDownCenterForUpPriceRate() {
		return upDownCenterForUpPriceRate;
	}

	public void setUpDownCenterForUpPriceRate(BigDecimal upDownCenterForUpPriceRate) {
		this.upDownCenterForUpPriceRate = upDownCenterForUpPriceRate;
	}

	public BigDecimal getUpForDownPriceRate() {
		return upForDownPriceRate;
	}

	public void setUpForDownPriceRate(BigDecimal upForDownPriceRate) {
		this.upForDownPriceRate = upForDownPriceRate;
	}

	public BigDecimal getDownForUpPriceRate() {
		return downForUpPriceRate;
	}

	public void setDownForUpPriceRate(BigDecimal downForUpPriceRate) {
		this.downForUpPriceRate = downForUpPriceRate;
	}

	public BigDecimal getUpDownCenterForDownPriceRate() {
		return upDownCenterForDownPriceRate;
	}

	public void setUpDownCenterForDownPriceRate(BigDecimal upDownCenterForDownPriceRate) {
		this.upDownCenterForDownPriceRate = upDownCenterForDownPriceRate;
	}
}
