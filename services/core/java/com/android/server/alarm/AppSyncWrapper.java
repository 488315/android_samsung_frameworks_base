package com.android.server.alarm;

import android.os.Build;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AppSyncWrapper {
    static {
        "eng".equals(Build.TYPE);
    }

    public abstract void dump(PrintWriter printWriter);

    public abstract long getWindowLength();

    public abstract boolean isAdjustableAlarm(int i, int i2, long j, long j2, long j3, String str);
}
