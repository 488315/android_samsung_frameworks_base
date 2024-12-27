package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QuickSALog {
    public final SharedPreferences.Editor brightnessBarPrefEditor;

    public QuickSALog(Context context) {
        boolean z = false;
        SharedPreferences.Editor edit = context.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
        if (edit != null) {
            if (!DeviceType.isLightSensorSupported(context) ? Settings.System.getIntForUser(context.getContentResolver(), "display_outdoor_mode", 0, -2) != 0 : Settings.System.getIntForUser(context.getContentResolver(), "screen_brightness_mode", 0, -2) != 0) {
                z = true;
            }
            edit.putBoolean(SystemUIAnalytics.STATUS_BRIGHTNESS_DETAIL_ADAPTIVE_BRIGHTNESS, z);
            edit.commit();
        } else {
            edit = null;
        }
        this.brightnessBarPrefEditor = edit;
    }
}
