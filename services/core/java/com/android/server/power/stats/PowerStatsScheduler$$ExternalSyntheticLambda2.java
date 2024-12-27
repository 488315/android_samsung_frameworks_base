package com.android.server.power.stats;

import android.util.IndentingPrintWriter;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PowerStatsScheduler$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PowerStatsScheduler$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                PowerStatsScheduler powerStatsScheduler = (PowerStatsScheduler) obj2;
                AggregatedPowerStats aggregatedPowerStats = (AggregatedPowerStats) obj;
                PowerStatsStore powerStatsStore = powerStatsScheduler.mPowerStatsStore;
                powerStatsStore.getClass();
                PowerStatsSpan createPowerStatsSpan =
                        PowerStatsStore.createPowerStatsSpan(aggregatedPowerStats);
                if (createPowerStatsSpan != null) {
                    powerStatsStore.storePowerStatsSpan(createPowerStatsSpan);
                }
                powerStatsScheduler.mLastSavedSpanEndMonotonicTime =
                        (((ArrayList) aggregatedPowerStats.mClockUpdates).isEmpty()
                                        ? 0L
                                        : ((AggregatedPowerStats.ClockUpdate)
                                                        ((ArrayList)
                                                                        aggregatedPowerStats
                                                                                .mClockUpdates)
                                                                .get(0))
                                                .monotonicTime)
                                + aggregatedPowerStats.mDurationMs;
                break;
            default:
                IndentingPrintWriter indentingPrintWriter = (IndentingPrintWriter) obj2;
                PowerStatsSpan createPowerStatsSpan2 =
                        PowerStatsStore.createPowerStatsSpan((AggregatedPowerStats) obj);
                if (createPowerStatsSpan2 != null) {
                    createPowerStatsSpan2.dump(indentingPrintWriter);
                    break;
                }
                break;
        }
    }
}
