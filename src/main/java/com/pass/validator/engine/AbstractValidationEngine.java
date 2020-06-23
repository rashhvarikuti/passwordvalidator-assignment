package com.pass.validator.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.pass.validator.model.ValidationEngineResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractValidationEngine implements ValidationEngine {

	private String engineName;
	private String messageId;
	private Object[] messageParams;

	private MessageSource messageSource;

	AbstractValidationEngine(String engineName, MessageSource messageSource, String messageId, Object[] messageParams) {
		this.engineName = engineName;
		this.messageSource = messageSource;
		this.messageId = messageId;
		this.messageParams = messageParams;
	}

	abstract boolean doValidate(String str);

	@Override
	public ValidationEngineResponse validate(String str) {
		log.debug("Validating password with {} validation rule", engineName);
		boolean isSuccess = doValidate(str);
		log.debug("Result of {} validation engine is {}", engineName, isSuccess ? "Success" : "Fail");
		ValidationEngineResponse engineResponse = new ValidationEngineResponse();
		engineResponse.setValidatorName(engineName);
		engineResponse.setSuccess(isSuccess);
		if (!isSuccess) {
			String errorMessage = messageSource.getMessage(messageId, messageParams, null);
			engineResponse.setErrorMessage(errorMessage);
		}
		return engineResponse;
	}
}
