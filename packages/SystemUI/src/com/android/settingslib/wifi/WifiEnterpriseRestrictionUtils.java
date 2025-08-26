package com.android.settingslib.wifi;

import android.content.Context;
import android.os.UserManager;

/* loaded from: classes.dex */
public class WifiEnterpriseRestrictionUtils {
    public static boolean hasUserRestrictionFromT(Context context, String str) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager == null) {
            return false;
        }
        return userManager.hasUserRestriction(str);
    }
}
