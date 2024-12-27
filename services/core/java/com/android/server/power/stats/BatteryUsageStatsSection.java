package com.android.server.power.stats;

import android.os.BatteryUsageStats;
import android.util.IndentingPrintWriter;

import com.android.modules.utils.TypedXmlSerializer;

public final class BatteryUsageStatsSection extends PowerStatsSpan.Section {
    public final BatteryUsageStats mBatteryUsageStats;

    public BatteryUsageStatsSection(BatteryUsageStats batteryUsageStats) {
        super("battery-usage-stats");
        this.mBatteryUsageStats = batteryUsageStats;
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        this.mBatteryUsageStats.dump(indentingPrintWriter, "");
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    public final void write(TypedXmlSerializer typedXmlSerializer) {
        this.mBatteryUsageStats.writeXml(typedXmlSerializer);
    }
}
