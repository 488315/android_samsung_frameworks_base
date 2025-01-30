package com.samsung.android.server.util;

import android.content.Context;
import android.os.IInstalld;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Base64;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes2.dex */
public class CoreEncryptor {
    public static final ConcurrentHashMap sCoreEncryptor = new ConcurrentHashMap();
    public final Context mContext;

    public static CoreEncryptor getCoreEncryptor(Context context) {
        ConcurrentHashMap concurrentHashMap = sCoreEncryptor;
        CoreEncryptor coreEncryptor = (CoreEncryptor) concurrentHashMap.get(context);
        if (coreEncryptor != null) {
            return coreEncryptor;
        }
        CoreEncryptor coreEncryptor2 = new CoreEncryptor(context);
        concurrentHashMap.put(context, coreEncryptor2);
        return coreEncryptor2;
    }

    public CoreEncryptor(Context context) {
        this.mContext = context;
    }

    public boolean encrypt(InputStream inputStream, OutputStream outputStream) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, KeyStoreHolder.getKey(this.mContext, "android_CoreEncryptorKey"));
            byte[] iv = cipher.getIV();
            outputStream.write(iv.length);
            outputStream.write(iv);
            byte[] bArr = new byte[IInstalld.FLAG_FORCE];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    outputStream.write(cipher.update(bArr, 0, read));
                } else {
                    outputStream.write(cipher.doFinal());
                    return true;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public boolean decrypt(InputStream inputStream, OutputStream outputStream) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            byte[] bArr = new byte[IInstalld.FLAG_FORCE];
            int read = inputStream.read();
            byte[] bArr2 = new byte[read];
            inputStream.read(bArr2, 0, read);
            cipher.init(2, KeyStoreHolder.getKey(this.mContext, "android_CoreEncryptorKey"), new IvParameterSpec(bArr2));
            while (true) {
                int read2 = inputStream.read(bArr);
                if (read2 != -1) {
                    outputStream.write(cipher.update(bArr, 0, read2));
                } else {
                    outputStream.write(cipher.doFinal());
                    return true;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public abstract class KeyStoreHolder {
        public static SecretKey generateKey(String str) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder(str, 3).setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").setDigests("SHA-256").setUserAuthenticationRequired(false).setKeySize(256).build());
            return keyGenerator.generateKey();
        }

        public static SecretKey getKey(Context context, String str) {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            String format = String.format("%s_scspcipher_%s", context.getPackageName(), str);
            SecretKey secretKey = (SecretKey) keyStore.getKey(format, null);
            return secretKey == null ? generateKey(format) : secretKey;
        }
    }

    public static String encodeBase64String(String str) {
        return str != null ? new String(Base64.encode(str.getBytes(), 0)) : "";
    }

    public static String decodeBase64String(String str) {
        return str != null ? new String(Base64.decode(str, 0)) : "";
    }
}
