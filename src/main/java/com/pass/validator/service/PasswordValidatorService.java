package com.pass.validator.service;

import java.util.List;

import com.pass.validator.model.ValidationEngineResponse;

public interface PasswordValidatorService {
	List<ValidationEngineResponse> passValidator(String password);
}
