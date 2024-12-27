package com.android.server.am;

import com.android.server.power.stats.BatteryStatsImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda8
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda8(
            BatteryStatsService batteryStatsService, int i, String str, long j, long j2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$2 = i;
        this.f$1 = str;
        this.f$3 = j;
        this.f$4 = j2;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda8(
            BatteryStatsService batteryStatsService, String str, int i, long j, long j2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = j;
        this.f$4 = j2;
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda129() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$2;
        String str = this.f$1;
        long j = this.f$3;
        long j2 = this.f$4;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.reportExcessiveCpuLocked(i, j, j2, str);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda13() {
        BatteryStatsService batteryStatsService = this.f$0;
        String str = this.f$1;
        int i = this.f$2;
        long j = this.f$3;
        long j2 = this.f$4;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.getClass();
            String encodingPersonalInformation = BatteryStatsImpl.encodingPersonalInformation(str);
            int mapUid = batteryStatsImpl.mapUid(i);
            ((BatteryStatsImpl.DualTimer)
                            batteryStatsImpl
                                    .getUidStatsLocked(mapUid, j, j2)
                                    .mSyncStats
                                    .startObject(j, encodingPersonalInformation))
                    .startRunningLocked(j);
            if (batteryStatsImpl.mActiveEvents.updateState(
                    32772, encodingPersonalInformation, mapUid, 0)) {
                batteryStatsImpl.mHistory.recordEvent(
                        j, j2, 32772, encodingPersonalInformation, mapUid);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda22() {
        BatteryStatsService batteryStatsService = this.f$0;
        String str = this.f$1;
        int i = this.f$2;
        long j = this.f$3;
        long j2 = this.f$4;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.getClass();
            String encodingPersonalInformation = BatteryStatsImpl.encodingPersonalInformation(str);
            int mapUid = batteryStatsImpl.mapUid(i);
            BatteryStatsImpl.DualTimer dualTimer =
                    (BatteryStatsImpl.DualTimer)
                            batteryStatsImpl
                                    .getUidStatsLocked(mapUid, j, j2)
                                    .mSyncStats
                                    .stopObject(j, encodingPersonalInformation);
            if (dualTimer != null) {
                dualTimer.stopRunningLocked(j);
            }
            if (batteryStatsImpl.mActiveEvents.updateState(
                    16388, encodingPersonalInformation, mapUid, 0)) {
                batteryStatsImpl.mHistory.recordEvent(
                        j, j2, 16388, encodingPersonalInformation, mapUid);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda36() {
        BatteryStatsService batteryStatsService = this.f$0;
        String str = this.f$1;
        int i = this.f$2;
        long j = this.f$3;
        long j2 = this.f$4;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.getClass();
            String encodingPersonalInformation = BatteryStatsImpl.encodingPersonalInformation(str);
            int mapUid = batteryStatsImpl.mapUid(i);
            ((BatteryStatsImpl.DualTimer)
                            batteryStatsImpl
                                    .getUidStatsLocked(mapUid, j, j2)
                                    .mJobStats
                                    .startObject(j, encodingPersonalInformation))
                    .startRunningLocked(j);
            if (batteryStatsImpl.mActiveEvents.updateState(
                    32774, encodingPersonalInformation, mapUid, 0)) {
                batteryStatsImpl.mHistory.recordEvent(
                        j, j2, 32774, encodingPersonalInformation, mapUid);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda68() {
        BatteryStatsService batteryStatsService = this.f$0;
        String str = this.f$1;
        int i = this.f$2;
        long j = this.f$3;
        long j2 = this.f$4;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            if (batteryStatsImpl.mOnBattery) {
                batteryStatsImpl
                        .getUidStatsLocked(mapUid, j, j2)
                        .getProcessStatsLocked(str)
                        .mStarts++;
            }
            if (batteryStatsImpl.mActiveEvents.updateState(32769, str, mapUid, 0)
                    && batteryStatsImpl.mRecordAllHistory) {
                batteryStatsImpl.mHistory.recordEvent(j, j2, 32769, str, mapUid);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda74() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$2;
        String str = this.f$1;
        long j = this.f$3;
        long j2 = this.f$4;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.mHistory.recordEvent(j, j2, 9, str, i);
            batteryStatsImpl.mNumConnectivityChange++;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                long j = this.f$3;
                long j2 = this.f$4;
                synchronized (batteryStatsService.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                    int mapUid = batteryStatsImpl.mapUid(i);
                    if (batteryStatsImpl.mActiveEvents.updateState(16385, str, mapUid, 0)
                            && batteryStatsImpl.mRecordAllHistory) {
                        batteryStatsImpl.mHistory.recordEvent(j, j2, 16385, str, mapUid);
                    }
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                String str2 = this.f$1;
                int i2 = this.f$2;
                long j3 = this.f$3;
                long j4 = this.f$4;
                synchronized (batteryStatsService2.mStats) {
                    BatteryStatsImpl batteryStatsImpl2 = batteryStatsService2.mStats;
                    int mapUid2 = batteryStatsImpl2.mapUid(i2);
                    if (batteryStatsImpl2.mOnBattery) {
                        batteryStatsImpl2
                                .getUidStatsLocked(mapUid2, j3, j4)
                                .getProcessStatsLocked(str2)
                                .mNumCrashes++;
                    }
                }
                return;
            case 2:
                BatteryStatsService batteryStatsService3 = this.f$0;
                String str3 = this.f$1;
                int i3 = this.f$2;
                long j5 = this.f$3;
                long j6 = this.f$4;
                synchronized (batteryStatsService3.mStats) {
                    BatteryStatsImpl batteryStatsImpl3 = batteryStatsService3.mStats;
                    int mapUid3 = batteryStatsImpl3.mapUid(i3);
                    if (batteryStatsImpl3.mOnBattery) {
                        batteryStatsImpl3
                                .getUidStatsLocked(mapUid3, j5, j6)
                                .getProcessStatsLocked(str3)
                                .mNumAnrs++;
                    }
                }
                return;
            case 3:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda129();
                return;
            case 4:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda13();
                return;
            case 5:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda22();
                return;
            case 6:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda36();
                return;
            case 7:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda68();
                return;
            case 8:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda74();
                return;
            default:
                BatteryStatsService batteryStatsService4 = this.f$0;
                String str4 = this.f$1;
                int i4 = this.f$2;
                long j7 = this.f$3;
                long j8 = this.f$4;
                synchronized (batteryStatsService4.mStats) {
                    BatteryStatsImpl batteryStatsImpl4 = batteryStatsService4.mStats;
                    batteryStatsImpl4.getClass();
                    String str5 = i4 + " " + str4;
                    if (!batteryStatsImpl4.mScreenWakeStats.containsKey(str5)) {
                        batteryStatsImpl4.mScreenWakeStats.put(
                                str5,
                                new BatteryStatsImpl.Counter(batteryStatsImpl4.mOnBatteryTimeBase));
                    }
                    ((BatteryStatsImpl.Counter) batteryStatsImpl4.mScreenWakeStats.get(str5))
                            .addAtomic(1);
                    batteryStatsImpl4.mHistory.recordEvent(j7, j8, 18, str4, i4);
                }
                return;
        }
    }
}
