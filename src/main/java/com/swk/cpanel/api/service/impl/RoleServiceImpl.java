package com.swk.cpanel.api.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.swk.cpanel.api.entity.Role;
import com.swk.cpanel.api.exception.ServiceException;
import com.swk.cpanel.api.repository.RoleRepository;
import com.swk.cpanel.api.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> getAllByIds(Collection<String> ids) {
		return roleRepository.findAllByIdIn(ids);
	}

	@Override
	public Role addPermissions(String id, Collection<String> permissionIds) throws ServiceException {
		Role dbRole = roleRepository.findOne(id);
		if (Objects.isNull(dbRole)) {
			throw new ServiceException(Role.class + " 不存在");
		}
		dbRole.getPermissionIds().addAll(permissionIds);
		return roleRepository.save(dbRole);
	}

	@Override
	public Role addPermission(String id, String permissionId) throws ServiceException {
		Role dbRole = roleRepository.findOne(id);
		if (Objects.isNull(dbRole)) {
			throw new ServiceException(Role.class + " 不存在");
		}
		dbRole.getPermissionIds().add(permissionId);
		return roleRepository.save(dbRole);
	}



}
