package com.android.server.pm;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.SuspendDialogInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public abstract class PackageManagerInternalBase extends PackageManagerInternal {
  public final PackageManagerService mService;

  public abstract ApexManager getApexManager();

  public abstract AppDataHelper getAppDataHelper();

  public abstract Context getContext();

  public abstract DistractingPackageHelper getDistractingPackageHelper();

  public abstract InstantAppRegistry getInstantAppRegistry();

  public abstract PackageObserverHelper getPackageObserverHelper();

  public abstract PermissionManagerServiceInternal getPermissionManager();

  public abstract ProtectedPackages getProtectedPackages();

  public abstract ResolveIntentHelper getResolveIntentHelper();

  public abstract SuspendPackageHelper getSuspendPackageHelper();

  public PackageManagerInternalBase(PackageManagerService packageManagerService) {
    this.mService = packageManagerService;
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final Computer snapshot() {
    return this.mService.snapshotComputer();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final List getInstalledApplications(long j, int i, int i2) {
    return snapshot().getInstalledApplications(j, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isInstantApp(String str, int i) {
    return snapshot().isInstantApp(str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final String getInstantAppPackageName(int i) {
    return snapshot().getInstantAppPackageName(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean filterAppAccess(AndroidPackage androidPackage, int i, int i2) {
    return snapshot().filterAppAccess(androidPackage, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean filterAppAccess(String str, int i, int i2, boolean z) {
    return snapshot().filterAppAccess(str, i, i2, z);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean filterAppAccess(int i, int i2) {
    return snapshot().filterAppAccess(i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int[] getVisibilityAllowList(String str, int i) {
    return snapshot().getVisibilityAllowList(str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean canQueryPackage(int i, String str) {
    return snapshot().canQueryPackage(i, str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final AndroidPackage getPackage(String str) {
    return snapshot().getPackage(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final AndroidPackage getAndroidPackage(String str) {
    return snapshot().getPackage(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final AndroidPackage getPackage(int i) {
    return snapshot().getPackage(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final List getPackagesForAppId(int i) {
    return snapshot().getPackagesForAppId(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final PackageStateInternal getPackageStateInternal(String str) {
    return snapshot().getPackageStateInternal(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ArrayMap getPackageStates() {
    return snapshot().getPackageStates();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void removePackageListObserver(
      PackageManagerInternal.PackageListObserver packageListObserver) {
    getPackageObserverHelper().removeObserver(packageListObserver);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final PackageStateInternal getDisabledSystemPackage(String str) {
    return snapshot().getDisabledSystemPackage(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final String[] getKnownPackageNames(int i, int i2) {
    return this.mService.getKnownPackageNamesInternal(snapshot(), i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void setKeepUninstalledPackages(List list) {
    this.mService.setKeepUninstalledPackagesInternal(snapshot(), list);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isPermissionsReviewRequired(String str, int i) {
    return getPermissionManager().isPermissionsReviewRequired(str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final PackageInfo getPackageInfo(String str, long j, int i, int i2) {
    return snapshot().getPackageInfoInternal(str, -1L, j, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final Bundle getSuspendedPackageLauncherExtras(String str, int i) {
    return getSuspendPackageHelper()
        .getSuspendedPackageLauncherExtras(snapshot(), str, i, Binder.getCallingUid());
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isPackageSuspended(String str, int i) {
    return getSuspendPackageHelper().isPackageSuspended(snapshot(), str, i, Binder.getCallingUid());
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void removeNonSystemPackageSuspensions(String str, int i) {
    getSuspendPackageHelper()
        .removeSuspensionsBySuspendingPackage(
            snapshot(),
            new String[] {str},
            new Predicate() { // from class:
              // com.android.server.pm.PackageManagerInternalBase$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$removeNonSystemPackageSuspensions$0;
                lambda$removeNonSystemPackageSuspensions$0 =
                    PackageManagerInternalBase.lambda$removeNonSystemPackageSuspensions$0(
                        (String) obj);
                return lambda$removeNonSystemPackageSuspensions$0;
              }
            },
            i);
  }

  public static /* synthetic */ boolean lambda$removeNonSystemPackageSuspensions$0(String str) {
    return !"android".equals(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void removeDistractingPackageRestrictions(String str, int i) {
    getDistractingPackageHelper()
        .removeDistractingPackageRestrictions(snapshot(), new String[] {str}, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void removeAllDistractingPackageRestrictions(int i) {
    this.mService.removeAllDistractingPackageRestrictions(snapshot(), i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final String getSuspendingPackage(String str, int i) {
    return getSuspendPackageHelper()
        .getSuspendingPackage(snapshot(), str, i, Binder.getCallingUid());
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final SuspendDialogInfo getSuspendedDialogInfo(String str, String str2, int i) {
    return getSuspendPackageHelper()
        .getSuspendedDialogInfo(snapshot(), str, str2, i, Binder.getCallingUid());
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int getDistractingPackageRestrictions(String str, int i) {
    PackageStateInternal packageStateInternal = getPackageStateInternal(str);
    if (packageStateInternal == null) {
      return 0;
    }
    return packageStateInternal.getUserStateOrDefault(i).getDistractionFlags();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int getPackageUid(String str, long j, int i) {
    return snapshot().getPackageUidInternal(str, j, i, 1000);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ApplicationInfo getApplicationInfo(String str, long j, int i, int i2) {
    return snapshot().getApplicationInfoInternal(str, j, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ActivityInfo getActivityInfo(ComponentName componentName, long j, int i, int i2) {
    return snapshot().getActivityInfoInternal(componentName, j, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final List queryIntentActivities(Intent intent, String str, long j, int i, int i2) {
    return snapshot().queryIntentActivitiesInternal(intent, str, j, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final List queryIntentReceivers(
      Intent intent, String str, long j, int i, int i2, boolean z) {
    return getResolveIntentHelper()
        .queryIntentReceiversInternal(snapshot(), intent, str, j, i2, i, z);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final List queryIntentServices(Intent intent, long j, int i, int i2) {
    return snapshot()
        .queryIntentServicesInternal(
            intent, intent.resolveTypeIfNeeded(getContext().getContentResolver()), j, i2, i, false);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ComponentName getHomeActivitiesAsUser(List list, int i) {
    return snapshot().getHomeActivitiesAsUser(list, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ComponentName getDefaultHomeActivity(int i) {
    return snapshot().getDefaultHomeActivity(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ComponentName getSystemUiServiceComponent() {
    return ComponentName.unflattenFromString(
        getContext().getResources().getString(R.string.ext_media_status_missing));
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void setOwnerProtectedPackages(int i, List list) {
    getProtectedPackages().setOwnerProtectedPackages(i, list);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isPackageDataProtected(int i, String str) {
    return getProtectedPackages().isPackageDataProtected(i, str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isPackageStateProtected(String str, int i) {
    return getProtectedPackages().isPackageStateProtected(i, str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isPackageEphemeral(int i, String str) {
    PackageStateInternal packageStateInternal = getPackageStateInternal(str);
    return packageStateInternal != null
        && packageStateInternal.getUserStateOrDefault(i).isInstantApp();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean wasPackageEverLaunched(String str, int i) {
    if (getPackageStateInternal(str) == null) {
      throw new IllegalArgumentException("Unknown package: " + str);
    }
    return !r1.getUserStateOrDefault(i).isNotLaunched();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final String getNameForUid(int i) {
    return snapshot().getNameForUid(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void requestInstantAppResolutionPhaseTwo(
      AuxiliaryResolveInfo auxiliaryResolveInfo,
      Intent intent,
      String str,
      String str2,
      String str3,
      boolean z,
      Bundle bundle,
      int i) {
    this.mService.requestInstantAppResolutionPhaseTwo(
        auxiliaryResolveInfo, intent, str, str2, str3, z, bundle, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void grantImplicitAccess(int i, Intent intent, int i2, int i3, boolean z) {
    grantImplicitAccess(i, intent, i2, i3, z, false);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void grantImplicitAccess(
      int i, Intent intent, int i2, int i3, boolean z, boolean z2) {
    this.mService.grantImplicitAccess(snapshot(), i, intent, i2, i3, z, z2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isInstantAppInstallerComponent(ComponentName componentName) {
    ActivityInfo activityInfo = this.mService.mInstantAppInstallerActivity;
    return activityInfo != null && activityInfo.getComponentName().equals(componentName);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void pruneInstantApps() {
    getInstantAppRegistry().pruneInstantApps(snapshot());
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final String getSetupWizardPackageName() {
    return this.mService.mSetupWizardPackage;
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ResolveInfo resolveIntentExported(
      Intent intent, String str, long j, long j2, int i, boolean z, int i2, int i3) {
    return getResolveIntentHelper()
        .resolveIntentInternal(snapshot(), intent, str, j, j2, i, z, i2, true, i3);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ResolveInfo resolveService(Intent intent, String str, long j, int i, int i2) {
    return getResolveIntentHelper().resolveServiceInternal(snapshot(), intent, str, j, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ProviderInfo resolveContentProvider(String str, long j, int i, int i2) {
    return snapshot().resolveContentProvider(str, j, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int getUidTargetSdkVersion(int i) {
    return snapshot().getUidTargetSdkVersion(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int getPackageTargetSdkVersion(String str) {
    PackageStateInternal packageStateInternal = getPackageStateInternal(str);
    if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
      return 10000;
    }
    return packageStateInternal.getPkg().getTargetSdkVersion();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean canAccessInstantApps(int i, int i2) {
    return snapshot().canViewInstantApps(i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean canAccessComponent(int i, ComponentName componentName, int i2) {
    return snapshot().canAccessComponent(i, componentName, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean hasInstantApplicationMetadata(String str, int i) {
    return getInstantAppRegistry().hasInstantApplicationMetadata(str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final String[] getSharedUserPackagesForPackage(String str, int i) {
    return snapshot().getSharedUserPackagesForPackage(str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ArrayMap getProcessesForUid(int i) {
    return snapshot().getProcessesForUid(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int[] getPermissionGids(String str, int i) {
    return getPermissionManager().getPermissionGids(str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void freeStorage(String str, long j, int i) {
    this.mService.freeStorage(str, j, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void freeAllAppCacheAboveQuota(String str) {
    this.mService.freeAllAppCacheAboveQuota(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void forEachPackageSetting(Consumer consumer) {
    this.mService.forEachPackageSetting(consumer);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void forEachPackageState(Consumer consumer) {
    this.mService.forEachPackageState(snapshot(), consumer);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void forEachPackage(Consumer consumer) {
    this.mService.forEachPackage(snapshot(), consumer);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void forEachInstalledPackage(Consumer consumer, int i) {
    this.mService.forEachInstalledPackage(snapshot(), consumer, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ArraySet getEnabledComponents(String str, int i) {
    PackageStateInternal packageStateInternal = getPackageStateInternal(str);
    if (packageStateInternal == null) {
      return new ArraySet();
    }
    return packageStateInternal.getUserStateOrDefault(i).m10014getEnabledComponents();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final ArraySet getDisabledComponents(String str, int i) {
    PackageStateInternal packageStateInternal = getPackageStateInternal(str);
    if (packageStateInternal == null) {
      return new ArraySet();
    }
    return packageStateInternal.getUserStateOrDefault(i).m10013getDisabledComponents();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int getApplicationEnabledState(String str, int i) {
    PackageStateInternal packageStateInternal = getPackageStateInternal(str);
    if (packageStateInternal == null) {
      return 0;
    }
    return packageStateInternal.getUserStateOrDefault(i).getEnabledState();
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final int getComponentEnabledSetting(ComponentName componentName, int i, int i2) {
    return snapshot().getComponentEnabledSettingInternal(componentName, i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void setEnableRollbackCode(int i, int i2) {
    this.mService.setEnableRollbackCode(i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void finishPackageInstall(int i, boolean z) {
    this.mService.finishPackageInstall(i, z);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isApexPackage(String str) {
    return snapshot().isApexPackage(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final List getApksInApex(String str) {
    return getApexManager().getApksInApex(str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isCallerInstallerOfRecord(AndroidPackage androidPackage, int i) {
    return snapshot().isCallerInstallerOfRecord(androidPackage, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isSystemPackage(String str) {
    return str.equals(this.mService.ensureSystemPackageName(snapshot(), str));
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void unsuspendForSuspendingPackage(String str, int i) {
    this.mService.unsuspendForSuspendingPackage(snapshot(), str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isSuspendingAnyPackages(String str, int i) {
    return snapshot().isSuspendingAnyPackages(str, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void requestChecksums(
      String str,
      boolean z,
      int i,
      int i2,
      List list,
      IOnChecksumsReadyListener iOnChecksumsReadyListener,
      int i3,
      Executor executor,
      Handler handler) {
    this.mService.requestChecksumsInternal(
        snapshot(), str, z, i, i2, list, iOnChecksumsReadyListener, i3, executor, handler);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final boolean isPackageFrozen(String str, int i, int i2) {
    return snapshot().getPackageStartability(this.mService.getSafeMode(), str, i, i2) == 3;
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final long deleteOatArtifactsOfPackage(String str) {
    return this.mService.deleteOatArtifactsOfPackage(snapshot(), str);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void reconcileAppsData(int i, int i2, boolean z) {
    getAppDataHelper().reconcileAppsData(i, i2, z);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public ArraySet getSharedUserPackages(int i) {
    return snapshot().getSharedUserPackages(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public SharedUserApi getSharedUserApi(int i) {
    return snapshot().getSharedUser(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public boolean isUidPrivileged(int i) {
    return snapshot().isUidPrivileged(i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public int checkUidSignaturesForAllUsers(int i, int i2) {
    return snapshot().checkUidSignaturesForAllUsers(i, i2);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public void setPackageStoppedState(String str, boolean z, int i) {
    this.mService.setPackageStoppedState(snapshot(), str, z, i);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final PackageStateMutator.Result commitPackageStateMutation(
      PackageStateMutator.InitialState initialState, Consumer consumer) {
    return this.mService.commitPackageStateMutation(initialState, consumer);
  }

  @Override // android.content.p000pm.PackageManagerInternal
  public final void shutdown() {
    this.mService.shutdown();
  }
}
