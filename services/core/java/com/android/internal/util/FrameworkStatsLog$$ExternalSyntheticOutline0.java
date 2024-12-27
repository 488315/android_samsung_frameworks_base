package com.android.internal.util;

import android.util.StatsEvent;

public abstract /* synthetic */ class FrameworkStatsLog$$ExternalSyntheticOutline0 {
    public static StatsEvent.Builder m(int i, int i2) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        return newBuilder;
    }
}
