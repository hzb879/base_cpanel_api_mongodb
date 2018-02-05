package com.swk.cpanel.api.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import com.swk.cpanel.api.entity.User;
import com.swk.cpanel.api.exception.ServiceException;

public interface UserService {
	
	User registUser(String username, String password);
	
	User getById(String id);
	
	Page<User> getAll(Query query,Pageable pageable);
	
	/**
	 * 获取用户的角色和权限信息
	 * @param id
	 * @return
	 * @throws ServiceException 
	 */
	User getInfoWithRolesAndPermissions(String id) throws ServiceException;
	
	/**
	 * 获取用户的角色信息
	 * @param id
	 * @return
	 * @throws ServiceException 
	 */
	User getInfoWithRoles(String id) throws ServiceException;
	
	User addRole(String id,String roleId) throws ServiceException;
	
	User addRoles(String id,Collection<String> roleIds) throws ServiceException;
}
