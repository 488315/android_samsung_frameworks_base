package android.content.pm;

import android.Manifest;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.IMemorySaverPackageMoveObserver;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.dex.IArtManager;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IPackageManager extends IInterface {

    public static class Default implements IPackageManager {
        @Override // android.content.pm.IPackageManager
        public boolean activitySupportsIntentAsUser(ComponentName componentName, Intent intent, String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean addPermission(PermissionInfo permissionInfo) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean applyRuntimePermissionsForMDM(String str, List<String> list, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean canForwardTo(Intent intent, String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean[] canPackageQuery(String str, String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean canRequestPackageInstalls(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public String[] canonicalToCurrentPackageNames(String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void changeMonetizationBadgeState(String str, String str2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void checkPackageStartable(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int checkPermission(String str, String str2, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int checkSignatures(String str, String str2, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int checkUidPermission(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int checkUidSignatures(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void clearAppCategoryHintDeveloper(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearAppCategoryHintUser(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearApplicationProfileData(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearCrossProfileIntentFilters(int i, String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePersistentPreferredActivities(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePreferredActivities(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePreferredActivitiesAsUserForMDM(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPersistentPreferredActivity(IntentFilter intentFilter, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean createEncAppData(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public String[] currentToCanonicalPackageNames(String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deletePreloadsFileCache() throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void enterSafeMode() throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void extendVerificationTimeout(int i, int i2, long j) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo findPersistentPreferredActivity(Intent intent, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void finishPackageInstall(int i, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void flushPackageRestrictionsAsUser(int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void freeStorage(String str, long j, int i, IntentSender intentSender) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void freeStorageAndNotify(String str, long j, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getAllApexDirectories() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getAllIntentFilters(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getAllPackages() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Map<String, String> getAppCategoryHintUserMap() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Map<String, String[]> getAppCategoryInfos(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParcelFileDescriptor getAppMetadataFd(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getAppMetadataSource(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getAppOpPermissionPackages(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getAppPredictionServicePackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getApplicationEnabledSetting(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getApplicationHiddenSettingAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ApplicationInfo getApplicationInfo(String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Bitmap getArchivedAppIcon(String str, UserHandle userHandle, String str2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ArchivedPackageParcel getArchivedPackage(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public IArtManager getArtManager() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getAttentionServicePackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getBlockUninstallForUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ChangedPackages getChangedPackages(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getComponentEnabledSetting(ComponentName componentName, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getDefaultAppsBackup(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getDefaultTextClassifierPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getDomainVerificationAgent(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getDomainVerificationBackup(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getFlagsForUid(int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getGrantedPermissionsForMDM(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public CharSequence getHarmfulAppWarning(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public IBinder getHoldLockToken() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getIncidentReportApproverPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getInitialNonStoppedSystemPackages() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getInstallLocation() throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int getInstallReason(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public InstallSourceInfo getInstallSourceInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getInstalledApplications(long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<ModuleInfo> getInstalledModules(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getInstalledPackages(long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getInstallerPackageName(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getInstantAppAndroidId(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getInstantAppCookie(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Bitmap getInstantAppIcon(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getInstantAppInstallerComponent() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getInstantAppResolverComponent() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getInstantApps(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getIntentFilterVerifications(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getIntentVerificationStatus(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public KeySet getKeySetByAlias(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo getLastChosenActivity(Intent intent, String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public IntentSender getLaunchIntentSenderForPackage(String str, String str2, String str3, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getMetadataForIconTray(String str, String str2, int i, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getMimeGroup(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ModuleInfo getModuleInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getMoveStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public String getNameForUid(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getNamesForUids(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int[] getPackageGids(String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getPackageGrantedPermissionsForMDM(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public PackageInfo getPackageInfo(String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public IPackageInstaller getPackageInstaller() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getPackageListForDualDarPolicy(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getPackageUid(String str, long j, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getPackagesForUid(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getPageSizeCompatWarningMessage(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getPermissionControllerPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getPersistentApplications(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getPreferredActivityBackup(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getPrivateFlagsForUid(int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getRequestedRuntimePermissionsForMDM(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getRotationResolverPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getRuntimePermissionsVersion(int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public String getSdkSandboxPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSetupWizardPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getSharedLibraries(String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public KeySet getSigningKeySet(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSplashScreenTheme(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Bundle getSuspendedPackageAppExtras(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSuspendingPackage(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSystemCaptionsServicePackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getSystemSharedLibraryNames() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Map<String, String> getSystemSharedLibraryNamesAndPaths() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSystemTextClassifierPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getTargetSdkVersion(String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int getUidForSharedUser(String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getUnknownSourcePackagesAsUser(long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getUnsuspendablePackagesForUser(String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getUserMinAspectRatio(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getWellbeingPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void grantRuntimePermission(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSigningCertificate(String str, byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSystemFeature(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSystemUidErrors() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void holdLock(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int installExistingPackageAsUser(String str, int i, int i2, int i3, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isAppArchivable(String str, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isAutoRevokeWhitelisted(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isDeviceUpgrading() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isFirstBoot() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isInstantApp(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageAutoDisabled(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageAvailable(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageDeviceAdminOnAnyUser(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageQuarantinedForUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSignedByKeySet(String str, KeySet keySet) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageStateProtected(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageStoppedForUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSuspendedForUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPageSizeCompatEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isProtectedBroadcast(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isSafeMode() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isStorageLow() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isSystemCompressedPackage(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isUidPrivileged(int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isUnknownSourcePackage(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void logAppProcessStartIfNeeded(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void makeProviderVisible(int i, String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void makeUidVisible(int i, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int movePackage(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int movePrimaryStorage(String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void notifyDexLoad(String str, Map<String, String> map, String str2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void notifyPackageUse(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void notifyPackagesReplacedReceived(String[] strArr) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void overrideLabelAndIcon(ComponentName componentName, String str, int i, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int performDexOptForADCP(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean performDexOptSecondary(String str, String str2, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryContentProviders(String str, int i, long j, String str2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentActivities(Intent intent, String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, String[] strArr, Intent intent, String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentContentProviders(Intent intent, String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentReceivers(Intent intent, String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentServices(Intent intent, String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryProperty(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void registerDexModule(String str, String str2, boolean z, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void registerPackageMonitorCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void relinquishUpdateOwnership(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean removeEncPkgDir(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean removeEncUserDir(int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void removePermission(String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void requestPackageChecksums(String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void resetApplicationPreferences(int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ProviderInfo resolveContentProvider(String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ProviderInfo resolveContentProviderForUid(String str, long j, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo resolveIntent(Intent intent, String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo resolveService(Intent intent, String str, long j, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void restoreDefaultApps(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void restoreDomainVerification(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void restoreLabelAndIcon(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void restorePreferredActivities(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean semIsInstalledPackageHiddenAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean semIsPermissionRevokedByUserFixed(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setAppCategoryHintDeveloper(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setAppCategoryHintUser(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setApplicationCategoryHint(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean setApplicationHiddenSettingAsUser(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setBlockUninstallForUser(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> list, int i, String str) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public String[] setDistractingPackageRestrictionsAsUser(String[] strArr, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setHarmfulAppWarning(String str, CharSequence charSequence, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setHomeActivity(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean setInstallLocation(int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setInstallerPackageName(String str, String str2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean setInstantAppCookie(String str, byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setKeepUninstalledPackages(List<String> list) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int setLicensePermissionsForMDM(String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setMimeGroup(String str, String str2, List<String> list) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setPackageStoppedState(String str, boolean z, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, int i, String str, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setPageSizeAppCompatFlagsSettingsOverride(String str, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean setRequiredForSystemUser(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setRuntimePermissionsVersion(int i, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setSplashScreenTheme(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setSystemAppHiddenUntilInstalled(String str, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean setSystemAppInstallState(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setUpdateAvailable(String str, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setUserMinAspectRatio(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean shouldAppSupportBadgeIcon(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void unregisterPackageMonitorCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean updateIntentVerificationStatus(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void verifyIntentFilter(int i, int i2, List<String> list) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void verifyPendingInstall(int i, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean waitForHandler(long j, boolean z) throws RemoteException {
            return false;
        }
    }

    boolean activitySupportsIntentAsUser(ComponentName componentName, Intent intent, String str, int i) throws RemoteException;

    void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException;

    boolean addPermission(PermissionInfo permissionInfo) throws RemoteException;

    boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException;

    void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) throws RemoteException;

    void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2, boolean z) throws RemoteException;

    boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) throws RemoteException;

    boolean applyRuntimePermissionsForMDM(String str, List<String> list, int i, int i2) throws RemoteException;

    boolean canForwardTo(Intent intent, String str, int i, int i2) throws RemoteException;

    boolean[] canPackageQuery(String str, String[] strArr, int i) throws RemoteException;

    boolean canRequestPackageInstalls(String str, int i) throws RemoteException;

    String[] canonicalToCurrentPackageNames(String[] strArr) throws RemoteException;

    void changeMonetizationBadgeState(String str, String str2) throws RemoteException;

    void checkPackageStartable(String str, int i) throws RemoteException;

    int checkPermission(String str, String str2, int i) throws RemoteException;

    int checkSignatures(String str, String str2, int i) throws RemoteException;

    int checkUidPermission(String str, int i) throws RemoteException;

    int checkUidSignatures(int i, int i2) throws RemoteException;

    void clearAppCategoryHintDeveloper(String str) throws RemoteException;

    void clearAppCategoryHintUser(String str) throws RemoteException;

    void clearApplicationProfileData(String str) throws RemoteException;

    void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException;

    void clearCrossProfileIntentFilters(int i, String str) throws RemoteException;

    void clearPackagePersistentPreferredActivities(String str, int i) throws RemoteException;

    void clearPackagePreferredActivities(String str) throws RemoteException;

    void clearPackagePreferredActivitiesAsUserForMDM(String str, int i) throws RemoteException;

    void clearPersistentPreferredActivity(IntentFilter intentFilter, int i) throws RemoteException;

    boolean createEncAppData(String str, int i) throws RemoteException;

    String[] currentToCanonicalPackageNames(String[] strArr) throws RemoteException;

    void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws RemoteException;

    @Deprecated
    void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws RemoteException;

    void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws RemoteException;

    void deletePreloadsFileCache() throws RemoteException;

    void enterSafeMode() throws RemoteException;

    void extendVerificationTimeout(int i, int i2, long j) throws RemoteException;

    ResolveInfo findPersistentPreferredActivity(Intent intent, int i) throws RemoteException;

    void finishPackageInstall(int i, boolean z) throws RemoteException;

    void flushPackageRestrictionsAsUser(int i) throws RemoteException;

    void freeStorage(String str, long j, int i, IntentSender intentSender) throws RemoteException;

    void freeStorageAndNotify(String str, long j, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) throws RemoteException;

    List<String> getAllApexDirectories() throws RemoteException;

    ParceledListSlice getAllIntentFilters(String str) throws RemoteException;

    List<String> getAllPackages() throws RemoteException;

    Map<String, String> getAppCategoryHintUserMap() throws RemoteException;

    Map<String, String[]> getAppCategoryInfos(String str) throws RemoteException;

    ParcelFileDescriptor getAppMetadataFd(String str, int i) throws RemoteException;

    int getAppMetadataSource(String str, int i) throws RemoteException;

    String[] getAppOpPermissionPackages(String str, int i) throws RemoteException;

    String getAppPredictionServicePackageName() throws RemoteException;

    int getApplicationEnabledSetting(String str, int i) throws RemoteException;

    boolean getApplicationHiddenSettingAsUser(String str, int i) throws RemoteException;

    ApplicationInfo getApplicationInfo(String str, long j, int i) throws RemoteException;

    Bitmap getArchivedAppIcon(String str, UserHandle userHandle, String str2) throws RemoteException;

    ArchivedPackageParcel getArchivedPackage(String str, int i) throws RemoteException;

    IArtManager getArtManager() throws RemoteException;

    String getAttentionServicePackageName() throws RemoteException;

    boolean getBlockUninstallForUser(String str, int i) throws RemoteException;

    ChangedPackages getChangedPackages(int i, int i2) throws RemoteException;

    int getComponentEnabledSetting(ComponentName componentName, int i) throws RemoteException;

    ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) throws RemoteException;

    byte[] getDefaultAppsBackup(int i) throws RemoteException;

    String getDefaultTextClassifierPackageName() throws RemoteException;

    ComponentName getDomainVerificationAgent(int i) throws RemoteException;

    byte[] getDomainVerificationBackup(int i) throws RemoteException;

    int getFlagsForUid(int i) throws RemoteException;

    List<String> getGrantedPermissionsForMDM(String str) throws RemoteException;

    CharSequence getHarmfulAppWarning(String str, int i) throws RemoteException;

    IBinder getHoldLockToken() throws RemoteException;

    ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException;

    String getIncidentReportApproverPackageName() throws RemoteException;

    List<String> getInitialNonStoppedSystemPackages() throws RemoteException;

    int getInstallLocation() throws RemoteException;

    int getInstallReason(String str, int i) throws RemoteException;

    InstallSourceInfo getInstallSourceInfo(String str, int i) throws RemoteException;

    ParceledListSlice getInstalledApplications(long j, int i) throws RemoteException;

    List<ModuleInfo> getInstalledModules(int i) throws RemoteException;

    ParceledListSlice getInstalledPackages(long j, int i) throws RemoteException;

    String getInstallerPackageName(String str) throws RemoteException;

    String getInstantAppAndroidId(String str, int i) throws RemoteException;

    byte[] getInstantAppCookie(String str, int i) throws RemoteException;

    Bitmap getInstantAppIcon(String str, int i) throws RemoteException;

    ComponentName getInstantAppInstallerComponent() throws RemoteException;

    ComponentName getInstantAppResolverComponent() throws RemoteException;

    ComponentName getInstantAppResolverSettingsComponent() throws RemoteException;

    ParceledListSlice getInstantApps(int i) throws RemoteException;

    InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) throws RemoteException;

    @Deprecated
    ParceledListSlice getIntentFilterVerifications(String str) throws RemoteException;

    @Deprecated
    int getIntentVerificationStatus(String str, int i) throws RemoteException;

    KeySet getKeySetByAlias(String str, String str2) throws RemoteException;

    ResolveInfo getLastChosenActivity(Intent intent, String str, int i) throws RemoteException;

    IntentSender getLaunchIntentSenderForPackage(String str, String str2, String str3, int i) throws RemoteException;

    boolean getMetadataForIconTray(String str, String str2, int i, List<String> list) throws RemoteException;

    List<String> getMimeGroup(String str, String str2) throws RemoteException;

    ModuleInfo getModuleInfo(String str, int i) throws RemoteException;

    int getMoveStatus(int i) throws RemoteException;

    String getNameForUid(int i) throws RemoteException;

    String[] getNamesForUids(int[] iArr) throws RemoteException;

    int[] getPackageGids(String str, long j, int i) throws RemoteException;

    List<String> getPackageGrantedPermissionsForMDM(String str) throws RemoteException;

    PackageInfo getPackageInfo(String str, long j, int i) throws RemoteException;

    PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long j, int i) throws RemoteException;

    IPackageInstaller getPackageInstaller() throws RemoteException;

    List<String> getPackageListForDualDarPolicy(String str) throws RemoteException;

    void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) throws RemoteException;

    int getPackageUid(String str, long j, int i) throws RemoteException;

    String[] getPackagesForUid(int i) throws RemoteException;

    ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) throws RemoteException;

    String getPageSizeCompatWarningMessage(String str) throws RemoteException;

    String getPermissionControllerPackageName() throws RemoteException;

    PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException;

    ParceledListSlice getPersistentApplications(int i) throws RemoteException;

    int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) throws RemoteException;

    byte[] getPreferredActivityBackup(int i) throws RemoteException;

    int getPrivateFlagsForUid(int i) throws RemoteException;

    PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) throws RemoteException;

    ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) throws RemoteException;

    ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) throws RemoteException;

    List<String> getRequestedRuntimePermissionsForMDM(String str) throws RemoteException;

    String getRotationResolverPackageName() throws RemoteException;

    int getRuntimePermissionsVersion(int i) throws RemoteException;

    String getSdkSandboxPackageName() throws RemoteException;

    ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) throws RemoteException;

    String getServicesSystemSharedLibraryPackageName() throws RemoteException;

    String getSetupWizardPackageName() throws RemoteException;

    ParceledListSlice getSharedLibraries(String str, long j, int i) throws RemoteException;

    String getSharedSystemSharedLibraryPackageName() throws RemoteException;

    KeySet getSigningKeySet(String str) throws RemoteException;

    String getSplashScreenTheme(String str, int i) throws RemoteException;

    Bundle getSuspendedPackageAppExtras(String str, int i) throws RemoteException;

    String getSuspendingPackage(String str, int i) throws RemoteException;

    ParceledListSlice getSystemAvailableFeatures() throws RemoteException;

    String getSystemCaptionsServicePackageName() throws RemoteException;

    @Deprecated
    String[] getSystemSharedLibraryNames() throws RemoteException;

    Map<String, String> getSystemSharedLibraryNamesAndPaths() throws RemoteException;

    String getSystemTextClassifierPackageName() throws RemoteException;

    int getTargetSdkVersion(String str) throws RemoteException;

    int getUidForSharedUser(String str) throws RemoteException;

    ParceledListSlice getUnknownSourcePackagesAsUser(long j, int i) throws RemoteException;

    String[] getUnsuspendablePackagesForUser(String[] strArr, int i) throws RemoteException;

    int getUserMinAspectRatio(String str, int i) throws RemoteException;

    VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException;

    String getWellbeingPackageName() throws RemoteException;

    void grantRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean hasSigningCertificate(String str, byte[] bArr, int i) throws RemoteException;

    boolean hasSystemFeature(String str, int i) throws RemoteException;

    boolean hasSystemUidErrors() throws RemoteException;

    boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws RemoteException;

    void holdLock(IBinder iBinder, int i) throws RemoteException;

    int installExistingPackageAsUser(String str, int i, int i2, int i3, List<String> list) throws RemoteException;

    boolean isAppArchivable(String str, UserHandle userHandle) throws RemoteException;

    boolean isAutoRevokeWhitelisted(String str) throws RemoteException;

    boolean isDeviceUpgrading() throws RemoteException;

    boolean isFirstBoot() throws RemoteException;

    boolean isInstantApp(String str, int i) throws RemoteException;

    boolean isPackageAutoDisabled(String str, int i) throws RemoteException;

    boolean isPackageAvailable(String str, int i) throws RemoteException;

    boolean isPackageDeviceAdminOnAnyUser(String str) throws RemoteException;

    boolean isPackageQuarantinedForUser(String str, int i) throws RemoteException;

    boolean isPackageSignedByKeySet(String str, KeySet keySet) throws RemoteException;

    boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) throws RemoteException;

    boolean isPackageStateProtected(String str, int i) throws RemoteException;

    boolean isPackageStoppedForUser(String str, int i) throws RemoteException;

    boolean isPackageSuspendedForUser(String str, int i) throws RemoteException;

    boolean isPageSizeCompatEnabled(String str) throws RemoteException;

    boolean isProtectedBroadcast(String str) throws RemoteException;

    boolean isSafeMode() throws RemoteException;

    boolean isStorageLow() throws RemoteException;

    boolean isSystemCompressedPackage(String str, int i) throws RemoteException;

    boolean isUidPrivileged(int i) throws RemoteException;

    boolean isUnknownSourcePackage(String str) throws RemoteException;

    void logAppProcessStartIfNeeded(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException;

    void makeProviderVisible(int i, String str) throws RemoteException;

    void makeUidVisible(int i, int i2) throws RemoteException;

    int movePackage(String str, String str2) throws RemoteException;

    int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) throws RemoteException;

    int movePrimaryStorage(String str) throws RemoteException;

    void notifyDexLoad(String str, Map<String, String> map, String str2) throws RemoteException;

    void notifyPackageUse(String str, int i) throws RemoteException;

    void notifyPackagesReplacedReceived(String[] strArr) throws RemoteException;

    void overrideLabelAndIcon(ComponentName componentName, String str, int i, int i2) throws RemoteException;

    int performDexOptForADCP(String str, boolean z) throws RemoteException;

    boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) throws RemoteException;

    boolean performDexOptSecondary(String str, String str2, boolean z) throws RemoteException;

    ParceledListSlice queryContentProviders(String str, int i, long j, String str2) throws RemoteException;

    ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryIntentActivities(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, String[] strArr, Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentContentProviders(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentReceivers(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentServices(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryProperty(String str, int i) throws RemoteException;

    void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException;

    void registerDexModule(String str, String str2, boolean z, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException;

    void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    void registerPackageMonitorCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException;

    void relinquishUpdateOwnership(String str) throws RemoteException;

    boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException;

    boolean removeEncPkgDir(int i, String str) throws RemoteException;

    boolean removeEncUserDir(int i) throws RemoteException;

    void removePermission(String str) throws RemoteException;

    void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) throws RemoteException;

    void requestPackageChecksums(String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws RemoteException;

    void resetApplicationPreferences(int i) throws RemoteException;

    ProviderInfo resolveContentProvider(String str, long j, int i) throws RemoteException;

    ProviderInfo resolveContentProviderForUid(String str, long j, int i, int i2) throws RemoteException;

    ResolveInfo resolveIntent(Intent intent, String str, long j, int i) throws RemoteException;

    ResolveInfo resolveService(Intent intent, String str, long j, int i) throws RemoteException;

    void restoreDefaultApps(byte[] bArr, int i) throws RemoteException;

    void restoreDomainVerification(byte[] bArr, int i) throws RemoteException;

    void restoreLabelAndIcon(ComponentName componentName, int i) throws RemoteException;

    void restorePreferredActivities(byte[] bArr, int i) throws RemoteException;

    boolean semIsInstalledPackageHiddenAsUser(String str, int i) throws RemoteException;

    boolean semIsPermissionRevokedByUserFixed(String str, String str2, int i) throws RemoteException;

    void sendDeviceCustomizationReadyBroadcast() throws RemoteException;

    void setAppCategoryHintDeveloper(String str, int i) throws RemoteException;

    void setAppCategoryHintUser(String str, int i) throws RemoteException;

    void setApplicationCategoryHint(String str, int i, String str2) throws RemoteException;

    void setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) throws RemoteException;

    boolean setApplicationHiddenSettingAsUser(String str, boolean z, int i) throws RemoteException;

    boolean setBlockUninstallForUser(String str, boolean z, int i) throws RemoteException;

    void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) throws RemoteException;

    void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> list, int i, String str) throws RemoteException;

    String[] setDistractingPackageRestrictionsAsUser(String[] strArr, int i, int i2) throws RemoteException;

    void setHarmfulAppWarning(String str, CharSequence charSequence, int i) throws RemoteException;

    void setHomeActivity(ComponentName componentName, int i) throws RemoteException;

    boolean setInstallLocation(int i) throws RemoteException;

    void setInstallerPackageName(String str, String str2) throws RemoteException;

    boolean setInstantAppCookie(String str, byte[] bArr, int i) throws RemoteException;

    void setKeepUninstalledPackages(List<String> list) throws RemoteException;

    void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) throws RemoteException;

    int setLicensePermissionsForMDM(String str) throws RemoteException;

    void setMimeGroup(String str, String str2, List<String> list) throws RemoteException;

    void setPackageStoppedState(String str, boolean z, int i) throws RemoteException;

    String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, int i, String str, int i2, int i3) throws RemoteException;

    void setPageSizeAppCompatFlagsSettingsOverride(String str, boolean z) throws RemoteException;

    boolean setRequiredForSystemUser(String str, boolean z) throws RemoteException;

    void setRuntimePermissionsVersion(int i, int i2) throws RemoteException;

    void setSplashScreenTheme(String str, String str2, int i) throws RemoteException;

    void setSystemAppHiddenUntilInstalled(String str, boolean z) throws RemoteException;

    boolean setSystemAppInstallState(String str, boolean z, int i) throws RemoteException;

    void setUpdateAvailable(String str, boolean z) throws RemoteException;

    void setUserMinAspectRatio(String str, int i, int i2) throws RemoteException;

    boolean shouldAppSupportBadgeIcon(String str) throws RemoteException;

    void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    void unregisterPackageMonitorCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    @Deprecated
    boolean updateIntentVerificationStatus(String str, int i, int i2) throws RemoteException;

    @Deprecated
    void verifyIntentFilter(int i, int i2, List<String> list) throws RemoteException;

    void verifyPendingInstall(int i, int i2) throws RemoteException;

    boolean waitForHandler(long j, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IPackageManager {
        public static final String DESCRIPTOR = "android.content.pm.IPackageManager";
        static final int TRANSACTION_activitySupportsIntentAsUser = 12;
        static final int TRANSACTION_addCrossProfileIntentFilter = 66;
        static final int TRANSACTION_addPermission = 194;
        static final int TRANSACTION_addPermissionAsync = 195;
        static final int TRANSACTION_addPersistentPreferredActivity = 63;
        static final int TRANSACTION_addPreferredActivity = 59;
        static final int TRANSACTION_applyRuntimePermissionsForAllApplicationsForMDM = 219;
        static final int TRANSACTION_applyRuntimePermissionsForMDM = 218;
        static final int TRANSACTION_canForwardTo = 29;
        static final int TRANSACTION_canPackageQuery = 221;
        static final int TRANSACTION_canRequestPackageInstalls = 163;
        static final int TRANSACTION_canonicalToCurrentPackageNames = 8;
        static final int TRANSACTION_changeMonetizationBadgeState = 245;
        static final int TRANSACTION_checkPackageStartable = 1;
        static final int TRANSACTION_checkPermission = 197;
        static final int TRANSACTION_checkSignatures = 17;
        static final int TRANSACTION_checkUidPermission = 199;
        static final int TRANSACTION_checkUidSignatures = 18;
        static final int TRANSACTION_clearAppCategoryHintDeveloper = 250;
        static final int TRANSACTION_clearAppCategoryHintUser = 248;
        static final int TRANSACTION_clearApplicationProfileData = 100;
        static final int TRANSACTION_clearApplicationUserData = 99;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 68;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 64;
        static final int TRANSACTION_clearPackagePreferredActivities = 61;
        static final int TRANSACTION_clearPackagePreferredActivitiesAsUserForMDM = 217;
        static final int TRANSACTION_clearPersistentPreferredActivity = 65;
        static final int TRANSACTION_createEncAppData = 235;
        static final int TRANSACTION_currentToCanonicalPackageNames = 7;
        static final int TRANSACTION_deleteApplicationCacheFiles = 97;
        static final int TRANSACTION_deleteApplicationCacheFilesAsUser = 98;
        static final int TRANSACTION_deleteExistingPackageAsUser = 53;
        static final int TRANSACTION_deletePackageAsUser = 51;
        static final int TRANSACTION_deletePackageVersioned = 52;
        static final int TRANSACTION_deletePreloadsFileCache = 164;
        static final int TRANSACTION_enterSafeMode = 107;
        static final int TRANSACTION_extendVerificationTimeout = 126;
        static final int TRANSACTION_findPersistentPreferredActivity = 28;
        static final int TRANSACTION_finishPackageInstall = 47;
        static final int TRANSACTION_flushPackageRestrictionsAsUser = 93;
        static final int TRANSACTION_freeStorage = 96;
        static final int TRANSACTION_freeStorageAndNotify = 95;
        static final int TRANSACTION_getActivityInfo = 11;
        static final int TRANSACTION_getAllApexDirectories = 233;
        static final int TRANSACTION_getAllIntentFilters = 131;
        static final int TRANSACTION_getAllPackages = 19;
        static final int TRANSACTION_getAppCategoryHintUserMap = 251;
        static final int TRANSACTION_getAppCategoryInfos = 252;
        static final int TRANSACTION_getAppMetadataFd = 37;
        static final int TRANSACTION_getAppMetadataSource = 228;
        static final int TRANSACTION_getAppOpPermissionPackages = 192;
        static final int TRANSACTION_getAppPredictionServicePackageName = 179;
        static final int TRANSACTION_getApplicationEnabledSetting = 91;
        static final int TRANSACTION_getApplicationHiddenSettingAsUser = 137;
        static final int TRANSACTION_getApplicationInfo = 9;
        static final int TRANSACTION_getArchivedAppIcon = 226;
        static final int TRANSACTION_getArchivedPackage = 225;
        static final int TRANSACTION_getArtManager = 169;
        static final int TRANSACTION_getAttentionServicePackageName = 176;
        static final int TRANSACTION_getBlockUninstallForUser = 142;
        static final int TRANSACTION_getChangedPackages = 158;
        static final int TRANSACTION_getComponentEnabledSetting = 89;
        static final int TRANSACTION_getDeclaredSharedLibraries = 162;
        static final int TRANSACTION_getDefaultAppsBackup = 79;
        static final int TRANSACTION_getDefaultTextClassifierPackageName = 174;
        static final int TRANSACTION_getDomainVerificationAgent = 229;
        static final int TRANSACTION_getDomainVerificationBackup = 81;
        static final int TRANSACTION_getFlagsForUid = 24;
        static final int TRANSACTION_getGrantedPermissionsForMDM = 216;
        static final int TRANSACTION_getHarmfulAppWarning = 171;
        static final int TRANSACTION_getHoldLockToken = 209;
        static final int TRANSACTION_getHomeActivities = 83;
        static final int TRANSACTION_getIncidentReportApproverPackageName = 182;
        static final int TRANSACTION_getInitialNonStoppedSystemPackages = 106;
        static final int TRANSACTION_getInstallLocation = 123;
        static final int TRANSACTION_getInstallReason = 160;
        static final int TRANSACTION_getInstallSourceInfo = 55;
        static final int TRANSACTION_getInstalledApplications = 39;
        static final int TRANSACTION_getInstalledModules = 185;
        static final int TRANSACTION_getInstalledPackages = 36;
        static final int TRANSACTION_getInstallerPackageName = 54;
        static final int TRANSACTION_getInstantAppAndroidId = 168;
        static final int TRANSACTION_getInstantAppCookie = 150;
        static final int TRANSACTION_getInstantAppIcon = 152;
        static final int TRANSACTION_getInstantAppInstallerComponent = 167;
        static final int TRANSACTION_getInstantAppResolverComponent = 165;
        static final int TRANSACTION_getInstantAppResolverSettingsComponent = 166;
        static final int TRANSACTION_getInstantApps = 149;
        static final int TRANSACTION_getInstrumentationInfoAsUser = 45;
        static final int TRANSACTION_getIntentFilterVerifications = 130;
        static final int TRANSACTION_getIntentVerificationStatus = 128;
        static final int TRANSACTION_getKeySetByAlias = 143;
        static final int TRANSACTION_getLastChosenActivity = 57;
        static final int TRANSACTION_getLaunchIntentSenderForPackage = 191;
        static final int TRANSACTION_getMetadataForIconTray = 238;
        static final int TRANSACTION_getMimeGroup = 205;
        static final int TRANSACTION_getModuleInfo = 186;
        static final int TRANSACTION_getMoveStatus = 116;
        static final int TRANSACTION_getNameForUid = 21;
        static final int TRANSACTION_getNamesForUids = 22;
        static final int TRANSACTION_getPackageGids = 6;
        static final int TRANSACTION_getPackageGrantedPermissionsForMDM = 215;
        static final int TRANSACTION_getPackageInfo = 3;
        static final int TRANSACTION_getPackageInfoVersioned = 4;
        static final int TRANSACTION_getPackageInstaller = 140;
        static final int TRANSACTION_getPackageListForDualDarPolicy = 234;
        static final int TRANSACTION_getPackageSizeInfo = 101;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 20;
        static final int TRANSACTION_getPackagesHoldingPermissions = 38;
        static final int TRANSACTION_getPageSizeCompatWarningMessage = 232;
        static final int TRANSACTION_getPermissionControllerPackageName = 147;
        static final int TRANSACTION_getPermissionGroupInfo = 193;
        static final int TRANSACTION_getPersistentApplications = 40;
        static final int TRANSACTION_getPreferredActivities = 62;
        static final int TRANSACTION_getPreferredActivityBackup = 77;
        static final int TRANSACTION_getPrivateFlagsForUid = 25;
        static final int TRANSACTION_getPropertyAsUser = 211;
        static final int TRANSACTION_getProviderInfo = 15;
        static final int TRANSACTION_getReceiverInfo = 13;
        static final int TRANSACTION_getRequestedRuntimePermissionsForMDM = 220;
        static final int TRANSACTION_getRotationResolverPackageName = 177;
        static final int TRANSACTION_getRuntimePermissionsVersion = 187;
        static final int TRANSACTION_getSdkSandboxPackageName = 148;
        static final int TRANSACTION_getServiceInfo = 14;
        static final int TRANSACTION_getServicesSystemSharedLibraryPackageName = 156;
        static final int TRANSACTION_getSetupWizardPackageName = 181;
        static final int TRANSACTION_getSharedLibraries = 161;
        static final int TRANSACTION_getSharedSystemSharedLibraryPackageName = 157;
        static final int TRANSACTION_getSigningKeySet = 144;
        static final int TRANSACTION_getSplashScreenTheme = 201;
        static final int TRANSACTION_getSuspendedPackageAppExtras = 75;
        static final int TRANSACTION_getSuspendingPackage = 76;
        static final int TRANSACTION_getSystemAvailableFeatures = 104;
        static final int TRANSACTION_getSystemCaptionsServicePackageName = 180;
        static final int TRANSACTION_getSystemSharedLibraryNames = 102;
        static final int TRANSACTION_getSystemSharedLibraryNamesAndPaths = 103;
        static final int TRANSACTION_getSystemTextClassifierPackageName = 175;
        static final int TRANSACTION_getTargetSdkVersion = 10;
        static final int TRANSACTION_getUidForSharedUser = 23;
        static final int TRANSACTION_getUnknownSourcePackagesAsUser = 242;
        static final int TRANSACTION_getUnsuspendablePackagesForUser = 71;
        static final int TRANSACTION_getUserMinAspectRatio = 203;
        static final int TRANSACTION_getVerifierDeviceIdentity = 132;
        static final int TRANSACTION_getWellbeingPackageName = 178;
        static final int TRANSACTION_grantRuntimePermission = 198;
        static final int TRANSACTION_hasSigningCertificate = 172;
        static final int TRANSACTION_hasSystemFeature = 105;
        static final int TRANSACTION_hasSystemUidErrors = 109;
        static final int TRANSACTION_hasUidSigningCertificate = 173;
        static final int TRANSACTION_holdLock = 210;
        static final int TRANSACTION_installExistingPackageAsUser = 124;
        static final int TRANSACTION_isAppArchivable = 227;
        static final int TRANSACTION_isAutoRevokeWhitelisted = 206;
        static final int TRANSACTION_isDeviceUpgrading = 134;
        static final int TRANSACTION_isFirstBoot = 133;
        static final int TRANSACTION_isInstantApp = 153;
        static final int TRANSACTION_isPackageAutoDisabled = 243;
        static final int TRANSACTION_isPackageAvailable = 2;
        static final int TRANSACTION_isPackageDeviceAdminOnAnyUser = 159;
        static final int TRANSACTION_isPackageQuarantinedForUser = 73;
        static final int TRANSACTION_isPackageSignedByKeySet = 145;
        static final int TRANSACTION_isPackageSignedByKeySetExactly = 146;
        static final int TRANSACTION_isPackageStateProtected = 183;
        static final int TRANSACTION_isPackageStoppedForUser = 74;
        static final int TRANSACTION_isPackageSuspendedForUser = 72;
        static final int TRANSACTION_isPageSizeCompatEnabled = 231;
        static final int TRANSACTION_isProtectedBroadcast = 16;
        static final int TRANSACTION_isSafeMode = 108;
        static final int TRANSACTION_isStorageLow = 135;
        static final int TRANSACTION_isSystemCompressedPackage = 244;
        static final int TRANSACTION_isUidPrivileged = 26;
        static final int TRANSACTION_isUnknownSourcePackage = 241;
        static final int TRANSACTION_logAppProcessStartIfNeeded = 92;
        static final int TRANSACTION_makeProviderVisible = 207;
        static final int TRANSACTION_makeUidVisible = 208;
        static final int TRANSACTION_movePackage = 119;
        static final int TRANSACTION_movePackageToSd = 120;
        static final int TRANSACTION_movePrimaryStorage = 121;
        static final int TRANSACTION_notifyDexLoad = 111;
        static final int TRANSACTION_notifyPackageUse = 110;
        static final int TRANSACTION_notifyPackagesReplacedReceived = 189;
        static final int TRANSACTION_overrideLabelAndIcon = 85;
        static final int TRANSACTION_performDexOptForADCP = 115;
        static final int TRANSACTION_performDexOptMode = 113;
        static final int TRANSACTION_performDexOptSecondary = 114;
        static final int TRANSACTION_queryContentProviders = 44;
        static final int TRANSACTION_queryInstrumentationAsUser = 46;
        static final int TRANSACTION_queryIntentActivities = 30;
        static final int TRANSACTION_queryIntentActivityOptions = 31;
        static final int TRANSACTION_queryIntentContentProviders = 35;
        static final int TRANSACTION_queryIntentReceivers = 32;
        static final int TRANSACTION_queryIntentServices = 34;
        static final int TRANSACTION_queryProperty = 212;
        static final int TRANSACTION_querySyncProviders = 43;
        static final int TRANSACTION_registerDexModule = 112;
        static final int TRANSACTION_registerMoveCallback = 117;
        static final int TRANSACTION_registerPackageMonitorCallback = 223;
        static final int TRANSACTION_relinquishUpdateOwnership = 49;
        static final int TRANSACTION_removeCrossProfileIntentFilter = 67;
        static final int TRANSACTION_removeEncPkgDir = 237;
        static final int TRANSACTION_removeEncUserDir = 236;
        static final int TRANSACTION_removePermission = 196;
        static final int TRANSACTION_replacePreferredActivity = 60;
        static final int TRANSACTION_requestPackageChecksums = 190;
        static final int TRANSACTION_resetApplicationPreferences = 56;
        static final int TRANSACTION_resolveContentProvider = 41;
        static final int TRANSACTION_resolveContentProviderForUid = 42;
        static final int TRANSACTION_resolveIntent = 27;
        static final int TRANSACTION_resolveService = 33;
        static final int TRANSACTION_restoreDefaultApps = 80;
        static final int TRANSACTION_restoreDomainVerification = 82;
        static final int TRANSACTION_restoreLabelAndIcon = 86;
        static final int TRANSACTION_restorePreferredActivities = 78;
        static final int TRANSACTION_semIsInstalledPackageHiddenAsUser = 240;
        static final int TRANSACTION_semIsPermissionRevokedByUserFixed = 239;
        static final int TRANSACTION_sendDeviceCustomizationReadyBroadcast = 184;
        static final int TRANSACTION_setAppCategoryHintDeveloper = 249;
        static final int TRANSACTION_setAppCategoryHintUser = 247;
        static final int TRANSACTION_setApplicationCategoryHint = 50;
        static final int TRANSACTION_setApplicationEnabledSetting = 90;
        static final int TRANSACTION_setApplicationHiddenSettingAsUser = 136;
        static final int TRANSACTION_setBlockUninstallForUser = 141;
        static final int TRANSACTION_setComponentEnabledSetting = 87;
        static final int TRANSACTION_setComponentEnabledSettings = 88;
        static final int TRANSACTION_setDistractingPackageRestrictionsAsUser = 69;
        static final int TRANSACTION_setHarmfulAppWarning = 170;
        static final int TRANSACTION_setHomeActivity = 84;
        static final int TRANSACTION_setInstallLocation = 122;
        static final int TRANSACTION_setInstallerPackageName = 48;
        static final int TRANSACTION_setInstantAppCookie = 151;
        static final int TRANSACTION_setKeepUninstalledPackages = 213;
        static final int TRANSACTION_setLastChosenActivity = 58;
        static final int TRANSACTION_setLicensePermissionsForMDM = 214;
        static final int TRANSACTION_setMimeGroup = 200;
        static final int TRANSACTION_setPackageStoppedState = 94;
        static final int TRANSACTION_setPackagesSuspendedAsUser = 70;
        static final int TRANSACTION_setPageSizeAppCompatFlagsSettingsOverride = 230;
        static final int TRANSACTION_setRequiredForSystemUser = 154;
        static final int TRANSACTION_setRuntimePermissionsVersion = 188;
        static final int TRANSACTION_setSplashScreenTheme = 202;
        static final int TRANSACTION_setSystemAppHiddenUntilInstalled = 138;
        static final int TRANSACTION_setSystemAppInstallState = 139;
        static final int TRANSACTION_setUpdateAvailable = 155;
        static final int TRANSACTION_setUserMinAspectRatio = 204;
        static final int TRANSACTION_shouldAppSupportBadgeIcon = 246;
        static final int TRANSACTION_unregisterMoveCallback = 118;
        static final int TRANSACTION_unregisterPackageMonitorCallback = 224;
        static final int TRANSACTION_updateIntentVerificationStatus = 129;
        static final int TRANSACTION_verifyIntentFilter = 127;
        static final int TRANSACTION_verifyPendingInstall = 125;
        static final int TRANSACTION_waitForHandler = 222;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 251;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPackageManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPackageManager)) {
                return (IPackageManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkPackageStartable";
                case 2:
                    return "isPackageAvailable";
                case 3:
                    return "getPackageInfo";
                case 4:
                    return "getPackageInfoVersioned";
                case 5:
                    return "getPackageUid";
                case 6:
                    return "getPackageGids";
                case 7:
                    return "currentToCanonicalPackageNames";
                case 8:
                    return "canonicalToCurrentPackageNames";
                case 9:
                    return "getApplicationInfo";
                case 10:
                    return "getTargetSdkVersion";
                case 11:
                    return "getActivityInfo";
                case 12:
                    return "activitySupportsIntentAsUser";
                case 13:
                    return "getReceiverInfo";
                case 14:
                    return "getServiceInfo";
                case 15:
                    return "getProviderInfo";
                case 16:
                    return "isProtectedBroadcast";
                case 17:
                    return "checkSignatures";
                case 18:
                    return "checkUidSignatures";
                case 19:
                    return "getAllPackages";
                case 20:
                    return "getPackagesForUid";
                case 21:
                    return "getNameForUid";
                case 22:
                    return "getNamesForUids";
                case 23:
                    return "getUidForSharedUser";
                case 24:
                    return "getFlagsForUid";
                case 25:
                    return "getPrivateFlagsForUid";
                case 26:
                    return "isUidPrivileged";
                case 27:
                    return "resolveIntent";
                case 28:
                    return "findPersistentPreferredActivity";
                case 29:
                    return "canForwardTo";
                case 30:
                    return "queryIntentActivities";
                case 31:
                    return "queryIntentActivityOptions";
                case 32:
                    return "queryIntentReceivers";
                case 33:
                    return "resolveService";
                case 34:
                    return "queryIntentServices";
                case 35:
                    return "queryIntentContentProviders";
                case 36:
                    return "getInstalledPackages";
                case 37:
                    return "getAppMetadataFd";
                case 38:
                    return "getPackagesHoldingPermissions";
                case 39:
                    return "getInstalledApplications";
                case 40:
                    return "getPersistentApplications";
                case 41:
                    return "resolveContentProvider";
                case 42:
                    return "resolveContentProviderForUid";
                case 43:
                    return "querySyncProviders";
                case 44:
                    return "queryContentProviders";
                case 45:
                    return "getInstrumentationInfoAsUser";
                case 46:
                    return "queryInstrumentationAsUser";
                case 47:
                    return "finishPackageInstall";
                case 48:
                    return "setInstallerPackageName";
                case 49:
                    return "relinquishUpdateOwnership";
                case 50:
                    return "setApplicationCategoryHint";
                case 51:
                    return "deletePackageAsUser";
                case 52:
                    return "deletePackageVersioned";
                case 53:
                    return "deleteExistingPackageAsUser";
                case 54:
                    return "getInstallerPackageName";
                case 55:
                    return "getInstallSourceInfo";
                case 56:
                    return "resetApplicationPreferences";
                case 57:
                    return "getLastChosenActivity";
                case 58:
                    return "setLastChosenActivity";
                case 59:
                    return "addPreferredActivity";
                case 60:
                    return "replacePreferredActivity";
                case 61:
                    return "clearPackagePreferredActivities";
                case 62:
                    return "getPreferredActivities";
                case 63:
                    return "addPersistentPreferredActivity";
                case 64:
                    return "clearPackagePersistentPreferredActivities";
                case 65:
                    return "clearPersistentPreferredActivity";
                case 66:
                    return "addCrossProfileIntentFilter";
                case 67:
                    return "removeCrossProfileIntentFilter";
                case 68:
                    return "clearCrossProfileIntentFilters";
                case 69:
                    return "setDistractingPackageRestrictionsAsUser";
                case 70:
                    return "setPackagesSuspendedAsUser";
                case 71:
                    return "getUnsuspendablePackagesForUser";
                case 72:
                    return "isPackageSuspendedForUser";
                case 73:
                    return "isPackageQuarantinedForUser";
                case 74:
                    return "isPackageStoppedForUser";
                case 75:
                    return "getSuspendedPackageAppExtras";
                case 76:
                    return "getSuspendingPackage";
                case 77:
                    return "getPreferredActivityBackup";
                case 78:
                    return "restorePreferredActivities";
                case 79:
                    return "getDefaultAppsBackup";
                case 80:
                    return "restoreDefaultApps";
                case 81:
                    return "getDomainVerificationBackup";
                case 82:
                    return "restoreDomainVerification";
                case 83:
                    return "getHomeActivities";
                case 84:
                    return "setHomeActivity";
                case 85:
                    return "overrideLabelAndIcon";
                case 86:
                    return "restoreLabelAndIcon";
                case 87:
                    return "setComponentEnabledSetting";
                case 88:
                    return "setComponentEnabledSettings";
                case 89:
                    return "getComponentEnabledSetting";
                case 90:
                    return "setApplicationEnabledSetting";
                case 91:
                    return "getApplicationEnabledSetting";
                case 92:
                    return "logAppProcessStartIfNeeded";
                case 93:
                    return "flushPackageRestrictionsAsUser";
                case 94:
                    return "setPackageStoppedState";
                case 95:
                    return "freeStorageAndNotify";
                case 96:
                    return "freeStorage";
                case 97:
                    return "deleteApplicationCacheFiles";
                case 98:
                    return "deleteApplicationCacheFilesAsUser";
                case 99:
                    return "clearApplicationUserData";
                case 100:
                    return "clearApplicationProfileData";
                case 101:
                    return "getPackageSizeInfo";
                case 102:
                    return "getSystemSharedLibraryNames";
                case 103:
                    return "getSystemSharedLibraryNamesAndPaths";
                case 104:
                    return "getSystemAvailableFeatures";
                case 105:
                    return "hasSystemFeature";
                case 106:
                    return "getInitialNonStoppedSystemPackages";
                case 107:
                    return "enterSafeMode";
                case 108:
                    return "isSafeMode";
                case 109:
                    return "hasSystemUidErrors";
                case 110:
                    return "notifyPackageUse";
                case 111:
                    return "notifyDexLoad";
                case 112:
                    return "registerDexModule";
                case 113:
                    return "performDexOptMode";
                case 114:
                    return "performDexOptSecondary";
                case 115:
                    return "performDexOptForADCP";
                case 116:
                    return "getMoveStatus";
                case 117:
                    return "registerMoveCallback";
                case 118:
                    return "unregisterMoveCallback";
                case 119:
                    return "movePackage";
                case 120:
                    return "movePackageToSd";
                case 121:
                    return "movePrimaryStorage";
                case 122:
                    return "setInstallLocation";
                case 123:
                    return "getInstallLocation";
                case 124:
                    return "installExistingPackageAsUser";
                case 125:
                    return "verifyPendingInstall";
                case 126:
                    return "extendVerificationTimeout";
                case 127:
                    return "verifyIntentFilter";
                case 128:
                    return "getIntentVerificationStatus";
                case 129:
                    return "updateIntentVerificationStatus";
                case 130:
                    return "getIntentFilterVerifications";
                case 131:
                    return "getAllIntentFilters";
                case 132:
                    return "getVerifierDeviceIdentity";
                case 133:
                    return "isFirstBoot";
                case 134:
                    return "isDeviceUpgrading";
                case 135:
                    return "isStorageLow";
                case 136:
                    return "setApplicationHiddenSettingAsUser";
                case 137:
                    return "getApplicationHiddenSettingAsUser";
                case 138:
                    return "setSystemAppHiddenUntilInstalled";
                case 139:
                    return "setSystemAppInstallState";
                case 140:
                    return "getPackageInstaller";
                case 141:
                    return "setBlockUninstallForUser";
                case 142:
                    return "getBlockUninstallForUser";
                case 143:
                    return "getKeySetByAlias";
                case 144:
                    return "getSigningKeySet";
                case 145:
                    return "isPackageSignedByKeySet";
                case 146:
                    return "isPackageSignedByKeySetExactly";
                case 147:
                    return "getPermissionControllerPackageName";
                case 148:
                    return "getSdkSandboxPackageName";
                case 149:
                    return "getInstantApps";
                case 150:
                    return "getInstantAppCookie";
                case 151:
                    return "setInstantAppCookie";
                case 152:
                    return "getInstantAppIcon";
                case 153:
                    return "isInstantApp";
                case 154:
                    return "setRequiredForSystemUser";
                case 155:
                    return "setUpdateAvailable";
                case 156:
                    return "getServicesSystemSharedLibraryPackageName";
                case 157:
                    return "getSharedSystemSharedLibraryPackageName";
                case 158:
                    return "getChangedPackages";
                case 159:
                    return "isPackageDeviceAdminOnAnyUser";
                case 160:
                    return "getInstallReason";
                case 161:
                    return "getSharedLibraries";
                case 162:
                    return "getDeclaredSharedLibraries";
                case 163:
                    return "canRequestPackageInstalls";
                case 164:
                    return "deletePreloadsFileCache";
                case 165:
                    return "getInstantAppResolverComponent";
                case 166:
                    return "getInstantAppResolverSettingsComponent";
                case 167:
                    return "getInstantAppInstallerComponent";
                case 168:
                    return "getInstantAppAndroidId";
                case 169:
                    return "getArtManager";
                case 170:
                    return "setHarmfulAppWarning";
                case 171:
                    return "getHarmfulAppWarning";
                case 172:
                    return "hasSigningCertificate";
                case 173:
                    return "hasUidSigningCertificate";
                case 174:
                    return "getDefaultTextClassifierPackageName";
                case 175:
                    return "getSystemTextClassifierPackageName";
                case 176:
                    return "getAttentionServicePackageName";
                case 177:
                    return "getRotationResolverPackageName";
                case 178:
                    return "getWellbeingPackageName";
                case 179:
                    return "getAppPredictionServicePackageName";
                case 180:
                    return "getSystemCaptionsServicePackageName";
                case 181:
                    return "getSetupWizardPackageName";
                case 182:
                    return "getIncidentReportApproverPackageName";
                case 183:
                    return "isPackageStateProtected";
                case 184:
                    return "sendDeviceCustomizationReadyBroadcast";
                case 185:
                    return "getInstalledModules";
                case 186:
                    return "getModuleInfo";
                case 187:
                    return "getRuntimePermissionsVersion";
                case 188:
                    return "setRuntimePermissionsVersion";
                case 189:
                    return "notifyPackagesReplacedReceived";
                case 190:
                    return "requestPackageChecksums";
                case 191:
                    return "getLaunchIntentSenderForPackage";
                case 192:
                    return "getAppOpPermissionPackages";
                case 193:
                    return "getPermissionGroupInfo";
                case 194:
                    return "addPermission";
                case 195:
                    return "addPermissionAsync";
                case 196:
                    return "removePermission";
                case 197:
                    return "checkPermission";
                case 198:
                    return "grantRuntimePermission";
                case 199:
                    return "checkUidPermission";
                case 200:
                    return "setMimeGroup";
                case 201:
                    return "getSplashScreenTheme";
                case 202:
                    return "setSplashScreenTheme";
                case 203:
                    return "getUserMinAspectRatio";
                case 204:
                    return "setUserMinAspectRatio";
                case 205:
                    return "getMimeGroup";
                case 206:
                    return "isAutoRevokeWhitelisted";
                case 207:
                    return "makeProviderVisible";
                case 208:
                    return "makeUidVisible";
                case 209:
                    return "getHoldLockToken";
                case 210:
                    return "holdLock";
                case 211:
                    return "getPropertyAsUser";
                case 212:
                    return "queryProperty";
                case 213:
                    return "setKeepUninstalledPackages";
                case 214:
                    return "setLicensePermissionsForMDM";
                case 215:
                    return "getPackageGrantedPermissionsForMDM";
                case 216:
                    return "getGrantedPermissionsForMDM";
                case 217:
                    return "clearPackagePreferredActivitiesAsUserForMDM";
                case 218:
                    return "applyRuntimePermissionsForMDM";
                case 219:
                    return "applyRuntimePermissionsForAllApplicationsForMDM";
                case 220:
                    return "getRequestedRuntimePermissionsForMDM";
                case 221:
                    return "canPackageQuery";
                case 222:
                    return "waitForHandler";
                case 223:
                    return "registerPackageMonitorCallback";
                case 224:
                    return "unregisterPackageMonitorCallback";
                case 225:
                    return "getArchivedPackage";
                case 226:
                    return "getArchivedAppIcon";
                case 227:
                    return "isAppArchivable";
                case 228:
                    return "getAppMetadataSource";
                case 229:
                    return "getDomainVerificationAgent";
                case 230:
                    return "setPageSizeAppCompatFlagsSettingsOverride";
                case 231:
                    return "isPageSizeCompatEnabled";
                case 232:
                    return "getPageSizeCompatWarningMessage";
                case 233:
                    return "getAllApexDirectories";
                case 234:
                    return "getPackageListForDualDarPolicy";
                case 235:
                    return "createEncAppData";
                case 236:
                    return "removeEncUserDir";
                case 237:
                    return "removeEncPkgDir";
                case 238:
                    return "getMetadataForIconTray";
                case 239:
                    return "semIsPermissionRevokedByUserFixed";
                case 240:
                    return "semIsInstalledPackageHiddenAsUser";
                case 241:
                    return "isUnknownSourcePackage";
                case 242:
                    return "getUnknownSourcePackagesAsUser";
                case 243:
                    return "isPackageAutoDisabled";
                case 244:
                    return "isSystemCompressedPackage";
                case 245:
                    return "changeMonetizationBadgeState";
                case 246:
                    return "shouldAppSupportBadgeIcon";
                case 247:
                    return "setAppCategoryHintUser";
                case 248:
                    return "clearAppCategoryHintUser";
                case 249:
                    return "setAppCategoryHintDeveloper";
                case 250:
                    return "clearAppCategoryHintDeveloper";
                case 251:
                    return "getAppCategoryHintUserMap";
                case 252:
                    return "getAppCategoryInfos";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    checkPackageStartable(string, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageAvailable = isPackageAvailable(string2, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageAvailable);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    long j = parcel.readLong();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageInfo packageInfo = getPackageInfo(string3, j, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageInfo, 1);
                    return true;
                case 4:
                    VersionedPackage versionedPackage = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    long j2 = parcel.readLong();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageInfo packageInfoVersioned = getPackageInfoVersioned(versionedPackage, j2, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageInfoVersioned, 1);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    long j3 = parcel.readLong();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageUid = getPackageUid(string4, j3, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageUid);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    long j4 = parcel.readLong();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] packageGids = getPackageGids(string5, j4, i8);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(packageGids);
                    return true;
                case 7:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] strArrCurrentToCanonicalPackageNames = currentToCanonicalPackageNames(strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrCurrentToCanonicalPackageNames);
                    return true;
                case 8:
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] strArrCanonicalToCurrentPackageNames = canonicalToCurrentPackageNames(strArrCreateStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrCanonicalToCurrentPackageNames);
                    return true;
                case 9:
                    String string6 = parcel.readString();
                    long j5 = parcel.readLong();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ApplicationInfo applicationInfo = getApplicationInfo(string6, j5, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationInfo, 1);
                    return true;
                case 10:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int targetSdkVersion = getTargetSdkVersion(string7);
                    parcel2.writeNoException();
                    parcel2.writeInt(targetSdkVersion);
                    return true;
                case 11:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long j6 = parcel.readLong();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityInfo activityInfo = getActivityInfo(componentName, j6, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activityInfo, 1);
                    return true;
                case 12:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string8 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zActivitySupportsIntentAsUser = activitySupportsIntentAsUser(componentName2, intent, string8, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivitySupportsIntentAsUser);
                    return true;
                case 13:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long j7 = parcel.readLong();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityInfo receiverInfo = getReceiverInfo(componentName3, j7, i12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(receiverInfo, 1);
                    return true;
                case 14:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long j8 = parcel.readLong();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ServiceInfo serviceInfo = getServiceInfo(componentName4, j8, i13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfo, 1);
                    return true;
                case 15:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long j9 = parcel.readLong();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ProviderInfo providerInfo = getProviderInfo(componentName5, j9, i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(providerInfo, 1);
                    return true;
                case 16:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProtectedBroadcast = isProtectedBroadcast(string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProtectedBroadcast);
                    return true;
                case 17:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckSignatures = checkSignatures(string10, string11, i15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckSignatures);
                    return true;
                case 18:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckUidSignatures = checkUidSignatures(i16, i17);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckUidSignatures);
                    return true;
                case 19:
                    List<String> allPackages = getAllPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allPackages);
                    return true;
                case 20:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] packagesForUid = getPackagesForUid(i18);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packagesForUid);
                    return true;
                case 21:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String nameForUid = getNameForUid(i19);
                    parcel2.writeNoException();
                    parcel2.writeString(nameForUid);
                    return true;
                case 22:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    String[] namesForUids = getNamesForUids(iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(namesForUids);
                    return true;
                case 23:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidForSharedUser = getUidForSharedUser(string12);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidForSharedUser);
                    return true;
                case 24:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int flagsForUid = getFlagsForUid(i20);
                    parcel2.writeNoException();
                    parcel2.writeInt(flagsForUid);
                    return true;
                case 25:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int privateFlagsForUid = getPrivateFlagsForUid(i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(privateFlagsForUid);
                    return true;
                case 26:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUidPrivileged = isUidPrivileged(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUidPrivileged);
                    return true;
                case 27:
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string13 = parcel.readString();
                    long j10 = parcel.readLong();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo resolveInfoResolveIntent = resolveIntent(intent2, string13, j10, i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveInfoResolveIntent, 1);
                    return true;
                case 28:
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo resolveInfoFindPersistentPreferredActivity = findPersistentPreferredActivity(intent3, i24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveInfoFindPersistentPreferredActivity, 1);
                    return true;
                case 29:
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string14 = parcel.readString();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanForwardTo = canForwardTo(intent4, string14, i25, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanForwardTo);
                    return true;
                case 30:
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string15 = parcel.readString();
                    long j11 = parcel.readLong();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryIntentActivities = queryIntentActivities(intent5, string15, j11, i27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryIntentActivities, 1);
                    return true;
                case 31:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    Intent intent6 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string16 = parcel.readString();
                    long j12 = parcel.readLong();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryIntentActivityOptions = queryIntentActivityOptions(componentName6, intentArr, strArrCreateStringArray3, intent6, string16, j12, i28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryIntentActivityOptions, 1);
                    return true;
                case 32:
                    Intent intent7 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string17 = parcel.readString();
                    long j13 = parcel.readLong();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryIntentReceivers = queryIntentReceivers(intent7, string17, j13, i29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryIntentReceivers, 1);
                    return true;
                case 33:
                    Intent intent8 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string18 = parcel.readString();
                    long j14 = parcel.readLong();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo resolveInfoResolveService = resolveService(intent8, string18, j14, i30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveInfoResolveService, 1);
                    return true;
                case 34:
                    Intent intent9 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string19 = parcel.readString();
                    long j15 = parcel.readLong();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryIntentServices = queryIntentServices(intent9, string19, j15, i31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryIntentServices, 1);
                    return true;
                case 35:
                    Intent intent10 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string20 = parcel.readString();
                    long j16 = parcel.readLong();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryIntentContentProviders = queryIntentContentProviders(intent10, string20, j16, i32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryIntentContentProviders, 1);
                    return true;
                case 36:
                    long j17 = parcel.readLong();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice installedPackages = getInstalledPackages(j17, i33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedPackages, 1);
                    return true;
                case 37:
                    String string21 = parcel.readString();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor appMetadataFd = getAppMetadataFd(string21, i34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appMetadataFd, 1);
                    return true;
                case 38:
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    long j18 = parcel.readLong();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice packagesHoldingPermissions = getPackagesHoldingPermissions(strArrCreateStringArray4, j18, i35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packagesHoldingPermissions, 1);
                    return true;
                case 39:
                    long j19 = parcel.readLong();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice installedApplications = getInstalledApplications(j19, i36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedApplications, 1);
                    return true;
                case 40:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice persistentApplications = getPersistentApplications(i37);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(persistentApplications, 1);
                    return true;
                case 41:
                    String string22 = parcel.readString();
                    long j20 = parcel.readLong();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ProviderInfo providerInfoResolveContentProvider = resolveContentProvider(string22, j20, i38);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(providerInfoResolveContentProvider, 1);
                    return true;
                case 42:
                    String string23 = parcel.readString();
                    long j21 = parcel.readLong();
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ProviderInfo providerInfoResolveContentProviderForUid = resolveContentProviderForUid(string23, j21, i39, i40);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(providerInfoResolveContentProviderForUid, 1);
                    return true;
                case 43:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    querySyncProviders(arrayListCreateStringArrayList, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeStringList(arrayListCreateStringArrayList);
                    parcel2.writeTypedList(arrayListCreateTypedArrayList, 1);
                    return true;
                case 44:
                    String string24 = parcel.readString();
                    int i41 = parcel.readInt();
                    long j22 = parcel.readLong();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryContentProviders = queryContentProviders(string24, i41, j22, string25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryContentProviders, 1);
                    return true;
                case 45:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InstrumentationInfo instrumentationInfoAsUser = getInstrumentationInfoAsUser(componentName7, i42, i43);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instrumentationInfoAsUser, 1);
                    return true;
                case 46:
                    String string26 = parcel.readString();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryInstrumentationAsUser = queryInstrumentationAsUser(string26, i44, i45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryInstrumentationAsUser, 1);
                    return true;
                case 47:
                    int i46 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishPackageInstall(i46, z);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInstallerPackageName(string27, string28);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    relinquishUpdateOwnership(string29);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String string30 = parcel.readString();
                    int i47 = parcel.readInt();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplicationCategoryHint(string30, i47, string31);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    String string32 = parcel.readString();
                    int i48 = parcel.readInt();
                    IPackageDeleteObserver iPackageDeleteObserverAsInterface = IPackageDeleteObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deletePackageAsUser(string32, i48, iPackageDeleteObserverAsInterface, i49, i50);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    VersionedPackage versionedPackage2 = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    IPackageDeleteObserver2 iPackageDeleteObserver2AsInterface = IPackageDeleteObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int i51 = parcel.readInt();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deletePackageVersioned(versionedPackage2, iPackageDeleteObserver2AsInterface, i51, i52);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    VersionedPackage versionedPackage3 = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    IPackageDeleteObserver2 iPackageDeleteObserver2AsInterface2 = IPackageDeleteObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteExistingPackageAsUser(versionedPackage3, iPackageDeleteObserver2AsInterface2, i53);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String installerPackageName = getInstallerPackageName(string33);
                    parcel2.writeNoException();
                    parcel2.writeString(installerPackageName);
                    return true;
                case 55:
                    String string34 = parcel.readString();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InstallSourceInfo installSourceInfo = getInstallSourceInfo(string34, i54);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installSourceInfo, 1);
                    return true;
                case 56:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetApplicationPreferences(i55);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    Intent intent11 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string35 = parcel.readString();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo lastChosenActivity = getLastChosenActivity(intent11, string35, i56);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastChosenActivity, 1);
                    return true;
                case 58:
                    Intent intent12 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string36 = parcel.readString();
                    int i57 = parcel.readInt();
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int i58 = parcel.readInt();
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLastChosenActivity(intent12, string36, i57, intentFilter, i58, componentName8);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    IntentFilter intentFilter2 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int i59 = parcel.readInt();
                    ComponentName[] componentNameArr = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i60 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addPreferredActivity(intentFilter2, i59, componentNameArr, componentName9, i60, z2);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    IntentFilter intentFilter3 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int i61 = parcel.readInt();
                    ComponentName[] componentNameArr2 = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    replacePreferredActivity(intentFilter3, i61, componentNameArr2, componentName10, i62);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearPackagePreferredActivities(string37);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int preferredActivities = getPreferredActivities(arrayList, arrayList2, string38);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredActivities);
                    parcel2.writeTypedList(arrayList, 1);
                    parcel2.writeTypedList(arrayList2, 1);
                    return true;
                case 63:
                    IntentFilter intentFilter4 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPersistentPreferredActivity(intentFilter4, componentName11, i63);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    String string39 = parcel.readString();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPackagePersistentPreferredActivities(string39, i64);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    IntentFilter intentFilter5 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPersistentPreferredActivity(intentFilter5, i65);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    IntentFilter intentFilter6 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    String string40 = parcel.readString();
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addCrossProfileIntentFilter(intentFilter6, string40, i66, i67, i68);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    IntentFilter intentFilter7 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    String string41 = parcel.readString();
                    int i69 = parcel.readInt();
                    int i70 = parcel.readInt();
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveCrossProfileIntentFilter = removeCrossProfileIntentFilter(intentFilter7, string41, i69, i70, i71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveCrossProfileIntentFilter);
                    return true;
                case 68:
                    int i72 = parcel.readInt();
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(i72, string42);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    int i73 = parcel.readInt();
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] distractingPackageRestrictionsAsUser = setDistractingPackageRestrictionsAsUser(strArrCreateStringArray5, i73, i74);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(distractingPackageRestrictionsAsUser);
                    return true;
                case 70:
                    String[] strArrCreateStringArray6 = parcel.createStringArray();
                    boolean z3 = parcel.readBoolean();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SuspendDialogInfo suspendDialogInfo = (SuspendDialogInfo) parcel.readTypedObject(SuspendDialogInfo.CREATOR);
                    int i75 = parcel.readInt();
                    String string43 = parcel.readString();
                    int i76 = parcel.readInt();
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] packagesSuspendedAsUser = setPackagesSuspendedAsUser(strArrCreateStringArray6, z3, persistableBundle, persistableBundle2, suspendDialogInfo, i75, string43, i76, i77);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packagesSuspendedAsUser);
                    return true;
                case 71:
                    String[] strArrCreateStringArray7 = parcel.createStringArray();
                    int i78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] unsuspendablePackagesForUser = getUnsuspendablePackagesForUser(strArrCreateStringArray7, i78);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(unsuspendablePackagesForUser);
                    return true;
                case 72:
                    String string44 = parcel.readString();
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageSuspendedForUser = isPackageSuspendedForUser(string44, i79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageSuspendedForUser);
                    return true;
                case 73:
                    String string45 = parcel.readString();
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageQuarantinedForUser = isPackageQuarantinedForUser(string45, i80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageQuarantinedForUser);
                    return true;
                case 74:
                    String string46 = parcel.readString();
                    int i81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageStoppedForUser = isPackageStoppedForUser(string46, i81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageStoppedForUser);
                    return true;
                case 75:
                    String string47 = parcel.readString();
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle suspendedPackageAppExtras = getSuspendedPackageAppExtras(string47, i82);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(suspendedPackageAppExtras, 1);
                    return true;
                case 76:
                    String string48 = parcel.readString();
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String suspendingPackage = getSuspendingPackage(string48, i83);
                    parcel2.writeNoException();
                    parcel2.writeString(suspendingPackage);
                    return true;
                case 77:
                    int i84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] preferredActivityBackup = getPreferredActivityBackup(i84);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(preferredActivityBackup);
                    return true;
                case 78:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restorePreferredActivities(bArrCreateByteArray, i85);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] defaultAppsBackup = getDefaultAppsBackup(i86);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(defaultAppsBackup);
                    return true;
                case 80:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreDefaultApps(bArrCreateByteArray2, i87);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    int i88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] domainVerificationBackup = getDomainVerificationBackup(i88);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(domainVerificationBackup);
                    return true;
                case 82:
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    int i89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreDomainVerification(bArrCreateByteArray3, i89);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    ArrayList arrayList3 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    ComponentName homeActivities = getHomeActivities(arrayList3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(homeActivities, 1);
                    parcel2.writeTypedList(arrayList3, 1);
                    return true;
                case 84:
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i90 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHomeActivity(componentName12, i90);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string49 = parcel.readString();
                    int i91 = parcel.readInt();
                    int i92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideLabelAndIcon(componentName13, string49, i91, i92);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreLabelAndIcon(componentName14, i93);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    ComponentName componentName15 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i94 = parcel.readInt();
                    int i95 = parcel.readInt();
                    int i96 = parcel.readInt();
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setComponentEnabledSetting(componentName15, i94, i95, i96, string50);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(PackageManager.ComponentEnabledSetting.CREATOR);
                    int i97 = parcel.readInt();
                    String string51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setComponentEnabledSettings(arrayListCreateTypedArrayList2, i97, string51);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    ComponentName componentName16 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int componentEnabledSetting = getComponentEnabledSetting(componentName16, i98);
                    parcel2.writeNoException();
                    parcel2.writeInt(componentEnabledSetting);
                    return true;
                case 90:
                    String string52 = parcel.readString();
                    int i99 = parcel.readInt();
                    int i100 = parcel.readInt();
                    int i101 = parcel.readInt();
                    String string53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplicationEnabledSetting(string52, i99, i100, i101, string53);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    String string54 = parcel.readString();
                    int i102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applicationEnabledSetting = getApplicationEnabledSetting(string54, i102);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationEnabledSetting);
                    return true;
                case 92:
                    String string55 = parcel.readString();
                    String string56 = parcel.readString();
                    int i103 = parcel.readInt();
                    String string57 = parcel.readString();
                    String string58 = parcel.readString();
                    int i104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logAppProcessStartIfNeeded(string55, string56, i103, string57, string58, i104);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int i105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    flushPackageRestrictionsAsUser(i105);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    String string59 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    int i106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageStoppedState(string59, z4, i106);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    String string60 = parcel.readString();
                    long j23 = parcel.readLong();
                    int i107 = parcel.readInt();
                    IPackageDataObserver iPackageDataObserverAsInterface = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    freeStorageAndNotify(string60, j23, i107, iPackageDataObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    String string61 = parcel.readString();
                    long j24 = parcel.readLong();
                    int i108 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    freeStorage(string61, j24, i108, intentSender);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    String string62 = parcel.readString();
                    IPackageDataObserver iPackageDataObserverAsInterface2 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteApplicationCacheFiles(string62, iPackageDataObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    String string63 = parcel.readString();
                    int i109 = parcel.readInt();
                    IPackageDataObserver iPackageDataObserverAsInterface3 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteApplicationCacheFilesAsUser(string63, i109, iPackageDataObserverAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    String string64 = parcel.readString();
                    IPackageDataObserver iPackageDataObserverAsInterface4 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearApplicationUserData(string64, iPackageDataObserverAsInterface4, i110);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearApplicationProfileData(string65);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    String string66 = parcel.readString();
                    int i111 = parcel.readInt();
                    IPackageStatsObserver iPackageStatsObserverAsInterface = IPackageStatsObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getPackageSizeInfo(string66, i111, iPackageStatsObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    String[] systemSharedLibraryNames = getSystemSharedLibraryNames();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(systemSharedLibraryNames);
                    return true;
                case 103:
                    Map<String, String> systemSharedLibraryNamesAndPaths = getSystemSharedLibraryNamesAndPaths();
                    parcel2.writeNoException();
                    if (systemSharedLibraryNamesAndPaths == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(systemSharedLibraryNamesAndPaths.size());
                        systemSharedLibraryNamesAndPaths.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.lambda$onTransact$0(parcel2, (String) obj, (String) obj2);
                            }
                        });
                    }
                    return true;
                case 104:
                    ParceledListSlice systemAvailableFeatures = getSystemAvailableFeatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemAvailableFeatures, 1);
                    return true;
                case 105:
                    String string67 = parcel.readString();
                    int i112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasSystemFeature = hasSystemFeature(string67, i112);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSystemFeature);
                    return true;
                case 106:
                    List<String> initialNonStoppedSystemPackages = getInitialNonStoppedSystemPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(initialNonStoppedSystemPackages);
                    return true;
                case 107:
                    enterSafeMode();
                    parcel2.writeNoException();
                    return true;
                case 108:
                    boolean zIsSafeMode = isSafeMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSafeMode);
                    return true;
                case 109:
                    boolean zHasSystemUidErrors = hasSystemUidErrors();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSystemUidErrors);
                    return true;
                case 110:
                    String string68 = parcel.readString();
                    int i113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPackageUse(string68, i113);
                    return true;
                case 111:
                    String string69 = parcel.readString();
                    int i114 = parcel.readInt();
                    final HashMap map = i114 < 0 ? null : new HashMap();
                    IntStream.range(0, i114).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i115) {
                            Parcel parcel3 = parcel;
                            map.put(parcel3.readString(), parcel3.readString());
                        }
                    });
                    String string70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyDexLoad(string69, map, string70);
                    return true;
                case 112:
                    String string71 = parcel.readString();
                    String string72 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    IDexModuleRegisterCallback iDexModuleRegisterCallbackAsInterface = IDexModuleRegisterCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDexModule(string71, string72, z5, iDexModuleRegisterCallbackAsInterface);
                    return true;
                case 113:
                    String string73 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    String string74 = parcel.readString();
                    boolean z7 = parcel.readBoolean();
                    boolean z8 = parcel.readBoolean();
                    String string75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zPerformDexOptMode = performDexOptMode(string73, z6, string74, z7, z8, string75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPerformDexOptMode);
                    return true;
                case 114:
                    String string76 = parcel.readString();
                    String string77 = parcel.readString();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zPerformDexOptSecondary = performDexOptSecondary(string76, string77, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPerformDexOptSecondary);
                    return true;
                case 115:
                    String string78 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iPerformDexOptForADCP = performDexOptForADCP(string78, z10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPerformDexOptForADCP);
                    return true;
                case 116:
                    int i115 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int moveStatus = getMoveStatus(i115);
                    parcel2.writeNoException();
                    parcel2.writeInt(moveStatus);
                    return true;
                case 117:
                    IPackageMoveObserver iPackageMoveObserverAsInterface = IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMoveCallback(iPackageMoveObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    IPackageMoveObserver iPackageMoveObserverAsInterface2 = IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterMoveCallback(iPackageMoveObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    String string79 = parcel.readString();
                    String string80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMovePackage = movePackage(string79, string80);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMovePackage);
                    return true;
                case 120:
                    String string81 = parcel.readString();
                    String string82 = parcel.readString();
                    IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserverAsInterface = IMemorySaverPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iMovePackageToSd = movePackageToSd(string81, string82, iMemorySaverPackageMoveObserverAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMovePackageToSd);
                    return true;
                case 121:
                    String string83 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMovePrimaryStorage = movePrimaryStorage(string83);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMovePrimaryStorage);
                    return true;
                case 122:
                    int i116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean installLocation = setInstallLocation(i116);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installLocation);
                    return true;
                case 123:
                    int installLocation2 = getInstallLocation();
                    parcel2.writeNoException();
                    parcel2.writeInt(installLocation2);
                    return true;
                case 124:
                    String string84 = parcel.readString();
                    int i117 = parcel.readInt();
                    int i118 = parcel.readInt();
                    int i119 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int iInstallExistingPackageAsUser = installExistingPackageAsUser(string84, i117, i118, i119, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInstallExistingPackageAsUser);
                    return true;
                case 125:
                    int i120 = parcel.readInt();
                    int i121 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    verifyPendingInstall(i120, i121);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    int i122 = parcel.readInt();
                    int i123 = parcel.readInt();
                    long j25 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    extendVerificationTimeout(i122, i123, j25);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    int i124 = parcel.readInt();
                    int i125 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    verifyIntentFilter(i124, i125, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    String string85 = parcel.readString();
                    int i126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int intentVerificationStatus = getIntentVerificationStatus(string85, i126);
                    parcel2.writeNoException();
                    parcel2.writeInt(intentVerificationStatus);
                    return true;
                case 129:
                    String string86 = parcel.readString();
                    int i127 = parcel.readInt();
                    int i128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateIntentVerificationStatus = updateIntentVerificationStatus(string86, i127, i128);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateIntentVerificationStatus);
                    return true;
                case 130:
                    String string87 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice intentFilterVerifications = getIntentFilterVerifications(string87);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentFilterVerifications, 1);
                    return true;
                case 131:
                    String string88 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice allIntentFilters = getAllIntentFilters(string88);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allIntentFilters, 1);
                    return true;
                case 132:
                    VerifierDeviceIdentity verifierDeviceIdentity = getVerifierDeviceIdentity();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifierDeviceIdentity, 1);
                    return true;
                case 133:
                    boolean zIsFirstBoot = isFirstBoot();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFirstBoot);
                    return true;
                case 134:
                    boolean zIsDeviceUpgrading = isDeviceUpgrading();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceUpgrading);
                    return true;
                case 135:
                    boolean zIsStorageLow = isStorageLow();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStorageLow);
                    return true;
                case 136:
                    String string89 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    int i129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationHiddenSettingAsUser = setApplicationHiddenSettingAsUser(string89, z11, i129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationHiddenSettingAsUser);
                    return true;
                case 137:
                    String string90 = parcel.readString();
                    int i130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationHiddenSettingAsUser2 = getApplicationHiddenSettingAsUser(string90, i130);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationHiddenSettingAsUser2);
                    return true;
                case 138:
                    String string91 = parcel.readString();
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSystemAppHiddenUntilInstalled(string91, z12);
                    parcel2.writeNoException();
                    return true;
                case 139:
                    String string92 = parcel.readString();
                    boolean z13 = parcel.readBoolean();
                    int i131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean systemAppInstallState = setSystemAppInstallState(string92, z13, i131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(systemAppInstallState);
                    return true;
                case 140:
                    IPackageInstaller packageInstaller = getPackageInstaller();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(packageInstaller);
                    return true;
                case 141:
                    String string93 = parcel.readString();
                    boolean z14 = parcel.readBoolean();
                    int i132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean blockUninstallForUser = setBlockUninstallForUser(string93, z14, i132);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockUninstallForUser);
                    return true;
                case 142:
                    String string94 = parcel.readString();
                    int i133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean blockUninstallForUser2 = getBlockUninstallForUser(string94, i133);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockUninstallForUser2);
                    return true;
                case 143:
                    String string95 = parcel.readString();
                    String string96 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeySet keySetByAlias = getKeySetByAlias(string95, string96);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keySetByAlias, 1);
                    return true;
                case 144:
                    String string97 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeySet signingKeySet = getSigningKeySet(string97);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(signingKeySet, 1);
                    return true;
                case 145:
                    String string98 = parcel.readString();
                    KeySet keySet = (KeySet) parcel.readTypedObject(KeySet.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageSignedByKeySet = isPackageSignedByKeySet(string98, keySet);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageSignedByKeySet);
                    return true;
                case 146:
                    String string99 = parcel.readString();
                    KeySet keySet2 = (KeySet) parcel.readTypedObject(KeySet.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageSignedByKeySetExactly = isPackageSignedByKeySetExactly(string99, keySet2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageSignedByKeySetExactly);
                    return true;
                case 147:
                    String permissionControllerPackageName = getPermissionControllerPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(permissionControllerPackageName);
                    return true;
                case 148:
                    String sdkSandboxPackageName = getSdkSandboxPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(sdkSandboxPackageName);
                    return true;
                case 149:
                    int i134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice instantApps = getInstantApps(i134);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantApps, 1);
                    return true;
                case 150:
                    String string100 = parcel.readString();
                    int i135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] instantAppCookie = getInstantAppCookie(string100, i135);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(instantAppCookie);
                    return true;
                case 151:
                    String string101 = parcel.readString();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    int i136 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean instantAppCookie2 = setInstantAppCookie(string101, bArrCreateByteArray4, i136);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(instantAppCookie2);
                    return true;
                case 152:
                    String string102 = parcel.readString();
                    int i137 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap instantAppIcon = getInstantAppIcon(string102, i137);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppIcon, 1);
                    return true;
                case 153:
                    String string103 = parcel.readString();
                    int i138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsInstantApp = isInstantApp(string103, i138);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInstantApp);
                    return true;
                case 154:
                    String string104 = parcel.readString();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requiredForSystemUser = setRequiredForSystemUser(string104, z15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requiredForSystemUser);
                    return true;
                case 155:
                    String string105 = parcel.readString();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUpdateAvailable(string105, z16);
                    parcel2.writeNoException();
                    return true;
                case 156:
                    String servicesSystemSharedLibraryPackageName = getServicesSystemSharedLibraryPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(servicesSystemSharedLibraryPackageName);
                    return true;
                case 157:
                    String sharedSystemSharedLibraryPackageName = getSharedSystemSharedLibraryPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(sharedSystemSharedLibraryPackageName);
                    return true;
                case 158:
                    int i139 = parcel.readInt();
                    int i140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ChangedPackages changedPackages = getChangedPackages(i139, i140);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(changedPackages, 1);
                    return true;
                case 159:
                    String string106 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageDeviceAdminOnAnyUser = isPackageDeviceAdminOnAnyUser(string106);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageDeviceAdminOnAnyUser);
                    return true;
                case 160:
                    String string107 = parcel.readString();
                    int i141 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int installReason = getInstallReason(string107, i141);
                    parcel2.writeNoException();
                    parcel2.writeInt(installReason);
                    return true;
                case 161:
                    String string108 = parcel.readString();
                    long j26 = parcel.readLong();
                    int i142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice sharedLibraries = getSharedLibraries(string108, j26, i142);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sharedLibraries, 1);
                    return true;
                case 162:
                    String string109 = parcel.readString();
                    long j27 = parcel.readLong();
                    int i143 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice declaredSharedLibraries = getDeclaredSharedLibraries(string109, j27, i143);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(declaredSharedLibraries, 1);
                    return true;
                case 163:
                    String string110 = parcel.readString();
                    int i144 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanRequestPackageInstalls = canRequestPackageInstalls(string110, i144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanRequestPackageInstalls);
                    return true;
                case 164:
                    deletePreloadsFileCache();
                    parcel2.writeNoException();
                    return true;
                case 165:
                    ComponentName instantAppResolverComponent = getInstantAppResolverComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppResolverComponent, 1);
                    return true;
                case 166:
                    ComponentName instantAppResolverSettingsComponent = getInstantAppResolverSettingsComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppResolverSettingsComponent, 1);
                    return true;
                case 167:
                    ComponentName instantAppInstallerComponent = getInstantAppInstallerComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppInstallerComponent, 1);
                    return true;
                case 168:
                    String string111 = parcel.readString();
                    int i145 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String instantAppAndroidId = getInstantAppAndroidId(string111, i145);
                    parcel2.writeNoException();
                    parcel2.writeString(instantAppAndroidId);
                    return true;
                case 169:
                    IArtManager artManager = getArtManager();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(artManager);
                    return true;
                case 170:
                    String string112 = parcel.readString();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int i146 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHarmfulAppWarning(string112, charSequence, i146);
                    parcel2.writeNoException();
                    return true;
                case 171:
                    String string113 = parcel.readString();
                    int i147 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence harmfulAppWarning = getHarmfulAppWarning(string113, i147);
                    parcel2.writeNoException();
                    if (harmfulAppWarning != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(harmfulAppWarning, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 172:
                    String string114 = parcel.readString();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    int i148 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasSigningCertificate = hasSigningCertificate(string114, bArrCreateByteArray5, i148);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSigningCertificate);
                    return true;
                case 173:
                    int i149 = parcel.readInt();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    int i150 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasUidSigningCertificate = hasUidSigningCertificate(i149, bArrCreateByteArray6, i150);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasUidSigningCertificate);
                    return true;
                case 174:
                    String defaultTextClassifierPackageName = getDefaultTextClassifierPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(defaultTextClassifierPackageName);
                    return true;
                case 175:
                    String systemTextClassifierPackageName = getSystemTextClassifierPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(systemTextClassifierPackageName);
                    return true;
                case 176:
                    String attentionServicePackageName = getAttentionServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(attentionServicePackageName);
                    return true;
                case 177:
                    String rotationResolverPackageName = getRotationResolverPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(rotationResolverPackageName);
                    return true;
                case 178:
                    String wellbeingPackageName = getWellbeingPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(wellbeingPackageName);
                    return true;
                case 179:
                    String appPredictionServicePackageName = getAppPredictionServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(appPredictionServicePackageName);
                    return true;
                case 180:
                    String systemCaptionsServicePackageName = getSystemCaptionsServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(systemCaptionsServicePackageName);
                    return true;
                case 181:
                    String setupWizardPackageName = getSetupWizardPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(setupWizardPackageName);
                    return true;
                case 182:
                    String incidentReportApproverPackageName = getIncidentReportApproverPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(incidentReportApproverPackageName);
                    return true;
                case 183:
                    String string115 = parcel.readString();
                    int i151 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageStateProtected = isPackageStateProtected(string115, i151);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageStateProtected);
                    return true;
                case 184:
                    sendDeviceCustomizationReadyBroadcast();
                    parcel2.writeNoException();
                    return true;
                case 185:
                    int i152 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ModuleInfo> installedModules = getInstalledModules(i152);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(installedModules, 1);
                    return true;
                case 186:
                    String string116 = parcel.readString();
                    int i153 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ModuleInfo moduleInfo = getModuleInfo(string116, i153);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(moduleInfo, 1);
                    return true;
                case 187:
                    int i154 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int runtimePermissionsVersion = getRuntimePermissionsVersion(i154);
                    parcel2.writeNoException();
                    parcel2.writeInt(runtimePermissionsVersion);
                    return true;
                case 188:
                    int i155 = parcel.readInt();
                    int i156 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRuntimePermissionsVersion(i155, i156);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    String[] strArrCreateStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    notifyPackagesReplacedReceived(strArrCreateStringArray8);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    String string117 = parcel.readString();
                    boolean z17 = parcel.readBoolean();
                    int i157 = parcel.readInt();
                    int i158 = parcel.readInt();
                    ArrayList arrayList4 = parcel.readArrayList(getClass().getClassLoader());
                    IOnChecksumsReadyListener iOnChecksumsReadyListenerAsInterface = IOnChecksumsReadyListener.Stub.asInterface(parcel.readStrongBinder());
                    int i159 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestPackageChecksums(string117, z17, i157, i158, arrayList4, iOnChecksumsReadyListenerAsInterface, i159);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    String string118 = parcel.readString();
                    String string119 = parcel.readString();
                    String string120 = parcel.readString();
                    int i160 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IntentSender launchIntentSenderForPackage = getLaunchIntentSenderForPackage(string118, string119, string120, i160);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launchIntentSenderForPackage, 1);
                    return true;
                case 192:
                    String string121 = parcel.readString();
                    int i161 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] appOpPermissionPackages = getAppOpPermissionPackages(string121, i161);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(appOpPermissionPackages);
                    return true;
                case 193:
                    String string122 = parcel.readString();
                    int i162 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionGroupInfo permissionGroupInfo = getPermissionGroupInfo(string122, i162);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionGroupInfo, 1);
                    return true;
                case 194:
                    PermissionInfo permissionInfo = (PermissionInfo) parcel.readTypedObject(PermissionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddPermission = addPermission(permissionInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPermission);
                    return true;
                case 195:
                    PermissionInfo permissionInfo2 = (PermissionInfo) parcel.readTypedObject(PermissionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddPermissionAsync = addPermissionAsync(permissionInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPermissionAsync);
                    return true;
                case 196:
                    String string123 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePermission(string123);
                    parcel2.writeNoException();
                    return true;
                case 197:
                    String string124 = parcel.readString();
                    String string125 = parcel.readString();
                    int i163 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckPermission = checkPermission(string124, string125, i163);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckPermission);
                    return true;
                case 198:
                    String string126 = parcel.readString();
                    String string127 = parcel.readString();
                    int i164 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(string126, string127, i164);
                    parcel2.writeNoException();
                    return true;
                case 199:
                    String string128 = parcel.readString();
                    int i165 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckUidPermission = checkUidPermission(string128, i165);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckUidPermission);
                    return true;
                case 200:
                    String string129 = parcel.readString();
                    String string130 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setMimeGroup(string129, string130, arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 201:
                    String string131 = parcel.readString();
                    int i166 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String splashScreenTheme = getSplashScreenTheme(string131, i166);
                    parcel2.writeNoException();
                    parcel2.writeString(splashScreenTheme);
                    return true;
                case 202:
                    String string132 = parcel.readString();
                    String string133 = parcel.readString();
                    int i167 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSplashScreenTheme(string132, string133, i167);
                    parcel2.writeNoException();
                    return true;
                case 203:
                    String string134 = parcel.readString();
                    int i168 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userMinAspectRatio = getUserMinAspectRatio(string134, i168);
                    parcel2.writeNoException();
                    parcel2.writeInt(userMinAspectRatio);
                    return true;
                case 204:
                    String string135 = parcel.readString();
                    int i169 = parcel.readInt();
                    int i170 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserMinAspectRatio(string135, i169, i170);
                    parcel2.writeNoException();
                    return true;
                case 205:
                    String string136 = parcel.readString();
                    String string137 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> mimeGroup = getMimeGroup(string136, string137);
                    parcel2.writeNoException();
                    parcel2.writeStringList(mimeGroup);
                    return true;
                case 206:
                    String string138 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAutoRevokeWhitelisted = isAutoRevokeWhitelisted(string138);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAutoRevokeWhitelisted);
                    return true;
                case 207:
                    int i171 = parcel.readInt();
                    String string139 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    makeProviderVisible(i171, string139);
                    parcel2.writeNoException();
                    return true;
                case 208:
                    int i172 = parcel.readInt();
                    int i173 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    makeUidVisible(i172, i173);
                    parcel2.writeNoException();
                    return true;
                case 209:
                    IBinder holdLockToken = getHoldLockToken();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(holdLockToken);
                    return true;
                case 210:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i174 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(strongBinder, i174);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    String string140 = parcel.readString();
                    String string141 = parcel.readString();
                    String string142 = parcel.readString();
                    int i175 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageManager.Property propertyAsUser = getPropertyAsUser(string140, string141, string142, i175);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(propertyAsUser, 1);
                    return true;
                case 212:
                    String string143 = parcel.readString();
                    int i176 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryProperty = queryProperty(string143, i176);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryProperty, 1);
                    return true;
                case 213:
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setKeepUninstalledPackages(arrayListCreateStringArrayList5);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    String string144 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int licensePermissionsForMDM = setLicensePermissionsForMDM(string144);
                    parcel2.writeNoException();
                    parcel2.writeInt(licensePermissionsForMDM);
                    return true;
                case 215:
                    String string145 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageGrantedPermissionsForMDM = getPackageGrantedPermissionsForMDM(string145);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageGrantedPermissionsForMDM);
                    return true;
                case 216:
                    String string146 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> grantedPermissionsForMDM = getGrantedPermissionsForMDM(string146);
                    parcel2.writeNoException();
                    parcel2.writeStringList(grantedPermissionsForMDM);
                    return true;
                case 217:
                    String string147 = parcel.readString();
                    int i177 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPackagePreferredActivitiesAsUserForMDM(string147, i177);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    String string148 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    int i178 = parcel.readInt();
                    int i179 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zApplyRuntimePermissionsForMDM = applyRuntimePermissionsForMDM(string148, arrayListCreateStringArrayList6, i178, i179);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zApplyRuntimePermissionsForMDM);
                    return true;
                case 219:
                    int i180 = parcel.readInt();
                    int i181 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zApplyRuntimePermissionsForAllApplicationsForMDM = applyRuntimePermissionsForAllApplicationsForMDM(i180, i181);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zApplyRuntimePermissionsForAllApplicationsForMDM);
                    return true;
                case 220:
                    String string149 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> requestedRuntimePermissionsForMDM = getRequestedRuntimePermissionsForMDM(string149);
                    parcel2.writeNoException();
                    parcel2.writeStringList(requestedRuntimePermissionsForMDM);
                    return true;
                case 221:
                    String string150 = parcel.readString();
                    String[] strArrCreateStringArray9 = parcel.createStringArray();
                    int i182 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean[] zArrCanPackageQuery = canPackageQuery(string150, strArrCreateStringArray9, i182);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(zArrCanPackageQuery);
                    return true;
                case 222:
                    long j28 = parcel.readLong();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zWaitForHandler = waitForHandler(j28, z18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWaitForHandler);
                    return true;
                case 223:
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i183 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPackageMonitorCallback(iRemoteCallbackAsInterface, i183);
                    parcel2.writeNoException();
                    return true;
                case 224:
                    IRemoteCallback iRemoteCallbackAsInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPackageMonitorCallback(iRemoteCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    String string151 = parcel.readString();
                    int i184 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ArchivedPackageParcel archivedPackage = getArchivedPackage(string151, i184);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(archivedPackage, 1);
                    return true;
                case 226:
                    String string152 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string153 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bitmap archivedAppIcon = getArchivedAppIcon(string152, userHandle, string153);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(archivedAppIcon, 1);
                    return true;
                case 227:
                    String string154 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAppArchivable = isAppArchivable(string154, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppArchivable);
                    return true;
                case 228:
                    String string155 = parcel.readString();
                    int i185 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appMetadataSource = getAppMetadataSource(string155, i185);
                    parcel2.writeNoException();
                    parcel2.writeInt(appMetadataSource);
                    return true;
                case 229:
                    int i186 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName domainVerificationAgent = getDomainVerificationAgent(i186);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationAgent, 1);
                    return true;
                case 230:
                    String string156 = parcel.readString();
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPageSizeAppCompatFlagsSettingsOverride(string156, z19);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    String string157 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPageSizeCompatEnabled = isPageSizeCompatEnabled(string157);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPageSizeCompatEnabled);
                    return true;
                case 232:
                    String string158 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String pageSizeCompatWarningMessage = getPageSizeCompatWarningMessage(string158);
                    parcel2.writeNoException();
                    parcel2.writeString(pageSizeCompatWarningMessage);
                    return true;
                case 233:
                    List<String> allApexDirectories = getAllApexDirectories();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allApexDirectories);
                    return true;
                case 234:
                    String string159 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageListForDualDarPolicy = getPackageListForDualDarPolicy(string159);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageListForDualDarPolicy);
                    return true;
                case 235:
                    String string160 = parcel.readString();
                    int i187 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCreateEncAppData = createEncAppData(string160, i187);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCreateEncAppData);
                    return true;
                case 236:
                    int i188 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveEncUserDir = removeEncUserDir(i188);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveEncUserDir);
                    return true;
                case 237:
                    int i189 = parcel.readInt();
                    String string161 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveEncPkgDir = removeEncPkgDir(i189, string161);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveEncPkgDir);
                    return true;
                case 238:
                    String string162 = parcel.readString();
                    String string163 = parcel.readString();
                    int i190 = parcel.readInt();
                    ArrayList arrayList5 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean metadataForIconTray = getMetadataForIconTray(string162, string163, i190, arrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(metadataForIconTray);
                    parcel2.writeStringList(arrayList5);
                    return true;
                case 239:
                    String string164 = parcel.readString();
                    String string165 = parcel.readString();
                    int i191 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemIsPermissionRevokedByUserFixed = semIsPermissionRevokedByUserFixed(string164, string165, i191);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsPermissionRevokedByUserFixed);
                    return true;
                case 240:
                    String string166 = parcel.readString();
                    int i192 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemIsInstalledPackageHiddenAsUser = semIsInstalledPackageHiddenAsUser(string166, i192);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsInstalledPackageHiddenAsUser);
                    return true;
                case 241:
                    String string167 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUnknownSourcePackage = isUnknownSourcePackage(string167);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUnknownSourcePackage);
                    return true;
                case 242:
                    long j29 = parcel.readLong();
                    int i193 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice unknownSourcePackagesAsUser = getUnknownSourcePackagesAsUser(j29, i193);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(unknownSourcePackagesAsUser, 1);
                    return true;
                case 243:
                    String string168 = parcel.readString();
                    int i194 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageAutoDisabled = isPackageAutoDisabled(string168, i194);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageAutoDisabled);
                    return true;
                case 244:
                    String string169 = parcel.readString();
                    int i195 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSystemCompressedPackage = isSystemCompressedPackage(string169, i195);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSystemCompressedPackage);
                    return true;
                case 245:
                    String string170 = parcel.readString();
                    String string171 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeMonetizationBadgeState(string170, string171);
                    parcel2.writeNoException();
                    return true;
                case 246:
                    String string172 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zShouldAppSupportBadgeIcon = shouldAppSupportBadgeIcon(string172);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldAppSupportBadgeIcon);
                    return true;
                case 247:
                    String string173 = parcel.readString();
                    int i196 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppCategoryHintUser(string173, i196);
                    parcel2.writeNoException();
                    return true;
                case 248:
                    String string174 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppCategoryHintUser(string174);
                    parcel2.writeNoException();
                    return true;
                case 249:
                    String string175 = parcel.readString();
                    int i197 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppCategoryHintDeveloper(string175, i197);
                    parcel2.writeNoException();
                    return true;
                case 250:
                    String string176 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppCategoryHintDeveloper(string176);
                    parcel2.writeNoException();
                    return true;
                case 251:
                    Map<String, String> appCategoryHintUserMap = getAppCategoryHintUserMap();
                    parcel2.writeNoException();
                    if (appCategoryHintUserMap == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(appCategoryHintUserMap.size());
                        appCategoryHintUserMap.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda2
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.lambda$onTransact$2(parcel2, (String) obj, (String) obj2);
                            }
                        });
                    }
                    return true;
                case 252:
                    String string177 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Map<String, String[]> appCategoryInfos = getAppCategoryInfos(string177);
                    parcel2.writeNoException();
                    if (appCategoryInfos == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(appCategoryInfos.size());
                        appCategoryInfos.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.lambda$onTransact$3(parcel2, (String) obj, (String[]) obj2);
                            }
                        });
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, String str2) {
            parcel.writeString(str);
            parcel.writeString(str2);
        }

        static /* synthetic */ void lambda$onTransact$2(Parcel parcel, String str, String str2) {
            parcel.writeString(str);
            parcel.writeString(str2);
        }

        static /* synthetic */ void lambda$onTransact$3(Parcel parcel, String str, String[] strArr) {
            parcel.writeString(str);
            parcel.writeStringArray(strArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IPackageManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageManager
            public void checkPackageStartable(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageAvailable(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageInfo getPackageInfo(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackageInfo) parcelObtain2.readTypedObject(PackageInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(versionedPackage, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackageInfo) parcelObtain2.readTypedObject(PackageInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPackageUid(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int[] getPackageGids(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] currentToCanonicalPackageNames(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] canonicalToCurrentPackageNames(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ApplicationInfo getApplicationInfo(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApplicationInfo) parcelObtain2.readTypedObject(ApplicationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getTargetSdkVersion(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityInfo) parcelObtain2.readTypedObject(ActivityInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean activitySupportsIntentAsUser(ComponentName componentName, Intent intent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityInfo) parcelObtain2.readTypedObject(ActivityInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ServiceInfo) parcelObtain2.readTypedObject(ServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ProviderInfo) parcelObtain2.readTypedObject(ProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isProtectedBroadcast(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkSignatures(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidSignatures(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getAllPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getPackagesForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getNameForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getNamesForUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUidForSharedUser(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getFlagsForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPrivateFlagsForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isUidPrivileged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo resolveIntent(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ResolveInfo) parcelObtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo findPersistentPreferredActivity(Intent intent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ResolveInfo) parcelObtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canForwardTo(Intent intent, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentActivities(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, String[] strArr, Intent intent, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedArray(intentArr, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentReceivers(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo resolveService(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ResolveInfo) parcelObtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentServices(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentContentProviders(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstalledPackages(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParcelFileDescriptor getAppMetadataFd(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstalledApplications(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getPersistentApplications(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo resolveContentProvider(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ProviderInfo) parcelObtain2.readTypedObject(ProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo resolveContentProviderForUid(String str, long j, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ProviderInfo) parcelObtain2.readTypedObject(ProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedList(list2, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readStringList(list);
                    parcelObtain2.readTypedList(list2, ProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryContentProviders(String str, int i, long j, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InstrumentationInfo) parcelObtain2.readTypedObject(InstrumentationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void finishPackageInstall(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setInstallerPackageName(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void relinquishUpdateOwnership(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationCategoryHint(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iPackageDeleteObserver);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(versionedPackage, 0);
                    parcelObtain.writeStrongInterface(iPackageDeleteObserver2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(versionedPackage, 0);
                    parcelObtain.writeStrongInterface(iPackageDeleteObserver2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getInstallerPackageName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public InstallSourceInfo getInstallSourceInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InstallSourceInfo) parcelObtain2.readTypedObject(InstallSourceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void resetApplicationPreferences(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo getLastChosenActivity(Intent intent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ResolveInfo) parcelObtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(componentNameArr, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(componentNameArr, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePreferredActivities(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readTypedList(list, IntentFilter.CREATOR);
                    parcelObtain2.readTypedList(list2, ComponentName.CREATOR);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePersistentPreferredActivities(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPersistentPreferredActivity(IntentFilter intentFilter, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearCrossProfileIntentFilters(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] setDistractingPackageRestrictionsAsUser(String[] strArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, int i, String str, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeTypedObject(persistableBundle2, 0);
                    parcelObtain.writeTypedObject(suspendDialogInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getUnsuspendablePackagesForUser(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSuspendedForUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageQuarantinedForUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStoppedForUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bundle getSuspendedPackageAppExtras(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSuspendingPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getPreferredActivityBackup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restorePreferredActivities(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDefaultAppsBackup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDefaultApps(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDomainVerificationBackup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDomainVerification(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ComponentName componentName = (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                    parcelObtain2.readTypedList(list, ResolveInfo.CREATOR);
                    return componentName;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHomeActivity(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void overrideLabelAndIcon(ComponentName componentName, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreLabelAndIcon(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> list, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getComponentEnabledSetting(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getApplicationEnabledSetting(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void logAppProcessStartIfNeeded(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void flushPackageRestrictionsAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setPackageStoppedState(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorageAndNotify(String str, long j, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorage(String str, long j, int i, IntentSender intentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iPackageDataObserver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationProfileData(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iPackageStatsObserver);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getSystemSharedLibraryNames() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String> getSystemSharedLibraryNamesAndPaths() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.readString());
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemFeature(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getInitialNonStoppedSystemPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void enterSafeMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isSafeMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemUidErrors() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackageUse(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(110, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyDexLoad(String str, Map<String, String> map, String str2) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.Proxy.lambda$notifyDexLoad$1(parcelObtain, (String) obj, (String) obj2);
                            }
                        });
                    }
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(111, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$notifyDexLoad$1(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.content.pm.IPackageManager
            public void registerDexModule(String str, String str2, boolean z, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iDexModuleRegisterCallback);
                    this.mRemote.transact(112, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptSecondary(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int performDexOptForADCP(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getMoveStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePackage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iMemorySaverPackageMoveObserver);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePrimaryStorage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstallLocation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallLocation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int installExistingPackageAsUser(String str, int i, int i2, int i3, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyPendingInstall(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void extendVerificationTimeout(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyIntentFilter(int i, int i2, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getIntentVerificationStatus(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean updateIntentVerificationStatus(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getIntentFilterVerifications(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getAllIntentFilters(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifierDeviceIdentity) parcelObtain2.readTypedObject(VerifierDeviceIdentity.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isFirstBoot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isDeviceUpgrading() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isStorageLow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setApplicationHiddenSettingAsUser(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getApplicationHiddenSettingAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSystemAppHiddenUntilInstalled(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setSystemAppInstallState(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IPackageInstaller getPackageInstaller() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IPackageInstaller.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setBlockUninstallForUser(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getBlockUninstallForUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public KeySet getKeySetByAlias(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeySet) parcelObtain2.readTypedObject(KeySet.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public KeySet getSigningKeySet(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeySet) parcelObtain2.readTypedObject(KeySet.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySet(String str, KeySet keySet) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(keySet, 0);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(keySet, 0);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getPermissionControllerPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSdkSandboxPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstantApps(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getInstantAppCookie(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstantAppCookie(String str, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bitmap getInstantAppIcon(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isInstantApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setRequiredForSystemUser(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUpdateAvailable(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ChangedPackages getChangedPackages(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ChangedPackages) parcelObtain2.readTypedObject(ChangedPackages.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageDeviceAdminOnAnyUser(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallReason(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getSharedLibraries(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canRequestPackageInstalls(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePreloadsFileCache() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppResolverComponent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppInstallerComponent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getInstantAppAndroidId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IArtManager getArtManager() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IArtManager.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHarmfulAppWarning(String str, CharSequence charSequence, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public CharSequence getHarmfulAppWarning(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSigningCertificate(String str, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getDefaultTextClassifierPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSystemTextClassifierPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getAttentionServicePackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getRotationResolverPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getWellbeingPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getAppPredictionServicePackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSystemCaptionsServicePackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSetupWizardPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getIncidentReportApproverPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStateProtected(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<ModuleInfo> getInstalledModules(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ModuleInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ModuleInfo getModuleInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ModuleInfo) parcelObtain2.readTypedObject(ModuleInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getRuntimePermissionsVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setRuntimePermissionsVersion(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackagesReplacedReceived(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void requestPackageChecksums(String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeList(list);
                    parcelObtain.writeStrongInterface(iOnChecksumsReadyListener);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IntentSender getLaunchIntentSenderForPackage(String str, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IntentSender) parcelObtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getAppOpPermissionPackages(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PermissionGroupInfo) parcelObtain2.readTypedObject(PermissionGroupInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermission(PermissionInfo permissionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(permissionInfo, 0);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(permissionInfo, 0);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void removePermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkPermission(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void grantRuntimePermission(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidPermission(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setMimeGroup(String str, String str2, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSplashScreenTheme(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSplashScreenTheme(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUserMinAspectRatio(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUserMinAspectRatio(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getMimeGroup(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAutoRevokeWhitelisted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeProviderVisible(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeUidVisible(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IBinder getHoldLockToken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void holdLock(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackageManager.Property) parcelObtain2.readTypedObject(PackageManager.Property.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryProperty(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setKeepUninstalledPackages(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int setLicensePermissionsForMDM(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getPackageGrantedPermissionsForMDM(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getGrantedPermissionsForMDM(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePreferredActivitiesAsUserForMDM(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean applyRuntimePermissionsForMDM(String str, List<String> list, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(219, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getRequestedRuntimePermissionsForMDM(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(220, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean[] canPackageQuery(String str, String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(221, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createBooleanArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean waitForHandler(long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(222, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerPackageMonitorCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(223, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterPackageMonitorCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(224, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ArchivedPackageParcel getArchivedPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(225, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ArchivedPackageParcel) parcelObtain2.readTypedObject(ArchivedPackageParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bitmap getArchivedAppIcon(String str, UserHandle userHandle, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(226, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAppArchivable(String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(227, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getAppMetadataSource(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(228, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getDomainVerificationAgent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(229, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setPageSizeAppCompatFlagsSettingsOverride(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPageSizeCompatEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(231, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getPageSizeCompatWarningMessage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(232, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getAllApexDirectories() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(233, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getPackageListForDualDarPolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(234, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean createEncAppData(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(235, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeEncUserDir(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(236, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeEncPkgDir(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(237, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getMetadataForIconTray(String str, String str2, int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(238, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readStringList(list);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean semIsPermissionRevokedByUserFixed(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(239, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean semIsInstalledPackageHiddenAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(240, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isUnknownSourcePackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(241, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getUnknownSourcePackagesAsUser(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(242, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageAutoDisabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(243, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isSystemCompressedPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(244, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void changeMonetizationBadgeState(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(245, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean shouldAppSupportBadgeIcon(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(246, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setAppCategoryHintUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(247, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearAppCategoryHintUser(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(248, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setAppCategoryHintDeveloper(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(249, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearAppCategoryHintDeveloper(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(250, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String> getAppCategoryHintUserMap() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(251, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.readString());
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String[]> getAppCategoryInfos(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(252, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda2
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.createStringArray());
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void getAppMetadataFd_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.GET_APP_METADATA, getCallingPid(), getCallingUid());
        }

        protected void removeCrossProfileIntentFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        protected void clearCrossProfileIntentFilters_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        protected void freeStorageAndNotify_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CLEAR_APP_CACHE, getCallingPid(), getCallingUid());
        }

        protected void freeStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CLEAR_APP_CACHE, getCallingPid(), getCallingUid());
        }

        protected void clearApplicationUserData_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CLEAR_APP_USER_DATA, getCallingPid(), getCallingUid());
        }

        protected void getMoveStatus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void registerMoveCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void unregisterMoveCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void movePackage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void movePackageToSd_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void movePrimaryStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void setInstallLocation_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void getVerifierDeviceIdentity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.PACKAGE_VERIFICATION_AGENT, getCallingPid(), getCallingUid());
        }

        protected void setApplicationHiddenSettingAsUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USERS, getCallingPid(), getCallingUid());
        }

        protected void setBlockUninstallForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DELETE_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void setUpdateAvailable_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void getInstantAppAndroidId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_INSTANT_APPS, getCallingPid(), getCallingUid());
        }

        protected void setUserMinAspectRatio_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void makeUidVisible_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MAKE_UID_VISIBLE, getCallingPid(), getCallingUid());
        }

        protected void getAppMetadataSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.GET_APP_METADATA, getCallingPid(), getCallingUid());
        }
    }
}
