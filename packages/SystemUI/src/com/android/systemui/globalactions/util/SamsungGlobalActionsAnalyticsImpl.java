package com.android.systemui.globalactions.util;

import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;

public final class SamsungGlobalActionsAnalyticsImpl implements SamsungGlobalActionsAnalytics {
    public final void sendEventLog(String str, String str2) {
        SystemUIAnalytics.sendEventLog(str, str2);
    }

    public final void sendEventLog(String str, String str2, String str3, long j) {
        SystemUIAnalytics.sendEventLog(str, str2, str3, j);
    }
}
