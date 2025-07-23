package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Preferences {
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
    }
}
