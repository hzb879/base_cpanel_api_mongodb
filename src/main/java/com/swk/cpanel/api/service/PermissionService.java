package com.swk.cpanel.api.service;

import java.util.Collection;
import java.util.List;

import com.swk.cpanel.api.entity.Permission;

public interface PermissionService {
	
	List<Permission> getAllByIds(Collection<String> ids);
	
}
