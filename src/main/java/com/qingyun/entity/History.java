package com.qingyun.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Description 股票数据表
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-01-28 17:07:39
 */
public class History implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer id;         //
    private String stockCode;         //股票代码
    private String stockName;         //股票名称
    private Date dataDate;         //股票交易时间
    private BigDecimal openPrice;         //开盘价格
    private BigDecimal highPrice;         //最高价格
    private BigDecimal lowPrice;         //最低价
    private BigDecimal nowPrice;         //收盘价
    private BigDecimal diffPrice;         //涨跌
    private BigDecimal diffRate;         //涨跌幅%
    private BigDecimal num;   //成交量
    private BigDecimal price;   //成交额



    public History() {
		super();
	}

	public History(Integer id, String stockCode, String stockName, Date dataDate, BigDecimal openPrice, BigDecimal highPrice, BigDecimal lowPrice, BigDecimal nowPrice, BigDecimal diffPrice, BigDecimal diffRate, BigDecimal num, BigDecimal price) {
		this.id = id;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.dataDate = dataDate;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.nowPrice = nowPrice;
		this.diffPrice = diffPrice;
		this.diffRate = diffRate;
		this.num = num;
		this.price = price;
	}

	@Id
    @Column(name = "id", length = 10)
	public Integer getId() {
		return id;
	}   
	
	public void setId(Integer id) {
		this.id = id;
	}  
		
    @Column(name = "stock_code", length = 10)
	public String getStockCode() {
		return stockCode;
	}   
	
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}  
		
    @Column(name = "stock_name", length = 40)
	public String getStockName() {
		return stockName;
	}   
	
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}  
		
    @Temporal(TemporalType.DATE)
    @Column(name = "data_date", nullable = false, length = 19)
	public Date getDataDate() {
		return dataDate;
	}   
	
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}  
		
    @Column(name = "now_price", nullable = false, precision = 10 , scale =2)
	public BigDecimal getNowPrice() {
		return nowPrice;
	}   
	
	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}  

		
    @Column(name = "diff_price", nullable = false, precision = 10 , scale =2)
	public BigDecimal getDiffPrice() {
		return diffPrice;
	}   
	
	public void setDiffPrice(BigDecimal diffPrice) {
		this.diffPrice = diffPrice;
	}


	@Column(name = "open_price", nullable = false, precision = 10 , scale =2)
	public BigDecimal getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}

	@Column(name = "high_price", nullable = false, precision = 10 , scale =2)
	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	@Column(name = "low_price", nullable = false, precision = 10 , scale =2)
	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	@Column(name = "diff_rate", nullable = false, precision = 10 , scale =4)
	public BigDecimal getDiffRate() {
		return diffRate;
	}

	public void setDiffRate(BigDecimal diffRate) {
		this.diffRate = diffRate;
	}

	@Column(name = "num",  nullable = false, precision = 20 , scale =0)
	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	@Column(name = "price",  nullable = false, precision = 20 , scale =0)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
