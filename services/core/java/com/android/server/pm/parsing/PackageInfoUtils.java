package com.android.server.pm.parsing;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.FallbackCategoryProvider;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PathPermission;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProcessInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.overlay.OverlayPaths;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Environment;
import android.os.PatternMatcher;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.secutil.Slog;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils;
import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.pm.pkg.component.ComponentParseUtils;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedPermissionGroup;
import com.android.internal.pm.pkg.component.ParsedProcess;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.AppCategoryHintHelper;
import com.android.server.pm.PackageArchiver;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.pm.pkg.PackageUserState;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.PackageUserStateUtils;
import com.android.server.pm.pkg.SharedLibraryWrapper;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class PackageInfoUtils {
    public static final String SYSTEM_DATA_PATH;

    public final class CachedApplicationInfoGenerator {
        public final ArrayMap mCache = new ArrayMap();
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getDataDirectoryPath());
        SYSTEM_DATA_PATH =
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, File.separator, "system");
    }

    public static int appInfoFlags(
            AndroidPackage androidPackage, PackageStateInternal packageStateInternal) {
        int i =
                (androidPackage.isFactoryTest() ? 16 : 0)
                        | (androidPackage.isExternalStorage() ? 262144 : 0)
                        | (androidPackage.isHardwareAccelerated() ? 536870912 : 0)
                        | (androidPackage.isBackupAllowed() ? 32768 : 0)
                        | (androidPackage.isKillAfterRestoreAllowed()
                                ? EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT
                                : 0)
                        | (androidPackage.isRestoreAnyVersion() ? 131072 : 0)
                        | (androidPackage.isFullBackupOnly() ? 67108864 : 0)
                        | (androidPackage.isPersistent() ? 8 : 0)
                        | (androidPackage.isDebuggable() ? 2 : 0)
                        | (androidPackage.isVmSafeMode()
                                ? EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION
                                : 0)
                        | (androidPackage.isDeclaredHavingCode() ? 4 : 0)
                        | (androidPackage.isTaskReparentingAllowed() ? 32 : 0)
                        | (androidPackage.isClearUserDataAllowed() ? 64 : 0)
                        | (androidPackage.isLargeHeap() ? 1048576 : 0)
                        | (androidPackage.isCleartextTrafficAllowed() ? 134217728 : 0)
                        | (androidPackage.isRtlSupported() ? 4194304 : 0)
                        | (androidPackage.isTestOnly() ? 256 : 0)
                        | (androidPackage.isMultiArch() ? Integer.MIN_VALUE : 0)
                        | (androidPackage.isExtractNativeLibrariesRequested() ? 268435456 : 0)
                        | (androidPackage.isGame() ? 33554432 : 0)
                        | (androidPackage.isSmallScreensSupported() ? 512 : 0)
                        | (androidPackage.isNormalScreensSupported() ? 1024 : 0)
                        | (androidPackage.isLargeScreensSupported() ? 2048 : 0)
                        | (androidPackage.isExtraLargeScreensSupported() ? 524288 : 0)
                        | (androidPackage.isResizeable() ? 4096 : 0)
                        | (androidPackage.isAnyDensity() ? 8192 : 0)
                        | (AndroidPackageLegacyUtils.isSystem(androidPackage) ? 1 : 0);
        if (packageStateInternal != null) {
            return i | (packageStateInternal.isUpdatedSystemApp() ? 128 : 0);
        }
        return i;
    }

    public static int appInfoPrivateFlags(AndroidPackage androidPackage) {
        int i =
                (androidPackage.isStaticSharedLibrary()
                                ? EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION
                                : 0)
                        | (androidPackage.isResourceOverlay() ? 268435456 : 0)
                        | (androidPackage.isIsolatedSplitLoading() ? 32768 : 0)
                        | (androidPackage.isHasDomainUrls() ? 16 : 0)
                        | (androidPackage.isProfileableByShell() ? 8388608 : 0)
                        | (androidPackage.isBackupInForeground() ? 8192 : 0)
                        | (androidPackage.isUseEmbeddedDex() ? 33554432 : 0)
                        | (androidPackage.isDefaultToDeviceProtectedStorage() ? 32 : 0)
                        | (androidPackage.isDirectBootAware() ? 64 : 0)
                        | (androidPackage.isPartiallyDirectBootAware() ? 256 : 0)
                        | (androidPackage.isClearUserDataOnFailedRestoreAllowed() ? 67108864 : 0)
                        | (androidPackage.isAllowAudioPlaybackCapture() ? 134217728 : 0)
                        | (androidPackage.isRequestLegacyExternalStorage() ? 536870912 : 0)
                        | (androidPackage.isNonSdkApiRequested() ? 4194304 : 0)
                        | (androidPackage.isUserDataFragile() ? 16777216 : 0)
                        | (androidPackage.isSaveStateDisallowed() ? 2 : 0)
                        | (androidPackage.isResizeableActivityViaSdkVersion() ? 4096 : 0)
                        | (androidPackage.isAllowNativeHeapPointerTagging() ? Integer.MIN_VALUE : 0)
                        | (AndroidPackageLegacyUtils.isSystemExt(androidPackage) ? 2097152 : 0)
                        | (AndroidPackageLegacyUtils.isPrivileged(androidPackage) ? 8 : 0)
                        | (AndroidPackageLegacyUtils.isOem(androidPackage) ? 131072 : 0)
                        | (AndroidPackageLegacyUtils.isVendor(androidPackage) ? 262144 : 0)
                        | (AndroidPackageLegacyUtils.isProduct(androidPackage) ? 524288 : 0)
                        | (AndroidPackageLegacyUtils.isOdm(androidPackage) ? 1073741824 : 0)
                        | (androidPackage.isSignedWithPlatformKey() ? 1048576 : 0);
        Boolean resizeableActivity = androidPackage.getResizeableActivity();
        return resizeableActivity != null
                ? resizeableActivity.booleanValue() ? i | 1024 : i | 2048
                : i;
    }

    public static void assignFieldsComponentInfoParsedMainComponent(
            ComponentInfo componentInfo,
            ParsedMainComponent parsedMainComponent,
            PackageStateInternal packageStateInternal,
            int i) {
        assignFieldsPackageItemInfoParsedComponent(componentInfo, parsedMainComponent);
        componentInfo.descriptionRes = parsedMainComponent.getDescriptionRes();
        componentInfo.directBootAware = parsedMainComponent.isDirectBootAware();
        componentInfo.enabled = parsedMainComponent.isEnabled();
        componentInfo.splitName = parsedMainComponent.getSplitName();
        componentInfo.attributionTags = parsedMainComponent.getAttributionTags();
        Pair nonLocalizedLabelAndIcon =
                ParsedComponentStateUtils.getNonLocalizedLabelAndIcon(
                        parsedMainComponent, packageStateInternal, i);
        componentInfo.nonLocalizedLabel = (CharSequence) nonLocalizedLabelAndIcon.first;
        componentInfo.icon = ((Integer) nonLocalizedLabelAndIcon.second).intValue();
    }

    public static void assignFieldsPackageItemInfoParsedComponent(
            PackageItemInfo packageItemInfo, ParsedComponent parsedComponent) {
        packageItemInfo.nonLocalizedLabel =
                ComponentParseUtils.getNonLocalizedLabel(parsedComponent);
        packageItemInfo.icon = ComponentParseUtils.getIcon(parsedComponent);
        packageItemInfo.banner = parsedComponent.getBanner();
        packageItemInfo.labelRes = parsedComponent.getLabelRes();
        packageItemInfo.logo = parsedComponent.getLogo();
        packageItemInfo.name = parsedComponent.getName();
        packageItemInfo.packageName = parsedComponent.getPackageName();
    }

    public static boolean checkUseInstalledOrHidden(
            long j,
            PackageUserStateInternal packageUserStateInternal,
            ApplicationInfo applicationInfo) {
        if ((536870912 & j) != 0
                || packageUserStateInternal.isInstalled()
                || applicationInfo == null
                || !applicationInfo.hiddenUntilInstalled) {
            return PackageUserStateUtils.isAvailable(packageUserStateInternal, j)
                    || !(applicationInfo == null
                            || !applicationInfo.isSystemApp()
                            || (j & 4836040704L) == 0);
        }
        return false;
    }

    public static boolean checkUseInstalledOrHidden(
            PackageStateInternal packageStateInternal,
            PackageUserStateInternal packageUserStateInternal,
            long j) {
        if ((536870912 & j) == 0
                && !packageUserStateInternal.isInstalled()
                && packageStateInternal.getTransientState().hiddenUntilInstalled) {
            return false;
        }
        return PackageUserStateUtils.isAvailable(packageUserStateInternal, j)
                || (packageStateInternal.isSystem() && (4836040704L & j) != 0);
    }

    public static ActivityInfo generateActivityInfo(
            AndroidPackage androidPackage,
            ParsedActivity parsedActivity,
            long j,
            PackageUserStateInternal packageUserStateInternal,
            int i,
            PackageStateInternal packageStateInternal) {
        return generateActivityInfo(
                androidPackage,
                parsedActivity,
                j,
                packageUserStateInternal,
                null,
                i,
                packageStateInternal);
    }

    public static ActivityInfo generateActivityInfo(
            AndroidPackage androidPackage,
            ParsedActivity parsedActivity,
            long j,
            PackageUserStateInternal packageUserStateInternal,
            ApplicationInfo applicationInfo,
            int i,
            PackageStateInternal packageStateInternal) {
        if (parsedActivity == null
                || !checkUseInstalledOrHidden(packageStateInternal, packageUserStateInternal, j)) {
            return null;
        }
        if (applicationInfo == null) {
            applicationInfo =
                    generateApplicationInfo(
                            androidPackage, j, packageUserStateInternal, i, packageStateInternal);
        }
        if (applicationInfo == null) {
            return null;
        }
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.targetActivity = parsedActivity.getTargetActivity();
        activityInfo.processName = parsedActivity.getProcessName();
        activityInfo.exported = parsedActivity.isExported();
        activityInfo.theme = parsedActivity.getTheme();
        activityInfo.uiOptions = parsedActivity.getUiOptions();
        activityInfo.parentActivityName = parsedActivity.getParentActivityName();
        activityInfo.permission = parsedActivity.getPermission();
        activityInfo.taskAffinity = parsedActivity.getTaskAffinity();
        activityInfo.flags = parsedActivity.getFlags();
        activityInfo.privateFlags = parsedActivity.getPrivateFlags();
        activityInfo.launchMode = parsedActivity.getLaunchMode();
        activityInfo.documentLaunchMode = parsedActivity.getDocumentLaunchMode();
        activityInfo.maxRecents = parsedActivity.getMaxRecents();
        activityInfo.configChanges = parsedActivity.getConfigChanges();
        activityInfo.softInputMode = parsedActivity.getSoftInputMode();
        activityInfo.persistableMode = parsedActivity.getPersistableMode();
        activityInfo.lockTaskLaunchMode = parsedActivity.getLockTaskLaunchMode();
        activityInfo.screenOrientation = parsedActivity.getScreenOrientation();
        activityInfo.resizeMode = parsedActivity.getResizeMode();
        activityInfo.setMaxAspectRatio(parsedActivity.getMaxAspectRatio());
        activityInfo.setMinAspectRatio(parsedActivity.getMinAspectRatio());
        activityInfo.supportsSizeChanges = parsedActivity.isSupportsSizeChanges();
        activityInfo.requestedVrComponent = parsedActivity.getRequestedVrComponent();
        activityInfo.rotationAnimation = parsedActivity.getRotationAnimation();
        activityInfo.colorMode = parsedActivity.getColorMode();
        activityInfo.windowLayout = parsedActivity.getWindowLayout();
        activityInfo.attributionTags = parsedActivity.getAttributionTags();
        if ((j & 128) != 0) {
            Bundle metaData = parsedActivity.getMetaData();
            activityInfo.metaData = metaData.isEmpty() ? null : metaData;
        } else {
            activityInfo.metaData = null;
        }
        activityInfo.applicationInfo = applicationInfo;
        activityInfo.requiredDisplayCategory = parsedActivity.getRequiredDisplayCategory();
        activityInfo.requireContentUriPermissionFromCaller =
                parsedActivity.getRequireContentUriPermissionFromCaller();
        activityInfo.setKnownActivityEmbeddingCerts(
                parsedActivity.getKnownActivityEmbeddingCerts());
        assignFieldsComponentInfoParsedMainComponent(
                activityInfo, parsedActivity, packageStateInternal, i);
        return activityInfo;
    }

    public static ApplicationInfo generateApplicationInfo(
            AndroidPackage androidPackage,
            long j,
            PackageUserStateInternal packageUserStateInternal,
            int i,
            PackageStateInternal packageStateInternal) {
        int indexOf;
        ArrayList arrayList = null;
        if (androidPackage != null
                && checkUseInstalledOrHidden(packageStateInternal, packageUserStateInternal, j)) {
            if ((1048576 & j) != 0 ? packageStateInternal.isSystem() : true) {
                ApplicationInfo appInfoWithoutState =
                        ((AndroidPackageHidden) androidPackage).toAppInfoWithoutState();
                updateApplicationInfo(appInfoWithoutState, j, packageUserStateInternal);
                PackageImpl packageImpl = (PackageImpl) androidPackage;
                String packageName = androidPackage.getPackageName();
                appInfoWithoutState.uid =
                        UserHandle.getUid(i, UserHandle.getAppId(androidPackage.getUid()));
                if ("android".equals(packageName)) {
                    appInfoWithoutState.dataDir = SYSTEM_DATA_PATH;
                } else if (packageUserStateInternal.isInstalled()
                        || packageUserStateInternal.dataExists()
                        || !Flags.nullableDataDir()) {
                    if (i == 0) {
                        appInfoWithoutState.credentialProtectedDataDir =
                                packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser()
                                        + packageName;
                        appInfoWithoutState.deviceProtectedDataDir =
                                packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser()
                                        + packageName;
                    } else {
                        String valueOf = String.valueOf(i);
                        int length =
                                packageImpl
                                        .getBaseAppDataCredentialProtectedDirForSystemUser()
                                        .length();
                        StringBuilder replace =
                                new StringBuilder(
                                                packageImpl
                                                        .getBaseAppDataCredentialProtectedDirForSystemUser())
                                        .replace(length - 2, length - 1, valueOf);
                        replace.append(packageName);
                        appInfoWithoutState.credentialProtectedDataDir = replace.toString();
                        int length2 =
                                packageImpl
                                        .getBaseAppDataDeviceProtectedDirForSystemUser()
                                        .length();
                        StringBuilder replace2 =
                                new StringBuilder(
                                                packageImpl
                                                        .getBaseAppDataDeviceProtectedDirForSystemUser())
                                        .replace(length2 - 2, length2 - 1, valueOf);
                        replace2.append(packageName);
                        appInfoWithoutState.deviceProtectedDataDir = replace2.toString();
                    }
                    if (androidPackage.isDefaultToDeviceProtectedStorage()) {
                        appInfoWithoutState.dataDir = appInfoWithoutState.deviceProtectedDataDir;
                    } else {
                        appInfoWithoutState.dataDir =
                                appInfoWithoutState.credentialProtectedDataDir;
                    }
                } else {
                    appInfoWithoutState.dataDir = null;
                }
                PackageStateUnserialized transientState = packageStateInternal.getTransientState();
                appInfoWithoutState.hiddenUntilInstalled = transientState.hiddenUntilInstalled;
                List list = transientState.usesLibraryFiles;
                List list2 = transientState.usesLibraryInfos;
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    arrayList2.add(((SharedLibraryWrapper) list2.get(i2)).mInfo);
                }
                appInfoWithoutState.sharedLibraryFiles =
                        list.isEmpty() ? null : (String[]) list.toArray(new String[0]);
                if (Flags.sdkLibIndependence()) {
                    appInfoWithoutState.sharedLibraryInfos =
                            arrayList2.isEmpty() ? null : arrayList2;
                    String[] usesSdkLibraries = packageStateInternal.getUsesSdkLibraries();
                    boolean[] usesSdkLibrariesOptional =
                            packageStateInternal.getUsesSdkLibrariesOptional();
                    if (!ArrayUtils.isEmpty(usesSdkLibrariesOptional)
                            && !ArrayUtils.isEmpty(usesSdkLibraries)
                            && usesSdkLibraries.length == usesSdkLibrariesOptional.length) {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) it.next();
                            if (sharedLibraryInfo.getType() == 3
                                    && (indexOf =
                                                    ArrayUtils.indexOf(
                                                            usesSdkLibraries,
                                                            sharedLibraryInfo.getName()))
                                            >= 0
                                    && usesSdkLibrariesOptional[indexOf]) {
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                arrayList.add(sharedLibraryInfo);
                            }
                        }
                    }
                    appInfoWithoutState.optionalSharedLibraryInfos = arrayList;
                } else {
                    if (arrayList2.isEmpty()) {
                        arrayList2 = null;
                    }
                    appInfoWithoutState.sharedLibraryInfos = arrayList2;
                    appInfoWithoutState.optionalSharedLibraryInfos = null;
                }
                int categoryOverride = packageStateInternal.getCategoryOverride();
                if (categoryOverride != -1) {
                    appInfoWithoutState.category = categoryOverride;
                }
                AppCategoryHintHelper appCategoryHintHelper =
                        PackageManagerService.sAppCategoryHintHelper;
                String packageName2 = packageStateInternal.getPackageName();
                int i3 = appInfoWithoutState.category;
                if (appCategoryHintHelper.mInit.get()) {
                    int packageCategory =
                            appCategoryHintHelper.mAppCategoryFilter.getPackageCategory(
                                    packageName2);
                    synchronized (appCategoryHintHelper.mCategoryMap) {
                        try {
                            if (appCategoryHintHelper.mCategoryMap.containsKey(packageName2)) {
                                i3 =
                                        ((Integer)
                                                        appCategoryHintHelper.mCategoryMap.get(
                                                                packageName2))
                                                .intValue();
                            } else if (packageCategory != -1) {
                                i3 = packageCategory;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } else {
                    Slog.d(
                            "AppCategoryHintHelper",
                            "AppCategoryHintHelper is not initialized, return category without"
                                + " user's setting");
                }
                appInfoWithoutState.category = i3;
                appInfoWithoutState.seInfo = packageStateInternal.getSeInfo();
                appInfoWithoutState.primaryCpuAbi = packageStateInternal.getPrimaryCpuAbi();
                appInfoWithoutState.secondaryCpuAbi = packageStateInternal.getSecondaryCpuAbi();
                int i4 = appInfoWithoutState.flags;
                appInfoWithoutState.flags =
                        i4 | (packageStateInternal.isUpdatedSystemApp() ? 128 : 0) | i4;
                appInfoWithoutState.privateFlags = appInfoWithoutState.privateFlags;
                int i5 = appInfoWithoutState.privateFlagsExt;
                appInfoWithoutState.privateFlagsExt =
                        i5 | i5 | (packageStateInternal.getCpuAbiOverride() != null ? 32 : 0);
                return appInfoWithoutState;
            }
        }
        return null;
    }

    public static ApplicationInfo generateDelegateApplicationInfo(
            ApplicationInfo applicationInfo,
            long j,
            PackageUserStateInternal packageUserStateInternal,
            int i) {
        int i2;
        if (applicationInfo == null
                || !checkUseInstalledOrHidden(j, packageUserStateInternal, applicationInfo)) {
            return null;
        }
        ApplicationInfo applicationInfo2 = new ApplicationInfo(applicationInfo);
        applicationInfo2.initForUser(i);
        if (!ParsingPackageUtils.sUseRoundIcon || (i2 = applicationInfo2.roundIconRes) == 0) {
            i2 = applicationInfo2.iconRes;
        }
        applicationInfo2.icon = i2;
        updateApplicationInfo(applicationInfo2, j, packageUserStateInternal);
        return applicationInfo2;
    }

    public static InstrumentationInfo generateInstrumentationInfo(
            ParsedInstrumentation parsedInstrumentation,
            AndroidPackage androidPackage,
            long j,
            PackageUserStateInternal packageUserStateInternal,
            int i,
            PackageStateInternal packageStateInternal) {
        if (parsedInstrumentation == null
                || !checkUseInstalledOrHidden(packageStateInternal, packageUserStateInternal, j)) {
            return null;
        }
        InstrumentationInfo instrumentationInfo = new InstrumentationInfo();
        instrumentationInfo.targetPackage = parsedInstrumentation.getTargetPackage();
        instrumentationInfo.targetProcesses = parsedInstrumentation.getTargetProcesses();
        instrumentationInfo.handleProfiling = parsedInstrumentation.isHandleProfiling();
        instrumentationInfo.functionalTest = parsedInstrumentation.isFunctionalTest();
        instrumentationInfo.sourceDir = androidPackage.getBaseApkPath();
        instrumentationInfo.publicSourceDir = androidPackage.getBaseApkPath();
        instrumentationInfo.splitNames = androidPackage.getSplitNames();
        instrumentationInfo.splitSourceDirs =
                androidPackage.getSplitCodePaths().length == 0
                        ? null
                        : androidPackage.getSplitCodePaths();
        instrumentationInfo.splitPublicSourceDirs =
                androidPackage.getSplitCodePaths().length == 0
                        ? null
                        : androidPackage.getSplitCodePaths();
        instrumentationInfo.splitDependencies =
                androidPackage.getSplitDependencies().size() == 0
                        ? null
                        : androidPackage.getSplitDependencies();
        PackageImpl packageImpl = (PackageImpl) androidPackage;
        String packageName = androidPackage.getPackageName();
        if ("android".equals(packageName)) {
            instrumentationInfo.dataDir = SYSTEM_DATA_PATH;
        } else if (packageUserStateInternal.isInstalled()
                || packageUserStateInternal.dataExists()
                || !Flags.nullableDataDir()) {
            if (i == 0) {
                instrumentationInfo.credentialProtectedDataDir =
                        packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser()
                                + packageName;
                instrumentationInfo.deviceProtectedDataDir =
                        packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser() + packageName;
            } else {
                String valueOf = String.valueOf(i);
                int length =
                        packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser().length();
                StringBuilder replace =
                        new StringBuilder(
                                        packageImpl
                                                .getBaseAppDataCredentialProtectedDirForSystemUser())
                                .replace(length - 2, length - 1, valueOf);
                replace.append(packageName);
                instrumentationInfo.credentialProtectedDataDir = replace.toString();
                int length2 = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser().length();
                StringBuilder replace2 =
                        new StringBuilder(
                                        packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser())
                                .replace(length2 - 2, length2 - 1, valueOf);
                replace2.append(packageName);
                instrumentationInfo.deviceProtectedDataDir = replace2.toString();
            }
            if (androidPackage.isDefaultToDeviceProtectedStorage()) {
                instrumentationInfo.dataDir = instrumentationInfo.deviceProtectedDataDir;
            } else {
                instrumentationInfo.dataDir = instrumentationInfo.credentialProtectedDataDir;
            }
        } else {
            instrumentationInfo.dataDir = null;
        }
        instrumentationInfo.primaryCpuAbi = packageStateInternal.getPrimaryCpuAbi();
        instrumentationInfo.secondaryCpuAbi = packageStateInternal.getSecondaryCpuAbi();
        instrumentationInfo.nativeLibraryDir = androidPackage.getNativeLibraryDir();
        instrumentationInfo.secondaryNativeLibraryDir =
                androidPackage.getSecondaryNativeLibraryDir();
        assignFieldsPackageItemInfoParsedComponent(instrumentationInfo, parsedInstrumentation);
        Pair nonLocalizedLabelAndIcon =
                ParsedComponentStateUtils.getNonLocalizedLabelAndIcon(
                        parsedInstrumentation, packageStateInternal, i);
        ((PackageItemInfo) instrumentationInfo).nonLocalizedLabel =
                (CharSequence) nonLocalizedLabelAndIcon.first;
        ((PackageItemInfo) instrumentationInfo).icon =
                ((Integer) nonLocalizedLabelAndIcon.second).intValue();
        if ((j & 128) == 0) {
            instrumentationInfo.metaData = null;
        } else {
            Bundle metaData = parsedInstrumentation.getMetaData();
            instrumentationInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        return instrumentationInfo;
    }

    public static PermissionGroupInfo generatePermissionGroupInfo(
            ParsedPermissionGroup parsedPermissionGroup, long j) {
        if (parsedPermissionGroup == null) {
            return null;
        }
        PermissionGroupInfo permissionGroupInfo =
                new PermissionGroupInfo(
                        parsedPermissionGroup.getRequestDetailRes(),
                        parsedPermissionGroup.getBackgroundRequestRes(),
                        parsedPermissionGroup.getBackgroundRequestDetailRes());
        assignFieldsPackageItemInfoParsedComponent(permissionGroupInfo, parsedPermissionGroup);
        permissionGroupInfo.descriptionRes = parsedPermissionGroup.getDescriptionRes();
        permissionGroupInfo.priority = parsedPermissionGroup.getPriority();
        permissionGroupInfo.requestRes = parsedPermissionGroup.getRequestRes();
        permissionGroupInfo.flags = parsedPermissionGroup.getFlags();
        if ((j & 128) == 0) {
            permissionGroupInfo.metaData = null;
        } else {
            Bundle metaData = parsedPermissionGroup.getMetaData();
            permissionGroupInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        return permissionGroupInfo;
    }

    public static PermissionInfo generatePermissionInfo(ParsedPermission parsedPermission, long j) {
        if (parsedPermission == null) {
            return null;
        }
        PermissionInfo permissionInfo =
                new PermissionInfo(parsedPermission.getBackgroundPermission());
        assignFieldsPackageItemInfoParsedComponent(permissionInfo, parsedPermission);
        permissionInfo.group = parsedPermission.getGroup();
        permissionInfo.requestRes = parsedPermission.getRequestRes();
        permissionInfo.protectionLevel = parsedPermission.getProtectionLevel();
        permissionInfo.descriptionRes = parsedPermission.getDescriptionRes();
        permissionInfo.flags = parsedPermission.getFlags();
        permissionInfo.knownCerts = parsedPermission.getKnownCerts();
        if ((j & 128) == 0) {
            permissionInfo.metaData = null;
        } else {
            Bundle metaData = parsedPermission.getMetaData();
            permissionInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        return permissionInfo;
    }

    public static ArrayMap generateProcessInfo(Map map) {
        if (map == null) {
            return null;
        }
        ArrayMap arrayMap = new ArrayMap(map.size());
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            ParsedProcess parsedProcess = (ParsedProcess) map.get((String) it.next());
            arrayMap.put(
                    parsedProcess.getName(),
                    new ProcessInfo(
                            parsedProcess.getName(),
                            new ArraySet(parsedProcess.getDeniedPermissions()),
                            parsedProcess.getGwpAsanMode(),
                            parsedProcess.getMemtagMode(),
                            parsedProcess.getNativeHeapZeroInitialized(),
                            parsedProcess.isUseEmbeddedDex()));
        }
        return arrayMap;
    }

    public static ProviderInfo generateProviderInfo(
            AndroidPackage androidPackage,
            ParsedProvider parsedProvider,
            long j,
            PackageUserStateInternal packageUserStateInternal,
            ApplicationInfo applicationInfo,
            int i,
            PackageStateInternal packageStateInternal) {
        if (parsedProvider == null
                || !checkUseInstalledOrHidden(packageStateInternal, packageUserStateInternal, j)) {
            return null;
        }
        if (applicationInfo == null
                || !androidPackage.getPackageName().equals(applicationInfo.packageName)) {
            StringBuilder sb = new StringBuilder("AppInfo's package name is different. Expected=");
            sb.append(androidPackage.getPackageName());
            sb.append(" actual=");
            sb.append(applicationInfo == null ? "(null AppInfo)" : applicationInfo.packageName);
            android.util.Slog.wtf("PackageParsing", sb.toString());
            applicationInfo =
                    generateApplicationInfo(
                            androidPackage, j, packageUserStateInternal, i, packageStateInternal);
        }
        if (applicationInfo == null) {
            return null;
        }
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.exported = parsedProvider.isExported();
        providerInfo.flags = parsedProvider.getFlags();
        providerInfo.processName = parsedProvider.getProcessName();
        providerInfo.authority = parsedProvider.getAuthority();
        providerInfo.isSyncable = parsedProvider.isSyncable();
        providerInfo.readPermission = parsedProvider.getReadPermission();
        providerInfo.writePermission = parsedProvider.getWritePermission();
        providerInfo.grantUriPermissions = parsedProvider.isGrantUriPermissions();
        providerInfo.forceUriPermissions = parsedProvider.isForceUriPermissions();
        providerInfo.multiprocess = parsedProvider.isMultiProcess();
        providerInfo.initOrder = parsedProvider.getInitOrder();
        providerInfo.uriPermissionPatterns =
                (PatternMatcher[])
                        parsedProvider.getUriPermissionPatterns().toArray(new PatternMatcher[0]);
        providerInfo.pathPermissions =
                (PathPermission[])
                        parsedProvider.getPathPermissions().toArray(new PathPermission[0]);
        if ((2048 & j) == 0) {
            providerInfo.uriPermissionPatterns = null;
        }
        if ((j & 128) != 0) {
            Bundle metaData = parsedProvider.getMetaData();
            providerInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        providerInfo.applicationInfo = applicationInfo;
        assignFieldsComponentInfoParsedMainComponent(
                providerInfo, parsedProvider, packageStateInternal, i);
        return providerInfo;
    }

    public static ServiceInfo generateServiceInfo(
            AndroidPackage androidPackage,
            ParsedService parsedService,
            long j,
            PackageUserStateInternal packageUserStateInternal,
            ApplicationInfo applicationInfo,
            int i,
            PackageStateInternal packageStateInternal) {
        if (parsedService == null
                || !checkUseInstalledOrHidden(packageStateInternal, packageUserStateInternal, j)) {
            return null;
        }
        if (applicationInfo == null) {
            applicationInfo =
                    generateApplicationInfo(
                            androidPackage, j, packageUserStateInternal, i, packageStateInternal);
        }
        if (applicationInfo == null) {
            return null;
        }
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.exported = parsedService.isExported();
        serviceInfo.flags = parsedService.getFlags();
        serviceInfo.permission = parsedService.getPermission();
        serviceInfo.processName = parsedService.getProcessName();
        serviceInfo.mForegroundServiceType = parsedService.getForegroundServiceType();
        serviceInfo.applicationInfo = applicationInfo;
        if ((j & 128) != 0) {
            Bundle metaData = parsedService.getMetaData();
            serviceInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        assignFieldsComponentInfoParsedMainComponent(
                serviceInfo, parsedService, packageStateInternal, i);
        return serviceInfo;
    }

    public static File getDataDir(PackageStateInternal packageStateInternal, int i) {
        if ("android".equals(packageStateInternal.getPackageName())) {
            return Environment.getDataSystemDirectory();
        }
        if (packageStateInternal.getUserStateOrDefault(i).isInstalled()
                || packageStateInternal.getUserStateOrDefault(i).dataExists()
                || !Flags.nullableDataDir()) {
            return packageStateInternal.isDefaultToDeviceProtectedStorage()
                    ? Environment.getDataUserDePackageDirectory(
                            packageStateInternal.getVolumeUuid(),
                            i,
                            packageStateInternal.getPackageName())
                    : Environment.getDataUserCePackageDirectory(
                            packageStateInternal.getVolumeUuid(),
                            i,
                            packageStateInternal.getPackageName());
        }
        return null;
    }

    public static Signature[] getDeprecatedSignatures(SigningDetails signingDetails, long j) {
        if ((j & 64) == 0) {
            return null;
        }
        if (signingDetails.hasPastSigningCertificates()) {
            return new Signature[] {signingDetails.getPastSigningCertificates()[0]};
        }
        if (!signingDetails.hasSignatures()) {
            return null;
        }
        int length = signingDetails.getSignatures().length;
        Signature[] signatureArr = new Signature[length];
        System.arraycopy(signingDetails.getSignatures(), 0, signatureArr, 0, length);
        return signatureArr;
    }

    public static void updateApplicationInfo(
            ApplicationInfo applicationInfo, long j, PackageUserState packageUserState) {
        if ((128 & j) == 0) {
            applicationInfo.metaData = null;
        }
        if ((1024 & j) == 0) {
            applicationInfo.sharedLibraryFiles = null;
            applicationInfo.sharedLibraryInfos = null;
        }
        if (!ParsingPackageUtils.sCompatibilityModeEnabled) {
            applicationInfo.disableCompatibilityMode();
        }
        applicationInfo.flags |=
                (packageUserState.isStopped() ? 2097152 : 0)
                        | (packageUserState.isInstalled() ? 8388608 : 0)
                        | (packageUserState.isSuspended() ? 1073741824 : 0);
        applicationInfo.privateFlags |=
                (packageUserState.isInstantApp() ? 128 : 0)
                        | (packageUserState.isVirtualPreload()
                                ? EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT
                                : 0)
                        | (packageUserState.isHidden() ? 1 : 0);
        applicationInfo.privateFlagsExt |= packageUserState.isNotLaunched() ? 64 : 0;
        boolean z = true;
        if (packageUserState.getEnabledState() == 1) {
            applicationInfo.enabled = true;
        } else if (packageUserState.getEnabledState() == 4) {
            if ((32768 & j) == 0 && (j & 536870912) == 0) {
                z = false;
            }
            applicationInfo.enabled = z;
        } else if (packageUserState.getEnabledState() == 2
                || packageUserState.getEnabledState() == 3) {
            applicationInfo.enabled = false;
        }
        applicationInfo.enabledSetting = packageUserState.getEnabledState();
        if (applicationInfo.category == -1) {
            applicationInfo.category =
                    FallbackCategoryProvider.getFallbackCategory(applicationInfo.packageName);
        }
        applicationInfo.seInfoUser =
                packageUserState.isInstantApp() ? ":ephemeralapp:complete" : ":complete";
        OverlayPaths allOverlayPaths = packageUserState.getAllOverlayPaths();
        if (allOverlayPaths != null) {
            applicationInfo.resourceDirs =
                    (String[]) allOverlayPaths.getResourceDirs().toArray(new String[0]);
            applicationInfo.overlayPaths =
                    (String[]) allOverlayPaths.getOverlayPaths().toArray(new String[0]);
        }
        boolean isArchived = PackageArchiver.isArchived(packageUserState);
        applicationInfo.isArchived = isArchived;
        if (isArchived) {
            applicationInfo.nonLocalizedLabel =
                    ((ArchiveState.ArchiveActivityInfo)
                                    packageUserState.getArchiveState().mActivityInfos.get(0))
                            .mTitle;
        }
        if (packageUserState.isInstalled()
                || packageUserState.dataExists()
                || !Flags.nullableDataDir()) {
            return;
        }
        applicationInfo.dataDir = null;
    }
}
