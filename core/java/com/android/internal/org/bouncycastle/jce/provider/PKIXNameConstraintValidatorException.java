package com.android.internal.org.bouncycastle.jce.provider;

public class PKIXNameConstraintValidatorException extends Exception {
    private Throwable cause;

    public PKIXNameConstraintValidatorException(String msg) {
        super(msg);
    }

    public PKIXNameConstraintValidatorException(String msg, Throwable e) {
        super(msg);
        this.cause = e;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
