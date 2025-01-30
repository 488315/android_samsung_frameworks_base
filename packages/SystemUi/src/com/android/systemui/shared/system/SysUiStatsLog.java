package com.android.systemui.shared.system;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SysUiStatsLog {
    public static StatsEvent buildStatsEvent(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(10174);
        newBuilder.writeInt(i);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeInt(i4);
        newBuilder.writeInt(i5);
        newBuilder.writeInt(i6);
        newBuilder.writeInt(i7);
        newBuilder.writeInt(i8);
        newBuilder.writeInt(i9);
        newBuilder.writeInt(i10);
        newBuilder.writeInt(i11);
        newBuilder.writeInt(i12);
        newBuilder.writeInt(i13);
        newBuilder.writeInt(i14);
        newBuilder.writeInt(i15);
        newBuilder.writeInt(i16);
        newBuilder.writeInt(i17);
        newBuilder.writeInt(i18);
        newBuilder.writeInt(0);
        return newBuilder.build();
    }

    public static void write(int i, int i2) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, int i3) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, byte[] bArr) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode);
        newBuilder.writeInt(i);
        newBuilder.writeInt(i2);
        if (bArr == null) {
            bArr = new byte[0];
        }
        newBuilder.writeByteArray(bArr);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, int i3, int i4, String str, int i5, int i6, int i7) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(IKnoxCustomManager.Stub.TRANSACTION_clearForcedDisplaySizeDensity);
        newBuilder.writeInt(i);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeInt(i4);
        newBuilder.writeString(str);
        newBuilder.writeInt(i5);
        newBuilder.writeInt(i6);
        newBuilder.writeInt(i7);
        newBuilder.writeInt(0);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, String str) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(466);
        newBuilder.writeInt(i);
        newBuilder.writeInt(i2);
        newBuilder.writeString(str);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
