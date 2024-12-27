package com.android.server.crashrecovery.proto;

import android.util.StatsEvent;
import android.util.StatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class CrashRecoveryStatsLog {
    public static void write(int i, String str, int i2, int i3, String str2, byte[] bArr) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(147);
        newBuilder.writeInt(i);
        newBuilder.writeString(str);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeString(str2);
        newBuilder.writeByteArray(bArr);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
