package com.android.internal.org.bouncycastle.crypto;

public class RuntimeCryptoException extends RuntimeException {
    public RuntimeCryptoException() {}

    public RuntimeCryptoException(String message) {
        super(message);
    }
}
