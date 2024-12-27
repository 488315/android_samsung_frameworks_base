package com.android.server.power.stats;

import com.android.server.power.stats.BatteryStatsImpl;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsImpl.PowerStatsCollectorInjector f$0;

    public /* synthetic */ BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda1(BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector, int i) {
        this.$r8$classId = i;
        this.f$0 = powerStatsCollectorInjector;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector = this.f$0;
        switch (i) {
            case 0:
                return BatteryStatsImpl.this.readMobileNetworkStatsLocked(powerStatsCollectorInjector.mNetworkStatsManager);
            default:
                return BatteryStatsImpl.this.readWifiNetworkStatsLocked(powerStatsCollectorInjector.mNetworkStatsManager);
        }
    }
}
