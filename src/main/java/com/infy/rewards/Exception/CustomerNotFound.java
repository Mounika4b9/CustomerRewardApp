package com.infy.rewards.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerNotFound extends RuntimeException {
	private String message;

	public CustomerNotFound(String message) {
		super(message);
		this.message = message;
	}

}
