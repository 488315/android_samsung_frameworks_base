package com.android.server.am;

import com.android.server.power.stats.BatteryStatsImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda6
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ long f$5;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda6(
            BatteryStatsService batteryStatsService,
            int i,
            String str,
            String str2,
            long j,
            long j2,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = str;
        this.f$3 = str2;
        this.f$4 = j;
        this.f$5 = j2;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda6(
            BatteryStatsService batteryStatsService,
            String str,
            String str2,
            int i,
            long j,
            long j2,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$2 = str;
        this.f$3 = str2;
        this.f$1 = i;
        this.f$4 = j;
        this.f$5 = j2;
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda121() {
        BatteryStatsService batteryStatsService = this.f$0;
        String str = this.f$2;
        String str2 = this.f$3;
        int i = this.f$1;
        long j = this.f$4;
        long j2 = this.f$5;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteLongPartialWakeLockStartInternal(i, str, str2, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda19() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        String str = this.f$2;
        String str2 = this.f$3;
        long j = this.f$4;
        long j2 = this.f$5;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            BatteryStatsImpl.Uid.Pkg.Serv serviceStatsLocked =
                    batteryStatsImpl
                            .getUidStatsLocked(batteryStatsImpl.mapUid(i), j, j2)
                            .getServiceStatsLocked(str, str2);
            if (serviceStatsLocked.mRunning) {
                long uptime =
                        (serviceStatsLocked.mBsi.mOnBatteryTimeBase.getUptime(j2 * 1000) / 1000)
                                - serviceStatsLocked.mRunningSinceMs;
                if (uptime > 0) {
                    serviceStatsLocked.mStartTimeMs += uptime;
                } else {
                    serviceStatsLocked.mStarts--;
                }
                serviceStatsLocked.mRunning = false;
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                int i = this.f$1;
                String str = this.f$2;
                String str2 = this.f$3;
                long j = this.f$4;
                long j2 = this.f$5;
                synchronized (batteryStatsService.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                    BatteryStatsImpl.Uid.Pkg.Serv serviceStatsLocked =
                            batteryStatsImpl
                                    .getUidStatsLocked(batteryStatsImpl.mapUid(i), j, j2)
                                    .getServiceStatsLocked(str, str2);
                    if (serviceStatsLocked.mLaunched) {
                        if ((serviceStatsLocked.mBsi.mOnBatteryTimeBase.getUptime(j2 * 1000) / 1000)
                                        - serviceStatsLocked.mLaunchedSinceMs
                                <= 0) {
                            serviceStatsLocked.mLaunches--;
                        }
                        serviceStatsLocked.mLaunched = false;
                    }
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                int i2 = this.f$1;
                String str3 = this.f$2;
                String str4 = this.f$3;
                long j3 = this.f$4;
                long j4 = this.f$5;
                synchronized (batteryStatsService2.mStats) {
                    BatteryStatsImpl batteryStatsImpl2 = batteryStatsService2.mStats;
                    BatteryStatsImpl.Uid.Pkg.Serv serviceStatsLocked2 =
                            batteryStatsImpl2
                                    .getUidStatsLocked(batteryStatsImpl2.mapUid(i2), j3, j4)
                                    .getServiceStatsLocked(str3, str4);
                    if (!serviceStatsLocked2.mRunning) {
                        serviceStatsLocked2.mStarts++;
                        serviceStatsLocked2.mRunningSinceMs =
                                serviceStatsLocked2.mBsi.mOnBatteryTimeBase.getUptime(j4 * 1000)
                                        / 1000;
                        serviceStatsLocked2.mRunning = true;
                    }
                }
                return;
            case 2:
                BatteryStatsService batteryStatsService3 = this.f$0;
                int i3 = this.f$1;
                String str5 = this.f$2;
                String str6 = this.f$3;
                long j5 = this.f$4;
                long j6 = this.f$5;
                synchronized (batteryStatsService3.mStats) {
                    BatteryStatsImpl batteryStatsImpl3 = batteryStatsService3.mStats;
                    BatteryStatsImpl.Uid.Pkg.Serv serviceStatsLocked3 =
                            batteryStatsImpl3
                                    .getUidStatsLocked(batteryStatsImpl3.mapUid(i3), j5, j6)
                                    .getServiceStatsLocked(str5, str6);
                    if (!serviceStatsLocked3.mLaunched) {
                        serviceStatsLocked3.mLaunches++;
                        serviceStatsLocked3.mLaunchedSinceMs =
                                serviceStatsLocked3.mBsi.mOnBatteryTimeBase.getUptime(j6 * 1000)
                                        / 1000;
                        serviceStatsLocked3.mLaunched = true;
                    }
                }
                return;
            case 3:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda121();
                return;
            case 4:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda19();
                return;
            default:
                BatteryStatsService batteryStatsService4 = this.f$0;
                String str7 = this.f$2;
                String str8 = this.f$3;
                int i4 = this.f$1;
                long j7 = this.f$4;
                long j8 = this.f$5;
                synchronized (batteryStatsService4.mStats) {
                    batteryStatsService4.mStats.noteLongPartialWakeLockFinishInternal(
                            i4, str7, str8, j7, j8);
                }
                return;
        }
    }
}
