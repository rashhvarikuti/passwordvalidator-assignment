package com.pass.validator.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class LengthValidationEngine extends AbstractValidationEngine {

    private int passwordMinLength;
    private int passwordMaxLength;

    private static final String MESSAGE_ID = "length.validation";

    @Autowired
    public LengthValidationEngine(@Value("${password.validation.min.length:5}") int passwordMinLength,
                                @Value("${password.validation.max.length:12}") int passwordMaxLength,
                                MessageSource messageSource) {
        super("LENGTH_CHECK", messageSource, MESSAGE_ID, new Object[]{passwordMinLength, passwordMaxLength});
        this.passwordMinLength = passwordMinLength;
        this.passwordMaxLength = passwordMaxLength;
    }

    @Override
    public boolean doValidate(String str) {
        int passwordLength = str.length();
        return passwordLength >= passwordMinLength && passwordLength <= passwordMaxLength;
    }
}
