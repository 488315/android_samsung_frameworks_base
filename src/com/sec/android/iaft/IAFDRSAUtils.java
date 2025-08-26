package com.sec.android.iaft;

import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes6.dex */
public class IAFDRSAUtils {
    public static final String KEY_ALGORITHM = "RSA";
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static byte[] decryptFileToBytes(String str, String str2) throws Exception {
        return decrypt(fileToByte(str), str2);
    }

    public static void decryptBytesToFile(byte[] bArr, String str, String str2) throws Exception {
        byteArrayToFile(decrypt(bArr, str), str2);
    }

    public static String decryptFile(String str, String str2) throws Exception {
        byte[] bArrDecrypt = decrypt(fileToByte(str), str2);
        String str3 = str + ".dec";
        byteArrayToFile(bArrDecrypt, str3);
        return str3;
    }

    public static byte[] decrypt(byte[] bArr, String str) throws Exception {
        byte[] bArrDoFinal;
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str.getBytes(), 2)));
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, publicKeyGeneratePublic);
        int length = bArr.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = length - i;
            if (i3 > 0) {
                if (i3 > 128) {
                    bArrDoFinal = cipher.doFinal(bArr, i, 128);
                } else {
                    bArrDoFinal = cipher.doFinal(bArr, i, i3);
                }
                byteArrayOutputStream.write(bArrDoFinal, 0, bArrDoFinal.length);
                i2++;
                i = i2 * 128;
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    private static byte[] fileToByte(String str) throws Exception {
        byte[] bArr = new byte[0];
        File file = new File(str);
        if (!file.exists()) {
            return bArr;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        byte[] bArr2 = new byte[1024];
        while (true) {
            int i = fileInputStream.read(bArr2);
            if (i != -1) {
                byteArrayOutputStream.write(bArr2, 0, i);
                byteArrayOutputStream.flush();
            } else {
                byteArrayOutputStream.close();
                fileInputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    private static void byteArrayToFile(byte[] bArr, String str) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bArr2 = new byte[1024];
        while (true) {
            int i = byteArrayInputStream.read(bArr2);
            if (i != -1) {
                fileOutputStream.write(bArr2, 0, i);
                fileOutputStream.flush();
            } else {
                fileOutputStream.close();
                byteArrayInputStream.close();
                return;
            }
        }
    }
}
