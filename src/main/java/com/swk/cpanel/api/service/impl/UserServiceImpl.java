package com.swk.cpanel.api.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.swk.cpanel.api.entity.Permission;
import com.swk.cpanel.api.entity.Role;
import com.swk.cpanel.api.entity.User;
import com.swk.cpanel.api.exception.ServiceException;
import com.swk.cpanel.api.repository.PermissionRepository;
import com.swk.cpanel.api.repository.RoleRepository;
import com.swk.cpanel.api.repository.UserRepository;
import com.swk.cpanel.api.service.UserService;
import com.swk.cpanel.api.util.ShiroEncryptUtil;
import com.swk.cpanel.api.util.ShiroEncryptUtil.SaltPwd;

@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserRepository userRepository;
	
	@Resource
	private RoleRepository roleRepository;
	
	@Resource
	private PermissionRepository permissionRepository;
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public User registUser(String username, String password) {
		SaltPwd saltPwd = ShiroEncryptUtil.encryptPwdWithRandomSalt(password);
		User user = new User();
		user.setUsername(username);
		user.setPassword(saltPwd.getPassword());
		user.setSalt(saltPwd.getSalt());
		return userRepository.save(user);
	}

	@Override
	public User addRole(String id, String roleId) throws ServiceException {
		User dbUser = userRepository.findOne(id);
		if (Objects.isNull(dbUser)) {
			throw new ServiceException(User.class + " 不存在");
		}
		dbUser.getRoleIds().add(roleId);
		return userRepository.save(dbUser);
	}

	@Override
	public User addRoles(String id, Collection<String> roleIds) throws ServiceException {
		User dbUser = userRepository.findOne(id);
		if (Objects.isNull(dbUser)) {
			throw new ServiceException(User.class + " 不存在");
		}
		dbUser.getRoleIds().addAll(roleIds);
		return userRepository.save(dbUser);
	}

	@Override
	public User getById(String id) {
		return userRepository.findOne(id);
	}

	@Override
	public User getInfoWithRolesAndPermissions(String id) throws ServiceException {
		User dbUser = userRepository.findOne(id);
		if (Objects.isNull(dbUser)) {
			throw new ServiceException(User.class + " 不存在");
		}
		Set<String> roleIds = dbUser.getRoleIds();
		if(Objects.nonNull(roleIds) && !roleIds.isEmpty()) {
			List<Role> roles = roleRepository.findAllByIdIn(roleIds);
			Set<String> permissionIds=new HashSet<>();
			if(Objects.nonNull(roles) && !roles.isEmpty()) {
				Set<String> roleNames=new HashSet<>();
				roles.forEach(r -> {
					permissionIds.addAll(r.getPermissionIds());
					roleNames.add(r.getMark());
				});
				dbUser.setRoles(roleNames);
			}
			if(!permissionIds.isEmpty()) {
				List<Permission> permissions = permissionRepository.findAllByIdIn(permissionIds);
				if(Objects.nonNull(permissions) && !permissions.isEmpty()) {
					Set<String> marks=new HashSet<>();
					permissions.forEach(p -> marks.add(p.getMark()));
					dbUser.setPermissions(marks);
				}
			}
		}
		return dbUser;
	}

	@Override
	public User getInfoWithRoles(String id) throws ServiceException {
		User dbUser = userRepository.findOne(id);
		if (Objects.isNull(dbUser)) {
			throw new ServiceException(User.class + " 不存在");
		}
		Set<String> roleIds = dbUser.getRoleIds();
		if(Objects.nonNull(roleIds) && !roleIds.isEmpty()) {
			List<Role> roles = roleRepository.findAllByIdIn(roleIds);
			if(Objects.nonNull(roles) && !roles.isEmpty()) {
				Set<String> roleNames=new HashSet<>();
				roles.forEach(r -> roleNames.add(r.getMark()));
				dbUser.setRoles(roleNames);
			}
		}
		dbUser.setPassword("");
		dbUser.setSalt("");
		return dbUser;
	}

	@Override
	public Page<User> getAll(Query query, Pageable pageable) {
		return new PageImpl<User>(mongoTemplate.find(query, User.class), pageable, mongoTemplate.count(query, User.class));
	}
	
}
