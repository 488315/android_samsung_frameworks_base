package com.android.internal.pm.parsing.pkg;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import com.android.internal.pm.pkg.parsing.ParsingPackageHidden;
import com.android.server.pm.pkg.AndroidPackage;

/* loaded from: classes5.dex */
public class AndroidPackageLegacyUtils {
    private AndroidPackageLegacyUtils() {
    }

    public static String getRawPrimaryCpuAbi(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).getPrimaryCpuAbi();
    }

    public static String getRawSecondaryCpuAbi(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).getSecondaryCpuAbi();
    }

    @Deprecated
    public static ApplicationInfo generateAppInfoWithoutState(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).toAppInfoWithoutState();
    }

    public static String getRealPackageOrNull(AndroidPackage androidPackage, boolean z) {
        if (androidPackage.getOriginalPackages().isEmpty() || !z) {
            return null;
        }
        return androidPackage.getManifestPackageName();
    }

    public static void fillVersionCodes(AndroidPackage androidPackage, PackageInfo packageInfo) {
        ParsingPackageHidden parsingPackageHidden = (ParsingPackageHidden) androidPackage;
        packageInfo.versionCode = parsingPackageHidden.getVersionCode();
        packageInfo.versionCodeMajor = parsingPackageHidden.getVersionCodeMajor();
    }

    @Deprecated
    public static boolean isSystem(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).isSystem();
    }

    @Deprecated
    public static boolean isSystemExt(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).isSystemExt();
    }

    @Deprecated
    public static boolean isPrivileged(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).isPrivileged();
    }

    @Deprecated
    public static boolean isOem(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).isOem();
    }

    @Deprecated
    public static boolean isVendor(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).isVendor();
    }

    @Deprecated
    public static boolean isProduct(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).isProduct();
    }

    @Deprecated
    public static boolean isOdm(AndroidPackage androidPackage) {
        return ((AndroidPackageHidden) androidPackage).isOdm();
    }
}
