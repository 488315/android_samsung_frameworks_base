package com.samsung.android.globalactions.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class PackageManagerWrapper {
    private final PackageManager mPackageManager;
    private final String KIDS_MODE_PACKAGE_NAME = "com.sec.android.app.kidshome";
    private final String KIDS_MODE_ACTIVITY_NAME =
            "com.sec.android.app.kidshome.apps.ui.AppsActivity";
    private final String ATT_FOTA_CLIENT_PACKAGE = "com.ws.dm";
    private final String SEC_FOTA_CLIENT_PACKAGE = "com.wssyncmldm";

    public PackageManagerWrapper(Context context) {
        this.mPackageManager = context.getPackageManager();
    }

    public boolean isKidsHomeMode() {
        ComponentName kidsMode =
                new ComponentName(
                        "com.sec.android.app.kidshome",
                        "com.sec.android.app.kidshome.apps.ui.AppsActivity");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = this.mPackageManager.resolveActivity(intent, 65536);
        ComponentName defaultHome =
                new ComponentName(
                        resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        return defaultHome.equals(kidsMode);
    }

    public boolean isATTFOTAPackageAvailable() {
        ApplicationInfo info = getApplicationInfo("com.ws.dm", 0);
        return info != null && info.enabled;
    }

    public boolean isSecFOTAPackageAvailable() {
        ApplicationInfo info = getApplicationInfo("com.wssyncmldm", 0);
        return info != null && info.enabled;
    }

    public ApplicationInfo getApplicationInfo(String s, int i) {
        try {
            return this.mPackageManager.getApplicationInfo(s, i);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
