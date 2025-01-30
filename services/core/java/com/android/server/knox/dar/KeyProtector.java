package com.android.server.knox.dar;

import android.os.Environment;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class KeyProtector extends KeyProtectorBase {
    public static KeyProtector sInstance;

    public static KeyProtector getInstance() {
        if (sInstance == null) {
            synchronized (KeyProtector.class) {
                if (sInstance == null) {
                    sInstance = new KeyProtector();
                }
            }
        }
        return sInstance;
    }

    public boolean protect(byte[] bArr, String str, int i) {
        if (bArr == null || str == null) {
            Log.d("KeyProtector", "Wrong input parameter...");
            return false;
        }
        String attach = attach(str, i);
        try {
            if (setSecretKey(attach)) {
                SecretKey secretKey = getSecretKey(attach);
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(1, secretKey);
                byte[] doFinal = cipher.doFinal(bArr);
                byte[] iv = cipher.getIV();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int length = iv == null ? 0 : iv.length;
                try {
                    if (length != 12) {
                        Log.d("KeyProtector", "Invalid iv length : " + length);
                        delete(str, i);
                        return false;
                    }
                    byteArrayOutputStream.write(iv);
                    byteArrayOutputStream.write(doFinal);
                    if (!writeToFile(i, str, byteArrayOutputStream.toByteArray())) {
                        Log.d("KeyProtector", "Failed to write into file...");
                        delete(str, i);
                        return false;
                    }
                    Log.d("KeyProtector", "Successfully wrote into file!");
                    return true;
                } catch (IOException e) {
                    Log.d("KeyProtector", "Failed to concatenate byte arrays");
                    e.printStackTrace();
                    delete(str, i);
                    return false;
                }
            }
            throw new Exception("Unexpected failure while set key");
        } catch (Exception e2) {
            e2.printStackTrace();
            deleteSecretKey(attach);
            return false;
        }
    }

    public byte[] release(String str, int i) {
        byte[] readFile = readFile(i, str);
        if (readFile == null) {
            return null;
        }
        try {
            byte[] copyOfRange = Arrays.copyOfRange(readFile, 0, 12);
            byte[] copyOfRange2 = Arrays.copyOfRange(readFile, 12, readFile.length);
            try {
                SecretKey secretKey = getSecretKey(attach(str, i));
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(2, secretKey, new GCMParameterSpec(128, copyOfRange));
                return cipher.doFinal(copyOfRange2);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            Log.d("KeyProtector", "Failed in copying array...");
            e2.printStackTrace();
            return null;
        }
    }

    public boolean delete(String str, int i) {
        String attach = attach(str, i);
        boolean z = !checkSecretKey(attach) || (checkSecretKey(attach) && deleteSecretKey(attach));
        boolean deleteFile = deleteFile(i, str);
        if (!z) {
            Log.d("KeyProtector", "Unexpected failure while delete key with " + attach(str, i));
        }
        if (!deleteFile) {
            Log.d("KeyProtector", "Unexpected failure while delete file with " + attach(str, i));
        }
        return z && deleteFile;
    }

    public boolean exists(String str, int i) {
        String attach = attach(str, i);
        boolean checkSecretKey = checkSecretKey(attach);
        if (checkSecretKey) {
            Log.d("KeyProtector", "Key exists in keystore(" + attach + ")");
        }
        return checkSecretKey;
    }

    public byte[] encryptFast(byte[] bArr, byte[] bArr2) {
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

    public byte[] decryptFast(byte[] bArr, byte[] bArr2) {
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

    /* JADX WARN: Code restructure failed: missing block: B:21:0x007a, code lost:
    
        if (r4 == null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] readFile(int i, String str) {
        byte[] bArr;
        FileInputStream fileInputStream;
        String str2 = Environment.getUserSystemDirectory(i).getAbsolutePath() + "/ENCRYPTED_KEY_" + str + "_" + i;
        Log.d("KeyProtector", "readFile - File path : " + str2);
        File file = new File(str2);
        FileInputStream fileInputStream2 = null;
        r4 = null;
        byte[] bArr2 = null;
        r4 = null;
        FileInputStream fileInputStream3 = null;
        try {
            if (!file.exists()) {
                return null;
            }
            try {
                fileInputStream = new FileInputStream(file);
            } catch (IOException e) {
                e = e;
                bArr = null;
            } catch (Exception e2) {
                e = e2;
                bArr = null;
            }
            try {
                bArr2 = new byte[fileInputStream.available()];
                fileInputStream.read(bArr2);
                try {
                    fileInputStream.close();
                    return bArr2;
                } catch (IOException unused) {
                    return bArr2;
                }
            } catch (IOException e3) {
                e = e3;
                byte[] bArr3 = bArr2;
                fileInputStream3 = fileInputStream;
                bArr = bArr3;
                e.printStackTrace();
            } catch (Exception e4) {
                e = e4;
                byte[] bArr4 = bArr2;
                fileInputStream3 = fileInputStream;
                bArr = bArr4;
                e.printStackTrace();
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException unused2) {
                    }
                }
                return bArr;
            } catch (Throwable th) {
                th = th;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0070, code lost:
    
        if (r3 == null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0066, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
    
        if (r3 == null) goto L24;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0078 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.io.FileOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean writeToFile(int i, String str, byte[] bArr) {
        Throwable th;
        FileOutputStream fileOutputStream;
        Exception e;
        IOException e2;
        String str2 = Environment.getUserSystemDirectory(i).getAbsolutePath() + "/ENCRYPTED_KEY_" + str + "_" + i;
        ?? r3 = "KeyProtector";
        Log.d("KeyProtector", "writeToFile - File path : " + str2);
        try {
            try {
                fileOutputStream = new FileOutputStream(new File(str2));
                try {
                    fileOutputStream.write(bArr);
                    fileOutputStream.flush();
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused) {
                    }
                    return true;
                } catch (IOException e3) {
                    e2 = e3;
                    e2.printStackTrace();
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
                if (r3 != 0) {
                    try {
                        r3.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            fileOutputStream = null;
            e2 = e5;
        } catch (Exception e6) {
            fileOutputStream = null;
            e = e6;
        } catch (Throwable th3) {
            r3 = 0;
            th = th3;
            if (r3 != 0) {
            }
            throw th;
        }
        return false;
    }

    public final boolean deleteFile(int i, String str) {
        String str2 = Environment.getUserSystemDirectory(i).getAbsolutePath() + "/ENCRYPTED_KEY_" + str + "_" + i;
        Log.d("KeyProtector", "deleteFile - File path : " + str2);
        File file = new File(str2);
        if (file.exists()) {
            try {
                return file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public final String attach(String str, int i) {
        if (str == null) {
            return null;
        }
        return str + "_" + i;
    }
}
