package com.pass.validator.service.impl.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.pass.validator.engine.AlphaNumericValidationEngine;
import com.pass.validator.engine.CharacterSequenceValiationEngine;
import com.pass.validator.engine.LengthValidationEngine;
import com.pass.validator.engine.ValidationEngine;
import com.pass.validator.model.ValidationEngineResponse;
import com.pass.validator.service.PasswordValidatorService;
import com.pass.validator.service.impl.PasswordValidatorServiceImpl;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PasswordValidatorServiceImplTest {

	private static final int PASSWORD_MIN_LENGTH = 5;
	private static final int PASSWORD_MAX_LENGTH = 12;

	private static final String alphaNumericRegex = "[\\p{Lower}]+[\\p{Digit}]+|[\\p{Digit}]+[\\p{Lower}]+";
	private static final String charSequenceRegex = "(\\p{Alnum}{2,})\\1";

	@Mock
	private MessageSource messageSource;

	private ValidationEngine alphaNumericValidationRule;

	private ValidationEngine passwordLengthValidationRule;

	private ValidationEngine characterSequenceValidationRule;

	private PasswordValidatorService passwordService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		alphaNumericValidationRule = spy(new AlphaNumericValidationEngine(alphaNumericRegex, messageSource));
		passwordLengthValidationRule = spy(
				new LengthValidationEngine(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH, messageSource));
		characterSequenceValidationRule = spy(new CharacterSequenceValiationEngine(charSequenceRegex, messageSource));

		when(messageSource.getMessage(anyString(), any(Object[].class), anyObject())).thenReturn("Test Message");
	}

	@Test
	public void testValidate() {
		Map<String, ValidationEngine> passwordValidationRules = getPasswordValidationRules();
		passwordService = new PasswordValidatorServiceImpl(passwordValidationRules, Collections.emptyList());

		List<ValidationEngineResponse> ruleResults = passwordService.passValidator("test1234");
		verify(alphaNumericValidationRule, times(1)).validate("test1234");
		verify(passwordLengthValidationRule, times(1)).validate("test1234");
		verify(characterSequenceValidationRule, times(1)).validate("test1234");
		ruleResults.forEach(ruleResult -> {
			Assert.assertEquals(ruleResult.isSuccess(), true);
			Assert.assertEquals(ruleResult.getErrorMessage(), null);
		});
	}

	private Map<String, ValidationEngine> getPasswordValidationRules() {
		Map<String, ValidationEngine> passwordValidationRules = new HashMap<>();
		passwordValidationRules.put("ALPHA_NUMERIC", alphaNumericValidationRule);
		passwordValidationRules.put("LENGTH", passwordLengthValidationRule);
		passwordValidationRules.put("CHAR_SEQUENCE", characterSequenceValidationRule);
		return passwordValidationRules;
	}
}
