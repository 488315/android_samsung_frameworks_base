package com.android.systemui.edgelighting.backup;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Encryption {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec secretKey;
    public static String securityPassword;

    /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:
    
        if (r2 != 0) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b2, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00af, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ad, code lost:
    
        if (r2 != 0) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0098, code lost:
    
        if (r2 != 0) goto L63;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c2  */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File decrypt(int i, String str) {
        Throwable th;
        ?? r1;
        ?? r2;
        FileOutputStream fileOutputStream;
        ?? r22;
        Exception e;
        InputStream inputStream;
        IOException e2;
        ?? r8;
        String str2;
        File file;
        File file2 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/decrypt_cocktailbar.xml"));
        String str3 = "/encrypt_cocktailbar.xml";
        File file3 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/encrypt_cocktailbar.xml"));
        InputStream inputStream2 = null;
        try {
            try {
                if (!file3.exists()) {
                    return null;
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                if (file3.length() > 0) {
                    r22 = new FileInputStream(file3);
                    try {
                        inputStream = decryptStream(r22, i);
                        try {
                            fileOutputStream = new FileOutputStream(file2);
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int read = inputStream.read(bArr, 0, 1024);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                inputStream2 = inputStream;
                                r22 = r22;
                            } catch (IOException e3) {
                                e2 = e3;
                                e2.printStackTrace();
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (Exception e4) {
                                e = e4;
                                e.printStackTrace();
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            }
                        } catch (IOException e5) {
                            fileOutputStream = null;
                            e2 = e5;
                        } catch (Exception e6) {
                            fileOutputStream = null;
                            e = e6;
                        } catch (Throwable th2) {
                            th = th2;
                            file = null;
                            str2 = r22;
                            r8 = inputStream;
                            inputStream2 = r8;
                            r1 = file;
                            r2 = str2;
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            if (r1 != 0) {
                                r1.close();
                            }
                            if (r2 != 0) {
                                r2.close();
                            }
                            throw th;
                        }
                    } catch (IOException e7) {
                        fileOutputStream = null;
                        e2 = e7;
                        inputStream = null;
                    } catch (Exception e8) {
                        fileOutputStream = null;
                        e = e8;
                        inputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                        r1 = 0;
                        r2 = r22;
                        if (inputStream2 != null) {
                        }
                        if (r1 != 0) {
                        }
                        if (r2 != 0) {
                        }
                        throw th;
                    }
                } else {
                    fileOutputStream = null;
                    r22 = 0;
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th4) {
                th = th4;
                file = file3;
                str2 = str3;
                r8 = i;
            }
        } catch (IOException e9) {
            fileOutputStream = null;
            r22 = 0;
            e2 = e9;
            inputStream = null;
        } catch (Exception e10) {
            fileOutputStream = null;
            r22 = 0;
            e = e10;
            inputStream = null;
        } catch (Throwable th5) {
            th = th5;
            r1 = 0;
            r2 = 0;
        }
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
