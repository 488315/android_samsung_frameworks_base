package com.android.server.power.stats;

import java.util.function.Supplier;

public final /* synthetic */
class BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsImpl.PowerStatsCollectorInjector f$0;

    public /* synthetic */ BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1(
            BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector, int i) {
        this.$r8$classId = i;
        this.f$0 = powerStatsCollectorInjector;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector = this.f$0;
        switch (i) {
            case 0:
                return BatteryStatsImpl.this.readMobileNetworkStatsLocked(
                        powerStatsCollectorInjector.mNetworkStatsManager);
            default:
                return BatteryStatsImpl.this.readWifiNetworkStatsLocked(
                        powerStatsCollectorInjector.mNetworkStatsManager);
        }
    }
}
