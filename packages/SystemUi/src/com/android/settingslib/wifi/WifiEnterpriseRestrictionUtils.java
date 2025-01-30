package com.android.settingslib.wifi;

import android.content.Context;
import android.os.UserManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WifiEnterpriseRestrictionUtils {
    public static boolean hasUserRestrictionFromT(Context context, String str) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager == null) {
            return false;
        }
        return userManager.hasUserRestriction(str);
    }
}
