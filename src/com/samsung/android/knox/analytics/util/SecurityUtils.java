package com.samsung.android.knox.analytics.util;

import android.Manifest;
import android.app.AppGlobals;
import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.os.SemSystemProperties;

/* loaded from: classes6.dex */
public class SecurityUtils {
    private static final boolean IS_ENG = false;
    private static final String KA_TEST_APP_PACKAGE = "com.samsung.android.knox.analytics.testapp";
    private static final String SERVICE_KEEPER_METHOD_NAME = "log";
    private static final String SERVICE_KEEPER_SERVICE_NAME = "KnoxAnalytics";
    private static final String TAG = "[KnoxAnalytics] SecurityUtils";
    private static final boolean IS_SHIP = Boolean.parseBoolean(SemSystemProperties.get("ro.product_ship", "false"));
    private static final String[] WHITELIST_FOR_TEST = {"com.android.frameworks.knoxservicestests", "com.samsung.android.knox.kpu.demo", "com.samsung.android.knox.kpu.poc", "com.samsung.knoxautomation", "android", "root"};

    public static void enforceProviderCaller(Context context, String str) {
        if (Binder.getCallingPid() == Process.myPid()) {
            Log.d(TAG, "enforceProviderCaller(): System Caller");
            return;
        }
        if (UploaderBroadcaster.UPLOADER_PACKAGENAME.equals(str) && isSystemApplication(str)) {
            Log.d(TAG, "enforceProviderCaller(): Package Allowed (Knox Analytics Uploader)");
            return;
        }
        if (isPackageWhitelisted(context, str)) {
            Log.d(TAG, "enforceProviderCaller(): Package Whitelisted");
            return;
        }
        throw new SecurityException(TAG + "Security Exception Occurred while caller " + str + " tried to access Content Provider");
    }

    private static boolean isSystemApplication(String str) {
        try {
            return AppGlobals.getPackageManager().checkSignatures("android", str, 0) == 0;
        } catch (Exception e) {
            Log.e(TAG, "isSystemApplication(): Failed to validate package signature", e);
            return false;
        }
    }

    public static void enforceCallingPermissionForLog(Context context, int i, int i2) {
        if (i == Process.myPid()) {
            Log.d(TAG, "enforceCallingPermissionForLog(): MyPid");
            return;
        }
        if (context.checkCallingPermission(Manifest.permission.KNOX_ANALYTICS_INTERNAL) == 0 || context.checkCallingPermission(Manifest.permission.KNOX_SOLUTION_SDK) == 0 || isPackageWhitelisted(context, context.getPackageManager().getNameForUid(i2))) {
            return;
        }
        throw new SecurityException(TAG + "Security Exception Occurred while pid[" + i + "] with uid[" + i2 + "] trying to access methodName [KnoxAnalytics] in [log] service");
    }

    public static boolean isAnalyticsTestDevice(Context context, String str) {
        return KA_TEST_APP_PACKAGE.equals(str) && context.checkCallingPermission(Manifest.permission.KNOX_ANALYTICS_DEVELOPER) == 0;
    }

    private static boolean isPackageWhitelisted(Context context, String str) {
        if (str == null) {
            return false;
        }
        if (isAnalyticsTestDevice(context, str)) {
            return true;
        }
        if (IS_SHIP) {
            return false;
        }
        for (String str2 : WHITELIST_FOR_TEST) {
            if (str2.equals(str)) {
                Log.d(TAG, "Allowing whitelisted package for tests: " + str);
                return true;
            }
        }
        return false;
    }
}
