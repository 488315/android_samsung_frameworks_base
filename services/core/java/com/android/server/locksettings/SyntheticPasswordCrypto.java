package com.android.server.locksettings;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.security.AndroidKeyStoreMaintenance;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.system.keystore2.KeyDescriptor;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
abstract class SyntheticPasswordCrypto {
    public static final byte[] PROTECTOR_SECRET_PERSONALIZATION = "application-id".getBytes();

    public static byte[] decrypt(byte[] bArr, SecretKey secretKey) {
        if (bArr == null) {
            return null;
        }
        byte[] copyOfRange = Arrays.copyOfRange(bArr, 0, 12);
        byte[] copyOfRange2 = Arrays.copyOfRange(bArr, 12, bArr.length);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, secretKey, new GCMParameterSpec(128, copyOfRange));
        return cipher.doFinal(copyOfRange2);
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return decrypt(
                    bArr3,
                    new SecretKeySpec(Arrays.copyOf(personalizedHash(bArr2, bArr), 32), "AES"));
        } catch (InvalidAlgorithmParameterException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            Slog.e("SyntheticPasswordCrypto", "Failed to decrypt", e);
            return null;
        }
    }

    public static byte[] encrypt(byte[] bArr, SecretKey secretKey) {
        if (bArr == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(1, secretKey);
        byte[] doFinal = cipher.doFinal(bArr);
        byte[] iv = cipher.getIV();
        if (iv.length != 12) {
            throw new IllegalArgumentException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            iv.length, new StringBuilder("Invalid iv length: "), " bytes"));
        }
        GCMParameterSpec gCMParameterSpec =
                (GCMParameterSpec) cipher.getParameters().getParameterSpec(GCMParameterSpec.class);
        if (gCMParameterSpec.getTLen() == 128) {
            return ArrayUtils.concat(new byte[][] {iv, doFinal});
        }
        throw new IllegalArgumentException(
                "Invalid tag length: " + gCMParameterSpec.getTLen() + " bits");
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return encrypt(
                    bArr3,
                    new SecretKeySpec(Arrays.copyOf(personalizedHash(bArr2, bArr), 32), "AES"));
        } catch (IOException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | InvalidParameterSpecException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            Slog.e("SyntheticPasswordCrypto", "Failed to encrypt", e);
            return null;
        }
    }

    public static KeyStore getKeyStore() {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(new AndroidKeyStoreLoadStoreParameter(103));
        return keyStore;
    }

    public static boolean migrateLockSettingsKey(String str) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = str;
        KeyDescriptor keyDescriptor2 = new KeyDescriptor();
        keyDescriptor2.domain = 2;
        keyDescriptor2.nspace = 103;
        keyDescriptor2.alias = str;
        Slog.i("SyntheticPasswordCrypto", "Migrating key " + str);
        int migrateKeyNamespace =
                AndroidKeyStoreMaintenance.migrateKeyNamespace(keyDescriptor, keyDescriptor2);
        if (migrateKeyNamespace == 0) {
            return true;
        }
        if (migrateKeyNamespace == 7) {
            Slog.i("SyntheticPasswordCrypto", "Key does not exist");
            return true;
        }
        if (migrateKeyNamespace == 20) {
            Slog.i("SyntheticPasswordCrypto", "Key already exists");
            return true;
        }
        Slog.e(
                "SyntheticPasswordCrypto",
                TextUtils.formatSimple(
                        "Failed to migrate key: %d",
                        new Object[] {Integer.valueOf(migrateKeyNamespace)}));
        return false;
    }

    public static native byte[] nativeKBKDF(byte[] bArr, byte[] bArr2, byte[] bArr3, int i);

    public static native byte[] nativeKDF(byte[] bArr, byte[] bArr2, byte[] bArr3, int i);

    public static native byte[] nativePBKDF2(byte[] bArr, byte[] bArr2, int i, int i2);

    public static native byte[] nativeRBG(int i);

    public static byte[] personalizedHash(byte[] bArr, byte[]... bArr2) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            if (bArr.length > 128) {
                throw new IllegalArgumentException("Personalization too long");
            }
            messageDigest.update(Arrays.copyOf(bArr, 128));
            for (byte[] bArr3 : bArr2) {
                messageDigest.update(bArr3);
            }
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("NoSuchAlgorithmException for SHA-512", e);
        }
    }
}
