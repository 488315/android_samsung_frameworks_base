package com.sec.android.iaft;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/* loaded from: classes6.dex */
public class IAFDFileHexUtils {
    private static int charToInt(byte b) {
        if (b >= 48 && b <= 57) {
            return b - SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90;
        }
        if (b < 65 || b > 70) {
            return 0;
        }
        return b - 55;
    }

    public boolean makeFileToHexFile(String str, String str2, String str3) {
        if (!new File(str + str2).exists()) {
            return false;
        }
        try {
            convertToHex(str + str2, str + str3);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void convertToHex(String str, String str2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            int i = 1024;
            while (i == 1024) {
                i = fileInputStream.read(bArr, 0, 1024);
                byteArrayOutputStream.write(bArr, 0, i);
            }
            String byteToHexStr = byteToHexStr(byteArrayOutputStream.toByteArray());
            fileInputStream.close();
            byteArrayOutputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bytes = byteToHexStr.getBytes();
            fileOutputStream.write(bytes, 0, bytes.length);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String byteToHexStr(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str.toUpperCase();
    }

    public byte[] makeHexStringToBytes(String str) {
        byte[] bArr = null;
        try {
            int length = str.length() >> 1;
            char[] charArray = str.toCharArray();
            bArr = new byte[length];
            for (int i = 0; i < length; i++) {
                int i2 = i << 1;
                bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
            }
            return bArr;
        } catch (Exception e) {
            e.printStackTrace();
            return bArr;
        }
    }

    public boolean makeHexStringToFile(String str, String str2, String str3) {
        try {
            File file = new File(str + str3);
            if (file.exists()) {
                file.delete();
            } else if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            byte[] makeHexStringToBytes = makeHexStringToBytes(str2);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str + str3));
            fileOutputStream.write(makeHexStringToBytes, 0, makeHexStringToBytes.length);
            fileOutputStream.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean makeHexFileToFile(String str, String str2, String str3) {
        if (!new File(str + str2).exists()) {
            return false;
        }
        try {
            return makeHexStringToFile(str, new String(Files.readAllBytes(Paths.get(str + str2, new String[0]))), str3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
