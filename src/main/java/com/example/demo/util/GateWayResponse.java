package com.example.demo.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * Common response format for all rest api's
 * 
 * @author pbattula
 *
 * @param <T>
 */
public class GateWayResponse<T> {

	private T result;
	private String status;
	private HttpStatus httpStatus;
	private String message;
	private List<String> errors;

	public GateWayResponse() {
		super();
	}

	public GateWayResponse(boolean status, final HttpStatus httpStatus, final T result) {
		super();
		this.result = result;
		updateStatus(status);
		this.httpStatus = httpStatus;
	}

	public GateWayResponse(boolean status, final HttpStatus httpStatus, final String message, final List<String> errors) {
		super();
		updateStatus(status);
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = errors;
	}

	public GateWayResponse(boolean status, final HttpStatus httpStatus, final String message, final String error) {
		super();
		updateStatus(status);
		this.httpStatus = httpStatus;
		this.message = message;
		if (StringUtils.isNoneBlank(error)) {
			errors = Arrays.asList(error);
		}
	}

	private void updateStatus(boolean status) {
		if (status) {
			this.status = "success";
		} else {
			this.status = "error";
		}
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
