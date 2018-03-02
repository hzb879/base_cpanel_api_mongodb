package com.swk.cpanel.api.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swk.cpanel.api.bean.ResponseData;
import com.swk.cpanel.api.entity.User;
import com.swk.cpanel.api.exception.ServiceException;
import com.swk.cpanel.api.service.UserService;
import com.swk.cpanel.api.util.MongoQueryUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags="用户操作相关")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@ApiOperation(value="根据条件查询分页查询用户")
	@GetMapping
	@RequiresRoles("admin")
	public ResponseData<Page<User>> findAll(
			 @PageableDefault(page=0,size=10) Pageable pageable,
			@RequestParam(required=false) String nickname) {
		Query query = MongoQueryUtil
						.queryBuilder(pageable)
						.regex("nickname", nickname)
						.build();
		return ResponseData.success(userService.getAll(query, pageable));
	}
	
	@ApiOperation(value="获取用户个人信息")
	@GetMapping("/info")
	public ResponseData<User> info() throws ServiceException {
		return ResponseData.success(userService.getInfoWithRoles(SecurityUtils.getSubject().getPrincipal().toString()));
	}
	
	@ApiOperation(value="注册新用户")
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	@RequiresRoles("admin")
	public ResponseData<User> add(@RequestBody User user) {
		return ResponseData.success(userService.registUser(user.getUsername(), user.getPassword()));
	}
	
	
}
