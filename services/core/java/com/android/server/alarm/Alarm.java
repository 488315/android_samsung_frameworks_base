package com.android.server.alarm;

import android.app.AlarmManager;
import android.app.IAlarmListener;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.WorkSource;
import android.util.IndentingPrintWriter;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/* loaded from: classes.dex */
public class Alarm {
  public static final int NUM_POLICIES = 6;
  public final AlarmManager.AlarmClockInfo alarmClock;
  public int count;
  public final int creatorUid;
  public final int flags;
  public final IAlarmListener listener;
  public final String listenerTag;
  public int mExactAllowReason;
  public Bundle mIdleOptions;
  public long mMaxWhenElapsed;
  public long[] mPolicyWhenElapsed;
  public boolean mUsingReserveQuota;
  public long mWhenElapsed;
  public final PendingIntent operation;
  public int origType;
  public final long origWhen;
  public final String packageName;
  public AlarmManagerService.PriorityClass priorityClass;
  public final long repeatInterval;
  public final String sourcePackage;
  public final String statsTag;
  public final int type;
  public final int uid;
  public final boolean wakeup;
  public final long windowLength;
  public final WorkSource workSource;

  public static String exactReasonToString(int i) {
    switch (i) {
      case -1:
        return "N/A";
      case 0:
        return "permission";
      case 1:
        return "allow-listed";
      case 2:
        return "compat";
      case 3:
        return "policy_permission";
      case 4:
        return "listener";
      case 5:
        return "prioritized";
      default:
        return "--unknown--";
    }
  }

  public static String typeToString(int i) {
    return i != 0
        ? i != 1 ? i != 2 ? i != 3 ? "--unknown--" : "ELAPSED" : "ELAPSED_WAKEUP" : "RTC"
        : "RTC_WAKEUP";
  }

  public Alarm(
      int i,
      long j,
      long j2,
      long j3,
      long j4,
      PendingIntent pendingIntent,
      IAlarmListener iAlarmListener,
      String str,
      WorkSource workSource,
      int i2,
      AlarmManager.AlarmClockInfo alarmClockInfo,
      int i3,
      String str2,
      Bundle bundle,
      int i4) {
    this.type = i;
    this.origType = i;
    this.origWhen = j;
    this.wakeup = i == 2 || i == 0;
    long[] jArr = new long[6];
    this.mPolicyWhenElapsed = jArr;
    jArr[0] = j2;
    this.mWhenElapsed = j2;
    this.windowLength = j3;
    this.mMaxWhenElapsed = AlarmManagerService.clampPositive(j2 + j3);
    this.repeatInterval = j4;
    this.operation = pendingIntent;
    this.listener = iAlarmListener;
    this.listenerTag = str;
    this.statsTag = makeTag(pendingIntent, str, i);
    this.workSource = workSource;
    this.flags = i2;
    this.alarmClock = alarmClockInfo;
    int i5 = i3;
    this.uid = i5;
    String str3 = str2;
    this.packageName = str3;
    this.mIdleOptions = bundle;
    this.mExactAllowReason = i4;
    this.sourcePackage = pendingIntent != null ? pendingIntent.getCreatorPackage() : str3;
    this.creatorUid = pendingIntent != null ? pendingIntent.getCreatorUid() : i5;
    this.mUsingReserveQuota = false;
  }

  public static String makeTag(PendingIntent pendingIntent, String str, int i) {
    String str2 = (i == 2 || i == 0) ? "*walarm*:" : "*alarm*:";
    if (pendingIntent != null) {
      return pendingIntent.getTag(str2);
    }
    return str2 + str;
  }

  public boolean matches(PendingIntent pendingIntent, IAlarmListener iAlarmListener) {
    PendingIntent pendingIntent2 = this.operation;
    if (pendingIntent2 != null) {
      return pendingIntent2.equals(pendingIntent);
    }
    return iAlarmListener != null && this.listener.asBinder().equals(iAlarmListener.asBinder());
  }

  public boolean matches(String str) {
    return str.equals(this.sourcePackage);
  }

  public long getPolicyElapsed(int i) {
    return this.mPolicyWhenElapsed[i];
  }

  public long getRequestedElapsed() {
    return this.mPolicyWhenElapsed[0];
  }

  public long getWhenElapsed() {
    return this.mWhenElapsed;
  }

  public long getMaxWhenElapsed() {
    return this.mMaxWhenElapsed;
  }

  public boolean setPolicyElapsed(int i, long j) {
    this.mPolicyWhenElapsed[i] = j;
    return updateWhenElapsed();
  }

  public final boolean updateWhenElapsed() {
    long j = this.mWhenElapsed;
    this.mWhenElapsed = 0L;
    for (int i = 0; i < 6; i++) {
      this.mWhenElapsed = Math.max(this.mWhenElapsed, this.mPolicyWhenElapsed[i]);
    }
    long j2 = this.mMaxWhenElapsed;
    long max =
        Math.max(
            AlarmManagerService.clampPositive(this.mPolicyWhenElapsed[0] + this.windowLength),
            this.mWhenElapsed);
    this.mMaxWhenElapsed = max;
    return (j == this.mWhenElapsed && j2 == max) ? false : true;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(128);
    sb.append("Alarm{");
    sb.append(Integer.toHexString(System.identityHashCode(this)));
    sb.append(" type ");
    sb.append(this.type);
    if (this.type != this.origType) {
      sb.append(" origType ");
      sb.append(this.origType);
    }
    sb.append(" origWhen ");
    sb.append(this.origWhen);
    sb.append(" whenElapsed ");
    sb.append(getWhenElapsed());
    sb.append(" ");
    sb.append(this.sourcePackage);
    sb.append('}');
    return sb.toString();
  }

  public static String policyIndexToString(int i) {
    if (i == 0) {
      return "requester";
    }
    if (i == 1) {
      return "app_standby";
    }
    if (i == 2) {
      return "device_idle";
    }
    if (i == 3) {
      return "battery_saver";
    }
    if (i == 4) {
      return "tare";
    }
    if (i == 5) {
      return "gms_manager";
    }
    return "--unknown(" + i + ")--";
  }

  public void dump(
      IndentingPrintWriter indentingPrintWriter, long j, SimpleDateFormat simpleDateFormat) {
    int i = this.type;
    boolean z = true;
    if (i != 1 && i != 0) {
      z = false;
    }
    indentingPrintWriter.print("tag=");
    indentingPrintWriter.println(this.statsTag);
    indentingPrintWriter.print("type=");
    indentingPrintWriter.print(typeToString(this.type));
    if (this.type != this.origType) {
      indentingPrintWriter.print(" origType=");
      indentingPrintWriter.print(typeToString(this.origType));
    }
    indentingPrintWriter.print(" origWhen=");
    if (z) {
      indentingPrintWriter.print(simpleDateFormat.format(new Date(this.origWhen)));
    } else {
      TimeUtils.formatDuration(this.origWhen, j, indentingPrintWriter);
    }
    indentingPrintWriter.print(" window=");
    TimeUtils.formatDuration(this.windowLength, indentingPrintWriter);
    if (this.mExactAllowReason != -1) {
      indentingPrintWriter.print(" exactAllowReason=");
      indentingPrintWriter.print(exactReasonToString(this.mExactAllowReason));
    }
    indentingPrintWriter.print(" repeatInterval=");
    indentingPrintWriter.print(this.repeatInterval);
    indentingPrintWriter.print(" count=");
    indentingPrintWriter.print(this.count);
    indentingPrintWriter.print(" flags=0x");
    indentingPrintWriter.println(Integer.toHexString(this.flags));
    indentingPrintWriter.print("policyWhenElapsed:");
    for (int i2 = 0; i2 < 6; i2++) {
      indentingPrintWriter.print(" " + policyIndexToString(i2) + "=");
      TimeUtils.formatDuration(this.mPolicyWhenElapsed[i2], j, indentingPrintWriter);
    }
    indentingPrintWriter.println();
    indentingPrintWriter.print("whenElapsed=");
    TimeUtils.formatDuration(getWhenElapsed(), j, indentingPrintWriter);
    indentingPrintWriter.print(" maxWhenElapsed=");
    TimeUtils.formatDuration(this.mMaxWhenElapsed, j, indentingPrintWriter);
    if (this.mUsingReserveQuota) {
      indentingPrintWriter.print(" usingReserveQuota=true");
    }
    indentingPrintWriter.println();
    if (this.alarmClock != null) {
      indentingPrintWriter.println("Alarm clock:");
      indentingPrintWriter.print("  triggerTime=");
      indentingPrintWriter.println(
          simpleDateFormat.format(new Date(this.alarmClock.getTriggerTime())));
      indentingPrintWriter.print("  showIntent=");
      indentingPrintWriter.println(this.alarmClock.getShowIntent());
    }
    if (this.operation != null) {
      indentingPrintWriter.print("operation=");
      indentingPrintWriter.println(this.operation);
    }
    if (this.listener != null) {
      indentingPrintWriter.print("listener=");
      indentingPrintWriter.println(this.listener.asBinder());
    }
    if (this.mIdleOptions != null) {
      indentingPrintWriter.print("idle-options=");
      indentingPrintWriter.println(this.mIdleOptions.toString());
    }
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j, long j2) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1138166333441L, this.statsTag);
    protoOutputStream.write(1159641169922L, this.type);
    protoOutputStream.write(1112396529667L, getWhenElapsed() - j2);
    protoOutputStream.write(1112396529668L, this.windowLength);
    protoOutputStream.write(1112396529669L, this.repeatInterval);
    protoOutputStream.write(1120986464262L, this.count);
    protoOutputStream.write(1120986464263L, this.flags);
    AlarmManager.AlarmClockInfo alarmClockInfo = this.alarmClock;
    if (alarmClockInfo != null) {
      alarmClockInfo.dumpDebug(protoOutputStream, 1146756268040L);
    }
    PendingIntent pendingIntent = this.operation;
    if (pendingIntent != null) {
      pendingIntent.dumpDebug(protoOutputStream, 1146756268041L);
    }
    IAlarmListener iAlarmListener = this.listener;
    if (iAlarmListener != null) {
      protoOutputStream.write(1138166333450L, iAlarmListener.asBinder().toString());
    }
    protoOutputStream.end(start);
  }

  public class Snapshot {
    public final long[] mPolicyWhenElapsed;
    public final String mTag;
    public final int mType;

    public Snapshot(Alarm alarm) {
      this.mType = alarm.type;
      this.mTag = alarm.statsTag;
      this.mPolicyWhenElapsed = Arrays.copyOf(alarm.mPolicyWhenElapsed, 6);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter, long j) {
      indentingPrintWriter.print("type", Alarm.typeToString(this.mType));
      indentingPrintWriter.print("tag", this.mTag);
      indentingPrintWriter.println();
      indentingPrintWriter.print("policyWhenElapsed:");
      for (int i = 0; i < 6; i++) {
        indentingPrintWriter.print(" " + Alarm.policyIndexToString(i) + "=");
        TimeUtils.formatDuration(this.mPolicyWhenElapsed[i], j, indentingPrintWriter);
      }
      indentingPrintWriter.println();
    }
  }
}
