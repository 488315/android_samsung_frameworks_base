package com.android.server.knox.dar;

import android.os.Environment;
import android.security.keystore.KeyProtection;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.util.Log;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KeyProtector {
    public static KeyProtector sInstance;

    public static String attach(int i, String str) {
        if (str != null) {
            return VpnManagerService$$ExternalSyntheticOutline0.m(i, str, "_");
        }
        return null;
    }

    public static boolean checkSecretKey(String str) {
        try {
            return getKeyStore().containsAlias(str);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            Log.e("KeyProtectorBase", "Failed to check secret key - " + str);
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] decryptFast(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length != 32 || bArr2 == null) {
            Log.e("KeyProtector", "fast decryption - Only supported for 32-bytes key");
            return null;
        }
        try {
            byte[] copyOfRange = Arrays.copyOfRange(bArr2, 0, 12);
            byte[] copyOfRange2 = Arrays.copyOfRange(bArr2, 12, bArr2.length);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, secretKeySpec, new GCMParameterSpec(128, copyOfRange));
            return cipher.doFinal(copyOfRange2);
        } catch (Exception e) {
            Log.e("KeyProtector", "fast decryption - Unexpected error");
            e.printStackTrace();
            return null;
        }
    }

    public static void delete(int i, String str) {
        String attach = attach(i, str);
        boolean z = false;
        boolean z2 = !checkSecretKey(attach) || (checkSecretKey(attach) && deleteSecretKey(attach));
        String str2 = Environment.getUserSystemDirectory(i).getAbsolutePath() + "/ENCRYPTED_KEY_" + str + "_" + i;
        Log.d("KeyProtector", "deleteFile - File path : " + str2);
        File file = new File(str2);
        if (file.exists()) {
            try {
                z = file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!z2) {
            Log.d("KeyProtector", "Unexpected failure while delete key with " + attach(i, str));
        }
        if (z) {
            return;
        }
        Log.d("KeyProtector", "Unexpected failure while delete file with " + attach(i, str));
    }

    public static boolean deleteSecretKey(String str) {
        try {
            getKeyStore().deleteEntry(str);
            return true;
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            Log.e("KeyProtectorBase", "Failed to delete secret key - " + str);
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] encryptFast(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = null;
        if (bArr == null || bArr.length != 32) {
            Log.e("KeyProtector", "fast encryption - Only supported for 32-bytes key");
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(1, secretKeySpec);
                byte[] doFinal = cipher.doFinal(bArr2);
                byteArrayOutputStream.write(cipher.getIV());
                byteArrayOutputStream.write(doFinal);
                bArr3 = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e("KeyProtector", "fast encryption - Unexpected error");
            e.printStackTrace();
        }
        return bArr3;
    }

    public static KeyStore getKeyStore() {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(new AndroidKeyStoreLoadStoreParameter(1250));
        return keyStore;
    }

    public static SecretKey getSecretKey(String str) {
        try {
            return (SecretKey) getKeyStore().getKey(str, null);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e) {
            Log.e("KeyProtectorBase", "Failed to get secret key - " + str);
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c4, code lost:
    
        if (r0 == null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00bd, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bb, code lost:
    
        if (r0 == null) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean protect(int r8, java.lang.String r9, byte[] r10) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.KeyProtector.protect(int, java.lang.String, byte[]):boolean");
    }

    public static boolean setSecretKey(String str) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new SecureRandom());
            getKeyStore().setEntry(str, new KeyStore.SecretKeyEntry(keyGenerator.generateKey()), new KeyProtection.Builder(3).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setCriticalToDeviceEncryption(true).build());
            return true;
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            Log.e("KeyProtectorBase", "Failed to set secret key - " + str);
            e.printStackTrace();
            return false;
        }
    }
}
