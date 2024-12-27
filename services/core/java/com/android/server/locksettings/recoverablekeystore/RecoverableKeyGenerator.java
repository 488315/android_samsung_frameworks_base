package com.android.server.locksettings.recoverablekeystore;

import android.util.Log;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb;

import java.util.Locale;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class RecoverableKeyGenerator {
    public final RecoverableKeyStoreDb mDatabase;
    public final KeyGenerator mKeyGenerator;

    public RecoverableKeyGenerator(
            KeyGenerator keyGenerator, RecoverableKeyStoreDb recoverableKeyStoreDb) {
        this.mKeyGenerator = keyGenerator;
        this.mDatabase = recoverableKeyStoreDb;
    }

    public final byte[] generateAndStoreKey(
            PlatformEncryptionKey platformEncryptionKey, int i, int i2, String str, byte[] bArr) {
        this.mKeyGenerator.init(256);
        SecretKey generateKey = this.mKeyGenerator.generateKey();
        WrappedKey fromSecretKey =
                WrappedKey.fromSecretKey(platformEncryptionKey, generateKey, bArr);
        RecoverableKeyStoreDb recoverableKeyStoreDb = this.mDatabase;
        if (recoverableKeyStoreDb.insertKey(i, i2, str, fromSecretKey) == -1) {
            Locale locale = Locale.US;
            throw new RecoverableKeyStorageException(
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            i2, "Failed writing (", ", ", str, ") to database."));
        }
        if (recoverableKeyStoreDb.setShouldCreateSnapshot(i, i2) < 0) {
            Log.e("PlatformKeyGen", "Failed to set the shoudCreateSnapshot flag in the local DB.");
        }
        return generateKey.getEncoded();
    }

    public final void importKey(
            PlatformEncryptionKey platformEncryptionKey,
            int i,
            int i2,
            String str,
            byte[] bArr,
            byte[] bArr2) {
        WrappedKey fromSecretKey =
                WrappedKey.fromSecretKey(
                        platformEncryptionKey, new SecretKeySpec(bArr, "AES"), bArr2);
        RecoverableKeyStoreDb recoverableKeyStoreDb = this.mDatabase;
        if (recoverableKeyStoreDb.insertKey(i, i2, str, fromSecretKey) != -1) {
            recoverableKeyStoreDb.setShouldCreateSnapshot(i, i2);
        } else {
            Locale locale = Locale.US;
            throw new RecoverableKeyStorageException(
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            i2, "Failed writing (", ", ", str, ") to database."));
        }
    }
}
