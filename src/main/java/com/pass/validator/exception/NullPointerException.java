package com.pass.validator.exception;

public final class NullPointerException extends RuntimeException {

	private static final long serialVersionUID = -6678184736802311093L;

	public NullPointerException() {
		super();
	}

	public NullPointerException(final String message) {
		super(message);
	}

	public NullPointerException(final Throwable cause) {
		super(cause);
	}

}