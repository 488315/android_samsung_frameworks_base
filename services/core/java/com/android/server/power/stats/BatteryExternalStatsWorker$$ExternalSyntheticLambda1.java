package com.android.server.power.stats;

import android.os.Process;
import android.os.ThreadLocalWorkSource;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryExternalStatsWorker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BatteryExternalStatsWorker$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BatteryExternalStatsWorker batteryExternalStatsWorker = (BatteryExternalStatsWorker) obj;
                batteryExternalStatsWorker.scheduleSync(1, "wakelock-change");
                batteryExternalStatsWorker.scheduleRunnable(new BatteryExternalStatsWorker$$ExternalSyntheticLambda1(1, batteryExternalStatsWorker));
                break;
            case 1:
                ((BatteryExternalStatsWorker) obj).mStats.mHandler.sendEmptyMessage(1);
                break;
            case 2:
                ((BatteryExternalStatsWorker) obj).scheduleSync(127, "battery-level");
                break;
            default:
                ThreadLocalWorkSource.setUid(Process.myUid());
                ((Runnable) obj).run();
                break;
        }
    }
}
