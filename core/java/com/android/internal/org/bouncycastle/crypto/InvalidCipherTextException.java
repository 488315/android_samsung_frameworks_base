package com.android.internal.org.bouncycastle.crypto;

public class InvalidCipherTextException extends CryptoException {
    public InvalidCipherTextException() {}

    public InvalidCipherTextException(String message) {
        super(message);
    }

    public InvalidCipherTextException(String message, Throwable cause) {
        super(message, cause);
    }
}
