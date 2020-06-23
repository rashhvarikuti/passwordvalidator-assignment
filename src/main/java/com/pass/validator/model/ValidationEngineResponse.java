package com.pass.validator.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationEngineResponse {

    private String validatorName;
    private boolean success;
    private String errorMessage;
}