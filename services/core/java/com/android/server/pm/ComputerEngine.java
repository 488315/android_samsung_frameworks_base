package com.android.server.pm;

import android.apex.ApexSessionInfo;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.admin.flags.Flags;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.KeySet;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.UserInfo;
import android.content.pm.UserPackage;
import android.content.pm.VersionedPackage;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelableException;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LogPrinter;
import android.util.LongSparseLongArray;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IntentForwarderActivity;
import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.appop.AppOpsService;
import com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService;
import com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda2;
import com.android.server.pm.ApexManager;
import com.android.server.pm.CompilerStats;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.SaferIntentUtils;
import com.android.server.pm.Settings;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.PackageDexUsage;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUtils;
import com.android.server.pm.pkg.PackageUserStateDefault;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.PackageUserStateUtils;
import com.android.server.pm.pkg.SharedLibraryWrapper;
import com.android.server.pm.pu.ProfileUtilizationService;
import com.android.server.pm.resolution.ComponentResolverApi;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedLongSparseArray;
import com.android.server.utils.WatchedSparseArray;
import com.android.server.utils.WatchedSparseBooleanArray;
import com.android.server.utils.WatchedSparseIntArray;
import com.android.server.voiceinteraction.HotwordDetectionConnection$2$$ExternalSyntheticLambda0;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.localservice.ApplicationPolicyInternal;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.nal.GetAppListHelper;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import libcore.util.EmptyArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class ComputerEngine implements Computer {
    public static final ComputerEngine$$ExternalSyntheticLambda1 sProviderInitOrderSorter = new ComputerEngine$$ExternalSyntheticLambda1();
    public final ApexManager mApexManager;
    public final String mAppPredictionServicePackage;
    public ApplicationPolicyInternal mApplicationPolicy;
    public final AppsFilterBase mAppsFilter;
    public final CompilerStats mCompilerStats;
    public final ComponentResolverApi mComponentResolver;
    public final Context mContext;
    public final CrossProfileIntentResolverEngine mCrossProfileIntentResolverEngine;
    public final DefaultAppProvider mDefaultAppProvider;
    public final DexManager mDexManager;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final AppOpsService.AnonymousClass6 mExternalSourcesPolicy;
    public final WatchedArrayMap mFrozenPackages;
    public final GetAppListHelper mGetAppListHelper;
    public final PackageManagerServiceInjector mInjector;
    public final ResolveInfo mInstantAppInstallerInfo;
    public final InstantAppRegistry mInstantAppRegistry;
    public final InstantAppResolverConnection mInstantAppResolverConnection;
    public final WatchedArrayMap mInstrumentation;
    public final WatchedSparseIntArray mIsolatedOwners;
    public final ApplicationInfo mLocalAndroidApplication;
    public final ActivityInfo mLocalInstantAppInstallerActivity;
    public final ComponentName mLocalResolveComponentName;
    public final WatchedArrayMap mPackages;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManager;
    public final ActivityInfo mResolveActivity;
    public final PackageManagerService mService;
    public final Settings mSettings;
    public final SharedLibrariesImpl mSharedLibraries;
    public int mUsed = 0;
    public final UserManagerService mUserManager;
    public final int mVersion;
    public final WatchedSparseBooleanArray mWebInstantAppsDisabled;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Settings {
        public final com.android.server.pm.Settings mSettings;

        public Settings(com.android.server.pm.Settings settings) {
            this.mSettings = settings;
        }

        public final int getApplicationEnabledSetting(String str, int i) {
            PackageSetting packageSetting = (PackageSetting) this.mSettings.mPackages.mStorage.get(str);
            if (packageSetting != null) {
                return packageSetting.getEnabled(i);
            }
            throw new PackageManager.NameNotFoundException(str);
        }

        public final int getComponentEnabledSetting(ComponentName componentName, int i) {
            com.android.server.pm.Settings settings = this.mSettings;
            settings.getClass();
            PackageSetting packageSetting = (PackageSetting) settings.mPackages.mStorage.get(componentName.getPackageName());
            if (packageSetting == null) {
                throw new PackageManager.NameNotFoundException(componentName.getPackageName());
            }
            String className = componentName.getClassName();
            PackageUserStateInternal readUserState = packageSetting.readUserState(i);
            if (readUserState.getEnabledComponentsNoCopy() == null || !readUserState.getEnabledComponentsNoCopy().mStorage.contains(className)) {
                return (readUserState.getDisabledComponentsNoCopy() == null || !readUserState.getDisabledComponentsNoCopy().mStorage.contains(className)) ? 0 : 2;
            }
            return 1;
        }

        public final ArrayMap getPackages() {
            return this.mSettings.mPackages.mStorage;
        }

        public boolean isEnabledAndMatch(AndroidPackage androidPackage, ParsedMainComponent parsedMainComponent, long j, int i) {
            PackageSetting packageLPr = this.mSettings.getPackageLPr(parsedMainComponent.getPackageName());
            if (packageLPr == null) {
                return false;
            }
            return PackageUserStateUtils.isMatch(packageLPr.getUserStateOrDefault(i), packageLPr.isSystem(), androidPackage.isEnabled(), parsedMainComponent.isEnabled(), parsedMainComponent.isDirectBootAware(), parsedMainComponent.getName(), j);
        }
    }

    public ComputerEngine(PackageManagerService.Snapshot snapshot, int i) {
        this.mVersion = i;
        this.mSettings = new Settings(snapshot.settings);
        this.mIsolatedOwners = snapshot.isolatedOwners;
        this.mPackages = snapshot.packages;
        this.mSharedLibraries = snapshot.sharedLibraries;
        this.mInstrumentation = snapshot.instrumentation;
        this.mWebInstantAppsDisabled = snapshot.webInstantAppsDisabled;
        this.mLocalResolveComponentName = snapshot.resolveComponentName;
        this.mResolveActivity = snapshot.resolveActivity;
        this.mLocalInstantAppInstallerActivity = snapshot.instantAppInstallerActivity;
        this.mInstantAppInstallerInfo = snapshot.instantAppInstallerInfo;
        this.mInstantAppRegistry = snapshot.instantAppRegistry;
        this.mLocalAndroidApplication = snapshot.androidApplication;
        this.mAppsFilter = snapshot.appsFilter;
        this.mFrozenPackages = snapshot.frozenPackages;
        this.mComponentResolver = snapshot.componentResolver;
        this.mAppPredictionServicePackage = snapshot.appPredictionServicePackage;
        PackageManagerService packageManagerService = snapshot.service;
        this.mPermissionManager = packageManagerService.mPermissionManager;
        UserManagerService userManagerService = packageManagerService.mUserManager;
        this.mUserManager = userManagerService;
        Context context = packageManagerService.mContext;
        this.mContext = context;
        this.mInjector = packageManagerService.mInjector;
        this.mApexManager = packageManagerService.mApexManager;
        this.mInstantAppResolverConnection = packageManagerService.mInstantAppResolverConnection;
        DefaultAppProvider defaultAppProvider = packageManagerService.mDefaultAppProvider;
        this.mDefaultAppProvider = defaultAppProvider;
        DomainVerificationManagerInternal domainVerificationManagerInternal = packageManagerService.mDomainVerificationManager;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mDexManager = packageManagerService.mDexManager;
        this.mCompilerStats = packageManagerService.mCompilerStats;
        this.mExternalSourcesPolicy = packageManagerService.mExternalSourcesPolicy;
        this.mCrossProfileIntentResolverEngine = new CrossProfileIntentResolverEngine(userManagerService, domainVerificationManagerInternal, defaultAppProvider, context);
        this.mService = packageManagerService;
        if (PMRune.PM_NAL_GET_APP_LIST) {
            this.mGetAppListHelper = new GetAppListHelper();
        }
    }

    public static int checkSignaturesInternal(SigningDetails signingDetails, SigningDetails signingDetails2) {
        if (signingDetails == null) {
            return signingDetails2 == null ? 1 : -1;
        }
        if (signingDetails2 == null) {
            return -2;
        }
        boolean z = PackageManagerServiceUtils.DEBUG;
        int compareSignatureArrays = PackageManagerServiceUtils.compareSignatureArrays(signingDetails.getSignatures(), signingDetails2.getSignatures());
        if (compareSignatureArrays == 0) {
            return compareSignatureArrays;
        }
        if (signingDetails.hasPastSigningCertificates() || signingDetails2.hasPastSigningCertificates()) {
            return PackageManagerServiceUtils.compareSignatureArrays(signingDetails.hasPastSigningCertificates() ? new Signature[]{signingDetails.getPastSigningCertificates()[0]} : signingDetails.getSignatures(), signingDetails2.hasPastSigningCertificates() ? new Signature[]{signingDetails2.getPastSigningCertificates()[0]} : signingDetails2.getSignatures());
        }
        return compareSignatureArrays;
    }

    public static void dumpApexPackageStates(List list, boolean z, String str, IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) arrayList.get(i);
            AndroidPackageHidden pkg = packageStateInternal.getPkg();
            if (str == null || str.equals(pkg.getPackageName())) {
                indentingPrintWriter.println(pkg.getPackageName());
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Version: " + pkg.getLongVersionCode());
                indentingPrintWriter.println("Path: " + pkg.getBaseApkPath());
                indentingPrintWriter.println("IsActive: " + z);
                indentingPrintWriter.println("IsFactory: " + (packageStateInternal.isUpdatedSystemApp() ^ true));
                indentingPrintWriter.println("ApplicationInfo: ");
                indentingPrintWriter.increaseIndent();
                pkg.toAppInfoWithoutState().dump(new PrintWriterPrinter(indentingPrintWriter), "");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public static void filterIfNotSystemUser(int i, List list) {
        if (i == 0) {
            return;
        }
        for (int size = CollectionUtils.size(list) - 1; size >= 0; size--) {
            if ((((ResolveInfo) list.get(size)).activityInfo.flags & 536870912) != 0) {
                list.remove(size);
            }
        }
    }

    public static String resolveExternalPackageName(AndroidPackage androidPackage) {
        return androidPackage.getStaticSharedLibraryName() != null ? androidPackage.getManifestPackageName() : androidPackage.getPackageName();
    }

    @Override // com.android.server.pm.Computer
    public final boolean activitySupportsIntentAsUser(ComponentName componentName, ComponentName componentName2, Intent intent, String str, int i) {
        PackageSetting packageStateInternal;
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "activitySupportsIntentAsUser", i, false, false);
        if (componentName2.equals(componentName)) {
            return true;
        }
        ParsedActivity activity = this.mComponentResolver.getActivity(componentName2);
        if (activity == null || (packageStateInternal = getPackageStateInternal(componentName2.getPackageName())) == null || shouldFilterApplication(packageStateInternal, callingUid, componentName2, 1, i, true, true)) {
            return false;
        }
        for (int i2 = 0; i2 < activity.getIntents().size(); i2++) {
            if (((ParsedIntentInfo) activity.getIntents().get(i2)).getIntentFilter().match(intent.getAction(), str, intent.getScheme(), intent.getData(), intent.getCategories(), "PackageManager") >= 0) {
                return true;
            }
        }
        return false;
    }

    public ApplicationInfo androidApplication() {
        return this.mLocalAndroidApplication;
    }

    @Override // com.android.server.pm.Computer
    public final List applyPostResolutionFilter(List list, String str, boolean z, int i, boolean z2, int i2, Intent intent) {
        int i3;
        boolean z3;
        ActivityInfo activityInfo;
        String str2;
        ComponentName componentName;
        int i4 = i2;
        boolean z4 = true;
        boolean z5 = intent.isWebIntent() && this.mWebInstantAppsDisabled.mStorage.get(i4);
        int size = list.size() - 1;
        while (size >= 0) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(size);
            if (resolveInfo.isInstantAppAvailable && z5) {
                list.remove(size);
            } else if (!z || (activityInfo = resolveInfo.activityInfo) == null || (str2 = activityInfo.splitName) == null || ArrayUtils.contains(activityInfo.applicationInfo.splitNames, str2)) {
                i3 = size;
                z3 = z4;
                if (str == null) {
                    SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
                    PackageSetting packageStateInternal = getPackageStateInternal(0, resolveInfo.activityInfo.packageName);
                    if (!z2) {
                        if (!this.mAppsFilter.shouldFilterApplication(this, i, settingLPr, packageStateInternal, i2)) {
                        }
                        list.remove(i3);
                    }
                } else if (!str.equals(resolveInfo.activityInfo.packageName) && (!z2 || ((!intent.isWebIntent() && (intent.getFlags() & 2048) == 0) || intent.getPackage() != null || intent.getComponent() != null))) {
                    ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                    if ((activityInfo2.flags & 1048576) != 0 && !activityInfo2.applicationInfo.isInstantApp()) {
                    }
                    list.remove(i3);
                }
                size = i3 - 1;
                i4 = i2;
                z4 = z3;
            } else if (instantAppInstallerActivity() == null) {
                list.remove(size);
            } else if (z5 && isInstantAppInternal(i4, 1000, resolveInfo.activityInfo.packageName)) {
                list.remove(size);
            } else {
                ResolveInfo resolveInfo2 = new ResolveInfo(this.mInstantAppInstallerInfo);
                String str3 = resolveInfo.activityInfo.packageName;
                i3 = size;
                List queryIntentActivitiesInternal = queryIntentActivitiesInternal(ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("android.intent.action.INSTALL_FAILURE", str3), null, 0L, i, -1, i2, false, false);
                int size2 = queryIntentActivitiesInternal.size();
                if (size2 > 0) {
                    for (int i5 = 0; i5 < size2; i5++) {
                        ResolveInfo resolveInfo3 = (ResolveInfo) queryIntentActivitiesInternal.get(i5);
                        if (resolveInfo3.activityInfo.splitName == null) {
                            componentName = new ComponentName(str3, resolveInfo3.activityInfo.name);
                            break;
                        }
                    }
                }
                componentName = null;
                ComponentName componentName2 = componentName;
                ActivityInfo activityInfo3 = resolveInfo.activityInfo;
                resolveInfo2.auxiliaryInfo = new AuxiliaryResolveInfo(componentName2, activityInfo3.packageName, activityInfo3.applicationInfo.longVersionCode, activityInfo3.splitName);
                resolveInfo2.filter = new IntentFilter();
                resolveInfo2.resolvePackageName = resolveInfo.getComponentInfo().packageName;
                resolveInfo2.labelRes = resolveInfo.resolveLabelResId();
                resolveInfo2.icon = resolveInfo.resolveIconResId();
                z3 = true;
                resolveInfo2.isInstantAppAvailable = true;
                list.set(i3, resolveInfo2);
                size = i3 - 1;
                i4 = i2;
                z4 = z3;
            }
            i3 = size;
            z3 = z4;
            size = i3 - 1;
            i4 = i2;
            z4 = z3;
        }
        return list;
    }

    public final void applyPostServiceResolutionFilter(int i, String str, int i2, List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(size);
            if (str == null) {
                if (!this.mAppsFilter.shouldFilterApplication(this, i2, this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i2)), getPackageStateInternal(0, resolveInfo.serviceInfo.packageName), i)) {
                }
            }
            boolean isInstantApp = resolveInfo.serviceInfo.applicationInfo.isInstantApp();
            if (isInstantApp && str.equals(resolveInfo.serviceInfo.packageName)) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                String str2 = serviceInfo.splitName;
                if (str2 != null && !ArrayUtils.contains(serviceInfo.applicationInfo.splitNames, str2)) {
                    if (instantAppInstallerActivity() == null) {
                        if (PackageManagerService.DEBUG_INSTANT) {
                            Slog.v("PackageManager", "No installer - not adding it to the ResolveInfolist");
                        }
                        list.remove(size);
                    } else {
                        if (PackageManagerService.DEBUG_INSTANT) {
                            Slog.v("PackageManager", "Adding ephemeral installer to the ResolveInfo list");
                        }
                        ResolveInfo resolveInfo2 = new ResolveInfo(this.mInstantAppInstallerInfo);
                        ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
                        resolveInfo2.auxiliaryInfo = new AuxiliaryResolveInfo((ComponentName) null, serviceInfo2.packageName, serviceInfo2.applicationInfo.longVersionCode, serviceInfo2.splitName);
                        resolveInfo2.filter = new IntentFilter();
                        resolveInfo2.resolvePackageName = resolveInfo.getComponentInfo().packageName;
                        list.set(size, resolveInfo2);
                    }
                }
            } else if (isInstantApp || (resolveInfo.serviceInfo.flags & 1048576) == 0) {
                list.remove(size);
            }
        }
    }

    @Override // com.android.server.pm.Computer
    public final boolean canAccessComponent(int i, int i2, ComponentName componentName) {
        PackageSetting packageStateInternal = getPackageStateInternal(componentName.getPackageName());
        return (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, componentName, 0, i2, true, true)) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public final boolean canForwardTo(Intent intent, String str, int i, int i2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        this.mCrossProfileIntentResolverEngine.getClass();
        if (CrossProfileIntentResolverEngine.canReachToInternal(this, intent, str, i, i2, new HashSet())) {
            return true;
        }
        if (!intent.hasWebURI()) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        UserInfo profileParent = getProfileParent(i);
        if (profileParent == null) {
            return false;
        }
        int i3 = profileParent.id;
        return getCrossProfileDomainPreferredLpr(intent, str, updateFlagsForResolve(0L, i3, callingUid, false, false, isImplicitImageCaptureIntentAndNotSetByDpc(intent, str, 0L, i3)) | 65536, i, profileParent.id) != null;
    }

    @Override // com.android.server.pm.Computer
    public final boolean[] canPackageQuery(String str, String[] strArr, int i) {
        int length = strArr.length;
        boolean[] zArr = new boolean[length];
        if (!this.mUserManager.mLocalService.exists(i)) {
            return zArr;
        }
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "can package query", i, false, false);
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        PackageStateInternal[] packageStateInternalArr = new PackageStateInternal[length];
        boolean z = packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i);
        for (int i2 = 0; !z && i2 < length; i2++) {
            PackageSetting packageStateInternal2 = getPackageStateInternal(strArr[i2]);
            packageStateInternalArr[i2] = packageStateInternal2;
            z = packageStateInternal2 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal2, callingUid, i);
        }
        if (z) {
            throw new ParcelableException(new PackageManager.NameNotFoundException(AudioOffloadInfo$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("Package(s) ", str, " and/or "), Arrays.toString(strArr), " not found.")));
        }
        int uid = UserHandle.getUid(i, packageStateInternal.mAppId);
        for (int i3 = 0; i3 < length; i3++) {
            zArr[i3] = !shouldFilterApplication(packageStateInternalArr[i3], uid, i);
        }
        return zArr;
    }

    @Override // com.android.server.pm.Computer
    public final boolean canQueryPackage(int i, String str) {
        if (i == 0 || str == null) {
            return true;
        }
        int appId = UserHandle.getAppId(i);
        Settings settings = this.mSettings;
        SettingBase settingLPr = settings.mSettings.getSettingLPr(appId);
        if (settingLPr == null) {
            return false;
        }
        int appId2 = UserHandle.getAppId(getPackageUid(str, 0L, UserHandle.getUserId(i)));
        if (appId2 != -1) {
            return settings.mSettings.getSettingLPr(appId2) instanceof PackageSetting ? !shouldFilterApplication((PackageSetting) r9, i, r4) : !shouldFilterApplication((SharedUserSetting) r9, i, r4);
        }
        boolean z = settingLPr instanceof PackageSetting;
        AppsFilterBase appsFilterBase = this.mAppsFilter;
        if (z) {
            AndroidPackageInternal androidPackageInternal = ((PackageSetting) settingLPr).pkg;
            return androidPackageInternal != null && appsFilterBase.canQueryPackage(androidPackageInternal, str);
        }
        ArraySet arraySet = ((SharedUserSetting) settingLPr).mPackages.mStorage;
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            AndroidPackageInternal pkg = ((PackageStateInternal) arraySet.valueAt(size)).getPkg();
            if (pkg != null && appsFilterBase.canQueryPackage(pkg, str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final boolean canRequestPackageInstalls(int i, int i2, String str, boolean z) {
        AndroidPackage androidPackage;
        if (i != getPackageUidInternal(i2, i, 0L, str) && !PackageManagerServiceUtils.isSystemOrRoot(i)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "Caller uid ", " does not own package ", str));
        }
        if (isInstantAppInternal(i2, 1000, str) || (androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str)) == null || androidPackage.getTargetSdkVersion() < 26) {
            return false;
        }
        if (androidPackage.getRequestedPermissions().contains("android.permission.REQUEST_INSTALL_PACKAGES")) {
            return !isInstallDisabledForPackage(r0, i2, str);
        }
        if (z) {
            throw new SecurityException("Need to declare android.permission.REQUEST_INSTALL_PACKAGES to call this api");
        }
        Slog.e("PackageManager", "Need to declare android.permission.REQUEST_INSTALL_PACKAGES to call this api");
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final boolean canViewInstantApps(int i, int i2) {
        if (i < 10000 || this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS") == 0) {
            return true;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.VIEW_INSTANT_APPS") != 0) {
            return false;
        }
        ComponentName defaultHomeActivity = getDefaultHomeActivity(i2);
        if (defaultHomeActivity != null && isCallerSameApp(i, defaultHomeActivity.getPackageName(), false)) {
            return true;
        }
        String str = this.mAppPredictionServicePackage;
        return str != null && isCallerSameApp(i, str, false);
    }

    @Override // com.android.server.pm.Computer
    public final String[] canonicalToCurrentPackageNames(String[] strArr) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return strArr;
        }
        String[] strArr2 = new String[strArr.length];
        int userId = UserHandle.getUserId(callingUid);
        boolean canViewInstantApps = canViewInstantApps(callingUid, userId);
        for (int length = strArr.length - 1; length >= 0; length--) {
            String renamedPackageLPr = this.mSettings.mSettings.getRenamedPackageLPr(strArr[length]);
            if (renamedPackageLPr != null) {
                PackageSetting packageStateInternal = getPackageStateInternal(strArr[length]);
                if (packageStateInternal != null && packageStateInternal.getUserStateOrDefault(userId).isInstantApp() && !canViewInstantApps) {
                    if (this.mInstantAppRegistry.isInstantAccessGranted(userId, UserHandle.getAppId(callingUid), packageStateInternal.mAppId)) {
                    }
                }
                strArr2[length] = renamedPackageLPr;
            }
            renamedPackageLPr = strArr[length];
            strArr2[length] = renamedPackageLPr;
        }
        return strArr2;
    }

    @Override // com.android.server.pm.Computer
    public final void checkPackageFrozen(String str) {
        if (this.mFrozenPackages.mStorage.containsKey(str)) {
            return;
        }
        Slog.wtf("PackageManager", XmlUtils$$ExternalSyntheticOutline0.m("Expected ", str, " to be frozen!"), new Throwable());
    }

    @Override // com.android.server.pm.Computer
    public final int checkSignatures(String str, String str2, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "checkSignatures", i, false, false);
        WatchedArrayMap watchedArrayMap = this.mPackages;
        AndroidPackage androidPackage = (AndroidPackage) watchedArrayMap.mStorage.get(str);
        AndroidPackage androidPackage2 = (AndroidPackage) watchedArrayMap.mStorage.get(str2);
        PackageSetting packageStateInternal = androidPackage == null ? null : getPackageStateInternal(androidPackage.getPackageName());
        PackageSetting packageStateInternal2 = androidPackage2 != null ? getPackageStateInternal(androidPackage2.getPackageName()) : null;
        if (androidPackage == null || packageStateInternal == null || androidPackage2 == null || packageStateInternal2 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i) || shouldFilterApplicationIncludingUninstalled(packageStateInternal2, callingUid, i)) {
            return -4;
        }
        return checkSignaturesInternal(androidPackage.getSigningDetails(), androidPackage2.getSigningDetails());
    }

    @Override // com.android.server.pm.Computer
    public final int checkUidPermission(String str, int i) {
        return PermissionManagerService.this.checkUidPermission(i, str, 0);
    }

    @Override // com.android.server.pm.Computer
    public final int checkUidSignatures(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        SigningDetails signingDetailsAndFilterAccess = getSigningDetailsAndFilterAccess(i, callingUid, userId);
        SigningDetails signingDetailsAndFilterAccess2 = getSigningDetailsAndFilterAccess(i2, callingUid, userId);
        if (signingDetailsAndFilterAccess == null || signingDetailsAndFilterAccess2 == null) {
            return -4;
        }
        return checkSignaturesInternal(signingDetailsAndFilterAccess, signingDetailsAndFilterAccess2);
    }

    @Override // com.android.server.pm.Computer
    public final int checkUidSignaturesForAllUsers(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(i);
        int userId2 = UserHandle.getUserId(i2);
        enforceCrossUserPermission(callingUid, false, "checkUidSignaturesForAllUsers", userId, false, false);
        enforceCrossUserPermission(callingUid, false, "checkUidSignaturesForAllUsers", userId2, false, false);
        SigningDetails signingDetailsAndFilterAccess = getSigningDetailsAndFilterAccess(i, callingUid, userId);
        SigningDetails signingDetailsAndFilterAccess2 = getSigningDetailsAndFilterAccess(i2, callingUid, userId2);
        if (signingDetailsAndFilterAccess == null || signingDetailsAndFilterAccess2 == null) {
            return -4;
        }
        return checkSignaturesInternal(signingDetailsAndFilterAccess, signingDetailsAndFilterAccess2);
    }

    @Override // com.android.server.pm.Computer
    public final ResolveInfo createForwardingResolveInfoUnchecked(WatchedIntentFilter watchedIntentFilter, int i, int i2) {
        UserManagerService userManagerService = this.mUserManager;
        ResolveInfo resolveInfo = new ResolveInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
            UserInfo userInfo = userManagerService.getUserInfo(i2);
            boolean isManagedProfile = userInfo.isManagedProfile();
            userInfo.isKnoxWorkspace();
            boolean isSecureFolder = userInfo.isSecureFolder();
            String str = isSecureFolder ? "4" : "";
            boolean isKnoxWorkspace = userManagerService.getUserInfo(i).isKnoxWorkspace();
            boolean z = userInfo.isUserTypeAppSeparation() ? true : isManagedProfile;
            String str2 = z ? IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE : IntentForwarderActivity.FORWARD_INTENT_TO_PARENT;
            ActivityInfo activityInfoInternalBody = !userManagerService.mLocalService.exists(i) ? null : getActivityInfoInternalBody(Binder.getCallingUid(), i, updateFlags(i, 0L), isSecureFolder ? new ComponentName(androidApplication().packageName, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, str)) : new ComponentName(androidApplication().packageName, str2));
            if (!z && !isKnoxWorkspace) {
                if (activityInfoInternalBody != null) {
                    activityInfoInternalBody.showUserIcon = i2;
                }
                resolveInfo.noResourceId = true;
            }
            resolveInfo.activityInfo = activityInfoInternalBody;
            resolveInfo.priority = 0;
            resolveInfo.preferredOrder = 0;
            resolveInfo.match = 0;
            resolveInfo.isDefault = true;
            resolveInfo.filter = new IntentFilter(watchedIntentFilter.getIntentFilter$3());
            resolveInfo.targetUserId = i2;
            resolveInfo.userHandle = UserHandle.of(i);
            return resolveInfo;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
    
        if (r9.mInstantAppRegistry.isInstantAccessGranted(r2, android.os.UserHandle.getAppId(r0), r5.mAppId) != false) goto L16;
     */
    @Override // com.android.server.pm.Computer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] r10) {
        /*
            r9 = this;
            int r0 = android.os.Binder.getCallingUid()
            java.lang.String r1 = r9.getInstantAppPackageName(r0)
            if (r1 == 0) goto Lb
            return r10
        Lb:
            int r1 = r10.length
            java.lang.String[] r1 = new java.lang.String[r1]
            int r2 = android.os.UserHandle.getUserId(r0)
            boolean r3 = r9.canViewInstantApps(r0, r2)
            int r4 = r10.length
            int r4 = r4 + (-1)
        L19:
            if (r4 < 0) goto L4b
            r5 = r10[r4]
            com.android.server.pm.PackageSetting r5 = r9.getPackageStateInternal(r5)
            if (r5 == 0) goto L44
            java.lang.String r6 = r5.mRealName
            if (r6 == 0) goto L44
            com.android.server.pm.pkg.PackageUserStateInternal r6 = r5.getUserStateOrDefault(r2)
            boolean r6 = r6.isInstantApp()
            if (r6 == 0) goto L41
            if (r3 != 0) goto L41
            int r6 = android.os.UserHandle.getAppId(r0)
            int r7 = r5.mAppId
            com.android.server.pm.InstantAppRegistry r8 = r9.mInstantAppRegistry
            boolean r6 = r8.isInstantAccessGranted(r2, r6, r7)
            if (r6 == 0) goto L44
        L41:
            java.lang.String r5 = r5.mRealName
            goto L46
        L44:
            r5 = r10[r4]
        L46:
            r1[r4] = r5
            int r4 = r4 + (-1)
            goto L19
        L4b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ComputerEngine.currentToCanonicalPackageNames(java.lang.String[]):java.lang.String[]");
    }

    /* JADX WARN: Type inference failed for: r0v29, types: [com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda6] */
    @Override // com.android.server.pm.Computer
    public final void dump(int i, FileDescriptor fileDescriptor, PrintWriter printWriter, DumpState dumpState) {
        CompilerStats.PackageStats packageStats;
        String str = dumpState.mTargetPackageName;
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(str);
        if (str != null && packageLPr == null && !isApexPackage(str)) {
            return;
        }
        int i2 = 0;
        switch (i) {
            case Integer.MIN_VALUE:
                ProfileUtilizationService profileUtilizationService = (ProfileUtilizationService) this.mInjector.mGetLocalServiceProducer.produce(ProfileUtilizationService.class);
                if (profileUtilizationService == null) {
                    return;
                }
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                boolean z = dumpState.mTitlePrinted;
                dumpState.mTitlePrinted = true;
                if (z) {
                    printWriter.println();
                }
                profileUtilizationService.dump(indentingPrintWriter);
                return;
            case 1:
                SharedLibrariesImpl sharedLibrariesImpl = this.mSharedLibraries;
                sharedLibrariesImpl.getClass();
                boolean z2 = dumpState.mCheckIn;
                WatchedArrayMap watchedArrayMap = sharedLibrariesImpl.mSharedLibraries;
                int size = watchedArrayMap.mStorage.size();
                boolean z3 = false;
                for (int i3 = 0; i3 < size; i3++) {
                    WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) watchedArrayMap.mStorage.get((String) watchedArrayMap.mStorage.keyAt(i3));
                    if (watchedLongSparseArray != null) {
                        int size2 = watchedLongSparseArray.mStorage.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(i4);
                            if (z2) {
                                printWriter.print("lib,");
                            } else {
                                if (!z3) {
                                    boolean z4 = dumpState.mTitlePrinted;
                                    dumpState.mTitlePrinted = true;
                                    if (z4) {
                                        printWriter.println();
                                    }
                                    printWriter.println("Libraries:");
                                    z3 = true;
                                }
                                printWriter.print("  ");
                            }
                            printWriter.print(sharedLibraryInfo.getName());
                            if (sharedLibraryInfo.isStatic()) {
                                printWriter.print(" version=" + sharedLibraryInfo.getLongVersion());
                            }
                            if (!z2) {
                                printWriter.print(" -> ");
                            }
                            if (sharedLibraryInfo.getPath() != null) {
                                if (sharedLibraryInfo.isNative()) {
                                    printWriter.print(" (so) ");
                                } else {
                                    printWriter.print(" (jar) ");
                                }
                                printWriter.print(sharedLibraryInfo.getPath());
                            } else {
                                printWriter.print(" (apk) ");
                                printWriter.print(sharedLibraryInfo.getPackageName());
                            }
                            printWriter.println();
                        }
                    }
                }
                return;
            case 512:
                com.android.server.pm.Settings settings = this.mSettings.mSettings;
                settings.getClass();
                printWriter.println("Settings parse messages:");
                printWriter.print(settings.mReadMessages.toString());
                return;
            case 4096:
                Settings settings2 = this.mSettings;
                int i5 = 0;
                while (true) {
                    WatchedSparseArray watchedSparseArray = settings2.mSettings.mPreferredActivities;
                    if (i5 >= watchedSparseArray.mStorage.size()) {
                        return;
                    }
                    PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) watchedSparseArray.mStorage.valueAt(i5);
                    int keyAt = watchedSparseArray.mStorage.keyAt(i5);
                    if (preferredIntentResolver.dump(printWriter, dumpState.mTitlePrinted ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(keyAt, "\nPreferred Activities User ", ":") : BinaryTransparencyService$$ExternalSyntheticOutline0.m(keyAt, "Preferred Activities User ", ":"), "  ", str, true, false)) {
                        dumpState.mTitlePrinted = true;
                    }
                    i5++;
                }
            case 8192:
                printWriter.flush();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileDescriptor));
                TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
                try {
                    newFastSerializer.setOutput(bufferedOutputStream, StandardCharsets.UTF_8.name());
                    newFastSerializer.startDocument((String) null, Boolean.TRUE);
                    newFastSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    this.mSettings.mSettings.writePreferredActivitiesLPr(0, newFastSerializer, dumpState.mFullPreferred);
                    newFastSerializer.endDocument();
                    newFastSerializer.flush();
                    return;
                } catch (IOException e) {
                    printWriter.println("Failed writing: " + e);
                    return;
                } catch (IllegalArgumentException e2) {
                    printWriter.println("Failed writing: " + e2);
                    return;
                } catch (IllegalStateException e3) {
                    printWriter.println("Failed writing: " + e3);
                    return;
                }
            case 32768:
                boolean z5 = dumpState.mTitlePrinted;
                dumpState.mTitlePrinted = true;
                if (z5) {
                    printWriter.println();
                }
                printWriter.println("Database versions:");
                Settings settings3 = this.mSettings;
                IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(printWriter, "  ");
                com.android.server.pm.Settings settings4 = settings3.mSettings;
                settings4.getClass();
                indentingPrintWriter2.increaseIndent();
                while (true) {
                    WatchedArrayMap watchedArrayMap2 = settings4.mVersion;
                    if (i2 >= watchedArrayMap2.mStorage.size()) {
                        indentingPrintWriter2.decreaseIndent();
                        return;
                    }
                    String str2 = (String) watchedArrayMap2.mStorage.keyAt(i2);
                    Settings.VersionInfo versionInfo = (Settings.VersionInfo) watchedArrayMap2.mStorage.valueAt(i2);
                    if (Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, str2)) {
                        indentingPrintWriter2.println("Internal:");
                    } else if ("primary_physical".equals(str2)) {
                        indentingPrintWriter2.println("External:");
                    } else {
                        indentingPrintWriter2.println("UUID " + str2 + ":");
                    }
                    indentingPrintWriter2.increaseIndent();
                    indentingPrintWriter2.printPair("sdkVersion", Integer.valueOf(versionInfo.sdkVersion));
                    indentingPrintWriter2.printPair("databaseVersion", Integer.valueOf(versionInfo.databaseVersion));
                    indentingPrintWriter2.println();
                    indentingPrintWriter2.printPair("buildFingerprint", versionInfo.buildFingerprint);
                    indentingPrintWriter2.printPair("fingerprint", versionInfo.fingerprint);
                    indentingPrintWriter2.println();
                    indentingPrintWriter2.decreaseIndent();
                    i2++;
                }
            case 262144:
                android.util.IndentingPrintWriter indentingPrintWriter3 = new android.util.IndentingPrintWriter(printWriter);
                boolean z6 = dumpState.mTitlePrinted;
                dumpState.mTitlePrinted = true;
                if (z6) {
                    printWriter.println();
                }
                indentingPrintWriter3.println("Domain verification status:");
                indentingPrintWriter3.increaseIndent();
                try {
                    ((DomainVerificationService) this.mDomainVerificationManager).printState(indentingPrintWriter3, this, -1, str);
                } catch (Exception e4) {
                    printWriter.println("Failure printing domain verification information");
                    Slog.e("PackageManager", "Failure printing domain verification information", e4);
                }
                indentingPrintWriter3.decreaseIndent();
                return;
            case 524288:
                boolean z7 = dumpState.mTitlePrinted;
                dumpState.mTitlePrinted = true;
                if (z7) {
                    printWriter.println();
                }
                IndentingPrintWriter indentingPrintWriter4 = new IndentingPrintWriter(printWriter, "  ", 120);
                indentingPrintWriter4.println();
                indentingPrintWriter4.println("Frozen packages:");
                indentingPrintWriter4.increaseIndent();
                if (this.mFrozenPackages.mStorage.size() == 0) {
                    indentingPrintWriter4.println("(none)");
                } else {
                    while (i2 < this.mFrozenPackages.mStorage.size()) {
                        indentingPrintWriter4.print("package=");
                        indentingPrintWriter4.print((String) this.mFrozenPackages.mStorage.keyAt(i2));
                        indentingPrintWriter4.print(", refCounts=");
                        indentingPrintWriter4.println(this.mFrozenPackages.mStorage.valueAt(i2));
                        i2++;
                    }
                }
                indentingPrintWriter4.decreaseIndent();
                return;
            case 1048576:
                IndentingPrintWriter indentingPrintWriter5 = new IndentingPrintWriter(printWriter, "  ");
                boolean z8 = dumpState.mTitlePrinted;
                dumpState.mTitlePrinted = true;
                if (z8) {
                    printWriter.println();
                }
                indentingPrintWriter5.println("Dexopt state:");
                indentingPrintWriter5.increaseIndent();
                PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                try {
                    if (str != null) {
                        try {
                            DexOptHelper.getArtManagerLocal().dumpPackage(indentingPrintWriter5, withFilteredSnapshot, str);
                        } catch (IllegalArgumentException e5) {
                            indentingPrintWriter5.println(e5);
                        }
                    } else {
                        DexOptHelper.getArtManagerLocal().dump(indentingPrintWriter5, withFilteredSnapshot);
                    }
                    if (withFilteredSnapshot != null) {
                        withFilteredSnapshot.close();
                    }
                    indentingPrintWriter5.decreaseIndent();
                    return;
                } catch (Throwable th) {
                    if (withFilteredSnapshot != null) {
                        try {
                            withFilteredSnapshot.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            case 2097152:
                IndentingPrintWriter indentingPrintWriter6 = new IndentingPrintWriter(printWriter, "  ");
                boolean z9 = dumpState.mTitlePrinted;
                dumpState.mTitlePrinted = true;
                if (z9) {
                    printWriter.println();
                }
                indentingPrintWriter6.println("Compiler stats:");
                indentingPrintWriter6.increaseIndent();
                Iterator it = (packageLPr != null ? Collections.singletonList(packageLPr) : this.mSettings.getPackages().values()).iterator();
                while (it.hasNext()) {
                    AndroidPackageInternal pkg = ((PackageStateInternal) it.next()).getPkg();
                    if (pkg != null) {
                        String packageName = pkg.getPackageName();
                        indentingPrintWriter6.println("[" + packageName + "]");
                        indentingPrintWriter6.increaseIndent();
                        CompilerStats compilerStats = this.mCompilerStats;
                        synchronized (compilerStats.packageStats) {
                            packageStats = (CompilerStats.PackageStats) ((HashMap) compilerStats.packageStats).get(packageName);
                        }
                        if (packageStats == null) {
                            indentingPrintWriter6.println("(No recorded stats)");
                        } else {
                            synchronized (packageStats.compileTimePerCodePath) {
                                try {
                                    if (((ArrayMap) packageStats.compileTimePerCodePath).size() == 0) {
                                        indentingPrintWriter6.println("(No recorded stats)");
                                    } else {
                                        for (Map.Entry entry : ((ArrayMap) packageStats.compileTimePerCodePath).entrySet()) {
                                            indentingPrintWriter6.println(" " + ((String) entry.getKey()) + " - " + entry.getValue());
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                        indentingPrintWriter6.decreaseIndent();
                    }
                }
                return;
            case 33554432:
                if (str == null || isApexPackage(str)) {
                    ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) this.mApexManager;
                    apexManagerImpl.getClass();
                    IndentingPrintWriter indentingPrintWriter7 = new IndentingPrintWriter(printWriter, "  ", 120);
                    try {
                        indentingPrintWriter7.println();
                        indentingPrintWriter7.println("APEX session state:");
                        indentingPrintWriter7.increaseIndent();
                        for (ApexSessionInfo apexSessionInfo : apexManagerImpl.waitForApexService().getSessions()) {
                            indentingPrintWriter7.println("Session ID: " + apexSessionInfo.sessionId);
                            indentingPrintWriter7.increaseIndent();
                            if (apexSessionInfo.isUnknown) {
                                indentingPrintWriter7.println("State: UNKNOWN");
                            } else if (apexSessionInfo.isVerified) {
                                indentingPrintWriter7.println("State: VERIFIED");
                            } else if (apexSessionInfo.isStaged) {
                                indentingPrintWriter7.println("State: STAGED");
                            } else if (apexSessionInfo.isActivated) {
                                indentingPrintWriter7.println("State: ACTIVATED");
                            } else if (apexSessionInfo.isActivationFailed) {
                                indentingPrintWriter7.println("State: ACTIVATION FAILED");
                            } else if (apexSessionInfo.isSuccess) {
                                indentingPrintWriter7.println("State: SUCCESS");
                            } else if (apexSessionInfo.isRevertInProgress) {
                                indentingPrintWriter7.println("State: REVERT IN PROGRESS");
                            } else if (apexSessionInfo.isReverted) {
                                indentingPrintWriter7.println("State: REVERTED");
                            } else if (apexSessionInfo.isRevertFailed) {
                                indentingPrintWriter7.println("State: REVERT FAILED");
                            }
                            indentingPrintWriter7.decreaseIndent();
                        }
                        indentingPrintWriter7.decreaseIndent();
                        indentingPrintWriter7.println();
                    } catch (RemoteException unused) {
                        indentingPrintWriter7.println("Couldn't communicate with apexd.");
                    }
                    IndentingPrintWriter indentingPrintWriter8 = new IndentingPrintWriter(printWriter, "  ", 120);
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    for (AndroidPackage androidPackage : this.mPackages.values()) {
                        String packageName2 = androidPackage.getPackageName();
                        Settings settings5 = this.mSettings;
                        PackageSetting packageLPr2 = settings5.mSettings.getPackageLPr(packageName2);
                        if (androidPackage.isApex() && packageLPr2 != null) {
                            arrayList.add(packageLPr2);
                            if (packageLPr2.pkgState.updatedSystemApp) {
                                PackageSetting disabledSystemPkgLPr = settings5.mSettings.getDisabledSystemPkgLPr(packageName2);
                                arrayList4.add(disabledSystemPkgLPr);
                                arrayList2.add(disabledSystemPkgLPr);
                            } else {
                                arrayList3.add(packageLPr2);
                            }
                        }
                    }
                    indentingPrintWriter8.println("Active APEX packages:");
                    dumpApexPackageStates(arrayList, true, str, indentingPrintWriter8);
                    indentingPrintWriter8.println("Inactive APEX packages:");
                    dumpApexPackageStates(arrayList2, false, str, indentingPrintWriter8);
                    indentingPrintWriter8.println("Factory APEX packages:");
                    dumpApexPackageStates(arrayList3, true, str, indentingPrintWriter8);
                    dumpApexPackageStates(arrayList4, false, str, indentingPrintWriter8);
                    return;
                }
                return;
            case 67108864:
                Integer valueOf = packageLPr != null ? Integer.valueOf(packageLPr.mAppId) : null;
                AppsFilterBase appsFilterBase = this.mAppsFilter;
                int[] userIds = this.mUserManager.getUserIds();
                ?? r0 = new QuadFunction() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda6
                    public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                        return ComputerEngine.this.getPackagesForUidInternalBody(((Integer) obj).intValue(), ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), ((Boolean) obj4).booleanValue());
                    }
                };
                appsFilterBase.getClass();
                AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0 = new AppsFilterBase$$ExternalSyntheticLambda0(new SparseArray(), userIds, r0);
                printWriter.println();
                printWriter.println("Queries:");
                dumpState.mTitlePrinted = true;
                if (!appsFilterBase.mFeatureConfig.isGloballyEnabled()) {
                    printWriter.println("  DISABLED");
                    return;
                }
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  system apps queryable: "), appsFilterBase.mSystemAppsQueryable, printWriter);
                appsFilterBase.dumpForceQueryable(printWriter, valueOf, appsFilterBase$$ExternalSyntheticLambda0);
                appsFilterBase.dumpQueriesViaPackage(printWriter, valueOf, appsFilterBase$$ExternalSyntheticLambda0);
                appsFilterBase.dumpQueriesViaComponent(printWriter, valueOf, appsFilterBase$$ExternalSyntheticLambda0);
                appsFilterBase.dumpQueriesViaImplicitlyQueryable(printWriter, valueOf, userIds, appsFilterBase$$ExternalSyntheticLambda0);
                appsFilterBase.dumpQueriesViaUsesLibrary(printWriter, valueOf, appsFilterBase$$ExternalSyntheticLambda0);
                return;
            default:
                return;
        }
    }

    @Override // com.android.server.pm.Computer
    public final void dumpKeySet(DumpState dumpState, PrintWriter printWriter, String str) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        for (Map.Entry entry : this.mSettings.mSettings.mKeySetManagerService.mPackages.entrySet()) {
            String str2 = (String) entry.getKey();
            if (str == null || str.equals(str2)) {
                if (!z3) {
                    boolean z4 = dumpState.mTitlePrinted;
                    dumpState.mTitlePrinted = true;
                    if (z4) {
                        printWriter.println();
                    }
                    printWriter.println("Key Set Manager:");
                    z3 = true;
                }
                PackageSetting packageSetting = (PackageSetting) entry.getValue();
                printWriter.print("  [");
                printWriter.print(str2);
                printWriter.println("]");
                PackageKeySetData packageKeySetData = packageSetting.keySetData;
                if (packageKeySetData != null) {
                    boolean z5 = false;
                    for (Map.Entry entry2 : packageKeySetData.mKeySetAliases.entrySet()) {
                        if (z5) {
                            printWriter.print(", ");
                        } else {
                            printWriter.print("      KeySets Aliases: ");
                            z5 = true;
                        }
                        printWriter.print((String) entry2.getKey());
                        printWriter.print('=');
                        printWriter.print(Long.toString(((Long) entry2.getValue()).longValue()));
                    }
                    if (z5) {
                        printWriter.println("");
                    }
                    if (packageSetting.keySetData.mKeySetAliases.size() > 0) {
                        ArrayMap arrayMap = packageSetting.keySetData.mKeySetAliases;
                        int size = arrayMap.size();
                        z = false;
                        for (int i = 0; i < size; i++) {
                            if (z) {
                                printWriter.print(", ");
                            } else {
                                printWriter.print("      Defined KeySets: ");
                                z = true;
                            }
                            printWriter.print(Long.toString(((Long) arrayMap.valueAt(i)).longValue()));
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        printWriter.println("");
                    }
                    long j = packageSetting.keySetData.mProperSigningKeySet;
                    printWriter.print("      Signing KeySets: ");
                    printWriter.print(Long.toString(j));
                    printWriter.println("");
                    long[] jArr = packageSetting.keySetData.mUpgradeKeySets;
                    if (jArr != null && jArr.length > 0) {
                        z2 = false;
                        for (long j2 : jArr) {
                            if (z2) {
                                printWriter.print(", ");
                            } else {
                                printWriter.print("      Upgrade KeySets: ");
                                z2 = true;
                            }
                            printWriter.print(Long.toString(j2));
                        }
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        printWriter.println("");
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0194, code lost:
    
        if (r9 != false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0196, code lost:
    
        r0 = r31.mTitlePrinted;
        r31.mTitlePrinted = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x019a, code lost:
    
        if (r0 == false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x019c, code lost:
    
        r28.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x019f, code lost:
    
        r28.println("Hidden system packages:");
        r21 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x010f, code lost:
    
        if (r9 != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0111, code lost:
    
        r2 = r31.mTitlePrinted;
        r31.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0115, code lost:
    
        if (r2 == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0117, code lost:
    
        r28.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x011a, code lost:
    
        r28.println("Renamed packages:");
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0120, code lost:
    
        r28.print("  ");
     */
    @Override // com.android.server.pm.Computer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpPackages(java.io.PrintWriter r28, java.lang.String r29, android.util.ArraySet r30, com.android.server.pm.DumpState r31, boolean r32) {
        /*
            Method dump skipped, instructions count: 481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ComputerEngine.dumpPackages(java.io.PrintWriter, java.lang.String, android.util.ArraySet, com.android.server.pm.DumpState, boolean):void");
    }

    @Override // com.android.server.pm.Computer
    public final void dumpPackagesProto(ProtoOutputStream protoOutputStream) {
        int i;
        int i2;
        long j;
        com.android.server.pm.Settings settings = this.mSettings.mSettings;
        settings.getClass();
        List users = com.android.server.pm.Settings.getUsers(UserManagerService.getInstance(), false, false);
        int size = settings.mPackages.mStorage.size();
        int i3 = 0;
        while (i3 < size) {
            PackageSetting packageSetting = (PackageSetting) settings.mPackages.mStorage.valueAt(i3);
            packageSetting.getClass();
            long start = protoOutputStream.start(2246267895813L);
            String str = packageSetting.mRealName;
            if (str == null) {
                str = packageSetting.mName;
            }
            protoOutputStream.write(1138166333441L, str);
            protoOutputStream.write(1120986464258L, packageSetting.mAppId);
            int i4 = size;
            protoOutputStream.write(1120986464259L, packageSetting.versionCode);
            protoOutputStream.write(1112396529670L, packageSetting.lastUpdateTime);
            protoOutputStream.write(1138166333447L, packageSetting.installSource.mInstallerPackageName);
            AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
            if (androidPackageInternal != null) {
                protoOutputStream.write(1138166333444L, androidPackageInternal.getVersionName());
                long start2 = protoOutputStream.start(2246267895816L);
                protoOutputStream.write(1138166333441L, "base");
                protoOutputStream.write(1120986464258L, packageSetting.pkg.getBaseRevisionCode());
                protoOutputStream.end(start2);
                for (int i5 = 0; i5 < packageSetting.pkg.getSplitNames().length; i5++) {
                    long start3 = protoOutputStream.start(2246267895816L);
                    protoOutputStream.write(1138166333441L, packageSetting.pkg.getSplitNames()[i5]);
                    protoOutputStream.write(1120986464258L, packageSetting.pkg.getSplitRevisionCodes()[i5]);
                    protoOutputStream.end(start3);
                }
                long start4 = protoOutputStream.start(1146756268042L);
                protoOutputStream.write(1138166333441L, packageSetting.installSource.mInitiatingPackageName);
                protoOutputStream.write(1138166333442L, packageSetting.installSource.mOriginatingPackageName);
                protoOutputStream.write(1138166333443L, packageSetting.installSource.mUpdateOwnerPackageName);
                protoOutputStream.end(start4);
            }
            protoOutputStream.write(1133871366146L, packageSetting.isLoading());
            int size2 = packageSetting.mUserStates.size();
            int i6 = 0;
            while (i6 < size2) {
                long start5 = protoOutputStream.start(2246267895817L);
                int keyAt = packageSetting.mUserStates.keyAt(i6);
                PackageUserStateInternal packageUserStateInternal = (PackageUserStateInternal) packageSetting.mUserStates.valueAt(i6);
                protoOutputStream.write(1120986464257L, keyAt);
                protoOutputStream.write(1159641169922L, packageUserStateInternal.isInstantApp() ? 2 : packageUserStateInternal.isInstalled() ? 1 : 0);
                protoOutputStream.write(1133871366147L, packageUserStateInternal.isHidden());
                protoOutputStream.write(1120986464266L, packageUserStateInternal.getDistractionFlags());
                protoOutputStream.write(1133871366148L, packageUserStateInternal.isSuspended());
                if (packageUserStateInternal.isSuspended()) {
                    int i7 = 0;
                    while (i7 < packageUserStateInternal.getSuspendParams().mStorage.size()) {
                        long j2 = start;
                        protoOutputStream.write(2237677961225L, ((UserPackage) packageUserStateInternal.getSuspendParams().mStorage.keyAt(i7)).packageName);
                        if (Flags.crossUserSuspensionEnabledRo()) {
                            protoOutputStream.write(2220498092045L, ((UserPackage) packageUserStateInternal.getSuspendParams().mStorage.keyAt(i7)).userId);
                        }
                        i7++;
                        start = j2;
                    }
                }
                long j3 = start;
                protoOutputStream.write(1133871366149L, packageUserStateInternal.isStopped());
                protoOutputStream.write(1133871366150L, !packageUserStateInternal.isNotLaunched());
                protoOutputStream.write(1159641169927L, packageUserStateInternal.getEnabledState());
                protoOutputStream.write(1138166333448L, packageUserStateInternal.getLastDisableAppCaller());
                protoOutputStream.write(1120986464267L, packageUserStateInternal.getFirstInstallTimeMillis());
                ArchiveState archiveState = packageUserStateInternal.getArchiveState();
                if (archiveState == null) {
                    i = size2;
                    i2 = i4;
                    j = start5;
                } else {
                    long start6 = protoOutputStream.start(1146756268044L);
                    Iterator it = archiveState.mActivityInfos.iterator();
                    while (it.hasNext()) {
                        ArchiveState.ArchiveActivityInfo archiveActivityInfo = (ArchiveState.ArchiveActivityInfo) it.next();
                        Iterator it2 = it;
                        int i8 = i4;
                        long start7 = protoOutputStream.start(2246267895809L);
                        int i9 = size2;
                        long j4 = start5;
                        protoOutputStream.write(1138166333441L, archiveActivityInfo.mTitle);
                        protoOutputStream.write(1138166333444L, archiveActivityInfo.mOriginalComponentName.flattenToString());
                        Path path = archiveActivityInfo.mIconBitmap;
                        if (path != null) {
                            protoOutputStream.write(1138166333442L, path.toAbsolutePath().toString());
                        }
                        Path path2 = archiveActivityInfo.mMonochromeIconBitmap;
                        if (path2 != null) {
                            protoOutputStream.write(1138166333443L, path2.toAbsolutePath().toString());
                        }
                        protoOutputStream.end(start7);
                        it = it2;
                        i4 = i8;
                        size2 = i9;
                        start5 = j4;
                    }
                    i = size2;
                    i2 = i4;
                    protoOutputStream.write(1138166333442L, archiveState.mInstallerTitle);
                    protoOutputStream.end(start6);
                    j = start5;
                }
                protoOutputStream.end(j);
                i6++;
                i4 = i2;
                start = j3;
                size2 = i;
            }
            long j5 = start;
            int i10 = i4;
            Iterator it3 = ((ArrayList) users).iterator();
            while (it3.hasNext()) {
                UserInfo userInfo = (UserInfo) it3.next();
                long start8 = protoOutputStream.start(2246267895820L);
                protoOutputStream.write(1120986464257L, userInfo.id);
                for (LegacyPermissionState.PermissionState permissionState : PermissionManagerService.this.mPermissionManagerServiceImpl.getLegacyPermissionState(packageSetting.mAppId).getPermissionStates(userInfo.id)) {
                    if (permissionState.mGranted) {
                        protoOutputStream.write(2237677961218L, permissionState.mName);
                    }
                }
                protoOutputStream.end(start8);
            }
            protoOutputStream.end(j5);
            i3++;
            size = i10;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0041, code lost:
    
        if (r11.contains(r6.mPermissionInfo.name) == false) goto L9;
     */
    @Override // com.android.server.pm.Computer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpPermissions(java.io.PrintWriter r9, java.lang.String r10, android.util.ArraySet r11, com.android.server.pm.DumpState r12) {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ComputerEngine.dumpPermissions(java.io.PrintWriter, java.lang.String, android.util.ArraySet, com.android.server.pm.DumpState):void");
    }

    @Override // com.android.server.pm.Computer
    public final void dumpSharedLibrariesProto(ProtoOutputStream protoOutputStream) {
        WatchedArrayMap watchedArrayMap = this.mSharedLibraries.mSharedLibraries;
        int size = watchedArrayMap.mStorage.size();
        for (int i = 0; i < size; i++) {
            WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) watchedArrayMap.mStorage.get((String) watchedArrayMap.mStorage.keyAt(i));
            if (watchedLongSparseArray != null) {
                int size2 = watchedLongSparseArray.mStorage.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(i2);
                    long start = protoOutputStream.start(2246267895811L);
                    protoOutputStream.write(1138166333441L, sharedLibraryInfo.getName());
                    boolean z = sharedLibraryInfo.getPath() != null;
                    protoOutputStream.write(1133871366146L, z);
                    if (z) {
                        protoOutputStream.write(1138166333443L, sharedLibraryInfo.getPath());
                    } else {
                        protoOutputStream.write(1138166333444L, sharedLibraryInfo.getPackageName());
                    }
                    protoOutputStream.end(start);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v10, types: [android.util.ArraySet] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [int] */
    /* JADX WARN: Type inference failed for: r14v4 */
    @Override // com.android.server.pm.Computer
    public final void dumpSharedUsers(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z) {
        com.android.server.pm.Settings settings = this.mSettings.mSettings;
        boolean z2 = false;
        boolean z3 = false;
        for (SharedUserSetting sharedUserSetting : settings.mSharedUsers.values()) {
            if (str == null || sharedUserSetting == dumpState.mSharedUser) {
                int i = sharedUserSetting.mAppId;
                PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = settings.mPermissionDataProvider;
                LegacyPermissionState legacyPermissionState = PermissionManagerService.this.mPermissionManagerServiceImpl.getLegacyPermissionState(i);
                if (arraySet == null || legacyPermissionState.hasPermissionState(arraySet)) {
                    String str2 = sharedUserSetting.name;
                    if (z) {
                        printWriter.print("suid,");
                        printWriter.print(sharedUserSetting.mAppId);
                        printWriter.print(",");
                        printWriter.println(str2);
                    } else {
                        if (!z3) {
                            boolean z4 = dumpState.mTitlePrinted;
                            dumpState.mTitlePrinted = true;
                            if (z4) {
                                printWriter.println();
                            }
                            printWriter.println("Shared users:");
                            z3 = true;
                        }
                        printWriter.print("  SharedUser [");
                        printWriter.print(str2);
                        printWriter.print("] (");
                        printWriter.print(Integer.toHexString(System.identityHashCode(sharedUserSetting)));
                        ProcessList$$ExternalSyntheticOutline0.m(printWriter, "):", "    ", "appId=");
                        printWriter.println(sharedUserSetting.mAppId);
                        printWriter.print("    ");
                        printWriter.println("Packages");
                        ?? r10 = sharedUserSetting.mPackages.mStorage;
                        int size = r10.size();
                        for (?? r14 = z2; r14 < size; r14++) {
                            PackageStateInternal packageStateInternal = (PackageStateInternal) r10.valueAt(r14);
                            if (packageStateInternal != null) {
                                printWriter.print("      ");
                                printWriter.println(packageStateInternal);
                            } else {
                                printWriter.print("      ");
                                printWriter.println("NULL?!");
                            }
                        }
                        if (!dumpState.isOptionEnabled(4)) {
                            List users = com.android.server.pm.Settings.getUsers(UserManagerService.getInstance(), z2, z2);
                            com.android.server.pm.Settings.dumpInstallPermissionsLPr(printWriter, "    ", arraySet, legacyPermissionState, users);
                            Iterator it = ((ArrayList) users).iterator();
                            while (it.hasNext()) {
                                int i2 = ((UserInfo) it.next()).id;
                                int[] gidsForUid = permissionManagerServiceInternalImpl.getGidsForUid(UserHandle.getUid(i2, sharedUserSetting.mAppId));
                                Collection permissionStates = legacyPermissionState.getPermissionStates(i2);
                                if (!ArrayUtils.isEmpty(gidsForUid) || !permissionStates.isEmpty()) {
                                    printWriter.print("    ");
                                    printWriter.print("User ");
                                    printWriter.print(i2);
                                    printWriter.println(": ");
                                    com.android.server.pm.Settings.dumpGidsLPr(printWriter, "      ", gidsForUid);
                                    com.android.server.pm.Settings.dumpRuntimePermissionsLPr(printWriter, "      ", arraySet, permissionStates, str != null);
                                }
                            }
                        }
                    }
                    z2 = false;
                }
            }
        }
    }

    @Override // com.android.server.pm.Computer
    public final void dumpSharedUsersProto(ProtoOutputStream protoOutputStream) {
        WatchedArrayMap watchedArrayMap = this.mSettings.mSettings.mSharedUsers;
        int size = watchedArrayMap.mStorage.size();
        for (int i = 0; i < size; i++) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) watchedArrayMap.mStorage.valueAt(i);
            sharedUserSetting.getClass();
            long start = protoOutputStream.start(2246267895814L);
            protoOutputStream.write(1120986464257L, sharedUserSetting.mAppId);
            protoOutputStream.write(1138166333442L, sharedUserSetting.name);
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.pm.Computer
    public final void enforceCrossUserOrProfilePermission(int i, int i2, String str) {
        if (i2 < 0) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Invalid userId "));
        }
        int userId = UserHandle.getUserId(i);
        if (hasCrossUserPermission(i, userId, i2, false, false)) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean isSameProfileGroup = UserManagerService.getInstance().isSameProfileGroup(userId, i2);
            if (isSameProfileGroup && PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.INTERACT_ACROSS_PROFILES", -1, i, getPackage(i).getPackageName()) == 0) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append("UID ");
            sb.append(i);
            sb.append(" requires android.permission.INTERACT_ACROSS_USERS_FULL");
            sb.append(" or android.permission.INTERACT_ACROSS_USERS");
            if (isSameProfileGroup) {
                sb.append(" or android.permission.INTERACT_ACROSS_PROFILES");
            }
            sb.append(" to access user ");
            sb.append(i2);
            sb.append(".");
            String sb2 = sb.toString();
            Slog.w("PackageManager", sb2);
            throw new SecurityException(sb2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void enforceCrossUserPermission(int i, boolean z, String str, int i2, boolean z2, boolean z3) {
        if (i2 < 0) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Invalid userId "));
        }
        if (z2) {
            PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerService().mLocalService, i, i2);
        }
        if (hasCrossUserPermission(i, UserHandle.getUserId(i), i2, z, z3)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append("UID ");
        sb.append(i);
        sb.append(" requires android.permission.INTERACT_ACROSS_USERS_FULL");
        if (!z) {
            sb.append(" or android.permission.INTERACT_ACROSS_USERS");
        }
        sb.append(" to access user ");
        sb.append(i2);
        sb.append(".");
        String sb2 = sb.toString();
        Slog.w("PackageManager", sb2);
        throw new SecurityException(sb2);
    }

    @Override // com.android.server.pm.Computer
    public final void enforceCrossUserPermission(String str, int i, int i2, boolean z, boolean z2) {
        enforceCrossUserPermission(i, z, str, i2, z2, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean filterAppAccess(int i, int i2) {
        if (Process.isSdkSandboxUid(i)) {
            return (i2 == i || Process.getAppUidForSdkSandboxUid(i) == i) ? false : true;
        }
        int userId = UserHandle.getUserId(i);
        Watchable settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (settingLPr == null) {
            return true;
        }
        if (settingLPr instanceof SharedUserSetting) {
            return shouldFilterApplicationIncludingUninstalled((SharedUserSetting) settingLPr, i2, userId);
        }
        if (settingLPr instanceof PackageStateInternal) {
            return shouldFilterApplicationIncludingUninstalled((PackageStateInternal) settingLPr, i2, userId);
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public final boolean filterAppAccess(int i, int i2, String str, boolean z) {
        return shouldFilterApplication(getPackageStateInternal(str), i, null, 0, i2, z, true);
    }

    @Override // com.android.server.pm.Computer
    public final boolean filterAppAccess(AndroidPackage androidPackage, int i, int i2) {
        return shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), i, i2);
    }

    @Override // com.android.server.pm.Computer
    public final String[] filterOnlySystemPackages(String... strArr) {
        if (strArr == null) {
            return (String[]) ArrayUtils.emptyArray(String.class);
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            if (str != null) {
                PackageSetting packageStateInternal = getPackageStateInternal(str);
                if (packageStateInternal == null || packageStateInternal.pkg == null) {
                    Log.w("PackageManager", "Could not find package ".concat(str));
                } else if (packageStateInternal.isSystem()) {
                    arrayList.add(str);
                } else {
                    Log.w("PackageManager", str.concat(" is not system"));
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public final boolean filterSharedLibPackage(PackageStateInternal packageStateInternal, int i, int i2, long j) {
        int i3;
        PackageSetting packageLPr;
        int indexOf;
        int indexOf2;
        long j2 = j & 67108864;
        Settings settings = this.mSettings;
        SharedLibrariesImpl sharedLibrariesImpl = this.mSharedLibraries;
        if ((j2 == 0 || (!PackageManagerServiceUtils.isSystemOrRootOrShell(UserHandle.getAppId(i)) && checkUidPermission("android.permission.INSTALL_PACKAGES", i) != 0)) && packageStateInternal != null && packageStateInternal.getPkg() != null && packageStateInternal.getPkg().isStaticSharedLibrary()) {
            SharedLibraryInfo sharedLibraryInfo = sharedLibrariesImpl.getSharedLibraryInfo(packageStateInternal.getPkg().getStaticSharedLibraryVersion(), packageStateInternal.getPkg().getStaticSharedLibraryName());
            if (sharedLibraryInfo != null) {
                String[] packagesForUid = getPackagesForUid(UserHandle.getUid(i2, UserHandle.getAppId(i)));
                if (packagesForUid != null) {
                    for (String str : packagesForUid) {
                        i3 = (!packageStateInternal.getPackageName().equals(str) && ((packageLPr = settings.mSettings.getPackageLPr(str)) == null || (indexOf = ArrayUtils.indexOf(packageLPr.getUsesStaticLibraries(), sharedLibraryInfo.getName())) < 0 || packageLPr.pkg.getUsesStaticLibrariesVersions()[indexOf] != sharedLibraryInfo.getLongVersion())) ? i3 + 1 : 0;
                    }
                }
                return true;
            }
        }
        if ((j2 != 0 && (PackageManagerServiceUtils.isSystemOrRootOrShell(UserHandle.getAppId(i)) || checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0)) || packageStateInternal == null || packageStateInternal.getPkg() == null || !packageStateInternal.getPkg().isSdkLibrary()) {
            return false;
        }
        SharedLibraryInfo sharedLibraryInfo2 = sharedLibrariesImpl.getSharedLibraryInfo(packageStateInternal.getPkg().getSdkLibVersionMajor(), packageStateInternal.getPkg().getSdkLibraryName());
        if (sharedLibraryInfo2 == null) {
            return false;
        }
        String[] packagesForUid2 = getPackagesForUid(UserHandle.getUid(i2, UserHandle.getAppId(i)));
        if (packagesForUid2 != null) {
            for (String str2 : packagesForUid2) {
                if (packageStateInternal.getPackageName().equals(str2)) {
                    return false;
                }
                PackageSetting packageLPr2 = settings.mSettings.getPackageLPr(str2);
                if (packageLPr2 != null && (indexOf2 = ArrayUtils.indexOf(packageLPr2.getUsesSdkLibraries(), sharedLibraryInfo2.getName())) >= 0 && packageLPr2.pkg.getUsesSdkLibrariesVersionsMajor()[indexOf2] == sharedLibraryInfo2.getLongVersion()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public final ResolveInfo findPersistentPreferredActivity(Intent intent, String str, long j, List list, boolean z, int i) {
        int size = list.size();
        PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mSettings.mSettings.mPersistentPreferredActivities.mStorage.get(i);
        if (z) {
            Slog.v("PackageManager", "Looking for persistent preferred activities...");
        }
        List queryIntent = persistentPreferredIntentResolver != null ? persistentPreferredIntentResolver.queryIntent(this, intent, str, (j & 65536) != 0, i, 0L) : null;
        if (queryIntent != null && queryIntent.size() > 0) {
            int size2 = queryIntent.size();
            for (int i2 = 0; i2 < size2; i2++) {
                PersistentPreferredActivity persistentPreferredActivity = (PersistentPreferredActivity) queryIntent.get(i2);
                if (z) {
                    StringBuilder sb = new StringBuilder("Checking PersistentPreferredActivity ds=");
                    sb.append(persistentPreferredActivity.mFilter.countDataSchemes() > 0 ? persistentPreferredActivity.mFilter.getDataScheme(0) : "<none>");
                    sb.append("\n  component=");
                    sb.append(persistentPreferredActivity.mComponent);
                    Slog.v("PackageManager", sb.toString());
                    persistentPreferredActivity.mFilter.dump(new LogPrinter(2, "PackageManager", 3), "  ");
                }
                ActivityInfo activityInfo = getActivityInfo(persistentPreferredActivity.mComponent, j | 512, i);
                if (z) {
                    Slog.v("PackageManager", "Found persistent preferred activity:");
                    if (activityInfo != null) {
                        activityInfo.dump(new LogPrinter(2, "PackageManager", 3), "  ");
                    } else {
                        Slog.v("PackageManager", "  null");
                    }
                }
                if (activityInfo != null) {
                    for (int i3 = 0; i3 < size; i3++) {
                        ResolveInfo resolveInfo = (ResolveInfo) list.get(i3);
                        if (resolveInfo.activityInfo.applicationInfo.packageName.equals(activityInfo.applicationInfo.packageName) && resolveInfo.activityInfo.name.equals(activityInfo.name)) {
                            if (z) {
                                Slog.v("PackageManager", "Returning persistent preferred activity: " + resolveInfo.activityInfo.packageName + "/" + resolveInfo.activityInfo.name);
                            }
                            return resolveInfo;
                        }
                    }
                }
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:267:0x046c, code lost:
    
        if (r2 == r14) goto L193;
     */
    /* JADX WARN: Removed duplicated region for block: B:126:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x060b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.pm.PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityBody(android.content.Intent r31, java.lang.String r32, long r33, java.util.List r35, boolean r36, boolean r37, boolean r38, int r39, boolean r40, int r41, boolean r42) {
        /*
            Method dump skipped, instructions count: 1623
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ComputerEngine.findPreferredActivityBody(android.content.Intent, java.lang.String, long, java.util.List, boolean, boolean, boolean, int, boolean, int, boolean):com.android.server.pm.PackageManagerService$FindPreferredActivityBodyResult");
    }

    @Override // com.android.server.pm.Computer
    public final PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal2(Intent intent, String str, long j, List list, boolean z, boolean z2, boolean z3, int i, boolean z4, boolean z5) {
        return findPreferredActivityBody(intent, str, j, list, z, z2, z3, i, z4, Binder.getCallingUid(), z5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.util.ArrayList] */
    @Override // com.android.server.pm.Computer
    public final List findSharedNonSystemLibraries(PackageStateInternal packageStateInternal) {
        ?? emptyList;
        if (packageStateInternal.getTransientState().usesLibraryInfos.isEmpty()) {
            emptyList = Collections.emptyList();
        } else {
            emptyList = new ArrayList();
            HashSet hashSet = new HashSet();
            Iterator it = packageStateInternal.getTransientState().usesLibraryInfos.iterator();
            while (it.hasNext()) {
                SharedLibraryUtils.findSharedLibrariesRecursive(((SharedLibraryWrapper) it.next()).mInfo, emptyList, hashSet);
            }
        }
        if (emptyList.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = emptyList.iterator();
        while (it2.hasNext()) {
            PackageSetting packageStateInternal2 = getPackageStateInternal(((SharedLibraryInfo) it2.next()).getPackageName());
            if (packageStateInternal2 != null && packageStateInternal2.pkg != null) {
                arrayList.add(packageStateInternal2);
            }
        }
        return arrayList;
    }

    public final ApplicationInfo generateApplicationInfoFromSettings(int i, int i2, long j, String str) {
        PackageSetting packageLPr;
        if (!this.mUserManager.mLocalService.exists(i2) || (packageLPr = this.mSettings.mSettings.getPackageLPr(str)) == null || filterSharedLibPackage(packageLPr, i, i2, j) || shouldFilterApplication(packageLPr, i, i2)) {
            return null;
        }
        AndroidPackageInternal androidPackageInternal = packageLPr.pkg;
        if (androidPackageInternal == null) {
            PackageInfo generatePackageInfo = generatePackageInfo(packageLPr, j, i2);
            if (generatePackageInfo != null) {
                return generatePackageInfo.applicationInfo;
            }
            return null;
        }
        ApplicationInfo generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(androidPackageInternal, j, packageLPr.getUserStateOrDefault(i2), i2, packageLPr);
        if (generateApplicationInfo != null) {
            generateApplicationInfo.packageName = resolveExternalPackageName(packageLPr.pkg);
        }
        return generateApplicationInfo;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0413  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x04e0 A[LOOP:7: B:188:0x04de->B:189:0x04e0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0576  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x05ea  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0579  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.PackageInfo generatePackageInfo(com.android.server.pm.pkg.PackageStateInternal r26, long r27, int r29) {
        /*
            Method dump skipped, instructions count: 1526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ComputerEngine.generatePackageInfo(com.android.server.pm.pkg.PackageStateInternal, long, int):android.content.pm.PackageInfo");
    }

    @Override // com.android.server.pm.Computer
    public final ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) {
        return getActivityInfoInternal(Binder.getCallingUid(), i, j, componentName);
    }

    @Override // com.android.server.pm.Computer
    public final ActivityInfo getActivityInfoInternal(int i, int i2, long j, ComponentName componentName) {
        if (!this.mUserManager.mLocalService.exists(i2)) {
            return null;
        }
        long updateFlags = updateFlags(i2, j) | 536870912;
        if (!isRecentsAccessingChildProfiles(Binder.getCallingUid(), i2)) {
            enforceCrossUserPermission(Binder.getCallingUid(), false, "get activity info", i2, false, false);
        }
        return getActivityInfoInternalBody(i, i2, updateFlags, componentName);
    }

    public final ActivityInfo getActivityInfoInternalBody(int i, int i2, long j, ComponentName componentName) {
        AndroidPackage androidPackage;
        ParsedMainComponent activity = this.mComponentResolver.getActivity(componentName);
        long j2 = j | 8589934592L;
        if (activity == null) {
            androidPackage = null;
        } else {
            androidPackage = (AndroidPackage) this.mPackages.mStorage.get(activity.getPackageName());
        }
        if (androidPackage != null && this.mSettings.isEnabledAndMatch(androidPackage, activity, j2, i2)) {
            PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(componentName.getPackageName());
            if (packageLPr == null || shouldFilterApplication(packageLPr, i, componentName, 1, i2, false, true)) {
                return null;
            }
            return PackageInfoUtils.generateActivityInfo(androidPackage, activity, j2, packageLPr.getUserStateOrDefault(i2), i2, packageLPr);
        }
        if (!resolveComponentName().equals(componentName)) {
            return null;
        }
        ActivityInfo activityInfo = this.mResolveActivity;
        PackageUserStateDefault packageUserStateDefault = PackageUserStateInternal.DEFAULT;
        String str = PackageInfoUtils.SYSTEM_DATA_PATH;
        if (activityInfo == null || !PackageInfoUtils.checkUseInstalledOrHidden(j2, packageUserStateDefault, activityInfo.applicationInfo)) {
            return null;
        }
        ActivityInfo activityInfo2 = new ActivityInfo(activityInfo);
        activityInfo2.applicationInfo = PackageInfoUtils.generateDelegateApplicationInfo(activityInfo2.applicationInfo, j2, packageUserStateDefault, i2);
        return activityInfo2;
    }

    @Override // com.android.server.pm.Computer
    public final String[] getAllAvailablePackageNames() {
        return (String[]) this.mPackages.keySet().toArray(new String[0]);
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice getAllIntentFilters(String str) {
        if (TextUtils.isEmpty(str)) {
            return ParceledListSlice.emptyList();
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.pkg;
        if (androidPackage == null || ArrayUtils.isEmpty(androidPackage.getActivities())) {
            return ParceledListSlice.emptyList();
        }
        if (shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, userId)) {
            return ParceledListSlice.emptyList();
        }
        int size = ArrayUtils.size(androidPackage.getActivities());
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            List intents = ((ParsedActivity) androidPackage.getActivities().get(i)).getIntents();
            for (int i2 = 0; i2 < intents.size(); i2++) {
                arrayList.add(new IntentFilter(((ParsedIntentInfo) intents.get(i2)).getIntentFilter()));
            }
        }
        return new ParceledListSlice(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public final List getAllPackages() {
        boolean z = PackageManagerServiceUtils.DEBUG;
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("getAllPackages is limited to privileged callers");
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        boolean canViewInstantApps = canViewInstantApps(callingUid, userId);
        WatchedArrayMap watchedArrayMap = this.mPackages;
        if (canViewInstantApps) {
            return new ArrayList(watchedArrayMap.keySet());
        }
        String instantAppPackageName = getInstantAppPackageName(callingUid);
        ArrayList arrayList = new ArrayList();
        if (instantAppPackageName != null) {
            for (AndroidPackage androidPackage : watchedArrayMap.values()) {
                if (androidPackage.isVisibleToInstantApps()) {
                    arrayList.add(androidPackage.getPackageName());
                }
            }
        } else {
            for (AndroidPackage androidPackage2 : watchedArrayMap.values()) {
                PackageSetting packageStateInternal = getPackageStateInternal(androidPackage2.getPackageName());
                if (packageStateInternal != null && packageStateInternal.getUserStateOrDefault(userId).isInstantApp()) {
                    if (!this.mInstantAppRegistry.isInstantAccessGranted(userId, UserHandle.getAppId(callingUid), packageStateInternal.mAppId)) {
                    }
                }
                arrayList.add(androidPackage2.getPackageName());
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    public final String[] getAppOpPermissionPackages(String str, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "getAppOpPermissionPackages", i, false, false);
        if (str == null || getInstantAppPackageName(callingUid) != null || !this.mUserManager.mLocalService.exists(i)) {
            return EmptyArray.STRING;
        }
        ArraySet arraySet = new ArraySet(PermissionManagerService.this.mPermissionManagerServiceImpl.getAppOpPermissionPackages(str));
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            if (shouldFilterApplicationIncludingUninstalled(this.mSettings.mSettings.getPackageLPr((String) arraySet.valueAt(size)), callingUid, i)) {
                arraySet.removeAt(size);
            }
        }
        return (String[]) arraySet.toArray(new String[arraySet.size()]);
    }

    @Override // com.android.server.pm.Computer
    public final int getApplicationEnabledSetting(String str, int i) {
        Settings settings = this.mSettings;
        if (!this.mUserManager.mLocalService.exists(i)) {
            return 2;
        }
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "get enabled", i, false, false);
        try {
            if (shouldFilterApplicationIncludingUninstalled(settings.mSettings.getPackageLPr(str), callingUid, i)) {
                throw new PackageManager.NameNotFoundException(str);
            }
            return settings.getApplicationEnabledSetting(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown package: ", str));
        }
    }

    @Override // com.android.server.pm.Computer
    public final boolean getApplicationHiddenSettingAsUser(String str, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USERS", null);
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, true, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "getApplicationHidden for user "), i, false, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(str);
            if (packageLPr == null) {
                return true;
            }
            if (shouldFilterApplicationIncludingUninstalled(packageLPr, callingUid, i)) {
                return true;
            }
            return packageLPr.getUserStateOrDefault(i).isHidden();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public final ApplicationInfo getApplicationInfo(String str, long j, int i) {
        return getApplicationInfoInternal(Binder.getCallingUid(), i, j, str);
    }

    @Override // com.android.server.pm.Computer
    public final ApplicationInfo getApplicationInfoInternal(int i, int i2, long j, String str) {
        if (!this.mUserManager.mLocalService.exists(i2)) {
            return null;
        }
        long updateFlagsForPackage = updateFlagsForPackage(i2, j);
        if (!isRecentsAccessingChildProfiles(Binder.getCallingUid(), i2)) {
            enforceCrossUserPermission(Binder.getCallingUid(), false, "get application info", i2, false, false);
        }
        String resolveInternalPackageName = resolveInternalPackageName(-1L, str);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(resolveInternalPackageName);
        boolean z = (1073741824 & updateFlagsForPackage) != 0;
        if (androidPackage == null) {
            if ("android".equals(resolveInternalPackageName) || "system".equals(resolveInternalPackageName)) {
                return androidApplication();
            }
            if ((4299169792L & updateFlagsForPackage) != 0) {
                return generateApplicationInfoFromSettings(i, i2, updateFlagsForPackage, resolveInternalPackageName);
            }
            return null;
        }
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(resolveInternalPackageName);
        if (packageLPr == null) {
            return null;
        }
        int i3 = (SemDualAppManager.isDualAppId(i2) && SemDualAppManager.isDualAppId(UserHandle.getUserId(Binder.getCallingUid())) && DualAppManagerService.shouldForwardToOwner(resolveInternalPackageName)) ? 0 : i2;
        if ((!z && androidPackage.isApex()) || filterSharedLibPackage(packageLPr, i, i3, updateFlagsForPackage) || shouldFilterApplication(packageLPr, i, i3)) {
            return null;
        }
        ApplicationInfo generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(androidPackage, updateFlagsForPackage, packageLPr.getUserStateOrDefault(i3), i3, packageLPr);
        if (generateApplicationInfo == null) {
            return generateApplicationInfo;
        }
        generateApplicationInfo.packageName = resolveExternalPackageName(androidPackage);
        return generateApplicationInfo;
    }

    public final int getBaseSdkSandboxUid() {
        return getPackage(this.mService.mRequiredSdkSandboxPackage).getUid();
    }

    @Override // com.android.server.pm.Computer
    public final boolean getBlockUninstall(int i, String str) {
        ArraySet arraySet = (ArraySet) this.mSettings.mSettings.mBlockUninstallPackages.mStorage.get(i);
        if (arraySet == null) {
            return false;
        }
        return arraySet.contains(str);
    }

    @Override // com.android.server.pm.Computer
    public final boolean getBlockUninstallForUser(String str, int i) {
        boolean z;
        PackageSetting packageLPr;
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (asInterface != null) {
            try {
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (!asInterface.getApplicationUninstallationEnabledAsUser(str, i)) {
                Log.d("PackageManager", "This app uninstallation is not allowed");
                z = true;
                Settings settings = this.mSettings;
                packageLPr = settings.mSettings.getPackageLPr(str);
                int callingUid = Binder.getCallingUid();
                if (packageLPr == null && !shouldFilterApplicationIncludingUninstalled(packageLPr, callingUid, i)) {
                    if (z) {
                        return true;
                    }
                    ArraySet arraySet = (ArraySet) settings.mSettings.mBlockUninstallPackages.mStorage.get(i);
                    if (arraySet == null) {
                        return false;
                    }
                    return arraySet.contains(str);
                }
            }
        }
        z = false;
        Settings settings2 = this.mSettings;
        packageLPr = settings2.mSettings.getPackageLPr(str);
        int callingUid2 = Binder.getCallingUid();
        return packageLPr == null ? false : false;
    }

    @Override // com.android.server.pm.Computer
    public final int getComponentEnabledSetting(int i, int i2, ComponentName componentName) {
        enforceCrossUserPermission(i, false, "getComponentEnabled", i2, false, false);
        return getComponentEnabledSettingInternal(i, i2, componentName);
    }

    @Override // com.android.server.pm.Computer
    public final int getComponentEnabledSettingInternal(int i, int i2, ComponentName componentName) {
        Settings settings = this.mSettings;
        if (componentName == null) {
            return 0;
        }
        if (!this.mUserManager.mLocalService.exists(i2)) {
            return 2;
        }
        try {
            if (shouldFilterApplication(settings.mSettings.getPackageLPr(componentName.getPackageName()), i, componentName, 0, i2, true, true)) {
                throw new PackageManager.NameNotFoundException(componentName.getPackageName());
            }
            return settings.getComponentEnabledSetting(componentName, i2);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Unknown component: "));
        }
    }

    @Override // com.android.server.pm.Computer
    public final ComponentResolverApi getComponentResolver() {
        return this.mComponentResolver;
    }

    @Override // com.android.server.pm.Computer
    public final CrossProfileDomainInfo getCrossProfileDomainPreferredLpr(Intent intent, String str, long j, int i, int i2) {
        List queryActivities;
        if (!this.mUserManager.hasUserRestriction("allow_parent_profile_app_linking", i) || (queryActivities = this.mComponentResolver.queryActivities(this, intent, str, j, i2)) == null || queryActivities.isEmpty()) {
            return null;
        }
        int size = queryActivities.size();
        CrossProfileDomainInfo crossProfileDomainInfo = null;
        for (int i3 = 0; i3 < size; i3++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryActivities.get(i3);
            if (!resolveInfo.handleAllWebDataURI) {
                PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(resolveInfo.activityInfo.packageName);
                if (packageLPr != null) {
                    int approvalLevelForDomain = ((DomainVerificationService) this.mDomainVerificationManager).approvalLevelForDomain(packageLPr, intent, j, i2);
                    if (crossProfileDomainInfo == null) {
                        crossProfileDomainInfo = new CrossProfileDomainInfo(createForwardingResolveInfoUnchecked(new WatchedIntentFilter(), i, i2), approvalLevelForDomain, i2);
                    } else {
                        crossProfileDomainInfo.mHighestApprovalLevel = Math.max(approvalLevelForDomain, crossProfileDomainInfo.mHighestApprovalLevel);
                    }
                }
            }
        }
        if (crossProfileDomainInfo == null || crossProfileDomainInfo.mHighestApprovalLevel > 0) {
            return crossProfileDomainInfo;
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        WatchedLongSparseArray watchedLongSparseArray;
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_SHARED_LIBRARIES", "getDeclaredSharedLibraries");
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, true, "getDeclaredSharedLibraries", i, false, false);
        Preconditions.checkNotNull(str, "packageName cannot be null");
        Preconditions.checkArgumentNonnegative(i, "userId must be >= 0");
        if (!this.mUserManager.mLocalService.exists(i) || getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        WatchedArrayMap watchedArrayMap = this.mSharedLibraries.mSharedLibraries;
        int size = watchedArrayMap.mStorage.size();
        ArrayList arrayList = null;
        int i6 = 0;
        while (i6 < size) {
            WatchedLongSparseArray watchedLongSparseArray2 = (WatchedLongSparseArray) watchedArrayMap.mStorage.valueAt(i6);
            if (watchedLongSparseArray2 == null) {
                i2 = i6;
            } else {
                int size2 = watchedLongSparseArray2.mStorage.size();
                ArrayList arrayList2 = arrayList;
                int i7 = 0;
                while (i7 < size2) {
                    SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray2.mStorage.valueAt(i7);
                    VersionedPackage declaringPackage = sharedLibraryInfo.getDeclaringPackage();
                    if (Objects.equals(declaringPackage.getPackageName(), str)) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            i3 = size2;
                            i4 = i7;
                            i5 = i6;
                            watchedLongSparseArray = watchedLongSparseArray2;
                            if (getPackageInfoInternal(Binder.getCallingUid(), i, declaringPackage.getPackageName(), declaringPackage.getLongVersionCode(), j | 67108864) != null) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                SharedLibraryInfo sharedLibraryInfo2 = new SharedLibraryInfo(sharedLibraryInfo.getPath(), sharedLibraryInfo.getPackageName(), sharedLibraryInfo.getAllCodePaths(), sharedLibraryInfo.getName(), sharedLibraryInfo.getLongVersion(), sharedLibraryInfo.getType(), sharedLibraryInfo.getDeclaringPackage(), (List) getPackagesUsingSharedLibrary(sharedLibraryInfo, j, callingUid, i).first, sharedLibraryInfo.getDependencies() == null ? null : new ArrayList(sharedLibraryInfo.getDependencies()), sharedLibraryInfo.isNative());
                                if (arrayList2 == null) {
                                    arrayList2 = new ArrayList();
                                }
                                ArrayList arrayList3 = arrayList2;
                                arrayList3.add(sharedLibraryInfo2);
                                arrayList2 = arrayList3;
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } else {
                        i3 = size2;
                        i4 = i7;
                        i5 = i6;
                        watchedLongSparseArray = watchedLongSparseArray2;
                    }
                    i7 = i4 + 1;
                    i6 = i5;
                    watchedLongSparseArray2 = watchedLongSparseArray;
                    size2 = i3;
                }
                i2 = i6;
                arrayList = arrayList2;
            }
            i6 = i2 + 1;
        }
        if (arrayList != null) {
            return new ParceledListSlice(arrayList);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final ComponentName getDefaultHomeActivity(int i) {
        ArrayList arrayList = new ArrayList();
        ComponentName homeActivitiesAsUser = getHomeActivitiesAsUser(i, arrayList);
        if (homeActivitiesAsUser != null) {
            return homeActivitiesAsUser;
        }
        Slog.w("PackageManager", "Default package for ROLE_HOME is not set in RoleManager");
        int size = arrayList.size();
        int i2 = Integer.MIN_VALUE;
        ComponentName componentName = null;
        for (int i3 = 0; i3 < size; i3++) {
            ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i3);
            int i4 = resolveInfo.priority;
            if (i4 > i2) {
                componentName = resolveInfo.activityInfo.getComponentName();
                i2 = resolveInfo.priority;
            } else if (i4 == i2) {
                componentName = null;
            }
        }
        return componentName;
    }

    @Override // com.android.server.pm.Computer
    public final PackageSetting getDisabledSystemPackage(String str) {
        return this.mSettings.mSettings.getDisabledSystemPkgLPr(str);
    }

    @Override // com.android.server.pm.Computer
    public final ArrayMap getDisabledSystemPackageStates() {
        return this.mSettings.mSettings.mDisabledSysPackages.mStorage;
    }

    @Override // com.android.server.pm.Computer
    public final int getFlagsForUid(int i) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return 0;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        int userId = UserHandle.getUserId(callingUid);
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (settingLPr instanceof SharedUserSetting) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) settingLPr;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return 0;
            }
            return sharedUserSetting.mPkgFlags;
        }
        if (!(settingLPr instanceof PackageSetting)) {
            return 0;
        }
        PackageSetting packageSetting = (PackageSetting) settingLPr;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return 0;
        }
        return packageSetting.mPkgFlags;
    }

    @Override // com.android.server.pm.Computer
    public final WatchedArrayMap getFrozenPackages() {
        return this.mFrozenPackages;
    }

    @Override // com.android.server.pm.Computer
    public final ProviderInfo getGrantImplicitAccessProviderInfo(int i, String str) {
        ApplicationInfo applicationInfo;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(i);
        ProviderInfo resolveContentProvider = resolveContentProvider(UserHandle.getUserId(callingUid), callingUid, 0L, "com.android.contacts");
        if (resolveContentProvider == null || (applicationInfo = resolveContentProvider.applicationInfo) == null || !UserHandle.isSameApp(applicationInfo.uid, callingUid)) {
            throw new SecurityException(NandswapManager$$ExternalSyntheticOutline0.m(callingUid, " is not allow to call grantImplicitAccess"));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return resolveContentProvider(userId, callingUid, 0L, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public final CharSequence getHarmfulAppWarning(String str, int i) {
        int callingUid = Binder.getCallingUid();
        int appId = UserHandle.getAppId(callingUid);
        enforceCrossUserPermission(callingUid, true, "getHarmfulAppInfo", i, true, false);
        if (!PackageManagerServiceUtils.isSystemOrRoot(appId) && checkUidPermission("android.permission.SET_HARMFUL_APP_WARNINGS", callingUid) != 0) {
            throw new SecurityException("Caller must have the android.permission.SET_HARMFUL_APP_WARNINGS permission.");
        }
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal != null) {
            return packageStateInternal.getUserStateOrDefault(i).getHarmfulAppWarning();
        }
        throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown package: ", str));
    }

    @Override // com.android.server.pm.Computer
    public final ComponentName getHomeActivitiesAsUser(int i, List list) {
        int i2;
        ActivityInfo activityInfo;
        Intent homeIntent = getHomeIntent();
        List queryIntentActivitiesInternal = queryIntentActivitiesInternal(homeIntent, null, 128L, i);
        list.clear();
        if (queryIntentActivitiesInternal == null) {
            return null;
        }
        list.addAll(queryIntentActivitiesInternal);
        String defaultHome = this.mDefaultAppProvider.getDefaultHome(i);
        if (defaultHome == null) {
            i2 = 0;
            ResolveInfo resolveInfo = findPreferredActivityBody(homeIntent, null, 0L, queryIntentActivitiesInternal, true, false, false, i, UserHandle.getAppId(Binder.getCallingUid()) >= 10000, Binder.getCallingUid(), Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 1).mPreferredResolveInfo;
            if (resolveInfo != null && (activityInfo = resolveInfo.activityInfo) != null) {
                defaultHome = activityInfo.packageName;
            }
        } else {
            i2 = 0;
        }
        if (defaultHome == null) {
            return null;
        }
        int size = queryIntentActivitiesInternal.size();
        for (int i3 = i2; i3 < size; i3++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentActivitiesInternal.get(i3);
            ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
            if (activityInfo2 != null && TextUtils.equals(activityInfo2.packageName, defaultHome)) {
                ActivityInfo activityInfo3 = resolveInfo2.activityInfo;
                return new ComponentName(activityInfo3.packageName, activityInfo3.name);
            }
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final Intent getHomeIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        return intent;
    }

    @Override // com.android.server.pm.Computer
    public final int getInstallReason(String str, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, true, "get install reason", i, false, false);
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(str);
        if (packageLPr == null || shouldFilterApplicationIncludingUninstalled(packageLPr, callingUid, i)) {
            return 0;
        }
        return packageLPr.getUserStateOrDefault(i).getInstallReason();
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a0, code lost:
    
        if (isCallerSameApp(r11, r16, false) != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00b7, code lost:
    
        if (shouldFilterApplicationIncludingUninstalled(r1, r11, r17) == false) goto L56;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00a3  */
    @Override // com.android.server.pm.Computer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.InstallSourceInfo getInstallSourceInfo(java.lang.String r16, int r17) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ComputerEngine.getInstallSourceInfo(java.lang.String, int):android.content.pm.InstallSourceInfo");
    }

    @Override // com.android.server.pm.Computer
    public final List getInstalledApplications(int i, int i2, boolean z, long j) {
        ArrayList arrayList;
        ApplicationInfo generateApplicationInfoFromSettings;
        ApplicationInfo generateApplicationInfo;
        if (getInstantAppPackageName(i2) == null && this.mUserManager.mLocalService.exists(i)) {
            long updateFlagsForPackage = updateFlagsForPackage(i, j);
            boolean z2 = (4202496 & updateFlagsForPackage) != 0;
            boolean z3 = (1073741824 & updateFlagsForPackage) != 0;
            boolean z4 = (z2 || (4294967296L & updateFlagsForPackage) == 0) ? false : true;
            if (!z) {
                enforceCrossUserPermission(i2, false, "get installed application info", i, false, false);
            }
            boolean z5 = PMRune.PM_NAL_GET_APP_LIST;
            Settings settings = this.mSettings;
            if (z5) {
                if (GetAppListHelper.checkCallingGetAppList(this.mContext, i2, this.mGetAppListHelper.isExemptedPackage(i2, settings.mSettings.getSettingLPr(UserHandle.getAppId(i2))), new ComputerEngine$$ExternalSyntheticLambda2(1, this))) {
                    this.mInjector.getHandler().post(new ComputerEngine$$ExternalSyntheticLambda3(this, i2, Binder.getCallingPid(), 1));
                    return Collections.emptyList();
                }
            }
            ArrayMap packages = settings.getPackages();
            if (z2 || z4) {
                arrayList = new ArrayList(packages.size());
                for (PackageStateInternal packageStateInternal : packages.values()) {
                    long j2 = packageStateInternal.isSystem() ? 4194304 | updateFlagsForPackage : updateFlagsForPackage;
                    if (packageStateInternal.getPkg() == null) {
                        generateApplicationInfoFromSettings = generateApplicationInfoFromSettings(i2, i, j2, packageStateInternal.getPackageName());
                    } else if (z3 || !packageStateInternal.getPkg().isApex()) {
                        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                        if (!z4 || userStateOrDefault.isInstalled() || userStateOrDefault.getArchiveState() != null) {
                            if (!filterSharedLibPackage(packageStateInternal, i2, i, updateFlagsForPackage) && !shouldFilterApplication(packageStateInternal, i2, i)) {
                                generateApplicationInfoFromSettings = PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), j2, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
                                if (generateApplicationInfoFromSettings != null) {
                                    generateApplicationInfoFromSettings.packageName = resolveExternalPackageName(packageStateInternal.getPkg());
                                }
                            }
                        }
                    }
                    if (generateApplicationInfoFromSettings != null) {
                        arrayList.add(generateApplicationInfoFromSettings);
                    }
                }
            } else {
                arrayList = new ArrayList(this.mPackages.mStorage.size());
                for (PackageStateInternal packageStateInternal2 : packages.values()) {
                    AndroidPackageInternal pkg = packageStateInternal2.getPkg();
                    if (pkg != null && (z3 || !pkg.isApex())) {
                        if (!filterSharedLibPackage(packageStateInternal2, Binder.getCallingUid(), i, updateFlagsForPackage) && !shouldFilterApplication(packageStateInternal2, i2, i) && (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(pkg, updateFlagsForPackage, packageStateInternal2.getUserStateOrDefault(i), i, packageStateInternal2)) != null) {
                            generateApplicationInfo.packageName = resolveExternalPackageName(pkg);
                            arrayList.add(generateApplicationInfo);
                        }
                    }
                }
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice getInstalledPackages(long j, int i) {
        ArrayList arrayList;
        PackageStateInternal packageStateInternal;
        PackageInfo generatePackageInfo;
        PackageSetting packageSetting;
        PackageInfo generatePackageInfo2;
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return ParceledListSlice.emptyList();
        }
        boolean z = PMRune.PM_NAL_GET_APP_LIST;
        Settings settings = this.mSettings;
        if (z) {
            if (GetAppListHelper.checkCallingGetAppList(this.mContext, callingUid, this.mGetAppListHelper.isExemptedPackage(callingUid, settings.mSettings.getSettingLPr(UserHandle.getAppId(callingUid))), new ComputerEngine$$ExternalSyntheticLambda2(0, this))) {
                this.mInjector.getHandler().post(new ComputerEngine$$ExternalSyntheticLambda3(this, callingUid, Binder.getCallingPid(), 0));
                return ParceledListSlice.emptyList();
            }
        }
        if (!this.mUserManager.mLocalService.exists(i)) {
            return ParceledListSlice.emptyList();
        }
        long updateFlagsForPackage = updateFlagsForPackage(i, j);
        enforceCrossUserPermission(callingUid, false, "get installed packages", i, false, false);
        boolean z2 = (4202496 & updateFlagsForPackage) != 0;
        boolean z3 = (1073741824 & updateFlagsForPackage) != 0;
        boolean z4 = (2097152 & updateFlagsForPackage) != 0;
        boolean z5 = (z2 || (4294967296L & updateFlagsForPackage) == 0) ? false : true;
        if (z2 || z5) {
            ArrayList arrayList2 = new ArrayList(settings.getPackages().size());
            for (PackageStateInternal packageStateInternal2 : settings.getPackages().values()) {
                if (z4) {
                    if (packageStateInternal2.isSystem()) {
                        PackageSetting disabledSystemPkgLPr = settings.mSettings.getDisabledSystemPkgLPr(packageStateInternal2.getPackageName());
                        if (disabledSystemPkgLPr != null) {
                            packageStateInternal = disabledSystemPkgLPr;
                            if (!z3 || packageStateInternal.getPkg() == null || !packageStateInternal.getPkg().isApex()) {
                                PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                                if (z5 || userStateOrDefault.isInstalled() || userStateOrDefault.getArchiveState() != null) {
                                    boolean z6 = z3;
                                    PackageStateInternal packageStateInternal3 = packageStateInternal;
                                    if (!filterSharedLibPackage(packageStateInternal, callingUid, i, updateFlagsForPackage) && !shouldFilterApplication(packageStateInternal3, callingUid, i) && (generatePackageInfo = generatePackageInfo(packageStateInternal3, updateFlagsForPackage, i)) != null) {
                                        arrayList2.add(generatePackageInfo);
                                    }
                                    z3 = z6;
                                }
                            }
                        }
                    }
                }
                packageStateInternal = packageStateInternal2;
                if (!z3) {
                }
                PackageUserStateInternal userStateOrDefault2 = packageStateInternal.getUserStateOrDefault(i);
                if (z5) {
                }
                boolean z62 = z3;
                PackageStateInternal packageStateInternal32 = packageStateInternal;
                if (!filterSharedLibPackage(packageStateInternal, callingUid, i, updateFlagsForPackage)) {
                    arrayList2.add(generatePackageInfo);
                }
                z3 = z62;
            }
            arrayList = arrayList2;
        } else {
            WatchedArrayMap watchedArrayMap = this.mPackages;
            arrayList = new ArrayList(watchedArrayMap.mStorage.size());
            Iterator it = watchedArrayMap.values().iterator();
            while (it.hasNext()) {
                AndroidPackage androidPackage = (AndroidPackage) it.next();
                PackageSetting packageStateInternal4 = getPackageStateInternal(androidPackage.getPackageName());
                if (z4) {
                    if (packageStateInternal4.isSystem()) {
                        PackageSetting disabledSystemPkgLPr2 = settings.mSettings.getDisabledSystemPkgLPr(packageStateInternal4.mName);
                        if (disabledSystemPkgLPr2 != null) {
                            packageSetting = disabledSystemPkgLPr2;
                            if (!z3 || !androidPackage.isApex()) {
                                Iterator it2 = it;
                                PackageSetting packageSetting2 = packageSetting;
                                if (!filterSharedLibPackage(packageSetting, callingUid, i, updateFlagsForPackage) && !shouldFilterApplication(packageSetting2, callingUid, i) && (generatePackageInfo2 = generatePackageInfo(packageSetting2, updateFlagsForPackage, i)) != null) {
                                    arrayList.add(generatePackageInfo2);
                                }
                                it = it2;
                            }
                        }
                    }
                }
                packageSetting = packageStateInternal4;
                if (!z3) {
                }
                Iterator it22 = it;
                PackageSetting packageSetting22 = packageSetting;
                if (!filterSharedLibPackage(packageSetting, callingUid, i, updateFlagsForPackage)) {
                    arrayList.add(generatePackageInfo2);
                }
                it = it22;
            }
        }
        return new ParceledListSlice(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public final String getInstallerPackageName(int i, String str) {
        PackageStateInternal packageLPr;
        int callingUid = Binder.getCallingUid();
        Settings settings = this.mSettings;
        PackageSetting packageLPr2 = settings.mSettings.getPackageLPr(str);
        InstallSource installSource = isApexPackage(str) ? InstallSource.EMPTY : (packageLPr2 == null || shouldFilterApplication(packageLPr2, callingUid, null, 0, i, true, false)) ? null : packageLPr2.installSource;
        if (installSource == null) {
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown package: ", str));
        }
        String str2 = installSource.mInstallerPackageName;
        if (str2 == null || !((packageLPr = settings.mSettings.getPackageLPr(str2)) == null || shouldFilterApplication(packageLPr, callingUid, null, 0, UserHandle.getUserId(callingUid), true, false))) {
            return str2;
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final ComponentName getInstantAppInstallerComponent() {
        ActivityInfo activityInfo = this.mLocalInstantAppInstallerActivity;
        if (activityInfo == null) {
            return null;
        }
        return activityInfo.getComponentName();
    }

    @Override // com.android.server.pm.Computer
    public final ResolveInfo getInstantAppInstallerInfo() {
        return this.mInstantAppInstallerInfo;
    }

    @Override // com.android.server.pm.Computer
    public final String getInstantAppPackageName(int i) {
        if (Process.isIsolated(i)) {
            i = getIsolatedOwner(i);
        }
        Watchable settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (!(settingLPr instanceof PackageStateInternal)) {
            return null;
        }
        PackageStateInternal packageStateInternal = (PackageStateInternal) settingLPr;
        if (packageStateInternal.getUserStateOrDefault(UserHandle.getUserId(i)).isInstantApp()) {
            return packageStateInternal.getPkg().getPackageName();
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "getInstrumentationInfoAsUser", i2, false, false);
        if (!this.mUserManager.mLocalService.exists(i2)) {
            return null;
        }
        String packageName = componentName.getPackageName();
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(packageName);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(packageName);
        if (packageLPr == null || androidPackage == null || shouldFilterApplication(packageLPr, callingUid, componentName, 0, i2, false, true)) {
            return null;
        }
        return PackageInfoUtils.generateInstrumentationInfo((ParsedInstrumentation) this.mInstrumentation.mStorage.get(componentName), androidPackage, i, packageLPr.getUserStateOrDefault(i2), i2, packageLPr);
    }

    public final int getIsolatedOwner(int i) {
        int i2 = this.mIsolatedOwners.mStorage.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "No owner UID found for isolated UID "));
    }

    @Override // com.android.server.pm.Computer
    public final KeySet getKeySetByAlias(String str, String str2) {
        PackageKeySetData packageKeySetData;
        KeySetHandle keySetHandle = null;
        if (str == null || str2 == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: ".concat(str));
            throw new IllegalArgumentException("Unknown package: ".concat(str));
        }
        KeySetManagerService keySetManagerService = this.mSettings.mSettings.mKeySetManagerService;
        PackageSetting packageSetting = (PackageSetting) keySetManagerService.mPackages.mStorage.get(str);
        if (packageSetting != null && (packageKeySetData = packageSetting.keySetData) != null) {
            ArrayMap arrayMap = packageKeySetData.mKeySetAliases;
            Long l = (Long) arrayMap.get(str2);
            if (l == null) {
                throw new IllegalArgumentException("Unknown KeySet alias: " + str2 + ", aliases = " + arrayMap);
            }
            keySetHandle = (KeySetHandle) keySetManagerService.mKeySets.get(l.longValue());
        }
        return new KeySet(keySetHandle);
    }

    @Override // com.android.server.pm.Computer
    public final List getMatchingCrossProfileIntentFilters(Intent intent, String str, int i) {
        CrossProfileIntentResolver crossProfileIntentResolver = (CrossProfileIntentResolver) this.mSettings.mSettings.mCrossProfileIntentResolvers.mStorage.get(i);
        if (crossProfileIntentResolver != null) {
            return crossProfileIntentResolver.queryIntent(this, intent, str, false, i, 0L);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final String getNameForUid(int i) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        int userId = UserHandle.getUserId(callingUid);
        if (isKnownIsolatedComputeApp(i)) {
            try {
                i = getIsolatedOwner(i);
            } catch (IllegalStateException e) {
                Slog.wtf("PackageManager", "Expected isolated uid " + i + " to have an owner", e);
            }
        }
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (!(settingLPr instanceof SharedUserSetting)) {
            if (!(settingLPr instanceof PackageSetting)) {
                return null;
            }
            PackageSetting packageSetting = (PackageSetting) settingLPr;
            if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
                return null;
            }
            return packageSetting.mName;
        }
        SharedUserSetting sharedUserSetting = (SharedUserSetting) settingLPr;
        if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
            return null;
        }
        return sharedUserSetting.name + ":" + sharedUserSetting.mAppId;
    }

    @Override // com.android.server.pm.Computer
    public final String[] getNamesForUids(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        int userId = UserHandle.getUserId(callingUid);
        String[] strArr = new String[iArr.length];
        for (int length = iArr.length - 1; length >= 0; length--) {
            int i = iArr[length];
            if (Process.isSdkSandboxUid(i)) {
                i = getBaseSdkSandboxUid();
            }
            if (isKnownIsolatedComputeApp(i)) {
                try {
                    i = getIsolatedOwner(i);
                } catch (IllegalStateException e) {
                    Slog.wtf("PackageManager", "Expected isolated uid " + i + " to have an owner", e);
                }
            }
            SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
            if (settingLPr instanceof SharedUserSetting) {
                SharedUserSetting sharedUserSetting = (SharedUserSetting) settingLPr;
                if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                    strArr[length] = null;
                } else {
                    strArr[length] = "shared:" + sharedUserSetting.name;
                }
            } else if (settingLPr instanceof PackageSetting) {
                PackageSetting packageSetting = (PackageSetting) settingLPr;
                if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
                    strArr[length] = null;
                } else {
                    strArr[length] = packageSetting.mName;
                }
            } else {
                strArr[length] = null;
            }
        }
        return strArr;
    }

    @Override // com.android.server.pm.Computer
    public final ArraySet getNotifyPackagesForReplacedReceived(String[] strArr) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        ArraySet arraySet = new ArraySet();
        for (String str : strArr) {
            if (!shouldFilterApplication(getPackageStateInternal(str), callingUid, userId)) {
                arraySet.add(str);
            }
        }
        return arraySet;
    }

    @Override // com.android.server.pm.Computer
    public final AndroidPackage getPackage(int i) {
        String[] packagesForUidInternal = getPackagesForUidInternal(i, 1000);
        int length = packagesForUidInternal == null ? 0 : packagesForUidInternal.length;
        AndroidPackage androidPackage = null;
        for (int i2 = 0; androidPackage == null && i2 < length; i2++) {
            androidPackage = (AndroidPackage) this.mPackages.mStorage.get(packagesForUidInternal[i2]);
        }
        return androidPackage;
    }

    @Override // com.android.server.pm.Computer
    public final AndroidPackage getPackage(String str) {
        return (AndroidPackage) this.mPackages.mStorage.get(resolveInternalPackageName(-1L, str));
    }

    @Override // com.android.server.pm.Computer
    public final int[] getPackageGids(String str, long j, int i) {
        if (!this.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlagsForPackage = updateFlagsForPackage(i, j);
        enforceCrossUserPermission(callingUid, false, "getPackageGids", i, false, false);
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return null;
        }
        AndroidPackageInternal androidPackageInternal = packageStateInternal.pkg;
        PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = this.mPermissionManager;
        if (androidPackageInternal != null) {
            if (((updateFlagsForPackage & 1048576) != 0 ? packageStateInternal.isSystem() : true) && packageStateInternal.getUserStateOrDefault(i).isInstalled() && !shouldFilterApplication(packageStateInternal, callingUid, i)) {
                return permissionManagerServiceInternalImpl.getGidsForUid(UserHandle.getUid(i, packageStateInternal.mAppId));
            }
        }
        if ((4299169792L & updateFlagsForPackage) != 0) {
            if (((updateFlagsForPackage & 1048576) != 0 ? packageStateInternal.isSystem() : true) && !shouldFilterApplication(packageStateInternal, callingUid, i)) {
                return permissionManagerServiceInternalImpl.getGidsForUid(UserHandle.getUid(i, packageStateInternal.mAppId));
            }
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final PackageInfo getPackageInfo(String str, long j, int i) {
        return getPackageInfoInternal(Binder.getCallingUid(), i, str, -1L, j);
    }

    @Override // com.android.server.pm.Computer
    public final PackageInfo getPackageInfoInternal(int i, int i2, String str, long j, long j2) {
        PackageStateInternal packageLPr;
        PackageSetting disabledSystemPkgLPr;
        AndroidPackageInternal androidPackageInternal;
        int i3 = i2;
        if (!this.mUserManager.mLocalService.exists(i3)) {
            return null;
        }
        long updateFlagsForPackage = updateFlagsForPackage(i3, j2);
        enforceCrossUserPermission(Binder.getCallingUid(), false, "get package info", i2, false, false);
        String resolveInternalPackageName = resolveInternalPackageName(j, str);
        if (SemDualAppManager.isDualAppId(i2) && SemDualAppManager.isDualAppId(UserHandle.getUserId(Binder.getCallingUid())) && DualAppManagerService.shouldForwardToOwner(resolveInternalPackageName)) {
            i3 = 0;
        }
        boolean z = (2097152 & updateFlagsForPackage) != 0;
        boolean z2 = (1073741824 & updateFlagsForPackage) != 0;
        Settings settings = this.mSettings;
        if (z && (disabledSystemPkgLPr = settings.mSettings.getDisabledSystemPkgLPr(resolveInternalPackageName)) != null) {
            if ((!z2 && (androidPackageInternal = disabledSystemPkgLPr.pkg) != null && androidPackageInternal.isApex()) || filterSharedLibPackage(disabledSystemPkgLPr, i, i3, updateFlagsForPackage) || shouldFilterApplication(disabledSystemPkgLPr, i, i3)) {
                return null;
            }
            return generatePackageInfo(disabledSystemPkgLPr, updateFlagsForPackage, i3);
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(resolveInternalPackageName);
        PackageSetting packageLPr2 = settings.mSettings.getPackageLPr(resolveInternalPackageName);
        if (z && androidPackage != null && !packageLPr2.isSystem()) {
            return null;
        }
        if (androidPackage == null) {
            if (z || (4299169792L & updateFlagsForPackage) == 0 || (packageLPr = settings.mSettings.getPackageLPr(resolveInternalPackageName)) == null || filterSharedLibPackage(packageLPr, i, i3, updateFlagsForPackage) || shouldFilterApplication(packageLPr, i, i3)) {
                return null;
            }
            return generatePackageInfo(packageLPr, updateFlagsForPackage, i3);
        }
        PackageSetting packageStateInternal = getPackageStateInternal(androidPackage.getPackageName());
        if ((!z2 && androidPackage.isApex()) || filterSharedLibPackage(packageStateInternal, i, i3, updateFlagsForPackage)) {
            return null;
        }
        if (packageStateInternal == null || !shouldFilterApplication(packageStateInternal, i, i3)) {
            return generatePackageInfo(packageStateInternal, updateFlagsForPackage, i3);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final Pair getPackageOrSharedUser(int i) {
        Watchable settingLPr = this.mSettings.mSettings.getSettingLPr(i);
        if (settingLPr instanceof SharedUserSetting) {
            return Pair.create(null, (SharedUserSetting) settingLPr);
        }
        if (settingLPr instanceof PackageSetting) {
            return Pair.create((PackageStateInternal) settingLPr, null);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final int getPackageStartability(int i, int i2, String str, boolean z) {
        ApplicationInfo applicationInfo;
        boolean isCeStorageUnlocked = StorageManager.isCeStorageUnlocked(i2);
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, i2) || !packageStateInternal.getUserStateOrDefault(i2).isInstalled()) {
            return 1;
        }
        if (z && !packageStateInternal.isSystem()) {
            return 2;
        }
        if (this.mFrozenPackages.mStorage.containsKey(str)) {
            return 3;
        }
        if (!isCeStorageUnlocked) {
            AndroidPackageInternal androidPackageInternal = packageStateInternal.pkg;
            if (!androidPackageInternal.isDirectBootAware() && !androidPackageInternal.isPartiallyDirectBootAware()) {
                return 4;
            }
        }
        if (SemPersonaManager.isDarDualEncrypted(i2)) {
            if (!DualDarManager.isOnDeviceOwner(i2) || SemPersonaManager.isDoEnabled(i2)) {
                Context context = this.mContext;
                if (PersonaServiceHelper.isSystemApp(i2, str)) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m("system app allowed - ", str, "PersonaServiceHelper");
                } else {
                    Bundle bundle = (Bundle) PersonaServiceHelper.getDualDARPolicyService().map(new PersonaServiceHelper$$ExternalSyntheticLambda0(i2, 3)).orElseGet(new PersonaServiceHelper$$ExternalSyntheticLambda2(i2, 2));
                    if (bundle == null) {
                        return 5;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (AppIdentity appIdentity : bundle.getParcelableArray("dualdar-config-datalock-whitelistpackages")) {
                        arrayList.add(appIdentity);
                    }
                    try {
                        applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i2);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        applicationInfo = null;
                    }
                    if (applicationInfo == null) {
                        return 5;
                    }
                    if ((!applicationInfo.isDirectBootAware() && !applicationInfo.isPartiallyDirectBootAware()) || !PersonaServiceHelper.verifyPackageForDualDAR(context, i2, str, arrayList)) {
                        return 5;
                    }
                    DualAppManagerService$$ExternalSyntheticOutline0.m("checkPackageStartable direct boot aware and whitelisted allowed: ", str, "PersonaServiceHelper");
                }
            } else {
                Slog.i("PackageManager", "DualDAR at DO provisioning not completed");
            }
        }
        return 0;
    }

    @Override // com.android.server.pm.Computer
    public final PackageSetting getPackageStateFiltered(int i, int i2, String str) {
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(resolveInternalPackageNameInternalLocked(i, str, -1L));
        if (shouldFilterApplication(packageLPr, i, i2)) {
            return null;
        }
        return packageLPr;
    }

    @Override // com.android.server.pm.Computer
    public final PackageSetting getPackageStateForInstalledAndFiltered(int i, int i2, String str) {
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, i, i2)) {
            return null;
        }
        return packageStateInternal;
    }

    @Override // com.android.server.pm.Computer
    public final PackageSetting getPackageStateInternal(int i, String str) {
        return this.mSettings.mSettings.getPackageLPr(resolveInternalPackageNameInternalLocked(i, str, -1L));
    }

    @Override // com.android.server.pm.Computer
    public final PackageSetting getPackageStateInternal(String str) {
        return getPackageStateInternal(Binder.getCallingUid(), str);
    }

    @Override // com.android.server.pm.Computer
    public final ArrayMap getPackageStates() {
        return this.mSettings.getPackages();
    }

    @Override // com.android.server.pm.Computer
    public final int getPackageUid(String str, long j, int i) {
        if (!this.mUserManager.mLocalService.exists(i)) {
            return -1;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlagsForPackage = updateFlagsForPackage(i, j);
        enforceCrossUserPermission(callingUid, false, "getPackageUid", i, false, false);
        return getPackageUidInternal(i, callingUid, updateFlagsForPackage, str);
    }

    @Override // com.android.server.pm.Computer
    public final int getPackageUidInternal(int i, int i2, long j, String str) {
        PackageSetting packageLPr;
        PackageSetting packageStateInternal;
        Settings settings = this.mSettings;
        PackageSetting packageLPr2 = settings.mSettings.getPackageLPr(str);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        if (androidPackage != null) {
            if (((j & 1048576) != 0 ? packageLPr2.isSystem() : true) && (packageStateInternal = getPackageStateInternal(i2, androidPackage.getPackageName())) != null && packageStateInternal.getUserStateOrDefault(i).isInstalled() && !shouldFilterApplication(packageStateInternal, i2, i)) {
                return UserHandle.getUid(i, androidPackage.getUid());
            }
        }
        if ((4299169792L & j) == 0 || (packageLPr = settings.mSettings.getPackageLPr(str)) == null) {
            return -1;
        }
        if (!((j & 1048576) != 0 ? packageLPr.isSystem() : true) || shouldFilterApplication(packageLPr, i2, i)) {
            return -1;
        }
        return UserHandle.getUid(i, packageLPr.mAppId);
    }

    @Override // com.android.server.pm.Computer
    public final List getPackagesForAppId(int i) {
        AndroidPackageInternal androidPackageInternal;
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(i);
        if (settingLPr instanceof SharedUserSetting) {
            return ((SharedUserSetting) settingLPr).getPackages();
        }
        if ((settingLPr instanceof PackageSetting) && (androidPackageInternal = ((PackageSetting) settingLPr).pkg) != null) {
            return Collections.singletonList(androidPackageInternal);
        }
        return Collections.emptyList();
    }

    @Override // com.android.server.pm.Computer
    public final String[] getPackagesForUid(int i) {
        return getPackagesForUidInternal(i, Binder.getCallingUid());
    }

    public final String[] getPackagesForUidInternal(int i, int i2) {
        boolean z = getInstantAppPackageName(i2) != null;
        int userId = UserHandle.getUserId(i);
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        return getPackagesForUidInternalBody(i2, userId, UserHandle.getAppId(i), z);
    }

    public final String[] getPackagesForUidInternalBody(int i, int i2, int i3, boolean z) {
        Watchable settingLPr = this.mSettings.mSettings.getSettingLPr(i3);
        if (!(settingLPr instanceof SharedUserSetting)) {
            if (settingLPr instanceof PackageStateInternal) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) settingLPr;
                if (packageStateInternal.getUserStateOrDefault(i2).isInstalled() && !shouldFilterApplication(packageStateInternal, i, i2)) {
                    return new String[]{packageStateInternal.getPackageName()};
                }
            }
            return null;
        }
        if (z) {
            return null;
        }
        ArraySet arraySet = ((SharedUserSetting) settingLPr).mPackages.mStorage;
        int size = arraySet.size();
        String[] strArr = new String[size];
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            PackageStateInternal packageStateInternal2 = (PackageStateInternal) arraySet.valueAt(i5);
            if (packageStateInternal2.getUserStateOrDefault(i2).isInstalled() && !shouldFilterApplication(packageStateInternal2, i, i2)) {
                strArr[i4] = packageStateInternal2.getPackageName();
                i4++;
            }
        }
        return (String[]) ArrayUtils.trimToSize(strArr, i4);
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) {
        PackageInfo generatePackageInfo;
        long j2;
        if (!this.mUserManager.mLocalService.exists(i)) {
            return ParceledListSlice.emptyList();
        }
        long updateFlagsForPackage = updateFlagsForPackage(i, j);
        enforceCrossUserPermission(Binder.getCallingUid(), true, "get packages holding permissions", i, false, false);
        boolean z = (4299169792L & updateFlagsForPackage) != 0;
        ArrayList arrayList = new ArrayList();
        boolean[] zArr = new boolean[strArr.length];
        for (PackageStateInternal packageStateInternal : this.mSettings.getPackages().values()) {
            if (packageStateInternal.getPkg() != null || z) {
                int i2 = 0;
                for (int i3 = 0; i3 < strArr.length; i3++) {
                    if (PermissionManagerService.this.checkPermission(packageStateInternal.getPackageName(), strArr[i3], "default:0", i) == 0) {
                        zArr[i3] = true;
                        i2++;
                    } else {
                        zArr[i3] = false;
                    }
                }
                if (i2 == 0 || (generatePackageInfo = generatePackageInfo(packageStateInternal, updateFlagsForPackage, i)) == null) {
                    j2 = 0;
                } else {
                    j2 = 0;
                    if ((4096 & updateFlagsForPackage) == 0) {
                        if (i2 == strArr.length) {
                            generatePackageInfo.requestedPermissions = strArr;
                        } else {
                            generatePackageInfo.requestedPermissions = new String[i2];
                            int i4 = 0;
                            for (int i5 = 0; i5 < strArr.length; i5++) {
                                if (zArr[i5]) {
                                    generatePackageInfo.requestedPermissions[i4] = strArr[i5];
                                    i4++;
                                }
                            }
                        }
                    }
                    arrayList.add(generatePackageInfo);
                }
            }
        }
        return new ParceledListSlice(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public final Pair getPackagesUsingSharedLibrary(SharedLibraryInfo sharedLibraryInfo, long j, int i, int i2) {
        ComputerEngine computerEngine = this;
        int i3 = i;
        ArrayMap packages = computerEngine.mSettings.getPackages();
        int size = packages.size();
        int i4 = 0;
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        while (i4 < size) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packages.valueAt(i4);
            if (packageStateInternal != null && PackageUserStateUtils.isAvailable(packageStateInternal.getUserStateOrDefault(i2), j)) {
                String name = sharedLibraryInfo.getName();
                if (sharedLibraryInfo.isStatic() || sharedLibraryInfo.isSdk()) {
                    String[] usesStaticLibraries = sharedLibraryInfo.isStatic() ? packageStateInternal.getUsesStaticLibraries() : packageStateInternal.getUsesSdkLibraries();
                    long[] usesStaticLibrariesVersions = sharedLibraryInfo.isStatic() ? packageStateInternal.getUsesStaticLibrariesVersions() : packageStateInternal.getUsesSdkLibrariesVersionsMajor();
                    boolean[] usesSdkLibrariesOptional = sharedLibraryInfo.isSdk() ? packageStateInternal.getUsesSdkLibrariesOptional() : null;
                    int indexOf = ArrayUtils.indexOf(usesStaticLibraries, name);
                    if (indexOf >= 0 && usesStaticLibrariesVersions[indexOf] == sharedLibraryInfo.getLongVersion() && !computerEngine.shouldFilterApplication(packageStateInternal, i3, i2)) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                        }
                        String packageName = packageStateInternal.getPackageName();
                        if (packageStateInternal.getPkg() != null && packageStateInternal.getPkg().isStaticSharedLibrary()) {
                            packageName = packageStateInternal.getPkg().getManifestPackageName();
                        }
                        arrayList.add(new VersionedPackage(packageName, packageStateInternal.getVersionCode()));
                        arrayList2.add(Boolean.valueOf(usesSdkLibrariesOptional != null && usesSdkLibrariesOptional[indexOf]));
                    }
                } else if (packageStateInternal.getPkg() != null && ((ArrayUtils.contains(packageStateInternal.getPkg().getUsesLibraries(), name) || ArrayUtils.contains(packageStateInternal.getPkg().getUsesOptionalLibraries(), name)) && !computerEngine.shouldFilterApplication(packageStateInternal, i3, i2))) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(new VersionedPackage(packageStateInternal.getPackageName(), packageStateInternal.getVersionCode()));
                }
            }
            i4++;
            computerEngine = this;
            i3 = i;
        }
        return new Pair(arrayList, arrayList2);
    }

    @Override // com.android.server.pm.Computer
    public final List getPersistentApplications(int i, boolean z) {
        ApplicationInfo generateApplicationInfo;
        ArrayList arrayList = new ArrayList();
        WatchedArrayMap watchedArrayMap = this.mPackages;
        int size = watchedArrayMap.mStorage.size();
        int callingUserId = UserHandle.getCallingUserId();
        for (int i2 = 0; i2 < size; i2++) {
            AndroidPackage androidPackage = (AndroidPackage) watchedArrayMap.mStorage.valueAt(i2);
            String packageName = androidPackage.getPackageName();
            Settings settings = this.mSettings;
            PackageSetting packageLPr = settings.mSettings.getPackageLPr(packageName);
            boolean z2 = ((262144 & i) == 0 || androidPackage.isDirectBootAware()) ? false : true;
            boolean z3 = (524288 & i) != 0 && androidPackage.isDirectBootAware();
            if (androidPackage.isPersistent() && ((!z || packageLPr.isSystem()) && (z2 || z3))) {
                PackageSetting packageLPr2 = settings.mSettings.getPackageLPr(androidPackage.getPackageName());
                if (packageLPr2 != null && (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(androidPackage, i, packageLPr2.getUserStateOrDefault(callingUserId), callingUserId, packageLPr2)) != null) {
                    arrayList.add(generateApplicationInfo);
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    public final PreferredIntentResolver getPreferredActivities(int i) {
        return (PreferredIntentResolver) this.mSettings.mSettings.mPreferredActivities.mStorage.get(i);
    }

    @Override // com.android.server.pm.Computer
    public final int getPrivateFlagsForUid(int i) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return 0;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        int userId = UserHandle.getUserId(callingUid);
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (settingLPr instanceof SharedUserSetting) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) settingLPr;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return 0;
            }
            return sharedUserSetting.mPkgPrivateFlags;
        }
        if (!(settingLPr instanceof PackageSetting)) {
            return 0;
        }
        PackageSetting packageSetting = (PackageSetting) settingLPr;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return 0;
        }
        return packageSetting.mPkgPrivateFlags;
    }

    @Override // com.android.server.pm.Computer
    public final ArrayMap getProcessesForUid(int i) {
        AndroidPackageInternal androidPackageInternal;
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (settingLPr instanceof SharedUserSetting) {
            return PackageInfoUtils.generateProcessInfo(((SharedUserSetting) settingLPr).processes);
        }
        if (!(settingLPr instanceof PackageSetting) || (androidPackageInternal = ((PackageSetting) settingLPr).pkg) == null) {
            return null;
        }
        return PackageInfoUtils.generateProcessInfo(androidPackageInternal.getProcesses());
    }

    @Override // com.android.server.pm.Computer
    public final UserInfo getProfileParent(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mUserManager.getProfileParent(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public final ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) {
        PackageSetting packageStateInternal;
        PackageUserStateInternal userStateOrDefault;
        ApplicationInfo generateApplicationInfo;
        if (!this.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlags = updateFlags(i, j);
        enforceCrossUserPermission(callingUid, false, "get provider info", i, false, false);
        ParsedProvider provider = this.mComponentResolver.getProvider(componentName);
        if (provider == null || (packageStateInternal = getPackageStateInternal(provider.getPackageName())) == null || packageStateInternal.pkg == null || !PackageStateUtils.isEnabledAndMatches(packageStateInternal, provider, updateFlags, i) || shouldFilterApplication(packageStateInternal, callingUid, componentName, 4, i, false, true) || (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(packageStateInternal.pkg, updateFlags, (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)), i, packageStateInternal)) == null) {
            return null;
        }
        return PackageInfoUtils.generateProviderInfo(packageStateInternal.pkg, provider, updateFlags, userStateOrDefault, generateApplicationInfo, i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    public final ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) {
        PackageSetting packageStateInternal;
        if (!this.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlags = updateFlags(i, j);
        enforceCrossUserPermission(callingUid, false, "get receiver info", i, false, false);
        ParsedActivity receiver = this.mComponentResolver.getReceiver(componentName);
        if (receiver == null || (packageStateInternal = getPackageStateInternal(receiver.getPackageName())) == null || packageStateInternal.pkg == null || !PackageStateUtils.isEnabledAndMatches(packageStateInternal, receiver, updateFlags, i) || shouldFilterApplication(packageStateInternal, callingUid, componentName, 2, i, false, true)) {
            return null;
        }
        return PackageInfoUtils.generateActivityInfo(packageStateInternal.pkg, receiver, updateFlags, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    public final ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) {
        if (!this.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlags = updateFlags(i, j);
        enforceCrossUserOrProfilePermission(callingUid, i, "get service info");
        ParsedMainComponent service = this.mComponentResolver.getService(componentName);
        if (service == null) {
            return null;
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(service.getPackageName());
        if (!this.mSettings.isEnabledAndMatch(androidPackage, service, updateFlags, i)) {
            return null;
        }
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(componentName.getPackageName());
        if (packageLPr == null || shouldFilterApplication(packageLPr, callingUid, componentName, 3, i, false, true)) {
            return null;
        }
        return PackageInfoUtils.generateServiceInfo(androidPackage, service, updateFlags, packageLPr.getUserStateOrDefault(i), null, i, packageLPr);
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice getSharedLibraries(String str, long j, int i) {
        int i2;
        WatchedArrayMap watchedArrayMap;
        int i3;
        if (!this.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        Preconditions.checkArgumentNonnegative(i, "userId must be >= 0");
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        long updateFlagsForPackage = updateFlagsForPackage(i, j);
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_PACKAGES") == 0 || canRequestPackageInstalls(callingUid, i, str, false) || this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_DELETE_PACKAGES") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_SHARED_LIBRARIES") == 0;
        WatchedArrayMap watchedArrayMap2 = this.mSharedLibraries.mSharedLibraries;
        int size = watchedArrayMap2.mStorage.size();
        ArrayList arrayList = null;
        int i4 = 0;
        while (i4 < size) {
            WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) watchedArrayMap2.mStorage.valueAt(i4);
            if (watchedLongSparseArray == null) {
                i2 = i4;
                watchedArrayMap = watchedArrayMap2;
                i3 = size;
            } else {
                int size2 = watchedLongSparseArray.mStorage.size();
                ArrayList arrayList2 = arrayList;
                int i5 = 0;
                while (i5 < size2) {
                    SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(i5);
                    if (!z && (sharedLibraryInfo.isStatic() || sharedLibraryInfo.isSdk())) {
                        break;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    VersionedPackage declaringPackage = sharedLibraryInfo.getDeclaringPackage();
                    try {
                        int i6 = i5;
                        int i7 = size2;
                        int i8 = i4;
                        WatchedLongSparseArray watchedLongSparseArray2 = watchedLongSparseArray;
                        WatchedArrayMap watchedArrayMap3 = watchedArrayMap2;
                        int i9 = size;
                        if (getPackageInfoInternal(Binder.getCallingUid(), i, declaringPackage.getPackageName(), declaringPackage.getLongVersionCode(), updateFlagsForPackage | 67108864) != null) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            SharedLibraryInfo sharedLibraryInfo2 = new SharedLibraryInfo(sharedLibraryInfo.getPath(), sharedLibraryInfo.getPackageName(), sharedLibraryInfo.getAllCodePaths(), sharedLibraryInfo.getName(), sharedLibraryInfo.getLongVersion(), sharedLibraryInfo.getType(), declaringPackage, sharedLibraryInfo.getDependencies() == null ? null : new ArrayList(sharedLibraryInfo.getDependencies()), sharedLibraryInfo.isNative(), getPackagesUsingSharedLibrary(sharedLibraryInfo, updateFlagsForPackage, callingUid, i));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            ArrayList arrayList3 = arrayList2;
                            arrayList3.add(sharedLibraryInfo2);
                            arrayList2 = arrayList3;
                        }
                        i5 = i6 + 1;
                        size2 = i7;
                        i4 = i8;
                        watchedLongSparseArray = watchedLongSparseArray2;
                        watchedArrayMap2 = watchedArrayMap3;
                        size = i9;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                i2 = i4;
                watchedArrayMap = watchedArrayMap2;
                i3 = size;
                arrayList = arrayList2;
            }
            i4 = i2 + 1;
            watchedArrayMap2 = watchedArrayMap;
            size = i3;
        }
        if (arrayList != null) {
            return new ParceledListSlice(arrayList);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final WatchedArrayMap getSharedLibraries() {
        return this.mSharedLibraries.mSharedLibraries;
    }

    @Override // com.android.server.pm.Computer
    public final SharedLibraryInfo getSharedLibraryInfo(long j, String str) {
        return this.mSharedLibraries.getSharedLibraryInfo(j, str);
    }

    @Override // com.android.server.pm.Computer
    public final SharedUserSetting getSharedUser(int i) {
        return (SharedUserSetting) this.mSettings.mSettings.getSettingLPr(i);
    }

    @Override // com.android.server.pm.Computer
    public final ArraySet getSharedUserPackages(int i) {
        Settings settings = this.mSettings;
        settings.getClass();
        ArraySet arraySet = new ArraySet();
        SharedUserSetting sharedUserSetting = (SharedUserSetting) settings.mSettings.getSettingLPr(i);
        if (sharedUserSetting != null) {
            Iterator it = sharedUserSetting.mPackages.mStorage.iterator();
            while (it.hasNext()) {
                arraySet.add((PackageStateInternal) it.next());
            }
        }
        return arraySet;
    }

    @Override // com.android.server.pm.Computer
    public final String[] getSharedUserPackagesForPackage(int i, String str) {
        Settings settings = this.mSettings;
        if (settings.mSettings.getPackageLPr(str) != null) {
            com.android.server.pm.Settings settings2 = settings.mSettings;
            if (settings2.getSharedUserSettingLPr((PackageSetting) settings2.mPackages.mStorage.get(str)) != null) {
                ArraySet arraySet = settings2.getSharedUserSettingLPr((PackageSetting) settings2.mPackages.mStorage.get(str)).mPackages.mStorage;
                int size = arraySet.size();
                String[] strArr = new String[size];
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    PackageStateInternal packageStateInternal = (PackageStateInternal) arraySet.valueAt(i3);
                    if (packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                        strArr[i2] = packageStateInternal.getPackageName();
                        i2++;
                    }
                }
                String[] strArr2 = (String[]) ArrayUtils.trimToSize(strArr, i2);
                return strArr2 != null ? strArr2 : EmptyArray.STRING;
            }
        }
        return EmptyArray.STRING;
    }

    @Override // com.android.server.pm.Computer
    public final ArrayMap getSharedUsers() {
        return this.mSettings.mSettings.mSharedUsers.mStorage;
    }

    @Override // com.android.server.pm.Computer
    public final SigningDetails getSigningDetails(int i) {
        Watchable settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (settingLPr != null) {
            if (settingLPr instanceof SharedUserSetting) {
                return ((SharedUserSetting) settingLPr).signatures.mSigningDetails;
            }
            if (settingLPr instanceof PackageStateInternal) {
                return ((PackageStateInternal) settingLPr).getSigningDetails();
            }
        }
        return SigningDetails.UNKNOWN;
    }

    @Override // com.android.server.pm.Computer
    public final SigningDetails getSigningDetails(String str) {
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        if (androidPackage == null) {
            return null;
        }
        return androidPackage.getSigningDetails();
    }

    public final SigningDetails getSigningDetailsAndFilterAccess(int i, int i2, int i3) {
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (settingLPr == null) {
            return null;
        }
        if (settingLPr instanceof SharedUserSetting) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) settingLPr;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, i2, i3)) {
                return null;
            }
            return sharedUserSetting.signatures.mSigningDetails;
        }
        if (!(settingLPr instanceof PackageSetting)) {
            return null;
        }
        PackageSetting packageSetting = (PackageSetting) settingLPr;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, i2, i3)) {
            return null;
        }
        return packageSetting.signatures.mSigningDetails;
    }

    @Override // com.android.server.pm.Computer
    public final KeySet getSigningKeySet(String str) {
        PackageKeySetData packageKeySetData;
        KeySetHandle keySetHandle = null;
        if (str == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: " + str + ", uid:" + callingUid);
            throw new IllegalArgumentException("Unknown package: ".concat(str));
        }
        if (androidPackage.getUid() != callingUid && 1000 != callingUid) {
            throw new SecurityException("May not access signing KeySet of other apps.");
        }
        KeySetManagerService keySetManagerService = this.mSettings.mSettings.mKeySetManagerService;
        PackageSetting packageSetting = (PackageSetting) keySetManagerService.mPackages.mStorage.get(str);
        if (packageSetting != null && (packageKeySetData = packageSetting.keySetData) != null) {
            long j = packageKeySetData.mProperSigningKeySet;
            if (j != -1) {
                keySetHandle = (KeySetHandle) keySetManagerService.mKeySets.get(j);
            }
        }
        return new KeySet(keySetHandle);
    }

    @Override // com.android.server.pm.Computer
    public final ArrayMap getSystemSharedLibraryNamesAndPaths() {
        WatchedArrayMap watchedArrayMap = this.mSharedLibraries.mSharedLibraries;
        ArrayMap arrayMap = new ArrayMap();
        int size = watchedArrayMap.mStorage.size();
        for (int i = 0; i < size; i++) {
            WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) watchedArrayMap.mStorage.valueAt(i);
            if (watchedLongSparseArray != null) {
                int size2 = watchedLongSparseArray.mStorage.size();
                int i2 = 0;
                while (true) {
                    if (i2 < size2) {
                        SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(i2);
                        if (!sharedLibraryInfo.isStatic()) {
                            arrayMap.put(sharedLibraryInfo.getName(), sharedLibraryInfo.getPath());
                            break;
                        }
                        PackageSetting packageStateInternal = getPackageStateInternal(sharedLibraryInfo.getPackageName());
                        if (packageStateInternal != null && !filterSharedLibPackage(packageStateInternal, Binder.getCallingUid(), UserHandle.getUserId(Binder.getCallingUid()), 67108864L)) {
                            arrayMap.put(sharedLibraryInfo.getName(), sharedLibraryInfo.getPath());
                            break;
                        }
                        i2++;
                    }
                }
            }
        }
        return arrayMap;
    }

    @Override // com.android.server.pm.Computer
    public final int getTargetSdkVersion(String str) {
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.pkg == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, Binder.getCallingUid(), UserHandle.getCallingUserId())) {
            return -1;
        }
        return packageStateInternal.pkg.getTargetSdkVersion();
    }

    @Override // com.android.server.pm.Computer
    public final int getUidForSharedUser(String str) {
        if (str == null) {
            return -1;
        }
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return -1;
        }
        Settings settings = this.mSettings;
        settings.getClass();
        try {
            SharedUserSetting sharedUserLPw = settings.mSettings.getSharedUserLPw(str, false);
            if (sharedUserLPw == null || shouldFilterApplicationIncludingUninstalled(sharedUserLPw, callingUid, UserHandle.getUserId(callingUid))) {
                return -1;
            }
            return sharedUserLPw.mAppId;
        } catch (PackageManagerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.android.server.pm.Computer
    public final int getUidTargetSdkVersion(int i) {
        AndroidPackageInternal androidPackageInternal;
        int targetSdkVersion;
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        int i2 = 10000;
        if (!(settingLPr instanceof SharedUserSetting)) {
            if (!(settingLPr instanceof PackageSetting) || (androidPackageInternal = ((PackageSetting) settingLPr).pkg) == null) {
                return 10000;
            }
            return androidPackageInternal.getTargetSdkVersion();
        }
        ArraySet arraySet = ((SharedUserSetting) settingLPr).mPackages.mStorage;
        int size = arraySet.size();
        for (int i3 = 0; i3 < size; i3++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) arraySet.valueAt(i3);
            if (packageStateInternal.getPkg() != null && (targetSdkVersion = packageStateInternal.getPkg().getTargetSdkVersion()) < i2) {
                i2 = targetSdkVersion;
            }
        }
        return i2;
    }

    @Override // com.android.server.pm.Computer
    public final Set getUnusedPackages(long j) {
        ArraySet arraySet = new ArraySet();
        long currentTimeMillis = System.currentTimeMillis();
        ArrayMap packages = this.mSettings.getPackages();
        for (int i = 0; i < packages.size(); i++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packages.valueAt(i);
            if (packageStateInternal.getPkg() != null) {
                String packageName = packageStateInternal.getPackageName();
                PackageDexUsage.PackageUseInfo packageUseInfo = this.mDexManager.mPackageDexUsage.getPackageUseInfo(packageName);
                if (packageUseInfo == null) {
                    packageUseInfo = new PackageDexUsage.PackageUseInfo(packageName);
                }
                long earliestFirstInstallTime = PackageStateUtils.getEarliestFirstInstallTime(packageStateInternal.getUserStates());
                long latestPackageUseTimeInMills = packageStateInternal.getTransientState().getLatestPackageUseTimeInMills();
                long latestForegroundPackageUseTimeInMills = packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills();
                boolean z = PackageManagerServiceUtils.DEBUG;
                if ((currentTimeMillis - earliestFirstInstallTime >= j && currentTimeMillis - latestForegroundPackageUseTimeInMills >= j) ? !(currentTimeMillis - latestPackageUseTimeInMills < j && (((HashMap) packageUseInfo.mPrimaryCodePaths).isEmpty() ^ true)) : false) {
                    arraySet.add(packageStateInternal.getPackageName());
                }
            }
        }
        return arraySet;
    }

    @Override // com.android.server.pm.Computer
    public final int getUsed() {
        return this.mUsed;
    }

    @Override // com.android.server.pm.Computer
    public final UserInfo[] getUserInfos() {
        return this.mInjector.getUserManagerService().mLocalService.getUserInfos();
    }

    public final PackageUserStateInternal getUserStateOrDefaultForUser(int i, String str) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, true, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "when asking about packages for user "), i, false, false);
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(str);
        if (packageLPr == null || shouldFilterApplicationIncludingUninstalled(packageLPr, callingUid, i)) {
            throw new PackageManager.NameNotFoundException(str);
        }
        return packageLPr.getUserStateOrDefault(i);
    }

    @Override // com.android.server.pm.Computer
    public final int getVersion() {
        return this.mVersion;
    }

    @Override // com.android.server.pm.Computer
    public final int[] getVisibilityAllowList(int i, String str) {
        SparseArray visibilityAllowLists = getVisibilityAllowLists(str, new int[]{i});
        if (visibilityAllowLists != null) {
            return (int[]) visibilityAllowLists.get(i);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final SparseArray getVisibilityAllowLists(String str, int[] iArr) {
        PackageSetting packageStateInternal = getPackageStateInternal(1000, str);
        if (packageStateInternal == null) {
            return null;
        }
        return this.mAppsFilter.getVisibilityAllowList(this, packageStateInternal, iArr, this.mSettings.getPackages());
    }

    @Override // com.android.server.pm.Computer
    public final List getVolumePackages(String str) {
        return this.mSettings.mSettings.getVolumePackagesLPr(str);
    }

    public final boolean hasCrossUserPermission(int i, int i2, int i3, boolean z, boolean z2) {
        if ((!z2 && i3 == i2) || PackageManagerServiceUtils.isSystemOrRoot(i)) {
            return true;
        }
        Context context = DualAppManagerService.mContext;
        if ((SemDualAppManager.isDualAppId(i3) && i2 == 0) || (SemDualAppManager.isDualAppId(i2) && i3 == 0)) {
            return true;
        }
        boolean z3 = !z ? !(this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") == 0) : this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0;
        if (z3 || !Process.isIsolatedUid(i) || !isKnownIsolatedComputeApp(i)) {
            return z3;
        }
        int isolatedOwner = getIsolatedOwner(i);
        if (z) {
            if (this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", -1, isolatedOwner) == 0) {
                return true;
            }
        } else if (this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", -1, isolatedOwner) == 0 || this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS", -1, isolatedOwner) == 0) {
            return true;
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final boolean hasSigningCertificate(String str, byte[] bArr, int i) {
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        if (androidPackage == null) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        PackageSetting packageStateInternal = getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, userId)) {
            return false;
        }
        if (i == 0) {
            return androidPackage.getSigningDetails().hasCertificate(bArr);
        }
        if (i != 1) {
            return false;
        }
        return androidPackage.getSigningDetails().hasSha256Certificate(bArr);
    }

    @Override // com.android.server.pm.Computer
    public final boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) {
        int callingUid = Binder.getCallingUid();
        SigningDetails signingDetailsAndFilterAccess = getSigningDetailsAndFilterAccess(i, callingUid, UserHandle.getUserId(callingUid));
        if (signingDetailsAndFilterAccess == null) {
            return false;
        }
        if (i2 == 0) {
            return signingDetailsAndFilterAccess.hasCertificate(bArr);
        }
        if (i2 != 1) {
            return false;
        }
        return signingDetailsAndFilterAccess.hasSha256Certificate(bArr);
    }

    public ActivityInfo instantAppInstallerActivity() {
        return this.mLocalInstantAppInstallerActivity;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isApexPackage(String str) {
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        return androidPackage != null && androidPackage.isApex();
    }

    @Override // com.android.server.pm.Computer
    public final boolean isApplicationEffectivelyEnabled(String str, UserHandle userHandle) {
        try {
            int applicationEnabledSetting = this.mSettings.getApplicationEnabledSetting(str, userHandle.getIdentifier());
            if (applicationEnabledSetting != 0) {
                return applicationEnabledSetting == 1;
            }
            AndroidPackage androidPackage = getPackage(str);
            if (androidPackage == null) {
                return false;
            }
            return androidPackage.isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.server.pm.Computer
    public final boolean isCallerInstallerOfRecord(AndroidPackage androidPackage, int i) {
        PackageSetting packageStateInternal;
        PackageSetting packageStateInternal2;
        return (androidPackage == null || (packageStateInternal = getPackageStateInternal(androidPackage.getPackageName())) == null || (packageStateInternal2 = getPackageStateInternal(packageStateInternal.installSource.mInstallerPackageName)) == null || !UserHandle.isSameApp(packageStateInternal2.mAppId, i)) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isCallerSameApp(int i, String str) {
        return isCallerSameApp(i, str, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isCallerSameApp(int i, String str, boolean z) {
        if (Process.isSdkSandboxUid(i)) {
            return str != null && str.equals(this.mService.mRequiredSdkSandboxPackage);
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        if (z && Process.isIsolated(i)) {
            i = getIsolatedOwner(i);
        }
        return androidPackage != null && UserHandle.getAppId(i) == androidPackage.getUid();
    }

    public final boolean isComponentVisibleToInstantApp(int i, ComponentName componentName) {
        ComponentResolverApi componentResolverApi = this.mComponentResolver;
        if (i == 1) {
            ParsedActivity activity = componentResolverApi.getActivity(componentName);
            if (activity == null) {
                return false;
            }
            return ((activity.getFlags() & 1048576) != 0) && ((activity.getFlags() & 2097152) == 0);
        }
        if (i == 2) {
            ParsedActivity receiver = componentResolverApi.getReceiver(componentName);
            if (receiver == null) {
                return false;
            }
            return ((receiver.getFlags() & 1048576) != 0) && !((receiver.getFlags() & 2097152) == 0);
        }
        if (i == 3) {
            ParsedService service = componentResolverApi.getService(componentName);
            return (service == null || (service.getFlags() & 1048576) == 0) ? false : true;
        }
        if (i == 4) {
            ParsedProvider provider = componentResolverApi.getProvider(componentName);
            return (provider == null || (provider.getFlags() & 1048576) == 0) ? false : true;
        }
        if (i != 0) {
            return false;
        }
        if (isComponentVisibleToInstantApp(1, componentName) || isComponentVisibleToInstantApp(3, componentName)) {
            return true;
        }
        return isComponentVisibleToInstantApp(4, componentName);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isImplicitImageCaptureIntentAndNotSetByDpc(Intent intent, String str, long j, int i) {
        List arrayList;
        if (!intent.isImplicitImageCaptureIntent()) {
            return false;
        }
        PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mSettings.mSettings.mPersistentPreferredActivities.mStorage.get(i);
        if (persistentPreferredIntentResolver != null) {
            arrayList = persistentPreferredIntentResolver.queryIntent(this, intent, str, (j & 65536) != 0, i, 0L);
        } else {
            arrayList = new ArrayList();
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((PersistentPreferredActivity) it.next()).mIsSetByDpm) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstallDisabledForPackage(int i, int i2, String str) {
        UserManagerService userManagerService = this.mUserManager;
        if (userManagerService.hasUserRestriction("no_install_unknown_sources", i2) || userManagerService.hasUserRestriction("no_install_unknown_sources_globally", i2)) {
            return true;
        }
        AppOpsService.AnonymousClass6 anonymousClass6 = this.mExternalSourcesPolicy;
        return (anonymousClass6 == null || AppOpsService.this.checkOperation(66, i, str) == 0) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstantApp(String str, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, true, "isInstantApp", i, false, false);
        return isInstantAppInternal(i, callingUid, str);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstantAppInternal(int i, int i2, String str) {
        if (Process.isIsolated(i2)) {
            i2 = getIsolatedOwner(i2);
        }
        PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(str);
        if (packageLPr == null) {
            return false;
        }
        if (!isCallerSameApp(i2, str, false) && !canViewInstantApps(i2, i)) {
            if (!this.mInstantAppRegistry.isInstantAccessGranted(i, UserHandle.getAppId(i2), packageLPr.mAppId)) {
                return false;
            }
        }
        return packageLPr.getUserStateOrDefault(i).isInstantApp();
    }

    public final boolean isInstantAppResolutionAllowed(Intent intent, List list, int i, boolean z, long j) {
        PackageSetting packageSetting;
        if (this.mInstantAppResolverConnection == null || instantAppInstallerActivity() == null || intent.getComponent() != null || (intent.getFlags() & Integer.MIN_VALUE) != 0 || (intent.getFlags() & 1024) != 0) {
            return false;
        }
        if (!z && intent.getPackage() != null) {
            return false;
        }
        if (intent.isWebIntent()) {
            if (intent.getData() == null || TextUtils.isEmpty(intent.getData().getHost())) {
                return false;
            }
            if (this.mWebInstantAppsDisabled.mStorage.get(i)) {
                return false;
            }
        } else if ((list != null && ((ArrayList) list).size() != 0) || (intent.getFlags() & 2048) == 0) {
            return false;
        }
        int size = list == null ? 0 : ((ArrayList) list).size();
        boolean z2 = (intent.getFlags() & 8) != 0;
        if (z2) {
            Slog.d("PackageManager", "Checking if instant app resolution allowed, resolvedActivities = " + list);
        }
        for (int i2 = 0; i2 < size; i2++) {
            ResolveInfo resolveInfo = (ResolveInfo) ((ArrayList) list).get(i2);
            String str = resolveInfo.activityInfo.packageName;
            PackageSetting packageLPr = this.mSettings.mSettings.getPackageLPr(str);
            if (packageLPr != null) {
                if (resolveInfo.handleAllWebDataURI) {
                    packageSetting = packageLPr;
                } else {
                    boolean z3 = PackageManagerServiceUtils.DEBUG;
                    packageSetting = packageLPr;
                    if (((DomainVerificationService) this.mDomainVerificationManager).approvalLevelForDomain(packageLPr, intent, j, i) > 0) {
                        if (PackageManagerService.DEBUG_INSTANT) {
                            Slog.v("PackageManager", "DENY instant app; pkg: " + str + ", approved");
                        }
                        return false;
                    }
                }
                if (packageSetting.getUserStateOrDefault(i).isInstantApp()) {
                    if (PackageManagerService.DEBUG_INSTANT) {
                        Slog.v("PackageManager", "DENY instant app installed; pkg: " + str);
                    }
                    return false;
                }
            } else if (z2) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m("Could not find package ", str, "PackageManager");
            }
        }
        return true;
    }

    public final boolean isKnownIsolatedComputeApp(int i) {
        int i2;
        if (!Process.isIsolatedUid(i)) {
            return false;
        }
        HotwordDetectionConnection$2$$ExternalSyntheticLambda0 hotwordDetectionConnection$2$$ExternalSyntheticLambda0 = PermissionManagerService.this.mHotwordDetectionServiceProvider;
        if (hotwordDetectionConnection$2$$ExternalSyntheticLambda0 != null && i == hotwordDetectionConnection$2$$ExternalSyntheticLambda0.f$0) {
            return true;
        }
        OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda2 onDeviceIntelligenceManagerService$$ExternalSyntheticLambda2 = (OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda2) this.mInjector.mGetLocalServiceProducer.produce(OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda2.class);
        if (onDeviceIntelligenceManagerService$$ExternalSyntheticLambda2 == null) {
            return false;
        }
        OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService = onDeviceIntelligenceManagerService$$ExternalSyntheticLambda2.f$0;
        synchronized (onDeviceIntelligenceManagerService.mLock) {
            i2 = onDeviceIntelligenceManagerService.remoteInferenceServiceUid;
        }
        return i == i2;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isPackageAvailable(String str, int i) {
        PackageUserStateInternal userStateOrDefault;
        if (!this.mUserManager.mLocalService.exists(i)) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "is package available", i, false, false);
        PackageSetting packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.pkg == null || shouldFilterApplication(packageStateInternal, callingUid, i) || (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)) == null) {
            return false;
        }
        return PackageUserStateUtils.isAvailable(userStateOrDefault, 0L);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isPackageQuarantinedForUser(String str, int i) {
        return getUserStateOrDefaultForUser(i, str).isQuarantined();
    }

    @Override // com.android.server.pm.Computer
    public final boolean isPackageSignedByKeySet(String str, KeySet keySet) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || str == null || keySet == null) {
            return false;
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        int userId = UserHandle.getUserId(callingUid);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: ".concat(str));
            throw new IllegalArgumentException("Unknown package: ".concat(str));
        }
        IBinder token = keySet.getToken();
        if (!(token instanceof KeySetHandle)) {
            return false;
        }
        KeySetManagerService keySetManagerService = this.mSettings.mSettings.mKeySetManagerService;
        KeySetHandle keySetHandle = (KeySetHandle) token;
        PackageSetting packageSetting = (PackageSetting) keySetManagerService.mPackages.mStorage.get(str);
        if (packageSetting == null) {
            throw new NullPointerException("Invalid package name");
        }
        if (packageSetting.keySetData == null) {
            throw new NullPointerException("Package has no KeySet data");
        }
        long idByKeySetLPr = keySetManagerService.getIdByKeySetLPr(keySetHandle);
        if (idByKeySetLPr == -1) {
            return false;
        }
        return ((ArraySet) keySetManagerService.mKeySetMapping.get(packageSetting.keySetData.mProperSigningKeySet)).containsAll((ArraySet) keySetManagerService.mKeySetMapping.get(idByKeySetLPr));
    }

    @Override // com.android.server.pm.Computer
    public final boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || str == null || keySet == null) {
            return false;
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str);
        int userId = UserHandle.getUserId(callingUid);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: ".concat(str));
            throw new IllegalArgumentException("Unknown package: ".concat(str));
        }
        IBinder token = keySet.getToken();
        if (!(token instanceof KeySetHandle)) {
            return false;
        }
        KeySetManagerService keySetManagerService = this.mSettings.mSettings.mKeySetManagerService;
        KeySetHandle keySetHandle = (KeySetHandle) token;
        PackageSetting packageSetting = (PackageSetting) keySetManagerService.mPackages.mStorage.get(str);
        if (packageSetting == null) {
            throw new NullPointerException("Invalid package name");
        }
        PackageKeySetData packageKeySetData = packageSetting.keySetData;
        if (packageKeySetData == null || packageKeySetData.mProperSigningKeySet == -1) {
            throw new NullPointerException("Package has no KeySet data");
        }
        long idByKeySetLPr = keySetManagerService.getIdByKeySetLPr(keySetHandle);
        if (idByKeySetLPr == -1) {
            return false;
        }
        return ((ArraySet) keySetManagerService.mKeySetMapping.get(packageSetting.keySetData.mProperSigningKeySet)).equals((ArraySet) keySetManagerService.mKeySetMapping.get(idByKeySetLPr));
    }

    @Override // com.android.server.pm.Computer
    public final boolean isPackageStoppedForUser(String str, int i) {
        return getUserStateOrDefaultForUser(i, str).isStopped();
    }

    @Override // com.android.server.pm.Computer
    public final boolean isPackageSuspendedForUser(String str, int i) {
        return getUserStateOrDefaultForUser(i, str).isSuspended();
    }

    public final boolean isRecentsAccessingChildProfiles(int i, int i2) {
        if (!((ActivityTaskManagerInternal) this.mInjector.mGetLocalServiceProducer.produce(ActivityTaskManagerInternal.class)).isCallerRecents(i)) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int userId = UserHandle.getUserId(i);
            if (ActivityManager.getCurrentUser() != userId) {
                return false;
            }
            return this.mUserManager.isSameProfileGroup(userId, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public final boolean isSuspendingAnyPackages(int i, int i2) {
        UserPackage of = UserPackage.of(i, "android");
        Iterator it = this.mSettings.getPackages().values().iterator();
        while (it.hasNext()) {
            PackageUserStateInternal userStateOrDefault = ((PackageStateInternal) it.next()).getUserStateOrDefault(i2);
            if (userStateOrDefault.getSuspendParams() != null && userStateOrDefault.getSuspendParams().mStorage.containsKey(of)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isUidPrivileged(int i) {
        if (getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return false;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        SettingBase settingLPr = this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i));
        if (settingLPr instanceof SharedUserSetting) {
            ArraySet arraySet = ((SharedUserSetting) settingLPr).mPackages.mStorage;
            int size = arraySet.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((PackageStateInternal) arraySet.valueAt(i2)).isPrivileged()) {
                    return true;
                }
            }
        } else if (settingLPr instanceof PackageSetting) {
            return ((PackageSetting) settingLPr).isPrivileged();
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice queryContentProviders(String str, int i, long j, String str2) {
        Settings settings;
        ProviderInfo providerInfo;
        int i2;
        List list;
        ArrayList arrayList;
        boolean isMatch;
        ComputerEngine computerEngine = this;
        int callingUid = Binder.getCallingUid();
        int userId = str != null ? UserHandle.getUserId(i) : UserHandle.getCallingUserId();
        enforceCrossUserPermission(callingUid, false, "queryContentProviders", userId, false, false);
        if (!computerEngine.mUserManager.mLocalService.exists(userId)) {
            return ParceledListSlice.emptyList();
        }
        long updateFlags = computerEngine.updateFlags(userId, j);
        List queryProviders = computerEngine.mComponentResolver.queryProviders(this, str, str2, i, updateFlags, userId);
        int size = queryProviders == null ? 0 : queryProviders.size();
        ArrayList arrayList2 = null;
        int i3 = 0;
        while (i3 < size) {
            ProviderInfo providerInfo2 = (ProviderInfo) queryProviders.get(i3);
            String str3 = providerInfo2.packageName;
            Settings settings2 = computerEngine.mSettings;
            PackageSetting packageLPr = settings2.mSettings.getPackageLPr(str3);
            if (packageLPr == null) {
                settings = settings2;
                providerInfo = providerInfo2;
                i2 = i3;
                list = queryProviders;
                isMatch = false;
                arrayList = arrayList2;
            } else {
                settings = settings2;
                providerInfo = providerInfo2;
                i2 = i3;
                list = queryProviders;
                arrayList = arrayList2;
                isMatch = PackageUserStateUtils.isMatch(packageLPr.getUserStateOrDefault(userId), ((ComponentInfo) providerInfo2).applicationInfo.isSystemApp(), ((ComponentInfo) providerInfo2).applicationInfo.enabled, ((ComponentInfo) providerInfo2).enabled, ((ComponentInfo) providerInfo2).directBootAware, ((ComponentInfo) providerInfo2).name, updateFlags);
            }
            if (isMatch) {
                if (!shouldFilterApplication(settings.mSettings.getPackageLPr(providerInfo.packageName), callingUid, new ComponentName(providerInfo.packageName, providerInfo.name), 4, userId, false, true)) {
                    arrayList2 = arrayList == null ? new ArrayList(size - i2) : arrayList;
                    arrayList2.add(providerInfo);
                    i3 = i2 + 1;
                    computerEngine = this;
                    queryProviders = list;
                }
            }
            arrayList2 = arrayList;
            i3 = i2 + 1;
            computerEngine = this;
            queryProviders = list;
        }
        ArrayList arrayList3 = arrayList2;
        if (arrayList3 == null) {
            return ParceledListSlice.emptyList();
        }
        arrayList3.sort(sProviderInitOrderSorter);
        return new ParceledListSlice(arrayList3);
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) {
        ComputerEngine computerEngine = this;
        String str2 = str;
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, false, "queryInstrumentationAsUser", i2, false, false);
        if (!computerEngine.mUserManager.mLocalService.exists(i2)) {
            return ParceledListSlice.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        WatchedArrayMap watchedArrayMap = computerEngine.mInstrumentation;
        int size = watchedArrayMap.mStorage.size();
        int i3 = 0;
        while (i3 < size) {
            ParsedInstrumentation parsedInstrumentation = (ParsedInstrumentation) watchedArrayMap.mStorage.valueAt(i3);
            if (str2 == null || str2.equals(parsedInstrumentation.getTargetPackage())) {
                String packageName = parsedInstrumentation.getPackageName();
                AndroidPackage androidPackage = (AndroidPackage) computerEngine.mPackages.mStorage.get(packageName);
                PackageSetting packageStateInternal = computerEngine.getPackageStateInternal(packageName);
                if (androidPackage != null && packageStateInternal != null && !computerEngine.shouldFilterApplication(packageStateInternal, callingUid, i2)) {
                    InstrumentationInfo generateInstrumentationInfo = PackageInfoUtils.generateInstrumentationInfo(parsedInstrumentation, androidPackage, i, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
                    if (generateInstrumentationInfo != null) {
                        arrayList.add(generateInstrumentationInfo);
                    }
                    i3++;
                    computerEngine = this;
                    str2 = str;
                }
            }
            i3++;
            computerEngine = this;
            str2 = str;
        }
        return new ParceledListSlice(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public final List queryIntentActivitiesInternal(Intent intent, String str, long j, int i) {
        return queryIntentActivitiesInternal(intent, str, j, Binder.getCallingUid(), -1, i, false, true);
    }

    @Override // com.android.server.pm.Computer
    public final List queryIntentActivitiesInternal(Intent intent, String str, long j, int i, int i2) {
        return queryIntentActivitiesInternal(intent, str, j, i, -1, i2, false, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:150:0x0282, code lost:
    
        if (r7.hasCategory("android.intent.category.NOTIFICATION_PREFERENCES") != false) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x034e, code lost:
    
        if (r9.equals(((android.content.pm.ResolveInfo) r1.get(0)).activityInfo.packageName) != false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0354, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0352, code lost:
    
        if (r3 != false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x046e, code lost:
    
        r10 = new com.android.server.pm.QueryIntentActivitiesResult(r10, r11, r0);
        r14 = r13;
        r13 = r38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x069e, code lost:
    
        if (isInstantAppInternal(r13, 1000, r7.activityInfo.packageName) != false) goto L332;
     */
    /* JADX WARN: Code restructure failed: missing block: B:516:0x084b, code lost:
    
        if (r11 == false) goto L399;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:189:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0ab9  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0ac2  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0b7a  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0c62 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0c70 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0c74  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0c7c  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0c96  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0ce9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0db6  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0d06  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0cd3  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x052d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:362:0x053e  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x05c4  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x05ed  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x060e  */
    /* JADX WARN: Removed duplicated region for block: B:649:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0dcd  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0dd5  */
    /* JADX WARN: Type inference failed for: r0v125 */
    /* JADX WARN: Type inference failed for: r0v72 */
    /* JADX WARN: Type inference failed for: r0v73 */
    /* JADX WARN: Type inference failed for: r0v75, types: [int] */
    /* JADX WARN: Type inference failed for: r1v152 */
    /* JADX WARN: Type inference failed for: r1v156, types: [java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.util.List] */
    @Override // com.android.server.pm.Computer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List queryIntentActivitiesInternal(android.content.Intent r50, java.lang.String r51, long r52, int r54, int r55, int r56, boolean r57, boolean r58) {
        /*
            Method dump skipped, instructions count: 3557
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ComputerEngine.queryIntentActivitiesInternal(android.content.Intent, java.lang.String, long, int, int, int, boolean, boolean):java.util.List");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.util.List] */
    @Override // com.android.server.pm.Computer
    public final List queryIntentServicesInternal(Intent intent, String str, long j, int i, int i2, int i3, boolean z) {
        Intent intent2;
        Intent intent3;
        List emptyList;
        int i4 = i;
        if (!this.mUserManager.mLocalService.exists(i4)) {
            return Collections.emptyList();
        }
        enforceCrossUserOrProfilePermission(i2, i4, "query intent receivers");
        String instantAppPackageName = getInstantAppPackageName(i2);
        long updateFlagsForResolve = updateFlagsForResolve(j, i, i2, false, false, false);
        SaferIntentUtils.IntentArgs intentArgs = new SaferIntentUtils.IntentArgs(false, i2, i3, intent, str, z);
        intentArgs.platformCompat = this.mInjector.getCompatibility();
        intentArgs.snapshot = this;
        ComponentName component = intent.getComponent();
        if (component != null || intent.getSelector() == null) {
            intent2 = intent;
            intent3 = null;
        } else {
            Intent selector = intent.getSelector();
            intent3 = intent;
            intent2 = selector;
            component = selector.getComponent();
        }
        ?? emptyList2 = Collections.emptyList();
        boolean z2 = false;
        if (component != null) {
            ServiceInfo serviceInfo = getServiceInfo(component, updateFlagsForResolve, i4);
            if (i4 == UserHandle.getUserId(Binder.getCallingUid()) && SemDualAppManager.isDualAppId(i) && component.getPackageName() != null && DualAppManagerService.shouldForwardToOwner(component.getPackageName())) {
                i4 = 0;
            }
            if (serviceInfo != null) {
                boolean z3 = (8388608 & updateFlagsForResolve) != 0;
                boolean z4 = (updateFlagsForResolve & 16777216) != 0;
                boolean z5 = instantAppPackageName != null;
                boolean equals = component.getPackageName().equals(instantAppPackageName);
                ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
                boolean z6 = (applicationInfo.privateFlags & 128) != 0;
                boolean z7 = !equals && (!(z3 || z5 || !z6) || (z4 && z5 && ((serviceInfo.flags & 1048576) == 0)));
                if (!z6 && !z5 && shouldFilterApplication(getPackageStateInternal(1000, applicationInfo.packageName), i2, i4)) {
                    z2 = true;
                }
                if (!z7 && !z2) {
                    ResolveInfo resolveInfo = new ResolveInfo();
                    resolveInfo.serviceInfo = serviceInfo;
                    emptyList2 = new ArrayList(1);
                    emptyList2.add(resolveInfo);
                    SaferIntentUtils.enforceIntentFilterMatching(intentArgs, emptyList2);
                }
            }
        } else {
            String str2 = intent2.getPackage();
            if (str2 == null) {
                emptyList = this.mComponentResolver.queryServices(this, intent2, str, updateFlagsForResolve, i);
                if (emptyList == null) {
                    emptyList = Collections.emptyList();
                } else {
                    applyPostServiceResolutionFilter(i4, instantAppPackageName, i2, emptyList);
                }
            } else {
                AndroidPackage androidPackage = (AndroidPackage) this.mPackages.mStorage.get(str2);
                if (i4 == UserHandle.getUserId(Binder.getCallingUid()) && SemDualAppManager.isDualAppId(i) && DualAppManagerService.shouldForwardToOwner(str2)) {
                    i4 = 0;
                }
                if (androidPackage != null) {
                    emptyList = this.mComponentResolver.queryServices(this, intent2, str, updateFlagsForResolve, androidPackage.getServices(), i4);
                    if (emptyList == null) {
                        emptyList = Collections.emptyList();
                    } else {
                        applyPostServiceResolutionFilter(i4, instantAppPackageName, i2, emptyList);
                    }
                } else {
                    emptyList = Collections.emptyList();
                }
            }
            emptyList2 = emptyList;
            SaferIntentUtils.blockNullAction(intentArgs, emptyList2);
        }
        if (intent3 != null) {
            intentArgs.intent = intent3;
            SaferIntentUtils.enforceIntentFilterMatching(intentArgs, emptyList2);
        }
        return emptyList2;
    }

    @Override // com.android.server.pm.Computer
    public final void querySyncProviders(List list, List list2, boolean z) {
        if (getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int callingUserId = UserHandle.getCallingUserId();
        this.mComponentResolver.querySyncProviders(this, arrayList, arrayList2, z, callingUserId);
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            ProviderInfo providerInfo = (ProviderInfo) arrayList2.get(size);
            if (shouldFilterApplication(this.mSettings.mSettings.getPackageLPr(providerInfo.packageName), Binder.getCallingUid(), new ComponentName(providerInfo.packageName, providerInfo.name), 4, callingUserId, false, true)) {
                arrayList2.remove(size);
                arrayList.remove(size);
            }
        }
        if (!arrayList.isEmpty()) {
            list.addAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        list2.addAll(arrayList2);
    }

    public ComponentName resolveComponentName() {
        return this.mLocalResolveComponentName;
    }

    @Override // com.android.server.pm.Computer
    public final ProviderInfo resolveContentProvider(int i, int i2, long j, String str) {
        ProviderInfo providerInfo;
        UserInfo userInfo;
        if (!this.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        long updateFlags = updateFlags(i, j);
        ProviderInfo queryProvider = this.mComponentResolver.queryProvider(this, str, updateFlags, i);
        boolean z = false;
        boolean z2 = true;
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        if (!((queryProvider == null || i == UserHandle.getUserId(i2)) ? false : ((UriGrantsManagerService.LocalService) ((UriGrantsManagerInternal) packageManagerServiceInjector.mGetLocalServiceProducer.produce(UriGrantsManagerInternal.class))).checkAuthorityGrants(i2, queryProvider, i, true))) {
            if (ContentProvider.isAuthorityRedirectedForCloneProfile(str) && (userInfo = packageManagerServiceInjector.getUserManagerService().mLocalService.getUserInfo(UserHandle.getUserId(i2))) != null && userInfo.isCloneProfile() && userInfo.profileGroupId == i) {
                z2 = false;
            }
            if (z2) {
                enforceCrossUserPermission(i2, false, "resolveContentProvider", i, false, false);
            }
        }
        if (queryProvider == null) {
            return null;
        }
        PackageSetting packageStateInternal = getPackageStateInternal(queryProvider.packageName);
        if (packageStateInternal == null) {
            providerInfo = queryProvider;
        } else {
            providerInfo = queryProvider;
            z = PackageUserStateUtils.isMatch(packageStateInternal.getUserStateOrDefault(i), ((ComponentInfo) queryProvider).applicationInfo.isSystemApp(), ((ComponentInfo) queryProvider).applicationInfo.enabled, ((ComponentInfo) queryProvider).enabled, ((ComponentInfo) queryProvider).directBootAware, ((ComponentInfo) queryProvider).name, updateFlags);
        }
        if (!z) {
            return null;
        }
        ProviderInfo providerInfo2 = providerInfo;
        if (shouldFilterApplication(packageStateInternal, i2, new ComponentName(providerInfo.packageName, providerInfo.name), 4, i, false, true)) {
            return null;
        }
        return providerInfo2;
    }

    @Override // com.android.server.pm.Computer
    public final String resolveInternalPackageName(long j, String str) {
        return resolveInternalPackageNameInternalLocked(Binder.getCallingUid(), str, j);
    }

    public final String resolveInternalPackageNameInternalLocked(int i, String str, long j) {
        LongSparseLongArray longSparseLongArray;
        Settings settings = this.mSettings;
        String renamedPackageLPr = settings.mSettings.getRenamedPackageLPr(str);
        if (renamedPackageLPr != null) {
            str = renamedPackageLPr;
        }
        WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) this.mSharedLibraries.mStaticLibsByDeclaringPackage.mStorage.get(str);
        if (watchedLongSparseArray != null && watchedLongSparseArray.mStorage.size() > 0) {
            SharedLibraryInfo sharedLibraryInfo = null;
            if (PackageManagerServiceUtils.isSystemOrRootOrShell(UserHandle.getAppId(i))) {
                longSparseLongArray = null;
            } else {
                longSparseLongArray = new LongSparseLongArray();
                String name = ((SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(0)).getName();
                String[] packagesForUidInternal = getPackagesForUidInternal(i, i);
                if (packagesForUidInternal != null) {
                    for (String str2 : packagesForUidInternal) {
                        PackageSetting packageLPr = settings.mSettings.getPackageLPr(str2);
                        int indexOf = ArrayUtils.indexOf(packageLPr.getUsesStaticLibraries(), name);
                        if (indexOf >= 0) {
                            long j2 = packageLPr.getUsesStaticLibrariesVersions()[indexOf];
                            longSparseLongArray.append(j2, j2);
                        }
                    }
                }
            }
            if (longSparseLongArray != null && longSparseLongArray.size() <= 0) {
                return str;
            }
            int size = watchedLongSparseArray.mStorage.size();
            for (int i2 = 0; i2 < size; i2++) {
                SharedLibraryInfo sharedLibraryInfo2 = (SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(i2);
                if (longSparseLongArray == null || longSparseLongArray.indexOfKey(sharedLibraryInfo2.getLongVersion()) >= 0) {
                    long longVersionCode = sharedLibraryInfo2.getDeclaringPackage().getLongVersionCode();
                    if (j != -1) {
                        if (longVersionCode == j) {
                            return sharedLibraryInfo2.getPackageName();
                        }
                    } else if (sharedLibraryInfo == null || longVersionCode > sharedLibraryInfo.getDeclaringPackage().getLongVersionCode()) {
                        sharedLibraryInfo = sharedLibraryInfo2;
                    }
                }
            }
            if (sharedLibraryInfo != null) {
                return sharedLibraryInfo.getPackageName();
            }
        }
        return str;
    }

    public final boolean shouldFilterApplication(SharedUserSetting sharedUserSetting, int i, int i2) {
        ArraySet arraySet = sharedUserSetting.mPackages.mStorage;
        boolean z = true;
        for (int size = arraySet.size() - 1; size >= 0 && z; size--) {
            z &= shouldFilterApplication((PackageStateInternal) arraySet.valueAt(size), i, null, 0, i2, false, true);
        }
        return z;
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplication(PackageStateInternal packageStateInternal, int i, int i2) {
        return shouldFilterApplication(packageStateInternal, i, null, 0, i2, false, true);
    }

    public final boolean shouldFilterApplication(PackageStateInternal packageStateInternal, int i, ComponentName componentName, int i2, int i3, boolean z, boolean z2) {
        if (Process.isSdkSandboxUid(i)) {
            int appUidForSdkSandboxUid = Process.getAppUidForSdkSandboxUid(i);
            if (packageStateInternal != null && appUidForSdkSandboxUid == UserHandle.getUid(i3, packageStateInternal.getAppId())) {
                return false;
            }
        }
        if (Process.isIsolated(i)) {
            i = getIsolatedOwner(i);
        }
        int i4 = i;
        boolean z3 = getInstantAppPackageName(i4) != null;
        boolean z4 = packageStateInternal != null && PackageArchiver.isArchived(packageStateInternal.getUserStateOrDefault(i3));
        if (packageStateInternal == null || !(!z || PackageManagerServiceUtils.isSystemOrRootOrShell(i4) || packageStateInternal.isHiddenUntilInstalled() || packageStateInternal.getUserStateOrDefault(i3).isInstalled() || (z4 && !z2))) {
            return z3 || z || Process.isSdkSandboxUid(i4);
        }
        if (isCallerSameApp(i4, packageStateInternal.getPackageName(), false)) {
            return false;
        }
        if (!z3) {
            if (!packageStateInternal.getUserStateOrDefault(i3).isInstantApp()) {
                return this.mAppsFilter.shouldFilterApplication(this, i4, this.mSettings.mSettings.getSettingLPr(UserHandle.getAppId(i4)), packageStateInternal, i3);
            }
            if (canViewInstantApps(i4, i3)) {
                return false;
            }
            if (componentName != null) {
                return true;
            }
            return !this.mInstantAppRegistry.isInstantAccessGranted(i3, UserHandle.getAppId(i4), packageStateInternal.getAppId());
        }
        if (packageStateInternal.getUserStateOrDefault(i3).isInstantApp()) {
            return true;
        }
        if (componentName == null) {
            return !packageStateInternal.getPkg().isVisibleToInstantApps();
        }
        ParsedInstrumentation parsedInstrumentation = (ParsedInstrumentation) this.mInstrumentation.mStorage.get(componentName);
        if (parsedInstrumentation == null || !isCallerSameApp(i4, parsedInstrumentation.getTargetPackage(), false)) {
            return !isComponentVisibleToInstantApp(i2, componentName);
        }
        return false;
    }

    public final boolean shouldFilterApplicationIncludingUninstalled(SharedUserSetting sharedUserSetting, int i, int i2) {
        if (shouldFilterApplication(sharedUserSetting, i, i2)) {
            return true;
        }
        if (PackageManagerServiceUtils.isSystemOrRootOrShell(i)) {
            return false;
        }
        ArraySet arraySet = sharedUserSetting.mPackages.mStorage;
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) arraySet.valueAt(i3);
            if (packageStateInternal.getUserStateOrDefault(i2).isInstalled() || packageStateInternal.isHiddenUntilInstalled()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplicationIncludingUninstalled(PackageStateInternal packageStateInternal, int i, int i2) {
        return shouldFilterApplication(packageStateInternal, i, null, 0, i2, true, true);
    }

    public final long updateFlags(int i, long j) {
        return (j & 786432) != 0 ? j : this.mInjector.getUserManagerService().mLocalService.isUserUnlockingOrUnlocked(i) ? j | 786432 : j | 524288;
    }

    public final long updateFlagsForPackage(int i, long j) {
        long j2;
        int i2;
        boolean z = UserHandle.getCallingUserId() == 0;
        if ((j & 4194304) != 0) {
            enforceCrossUserPermission(Binder.getCallingUid(), false, "MATCH_ANY_USER flag requires INTERACT_ACROSS_USERS permission", i, false, !isRecentsAccessingChildProfiles(Binder.getCallingUid(), i));
        } else if ((8192 & j) != 0 && z) {
            UserManagerService userManagerService = this.mUserManager;
            synchronized (userManagerService.mUsersLock) {
                try {
                    UserInfo userInfoLU = userManagerService.getUserInfoLU(0);
                    int size = userManagerService.mUsers.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        UserInfo userInfo = ((UserManagerService.UserData) userManagerService.mUsers.valueAt(i3)).info;
                        int i4 = userInfo.id;
                        if (i4 != 0 && (userInfoLU.id == i4 || ((i2 = userInfoLU.profileGroupId) != -10000 && i2 == userInfo.profileGroupId))) {
                            j2 = j | 4194304;
                            break;
                        }
                    }
                } finally {
                }
            }
            return updateFlags(i, j2);
        }
        j2 = j;
        return updateFlags(i, j2);
    }

    @Override // com.android.server.pm.Computer
    public final long updateFlagsForResolve(int i, int i2, long j, boolean z, boolean z2) {
        return updateFlagsForResolve(j, i, i2, z, false, z2);
    }

    public final long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2, boolean z3) {
        long j2;
        if (this.mService.mSafeMode || z3) {
            j |= 1048576;
        }
        if (getInstantAppPackageName(i2) != null) {
            if (z2) {
                j |= 33554432;
            }
            j2 = j | 25165824;
        } else {
            boolean z4 = false;
            boolean z5 = (8388608 & j) != 0;
            if (z || (z5 && canViewInstantApps(i2, i))) {
                z4 = true;
            }
            j2 = !z4 ? j & (-58720257) : (-50331649) & j;
        }
        return updateFlags(i, j2);
    }

    @Override // com.android.server.pm.Computer
    public final ComputerEngine use() {
        this.mUsed++;
        return this;
    }
}
