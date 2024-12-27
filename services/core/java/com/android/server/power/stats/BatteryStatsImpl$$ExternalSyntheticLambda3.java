package com.android.server.power.stats;

import android.os.ConditionVariable;

public final /* synthetic */ class BatteryStatsImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BatteryStatsImpl$$ExternalSyntheticLambda3(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BatteryStatsImpl batteryStatsImpl = (BatteryStatsImpl) obj;
                synchronized (batteryStatsImpl) {
                    batteryStatsImpl.writeSyncLocked();
                }
                return;
            case 1:
                BatteryStatsImpl batteryStatsImpl2 = (BatteryStatsImpl) obj;
                synchronized (batteryStatsImpl2) {
                    batteryStatsImpl2.maybeResetWhilePluggedInLocked();
                }
                return;
            default:
                ((ConditionVariable) obj).open();
                return;
        }
    }
}
