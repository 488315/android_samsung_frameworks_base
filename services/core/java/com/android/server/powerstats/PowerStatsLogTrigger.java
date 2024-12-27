package com.android.server.powerstats;

import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PowerStatsLogTrigger {
    public final PowerStatsLogger mPowerStatsLogger;

    public PowerStatsLogTrigger(Context context, PowerStatsLogger powerStatsLogger) {
        this.mPowerStatsLogger = powerStatsLogger;
    }
}
