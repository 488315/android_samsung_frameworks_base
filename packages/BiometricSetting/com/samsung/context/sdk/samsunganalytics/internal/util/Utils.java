package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;

/* loaded from: classes.dex */
public final class Utils {
    public static boolean compareDays(int i, Long l) {
        return Long.valueOf(System.currentTimeMillis()).longValue() > (((long) i) * 86400000) + l.longValue();
    }

    public static void throwException(String str) {
        if (Build.TYPE.equals("eng")) {
            throw new AnalyticsException(str);
        }
        Debug.LogE(str);
    }
}
