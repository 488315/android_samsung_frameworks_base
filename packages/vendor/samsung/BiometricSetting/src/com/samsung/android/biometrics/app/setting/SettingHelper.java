package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.provider.Settings;

public abstract class SettingHelper {
    public static boolean isNavigationBarHidden(Context context) {
        try {
            return Settings.Global.getInt(
                            context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                    != 0;
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("isNavigationBarHidden: "), "BSS_SH");
            return false;
        }
    }
}
