package com.android.server.multicontrol;

import android.os.Process;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class Log {
  public static DateTimeFormatter sFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
  public static int CAPACITY_LOG = 200;
  public static EvictingArrayQueue sSavedLogs = new EvictingArrayQueue(CAPACITY_LOG);
  public static int CAPACITY_STATE = 100;
  public static EvictingArrayQueue sSavedStates = new EvictingArrayQueue(CAPACITY_STATE);

  public static void dump(PrintWriter printWriter) {
    printWriter.println(
        buildLogString(
            'V',
            "MultiControl@StateStart",
            "=========================================================================="));
    sSavedStates.dump(printWriter);
    printWriter.println(
        buildLogString(
            'V',
            "MultiControl@StateEnd",
            "=========================================================================="));
    printWriter.println(
        buildLogString(
            'V',
            "MultiControl@SavedLogsStart",
            "=========================================================================="));
    sSavedLogs.dump(printWriter);
    printWriter.println(
        buildLogString(
            'V',
            "MultiControl@SavedLogsEnd",
            "=========================================================================="));
  }

  /* renamed from: d */
  public static int m47d(String str, String str2) {
    save('D', str, str2);
    return android.util.Log.d(str, str2);
  }

  /* renamed from: e */
  public static int m48e(String str, String str2) {
    save('E', str, str2);
    return android.util.Log.e(str, str2);
  }

  /* renamed from: e */
  public static int m49e(String str, String str2, Throwable th) {
    save('E', str, str2);
    return android.util.Log.e(str, str2, th);
  }

  /* renamed from: i */
  public static int m50i(String str, String str2) {
    save('I', str, str2);
    return android.util.Log.i(str, str2);
  }

  public static void save(char c, String str, String str2) {
    sSavedLogs.add(buildLogString(c, str, str2));
  }

  public static String buildLogString(char c, String str, String str2) {
    return String.format(
        null,
        "%s %5d %5d %5d %c %s: %s",
        LocalDateTime.now().format(sFormatter),
        Integer.valueOf(Process.myUid()),
        Integer.valueOf(Process.myPid()),
        Integer.valueOf(Process.myTid()),
        Character.valueOf(c),
        str,
        str2);
  }

  public class EvictingArrayQueue {
    public final int mCapacity;
    public final Object mLock = new Object();
    public int mPointer = 0;
    public final ArrayList mElements = new ArrayList();

    public EvictingArrayQueue(int i) {
      this.mCapacity = i;
    }

    public void add(Object obj) {
      synchronized (this.mLock) {
        this.mPointer = (this.mPointer + 1) % this.mCapacity;
        if (this.mElements.size() == this.mCapacity) {
          this.mElements.set(this.mPointer, obj);
        } else {
          this.mElements.add(obj);
        }
      }
    }

    public void dump(PrintWriter printWriter) {
      synchronized (this.mLock) {
        int size = this.mElements.size();
        boolean z = size == this.mCapacity;
        for (int i = 0; i < size; i++) {
          printWriter.println(
              this.mElements.get(z ? ((this.mPointer + i) + 1) % this.mCapacity : i));
        }
      }
    }
  }
}
