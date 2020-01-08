package com.qingyun.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.dao.UserDao;
import com.qingyun.entity.Message;
import com.qingyun.entity.User;
import com.qingyun.utils.PageUtils;

@Service
public class UserService {

     @Autowired
     UserDao userDao;
		   
     public List<User> findUserList() {
		return userDao.findUserList();
     }

    /**
     * @Description 通过用户名查询用户集合的方法
     * @author 张立增[zhanglizeng] Tel：18860126570
     * @createDate 2019年1月25日 上午11:21:45
     */
	public List<User> getUserListByName(String userName) {
		return userDao.getUserListByName(userName);
	}

	/**
	 * @Description  查询用户列表的方法
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	public String userList(String page, String limit, String username) {
		Integer startIndex = PageUtils.getStartIndex(page, limit);
		Map<String, Object> data = new HashedMap<String, Object>();
        data.put("startIndex",startIndex);
        data.put("pageSize", Integer.parseInt(limit));
        data.put("username", username);
		List<User> list = userDao.userList(data);
		Integer totalListSize = userDao.getTotalListSize(username);   //得到所有的数量
		Object object = PageUtils.makeObject(list,totalListSize);
		return JSONObject.toJSONString(object);
	}

	/**
	 * @Description 根据ids删除用户信息
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	public String deleteUserByids(String ids) {
		Message message = new Message();
		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {
				userDao.deleteUserByid(idArr[i]);
			}
			message.setType(Message.OK);
			message.setMsg("删除用户成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("删除用户错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description 根据id得到用户记录
	 * @author 张立增
	 * @Date 2020年1月8日 下午1:10:31
	 */
	public String getUserById(String id) {
		User user = userDao.getUserById(id);
		return JSONObject.toJSONString(user);
	}

	/**
	 * @Description 校验用户名是否已经存在
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	public String checkUserByUsername(String username) {
		List<User> list = userDao.getUserListEqualsName(username);
		if(null == list || list.size() == 0) {
			return "1";
		}else {
			return "0";
		}
	}

	/**
	 * @Description 更新用户信息
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	public String updateUser(User user) {
		Message message = new Message();
		try {
			userDao.updateUser(user);
			message.setType(Message.OK);
			message.setMsg("更新用户信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("更新用户信息错误");
		}
		return JSONObject.toJSONString(message);
	}

	/**
	 * @Description 添加用户信息
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	public String addUser(User user) {
		Message message = new Message();
		try {
			userDao.addUser(user);
			message.setType(Message.OK);
			message.setMsg("添加用户信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType(Message.ERROR);
			message.setMsg("添加用户信息错误");
		}
		return JSONObject.toJSONString(message);
	}
}
