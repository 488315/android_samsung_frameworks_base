package com.android.systemui.uithreadmonitor;

import android.os.Build;
import android.os.SystemProperties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BinderCallMonitorConstants {
    public static final int MAX_BUF_COUNT;
    public static final long MAX_DURATION;
    public static final boolean STRICT_MODE_ENABLED;

    static {
        int i = 0;
        STRICT_MODE_ENABLED = !SystemProperties.getBoolean("persist.sysui.ipc_monitor.enabled", false) && (Build.IS_ENG || SystemProperties.getBoolean("persist.sysui.strictmode", false));
        MAX_DURATION = SystemProperties.getInt("debug.sysui.ipc_monitor.dur", 30) * 1000000;
        int i2 = SystemProperties.getInt("debug.sysui.ipc_monitor.max", 50);
        if (i2 > 0 && i2 < 1000) {
            i = i2;
        }
        MAX_BUF_COUNT = i;
    }
}
