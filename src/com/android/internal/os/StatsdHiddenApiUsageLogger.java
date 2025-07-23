package com.android.internal.os;

import com.android.internal.logging.MetricsLogger;
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

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000a, code lost:
    
        if (r6 != 3) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void logUsage(java.lang.String r4, java.lang.String r5, int r6, boolean r7) {
        /*
            r3 = this;
            r0 = 1
            r1 = 0
            if (r6 == 0) goto L10
            if (r6 == r0) goto Lf
            r2 = 2
            if (r6 == r2) goto Ld
            r2 = 3
            if (r6 == r2) goto Ld
            goto L10
        Ld:
            r1 = r2
            goto L10
        Lf:
            r1 = r0
        L10:
            android.metrics.LogMaker r6 = new android.metrics.LogMaker
            r2 = 1391(0x56f, float:1.949E-42)
            r6.<init>(r2)
            android.metrics.LogMaker r4 = r6.setPackageName(r4)
            r6 = 1394(0x572, float:1.953E-42)
            android.metrics.LogMaker r4 = r4.addTaggedData(r6, r5)
            r5 = 1392(0x570, float:1.95E-42)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r1)
            android.metrics.LogMaker r4 = r4.addTaggedData(r5, r6)
            if (r7 == 0) goto L36
            r5 = 1393(0x571, float:1.952E-42)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)
            r4.addTaggedData(r5, r6)
        L36:
            com.android.internal.logging.MetricsLogger r3 = r3.mMetricsLogger
            r3.write(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.StatsdHiddenApiUsageLogger.logUsage(java.lang.String, java.lang.String, int, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000a, code lost:
    
        if (r3 != 3) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void newLogUsage(java.lang.String r2, int r3, boolean r4) {
        /*
            r1 = this;
            r1 = 0
            if (r3 == 0) goto Le
            r0 = 1
            if (r3 == r0) goto Ld
            r0 = 2
            if (r3 == r0) goto Ld
            r0 = 3
            if (r3 == r0) goto Ld
            goto Le
        Ld:
            r1 = r0
        Le:
            int r3 = android.os.Process.myUid()
            r0 = 178(0xb2, float:2.5E-43)
            com.android.internal.util.FrameworkStatsLog.write(r0, r3, r2, r1, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.StatsdHiddenApiUsageLogger.newLogUsage(java.lang.String, int, boolean):void");
    }
}
