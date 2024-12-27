package com.android.server.locksettings;

import android.security.keystore.KeyGenParameterSpec;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.LockscreenCredential;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class UnifiedProfilePasswordCache {
    public static final int CACHE_TIMEOUT_SECONDS = (int) TimeUnit.DAYS.toSeconds(7);
    public final SparseArray mEncryptedPasswords = new SparseArray();
    public final KeyStore mKeyStore;

    public UnifiedProfilePasswordCache(KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }

    public final void removePassword(int i) {
        synchronized (this.mEncryptedPasswords) {
            try {
                String str = "com.android.server.locksettings.unified_profile_cache_v2_" + i;
                String str2 = "com.android.server.locksettings.unified_profile_cache_" + i;
                try {
                    if (this.mKeyStore.containsAlias(str)) {
                        this.mKeyStore.deleteEntry(str);
                    }
                    if (this.mKeyStore.containsAlias(str2)) {
                        this.mKeyStore.deleteEntry(str2);
                    }
                } catch (KeyStoreException e) {
                    Slog.d("UnifiedProfilePasswordCache", "Cannot delete key", e);
                }
                if (this.mEncryptedPasswords.contains(i)) {
                    Arrays.fill((byte[]) this.mEncryptedPasswords.get(i), (byte) 0);
                    this.mEncryptedPasswords.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void storePassword(int i, LockscreenCredential lockscreenCredential, long j) {
        if (j == 0) {
            return;
        }
        synchronized (this.mEncryptedPasswords) {
            try {
                if (this.mEncryptedPasswords.contains(i)) {
                    return;
                }
                String str = "com.android.server.locksettings.unified_profile_cache_v2_" + i;
                try {
                    KeyGenerator keyGenerator =
                            KeyGenerator.getInstance("AES", this.mKeyStore.getProvider());
                    KeyGenParameterSpec.Builder blockModes =
                            new KeyGenParameterSpec.Builder(str, 3)
                                    .setKeySize(256)
                                    .setBlockModes("GCM");
                    byte[] bArr = SyntheticPasswordCrypto.PROTECTOR_SECRET_PERSONALIZATION;
                    keyGenerator.init(
                            blockModes
                                    .setNamespace(103)
                                    .setEncryptionPaddings("NoPadding")
                                    .setUserAuthenticationRequired(true)
                                    .setBoundToSpecificSecureUserId(j)
                                    .setUserAuthenticationValidityDurationSeconds(
                                            CACHE_TIMEOUT_SECONDS)
                                    .build());
                    SecretKey generateKey = keyGenerator.generateKey();
                    try {
                        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                        cipher.init(1, generateKey);
                        this.mEncryptedPasswords.put(
                                i,
                                ArrayUtils.concat(
                                        new byte[][] {
                                            cipher.getIV(),
                                            cipher.doFinal(lockscreenCredential.getCredential())
                                        }));
                    } catch (GeneralSecurityException e) {
                        Slog.d("UnifiedProfilePasswordCache", "Cannot encrypt", e);
                    }
                } catch (GeneralSecurityException e2) {
                    Slog.e("UnifiedProfilePasswordCache", "Cannot generate key", e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
