package com.samsung.android.core.pm;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.os.SystemProperties;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class PmUtils {
    private static final String TAG = "PmUtils";
    private static final List<String> sLiveIconAllowed = Arrays.asList("com.sec.android.app.clockpackage", "com.android.calendar", "com.samsung.android.calendar", "com.sec.android.widgetapp.SPlannerAppWidget", "com.samsung.android.game.gamehome", "com.samsung.android.opencalendar", "com.android.deskclock", "com.samsung.android.smartsuggestions.feature.aisuggestion.ui.activity.SuggestionUiActivity");

    public static boolean isLduSkuBinary() {
        String str = SystemProperties.get("ril.product_code", "");
        if (str.length() < 11) {
            return false;
        }
        return str.charAt(10) == '8' || str.charAt(10) == '9';
    }

    public static boolean supportLiveIcon(ApplicationInfo applicationInfo, Context context) {
        return applicationInfo != null && sLiveIconAllowed.contains(applicationInfo.packageName) && applicationInfo.isSignedWithPlatformKey();
    }

    public static boolean supportLiveIcon(PackageItemInfo packageItemInfo, ApplicationInfo applicationInfo, Context context) {
        return packageItemInfo instanceof ActivityInfo ? sLiveIconAllowed.contains(packageItemInfo.name) && packageItemInfo.getApplicationInfo().isSignedWithPlatformKey() : sLiveIconAllowed.contains(applicationInfo.packageName) && applicationInfo.isSignedWithPlatformKey();
    }
}
