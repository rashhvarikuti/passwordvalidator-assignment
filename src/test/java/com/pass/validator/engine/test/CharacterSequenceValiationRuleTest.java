package com.pass.validator.engine.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.pass.validator.engine.CharacterSequenceValiationEngine;
import com.pass.validator.engine.ValidationEngine;
import com.pass.validator.model.ValidationEngineResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CharacterSequenceValiationRuleTest {

	private static final String VALIDATION_ERROR_MESSAGE = "password must not contain any sequence of characters immediately followed by the same sequence";
	private static final String regex = "(\\p{Alnum}{2,})\\1";
	private static final String CHAR_SEQ_VALIDATION = "charactersequence.validation";

	private ValidationEngine validationEngine;

	@Mock
	private MessageSource messageSource;

	@Before
	public void setUp() throws Exception {
		validationEngine = new CharacterSequenceValiationEngine(regex, messageSource);

		when(messageSource.getMessage(eq(CHAR_SEQ_VALIDATION), any(Object[].class), anyObject()))
				.thenReturn(VALIDATION_ERROR_MESSAGE);
	}

	@Test
	public void testValidate() {
		ValidationEngineResponse engineRes = validationEngine.validate("test1234");
		Assert.assertEquals(engineRes.isSuccess(), true);
	}

	@Test
	public void testValidate_ImmediateCharSeq() {
		ValidationEngineResponse engineRes = validationEngine.validate("testtest123");
		Assert.assertEquals(engineRes.isSuccess(), false);
	}

}
