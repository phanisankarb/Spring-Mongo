package com.example.demo.exceptions;

/**
 * BadRequestException is thrown while given values are not valid for rest API
 * @author pbattula
 *
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -315194665962960891L;

	public BadRequestException(String message) {
		super(message);
	}

}
