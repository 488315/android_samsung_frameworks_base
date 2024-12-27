package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;

import com.samsung.context.sdk.samsunganalytics.AnalyticsException;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public abstract class Utils {
    public static boolean compareDays(int i, Long l) {
        return System.currentTimeMillis() > (((long) i) * 86400000) + l.longValue();
    }

    public static void throwException(String str) {
        if (Build.TYPE.equals("eng")) {
            throw new AnalyticsException(str);
        }
        Debug.LogE(str);
    }
}
