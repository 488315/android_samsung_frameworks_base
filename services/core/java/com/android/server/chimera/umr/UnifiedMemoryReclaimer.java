package com.android.server.chimera.umr;

import android.os.Process;
import android.os.SystemProperties;

import java.io.PrintWriter;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UnifiedMemoryReclaimer {
    public static final boolean MODEL_UMR_ENABLED =
            "true".equals(SystemProperties.get("ro.sys.kernelmemory.umr.enabled", "false"));

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Reclaimer {
        public static final String[] CONTROL_STRINGS = {"disabled", "enabled"};
        public static final String[] MODE_STRINGS = {"default", "suppressed", "proactive"};
        public final int efficiency = 0;
        public final String name;

        public Reclaimer(String str) {
            this.name = str;
        }

        public final String toString() {
            return String.format(
                    "%s: %s, %s, %d",
                    this.name,
                    CONTROL_STRINGS[0],
                    MODE_STRINGS[0],
                    Integer.valueOf(this.efficiency));
        }
    }

    static {
        "0x4f4c".equalsIgnoreCase(SystemProperties.get("ro.boot.debug_level", "Unknown"));
        Process.getTotalMemory();
        SystemProperties.getInt("ro.sys.kernelmemory.umr.psi_cpu_threshold_ms", 400);
        SystemProperties.getInt("ro.sys.kernelmemory.umr.psi_mem_threshold_ms", 100);
        SystemProperties.getLong("ro.sys.kernelmemory.umr.mem_avail_low_threshold_kb", 2097152L);
        SystemProperties.getLong("ro.sys.kernelmemory.umr.mem_free_low_threshold_kb", 102400L);
        KernelMemoryProxy$ReclaimerLog.write(
                "staticInitialize: UnifiedMemoryReclaimer is disabled by config", true);
    }

    private static native void closeCpuStatusMonitorNative();

    private static native void closeMemStatusMonitorNative();

    private static native int createStatusMonitorNative();

    private static native void destroyStatusMonitorNative();

    public static void dumpInfo(PrintWriter printWriter) {
        Arrays.asList("1", "true", "True", "TRUE");
        Arrays.asList("0", "false", "False", "FALSE");
        try {
            printWriter.println("disabled by system configuration");
        } catch (Exception unused) {
        }
    }

    private static native long getFreeMemoryNative();

    public static boolean isInAppLaunch() {
        if (ForegroundAppTracker.mForegroundMonitor == null) {
            return false;
        }
        return ForegroundAppTracker.getForegroundMonitor().mAppLaunch;
    }

    private static native int openCpuStatusMonitorNative(int i, int i2);

    private static native int openMemStatusMonitorNative(int i, int i2);

    private static native int waitForStatusUpdate();
}
