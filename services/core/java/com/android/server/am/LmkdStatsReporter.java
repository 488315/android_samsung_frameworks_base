package com.android.server.am;

import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class LmkdStatsReporter {
  public static int mapKillReason(int i) {
    switch (i) {
      case 0:
        return 1;
      case 1:
        return 2;
      case 2:
        return 3;
      case 3:
        return 4;
      case 4:
        return 5;
      case 5:
        return 6;
      case 6:
        return 7;
      case 7:
        return 8;
      default:
        return 0;
    }
  }

  public static void logKillOccurred(DataInputStream dataInputStream, int i, int i2) {
    try {
      long readLong = dataInputStream.readLong();
      long readLong2 = dataInputStream.readLong();
      long readLong3 = dataInputStream.readLong();
      long readLong4 = dataInputStream.readLong();
      long readLong5 = dataInputStream.readLong();
      long readLong6 = dataInputStream.readLong();
      int readInt = dataInputStream.readInt();
      int readInt2 = dataInputStream.readInt();
      int readInt3 = dataInputStream.readInt();
      int readInt4 = dataInputStream.readInt();
      int readInt5 = dataInputStream.readInt();
      int readInt6 = dataInputStream.readInt();
      FrameworkStatsLog.write(
          51,
          readInt,
          dataInputStream.readUTF(),
          readInt2,
          readLong,
          readLong2,
          readLong3,
          readLong4,
          readLong5,
          readLong6,
          readInt3,
          readInt4,
          readInt5,
          mapKillReason(readInt6),
          dataInputStream.readInt(),
          dataInputStream.readInt(),
          i,
          i2);
    } catch (IOException unused) {
      Slog.e("ActivityManager", "Invalid buffer data. Failed to log LMK_KILL_OCCURRED");
    }
  }

  public static void logStateChanged(int i) {
    FrameworkStatsLog.write(54, i);
  }
}
