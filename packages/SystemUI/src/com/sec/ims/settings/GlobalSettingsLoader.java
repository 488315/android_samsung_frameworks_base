package com.sec.ims.settings;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GlobalSettingsLoader {
    private static final String LOG_TAG = "GlobalSettingsLoader";

    public static GlobalSettings loadGlobalSettings(Context context, String str) {
        return GlobalSettings.getInstance(context);
    }

    public static GlobalSettings loadGlobalSettings(Context context, int i) {
        return GlobalSettings.getInstance(context, i);
    }
}
