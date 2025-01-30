package com.android.server.timedetector;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.server.AlarmManagerInternal;
import com.android.server.LocalServices;
import com.android.server.SystemClockTime;
import com.android.server.timedetector.TimeDetectorStrategyImpl;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class EnvironmentImpl implements TimeDetectorStrategyImpl.Environment {
    public final AlarmManagerInternal mAlarmManagerInternal;
    public final Handler mHandler;
    public final PowerManager.WakeLock mWakeLock;

    public EnvironmentImpl(Context context, Handler handler) {
        Objects.requireNonNull(handler);
        this.mHandler = handler;
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "time_detector");
        Objects.requireNonNull(newWakeLock);
        this.mWakeLock = newWakeLock;
        AlarmManagerInternal alarmManagerInternal = (AlarmManagerInternal) LocalServices.getService(AlarmManagerInternal.class);
        Objects.requireNonNull(alarmManagerInternal);
        this.mAlarmManagerInternal = alarmManagerInternal;
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void acquireWakeLock() {
        if (this.mWakeLock.isHeld()) {
            Slog.wtf("time_detector", "WakeLock " + this.mWakeLock + " already held");
        }
        this.mWakeLock.acquire();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public long elapsedRealtimeMillis() {
        return SystemClock.elapsedRealtime();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public long systemClockMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public int systemClockConfidence() {
        return SystemClockTime.getTimeConfidence();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void setSystemClock(long j, int i, String str) {
        checkWakeLockHeld();
        this.mAlarmManagerInternal.setTime(j, i, str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void setSystemClockConfidence(int i, String str) {
        checkWakeLockHeld();
        SystemClockTime.setConfidence(i, str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void releaseWakeLock() {
        checkWakeLockHeld();
        this.mWakeLock.release();
    }

    public final void checkWakeLockHeld() {
        if (this.mWakeLock.isHeld()) {
            return;
        }
        Slog.wtf("time_detector", "WakeLock " + this.mWakeLock + " not held");
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void addDebugLogEntry(String str) {
        SystemClockTime.addDebugLogEntry(str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void dumpDebugLog(IndentingPrintWriter indentingPrintWriter) {
        long elapsedRealtimeMillis = elapsedRealtimeMillis();
        indentingPrintWriter.printf("elapsedRealtimeMillis()=%s (%s)\n", new Object[]{Duration.ofMillis(elapsedRealtimeMillis), Long.valueOf(elapsedRealtimeMillis)});
        long systemClockMillis = systemClockMillis();
        indentingPrintWriter.printf("systemClockMillis()=%s (%s)\n", new Object[]{Instant.ofEpochMilli(systemClockMillis), Long.valueOf(systemClockMillis)});
        indentingPrintWriter.println("systemClockConfidence()=" + systemClockConfidence());
        indentingPrintWriter.println("SystemClockTime debug log:");
        indentingPrintWriter.increaseIndent();
        SystemClockTime.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void runAsync(Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
