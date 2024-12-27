package com.android.internal.art;

import android.util.StatsEvent;
import android.util.StatsLog;

public abstract /* synthetic */ class ArtStatsLog$$ExternalSyntheticOutline0 {
    public static void m(StatsEvent.Builder builder, int i) {
        builder.writeInt(i);
        builder.usePooledBuffer();
        StatsLog.write(builder.build());
    }

    public static void m(StatsEvent.Builder builder, int i, int i2, int i3, int i4) {
        builder.writeInt(i);
        builder.writeInt(i2);
        builder.writeInt(i3);
        builder.writeInt(i4);
    }

    public static void m(StatsEvent.Builder builder, long j, int i, int i2) {
        builder.writeLong(j);
        builder.writeInt(i);
        builder.writeInt(i2);
    }
}
