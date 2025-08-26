package com.samsung.android.globalactions.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

/* loaded from: classes6.dex */
public class TSafeLockUtil {
    private static final String OFF_MENU_SETTING = "off_menu_setting";
    private static final String TLOCK_PKG_NAME = "com.skt.t_smart_charge";
    private final Context mContext;

    public TSafeLockUtil(Context context) {
        this.mContext = context;
    }

    public boolean isTSafeLock() {
        Boolean boolValueOf = false;
        try {
            boolValueOf = Boolean.valueOf(this.mContext.getPackageManager().getApplicationInfo(TLOCK_PKG_NAME, 0) != null);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (boolValueOf.booleanValue()) {
            boolValueOf = Boolean.valueOf(Settings.System.getInt(this.mContext.getContentResolver(), OFF_MENU_SETTING, 0) == 1);
        }
        return boolValueOf.booleanValue();
    }
}
