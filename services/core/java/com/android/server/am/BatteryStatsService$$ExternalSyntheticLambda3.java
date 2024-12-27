package com.android.server.am;

import android.os.WorkSource;

import com.android.server.power.stats.BatteryStatsImpl;

import java.util.List;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ WorkSource f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda3(
            BatteryStatsService batteryStatsService,
            WorkSource workSource,
            long j,
            long j2,
            int i) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
        this.f$1 = workSource;
        this.f$2 = j;
        this.f$3 = j2;
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda37() {
        BatteryStatsService batteryStatsService = this.f$0;
        WorkSource workSource = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.getClass();
            int size = workSource.size();
            for (int i = 0; i < size; i++) {
                batteryStatsImpl.noteFullWifiLockReleasedLocked(
                        batteryStatsImpl.mapUid(workSource.getUid(i)), j, j2);
            }
            List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    batteryStatsImpl.noteFullWifiLockReleasedLocked(
                            batteryStatsImpl.mapUid(
                                    ((WorkSource.WorkChain) workChains.get(i2))
                                            .getAttributionUid()),
                            j,
                            j2);
                }
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda73() {
        BatteryStatsService batteryStatsService = this.f$0;
        WorkSource workSource = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.getClass();
            int size = workSource.size();
            for (int i = 0; i < size; i++) {
                batteryStatsImpl.noteWifiScanStartedLocked(
                        batteryStatsImpl.mapUid(workSource.getUid(i)), j, j2);
            }
            List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    batteryStatsImpl.noteWifiScanStartedLocked(
                            batteryStatsImpl.mapUid(
                                    ((WorkSource.WorkChain) workChains.get(i2))
                                            .getAttributionUid()),
                            j,
                            j2);
                }
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda80() {
        BatteryStatsService batteryStatsService = this.f$0;
        WorkSource workSource = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.getClass();
            int size = workSource.size();
            for (int i = 0; i < size; i++) {
                batteryStatsImpl.noteWifiScanStoppedLocked(
                        batteryStatsImpl.mapUid(workSource.getUid(i)), j, j2);
            }
            List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    batteryStatsImpl.noteWifiScanStoppedLocked(
                            batteryStatsImpl.mapUid(
                                    ((WorkSource.WorkChain) workChains.get(i2))
                                            .getAttributionUid()),
                            j,
                            j2);
                }
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                WorkSource workSource = this.f$1;
                long j = this.f$2;
                long j2 = this.f$3;
                synchronized (batteryStatsService.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                    batteryStatsImpl.getClass();
                    int size = workSource.size();
                    for (int i = 0; i < size; i++) {
                        batteryStatsImpl
                                .getUidStatsLocked(
                                        batteryStatsImpl.mapUid(workSource.getUid(i)), j, j2)
                                .noteWifiBatchedScanStoppedLocked(j);
                    }
                    List workChains = workSource.getWorkChains();
                    if (workChains != null) {
                        for (int i2 = 0; i2 < workChains.size(); i2++) {
                            batteryStatsImpl
                                    .getUidStatsLocked(
                                            batteryStatsImpl.mapUid(
                                                    ((WorkSource.WorkChain) workChains.get(i2))
                                                            .getAttributionUid()),
                                            j,
                                            j2)
                                    .noteWifiBatchedScanStoppedLocked(j);
                        }
                    }
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                WorkSource workSource2 = this.f$1;
                long j3 = this.f$2;
                long j4 = this.f$3;
                synchronized (batteryStatsService2.mStats) {
                    BatteryStatsImpl batteryStatsImpl2 = batteryStatsService2.mStats;
                    batteryStatsImpl2.getClass();
                    int size2 = workSource2.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        batteryStatsImpl2.noteFullWifiLockAcquiredLocked(
                                batteryStatsImpl2.mapUid(workSource2.getUid(i3)), j3, j4);
                    }
                    List workChains2 = workSource2.getWorkChains();
                    if (workChains2 != null) {
                        for (int i4 = 0; i4 < workChains2.size(); i4++) {
                            batteryStatsImpl2.noteFullWifiLockAcquiredLocked(
                                    batteryStatsImpl2.mapUid(
                                            ((WorkSource.WorkChain) workChains2.get(i4))
                                                    .getAttributionUid()),
                                    j3,
                                    j4);
                        }
                    }
                }
                return;
            case 2:
                BatteryStatsService batteryStatsService3 = this.f$0;
                WorkSource workSource3 = this.f$1;
                long j5 = this.f$2;
                long j6 = this.f$3;
                synchronized (batteryStatsService3.mStats) {
                    batteryStatsService3.mStats.noteWifiStoppedLocked(workSource3, j5, j6);
                }
                return;
            case 3:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda37();
                return;
            case 4:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda73();
                return;
            case 5:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda80();
                return;
            default:
                BatteryStatsService batteryStatsService4 = this.f$0;
                WorkSource workSource4 = this.f$1;
                long j7 = this.f$2;
                long j8 = this.f$3;
                synchronized (batteryStatsService4.mStats) {
                    batteryStatsService4.mStats.noteWifiRunningLocked(workSource4, j7, j8);
                }
                return;
        }
    }
}
