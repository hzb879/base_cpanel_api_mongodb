package com.swk.cpanel.api.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.swk.cpanel.api.entity.common.BaseEntity;
import com.swk.cpanel.api.entity.common.Media;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Document(collection="users")
@ApiModel("用户基本信息")
public class User extends BaseEntity{
	
	@ApiModelProperty("用户名")
	private String username;
	
	@ApiModelProperty("密码")
	private String password;
	
	@ApiModelProperty("盐")
	private String salt;	
	
	@ApiModelProperty("昵称")
	private String nickname;
	
	@ApiModelProperty("性别")
	private String gender;
	
	@ApiModelProperty("头像")
	private Media avatar;
	
	/**
	 * 角色id集合
	 */
	private Set<String> roleIds = new HashSet<>();
	
	/**
	 * 角色名称集合
	 * 仅作为返回数据使用 
	 */
	@Transient
	private Set<String> roles = new HashSet<>();
	
	/**
	 * 权限标识集合
	 * 仅作为返回数据使用 
	 */
	@Transient
	private Set<String> permissions = new HashSet<>();
	
}
