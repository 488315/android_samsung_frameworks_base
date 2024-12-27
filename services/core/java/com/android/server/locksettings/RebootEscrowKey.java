package com.android.server.locksettings;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public final class RebootEscrowKey {
    public final SecretKey mKey;

    public RebootEscrowKey(SecretKey secretKey) {
        this.mKey = secretKey;
    }

    public static RebootEscrowKey generate() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new SecureRandom());
            return new RebootEscrowKey(keyGenerator.generateKey());
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Could not generate new secret key", e);
        }
    }
}
