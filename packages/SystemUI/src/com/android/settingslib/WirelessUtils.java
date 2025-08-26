package com.android.settingslib;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes.dex */
public class WirelessUtils {
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) != 0;
    }
}
