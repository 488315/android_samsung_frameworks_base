package com.samsung.android.core.p036pm;

import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.p009os.Build;
import dalvik.system.VMRuntime;
import java.io.File;

/* loaded from: classes5.dex */
public class AbiAppHelper {
    private static final String TAG = "ApplicationPackageManager";
    private boolean is32bitAppRunningInAbi64;

    public AbiAppHelper() {
        this.is32bitAppRunningInAbi64 = Build.SUPPORTED_64_BIT_ABIS.length > 0 && !VMRuntime.getRuntime().is64Bit();
    }

    public boolean canAccessApkFile(ApplicationInfo callerInfo, ApplicationInfo targetInfo) {
        if (!this.is32bitAppRunningInAbi64 || callerInfo == null || targetInfo == null || targetInfo.isSystemApp()) {
            return true;
        }
        String apkPath = targetInfo.getBaseCodePath();
        if (apkPath == null) {
            return false;
        }
        File apkFile = new File(apkPath);
        long fileSizeInMegaByte = (apkFile.length() / 1024) / 1024;
        boolean lowerThan2gb = fileSizeInMegaByte < 2000;
        return lowerThan2gb;
    }

    public boolean canAccessApkFile(Context context, ApplicationInfo targetAppInfo, String packageName) {
        if (targetAppInfo == null) {
            try {
                targetAppInfo = context.getPackageManager().getApplicationInfo(packageName, 1024);
            } catch (PackageManager.NameNotFoundException e) {
                targetAppInfo = null;
            }
        }
        return canAccessApkFile(context.getApplicationInfo(), targetAppInfo);
    }
}
