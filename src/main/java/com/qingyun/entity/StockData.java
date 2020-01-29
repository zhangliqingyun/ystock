package com.qingyun.entity; 

import java.math.BigDecimal;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id; 
import javax.persistence.Column;  

/**
 * @Description 股票数据表
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-01-28 17:07:39
 */
public class StockData implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer id;         //
    private String stockCode;         //股票代码
    private String stockName;         //股票名称
    private Date dataDate;         //股票交易时间
    private BigDecimal nowPrice;         //当前价格
    private BigDecimal lastPrice;         //上次交易价格
    private BigDecimal diffPrice;         //与上次的差值
    private Integer basicStockId;   //股票基本信息主键id，目前用于删除数据

    public StockData() {
		super();
	}
    
	public StockData(Integer id, String stockCode, String stockName, Date dataDate, BigDecimal nowPrice,
			BigDecimal lastPrice, BigDecimal diffPrice) {
		super();
		this.id = id;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.dataDate = dataDate;
		this.nowPrice = nowPrice;
		this.lastPrice = lastPrice;
		this.diffPrice = diffPrice;
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
		
    @Column(name = "last_price", nullable = false, precision = 10 , scale =2)
	public BigDecimal getLastPrice() {
		return lastPrice;
	}   
	
	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}  
		
    @Column(name = "diff_price", nullable = false, precision = 10 , scale =2)
	public BigDecimal getDiffPrice() {
		return diffPrice;
	}   
	
	public void setDiffPrice(BigDecimal diffPrice) {
		this.diffPrice = diffPrice;
	}

	@Column(name = "id", length = 10)
	public Integer getBasicStockId() {
		return basicStockId;
	}

	public void setBasicStockId(Integer basicStockId) {
		this.basicStockId = basicStockId;
	}  
	
	
		
}
