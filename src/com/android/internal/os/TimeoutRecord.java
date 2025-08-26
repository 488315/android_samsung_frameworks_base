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
    private AutoCloseable mExpiredTimer = null;
    public final int mKind;
    public final AnrLatencyTracker mLatencyTracker;
    public final String mReason;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeoutKind {
        public static final int APP_REGISTERED = 7;
        public static final int APP_START = 10;
        public static final int BROADCAST_RECEIVER = 3;
        public static final int CONTENT_PROVIDER = 6;
        public static final int INPUT_DISPATCH_NO_FOCUSED_WINDOW = 1;
        public static final int INPUT_DISPATCH_WINDOW_UNRESPONSIVE = 2;
        public static final int JOB_SERVICE = 9;
        public static final int SERVICE_EXEC = 5;
        public static final int SERVICE_START = 4;
        public static final int SHORT_FGS_TIMEOUT = 8;
    }

    private TimeoutRecord(int i, String str, long j, boolean z) {
        this.mKind = i;
        this.mReason = str;
        this.mEndUptimeMillis = j;
        this.mEndTakenBeforeLocks = z;
        this.mLatencyTracker = new AnrLatencyTracker(i, j);
    }

    private static TimeoutRecord endingNow(int i, String str) {
        return new TimeoutRecord(i, str, SystemClock.uptimeMillis(), true);
    }

    private static TimeoutRecord endingApproximatelyNow(int i, String str) {
        return new TimeoutRecord(i, str, SystemClock.uptimeMillis(), false);
    }

    public static TimeoutRecord forBroadcastReceiver(Intent intent, String str, String str2) {
        if (str != null) {
            if (str2 != null) {
                Intent intent2 = new Intent(intent);
                intent2.setComponent(new ComponentName(str, str2));
                intent = intent2;
            } else {
                Intent intent3 = new Intent(intent);
                intent3.setPackage(str);
                intent = intent3;
            }
        }
        return forBroadcastReceiver(intent);
    }

    public static TimeoutRecord forBroadcastReceiver(Intent intent) {
        StringBuilder sb = new StringBuilder("Broadcast of ");
        intent.toString(sb);
        return endingNow(3, sb.toString());
    }

    public static TimeoutRecord forBroadcastReceiver(Intent intent, long j) {
        StringBuilder sb = new StringBuilder("Broadcast of ");
        intent.toString(sb);
        sb.append(", waited ");
        sb.append(j);
        sb.append("ms");
        return endingNow(3, sb.toString());
    }

    public static TimeoutRecord forInputDispatchNoFocusedWindow(String str) {
        return endingNow(1, str);
    }

    public static TimeoutRecord forInputDispatchWindowUnresponsive(String str) {
        return endingNow(2, str);
    }

    public static TimeoutRecord forServiceExec(String str, long j) {
        return endingNow(5, "executing service " + str + ", waited " + j + "ms");
    }

    public static TimeoutRecord forServiceStartWithEndTime(String str, long j) {
        return new TimeoutRecord(4, str, j, true);
    }

    public static TimeoutRecord forContentProvider(String str) {
        return endingApproximatelyNow(6, str);
    }

    public static TimeoutRecord forApp(String str) {
        return endingApproximatelyNow(7, str);
    }

    public static TimeoutRecord forShortFgsTimeout(String str) {
        return endingNow(8, str);
    }

    public static TimeoutRecord forJobService(String str) {
        return endingNow(9, str);
    }

    public static TimeoutRecord forAppStart(String str) {
        return endingNow(10, str);
    }

    public TimeoutRecord setExpiredTimer(AutoCloseable autoCloseable) {
        this.mExpiredTimer = autoCloseable;
        return this;
    }

    public void closeExpiredTimer() throws Exception {
        try {
            AutoCloseable autoCloseable = this.mExpiredTimer;
            if (autoCloseable != null) {
                autoCloseable.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
