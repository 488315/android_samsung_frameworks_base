package com.android.server.power.stats;

import java.util.function.IntSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0
        implements IntSupplier {
    public final /* synthetic */ BatteryStatsImpl.PowerStatsCollectorInjector f$0;

    public /* synthetic */ BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0(
            BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector) {
        this.f$0 = powerStatsCollectorInjector;
    }

    @Override // java.util.function.IntSupplier
    public final int getAsInt() {
        return BatteryStatsImpl.this.mBatteryVoltageMv;
    }
}
