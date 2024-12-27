package com.android.server.knox.dar.sdp;

import android.os.SystemProperties;
import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SDPLogFile {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final Lock FILE_LOCK = new ReentrantLock();

    public static void check(RandomAccessFile randomAccessFile) {
        try {
            randomAccessFile.seek(0L);
            long length = randomAccessFile.length();
            if (length == 0) {
                throw new SecurityException("File created from scratch");
            }
            if (length <= 17) {
                throw new SecurityException("Broken file header");
            }
            if (length > 2097152) {
                throw new SecurityException("File size exceeded");
            }
            if (randomAccessFile.readLong() > 2097152) {
                throw new SecurityException("File corrupted");
            }
            if (randomAccessFile.readLong() != 2) {
                throw new SecurityException("Version mismatched");
            }
            if (DEBUG) {
                Log.d("SDPLogFile", "Header Check : Passed!");
            }
        } catch (IOException e) {
            throw new SecurityException("Unexpected error", e);
        }
    }

    public static void checkAndReset(RandomAccessFile randomAccessFile) {
        byte[] bArr;
        try {
            check(randomAccessFile);
            bArr = null;
        } catch (SecurityException e) {
            byte[] bytes =
                    SDPLogUtil.makeDebugMessage(e.getMessage()).getBytes(Charset.forName("UTF-8"));
            String str = "Reset reason : " + e.getMessage();
            if (DEBUG && str != null) {
                Log.d("SDPLogFile", str);
            }
            bArr = bytes;
        }
        if (bArr != null) {
            long length = bArr.length + 18;
            randomAccessFile.seek(0L);
            randomAccessFile.writeLong(length);
            randomAccessFile.writeLong(2L);
            randomAccessFile.write(10);
            randomAccessFile.write(bArr);
            randomAccessFile.write(10);
            randomAccessFile.setLength(length);
        }
    }
}
