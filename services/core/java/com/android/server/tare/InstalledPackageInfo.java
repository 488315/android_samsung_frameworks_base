package com.android.server.tare;

import android.app.AppGlobals;
import android.content.Context;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.ApplicationInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import com.android.internal.util.jobs.ArrayUtils;

/* loaded from: classes3.dex */
public class InstalledPackageInfo {
    public final boolean hasCode;
    public final String installerPackageName;
    public final boolean isHeadlessSystemApp;
    public final boolean isSystemInstaller;
    public final String packageName;
    public final int uid;

    public InstalledPackageInfo(Context context, int i, PackageInfo packageInfo) {
        InstallSourceInfo installSourceInfo;
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        this.uid = applicationInfo == null ? -1 : applicationInfo.uid;
        String str = packageInfo.packageName;
        this.packageName = str;
        this.hasCode = applicationInfo != null && applicationInfo.hasCode();
        this.isHeadlessSystemApp = applicationInfo != null && (applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp()) && ArrayUtils.isEmpty(context.getPackageManager().queryIntentActivitiesAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(str), 786944, i));
        this.isSystemInstaller = applicationInfo != null && ArrayUtils.indexOf(packageInfo.requestedPermissions, "android.permission.INSTALL_PACKAGES") >= 0 && PermissionChecker.checkPermissionForPreflight(context, "android.permission.INSTALL_PACKAGES", -1, applicationInfo.uid, str) == 0;
        try {
            installSourceInfo = AppGlobals.getPackageManager().getInstallSourceInfo(str, i);
        } catch (RemoteException unused) {
            installSourceInfo = null;
        }
        this.installerPackageName = installSourceInfo != null ? installSourceInfo.getInstallingPackageName() : null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IPO{uid=");
        sb.append(this.uid);
        sb.append(", pkgName=");
        sb.append(this.packageName);
        sb.append(this.hasCode ? " HAS_CODE" : "");
        sb.append(this.isHeadlessSystemApp ? " HEADLESS_SYSTEM" : "");
        sb.append(this.isSystemInstaller ? " SYSTEM_INSTALLER" : "");
        sb.append(", installer=");
        sb.append(this.installerPackageName);
        sb.append('}');
        return sb.toString();
    }
}
