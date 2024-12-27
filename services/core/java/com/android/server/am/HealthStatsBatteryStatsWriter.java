package com.android.server.am;

import android.os.BatteryStats;
import android.os.SystemClock;
import android.os.health.HealthStatsWriter;
import android.os.health.TimerStat;

public final class HealthStatsBatteryStatsWriter {
    public final long mNowRealtimeMs = SystemClock.elapsedRealtime();
    public final long mNowUptimeMs = SystemClock.uptimeMillis();

    public final void addTimer(
            HealthStatsWriter healthStatsWriter, int i, BatteryStats.Timer timer) {
        if (timer != null) {
            healthStatsWriter.addTimer(
                    i,
                    timer.getCountLocked(0),
                    timer.getTotalTimeLocked(this.mNowRealtimeMs * 1000, 0) / 1000);
        }
    }

    public final void addTimers(
            HealthStatsWriter healthStatsWriter, int i, String str, BatteryStats.Timer timer) {
        if (timer != null) {
            healthStatsWriter.addTimers(
                    i,
                    str,
                    new TimerStat(
                            timer.getCountLocked(0),
                            timer.getTotalTimeLocked(this.mNowRealtimeMs * 1000, 0) / 1000));
        }
    }
}
