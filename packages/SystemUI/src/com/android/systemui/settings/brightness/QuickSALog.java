package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;

/* loaded from: classes3.dex */
public final class QuickSALog {
    public final SharedPreferences.Editor brightnessBarPrefEditor;

    public QuickSALog(Context context) {
        boolean z = false;
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
        if (editorEdit != null) {
            if (!DeviceType.isLightSensorSupported(context) ? Settings.System.getIntForUser(context.getContentResolver(), "display_outdoor_mode", 0, -2) != 0 : Settings.System.getIntForUser(context.getContentResolver(), "screen_brightness_mode", 0, -2) != 0) {
                z = true;
            }
            editorEdit.putBoolean(SystemUIAnalytics.STATUS_BRIGHTNESS_DETAIL_ADAPTIVE_BRIGHTNESS, z);
            editorEdit.commit();
        } else {
            editorEdit = null;
        }
        this.brightnessBarPrefEditor = editorEdit;
    }
}
