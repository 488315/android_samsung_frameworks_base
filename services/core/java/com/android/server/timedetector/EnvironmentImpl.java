package com.android.server.timedetector;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.util.LocalLog;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.SystemClockTime;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.timedetector.TimeDetectorStrategyImpl;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EnvironmentImpl implements TimeDetectorStrategyImpl.Environment {
    public final AlarmManagerService.LocalService mAlarmManagerInternal;
    public final Handler mHandler;
    public final PowerManager.WakeLock mWakeLock;

    public EnvironmentImpl(Context context, Handler handler) {
        Objects.requireNonNull(handler);
        this.mHandler = handler;
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "time_detector");
        Objects.requireNonNull(newWakeLock);
        this.mWakeLock = newWakeLock;
        AlarmManagerService.LocalService localService = (AlarmManagerService.LocalService) LocalServices.getService(AlarmManagerService.LocalService.class);
        Objects.requireNonNull(localService);
        this.mAlarmManagerInternal = localService;
    }

    public final void acquireWakeLock() {
        if (this.mWakeLock.isHeld()) {
            Slog.wtf("time_detector", "WakeLock " + this.mWakeLock + " already held");
        }
        this.mWakeLock.acquire();
    }

    public final void checkWakeLockHeld() {
        if (this.mWakeLock.isHeld()) {
            return;
        }
        Slog.wtf("time_detector", "WakeLock " + this.mWakeLock + " not held");
    }

    public final void dumpDebugLog(IndentingPrintWriter indentingPrintWriter) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.printf("elapsedRealtimeMillis()=%s (%s)\n", new Object[]{Duration.ofMillis(elapsedRealtime), Long.valueOf(elapsedRealtime)});
        long currentTimeMillis = System.currentTimeMillis();
        indentingPrintWriter.printf("systemClockMillis()=%s (%s)\n", new Object[]{Instant.ofEpochMilli(currentTimeMillis), Long.valueOf(currentTimeMillis)});
        indentingPrintWriter.println("systemClockConfidence()=" + systemClockConfidence());
        indentingPrintWriter.println("SystemClockTime debug log:");
        indentingPrintWriter.increaseIndent();
        SystemClockTime.sTimeDebugLog.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    public final void releaseWakeLock() {
        checkWakeLockHeld();
        this.mWakeLock.release();
    }

    public final void setSystemClockConfidence(int i, String str) {
        checkWakeLockHeld();
        LocalLog localLog = SystemClockTime.sTimeDebugLog;
        synchronized (SystemClockTime.class) {
            SystemClockTime.sTimeConfidence = i;
            SystemClockTime.sTimeDebugLog.log(str);
        }
    }

    public final int systemClockConfidence() {
        int i;
        LocalLog localLog = SystemClockTime.sTimeDebugLog;
        synchronized (SystemClockTime.class) {
            i = SystemClockTime.sTimeConfidence;
        }
        return i;
    }
}
