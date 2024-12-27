package com.android.server.power.stats;

import android.os.BatteryStats;

public final class VideoPowerStatsProcessor extends BinaryStatePowerStatsProcessor {
    @Override // com.android.server.power.stats.BinaryStatePowerStatsProcessor
    public final int getBinaryState(BatteryStats.HistoryItem historyItem) {
        return (historyItem.states2 & 1073741824) != 0 ? 1 : 0;
    }
}
