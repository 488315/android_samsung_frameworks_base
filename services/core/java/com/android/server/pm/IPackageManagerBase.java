package com.android.server.pm;

import android.R;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstallSourceInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.KeySet;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.VersionedPackage;
import android.content.pm.dex.IArtManager;
import android.os.Binder;
import android.os.Trace;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.util.CollectionUtils;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1;
import com.samsung.android.knox.SemPersonaManager;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public abstract class IPackageManagerBase extends IPackageManager.Stub {
    public final Context mContext;
    public final DexOptHelper mDexOptHelper;
    public final DomainVerificationConnection mDomainVerificationConnection;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final PackageInstallerService mInstallerService;
    public final ComponentName mInstantAppResolverSettingsComponent;
    public final ModuleInfoProvider mModuleInfoProvider;
    public final PackageProperty mPackageProperty;
    public final PreferredActivityHelper mPreferredActivityHelper;
    public final String mRequiredSupplementalProcessPackage;
    public final ComponentName mResolveComponentName;
    public final ResolveIntentHelper mResolveIntentHelper;
    public final PackageManagerService mService;
    public final String mServicesExtensionPackageName;
    public final String mSharedSystemSharedLibraryPackageName;

    public final boolean hasSystemUidErrors() {
        return false;
    }

    public IPackageManagerBase(PackageManagerService packageManagerService, Context context, DexOptHelper dexOptHelper, ModuleInfoProvider moduleInfoProvider, PreferredActivityHelper preferredActivityHelper, ResolveIntentHelper resolveIntentHelper, DomainVerificationManagerInternal domainVerificationManagerInternal, DomainVerificationConnection domainVerificationConnection, PackageInstallerService packageInstallerService, PackageProperty packageProperty, ComponentName componentName, ComponentName componentName2, String str, String str2, String str3) {
        this.mService = packageManagerService;
        this.mContext = context;
        this.mDexOptHelper = dexOptHelper;
        this.mModuleInfoProvider = moduleInfoProvider;
        this.mPreferredActivityHelper = preferredActivityHelper;
        this.mResolveIntentHelper = resolveIntentHelper;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mDomainVerificationConnection = domainVerificationConnection;
        this.mInstallerService = packageInstallerService;
        this.mPackageProperty = packageProperty;
        this.mResolveComponentName = componentName;
        this.mInstantAppResolverSettingsComponent = componentName2;
        this.mRequiredSupplementalProcessPackage = str;
        this.mServicesExtensionPackageName = str2;
        this.mSharedSystemSharedLibraryPackageName = str3;
    }

    public Computer snapshot() {
        return this.mService.snapshotComputer();
    }

    public final boolean activitySupportsIntentAsUser(ComponentName componentName, Intent intent, String str, int i) {
        return snapshot().activitySupportsIntentAsUser(this.mResolveComponentName, componentName, intent, str, i);
    }

    public final void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) {
        this.mService.addCrossProfileIntentFilter(snapshot(), new WatchedIntentFilter(intentFilter), str, i, i2, i3);
    }

    public final boolean addPermission(PermissionInfo permissionInfo) {
        return ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).addPermission(permissionInfo, false);
    }

    public final boolean addPermissionAsync(PermissionInfo permissionInfo) {
        return ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).addPermission(permissionInfo, true);
    }

    public final void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) {
        this.mPreferredActivityHelper.addPersistentPreferredActivity(new WatchedIntentFilter(intentFilter), componentName, i);
    }

    public final void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2, boolean z) {
        this.mPreferredActivityHelper.addPreferredActivity(snapshot(), new WatchedIntentFilter(intentFilter), i, componentNameArr, componentName, true, i2, "Adding preferred", z);
    }

    public final boolean canForwardTo(Intent intent, String str, int i, int i2) {
        return snapshot().canForwardTo(intent, str, i, i2);
    }

    public final boolean canRequestPackageInstalls(String str, int i) {
        return snapshot().canRequestPackageInstalls(str, Binder.getCallingUid(), i, true);
    }

    public final String[] canonicalToCurrentPackageNames(String[] strArr) {
        return snapshot().canonicalToCurrentPackageNames(strArr);
    }

    public final int checkPermission(String str, String str2, int i) {
        return this.mService.checkPermission(str, str2, i);
    }

    public final int checkSignatures(String str, String str2, int i) {
        return snapshot().checkSignatures(str, str2, i);
    }

    public final int checkUidPermission(String str, int i) {
        return snapshot().checkUidPermission(str, i);
    }

    public final int checkUidSignatures(int i, int i2) {
        return snapshot().checkUidSignatures(i, i2);
    }

    public final void clearPackagePersistentPreferredActivities(String str, int i) {
        this.mPreferredActivityHelper.clearPackagePersistentPreferredActivities(str, i);
    }

    public final void clearPersistentPreferredActivity(IntentFilter intentFilter, int i) {
        this.mPreferredActivityHelper.clearPersistentPreferredActivity(intentFilter, i);
    }

    public final void clearPackagePreferredActivities(String str) {
        this.mPreferredActivityHelper.clearPackagePreferredActivities(snapshot(), str);
    }

    public final String[] currentToCanonicalPackageNames(String[] strArr) {
        return snapshot().currentToCanonicalPackageNames(strArr);
    }

    public final void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        this.mService.deleteExistingPackageAsUser(versionedPackage, iPackageDeleteObserver2, i);
    }

    public final void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) {
        deletePackageVersioned(new VersionedPackage(str, i), new PackageManager.LegacyPackageDeleteObserver(iPackageDeleteObserver).getBinder(), i2, i3);
    }

    public final void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) {
        this.mService.deletePackageVersioned(versionedPackage, iPackageDeleteObserver2, i, i2);
    }

    public final ResolveInfo findPersistentPreferredActivity(Intent intent, int i) {
        return this.mPreferredActivityHelper.findPersistentPreferredActivity(snapshot(), intent, i);
    }

    public final ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) {
        return snapshot().getActivityInfo(componentName, j, i);
    }

    public final ParceledListSlice getAllIntentFilters(String str) {
        return snapshot().getAllIntentFilters(str);
    }

    public final List getAllPackages() {
        return snapshot().getAllPackages();
    }

    public final String[] getAppOpPermissionPackages(String str, int i) {
        return snapshot().getAppOpPermissionPackages(str, i);
    }

    public final String getAppPredictionServicePackageName() {
        return this.mService.mAppPredictionServicePackage;
    }

    public final int getApplicationEnabledSetting(String str, int i) {
        return snapshot().getApplicationEnabledSetting(str, i);
    }

    public final boolean getApplicationHiddenSettingAsUser(String str, int i) {
        return snapshot().getApplicationHiddenSettingAsUser(str, i);
    }

    public final ApplicationInfo getApplicationInfo(String str, long j, int i) {
        return snapshot().getApplicationInfo(str, j, i);
    }

    public final IArtManager getArtManager() {
        return this.mService.mArtManagerService;
    }

    public final String getAttentionServicePackageName() {
        return this.mService.ensureSystemPackageName(snapshot(), this.mService.getPackageFromComponentString(R.string.data_usage_mobile_limit_snoozed_title));
    }

    public final boolean getBlockUninstallForUser(String str, int i) {
        return snapshot().getBlockUninstallForUser(str, i);
    }

    public final int getComponentEnabledSetting(ComponentName componentName, int i) {
        return snapshot().getComponentEnabledSetting(componentName, Binder.getCallingUid(), i);
    }

    public final ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) {
        return snapshot().getDeclaredSharedLibraries(str, j, i);
    }

    public final byte[] getDefaultAppsBackup(int i) {
        return this.mPreferredActivityHelper.getDefaultAppsBackup(i);
    }

    public final String getDefaultTextClassifierPackageName() {
        return this.mService.mDefaultTextClassifierPackage;
    }

    public final int getFlagsForUid(int i) {
        return snapshot().getFlagsForUid(i);
    }

    public final CharSequence getHarmfulAppWarning(String str, int i) {
        return snapshot().getHarmfulAppWarning(str, i);
    }

    public final ComponentName getHomeActivities(List list) {
        Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return null;
        }
        return snapshot.getHomeActivitiesAsUser(list, UserHandle.getCallingUserId());
    }

    public final String getIncidentReportApproverPackageName() {
        return this.mService.mIncidentReportApproverPackage;
    }

    public final int getInstallLocation() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "default_install_location", 0);
    }

    public final int getInstallReason(String str, int i) {
        return snapshot().getInstallReason(str, i);
    }

    public final InstallSourceInfo getInstallSourceInfo(String str, int i) {
        return snapshot().getInstallSourceInfo(str, i);
    }

    public final ParceledListSlice getInstalledApplications(long j, int i) {
        return new ParceledListSlice(snapshot().getInstalledApplications(j, i, Binder.getCallingUid()));
    }

    public final List getInstalledModules(int i) {
        return this.mModuleInfoProvider.getInstalledModules(i);
    }

    public final ParceledListSlice getInstalledPackages(long j, int i) {
        return snapshot().getInstalledPackages(j, i);
    }

    public final String getInstallerPackageName(String str) {
        return snapshot().getInstallerPackageName(str, UserHandle.getCallingUserId());
    }

    public final ComponentName getInstantAppInstallerComponent() {
        Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return null;
        }
        return snapshot.getInstantAppInstallerComponent();
    }

    public final ComponentName getInstantAppResolverComponent() {
        Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return null;
        }
        return this.mService.getInstantAppResolver(snapshot);
    }

    public final ComponentName getInstantAppResolverSettingsComponent() {
        return this.mInstantAppResolverSettingsComponent;
    }

    public final InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) {
        return snapshot().getInstrumentationInfoAsUser(componentName, i, i2);
    }

    public final ParceledListSlice getIntentFilterVerifications(String str) {
        return ParceledListSlice.emptyList();
    }

    public final int getIntentVerificationStatus(String str, int i) {
        return this.mDomainVerificationManager.getLegacyState(str, i);
    }

    public final KeySet getKeySetByAlias(String str, String str2) {
        return snapshot().getKeySetByAlias(str, str2);
    }

    public final ModuleInfo getModuleInfo(String str, int i) {
        return this.mModuleInfoProvider.getModuleInfo(str, i);
    }

    public final String getNameForUid(int i) {
        return snapshot().getNameForUid(i);
    }

    public final String[] getNamesForUids(int[] iArr) {
        return snapshot().getNamesForUids(iArr);
    }

    public final int[] getPackageGids(String str, long j, int i) {
        return snapshot().getPackageGids(str, j, i);
    }

    public final PackageInfo getPackageInfo(String str, long j, int i) {
        return snapshot().getPackageInfo(str, j, i);
    }

    public final PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long j, int i) {
        return snapshot().getPackageInfoInternal(versionedPackage.getPackageName(), versionedPackage.getLongVersionCode(), j, Binder.getCallingUid(), i);
    }

    public final IPackageInstaller getPackageInstaller() {
        if (PackageManagerServiceUtils.isSystemOrRoot()) {
            return this.mInstallerService;
        }
        if (snapshot().getInstantAppPackageName(Binder.getCallingUid()) != null) {
            Log.w("PackageManager", "Returning null PackageInstaller for InstantApps");
            return null;
        }
        return this.mInstallerService;
    }

    public final void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) {
        throw new UnsupportedOperationException("Shame on you for calling the hidden API getPackageSizeInfo(). Shame!");
    }

    public final int getPackageUid(String str, long j, int i) {
        return snapshot().getPackageUid(str, j, i);
    }

    public final String[] getPackagesForUid(int i) {
        snapshot().enforceCrossUserOrProfilePermission(Binder.getCallingUid(), UserHandle.getUserId(i), false, false, "getPackagesForUid");
        return snapshot().getPackagesForUid(i);
    }

    public final ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) {
        return snapshot().getPackagesHoldingPermissions(strArr, j, i);
    }

    public final PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        return this.mService.getPermissionGroupInfo(str, i);
    }

    public final ParceledListSlice getPersistentApplications(int i) {
        Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return ParceledListSlice.emptyList();
        }
        return new ParceledListSlice(snapshot.getPersistentApplications(isSafeMode(), i));
    }

    public final int getPreferredActivities(List list, List list2, String str) {
        return this.mPreferredActivityHelper.getPreferredActivities(snapshot(), list, list2, str);
    }

    public final byte[] getPreferredActivityBackup(int i) {
        return this.mPreferredActivityHelper.getPreferredActivityBackup(i);
    }

    public final int getPrivateFlagsForUid(int i) {
        return snapshot().getPrivateFlagsForUid(i);
    }

    public final PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        int callingUid = Binder.getCallingUid();
        Computer snapshot = snapshot();
        snapshot.enforceCrossUserOrProfilePermission(callingUid, i, false, false, "getPropertyAsUser");
        if (snapshot.getPackageStateForInstalledAndFiltered(str2, callingUid, i) == null) {
            return null;
        }
        return this.mPackageProperty.getProperty(str, str2, str3);
    }

    public final ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) {
        return snapshot().getProviderInfo(componentName, j, i);
    }

    public final ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) {
        return snapshot().getReceiverInfo(componentName, j, i);
    }

    public final String getRotationResolverPackageName() {
        return this.mService.ensureSystemPackageName(snapshot(), this.mService.getPackageFromComponentString(R.string.day));
    }

    public final ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) {
        return snapshot().getServiceInfo(componentName, j, i);
    }

    public final String getServicesSystemSharedLibraryPackageName() {
        return this.mServicesExtensionPackageName;
    }

    public final String getSetupWizardPackageName() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Non-system caller");
        }
        return this.mService.mSetupWizardPackage;
    }

    public final ParceledListSlice getSharedLibraries(String str, long j, int i) {
        return snapshot().getSharedLibraries(str, j, i);
    }

    public final String getSharedSystemSharedLibraryPackageName() {
        return this.mSharedSystemSharedLibraryPackageName;
    }

    public final KeySet getSigningKeySet(String str) {
        return snapshot().getSigningKeySet(str);
    }

    public final String getSdkSandboxPackageName() {
        return this.mService.getSdkSandboxPackageName();
    }

    public final String getSystemCaptionsServicePackageName() {
        return this.mService.ensureSystemPackageName(snapshot(), this.mService.getPackageFromComponentString(R.string.decline));
    }

    public final String[] getSystemSharedLibraryNames() {
        return snapshot().getSystemSharedLibraryNames();
    }

    public final String getSystemTextClassifierPackageName() {
        return this.mService.mSystemTextClassifierPackageName;
    }

    public final int getTargetSdkVersion(String str) {
        return snapshot().getTargetSdkVersion(str);
    }

    public final int getUidForSharedUser(String str) {
        return snapshot().getUidForSharedUser(str);
    }

    public final String getWellbeingPackageName() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (String) CollectionUtils.firstOrNull(((RoleManager) this.mContext.getSystemService(RoleManager.class)).getRoleHolders("android.app.role.SYSTEM_WELLBEING"));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void grantRuntimePermission(String str, String str2, int i) {
        ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).grantRuntimePermission(str, str2, UserHandle.of(i));
    }

    public final boolean hasSigningCertificate(String str, byte[] bArr, int i) {
        return snapshot().hasSigningCertificate(str, bArr, i);
    }

    public final boolean hasSystemFeature(String str, int i) {
        return this.mService.hasSystemFeature(str, i);
    }

    public final boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) {
        return snapshot().hasUidSigningCertificate(i, bArr, i2);
    }

    public final boolean isDeviceUpgrading() {
        return this.mService.isDeviceUpgrading();
    }

    public final boolean isFirstBoot() {
        return this.mService.isFirstBoot();
    }

    public final boolean isInstantApp(String str, int i) {
        return snapshot().isInstantApp(str, i);
    }

    public final boolean isPackageAvailable(String str, int i) {
        return snapshot().isPackageAvailable(str, i);
    }

    public final boolean isPackageDeviceAdminOnAnyUser(String str) {
        return this.mService.isPackageDeviceAdminOnAnyUser(snapshot(), str);
    }

    public final boolean isPackageSignedByKeySet(String str, KeySet keySet) {
        return snapshot().isPackageSignedByKeySet(str, keySet);
    }

    public final boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) {
        return snapshot().isPackageSignedByKeySetExactly(str, keySet);
    }

    public final boolean isPackageSuspendedForUser(String str, int i) {
        return snapshot().isPackageSuspendedForUser(str, i);
    }

    public final boolean isSafeMode() {
        return this.mService.getSafeMode();
    }

    public final boolean isStorageLow() {
        return this.mService.isStorageLow();
    }

    public final boolean isUidPrivileged(int i) {
        return snapshot().isUidPrivileged(i);
    }

    public final boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) {
        Computer snapshot = snapshot();
        if (!z) {
            Log.w("PackageManager", "Ignored checkProfiles=false flag");
        }
        return this.mDexOptHelper.performDexOptMode(snapshot, str, str2, z2, z3, str3);
    }

    public final boolean performDexOptSecondary(String str, String str2, boolean z) {
        return this.mDexOptHelper.performDexOptSecondary(str, str2, z);
    }

    public final ParceledListSlice queryIntentActivities(Intent intent, String str, long j, int i) {
        SemPersonaManager semPersonaManager;
        try {
            Trace.traceBegin(262144L, "queryIntentActivities");
            List queryIntentActivitiesInternal = snapshot().queryIntentActivitiesInternal(intent, str, j, i);
            if (SemPersonaManager.isDoEnabled(i) && (semPersonaManager = (SemPersonaManager) this.mContext.getSystemService(SemPersonaManager.class)) != null && semPersonaManager.isAppSeparationPresent()) {
                queryIntentActivitiesInternal = semPersonaManager.getUpdatedListWithAppSeparation(queryIntentActivitiesInternal);
            }
            return new ParceledListSlice(queryIntentActivitiesInternal);
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public final ParceledListSlice queryContentProviders(String str, int i, long j, String str2) {
        return snapshot().queryContentProviders(str, i, j, str2);
    }

    public final ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) {
        return snapshot().queryInstrumentationAsUser(str, i, i2);
    }

    public final ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, String[] strArr, Intent intent, String str, long j, int i) {
        return new ParceledListSlice(this.mResolveIntentHelper.queryIntentActivityOptionsInternal(snapshot(), componentName, intentArr, strArr, intent, str, j, i));
    }

    public final ParceledListSlice queryIntentContentProviders(Intent intent, String str, long j, int i) {
        return new ParceledListSlice(this.mResolveIntentHelper.queryIntentContentProvidersInternal(snapshot(), intent, str, j, i));
    }

    public final ParceledListSlice queryIntentReceivers(Intent intent, String str, long j, int i) {
        return new ParceledListSlice(this.mResolveIntentHelper.queryIntentReceiversInternal(snapshot(), intent, str, j, i, Binder.getCallingUid()));
    }

    public final ParceledListSlice queryIntentServices(Intent intent, String str, long j, int i) {
        return new ParceledListSlice(snapshot().queryIntentServicesInternal(intent, str, j, i, Binder.getCallingUid(), false));
    }

    public final void querySyncProviders(List list, List list2) {
        snapshot().querySyncProviders(isSafeMode(), list, list2);
    }

    public final void removePermission(String str) {
        ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).removePermission(str);
    }

    public final void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) {
        this.mPreferredActivityHelper.replacePreferredActivity(snapshot(), new WatchedIntentFilter(intentFilter), i, componentNameArr, componentName, i2);
    }

    public final ProviderInfo resolveContentProvider(String str, long j, int i) {
        return snapshot().resolveContentProvider(str, j, i, Binder.getCallingUid());
    }

    public final void resetApplicationPreferences(int i) {
        this.mPreferredActivityHelper.resetApplicationPreferences(i);
    }

    public final ResolveInfo resolveIntent(Intent intent, String str, long j, int i) {
        return this.mResolveIntentHelper.resolveIntentInternal(snapshot(), intent, str, j, 0L, i, false, Binder.getCallingUid());
    }

    public final ResolveInfo resolveService(Intent intent, String str, long j, int i) {
        return this.mResolveIntentHelper.resolveServiceInternal(snapshot(), intent, str, j, i, Binder.getCallingUid());
    }

    public final void restoreDefaultApps(byte[] bArr, int i) {
        this.mPreferredActivityHelper.restoreDefaultApps(bArr, i);
    }

    public final void restorePreferredActivities(byte[] bArr, int i) {
        this.mPreferredActivityHelper.restorePreferredActivities(bArr, i);
    }

    public final void setHomeActivity(ComponentName componentName, int i) {
        this.mPreferredActivityHelper.setHomeActivity(snapshot(), componentName, i);
    }

    public final void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) {
        this.mPreferredActivityHelper.setLastChosenActivity(snapshot(), intent, str, i, new WatchedIntentFilter(intentFilter), i2, componentName);
    }

    public final boolean updateIntentVerificationStatus(String str, int i, int i2) {
        return this.mDomainVerificationManager.setLegacyUserState(str, i2, i);
    }

    public final void verifyIntentFilter(int i, int i2, List list) {
        DomainVerificationProxyV1.queueLegacyVerifyResult(this.mContext, this.mDomainVerificationConnection, i, i2, list, Binder.getCallingUid());
    }

    public final boolean[] canPackageQuery(String str, String[] strArr, int i) {
        return snapshot().canPackageQuery(str, strArr, i);
    }

    public final void deletePreloadsFileCache() {
        this.mService.deletePreloadsFileCache();
    }

    public final void setSystemAppHiddenUntilInstalled(String str, boolean z) {
        this.mService.setSystemAppHiddenUntilInstalled(snapshot(), str, z);
    }

    public final boolean setSystemAppInstallState(String str, boolean z, int i) {
        return this.mService.setSystemAppInstallState(snapshot(), str, z, i);
    }

    public final void finishPackageInstall(int i, boolean z) {
        this.mService.finishPackageInstall(i, z);
    }

    public final boolean isSystemCompressedPackage(String str, int i) {
        PackageStateInternal disabledSystemPackage;
        Objects.requireNonNull(str);
        int callingUid = Binder.getCallingUid();
        Computer snapshot = snapshot();
        PackageStateInternal packageStateForInstalledAndFiltered = snapshot.getPackageStateForInstalledAndFiltered(str, callingUid, i);
        return packageStateForInstalledAndFiltered != null && (disabledSystemPackage = snapshot.getDisabledSystemPackage(str)) != null && disabledSystemPackage.getPkg().isStub() && packageStateForInstalledAndFiltered.getPkg().getLongVersionCode() == disabledSystemPackage.getPkg().getLongVersionCode();
    }
}
