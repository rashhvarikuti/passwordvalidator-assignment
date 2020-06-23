package com.pass.validator.engine.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.pass.validator.engine.AlphaNumericValidationEngine;
import com.pass.validator.engine.ValidationEngine;
import com.pass.validator.model.ValidationEngineResponse;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AlphaNumericValidationEngineTest {

	private static final String VALIDATION_ERROR_MESSAGE = "password must consist of a mixture of lowercase letters and numerical digits only, with at least one of each";
	private static final String regex = "[\\p{Lower}]+[\\p{Digit}]+|[\\p{Digit}]+[\\p{Lower}]+";
	private static final String ALPHANUMERIC_VALIDATION = "alphanumeric.validation";

	@Mock
	private MessageSource messageSource;

	private ValidationEngine validationengine;

	@Before
	public void setUp() throws Exception {
		validationengine = new AlphaNumericValidationEngine(regex, messageSource);

		when(messageSource.getMessage(eq(ALPHANUMERIC_VALIDATION), any(Object[].class), anyObject()))
				.thenReturn(VALIDATION_ERROR_MESSAGE);
	}

	@Test
	public void testValidate() {
		ValidationEngineResponse engineResult = validationengine.validate("test1234");
		Assert.assertEquals(engineResult.isSuccess(), true);
		Assert.assertEquals(engineResult.getErrorMessage(), null);
	}

	@Test
	public void testValidate_upperCase() {
		ValidationEngineResponse engineResult = validationengine.validate("TEST");
		Assert.assertEquals(engineResult.isSuccess(), false);
	}

	@Test
	public void testValidate_digits() {
		ValidationEngineResponse engineResult = validationengine.validate("123");
		Assert.assertEquals(engineResult.isSuccess(), false);
	}

	@Test
	public void testValidate_chars() {
		ValidationEngineResponse engineResult = validationengine.validate("test");
		Assert.assertEquals(engineResult.isSuccess(), false);
	}

	@Test
	public void testValidate_specialChars() {
		ValidationEngineResponse engineResult = validationengine.validate("test123$!@#%");
		Assert.assertEquals(engineResult.isSuccess(), false);
	}

	@Test
	public void testValidate_upperLowerCase() {
		ValidationEngineResponse engineResult = validationengine.validate("teST");
		Assert.assertEquals(engineResult.isSuccess(), false);
	}

	@Test
	public void testValidate_upperLowerCase_Digits() {
		ValidationEngineResponse engineResult = validationengine.validate("teST123");
		Assert.assertEquals(engineResult.isSuccess(), false);
	}

	@Test
	public void testValidate_upperCase_Digits() {
		ValidationEngineResponse engineResult = validationengine.validate("TEST123");
		Assert.assertEquals(engineResult.isSuccess(), false);
	}
}
