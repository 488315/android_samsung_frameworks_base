package com.samsung.android.core.pm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import dalvik.system.VMRuntime;
import java.io.File;

/* loaded from: classes6.dex */
public class AbiAppHelper {
    private static final String TAG = "ApplicationPackageManager";
    private boolean is32bitAppRunningInAbi64;

    public AbiAppHelper() {
        this.is32bitAppRunningInAbi64 = Build.SUPPORTED_64_BIT_ABIS.length > 0 && !VMRuntime.getRuntime().is64Bit();
    }

    public boolean canAccessApkFile(ApplicationInfo applicationInfo, ApplicationInfo applicationInfo2) {
        if (!this.is32bitAppRunningInAbi64 || applicationInfo == null || applicationInfo2 == null || applicationInfo2.isSystemApp() || applicationInfo2.isArchived) {
            return true;
        }
        String baseCodePath = applicationInfo2.getBaseCodePath();
        return baseCodePath != null && (new File(baseCodePath).length() / 1024) / 1024 < 2000;
    }

    public boolean canAccessApkFile(Context context, ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null) {
            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(str, 1024);
            } catch (PackageManager.NameNotFoundException unused) {
                applicationInfo = null;
            }
        }
        return canAccessApkFile(context.getApplicationInfo(), applicationInfo);
    }
}
