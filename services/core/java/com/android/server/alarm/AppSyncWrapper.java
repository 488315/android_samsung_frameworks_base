package com.android.server.alarm;

import android.os.Build;

import java.io.PrintWriter;

public abstract class AppSyncWrapper {
    static {
        "eng".equals(Build.TYPE);
    }

    public abstract void dump(PrintWriter printWriter);

    public abstract long getWindowLength();

    public abstract boolean isAdjustableAlarm(int i, int i2, long j, long j2, long j3, String str);
}
