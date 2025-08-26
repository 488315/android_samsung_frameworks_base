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
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes6.dex */
public class BnRFileHelper {
    public static final int REQ_MINIMUM_SIZE = 10485760;
    public static final int SECURITY_LEVEL_HIGH = 1;
    public static final int SECURITY_LEVEL_NORMAL = 0;
    private static final String TAG = "BnRFileHelper";

    public enum ErrorCode {
        ERROR_NONE(0),
        UNKNOWN_ERROR(1),
        STORAGE_FULL(2),
        INVALID_DATA(3),
        PARTIAL_SUCCESS(7);

        private int code;

        ErrorCode(int i) {
            this.code = i;
        }

        public int getCode() {
            return this.code;
        }
    }

    public static ErrorCode checkSaveAvailable(String str) {
        ErrorCode errorCode = ErrorCode.ERROR_NONE;
        try {
            File file = new File(str);
            if (!file.exists()) {
                boolean zMkdir = file.mkdir();
                Slog.d(TAG, "file doesn't exists, Result of making the directory : " + zMkdir);
            }
            StatFs statFs = new StatFs(str);
            long availableBlocksLong = statFs.getAvailableBlocksLong();
            long blockSizeLong = statFs.getBlockSizeLong();
            long j = availableBlocksLong * blockSizeLong;
            if (j >= 10485760) {
                return errorCode;
            }
            Slog.d(TAG, "StatFs : availableBlocks = " + availableBlocksLong + " blockSizeInBytes = " + blockSizeLong + " freeSpaceInBytes = " + j);
            return ErrorCode.STORAGE_FULL;
        } catch (Exception e) {
            ErrorCode errorCode2 = ErrorCode.UNKNOWN_ERROR;
            e.printStackTrace();
            return errorCode2;
        }
    }

    public static void deleteFile(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean copyDir(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            Log.d(TAG, "copyDir: filePath is empty. source = " + str2 + ", target = " + str);
            return false;
        }
        try {
            String str4 = TAG;
            Log.d(str4, "copyDir: sourceFilePath = " + str2);
            Log.d(str4, "copyDir: targetFilePath = " + str);
            File file = new File(str2);
            File file2 = new File(str);
            if (!file2.exists()) {
                Log.d(str4, "copydir: " + file2.getPath() + " is not exist. create success = " + file2.mkdirs());
            }
            String[] list = file.list();
            if (list == null) {
                return false;
            }
            for (String str5 : list) {
                Log.d(TAG, "copyDir: f = " + str5);
                File file3 = new File(file2, str5);
                if (str3.isEmpty()) {
                    if (!copyFile(new File(file, str5), file3)) {
                        return false;
                    }
                } else if (!copyEncryptFile(new File(file, str5), file3, str3)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyAssets(String str, Bundle bundle, String str2) {
        if (bundle == null) {
            return false;
        }
        for (String str3 : bundle.keySet()) {
            String str4 = str + str3;
            Log.i(TAG, "copyAssets: to " + str4);
            if (TextUtils.isEmpty(str2) || str3.endsWith(".xml")) {
                if (!copyFile(str4, (ParcelFileDescriptor) bundle.getParcelable(str3))) {
                    return false;
                }
            } else if (!copyEncryptFile(str4, (ParcelFileDescriptor) bundle.getParcelable(str3), str2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean copyFile(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            Log.d(TAG, "copyFile: sourceFilePath is empty.");
            return false;
        }
        File file = new File(str2);
        if (file.exists() && file.canRead()) {
            Log.d(TAG, "copyFile: original image file exists.");
            if (TextUtils.isEmpty(str3)) {
                return copyFile(str2, str);
            }
            return copyEncryptFile(str2, str, str3);
        }
        Log.d(TAG, "copyFile: source file does not exists or can't read.");
        return false;
    }

    public static boolean copyFile(String str, ParcelFileDescriptor parcelFileDescriptor, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return copyFile(str, parcelFileDescriptor);
        }
        return copyEncryptFile(str, parcelFileDescriptor, str2);
    }

    public static boolean copyFile(String str, ParcelFileDescriptor parcelFileDescriptor) throws Throwable {
        Throwable th;
        FileInputStream fileInputStream;
        FileChannel fileChannel;
        Exception exc;
        FileChannel fileChannel2;
        FileChannel channel;
        FileChannel fileChannel3;
        FileChannel fileChannel4;
        File file = new File(str);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileChannel fileChannel5 = null;
        try {
            fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                try {
                    FileChannel channel2 = fileInputStream.getChannel();
                    try {
                        channel = fileOutputStream.getChannel();
                        try {
                            channel2.transferTo(0L, channel2.size(), channel);
                            closeSilently(channel2);
                            closeSilently(channel);
                            closeSilently(fileInputStream);
                            closeSilently(fileOutputStream);
                            closeSilently(parcelFileDescriptor);
                            return true;
                        } catch (Exception e) {
                            exc = e;
                            fileChannel5 = channel2;
                            fileChannel4 = fileOutputStream;
                            try {
                                exc.printStackTrace();
                                closeSilently(fileChannel5);
                                closeSilently(channel);
                                closeSilently(fileInputStream);
                                closeSilently(fileChannel4);
                                closeSilently(parcelFileDescriptor);
                                return false;
                            } catch (Throwable th2) {
                                th = th2;
                                fileChannel3 = fileChannel4;
                                closeSilently(fileChannel5);
                                closeSilently(channel);
                                closeSilently(fileInputStream);
                                closeSilently(fileChannel3);
                                closeSilently(parcelFileDescriptor);
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            fileChannel5 = channel2;
                            fileChannel3 = fileOutputStream;
                            closeSilently(fileChannel5);
                            closeSilently(channel);
                            closeSilently(fileInputStream);
                            closeSilently(fileChannel3);
                            closeSilently(parcelFileDescriptor);
                            throw th;
                        }
                    } catch (Exception e2) {
                        exc = e2;
                        channel = null;
                    } catch (Throwable th4) {
                        th = th4;
                        channel = null;
                    }
                } catch (Exception e3) {
                    exc = e3;
                    channel = null;
                    fileChannel4 = fileOutputStream;
                } catch (Throwable th5) {
                    th = th5;
                    channel = null;
                    fileChannel3 = fileOutputStream;
                }
            } catch (Exception e4) {
                exc = e4;
                fileChannel2 = null;
                channel = fileChannel2;
                fileChannel4 = fileChannel2;
                exc.printStackTrace();
                closeSilently(fileChannel5);
                closeSilently(channel);
                closeSilently(fileInputStream);
                closeSilently(fileChannel4);
                closeSilently(parcelFileDescriptor);
                return false;
            } catch (Throwable th6) {
                th = th6;
                fileChannel = null;
                channel = fileChannel;
                fileChannel3 = fileChannel;
                closeSilently(fileChannel5);
                closeSilently(channel);
                closeSilently(fileInputStream);
                closeSilently(fileChannel3);
                closeSilently(parcelFileDescriptor);
                throw th;
            }
        } catch (Exception e5) {
            exc = e5;
            fileInputStream = null;
            fileChannel2 = null;
        } catch (Throwable th7) {
            th = th7;
            fileInputStream = null;
            fileChannel = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean copyEncryptFile(String str, ParcelFileDescriptor parcelFileDescriptor, String str2) throws Throwable {
        OutputStream outputStream;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        File file = new File(str);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        OutputStream outputStreamEncryptStream = null;
        try {
            fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                fileOutputStream = new FileOutputStream(str);
                try {
                    outputStreamEncryptStream = encryptStream(fileOutputStream, str2);
                } catch (Exception e) {
                    e = e;
                    outputStream = outputStreamEncryptStream;
                    outputStreamEncryptStream = fileInputStream;
                    try {
                        e.printStackTrace();
                        closeSilently(outputStreamEncryptStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStream);
                        closeSilently(parcelFileDescriptor);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        closeSilently(outputStreamEncryptStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStream);
                        closeSilently(parcelFileDescriptor);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = outputStreamEncryptStream;
                    outputStreamEncryptStream = fileInputStream;
                    closeSilently(outputStreamEncryptStream);
                    closeSilently(fileOutputStream);
                    closeSilently(outputStream);
                    closeSilently(parcelFileDescriptor);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                outputStream = null;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                outputStream = null;
                fileOutputStream = null;
            }
        } catch (Exception e3) {
            e = e3;
            outputStream = null;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            outputStream = null;
            fileOutputStream = null;
        }
        if (outputStreamEncryptStream == null) {
            closeSilently(fileInputStream);
            closeSilently(fileOutputStream);
            closeSilently(outputStreamEncryptStream);
            closeSilently(parcelFileDescriptor);
            return false;
        }
        byte[] bArr = new byte[1024];
        while (true) {
            int i = fileInputStream.read(bArr, 0, 1024);
            if (i == -1) {
                closeSilently(fileInputStream);
                closeSilently(fileOutputStream);
                closeSilently(outputStreamEncryptStream);
                closeSilently(parcelFileDescriptor);
                return true;
            }
            outputStreamEncryptStream.write(bArr, 0, i);
        }
    }

    public static boolean copyFile(String str, String str2) throws Throwable {
        Throwable th;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileChannel channel;
        Exception exc;
        File file = new File(str2);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileChannel fileChannel = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
                try {
                    FileChannel channel2 = fileInputStream.getChannel();
                    try {
                        channel = fileOutputStream.getChannel();
                        try {
                            channel2.transferTo(0L, channel2.size(), channel);
                            closeSilently(channel2);
                            closeSilently(channel);
                            closeSilently(fileInputStream);
                            closeSilently(fileOutputStream);
                            return true;
                        } catch (Exception e) {
                            exc = e;
                            fileChannel = channel2;
                            try {
                                exc.printStackTrace();
                                closeSilently(fileChannel);
                                closeSilently(channel);
                                closeSilently(fileInputStream);
                                closeSilently(fileOutputStream);
                                return false;
                            } catch (Throwable th2) {
                                th = th2;
                                closeSilently(fileChannel);
                                closeSilently(channel);
                                closeSilently(fileInputStream);
                                closeSilently(fileOutputStream);
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            fileChannel = channel2;
                            closeSilently(fileChannel);
                            closeSilently(channel);
                            closeSilently(fileInputStream);
                            closeSilently(fileOutputStream);
                            throw th;
                        }
                    } catch (Exception e2) {
                        exc = e2;
                        channel = null;
                    } catch (Throwable th4) {
                        th = th4;
                        channel = null;
                    }
                } catch (Exception e3) {
                    exc = e3;
                    channel = null;
                } catch (Throwable th5) {
                    th = th5;
                    channel = null;
                }
            } catch (Exception e4) {
                exc = e4;
                fileOutputStream = null;
                channel = null;
            } catch (Throwable th6) {
                th = th6;
                fileOutputStream = null;
                channel = null;
            }
        } catch (Exception e5) {
            exc = e5;
            fileOutputStream = null;
            fileInputStream = null;
            channel = null;
        } catch (Throwable th7) {
            th = th7;
            fileOutputStream = null;
            fileInputStream = null;
            channel = null;
        }
    }

    public static boolean copyFile(File file, File file2) throws Throwable {
        Throwable th;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileChannel channel;
        Exception exc;
        FileChannel channel2;
        FileChannel fileChannel = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    channel2 = fileInputStream.getChannel();
                    try {
                        channel = fileOutputStream.getChannel();
                    } catch (Exception e) {
                        exc = e;
                        channel = null;
                    } catch (Throwable th2) {
                        th = th2;
                        channel = null;
                    }
                } catch (Exception e2) {
                    exc = e2;
                    channel = null;
                } catch (Throwable th3) {
                    th = th3;
                    channel = null;
                }
            } catch (Exception e3) {
                exc = e3;
                fileOutputStream = null;
                channel = null;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                channel = null;
            }
            try {
                channel2.transferTo(0L, channel2.size(), channel);
                closeSilently(channel2);
                closeSilently(channel);
                closeSilently(fileInputStream);
                closeSilently(fileOutputStream);
                return true;
            } catch (Exception e4) {
                exc = e4;
                fileChannel = channel2;
                try {
                    exc.printStackTrace();
                    closeSilently(fileChannel);
                    closeSilently(channel);
                    closeSilently(fileInputStream);
                    closeSilently(fileOutputStream);
                    return false;
                } catch (Throwable th5) {
                    th = th5;
                    closeSilently(fileChannel);
                    closeSilently(channel);
                    closeSilently(fileInputStream);
                    closeSilently(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                fileChannel = channel2;
                closeSilently(fileChannel);
                closeSilently(channel);
                closeSilently(fileInputStream);
                closeSilently(fileOutputStream);
                throw th;
            }
        } catch (Exception e5) {
            exc = e5;
            fileOutputStream = null;
            fileInputStream = null;
            channel = null;
        } catch (Throwable th7) {
            th = th7;
            fileOutputStream = null;
            fileInputStream = null;
            channel = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean copyEncryptFile(String str, String str2, String str3) throws Throwable {
        FileOutputStream fileOutputStream;
        OutputStream outputStream;
        Slog.d(TAG, "copyEncryptFile sourceImagePath = " + str + " destImagePath = " + str2);
        File file = new File(str2);
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        OutputStream outputStreamEncryptStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
                try {
                    outputStreamEncryptStream = encryptStream(fileOutputStream, str3);
                    if (outputStreamEncryptStream == null) {
                        closeSilently(fileInputStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStreamEncryptStream);
                        return false;
                    }
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = fileInputStream.read(bArr, 0, 1024);
                        if (i == -1) {
                            closeSilently(fileInputStream);
                            closeSilently(fileOutputStream);
                            closeSilently(outputStreamEncryptStream);
                            return true;
                        }
                        outputStreamEncryptStream.write(bArr, 0, i);
                    }
                } catch (Exception e) {
                    e = e;
                    outputStream = outputStreamEncryptStream;
                    outputStreamEncryptStream = fileInputStream;
                    try {
                        e.printStackTrace();
                        closeSilently(outputStreamEncryptStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        closeSilently(outputStreamEncryptStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = outputStreamEncryptStream;
                    outputStreamEncryptStream = fileInputStream;
                    closeSilently(outputStreamEncryptStream);
                    closeSilently(fileOutputStream);
                    closeSilently(outputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
                outputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                outputStream = null;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
            outputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            outputStream = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean copyEncryptFile(File file, File file2, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        OutputStream outputStream;
        OutputStream outputStreamEncryptStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    outputStreamEncryptStream = encryptStream(fileOutputStream, str);
                    if (outputStreamEncryptStream == null) {
                        closeSilently(fileInputStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStreamEncryptStream);
                        return false;
                    }
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = fileInputStream.read(bArr, 0, 1024);
                        if (i == -1) {
                            closeSilently(fileInputStream);
                            closeSilently(fileOutputStream);
                            closeSilently(outputStreamEncryptStream);
                            return true;
                        }
                        outputStreamEncryptStream.write(bArr, 0, i);
                    }
                } catch (Exception e) {
                    e = e;
                    outputStream = outputStreamEncryptStream;
                    outputStreamEncryptStream = fileInputStream;
                    try {
                        e.printStackTrace();
                        closeSilently(outputStreamEncryptStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        closeSilently(outputStreamEncryptStream);
                        closeSilently(fileOutputStream);
                        closeSilently(outputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = outputStreamEncryptStream;
                    outputStreamEncryptStream = fileInputStream;
                    closeSilently(outputStreamEncryptStream);
                    closeSilently(fileOutputStream);
                    closeSilently(outputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
                outputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                outputStream = null;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
            outputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            outputStream = null;
        }
    }

    public static OutputStream encryptStream(OutputStream outputStream, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (str.isEmpty()) {
            Slog.d(TAG, "sessionKey is empty");
            return outputStream;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bArr = new byte[cipher.getBlockSize()];
            new SecureRandom().nextBytes(bArr);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
            outputStream.write(bArr);
            byte[] bArrGenerateEncryptSalt = generateEncryptSalt();
            outputStream.write(bArrGenerateEncryptSalt);
            SecretKeySpec secretKeySpecGeneratePBKDF2SecretKey = generatePBKDF2SecretKey(bArrGenerateEncryptSalt, str);
            if (secretKeySpecGeneratePBKDF2SecretKey == null) {
                return null;
            }
            cipher.init(1, secretKeySpecGeneratePBKDF2SecretKey, ivParameterSpec);
            return new CipherOutputStream(outputStream, cipher);
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

    public static InputStream decryptStream(InputStream inputStream, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bArr = new byte[cipher.getBlockSize()];
            inputStream.read(bArr);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
            byte[] bArr2 = new byte[16];
            inputStream.read(bArr2);
            SecretKeySpec secretKeySpecGeneratePBKDF2SecretKey = generatePBKDF2SecretKey(bArr2, str);
            if (secretKeySpecGeneratePBKDF2SecretKey == null) {
                return null;
            }
            cipher.init(2, secretKeySpecGeneratePBKDF2SecretKey, ivParameterSpec);
            return new CipherInputStream(inputStream, cipher);
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
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public static SecretKeySpec generatePBKDF2SecretKey(byte[] bArr, String str) {
        try {
            return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(str.toCharArray(), bArr, 1000, 256)).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void closeSilently(Closeable closeable) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            Slog.w(TAG, "close fail ", e);
        }
    }

    public static boolean isExist(String str) {
        return new File(str).exists();
    }

    public static Bitmap getBitmapFromPath(String str, int i, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (i == 0) {
            return BitmapFactory.decodeFile(str);
        }
        if (i != 1) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            InputStream inputStreamDecryptStream = decryptStream(fileInputStream, str2);
            bitmapDecodeStream = inputStreamDecryptStream != null ? BitmapFactory.decodeStream(inputStreamDecryptStream) : null;
            closeSilently(fileInputStream);
            closeSilently(inputStreamDecryptStream);
            return bitmapDecodeStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return bitmapDecodeStream;
        }
    }

    public static InputStream getInputStreamFromPath(String str, int i, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            if (i != 1) {
                return fileInputStream;
            }
            InputStream inputStreamDecryptStream = decryptStream(fileInputStream, str2);
            closeSilently(fileInputStream);
            return inputStreamDecryptStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
