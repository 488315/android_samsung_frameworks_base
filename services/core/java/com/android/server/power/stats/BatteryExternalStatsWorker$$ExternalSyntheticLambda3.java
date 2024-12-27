package com.android.server.power.stats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryExternalStatsWorker$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryExternalStatsWorker f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BatteryExternalStatsWorker$$ExternalSyntheticLambda3(BatteryExternalStatsWorker batteryExternalStatsWorker, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryExternalStatsWorker;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryExternalStatsWorker batteryExternalStatsWorker = this.f$0;
                int i = this.f$1;
                synchronized (batteryExternalStatsWorker.mStats) {
                    batteryExternalStatsWorker.mStats.clearRemovedUserUidsLocked(i);
                }
                return;
            case 1:
                BatteryExternalStatsWorker batteryExternalStatsWorker2 = this.f$0;
                int i2 = this.f$1;
                synchronized (batteryExternalStatsWorker2.mStats) {
                    batteryExternalStatsWorker2.mStats.clearRemovedUserUidsLocked(i2);
                }
                return;
            default:
                this.f$0.scheduleSync(this.f$1, "procstate-change");
                return;
        }
    }
}
