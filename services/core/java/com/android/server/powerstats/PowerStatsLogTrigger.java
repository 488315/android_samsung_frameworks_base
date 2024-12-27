package com.android.server.powerstats;

import android.content.Context;

public abstract class PowerStatsLogTrigger {
    public final PowerStatsLogger mPowerStatsLogger;

    public PowerStatsLogTrigger(Context context, PowerStatsLogger powerStatsLogger) {
        this.mPowerStatsLogger = powerStatsLogger;
    }
}
