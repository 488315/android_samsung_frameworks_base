package com.android.server.power.stats;

import android.app.AlarmManager;
import android.os.BatteryUsageStats;
import android.util.Log;

import com.android.server.clipboard.ClipboardService;

import java.io.IOException;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryStatsImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ BatteryStatsImpl f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ BatteryStatsImpl$$ExternalSyntheticLambda0(
            BatteryStatsImpl batteryStatsImpl, long j, BatteryUsageStats batteryUsageStats) {
        this.f$0 = batteryStatsImpl;
        this.f$2 = j;
        this.f$1 = batteryUsageStats;
    }

    public /* synthetic */ BatteryStatsImpl$$ExternalSyntheticLambda0(
            BatteryStatsImpl batteryStatsImpl, AlarmManager alarmManager, long j) {
        this.f$0 = batteryStatsImpl;
        this.f$1 = alarmManager;
        this.f$2 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsImpl batteryStatsImpl = this.f$0;
                ((AlarmManager) this.f$1)
                        .setWindow(
                                1,
                                this.f$2,
                                ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS,
                                "BatteryStatsImpl",
                                batteryStatsImpl.mLongPlugInAlarmHandler,
                                batteryStatsImpl.mHandler);
                break;
            default:
                BatteryStatsImpl batteryStatsImpl2 = this.f$0;
                long j = this.f$2;
                BatteryUsageStats batteryUsageStats = (BatteryUsageStats) this.f$1;
                PowerStatsStore powerStatsStore = batteryStatsImpl2.mPowerStatsStore;
                powerStatsStore.getClass();
                PowerStatsSpan powerStatsSpan = new PowerStatsSpan(j);
                long statsStartTimestamp = batteryUsageStats.getStatsStartTimestamp();
                long statsDuration = batteryUsageStats.getStatsDuration();
                PowerStatsSpan.Metadata metadata = powerStatsSpan.mMetadata;
                ((ArrayList) metadata.mTimeFrames)
                        .add(new PowerStatsSpan.TimeFrame(j, statsStartTimestamp, statsDuration));
                BatteryUsageStatsSection batteryUsageStatsSection =
                        new BatteryUsageStatsSection(batteryUsageStats);
                if (!((ArrayList) metadata.mSections).contains("battery-usage-stats")) {
                    ((ArrayList) metadata.mSections).add("battery-usage-stats");
                }
                ((ArrayList) powerStatsSpan.mSections).add(batteryUsageStatsSection);
                powerStatsStore.storePowerStatsSpan(powerStatsSpan);
                try {
                    batteryUsageStats.close();
                    break;
                } catch (IOException e) {
                    Log.e("BatteryStatsImpl", "Cannot close BatteryUsageStats", e);
                }
        }
    }
}
