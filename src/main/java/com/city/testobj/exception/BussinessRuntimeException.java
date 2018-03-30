package com.city.testobj.exception;

public class BussinessRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String msg;

	public BussinessRuntimeException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public BussinessRuntimeException(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	}

	@Override
	public String getMessage() {
		return this.code + " | " + this.msg;
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
