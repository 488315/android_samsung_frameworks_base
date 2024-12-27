package com.samsung.android.server.pm.install;

import android.os.SystemProperties;

import com.android.server.chimera.genie.GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0;

public abstract class ThermalInstallThrottlingUtils {
    public static long getInstallDelayByThermal(int i) {
        if (i == 2) {
            return 0L;
        }
        long j = SystemProperties.getLong("dev.ssrm.app.install.standby", 0L);
        if (j <= 0) {
            return 0L;
        }
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "Have install standby: ", j, "PackageManager");
        return j;
    }
}
