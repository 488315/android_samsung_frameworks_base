package com.android.server.power.stats;

import android.hardware.power.stats.EnergyConsumer;

import java.util.function.Function;

public final /* synthetic */
class PowerStatsCollector$ConsumedEnergyRetrieverImpl$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((EnergyConsumer) obj).ordinal);
    }
}
