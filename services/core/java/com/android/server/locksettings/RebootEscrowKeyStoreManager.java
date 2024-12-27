package com.android.server.locksettings;

import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.util.Slog;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.crypto.SecretKey;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RebootEscrowKeyStoreManager {
    public final Object mKeyStoreLock = new Object();

    public static SecretKey getKeyStoreEncryptionKeyLocked() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeystore");
            keyStore.load(new AndroidKeyStoreLoadStoreParameter(120));
            return (SecretKey) keyStore.getKey("reboot_escrow_key_store_encryption_key", null);
        } catch (IOException | GeneralSecurityException e) {
            Slog.e("RebootEscrowKeyStoreManager", "Unable to get encryption key from keystore.", e);
            return null;
        }
    }
}
