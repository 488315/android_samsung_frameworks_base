package com.android.server.alarm;

import android.app.AlarmManager;
import android.app.IAlarmListener;
import android.app.PendingIntent;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.WorkSource;
import android.util.IndentingPrintWriter;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Alarm {
    public static final int NUM_POLICIES = 5;
    public final AlarmManager.AlarmClockInfo alarmClock;
    public int count;
    public final int creatorUid;
    public final int exactAllowReason;
    public final int flags;
    public final IAlarmListener listener;
    public final String listenerTag;
    public final Bundle mIdleOptions;
    public long mMaxWhenElapsed;
    public final long[] mPolicyWhenElapsed;
    public boolean mUsingReserveQuota;
    public long mWhenElapsed;
    public final PendingIntent operation;
    public int origType;
    public final long origWhen;
    public final String packageName;
    public int priorityClass;
    public final long repeatInterval;
    public final String sourcePackage;
    public final String statsTag;
    public final int type;
    public final int uid;
    public final boolean wakeup;
    public final long windowLength;
    public final WorkSource workSource;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Snapshot {
        public final long[] mPolicyWhenElapsed;
        public final String mTag;
        public final int mType;

        public Snapshot(Alarm alarm) {
            this.mType = alarm.type;
            this.mTag = alarm.statsTag;
            this.mPolicyWhenElapsed = Arrays.copyOf(alarm.mPolicyWhenElapsed, 5);
        }
    }

    public Alarm(
            int i,
            int i2,
            int i3,
            int i4,
            long j,
            long j2,
            long j3,
            long j4,
            AlarmManager.AlarmClockInfo alarmClockInfo,
            IAlarmListener iAlarmListener,
            PendingIntent pendingIntent,
            Bundle bundle,
            WorkSource workSource,
            String str,
            String str2) {
        this.type = i;
        this.origType = i;
        this.origWhen = j;
        this.wakeup = i == 2 || i == 0;
        long[] jArr = new long[5];
        this.mPolicyWhenElapsed = jArr;
        jArr[0] = j2;
        this.mWhenElapsed = j2;
        this.windowLength = j3;
        this.mMaxWhenElapsed = AlarmManagerService.addClampPositive(j2, j3);
        this.repeatInterval = j4;
        this.operation = pendingIntent;
        this.listener = iAlarmListener;
        this.listenerTag = str;
        String str3 = (i == 2 || i == 0) ? "*walarm*:" : "*alarm*:";
        this.statsTag =
                pendingIntent != null
                        ? pendingIntent.getTag(str3)
                        : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str3, str);
        this.workSource = workSource;
        this.flags = i2;
        this.alarmClock = alarmClockInfo;
        int i5 = i3;
        this.uid = i5;
        String str4 = str2;
        this.packageName = str4;
        this.mIdleOptions = bundle;
        this.exactAllowReason = i4;
        this.sourcePackage = pendingIntent != null ? pendingIntent.getCreatorPackage() : str4;
        this.creatorUid = pendingIntent != null ? pendingIntent.getCreatorUid() : i5;
        this.mUsingReserveQuota = false;
        this.priorityClass = 2;
    }

    public static String policyIndexToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4
                                                ? BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(i, "--unknown(", ")--")
                                                : "gms_manager"
                                        : "battery_saver"
                                : "device_idle"
                        : "app_standby"
                : "requester";
    }

    public static String typeToString(int i) {
        return i != 0
                ? i != 1 ? i != 2 ? i != 3 ? "--unknown--" : "ELAPSED" : "ELAPSED_WAKEUP" : "RTC"
                : "RTC_WAKEUP";
    }

    public final void dump(
            IndentingPrintWriter indentingPrintWriter, long j, SimpleDateFormat simpleDateFormat) {
        String str;
        int i = this.type;
        boolean z = true;
        if (i != 1 && i != 0) {
            z = false;
        }
        indentingPrintWriter.print("tag=");
        indentingPrintWriter.println(this.statsTag);
        indentingPrintWriter.print("type=");
        indentingPrintWriter.print(typeToString(i));
        if (i != this.origType) {
            indentingPrintWriter.print(" origType=");
            indentingPrintWriter.print(typeToString(this.origType));
        }
        indentingPrintWriter.print(" origWhen=");
        long j2 = this.origWhen;
        if (z) {
            indentingPrintWriter.print(simpleDateFormat.format(new Date(j2)));
        } else {
            TimeUtils.formatDuration(j2, j, indentingPrintWriter);
        }
        indentingPrintWriter.print(" window=");
        TimeUtils.formatDuration(this.windowLength, indentingPrintWriter);
        int i2 = this.exactAllowReason;
        if (i2 != -1) {
            indentingPrintWriter.print(" exactAllowReason=");
            switch (i2) {
                case -1:
                    str = "N/A";
                    break;
                case 0:
                    str = "permission";
                    break;
                case 1:
                    str = "allow-listed";
                    break;
                case 2:
                    str = "compat";
                    break;
                case 3:
                    str = "policy_permission";
                    break;
                case 4:
                    str = "listener";
                    break;
                case 5:
                    str = "prioritized";
                    break;
                default:
                    str = "--unknown--";
                    break;
            }
            indentingPrintWriter.print(str);
        }
        indentingPrintWriter.print(" repeatInterval=");
        indentingPrintWriter.print(this.repeatInterval);
        indentingPrintWriter.print(" count=");
        indentingPrintWriter.print(this.count);
        indentingPrintWriter.print(" flags=0x");
        indentingPrintWriter.println(Integer.toHexString(this.flags));
        indentingPrintWriter.print("policyWhenElapsed:");
        for (int i3 = 0; i3 < 5; i3++) {
            indentingPrintWriter.print(" " + policyIndexToString(i3) + "=");
            TimeUtils.formatDuration(this.mPolicyWhenElapsed[i3], j, indentingPrintWriter);
        }
        indentingPrintWriter.println();
        indentingPrintWriter.print("whenElapsed=");
        TimeUtils.formatDuration(this.mWhenElapsed, j, indentingPrintWriter);
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

    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, long j2) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.statsTag);
        protoOutputStream.write(1159641169922L, this.type);
        protoOutputStream.write(1112396529667L, this.mWhenElapsed - j2);
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

    public long getPolicyElapsed(int i) {
        return this.mPolicyWhenElapsed[i];
    }

    public final boolean setPolicyElapsed(int i, long j) {
        long[] jArr = this.mPolicyWhenElapsed;
        jArr[i] = j;
        long j2 = this.mWhenElapsed;
        this.mWhenElapsed = 0L;
        for (int i2 = 0; i2 < 5; i2++) {
            this.mWhenElapsed = Math.max(this.mWhenElapsed, jArr[i2]);
        }
        long j3 = this.mMaxWhenElapsed;
        long max =
                Math.max(
                        AlarmManagerService.addClampPositive(jArr[0], this.windowLength),
                        this.mWhenElapsed);
        this.mMaxWhenElapsed = max;
        return (j2 == this.mWhenElapsed && j3 == max) ? false : true;
    }

    public final String toString() {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "Alarm{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" type ");
        int i = this.type;
        m.append(i);
        if (i != this.origType) {
            m.append(" origType ");
            m.append(this.origType);
        }
        m.append(" origWhen ");
        m.append(this.origWhen);
        m.append(" whenElapsed ");
        m.append(this.mWhenElapsed);
        m.append(" ");
        m.append(this.sourcePackage);
        m.append('}');
        return m.toString();
    }
}
