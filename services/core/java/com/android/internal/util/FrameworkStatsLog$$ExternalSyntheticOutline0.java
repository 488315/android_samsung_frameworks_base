package com.android.internal.util;

import android.util.StatsEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract /* synthetic */ class FrameworkStatsLog$$ExternalSyntheticOutline0 {
    public static StatsEvent.Builder m(int i, int i2) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        return newBuilder;
    }
}
