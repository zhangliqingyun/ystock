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
 * @Description 持仓股票表
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-02-08 17:52:07
 */
public class HoldStock implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer id;         //主键
    private String stockCode;         //股票代码
    private String stockName;         //股票名称
    private Date bulidDate;         //建仓时间
    private Integer holdNum;         //持仓数量
    private BigDecimal buyPrice;         //成交价
    private BigDecimal servicePrice;         //手续费
    private Date addDate;         //录入系统日期
    private String historyDealIds; //历史交易ids，使用逗号分隔，用于删除时使用
    
    private BigDecimal nowPrice;   //当前价
    private BigDecimal nowProfitLoss;   //当前盈亏
    private Integer basicStockId;   //股票基本信息主键id
    private BigDecimal marketValue; //市值
	
    public HoldStock() {
		super();
	}

	public HoldStock(Integer id, String stockCode, String stockName, Date bulidDate, Integer holdNum,
			BigDecimal buyPrice, BigDecimal servicePrice, Date addDate, BigDecimal nowPrice, BigDecimal nowProfitLoss,Integer basicStockId,String historyDealIds,BigDecimal marketValue) {
		super();
		this.id = id;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.bulidDate = bulidDate;
		this.holdNum = holdNum;
		this.buyPrice = buyPrice;
		this.servicePrice = servicePrice;
		this.addDate = addDate;
		this.nowPrice = nowPrice;
		this.nowProfitLoss = nowProfitLoss;
		this.basicStockId = basicStockId;
		this.historyDealIds = historyDealIds;
		this.marketValue = marketValue;
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
    @Column(name = "bulid_date", nullable = false, length = 19)
	public Date getBulidDate() {
		return bulidDate;
	}   
	
	public void setBulidDate(Date bulidDate) {
		this.bulidDate = bulidDate;
	}  
		
    @Column(name = "hold_num", length = 10)
	public Integer getHoldNum() {
		return holdNum;
	}   
	
	public void setHoldNum(Integer holdNum) {
		this.holdNum = holdNum;
	}  
		
    @Column(name = "buy_price", nullable = false, precision = 10 , scale =3)
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}   
	
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}  
		
    @Column(name = "service_price", nullable = false, precision = 5 , scale =3)
	public BigDecimal getServicePrice() {
		return servicePrice;
	}   
	
	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}  
		
    @Temporal(TemporalType.DATE)
    @Column(name = "add_date", nullable = false, length = 19)
	public Date getAddDate() {
		return addDate;
	}   
	
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	@Column(name = "history_deal_ids", length = 500)
	public String getHistoryDealIds() {
		return historyDealIds;
	}

	public void setHistoryDealIds(String historyDealIds) {
		this.historyDealIds = historyDealIds;
	}

	public BigDecimal getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}

	public BigDecimal getNowProfitLoss() {
		return nowProfitLoss;
	}

	public void setNowProfitLoss(BigDecimal nowProfitLoss) {
		this.nowProfitLoss = nowProfitLoss;
	}

	public Integer getBasicStockId() {
		return basicStockId;
	}

	public void setBasicStockId(Integer basicStockId) {
		this.basicStockId = basicStockId;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}  
	
	
	
	
		
	
}
