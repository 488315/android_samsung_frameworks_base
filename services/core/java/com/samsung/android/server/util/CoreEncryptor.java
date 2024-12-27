package com.samsung.android.server.util;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.security.keystore.KeyGenParameterSpec;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CoreEncryptor {
    public static final ConcurrentHashMap sCoreEncryptor = new ConcurrentHashMap();
    public final Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class KeyStoreHolder {
        /* renamed from: -$$Nest$smgetKey, reason: not valid java name */
        public static SecretKey m1233$$Nest$smgetKey(Context context) {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            String m$1 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            context.getPackageName(), "_scspcipher_android_CoreEncryptorKey");
            SecretKey secretKey = (SecretKey) keyStore.getKey(m$1, null);
            if (secretKey != null) {
                return secretKey;
            }
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            keyGenerator.init(
                    new KeyGenParameterSpec.Builder(m$1, 3)
                            .setBlockModes("CBC")
                            .setEncryptionPaddings("PKCS7Padding")
                            .setDigests("SHA-256")
                            .setUserAuthenticationRequired(false)
                            .setKeySize(256)
                            .build());
            return keyGenerator.generateKey();
        }
    }

    public CoreEncryptor(Context context) {
        this.mContext = context;
    }

    public final boolean decrypt(InputStream inputStream, OutputStream outputStream) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            byte[] bArr = new byte[8192];
            int read = inputStream.read();
            byte[] bArr2 = new byte[read];
            inputStream.read(bArr2, 0, read);
            cipher.init(
                    2,
                    KeyStoreHolder.m1233$$Nest$smgetKey(this.mContext),
                    new IvParameterSpec(bArr2));
            while (true) {
                int read2 = inputStream.read(bArr);
                if (read2 == -1) {
                    outputStream.write(cipher.doFinal());
                    return true;
                }
                outputStream.write(cipher.update(bArr, 0, read2));
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public final boolean encrypt(InputStream inputStream, OutputStream outputStream) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, KeyStoreHolder.m1233$$Nest$smgetKey(this.mContext));
            byte[] iv = cipher.getIV();
            outputStream.write(iv.length);
            outputStream.write(iv);
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    outputStream.write(cipher.doFinal());
                    return true;
                }
                outputStream.write(cipher.update(bArr, 0, read));
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}
