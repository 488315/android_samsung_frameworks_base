package com.android.internal.art;

import android.util.StatsEvent;
import android.util.StatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
