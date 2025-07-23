package com.android.internal.logging;

import android.util.EventLog;

/* loaded from: classes5.dex */
public class EventLogTags {
    public static final int COMMIT_SYS_CONFIG_FILE = 525000;
    public static final int SYSUI_ACTION = 524288;
    public static final int SYSUI_COUNT = 524290;
    public static final int SYSUI_HISTOGRAM = 524291;
    public static final int SYSUI_LATENCY = 36070;
    public static final int SYSUI_MULTI_ACTION = 524292;
    public static final int SYSUI_VIEW_VISIBILITY = 524287;

    private EventLogTags() {
    }

    public static void writeSysuiViewVisibility(int i, int i2) {
        EventLog.writeEvent(524287, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeSysuiAction(int i, String str) {
        EventLog.writeEvent(524288, Integer.valueOf(i), str);
    }

    public static void writeSysuiMultiAction(Object[] objArr) {
        EventLog.writeEvent(524292, objArr);
    }

    public static void writeSysuiCount(String str, int i) {
        EventLog.writeEvent(524290, str, Integer.valueOf(i));
    }

    public static void writeSysuiHistogram(String str, int i) {
        EventLog.writeEvent(SYSUI_HISTOGRAM, str, Integer.valueOf(i));
    }

    public static void writeSysuiLatency(int i, int i2) {
        EventLog.writeEvent(36070, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void writeCommitSysConfigFile(String str, long j) {
        EventLog.writeEvent(COMMIT_SYS_CONFIG_FILE, str, Long.valueOf(j));
    }
}
