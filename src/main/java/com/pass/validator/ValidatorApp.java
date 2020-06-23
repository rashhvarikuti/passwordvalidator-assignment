package com.pass.validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pass.validator")
public class ValidatorApp {

	public static void main(String[] args) {
		SpringApplication.run(ValidatorApp.class, args);
	}

}
