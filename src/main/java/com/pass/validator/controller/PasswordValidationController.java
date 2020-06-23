package com.pass.validator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pass.validator.exception.NullPointerException;
import com.pass.validator.model.ValidationEngineResponse;
import com.pass.validator.service.PasswordValidatorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class PasswordValidationController {

	private PasswordValidatorService passwordService;

	@Autowired
	public PasswordValidationController(PasswordValidatorService passwordService) {
		this.passwordService = passwordService;
	}

	/**
	 * passwordValidator method validates the password
	 * 
	 * @return the response with message
	 */
	@PostMapping(value = "/pass/validator")
	public ResponseEntity passwordValidator(@RequestBody String passKey) {

		if (passKey == null || passKey.isEmpty()) {
			log.warn("Requested password can't be null or empty.");
			throw new NullPointerException("password cannot be empty or null");
		}
		List<ValidationEngineResponse> ruleResults = passwordService.passValidator(passKey);
		return new ResponseEntity<List<ValidationEngineResponse>>(ruleResults, HttpStatus.OK);
	}
}
