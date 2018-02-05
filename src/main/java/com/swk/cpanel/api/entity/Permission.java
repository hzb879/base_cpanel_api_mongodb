package com.swk.cpanel.api.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.swk.cpanel.api.entity.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Document(collection="permissions")
public class Permission extends BaseEntity{
	
	/**
	 * 权限名称说明,如添加用户
	 */
	private String name;
	
	/**
	 * 权限标记:如user:add
	 */
	private String mark;
	
}
