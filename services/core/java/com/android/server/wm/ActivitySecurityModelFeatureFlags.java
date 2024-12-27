package com.android.server.wm;

import android.app.compat.CompatChanges;
import android.security.Flags;

public abstract class ActivitySecurityModelFeatureFlags {
    public static int sAsmRestrictionsEnabled;
    public static int sAsmToastsEnabled;

    public static boolean shouldRestrictActivitySwitch(int i) {
        if (Flags.asmRestrictionsEnabled()) {
            return CompatChanges.isChangeEnabled(230590090L, i) || sAsmRestrictionsEnabled == 2;
        }
        return false;
    }

    public static boolean shouldShowToast(int i) {
        int i2 = sAsmToastsEnabled;
        if (i2 != 2) {
            return i2 == 1 && CompatChanges.isChangeEnabled(230590090L, i);
        }
        return true;
    }
}
