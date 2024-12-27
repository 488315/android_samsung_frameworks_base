package com.android.systemui.globalactions.util;

import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SamsungGlobalActionsAnalyticsImpl implements SamsungGlobalActionsAnalytics {
    public final void sendEventLog(String str, String str2) {
        SystemUIAnalytics.sendEventLog(str, str2);
    }

    public final void sendEventLog(String str, String str2, String str3, long j) {
        SystemUIAnalytics.sendEventLog(str, str2, str3, j);
    }
}
