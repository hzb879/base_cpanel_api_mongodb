package com.swk.cpanel.api.config.constants;

/**
 * 响应信息枚举
 * @author spacewalker
 *
 */
public enum ResponseMsgEnum {
	
	SUCCESS(0, "SUCCESS"),
	FAIL(1, "FAIL"),
	
	UN_AUTHORIZED(40001, "未认证"),
	EXPIRED_TOKEN(40002, "token超时"),
	INVALID_TOKEN(40003, "非法token"),
	MISS_INFO_TOKEN(40004, "token信息缺失"),
	USER_MISS_TOKEN(40005, "token用户不存在"),
	ACCESS_FORBIDDEN(40006, "未授权");
	
	private Integer code;
	private String msg;
	
	ResponseMsgEnum(Integer code, String msg) {
		this.code=code;
		this.msg=msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
