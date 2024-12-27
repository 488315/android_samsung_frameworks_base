package com.samsung.android.knox.analytics.util;

import android.Manifest;
import android.content.Context;
import android.os.Process;
import android.os.SemSystemProperties;
import android.os.SystemProperties;

/* loaded from: classes6.dex */
public class SecurityUtils {
    private static final String SERVICE_KEEPER_METHOD_NAME = "log";
    private static final String SERVICE_KEEPER_SERVICE_NAME = "KnoxAnalytics";
    private static final String TAG = "[KnoxAnalytics] " + SecurityUtils.class.getSimpleName();
    private static final boolean IS_ENG = "eng".equals(SystemProperties.get("ro.build.type"));
    private static final boolean IS_SHIP = Boolean.parseBoolean(SemSystemProperties.get("ro.product_ship", "false"));
    private static final String[] WHITELIST_FOR_TEST = {"com.android.frameworks.knoxservicestests", "com.samsung.android.knox.analytics.testapp", "com.samsung.android.knox.kpu.demo", "com.samsung.android.knox.kpu.poc", "com.samsung.knoxautomation", "android", "root"};

    public static void enforcePackageNameForContentProvider(String callingPackage, int pid) {
        if (pid == Process.myPid()) {
            Log.d(TAG, "enforcePackageNameForContentProvider(): MyPid");
        } else if (UploaderBroadcaster.UPLOADER_PACKAGENAME.equals(callingPackage)) {
            Log.d(TAG, "enforcePackageNameForContentProvider(): allowed");
        } else if (isPackageWhitelisted(callingPackage)) {
        } else {
            throw new SecurityException(TAG + "Security Exception Occurred while caller " + callingPackage + " tried to access Content Provider");
        }
    }

    public static final void enforceInternalOnly(String callingPackage, int pid) {
        if (pid == Process.myPid()) {
            Log.d(TAG, "enforceInternalOnly(): MyPid");
        } else if (isPackageWhitelisted(callingPackage)) {
        } else {
            throw new SecurityException(TAG + "Security Exception Occurred while caller " + callingPackage + ", pid = " + pid + " tried to access Content Provider");
        }
    }

    public static void enforceCallingPermissionForLog(Context context, int callingPid, int callingUid) {
        if (callingPid == Process.myPid()) {
            Log.d(TAG, "enforceCallingPermissionForLog(): MyPid");
        } else {
            if (context.checkCallingOrSelfPermission(Manifest.permission.KNOX_ANALYTICS_INTERNAL) == 0 || context.checkCallingOrSelfPermission(Manifest.permission.KNOX_SOLUTION_SDK) == 0) {
                return;
            }
            String callingPackage = context.getPackageManager().getNameForUid(callingUid);
            if (isPackageWhitelisted(callingPackage)) {
            } else {
                throw new SecurityException(TAG + "Security Exception Occurred while pid[" + callingPid + "] with uid[" + callingUid + "] trying to access methodName [" + SERVICE_KEEPER_SERVICE_NAME + "] in [" + SERVICE_KEEPER_METHOD_NAME + "] service");
            }
        }
    }

    private static boolean isPackageWhitelisted(String packageName) {
        if (packageName == null) {
            return false;
        }
        if (IS_SHIP && !IS_ENG) {
            return false;
        }
        for (String whitelistedPackage : WHITELIST_FOR_TEST) {
            if (whitelistedPackage.equals(packageName)) {
                Log.d(TAG, "Allowing whitelisted package for tests: " + packageName);
                return true;
            }
        }
        return false;
    }
}
