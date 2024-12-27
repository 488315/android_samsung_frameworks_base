package com.samsung.android.wallpaperbackup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class BnRFileHelper {
    public static final int REQ_MINIMUM_SIZE = 10485760;
    public static final int SECURITY_LEVEL_HIGH = 1;
    public static final int SECURITY_LEVEL_NORMAL = 0;
    private static final String TAG = BnRFileHelper.class.getSimpleName();

    public enum ErrorCode {
        ERROR_NONE(0),
        UNKNOWN_ERROR(1),
        STORAGE_FULL(2),
        INVALID_DATA(3),
        PARTIAL_SUCCESS(7);

        private int code;

        ErrorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }

    public static ErrorCode checkSaveAvailable(String basePath) {
        ErrorCode errorCode = ErrorCode.ERROR_NONE;
        try {
            File file = new File(basePath);
            if (!file.exists()) {
                boolean success = file.mkdir();
                Slog.d(TAG, "file doesn't exists, Result of making the directory : " + success);
            }
            StatFs stat = new StatFs(basePath);
            long availableBlocks = stat.getAvailableBlocksLong();
            long blockSizeInBytes = stat.getBlockSizeLong();
            long freeSpaceInBytes = availableBlocks * blockSizeInBytes;
            if (freeSpaceInBytes < 10485760) {
                Slog.d(
                        TAG,
                        "StatFs : availableBlocks = "
                                + availableBlocks
                                + " blockSizeInBytes = "
                                + blockSizeInBytes
                                + " freeSpaceInBytes = "
                                + freeSpaceInBytes);
                ErrorCode errorCode2 = ErrorCode.STORAGE_FULL;
                return errorCode2;
            }
            return errorCode;
        } catch (Exception e) {
            ErrorCode errorCode3 = ErrorCode.UNKNOWN_ERROR;
            e.printStackTrace();
            return errorCode3;
        }
    }

    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean copyDir(String targetFilePath, String sourceFilePath, String key) {
        if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(targetFilePath)) {
            Log.d(
                    TAG,
                    "copyDir: filePath is empty. source = "
                            + sourceFilePath
                            + ", target = "
                            + targetFilePath);
            return false;
        }
        try {
            Log.d(TAG, "copyDir: sourceFilePath = " + sourceFilePath);
            Log.d(TAG, "copyDir: targetFilePath = " + targetFilePath);
            File srcDir = new File(sourceFilePath);
            File destDir = new File(targetFilePath);
            if (!destDir.exists()) {
                boolean success = destDir.mkdirs();
                Log.d(
                        TAG,
                        "copydir: "
                                + destDir.getPath()
                                + " is not exist. create success = "
                                + success);
            }
            String[] files = srcDir.list();
            if (files == null) {
                return false;
            }
            for (String f : files) {
                Log.d(TAG, "copyDir: f = " + f);
                File destFile = new File(destDir, f);
                if (key.isEmpty()) {
                    if (!copyFile(new File(srcDir, f), destFile)) {
                        return false;
                    }
                } else if (!copyEncryptFile(new File(srcDir, f), destFile, key)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyAssets(String targetDir, Bundle assets, String key) {
        if (assets == null) {
            return false;
        }
        Set<String> fileNames = assets.keySet();
        for (String fileName : fileNames) {
            String targetFilePath = targetDir + fileName;
            Log.i(TAG, "copyAssets: to " + targetFilePath);
            if (TextUtils.isEmpty(key) || fileName.endsWith(".xml")) {
                if (!copyFile(
                        targetFilePath, (ParcelFileDescriptor) assets.getParcelable(fileName))) {
                    return false;
                }
            } else if (!copyEncryptFile(
                    targetFilePath, (ParcelFileDescriptor) assets.getParcelable(fileName), key)) {
                return false;
            }
        }
        return true;
    }

    public static boolean copyFile(String targetFilePath, String sourceFilePath, String key) {
        if (TextUtils.isEmpty(sourceFilePath)) {
            Log.d(TAG, "copyFile: sourceFilePath is empty.");
            return false;
        }
        File sourceFile = new File(sourceFilePath);
        if (sourceFile.exists() && sourceFile.canRead()) {
            Log.d(TAG, "copyFile: original image file exists.");
            if (TextUtils.isEmpty(key)) {
                return copyFile(sourceFilePath, targetFilePath);
            }
            return copyEncryptFile(sourceFilePath, targetFilePath, key);
        }
        Log.d(TAG, "copyFile: source file does not exists or can't read.");
        return false;
    }

    public static boolean copyFile(String dest, ParcelFileDescriptor source, String sessionKey) {
        if (TextUtils.isEmpty(sessionKey)) {
            return copyFile(dest, source);
        }
        return copyEncryptFile(dest, source, sessionKey);
    }

    public static boolean copyFile(String dest, ParcelFileDescriptor source) {
        Exception e;
        Exception e2;
        FileChannel inc;
        FileChannel outc;
        long fsize;
        File file = new File(dest);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel inc2 = null;
        FileChannel outc2 = null;
        try {
            fin = new FileInputStream(source.getFileDescriptor());
            fout = new FileOutputStream(dest);
            inc = fin.getChannel();
            try {
                outc = fout.getChannel();
                try {
                    fsize = inc.size();
                } catch (Exception e3) {
                    e2 = e3;
                    inc2 = inc;
                    outc2 = outc;
                } catch (Throwable th) {
                    e = th;
                    inc2 = inc;
                    outc2 = outc;
                }
            } catch (Exception e4) {
                e2 = e4;
                inc2 = inc;
            } catch (Throwable th2) {
                e = th2;
                inc2 = inc;
            }
        } catch (Exception e5) {
            e2 = e5;
        } catch (Throwable th3) {
            e = th3;
        }
        try {
            inc.transferTo(0L, fsize, outc);
            closeSilently(inc);
            closeSilently(outc);
            closeSilently(fin);
            closeSilently(fout);
            closeSilently(source);
            return true;
        } catch (Exception e6) {
            e2 = e6;
            inc2 = inc;
            outc2 = outc;
            try {
                e2.printStackTrace();
                closeSilently(inc2);
                closeSilently(outc2);
                closeSilently(fin);
                closeSilently(fout);
                closeSilently(source);
                return false;
            } catch (Throwable th4) {
                e = th4;
                closeSilently(inc2);
                closeSilently(outc2);
                closeSilently(fin);
                closeSilently(fout);
                closeSilently(source);
                throw e;
            }
        } catch (Throwable th5) {
            e = th5;
            inc2 = inc;
            outc2 = outc;
            closeSilently(inc2);
            closeSilently(outc2);
            closeSilently(fin);
            closeSilently(fout);
            closeSilently(source);
            throw e;
        }
    }

    public static boolean copyEncryptFile(
            String dest, ParcelFileDescriptor source, String sessionKey) {
        File file = new File(dest);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileInputStream fin = null;
        FileOutputStream fout = null;
        OutputStream out = null;
        try {
            fin = new FileInputStream(source.getFileDescriptor());
            fout = new FileOutputStream(dest);
            out = encryptStream(fout, sessionKey);
            if (out == null) {
                return false;
            }
            byte[] buffer = new byte[1024];
            while (true) {
                int readcount = fin.read(buffer, 0, 1024);
                if (readcount == -1) {
                    closeSilently(fin);
                    closeSilently(fout);
                    closeSilently(out);
                    closeSilently(source);
                    return true;
                }
                out.write(buffer, 0, readcount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeSilently(fin);
            closeSilently(fout);
            closeSilently(out);
            closeSilently(source);
        }
    }

    public static boolean copyFile(String srcPath, String desPath) {
        Exception e;
        Exception e2;
        FileChannel inc;
        FileChannel outc;
        long fsize;
        File file = new File(desPath);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel inc2 = null;
        FileChannel outc2 = null;
        try {
            fin = new FileInputStream(srcPath);
            fout = new FileOutputStream(desPath);
            inc = fin.getChannel();
            try {
                outc = fout.getChannel();
                try {
                    fsize = inc.size();
                } catch (Exception e3) {
                    e2 = e3;
                    inc2 = inc;
                    outc2 = outc;
                } catch (Throwable th) {
                    e = th;
                    inc2 = inc;
                    outc2 = outc;
                }
            } catch (Exception e4) {
                e2 = e4;
                inc2 = inc;
            } catch (Throwable th2) {
                e = th2;
                inc2 = inc;
            }
        } catch (Exception e5) {
            e2 = e5;
        } catch (Throwable th3) {
            e = th3;
        }
        try {
            inc.transferTo(0L, fsize, outc);
            closeSilently(inc);
            closeSilently(outc);
            closeSilently(fin);
            closeSilently(fout);
            return true;
        } catch (Exception e6) {
            e2 = e6;
            inc2 = inc;
            outc2 = outc;
            try {
                e2.printStackTrace();
                closeSilently(inc2);
                closeSilently(outc2);
                closeSilently(fin);
                closeSilently(fout);
                return false;
            } catch (Throwable th4) {
                e = th4;
                closeSilently(inc2);
                closeSilently(outc2);
                closeSilently(fin);
                closeSilently(fout);
                throw e;
            }
        } catch (Throwable th5) {
            e = th5;
            inc2 = inc;
            outc2 = outc;
            closeSilently(inc2);
            closeSilently(outc2);
            closeSilently(fin);
            closeSilently(fout);
            throw e;
        }
    }

    public static boolean copyFile(File srcFile, File destFile) {
        Exception e;
        Exception e2;
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel inc = null;
        FileChannel outc = null;
        try {
            fin = new FileInputStream(srcFile);
            fout = new FileOutputStream(destFile);
            FileChannel inc2 = fin.getChannel();
            try {
                FileChannel outc2 = fout.getChannel();
                try {
                    long fsize = inc2.size();
                    try {
                        inc2.transferTo(0L, fsize, outc2);
                        closeSilently(inc2);
                        closeSilently(outc2);
                        closeSilently(fin);
                        closeSilently(fout);
                        return true;
                    } catch (Exception e3) {
                        e2 = e3;
                        inc = inc2;
                        outc = outc2;
                        try {
                            e2.printStackTrace();
                            closeSilently(inc);
                            closeSilently(outc);
                            closeSilently(fin);
                            closeSilently(fout);
                            return false;
                        } catch (Throwable th) {
                            e = th;
                            closeSilently(inc);
                            closeSilently(outc);
                            closeSilently(fin);
                            closeSilently(fout);
                            throw e;
                        }
                    } catch (Throwable th2) {
                        e = th2;
                        inc = inc2;
                        outc = outc2;
                        closeSilently(inc);
                        closeSilently(outc);
                        closeSilently(fin);
                        closeSilently(fout);
                        throw e;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    inc = inc2;
                    outc = outc2;
                } catch (Throwable th3) {
                    e = th3;
                    inc = inc2;
                    outc = outc2;
                }
            } catch (Exception e5) {
                e2 = e5;
                inc = inc2;
            } catch (Throwable th4) {
                e = th4;
                inc = inc2;
            }
        } catch (Exception e6) {
            e2 = e6;
        } catch (Throwable th5) {
            e = th5;
        }
    }

    public static boolean copyEncryptFile(
            String sourceImagePath, String destImagePath, String sessionKey) {
        Slog.d(
                TAG,
                "copyEncryptFile sourceImagePath = "
                        + sourceImagePath
                        + " destImagePath = "
                        + destImagePath);
        File file = new File(destImagePath);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileInputStream fin = null;
        FileOutputStream fout = null;
        OutputStream out = null;
        try {
            fin = new FileInputStream(sourceImagePath);
            fout = new FileOutputStream(destImagePath);
            out = encryptStream(fout, sessionKey);
            if (out == null) {
                return false;
            }
            byte[] buffer = new byte[1024];
            while (true) {
                int readcount = fin.read(buffer, 0, 1024);
                if (readcount == -1) {
                    closeSilently(fin);
                    closeSilently(fout);
                    closeSilently(out);
                    return true;
                }
                out.write(buffer, 0, readcount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeSilently(fin);
            closeSilently(fout);
            closeSilently(out);
        }
    }

    public static boolean copyEncryptFile(File srcFile, File destFile, String sessionKey) {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        OutputStream out = null;
        try {
            fin = new FileInputStream(srcFile);
            fout = new FileOutputStream(destFile);
            out = encryptStream(fout, sessionKey);
            if (out == null) {
                return false;
            }
            byte[] buffer = new byte[1024];
            while (true) {
                int readcount = fin.read(buffer, 0, 1024);
                if (readcount == -1) {
                    closeSilently(fin);
                    closeSilently(fout);
                    closeSilently(out);
                    return true;
                }
                out.write(buffer, 0, readcount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeSilently(fin);
            closeSilently(fout);
            closeSilently(out);
        }
    }

    public static OutputStream encryptStream(OutputStream out, String sessionKey) {
        if (sessionKey.isEmpty()) {
            Slog.d(TAG, "sessionKey is empty");
            return out;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[cipher.getBlockSize()];
            new SecureRandom().nextBytes(iv);
            AlgorithmParameterSpec spec = new IvParameterSpec(iv);
            out.write(iv);
            byte[] salt = generateEncryptSalt();
            out.write(salt);
            SecretKeySpec secretKey = generatePBKDF2SecretKey(salt, sessionKey);
            if (secretKey == null) {
                return null;
            }
            cipher.init(1, secretKey, spec);
            OutputStream result = new CipherOutputStream(out, cipher);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidAlgorithmParameterException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static InputStream decryptStream(InputStream in, String sessionKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[cipher.getBlockSize()];
            in.read(iv);
            AlgorithmParameterSpec spec = new IvParameterSpec(iv);
            byte[] salt = new byte[16];
            in.read(salt);
            SecretKeySpec secretKey = generatePBKDF2SecretKey(salt, sessionKey);
            if (secretKey == null) {
                return null;
            }
            cipher.init(2, secretKey, spec);
            InputStream result = new CipherInputStream(in, cipher);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidAlgorithmParameterException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static byte[] generateEncryptSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static SecretKeySpec generatePBKDF2SecretKey(byte[] salt, String securityPassword) {
        char[] chars = securityPassword.toCharArray();
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec keySpec = new PBEKeySpec(chars, salt, 1000, 256);
            SecretKey key = keyFactory.generateSecret(keySpec);
            SecretKeySpec resultKey = new SecretKeySpec(key.getEncoded(), "AES");
            return resultKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void closeSilently(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException e) {
            Slog.w(TAG, "close fail ", e);
        }
    }

    public static boolean isExist(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static Bitmap getBitmapFromPath(String imagePath, int securityLevel, String saveKey) {
        Bitmap bitmap = null;
        switch (securityLevel) {
            case 0:
                return BitmapFactory.decodeFile(imagePath);
            case 1:
                try {
                    InputStream stream = new FileInputStream(imagePath);
                    InputStream decryptedStream = decryptStream(stream, saveKey);
                    if (decryptedStream != null) {
                        bitmap = BitmapFactory.decodeStream(decryptedStream);
                    }
                    closeSilently(stream);
                    closeSilently(decryptedStream);
                    return bitmap;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return bitmap;
                }
            default:
                return null;
        }
    }

    public static InputStream getInputStreamFromPath(
            String imagePath, int securityLevel, String saveKey) {
        try {
            InputStream stream = new FileInputStream(imagePath);
            switch (securityLevel) {
                case 0:
                    return stream;
                case 1:
                    InputStream decryptedStream = decryptStream(stream, saveKey);
                    closeSilently(stream);
                    return decryptedStream;
                default:
                    return stream;
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
            return null;
        }
        fne.printStackTrace();
        return null;
    }
}
