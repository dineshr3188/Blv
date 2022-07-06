package com.blv.tracker.application.exception;

import org.springframework.http.HttpStatus;

public class ProjectAPIException extends RuntimeException {

	
	private HttpStatus  httpStatus;
	private String message;
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ProjectAPIException(HttpStatus httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}
	public ProjectAPIException(HttpStatus httpStatus, String message,String message1) {
		super(message);
		this.httpStatus = httpStatus;
		this.message = message1;
	}
	public ProjectAPIException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
