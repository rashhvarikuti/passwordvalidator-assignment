package com.pass.validator.engine;

import com.pass.validator.model.ValidationEngineResponse;

public interface ValidationEngine {
	ValidationEngineResponse validate(String str);
}
