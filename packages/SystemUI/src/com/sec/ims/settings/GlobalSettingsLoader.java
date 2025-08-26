package com.sec.ims.settings;

import android.content.Context;

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
