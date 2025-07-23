package com.samsung.android.globalactions.util;

import android.app.usage.IUsageStatsManager;
import android.content.Context;
import android.os.ServiceManager;

/* loaded from: classes6.dex */
public class UsageStatsWrapper {
    private static final String TAG = "UsageStatsWrapper";
    private IUsageStatsManager mAppUsageStats = IUsageStatsManager.Stub.asInterface(ServiceManager.getService(Context.USAGE_STATS_SERVICE));
    private final Context mContext;
    private final LogWrapper mLogWrapper;

    private void dump(String str, String str2) {
    }

    public UsageStatsWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
    }

    public void shutdownDump(String str) {
        dump("[shutdown]", str);
    }

    public void restartDump(String str) {
        dump("[restart]", str);
    }
}
