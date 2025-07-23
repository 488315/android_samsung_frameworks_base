package com.samsung.android.globalactions.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/* loaded from: classes6.dex */
public class PackageManagerWrapper {
    private final PackageManager mPackageManager;
    private final String KIDS_MODE_PACKAGE_NAME = "com.sec.android.app.kidshome";
    private final String KIDS_MODE_ACTIVITY_NAME = "com.sec.android.app.kidshome.apps.ui.AppsActivity";
    private final String ATT_FOTA_CLIENT_PACKAGE = "com.ws.dm";
    private final String SEC_FOTA_CLIENT_PACKAGE = "com.wssyncmldm";

    public PackageManagerWrapper(Context context) {
        this.mPackageManager = context.getPackageManager();
    }

    public boolean isKidsHomeMode() {
        ComponentName componentName = new ComponentName("com.sec.android.app.kidshome", "com.sec.android.app.kidshome.apps.ui.AppsActivity");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveActivity = this.mPackageManager.resolveActivity(intent, 65536);
        return new ComponentName(resolveActivity.activityInfo.packageName, resolveActivity.activityInfo.name).equals(componentName);
    }

    public boolean isATTFOTAPackageAvailable() {
        ApplicationInfo applicationInfo = getApplicationInfo("com.ws.dm", 0);
        return applicationInfo != null && applicationInfo.enabled;
    }

    public boolean isSecFOTAPackageAvailable() {
        ApplicationInfo applicationInfo = getApplicationInfo("com.wssyncmldm", 0);
        return applicationInfo != null && applicationInfo.enabled;
    }

    public ApplicationInfo getApplicationInfo(String str, int i) {
        try {
            return this.mPackageManager.getApplicationInfo(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }
}
