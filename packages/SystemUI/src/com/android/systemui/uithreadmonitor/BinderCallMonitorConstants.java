package com.android.systemui.uithreadmonitor;

import android.os.Build;
import android.os.SystemProperties;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BinderCallMonitorConstants {
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
