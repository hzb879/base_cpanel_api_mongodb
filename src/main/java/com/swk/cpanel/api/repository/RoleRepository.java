package com.swk.cpanel.api.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.swk.cpanel.api.entity.Role;

public interface RoleRepository extends MongoRepository<Role,String>{
	List<Role> findAllByIdIn(Collection<String> ids);
}
