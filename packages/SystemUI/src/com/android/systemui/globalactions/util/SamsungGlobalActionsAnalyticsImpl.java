package com.android.systemui.globalactions.util;

import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SamsungGlobalActionsAnalyticsImpl implements SamsungGlobalActionsAnalytics {
    public final void sendEventLog(String str, String str2) {
        SystemUIAnalytics.sendEventLog(str, str2);
    }

    public final void sendEventLog(String str, String str2, String str3, long j) {
        SystemUIAnalytics.sendEventLog(str, str2, str3, j);
    }
}
