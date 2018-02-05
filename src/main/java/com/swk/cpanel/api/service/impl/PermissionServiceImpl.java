package com.swk.cpanel.api.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.swk.cpanel.api.entity.Permission;
import com.swk.cpanel.api.repository.PermissionRepository;
import com.swk.cpanel.api.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{

	@Resource
	private PermissionRepository permissionRepository;
	
	@Override
	public List<Permission> getAllByIds(Collection<String> ids) {
		return permissionRepository.findAllByIdIn(ids);
	}

}
