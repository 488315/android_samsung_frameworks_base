package com.android.systemui.edgelighting.backup;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class Encryption {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec secretKey;
    public static String securityPassword;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ae A[PHI: r1 r2 r9
      0x00ae: PHI (r1v10 ??) = (r1v8 ??), (r1v11 ??) binds: [B:58:0x00ac, B:67:0x00c1] A[DONT_GENERATE, DONT_INLINE]
      0x00ae: PHI (r2v8 ??) = (r2v6 ??), (r2v9 ??) binds: [B:58:0x00ac, B:67:0x00c1] A[DONT_GENERATE, DONT_INLINE]
      0x00ae: PHI (r9v7 java.io.InputStream) = (r9v5 java.io.InputStream), (r9v8 java.io.InputStream) binds: [B:58:0x00ac, B:67:0x00c1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v16, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File decrypt(int i, String str) throws Throwable {
        OutputStream outputStream;
        InputStream inputStream;
        InputStream inputStreamDecryptStream;
        File file = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/decrypt_cocktailbar.xml"));
        ?? fileInputStream = "/encrypt_cocktailbar.xml";
        ?? file2 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/encrypt_cocktailbar.xml"));
        InputStream inputStream2 = null;
        try {
            try {
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
            file2 = 0;
            fileInputStream = 0;
        } catch (Exception e2) {
            e = e2;
            file2 = 0;
            fileInputStream = 0;
        } catch (Throwable th2) {
            th = th2;
            file2 = 0;
            fileInputStream = 0;
        }
        if (!file2.exists()) {
            return null;
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        if (file2.length() > 0) {
            fileInputStream = new FileInputStream((File) file2);
            try {
                inputStreamDecryptStream = decryptStream(fileInputStream, i);
                try {
                    file2 = new FileOutputStream(file);
                } catch (IOException e3) {
                    inputStream2 = inputStreamDecryptStream;
                    e = e3;
                    file2 = 0;
                    fileInputStream = fileInputStream;
                } catch (Exception e4) {
                    inputStream2 = inputStreamDecryptStream;
                    e = e4;
                    file2 = 0;
                    fileInputStream = fileInputStream;
                } catch (Throwable th3) {
                    file2 = 0;
                    inputStream2 = inputStreamDecryptStream;
                    th = th3;
                }
            } catch (IOException e5) {
                e = e5;
                file2 = 0;
                fileInputStream = fileInputStream;
            } catch (Exception e6) {
                e = e6;
                file2 = 0;
                fileInputStream = fileInputStream;
            } catch (Throwable th4) {
                th = th4;
                file2 = 0;
            }
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = inputStreamDecryptStream.read(bArr, 0, 1024);
                    if (i2 == -1) {
                        break;
                    }
                    file2.write(bArr, 0, i2);
                }
                inputStream2 = inputStreamDecryptStream;
                outputStream = file2;
                inputStream = fileInputStream;
            } catch (IOException e7) {
                inputStream2 = inputStreamDecryptStream;
                e = e7;
                file2 = file2;
                fileInputStream = fileInputStream;
                e.printStackTrace();
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (file2 != 0) {
                    file2.close();
                }
                if (fileInputStream != 0) {
                    fileInputStream.close();
                }
                return file;
            } catch (Exception e8) {
                inputStream2 = inputStreamDecryptStream;
                e = e8;
                file2 = file2;
                fileInputStream = fileInputStream;
                e.printStackTrace();
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (file2 != 0) {
                    file2.close();
                }
                if (fileInputStream != 0) {
                }
                return file;
            } catch (Throwable th5) {
                inputStream2 = inputStreamDecryptStream;
                th = th5;
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (file2 != 0) {
                    file2.close();
                }
                if (fileInputStream != 0) {
                    fileInputStream.close();
                }
                throw th;
            }
        } else {
            outputStream = null;
            inputStream = null;
        }
        if (inputStream2 != null) {
            inputStream2.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
            return file;
        }
        return file;
    }

    public static InputStream decryptStream(InputStream inputStream, int i) throws NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
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

    public static void streamCrypt(String str) throws NoSuchAlgorithmException {
        securityPassword = str;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(securityPassword.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        secretKey = new SecretKeySpec(bArr, "AES");
    }
}
