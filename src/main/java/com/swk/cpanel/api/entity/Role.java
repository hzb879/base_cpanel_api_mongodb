package com.swk.cpanel.api.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.swk.cpanel.api.entity.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Document(collection="roles")
public class Role extends BaseEntity{
	
	/**
	 * 角色中文名称标识,如:管理员
	 */
	private String name;
	/**
	 * 角色标记,如:admin
	 */
	private String mark;
	/**
	 * 权限id集合
	 */
	private Set<String> permissionIds = new HashSet<>();
	
}
