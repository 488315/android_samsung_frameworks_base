package com.sec.ims.settings;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class GlobalSettingsLoader {
    private static final String LOG_TAG = "GlobalSettingsLoader";

    public static GlobalSettings loadGlobalSettings(Context context, String str) {
        return GlobalSettings.getInstance(context);
    }

    public static GlobalSettings loadGlobalSettings(Context context, int i) {
        return GlobalSettings.getInstance(context, i);
    }
}
