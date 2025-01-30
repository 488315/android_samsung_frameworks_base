package com.samsung.android.server.pm.install;

import android.os.SystemProperties;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class ThermalInstallThrottlingUtils {
    public static long getInstallDelayByThermal(int i) {
        if (i == 2) {
            return 0L;
        }
        long j = SystemProperties.getLong("dev.ssrm.app.install.standby", 0L);
        if (j <= 0) {
            return 0L;
        }
        Log.i("PackageManager", "Have install standby: " + j);
        return j;
    }
}
