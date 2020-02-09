package com.qingyun.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.BasicStockDao;
import com.qingyun.dao.HistoryDealDao;
import com.qingyun.dao.HoldStockDao;
import com.qingyun.entity.BasicStock;
import com.qingyun.entity.HistoryDeal;
import com.qingyun.entity.HoldStock;
import com.qingyun.entity.Message;
import com.qingyun.utils.DateUtils;
import com.qingyun.utils.PageUtils;

/**
 * @Description 
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @email 1472052711@qq.com
 * @date 2020-02-08 17:52:07
 */
 @Service
public class HoldStockService {

    @Autowired
    HoldStockDao holdStockDao;
    
    @Autowired
    BasicStockDao basicStockDao;
    
    @Autowired
    HistoryDealDao historyDealDao;

    /**
     * @Description 查询持仓股票记录
     * @author 张立增
     * @Date 2020年2月8日 下午7:42:45
     */
	public String holdStockList(String page, String limit, String inputSearch) {
		try {
			Integer startIndex = PageUtils.getStartIndex(page, limit);
			Map<String, Object> data = new HashedMap<String, Object>();
	        data.put("startIndex",startIndex);
	        data.put("pageSize", Integer.parseInt(limit));
	        data.put("inputSearch", inputSearch);
			List<HoldStock> list = holdStockDao.holdStockList(data);
			Integer totalListSize = holdStockDao.getTotalListSize(inputSearch);   //得到所有的数量
			Object object = PageUtils.makeObject(list,totalListSize);
			return JSONObject.toJSONString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * @Description 保存购买股票记录
     * @author 张立增
     * @Date 2020年2月8日 下午11:35:11
     */
	public String saveBuyData(HoldStock holdStock) {
		Message message = new Message();
		try {
			List<BasicStock> listBasicStock = basicStockDao.findById(holdStock.getBasicStockId()); 
			if(null != listBasicStock && listBasicStock.size() > 0) {
				String stockCode = listBasicStock.get(0).getStockCode();
				String stockName = listBasicStock.get(0).getStockName();
				Integer historyDealId = saveBuyHistoryDeal(stockCode,stockName,holdStock);   //保存买的交易记录
				List<HoldStock> listHoldStock = holdStockDao.findByStockCode(stockCode);     //根据股票代码查询是否已有持仓
				holdStock.setStockCode(stockCode);
				holdStock.setStockName(stockName);
				holdStock.setAddDate(new Date());
				if(null == listHoldStock || listHoldStock.size() == 0) {   
					holdStock.setHistoryDealIds(String.valueOf(historyDealId));
				}else {//之前已经存在买过此股票，重新计算买入价格（求平均值），买入日期使用之前的
					holdStock.setBulidDate(listHoldStock.get(0).getBulidDate());
					Integer holdNumOld = listHoldStock.get(0).getHoldNum();        //之前的持仓数量
					BigDecimal servicePriceOld = listHoldStock.get(0).getServicePrice();//之前的手续费
					Integer holdNumThis = holdStock.getHoldNum();  //这次的持仓数量
					Integer holdNumTotal = holdNumOld+holdNumThis; //持仓数量相加
					holdStock.setHoldNum(holdNumTotal); 
					holdStock.setServicePrice(servicePriceOld.add(holdStock.getServicePrice())); //手续费相加
					BigDecimal buyPriceThis = holdStock.getBuyPrice();  //这次的成交价
					BigDecimal buyPriceOld = listHoldStock.get(0).getBuyPrice(); //之前的成交价
					//重新计算成交价：（之前的成交价*之前的持仓数量+这次的成交价*这次的持仓数量）/（之前的持仓数量+这次的持仓数量）
					BigDecimal priceTotal = (buyPriceOld.multiply(new BigDecimal(holdNumOld.toString()))).add(buyPriceThis.multiply(new BigDecimal(holdNumThis.toString())));//计算出所有的购买股票钱数量
					BigDecimal buyPriceNew = priceTotal.divide(new BigDecimal(holdNumTotal.toString()),3, BigDecimal.ROUND_HALF_UP);
					holdStock.setBuyPrice(buyPriceNew);
					holdStock.setHistoryDealIds(listHoldStock.get(0).getHistoryDealIds()+","+String.valueOf(historyDealId));   //股票交易主键id使用逗号分隔
					holdStockDao.deleteByStockCode(stockCode);   //根据股票代码删除持仓数据
				}
				holdStockDao.saveBuyData(holdStock);
				message.setType(Message.OK);
				message.setMsg("保存购买股票记录成功");
			}else {
				message.setType(Message.ERROR);
				message.setMsg("查询不到此股票的基础信息，请确认信息的准确性");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("保存购买股票记录错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description  保存买的交易记录
	 * @author 张立增
	 * @Date 2020年2月9日 下午2:53:16
	 */
	private Integer saveBuyHistoryDeal(String stockCode, String stockName, HoldStock holdStock)throws Exception {
		HistoryDeal historyDeal = new HistoryDeal();
		historyDeal.setStockCode(stockCode);
		historyDeal.setStockName(stockName);
		historyDeal.setDealDate(holdStock.getBulidDate());
		historyDeal.setDealUnitPrice(holdStock.getBuyPrice());
		historyDeal.setDealNum(holdStock.getHoldNum());
		historyDeal.setBuySellFlag("买入");
		historyDeal.setDealPriceTotal(holdStock.getBuyPrice().multiply(new BigDecimal(holdStock.getHoldNum().toString())));
		historyDeal.setServicePrice(holdStock.getServicePrice());
		String nowString = DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss"); //当前日期转成字符串，处理保存时会多出来一秒的问题
		Date nowDate = DateUtils.formatDate(nowString, "yyyy-MM-dd HH:mm:ss");
		historyDeal.setAddDate(nowDate);
		historyDealDao.saveHistoryDeal(historyDeal);
		Map<String, Object> params = new HashedMap<String, Object>();
		params.put("stockCode",stockCode);
		params.put("addDate",nowString);
		return historyDealDao.findIdByStockCodeAndDate(params);   //通过股票代码，入库时间查询id
	}

	/**
	 * @Description 持仓股票下拉集合
	 * @author 张立增
	 * @Date 2020年2月9日 下午4:46:39
	 */
	public String holdStockListCombox() {
		try {
			List<HoldStock> list = holdStockDao.holdStockListCombox();
			return JSONObject.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description 保存卖出股票记录
	 * @author 张立增
	 * @Date 2020年2月9日 下午5:38:34
	 */
	public String saveSellData(HoldStock holdStock) {
		Message message = new Message();
		try {
			List<HoldStock> list = holdStockDao.findById(holdStock.getId());
			if(null != list && list.size() > 0) {
				HoldStock holdStockDataBase = list.get(0);     //数据库中存放的持仓记录
				Integer holdNumDataBase = holdStockDataBase.getHoldNum();
				Integer sellNum = holdStock.getHoldNum();      //卖出数量
				if(sellNum < holdNumDataBase) {                //部分卖出，更新持仓数量，买的手续费不计入盈亏里面
					sellPartHoldStock(holdStock,holdStockDataBase);
				}else {                                        //相等，全部卖出，清仓，删除持仓记录，把买的手续费计算到盈亏里面
					clearHoldStock(holdStock,holdStockDataBase);
				}
				message.setType(Message.OK);
				message.setMsg("保存卖出股票记录成功");
			}else {
				message.setType(Message.ERROR);
				message.setMsg("查询不到此股票的持仓信息，请确认信息的准确性");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("保存卖出股票记录错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description 部分卖出，更新持仓数量，买的手续费不计入盈亏里面 
	 * @author 张立增
	 * @Date 2020年2月9日 下午6:47:25
	 */
	private void sellPartHoldStock(HoldStock holdStock, HoldStock holdStockDataBase) {
		holdStockDataBase.setHoldNum(holdStockDataBase.getHoldNum()-holdStock.getHoldNum());   //更新持仓数量
		holdStockDao.updateHoldNum(holdStockDataBase);
		HistoryDeal historyDeal = new HistoryDeal();
		historyDeal.setStockCode(holdStockDataBase.getStockCode());
		historyDeal.setStockName(holdStockDataBase.getStockName());
		historyDeal.setDealDate(holdStock.getBulidDate());
		historyDeal.setDealUnitPrice(holdStock.getBuyPrice());
		historyDeal.setDealNum(holdStock.getHoldNum());
		historyDeal.setBuySellFlag("卖出");
		historyDeal.setDealPriceTotal(holdStock.getBuyPrice().multiply(new BigDecimal(holdStock.getHoldNum().toString())));
		historyDeal.setServicePrice(holdStock.getServicePrice());
		BigDecimal servicePriceTotal = holdStock.getServicePrice(); //总手续费：卖出
		BigDecimal buyPriceTotal = holdStockDataBase.getBuyPrice().multiply(new BigDecimal(holdStock.getHoldNum().toString()));
		BigDecimal profitLoss = historyDeal.getDealPriceTotal().subtract(servicePriceTotal).subtract(buyPriceTotal);
		historyDeal.setProfitLoss(profitLoss); //盈亏：成交额-购买价*数量-购买手续-卖出手续
		historyDeal.setAddDate(new Date());
		historyDealDao.saveHistoryDeal(historyDeal);
	}

	/**
	 * @Description 清仓，删除持仓记录，把买的手续费计算到盈亏里面
	 * @author 张立增
	 * @Date 2020年2月9日 下午6:27:28
	 */
	private void clearHoldStock(HoldStock holdStock, HoldStock holdStockDataBase) {
		HistoryDeal historyDeal = new HistoryDeal();
		historyDeal.setStockCode(holdStockDataBase.getStockCode());
		historyDeal.setStockName(holdStockDataBase.getStockName());
		historyDeal.setDealDate(holdStock.getBulidDate());
		historyDeal.setDealUnitPrice(holdStock.getBuyPrice());
		historyDeal.setDealNum(holdStock.getHoldNum());
		historyDeal.setBuySellFlag("卖出");
		historyDeal.setDealPriceTotal(holdStock.getBuyPrice().multiply(new BigDecimal(holdStock.getHoldNum().toString())));
		historyDeal.setServicePrice(holdStock.getServicePrice());
		BigDecimal servicePriceTotal = holdStock.getServicePrice().add(holdStockDataBase.getServicePrice()); //总手续费：购买+卖出
		BigDecimal buyPriceTotal = holdStockDataBase.getBuyPrice().multiply(new BigDecimal(holdStockDataBase.getHoldNum().toString()));
		BigDecimal profitLoss = historyDeal.getDealPriceTotal().subtract(servicePriceTotal).subtract(buyPriceTotal);
		historyDeal.setProfitLoss(profitLoss); //盈亏：成交额-购买价*数量-购买手续-卖出手续
		historyDeal.setAddDate(new Date());
		historyDealDao.saveHistoryDeal(historyDeal);
		holdStockDao.deleteById(holdStockDataBase.getId());    //根据id删除持仓记录
	}

	/**
	 * @Description 根据ids删除记录
	 * @author 张立增
	 * @Date 2020年2月9日 下午7:05:59
	 */
	public String deleteDataByids(String ids) {
		Message message = new Message();
		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {
				List<HoldStock> list = holdStockDao.findById(Integer.parseInt(idArr[i]));
				if(null != list && list.size() > 0) {
					holdStockDao.deleteById(Integer.parseInt(idArr[i]));
					deleteHistoryDealByIds(list.get(0).getHistoryDealIds());  //根据ids删除历史交易记录
				}
			}
			message.setType(Message.OK);
			message.setMsg("删除股票成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("删除股票错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description 根据ids删除历史交易记录
	 * @author 张立增
	 * @Date 2020年2月9日 下午7:11:30
	 */
	private void deleteHistoryDealByIds(String ids) {
		String[] idArr = ids.split(",");
		for (int i = 0; i < idArr.length; i++) {
			historyDealDao.deleteById(Integer.parseInt(idArr[i]));
		}
	}
    
    
}

