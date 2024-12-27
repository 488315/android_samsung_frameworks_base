package com.android.server.am;

import android.os.WorkSource;
import com.android.server.power.stats.BatteryStatsImpl;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda84 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ WorkSource f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda84(BatteryStatsService batteryStatsService, WorkSource workSource, int i, long j, long j2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = workSource;
        this.f$2 = i;
        this.f$3 = j;
        this.f$4 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                WorkSource workSource = this.f$1;
                int i = this.f$2;
                long j = this.f$3;
                long j2 = this.f$4;
                synchronized (batteryStatsService.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                    batteryStatsImpl.getClass();
                    int size = workSource.size();
                    int i2 = 0;
                    while (i2 < size) {
                        int i3 = i2;
                        BatteryStatsImpl.Uid uidStatsLocked = batteryStatsImpl.getUidStatsLocked(batteryStatsImpl.mapUid(workSource.getUid(i2)), j, j2);
                        if (uidStatsLocked.mBluetoothScanResultCounter == null) {
                            uidStatsLocked.mBluetoothScanResultCounter = new BatteryStatsImpl.Counter(uidStatsLocked.mBsi.mOnBatteryTimeBase);
                        }
                        uidStatsLocked.mBluetoothScanResultCounter.addAtomic(i);
                        if (uidStatsLocked.mBluetoothScanResultBgCounter == null) {
                            uidStatsLocked.mBluetoothScanResultBgCounter = new BatteryStatsImpl.Counter(uidStatsLocked.mOnBatteryBackgroundTimeBase);
                        }
                        uidStatsLocked.mBluetoothScanResultBgCounter.addAtomic(i);
                        i2 = i3 + 1;
                    }
                    List workChains = workSource.getWorkChains();
                    if (workChains != null) {
                        for (int i4 = 0; i4 < workChains.size(); i4++) {
                            BatteryStatsImpl.Uid uidStatsLocked2 = batteryStatsImpl.getUidStatsLocked(batteryStatsImpl.mapUid(((WorkSource.WorkChain) workChains.get(i4)).getAttributionUid()), j, j2);
                            if (uidStatsLocked2.mBluetoothScanResultCounter == null) {
                                uidStatsLocked2.mBluetoothScanResultCounter = new BatteryStatsImpl.Counter(uidStatsLocked2.mBsi.mOnBatteryTimeBase);
                            }
                            uidStatsLocked2.mBluetoothScanResultCounter.addAtomic(i);
                            if (uidStatsLocked2.mBluetoothScanResultBgCounter == null) {
                                uidStatsLocked2.mBluetoothScanResultBgCounter = new BatteryStatsImpl.Counter(uidStatsLocked2.mOnBatteryBackgroundTimeBase);
                            }
                            uidStatsLocked2.mBluetoothScanResultBgCounter.addAtomic(i);
                        }
                    }
                }
                return;
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                WorkSource workSource2 = this.f$1;
                int i5 = this.f$2;
                long j3 = this.f$3;
                long j4 = this.f$4;
                synchronized (batteryStatsService2.mStats) {
                    BatteryStatsImpl batteryStatsImpl2 = batteryStatsService2.mStats;
                    batteryStatsImpl2.getClass();
                    int size2 = workSource2.size();
                    for (int i6 = 0; i6 < size2; i6++) {
                        batteryStatsImpl2.getUidStatsLocked(batteryStatsImpl2.mapUid(workSource2.getUid(i6)), j3, j4).noteWifiBatchedScanStartedLocked(i5, j3);
                    }
                    List workChains2 = workSource2.getWorkChains();
                    if (workChains2 != null) {
                        for (int i7 = 0; i7 < workChains2.size(); i7++) {
                            batteryStatsImpl2.getUidStatsLocked(batteryStatsImpl2.mapUid(((WorkSource.WorkChain) workChains2.get(i7)).getAttributionUid()), j3, j4).noteWifiBatchedScanStartedLocked(i5, j3);
                        }
                    }
                }
                return;
        }
    }
}
