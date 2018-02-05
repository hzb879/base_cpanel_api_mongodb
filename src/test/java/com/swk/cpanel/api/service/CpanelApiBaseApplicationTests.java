package com.swk.cpanel.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.swk.cpanel.api.entity.Permission;
import com.swk.cpanel.api.entity.Role;
import com.swk.cpanel.api.exception.ServiceException;
import com.swk.cpanel.api.repository.PermissionRepository;
import com.swk.cpanel.api.repository.RoleRepository;
import com.swk.cpanel.api.repository.UserRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CpanelApiBaseApplicationTests {

	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private RoleRepository roleRepository;
	
	@Resource
	private PermissionRepository permissionRepository;
	
	@Test
	public void addUser() {
		userService.registUser("user", "user123");
	}
	
	@Test
	public void addRole() throws ServiceException {
		Role role = new Role();
		role.setName("普通管理员");
		role.setMark("user");
		roleRepository.save(role);
	}
	
	@Test
	public void addPermission() throws ServiceException {
		
		Permission permission = new Permission();
		permission.setName("添加普通管理员");
		permission.setMark("user:add");
		
		Permission permission2 = new Permission();
		permission2.setName("删除普通管理员");
		permission2.setMark("user:delete");
		
		Permission permission3 = new Permission();
		permission3.setName("查看管理员列表");
		permission3.setMark("user:search");
		
		List<Permission> ps=new ArrayList<>();
		ps.add(permission);
		ps.add(permission2);
		ps.add(permission3);
		
		permissionRepository.save(ps);
	}
	
	@Test
	public void addUserRole() throws ServiceException {
		userService.addRole("5a33a56818470803350bbe49", "5a33ad15184708033fb31f9c");
	}
	
	@Test
	public void addRolePermission() throws ServiceException {
		List<String> ids=new ArrayList<>();
		ids.add("5a33ae801847080341ec9889");
		ids.add("5a33ae801847080341ec988a");
		ids.add("5a33ae801847080341ec988b");
		roleService.addPermissions("5a33ac96184708033e9e4591",ids);
	}
	
}
