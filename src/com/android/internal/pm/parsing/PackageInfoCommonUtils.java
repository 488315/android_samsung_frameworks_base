package com.android.internal.pm.parsing;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.Attribution;
import android.content.pm.ComponentInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FallbackCategoryProvider;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PathPermission;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils;
import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.pm.pkg.SEInfoUtil;
import com.android.internal.pm.pkg.component.ComponentParseUtils;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedAttribution;
import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.pm.pkg.component.ParsedUsesPermission;
import com.android.internal.pm.pkg.parsing.ParsingPackageHidden;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.pkg.AndroidPackage;
import java.util.List;

/* loaded from: classes5.dex */
public class PackageInfoCommonUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "PackageParsing";

    private static boolean reportIfDebug(boolean z, long j) {
        return z;
    }

    public static PackageInfo generate(AndroidPackage androidPackage, long j, int i) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        if (androidPackage == null) {
            return null;
        }
        ApplicationInfo applicationInfoGenerateApplicationInfo = generateApplicationInfo(androidPackage, j, i);
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.packageName = androidPackage.getPackageName();
        packageInfo.splitNames = androidPackage.getSplitNames();
        ParsingPackageHidden parsingPackageHidden = (ParsingPackageHidden) androidPackage;
        packageInfo.versionCode = parsingPackageHidden.getVersionCode();
        packageInfo.versionCodeMajor = parsingPackageHidden.getVersionCodeMajor();
        packageInfo.baseRevisionCode = androidPackage.getBaseRevisionCode();
        packageInfo.splitRevisionCodes = androidPackage.getSplitRevisionCodes();
        packageInfo.versionName = androidPackage.getVersionName();
        if (!androidPackage.isLeavingSharedUser()) {
            packageInfo.sharedUserId = androidPackage.getSharedUserId();
            packageInfo.sharedUserLabel = androidPackage.getSharedUserLabelResourceId();
        }
        packageInfo.applicationInfo = applicationInfoGenerateApplicationInfo;
        packageInfo.installLocation = androidPackage.getInstallLocation();
        if ((packageInfo.applicationInfo.flags & 1) != 0 || (packageInfo.applicationInfo.flags & 128) != 0) {
            packageInfo.requiredForAllUsers = androidPackage.isRequiredForAllUsers();
        }
        packageInfo.restrictedAccountType = androidPackage.getRestrictedAccountType();
        packageInfo.requiredAccountType = androidPackage.getRequiredAccountType();
        packageInfo.overlayTarget = androidPackage.getOverlayTarget();
        packageInfo.targetOverlayableName = androidPackage.getOverlayTargetOverlayableName();
        packageInfo.overlayCategory = androidPackage.getOverlayCategory();
        packageInfo.overlayPriority = androidPackage.getOverlayPriority();
        packageInfo.mOverlayIsStatic = androidPackage.isOverlayIsStatic();
        packageInfo.compileSdkVersion = androidPackage.getCompileSdkVersion();
        packageInfo.compileSdkVersionCodename = androidPackage.getCompileSdkVersionCodeName();
        packageInfo.isStub = androidPackage.isStub();
        packageInfo.coreApp = androidPackage.isCoreApp();
        packageInfo.isApex = androidPackage.isApex();
        if ((16384 & j) != 0) {
            int size6 = androidPackage.getConfigPreferences().size();
            if (size6 > 0) {
                packageInfo.configPreferences = new ConfigurationInfo[size6];
                androidPackage.getConfigPreferences().toArray(packageInfo.configPreferences);
            }
            int size7 = androidPackage.getRequestedFeatures().size();
            if (size7 > 0) {
                packageInfo.reqFeatures = new FeatureInfo[size7];
                androidPackage.getRequestedFeatures().toArray(packageInfo.reqFeatures);
            }
            int size8 = androidPackage.getFeatureGroups().size();
            if (size8 > 0) {
                packageInfo.featureGroups = new FeatureGroupInfo[size8];
                androidPackage.getFeatureGroups().toArray(packageInfo.featureGroups);
            }
        }
        if ((4096 & j) != 0) {
            int size9 = ArrayUtils.size(androidPackage.getPermissions());
            if (size9 > 0) {
                packageInfo.permissions = new PermissionInfo[size9];
                for (int i2 = 0; i2 < size9; i2++) {
                    packageInfo.permissions[i2] = generatePermissionInfo(androidPackage.getPermissions().get(i2), j);
                }
            }
            List<ParsedUsesPermission> usesPermissions = androidPackage.getUsesPermissions();
            int size10 = usesPermissions.size();
            if (size10 > 0) {
                packageInfo.requestedPermissions = new String[size10];
                packageInfo.requestedPermissionsFlags = new int[size10];
                for (int i3 = 0; i3 < size10; i3++) {
                    ParsedUsesPermission parsedUsesPermission = usesPermissions.get(i3);
                    packageInfo.requestedPermissions[i3] = parsedUsesPermission.getName();
                    int[] iArr = packageInfo.requestedPermissionsFlags;
                    iArr[i3] = iArr[i3] | 1;
                    if ((parsedUsesPermission.getUsesPermissionFlags() & 65536) != 0) {
                        int[] iArr2 = packageInfo.requestedPermissionsFlags;
                        iArr2[i3] = 65536 | iArr2[i3];
                    }
                    if (androidPackage.getImplicitPermissions().contains(packageInfo.requestedPermissions[i3])) {
                        int[] iArr3 = packageInfo.requestedPermissionsFlags;
                        iArr3[i3] = iArr3[i3] | 4;
                    }
                }
            }
        }
        if ((2147483648L & j) != 0) {
            int size11 = ArrayUtils.size(androidPackage.getAttributions());
            if (size11 > 0) {
                packageInfo.attributions = new Attribution[size11];
                for (int i4 = 0; i4 < size11; i4++) {
                    ParsedAttribution parsedAttribution = androidPackage.getAttributions().get(i4);
                    if (parsedAttribution != null) {
                        packageInfo.attributions[i4] = new Attribution(parsedAttribution.getTag(), parsedAttribution.getLabel());
                    }
                }
            }
            if (androidPackage.isAttributionsUserVisible()) {
                packageInfo.applicationInfo.privateFlagsExt |= 4;
            } else {
                packageInfo.applicationInfo.privateFlagsExt &= -5;
            }
        } else {
            packageInfo.applicationInfo.privateFlagsExt &= -5;
        }
        SigningDetails signingDetails = androidPackage.getSigningDetails();
        if ((64 & j) != 0) {
            if (signingDetails.hasPastSigningCertificates()) {
                packageInfo.signatures = new Signature[1];
                packageInfo.signatures[0] = signingDetails.getPastSigningCertificates()[0];
            } else if (signingDetails.hasSignatures()) {
                int length = signingDetails.getSignatures().length;
                packageInfo.signatures = new Signature[length];
                System.arraycopy(signingDetails.getSignatures(), 0, packageInfo.signatures, 0, length);
            }
        }
        if ((134217728 & j) != 0) {
            if (signingDetails != SigningDetails.UNKNOWN) {
                packageInfo.signingInfo = new SigningInfo(signingDetails);
            } else {
                packageInfo.signingInfo = null;
            }
        }
        if ((1 & j) != 0 && (size5 = androidPackage.getActivities().size()) > 0) {
            ActivityInfo[] activityInfoArr = new ActivityInfo[size5];
            int i5 = 0;
            for (int i6 = 0; i6 < size5; i6++) {
                ParsedActivity parsedActivity = androidPackage.getActivities().get(i6);
                if (isMatch(androidPackage, parsedActivity.isDirectBootAware(), j) && !PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(parsedActivity.getName())) {
                    activityInfoArr[i5] = generateActivityInfo(parsedActivity, j, applicationInfoGenerateApplicationInfo);
                    i5++;
                }
            }
            packageInfo.activities = (ActivityInfo[]) ArrayUtils.trimToSize(activityInfoArr, i5);
        }
        if ((2 & j) != 0 && (size4 = androidPackage.getReceivers().size()) > 0) {
            ActivityInfo[] activityInfoArr2 = new ActivityInfo[size4];
            int i7 = 0;
            for (int i8 = 0; i8 < size4; i8++) {
                ParsedActivity parsedActivity2 = androidPackage.getReceivers().get(i8);
                if (isMatch(androidPackage, parsedActivity2.isDirectBootAware(), j)) {
                    activityInfoArr2[i7] = generateActivityInfo(parsedActivity2, j, applicationInfoGenerateApplicationInfo);
                    i7++;
                }
            }
            packageInfo.receivers = (ActivityInfo[]) ArrayUtils.trimToSize(activityInfoArr2, i7);
        }
        if ((4 & j) != 0 && (size3 = androidPackage.getServices().size()) > 0) {
            ServiceInfo[] serviceInfoArr = new ServiceInfo[size3];
            int i9 = 0;
            for (int i10 = 0; i10 < size3; i10++) {
                ParsedService parsedService = androidPackage.getServices().get(i10);
                if (isMatch(androidPackage, parsedService.isDirectBootAware(), j)) {
                    serviceInfoArr[i9] = generateServiceInfo(parsedService, j, applicationInfoGenerateApplicationInfo);
                    i9++;
                }
            }
            packageInfo.services = (ServiceInfo[]) ArrayUtils.trimToSize(serviceInfoArr, i9);
        }
        if ((8 & j) != 0 && (size2 = androidPackage.getProviders().size()) > 0) {
            ProviderInfo[] providerInfoArr = new ProviderInfo[size2];
            int i11 = 0;
            for (int i12 = 0; i12 < size2; i12++) {
                ParsedProvider parsedProvider = androidPackage.getProviders().get(i12);
                if (isMatch(androidPackage, parsedProvider.isDirectBootAware(), j)) {
                    providerInfoArr[i11] = generateProviderInfo(androidPackage, parsedProvider, j, applicationInfoGenerateApplicationInfo, i);
                    i11++;
                }
            }
            packageInfo.providers = (ProviderInfo[]) ArrayUtils.trimToSize(providerInfoArr, i11);
        }
        if ((16 & j) != 0 && (size = androidPackage.getInstrumentations().size()) > 0) {
            packageInfo.instrumentation = new InstrumentationInfo[size];
            for (int i13 = 0; i13 < size; i13++) {
                packageInfo.instrumentation[i13] = generateInstrumentationInfo(androidPackage.getInstrumentations().get(i13), androidPackage, j, i);
            }
        }
        return packageInfo;
    }

    private static void updateApplicationInfo(ApplicationInfo applicationInfo, long j) {
        if ((128 & j) == 0) {
            applicationInfo.metaData = null;
        }
        if ((j & 1024) == 0) {
            applicationInfo.sharedLibraryFiles = null;
            applicationInfo.sharedLibraryInfos = null;
        }
        if (!ParsingPackageUtils.sCompatibilityModeEnabled) {
            applicationInfo.disableCompatibilityMode();
        }
        if (applicationInfo.category == -1) {
            applicationInfo.category = FallbackCategoryProvider.getFallbackCategory(applicationInfo.packageName);
        }
        applicationInfo.seInfoUser = SEInfoUtil.COMPLETE_STR;
    }

    private static ApplicationInfo generateApplicationInfo(AndroidPackage androidPackage, long j, int i) {
        ApplicationInfo appInfoWithoutState = ((AndroidPackageHidden) androidPackage).toAppInfoWithoutState();
        updateApplicationInfo(appInfoWithoutState, j);
        initForUser(appInfoWithoutState, androidPackage, i);
        appInfoWithoutState.primaryCpuAbi = AndroidPackageLegacyUtils.getRawPrimaryCpuAbi(androidPackage);
        appInfoWithoutState.secondaryCpuAbi = AndroidPackageLegacyUtils.getRawSecondaryCpuAbi(androidPackage);
        if ((128 & j) != 0) {
            appInfoWithoutState.metaData = androidPackage.getMetaData();
        }
        if ((j & 1024) != 0) {
            List<String> usesLibraries = androidPackage.getUsesLibraries();
            appInfoWithoutState.sharedLibraryFiles = usesLibraries.isEmpty() ? null : (String[]) usesLibraries.toArray(new String[0]);
        }
        return appInfoWithoutState;
    }

    private static ActivityInfo generateActivityInfo(ParsedActivity parsedActivity, long j, ApplicationInfo applicationInfo) {
        if (parsedActivity == null) {
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
        activityInfo.setKnownActivityEmbeddingCerts(parsedActivity.getKnownActivityEmbeddingCerts());
        assignFieldsComponentInfoParsedMainComponent(activityInfo, parsedActivity);
        return activityInfo;
    }

    private static ServiceInfo generateServiceInfo(ParsedService parsedService, long j, ApplicationInfo applicationInfo) {
        if (parsedService == null) {
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
        assignFieldsComponentInfoParsedMainComponent(serviceInfo, parsedService);
        return serviceInfo;
    }

    private static ProviderInfo generateProviderInfo(AndroidPackage androidPackage, ParsedProvider parsedProvider, long j, ApplicationInfo applicationInfo, int i) {
        if (parsedProvider == null) {
            return null;
        }
        if (!androidPackage.getPackageName().equals(applicationInfo.packageName)) {
            Slog.wtf("PackageParsing", "AppInfo's package name is different. Expected=" + androidPackage.getPackageName() + " actual=" + applicationInfo.packageName);
            applicationInfo = generateApplicationInfo(androidPackage, j, i);
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
        providerInfo.uriPermissionPatterns = (PatternMatcher[]) parsedProvider.getUriPermissionPatterns().toArray(new PatternMatcher[0]);
        providerInfo.pathPermissions = (PathPermission[]) parsedProvider.getPathPermissions().toArray(new PathPermission[0]);
        if ((2048 & j) == 0) {
            providerInfo.uriPermissionPatterns = null;
        }
        if ((j & 128) != 0) {
            Bundle metaData = parsedProvider.getMetaData();
            providerInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        providerInfo.applicationInfo = applicationInfo;
        assignFieldsComponentInfoParsedMainComponent(providerInfo, parsedProvider);
        return providerInfo;
    }

    private static InstrumentationInfo generateInstrumentationInfo(ParsedInstrumentation parsedInstrumentation, AndroidPackage androidPackage, long j, int i) {
        if (parsedInstrumentation == null) {
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
        instrumentationInfo.splitSourceDirs = androidPackage.getSplitCodePaths().length == 0 ? null : androidPackage.getSplitCodePaths();
        instrumentationInfo.splitPublicSourceDirs = androidPackage.getSplitCodePaths().length == 0 ? null : androidPackage.getSplitCodePaths();
        instrumentationInfo.splitDependencies = androidPackage.getSplitDependencies().size() == 0 ? null : androidPackage.getSplitDependencies();
        initForUser(instrumentationInfo, androidPackage, i);
        instrumentationInfo.primaryCpuAbi = AndroidPackageLegacyUtils.getRawPrimaryCpuAbi(androidPackage);
        instrumentationInfo.secondaryCpuAbi = AndroidPackageLegacyUtils.getRawSecondaryCpuAbi(androidPackage);
        instrumentationInfo.nativeLibraryDir = androidPackage.getNativeLibraryDir();
        instrumentationInfo.secondaryNativeLibraryDir = androidPackage.getSecondaryNativeLibraryDir();
        assignFieldsPackageItemInfoParsedComponent(instrumentationInfo, parsedInstrumentation);
        if ((j & 128) == 0) {
            instrumentationInfo.metaData = null;
            return instrumentationInfo;
        }
        Bundle metaData = parsedInstrumentation.getMetaData();
        instrumentationInfo.metaData = metaData.isEmpty() ? null : metaData;
        return instrumentationInfo;
    }

    private static PermissionInfo generatePermissionInfo(ParsedPermission parsedPermission, long j) {
        if (parsedPermission == null) {
            return null;
        }
        PermissionInfo permissionInfo = new PermissionInfo(parsedPermission.getBackgroundPermission());
        assignFieldsPackageItemInfoParsedComponent(permissionInfo, parsedPermission);
        permissionInfo.group = parsedPermission.getGroup();
        permissionInfo.requestRes = parsedPermission.getRequestRes();
        permissionInfo.protectionLevel = parsedPermission.getProtectionLevel();
        permissionInfo.descriptionRes = parsedPermission.getDescriptionRes();
        permissionInfo.flags = parsedPermission.getFlags();
        permissionInfo.knownCerts = parsedPermission.getKnownCerts();
        if ((j & 128) == 0) {
            permissionInfo.metaData = null;
            return permissionInfo;
        }
        Bundle metaData = parsedPermission.getMetaData();
        permissionInfo.metaData = metaData.isEmpty() ? null : metaData;
        return permissionInfo;
    }

    private static void assignFieldsComponentInfoParsedMainComponent(ComponentInfo componentInfo, ParsedMainComponent parsedMainComponent) {
        assignFieldsPackageItemInfoParsedComponent(componentInfo, parsedMainComponent);
        componentInfo.descriptionRes = parsedMainComponent.getDescriptionRes();
        componentInfo.directBootAware = parsedMainComponent.isDirectBootAware();
        componentInfo.enabled = parsedMainComponent.isEnabled();
        componentInfo.splitName = parsedMainComponent.getSplitName();
        componentInfo.attributionTags = parsedMainComponent.getAttributionTags();
        componentInfo.nonLocalizedLabel = parsedMainComponent.getNonLocalizedLabel();
        componentInfo.icon = parsedMainComponent.getIcon();
    }

    private static void assignFieldsPackageItemInfoParsedComponent(PackageItemInfo packageItemInfo, ParsedComponent parsedComponent) {
        packageItemInfo.nonLocalizedLabel = ComponentParseUtils.getNonLocalizedLabel(parsedComponent);
        packageItemInfo.icon = ComponentParseUtils.getIcon(parsedComponent);
        packageItemInfo.banner = parsedComponent.getBanner();
        packageItemInfo.labelRes = parsedComponent.getLabelRes();
        packageItemInfo.logo = parsedComponent.getLogo();
        packageItemInfo.name = parsedComponent.getName();
        packageItemInfo.packageName = parsedComponent.getPackageName();
    }

    private static void initForUser(ApplicationInfo applicationInfo, AndroidPackage androidPackage, int i) {
        PackageImpl packageImpl = (PackageImpl) androidPackage;
        String packageName = androidPackage.getPackageName();
        applicationInfo.uid = UserHandle.getUid(i, UserHandle.getAppId(androidPackage.getUid()));
        String baseAppDataCredentialProtectedDirForSystemUser = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser();
        String baseAppDataDeviceProtectedDirForSystemUser = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser();
        if (baseAppDataCredentialProtectedDirForSystemUser != null && baseAppDataDeviceProtectedDirForSystemUser != null) {
            if (i == 0) {
                applicationInfo.credentialProtectedDataDir = baseAppDataCredentialProtectedDirForSystemUser + packageName;
                applicationInfo.deviceProtectedDataDir = baseAppDataDeviceProtectedDirForSystemUser + packageName;
            } else {
                String strValueOf = String.valueOf(i);
                int length = baseAppDataCredentialProtectedDirForSystemUser.length();
                StringBuilder sbReplace = new StringBuilder(baseAppDataCredentialProtectedDirForSystemUser).replace(length - 2, length - 1, strValueOf);
                sbReplace.append(packageName);
                applicationInfo.credentialProtectedDataDir = sbReplace.toString();
                int length2 = baseAppDataDeviceProtectedDirForSystemUser.length();
                StringBuilder sbReplace2 = new StringBuilder(baseAppDataDeviceProtectedDirForSystemUser).replace(length2 - 2, length2 - 1, strValueOf);
                sbReplace2.append(packageName);
                applicationInfo.deviceProtectedDataDir = sbReplace2.toString();
            }
        }
        if (androidPackage.isDefaultToDeviceProtectedStorage()) {
            applicationInfo.dataDir = applicationInfo.deviceProtectedDataDir;
        } else {
            applicationInfo.dataDir = applicationInfo.credentialProtectedDataDir;
        }
    }

    private static void initForUser(InstrumentationInfo instrumentationInfo, AndroidPackage androidPackage, int i) {
        PackageImpl packageImpl = (PackageImpl) androidPackage;
        String packageName = androidPackage.getPackageName();
        String baseAppDataCredentialProtectedDirForSystemUser = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser();
        String baseAppDataDeviceProtectedDirForSystemUser = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser();
        if (baseAppDataCredentialProtectedDirForSystemUser != null && baseAppDataDeviceProtectedDirForSystemUser != null) {
            if (i == 0) {
                instrumentationInfo.credentialProtectedDataDir = baseAppDataCredentialProtectedDirForSystemUser + packageName;
                instrumentationInfo.deviceProtectedDataDir = baseAppDataDeviceProtectedDirForSystemUser + packageName;
            } else {
                String strValueOf = String.valueOf(i);
                int length = baseAppDataCredentialProtectedDirForSystemUser.length();
                StringBuilder sbReplace = new StringBuilder(baseAppDataCredentialProtectedDirForSystemUser).replace(length - 2, length - 1, strValueOf);
                sbReplace.append(packageName);
                instrumentationInfo.credentialProtectedDataDir = sbReplace.toString();
                int length2 = baseAppDataDeviceProtectedDirForSystemUser.length();
                StringBuilder sbReplace2 = new StringBuilder(baseAppDataDeviceProtectedDirForSystemUser).replace(length2 - 2, length2 - 1, strValueOf);
                sbReplace2.append(packageName);
                instrumentationInfo.deviceProtectedDataDir = sbReplace2.toString();
            }
        }
        if (androidPackage.isDefaultToDeviceProtectedStorage()) {
            instrumentationInfo.dataDir = instrumentationInfo.deviceProtectedDataDir;
        } else {
            instrumentationInfo.dataDir = instrumentationInfo.credentialProtectedDataDir;
        }
    }

    private static boolean isMatch(AndroidPackage androidPackage, boolean z, long j) {
        boolean zIsSystem = ((AndroidPackageHidden) androidPackage).isSystem();
        if ((1048576 & j) != 0 && !zIsSystem) {
            return reportIfDebug(false, j);
        }
        return reportIfDebug((((262144 & j) > 0L ? 1 : ((262144 & j) == 0L ? 0 : -1)) != 0 && !z) || (((524288 & j) > 0L ? 1 : ((524288 & j) == 0L ? 0 : -1)) != 0 && z), j);
    }
}
