package com.pass.validator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.pass.validator.engine.ValidationEngine;
import com.pass.validator.model.ValidationEngineResponse;
import com.pass.validator.service.PasswordValidatorService;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PasswordValidatorServiceImpl implements PasswordValidatorService {

	List<ValidationEngine> configuredValidationRules = new ArrayList<>();

	@Autowired
	public PasswordValidatorServiceImpl(Map<String, ValidationEngine> passwordValidationRules,
			List<String> configuredRules) {
		if (CollectionUtils.isEmpty(configuredValidationRules)) {
			log.info("There are no configured rules. So, validating with all available rules.");
			configuredValidationRules.addAll(passwordValidationRules.values());
		}
	}

	@Override
	public List<ValidationEngineResponse> passValidator(String password) {
		return configuredValidationRules.stream()
				.map(passwordValidationRule -> passwordValidationRule.validate(password)).collect(Collectors.toList());
	}
}
