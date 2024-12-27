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

    static void setHiddenApiAccessLogSampleRates(int sampleRate, int newSampleRate) {
        sInstance.mHiddenApiAccessLogSampleRate = sampleRate;
        sInstance.mHiddenApiAccessStatslogSampleRate = newSampleRate;
    }

    static StatsdHiddenApiUsageLogger getInstance() {
        return sInstance;
    }

    public void hiddenApiUsed(int sampledValue, String packageName, String signature, int accessMethod, boolean accessDenied) {
        if (sampledValue < this.mHiddenApiAccessLogSampleRate) {
            logUsage(packageName, signature, accessMethod, accessDenied);
        }
        if (sampledValue < this.mHiddenApiAccessStatslogSampleRate) {
            newLogUsage(signature, accessMethod, accessDenied);
        }
    }

    private void logUsage(String packageName, String signature, int accessMethod, boolean accessDenied) {
        int accessMethodMetric = 0;
        switch (accessMethod) {
            case 0:
                accessMethodMetric = 0;
                break;
            case 1:
                accessMethodMetric = 1;
                break;
            case 2:
                accessMethodMetric = 2;
                break;
            case 3:
                accessMethodMetric = 3;
                break;
        }
        LogMaker logMaker = new LogMaker(MetricsProto.MetricsEvent.ACTION_HIDDEN_API_ACCESSED).setPackageName(packageName).addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_SIGNATURE, signature).addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_METHOD, Integer.valueOf(accessMethodMetric));
        if (accessDenied) {
            logMaker.addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_DENIED, 1);
        }
        this.mMetricsLogger.write(logMaker);
    }

    private void newLogUsage(String signature, int accessMethod, boolean accessDenied) {
        int accessMethodProto = 0;
        switch (accessMethod) {
            case 0:
                accessMethodProto = 0;
                break;
            case 1:
                accessMethodProto = 1;
                break;
            case 2:
                accessMethodProto = 2;
                break;
            case 3:
                accessMethodProto = 3;
                break;
        }
        int uid = Process.myUid();
        FrameworkStatsLog.write(178, uid, signature, accessMethodProto, accessDenied);
    }
}
