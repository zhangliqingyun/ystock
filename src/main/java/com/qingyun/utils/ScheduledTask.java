package com.qingyun.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qingyun.dao.BasicStockDao;
import com.qingyun.dao.StockDataDao;
import com.qingyun.entity.BasicStock;
import com.qingyun.entity.StockData;

import groovy.util.logging.Slf4j;

/**
 * @Description 定时器任务
 * @author 张立增
 * @Date 2020年1月28日 下午5:54:33
 */
@EnableScheduling
@Slf4j
@Component
public class ScheduledTask {
 
    @Autowired
    BasicStockDao basicStockDao;
    
    @Autowired
    StockDataDao stockDataDao;
    
 	@Value("${stock_data_url}")  
 	private String stockDataUrl;     //从配置文件中读取爬股票的url
 	
	/**
	 * @Description 定时器，定时执行爬取网络股票数据任务，参数： 秒 分 时 
	 * @author 张立增
	 * @Date 2020年1月28日 下午5:59:22
	 */
	/* @Scheduled(cron="0 30 15 ? * * ") */
 	@Scheduled(cron="20 03 22 ? * * ")
    public void stockDataTask() {
    	List<BasicStock> list = basicStockDao.basicStockAll();   //查询所有的股票数据
        if(null != list && list.size() > 0) {
        	String searchStockCode = "";
        	for(int i = 0;i < list.size();i++) {
        		searchStockCode += list.get(i).getAppearAddrCode()+list.get(i).getStockCode()+",";
        	}
        	if(null != searchStockCode && searchStockCode.length() > 0) {
        		searchStockCode = searchStockCode.substring(0, searchStockCode.length()-1);
        		searchStockData(searchStockCode,list);   //爬取股票数据
        	}
        }
    }

    /**
     * @Description 爬取股票数据
     * @author 张立增
     * @Date 2020年1月28日 下午7:14:59
     */
	private void searchStockData(String searchStockCode, List<BasicStock> list) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<StockData> nowStockData = new ArrayList<StockData>();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		HttpGet httpGet = new HttpGet("http://hq.sinajs.cn/list="+searchStockCode);// 创建Get请求
		CloseableHttpResponse response = null;// 响应模型
		try {
			response = httpClient.execute(httpGet);// 由客户端执行(发送)Get请求
			HttpEntity responseEntity = response.getEntity();// 从响应模型中获取响应实体
			if (responseEntity != null) {
				String responseString = EntityUtils.toString(responseEntity);
				String[] stockMessageAllArr = responseString.split(";");
				for(int i = 0;i < stockMessageAllArr.length;i++) {         //遍历每支股票数据 var hq_str_sh601006="大秦铁路,7.830,7.840,7.700,7.840,7.670,7.700,7.710,35035022,271046737.000,328595,7.700,277500,7.690,226500,7.680,251200,7.670,266200,7.660,5100,7.710,38600,7.720,153244,7.730,275400,7.740,104200,7.750,2020-01-23,15:00:00,00,"
					String stockMessageOneStr = stockMessageAllArr[i];
					if(null != stockMessageOneStr && stockMessageOneStr.length() > 0) {
						String[] stockMessageArr = stockMessageOneStr.split("=");      //分割等号前后数据 "大秦铁路,7.830,7.840,7.700,7.840,7.670,7.700,7.710,35035022,271046737.000,328595,7.700,277500,7.690,226500,7.680,251200,7.670,266200,7.660,5100,7.710,38600,7.720,153244,7.730,275400,7.740,104200,7.750,2020-01-23,15:00:00,00,"
						if(null != stockMessageArr && stockMessageArr.length >= 2) {
							String[] stockDetailArr = stockMessageArr[1].split(",");   //，分割"大秦铁路,7.830,7.840,7.700,7.840,7.670,7.700,7.710,35035022,271046737.000,328595,7.700,277500,7.690,226500,7.680,251200,7.670,266200,7.660,5100,7.710,38600,7.720,153244,7.730,275400,7.740,104200,7.750,2020-01-23,15:00:00,00,"
							if(null != stockDetailArr && stockDetailArr.length > 0) {
								BigDecimal nowPrice = new BigDecimal(stockDetailArr[3]);  //当前股价
								BigDecimal lastPrice = new BigDecimal(stockDetailArr[2]); //昨日收盘价
								BigDecimal diffPrice = nowPrice.subtract(lastPrice);      //与上次的差值
								Date dataDate = formatter.parse(stockDetailArr[30]);      //股票交易时间
								StockData stockDataTemp = new StockData();
								stockDataTemp.setNowPrice(nowPrice);
								stockDataTemp.setLastPrice(lastPrice);
								stockDataTemp.setDiffPrice(diffPrice);
								stockDataTemp.setDataDate(dataDate);
								nowStockData.add(stockDataTemp);
							}
						}
					}
				}
				if(null != nowStockData && nowStockData.size() > 0) {
					checkDataAndSave(list,nowStockData);    //检查和保存数据
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {// 释放资源
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	   }
	}
	
	/**
	 * @Description 检查和保存数据 
	 * @author 张立增
	 * @Date 2020年1月28日 下午8:41:32
	 */
	private void checkDataAndSave(List<BasicStock> list, List<StockData> nowStockData) {
		if(list.size() == nowStockData.size()) {       //查询到的股票数据需要与股票数量一致，数组是有序的，可以这样进行判断
			List<StockData> insertList = new ArrayList<StockData>();
			List<StockData> maxDateStockData = stockDataDao.getMaxDateStockData();   //查询日期最大的股票数据
		    if(null != maxDateStockData && maxDateStockData.size() > 0) {    //没有查询到股票直接进行保存
		    	for(int i = 0;i < nowStockData.size();i++) {
		    	   String nowStockCode = list.get(i).getStockCode();         //数组是有序的，可以这样取
		    	   Date nowDataDate = nowStockData.get(i).getDataDate();     //爬取的日期
		    	   boolean flag = true;
		    	   for(int j = 0;j < maxDateStockData.size();j++) {          //过滤已经存在的数据
		        	   String maxStockCode = maxDateStockData.get(j).getStockCode();
			    	   Date maxDataDate = maxDateStockData.get(j).getDataDate();
			    	   if(nowStockCode.equals(maxStockCode) && nowDataDate.getTime() == maxDataDate.getTime()) { //已经存在这个日期的数据
			    		   flag = false;
			    	   }
		    	   }
		    	   if(flag) {
		    		   StockData stockDataTemp = nowStockData.get(i);
		    		   stockDataTemp.setStockCode(nowStockCode);
		    		   stockDataTemp.setStockName(list.get(i).getStockName());
		    		   stockDataTemp.setBasicStockId(list.get(i).getId());
		    		   insertList.add(stockDataTemp);
		    	   }
		    	}
		    }else {
		    	for(int i = 0;i < nowStockData.size();i++) {
		    		nowStockData.get(i).setStockCode(list.get(i).getStockCode());
		    		nowStockData.get(i).setStockName(list.get(i).getStockName());
		    		nowStockData.get(i).setBasicStockId(list.get(i).getId());
		    	}
		    	insertList.addAll(nowStockData);
		    }
		    if(null != insertList && insertList.size() > 0) {    //保存组织好的股票数据
		    	stockDataDao.saveStockData(insertList);  
		    	sendMailToUser();    //给用户发送邮件
		    	System.out.println("插入股票数据成功，插入记录："+insertList.size()+"条");
		    }else {
		    	System.out.println("没有最新股票数据，不进行插入操作");
		    }
		}else {
			System.out.println("获取到的股票数据与股票数量对应不上，不执行插入操作。股票记录数量："+list.size()+"，爬取到的记录数量："+nowStockData.size());
		}
	}
	
	/**
	 * @Description 给用户发送邮件
	 * @author 张立增
	 * @Date 2020年2月4日 下午8:56:44
	 */
	private void sendMailToUser() {
		
	}

	public static String sendMail() {
		try{
			 String toEmailAddress="1472052711@qq.com";
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 Date date = new Date();
			 //邮件主题
			 String emailTitle=formatter.format(date)+"股票分析预警消息";
			 //邮件内容
			 String emailContent="贵州茅台提示你应该买了...";
             //发送邮件
             SendmailUtil.sendEmail(toEmailAddress, emailTitle, emailContent);
			 return CalculatorUtil.getJSONString(0);
	    }catch(Exception e){
			 return CalculatorUtil.getJSONString(1,"邮件发送失败！");
		}
	}
	
}