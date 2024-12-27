package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
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
