package com.android.server.timezonedetector;

import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.LocalLog;

import com.android.server.SystemTimeZone;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda0;

import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EnvironmentImpl implements TimeZoneDetectorStrategyImpl.Environment {
    public final Handler mHandler;

    public EnvironmentImpl(Handler handler) {
        Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public final void addDebugLogEntry(String str) {
        SystemTimeZone.sTimeZoneDebugLog.log(str);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public final void dumpDebugLog(PrintWriter printWriter) {
        SystemTimeZone.sTimeZoneDebugLog.dump(printWriter);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public final long elapsedRealtimeMillis() {
        return SystemClock.elapsedRealtime();
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public final String getDeviceTimeZone() {
        return SystemProperties.get("persist.sys.timezone");
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public final int getDeviceTimeZoneConfidence() {
        LocalLog localLog = SystemTimeZone.sTimeZoneDebugLog;
        int i = SystemProperties.getInt("persist.sys.timezone_confidence", 0);
        if (i < 0 || i > 100) {
            return 0;
        }
        return i;
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public final void runAsync(
            TimeDetectorStrategyImpl$$ExternalSyntheticLambda0
                    timeDetectorStrategyImpl$$ExternalSyntheticLambda0) {
        this.mHandler.post(timeDetectorStrategyImpl$$ExternalSyntheticLambda0);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public final void setDeviceTimeZoneAndConfidence(int i, String str, String str2) {
        AlarmManagerService.this.setTimeZoneImpl(i, str, str2);
    }
}
