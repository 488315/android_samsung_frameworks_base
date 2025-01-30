package com.android.server.backup.utils;

import android.util.Slog;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes.dex */
public abstract class RandomAccessFileUtils {
  public static RandomAccessFile getRandomAccessFile(File file) {
    return new RandomAccessFile(file, "rwd");
  }

  public static void writeBoolean(File file, boolean z) {
    try {
      RandomAccessFile randomAccessFile = getRandomAccessFile(file);
      try {
        randomAccessFile.writeBoolean(z);
        randomAccessFile.close();
      } finally {
      }
    } catch (IOException e) {
      Slog.w("BackupManagerService", "Error writing file:" + file.getAbsolutePath(), e);
    }
  }
}
