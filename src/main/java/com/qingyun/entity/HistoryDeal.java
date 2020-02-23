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
 * @Description 历史交易表
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-02-08 17:52:07
 */
public class HistoryDeal implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer id;         //主键
    private String stockCode;         //股票代码
    private String stockName;         //股票名称
    private Date dealDate;         //成交日期
    private BigDecimal dealUnitPrice;         //成交单价
    private Integer dealNum;         //成交量
    private String buySellFlag;         //买或卖标识
    private BigDecimal dealPriceTotal;         //成交额
    private BigDecimal servicePrice;         //手续费
    private BigDecimal profitLoss;         //盈亏（卖出股票才有值）
    private Date addDate;         //录入系统日期
    
    private String tradingDate;   //交易日期、
    private BigDecimal profitLossPercent;//盈亏比例
	
    public HistoryDeal() {
		super();
	}

	public HistoryDeal(Integer id, String stockCode, String stockName, Date dealDate, BigDecimal dealUnitPrice,
			Integer dealNum, String buySellFlag, BigDecimal dealPriceTotal, BigDecimal servicePrice,
			BigDecimal profitLoss, Date addDate, String tradingDate, BigDecimal profitLossPercent) {
		super();
		this.id = id;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.dealDate = dealDate;
		this.dealUnitPrice = dealUnitPrice;
		this.dealNum = dealNum;
		this.buySellFlag = buySellFlag;
		this.dealPriceTotal = dealPriceTotal;
		this.servicePrice = servicePrice;
		this.profitLoss = profitLoss;
		this.addDate = addDate;
		this.tradingDate = tradingDate;
		this.profitLossPercent = profitLossPercent;
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
    @Column(name = "deal_date", nullable = false, length = 19)
	public Date getDealDate() {
		return dealDate;
	}   
	
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}  
		
    @Column(name = "deal_unit_price", nullable = false, precision = 10 , scale =3)
	public BigDecimal getDealUnitPrice() {
		return dealUnitPrice;
	}   
	
	public void setDealUnitPrice(BigDecimal dealUnitPrice) {
		this.dealUnitPrice = dealUnitPrice;
	}  
		
    @Column(name = "deal_num", length = 10)
	public Integer getDealNum() {
		return dealNum;
	}   
	
	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}  
		
    @Column(name = "buy_sell_flag", length = 10)
	public String getBuySellFlag() {
		return buySellFlag;
	}   
	
	public void setBuySellFlag(String buySellFlag) {
		this.buySellFlag = buySellFlag;
	}  
		
    @Column(name = "deal_price_total", nullable = false, precision = 10 , scale =3)
	public BigDecimal getDealPriceTotal() {
		return dealPriceTotal;
	}   
	
	public void setDealPriceTotal(BigDecimal dealPriceTotal) {
		this.dealPriceTotal = dealPriceTotal;
	}  
		
    @Column(name = "service_price", nullable = false, precision = 5 , scale =3)
	public BigDecimal getServicePrice() {
		return servicePrice;
	}   
	
	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}  
		
    @Column(name = "profit_loss", nullable = false, precision = 10 , scale =3)
	public BigDecimal getProfitLoss() {
		return profitLoss;
	}   
	
	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}  
		
    @Temporal(TemporalType.DATE)
    @Column(name = "add_date", nullable = false, length = 19)
	public Date getAddDate() {
		return addDate;
	}   
	
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}  
	
	public String getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(String tradingDate) {
		this.tradingDate = tradingDate;
	}

	public BigDecimal getProfitLossPercent() {
		return profitLossPercent;
	}

	public void setProfitLossPercent(BigDecimal profitLossPercent) {
		this.profitLossPercent = profitLossPercent;
	}


		
}
