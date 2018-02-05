package com.swk.cpanel.api.bean;

import com.swk.cpanel.api.config.constants.CodeMsgEnum;

public class ResponseData<T> {
	
	private Integer code;
	private String message;
	private T data;
	
	public ResponseData(Integer code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static <T> ResponseData<T> success(T data) {
		CodeMsgEnum success = CodeMsgEnum.SUCCESS;
		return new ResponseData<T>(success.getCode()	, success.getMsg(), data);
	}
	
	public static <T> ResponseData<T> fail(T data) {
		CodeMsgEnum fail = CodeMsgEnum.FAIL;
		return new ResponseData<T>(fail.getCode(),fail.getMsg(),data);
	}
	
	public static ResponseData<String> codeMsg(CodeMsgEnum codeMsgEnum) {
		return new ResponseData<String>(codeMsgEnum.getCode(),codeMsgEnum.getMsg(),codeMsgEnum.getMsg());
	}
	
	public static ResponseData<String> empty(Integer code, String message) {
		return new ResponseData<String>(code, message, message);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
