package com.android.server.am;

import android.os.WorkSource;
import com.android.server.power.stats.BatteryStatsImpl;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda48 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ WorkSource f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ long f$5;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda48(BatteryStatsService batteryStatsService, WorkSource workSource, boolean z, int i, long j, long j2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = workSource;
        this.f$2 = z;
        this.f$3 = i;
        this.f$4 = j;
        this.f$5 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                WorkSource workSource = this.f$1;
                boolean z = this.f$2;
                int i = this.f$3;
                long j = this.f$4;
                long j2 = this.f$5;
                BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                synchronized (batteryStatsImpl) {
                    try {
                        try {
                            BatteryStatsImpl batteryStatsImpl2 = batteryStatsService.mStats;
                            batteryStatsImpl2.getClass();
                            int size = workSource.size();
                            int i2 = 0;
                            while (i2 < size) {
                                BatteryStatsImpl batteryStatsImpl3 = batteryStatsImpl;
                                int i3 = i2;
                                long j3 = j2;
                                batteryStatsImpl2.noteBluetoothDutyScanStoppedLocked(null, workSource.get(i2), z, i, j, j2);
                                i2 = i3 + 1;
                                batteryStatsImpl = batteryStatsImpl3;
                                j2 = j3;
                            }
                            BatteryStatsImpl batteryStatsImpl4 = batteryStatsImpl;
                            long j4 = j2;
                            List workChains = workSource.getWorkChains();
                            if (workChains != null) {
                                for (int i4 = 0; i4 < workChains.size(); i4++) {
                                    batteryStatsImpl2.noteBluetoothDutyScanStoppedLocked((WorkSource.WorkChain) workChains.get(i4), -1, z, i, j, j4);
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
                int i5 = this.f$3;
                long j5 = this.f$4;
                long j6 = this.f$5;
                BatteryStatsImpl batteryStatsImpl6 = batteryStatsService2.mStats;
                synchronized (batteryStatsImpl6) {
                    try {
                        try {
                            BatteryStatsImpl batteryStatsImpl7 = batteryStatsService2.mStats;
                            batteryStatsImpl7.getClass();
                            int size2 = workSource2.size();
                            int i6 = 0;
                            while (i6 < size2) {
                                BatteryStatsImpl batteryStatsImpl8 = batteryStatsImpl6;
                                int i7 = i6;
                                long j7 = j6;
                                batteryStatsImpl7.noteBluetoothDutyScanStartedLocked(null, workSource2.get(i6), z2, i5, j5, j6);
                                i6 = i7 + 1;
                                batteryStatsImpl6 = batteryStatsImpl8;
                                j6 = j7;
                            }
                            BatteryStatsImpl batteryStatsImpl9 = batteryStatsImpl6;
                            long j8 = j6;
                            List workChains2 = workSource2.getWorkChains();
                            if (workChains2 != null) {
                                for (int i8 = 0; i8 < workChains2.size(); i8++) {
                                    batteryStatsImpl7.noteBluetoothDutyScanStartedLocked((WorkSource.WorkChain) workChains2.get(i8), -1, z2, i5, j5, j8);
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
