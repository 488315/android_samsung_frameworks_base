package com.android.server.am;

import android.os.WorkSource;

import com.android.server.power.stats.BatteryStatsImpl;

import java.util.List;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda46
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ WorkSource f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ long f$5;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda46(
            int i,
            long j,
            long j2,
            WorkSource workSource,
            BatteryStatsService batteryStatsService,
            String str,
            String str2) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = workSource;
        this.f$4 = j;
        this.f$5 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                String str = this.f$1;
                String str2 = this.f$2;
                WorkSource workSource = this.f$3;
                long j = this.f$4;
                long j2 = this.f$5;
                synchronized (batteryStatsService.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                    batteryStatsImpl.getClass();
                    int size = workSource.size();
                    int i = 0;
                    while (i < size) {
                        batteryStatsImpl.noteLongPartialWakeLockStartInternal(
                                batteryStatsImpl.mapUid(workSource.getUid(i)), str, str2, j, j2);
                        i++;
                        j2 = j2;
                    }
                    long j3 = j2;
                    List workChains = workSource.getWorkChains();
                    if (workChains != null) {
                        for (int i2 = 0; i2 < workChains.size(); i2++) {
                            batteryStatsImpl.noteLongPartialWakeLockStartInternal(
                                    ((WorkSource.WorkChain) workChains.get(i2)).getAttributionUid(),
                                    str,
                                    str2,
                                    j,
                                    j3);
                        }
                    }
                }
                return;
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                String str3 = this.f$1;
                String str4 = this.f$2;
                WorkSource workSource2 = this.f$3;
                long j4 = this.f$4;
                long j5 = this.f$5;
                synchronized (batteryStatsService2.mStats) {
                    BatteryStatsImpl batteryStatsImpl2 = batteryStatsService2.mStats;
                    batteryStatsImpl2.getClass();
                    int size2 = workSource2.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        batteryStatsImpl2.noteLongPartialWakeLockFinishInternal(
                                batteryStatsImpl2.mapUid(workSource2.getUid(i3)),
                                str3,
                                str4,
                                j4,
                                j5);
                        i3++;
                        j5 = j5;
                    }
                    long j6 = j5;
                    List workChains2 = workSource2.getWorkChains();
                    if (workChains2 != null) {
                        for (int i4 = 0; i4 < workChains2.size(); i4++) {
                            batteryStatsImpl2.noteLongPartialWakeLockFinishInternal(
                                    ((WorkSource.WorkChain) workChains2.get(i4))
                                            .getAttributionUid(),
                                    str3,
                                    str4,
                                    j4,
                                    j6);
                        }
                    }
                }
                return;
        }
    }
}
