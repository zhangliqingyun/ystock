package com.qingyun.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingyun.entity.User;

@Repository
public interface UserDao {

	List<User> findUserList();

    /**
     * @Description 通过用户名查询用户集合的方法
     * @author 张立增[zhanglizeng] Tel：18860126570
     * @createDate 2019年1月25日 上午11:21:45
     */
	List<User> getUserListByName(@Param("userName")String userName);

	/**
	 * @Description  查询用户列表的方法
	 * @author 张立增
	 * @param limit 
	 * @Date 2020年1月6日 下午8:15:18
	 */
	List<User> userList(Map<String, Object> data);

	/**
	 * @Description 得到所有的数量
	 * @author 张立增
	 * @Date 2020年1月6日 下午9:53:36
	 */
	Integer getTotalListSize(@Param("username")String username);

	void deleteUserByid(String id);

	User getUserById(@Param("id")String id);

	List<User> getUserListEqualsName(@Param("username")String username);

	void updateUser(User user);

	void addUser(User user);  
	
	
}
