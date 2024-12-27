package com.samsung.android.core.pm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.SystemProperties;

import com.samsung.android.desktopmode.DesktopModeFeature;

import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class PmUtils {
    private static final String TAG = "PmUtils";
    private static final List<String> sLiveIconPackages =
            Arrays.asList(
                    "com.sec.android.app.clockpackage",
                    "com.android.calendar",
                    "com.samsung.android.calendar",
                    "com.sec.android.widgetapp.SPlannerAppWidget",
                    "com.samsung.android.game.gamehome",
                    "com.samsung.android.opencalendar",
                    "com.android.deskclock");

    public static boolean isLduSkuBinary() {
        String productCode = SystemProperties.get("ril.product_code", "");
        if (productCode.length() < 11) {
            return false;
        }
        return productCode.charAt(10) == '8' || productCode.charAt(10) == '9';
    }

    public static boolean supportLiveIcon(ApplicationInfo appInfo, Context context) {
        if (appInfo != null
                && sLiveIconPackages.contains(appInfo.packageName)
                && appInfo.isSignedWithPlatformKey()
                && !DesktopModeFeature.isDesktopMode(context)) {
            return true;
        }
        return false;
    }
}
