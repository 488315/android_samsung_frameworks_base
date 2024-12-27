package com.android.server.companion.securechannel;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
