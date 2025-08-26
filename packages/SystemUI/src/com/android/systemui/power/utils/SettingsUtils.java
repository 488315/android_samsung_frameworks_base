package com.android.systemui.power.utils;

import android.content.Context;
import android.provider.Settings;

/* loaded from: classes2.dex */
public class SettingsUtils {
    private SettingsUtils() {
    }

    public static int globalGetInt(Context context, String str, int i) {
        return Settings.Global.getInt(context.getContentResolver(), str, i);
    }
}
