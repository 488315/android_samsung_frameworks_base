package com.android.internal.os;

import android.content.ComponentName;
import android.content.Intent;
import android.os.SystemClock;
import com.android.internal.os.anr.AnrLatencyTracker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class TimeoutRecord {
  public final boolean mEndTakenBeforeLocks;
  public final long mEndUptimeMillis;
  public final int mKind;
  public final AnrLatencyTracker mLatencyTracker;
  public final String mReason;

  @Retention(RetentionPolicy.SOURCE)
  public @interface TimeoutKind {
    public static final int APP_REGISTERED = 7;
    public static final int BROADCAST_RECEIVER = 3;
    public static final int CONTENT_PROVIDER = 6;
    public static final int INPUT_DISPATCH_NO_FOCUSED_WINDOW = 1;
    public static final int INPUT_DISPATCH_WINDOW_UNRESPONSIVE = 2;
    public static final int JOB_SERVICE = 9;
    public static final int SERVICE_EXEC = 5;
    public static final int SERVICE_START = 4;
    public static final int SHORT_FGS_TIMEOUT = 8;
  }

  private TimeoutRecord(
      int kind, String reason, long endUptimeMillis, boolean endTakenBeforeLocks) {
    this.mKind = kind;
    this.mReason = reason;
    this.mEndUptimeMillis = endUptimeMillis;
    this.mEndTakenBeforeLocks = endTakenBeforeLocks;
    this.mLatencyTracker = new AnrLatencyTracker(kind, endUptimeMillis);
  }

  private static TimeoutRecord endingNow(int kind, String reason) {
    long endUptimeMillis = SystemClock.uptimeMillis();
    return new TimeoutRecord(kind, reason, endUptimeMillis, true);
  }

  private static TimeoutRecord endingApproximatelyNow(int kind, String reason) {
    long endUptimeMillis = SystemClock.uptimeMillis();
    return new TimeoutRecord(kind, reason, endUptimeMillis, false);
  }

  public static TimeoutRecord forBroadcastReceiver(
      Intent intent, String packageName, String className) {
    Intent logIntent;
    if (packageName != null) {
      if (className != null) {
        logIntent = new Intent(intent);
        logIntent.setComponent(new ComponentName(packageName, className));
      } else {
        logIntent = new Intent(intent);
        logIntent.setPackage(packageName);
      }
    } else {
      logIntent = intent;
    }
    return forBroadcastReceiver(logIntent);
  }

  public static TimeoutRecord forBroadcastReceiver(Intent intent) {
    StringBuilder reason = new StringBuilder("Broadcast of ");
    intent.toString(reason);
    return endingNow(3, reason.toString());
  }

  public static TimeoutRecord forBroadcastReceiver(Intent intent, long timeoutDurationMs) {
    StringBuilder reason = new StringBuilder("Broadcast of ");
    intent.toString(reason);
    reason.append(", waited ");
    reason.append(timeoutDurationMs);
    reason.append("ms");
    return endingNow(3, reason.toString());
  }

  public static TimeoutRecord forInputDispatchNoFocusedWindow(String reason) {
    return endingNow(1, reason);
  }

  public static TimeoutRecord forInputDispatchWindowUnresponsive(String reason) {
    return endingNow(2, reason);
  }

  public static TimeoutRecord forServiceExec(String reason) {
    return endingNow(5, reason);
  }

  public static TimeoutRecord forServiceStartWithEndTime(String reason, long endUptimeMillis) {
    return new TimeoutRecord(4, reason, endUptimeMillis, true);
  }

  public static TimeoutRecord forContentProvider(String reason) {
    return endingApproximatelyNow(6, reason);
  }

  public static TimeoutRecord forApp(String reason) {
    return endingApproximatelyNow(7, reason);
  }

  public static TimeoutRecord forShortFgsTimeout(String reason) {
    return endingNow(8, reason);
  }

  public static TimeoutRecord forJobService(String reason) {
    return endingNow(9, reason);
  }
}
