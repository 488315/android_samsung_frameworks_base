package com.android.server.am;

import android.os.WorkSource;

import com.android.server.power.stats.BatteryStatsImpl;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ WorkSource f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda7(
            BatteryStatsService batteryStatsService,
            WorkSource workSource,
            boolean z,
            long j,
            long j2,
            int i) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
        this.f$1 = workSource;
        this.f$2 = z;
        this.f$3 = j;
        this.f$4 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                WorkSource workSource = this.f$1;
                boolean z = this.f$2;
                long j = this.f$3;
                long j2 = this.f$4;
                BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                synchronized (batteryStatsImpl) {
                    try {
                        try {
                            BatteryStatsImpl batteryStatsImpl2 = batteryStatsService.mStats;
                            batteryStatsImpl2.getClass();
                            int size = workSource.size();
                            int i = 0;
                            while (i < size) {
                                BatteryStatsImpl batteryStatsImpl3 = batteryStatsImpl;
                                int i2 = i;
                                batteryStatsImpl2.noteBluetoothScanStartedLocked(
                                        null, workSource.getUid(i), z, j, j2);
                                i = i2 + 1;
                                batteryStatsImpl = batteryStatsImpl3;
                            }
                            BatteryStatsImpl batteryStatsImpl4 = batteryStatsImpl;
                            List workChains = workSource.getWorkChains();
                            if (workChains != null) {
                                for (int i3 = 0; i3 < workChains.size(); i3++) {
                                    batteryStatsImpl2.noteBluetoothScanStartedLocked(
                                            (WorkSource.WorkChain) workChains.get(i3),
                                            -1,
                                            z,
                                            j,
                                            j2);
                                }
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            BatteryStatsImpl batteryStatsImpl5 = batteryStatsImpl;
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                WorkSource workSource2 = this.f$1;
                boolean z2 = this.f$2;
                long j3 = this.f$3;
                long j4 = this.f$4;
                BatteryStatsImpl batteryStatsImpl6 = batteryStatsService2.mStats;
                synchronized (batteryStatsImpl6) {
                    try {
                        try {
                            BatteryStatsImpl batteryStatsImpl7 = batteryStatsService2.mStats;
                            batteryStatsImpl7.getClass();
                            int size2 = workSource2.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                BatteryStatsImpl batteryStatsImpl8 = batteryStatsImpl6;
                                int i5 = i4;
                                batteryStatsImpl7.noteBluetoothScanStoppedLocked(
                                        null, workSource2.getUid(i4), z2, j3, j4);
                                i4 = i5 + 1;
                                batteryStatsImpl6 = batteryStatsImpl8;
                            }
                            BatteryStatsImpl batteryStatsImpl9 = batteryStatsImpl6;
                            List workChains2 = workSource2.getWorkChains();
                            if (workChains2 != null) {
                                for (int i6 = 0; i6 < workChains2.size(); i6++) {
                                    batteryStatsImpl7.noteBluetoothScanStoppedLocked(
                                            (WorkSource.WorkChain) workChains2.get(i6),
                                            -1,
                                            z2,
                                            j3,
                                            j4);
                                }
                            }
                            return;
                        } catch (Throwable th3) {
                            th = th3;
                            BatteryStatsImpl batteryStatsImpl10 = batteryStatsImpl6;
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        throw th;
                    }
                }
        }
    }
}
