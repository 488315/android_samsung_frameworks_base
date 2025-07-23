package com.android.settingslib;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class WirelessUtils {
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) != 0;
    }
}
