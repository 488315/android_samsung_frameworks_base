package com.android.server.cpu;

/* loaded from: classes.dex */
public abstract class CpuAvailabilityMonitoringConfig {
    public static String toCpusetString(int i) {
        if (i == 1) {
            return "CPUSET_ALL";
        }
        if (i == 2) {
            return "CPUSET_BACKGROUND";
        }
        return "Invalid cpuset: " + i;
    }
}
