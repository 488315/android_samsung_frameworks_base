package com.android.internal.pm.parsing.pkg;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import com.android.internal.pm.pkg.parsing.ParsingPackageHidden;
import com.android.server.pm.pkg.AndroidPackage;

/* loaded from: classes5.dex */
public class AndroidPackageLegacyUtils {
    private AndroidPackageLegacyUtils() {
    }

    public static String getRawPrimaryCpuAbi(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).getPrimaryCpuAbi();
    }

    public static String getRawSecondaryCpuAbi(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).getSecondaryCpuAbi();
    }

    @Deprecated
    public static ApplicationInfo generateAppInfoWithoutState(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).toAppInfoWithoutState();
    }

    public static String getRealPackageOrNull(AndroidPackage pkg, boolean isSystem) {
        if (pkg.getOriginalPackages().isEmpty() || !isSystem) {
            return null;
        }
        return pkg.getManifestPackageName();
    }

    public static void fillVersionCodes(AndroidPackage pkg, PackageInfo info) {
        info.versionCode = ((ParsingPackageHidden) pkg).getVersionCode();
        info.versionCodeMajor = ((ParsingPackageHidden) pkg).getVersionCodeMajor();
    }

    @Deprecated
    public static boolean isSystem(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).isSystem();
    }

    @Deprecated
    public static boolean isSystemExt(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).isSystemExt();
    }

    @Deprecated
    public static boolean isPrivileged(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).isPrivileged();
    }

    @Deprecated
    public static boolean isOem(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).isOem();
    }

    @Deprecated
    public static boolean isVendor(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).isVendor();
    }

    @Deprecated
    public static boolean isProduct(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).isProduct();
    }

    @Deprecated
    public static boolean isOdm(AndroidPackage pkg) {
        return ((AndroidPackageHidden) pkg).isOdm();
    }
}
