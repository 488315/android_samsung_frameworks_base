package android.content.p000pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.IPackageLoadingProgressCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.p014pm.PackageList;
import com.android.server.p014pm.dex.DynamicCodeLogger;
import com.android.server.p014pm.pkg.AndroidPackage;
import com.android.server.p014pm.pkg.PackageStateInternal;
import com.android.server.p014pm.pkg.SharedUserApi;
import com.android.server.p014pm.pkg.mutate.PackageStateMutator;
import com.android.server.p014pm.snapshot.PackageDataSnapshot;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class PackageManagerInternal {

  public interface ExternalSourcesPolicy {
    int getPackageTrustedToInstallApps(String str, int i);
  }

  public interface PackageListObserver {
    void onPackageAdded(String str, int i);

    default void onPackageChanged(String str, int i) {}

    default void onPackageRemoved(String str, int i) {}
  }

  public abstract void addIsolatedUid(int i, int i2);

  public abstract boolean canAccessComponent(int i, ComponentName componentName, int i2);

  public abstract boolean canAccessInstantApps(int i, int i2);

  public abstract boolean canQueryPackage(int i, String str);

  public abstract int checkUidSignaturesForAllUsers(int i, int i2);

  public abstract void clearBlockUninstallForUser(int i);

  public abstract PackageStateMutator.Result commitPackageStateMutation(
      PackageStateMutator.InitialState initialState, Consumer consumer);

  public abstract long deleteOatArtifactsOfPackage(String str);

  public abstract boolean filterAppAccess(int i, int i2);

  public abstract boolean filterAppAccess(AndroidPackage androidPackage, int i, int i2);

  public abstract boolean filterAppAccess(String str, int i, int i2, boolean z);

  public abstract void finishPackageInstall(int i, boolean z);

  public abstract void flushPackageRestrictions(int i);

  public abstract void forEachInstalledPackage(Consumer consumer, int i);

  public abstract void forEachPackage(Consumer consumer);

  public abstract void forEachPackageSetting(Consumer consumer);

  public abstract void forEachPackageState(Consumer consumer);

  public abstract void freeAllAppCacheAboveQuota(String str);

  public abstract void freeStorage(String str, long j, int i);

  public abstract ActivityInfo getActivityInfo(ComponentName componentName, long j, int i, int i2);

  public abstract AndroidPackage getAndroidPackage(String str);

  public abstract List getApksInApex(String str);

  public abstract int getApplicationEnabledState(String str, int i);

  public abstract ApplicationInfo getApplicationInfo(String str, long j, int i, int i2);

  public abstract int getComponentEnabledSetting(ComponentName componentName, int i, int i2);

  public abstract ComponentName getDefaultHomeActivity(int i);

  public abstract ArraySet getDisabledComponents(String str, int i);

  public abstract PackageStateInternal getDisabledSystemPackage(String str);

  public abstract String getDisabledSystemPackageName(String str);

  public abstract int getDistractingPackageRestrictions(String str, int i);

  public abstract DynamicCodeLogger getDynamicCodeLogger();

  public abstract ArraySet getEnabledComponents(String str, int i);

  public abstract ComponentName getHomeActivitiesAsUser(List list, int i);

  public abstract IncrementalStatesInfo getIncrementalStatesInfo(String str, int i, int i2);

  public abstract List getInstalledApplications(long j, int i, int i2);

  public abstract String getInstantAppPackageName(int i);

  public abstract String[] getKnownPackageNames(int i, int i2);

  public abstract String getNameForUid(int i);

  public abstract AndroidPackage getPackage(int i);

  public abstract AndroidPackage getPackage(String str);

  public abstract PackageInfo getPackageInfo(String str, long j, int i, int i2);

  public abstract PackageList getPackageList(PackageListObserver packageListObserver);

  public abstract PackageStateInternal getPackageStateInternal(String str);

  public abstract ArrayMap getPackageStates();

  public abstract int getPackageTargetSdkVersion(String str);

  public abstract int getPackageUid(String str, long j, int i);

  public abstract List getPackagesForAppId(int i);

  public abstract int[] getPermissionGids(String str, int i);

  public abstract ArrayMap getProcessesForUid(int i);

  public abstract String getSetupWizardPackageName();

  public abstract SharedUserApi getSharedUserApi(int i);

  public abstract ArraySet getSharedUserPackages(int i);

  public abstract String[] getSharedUserPackagesForPackage(String str, int i);

  public abstract SuspendDialogInfo getSuspendedDialogInfo(String str, String str2, int i);

  public abstract Bundle getSuspendedPackageLauncherExtras(String str, int i);

  public abstract String getSuspendingPackage(String str, int i);

  public abstract ComponentName getSystemUiServiceComponent();

  public abstract List getTargetPackageNames(int i);

  public abstract int getUidTargetSdkVersion(int i);

  public abstract int[] getVisibilityAllowList(String str, int i);

  public abstract void grantImplicitAccess(int i, Intent intent, int i2, int i3, boolean z);

  public abstract void grantImplicitAccess(
      int i, Intent intent, int i2, int i3, boolean z, boolean z2);

  public abstract boolean hasInstantApplicationMetadata(String str, int i);

  public abstract boolean hasSignatureCapability(int i, int i2, int i3);

  public abstract boolean isApexPackage(String str);

  public abstract boolean isCallerInstallerOfRecord(AndroidPackage androidPackage, int i);

  public abstract boolean isDataRestoreSafe(Signature signature, String str);

  public abstract boolean isDataRestoreSafe(byte[] bArr, String str);

  public abstract boolean isInstantApp(String str, int i);

  public abstract boolean isInstantAppInstallerComponent(ComponentName componentName);

  public abstract boolean isPackageDataProtected(int i, String str);

  public abstract boolean isPackageEphemeral(int i, String str);

  public abstract boolean isPackageFrozen(String str, int i, int i2);

  public abstract boolean isPackagePersistent(String str);

  public abstract boolean isPackageStateProtected(String str, int i);

  public abstract boolean isPackageSuspended(String str, int i);

  public abstract boolean isPermissionUpgradeNeeded(int i);

  public abstract boolean isPermissionsReviewRequired(String str, int i);

  public abstract boolean isPlatformSigned(String str);

  public abstract boolean isResolveActivityComponent(ComponentInfo componentInfo);

  public abstract boolean isSameApp(String str, int i, int i2);

  public abstract boolean isSameApp(String str, long j, int i, int i2);

  public abstract boolean isSuspendingAnyPackages(String str, int i);

  public abstract boolean isSystemPackage(String str);

  public abstract boolean isUidPrivileged(int i);

  public abstract void legacyDumpProfiles(String str, boolean z);

  public abstract void legacyForceDexOpt(String str);

  public abstract void legacyReconcileSecondaryDexFiles(String str);

  public abstract void migrateLegacyObbData();

  public abstract void notifyPackageUse(String str, int i);

  public abstract void onPackageProcessKilledForUninstall(String str);

  public abstract void pruneInstantApps();

  public abstract List queryIntentActivities(Intent intent, String str, long j, int i, int i2);

  public abstract List queryIntentReceivers(
      Intent intent, String str, long j, int i, int i2, boolean z);

  public abstract List queryIntentServices(Intent intent, long j, int i, int i2);

  public abstract void reconcileAppsData(int i, int i2, boolean z);

  public abstract boolean registerInstalledLoadingProgressCallback(
      String str, InstalledLoadingProgressCallback installedLoadingProgressCallback, int i);

  public abstract void removeAllDistractingPackageRestrictions(int i);

  public abstract void removeAllNonSystemPackageSuspensions(int i);

  public abstract void removeDistractingPackageRestrictions(String str, int i);

  public abstract void removeIsolatedUid(int i);

  public abstract String removeLegacyDefaultBrowserPackageName(int i);

  public abstract void removeNonSystemPackageSuspensions(String str, int i);

  public abstract void removePackageListObserver(PackageListObserver packageListObserver);

  public abstract void requestChecksums(
      String str,
      boolean z,
      int i,
      int i2,
      List list,
      IOnChecksumsReadyListener iOnChecksumsReadyListener,
      int i3,
      Executor executor,
      Handler handler);

  public abstract void requestInstantAppResolutionPhaseTwo(
      AuxiliaryResolveInfo auxiliaryResolveInfo,
      Intent intent,
      String str,
      String str2,
      String str3,
      boolean z,
      Bundle bundle,
      int i);

  public abstract ProviderInfo resolveContentProvider(String str, long j, int i, int i2);

  public abstract ResolveInfo resolveIntentExported(
      Intent intent, String str, long j, long j2, int i, boolean z, int i2, int i3);

  public abstract ResolveInfo resolveService(Intent intent, String str, long j, int i, int i2);

  public abstract void setDeviceAndProfileOwnerPackages(int i, String str, SparseArray sparseArray);

  public abstract void setEnableRollbackCode(int i, int i2);

  public abstract void setEnabledOverlayPackages(int i, ArrayMap arrayMap, Set set, Set set2);

  public abstract void setExternalSourcesPolicy(ExternalSourcesPolicy externalSourcesPolicy);

  public abstract void setIntegrityVerificationResult(int i, int i2);

  public abstract void setKeepUninstalledPackages(List list);

  public abstract void setOwnerProtectedPackages(int i, List list);

  public abstract void setPackageStoppedState(String str, boolean z, int i);

  public abstract String[] setPackagesSuspendedByAdmin(int i, String[] strArr, boolean z);

  public abstract void setPackagesSuspendedForQuietMode(int i, boolean z);

  public abstract void setVisibilityLogging(String str, boolean z);

  public abstract void shutdown();

  public abstract PackageDataSnapshot snapshot();

  public abstract void uninstallApex(String str, long j, int i, IntentSender intentSender, int i2);

  public abstract void unsuspendForSuspendingPackage(String str, int i);

  public abstract void updateExternalMediaStatus(boolean z, boolean z2);

  public abstract void updateRuntimePermissionsFingerprint(int i);

  public abstract boolean wasPackageEverLaunched(String str, int i);

  public abstract boolean wasPackageStopped(String str, int i);

  public abstract void writePermissionSettings(int[] iArr, boolean z);

  public abstract void writeSettings(boolean z);

  public PackageList getPackageList() {
    return getPackageList(null);
  }

  public boolean filterAppAccess(String str, int i, int i2) {
    return filterAppAccess(str, i, i2, true);
  }

  public abstract class InstalledLoadingProgressCallback {
    public final LoadingProgressCallbackBinder mBinder = new LoadingProgressCallbackBinder();
    public final Executor mExecutor;

    public abstract void onLoadingProgressChanged(float f);

    public InstalledLoadingProgressCallback(Handler handler) {
      this.mExecutor =
          new HandlerExecutor(handler == null ? new Handler(Looper.getMainLooper()) : handler);
    }

    public final IBinder getBinder() {
      return this.mBinder;
    }

    public class LoadingProgressCallbackBinder extends IPackageLoadingProgressCallback.Stub {
      public LoadingProgressCallbackBinder() {}

      public void onPackageLoadingProgressChanged(float f) {
        InstalledLoadingProgressCallback.this.mExecutor.execute(
            PooledLambda.obtainRunnable(
                    new BiConsumer() { // from class:
                                       // android.content.pm.PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder$$ExternalSyntheticLambda0
                      @Override // java.util.function.BiConsumer
                      public final void accept(Object obj, Object obj2) {
                        ((PackageManagerInternal.InstalledLoadingProgressCallback) obj)
                            .onLoadingProgressChanged(((Float) obj2).floatValue());
                      }
                    },
                    InstalledLoadingProgressCallback.this,
                    Float.valueOf(f))
                .recycleOnUse());
      }
    }
  }
}
