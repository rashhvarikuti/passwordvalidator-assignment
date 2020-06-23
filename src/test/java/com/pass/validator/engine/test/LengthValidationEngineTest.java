package com.pass.validator.engine.test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.pass.validator.engine.LengthValidationEngine;
import com.pass.validator.engine.ValidationEngine;
import com.pass.validator.model.ValidationEngineResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class LengthValidationEngineTest {

    private static final String VALIDATION_ERROR_MESSAGE = "password length must be between 5 and 12 characters";
    private static final String LENGTH_VALIDATION = "length.validation";

    private ValidationEngine validationEngine;

    @Mock
    private MessageSource messageSource;

    @Before
    public void setUp() throws Exception{
        int PASSWORD_MIN_LENGTH = 5;
        int PASSWORD_MAX_LENGTH = 12;
        validationEngine = new LengthValidationEngine(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH, messageSource);

        when(messageSource.getMessage(eq(LENGTH_VALIDATION),any(Object[].class),anyObject())).thenReturn(VALIDATION_ERROR_MESSAGE);
    }

    @Test
    public void testValidate(){
        ValidationEngineResponse ruleResult = validationEngine.validate("test1234");
        Assert.assertEquals(ruleResult.isSuccess(), true);
        Assert.assertEquals(ruleResult.getErrorMessage(), null);
    }

    @Test
    public void testValidate_5chars(){
    	ValidationEngineResponse ruleResult = validationEngine.validate("test5");
        Assert.assertEquals(ruleResult.isSuccess(), true);
        Assert.assertEquals(ruleResult.getErrorMessage(), null);
    }

    @Test
    public void testValidate_lessThan5chars(){
    	ValidationEngineResponse ruleResult = validationEngine.validate("testtest123");
        Assert.assertEquals(ruleResult.isSuccess(), true);
        Assert.assertEquals(ruleResult.getErrorMessage(), null);
    }

    @Test
    public void testValidate_12chars(){
    	ValidationEngineResponse ruleResult = validationEngine.validate("test12345678");
        Assert.assertEquals(ruleResult.isSuccess(), true);
        Assert.assertEquals(ruleResult.getErrorMessage(), null);
    }

    @Test
    public void testValidate_greaterThan12chars(){
    	ValidationEngineResponse ruleResult = validationEngine.validate("testing123456");
        Assert.assertEquals(ruleResult.isSuccess(), false);
        Assert.assertEquals(ruleResult.getErrorMessage(), VALIDATION_ERROR_MESSAGE);
    }
}
