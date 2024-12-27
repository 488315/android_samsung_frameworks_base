package com.android.internal.art;

import android.util.StatsEvent;
import android.util.StatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ArtStatsLog {
    public static void write(int i, int i2) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            long j,
            long j2,
            int i7,
            int i8,
            int i9,
            int i10,
            int i11) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeInt(i4);
        newBuilder.writeInt(i5);
        newBuilder.writeInt(i6);
        newBuilder.writeLong(j);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, j2, i7, i8);
        newBuilder.writeInt(i9);
        newBuilder.writeInt(i10);
        newBuilder.writeInt(i11);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(
            int i, int i2, int i3, long j, long j2, int i4, int i5, int i6, int i7) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeLong(j);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, j2, i4, i5);
        newBuilder.writeInt(i6);
        newBuilder.writeInt(i7);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, long j, int i2, int i3) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeLong(j);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(
            int i,
            long j,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            int i7,
            int i8,
            int i9,
            int i10,
            int i11,
            int i12,
            int i13,
            int i14,
            int i15,
            int i16,
            int i17,
            int i18,
            int i19,
            int i20,
            int i21,
            int i22,
            int i23) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeLong(j);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, i4, i5, i6, i7);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, i8, i9, i10, i11);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, i12, i13, i14, i15);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, i16, i17, i18, i19);
        newBuilder.writeInt(i20);
        newBuilder.writeInt(i21);
        newBuilder.writeInt(i22);
        newBuilder.writeInt(i23);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(
            int i,
            long j,
            int i2,
            int i3,
            int i4,
            long j2,
            int i5,
            int i6,
            long j3,
            int i7,
            int i8,
            int i9,
            int i10,
            int i11) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeLong(j);
        newBuilder.writeInt(i2);
        if (332 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        if (565 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        newBuilder.writeInt(i3);
        newBuilder.writeInt(i4);
        newBuilder.writeLong(j2);
        newBuilder.writeInt(i5);
        newBuilder.writeInt(i6);
        newBuilder.writeLong(j3);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, i7, i8, i9, i10);
        ArtStatsLog$$ExternalSyntheticOutline0.m(newBuilder, i11);
    }

    public static void write(int i, boolean z, boolean z2, boolean z3) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeBoolean(z);
        newBuilder.writeBoolean(z2);
        newBuilder.writeBoolean(z3);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
