package com.android.internal.protolog.common;

public class InvalidFormatStringException extends RuntimeException {
    public InvalidFormatStringException(String message) {
        super(message);
    }

    public InvalidFormatStringException(String message, Throwable cause) {
        super(message, cause);
    }
}
