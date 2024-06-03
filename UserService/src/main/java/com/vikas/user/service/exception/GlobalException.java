package com.vikas.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.vikas.user.service.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFounException(ResourceNotFoundException ex) {

		String message = ex.getMessage();
		System.out.println("GlobalException::handlerResourceNotFounException::message " + message);
		ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND)
				.build();
		System.out.println("GlobalException::handlerResourceNotFounException::response " + response);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}

}
