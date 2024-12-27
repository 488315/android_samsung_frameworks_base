package com.android.server.locksettings;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public abstract class AesEncryptionUtil {
    public static byte[] decrypt(SecretKey secretKey, DataInputStream dataInputStream) {
        Objects.requireNonNull(secretKey);
        int readInt = dataInputStream.readInt();
        if (readInt < 0 || readInt > 32) {
            throw new IOException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(readInt, "IV out of range: "));
        }
        byte[] bArr = new byte[readInt];
        dataInputStream.readFully(bArr);
        int readInt2 = dataInputStream.readInt();
        if (readInt2 < 0) {
            throw new IOException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            readInt2, "Invalid cipher text size: "));
        }
        byte[] bArr2 = new byte[readInt2];
        dataInputStream.readFully(bArr2);
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, secretKey, new GCMParameterSpec(128, bArr));
            return cipher.doFinal(bArr2);
        } catch (InvalidAlgorithmParameterException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            throw new IOException("Could not decrypt cipher text", e);
        }
    }

    public static byte[] encrypt(byte[] bArr, SecretKey secretKey) {
        Objects.requireNonNull(secretKey);
        Objects.requireNonNull(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, secretKey);
            byte[] doFinal = cipher.doFinal(bArr);
            byte[] iv = cipher.getIV();
            dataOutputStream.writeInt(iv.length);
            dataOutputStream.write(iv);
            dataOutputStream.writeInt(doFinal.length);
            dataOutputStream.write(doFinal);
            return byteArrayOutputStream.toByteArray();
        } catch (InvalidKeyException
                | NoSuchAlgorithmException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            throw new IOException("Could not encrypt input data", e);
        }
    }
}
