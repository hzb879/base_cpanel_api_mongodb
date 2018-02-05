package com.swk.cpanel.api.service;

import java.util.Collection;
import java.util.List;

import com.swk.cpanel.api.entity.Role;
import com.swk.cpanel.api.exception.ServiceException;

public interface RoleService {
	
	List<Role> getAllByIds(Collection<String> ids);
	
	Role addPermissions(String id, Collection<String> permissionIds) throws ServiceException;
	
	Role addPermission(String id, String permissionId) throws ServiceException;
}
