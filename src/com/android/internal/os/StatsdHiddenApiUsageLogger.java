package com.android.internal.os;

import android.metrics.LogMaker;
import android.os.Process;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.FrameworkStatsLog;
import dalvik.system.VMRuntime;

/* loaded from: classes5.dex */
class StatsdHiddenApiUsageLogger implements VMRuntime.HiddenApiUsageLogger {
    private static final StatsdHiddenApiUsageLogger sInstance = new StatsdHiddenApiUsageLogger();
    private final MetricsLogger mMetricsLogger = new MetricsLogger();
    private int mHiddenApiAccessLogSampleRate = 0;
    private int mHiddenApiAccessStatslogSampleRate = 0;

    StatsdHiddenApiUsageLogger() {
    }

    static void setHiddenApiAccessLogSampleRates(int i, int i2) {
        StatsdHiddenApiUsageLogger statsdHiddenApiUsageLogger = sInstance;
        statsdHiddenApiUsageLogger.mHiddenApiAccessLogSampleRate = i;
        statsdHiddenApiUsageLogger.mHiddenApiAccessStatslogSampleRate = i2;
    }

    static StatsdHiddenApiUsageLogger getInstance() {
        return sInstance;
    }

    public void hiddenApiUsed(int i, String str, String str2, int i2, boolean z) {
        if (i < this.mHiddenApiAccessLogSampleRate) {
            logUsage(str, str2, i2, z);
        }
        if (i < this.mHiddenApiAccessStatslogSampleRate) {
            newLogUsage(str2, i2, z);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x000d A[PHI: r2
      0x000d: PHI (r2v2 int) = (r2v1 int), (r2v3 int) binds: [B:6:0x0007, B:8:0x000a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void logUsage(String str, String str2, int i, boolean z) {
        int i2 = 0;
        if (i != 0) {
            if (i != 1) {
                int i3 = 2;
                if (i != 2) {
                    i3 = 3;
                    if (i == 3) {
                        i2 = i3;
                    }
                }
            } else {
                i2 = 1;
            }
        }
        LogMaker logMakerAddTaggedData = new LogMaker(MetricsProto.MetricsEvent.ACTION_HIDDEN_API_ACCESSED).setPackageName(str).addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_SIGNATURE, str2).addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_METHOD, Integer.valueOf(i2));
        if (z) {
            logMakerAddTaggedData.addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_DENIED, 1);
        }
        this.mMetricsLogger.write(logMakerAddTaggedData);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x000d A[PHI: r0
      0x000d: PHI (r0v2 int) = (r0v1 int), (r0v3 int), (r0v4 int) binds: [B:5:0x0004, B:7:0x0007, B:9:0x000a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void newLogUsage(String str, int i, boolean z) {
        int i2 = 0;
        if (i != 0) {
            int i3 = 1;
            if (i != 1) {
                i3 = 2;
                if (i != 2) {
                    i3 = 3;
                    if (i == 3) {
                        i2 = i3;
                    }
                }
            }
        }
        FrameworkStatsLog.write(178, Process.myUid(), str, i2, z);
    }
}
