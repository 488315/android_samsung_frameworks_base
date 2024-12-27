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

public class PackageInfoCommonUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "PackageParsing";

    public static PackageInfo generate(AndroidPackage pkg, long flags, int userId) {
        int size;
        int size2;
        int i;
        int size3;
        int size4;
        int size5;
        if (pkg == null) {
            return null;
        }
        ApplicationInfo applicationInfo = generateApplicationInfo(pkg, flags, userId);
        PackageInfo info = new PackageInfo();
        info.packageName = pkg.getPackageName();
        info.splitNames = pkg.getSplitNames();
        info.versionCode = ((ParsingPackageHidden) pkg).getVersionCode();
        info.versionCodeMajor = ((ParsingPackageHidden) pkg).getVersionCodeMajor();
        info.baseRevisionCode = pkg.getBaseRevisionCode();
        info.splitRevisionCodes = pkg.getSplitRevisionCodes();
        info.versionName = pkg.getVersionName();
        if (!pkg.isLeavingSharedUser()) {
            info.sharedUserId = pkg.getSharedUserId();
            info.sharedUserLabel = pkg.getSharedUserLabelResourceId();
        }
        info.applicationInfo = applicationInfo;
        info.installLocation = pkg.getInstallLocation();
        if ((info.applicationInfo.flags & 1) != 0 || (info.applicationInfo.flags & 128) != 0) {
            info.requiredForAllUsers = pkg.isRequiredForAllUsers();
        }
        info.restrictedAccountType = pkg.getRestrictedAccountType();
        info.requiredAccountType = pkg.getRequiredAccountType();
        info.overlayTarget = pkg.getOverlayTarget();
        info.targetOverlayableName = pkg.getOverlayTargetOverlayableName();
        info.overlayCategory = pkg.getOverlayCategory();
        info.overlayPriority = pkg.getOverlayPriority();
        info.mOverlayIsStatic = pkg.isOverlayIsStatic();
        info.compileSdkVersion = pkg.getCompileSdkVersion();
        info.compileSdkVersionCodename = pkg.getCompileSdkVersionCodeName();
        info.isStub = pkg.isStub();
        info.coreApp = pkg.isCoreApp();
        info.isApex = pkg.isApex();
        if ((16384 & flags) != 0) {
            int size6 = pkg.getConfigPreferences().size();
            if (size6 > 0) {
                info.configPreferences = new ConfigurationInfo[size6];
                pkg.getConfigPreferences().toArray(info.configPreferences);
            }
            int size7 = pkg.getRequestedFeatures().size();
            if (size7 > 0) {
                info.reqFeatures = new FeatureInfo[size7];
                pkg.getRequestedFeatures().toArray(info.reqFeatures);
            }
            int size8 = pkg.getFeatureGroups().size();
            if (size8 > 0) {
                info.featureGroups = new FeatureGroupInfo[size8];
                pkg.getFeatureGroups().toArray(info.featureGroups);
            }
        }
        if ((4096 & flags) != 0) {
            int size9 = ArrayUtils.size(pkg.getPermissions());
            if (size9 > 0) {
                info.permissions = new PermissionInfo[size9];
                for (int i2 = 0; i2 < size9; i2++) {
                    ParsedPermission permission = pkg.getPermissions().get(i2);
                    PermissionInfo permissionInfo = generatePermissionInfo(permission, flags);
                    info.permissions[i2] = permissionInfo;
                }
            }
            List<ParsedUsesPermission> usesPermissions = pkg.getUsesPermissions();
            int size10 = usesPermissions.size();
            if (size10 > 0) {
                info.requestedPermissions = new String[size10];
                info.requestedPermissionsFlags = new int[size10];
                for (int i3 = 0; i3 < size10; i3++) {
                    ParsedUsesPermission usesPermission = usesPermissions.get(i3);
                    info.requestedPermissions[i3] = usesPermission.getName();
                    int[] iArr = info.requestedPermissionsFlags;
                    iArr[i3] = iArr[i3] | 1;
                    if ((usesPermission.getUsesPermissionFlags() & 65536) != 0) {
                        int[] iArr2 = info.requestedPermissionsFlags;
                        iArr2[i3] = 65536 | iArr2[i3];
                    }
                    if (pkg.getImplicitPermissions().contains(info.requestedPermissions[i3])) {
                        int[] iArr3 = info.requestedPermissionsFlags;
                        iArr3[i3] = iArr3[i3] | 4;
                    }
                }
            }
        }
        if ((2147483648L & flags) != 0) {
            int size11 = ArrayUtils.size(pkg.getAttributions());
            if (size11 > 0) {
                info.attributions = new Attribution[size11];
                for (int i4 = 0; i4 < size11; i4++) {
                    ParsedAttribution parsedAttribution = pkg.getAttributions().get(i4);
                    if (parsedAttribution != null) {
                        info.attributions[i4] =
                                new Attribution(
                                        parsedAttribution.getTag(), parsedAttribution.getLabel());
                    }
                }
            }
            if (pkg.isAttributionsUserVisible()) {
                info.applicationInfo.privateFlagsExt |= 4;
            } else {
                info.applicationInfo.privateFlagsExt &= -5;
            }
        } else {
            info.applicationInfo.privateFlagsExt &= -5;
        }
        SigningDetails signingDetails = pkg.getSigningDetails();
        if ((64 & flags) != 0) {
            if (signingDetails.hasPastSigningCertificates()) {
                info.signatures = new Signature[1];
                info.signatures[0] = signingDetails.getPastSigningCertificates()[0];
            } else if (signingDetails.hasSignatures()) {
                int numberOfSigs = signingDetails.getSignatures().length;
                info.signatures = new Signature[numberOfSigs];
                System.arraycopy(
                        signingDetails.getSignatures(), 0, info.signatures, 0, numberOfSigs);
            }
        }
        if ((134217728 & flags) != 0) {
            if (signingDetails != SigningDetails.UNKNOWN) {
                info.signingInfo = new SigningInfo(signingDetails);
            } else {
                info.signingInfo = null;
            }
        }
        if ((1 & flags) != 0 && (size5 = pkg.getActivities().size()) > 0) {
            int num = 0;
            ActivityInfo[] res = new ActivityInfo[size5];
            for (int i5 = 0; i5 < size5; i5++) {
                ParsedActivity a = pkg.getActivities().get(i5);
                if (isMatch(pkg, a.isDirectBootAware(), flags)
                        && !PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(a.getName())) {
                    res[num] = generateActivityInfo(a, flags, applicationInfo);
                    num++;
                }
            }
            info.activities = (ActivityInfo[]) ArrayUtils.trimToSize(res, num);
        }
        if ((2 & flags) != 0 && (size4 = pkg.getReceivers().size()) > 0) {
            int num2 = 0;
            ActivityInfo[] res2 = new ActivityInfo[size4];
            for (int i6 = 0; i6 < size4; i6++) {
                ParsedActivity a2 = pkg.getReceivers().get(i6);
                if (isMatch(pkg, a2.isDirectBootAware(), flags)) {
                    res2[num2] = generateActivityInfo(a2, flags, applicationInfo);
                    num2++;
                }
            }
            info.receivers = (ActivityInfo[]) ArrayUtils.trimToSize(res2, num2);
        }
        if ((4 & flags) != 0 && (size3 = pkg.getServices().size()) > 0) {
            int num3 = 0;
            ServiceInfo[] res3 = new ServiceInfo[size3];
            for (int i7 = 0; i7 < size3; i7++) {
                ParsedService s = pkg.getServices().get(i7);
                if (isMatch(pkg, s.isDirectBootAware(), flags)) {
                    res3[num3] = generateServiceInfo(s, flags, applicationInfo);
                    num3++;
                }
            }
            info.services = (ServiceInfo[]) ArrayUtils.trimToSize(res3, num3);
        }
        if ((8 & flags) != 0 && (size2 = pkg.getProviders().size()) > 0) {
            ProviderInfo[] res4 = new ProviderInfo[size2];
            int num4 = 0;
            int i8 = 0;
            while (i8 < size2) {
                ParsedProvider pr = pkg.getProviders().get(i8);
                if (!isMatch(pkg, pr.isDirectBootAware(), flags)) {
                    i = i8;
                } else {
                    i = i8;
                    res4[num4] = generateProviderInfo(pkg, pr, flags, applicationInfo, userId);
                    num4++;
                }
                i8 = i + 1;
            }
            info.providers = (ProviderInfo[]) ArrayUtils.trimToSize(res4, num4);
        }
        if ((16 & flags) != 0 && (size = pkg.getInstrumentations().size()) > 0) {
            info.instrumentation = new InstrumentationInfo[size];
            for (int i9 = 0; i9 < size; i9++) {
                info.instrumentation[i9] =
                        generateInstrumentationInfo(
                                pkg.getInstrumentations().get(i9), pkg, flags, userId);
            }
        }
        return info;
    }

    private static void updateApplicationInfo(ApplicationInfo ai, long flags) {
        if ((128 & flags) == 0) {
            ai.metaData = null;
        }
        if ((1024 & flags) == 0) {
            ai.sharedLibraryFiles = null;
            ai.sharedLibraryInfos = null;
        }
        if (!ParsingPackageUtils.sCompatibilityModeEnabled) {
            ai.disableCompatibilityMode();
        }
        if (ai.category == -1) {
            ai.category = FallbackCategoryProvider.getFallbackCategory(ai.packageName);
        }
        ai.seInfoUser = SEInfoUtil.COMPLETE_STR;
    }

    private static ApplicationInfo generateApplicationInfo(
            AndroidPackage pkg, long flags, int userId) {
        ApplicationInfo info = ((AndroidPackageHidden) pkg).toAppInfoWithoutState();
        updateApplicationInfo(info, flags);
        initForUser(info, pkg, userId);
        info.primaryCpuAbi = AndroidPackageLegacyUtils.getRawPrimaryCpuAbi(pkg);
        info.secondaryCpuAbi = AndroidPackageLegacyUtils.getRawSecondaryCpuAbi(pkg);
        if ((128 & flags) != 0) {
            info.metaData = pkg.getMetaData();
        }
        if ((1024 & flags) != 0) {
            List<String> usesLibraryFiles = pkg.getUsesLibraries();
            info.sharedLibraryFiles =
                    usesLibraryFiles.isEmpty()
                            ? null
                            : (String[]) usesLibraryFiles.toArray(new String[0]);
        }
        return info;
    }

    private static ActivityInfo generateActivityInfo(
            ParsedActivity a, long flags, ApplicationInfo applicationInfo) {
        if (a == null) {
            return null;
        }
        ActivityInfo ai = new ActivityInfo();
        ai.targetActivity = a.getTargetActivity();
        ai.processName = a.getProcessName();
        ai.exported = a.isExported();
        ai.theme = a.getTheme();
        ai.uiOptions = a.getUiOptions();
        ai.parentActivityName = a.getParentActivityName();
        ai.permission = a.getPermission();
        ai.taskAffinity = a.getTaskAffinity();
        ai.flags = a.getFlags();
        ai.privateFlags = a.getPrivateFlags();
        ai.launchMode = a.getLaunchMode();
        ai.documentLaunchMode = a.getDocumentLaunchMode();
        ai.maxRecents = a.getMaxRecents();
        ai.configChanges = a.getConfigChanges();
        ai.softInputMode = a.getSoftInputMode();
        ai.persistableMode = a.getPersistableMode();
        ai.lockTaskLaunchMode = a.getLockTaskLaunchMode();
        ai.screenOrientation = a.getScreenOrientation();
        ai.resizeMode = a.getResizeMode();
        ai.setMaxAspectRatio(a.getMaxAspectRatio());
        ai.setMinAspectRatio(a.getMinAspectRatio());
        ai.supportsSizeChanges = a.isSupportsSizeChanges();
        ai.requestedVrComponent = a.getRequestedVrComponent();
        ai.rotationAnimation = a.getRotationAnimation();
        ai.colorMode = a.getColorMode();
        ai.windowLayout = a.getWindowLayout();
        ai.attributionTags = a.getAttributionTags();
        if ((128 & flags) != 0) {
            Bundle metaData = a.getMetaData();
            ai.metaData = metaData.isEmpty() ? null : metaData;
        } else {
            ai.metaData = null;
        }
        ai.applicationInfo = applicationInfo;
        ai.requiredDisplayCategory = a.getRequiredDisplayCategory();
        ai.setKnownActivityEmbeddingCerts(a.getKnownActivityEmbeddingCerts());
        assignFieldsComponentInfoParsedMainComponent(ai, a);
        return ai;
    }

    private static ServiceInfo generateServiceInfo(
            ParsedService s, long flags, ApplicationInfo applicationInfo) {
        if (s == null) {
            return null;
        }
        ServiceInfo si = new ServiceInfo();
        si.exported = s.isExported();
        si.flags = s.getFlags();
        si.permission = s.getPermission();
        si.processName = s.getProcessName();
        si.mForegroundServiceType = s.getForegroundServiceType();
        si.applicationInfo = applicationInfo;
        if ((128 & flags) != 0) {
            Bundle metaData = s.getMetaData();
            si.metaData = metaData.isEmpty() ? null : metaData;
        }
        assignFieldsComponentInfoParsedMainComponent(si, s);
        return si;
    }

    private static ProviderInfo generateProviderInfo(
            AndroidPackage pkg,
            ParsedProvider p,
            long flags,
            ApplicationInfo applicationInfo,
            int userId) {
        if (p == null) {
            return null;
        }
        if (!pkg.getPackageName().equals(applicationInfo.packageName)) {
            Slog.wtf(
                    "PackageParsing",
                    "AppInfo's package name is different. Expected="
                            + pkg.getPackageName()
                            + " actual="
                            + applicationInfo.packageName);
            applicationInfo = generateApplicationInfo(pkg, flags, userId);
        }
        ProviderInfo pi = new ProviderInfo();
        pi.exported = p.isExported();
        pi.flags = p.getFlags();
        pi.processName = p.getProcessName();
        pi.authority = p.getAuthority();
        pi.isSyncable = p.isSyncable();
        pi.readPermission = p.getReadPermission();
        pi.writePermission = p.getWritePermission();
        pi.grantUriPermissions = p.isGrantUriPermissions();
        pi.forceUriPermissions = p.isForceUriPermissions();
        pi.multiprocess = p.isMultiProcess();
        pi.initOrder = p.getInitOrder();
        pi.uriPermissionPatterns =
                (PatternMatcher[]) p.getUriPermissionPatterns().toArray(new PatternMatcher[0]);
        pi.pathPermissions =
                (PathPermission[]) p.getPathPermissions().toArray(new PathPermission[0]);
        if ((2048 & flags) == 0) {
            pi.uriPermissionPatterns = null;
        }
        if ((128 & flags) != 0) {
            Bundle metaData = p.getMetaData();
            pi.metaData = metaData.isEmpty() ? null : metaData;
        }
        pi.applicationInfo = applicationInfo;
        assignFieldsComponentInfoParsedMainComponent(pi, p);
        return pi;
    }

    private static InstrumentationInfo generateInstrumentationInfo(
            ParsedInstrumentation i, AndroidPackage pkg, long flags, int userId) {
        if (i == null) {
            return null;
        }
        InstrumentationInfo info = new InstrumentationInfo();
        info.targetPackage = i.getTargetPackage();
        info.targetProcesses = i.getTargetProcesses();
        info.handleProfiling = i.isHandleProfiling();
        info.functionalTest = i.isFunctionalTest();
        info.sourceDir = pkg.getBaseApkPath();
        info.publicSourceDir = pkg.getBaseApkPath();
        info.splitNames = pkg.getSplitNames();
        info.splitSourceDirs = pkg.getSplitCodePaths().length == 0 ? null : pkg.getSplitCodePaths();
        info.splitPublicSourceDirs =
                pkg.getSplitCodePaths().length == 0 ? null : pkg.getSplitCodePaths();
        info.splitDependencies =
                pkg.getSplitDependencies().size() == 0 ? null : pkg.getSplitDependencies();
        initForUser(info, pkg, userId);
        info.primaryCpuAbi = AndroidPackageLegacyUtils.getRawPrimaryCpuAbi(pkg);
        info.secondaryCpuAbi = AndroidPackageLegacyUtils.getRawSecondaryCpuAbi(pkg);
        info.nativeLibraryDir = pkg.getNativeLibraryDir();
        info.secondaryNativeLibraryDir = pkg.getSecondaryNativeLibraryDir();
        assignFieldsPackageItemInfoParsedComponent(info, i);
        if ((128 & flags) == 0) {
            info.metaData = null;
        } else {
            Bundle metaData = i.getMetaData();
            info.metaData = metaData.isEmpty() ? null : metaData;
        }
        return info;
    }

    private static PermissionInfo generatePermissionInfo(ParsedPermission p, long flags) {
        if (p == null) {
            return null;
        }
        PermissionInfo pi = new PermissionInfo(p.getBackgroundPermission());
        assignFieldsPackageItemInfoParsedComponent(pi, p);
        pi.group = p.getGroup();
        pi.requestRes = p.getRequestRes();
        pi.protectionLevel = p.getProtectionLevel();
        pi.descriptionRes = p.getDescriptionRes();
        pi.flags = p.getFlags();
        pi.knownCerts = p.getKnownCerts();
        if ((128 & flags) == 0) {
            pi.metaData = null;
        } else {
            Bundle metaData = p.getMetaData();
            pi.metaData = metaData.isEmpty() ? null : metaData;
        }
        return pi;
    }

    private static void assignFieldsComponentInfoParsedMainComponent(
            ComponentInfo info, ParsedMainComponent component) {
        assignFieldsPackageItemInfoParsedComponent(info, component);
        info.descriptionRes = component.getDescriptionRes();
        info.directBootAware = component.isDirectBootAware();
        info.enabled = component.isEnabled();
        info.splitName = component.getSplitName();
        info.attributionTags = component.getAttributionTags();
        info.nonLocalizedLabel = component.getNonLocalizedLabel();
        info.icon = component.getIcon();
    }

    private static void assignFieldsPackageItemInfoParsedComponent(
            PackageItemInfo packageItemInfo, ParsedComponent component) {
        packageItemInfo.nonLocalizedLabel = ComponentParseUtils.getNonLocalizedLabel(component);
        packageItemInfo.icon = ComponentParseUtils.getIcon(component);
        packageItemInfo.banner = component.getBanner();
        packageItemInfo.labelRes = component.getLabelRes();
        packageItemInfo.logo = component.getLogo();
        packageItemInfo.name = component.getName();
        packageItemInfo.packageName = component.getPackageName();
    }

    private static void initForUser(ApplicationInfo output, AndroidPackage input, int userId) {
        PackageImpl pkg = (PackageImpl) input;
        String packageName = input.getPackageName();
        output.uid = UserHandle.getUid(userId, UserHandle.getAppId(input.getUid()));
        String credentialDir = pkg.getBaseAppDataCredentialProtectedDirForSystemUser();
        String deviceDir = pkg.getBaseAppDataDeviceProtectedDirForSystemUser();
        if (credentialDir != null && deviceDir != null) {
            if (userId == 0) {
                output.credentialProtectedDataDir = credentialDir + packageName;
                output.deviceProtectedDataDir = deviceDir + packageName;
            } else {
                String userIdString = String.valueOf(userId);
                int credentialLength = credentialDir.length();
                output.credentialProtectedDataDir =
                        new StringBuilder(credentialDir)
                                .replace(credentialLength - 2, credentialLength - 1, userIdString)
                                .append(packageName)
                                .toString();
                int deviceLength = deviceDir.length();
                output.deviceProtectedDataDir =
                        new StringBuilder(deviceDir)
                                .replace(deviceLength - 2, deviceLength - 1, userIdString)
                                .append(packageName)
                                .toString();
            }
        }
        if (input.isDefaultToDeviceProtectedStorage()) {
            output.dataDir = output.deviceProtectedDataDir;
        } else {
            output.dataDir = output.credentialProtectedDataDir;
        }
    }

    private static void initForUser(InstrumentationInfo output, AndroidPackage input, int userId) {
        PackageImpl pkg = (PackageImpl) input;
        String packageName = input.getPackageName();
        String credentialDir = pkg.getBaseAppDataCredentialProtectedDirForSystemUser();
        String deviceDir = pkg.getBaseAppDataDeviceProtectedDirForSystemUser();
        if (credentialDir != null && deviceDir != null) {
            if (userId == 0) {
                output.credentialProtectedDataDir = credentialDir + packageName;
                output.deviceProtectedDataDir = deviceDir + packageName;
            } else {
                String userIdString = String.valueOf(userId);
                int credentialLength = credentialDir.length();
                output.credentialProtectedDataDir =
                        new StringBuilder(credentialDir)
                                .replace(credentialLength - 2, credentialLength - 1, userIdString)
                                .append(packageName)
                                .toString();
                int deviceLength = deviceDir.length();
                output.deviceProtectedDataDir =
                        new StringBuilder(deviceDir)
                                .replace(deviceLength - 2, deviceLength - 1, userIdString)
                                .append(packageName)
                                .toString();
            }
        }
        if (input.isDefaultToDeviceProtectedStorage()) {
            output.dataDir = output.deviceProtectedDataDir;
        } else {
            output.dataDir = output.credentialProtectedDataDir;
        }
    }

    private static boolean isMatch(
            AndroidPackage pkg, boolean isComponentDirectBootAware, long flags) {
        boolean isSystem = ((AndroidPackageHidden) pkg).isSystem();
        if ((1048576 & flags) != 0 && !isSystem) {
            return reportIfDebug(false, flags);
        }
        boolean matchesUnaware =
                ((262144 & flags) == 0 || isComponentDirectBootAware) ? false : true;
        boolean matchesAware = (524288 & flags) != 0 && isComponentDirectBootAware;
        return reportIfDebug(matchesUnaware || matchesAware, flags);
    }

    private static boolean reportIfDebug(boolean result, long flags) {
        return result;
    }
}
