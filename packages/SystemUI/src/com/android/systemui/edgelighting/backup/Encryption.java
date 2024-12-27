package com.android.systemui.edgelighting.backup;

import java.io.InputStream;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public final class Encryption {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec secretKey;
    public static String securityPassword;

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0098, code lost:
    
        if (r2 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x009a, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00c0, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00bd, code lost:
    
        if (r2 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ab, code lost:
    
        if (r2 != null) goto L51;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v19, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v14, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.File decrypt(int r8, java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.backup.Encryption.decrypt(int, java.lang.String):java.io.File");
    }

    public static InputStream decryptStream(InputStream inputStream, int i) {
        byte[] bArr = new byte[mCipher.getBlockSize()];
        inputStream.read(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            mSalt = bArr2;
            inputStream.read(bArr2);
            secretKey = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(securityPassword.toCharArray(), mSalt, 1000, 256)).getEncoded(), "AES");
        } else {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(securityPassword.getBytes("UTF-8"));
            byte[] bArr3 = new byte[16];
            System.arraycopy(messageDigest.digest(), 0, bArr3, 0, 16);
            secretKey = new SecretKeySpec(bArr3, "AES");
        }
        mCipher.init(2, secretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, mCipher);
    }

    public static void streamCrypt(String str) {
        securityPassword = str;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(securityPassword.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        secretKey = new SecretKeySpec(bArr, "AES");
    }
}
