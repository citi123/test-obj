package com.city.testobj.exception;

public enum ErrorCode {
	DB_CONFIG_ERROR(1,"数据库配置异常"),
	DB_OPERATE_ERROR(1,"数据库操作异常"),
	PARAM_VALIDATE_ERROR(1,"参数异常")
	;
	private int code;
	private String msg;

	private ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
