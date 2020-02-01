package com.qingyun.entity;

import java.math.BigDecimal;

/**
 * @Description 数据态势实体类
 * @author 张立增
 * @Date 2020年2月1日 下午7:19:57
 */
public class SituaData {

	private String dataDate;     //数据时间
	private BigDecimal averagePrice;//平均股价
	private BigDecimal gainsPrice;//涨幅股价
	
	public SituaData() {
		super();
	}

	public SituaData(String dataDate, BigDecimal averagePrice, BigDecimal gainsPrice) {
		super();
		this.dataDate = dataDate;
		this.averagePrice = averagePrice;
		this.gainsPrice = gainsPrice;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getGainsPrice() {
		return gainsPrice;
	}

	public void setGainsPrice(BigDecimal gainsPrice) {
		this.gainsPrice = gainsPrice;
	}
	
	
	
	
}
