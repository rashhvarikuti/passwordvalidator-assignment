package com.pass.validator.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.pass.validator.model.ValidationEngineResponse;

@ControllerAdvice
public class ExceptionHandlerAdviceController {

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public @ResponseBody ValidationEngineResponse handleResourceNullPointerException(
			final Exception exception, final HttpServletRequest request) {
		ValidationEngineResponse error = new ValidationEngineResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ValidationEngineResponse handleException(final Exception exception,
			final HttpServletRequest request) {

		ValidationEngineResponse error = new ValidationEngineResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}
}