package com.android.server.companion.securechannel;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;

public abstract class KeyStoreUtils {
    public static KeyStore loadKeyStore() {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        try {
            keyStore.load(null);
            return keyStore;
        } catch (IOException e) {
            throw new KeyStoreException("Failed to load Android Keystore.", e);
        }
    }
}
