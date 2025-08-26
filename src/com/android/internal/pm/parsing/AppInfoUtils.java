package com.android.internal.pm.parsing;

import com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils;
import com.android.server.pm.pkg.AndroidPackage;

/* loaded from: classes5.dex */
public class AppInfoUtils {
    private static int flag(boolean z, int i) {
        if (z) {
            return i;
        }
        return 0;
    }

    public static int appInfoFlags(AndroidPackage androidPackage) {
        return flag(androidPackage.isFactoryTest(), 16) | flag(androidPackage.isExternalStorage(), 262144) | flag(androidPackage.isHardwareAccelerated(), 536870912) | flag(androidPackage.isBackupAllowed(), 32768) | flag(androidPackage.isKillAfterRestoreAllowed(), 65536) | flag(androidPackage.isRestoreAnyVersion(), 131072) | flag(androidPackage.isFullBackupOnly(), 67108864) | flag(androidPackage.isPersistent(), 8) | flag(androidPackage.isDebuggable(), 2) | flag(androidPackage.isVmSafeMode(), 16384) | flag(androidPackage.isDeclaredHavingCode(), 4) | flag(androidPackage.isTaskReparentingAllowed(), 32) | flag(androidPackage.isClearUserDataAllowed(), 64) | flag(androidPackage.isLargeHeap(), 1048576) | flag(androidPackage.isCleartextTrafficAllowed(), 134217728) | flag(androidPackage.isRtlSupported(), 4194304) | flag(androidPackage.isTestOnly(), 256) | flag(androidPackage.isMultiArch(), Integer.MIN_VALUE) | flag(androidPackage.isExtractNativeLibrariesRequested(), 268435456) | flag(androidPackage.isGame(), 33554432) | flag(androidPackage.isSmallScreensSupported(), 512) | flag(androidPackage.isNormalScreensSupported(), 1024) | flag(androidPackage.isLargeScreensSupported(), 2048) | flag(androidPackage.isExtraLargeScreensSupported(), 524288) | flag(androidPackage.isResizeable(), 4096) | flag(androidPackage.isAnyDensity(), 8192) | flag(AndroidPackageLegacyUtils.isSystem(androidPackage), 1);
    }

    public static int appInfoPrivateFlags(AndroidPackage androidPackage) {
        int iFlag = flag(androidPackage.isStaticSharedLibrary(), 16384) | flag(androidPackage.isResourceOverlay(), 268435456) | flag(androidPackage.isIsolatedSplitLoading(), 32768) | flag(androidPackage.isHasDomainUrls(), 16) | flag(androidPackage.isProfileableByShell(), 8388608) | flag(androidPackage.isBackupInForeground(), 8192) | flag(androidPackage.isUseEmbeddedDex(), 33554432) | flag(androidPackage.isDefaultToDeviceProtectedStorage(), 32) | flag(androidPackage.isDirectBootAware(), 64) | flag(androidPackage.isPartiallyDirectBootAware(), 256) | flag(androidPackage.isClearUserDataOnFailedRestoreAllowed(), 67108864) | flag(androidPackage.isAllowAudioPlaybackCapture(), 134217728) | flag(androidPackage.isRequestLegacyExternalStorage(), 536870912) | flag(androidPackage.isNonSdkApiRequested(), 4194304) | flag(androidPackage.isUserDataFragile(), 16777216) | flag(androidPackage.isSaveStateDisallowed(), 2) | flag(androidPackage.isResizeableActivityViaSdkVersion(), 4096) | flag(androidPackage.isAllowNativeHeapPointerTagging(), Integer.MIN_VALUE) | flag(AndroidPackageLegacyUtils.isSystemExt(androidPackage), 2097152) | flag(AndroidPackageLegacyUtils.isPrivileged(androidPackage), 8) | flag(AndroidPackageLegacyUtils.isOem(androidPackage), 131072) | flag(AndroidPackageLegacyUtils.isVendor(androidPackage), 262144) | flag(AndroidPackageLegacyUtils.isProduct(androidPackage), 524288) | flag(AndroidPackageLegacyUtils.isOdm(androidPackage), 1073741824) | flag(androidPackage.isSignedWithPlatformKey(), 1048576);
        Boolean resizeableActivity = androidPackage.getResizeableActivity();
        return resizeableActivity != null ? resizeableActivity.booleanValue() ? iFlag | 1024 : iFlag | 2048 : iFlag;
    }

    public static int appInfoPrivateFlagsExt(AndroidPackage androidPackage, boolean z) {
        return flag(androidPackage.isOnBackInvokedCallbackEnabled(), 8) | flag(androidPackage.isProfileable(), 1) | flag(androidPackage.hasRequestForegroundServiceExemption(), 2) | flag(androidPackage.isAttributionsUserVisible(), 4) | flag(z, 16);
    }
}
