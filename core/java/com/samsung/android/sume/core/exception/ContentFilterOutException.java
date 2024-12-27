package com.samsung.android.sume.core.exception;

public class ContentFilterOutException extends IllegalArgumentException
        implements WarningException {
    public ContentFilterOutException() {}

    public ContentFilterOutException(String s) {
        super(s);
    }

    public ContentFilterOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentFilterOutException(Throwable cause) {
        super(cause);
    }
}
