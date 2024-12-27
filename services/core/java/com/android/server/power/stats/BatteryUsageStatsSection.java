package com.android.server.power.stats;

import android.os.BatteryUsageStats;
import android.util.IndentingPrintWriter;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.power.stats.PowerStatsSpan;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
