package com.android.internal.pm.parsing;

import com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils;
import com.android.server.pm.pkg.AndroidPackage;

/* loaded from: classes5.dex */
public class AppInfoUtils {
    public static int appInfoFlags(AndroidPackage pkg) {
        int pkgWithoutStateFlags = flag(pkg.isExternalStorage(), 262144) | flag(pkg.isHardwareAccelerated(), 536870912) | flag(pkg.isBackupAllowed(), 32768) | flag(pkg.isKillAfterRestoreAllowed(), 65536) | flag(pkg.isRestoreAnyVersion(), 131072) | flag(pkg.isFullBackupOnly(), 67108864) | flag(pkg.isPersistent(), 8) | flag(pkg.isDebuggable(), 2) | flag(pkg.isVmSafeMode(), 16384) | flag(pkg.isDeclaredHavingCode(), 4) | flag(pkg.isTaskReparentingAllowed(), 32) | flag(pkg.isClearUserDataAllowed(), 64) | flag(pkg.isLargeHeap(), 1048576) | flag(pkg.isCleartextTrafficAllowed(), 134217728) | flag(pkg.isRtlSupported(), 4194304) | flag(pkg.isTestOnly(), 256) | flag(pkg.isMultiArch(), Integer.MIN_VALUE) | flag(pkg.isExtractNativeLibrariesRequested(), 268435456) | flag(pkg.isGame(), 33554432) | flag(pkg.isSmallScreensSupported(), 512) | flag(pkg.isNormalScreensSupported(), 1024) | flag(pkg.isLargeScreensSupported(), 2048) | flag(pkg.isExtraLargeScreensSupported(), 524288) | flag(pkg.isResizeable(), 4096) | flag(pkg.isAnyDensity(), 8192) | flag(AndroidPackageLegacyUtils.isSystem(pkg), 1) | flag(pkg.isFactoryTest(), 16);
        return pkgWithoutStateFlags;
    }

    public static int appInfoPrivateFlags(AndroidPackage pkg) {
        int pkgWithoutStateFlags = flag(pkg.isStaticSharedLibrary(), 16384) | flag(pkg.isResourceOverlay(), 268435456) | flag(pkg.isIsolatedSplitLoading(), 32768) | flag(pkg.isHasDomainUrls(), 16) | flag(pkg.isProfileableByShell(), 8388608) | flag(pkg.isBackupInForeground(), 8192) | flag(pkg.isUseEmbeddedDex(), 33554432) | flag(pkg.isDefaultToDeviceProtectedStorage(), 32) | flag(pkg.isDirectBootAware(), 64) | flag(pkg.isPartiallyDirectBootAware(), 256) | flag(pkg.isClearUserDataOnFailedRestoreAllowed(), 67108864) | flag(pkg.isAllowAudioPlaybackCapture(), 134217728) | flag(pkg.isRequestLegacyExternalStorage(), 536870912) | flag(pkg.isNonSdkApiRequested(), 4194304) | flag(pkg.isUserDataFragile(), 16777216) | flag(pkg.isSaveStateDisallowed(), 2) | flag(pkg.isResizeableActivityViaSdkVersion(), 4096) | flag(pkg.isAllowNativeHeapPointerTagging(), Integer.MIN_VALUE) | flag(AndroidPackageLegacyUtils.isSystemExt(pkg), 2097152) | flag(AndroidPackageLegacyUtils.isPrivileged(pkg), 8) | flag(AndroidPackageLegacyUtils.isOem(pkg), 131072) | flag(AndroidPackageLegacyUtils.isVendor(pkg), 262144) | flag(AndroidPackageLegacyUtils.isProduct(pkg), 524288) | flag(AndroidPackageLegacyUtils.isOdm(pkg), 1073741824) | flag(pkg.isSignedWithPlatformKey(), 1048576);
        Boolean resizeableActivity = pkg.getResizeableActivity();
        if (resizeableActivity != null) {
            if (resizeableActivity.booleanValue()) {
                return pkgWithoutStateFlags | 1024;
            }
            return pkgWithoutStateFlags | 2048;
        }
        return pkgWithoutStateFlags;
    }

    public static int appInfoPrivateFlagsExt(AndroidPackage pkg, boolean isAllowlistedForHiddenApis) {
        int pkgWithoutStateFlags = flag(pkg.isProfileable(), 1) | flag(pkg.hasRequestForegroundServiceExemption(), 2) | flag(pkg.isAttributionsUserVisible(), 4) | flag(pkg.isOnBackInvokedCallbackEnabled(), 8) | flag(isAllowlistedForHiddenApis, 16);
        return pkgWithoutStateFlags;
    }

    private static int flag(boolean hasFlag, int flag) {
        if (hasFlag) {
            return flag;
        }
        return 0;
    }
}
