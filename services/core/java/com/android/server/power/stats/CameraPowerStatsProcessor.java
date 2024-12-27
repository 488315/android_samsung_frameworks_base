package com.android.server.power.stats;

import android.os.BatteryStats;

public final class CameraPowerStatsProcessor extends BinaryStatePowerStatsProcessor {
    @Override // com.android.server.power.stats.BinaryStatePowerStatsProcessor
    public final int getBinaryState(BatteryStats.HistoryItem historyItem) {
        return (historyItem.states2 & 2097152) != 0 ? 1 : 0;
    }
}
