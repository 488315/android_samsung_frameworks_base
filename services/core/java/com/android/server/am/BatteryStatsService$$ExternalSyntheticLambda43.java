package com.android.server.am;

import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.power.stats.WifiPowerStatsCollector;
import com.android.server.power.stats.wakeups.CpuWakeupStats;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda43
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda43(
            BatteryStatsService batteryStatsService, int i, long j, int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = j;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda43(
            BatteryStatsService batteryStatsService, int i, String str, long j) {
        this.$r8$classId = 2;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                int i = this.f$1;
                long j = this.f$2;
                CpuWakeupStats cpuWakeupStats = batteryStatsService.mCpuWakeupStats;
                synchronized (cpuWakeupStats) {
                    cpuWakeupStats.mUidProcStates.delete(i);
                }
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.removeUidStatsLocked(i, j);
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                int i2 = this.f$1;
                long j2 = this.f$2;
                synchronized (batteryStatsService2.mStats) {
                    batteryStatsService2.mStats.onCleanupUserLocked(i2, j2);
                }
                return;
            default:
                BatteryStatsService batteryStatsService3 = this.f$0;
                int i3 = this.f$1;
                long j3 = this.f$2;
                synchronized (batteryStatsService3.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService3.mStats;
                    int i4 = batteryStatsImpl.mWifiState;
                    if (i4 != i3) {
                        if (i4 >= 0) {
                            batteryStatsImpl.mWifiStateTimer[i4].stopRunningLocked(j3);
                        }
                        batteryStatsImpl.mWifiState = i3;
                        batteryStatsImpl.mWifiStateTimer[i3].startRunningLocked(j3);
                        WifiPowerStatsCollector wifiPowerStatsCollector =
                                batteryStatsImpl.mWifiPowerStatsCollector;
                        if (wifiPowerStatsCollector.mEnabled) {
                            wifiPowerStatsCollector.schedule();
                        } else {
                            batteryStatsImpl.scheduleSyncExternalStatsLocked(2, "wifi-state");
                        }
                    }
                }
                return;
        }
    }
}
