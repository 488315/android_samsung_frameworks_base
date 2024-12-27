package com.android.server.power.stats;

import android.os.BatteryStats;

public final class AudioPowerStatsProcessor extends BinaryStatePowerStatsProcessor {
    @Override // com.android.server.power.stats.BinaryStatePowerStatsProcessor
    public final int getBinaryState(BatteryStats.HistoryItem historyItem) {
        return (historyItem.states & 4194304) != 0 ? 1 : 0;
    }
}
