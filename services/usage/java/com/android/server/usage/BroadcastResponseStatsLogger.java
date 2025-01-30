package com.android.server.usage;

import android.app.ActivityManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.RingBuffer;
import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;

/* loaded from: classes3.dex */
public class BroadcastResponseStatsLogger {
  public static final int MAX_LOG_SIZE;
  public final LogBuffer mBroadcastEventsBuffer;
  public final Object mLock = new Object();
  public final LogBuffer mNotificationEventsBuffer;

  public abstract class BroadcastEvent implements Data {}

  public interface Data {}

  public abstract class NotificationEvent implements Data {}

  public BroadcastResponseStatsLogger() {
    int i = MAX_LOG_SIZE;
    this.mBroadcastEventsBuffer = new LogBuffer(BroadcastEvent.class, i);
    this.mNotificationEventsBuffer = new LogBuffer(NotificationEvent.class, i);
  }

  static {
    MAX_LOG_SIZE = ActivityManager.isLowRamDeviceStatic() ? 20 : 50;
  }

  public void logBroadcastDispatchEvent(
      int i, String str, UserHandle userHandle, long j, long j2, int i2) {
    synchronized (this.mLock) {
      if (UsageStatsService.DEBUG_RESPONSE_STATS) {
        Slog.d(
            "ResponseStatsTracker",
            getBroadcastDispatchEventLog(i, str, userHandle.getIdentifier(), j, j2, i2));
      }
      this.mBroadcastEventsBuffer.logBroadcastDispatchEvent(i, str, userHandle, j, j2, i2);
    }
  }

  public void logNotificationEvent(int i, String str, UserHandle userHandle, long j) {
    synchronized (this.mLock) {
      if (UsageStatsService.DEBUG_RESPONSE_STATS) {
        Slog.d(
            "ResponseStatsTracker", getNotificationEventLog(i, str, userHandle.getIdentifier(), j));
      }
      this.mNotificationEventsBuffer.logNotificationEvent(i, str, userHandle, j);
    }
  }

  public void dumpLogs(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      indentingPrintWriter.println("Broadcast events (most recent first):");
      indentingPrintWriter.increaseIndent();
      this.mBroadcastEventsBuffer.reverseDump(indentingPrintWriter);
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.println();
      indentingPrintWriter.println("Notification events (most recent first):");
      indentingPrintWriter.increaseIndent();
      this.mNotificationEventsBuffer.reverseDump(indentingPrintWriter);
      indentingPrintWriter.decreaseIndent();
    }
  }

  public final class LogBuffer extends RingBuffer {
    public LogBuffer(Class cls, int i) {
      super(cls, i);
    }

    public void logBroadcastDispatchEvent(
        int i, String str, UserHandle userHandle, long j, long j2, int i2) {
      CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(getNextSlot());
    }

    public void logNotificationEvent(int i, String str, UserHandle userHandle, long j) {
      CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(getNextSlot());
    }

    public void reverseDump(IndentingPrintWriter indentingPrintWriter) {
      Data[] dataArr = (Data[]) toArray();
      for (int length = dataArr.length - 1; length >= 0; length--) {
        Data data = dataArr[length];
      }
    }
  }

  public static String getBroadcastDispatchEventLog(
      int i, String str, int i2, long j, long j2, int i3) {
    return TextUtils.formatSimple(
        "broadcast:%s; srcUid=%d, tgtPkg=%s, tgtUsr=%d, id=%d, state=%s",
        new Object[] {
          TimeUtils.formatDuration(j2),
          Integer.valueOf(i),
          str,
          Integer.valueOf(i2),
          Long.valueOf(j),
          ActivityManager.procStateToString(i3)
        });
  }

  public static String getNotificationEventLog(int i, String str, int i2, long j) {
    return TextUtils.formatSimple(
        "notification:%s; event=<%s>, pkg=%s, usr=%d",
        new Object[] {
          TimeUtils.formatDuration(j), notificationEventToString(i), str, Integer.valueOf(i2)
        });
  }

  public static String notificationEventToString(int i) {
    return i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "cancelled" : "updated" : "posted";
  }
}
