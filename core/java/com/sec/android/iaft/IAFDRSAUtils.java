package com.sec.android.iaft;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/* loaded from: classes6.dex */
public class IAFDRSAUtils {
    public static final String KEY_ALGORITHM = "RSA";
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static byte[] decryptFileToBytes(String filePath, String pkey) throws Exception {
        byte[] encryptedData = fileToByte(filePath);
        byte[] decodedData = decrypt(encryptedData, pkey);
        return decodedData;
    }

    public static void decryptBytesToFile(byte[] encryptedData, String pkey, String filePath)
            throws Exception {
        byte[] decodedData = decrypt(encryptedData, pkey);
        byteArrayToFile(decodedData, filePath);
    }

    public static String decryptFile(String filePath, String pkey) throws Exception {
        byte[] encryptedData = fileToByte(filePath);
        byte[] decodedData = decrypt(encryptedData, pkey);
        String newPath = filePath + ".dec";
        byteArrayToFile(decodedData, newPath);
        return newPath;
    }

    public static byte[] decrypt(byte[] encryptedData, String pkey) throws Exception {
        byte[] cache;
        byte[] keyBytes = Base64.decode(pkey.getBytes(), 2);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    private static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[1024];
            while (true) {
                int nRead = in.read(cache);
                if (nRead != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                } else {
                    out.close();
                    in.close();
                    byte[] data2 = out.toByteArray();
                    return data2;
                }
            }
        } else {
            return data;
        }
    }

    private static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (destFile.exists()) {
            destFile.delete();
        } else {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[1024];
        while (true) {
            int nRead = in.read(cache);
            if (nRead != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            } else {
                out.close();
                in.close();
                return;
            }
        }
    }
}
