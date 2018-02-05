package com.swk.cpanel.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.swk.cpanel.api.entity.User;

public interface UserRepository extends MongoRepository<User,String>{
	User findOneByUsername(String username);
}
