package com.sec.android.diagmonagent.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CommonUtils {
    public static String PKG_VERSION;
    public static PackageInfo SERVICE_PKG_INFO;

    public static int getDMAVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("DIAGMON_SDK", "DMA Client is not exist");
            return 0;
        }
    }

    public static String getPackageVersion(Context context) {
        if (PKG_VERSION == null) {
            PackageInfo servicePkgInfo = getServicePkgInfo(context);
            if (servicePkgInfo != null) {
                PKG_VERSION = servicePkgInfo.versionName;
            } else {
                PKG_VERSION = "";
            }
        }
        return PKG_VERSION;
    }

    public static PackageInfo getServicePkgInfo(Context context) {
        if (SERVICE_PKG_INFO == null) {
            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                try {
                    SERVICE_PKG_INFO = packageManager.getPackageInfo(packageName, 4096);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("DIAGMON_SDK", packageName + " is not found");
                }
            }
        }
        return SERVICE_PKG_INFO;
    }
}
