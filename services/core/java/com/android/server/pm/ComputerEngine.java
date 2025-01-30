package com.android.server.pm;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.InstantAppRequest;
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
import android.content.pm.SigningInfo;
import android.content.pm.UserInfo;
import android.content.pm.VersionedPackage;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelableException;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.p005os.IInstalld;
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
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.DualAppManagerService;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.CompilerStats;
import com.android.server.pm.ComputerEngine;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUtils;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.PackageUserStateUtils;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.pm.pkg.component.ParsedActivity;
import com.android.server.pm.pkg.component.ParsedInstrumentation;
import com.android.server.pm.pkg.component.ParsedIntentInfo;
import com.android.server.pm.pkg.component.ParsedMainComponent;
import com.android.server.pm.pkg.component.ParsedProvider;
import com.android.server.pm.pkg.component.ParsedService;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.pm.resolution.ComponentResolverApi;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedLongSparseArray;
import com.android.server.utils.WatchedSparseBooleanArray;
import com.android.server.utils.WatchedSparseIntArray;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.kiosk.IKioskMode;
import com.samsung.android.knox.localservice.ApplicationPolicyInternal;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.PmServerUtils;
import com.samsung.android.server.p025pm.desktopmode.DesktopModeUtils;
import com.samsung.android.server.p025pm.nal.GetAppListHelper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class ComputerEngine implements Computer {
    public static final Comparator sProviderInitOrderSorter = new Comparator() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int lambda$static$0;
            lambda$static$0 = ComputerEngine.lambda$static$0((ProviderInfo) obj, (ProviderInfo) obj2);
            return lambda$static$0;
        }
    };
    public final ApexManager mApexManager;
    public final String mAppPredictionServicePackage;
    public ApplicationPolicyInternal mApplicationPolicy;
    public final AppsFilterSnapshot mAppsFilter;
    public final BackgroundDexOptService mBackgroundDexOptService;
    public final CompilerStats mCompilerStats;
    public final ComponentResolverApi mComponentResolver;
    public final Context mContext;
    public final CrossProfileIntentResolverEngine mCrossProfileIntentResolverEngine;
    public final DefaultAppProvider mDefaultAppProvider;
    public final DexManager mDexManager;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final PackageManagerInternal.ExternalSourcesPolicy mExternalSourcesPolicy;
    public final WatchedArrayMap mFrozenPackages;
    public GetAppListHelper mGetAppListHelper;
    public final PackageManagerServiceInjector mInjector;
    public final ResolveInfo mInstantAppInstallerInfo;
    public final InstantAppRegistry mInstantAppRegistry;
    public final InstantAppResolverConnection mInstantAppResolverConnection;
    public final WatchedArrayMap mInstrumentation;
    public final WatchedSparseIntArray mIsolatedOwners;
    public final ApplicationInfo mLocalAndroidApplication;
    public final ActivityInfo mLocalInstantAppInstallerActivity;
    public final ComponentName mLocalResolveComponentName;
    public final PackageDexOptimizer mPackageDexOptimizer;
    public final WatchedArrayMap mPackages;
    public final PermissionManagerServiceInternal mPermissionManager;
    public final ActivityInfo mResolveActivity;
    public final PackageManagerService mService;
    public final Settings mSettings;
    public final SharedLibrariesRead mSharedLibraries;
    public final UserManagerService mUserManager;
    public final int mVersion;
    public final WatchedSparseBooleanArray mWebInstantAppsDisabled;
    public int mUsed = 0;
    public final File mPendingThemePkgUninstallIndicatorFile = new File("/data/overlays", "pending_migrated_packages_uninstall.txt");

    public class Settings {
        public final com.android.server.pm.Settings mSettings;

        public ArrayMap getPackages() {
            return this.mSettings.getPackagesLocked().untrackedStorage();
        }

        public ArrayMap getDisabledSystemPackages() {
            return this.mSettings.getDisabledSystemPackagesLocked().untrackedStorage();
        }

        public Settings(com.android.server.pm.Settings settings) {
            this.mSettings = settings;
        }

        public PackageStateInternal getPackage(String str) {
            return this.mSettings.getPackageLPr(str);
        }

        public PackageStateInternal getDisabledSystemPkg(String str) {
            return this.mSettings.getDisabledSystemPkgLPr(str);
        }

        public boolean isEnabledAndMatch(AndroidPackage androidPackage, ParsedMainComponent parsedMainComponent, long j, int i) {
            PackageStateInternal packageStateInternal = getPackage(parsedMainComponent.getPackageName());
            if (packageStateInternal == null) {
                return false;
            }
            return PackageUserStateUtils.isMatch(packageStateInternal.getUserStateOrDefault(i), packageStateInternal.isSystem(), androidPackage.isEnabled(), parsedMainComponent, j);
        }

        public CrossProfileIntentResolver getCrossProfileIntentResolver(int i) {
            return this.mSettings.getCrossProfileIntentResolver(i);
        }

        public SettingBase getSettingBase(int i) {
            return this.mSettings.getSettingLPr(i);
        }

        public String getRenamedPackageLPr(String str) {
            return this.mSettings.getRenamedPackageLPr(str);
        }

        public PersistentPreferredIntentResolver getPersistentPreferredActivities(int i) {
            return this.mSettings.getPersistentPreferredActivities(i);
        }

        public void dumpVersionLPr(IndentingPrintWriter indentingPrintWriter) {
            this.mSettings.dumpVersionLPr(indentingPrintWriter);
        }

        public void dumpPreferred(PrintWriter printWriter, DumpState dumpState, String str) {
            this.mSettings.dumpPreferred(printWriter, dumpState, str);
        }

        public void writePreferredActivitiesLPr(TypedXmlSerializer typedXmlSerializer, int i, boolean z) {
            this.mSettings.writePreferredActivitiesLPr(typedXmlSerializer, i, z);
        }

        public PreferredIntentResolver getPreferredActivities(int i) {
            return this.mSettings.getPreferredActivities(i);
        }

        public SharedUserSetting getSharedUserFromId(String str) {
            try {
                return this.mSettings.getSharedUserLPw(str, 0, 0, false);
            } catch (PackageManagerException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean getBlockUninstall(int i, String str) {
            return this.mSettings.getBlockUninstallLPr(i, str);
        }

        public int getApplicationEnabledSetting(String str, int i) {
            return this.mSettings.getApplicationEnabledSettingLPr(str, i);
        }

        public int getComponentEnabledSetting(ComponentName componentName, int i) {
            return this.mSettings.getComponentEnabledSettingLPr(componentName, i);
        }

        public KeySetManagerService getKeySetManagerService() {
            return this.mSettings.getKeySetManagerService();
        }

        public SharedUserApi getSharedUserFromPackageName(String str) {
            return this.mSettings.getSharedUserSettingLPr(str);
        }

        public SharedUserApi getSharedUserFromAppId(int i) {
            return (SharedUserSetting) this.mSettings.getSettingLPr(i);
        }

        public ArraySet getSharedUserPackages(int i) {
            ArraySet arraySet = new ArraySet();
            SharedUserSetting sharedUserSetting = (SharedUserSetting) this.mSettings.getSettingLPr(i);
            if (sharedUserSetting != null) {
                Iterator it = sharedUserSetting.getPackageStates().iterator();
                while (it.hasNext()) {
                    arraySet.add((PackageStateInternal) it.next());
                }
            }
            return arraySet;
        }

        public void dumpPackagesProto(ProtoOutputStream protoOutputStream) {
            this.mSettings.dumpPackagesProto(protoOutputStream);
        }

        public void dumpPermissions(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState) {
            this.mSettings.dumpPermissions(printWriter, str, arraySet, dumpState);
        }

        public void dumpPackages(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z) {
            this.mSettings.dumpPackagesLPr(printWriter, str, arraySet, dumpState, z);
        }

        public void dumpKeySet(PrintWriter printWriter, String str, DumpState dumpState) {
            this.mSettings.getKeySetManagerService().dumpLPr(printWriter, str, dumpState);
        }

        public void dumpSharedUsers(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z) {
            this.mSettings.dumpSharedUsersLPr(printWriter, str, arraySet, dumpState, z);
        }

        public void dumpReadMessages(PrintWriter printWriter, DumpState dumpState) {
            this.mSettings.dumpReadMessages(printWriter, dumpState);
        }

        public void dumpSharedUsersProto(ProtoOutputStream protoOutputStream) {
            this.mSettings.dumpSharedUsersProto(protoOutputStream);
        }

        public List getVolumePackages(String str) {
            return this.mSettings.getVolumePackagesLPr(str);
        }
    }

    public static /* synthetic */ int lambda$static$0(ProviderInfo providerInfo, ProviderInfo providerInfo2) {
        int i = providerInfo.initOrder;
        int i2 = providerInfo2.initOrder;
        if (i > i2) {
            return -1;
        }
        return i < i2 ? 1 : 0;
    }

    public final boolean safeMode() {
        return this.mService.getSafeMode();
    }

    public ComponentName resolveComponentName() {
        return this.mLocalResolveComponentName;
    }

    public ActivityInfo instantAppInstallerActivity() {
        return this.mLocalInstantAppInstallerActivity;
    }

    public ApplicationInfo androidApplication() {
        return this.mLocalAndroidApplication;
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
        DefaultAppProvider defaultAppProvider = packageManagerService.getDefaultAppProvider();
        this.mDefaultAppProvider = defaultAppProvider;
        PackageManagerService packageManagerService2 = snapshot.service;
        DomainVerificationManagerInternal domainVerificationManagerInternal = packageManagerService2.mDomainVerificationManager;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mPackageDexOptimizer = packageManagerService2.mPackageDexOptimizer;
        this.mDexManager = packageManagerService2.getDexManager();
        PackageManagerService packageManagerService3 = snapshot.service;
        this.mCompilerStats = packageManagerService3.mCompilerStats;
        this.mBackgroundDexOptService = packageManagerService3.mBackgroundDexOptService;
        this.mExternalSourcesPolicy = packageManagerService3.mExternalSourcesPolicy;
        this.mCrossProfileIntentResolverEngine = new CrossProfileIntentResolverEngine(userManagerService, domainVerificationManagerInternal, defaultAppProvider, context);
        this.mService = snapshot.service;
        if (PMRune.PM_NAL_GET_APP_LIST) {
            this.mGetAppListHelper = new GetAppListHelper();
        }
    }

    @Override // com.android.server.pm.Computer
    public int getVersion() {
        return this.mVersion;
    }

    @Override // com.android.server.pm.Computer
    public final Computer use() {
        this.mUsed++;
        return this;
    }

    @Override // com.android.server.pm.Computer
    public final int getUsed() {
        return this.mUsed;
    }

    @Override // com.android.server.pm.Computer
    public final List queryIntentActivitiesInternal(Intent intent, String str, long j, long j2, int i, int i2, boolean z, boolean z2) {
        Intent intent2;
        ComponentName componentName;
        Intent intent3;
        long j3;
        int i3;
        int i4;
        int i5;
        int i6;
        List list;
        ApplicationPolicyInternal applicationPolicy;
        String applicationNameFromDb;
        IRestrictionPolicy asInterface;
        if (!this.mUserManager.exists(i2)) {
            return Collections.emptyList();
        }
        String instantAppPackageName = getInstantAppPackageName(i);
        enforceCrossUserPermission(Binder.getCallingUid(), i2, false, false, "query intent activities");
        String action = intent.getAction();
        if (action != null && ((action.equals("android.intent.action.SEND") || action.equals("android.intent.action.SEND_MULTIPLE")) && (asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"))) != null)) {
            try {
                if (!asInterface.isShareListAllowedAsUser(i2, false)) {
                    Log.w("PackageManager", "queryIntentActivitiesInternal() : Share List not allowed");
                    return new ArrayList();
                }
            } catch (RemoteException unused) {
            }
        }
        String str2 = intent.getPackage();
        ComponentName component = intent.getComponent();
        if (component != null || intent.getSelector() == null) {
            intent2 = intent;
            componentName = component;
            intent3 = null;
        } else {
            Intent selector = intent.getSelector();
            intent3 = intent;
            intent2 = selector;
            componentName = selector.getComponent();
        }
        ComponentName componentName2 = componentName;
        long updateFlagsForResolve = updateFlagsForResolve(j, i2, i, z, (componentName == null && str2 == null) ? false : true, isImplicitImageCaptureIntentAndNotSetByDpc(intent2, i2, str, j)) | 536870912;
        List emptyList = Collections.emptyList();
        if (componentName2 != null) {
            ActivityInfo activityInfo = getActivityInfo(componentName2, updateFlagsForResolve, i2);
            if (activityInfo != null) {
                boolean z3 = (8388608 & updateFlagsForResolve) != 0;
                boolean z4 = (16777216 & updateFlagsForResolve) != 0;
                boolean z5 = (updateFlagsForResolve & 33554432) != 0;
                boolean z6 = instantAppPackageName != null;
                boolean equals = componentName2.getPackageName().equals(instantAppPackageName);
                boolean z7 = (activityInfo.applicationInfo.privateFlags & 128) != 0;
                int i7 = activityInfo.flags;
                boolean z8 = (1048576 & i7) != 0;
                boolean z9 = !equals && (!(z3 || z6 || !z7) || (z4 && z6 && (!z8 || (z5 && !(z8 && (i7 & 2097152) == 0)))));
                boolean z10 = (!z || (z && !activityInfo.exported && !isCallerSameApp(str2, i))) && !z7 && !z6 && shouldFilterApplication(getPackageStateInternal(activityInfo.applicationInfo.packageName, 1000), i, i2);
                if (!z9 && !z10) {
                    ResolveInfo resolveInfo = new ResolveInfo();
                    resolveInfo.activityInfo = activityInfo;
                    resolveInfo.userHandle = UserHandle.of(i2);
                    if (SystemProperties.getBoolean("sys.knox.app_name_change", false) && (applicationPolicy = getApplicationPolicy()) != null && (applicationNameFromDb = applicationPolicy.getApplicationNameFromDb(resolveInfo.activityInfo.packageName, i2)) != null) {
                        resolveInfo.nonLocalizedLabel = applicationNameFromDb;
                    }
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(resolveInfo);
                    PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, arrayList, false, intent2, str, i);
                    emptyList = arrayList;
                }
            }
            list = emptyList;
            i5 = i2;
            i6 = 0;
        } else {
            boolean z11 = SemDualAppManager.isDualAppId(i2) && SemDualAppManager.isDualAppId(UserHandle.getUserId(i));
            String action2 = intent2.getAction();
            if (z11 && !DualAppManagerService.isSelfContainedAction(i, action2) && DualAppManagerService.mayForwardIntent(intent2)) {
                if ("android.intent.action.SEND".equals(action2) || "android.intent.action.SEND_MULTIPLE".equals(action2)) {
                    j3 = updateFlagsForResolve;
                } else {
                    j3 = updateFlagsForResolve;
                    List queryActivities = this.mComponentResolver.queryActivities(this, intent2, str, updateFlagsForResolve, i2);
                    if (queryActivities != null && queryActivities.size() == 1) {
                        String str3 = ((ResolveInfo) queryActivities.get(0)).activityInfo.packageName;
                        if (!str3.equals("com.android.settings") && !str3.equals("com.google.android.packageinstaller") && !str3.equals("com.google.android.permissioncontroller") && !str3.equals("com.samsung.android.permissioncontroller")) {
                            List queryActivities2 = this.mComponentResolver.queryActivities(this, intent2, str, j3, 0);
                            if (!DualAppManagerService.shouldForwardToOwner(str3) && queryActivities2 != null && queryActivities2.size() == 1) {
                                i3 = 0;
                                if (str3.equals(((ResolveInfo) queryActivities2.get(0)).activityInfo.packageName)) {
                                    return queryActivities;
                                }
                                i4 = i3;
                            }
                        }
                    }
                }
                i3 = 0;
                i4 = i3;
            } else {
                j3 = updateFlagsForResolve;
                i3 = 0;
                i4 = i2;
            }
            i5 = i4;
            i6 = i3;
            QueryIntentActivitiesResult queryIntentActivitiesInternalBody = queryIntentActivitiesInternalBody(intent2, str, j3, i, i4, z, z2, str2, instantAppPackageName);
            List list2 = queryIntentActivitiesInternalBody.answer;
            if (list2 != null) {
                list = list2;
                i6 = 1;
            } else {
                if (queryIntentActivitiesInternalBody.addInstant) {
                    queryIntentActivitiesInternalBody.result = maybeAddInstantAppInstaller(queryIntentActivitiesInternalBody.result, intent2, str, j3, i5, z, isInstantApp(getInstantAppPackageName(i), i5));
                }
                if (queryIntentActivitiesInternalBody.sortResult) {
                    queryIntentActivitiesInternalBody.result.sort(ComponentResolver.RESOLVE_PRIORITY_SORTER);
                }
                list = queryIntentActivitiesInternalBody.result;
            }
        }
        if (intent3 != null) {
            PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, list, false, intent3, str, i);
        }
        return i6 != 0 ? list : applyPostResolutionFilter(list, instantAppPackageName, z2, i, z, i5, intent2);
    }

    public final ApplicationPolicyInternal getApplicationPolicy() {
        if (this.mApplicationPolicy == null) {
            this.mApplicationPolicy = (ApplicationPolicyInternal) this.mInjector.getLocalService(ApplicationPolicyInternal.class);
        }
        return this.mApplicationPolicy;
    }

    @Override // com.android.server.pm.Computer
    public final List queryIntentActivitiesInternal(Intent intent, String str, long j, int i, int i2) {
        return queryIntentActivitiesInternal(intent, str, j, 0L, i, i2, false, true);
    }

    @Override // com.android.server.pm.Computer
    public final List queryIntentActivitiesInternal(Intent intent, String str, long j, int i) {
        return queryIntentActivitiesInternal(intent, str, j, 0L, Binder.getCallingUid(), i, false, true);
    }

    @Override // com.android.server.pm.Computer
    public final List queryIntentServicesInternal(Intent intent, String str, long j, int i, int i2, boolean z) {
        Intent intent2;
        Intent intent3;
        int i3 = i;
        if (!this.mUserManager.exists(i3)) {
            return Collections.emptyList();
        }
        enforceCrossUserOrProfilePermission(i2, i, false, false, "query intent receivers");
        String instantAppPackageName = getInstantAppPackageName(i2);
        long updateFlagsForResolve = updateFlagsForResolve(j, i, i2, z, false);
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
        List emptyList = Collections.emptyList();
        if (component != null) {
            ServiceInfo serviceInfo = getServiceInfo(component, updateFlagsForResolve, i3);
            boolean z2 = false;
            if (i3 == UserHandle.getUserId(Binder.getCallingUid()) && SemDualAppManager.isDualAppId(i) && component.getPackageName() != null && DualAppManagerService.shouldForwardToOwner(component.getPackageName())) {
                i3 = 0;
            }
            if (serviceInfo != null) {
                boolean z3 = (8388608 & updateFlagsForResolve) != 0;
                boolean z4 = (updateFlagsForResolve & 16777216) != 0;
                boolean z5 = instantAppPackageName != null;
                boolean equals = component.getPackageName().equals(instantAppPackageName);
                ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
                boolean z6 = (applicationInfo.privateFlags & 128) != 0;
                boolean z7 = !equals && (!(z3 || z5 || !z6) || (z4 && z5 && ((serviceInfo.flags & 1048576) == 0)));
                if (!z6 && !z5 && shouldFilterApplication(getPackageStateInternal(applicationInfo.packageName, 1000), i2, i3)) {
                    z2 = true;
                }
                if (!z7 && !z2) {
                    ResolveInfo resolveInfo = new ResolveInfo();
                    resolveInfo.serviceInfo = serviceInfo;
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(resolveInfo);
                    PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, arrayList, false, intent2, str, i2);
                    emptyList = arrayList;
                }
            }
        } else {
            emptyList = queryIntentServicesInternalBody(intent2, str, updateFlagsForResolve, i, i2, instantAppPackageName);
        }
        List list = emptyList;
        if (intent3 != null) {
            PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, list, false, intent3, str, i2);
        }
        return list;
    }

    public List queryIntentServicesInternalBody(Intent intent, String str, long j, int i, int i2, String str2) {
        String str3 = intent.getPackage();
        if (str3 == null) {
            List queryServices = this.mComponentResolver.queryServices(this, intent, str, j, i);
            if (queryServices == null) {
                return Collections.emptyList();
            }
            return applyPostServiceResolutionFilter(queryServices, str2, i, i2);
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str3);
        int i3 = (i == UserHandle.getUserId(Binder.getCallingUid()) && SemDualAppManager.isDualAppId(i) && DualAppManagerService.shouldForwardToOwner(str3)) ? 0 : i;
        if (androidPackage != null) {
            List queryServices2 = this.mComponentResolver.queryServices(this, intent, str, j, androidPackage.getServices(), i3);
            if (queryServices2 == null) {
                return Collections.emptyList();
            }
            return applyPostServiceResolutionFilter(queryServices2, str2, i3, i2);
        }
        return Collections.emptyList();
    }

    public QueryIntentActivitiesResult queryIntentActivitiesInternalBody(Intent intent, String str, long j, int i, int i2, boolean z, boolean z2, String str2, String str3) {
        int i3;
        int i4;
        List<CrossProfileDomainInfo> resolveIntent;
        boolean z3;
        boolean z4;
        List list;
        ArrayList arrayList = new ArrayList();
        new ArrayList();
        if (str2 == null) {
            if (!this.mCrossProfileIntentResolverEngine.shouldSkipCurrentProfile(this, intent, str, i2)) {
                arrayList.addAll(filterIfNotSystemUser(this.mComponentResolver.queryActivities(this, intent, str, j, i2), i2));
            }
            boolean isInstantAppResolutionAllowed = isInstantAppResolutionAllowed(intent, arrayList, i2, false, j);
            boolean hasNonNegativePriority = hasNonNegativePriority(arrayList);
            CrossProfileIntentResolverEngine crossProfileIntentResolverEngine = this.mCrossProfileIntentResolverEngine;
            final Settings settings = this.mSettings;
            Objects.requireNonNull(settings);
            resolveIntent = crossProfileIntentResolverEngine.resolveIntent(this, intent, str, i2, j, str2, hasNonNegativePriority, z, new Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ComputerEngine.Settings.this.getPackage((String) obj);
                }
            });
            boolean z5 = intent.hasWebURI() || !resolveIntent.isEmpty();
            if ("android.intent.action.MAIN".equals(intent.getAction()) && intent.hasCategory("android.intent.category.HOME") && DesktopModeUtils.filterHomeActivitiesByDesktopMode(DesktopModeUtils.isNewDexMode(this.mContext), DesktopModeFeature.isDesktopMode(this.mContext), "com.sec.android.app.desktoplauncher", arrayList)) {
                return new QueryIntentActivitiesResult(z5, isInstantAppResolutionAllowed, arrayList);
            }
            i4 = i2;
            z3 = z5;
            z4 = isInstantAppResolutionAllowed;
        } else {
            PackageStateInternal packageStateInternal = getPackageStateInternal(str2, 1000);
            if (packageStateInternal == null || packageStateInternal.getAndroidPackage() == null) {
                i3 = i2;
            } else {
                i3 = i2;
                if (z || !shouldFilterApplication(packageStateInternal, i, i3)) {
                    arrayList.addAll(filterIfNotSystemUser(this.mComponentResolver.queryActivities(this, intent, str, j, packageStateInternal.getAndroidPackage().getActivities(), i2), i3));
                }
            }
            boolean isInstantAppResolutionAllowed2 = arrayList.size() == 0 ? isInstantAppResolutionAllowed(intent, null, i2, true, j) : false;
            CrossProfileIntentResolverEngine crossProfileIntentResolverEngine2 = this.mCrossProfileIntentResolverEngine;
            final Settings settings2 = this.mSettings;
            Objects.requireNonNull(settings2);
            i4 = i3;
            resolveIntent = crossProfileIntentResolverEngine2.resolveIntent(this, intent, str, i2, j, str2, false, z, new Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ComputerEngine.Settings.this.getPackage((String) obj);
                }
            });
            z3 = false;
            z4 = isInstantAppResolutionAllowed2;
        }
        if (resolveIntent.isEmpty() || i4 != 0) {
            list = resolveIntent;
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (CrossProfileDomainInfo crossProfileDomainInfo : resolveIntent) {
                if (!SemDualAppManager.shouldRemove(crossProfileDomainInfo.mResolveInfo)) {
                    arrayList2.add(crossProfileDomainInfo);
                }
            }
            list = arrayList2;
        }
        CrossProfileIntentResolverEngine crossProfileIntentResolverEngine3 = this.mCrossProfileIntentResolverEngine;
        boolean areWebInstantAppsDisabled = areWebInstantAppsDisabled(i4);
        final Settings settings3 = this.mSettings;
        Objects.requireNonNull(settings3);
        return crossProfileIntentResolverEngine3.combineFilterAndCreateQueryActivitiesResponse(this, intent, str, str3, str2, z2, j, i2, i, z, arrayList, list, areWebInstantAppsDisabled, z4, z3, new Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ComputerEngine.Settings.this.getPackage((String) obj);
            }
        });
    }

    public final ComponentName findInstallFailureActivity(String str, int i, int i2) {
        Intent intent = new Intent("android.intent.action.INSTALL_FAILURE");
        intent.setPackage(str);
        List queryIntentActivitiesInternal = queryIntentActivitiesInternal(intent, null, 0L, 0L, i, i2, false, false);
        int size = queryIntentActivitiesInternal.size();
        if (size <= 0) {
            return null;
        }
        for (int i3 = 0; i3 < size; i3++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesInternal.get(i3);
            if (resolveInfo.activityInfo.splitName == null) {
                return new ComponentName(str, resolveInfo.activityInfo.name);
            }
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) {
        return getActivityInfoInternal(componentName, j, Binder.getCallingUid(), i);
    }

    public final ActivityInfo getActivityInfoCrossProfile(ComponentName componentName, long j, int i) {
        if (this.mUserManager.exists(i)) {
            return getActivityInfoInternalBody(componentName, updateFlagsForComponent(j, i), Binder.getCallingUid(), i);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final ActivityInfo getActivityInfoInternal(ComponentName componentName, long j, int i, int i2) {
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        long updateFlagsForComponent = updateFlagsForComponent(j, i2) | 536870912;
        if (!isRecentsAccessingChildProfiles(Binder.getCallingUid(), i2)) {
            enforceCrossUserPermission(Binder.getCallingUid(), i2, false, false, "get activity info");
        }
        return getActivityInfoInternalBody(componentName, updateFlagsForComponent, i, i2);
    }

    public ActivityInfo getActivityInfoInternalBody(ComponentName componentName, long j, int i, int i2) {
        ParsedActivity activity = this.mComponentResolver.getActivity(componentName);
        AndroidPackage androidPackage = activity == null ? null : (AndroidPackage) this.mPackages.get(activity.getPackageName());
        if (androidPackage != null && this.mSettings.isEnabledAndMatch(androidPackage, activity, j, i2)) {
            PackageStateInternal packageStateInternal = this.mSettings.getPackage(componentName.getPackageName());
            if (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, componentName, 1, i2)) {
                return null;
            }
            return PackageInfoUtils.generateActivityInfo(androidPackage, activity, j, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
        }
        if (resolveComponentName().equals(componentName)) {
            return PackageInfoUtils.generateDelegateActivityInfo(this.mResolveActivity, j, PackageUserStateInternal.DEFAULT, i2);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public AndroidPackage getPackage(String str) {
        return (AndroidPackage) this.mPackages.get(resolveInternalPackageName(str, -1L));
    }

    @Override // com.android.server.pm.Computer
    public AndroidPackage getPackage(int i) {
        String[] packagesForUidInternal = getPackagesForUidInternal(i, 1000);
        int length = packagesForUidInternal == null ? 0 : packagesForUidInternal.length;
        AndroidPackage androidPackage = null;
        for (int i2 = 0; androidPackage == null && i2 < length; i2++) {
            androidPackage = (AndroidPackage) this.mPackages.get(packagesForUidInternal[i2]);
        }
        return androidPackage;
    }

    public final ApplicationInfo generateApplicationInfoFromSettings(String str, long j, int i, int i2) {
        PackageStateInternal packageStateInternal;
        if (!this.mUserManager.exists(i2) || (packageStateInternal = this.mSettings.getPackage(str)) == null || filterSharedLibPackage(packageStateInternal, i, i2, j) || shouldFilterApplication(packageStateInternal, i, i2)) {
            return null;
        }
        if (packageStateInternal.getAndroidPackage() == null) {
            PackageInfo generatePackageInfo = generatePackageInfo(packageStateInternal, j, i2);
            if (generatePackageInfo != null) {
                return generatePackageInfo.applicationInfo;
            }
            return null;
        }
        ApplicationInfo generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), j, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
        if (generateApplicationInfo != null) {
            generateApplicationInfo.packageName = resolveExternalPackageName(packageStateInternal.getPkg());
        }
        return generateApplicationInfo;
    }

    @Override // com.android.server.pm.Computer
    public final ApplicationInfo getApplicationInfo(String str, long j, int i) {
        return getApplicationInfoInternal(str, j, Binder.getCallingUid(), i);
    }

    @Override // com.android.server.pm.Computer
    public final ApplicationInfo getApplicationInfoInternal(String str, long j, int i, int i2) {
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        long updateFlagsForApplication = updateFlagsForApplication(j, i2);
        if (!isRecentsAccessingChildProfiles(Binder.getCallingUid(), i2)) {
            enforceCrossUserPermission(Binder.getCallingUid(), i2, false, false, "get application info");
        }
        return getApplicationInfoInternalBody(str, updateFlagsForApplication, i, i2);
    }

    public ApplicationInfo getApplicationInfoInternalBody(String str, long j, int i, int i2) {
        String resolveInternalPackageName = resolveInternalPackageName(str, -1L);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(resolveInternalPackageName);
        boolean z = (1073741824 & j) != 0;
        if (androidPackage != null) {
            PackageStateInternal packageStateInternal = this.mSettings.getPackage(resolveInternalPackageName);
            if (packageStateInternal == null) {
                return null;
            }
            int i3 = (SemDualAppManager.isDualAppId(i2) && SemDualAppManager.isDualAppId(UserHandle.getUserId(Binder.getCallingUid())) && DualAppManagerService.shouldForwardToOwner(resolveInternalPackageName) && !DualAppManagerService.isDefalutAppPackage(resolveInternalPackageName)) ? 0 : i2;
            if ((!z && androidPackage.isApex()) || filterSharedLibPackage(packageStateInternal, i, i3, j) || shouldFilterApplication(packageStateInternal, i, i3)) {
                return null;
            }
            ApplicationInfo generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(androidPackage, j, packageStateInternal.getUserStateOrDefault(i3), i3, packageStateInternal);
            if (generateApplicationInfo != null) {
                generateApplicationInfo.packageName = resolveExternalPackageName(androidPackage);
            }
            return generateApplicationInfo;
        }
        if ("android".equals(resolveInternalPackageName) || "system".equals(resolveInternalPackageName)) {
            return androidApplication();
        }
        if ((4202496 & j) != 0) {
            return generateApplicationInfoFromSettings(resolveInternalPackageName, j, i, i2);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final ComponentName getDefaultHomeActivity(int i) {
        ArrayList arrayList = new ArrayList();
        ComponentName homeActivitiesAsUser = getHomeActivitiesAsUser(arrayList, i);
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
    public final ComponentName getHomeActivitiesAsUser(List list, int i) {
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
            ResolveInfo resolveInfo = findPreferredActivityInternal(homeIntent, null, 0L, queryIntentActivitiesInternal, true, false, false, i, UserHandle.getAppId(Binder.getCallingUid()) >= 10000).mPreferredResolveInfo;
            if (resolveInfo != null && (activityInfo = resolveInfo.activityInfo) != null) {
                defaultHome = activityInfo.packageName;
            }
        }
        if (defaultHome == null) {
            return null;
        }
        int size = queryIntentActivitiesInternal.size();
        for (int i2 = 0; i2 < size; i2++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentActivitiesInternal.get(i2);
            ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
            if (activityInfo2 != null && TextUtils.equals(activityInfo2.packageName, defaultHome)) {
                ActivityInfo activityInfo3 = resolveInfo2.activityInfo;
                return new ComponentName(activityInfo3.packageName, activityInfo3.name);
            }
        }
        return null;
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
                PackageStateInternal packageStateInternal = this.mSettings.getPackage(resolveInfo.activityInfo.packageName);
                if (packageStateInternal != null) {
                    int approvalLevelForDomain = this.mDomainVerificationManager.approvalLevelForDomain(packageStateInternal, intent, j, i2);
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
    public final Intent getHomeIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        return intent;
    }

    @Override // com.android.server.pm.Computer
    public final List getMatchingCrossProfileIntentFilters(Intent intent, String str, int i) {
        CrossProfileIntentResolver crossProfileIntentResolver = this.mSettings.getCrossProfileIntentResolver(i);
        if (crossProfileIntentResolver != null) {
            return crossProfileIntentResolver.queryIntent(this, intent, str, false, i);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final List applyPostResolutionFilter(List list, String str, boolean z, int i, boolean z2, int i2, Intent intent) {
        boolean z3;
        ActivityInfo activityInfo;
        String str2;
        boolean z4 = true;
        boolean z5 = intent.isWebIntent() && areWebInstantAppsDisabled(i2);
        int size = list.size() - 1;
        while (size >= 0) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(size);
            if (resolveInfo.isInstantAppAvailable && z5) {
                list.remove(size);
            } else if (!z || (activityInfo = resolveInfo.activityInfo) == null || (str2 = activityInfo.splitName) == null || ArrayUtils.contains(activityInfo.applicationInfo.splitNames, str2)) {
                z3 = z4;
                if (str == null) {
                    SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
                    PackageStateInternal packageStateInternal = getPackageStateInternal(resolveInfo.activityInfo.packageName, 0);
                    if (!z2) {
                        if (!this.mAppsFilter.shouldFilterApplication(this, i, settingBase, packageStateInternal, i2)) {
                        }
                        list.remove(size);
                    }
                } else if (!str.equals(resolveInfo.activityInfo.packageName) && (!z2 || ((!intent.isWebIntent() && (intent.getFlags() & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0) || intent.getPackage() != null || intent.getComponent() != null))) {
                    ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                    if ((activityInfo2.flags & 1048576) != 0 && !activityInfo2.applicationInfo.isInstantApp()) {
                    }
                    list.remove(size);
                }
                size--;
                z4 = z3;
            } else if (instantAppInstallerActivity() == null) {
                list.remove(size);
            } else if (z5 && isInstantAppInternal(resolveInfo.activityInfo.packageName, i2, 1000)) {
                list.remove(size);
            } else {
                ResolveInfo resolveInfo2 = new ResolveInfo(this.mInstantAppInstallerInfo);
                ComponentName findInstallFailureActivity = findInstallFailureActivity(resolveInfo.activityInfo.packageName, i, i2);
                ActivityInfo activityInfo3 = resolveInfo.activityInfo;
                resolveInfo2.auxiliaryInfo = new AuxiliaryResolveInfo(findInstallFailureActivity, activityInfo3.packageName, activityInfo3.applicationInfo.longVersionCode, activityInfo3.splitName);
                resolveInfo2.filter = new IntentFilter();
                resolveInfo2.resolvePackageName = resolveInfo.getComponentInfo().packageName;
                resolveInfo2.labelRes = resolveInfo.resolveLabelResId();
                resolveInfo2.icon = resolveInfo.resolveIconResId();
                z3 = true;
                resolveInfo2.isInstantAppAvailable = true;
                list.set(size, resolveInfo2);
                size--;
                z4 = z3;
            }
            z3 = z4;
            size--;
            z4 = z3;
        }
        return list;
    }

    public final List applyPostServiceResolutionFilter(List list, String str, int i, int i2) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(size);
            if (str == null) {
                if (!this.mAppsFilter.shouldFilterApplication(this, i2, this.mSettings.getSettingBase(UserHandle.getAppId(i2)), getPackageStateInternal(resolveInfo.serviceInfo.packageName, 0), i)) {
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
        return list;
    }

    public final List filterIfNotSystemUser(List list, int i) {
        if (i == 0) {
            return list;
        }
        for (int size = CollectionUtils.size(list) - 1; size >= 0; size--) {
            if ((((ResolveInfo) list.get(size)).activityInfo.flags & 536870912) != 0) {
                list.remove(size);
            }
        }
        return list;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0101 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List maybeAddInstantAppInstaller(List list, Intent intent, String str, long j, int i, boolean z, boolean z2) {
        boolean z3;
        ResolveInfo resolveInfo;
        String str2;
        int i2;
        int i3;
        AuxiliaryResolveInfo auxiliaryResolveInfo;
        PackageStateInternal packageStateInternal;
        ResolveInfo resolveInfo2;
        if (!((j & 8388608) != 0)) {
            List queryActivities = this.mComponentResolver.queryActivities(this, intent, str, 8388608 | j | 64 | 16777216, i);
            for (int size = queryActivities.size() - 1; size >= 0; size--) {
                resolveInfo = (ResolveInfo) queryActivities.get(size);
                String str3 = resolveInfo.activityInfo.packageName;
                PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(str3);
                if (packageStateInternal2.getUserStateOrDefault(i).isInstantApp()) {
                    if (PackageManagerServiceUtils.hasAnyDomainApproval(this.mDomainVerificationManager, packageStateInternal2, intent, j, i)) {
                        if (PackageManagerService.DEBUG_INSTANT) {
                            Slog.v("PackageManager", "Instant app approved for intent; pkg: " + str3);
                        }
                        z3 = false;
                        if (z3) {
                            str2 = "PackageManager";
                            i2 = 0;
                            i3 = i;
                            auxiliaryResolveInfo = null;
                        } else if (resolveInfo == null) {
                            Trace.traceBegin(262144L, "resolveEphemeral");
                            str2 = "PackageManager";
                            i2 = 0;
                            i3 = i;
                            auxiliaryResolveInfo = InstantAppResolver.doInstantAppResolutionPhaseOne(this, this.mUserManager, this.mInstantAppResolverConnection, new InstantAppRequest((AuxiliaryResolveInfo) null, intent, str, (String) null, (String) null, z2, i, (Bundle) null, z, InstantAppResolver.parseDigest(intent).getDigestPrefixSecure(), UUID.randomUUID().toString()));
                            Trace.traceEnd(262144L);
                        } else {
                            str2 = "PackageManager";
                            i2 = 0;
                            i3 = i;
                            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
                            auxiliaryResolveInfo = new AuxiliaryResolveInfo((ComponentName) null, applicationInfo.packageName, applicationInfo.longVersionCode, (String) null);
                        }
                        if ((intent.isWebIntent() || auxiliaryResolveInfo != null) && (packageStateInternal = this.mSettings.getPackage(instantAppInstallerActivity().packageName)) != null && PackageUserStateUtils.isEnabled(packageStateInternal.getUserStateOrDefault(i3), instantAppInstallerActivity(), 0L)) {
                            resolveInfo2 = new ResolveInfo(this.mInstantAppInstallerInfo);
                            resolveInfo2.activityInfo = PackageInfoUtils.generateDelegateActivityInfo(instantAppInstallerActivity(), 0L, packageStateInternal.getUserStateOrDefault(i3), i3);
                            resolveInfo2.match = 5799936;
                            resolveInfo2.filter = new IntentFilter();
                            if (intent.getAction() != null) {
                                resolveInfo2.filter.addAction(intent.getAction());
                            }
                            if (intent.getData() != null && intent.getData().getPath() != null) {
                                resolveInfo2.filter.addDataPath(intent.getData().getPath(), i2);
                            }
                            resolveInfo2.isInstantAppAvailable = true;
                            resolveInfo2.isDefault = true;
                            resolveInfo2.auxiliaryInfo = auxiliaryResolveInfo;
                            if (PackageManagerService.DEBUG_INSTANT) {
                                Slog.v(str2, "Adding ephemeral installer to the ResolveInfo list");
                            }
                            list.add(resolveInfo2);
                        }
                        return list;
                    }
                    if (PackageManagerService.DEBUG_INSTANT) {
                        Slog.v("PackageManager", "Instant app not approved for intent; pkg: " + str3);
                    }
                    z3 = true;
                    resolveInfo = null;
                    if (z3) {
                    }
                    if (intent.isWebIntent()) {
                    }
                    resolveInfo2 = new ResolveInfo(this.mInstantAppInstallerInfo);
                    resolveInfo2.activityInfo = PackageInfoUtils.generateDelegateActivityInfo(instantAppInstallerActivity(), 0L, packageStateInternal.getUserStateOrDefault(i3), i3);
                    resolveInfo2.match = 5799936;
                    resolveInfo2.filter = new IntentFilter();
                    if (intent.getAction() != null) {
                    }
                    if (intent.getData() != null) {
                        resolveInfo2.filter.addDataPath(intent.getData().getPath(), i2);
                    }
                    resolveInfo2.isInstantAppAvailable = true;
                    resolveInfo2.isDefault = true;
                    resolveInfo2.auxiliaryInfo = auxiliaryResolveInfo;
                    if (PackageManagerService.DEBUG_INSTANT) {
                    }
                    list.add(resolveInfo2);
                    return list;
                }
            }
        }
        z3 = false;
        resolveInfo = null;
        if (z3) {
        }
        if (intent.isWebIntent()) {
        }
        resolveInfo2 = new ResolveInfo(this.mInstantAppInstallerInfo);
        resolveInfo2.activityInfo = PackageInfoUtils.generateDelegateActivityInfo(instantAppInstallerActivity(), 0L, packageStateInternal.getUserStateOrDefault(i3), i3);
        resolveInfo2.match = 5799936;
        resolveInfo2.filter = new IntentFilter();
        if (intent.getAction() != null) {
        }
        if (intent.getData() != null) {
        }
        resolveInfo2.isInstantAppAvailable = true;
        resolveInfo2.isDefault = true;
        resolveInfo2.auxiliaryInfo = auxiliaryResolveInfo;
        if (PackageManagerService.DEBUG_INSTANT) {
        }
        list.add(resolveInfo2);
        return list;
    }

    public final PackageInfo generatePackageInfo(PackageStateInternal packageStateInternal, long j, int i) {
        int[] gidsForUid;
        Set emptySet;
        Set emptySet2;
        if (!this.mUserManager.exists(i) || packageStateInternal == null || shouldFilterApplication(packageStateInternal, Binder.getCallingUid(), i)) {
            return null;
        }
        long j2 = ((j & 8192) == 0 || !packageStateInternal.isSystem()) ? j : j | 4194304;
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg != null) {
            if ((256 & j2) == 0) {
                gidsForUid = PackageManagerService.EMPTY_INT_ARRAY;
            } else {
                gidsForUid = this.mPermissionManager.getGidsForUid(UserHandle.getUid(i, packageStateInternal.getAppId()));
            }
            int[] iArr = gidsForUid;
            long j3 = 4096 & j2;
            if (j3 == 0 || ArrayUtils.isEmpty(pkg.getPermissions())) {
                emptySet = Collections.emptySet();
            } else {
                emptySet = this.mPermissionManager.getInstalledPermissions(packageStateInternal.getPackageName());
            }
            Set set = emptySet;
            if (j3 == 0 || ArrayUtils.isEmpty(pkg.getRequestedPermissions())) {
                emptySet2 = Collections.emptySet();
            } else {
                emptySet2 = this.mPermissionManager.getGrantedPermissions(packageStateInternal.getPackageName(), i);
            }
            PackageInfo generate = PackageInfoUtils.generate(pkg, iArr, j2, userStateOrDefault.getFirstInstallTimeMillis(), packageStateInternal.getLastUpdateTime(), set, emptySet2, userStateOrDefault, i, packageStateInternal);
            if (generate == null) {
                return null;
            }
            ApplicationInfo applicationInfo = generate.applicationInfo;
            String resolveExternalPackageName = resolveExternalPackageName(pkg);
            applicationInfo.packageName = resolveExternalPackageName;
            generate.packageName = resolveExternalPackageName;
            return generate;
        }
        if (((8192 & j2) == 0 || !PackageUserStateUtils.isAvailable(userStateOrDefault, j2)) && !PmServerUtils.installedOnSdcardAsUser(packageStateInternal, j2, i)) {
            return null;
        }
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.packageName = packageStateInternal.getPackageName();
        packageInfo.setLongVersionCode(packageStateInternal.getVersionCode());
        SharedUserApi sharedUserFromPackageName = this.mSettings.getSharedUserFromPackageName(packageInfo.packageName);
        packageInfo.sharedUserId = sharedUserFromPackageName != null ? sharedUserFromPackageName.getName() : null;
        packageInfo.firstInstallTime = userStateOrDefault.getFirstInstallTimeMillis();
        packageInfo.lastUpdateTime = packageStateInternal.getLastUpdateTime();
        ApplicationInfo applicationInfo2 = new ApplicationInfo();
        applicationInfo2.packageName = packageStateInternal.getPackageName();
        applicationInfo2.uid = UserHandle.getUid(i, packageStateInternal.getAppId());
        applicationInfo2.primaryCpuAbi = packageStateInternal.getPrimaryCpuAbiLegacy();
        applicationInfo2.secondaryCpuAbi = packageStateInternal.getSecondaryCpuAbiLegacy();
        applicationInfo2.setVersionCode(packageStateInternal.getVersionCode());
        applicationInfo2.flags = packageStateInternal.getFlags();
        applicationInfo2.privateFlags = packageStateInternal.getPrivateFlags();
        packageInfo.applicationInfo = PackageInfoUtils.generateDelegateApplicationInfo(applicationInfo2, j2, userStateOrDefault, i);
        return packageInfo;
    }

    @Override // com.android.server.pm.Computer
    public final PackageInfo getPackageInfo(String str, long j, int i) {
        return getPackageInfoInternal(str, -1L, j, Binder.getCallingUid(), i);
    }

    @Override // com.android.server.pm.Computer
    public final PackageInfo getPackageInfoInternal(String str, long j, long j2, int i, int i2) {
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        long updateFlagsForPackage = updateFlagsForPackage(j2, i2);
        enforceCrossUserPermission(Binder.getCallingUid(), i2, false, false, "get package info");
        return getPackageInfoInternalBody(str, j, updateFlagsForPackage, i, i2);
    }

    public PackageInfo getPackageInfoInternalBody(String str, long j, long j2, int i, int i2) {
        PackageStateInternal packageStateInternal;
        PackageStateInternal disabledSystemPkg;
        String resolveInternalPackageName = resolveInternalPackageName(str, j);
        if (SemDualAppManager.isDualAppId(i2) && SemDualAppManager.isDualAppId(UserHandle.getUserId(Binder.getCallingUid())) && DualAppManagerService.shouldForwardToOwner(resolveInternalPackageName) && !DualAppManagerService.isDefalutAppPackage(resolveInternalPackageName)) {
            i2 = 0;
        }
        boolean z = (2097152 & j2) != 0;
        boolean z2 = (1073741824 & j2) != 0;
        if (z && (disabledSystemPkg = this.mSettings.getDisabledSystemPkg(resolveInternalPackageName)) != null) {
            if ((!z2 && disabledSystemPkg.getPkg() != null && disabledSystemPkg.getPkg().isApex()) || filterSharedLibPackage(disabledSystemPkg, i, i2, j2) || shouldFilterApplication(disabledSystemPkg, i, i2)) {
                return null;
            }
            return generatePackageInfo(disabledSystemPkg, j2, i2);
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(resolveInternalPackageName);
        PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(resolveInternalPackageName);
        if (z && androidPackage != null && !packageStateInternal2.isSystem()) {
            return null;
        }
        if (androidPackage != null) {
            PackageStateInternal packageStateInternal3 = getPackageStateInternal(androidPackage.getPackageName());
            if ((!z2 && androidPackage.isApex()) || filterSharedLibPackage(packageStateInternal3, i, i2, j2)) {
                return null;
            }
            if (packageStateInternal3 == null || !shouldFilterApplication(packageStateInternal3, i, i2)) {
                return generatePackageInfo(packageStateInternal3, j2, i2);
            }
            return null;
        }
        if (z || (4202496 & j2) == 0 || (packageStateInternal = this.mSettings.getPackage(resolveInternalPackageName)) == null || filterSharedLibPackage(packageStateInternal, i, i2, j2) || shouldFilterApplication(packageStateInternal, i, i2)) {
            return null;
        }
        return generatePackageInfo(packageStateInternal, j2, i2);
    }

    @Override // com.android.server.pm.Computer
    public String[] getAllAvailablePackageNames() {
        return (String[]) this.mPackages.keySet().toArray(new String[0]);
    }

    @Override // com.android.server.pm.Computer
    public final PackageStateInternal getPackageStateInternal(String str) {
        return getPackageStateInternal(str, Binder.getCallingUid());
    }

    @Override // com.android.server.pm.Computer
    public PackageStateInternal getPackageStateInternal(String str, int i) {
        return this.mSettings.getPackage(resolveInternalPackageNameInternalLocked(str, -1L, i));
    }

    @Override // com.android.server.pm.Computer
    public PackageStateInternal getPackageStateFiltered(String str, int i, int i2) {
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(resolveInternalPackageNameInternalLocked(str, -1L, i));
        if (shouldFilterApplication(packageStateInternal, i, i2)) {
            return null;
        }
        return packageStateInternal;
    }

    @Override // com.android.server.pm.Computer
    public final ParceledListSlice getInstalledPackages(long j, int i) {
        final int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return ParceledListSlice.emptyList();
        }
        if (PMRune.PM_NAL_GET_APP_LIST) {
            if (this.mGetAppListHelper.checkCallingGetAppList(this.mContext, callingUid, this.mGetAppListHelper.isExemptedPackage(callingUid, this.mSettings.getSettingBase(UserHandle.getAppId(callingUid))), new Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    String[] lambda$getInstalledPackages$1;
                    lambda$getInstalledPackages$1 = ComputerEngine.this.lambda$getInstalledPackages$1((Integer) obj);
                    return lambda$getInstalledPackages$1;
                }
            })) {
                final int callingPid = Binder.getCallingPid();
                this.mInjector.getHandler().post(new Runnable() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ComputerEngine.this.lambda$getInstalledPackages$2(callingUid, callingPid);
                    }
                });
                return ParceledListSlice.emptyList();
            }
        }
        if (!this.mUserManager.exists(i)) {
            return ParceledListSlice.emptyList();
        }
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "get installed packages");
        return getInstalledPackagesBody(updateFlagsForPackage, i, callingUid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String[] lambda$getInstalledPackages$1(Integer num) {
        return getPackagesForUid(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstalledPackages$2(int i, int i2) {
        this.mGetAppListHelper.requestGetAppListPermIfNeeded(this.mContext, i, i2);
    }

    public final boolean needToFilterSamsungThemePackages(long j) {
        if ((j & Long.MIN_VALUE) != 0) {
            return false;
        }
        return this.mPendingThemePkgUninstallIndicatorFile.exists();
    }

    public ParceledListSlice getInstalledPackagesBody(long j, int i, int i2) {
        ArrayList arrayList;
        PackageStateInternal packageStateInternal;
        PackageInfo generatePackageInfo;
        PackageStateInternal packageStateInternal2;
        PackageInfo generatePackageInfo2;
        boolean z = (4202496 & j) != 0;
        boolean z2 = (1073741824 & j) != 0;
        boolean z3 = (2097152 & j) != 0;
        boolean needToFilterSamsungThemePackages = needToFilterSamsungThemePackages(j);
        if (z) {
            arrayList = new ArrayList(this.mSettings.getPackages().size());
            Iterator it = this.mSettings.getPackages().values().iterator();
            while (it.hasNext()) {
                PackageStateInternal packageStateInternal3 = (PackageStateInternal) it.next();
                if (z3) {
                    if (packageStateInternal3.isSystem()) {
                        PackageStateInternal disabledSystemPkg = this.mSettings.getDisabledSystemPkg(packageStateInternal3.getPackageName());
                        if (disabledSystemPkg != null) {
                            packageStateInternal2 = disabledSystemPkg;
                            if (!z2 || packageStateInternal2.getPkg() == null || !packageStateInternal2.getPkg().isApex()) {
                                Iterator it2 = it;
                                PackageStateInternal packageStateInternal4 = packageStateInternal2;
                                if (!filterSharedLibPackage(packageStateInternal2, i2, i, j) && !shouldFilterApplication(packageStateInternal4, i2, i) && (generatePackageInfo2 = generatePackageInfo(packageStateInternal4, j, i)) != null && (!needToFilterSamsungThemePackages || !filterSamsungThemePackage(packageStateInternal4, generatePackageInfo2.applicationInfo, i))) {
                                    arrayList.add(generatePackageInfo2);
                                }
                                it = it2;
                            }
                        }
                    }
                }
                packageStateInternal2 = packageStateInternal3;
                if (!z2) {
                }
                Iterator it22 = it;
                PackageStateInternal packageStateInternal42 = packageStateInternal2;
                if (!filterSharedLibPackage(packageStateInternal2, i2, i, j)) {
                    arrayList.add(generatePackageInfo2);
                }
                it = it22;
            }
        } else {
            arrayList = new ArrayList(this.mPackages.size());
            for (AndroidPackage androidPackage : this.mPackages.values()) {
                PackageStateInternal packageStateInternal5 = getPackageStateInternal(androidPackage.getPackageName());
                if (z3) {
                    if (packageStateInternal5.isSystem()) {
                        PackageStateInternal disabledSystemPkg2 = this.mSettings.getDisabledSystemPkg(packageStateInternal5.getPackageName());
                        if (disabledSystemPkg2 != null) {
                            packageStateInternal = disabledSystemPkg2;
                            if (!z2 || !androidPackage.isApex()) {
                                boolean z4 = z2;
                                PackageStateInternal packageStateInternal6 = packageStateInternal;
                                if (!filterSharedLibPackage(packageStateInternal, i2, i, j) && !shouldFilterApplication(packageStateInternal6, i2, i) && (generatePackageInfo = generatePackageInfo(packageStateInternal6, j, i)) != null && (!needToFilterSamsungThemePackages || !filterSamsungThemePackage(packageStateInternal6, generatePackageInfo.applicationInfo, i))) {
                                    arrayList.add(generatePackageInfo);
                                }
                                z2 = z4;
                            }
                        }
                    }
                }
                packageStateInternal = packageStateInternal5;
                if (!z2) {
                }
                boolean z42 = z2;
                PackageStateInternal packageStateInternal62 = packageStateInternal;
                if (!filterSharedLibPackage(packageStateInternal, i2, i, j)) {
                    arrayList.add(generatePackageInfo);
                }
                z2 = z42;
            }
        }
        return new ParceledListSlice(arrayList);
    }

    public final boolean filterSamsungThemePackage(PackageStateInternal packageStateInternal, ApplicationInfo applicationInfo, int i) {
        List asList;
        PackageInfo packageInfo;
        if (packageStateInternal != null && packageStateInternal.getPkg() != null) {
            asList = packageStateInternal.getPkg().getRequestedPermissions();
        } else {
            asList = (applicationInfo == null || (packageInfo = getPackageInfo(applicationInfo.packageName, 4096L, i)) == null) ? null : Arrays.asList(packageInfo.requestedPermissions);
        }
        return SemSamsungThemeUtils.hasSamsungOverlayPermission(asList);
    }

    @Override // com.android.server.pm.Computer
    public final ResolveInfo createForwardingResolveInfoUnchecked(WatchedIntentFilter watchedIntentFilter, int i, int i2) {
        String str;
        boolean isManagedProfile;
        boolean z;
        boolean z2;
        String str2;
        ComponentName componentName;
        ResolveInfo resolveInfo = new ResolveInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            str = "";
            if (PersonaServiceHelper.isSpfKnoxSupported()) {
                UserInfo userInfo = this.mUserManager.getUserInfo(i2);
                isManagedProfile = userInfo.isManagedProfile();
                userInfo.isKnoxWorkspace();
                z = userInfo.isSecureFolder();
                str = z ? "4" : "";
                z2 = this.mUserManager.getUserInfo(i).isKnoxWorkspace();
                if (userInfo.isUserTypeAppSeparation()) {
                    isManagedProfile = true;
                }
            } else {
                isManagedProfile = this.mUserManager.getUserInfo(i2).isManagedProfile();
                z = false;
                z2 = false;
            }
            if (isManagedProfile) {
                str2 = IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE;
            } else {
                str2 = IntentForwarderActivity.FORWARD_INTENT_TO_PARENT;
            }
            if (z) {
                componentName = new ComponentName(androidApplication().packageName, str2 + str);
            } else {
                componentName = new ComponentName(androidApplication().packageName, str2);
            }
            ActivityInfo activityInfoCrossProfile = getActivityInfoCrossProfile(componentName, 0L, i);
            if (!isManagedProfile && !z2) {
                if (activityInfoCrossProfile != null) {
                    activityInfoCrossProfile.showUserIcon = i2;
                }
                resolveInfo.noResourceId = true;
            }
            resolveInfo.activityInfo = activityInfoCrossProfile;
            resolveInfo.priority = 0;
            resolveInfo.preferredOrder = 0;
            resolveInfo.match = 0;
            resolveInfo.isDefault = true;
            resolveInfo.filter = new IntentFilter(watchedIntentFilter.getIntentFilter());
            resolveInfo.targetUserId = i2;
            resolveInfo.userHandle = UserHandle.of(i);
            return resolveInfo;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public final ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        enforceCrossUserOrProfilePermission(callingUid, i, false, false, "get service info");
        return getServiceInfoBody(componentName, updateFlagsForComponent, i, callingUid);
    }

    public ServiceInfo getServiceInfoBody(ComponentName componentName, long j, int i, int i2) {
        PackageStateInternal packageStateInternal;
        ParsedService service = this.mComponentResolver.getService(componentName);
        if (service == null) {
            return null;
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(service.getPackageName());
        if (!this.mSettings.isEnabledAndMatch(androidPackage, service, j, i) || (packageStateInternal = this.mSettings.getPackage(componentName.getPackageName())) == null || shouldFilterApplication(packageStateInternal, i2, componentName, 3, i)) {
            return null;
        }
        return PackageInfoUtils.generateServiceInfo(androidPackage, service, j, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    public final SharedLibraryInfo getSharedLibraryInfo(String str, long j) {
        return this.mSharedLibraries.getSharedLibraryInfo(str, j);
    }

    @Override // com.android.server.pm.Computer
    public String getInstantAppPackageName(int i) {
        if (Process.isIsolated(i)) {
            i = getIsolatedOwner(i);
        }
        Watchable settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (!(settingBase instanceof PackageStateInternal)) {
            return null;
        }
        PackageStateInternal packageStateInternal = (PackageStateInternal) settingBase;
        if (packageStateInternal.getUserStateOrDefault(UserHandle.getUserId(i)).isInstantApp()) {
            return packageStateInternal.getPkg().getPackageName();
        }
        return null;
    }

    public final int getIsolatedOwner(int i) {
        int i2 = this.mIsolatedOwners.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        throw new IllegalStateException("No owner UID found for isolated UID " + i);
    }

    public final String resolveExternalPackageName(AndroidPackage androidPackage) {
        if (androidPackage.getStaticSharedLibraryName() != null) {
            return androidPackage.getManifestPackageName();
        }
        return androidPackage.getPackageName();
    }

    public final String resolveInternalPackageNameInternalLocked(String str, long j, int i) {
        LongSparseLongArray longSparseLongArray;
        String renamedPackageLPr = this.mSettings.getRenamedPackageLPr(str);
        if (renamedPackageLPr != null) {
            str = renamedPackageLPr;
        }
        WatchedLongSparseArray staticLibraryInfos = this.mSharedLibraries.getStaticLibraryInfos(str);
        if (staticLibraryInfos != null && staticLibraryInfos.size() > 0) {
            SharedLibraryInfo sharedLibraryInfo = null;
            if (PackageManagerServiceUtils.isSystemOrRootOrShell(UserHandle.getAppId(i))) {
                longSparseLongArray = null;
            } else {
                longSparseLongArray = new LongSparseLongArray();
                String name = ((SharedLibraryInfo) staticLibraryInfos.valueAt(0)).getName();
                String[] packagesForUidInternal = getPackagesForUidInternal(i, i);
                if (packagesForUidInternal != null) {
                    for (String str2 : packagesForUidInternal) {
                        PackageStateInternal packageStateInternal = this.mSettings.getPackage(str2);
                        int indexOf = ArrayUtils.indexOf(packageStateInternal.getUsesStaticLibraries(), name);
                        if (indexOf >= 0) {
                            long j2 = packageStateInternal.getUsesStaticLibrariesVersions()[indexOf];
                            longSparseLongArray.append(j2, j2);
                        }
                    }
                }
            }
            if (longSparseLongArray != null && longSparseLongArray.size() <= 0) {
                return str;
            }
            int size = staticLibraryInfos.size();
            for (int i2 = 0; i2 < size; i2++) {
                SharedLibraryInfo sharedLibraryInfo2 = (SharedLibraryInfo) staticLibraryInfos.valueAt(i2);
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

    @Override // com.android.server.pm.Computer
    public final String resolveInternalPackageName(String str, long j) {
        return resolveInternalPackageNameInternalLocked(str, j, Binder.getCallingUid());
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

    public String[] getPackagesForUidInternalBody(int i, int i2, int i3, boolean z) {
        Watchable settingBase = this.mSettings.getSettingBase(i3);
        if (!(settingBase instanceof SharedUserSetting)) {
            if (settingBase instanceof PackageStateInternal) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) settingBase;
                if (packageStateInternal.getUserStateOrDefault(i2).isInstalled() && !shouldFilterApplication(packageStateInternal, i, i2)) {
                    return new String[]{packageStateInternal.getPackageName()};
                }
            }
            return null;
        }
        if (z) {
            return null;
        }
        ArraySet packageStates = ((SharedUserSetting) settingBase).getPackageStates();
        int size = packageStates.size();
        String[] strArr = new String[size];
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            PackageStateInternal packageStateInternal2 = (PackageStateInternal) packageStates.valueAt(i5);
            if (packageStateInternal2.getUserStateOrDefault(i2).isInstalled() && !shouldFilterApplication(packageStateInternal2, i, i2)) {
                strArr[i4] = packageStateInternal2.getPackageName();
                i4++;
            }
        }
        return (String[]) ArrayUtils.trimToSize(strArr, i4);
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

    public final boolean areWebInstantAppsDisabled(int i) {
        return this.mWebInstantAppsDisabled.get(i);
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
        if (defaultHomeActivity != null && isCallerSameApp(defaultHomeActivity.getPackageName(), i)) {
            return true;
        }
        String str = this.mAppPredictionServicePackage;
        return str != null && isCallerSameApp(str, i);
    }

    public final boolean filterStaticSharedLibPackage(PackageStateInternal packageStateInternal, int i, int i2, long j) {
        SharedLibraryInfo sharedLibraryInfo;
        int indexOf;
        if (((j & 67108864) != 0 && (PackageManagerServiceUtils.isSystemOrRootOrShell(UserHandle.getAppId(i)) || checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0)) || packageStateInternal == null || packageStateInternal.getPkg() == null || !packageStateInternal.getPkg().isStaticSharedLibrary() || (sharedLibraryInfo = getSharedLibraryInfo(packageStateInternal.getPkg().getStaticSharedLibraryName(), packageStateInternal.getPkg().getStaticSharedLibraryVersion())) == null) {
            return false;
        }
        String[] packagesForUid = getPackagesForUid(UserHandle.getUid(i2, UserHandle.getAppId(i)));
        if (packagesForUid == null) {
            return true;
        }
        for (String str : packagesForUid) {
            if (packageStateInternal.getPackageName().equals(str)) {
                return false;
            }
            PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(str);
            if (packageStateInternal2 != null && (indexOf = ArrayUtils.indexOf(packageStateInternal2.getUsesStaticLibraries(), sharedLibraryInfo.getName())) >= 0 && packageStateInternal2.getPkg().getUsesStaticLibrariesVersions()[indexOf] == sharedLibraryInfo.getLongVersion()) {
                return false;
            }
        }
        return true;
    }

    public final boolean filterSdkLibPackage(PackageStateInternal packageStateInternal, int i, int i2, long j) {
        SharedLibraryInfo sharedLibraryInfo;
        int indexOf;
        if (((j & 67108864) != 0 && (PackageManagerServiceUtils.isSystemOrRootOrShell(UserHandle.getAppId(i)) || checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0)) || packageStateInternal == null || packageStateInternal.getPkg() == null || !packageStateInternal.getPkg().isSdkLibrary() || (sharedLibraryInfo = getSharedLibraryInfo(packageStateInternal.getPkg().getSdkLibraryName(), packageStateInternal.getPkg().getSdkLibVersionMajor())) == null) {
            return false;
        }
        String[] packagesForUid = getPackagesForUid(UserHandle.getUid(i2, UserHandle.getAppId(i)));
        if (packagesForUid == null) {
            return true;
        }
        for (String str : packagesForUid) {
            if (packageStateInternal.getPackageName().equals(str)) {
                return false;
            }
            PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(str);
            if (packageStateInternal2 != null && (indexOf = ArrayUtils.indexOf(packageStateInternal2.getUsesSdkLibraries(), sharedLibraryInfo.getName())) >= 0 && packageStateInternal2.getPkg().getUsesSdkLibrariesVersionsMajor()[indexOf] == sharedLibraryInfo.getLongVersion()) {
                return false;
            }
        }
        return true;
    }

    public final boolean filterSharedLibPackage(PackageStateInternal packageStateInternal, int i, int i2, long j) {
        return filterStaticSharedLibPackage(packageStateInternal, i, i2, j) || filterSdkLibPackage(packageStateInternal, i, i2, j);
    }

    public final boolean hasCrossUserPermission(int i, int i2, int i3, boolean z, boolean z2) {
        if ((!z2 && i3 == i2) || PackageManagerServiceUtils.isSystemOrRoot(i) || DualAppManagerService.isCrossAccessAllowed(i2, i3)) {
            return true;
        }
        if (z) {
            return hasPermission("android.permission.INTERACT_ACROSS_USERS_FULL");
        }
        return hasPermission("android.permission.INTERACT_ACROSS_USERS_FULL") || hasPermission("android.permission.INTERACT_ACROSS_USERS");
    }

    public final boolean hasNonNegativePriority(List list) {
        return list.size() > 0 && ((ResolveInfo) list.get(0)).priority >= 0;
    }

    public final boolean hasPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isCallerSameApp(String str, int i) {
        return isCallerSameApp(str, i, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isCallerSameApp(String str, int i, boolean z) {
        if (Process.isSdkSandboxUid(i)) {
            return str != null && str.equals(this.mService.getSdkSandboxPackageName());
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        if (z && Process.isIsolated(i)) {
            i = getIsolatedOwner(i);
        }
        return androidPackage != null && UserHandle.getAppId(i) == androidPackage.getUid();
    }

    public final boolean isCallerFromManagedUserOrProfile(int i) {
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) this.mInjector.getLocalService(DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null && devicePolicyManagerInternal.isUserOrganizationManaged(i);
    }

    public final boolean isComponentVisibleToInstantApp(ComponentName componentName) {
        if (isComponentVisibleToInstantApp(componentName, 1) || isComponentVisibleToInstantApp(componentName, 3)) {
            return true;
        }
        return isComponentVisibleToInstantApp(componentName, 4);
    }

    public final boolean isComponentVisibleToInstantApp(ComponentName componentName, int i) {
        if (i == 1) {
            ParsedActivity activity = this.mComponentResolver.getActivity(componentName);
            if (activity == null) {
                return false;
            }
            return ((activity.getFlags() & 1048576) != 0) && ((activity.getFlags() & 2097152) == 0);
        }
        if (i == 2) {
            ParsedActivity receiver = this.mComponentResolver.getReceiver(componentName);
            if (receiver == null) {
                return false;
            }
            return ((receiver.getFlags() & 1048576) != 0) && !((receiver.getFlags() & 2097152) == 0);
        }
        if (i == 3) {
            ParsedService service = this.mComponentResolver.getService(componentName);
            return (service == null || (service.getFlags() & 1048576) == 0) ? false : true;
        }
        if (i == 4) {
            ParsedProvider provider = this.mComponentResolver.getProvider(componentName);
            return (provider == null || (provider.getFlags() & 1048576) == 0) ? false : true;
        }
        if (i == 0) {
            return isComponentVisibleToInstantApp(componentName);
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isImplicitImageCaptureIntentAndNotSetByDpc(Intent intent, int i, String str, long j) {
        return intent.isImplicitImageCaptureIntent() && !isPersistentPreferredActivitySetByDpm(intent, i, str, j);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstantApp(String str, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "isInstantApp");
        return isInstantAppInternal(str, i, callingUid);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstantAppInternal(String str, int i, int i2) {
        return isInstantAppInternalBody(str, i, i2);
    }

    public boolean isInstantAppInternalBody(String str, int i, int i2) {
        if (Process.isIsolated(i2)) {
            i2 = getIsolatedOwner(i2);
        }
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (packageStateInternal != null && (isCallerSameApp(str, i2) || canViewInstantApps(i2, i) || this.mInstantAppRegistry.isInstantAccessGranted(i, UserHandle.getAppId(i2), packageStateInternal.getAppId()))) {
            return packageStateInternal.getUserStateOrDefault(i).isInstantApp();
        }
        return false;
    }

    public final boolean isInstantAppResolutionAllowed(Intent intent, List list, int i, boolean z, long j) {
        if (this.mInstantAppResolverConnection == null || instantAppInstallerActivity() == null || intent.getComponent() != null || (intent.getFlags() & Integer.MIN_VALUE) != 0 || (intent.getFlags() & 1024) != 0) {
            return false;
        }
        if (!z && intent.getPackage() != null) {
            return false;
        }
        if (!intent.isWebIntent()) {
            if ((list != null && list.size() != 0) || (intent.getFlags() & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0) {
                return false;
            }
        } else if (intent.getData() == null || TextUtils.isEmpty(intent.getData().getHost()) || areWebInstantAppsDisabled(i)) {
            return false;
        }
        return isInstantAppResolutionAllowedBody(intent, list, i, z, j);
    }

    public boolean isInstantAppResolutionAllowedBody(Intent intent, List list, int i, boolean z, long j) {
        int size = list == null ? 0 : list.size();
        boolean z2 = (intent.getFlags() & 8) != 0;
        if (z2) {
            Slog.d("PackageManager", "Checking if instant app resolution allowed, resolvedActivities = " + list);
        }
        for (int i2 = 0; i2 < size; i2++) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(i2);
            String str = resolveInfo.activityInfo.packageName;
            PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
            if (packageStateInternal != null) {
                if (!resolveInfo.handleAllWebDataURI && PackageManagerServiceUtils.hasAnyDomainApproval(this.mDomainVerificationManager, packageStateInternal, intent, j, i)) {
                    if (PackageManagerService.DEBUG_INSTANT) {
                        Slog.v("PackageManager", "DENY instant app; pkg: " + str + ", approved");
                    }
                    return false;
                }
                if (packageStateInternal.getUserStateOrDefault(i).isInstantApp()) {
                    if (PackageManagerService.DEBUG_INSTANT) {
                        Slog.v("PackageManager", "DENY instant app installed; pkg: " + str);
                    }
                    return false;
                }
            } else if (z2) {
                Slog.d("PackageManager", "Could not find package " + str);
            }
        }
        return true;
    }

    public final boolean isPersistentPreferredActivitySetByDpm(Intent intent, int i, String str, long j) {
        List arrayList;
        PersistentPreferredIntentResolver persistentPreferredActivities = this.mSettings.getPersistentPreferredActivities(i);
        if (persistentPreferredActivities != null) {
            arrayList = persistentPreferredActivities.queryIntent(this, intent, str, (j & 65536) != 0, i);
        } else {
            arrayList = new ArrayList();
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((PersistentPreferredActivity) it.next()).mIsSetByDpm) {
                return true;
            }
        }
        return false;
    }

    public final boolean isRecentsAccessingChildProfiles(int i, int i2) {
        if (!((ActivityTaskManagerInternal) this.mInjector.getLocalService(ActivityTaskManagerInternal.class)).isCallerRecents(i)) {
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

    public final boolean isSameProfileGroup(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return UserManagerService.getInstance().isSameProfileGroup(i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean shouldFilterApplication(PackageStateInternal packageStateInternal, int i, ComponentName componentName, int i2, int i3, boolean z) {
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
        boolean z2 = getInstantAppPackageName(i4) != null;
        if (packageStateInternal == null || !(!z || PackageManagerServiceUtils.isSystemOrRootOrShell(i4) || packageStateInternal.isHiddenUntilInstalled() || packageStateInternal.getUserStateOrDefault(i3).isInstalled())) {
            return z2 || z || Process.isSdkSandboxUid(i4);
        }
        if (isCallerSameApp(packageStateInternal.getPackageName(), i4)) {
            return false;
        }
        if (z2) {
            if (packageStateInternal.getUserStateOrDefault(i3).isInstantApp()) {
                return true;
            }
            if (componentName != null) {
                ParsedInstrumentation parsedInstrumentation = (ParsedInstrumentation) this.mInstrumentation.get(componentName);
                if (parsedInstrumentation == null || !isCallerSameApp(parsedInstrumentation.getTargetPackage(), i4)) {
                    return !isComponentVisibleToInstantApp(componentName, i2);
                }
                return false;
            }
            return !packageStateInternal.getPkg().isVisibleToInstantApps();
        }
        if (packageStateInternal.getUserStateOrDefault(i3).isInstantApp()) {
            if (canViewInstantApps(i4, i3)) {
                return false;
            }
            if (componentName != null) {
                return true;
            }
            return !this.mInstantAppRegistry.isInstantAccessGranted(i3, UserHandle.getAppId(i4), packageStateInternal.getAppId());
        }
        return this.mAppsFilter.shouldFilterApplication(this, i4, this.mSettings.getSettingBase(UserHandle.getAppId(i4)), packageStateInternal, i3);
    }

    public final boolean shouldFilterApplication(PackageStateInternal packageStateInternal, int i, ComponentName componentName, int i2, int i3) {
        return shouldFilterApplication(packageStateInternal, i, componentName, i2, i3, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplication(PackageStateInternal packageStateInternal, int i, int i2) {
        return shouldFilterApplication(packageStateInternal, i, null, 0, i2, false);
    }

    public final boolean shouldFilterApplication(SharedUserSetting sharedUserSetting, int i, int i2) {
        ArraySet packageStates = sharedUserSetting.getPackageStates();
        boolean z = true;
        for (int size = packageStates.size() - 1; size >= 0 && z; size--) {
            z &= shouldFilterApplication((PackageStateInternal) packageStates.valueAt(size), i, null, 0, i2, false);
        }
        return z;
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplicationIncludingUninstalled(PackageStateInternal packageStateInternal, int i, int i2) {
        return shouldFilterApplication(packageStateInternal, i, null, 0, i2, true);
    }

    public final boolean shouldFilterApplicationIncludingUninstalled(SharedUserSetting sharedUserSetting, int i, int i2) {
        if (shouldFilterApplication(sharedUserSetting, i, i2)) {
            return true;
        }
        if (PackageManagerServiceUtils.isSystemOrRootOrShell(i)) {
            return false;
        }
        ArraySet packageStates = sharedUserSetting.getPackageStates();
        for (int i3 = 0; i3 < packageStates.size(); i3++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i3);
            if (packageStateInternal.getUserStateOrDefault(i2).isInstalled() || packageStateInternal.isHiddenUntilInstalled()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public final int checkUidPermission(String str, int i) {
        return this.mPermissionManager.checkUidPermission(i, str);
    }

    @Override // com.android.server.pm.Computer
    public int getPackageUidInternal(String str, long j, int i, int i2) {
        PackageStateInternal packageStateInternal;
        PackageStateInternal packageStateInternal2;
        PackageStateInternal packageStateInternal3 = this.mSettings.getPackage(str);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        if (androidPackage != null && AndroidPackageUtils.isMatchForSystemOnly(packageStateInternal3, j) && (packageStateInternal2 = getPackageStateInternal(androidPackage.getPackageName(), i2)) != null && packageStateInternal2.getUserStateOrDefault(i).isInstalled() && !shouldFilterApplication(packageStateInternal2, i2, i)) {
            return UserHandle.getUid(i, androidPackage.getUid());
        }
        if ((4202496 & j) == 0 || (packageStateInternal = this.mSettings.getPackage(str)) == null || !PackageStateUtils.isMatch(packageStateInternal, j) || shouldFilterApplication(packageStateInternal, i2, i)) {
            return -1;
        }
        return UserHandle.getUid(i, packageStateInternal.getAppId());
    }

    public final long updateFlags(long j, int i) {
        if ((j & 786432) != 0) {
            return j;
        }
        return j | (this.mInjector.getUserManagerInternal().isUserUnlockingOrUnlocked(i) ? 786432L : 524288L);
    }

    public final long updateFlagsForApplication(long j, int i) {
        return updateFlagsForPackage(j, i);
    }

    public final long updateFlagsForComponent(long j, int i) {
        return updateFlags(j, i);
    }

    public final long updateFlagsForPackage(long j, int i) {
        long j2;
        boolean z = UserHandle.getCallingUserId() == 0;
        if ((j & 4194304) != 0) {
            enforceCrossUserPermission(Binder.getCallingUid(), i, false, false, !isRecentsAccessingChildProfiles(Binder.getCallingUid(), i), "MATCH_ANY_USER flag requires INTERACT_ACROSS_USERS permission");
        } else if ((8192 & j) != 0 && z && this.mUserManager.hasProfile(0)) {
            j2 = j | 4194304;
            return updateFlags(j2, i);
        }
        j2 = j;
        return updateFlags(j2, i);
    }

    @Override // com.android.server.pm.Computer
    public final long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2) {
        return updateFlagsForResolve(j, i, i2, z, false, z2);
    }

    public final long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2, boolean z3) {
        long j2;
        if (safeMode() || z3) {
            j |= 1048576;
        }
        if (getInstantAppPackageName(i2) != null) {
            if (z2) {
                j |= 33554432;
            }
            j2 = j | 16777216 | 8388608;
        } else {
            boolean z4 = true;
            boolean z5 = (j & 8388608) != 0;
            if (!z && (!z5 || !canViewInstantApps(i2, i))) {
                z4 = false;
            }
            j2 = j & (-50331649);
            if (!z4) {
                j2 &= -8388609;
            }
        }
        return updateFlagsForComponent(j2, i);
    }

    @Override // com.android.server.pm.Computer
    public final void enforceCrossUserOrProfilePermission(int i, int i2, boolean z, boolean z2, String str) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Invalid userId " + i2);
        }
        if (z2) {
            PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerInternal(), "no_debugging_features", i, i2);
        }
        int userId = UserHandle.getUserId(i);
        if (hasCrossUserPermission(i, userId, i2, z, false)) {
            return;
        }
        boolean isSameProfileGroup = isSameProfileGroup(userId, i2);
        if (isSameProfileGroup && PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.INTERACT_ACROSS_PROFILES", -1, i, getPackage(i).getPackageName()) == 0) {
            return;
        }
        String buildInvalidCrossUserOrProfilePermissionMessage = buildInvalidCrossUserOrProfilePermissionMessage(i, i2, str, z, isSameProfileGroup);
        Slog.w("PackageManager", buildInvalidCrossUserOrProfilePermissionMessage);
        throw new SecurityException(buildInvalidCrossUserOrProfilePermissionMessage);
    }

    public static String buildInvalidCrossUserOrProfilePermissionMessage(int i, int i2, String str, boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append("UID ");
        sb.append(i);
        sb.append(" requires ");
        sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
        if (!z) {
            sb.append(" or ");
            sb.append("android.permission.INTERACT_ACROSS_USERS");
            if (z2) {
                sb.append(" or ");
                sb.append("android.permission.INTERACT_ACROSS_PROFILES");
            }
        }
        sb.append(" to access user ");
        sb.append(i2);
        sb.append(".");
        return sb.toString();
    }

    @Override // com.android.server.pm.Computer
    public final void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, String str) {
        enforceCrossUserPermission(i, i2, z, z2, false, str);
    }

    public final void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, boolean z3, String str) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Invalid userId " + i2);
        }
        if (z2) {
            PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerInternal(), "no_debugging_features", i, i2);
        }
        if (hasCrossUserPermission(i, UserHandle.getUserId(i), i2, z, z3)) {
            return;
        }
        String buildInvalidCrossUserPermissionMessage = buildInvalidCrossUserPermissionMessage(i, i2, str, z);
        Slog.w("PackageManager", buildInvalidCrossUserPermissionMessage);
        throw new SecurityException(buildInvalidCrossUserPermissionMessage);
    }

    public static String buildInvalidCrossUserPermissionMessage(int i, int i2, String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append("UID ");
        sb.append(i);
        sb.append(" requires ");
        sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
        if (!z) {
            sb.append(" or ");
            sb.append("android.permission.INTERACT_ACROSS_USERS");
        }
        sb.append(" to access user ");
        sb.append(i2);
        sb.append(".");
        return sb.toString();
    }

    @Override // com.android.server.pm.Computer
    public SigningDetails getSigningDetails(String str) {
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        if (androidPackage == null) {
            return null;
        }
        return androidPackage.getSigningDetails();
    }

    @Override // com.android.server.pm.Computer
    public SigningDetails getSigningDetails(int i) {
        Watchable settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase != null) {
            if (settingBase instanceof SharedUserSetting) {
                return ((SharedUserSetting) settingBase).signatures.mSigningDetails;
            }
            if (settingBase instanceof PackageStateInternal) {
                return ((PackageStateInternal) settingBase).getSigningDetails();
            }
        }
        return SigningDetails.UNKNOWN;
    }

    @Override // com.android.server.pm.Computer
    public boolean filterAppAccess(AndroidPackage androidPackage, int i, int i2) {
        return shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), i, i2);
    }

    @Override // com.android.server.pm.Computer
    public boolean filterAppAccess(String str, int i, int i2, boolean z) {
        return shouldFilterApplication(getPackageStateInternal(str), i, null, 0, i2, z);
    }

    @Override // com.android.server.pm.Computer
    public boolean filterAppAccess(int i, int i2) {
        if (Process.isSdkSandboxUid(i)) {
            return (i2 == i || Process.getAppUidForSdkSandboxUid(i) == i) ? false : true;
        }
        int userId = UserHandle.getUserId(i);
        Watchable settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase == null) {
            return true;
        }
        if (settingBase instanceof SharedUserSetting) {
            return shouldFilterApplicationIncludingUninstalled((SharedUserSetting) settingBase, i2, userId);
        }
        if (settingBase instanceof PackageStateInternal) {
            return shouldFilterApplicationIncludingUninstalled((PackageStateInternal) settingBase, i2, userId);
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public void dump(int i, FileDescriptor fileDescriptor, PrintWriter printWriter, DumpState dumpState) {
        Collection<PackageStateInternal> values;
        Collection values2;
        String targetPackageName = dumpState.getTargetPackageName();
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(targetPackageName);
        dumpState.isCheckIn();
        if (targetPackageName == null || packageStateInternal != null || isApexPackage(targetPackageName)) {
            switch (i) {
                case 1:
                    this.mSharedLibraries.dump(printWriter, dumpState);
                    return;
                case 512:
                    this.mSettings.dumpReadMessages(printWriter, dumpState);
                    return;
                case IInstalld.FLAG_USE_QUOTA /* 4096 */:
                    this.mSettings.dumpPreferred(printWriter, dumpState, targetPackageName);
                    return;
                case IInstalld.FLAG_FORCE /* 8192 */:
                    printWriter.flush();
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileDescriptor));
                    TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
                    try {
                        newFastSerializer.setOutput(bufferedOutputStream, StandardCharsets.UTF_8.name());
                        newFastSerializer.startDocument((String) null, Boolean.TRUE);
                        newFastSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                        this.mSettings.writePreferredActivitiesLPr(newFastSerializer, 0, dumpState.isFullPreferred());
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
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("Database versions:");
                    this.mSettings.dumpVersionLPr(new IndentingPrintWriter(printWriter, "  "));
                    return;
                case 262144:
                    android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    indentingPrintWriter.println("Domain verification status:");
                    indentingPrintWriter.increaseIndent();
                    try {
                        this.mDomainVerificationManager.printState(this, indentingPrintWriter, targetPackageName, -1);
                    } catch (Exception e4) {
                        printWriter.println("Failure printing domain verification information");
                        Slog.e("PackageManager", "Failure printing domain verification information", e4);
                    }
                    indentingPrintWriter.decreaseIndent();
                    return;
                case 524288:
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(printWriter, "  ", 120);
                    indentingPrintWriter2.println();
                    indentingPrintWriter2.println("Frozen packages:");
                    indentingPrintWriter2.increaseIndent();
                    if (this.mFrozenPackages.size() == 0) {
                        indentingPrintWriter2.println("(none)");
                    } else {
                        for (int i2 = 0; i2 < this.mFrozenPackages.size(); i2++) {
                            indentingPrintWriter2.print("package=");
                            indentingPrintWriter2.print((String) this.mFrozenPackages.keyAt(i2));
                            indentingPrintWriter2.print(", refCounts=");
                            indentingPrintWriter2.println(this.mFrozenPackages.valueAt(i2));
                        }
                    }
                    indentingPrintWriter2.decreaseIndent();
                    return;
                case 1048576:
                    IndentingPrintWriter indentingPrintWriter3 = new IndentingPrintWriter(printWriter, "  ");
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    indentingPrintWriter3.println("Dexopt state:");
                    indentingPrintWriter3.increaseIndent();
                    if (DexOptHelper.useArtService()) {
                        DexOptHelper.dumpDexoptState(indentingPrintWriter3, targetPackageName);
                    } else {
                        if (packageStateInternal != null) {
                            values = Collections.singletonList(packageStateInternal);
                        } else {
                            values = this.mSettings.getPackages().values();
                        }
                        for (PackageStateInternal packageStateInternal2 : values) {
                            AndroidPackageInternal pkg = packageStateInternal2.getPkg();
                            if (pkg != null && !pkg.isApex()) {
                                String packageName = pkg.getPackageName();
                                indentingPrintWriter3.println("[" + packageName + "]");
                                indentingPrintWriter3.increaseIndent();
                                try {
                                    this.mPackageDexOptimizer.dumpDexoptState(indentingPrintWriter3, pkg, packageStateInternal2, this.mDexManager.getPackageUseInfoOrDefault(packageName));
                                    indentingPrintWriter3.decreaseIndent();
                                } catch (Installer.LegacyDexoptDisabledException e5) {
                                    throw new RuntimeException(e5);
                                }
                            }
                        }
                        indentingPrintWriter3.println("BgDexopt state:");
                        indentingPrintWriter3.increaseIndent();
                        this.mBackgroundDexOptService.dump(indentingPrintWriter3);
                        indentingPrintWriter3.decreaseIndent();
                    }
                    indentingPrintWriter3.decreaseIndent();
                    return;
                case 2097152:
                    IndentingPrintWriter indentingPrintWriter4 = new IndentingPrintWriter(printWriter, "  ");
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    indentingPrintWriter4.println("Compiler stats:");
                    indentingPrintWriter4.increaseIndent();
                    if (packageStateInternal != null) {
                        values2 = Collections.singletonList(packageStateInternal);
                    } else {
                        values2 = this.mSettings.getPackages().values();
                    }
                    Iterator it = values2.iterator();
                    while (it.hasNext()) {
                        AndroidPackageInternal pkg2 = ((PackageStateInternal) it.next()).getPkg();
                        if (pkg2 != null) {
                            String packageName2 = pkg2.getPackageName();
                            indentingPrintWriter4.println("[" + packageName2 + "]");
                            indentingPrintWriter4.increaseIndent();
                            CompilerStats.PackageStats packageStats = this.mCompilerStats.getPackageStats(packageName2);
                            if (packageStats == null) {
                                indentingPrintWriter4.println("(No recorded stats)");
                            } else {
                                packageStats.dump(indentingPrintWriter4);
                            }
                            indentingPrintWriter4.decreaseIndent();
                        }
                    }
                    return;
                case 33554432:
                    if (targetPackageName == null || isApexPackage(targetPackageName)) {
                        this.mApexManager.dump(printWriter);
                        dumpApex(printWriter, targetPackageName);
                        return;
                    }
                    return;
                case 67108864:
                    this.mAppsFilter.dumpQueries(printWriter, packageStateInternal != null ? Integer.valueOf(packageStateInternal.getAppId()) : null, dumpState, this.mUserManager.getUserIds(), new QuadFunction() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda6
                        public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                            return ComputerEngine.this.getPackagesForUidInternalBody(((Integer) obj).intValue(), ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), ((Boolean) obj4).booleanValue());
                        }
                    });
                    return;
                default:
                    return;
            }
        }
    }

    public final void generateApexPackageInfo(List list, List list2, List list3, List list4) {
        for (AndroidPackage androidPackage : this.mPackages.values()) {
            String packageName = androidPackage.getPackageName();
            PackageStateInternal packageStateInternal = this.mSettings.getPackage(packageName);
            if (androidPackage.isApex() && packageStateInternal != null) {
                list.add(packageStateInternal);
                if (!packageStateInternal.isUpdatedSystemApp()) {
                    list3.add(packageStateInternal);
                } else {
                    PackageStateInternal disabledSystemPkg = this.mSettings.getDisabledSystemPkg(packageName);
                    list4.add(disabledSystemPkg);
                    list2.add(disabledSystemPkg);
                }
            }
        }
    }

    public final void dumpApex(PrintWriter printWriter, String str) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ", 120);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        generateApexPackageInfo(arrayList, arrayList2, arrayList3, arrayList4);
        indentingPrintWriter.println("Active APEX packages:");
        dumpApexPackageStates(arrayList, true, str, indentingPrintWriter);
        indentingPrintWriter.println("Inactive APEX packages:");
        dumpApexPackageStates(arrayList2, false, str, indentingPrintWriter);
        indentingPrintWriter.println("Factory APEX packages:");
        dumpApexPackageStates(arrayList3, true, str, indentingPrintWriter);
        dumpApexPackageStates(arrayList4, false, str, indentingPrintWriter);
    }

    public static void dumpApexPackageStates(List list, boolean z, String str, IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) list.get(i);
            AndroidPackageInternal pkg = packageStateInternal.getPkg();
            if (str == null || str.equals(pkg.getPackageName())) {
                indentingPrintWriter.println(pkg.getPackageName());
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Version: " + pkg.getLongVersionCode());
                indentingPrintWriter.println("Path: " + pkg.getBaseApkPath());
                indentingPrintWriter.println("IsActive: " + z);
                indentingPrintWriter.println("IsFactory: " + (packageStateInternal.isUpdatedSystemApp() ^ true));
                indentingPrintWriter.println("ApplicationInfo: ");
                indentingPrintWriter.increaseIndent();
                AndroidPackageUtils.generateAppInfoWithoutState(pkg).dump(new PrintWriterPrinter(indentingPrintWriter), "");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityBody(Intent intent, String str, long j, List list, boolean z, boolean z2, boolean z3, int i, boolean z4, int i2, boolean z5) {
        String kioskHomePackageAsUser;
        List list2;
        int i3;
        int i4;
        int i5;
        int i6;
        long j2;
        String str2;
        String homeActivity;
        String str3;
        PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityBodyResult = new PackageManagerService.FindPreferredActivityBodyResult();
        long updateFlagsForResolve = updateFlagsForResolve(j, i, i2, false, isImplicitImageCaptureIntentAndNotSetByDpc(intent, i, str, j));
        Intent updateIntentForResolve = PackageManagerServiceUtils.updateIntentForResolve(intent);
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        if (proKioskManager != null && proKioskManager.getProKioskState() && updateIntentForResolve.hasCategory("android.intent.category.HOME") && updateIntentForResolve.getComponent() == null && (homeActivity = proKioskManager.getHomeActivity()) != null) {
            int indexOf = homeActivity.indexOf(47);
            if (indexOf != -1) {
                String substring = homeActivity.substring(indexOf + 1);
                homeActivity = homeActivity.substring(0, indexOf);
                Intent intent2 = new Intent();
                intent2.setClassName(homeActivity, substring);
                if (!queryIntentActivitiesInternal(intent2, null, 131072L, i).isEmpty()) {
                    str3 = substring;
                    Intent intent3 = new Intent("android.intent.action.MAIN", (Uri) null);
                    intent3.addCategory("android.intent.category.LAUNCHER");
                    String str4 = str3;
                    for (ResolveInfo resolveInfo : queryIntentActivitiesInternal(intent3, null, 1024L, i)) {
                        if (resolveInfo.activityInfo.packageName.equalsIgnoreCase(homeActivity)) {
                            if (str4 != null) {
                                resolveInfo.activityInfo.name = str4;
                            }
                            findPreferredActivityBodyResult.mPreferredResolveInfo = resolveInfo;
                            return findPreferredActivityBodyResult;
                        }
                    }
                }
            }
            str3 = null;
            Intent intent32 = new Intent("android.intent.action.MAIN", (Uri) null);
            intent32.addCategory("android.intent.category.LAUNCHER");
            String str42 = str3;
            while (r1.hasNext()) {
            }
        }
        if (updateIntentForResolve.hasCategory("android.intent.category.HOME")) {
            try {
                IKioskMode asInterface = IKioskMode.Stub.asInterface(ServiceManager.getService("kioskmode"));
                if (asInterface != null && (kioskHomePackageAsUser = asInterface.getKioskHomePackageAsUser(i)) != null) {
                    int size = list.size();
                    for (int i7 = 0; i7 < size; i7++) {
                        ResolveInfo resolveInfo2 = (ResolveInfo) list.get(i7);
                        if (resolveInfo2.activityInfo.packageName.equals(kioskHomePackageAsUser)) {
                            findPreferredActivityBodyResult.mPreferredResolveInfo = resolveInfo2;
                            return findPreferredActivityBodyResult;
                        }
                    }
                }
            } catch (RemoteException e) {
                Log.e("PackageManager", "Failed talking with kiosk mode service", e);
            }
        }
        IApplicationPolicy asInterface2 = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (asInterface2 != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ComponentName defaultApplicationInternal = asInterface2.getDefaultApplicationInternal(updateIntentForResolve, i);
                if (defaultApplicationInternal != null) {
                    int size2 = list.size();
                    int i8 = 0;
                    while (i8 < size2) {
                        ResolveInfo resolveInfo3 = (ResolveInfo) list.get(i8);
                        int i9 = size2;
                        if (resolveInfo3.activityInfo.packageName.equals(defaultApplicationInternal.getPackageName()) && resolveInfo3.activityInfo.name.equals(defaultApplicationInternal.getClassName())) {
                            findPreferredActivityBodyResult.mPreferredResolveInfo = resolveInfo3;
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return findPreferredActivityBodyResult;
                        }
                        i8++;
                        size2 = i9;
                    }
                }
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        String str5 = "PackageManager";
        String str6 = "android.intent.category.HOME";
        ResolveInfo findPersistentPreferredActivity = findPersistentPreferredActivity(updateIntentForResolve, str, updateFlagsForResolve, list, z3, i);
        findPreferredActivityBodyResult.mPreferredResolveInfo = findPersistentPreferredActivity;
        if (findPersistentPreferredActivity != null) {
            return findPreferredActivityBodyResult;
        }
        PreferredIntentResolver preferredActivities = this.mSettings.getPreferredActivities(i);
        if (z3) {
            Slog.v(str5, "Looking for preferred activities...");
        }
        if (preferredActivities != null) {
            list2 = preferredActivities.queryIntent(this, updateIntentForResolve, str, (65536 & updateFlagsForResolve) != 0, i);
        } else {
            list2 = null;
        }
        if (list2 != null && list2.size() > 0) {
            if (z3) {
                Slog.v(str5, "Figuring out best match...");
            }
            int size3 = list.size();
            int i10 = 0;
            int i11 = 0;
            while (i11 < size3) {
                String str7 = str5;
                ResolveInfo resolveInfo4 = (ResolveInfo) list.get(i11);
                if (z3) {
                    Slog.v(str7, "Match for " + resolveInfo4.activityInfo + ": 0x" + Integer.toHexString(resolveInfo4.match));
                }
                int i12 = resolveInfo4.match;
                if (i12 > i10) {
                    i10 = i12;
                }
                i11++;
                str5 = str7;
            }
            String str8 = str5;
            if (z3) {
                Slog.v(str8, "Best match: 0x" + Integer.toHexString(i10));
            }
            int i13 = i10 & 268369920;
            int size4 = list2.size();
            int i14 = 0;
            while (i14 < size4) {
                PreferredActivity preferredActivity = (PreferredActivity) list2.get(i14);
                List list3 = list2;
                if (z3) {
                    StringBuilder sb = new StringBuilder();
                    i3 = size4;
                    sb.append("Checking PreferredActivity ds=");
                    sb.append(preferredActivity.countDataSchemes() > 0 ? preferredActivity.getDataScheme(0) : "<none>");
                    sb.append("\n  component=");
                    sb.append(preferredActivity.mPref.mComponent);
                    Slog.v(str8, sb.toString());
                    i4 = i14;
                    preferredActivity.dump(new LogPrinter(2, str8, 3), "  ");
                } else {
                    i3 = size4;
                    i4 = i14;
                }
                PreferredComponent preferredComponent = preferredActivity.mPref;
                if (preferredComponent.mMatch != i13) {
                    if (z3) {
                        Slog.v(str8, "Skipping bad match " + Integer.toHexString(preferredActivity.mPref.mMatch));
                    }
                } else if (!z || preferredComponent.mAlways) {
                    ActivityInfo activityInfo = getActivityInfo(preferredComponent.mComponent, 512 | updateFlagsForResolve | 524288 | 262144, i);
                    if (z3) {
                        Slog.v(str8, "Found preferred activity:");
                        if (activityInfo != null) {
                            i5 = i13;
                            activityInfo.dump(new LogPrinter(2, str8, 3), "  ");
                        } else {
                            i5 = i13;
                            Slog.v(str8, "  null");
                        }
                    } else {
                        i5 = i13;
                    }
                    boolean z6 = isHomeIntent(updateIntentForResolve) && !z5;
                    boolean z7 = (z6 || z4) ? false : true;
                    if (activityInfo != null) {
                        int i15 = 0;
                        while (i15 < size3) {
                            ResolveInfo resolveInfo5 = (ResolveInfo) list.get(i15);
                            i6 = size3;
                            j2 = updateFlagsForResolve;
                            if (!resolveInfo5.activityInfo.applicationInfo.packageName.equals(activityInfo.applicationInfo.packageName) || !resolveInfo5.activityInfo.name.equals(activityInfo.name)) {
                                i15++;
                                size3 = i6;
                                updateFlagsForResolve = j2;
                            } else if (z2 && z7) {
                                preferredActivities.removeFilter((WatchedIntentFilter) preferredActivity);
                                findPreferredActivityBodyResult.mChanged = true;
                                str2 = str6;
                                i13 = i5;
                                i14 = i4 + 1;
                                str6 = str2;
                                size4 = i3;
                                updateFlagsForResolve = j2;
                                list2 = list3;
                                size3 = i6;
                            } else {
                                if (z && !preferredActivity.mPref.sameSet(list, z6, i)) {
                                    if (updateIntentForResolve.hasCategory(str6)) {
                                        try {
                                            IKioskMode asInterface3 = IKioskMode.Stub.asInterface(ServiceManager.getService("kioskmode"));
                                            if (asInterface3 != null && asInterface3.isKioskModeEnabledAsUser(i)) {
                                                findPreferredActivityBodyResult.mPreferredResolveInfo = resolveInfo5;
                                                return findPreferredActivityBodyResult;
                                            }
                                        } catch (RemoteException e2) {
                                            Log.e(str8, "Failed talking to Kiosk mode service", e2);
                                        }
                                    }
                                    if (!preferredActivity.mPref.isSuperset(list, z6)) {
                                        if (z7) {
                                            Slog.i(str8, "Result set changed, dropping preferred activity for " + updateIntentForResolve + " type " + str);
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append("Result set changed, ");
                                            sb2.append(updateIntentForResolve);
                                            PreferredActivityLog.logDroppingPreferredActivity(preferredActivity, list, sb2.toString());
                                            preferredActivities.removeFilter((WatchedIntentFilter) preferredActivity);
                                            PreferredComponent preferredComponent2 = preferredActivity.mPref;
                                            preferredActivities.addFilter((PackageDataSnapshot) this, (WatchedIntentFilter) new PreferredActivity((WatchedIntentFilter) preferredActivity, preferredComponent2.mMatch, (ComponentName[]) null, preferredComponent2.mComponent, false));
                                            findPreferredActivityBodyResult.mChanged = true;
                                        }
                                        findPreferredActivityBodyResult.mPreferredResolveInfo = null;
                                        return findPreferredActivityBodyResult;
                                    }
                                    if (z7) {
                                        PreferredActivityLog.logDroppingPreferredActivity(preferredActivity, list, "Result set changed, but still valid, " + updateIntentForResolve);
                                        PreferredComponent preferredComponent3 = preferredActivity.mPref;
                                        int i16 = preferredComponent3.mMatch;
                                        ComponentName[] discardObsoleteComponents = preferredComponent3.discardObsoleteComponents(list);
                                        PreferredComponent preferredComponent4 = preferredActivity.mPref;
                                        PreferredActivity preferredActivity2 = new PreferredActivity(preferredActivity, i16, discardObsoleteComponents, preferredComponent4.mComponent, preferredComponent4.mAlways);
                                        preferredActivities.removeFilter((WatchedIntentFilter) preferredActivity);
                                        preferredActivities.addFilter((PackageDataSnapshot) this, (WatchedIntentFilter) preferredActivity2);
                                        findPreferredActivityBodyResult.mChanged = true;
                                    }
                                }
                                if (z3) {
                                    Slog.v(str8, "Returning preferred activity: " + resolveInfo5.activityInfo.packageName + "/" + resolveInfo5.activityInfo.name);
                                }
                                findPreferredActivityBodyResult.mPreferredResolveInfo = resolveInfo5;
                                return findPreferredActivityBodyResult;
                            }
                        }
                    } else if (z7) {
                        Slog.w(str8, "Removing dangling preferred activity: " + preferredActivity.mPref.mComponent);
                        preferredActivities.removeFilter((WatchedIntentFilter) preferredActivity);
                        findPreferredActivityBodyResult.mChanged = true;
                        i6 = size3;
                        j2 = updateFlagsForResolve;
                        str2 = str6;
                        i13 = i5;
                        i14 = i4 + 1;
                        str6 = str2;
                        size4 = i3;
                        updateFlagsForResolve = j2;
                        list2 = list3;
                        size3 = i6;
                    }
                    i6 = size3;
                    j2 = updateFlagsForResolve;
                    str2 = str6;
                    i13 = i5;
                    i14 = i4 + 1;
                    str6 = str2;
                    size4 = i3;
                    updateFlagsForResolve = j2;
                    list2 = list3;
                    size3 = i6;
                } else if (z3) {
                    Slog.v(str8, "Skipping mAlways=false entry");
                }
                i6 = size3;
                i5 = i13;
                j2 = updateFlagsForResolve;
                str2 = str6;
                i13 = i5;
                i14 = i4 + 1;
                str6 = str2;
                size4 = i3;
                updateFlagsForResolve = j2;
                list2 = list3;
                size3 = i6;
            }
        }
        return findPreferredActivityBodyResult;
    }

    public static boolean isHomeIntent(Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && intent.hasCategory("android.intent.category.HOME") && intent.hasCategory("android.intent.category.DEFAULT");
    }

    @Override // com.android.server.pm.Computer
    public final PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal2(Intent intent, String str, long j, List list, boolean z, boolean z2, boolean z3, int i, boolean z4, boolean z5) {
        return findPreferredActivityBody(intent, str, j, list, z, z2, z3, i, z4, Binder.getCallingUid(), z5);
    }

    public final PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal(Intent intent, String str, long j, List list, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        return findPreferredActivityBody(intent, str, j, list, z, z2, z3, i, z4, Binder.getCallingUid(), Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 1);
    }

    @Override // com.android.server.pm.Computer
    public final ResolveInfo findPersistentPreferredActivity(Intent intent, String str, long j, List list, boolean z, int i) {
        List list2;
        int size = list.size();
        PersistentPreferredIntentResolver persistentPreferredActivities = this.mSettings.getPersistentPreferredActivities(i);
        if (z) {
            Slog.v("PackageManager", "Looking for persistent preferred activities...");
        }
        if (persistentPreferredActivities != null) {
            list2 = persistentPreferredActivities.queryIntent(this, intent, str, (j & 65536) != 0, i);
        } else {
            list2 = null;
        }
        if (list2 != null && list2.size() > 0) {
            int size2 = list2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                PersistentPreferredActivity persistentPreferredActivity = (PersistentPreferredActivity) list2.get(i2);
                if (z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Checking PersistentPreferredActivity ds=");
                    sb.append(persistentPreferredActivity.countDataSchemes() > 0 ? persistentPreferredActivity.getDataScheme(0) : "<none>");
                    sb.append("\n  component=");
                    sb.append(persistentPreferredActivity.mComponent);
                    Slog.v("PackageManager", sb.toString());
                    persistentPreferredActivity.dump(new LogPrinter(2, "PackageManager", 3), "  ");
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

    @Override // com.android.server.pm.Computer
    public PreferredIntentResolver getPreferredActivities(int i) {
        return this.mSettings.getPreferredActivities(i);
    }

    @Override // com.android.server.pm.Computer
    public ArrayMap getPackageStates() {
        return this.mSettings.getPackages();
    }

    @Override // com.android.server.pm.Computer
    public ArrayMap getDisabledSystemPackageStates() {
        return this.mSettings.getDisabledSystemPackages();
    }

    public String getRenamedPackage(String str) {
        return this.mSettings.getRenamedPackageLPr(str);
    }

    @Override // com.android.server.pm.Computer
    public WatchedArrayMap getSharedLibraries() {
        return this.mSharedLibraries.getAll();
    }

    @Override // com.android.server.pm.Computer
    public ArraySet getNotifyPackagesForReplacedReceived(String[] strArr) {
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
    public int getPackageStartability(boolean z, String str, int i, int i2) {
        boolean isUserKeyUnlocked = StorageManager.isUserKeyUnlocked(i2);
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, i2) || !packageStateInternal.getUserStateOrDefault(i2).isInstalled()) {
            return 1;
        }
        if (z && !packageStateInternal.isSystem()) {
            return 2;
        }
        if (this.mFrozenPackages.containsKey(str)) {
            return 3;
        }
        if (!isUserKeyUnlocked && !AndroidPackageUtils.isEncryptionAware(packageStateInternal.getPkg())) {
            return 4;
        }
        if (!SemPersonaManager.isDarDualEncrypted(i2)) {
            return 0;
        }
        if (!DualDarManager.isOnDeviceOwner(i2) || SemPersonaManager.isDoEnabled(i2)) {
            return !PersonaServiceHelper.isPackageAllowlistedForDualDAR(this.mContext, str, i2) ? 5 : 0;
        }
        Slog.i("PackageManager", "DualDAR at DO provisioning not completed");
        return 0;
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageAvailable(String str, int i) {
        PackageUserStateInternal userStateOrDefault;
        if (!this.mUserManager.exists(i)) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "is package available");
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null || shouldFilterApplication(packageStateInternal, callingUid, i) || (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)) == null) {
            return false;
        }
        return PackageUserStateUtils.isAvailable(userStateOrDefault, 0L);
    }

    @Override // com.android.server.pm.Computer
    public boolean isApexPackage(String str) {
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        return androidPackage != null && androidPackage.isApex();
    }

    @Override // com.android.server.pm.Computer
    public String[] currentToCanonicalPackageNames(String[] strArr) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return strArr;
        }
        String[] strArr2 = new String[strArr.length];
        int userId = UserHandle.getUserId(callingUid);
        boolean canViewInstantApps = canViewInstantApps(callingUid, userId);
        for (int length = strArr.length - 1; length >= 0; length--) {
            PackageStateInternal packageStateInternal = getPackageStateInternal(strArr[length]);
            boolean z = false;
            if (packageStateInternal != null && packageStateInternal.getRealName() != null && (!packageStateInternal.getUserStateOrDefault(userId).isInstantApp() || canViewInstantApps || this.mInstantAppRegistry.isInstantAccessGranted(userId, UserHandle.getAppId(callingUid), packageStateInternal.getAppId()))) {
                z = true;
            }
            strArr2[length] = z ? packageStateInternal.getRealName() : strArr[length];
        }
        return strArr2;
    }

    @Override // com.android.server.pm.Computer
    public String[] canonicalToCurrentPackageNames(String[] strArr) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return strArr;
        }
        String[] strArr2 = new String[strArr.length];
        int userId = UserHandle.getUserId(callingUid);
        boolean canViewInstantApps = canViewInstantApps(callingUid, userId);
        for (int length = strArr.length - 1; length >= 0; length--) {
            String renamedPackage = getRenamedPackage(strArr[length]);
            boolean z = false;
            if (renamedPackage != null) {
                PackageStateInternal packageStateInternal = getPackageStateInternal(strArr[length]);
                if (!(packageStateInternal != null && packageStateInternal.getUserStateOrDefault(userId).isInstantApp()) || canViewInstantApps || this.mInstantAppRegistry.isInstantAccessGranted(userId, UserHandle.getAppId(callingUid), packageStateInternal.getAppId())) {
                    z = true;
                }
            }
            if (!z) {
                renamedPackage = strArr[length];
            }
            strArr2[length] = renamedPackage;
        }
        return strArr2;
    }

    @Override // com.android.server.pm.Computer
    public int[] getPackageGids(String str, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "getPackageGids");
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return null;
        }
        if (packageStateInternal.getPkg() != null && AndroidPackageUtils.isMatchForSystemOnly(packageStateInternal, updateFlagsForPackage) && packageStateInternal.getUserStateOrDefault(i).isInstalled() && !shouldFilterApplication(packageStateInternal, callingUid, i)) {
            return this.mPermissionManager.getGidsForUid(UserHandle.getUid(i, packageStateInternal.getAppId()));
        }
        if ((4202496 & updateFlagsForPackage) == 0 || !PackageStateUtils.isMatch(packageStateInternal, updateFlagsForPackage) || shouldFilterApplication(packageStateInternal, callingUid, i)) {
            return null;
        }
        return this.mPermissionManager.getGidsForUid(UserHandle.getUid(i, packageStateInternal.getAppId()));
    }

    @Override // com.android.server.pm.Computer
    public int getTargetSdkVersion(String str) {
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, Binder.getCallingUid(), UserHandle.getCallingUserId())) {
            return -1;
        }
        return packageStateInternal.getPkg().getTargetSdkVersion();
    }

    @Override // com.android.server.pm.Computer
    public boolean activitySupportsIntentAsUser(ComponentName componentName, ComponentName componentName2, Intent intent, String str, int i) {
        PackageStateInternal packageStateInternal;
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "activitySupportsIntentAsUser");
        if (componentName2.equals(componentName)) {
            return true;
        }
        ParsedActivity activity = this.mComponentResolver.getActivity(componentName2);
        if (activity == null || (packageStateInternal = getPackageStateInternal(componentName2.getPackageName())) == null || shouldFilterApplication(packageStateInternal, callingUid, componentName2, 1, i, true)) {
            return false;
        }
        for (int i2 = 0; i2 < activity.getIntents().size(); i2++) {
            if (((ParsedIntentInfo) activity.getIntents().get(i2)).getIntentFilter().match(intent.getAction(), str, intent.getScheme(), intent.getData(), intent.getCategories(), "PackageManager") >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) {
        PackageStateInternal packageStateInternal;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "get receiver info");
        ParsedActivity receiver = this.mComponentResolver.getReceiver(componentName);
        if (receiver == null || (packageStateInternal = getPackageStateInternal(receiver.getPackageName())) == null || packageStateInternal.getPkg() == null || !PackageStateUtils.isEnabledAndMatches(packageStateInternal, receiver, updateFlagsForComponent, i) || shouldFilterApplication(packageStateInternal, callingUid, componentName, 2, i)) {
            return null;
        }
        return PackageInfoUtils.generateActivityInfo(packageStateInternal.getPkg(), receiver, updateFlagsForComponent, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    public ParceledListSlice getSharedLibraries(String str, long j, int i) {
        int i2;
        int i3;
        WatchedArrayMap watchedArrayMap;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        Preconditions.checkArgumentNonnegative(i, "userId must be >= 0");
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_PACKAGES") == 0 || canRequestPackageInstalls(str, callingUid, i, false) || this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_DELETE_PACKAGES") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_SHARED_LIBRARIES") == 0;
        WatchedArrayMap sharedLibraries = getSharedLibraries();
        int size = sharedLibraries.size();
        ArrayList arrayList = null;
        int i4 = 0;
        while (i4 < size) {
            WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) sharedLibraries.valueAt(i4);
            if (watchedLongSparseArray == null) {
                i2 = i4;
                i3 = size;
                watchedArrayMap = sharedLibraries;
            } else {
                int size2 = watchedLongSparseArray.size();
                ArrayList arrayList2 = arrayList;
                int i5 = 0;
                while (i5 < size2) {
                    SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.valueAt(i5);
                    if (!z && (sharedLibraryInfo.isStatic() || sharedLibraryInfo.isSdk())) {
                        break;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    VersionedPackage declaringPackage = sharedLibraryInfo.getDeclaringPackage();
                    try {
                        int i6 = size2;
                        int i7 = i5;
                        int i8 = i4;
                        WatchedLongSparseArray watchedLongSparseArray2 = watchedLongSparseArray;
                        int i9 = size;
                        WatchedArrayMap watchedArrayMap2 = sharedLibraries;
                        if (getPackageInfoInternal(declaringPackage.getPackageName(), declaringPackage.getLongVersionCode(), updateFlagsForPackage | 67108864, Binder.getCallingUid(), i) != null) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            SharedLibraryInfo sharedLibraryInfo2 = new SharedLibraryInfo(sharedLibraryInfo.getPath(), sharedLibraryInfo.getPackageName(), sharedLibraryInfo.getAllCodePaths(), sharedLibraryInfo.getName(), sharedLibraryInfo.getLongVersion(), sharedLibraryInfo.getType(), declaringPackage, getPackagesUsingSharedLibrary(sharedLibraryInfo, updateFlagsForPackage, callingUid, i), sharedLibraryInfo.getDependencies() == null ? null : new ArrayList(sharedLibraryInfo.getDependencies()), sharedLibraryInfo.isNative());
                            ArrayList arrayList3 = arrayList2 == null ? new ArrayList() : arrayList2;
                            arrayList3.add(sharedLibraryInfo2);
                            arrayList2 = arrayList3;
                        }
                        i5 = i7 + 1;
                        size2 = i6;
                        i4 = i8;
                        watchedLongSparseArray = watchedLongSparseArray2;
                        size = i9;
                        sharedLibraries = watchedArrayMap2;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                i2 = i4;
                i3 = size;
                watchedArrayMap = sharedLibraries;
                arrayList = arrayList2;
            }
            i4 = i2 + 1;
            size = i3;
            sharedLibraries = watchedArrayMap;
        }
        if (arrayList != null) {
            return new ParceledListSlice(arrayList);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public boolean canRequestPackageInstalls(String str, int i, int i2, boolean z) {
        AndroidPackage androidPackage;
        if (i != getPackageUidInternal(str, 0L, i2, i) && !PackageManagerServiceUtils.isSystemOrRoot(i)) {
            throw new SecurityException("Caller uid " + i + " does not own package " + str);
        }
        if (isInstantAppInternal(str, i2, 1000) || (androidPackage = (AndroidPackage) this.mPackages.get(str)) == null || androidPackage.getTargetSdkVersion() < 26) {
            return false;
        }
        if (androidPackage.getRequestedPermissions().contains("android.permission.REQUEST_INSTALL_PACKAGES")) {
            return !isInstallDisabledForPackage(str, r0, i2);
        }
        if (z) {
            throw new SecurityException("Need to declare android.permission.REQUEST_INSTALL_PACKAGES to call this api");
        }
        Slog.e("PackageManager", "Need to declare android.permission.REQUEST_INSTALL_PACKAGES to call this api");
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstallDisabledForPackage(String str, int i, int i2) {
        if (this.mUserManager.hasUserRestriction("no_install_unknown_sources", i2) || this.mUserManager.hasUserRestriction("no_install_unknown_sources_globally", i2)) {
            return true;
        }
        PackageManagerInternal.ExternalSourcesPolicy externalSourcesPolicy = this.mExternalSourcesPolicy;
        return (externalSourcesPolicy == null || externalSourcesPolicy.getPackageTrustedToInstallApps(str, i) == 0) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public List getPackagesUsingSharedLibrary(SharedLibraryInfo sharedLibraryInfo, long j, int i, int i2) {
        ArrayMap packageStates = getPackageStates();
        int size = packageStates.size();
        ArrayList arrayList = null;
        for (int i3 = 0; i3 < size; i3++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i3);
            if (packageStateInternal != null && PackageUserStateUtils.isAvailable(packageStateInternal.getUserStateOrDefault(i2), j)) {
                String name = sharedLibraryInfo.getName();
                if (sharedLibraryInfo.isStatic() || sharedLibraryInfo.isSdk()) {
                    String[] usesStaticLibraries = sharedLibraryInfo.isStatic() ? packageStateInternal.getUsesStaticLibraries() : packageStateInternal.getUsesSdkLibraries();
                    long[] usesStaticLibrariesVersions = sharedLibraryInfo.isStatic() ? packageStateInternal.getUsesStaticLibrariesVersions() : packageStateInternal.getUsesSdkLibrariesVersionsMajor();
                    int indexOf = ArrayUtils.indexOf(usesStaticLibraries, name);
                    if (indexOf >= 0 && usesStaticLibrariesVersions[indexOf] == sharedLibraryInfo.getLongVersion() && !shouldFilterApplication(packageStateInternal, i, i2)) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        String packageName = packageStateInternal.getPackageName();
                        if (packageStateInternal.getPkg() != null && packageStateInternal.getPkg().isStaticSharedLibrary()) {
                            packageName = packageStateInternal.getPkg().getManifestPackageName();
                        }
                        arrayList.add(new VersionedPackage(packageName, packageStateInternal.getVersionCode()));
                    }
                } else if (packageStateInternal.getPkg() != null && ((ArrayUtils.contains(packageStateInternal.getPkg().getUsesLibraries(), name) || ArrayUtils.contains(packageStateInternal.getPkg().getUsesOptionalLibraries(), name)) && !shouldFilterApplication(packageStateInternal, i, i2))) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(new VersionedPackage(packageStateInternal.getPackageName(), packageStateInternal.getVersionCode()));
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    public ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) {
        int i2;
        int i3;
        int i4;
        WatchedLongSparseArray watchedLongSparseArray;
        int i5;
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_SHARED_LIBRARIES", "getDeclaredSharedLibraries");
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "getDeclaredSharedLibraries");
        Preconditions.checkNotNull(str, "packageName cannot be null");
        Preconditions.checkArgumentNonnegative(i, "userId must be >= 0");
        if (!this.mUserManager.exists(i) || getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        WatchedArrayMap sharedLibraries = getSharedLibraries();
        int size = sharedLibraries.size();
        ArrayList arrayList = null;
        int i6 = 0;
        while (i6 < size) {
            WatchedLongSparseArray watchedLongSparseArray2 = (WatchedLongSparseArray) sharedLibraries.valueAt(i6);
            if (watchedLongSparseArray2 == null) {
                i2 = i6;
            } else {
                int size2 = watchedLongSparseArray2.size();
                ArrayList arrayList2 = arrayList;
                int i7 = 0;
                while (i7 < size2) {
                    SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray2.valueAt(i7);
                    VersionedPackage declaringPackage = sharedLibraryInfo.getDeclaringPackage();
                    if (Objects.equals(declaringPackage.getPackageName(), str)) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            i3 = size2;
                            i4 = i7;
                            watchedLongSparseArray = watchedLongSparseArray2;
                            i5 = i6;
                            if (getPackageInfoInternal(declaringPackage.getPackageName(), declaringPackage.getLongVersionCode(), j | 67108864, Binder.getCallingUid(), i) != null) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                SharedLibraryInfo sharedLibraryInfo2 = new SharedLibraryInfo(sharedLibraryInfo.getPath(), sharedLibraryInfo.getPackageName(), sharedLibraryInfo.getAllCodePaths(), sharedLibraryInfo.getName(), sharedLibraryInfo.getLongVersion(), sharedLibraryInfo.getType(), sharedLibraryInfo.getDeclaringPackage(), getPackagesUsingSharedLibrary(sharedLibraryInfo, j, callingUid, i), sharedLibraryInfo.getDependencies() == null ? null : new ArrayList(sharedLibraryInfo.getDependencies()), sharedLibraryInfo.isNative());
                                ArrayList arrayList3 = arrayList2 == null ? new ArrayList() : arrayList2;
                                arrayList3.add(sharedLibraryInfo2);
                                arrayList2 = arrayList3;
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } else {
                        i3 = size2;
                        i4 = i7;
                        watchedLongSparseArray = watchedLongSparseArray2;
                        i5 = i6;
                    }
                    i7 = i4 + 1;
                    watchedLongSparseArray2 = watchedLongSparseArray;
                    i6 = i5;
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
    public ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) {
        PackageStateInternal packageStateInternal;
        PackageUserStateInternal userStateOrDefault;
        ApplicationInfo generateApplicationInfo;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "get provider info");
        ParsedProvider provider = this.mComponentResolver.getProvider(componentName);
        if (provider == null || (packageStateInternal = getPackageStateInternal(provider.getPackageName())) == null || packageStateInternal.getPkg() == null || !PackageStateUtils.isEnabledAndMatches(packageStateInternal, provider, updateFlagsForComponent, i) || shouldFilterApplication(packageStateInternal, callingUid, componentName, 4, i) || (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), updateFlagsForComponent, (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)), i, packageStateInternal)) == null) {
            return null;
        }
        return PackageInfoUtils.generateProviderInfo(packageStateInternal.getPkg(), provider, updateFlagsForComponent, userStateOrDefault, generateApplicationInfo, i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    public String[] getSystemSharedLibraryNames() {
        WatchedArrayMap sharedLibraries = getSharedLibraries();
        int size = sharedLibraries.size();
        ArraySet arraySet = null;
        for (int i = 0; i < size; i++) {
            WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) sharedLibraries.valueAt(i);
            if (watchedLongSparseArray != null) {
                int size2 = watchedLongSparseArray.size();
                int i2 = 0;
                while (true) {
                    if (i2 < size2) {
                        SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.valueAt(i2);
                        if (!sharedLibraryInfo.isStatic()) {
                            if (arraySet == null) {
                                arraySet = new ArraySet();
                            }
                            arraySet.add(sharedLibraryInfo.getName());
                        } else {
                            PackageStateInternal packageStateInternal = getPackageStateInternal(sharedLibraryInfo.getPackageName());
                            if (packageStateInternal == null || filterSharedLibPackage(packageStateInternal, Binder.getCallingUid(), UserHandle.getUserId(Binder.getCallingUid()), 67108864L)) {
                                i2++;
                            } else {
                                if (arraySet == null) {
                                    arraySet = new ArraySet();
                                }
                                arraySet.add(sharedLibraryInfo.getName());
                            }
                        }
                    }
                }
            }
        }
        if (arraySet == null) {
            return null;
        }
        String[] strArr = new String[arraySet.size()];
        arraySet.toArray(strArr);
        return strArr;
    }

    @Override // com.android.server.pm.Computer
    public PackageStateInternal getPackageStateForInstalledAndFiltered(String str, int i, int i2) {
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, i, i2)) {
            return null;
        }
        return packageStateInternal;
    }

    @Override // com.android.server.pm.Computer
    public int checkSignatures(String str, String str2, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "checkSignatures");
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        AndroidPackage androidPackage2 = (AndroidPackage) this.mPackages.get(str2);
        PackageStateInternal packageStateInternal = androidPackage == null ? null : getPackageStateInternal(androidPackage.getPackageName());
        PackageStateInternal packageStateInternal2 = androidPackage2 != null ? getPackageStateInternal(androidPackage2.getPackageName()) : null;
        if (androidPackage == null || packageStateInternal == null || androidPackage2 == null || packageStateInternal2 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i) || shouldFilterApplicationIncludingUninstalled(packageStateInternal2, callingUid, i)) {
            return -4;
        }
        return checkSignaturesInternal(androidPackage.getSigningDetails(), androidPackage2.getSigningDetails());
    }

    @Override // com.android.server.pm.Computer
    public int checkUidSignatures(int i, int i2) {
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
    public int checkUidSignaturesForAllUsers(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(i);
        int userId2 = UserHandle.getUserId(i2);
        enforceCrossUserPermission(callingUid, userId, false, false, "checkUidSignaturesForAllUsers");
        enforceCrossUserPermission(callingUid, userId2, false, false, "checkUidSignaturesForAllUsers");
        SigningDetails signingDetailsAndFilterAccess = getSigningDetailsAndFilterAccess(i, callingUid, userId);
        SigningDetails signingDetailsAndFilterAccess2 = getSigningDetailsAndFilterAccess(i2, callingUid, userId2);
        if (signingDetailsAndFilterAccess == null || signingDetailsAndFilterAccess2 == null) {
            return -4;
        }
        return checkSignaturesInternal(signingDetailsAndFilterAccess, signingDetailsAndFilterAccess2);
    }

    public final SigningDetails getSigningDetailsAndFilterAccess(int i, int i2, int i3) {
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase == null) {
            return null;
        }
        if (settingBase instanceof SharedUserSetting) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, i2, i3)) {
                return null;
            }
            return sharedUserSetting.signatures.mSigningDetails;
        }
        if (!(settingBase instanceof PackageSetting)) {
            return null;
        }
        PackageSetting packageSetting = (PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, i2, i3)) {
            return null;
        }
        return packageSetting.getSigningDetails();
    }

    public final int checkSignaturesInternal(SigningDetails signingDetails, SigningDetails signingDetails2) {
        Signature[] signatures;
        Signature[] signatures2;
        if (signingDetails == null) {
            return signingDetails2 == null ? 1 : -1;
        }
        if (signingDetails2 == null) {
            return -2;
        }
        int compareSignatures = PackageManagerServiceUtils.compareSignatures(signingDetails.getSignatures(), signingDetails2.getSignatures());
        if (compareSignatures == 0) {
            return compareSignatures;
        }
        if (!signingDetails.hasPastSigningCertificates() && !signingDetails2.hasPastSigningCertificates()) {
            return compareSignatures;
        }
        if (signingDetails.hasPastSigningCertificates()) {
            signatures = new Signature[]{signingDetails.getPastSigningCertificates()[0]};
        } else {
            signatures = signingDetails.getSignatures();
        }
        if (signingDetails2.hasPastSigningCertificates()) {
            signatures2 = new Signature[]{signingDetails2.getPastSigningCertificates()[0]};
        } else {
            signatures2 = signingDetails2.getSignatures();
        }
        return PackageManagerServiceUtils.compareSignatures(signatures, signatures2);
    }

    @Override // com.android.server.pm.Computer
    public boolean hasSigningCertificate(String str, byte[] bArr, int i) {
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        if (androidPackage == null) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        PackageStateInternal packageStateInternal = getPackageStateInternal(androidPackage.getPackageName());
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
    public boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) {
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

    @Override // com.android.server.pm.Computer
    public List getAllPackages() {
        PackageManagerServiceUtils.enforceSystemOrRootOrShell("getAllPackages is limited to privileged callers");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (canViewInstantApps(callingUid, userId)) {
            return new ArrayList(this.mPackages.keySet());
        }
        String instantAppPackageName = getInstantAppPackageName(callingUid);
        ArrayList arrayList = new ArrayList();
        if (instantAppPackageName != null) {
            for (AndroidPackage androidPackage : this.mPackages.values()) {
                if (androidPackage.isVisibleToInstantApps()) {
                    arrayList.add(androidPackage.getPackageName());
                }
            }
        } else {
            for (AndroidPackage androidPackage2 : this.mPackages.values()) {
                PackageStateInternal packageStateInternal = getPackageStateInternal(androidPackage2.getPackageName());
                if (packageStateInternal == null || !packageStateInternal.getUserStateOrDefault(userId).isInstantApp() || this.mInstantAppRegistry.isInstantAccessGranted(userId, UserHandle.getAppId(callingUid), packageStateInternal.getAppId())) {
                    arrayList.add(androidPackage2.getPackageName());
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    public String getNameForUid(int i) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        if (Process.isIsolatedUid(i) && this.mPermissionManager.getHotwordDetectionServiceProvider() != null && i == this.mPermissionManager.getHotwordDetectionServiceProvider().getUid()) {
            try {
                i = getIsolatedOwner(i);
            } catch (IllegalStateException e) {
                Slog.wtf("PackageManager", "Expected isolated uid " + i + " to have an owner", e);
            }
        }
        int userId = UserHandle.getUserId(callingUid);
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase instanceof SharedUserSetting) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return null;
            }
            return sharedUserSetting.name + XmlUtils.STRING_ARRAY_SEPARATOR + sharedUserSetting.mAppId;
        }
        if (!(settingBase instanceof PackageSetting)) {
            return null;
        }
        PackageSetting packageSetting = (PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return null;
        }
        return packageSetting.getPackageName();
    }

    @Override // com.android.server.pm.Computer
    public String[] getNamesForUids(int[] iArr) {
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
            if (Process.isIsolatedUid(i) && this.mPermissionManager.getHotwordDetectionServiceProvider() != null && i == this.mPermissionManager.getHotwordDetectionServiceProvider().getUid()) {
                try {
                    i = getIsolatedOwner(i);
                } catch (IllegalStateException e) {
                    Slog.wtf("PackageManager", "Expected isolated uid " + i + " to have an owner", e);
                }
            }
            SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
            if (settingBase instanceof SharedUserSetting) {
                SharedUserSetting sharedUserSetting = (SharedUserSetting) settingBase;
                if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                    strArr[length] = null;
                } else {
                    strArr[length] = "shared:" + sharedUserSetting.name;
                }
            } else if (settingBase instanceof PackageSetting) {
                PackageSetting packageSetting = (PackageSetting) settingBase;
                if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
                    strArr[length] = null;
                } else {
                    strArr[length] = packageSetting.getPackageName();
                }
            } else {
                strArr[length] = null;
            }
        }
        return strArr;
    }

    @Override // com.android.server.pm.Computer
    public int getUidForSharedUser(String str) {
        SharedUserSetting sharedUserFromId;
        if (str == null) {
            return -1;
        }
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || (sharedUserFromId = this.mSettings.getSharedUserFromId(str)) == null || shouldFilterApplicationIncludingUninstalled(sharedUserFromId, callingUid, UserHandle.getUserId(callingUid))) {
            return -1;
        }
        return sharedUserFromId.mAppId;
    }

    @Override // com.android.server.pm.Computer
    public int getFlagsForUid(int i) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return 0;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        int userId = UserHandle.getUserId(callingUid);
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase instanceof SharedUserSetting) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return 0;
            }
            return sharedUserSetting.getFlags();
        }
        if (!(settingBase instanceof PackageSetting)) {
            return 0;
        }
        PackageSetting packageSetting = (PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return 0;
        }
        return packageSetting.getFlags();
    }

    @Override // com.android.server.pm.Computer
    public int getPrivateFlagsForUid(int i) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return 0;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        int userId = UserHandle.getUserId(callingUid);
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase instanceof SharedUserSetting) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return 0;
            }
            return sharedUserSetting.getPrivateFlags();
        }
        if (!(settingBase instanceof PackageSetting)) {
            return 0;
        }
        PackageSetting packageSetting = (PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return 0;
        }
        return packageSetting.getPrivateFlags();
    }

    @Override // com.android.server.pm.Computer
    public boolean isUidPrivileged(int i) {
        if (getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return false;
        }
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase instanceof SharedUserSetting) {
            ArraySet packageStates = ((SharedUserSetting) settingBase).getPackageStates();
            int size = packageStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((PackageStateInternal) packageStates.valueAt(i2)).isPrivileged()) {
                    return true;
                }
            }
        } else if (settingBase instanceof PackageSetting) {
            return ((PackageSetting) settingBase).isPrivileged();
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public String[] getAppOpPermissionPackages(String str, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "getAppOpPermissionPackages");
        if (str == null || getInstantAppPackageName(callingUid) != null || !this.mUserManager.exists(i)) {
            return EmptyArray.STRING;
        }
        ArraySet arraySet = new ArraySet(this.mPermissionManager.getAppOpPermissionPackages(str));
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            if (shouldFilterApplicationIncludingUninstalled(this.mSettings.getPackage((String) arraySet.valueAt(size)), callingUid, i)) {
                arraySet.removeAt(size);
            }
        }
        return (String[]) arraySet.toArray(new String[arraySet.size()]);
    }

    @Override // com.android.server.pm.Computer
    public ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return ParceledListSlice.emptyList();
        }
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(Binder.getCallingUid(), i, true, false, "get packages holding permissions");
        boolean z = (4202496 & updateFlagsForPackage) != 0;
        ArrayList arrayList = new ArrayList();
        boolean[] zArr = new boolean[strArr.length];
        for (PackageStateInternal packageStateInternal : getPackageStates().values()) {
            if (packageStateInternal.getPkg() != null || z) {
                addPackageHoldingPermissions(arrayList, packageStateInternal, strArr, zArr, updateFlagsForPackage, i);
            }
        }
        return new ParceledListSlice(arrayList);
    }

    public final void addPackageHoldingPermissions(ArrayList arrayList, PackageStateInternal packageStateInternal, String[] strArr, boolean[] zArr, long j, int i) {
        PackageInfo generatePackageInfo;
        int i2 = 0;
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if (this.mPermissionManager.checkPermission(packageStateInternal.getPackageName(), strArr[i3], i) == 0) {
                zArr[i3] = true;
                i2++;
            } else {
                zArr[i3] = false;
            }
        }
        if (i2 == 0 || (generatePackageInfo = generatePackageInfo(packageStateInternal, j, i)) == null) {
            return;
        }
        if ((j & 4096) == 0) {
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

    @Override // com.android.server.pm.Computer
    public List getInstalledApplications(long j, int i, final int i2) {
        ArrayList arrayList;
        ApplicationInfo generateApplicationInfo;
        ApplicationInfo generateApplicationInfoFromSettings;
        if (getInstantAppPackageName(i2) != null) {
            return Collections.emptyList();
        }
        if (!this.mUserManager.exists(i)) {
            return Collections.emptyList();
        }
        long updateFlagsForApplication = updateFlagsForApplication(j, i);
        boolean z = (4202496 & updateFlagsForApplication) != 0;
        boolean z2 = (1073741824 & updateFlagsForApplication) != 0;
        enforceCrossUserPermission(i2, i, false, false, "get installed application info");
        if (PMRune.PM_NAL_GET_APP_LIST) {
            if (this.mGetAppListHelper.checkCallingGetAppList(this.mContext, i2, this.mGetAppListHelper.isExemptedPackage(i2, this.mSettings.getSettingBase(UserHandle.getAppId(i2))), new Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    String[] lambda$getInstalledApplications$3;
                    lambda$getInstalledApplications$3 = ComputerEngine.this.lambda$getInstalledApplications$3((Integer) obj);
                    return lambda$getInstalledApplications$3;
                }
            })) {
                final int callingPid = Binder.getCallingPid();
                this.mInjector.getHandler().post(new Runnable() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ComputerEngine.this.lambda$getInstalledApplications$4(i2, callingPid);
                    }
                });
                return Collections.emptyList();
            }
        }
        boolean needToFilterSamsungThemePackages = needToFilterSamsungThemePackages(updateFlagsForApplication);
        ArrayMap packageStates = getPackageStates();
        if (z) {
            arrayList = new ArrayList(packageStates.size());
            for (PackageStateInternal packageStateInternal : packageStates.values()) {
                long j2 = packageStateInternal.isSystem() ? 4194304 | updateFlagsForApplication : updateFlagsForApplication;
                if (packageStateInternal.getPkg() != null) {
                    if (z2 || !packageStateInternal.getPkg().isApex()) {
                        if (!filterSharedLibPackage(packageStateInternal, i2, i, updateFlagsForApplication) && !shouldFilterApplication(packageStateInternal, i2, i)) {
                            generateApplicationInfoFromSettings = PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), j2, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
                            if (generateApplicationInfoFromSettings != null) {
                                generateApplicationInfoFromSettings.packageName = resolveExternalPackageName(packageStateInternal.getPkg());
                            }
                        }
                    }
                } else {
                    generateApplicationInfoFromSettings = generateApplicationInfoFromSettings(packageStateInternal.getPackageName(), j2, i2, i);
                }
                if (generateApplicationInfoFromSettings != null && (!needToFilterSamsungThemePackages || !filterSamsungThemePackage(packageStateInternal, generateApplicationInfoFromSettings, i))) {
                    arrayList.add(generateApplicationInfoFromSettings);
                }
            }
        } else {
            arrayList = new ArrayList(this.mPackages.size());
            for (PackageStateInternal packageStateInternal2 : packageStates.values()) {
                AndroidPackageInternal pkg = packageStateInternal2.getPkg();
                if (pkg != null && (z2 || !pkg.isApex())) {
                    if (!filterSharedLibPackage(packageStateInternal2, Binder.getCallingUid(), i, updateFlagsForApplication) && !shouldFilterApplication(packageStateInternal2, i2, i) && (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(pkg, updateFlagsForApplication, packageStateInternal2.getUserStateOrDefault(i), i, packageStateInternal2)) != null) {
                        generateApplicationInfo.packageName = resolveExternalPackageName(pkg);
                        if (!needToFilterSamsungThemePackages || !filterSamsungThemePackage(packageStateInternal2, generateApplicationInfo, i)) {
                            arrayList.add(generateApplicationInfo);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String[] lambda$getInstalledApplications$3(Integer num) {
        return getPackagesForUid(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInstalledApplications$4(int i, int i2) {
        this.mGetAppListHelper.requestGetAppListPermIfNeeded(this.mContext, i, i2);
    }

    @Override // com.android.server.pm.Computer
    public ProviderInfo resolveContentProvider(String str, long j, int i, int i2) {
        UserInfo userInfo;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        ProviderInfo queryProvider = this.mComponentResolver.queryProvider(this, str, updateFlagsForComponent, i);
        boolean z = true;
        if (!((queryProvider == null || i == UserHandle.getUserId(i2)) ? false : ((UriGrantsManagerInternal) this.mInjector.getLocalService(UriGrantsManagerInternal.class)).checkAuthorityGrants(i2, queryProvider, i, true))) {
            if (ContentProvider.isAuthorityRedirectedForCloneProfile(str) && (userInfo = this.mInjector.getUserManagerInternal().getUserInfo(UserHandle.getUserId(i2))) != null && userInfo.isCloneProfile() && userInfo.profileGroupId == i) {
                z = false;
            }
            if (z) {
                enforceCrossUserPermission(i2, i, false, false, "resolveContentProvider");
            }
        }
        if (queryProvider == null) {
            return null;
        }
        PackageStateInternal packageStateInternal = getPackageStateInternal(queryProvider.packageName);
        if (PackageStateUtils.isEnabledAndMatches(packageStateInternal, queryProvider, updateFlagsForComponent, i) && !shouldFilterApplication(packageStateInternal, i2, new ComponentName(queryProvider.packageName, queryProvider.name), 4, i)) {
            return queryProvider;
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public ProviderInfo getGrantImplicitAccessProviderInfo(int i, String str) {
        ApplicationInfo applicationInfo;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(i);
        ProviderInfo resolveContentProvider = resolveContentProvider("com.android.contacts", 0L, UserHandle.getUserId(callingUid), callingUid);
        if (resolveContentProvider == null || (applicationInfo = resolveContentProvider.applicationInfo) == null || !UserHandle.isSameApp(applicationInfo.uid, callingUid)) {
            throw new SecurityException(callingUid + " is not allow to call grantImplicitAccess");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return resolveContentProvider(str, 0L, userId, callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public void querySyncProviders(boolean z, List list, List list2) {
        if (getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int callingUserId = UserHandle.getCallingUserId();
        this.mComponentResolver.querySyncProviders(this, arrayList, arrayList2, z, callingUserId);
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            ProviderInfo providerInfo = (ProviderInfo) arrayList2.get(size);
            if (shouldFilterApplication(this.mSettings.getPackage(providerInfo.packageName), Binder.getCallingUid(), new ComponentName(providerInfo.packageName, providerInfo.name), 4, callingUserId)) {
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

    @Override // com.android.server.pm.Computer
    public ParceledListSlice queryContentProviders(String str, int i, long j, String str2) {
        int callingUserId;
        int callingUid = Binder.getCallingUid();
        if (str != null) {
            callingUserId = UserHandle.getUserId(i);
        } else {
            callingUserId = UserHandle.getCallingUserId();
        }
        int i2 = callingUserId;
        enforceCrossUserPermission(callingUid, i2, false, false, "queryContentProviders");
        if (!this.mUserManager.exists(i2)) {
            return ParceledListSlice.emptyList();
        }
        long updateFlagsForComponent = updateFlagsForComponent(j, i2);
        List queryProviders = this.mComponentResolver.queryProviders(this, str, str2, i, updateFlagsForComponent, i2);
        int size = queryProviders == null ? 0 : queryProviders.size();
        ArrayList arrayList = null;
        for (int i3 = 0; i3 < size; i3++) {
            ProviderInfo providerInfo = (ProviderInfo) queryProviders.get(i3);
            if (PackageStateUtils.isEnabledAndMatches(this.mSettings.getPackage(providerInfo.packageName), providerInfo, updateFlagsForComponent, i2) && !shouldFilterApplication(this.mSettings.getPackage(providerInfo.packageName), callingUid, new ComponentName(providerInfo.packageName, providerInfo.name), 4, i2)) {
                if (arrayList == null) {
                    arrayList = new ArrayList(size - i3);
                }
                arrayList.add(providerInfo);
            }
        }
        if (arrayList != null) {
            arrayList.sort(sProviderInitOrderSorter);
            return new ParceledListSlice(arrayList);
        }
        return ParceledListSlice.emptyList();
    }

    @Override // com.android.server.pm.Computer
    public InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i2, false, false, "getInstrumentationInfoAsUser");
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        String packageName = componentName.getPackageName();
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(packageName);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(packageName);
        if (packageStateInternal == null || androidPackage == null || shouldFilterApplication(packageStateInternal, callingUid, componentName, 0, i2)) {
            return null;
        }
        return PackageInfoUtils.generateInstrumentationInfo((ParsedInstrumentation) this.mInstrumentation.get(componentName), androidPackage, i, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    public ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) {
        int i3;
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i2, false, false, "queryInstrumentationAsUser");
        if (!this.mUserManager.exists(i2)) {
            return ParceledListSlice.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = this.mInstrumentation.size();
        int i4 = 0;
        while (i4 < size) {
            ParsedInstrumentation parsedInstrumentation = (ParsedInstrumentation) this.mInstrumentation.valueAt(i4);
            if (str == null || str.equals(parsedInstrumentation.getTargetPackage())) {
                String packageName = parsedInstrumentation.getPackageName();
                AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(packageName);
                PackageStateInternal packageStateInternal = getPackageStateInternal(packageName);
                if (androidPackage != null && packageStateInternal != null && !shouldFilterApplication(packageStateInternal, callingUid, i2)) {
                    i3 = callingUid;
                    InstrumentationInfo generateInstrumentationInfo = PackageInfoUtils.generateInstrumentationInfo(parsedInstrumentation, androidPackage, i, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
                    if (generateInstrumentationInfo != null) {
                        arrayList.add(generateInstrumentationInfo);
                    }
                    i4++;
                    callingUid = i3;
                }
            }
            i3 = callingUid;
            i4++;
            callingUid = i3;
        }
        return new ParceledListSlice(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public List findSharedNonSystemLibraries(PackageStateInternal packageStateInternal) {
        List findSharedLibraries = SharedLibraryUtils.findSharedLibraries(packageStateInternal);
        if (!findSharedLibraries.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            Iterator it = findSharedLibraries.iterator();
            while (it.hasNext()) {
                PackageStateInternal packageStateInternal2 = getPackageStateInternal(((SharedLibraryInfo) it.next()).getPackageName());
                if (packageStateInternal2 != null && packageStateInternal2.getPkg() != null) {
                    arrayList.add(packageStateInternal2);
                }
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    @Override // com.android.server.pm.Computer
    public boolean getApplicationHiddenSettingAsUser(String str, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USERS", null);
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "getApplicationHidden for user " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
            if (packageStateInternal == null) {
                return true;
            }
            if (shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
                return true;
            }
            return packageStateInternal.getUserStateOrDefault(i).isHidden();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageSuspendedForUser(String str, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "isPackageSuspendedForUser for user " + i);
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
            throw new IllegalArgumentException("Unknown target package: " + str);
        }
        return packageStateInternal.getUserStateOrDefault(i).isSuspended();
    }

    @Override // com.android.server.pm.Computer
    public boolean isSuspendingAnyPackages(String str, int i) {
        Iterator it = getPackageStates().values().iterator();
        while (it.hasNext()) {
            PackageUserStateInternal userStateOrDefault = ((PackageStateInternal) it.next()).getUserStateOrDefault(i);
            if (userStateOrDefault.getSuspendParams() != null && userStateOrDefault.getSuspendParams().containsKey(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public ParceledListSlice getAllIntentFilters(String str) {
        if (TextUtils.isEmpty(str)) {
            return ParceledListSlice.emptyList();
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        AndroidPackageInternal pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        if (pkg == null || ArrayUtils.isEmpty(pkg.getActivities())) {
            return ParceledListSlice.emptyList();
        }
        if (shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, userId)) {
            return ParceledListSlice.emptyList();
        }
        int size = ArrayUtils.size(pkg.getActivities());
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            List intents = ((ParsedActivity) pkg.getActivities().get(i)).getIntents();
            for (int i2 = 0; i2 < intents.size(); i2++) {
                arrayList.add(new IntentFilter(((ParsedIntentInfo) intents.get(i2)).getIntentFilter()));
            }
        }
        return new ParceledListSlice(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public boolean getBlockUninstallForUser(String str, int i) {
        boolean z;
        PackageStateInternal packageStateInternal;
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (asInterface != null) {
            try {
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (!asInterface.getApplicationUninstallationEnabledAsUser(str, i)) {
                Log.d("PackageManager", "This app uninstallation is not allowed");
                z = true;
                packageStateInternal = this.mSettings.getPackage(str);
                int callingUid = Binder.getCallingUid();
                if (packageStateInternal != null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
                    return false;
                }
                if (z) {
                    return true;
                }
                return this.mSettings.getBlockUninstall(i, str);
            }
        }
        z = false;
        packageStateInternal = this.mSettings.getPackage(str);
        int callingUid2 = Binder.getCallingUid();
        if (packageStateInternal != null) {
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public String getInstallerPackageName(String str, int i) {
        int callingUid = Binder.getCallingUid();
        InstallSource installSource = getInstallSource(str, callingUid, i);
        if (installSource == null) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        String str2 = installSource.mInstallerPackageName;
        if (str2 == null) {
            return str2;
        }
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(str2);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, UserHandle.getUserId(callingUid))) {
            return null;
        }
        return str2;
    }

    public final InstallSource getInstallSource(String str, int i, int i2) {
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (isApexPackage(str)) {
            return InstallSource.EMPTY;
        }
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, i, i2)) {
            return null;
        }
        return packageStateInternal.getInstallSource();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00be A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0078  */
    @Override // com.android.server.pm.Computer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public InstallSourceInfo getInstallSourceInfo(String str, int i) {
        String str2;
        String str3;
        String str4;
        String str5;
        PackageSignatures packageSignatures;
        PackageStateInternal packageStateInternal;
        PackageStateInternal packageStateInternal2;
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "getInstallSourceInfo");
        InstallSource installSource = getInstallSource(str, callingUid, i);
        SigningInfo signingInfo = null;
        if (installSource == null) {
            return null;
        }
        String str6 = installSource.mInstallerPackageName;
        String str7 = (str6 == null || !((packageStateInternal2 = this.mSettings.getPackage(str6)) == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal2, callingUid, i))) ? str6 : null;
        String str8 = installSource.mUpdateOwnerPackageName;
        if (str8 != null) {
            PackageStateInternal packageStateInternal3 = this.mSettings.getPackage(str8);
            boolean z = callingUid == 1000 || isCallerSameApp(str8, callingUid);
            if (packageStateInternal3 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal3, callingUid, i) || (!z && isCallerFromManagedUserOrProfile(i))) {
                str2 = null;
                if (!installSource.mIsInitiatingPackageUninstalled) {
                    str3 = ((getInstantAppPackageName(callingUid) != null) || !isCallerSameApp(str, callingUid)) ? null : installSource.mInitiatingPackageName;
                } else {
                    if (Objects.equals(installSource.mInitiatingPackageName, installSource.mInstallerPackageName)) {
                        str4 = str7;
                    } else {
                        str3 = installSource.mInitiatingPackageName;
                        PackageStateInternal packageStateInternal4 = this.mSettings.getPackage(str3);
                        if (packageStateInternal4 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal4, callingUid, i)) {
                            str4 = null;
                        }
                    }
                    str5 = installSource.mOriginatingPackageName;
                    if (str5 != null && ((packageStateInternal = this.mSettings.getPackage(str5)) == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i))) {
                        str5 = null;
                    }
                    String str9 = (str5 != null || this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0) ? str5 : null;
                    packageSignatures = installSource.mInitiatingPackageSignatures;
                    if (str4 != null && packageSignatures != null && packageSignatures.mSigningDetails != SigningDetails.UNKNOWN) {
                        signingInfo = new SigningInfo(packageSignatures.mSigningDetails);
                    }
                    return new InstallSourceInfo(str4, signingInfo, str9, str7, str2, installSource.mPackageSource);
                }
                str4 = str3;
                str5 = installSource.mOriginatingPackageName;
                if (str5 != null) {
                    str5 = null;
                }
                if (str5 != null) {
                }
                packageSignatures = installSource.mInitiatingPackageSignatures;
                if (str4 != null) {
                    signingInfo = new SigningInfo(packageSignatures.mSigningDetails);
                }
                return new InstallSourceInfo(str4, signingInfo, str9, str7, str2, installSource.mPackageSource);
            }
        }
        str2 = str8;
        if (!installSource.mIsInitiatingPackageUninstalled) {
        }
        str4 = str3;
        str5 = installSource.mOriginatingPackageName;
        if (str5 != null) {
        }
        if (str5 != null) {
        }
        packageSignatures = installSource.mInitiatingPackageSignatures;
        if (str4 != null) {
        }
        return new InstallSourceInfo(str4, signingInfo, str9, str7, str2, installSource.mPackageSource);
    }

    @Override // com.android.server.pm.Computer
    public int getApplicationEnabledSetting(String str, int i) {
        if (!this.mUserManager.exists(i)) {
            return 2;
        }
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "get enabled");
        try {
            if (shouldFilterApplicationIncludingUninstalled(this.mSettings.getPackage(str), callingUid, i)) {
                throw new PackageManager.NameNotFoundException(str);
            }
            return this.mSettings.getApplicationEnabledSetting(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
    }

    @Override // com.android.server.pm.Computer
    public int getComponentEnabledSetting(ComponentName componentName, int i, int i2) {
        enforceCrossUserPermission(i, i2, false, false, "getComponentEnabled");
        return getComponentEnabledSettingInternal(componentName, i, i2);
    }

    @Override // com.android.server.pm.Computer
    public int getComponentEnabledSettingInternal(ComponentName componentName, int i, int i2) {
        if (componentName == null) {
            return 0;
        }
        if (!this.mUserManager.exists(i2)) {
            return 2;
        }
        try {
            if (shouldFilterApplication(this.mSettings.getPackage(componentName.getPackageName()), i, componentName, 0, i2, true)) {
                throw new PackageManager.NameNotFoundException(componentName.getPackageName());
            }
            return this.mSettings.getComponentEnabledSetting(componentName, i2);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException("Unknown component: " + componentName);
        }
    }

    @Override // com.android.server.pm.Computer
    public boolean isComponentEffectivelyEnabled(ComponentInfo componentInfo, UserHandle userHandle) {
        try {
            String str = componentInfo.packageName;
            int identifier = userHandle.getIdentifier();
            int applicationEnabledSetting = this.mSettings.getApplicationEnabledSetting(str, identifier);
            if (applicationEnabledSetting == 0) {
                if (!componentInfo.applicationInfo.enabled) {
                    return false;
                }
            } else if (applicationEnabledSetting != 1) {
                return false;
            }
            int componentEnabledSetting = this.mSettings.getComponentEnabledSetting(componentInfo.getComponentName(), identifier);
            if (componentEnabledSetting == 0) {
                return componentInfo.isEnabled();
            }
            return componentEnabledSetting == 1;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.server.pm.Computer
    public boolean isApplicationEffectivelyEnabled(String str, UserHandle userHandle) {
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
    public KeySet getKeySetByAlias(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: " + str);
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        return new KeySet(this.mSettings.getKeySetManagerService().getKeySetByAliasAndPackageNameLPr(str, str2));
    }

    @Override // com.android.server.pm.Computer
    public KeySet getSigningKeySet(String str) {
        if (str == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: " + str + ", uid:" + callingUid);
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        if (androidPackage.getUid() != callingUid && 1000 != callingUid) {
            throw new SecurityException("May not access signing KeySet of other apps.");
        }
        return new KeySet(this.mSettings.getKeySetManagerService().getSigningKeySetByPackageNameLPr(str));
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageSignedByKeySet(String str, KeySet keySet) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || str == null || keySet == null) {
            return false;
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        int userId = UserHandle.getUserId(callingUid);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: " + str);
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        IBinder token = keySet.getToken();
        if (token instanceof KeySetHandle) {
            return this.mSettings.getKeySetManagerService().packageIsSignedByLPr(str, (KeySetHandle) token);
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) {
        int callingUid = Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || str == null || keySet == null) {
            return false;
        }
        AndroidPackage androidPackage = (AndroidPackage) this.mPackages.get(str);
        int userId = UserHandle.getUserId(callingUid);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            Slog.w("PackageManager", "KeySet requested for unknown package: " + str);
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        IBinder token = keySet.getToken();
        if (token instanceof KeySetHandle) {
            return this.mSettings.getKeySetManagerService().packageIsSignedByExactlyLPr(str, (KeySetHandle) token);
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public SparseArray getVisibilityAllowLists(String str, int[] iArr) {
        PackageStateInternal packageStateInternal = getPackageStateInternal(str, 1000);
        if (packageStateInternal == null) {
            return null;
        }
        return this.mAppsFilter.getVisibilityAllowList(this, packageStateInternal, iArr, getPackageStates());
    }

    @Override // com.android.server.pm.Computer
    public int[] getVisibilityAllowList(String str, int i) {
        SparseArray visibilityAllowLists = getVisibilityAllowLists(str, new int[]{i});
        if (visibilityAllowLists != null) {
            return (int[]) visibilityAllowLists.get(i);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public boolean canQueryPackage(int i, String str) {
        if (i == 0 || str == null) {
            return true;
        }
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase == null) {
            return false;
        }
        int appId = UserHandle.getAppId(getPackageUid(str, 0L, UserHandle.getUserId(i)));
        if (appId != -1) {
            if (this.mSettings.getSettingBase(appId) instanceof PackageSetting) {
                return !shouldFilterApplication((PackageSetting) r8, i, r3);
            }
            return !shouldFilterApplication((SharedUserSetting) r8, i, r3);
        }
        if (settingBase instanceof PackageSetting) {
            AndroidPackageInternal pkg = ((PackageSetting) settingBase).getPkg();
            return pkg != null && this.mAppsFilter.canQueryPackage(pkg, str);
        }
        ArraySet packageStates = ((SharedUserSetting) settingBase).getPackageStates();
        for (int size = packageStates.size() - 1; size >= 0; size--) {
            AndroidPackageInternal pkg2 = ((PackageStateInternal) packageStates.valueAt(size)).getPkg();
            if (pkg2 != null && this.mAppsFilter.canQueryPackage(pkg2, str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public int getPackageUid(String str, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return -1;
        }
        int callingUid = Binder.getCallingUid();
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "getPackageUid");
        return getPackageUidInternal(str, updateFlagsForPackage, i, callingUid);
    }

    @Override // com.android.server.pm.Computer
    public boolean canAccessComponent(int i, ComponentName componentName, int i2) {
        PackageStateInternal packageStateInternal = getPackageStateInternal(componentName.getPackageName());
        return (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, componentName, 0, i2, true)) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public boolean isCallerInstallerOfRecord(AndroidPackage androidPackage, int i) {
        PackageStateInternal packageStateInternal;
        PackageStateInternal packageStateInternal2;
        return (androidPackage == null || (packageStateInternal = getPackageStateInternal(androidPackage.getPackageName())) == null || (packageStateInternal2 = getPackageStateInternal(packageStateInternal.getInstallSource().mInstallerPackageName)) == null || !UserHandle.isSameApp(packageStateInternal2.getAppId(), i)) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public int getInstallReason(String str, int i) {
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "get install reason");
        PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
            return 0;
        }
        return packageStateInternal.getUserStateOrDefault(i).getInstallReason();
    }

    @Override // com.android.server.pm.Computer
    public boolean[] canPackageQuery(String str, String[] strArr, int i) {
        int length = strArr.length;
        boolean[] zArr = new boolean[length];
        if (!this.mUserManager.exists(i)) {
            return zArr;
        }
        int callingUid = Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "can package query");
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        PackageStateInternal[] packageStateInternalArr = new PackageStateInternal[length];
        boolean z = packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i);
        for (int i2 = 0; !z && i2 < length; i2++) {
            PackageStateInternal packageStateInternal2 = getPackageStateInternal(strArr[i2]);
            packageStateInternalArr[i2] = packageStateInternal2;
            z = packageStateInternal2 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal2, callingUid, i);
        }
        if (z) {
            throw new ParcelableException(new PackageManager.NameNotFoundException("Package(s) " + str + " and/or " + Arrays.toString(strArr) + " not found."));
        }
        int uid = UserHandle.getUid(i, packageStateInternal.getAppId());
        for (int i3 = 0; i3 < length; i3++) {
            zArr[i3] = !shouldFilterApplication(packageStateInternalArr[i3], uid, i);
        }
        return zArr;
    }

    @Override // com.android.server.pm.Computer
    public boolean canForwardTo(Intent intent, String str, int i, int i2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        if (this.mCrossProfileIntentResolverEngine.canReachTo(this, intent, str, i, i2)) {
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
        return getCrossProfileDomainPreferredLpr(intent, str, updateFlagsForResolve(0L, i3, callingUid, false, isImplicitImageCaptureIntentAndNotSetByDpc(intent, i3, str, 0L)) | 65536, i, profileParent.id) != null;
    }

    @Override // com.android.server.pm.Computer
    public List getPersistentApplications(boolean z, int i) {
        PackageStateInternal packageStateInternal;
        ApplicationInfo generateApplicationInfo;
        ArrayList arrayList = new ArrayList();
        int size = this.mPackages.size();
        int callingUserId = UserHandle.getCallingUserId();
        for (int i2 = 0; i2 < size; i2++) {
            AndroidPackage androidPackage = (AndroidPackage) this.mPackages.valueAt(i2);
            PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(androidPackage.getPackageName());
            boolean z2 = ((262144 & i) == 0 || androidPackage.isDirectBootAware()) ? false : true;
            boolean z3 = (524288 & i) != 0 && androidPackage.isDirectBootAware();
            if (androidPackage.isPersistent() && ((!z || packageStateInternal2.isSystem()) && ((z2 || z3) && (packageStateInternal = this.mSettings.getPackage(androidPackage.getPackageName())) != null && (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(androidPackage, i, packageStateInternal.getUserStateOrDefault(callingUserId), callingUserId, packageStateInternal)) != null))) {
                arrayList.add(generateApplicationInfo);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    public String[] getSharedUserPackagesForPackage(String str, int i) {
        if (this.mSettings.getPackage(str) == null || this.mSettings.getSharedUserFromPackageName(str) == null) {
            return EmptyArray.STRING;
        }
        ArraySet packageStates = this.mSettings.getSharedUserFromPackageName(str).getPackageStates();
        int size = packageStates.size();
        String[] strArr = new String[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i3);
            if (packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                strArr[i2] = packageStateInternal.getPackageName();
                i2++;
            }
        }
        String[] strArr2 = (String[]) ArrayUtils.trimToSize(strArr, i2);
        return strArr2 != null ? strArr2 : EmptyArray.STRING;
    }

    @Override // com.android.server.pm.Computer
    public Set getUnusedPackages(long j) {
        int i;
        ArraySet arraySet = new ArraySet();
        long currentTimeMillis = System.currentTimeMillis();
        ArrayMap packages = this.mSettings.getPackages();
        int i2 = 0;
        while (i2 < packages.size()) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packages.valueAt(i2);
            if (packageStateInternal.getPkg() == null) {
                i = i2;
            } else {
                i = i2;
                if (PackageManagerServiceUtils.isUnusedSinceTimeInMillis(PackageStateUtils.getEarliestFirstInstallTime(packageStateInternal.getUserStates()), currentTimeMillis, j, this.mDexManager.getPackageUseInfoOrDefault(packageStateInternal.getPackageName()), packageStateInternal.getTransientState().getLatestPackageUseTimeInMills(), packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills())) {
                    arraySet.add(packageStateInternal.getPackageName());
                }
            }
            i2 = i + 1;
        }
        return arraySet;
    }

    public final Map getRecentlyUsedPackages(long j) {
        ArrayMap arrayMap = new ArrayMap();
        long currentTimeMillis = System.currentTimeMillis();
        ArrayMap packages = this.mSettings.getPackages();
        for (int i = 0; i < packages.size(); i++) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packages.valueAt(i);
            if (packageStateInternal.getPkg() != null) {
                long latestForegroundPackageUseTimeInMills = packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills();
                if (PackageManagerServiceUtils.isActiveInForegroundWithinThresholdTime(currentTimeMillis, j, latestForegroundPackageUseTimeInMills)) {
                    arrayMap.put(packageStateInternal.getPackageName(), Long.valueOf(latestForegroundPackageUseTimeInMills));
                }
            }
        }
        return arrayMap;
    }

    @Override // com.android.server.pm.Computer
    public List getTopPriorityPackages(long j, int i) {
        ArrayList arrayList = new ArrayList();
        PriorityQueue priorityQueue = new PriorityQueue(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map recentlyUsedPackages = getRecentlyUsedPackages(j);
        for (String str : recentlyUsedPackages.keySet()) {
            priorityQueue.offer(new AbstractMap.SimpleEntry(str, (Long) recentlyUsedPackages.get(str)));
        }
        ArrayList arrayList2 = new ArrayList(Arrays.asList("com.google.android.gms"));
        while (!priorityQueue.isEmpty() && (i == -1 || i > 0)) {
            String str2 = (String) ((Map.Entry) priorityQueue.poll()).getKey();
            if (arrayList2.contains(str2)) {
                Log.d("PackageManager", "skipped because it's an exception package : " + str2);
            } else {
                arrayList.add(str2);
                if (i > 0) {
                    i--;
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    public CharSequence getHarmfulAppWarning(String str, int i) {
        int callingUid = Binder.getCallingUid();
        int appId = UserHandle.getAppId(callingUid);
        enforceCrossUserPermission(callingUid, i, true, true, "getHarmfulAppInfo");
        if (!PackageManagerServiceUtils.isSystemOrRoot(appId) && checkUidPermission("android.permission.SET_HARMFUL_APP_WARNINGS", callingUid) != 0) {
            throw new SecurityException("Caller must have the android.permission.SET_HARMFUL_APP_WARNINGS permission.");
        }
        PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        return packageStateInternal.getUserStateOrDefault(i).getHarmfulAppWarning();
    }

    @Override // com.android.server.pm.Computer
    public String[] filterOnlySystemPackages(String... strArr) {
        if (strArr == null) {
            return (String[]) ArrayUtils.emptyArray(String.class);
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            if (str != null) {
                PackageStateInternal packageStateInternal = getPackageStateInternal(str);
                if (packageStateInternal == null || packageStateInternal.getAndroidPackage() == null) {
                    Log.w("PackageManager", "Could not find package " + str);
                } else if (packageStateInternal.isSystem()) {
                    arrayList.add(str);
                } else {
                    Log.w("PackageManager", str + " is not system");
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    @Override // com.android.server.pm.Computer
    public List getPackagesForAppId(int i) {
        SettingBase settingBase = this.mSettings.getSettingBase(i);
        if (settingBase instanceof SharedUserSetting) {
            return ((SharedUserSetting) settingBase).getPackages();
        }
        if (settingBase instanceof PackageSetting) {
            return List.of(((PackageSetting) settingBase).getPkg());
        }
        return Collections.emptyList();
    }

    @Override // com.android.server.pm.Computer
    public int getUidTargetSdkVersion(int i) {
        int targetSdkVersion;
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        int i2 = 10000;
        if (settingBase instanceof SharedUserSetting) {
            ArraySet packageStates = ((SharedUserSetting) settingBase).getPackageStates();
            int size = packageStates.size();
            for (int i3 = 0; i3 < size; i3++) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i3);
                if (packageStateInternal.getPkg() != null && (targetSdkVersion = packageStateInternal.getPkg().getTargetSdkVersion()) < i2) {
                    i2 = targetSdkVersion;
                }
            }
            return i2;
        }
        if (settingBase instanceof PackageSetting) {
            PackageSetting packageSetting = (PackageSetting) settingBase;
            if (packageSetting.getPkg() != null) {
                return packageSetting.getPkg().getTargetSdkVersion();
            }
        }
        return 10000;
    }

    @Override // com.android.server.pm.Computer
    public ArrayMap getProcessesForUid(int i) {
        AndroidPackageInternal pkg;
        if (Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        SettingBase settingBase = this.mSettings.getSettingBase(UserHandle.getAppId(i));
        if (settingBase instanceof SharedUserSetting) {
            return PackageInfoUtils.generateProcessInfo(((SharedUserSetting) settingBase).processes, 0L);
        }
        if (!(settingBase instanceof PackageSetting) || (pkg = ((PackageSetting) settingBase).getPkg()) == null) {
            return null;
        }
        return PackageInfoUtils.generateProcessInfo(pkg.getProcesses(), 0L);
    }

    @Override // com.android.server.pm.Computer
    public boolean getBlockUninstall(int i, String str) {
        return this.mSettings.getBlockUninstall(i, str);
    }

    @Override // com.android.server.pm.Computer
    public Pair getPackageOrSharedUser(int i) {
        Watchable settingBase = this.mSettings.getSettingBase(i);
        if (settingBase instanceof SharedUserSetting) {
            return Pair.create(null, (SharedUserApi) settingBase);
        }
        if (settingBase instanceof PackageSetting) {
            return Pair.create((PackageStateInternal) settingBase, null);
        }
        return null;
    }

    public final int getBaseSdkSandboxUid() {
        return getPackage(this.mService.getSdkSandboxPackageName()).getUid();
    }

    @Override // com.android.server.pm.Computer
    public SharedUserApi getSharedUser(int i) {
        return this.mSettings.getSharedUserFromAppId(i);
    }

    @Override // com.android.server.pm.Computer
    public ArraySet getSharedUserPackages(int i) {
        return this.mSettings.getSharedUserPackages(i);
    }

    @Override // com.android.server.pm.Computer
    public ComponentResolverApi getComponentResolver() {
        return this.mComponentResolver;
    }

    @Override // com.android.server.pm.Computer
    public PackageStateInternal getDisabledSystemPackage(String str) {
        return this.mSettings.getDisabledSystemPkg(str);
    }

    @Override // com.android.server.pm.Computer
    public ResolveInfo getInstantAppInstallerInfo() {
        return this.mInstantAppInstallerInfo;
    }

    @Override // com.android.server.pm.Computer
    public WatchedArrayMap getFrozenPackages() {
        return this.mFrozenPackages;
    }

    @Override // com.android.server.pm.Computer
    public void checkPackageFrozen(String str) {
        if (this.mFrozenPackages.containsKey(str)) {
            return;
        }
        Slog.wtf("PackageManager", "Expected " + str + " to be frozen!", new Throwable());
    }

    @Override // com.android.server.pm.Computer
    public ComponentName getInstantAppInstallerComponent() {
        ActivityInfo activityInfo = this.mLocalInstantAppInstallerActivity;
        if (activityInfo == null) {
            return null;
        }
        return activityInfo.getComponentName();
    }

    @Override // com.android.server.pm.Computer
    public void dumpPermissions(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState) {
        this.mSettings.dumpPermissions(printWriter, str, arraySet, dumpState);
    }

    @Override // com.android.server.pm.Computer
    public void dumpPackages(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z) {
        this.mSettings.dumpPackages(printWriter, str, arraySet, dumpState, z);
    }

    @Override // com.android.server.pm.Computer
    public void dumpKeySet(PrintWriter printWriter, String str, DumpState dumpState) {
        this.mSettings.dumpKeySet(printWriter, str, dumpState);
    }

    @Override // com.android.server.pm.Computer
    public void dumpSharedUsers(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z) {
        this.mSettings.dumpSharedUsers(printWriter, str, arraySet, dumpState, z);
    }

    @Override // com.android.server.pm.Computer
    public void dumpSharedUsersProto(ProtoOutputStream protoOutputStream) {
        this.mSettings.dumpSharedUsersProto(protoOutputStream);
    }

    @Override // com.android.server.pm.Computer
    public void dumpPackagesProto(ProtoOutputStream protoOutputStream) {
        this.mSettings.dumpPackagesProto(protoOutputStream);
    }

    @Override // com.android.server.pm.Computer
    public void dumpSharedLibrariesProto(ProtoOutputStream protoOutputStream) {
        this.mSharedLibraries.dumpProto(protoOutputStream);
    }

    @Override // com.android.server.pm.Computer
    public List getVolumePackages(String str) {
        return this.mSettings.getVolumePackages(str);
    }

    @Override // com.android.server.pm.Computer
    public UserInfo[] getUserInfos() {
        return this.mInjector.getUserManagerInternal().getUserInfos();
    }
}
