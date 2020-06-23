package com.pass.validator.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CharacterSequenceValiationEngine extends AbstractValidationEngine {

	private static final String RULE_NAME = "CHAR_SEQUENCE_CHECK";
	private static final String MESSAGE_ID = "charactersequence.validation";

	Pattern passwordPattern;

	@Autowired
	public CharacterSequenceValiationEngine(@Value("${charactersequence.validation.regex}") String regex,
			MessageSource messageSource) {
		super(RULE_NAME, messageSource, MESSAGE_ID, null);
		passwordPattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
	}

	@Override
	public boolean doValidate(String str) {
		Matcher matcher = passwordPattern.matcher(str);
		return !matcher.find();
	}
}
