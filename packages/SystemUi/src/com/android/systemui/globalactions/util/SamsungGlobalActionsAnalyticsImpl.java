package com.android.systemui.globalactions.util;

import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungGlobalActionsAnalyticsImpl implements SamsungGlobalActionsAnalytics {
    public final void sendEventLog(String str, String str2) {
        SystemUIAnalytics.sendEventLog(str, str2);
    }

    public final void sendEventLog(String str, String str2, String str3, long j) {
        SystemUIAnalytics.sendEventLog(str, str2, str3, j);
    }
}
