package com.android.server.power.stats;

import android.os.BatteryStats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VideoPowerStatsProcessor extends BinaryStatePowerStatsProcessor {
    @Override // com.android.server.power.stats.BinaryStatePowerStatsProcessor
    public final int getBinaryState(BatteryStats.HistoryItem historyItem) {
        return (historyItem.states2 & 1073741824) != 0 ? 1 : 0;
    }
}
