package com.sec.android.iaft;

import com.android.internal.midi.MidiConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/* loaded from: classes6.dex */
public class IAFDFileHexUtils {
    public boolean makeFileToHexFile(String path, String inFileName, String outFileName) {
        File originfile = new File(path + inFileName);
        if (!originfile.exists()) {
            return false;
        }
        try {
            convertToHex(path + inFileName, path + outFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void convertToHex(String inFile, String outFile) {
        try {
            FileInputStream fis = new FileInputStream(inFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 1024;
            while (read == 1024) {
                read = fis.read(buffer, 0, 1024);
                bos.write(buffer, 0, read);
            }
            byte[] result = bos.toByteArray();
            String str = byteToHexStr(result);
            fis.close();
            bos.close();
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] hs2bytes = str.getBytes();
            fos.write(hs2bytes, 0, hs2bytes.length);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String byteToHexStr(byte[] bs) {
        String hs = "";
        for (byte b : bs) {
            String stmp = Integer.toHexString(b & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public byte[] makeHexStringToBytes(String hexStr) {
        byte[] bs = null;
        try {
            int len = hexStr.length() >> 1;
            char[] hexChars = hexStr.toCharArray();
            bs = new byte[len];
            for (int i = 0; i < len; i++) {
                int pos = i << 1;
                bs[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bs;
    }

    public boolean makeHexStringToFile(String path, String hexStr, String outFileName) {
        try {
            File outfile = new File(path + outFileName);
            if (outfile.exists()) {
                outfile.delete();
            } else if (!outfile.getParentFile().exists()) {
                outfile.getParentFile().mkdirs();
            }
            byte[] bs = makeHexStringToBytes(hexStr);
            FileOutputStream out = new FileOutputStream(new File(path + outFileName));
            out.write(bs, 0, bs.length);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean makeHexFileToFile(String path, String inHexFileName, String outFileName) {
        File originfile = new File(path + inHexFileName);
        if (!originfile.exists()) {
            return false;
        }
        try {
            String hexStr =
                    new String(Files.readAllBytes(Paths.get(path + inHexFileName, new String[0])));
            boolean result = makeHexStringToFile(path, hexStr, outFileName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private static int charToInt(byte ch) {
        if (ch >= 48 && ch <= 57) {
            int val = ch + MidiConstants.STATUS_CHANNEL_PRESSURE;
            return val;
        }
        if (ch < 65 || ch > 70) {
            return 0;
        }
        int val2 = (ch - 65) + 10;
        return val2;
    }
}
