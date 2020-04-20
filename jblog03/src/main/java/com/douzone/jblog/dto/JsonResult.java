package com.douzone.jblog.dto;

public class JsonResult {
	private String result; /* "success" OR "fail" */

	private Object data; /* if success, set Data */

	private String message; /* if fail, error Message set */

	private JsonResult(Object data) {
		result = "success";
		this.data = data;
	}
	
	private JsonResult(String message) {
		result = "fail";
		this.message = message;
	}

	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}
	
	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}

	public String getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}


}
