package com.qingyun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qingyun.entity.User;
import com.qingyun.service.UserService;

/** 
 * @Description 用户管理控制层
 * @author 张立增
 * @Date 2020年1月6日 下午8:12:28
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	/**
	 * @Description 跳转到用户列表页面的方法
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping("/userPage")
	public ModelAndView userPage() {
		ModelAndView mv = new ModelAndView("pages/user/index");
		return mv;
	}
	
	/**
	 * @Description  查询用户列表的方法
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping(value = "/userList", method = { RequestMethod.POST, RequestMethod.GET})
	public String userList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");    
		String limit = request.getParameter("limit");
		String username = request.getParameter("username");
		return userService.userList(page,limit,username);
	}
	
	/**
	 * @Description 根据ids删除用户信息
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping(value = "/deleteUserByids", method = { RequestMethod.POST, RequestMethod.GET})
	public String deleteUserByids(HttpServletRequest request) throws Exception {
		String ids = request.getParameter("ids");    
		return userService.deleteUserByids(ids);
	}
	
	/**
	 * @Description 跳转到编辑用户
	 * @author 张立增
	 * @Date 2020年1月6日 下午8:15:18
	 */
	@RequestMapping("/editPage")
	public ModelAndView editPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pages/user/edit");
		String id = request.getParameter("id");    
		mv.addObject("id", id);
		return mv;
	}
	
	/**
	 * @Description 根据id得到用户记录
	 * @author 张立增
	 * @Date 2020年1月8日 下午1:10:31
	 */
	@RequestMapping(value = "/getUserById", method = { RequestMethod.POST, RequestMethod.GET})
	public String getUserById(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");    
		return userService.getUserById(id);
	}
	
	/**
	 * @Description 校验用户名是否已经存在
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	@RequestMapping(value = "/checkUserByUsername", method = { RequestMethod.POST, RequestMethod.GET})
	public String checkUserByUsername(HttpServletRequest request) throws Exception {
		String username = request.getParameter("username");    
		return userService.checkUserByUsername(username);
	}
	
	/**
	 * @Description 更新用户信息
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	@RequestMapping(value = "/updateUser", method = { RequestMethod.POST, RequestMethod.GET})
	public String updateUser(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");    
		User user = JSONObject.parseObject(data, User.class);
		return userService.updateUser(user);
	}
	
    /**
     * @Description 跳转到添加用户界面
     * @author 张立增
     * @Date 2020年1月8日 下午3:33:32
     */
	@RequestMapping("/addPage")
	public ModelAndView addPage() {
		ModelAndView mv = new ModelAndView("pages/user/add");
		return mv;
	}
	
	/**
	 * @Description 添加用户信息
	 * @author 张立增
	 * @Date 2020年1月8日 下午2:07:40
	 */
	@RequestMapping(value = "/addUser", method = { RequestMethod.POST, RequestMethod.GET})
	public String addUser(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");    
		User user = JSONObject.parseObject(data, User.class);
		return userService.addUser(user);
	}
	
	
	
	
	
	
	
	
	
	
	
}
