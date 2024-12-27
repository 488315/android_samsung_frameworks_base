package com.android.server.power.stats;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerStatsExporter {
    public static final long BATTERY_SESSION_TIME_SPAN_SLACK_MILLIS = TimeUnit.MINUTES.toMillis(2);
    public final long mBatterySessionTimeSpanSlackMillis = BATTERY_SESSION_TIME_SPAN_SLACK_MILLIS;
    public final PowerStatsAggregator mPowerStatsAggregator;
    public final PowerStatsStore mPowerStatsStore;

    public PowerStatsExporter(
            PowerStatsStore powerStatsStore, PowerStatsAggregator powerStatsAggregator) {
        this.mPowerStatsStore = powerStatsStore;
        this.mPowerStatsAggregator = powerStatsAggregator;
    }

    public static boolean areMatchingStates(int[] iArr, int i, int i2) {
        if (i != 1) {
            if (i == 2 && iArr[1] != 1) {
                return false;
            }
        } else if (iArr[1] != 0) {
            return false;
        }
        if (i2 != 1) {
            if (i2 == 2 && iArr[0] != 1) {
                return false;
            }
        } else if (iArr[0] != 0) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void populateBatteryUsageStatsBuilder(
            android.os.BatteryUsageStats.Builder r32,
            com.android.server.power.stats.AggregatedPowerStats r33) {
        /*
            Method dump skipped, instructions count: 742
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.PowerStatsExporter.populateBatteryUsageStatsBuilder(android.os.BatteryUsageStats$Builder,"
                    + " com.android.server.power.stats.AggregatedPowerStats):void");
    }
}
