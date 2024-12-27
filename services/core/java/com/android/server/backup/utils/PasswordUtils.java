package com.android.server.backup.utils;

import android.util.Slog;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class PasswordUtils {
    public static SecretKey buildCharArrayKey(String str, char[] cArr, byte[] bArr, int i) {
        try {
            return SecretKeyFactory.getInstance(str)
                    .generateSecret(new PBEKeySpec(cArr, bArr, i, 256));
        } catch (NoSuchAlgorithmException unused) {
            Slog.e("BackupManagerService", "PBKDF2 unavailable!");
            return null;
        } catch (InvalidKeySpecException unused2) {
            Slog.e("BackupManagerService", "Invalid key spec for PBKDF2!");
            return null;
        }
    }

    public static byte[] hexToByteArray(String str) {
        int length = str.length() / 2;
        if (length * 2 != str.length()) {
            throw new IllegalArgumentException("Hex string must have an even number of digits");
        }
        byte[] bArr = new byte[length];
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 2;
            bArr[i / 2] = (byte) Integer.parseInt(str.substring(i, i2), 16);
            i = i2;
        }
        return bArr;
    }
}
