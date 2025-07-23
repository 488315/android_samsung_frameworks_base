package com.android.systemui.power.utils;

import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SettingsUtils {
    private SettingsUtils() {
    }

    public static int globalGetInt(Context context, String str, int i) {
        return Settings.Global.getInt(context.getContentResolver(), str, i);
    }
}
