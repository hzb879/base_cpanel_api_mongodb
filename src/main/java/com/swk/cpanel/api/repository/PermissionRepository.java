package com.swk.cpanel.api.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.swk.cpanel.api.entity.Permission;

public interface PermissionRepository extends MongoRepository<Permission,String>{
	List<Permission> findAllByIdIn(Collection<String> ids);
}
