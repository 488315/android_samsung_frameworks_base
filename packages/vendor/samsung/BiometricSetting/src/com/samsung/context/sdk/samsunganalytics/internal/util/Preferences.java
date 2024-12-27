package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public abstract class Preferences {
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
    }
}
