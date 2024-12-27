package com.android.server.power.stats;

import android.util.IndentingPrintWriter;

import com.android.modules.utils.TypedXmlSerializer;

public final class AggregatedPowerStatsSection extends PowerStatsSpan.Section {
    public final AggregatedPowerStats mAggregatedPowerStats;

    public AggregatedPowerStatsSection(AggregatedPowerStats aggregatedPowerStats) {
        super("aggregated-power-stats");
        this.mAggregatedPowerStats = aggregatedPowerStats;
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        this.mAggregatedPowerStats.dump(indentingPrintWriter);
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    public final void write(TypedXmlSerializer typedXmlSerializer) {
        AggregatedPowerStats aggregatedPowerStats = this.mAggregatedPowerStats;
        aggregatedPowerStats.getClass();
        typedXmlSerializer.startTag((String) null, "agg-power-stats");
        for (int i = 0; i < aggregatedPowerStats.mPowerComponentStats.size(); i++) {
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats =
                    (PowerComponentAggregatedPowerStats)
                            aggregatedPowerStats.mPowerComponentStats.valueAt(i);
            if (powerComponentAggregatedPowerStats != aggregatedPowerStats.mGenericPowerComponent
                    && powerComponentAggregatedPowerStats.mPowerStatsDescriptor != null) {
                typedXmlSerializer.startTag((String) null, "power_component");
                typedXmlSerializer.attributeInt(
                        (String) null, "id", powerComponentAggregatedPowerStats.powerComponentId);
                powerComponentAggregatedPowerStats.mPowerStatsDescriptor.writeXml(
                        typedXmlSerializer);
                if (powerComponentAggregatedPowerStats.mDeviceStats != null) {
                    typedXmlSerializer.startTag((String) null, "device-stats");
                    powerComponentAggregatedPowerStats.mDeviceStats.writeXml(typedXmlSerializer);
                    typedXmlSerializer.endTag((String) null, "device-stats");
                }
                for (int i2 = 0; i2 < powerComponentAggregatedPowerStats.mStateStats.size(); i2++) {
                    typedXmlSerializer.startTag((String) null, "state-stats");
                    typedXmlSerializer.attributeInt(
                            (String) null,
                            "key",
                            powerComponentAggregatedPowerStats.mStateStats.keyAt(i2));
                    ((MultiStateStats) powerComponentAggregatedPowerStats.mStateStats.valueAt(i2))
                            .writeXml(typedXmlSerializer);
                    typedXmlSerializer.endTag((String) null, "state-stats");
                }
                for (int size = powerComponentAggregatedPowerStats.mUidStats.size() - 1;
                        size >= 0;
                        size--) {
                    int keyAt = powerComponentAggregatedPowerStats.mUidStats.keyAt(size);
                    PowerComponentAggregatedPowerStats.UidStats uidStats =
                            (PowerComponentAggregatedPowerStats.UidStats)
                                    powerComponentAggregatedPowerStats.mUidStats.valueAt(size);
                    if (uidStats.stats != null) {
                        typedXmlSerializer.startTag((String) null, "uid-stats");
                        typedXmlSerializer.attributeInt((String) null, "uid", keyAt);
                        uidStats.stats.writeXml(typedXmlSerializer);
                        typedXmlSerializer.endTag((String) null, "uid-stats");
                    }
                }
                typedXmlSerializer.endTag((String) null, "power_component");
                typedXmlSerializer.flush();
            }
        }
        typedXmlSerializer.endTag((String) null, "agg-power-stats");
        typedXmlSerializer.flush();
    }
}
