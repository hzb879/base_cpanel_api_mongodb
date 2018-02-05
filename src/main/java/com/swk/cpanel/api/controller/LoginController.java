package com.swk.cpanel.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swk.cpanel.api.bean.ResponseData;
import com.swk.cpanel.api.entity.User;
import com.swk.cpanel.api.repository.UserRepository;
import com.swk.cpanel.api.util.JwtUtil;
import com.swk.cpanel.api.util.ShiroEncryptUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class LoginController {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expired}")
	private long expired;
	
	@Resource
	private UserRepository userRepository;
	
	@ApiOperation(value="登录认证获取token")
	@PostMapping("/login")
	public ResponseData<String> login(@ApiParam(value="用户名",required=true) @RequestParam String username,
							@ApiParam(value="密码",required=true) @RequestParam String password) {
		User user = userRepository.findOneByUsername(username);
		if(Objects.nonNull(user)) {
			boolean passwordCorrect = ShiroEncryptUtil.verifyPassword(password, user.getPassword(), user.getSalt()); 
			if(passwordCorrect) {
				Map<String, Object> claims=new HashMap<>();
				claims.put("id", user.getId());
				return ResponseData.success(JwtUtil.createJWT(claims, secret, expired));
			}
		}
		return ResponseData.fail("用户名或者密码错误");
	}
	
}
