package com.android.server.locksettings.recoverablekeystore;

import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class WrappedKey {
    public final byte[] mKeyMaterial;
    public final byte[] mKeyMetadata;
    public final byte[] mNonce;
    public final int mPlatformKeyGenerationId;
    public final int mRecoveryStatus;

    public WrappedKey(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2) {
        this.mNonce = bArr;
        this.mKeyMaterial = bArr2;
        this.mKeyMetadata = bArr3;
        this.mPlatformKeyGenerationId = i;
        this.mRecoveryStatus = i2;
    }

    public static WrappedKey fromSecretKey(
            PlatformEncryptionKey platformEncryptionKey, SecretKey secretKey, byte[] bArr) {
        if (secretKey.getEncoded() == null) {
            throw new InvalidKeyException(
                    "key does not expose encoded material. It cannot be wrapped.");
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(3, platformEncryptionKey.mKey);
            try {
                return new WrappedKey(
                        cipher.getIV(),
                        cipher.wrap(secretKey),
                        bArr,
                        platformEncryptionKey.mGenerationId,
                        1);
            } catch (IllegalBlockSizeException e) {
                Throwable cause = e.getCause();
                if (cause instanceof KeyStoreException) {
                    throw ((KeyStoreException) cause);
                }
                throw new RuntimeException(
                        "IllegalBlockSizeException should not be thrown by AES/GCM/NoPadding mode.",
                        e);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException unused) {
            throw new RuntimeException(
                    "Android does not support AES/GCM/NoPadding. This should never happen.");
        }
    }
}
