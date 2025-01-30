package com.android.server.knox.dar.sdp;

import android.os.FileUtils;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public final class SDPLogFile {
  public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
  public static final Lock FILE_LOCK = new ReentrantLock();

  public static void saveFile(Queue queue) {
    LogI("Saving logs... [QS : " + queue.size() + "]");
    LogD("Target path : /data/log/sdp_log");
    FILE_LOCK.lock();
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile("/data/log/sdp_log", "rwd");
      try {
        checkAndReset(randomAccessFile);
        randomAccessFile.seek(0L);
        long readLong = randomAccessFile.readLong();
        randomAccessFile.seek(readLong);
        while (!queue.isEmpty()) {
          String str = (String) queue.poll();
          if (str != null) {
            byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
            if (readLong + bytes.length + 1 > 2097152) {
              randomAccessFile.seek(17L);
            }
            randomAccessFile.write(bytes);
            randomAccessFile.write(10);
            readLong = randomAccessFile.getFilePointer();
          }
        }
        randomAccessFile.seek(0L);
        randomAccessFile.writeLong(readLong);
        setPermission();
        LogI(
            String.format(
                Locale.US,
                "Saving success! [FP : %d, FS : %d]",
                Long.valueOf(readLong),
                Long.valueOf(randomAccessFile.length())));
        randomAccessFile.close();
      } finally {
      }
    } catch (Exception e) {
      LogE("Failed to save logs : " + e.toString());
      e.printStackTrace();
    }
    FILE_LOCK.unlock();
  }

  public static void setPermission() {
    LogI(
        "Set permission : "
            + FileUtils.setPermissions(
                "/data/log/sdp_log", FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1007));
  }

  public static void checkAndReset(RandomAccessFile randomAccessFile) {
    byte[] bArr;
    try {
      check(randomAccessFile);
      bArr = null;
    } catch (SecurityException e) {
      byte[] bytes = SDPLogUtil.makeDebugMessage(e.getMessage()).getBytes(Charset.forName("UTF-8"));
      LogD("Reset reason : " + e.getMessage());
      bArr = bytes;
    }
    if (bArr != null) {
      long length = bArr.length + 17 + 1;
      randomAccessFile.seek(0L);
      randomAccessFile.writeLong(length);
      randomAccessFile.writeLong(2L);
      randomAccessFile.write(10);
      randomAccessFile.write(bArr);
      randomAccessFile.write(10);
      randomAccessFile.setLength(length);
    }
  }

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
      LogD("Header Check : Passed!");
    } catch (IOException e) {
      throw new SecurityException("Unexpected error", e);
    }
  }

  public static void dump(PrintWriter printWriter) {
    if (printWriter == null) {
      LogE("Failed to dump: Invalid writer...");
      return;
    }
    if (!FILE_LOCK.tryLock()) {
      LogE("Failed to dump: Maybe target file is already being used...");
      printWriter.println("Target file busy");
      return;
    }
    String str = null;
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile("/data/log/sdp_log", "r");
      try {
        try {
          check(randomAccessFile);
          randomAccessFile.seek(17L);
          int i = 0;
          while (true) {
            String readLine = randomAccessFile.readLine();
            if (readLine == null) {
              break;
            }
            int i2 = i + 1;
            if (i >= 6000) {
              str = "Dump line count reached to the limit: 6000";
              break;
            } else {
              printWriter.println(readLine);
              i = i2;
            }
          }
          randomAccessFile.close();
        } catch (SecurityException e) {
          String str2 = "Failed to dump: " + e.getMessage();
          LogE(str2);
          throw new IOException(str2);
        }
      } finally {
      }
    } catch (IOException e2) {
      e2.printStackTrace();
    }
    FILE_LOCK.unlock();
    if (str != null) {
      printWriter.println(str);
    }
    printWriter.println();
  }

  public static void LogD(String str) {
    if (!DEBUG || str == null) {
      return;
    }
    Log.d("SDPLogFile", str);
  }

  public static void LogI(String str) {
    if (str != null) {
      Log.i("SDPLogFile", str);
    }
  }

  public static void LogE(String str) {
    if (str != null) {
      Log.e("SDPLogFile", str);
    }
  }
}
