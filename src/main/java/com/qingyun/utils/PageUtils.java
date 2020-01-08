package com.qingyun.utils;

import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
/**
 * @Description 处理分页工具类
 * @author 张立增
 * @Date 2020年1月6日 下午9:45:44
 */
public class PageUtils {

	public static String getLimit(String page, String limit) {
		if (page != null && page.length() > 0 && limit != null && limit.length() > 0) {
			return " limit " + (Integer.parseInt(page) - 1) * Integer.parseInt(limit) + " , " + Integer.parseInt(limit);
		} else {
			return "";
		}
	}
	
	/**
	 * @Description 构造layui需要的数据对象
	 */
	public static Object makeObject(List<?> list,Integer totalListSize) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 0);
		jsonObject.put("msg", "");
		jsonObject.put("count",totalListSize);
		jsonObject.put("data", list.toArray());
		return jsonObject;
	}

	/**
	 * @Description 得到起始索引
	 * @author 张立增
	 * @Date 2020年1月6日 下午10:15:25
	 */
	public static Integer getStartIndex(String page, String limit) {
		if(!StringUtils.isEmpty(page) && !StringUtils.isEmpty(limit) ) {
			return (Integer.parseInt(page)-1)*Integer.parseInt(limit);
		}
		return 0;
	}
}
