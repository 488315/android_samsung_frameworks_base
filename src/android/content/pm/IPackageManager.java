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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPackageManager)) {
                return (IPackageManager) queryLocalInterface;
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
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    checkPackageStartable(readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAvailable = isPackageAvailable(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAvailable);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageInfo packageInfo = getPackageInfo(readString3, readLong, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageInfo, 1);
                    return true;
                case 4:
                    VersionedPackage versionedPackage = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    long readLong2 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageInfo packageInfoVersioned = getPackageInfoVersioned(versionedPackage, readLong2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageInfoVersioned, 1);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageUid = getPackageUid(readString4, readLong3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageUid);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] packageGids = getPackageGids(readString5, readLong4, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(packageGids);
                    return true;
                case 7:
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] currentToCanonicalPackageNames = currentToCanonicalPackageNames(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(currentToCanonicalPackageNames);
                    return true;
                case 8:
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] canonicalToCurrentPackageNames = canonicalToCurrentPackageNames(createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(canonicalToCurrentPackageNames);
                    return true;
                case 9:
                    String readString6 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ApplicationInfo applicationInfo = getApplicationInfo(readString6, readLong5, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationInfo, 1);
                    return true;
                case 10:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int targetSdkVersion = getTargetSdkVersion(readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(targetSdkVersion);
                    return true;
                case 11:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long readLong6 = parcel.readLong();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityInfo activityInfo = getActivityInfo(componentName, readLong6, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activityInfo, 1);
                    return true;
                case 12:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean activitySupportsIntentAsUser = activitySupportsIntentAsUser(componentName2, intent, readString8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activitySupportsIntentAsUser);
                    return true;
                case 13:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long readLong7 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityInfo receiverInfo = getReceiverInfo(componentName3, readLong7, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(receiverInfo, 1);
                    return true;
                case 14:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long readLong8 = parcel.readLong();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ServiceInfo serviceInfo = getServiceInfo(componentName4, readLong8, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfo, 1);
                    return true;
                case 15:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long readLong9 = parcel.readLong();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ProviderInfo providerInfo = getProviderInfo(componentName5, readLong9, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(providerInfo, 1);
                    return true;
                case 16:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProtectedBroadcast = isProtectedBroadcast(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProtectedBroadcast);
                    return true;
                case 17:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkSignatures = checkSignatures(readString10, readString11, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkSignatures);
                    return true;
                case 18:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkUidSignatures = checkUidSignatures(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUidSignatures);
                    return true;
                case 19:
                    List<String> allPackages = getAllPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allPackages);
                    return true;
                case 20:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] packagesForUid = getPackagesForUid(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packagesForUid);
                    return true;
                case 21:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String nameForUid = getNameForUid(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeString(nameForUid);
                    return true;
                case 22:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    String[] namesForUids = getNamesForUids(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(namesForUids);
                    return true;
                case 23:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidForSharedUser = getUidForSharedUser(readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidForSharedUser);
                    return true;
                case 24:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int flagsForUid = getFlagsForUid(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(flagsForUid);
                    return true;
                case 25:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int privateFlagsForUid = getPrivateFlagsForUid(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(privateFlagsForUid);
                    return true;
                case 26:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUidPrivileged = isUidPrivileged(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidPrivileged);
                    return true;
                case 27:
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString13 = parcel.readString();
                    long readLong10 = parcel.readLong();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo resolveIntent = resolveIntent(intent2, readString13, readLong10, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveIntent, 1);
                    return true;
                case 28:
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo findPersistentPreferredActivity = findPersistentPreferredActivity(intent3, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(findPersistentPreferredActivity, 1);
                    return true;
                case 29:
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString14 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canForwardTo = canForwardTo(intent4, readString14, readInt23, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canForwardTo);
                    return true;
                case 30:
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString15 = parcel.readString();
                    long readLong11 = parcel.readLong();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryIntentActivities = queryIntentActivities(intent5, readString15, readLong11, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentActivities, 1);
                    return true;
                case 31:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
                    String[] createStringArray3 = parcel.createStringArray();
                    Intent intent6 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString16 = parcel.readString();
                    long readLong12 = parcel.readLong();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryIntentActivityOptions = queryIntentActivityOptions(componentName6, intentArr, createStringArray3, intent6, readString16, readLong12, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentActivityOptions, 1);
                    return true;
                case 32:
                    Intent intent7 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString17 = parcel.readString();
                    long readLong13 = parcel.readLong();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryIntentReceivers = queryIntentReceivers(intent7, readString17, readLong13, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentReceivers, 1);
                    return true;
                case 33:
                    Intent intent8 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString18 = parcel.readString();
                    long readLong14 = parcel.readLong();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo resolveService = resolveService(intent8, readString18, readLong14, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveService, 1);
                    return true;
                case 34:
                    Intent intent9 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString19 = parcel.readString();
                    long readLong15 = parcel.readLong();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryIntentServices = queryIntentServices(intent9, readString19, readLong15, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentServices, 1);
                    return true;
                case 35:
                    Intent intent10 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString20 = parcel.readString();
                    long readLong16 = parcel.readLong();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryIntentContentProviders = queryIntentContentProviders(intent10, readString20, readLong16, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentContentProviders, 1);
                    return true;
                case 36:
                    long readLong17 = parcel.readLong();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice installedPackages = getInstalledPackages(readLong17, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedPackages, 1);
                    return true;
                case 37:
                    String readString21 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor appMetadataFd = getAppMetadataFd(readString21, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appMetadataFd, 1);
                    return true;
                case 38:
                    String[] createStringArray4 = parcel.createStringArray();
                    long readLong18 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice packagesHoldingPermissions = getPackagesHoldingPermissions(createStringArray4, readLong18, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packagesHoldingPermissions, 1);
                    return true;
                case 39:
                    long readLong19 = parcel.readLong();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice installedApplications = getInstalledApplications(readLong19, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedApplications, 1);
                    return true;
                case 40:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice persistentApplications = getPersistentApplications(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(persistentApplications, 1);
                    return true;
                case 41:
                    String readString22 = parcel.readString();
                    long readLong20 = parcel.readLong();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ProviderInfo resolveContentProvider = resolveContentProvider(readString22, readLong20, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveContentProvider, 1);
                    return true;
                case 42:
                    String readString23 = parcel.readString();
                    long readLong21 = parcel.readLong();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ProviderInfo resolveContentProviderForUid = resolveContentProviderForUid(readString23, readLong21, readInt37, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveContentProviderForUid, 1);
                    return true;
                case 43:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    querySyncProviders(createStringArrayList, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeStringList(createStringArrayList);
                    parcel2.writeTypedList(createTypedArrayList, 1);
                    return true;
                case 44:
                    String readString24 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    long readLong22 = parcel.readLong();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryContentProviders = queryContentProviders(readString24, readInt39, readLong22, readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryContentProviders, 1);
                    return true;
                case 45:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InstrumentationInfo instrumentationInfoAsUser = getInstrumentationInfoAsUser(componentName7, readInt40, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instrumentationInfoAsUser, 1);
                    return true;
                case 46:
                    String readString26 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryInstrumentationAsUser = queryInstrumentationAsUser(readString26, readInt42, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryInstrumentationAsUser, 1);
                    return true;
                case 47:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishPackageInstall(readInt44, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInstallerPackageName(readString27, readString28);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    relinquishUpdateOwnership(readString29);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String readString30 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplicationCategoryHint(readString30, readInt45, readString31);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    String readString32 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    IPackageDeleteObserver asInterface = IPackageDeleteObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deletePackageAsUser(readString32, readInt46, asInterface, readInt47, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    VersionedPackage versionedPackage2 = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    IPackageDeleteObserver2 asInterface2 = IPackageDeleteObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deletePackageVersioned(versionedPackage2, asInterface2, readInt49, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    VersionedPackage versionedPackage3 = (VersionedPackage) parcel.readTypedObject(VersionedPackage.CREATOR);
                    IPackageDeleteObserver2 asInterface3 = IPackageDeleteObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteExistingPackageAsUser(versionedPackage3, asInterface3, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String installerPackageName = getInstallerPackageName(readString33);
                    parcel2.writeNoException();
                    parcel2.writeString(installerPackageName);
                    return true;
                case 55:
                    String readString34 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InstallSourceInfo installSourceInfo = getInstallSourceInfo(readString34, readInt52);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installSourceInfo, 1);
                    return true;
                case 56:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetApplicationPreferences(readInt53);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    Intent intent11 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString35 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ResolveInfo lastChosenActivity = getLastChosenActivity(intent11, readString35, readInt54);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastChosenActivity, 1);
                    return true;
                case 58:
                    Intent intent12 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString36 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int readInt56 = parcel.readInt();
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLastChosenActivity(intent12, readString36, readInt55, intentFilter, readInt56, componentName8);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    IntentFilter intentFilter2 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int readInt57 = parcel.readInt();
                    ComponentName[] componentNameArr = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt58 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addPreferredActivity(intentFilter2, readInt57, componentNameArr, componentName9, readInt58, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    IntentFilter intentFilter3 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int readInt59 = parcel.readInt();
                    ComponentName[] componentNameArr2 = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    replacePreferredActivity(intentFilter3, readInt59, componentNameArr2, componentName10, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearPackagePreferredActivities(readString37);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int preferredActivities = getPreferredActivities(arrayList, arrayList2, readString38);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredActivities);
                    parcel2.writeTypedList(arrayList, 1);
                    parcel2.writeTypedList(arrayList2, 1);
                    return true;
                case 63:
                    IntentFilter intentFilter4 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPersistentPreferredActivity(intentFilter4, componentName11, readInt61);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    String readString39 = parcel.readString();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPackagePersistentPreferredActivities(readString39, readInt62);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    IntentFilter intentFilter5 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPersistentPreferredActivity(intentFilter5, readInt63);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    IntentFilter intentFilter6 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    String readString40 = parcel.readString();
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addCrossProfileIntentFilter(intentFilter6, readString40, readInt64, readInt65, readInt66);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    IntentFilter intentFilter7 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    String readString41 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeCrossProfileIntentFilter = removeCrossProfileIntentFilter(intentFilter7, readString41, readInt67, readInt68, readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeCrossProfileIntentFilter);
                    return true;
                case 68:
                    int readInt70 = parcel.readInt();
                    String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(readInt70, readString42);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    String[] createStringArray5 = parcel.createStringArray();
                    int readInt71 = parcel.readInt();
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] distractingPackageRestrictionsAsUser = setDistractingPackageRestrictionsAsUser(createStringArray5, readInt71, readInt72);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(distractingPackageRestrictionsAsUser);
                    return true;
                case 70:
                    String[] createStringArray6 = parcel.createStringArray();
                    boolean readBoolean3 = parcel.readBoolean();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SuspendDialogInfo suspendDialogInfo = (SuspendDialogInfo) parcel.readTypedObject(SuspendDialogInfo.CREATOR);
                    int readInt73 = parcel.readInt();
                    String readString43 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] packagesSuspendedAsUser = setPackagesSuspendedAsUser(createStringArray6, readBoolean3, persistableBundle, persistableBundle2, suspendDialogInfo, readInt73, readString43, readInt74, readInt75);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packagesSuspendedAsUser);
                    return true;
                case 71:
                    String[] createStringArray7 = parcel.createStringArray();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] unsuspendablePackagesForUser = getUnsuspendablePackagesForUser(createStringArray7, readInt76);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(unsuspendablePackagesForUser);
                    return true;
                case 72:
                    String readString44 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageSuspendedForUser = isPackageSuspendedForUser(readString44, readInt77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageSuspendedForUser);
                    return true;
                case 73:
                    String readString45 = parcel.readString();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageQuarantinedForUser = isPackageQuarantinedForUser(readString45, readInt78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageQuarantinedForUser);
                    return true;
                case 74:
                    String readString46 = parcel.readString();
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageStoppedForUser = isPackageStoppedForUser(readString46, readInt79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageStoppedForUser);
                    return true;
                case 75:
                    String readString47 = parcel.readString();
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle suspendedPackageAppExtras = getSuspendedPackageAppExtras(readString47, readInt80);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(suspendedPackageAppExtras, 1);
                    return true;
                case 76:
                    String readString48 = parcel.readString();
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String suspendingPackage = getSuspendingPackage(readString48, readInt81);
                    parcel2.writeNoException();
                    parcel2.writeString(suspendingPackage);
                    return true;
                case 77:
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] preferredActivityBackup = getPreferredActivityBackup(readInt82);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(preferredActivityBackup);
                    return true;
                case 78:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restorePreferredActivities(createByteArray, readInt83);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] defaultAppsBackup = getDefaultAppsBackup(readInt84);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(defaultAppsBackup);
                    return true;
                case 80:
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreDefaultApps(createByteArray2, readInt85);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] domainVerificationBackup = getDomainVerificationBackup(readInt86);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(domainVerificationBackup);
                    return true;
                case 82:
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreDomainVerification(createByteArray3, readInt87);
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
                    int readInt88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHomeActivity(componentName12, readInt88);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString49 = parcel.readString();
                    int readInt89 = parcel.readInt();
                    int readInt90 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideLabelAndIcon(componentName13, readString49, readInt89, readInt90);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreLabelAndIcon(componentName14, readInt91);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    ComponentName componentName15 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt92 = parcel.readInt();
                    int readInt93 = parcel.readInt();
                    int readInt94 = parcel.readInt();
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setComponentEnabledSetting(componentName15, readInt92, readInt93, readInt94, readString50);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(PackageManager.ComponentEnabledSetting.CREATOR);
                    int readInt95 = parcel.readInt();
                    String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setComponentEnabledSettings(createTypedArrayList2, readInt95, readString51);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    ComponentName componentName16 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int componentEnabledSetting = getComponentEnabledSetting(componentName16, readInt96);
                    parcel2.writeNoException();
                    parcel2.writeInt(componentEnabledSetting);
                    return true;
                case 90:
                    String readString52 = parcel.readString();
                    int readInt97 = parcel.readInt();
                    int readInt98 = parcel.readInt();
                    int readInt99 = parcel.readInt();
                    String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplicationEnabledSetting(readString52, readInt97, readInt98, readInt99, readString53);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    String readString54 = parcel.readString();
                    int readInt100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applicationEnabledSetting = getApplicationEnabledSetting(readString54, readInt100);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationEnabledSetting);
                    return true;
                case 92:
                    String readString55 = parcel.readString();
                    String readString56 = parcel.readString();
                    int readInt101 = parcel.readInt();
                    String readString57 = parcel.readString();
                    String readString58 = parcel.readString();
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logAppProcessStartIfNeeded(readString55, readString56, readInt101, readString57, readString58, readInt102);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int readInt103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    flushPackageRestrictionsAsUser(readInt103);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    String readString59 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageStoppedState(readString59, readBoolean4, readInt104);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    String readString60 = parcel.readString();
                    long readLong23 = parcel.readLong();
                    int readInt105 = parcel.readInt();
                    IPackageDataObserver asInterface4 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    freeStorageAndNotify(readString60, readLong23, readInt105, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    String readString61 = parcel.readString();
                    long readLong24 = parcel.readLong();
                    int readInt106 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    freeStorage(readString61, readLong24, readInt106, intentSender);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    String readString62 = parcel.readString();
                    IPackageDataObserver asInterface5 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteApplicationCacheFiles(readString62, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    String readString63 = parcel.readString();
                    int readInt107 = parcel.readInt();
                    IPackageDataObserver asInterface6 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteApplicationCacheFilesAsUser(readString63, readInt107, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    String readString64 = parcel.readString();
                    IPackageDataObserver asInterface7 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearApplicationUserData(readString64, asInterface7, readInt108);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearApplicationProfileData(readString65);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    String readString66 = parcel.readString();
                    int readInt109 = parcel.readInt();
                    IPackageStatsObserver asInterface8 = IPackageStatsObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getPackageSizeInfo(readString66, readInt109, asInterface8);
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
                                IPackageManager.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (String) obj2);
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
                    String readString67 = parcel.readString();
                    int readInt110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSystemFeature = hasSystemFeature(readString67, readInt110);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSystemFeature);
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
                    boolean isSafeMode = isSafeMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSafeMode);
                    return true;
                case 109:
                    boolean hasSystemUidErrors = hasSystemUidErrors();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSystemUidErrors);
                    return true;
                case 110:
                    String readString68 = parcel.readString();
                    int readInt111 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPackageUse(readString68, readInt111);
                    return true;
                case 111:
                    String readString69 = parcel.readString();
                    int readInt112 = parcel.readInt();
                    final HashMap hashMap = readInt112 < 0 ? null : new HashMap();
                    IntStream.range(0, readInt112).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), Parcel.this.readString());
                        }
                    });
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyDexLoad(readString69, hashMap, readString70);
                    return true;
                case 112:
                    String readString71 = parcel.readString();
                    String readString72 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    IDexModuleRegisterCallback asInterface9 = IDexModuleRegisterCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDexModule(readString71, readString72, readBoolean5, asInterface9);
                    return true;
                case 113:
                    String readString73 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    String readString74 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    boolean readBoolean8 = parcel.readBoolean();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean performDexOptMode = performDexOptMode(readString73, readBoolean6, readString74, readBoolean7, readBoolean8, readString75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performDexOptMode);
                    return true;
                case 114:
                    String readString76 = parcel.readString();
                    String readString77 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean performDexOptSecondary = performDexOptSecondary(readString76, readString77, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performDexOptSecondary);
                    return true;
                case 115:
                    String readString78 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int performDexOptForADCP = performDexOptForADCP(readString78, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeInt(performDexOptForADCP);
                    return true;
                case 116:
                    int readInt113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int moveStatus = getMoveStatus(readInt113);
                    parcel2.writeNoException();
                    parcel2.writeInt(moveStatus);
                    return true;
                case 117:
                    IPackageMoveObserver asInterface10 = IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMoveCallback(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    IPackageMoveObserver asInterface11 = IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterMoveCallback(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    String readString79 = parcel.readString();
                    String readString80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int movePackage = movePackage(readString79, readString80);
                    parcel2.writeNoException();
                    parcel2.writeInt(movePackage);
                    return true;
                case 120:
                    String readString81 = parcel.readString();
                    String readString82 = parcel.readString();
                    IMemorySaverPackageMoveObserver asInterface12 = IMemorySaverPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int movePackageToSd = movePackageToSd(readString81, readString82, asInterface12);
                    parcel2.writeNoException();
                    parcel2.writeInt(movePackageToSd);
                    return true;
                case 121:
                    String readString83 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int movePrimaryStorage = movePrimaryStorage(readString83);
                    parcel2.writeNoException();
                    parcel2.writeInt(movePrimaryStorage);
                    return true;
                case 122:
                    int readInt114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean installLocation = setInstallLocation(readInt114);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installLocation);
                    return true;
                case 123:
                    int installLocation2 = getInstallLocation();
                    parcel2.writeNoException();
                    parcel2.writeInt(installLocation2);
                    return true;
                case 124:
                    String readString84 = parcel.readString();
                    int readInt115 = parcel.readInt();
                    int readInt116 = parcel.readInt();
                    int readInt117 = parcel.readInt();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int installExistingPackageAsUser = installExistingPackageAsUser(readString84, readInt115, readInt116, readInt117, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(installExistingPackageAsUser);
                    return true;
                case 125:
                    int readInt118 = parcel.readInt();
                    int readInt119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    verifyPendingInstall(readInt118, readInt119);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    int readInt120 = parcel.readInt();
                    int readInt121 = parcel.readInt();
                    long readLong25 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    extendVerificationTimeout(readInt120, readInt121, readLong25);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    int readInt122 = parcel.readInt();
                    int readInt123 = parcel.readInt();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    verifyIntentFilter(readInt122, readInt123, createStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    String readString85 = parcel.readString();
                    int readInt124 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int intentVerificationStatus = getIntentVerificationStatus(readString85, readInt124);
                    parcel2.writeNoException();
                    parcel2.writeInt(intentVerificationStatus);
                    return true;
                case 129:
                    String readString86 = parcel.readString();
                    int readInt125 = parcel.readInt();
                    int readInt126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateIntentVerificationStatus = updateIntentVerificationStatus(readString86, readInt125, readInt126);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateIntentVerificationStatus);
                    return true;
                case 130:
                    String readString87 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice intentFilterVerifications = getIntentFilterVerifications(readString87);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentFilterVerifications, 1);
                    return true;
                case 131:
                    String readString88 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice allIntentFilters = getAllIntentFilters(readString88);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allIntentFilters, 1);
                    return true;
                case 132:
                    VerifierDeviceIdentity verifierDeviceIdentity = getVerifierDeviceIdentity();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifierDeviceIdentity, 1);
                    return true;
                case 133:
                    boolean isFirstBoot = isFirstBoot();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFirstBoot);
                    return true;
                case 134:
                    boolean isDeviceUpgrading = isDeviceUpgrading();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceUpgrading);
                    return true;
                case 135:
                    boolean isStorageLow = isStorageLow();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStorageLow);
                    return true;
                case 136:
                    String readString89 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    int readInt127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationHiddenSettingAsUser = setApplicationHiddenSettingAsUser(readString89, readBoolean11, readInt127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationHiddenSettingAsUser);
                    return true;
                case 137:
                    String readString90 = parcel.readString();
                    int readInt128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationHiddenSettingAsUser2 = getApplicationHiddenSettingAsUser(readString90, readInt128);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationHiddenSettingAsUser2);
                    return true;
                case 138:
                    String readString91 = parcel.readString();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSystemAppHiddenUntilInstalled(readString91, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 139:
                    String readString92 = parcel.readString();
                    boolean readBoolean13 = parcel.readBoolean();
                    int readInt129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean systemAppInstallState = setSystemAppInstallState(readString92, readBoolean13, readInt129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(systemAppInstallState);
                    return true;
                case 140:
                    IPackageInstaller packageInstaller = getPackageInstaller();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(packageInstaller);
                    return true;
                case 141:
                    String readString93 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    int readInt130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean blockUninstallForUser = setBlockUninstallForUser(readString93, readBoolean14, readInt130);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockUninstallForUser);
                    return true;
                case 142:
                    String readString94 = parcel.readString();
                    int readInt131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean blockUninstallForUser2 = getBlockUninstallForUser(readString94, readInt131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockUninstallForUser2);
                    return true;
                case 143:
                    String readString95 = parcel.readString();
                    String readString96 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeySet keySetByAlias = getKeySetByAlias(readString95, readString96);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keySetByAlias, 1);
                    return true;
                case 144:
                    String readString97 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeySet signingKeySet = getSigningKeySet(readString97);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(signingKeySet, 1);
                    return true;
                case 145:
                    String readString98 = parcel.readString();
                    KeySet keySet = (KeySet) parcel.readTypedObject(KeySet.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPackageSignedByKeySet = isPackageSignedByKeySet(readString98, keySet);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageSignedByKeySet);
                    return true;
                case 146:
                    String readString99 = parcel.readString();
                    KeySet keySet2 = (KeySet) parcel.readTypedObject(KeySet.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPackageSignedByKeySetExactly = isPackageSignedByKeySetExactly(readString99, keySet2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageSignedByKeySetExactly);
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
                    int readInt132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice instantApps = getInstantApps(readInt132);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantApps, 1);
                    return true;
                case 150:
                    String readString100 = parcel.readString();
                    int readInt133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] instantAppCookie = getInstantAppCookie(readString100, readInt133);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(instantAppCookie);
                    return true;
                case 151:
                    String readString101 = parcel.readString();
                    byte[] createByteArray4 = parcel.createByteArray();
                    int readInt134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean instantAppCookie2 = setInstantAppCookie(readString101, createByteArray4, readInt134);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(instantAppCookie2);
                    return true;
                case 152:
                    String readString102 = parcel.readString();
                    int readInt135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap instantAppIcon = getInstantAppIcon(readString102, readInt135);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppIcon, 1);
                    return true;
                case 153:
                    String readString103 = parcel.readString();
                    int readInt136 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInstantApp = isInstantApp(readString103, readInt136);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInstantApp);
                    return true;
                case 154:
                    String readString104 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requiredForSystemUser = setRequiredForSystemUser(readString104, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requiredForSystemUser);
                    return true;
                case 155:
                    String readString105 = parcel.readString();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUpdateAvailable(readString105, readBoolean16);
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
                    int readInt137 = parcel.readInt();
                    int readInt138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ChangedPackages changedPackages = getChangedPackages(readInt137, readInt138);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(changedPackages, 1);
                    return true;
                case 159:
                    String readString106 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackageDeviceAdminOnAnyUser = isPackageDeviceAdminOnAnyUser(readString106);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageDeviceAdminOnAnyUser);
                    return true;
                case 160:
                    String readString107 = parcel.readString();
                    int readInt139 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int installReason = getInstallReason(readString107, readInt139);
                    parcel2.writeNoException();
                    parcel2.writeInt(installReason);
                    return true;
                case 161:
                    String readString108 = parcel.readString();
                    long readLong26 = parcel.readLong();
                    int readInt140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice sharedLibraries = getSharedLibraries(readString108, readLong26, readInt140);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sharedLibraries, 1);
                    return true;
                case 162:
                    String readString109 = parcel.readString();
                    long readLong27 = parcel.readLong();
                    int readInt141 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice declaredSharedLibraries = getDeclaredSharedLibraries(readString109, readLong27, readInt141);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(declaredSharedLibraries, 1);
                    return true;
                case 163:
                    String readString110 = parcel.readString();
                    int readInt142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canRequestPackageInstalls = canRequestPackageInstalls(readString110, readInt142);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canRequestPackageInstalls);
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
                    String readString111 = parcel.readString();
                    int readInt143 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String instantAppAndroidId = getInstantAppAndroidId(readString111, readInt143);
                    parcel2.writeNoException();
                    parcel2.writeString(instantAppAndroidId);
                    return true;
                case 169:
                    IArtManager artManager = getArtManager();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(artManager);
                    return true;
                case 170:
                    String readString112 = parcel.readString();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt144 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHarmfulAppWarning(readString112, charSequence, readInt144);
                    parcel2.writeNoException();
                    return true;
                case 171:
                    String readString113 = parcel.readString();
                    int readInt145 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence harmfulAppWarning = getHarmfulAppWarning(readString113, readInt145);
                    parcel2.writeNoException();
                    if (harmfulAppWarning != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(harmfulAppWarning, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 172:
                    String readString114 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    int readInt146 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSigningCertificate = hasSigningCertificate(readString114, createByteArray5, readInt146);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSigningCertificate);
                    return true;
                case 173:
                    int readInt147 = parcel.readInt();
                    byte[] createByteArray6 = parcel.createByteArray();
                    int readInt148 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUidSigningCertificate = hasUidSigningCertificate(readInt147, createByteArray6, readInt148);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUidSigningCertificate);
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
                    String readString115 = parcel.readString();
                    int readInt149 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageStateProtected = isPackageStateProtected(readString115, readInt149);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageStateProtected);
                    return true;
                case 184:
                    sendDeviceCustomizationReadyBroadcast();
                    parcel2.writeNoException();
                    return true;
                case 185:
                    int readInt150 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ModuleInfo> installedModules = getInstalledModules(readInt150);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(installedModules, 1);
                    return true;
                case 186:
                    String readString116 = parcel.readString();
                    int readInt151 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ModuleInfo moduleInfo = getModuleInfo(readString116, readInt151);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(moduleInfo, 1);
                    return true;
                case 187:
                    int readInt152 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int runtimePermissionsVersion = getRuntimePermissionsVersion(readInt152);
                    parcel2.writeNoException();
                    parcel2.writeInt(runtimePermissionsVersion);
                    return true;
                case 188:
                    int readInt153 = parcel.readInt();
                    int readInt154 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRuntimePermissionsVersion(readInt153, readInt154);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    String[] createStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    notifyPackagesReplacedReceived(createStringArray8);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    String readString117 = parcel.readString();
                    boolean readBoolean17 = parcel.readBoolean();
                    int readInt155 = parcel.readInt();
                    int readInt156 = parcel.readInt();
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    IOnChecksumsReadyListener asInterface13 = IOnChecksumsReadyListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt157 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestPackageChecksums(readString117, readBoolean17, readInt155, readInt156, readArrayList, asInterface13, readInt157);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    String readString118 = parcel.readString();
                    String readString119 = parcel.readString();
                    String readString120 = parcel.readString();
                    int readInt158 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IntentSender launchIntentSenderForPackage = getLaunchIntentSenderForPackage(readString118, readString119, readString120, readInt158);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launchIntentSenderForPackage, 1);
                    return true;
                case 192:
                    String readString121 = parcel.readString();
                    int readInt159 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] appOpPermissionPackages = getAppOpPermissionPackages(readString121, readInt159);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(appOpPermissionPackages);
                    return true;
                case 193:
                    String readString122 = parcel.readString();
                    int readInt160 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionGroupInfo permissionGroupInfo = getPermissionGroupInfo(readString122, readInt160);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionGroupInfo, 1);
                    return true;
                case 194:
                    PermissionInfo permissionInfo = (PermissionInfo) parcel.readTypedObject(PermissionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addPermission = addPermission(permissionInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPermission);
                    return true;
                case 195:
                    PermissionInfo permissionInfo2 = (PermissionInfo) parcel.readTypedObject(PermissionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addPermissionAsync = addPermissionAsync(permissionInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPermissionAsync);
                    return true;
                case 196:
                    String readString123 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePermission(readString123);
                    parcel2.writeNoException();
                    return true;
                case 197:
                    String readString124 = parcel.readString();
                    String readString125 = parcel.readString();
                    int readInt161 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermission = checkPermission(readString124, readString125, readInt161);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermission);
                    return true;
                case 198:
                    String readString126 = parcel.readString();
                    String readString127 = parcel.readString();
                    int readInt162 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(readString126, readString127, readInt162);
                    parcel2.writeNoException();
                    return true;
                case 199:
                    String readString128 = parcel.readString();
                    int readInt163 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkUidPermission = checkUidPermission(readString128, readInt163);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUidPermission);
                    return true;
                case 200:
                    String readString129 = parcel.readString();
                    String readString130 = parcel.readString();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setMimeGroup(readString129, readString130, createStringArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 201:
                    String readString131 = parcel.readString();
                    int readInt164 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String splashScreenTheme = getSplashScreenTheme(readString131, readInt164);
                    parcel2.writeNoException();
                    parcel2.writeString(splashScreenTheme);
                    return true;
                case 202:
                    String readString132 = parcel.readString();
                    String readString133 = parcel.readString();
                    int readInt165 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSplashScreenTheme(readString132, readString133, readInt165);
                    parcel2.writeNoException();
                    return true;
                case 203:
                    String readString134 = parcel.readString();
                    int readInt166 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userMinAspectRatio = getUserMinAspectRatio(readString134, readInt166);
                    parcel2.writeNoException();
                    parcel2.writeInt(userMinAspectRatio);
                    return true;
                case 204:
                    String readString135 = parcel.readString();
                    int readInt167 = parcel.readInt();
                    int readInt168 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserMinAspectRatio(readString135, readInt167, readInt168);
                    parcel2.writeNoException();
                    return true;
                case 205:
                    String readString136 = parcel.readString();
                    String readString137 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> mimeGroup = getMimeGroup(readString136, readString137);
                    parcel2.writeNoException();
                    parcel2.writeStringList(mimeGroup);
                    return true;
                case 206:
                    String readString138 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAutoRevokeWhitelisted = isAutoRevokeWhitelisted(readString138);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAutoRevokeWhitelisted);
                    return true;
                case 207:
                    int readInt169 = parcel.readInt();
                    String readString139 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    makeProviderVisible(readInt169, readString139);
                    parcel2.writeNoException();
                    return true;
                case 208:
                    int readInt170 = parcel.readInt();
                    int readInt171 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    makeUidVisible(readInt170, readInt171);
                    parcel2.writeNoException();
                    return true;
                case 209:
                    IBinder holdLockToken = getHoldLockToken();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(holdLockToken);
                    return true;
                case 210:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt172 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(readStrongBinder, readInt172);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    String readString140 = parcel.readString();
                    String readString141 = parcel.readString();
                    String readString142 = parcel.readString();
                    int readInt173 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackageManager.Property propertyAsUser = getPropertyAsUser(readString140, readString141, readString142, readInt173);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(propertyAsUser, 1);
                    return true;
                case 212:
                    String readString143 = parcel.readString();
                    int readInt174 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryProperty = queryProperty(readString143, readInt174);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryProperty, 1);
                    return true;
                case 213:
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setKeepUninstalledPackages(createStringArrayList5);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    String readString144 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int licensePermissionsForMDM = setLicensePermissionsForMDM(readString144);
                    parcel2.writeNoException();
                    parcel2.writeInt(licensePermissionsForMDM);
                    return true;
                case 215:
                    String readString145 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageGrantedPermissionsForMDM = getPackageGrantedPermissionsForMDM(readString145);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageGrantedPermissionsForMDM);
                    return true;
                case 216:
                    String readString146 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> grantedPermissionsForMDM = getGrantedPermissionsForMDM(readString146);
                    parcel2.writeNoException();
                    parcel2.writeStringList(grantedPermissionsForMDM);
                    return true;
                case 217:
                    String readString147 = parcel.readString();
                    int readInt175 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPackagePreferredActivitiesAsUserForMDM(readString147, readInt175);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    String readString148 = parcel.readString();
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    int readInt176 = parcel.readInt();
                    int readInt177 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applyRuntimePermissionsForMDM = applyRuntimePermissionsForMDM(readString148, createStringArrayList6, readInt176, readInt177);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applyRuntimePermissionsForMDM);
                    return true;
                case 219:
                    int readInt178 = parcel.readInt();
                    int readInt179 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applyRuntimePermissionsForAllApplicationsForMDM = applyRuntimePermissionsForAllApplicationsForMDM(readInt178, readInt179);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applyRuntimePermissionsForAllApplicationsForMDM);
                    return true;
                case 220:
                    String readString149 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> requestedRuntimePermissionsForMDM = getRequestedRuntimePermissionsForMDM(readString149);
                    parcel2.writeNoException();
                    parcel2.writeStringList(requestedRuntimePermissionsForMDM);
                    return true;
                case 221:
                    String readString150 = parcel.readString();
                    String[] createStringArray9 = parcel.createStringArray();
                    int readInt180 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean[] canPackageQuery = canPackageQuery(readString150, createStringArray9, readInt180);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(canPackageQuery);
                    return true;
                case 222:
                    long readLong28 = parcel.readLong();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean waitForHandler = waitForHandler(readLong28, readBoolean18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(waitForHandler);
                    return true;
                case 223:
                    IRemoteCallback asInterface14 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt181 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPackageMonitorCallback(asInterface14, readInt181);
                    parcel2.writeNoException();
                    return true;
                case 224:
                    IRemoteCallback asInterface15 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPackageMonitorCallback(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    String readString151 = parcel.readString();
                    int readInt182 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ArchivedPackageParcel archivedPackage = getArchivedPackage(readString151, readInt182);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(archivedPackage, 1);
                    return true;
                case 226:
                    String readString152 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString153 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bitmap archivedAppIcon = getArchivedAppIcon(readString152, userHandle, readString153);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(archivedAppIcon, 1);
                    return true;
                case 227:
                    String readString154 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAppArchivable = isAppArchivable(readString154, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppArchivable);
                    return true;
                case 228:
                    String readString155 = parcel.readString();
                    int readInt183 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appMetadataSource = getAppMetadataSource(readString155, readInt183);
                    parcel2.writeNoException();
                    parcel2.writeInt(appMetadataSource);
                    return true;
                case 229:
                    int readInt184 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName domainVerificationAgent = getDomainVerificationAgent(readInt184);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationAgent, 1);
                    return true;
                case 230:
                    String readString156 = parcel.readString();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPageSizeAppCompatFlagsSettingsOverride(readString156, readBoolean19);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    String readString157 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPageSizeCompatEnabled = isPageSizeCompatEnabled(readString157);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPageSizeCompatEnabled);
                    return true;
                case 232:
                    String readString158 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String pageSizeCompatWarningMessage = getPageSizeCompatWarningMessage(readString158);
                    parcel2.writeNoException();
                    parcel2.writeString(pageSizeCompatWarningMessage);
                    return true;
                case 233:
                    List<String> allApexDirectories = getAllApexDirectories();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allApexDirectories);
                    return true;
                case 234:
                    String readString159 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageListForDualDarPolicy = getPackageListForDualDarPolicy(readString159);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageListForDualDarPolicy);
                    return true;
                case 235:
                    String readString160 = parcel.readString();
                    int readInt185 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean createEncAppData = createEncAppData(readString160, readInt185);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createEncAppData);
                    return true;
                case 236:
                    int readInt186 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeEncUserDir = removeEncUserDir(readInt186);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeEncUserDir);
                    return true;
                case 237:
                    int readInt187 = parcel.readInt();
                    String readString161 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeEncPkgDir = removeEncPkgDir(readInt187, readString161);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeEncPkgDir);
                    return true;
                case 238:
                    String readString162 = parcel.readString();
                    String readString163 = parcel.readString();
                    int readInt188 = parcel.readInt();
                    ArrayList arrayList4 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean metadataForIconTray = getMetadataForIconTray(readString162, readString163, readInt188, arrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(metadataForIconTray);
                    parcel2.writeStringList(arrayList4);
                    return true;
                case 239:
                    String readString164 = parcel.readString();
                    String readString165 = parcel.readString();
                    int readInt189 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semIsPermissionRevokedByUserFixed = semIsPermissionRevokedByUserFixed(readString164, readString165, readInt189);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsPermissionRevokedByUserFixed);
                    return true;
                case 240:
                    String readString166 = parcel.readString();
                    int readInt190 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semIsInstalledPackageHiddenAsUser = semIsInstalledPackageHiddenAsUser(readString166, readInt190);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsInstalledPackageHiddenAsUser);
                    return true;
                case 241:
                    String readString167 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUnknownSourcePackage = isUnknownSourcePackage(readString167);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUnknownSourcePackage);
                    return true;
                case 242:
                    long readLong29 = parcel.readLong();
                    int readInt191 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice unknownSourcePackagesAsUser = getUnknownSourcePackagesAsUser(readLong29, readInt191);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(unknownSourcePackagesAsUser, 1);
                    return true;
                case 243:
                    String readString168 = parcel.readString();
                    int readInt192 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAutoDisabled = isPackageAutoDisabled(readString168, readInt192);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAutoDisabled);
                    return true;
                case 244:
                    String readString169 = parcel.readString();
                    int readInt193 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSystemCompressedPackage = isSystemCompressedPackage(readString169, readInt193);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemCompressedPackage);
                    return true;
                case 245:
                    String readString170 = parcel.readString();
                    String readString171 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeMonetizationBadgeState(readString170, readString171);
                    parcel2.writeNoException();
                    return true;
                case 246:
                    String readString172 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean shouldAppSupportBadgeIcon = shouldAppSupportBadgeIcon(readString172);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldAppSupportBadgeIcon);
                    return true;
                case 247:
                    String readString173 = parcel.readString();
                    int readInt194 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppCategoryHintUser(readString173, readInt194);
                    parcel2.writeNoException();
                    return true;
                case 248:
                    String readString174 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppCategoryHintUser(readString174);
                    parcel2.writeNoException();
                    return true;
                case 249:
                    String readString175 = parcel.readString();
                    int readInt195 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppCategoryHintDeveloper(readString175, readInt195);
                    parcel2.writeNoException();
                    return true;
                case 250:
                    String readString176 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppCategoryHintDeveloper(readString176);
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
                                IPackageManager.Stub.lambda$onTransact$2(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    return true;
                case 252:
                    String readString177 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Map<String, String[]> appCategoryInfos = getAppCategoryInfos(readString177);
                    parcel2.writeNoException();
                    if (appCategoryInfos == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(appCategoryInfos.size());
                        appCategoryInfos.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.lambda$onTransact$3(Parcel.this, (String) obj, (String[]) obj2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageAvailable(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageInfo getPackageInfo(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackageInfo) obtain2.readTypedObject(PackageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackageInfo) obtain2.readTypedObject(PackageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPackageUid(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int[] getPackageGids(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] currentToCanonicalPackageNames(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] canonicalToCurrentPackageNames(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ApplicationInfo getApplicationInfo(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ApplicationInfo) obtain2.readTypedObject(ApplicationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getTargetSdkVersion(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityInfo) obtain2.readTypedObject(ActivityInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean activitySupportsIntentAsUser(ComponentName componentName, Intent intent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityInfo) obtain2.readTypedObject(ActivityInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ServiceInfo) obtain2.readTypedObject(ServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ProviderInfo) obtain2.readTypedObject(ProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isProtectedBroadcast(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkSignatures(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidSignatures(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getAllPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getPackagesForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getNameForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getNamesForUids(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUidForSharedUser(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getFlagsForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPrivateFlagsForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isUidPrivileged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo resolveIntent(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ResolveInfo) obtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo findPersistentPreferredActivity(Intent intent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ResolveInfo) obtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canForwardTo(Intent intent, String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentActivities(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, String[] strArr, Intent intent, String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentReceivers(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo resolveService(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ResolveInfo) obtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentServices(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentContentProviders(Intent intent, String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstalledPackages(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParcelFileDescriptor getAppMetadataFd(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstalledApplications(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getPersistentApplications(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo resolveContentProvider(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ProviderInfo) obtain2.readTypedObject(ProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo resolveContentProviderForUid(String str, long j, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ProviderInfo) obtain2.readTypedObject(ProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readStringList(list);
                    obtain2.readTypedList(list2, ProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryContentProviders(String str, int i, long j, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InstrumentationInfo) obtain2.readTypedObject(InstrumentationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void finishPackageInstall(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setInstallerPackageName(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void relinquishUpdateOwnership(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationCategoryHint(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageDeleteObserver);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeStrongInterface(iPackageDeleteObserver2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeStrongInterface(iPackageDeleteObserver2);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getInstallerPackageName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public InstallSourceInfo getInstallSourceInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InstallSourceInfo) obtain2.readTypedObject(InstallSourceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void resetApplicationPreferences(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo getLastChosenActivity(Intent intent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ResolveInfo) obtain2.readTypedObject(ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(componentNameArr, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(componentNameArr, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePreferredActivities(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, IntentFilter.CREATOR);
                    obtain2.readTypedList(list2, ComponentName.CREATOR);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePersistentPreferredActivities(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPersistentPreferredActivity(IntentFilter intentFilter, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearCrossProfileIntentFilters(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] setDistractingPackageRestrictionsAsUser(String[] strArr, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, int i, String str, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(persistableBundle2, 0);
                    obtain.writeTypedObject(suspendDialogInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getUnsuspendablePackagesForUser(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSuspendedForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageQuarantinedForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStoppedForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bundle getSuspendedPackageAppExtras(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSuspendingPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getPreferredActivityBackup(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restorePreferredActivities(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDefaultAppsBackup(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDefaultApps(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDomainVerificationBackup(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDomainVerification(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    ComponentName componentName = (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                    obtain2.readTypedList(list, ResolveInfo.CREATOR);
                    return componentName;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHomeActivity(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void overrideLabelAndIcon(ComponentName componentName, String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreLabelAndIcon(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> list, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getComponentEnabledSetting(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getApplicationEnabledSetting(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void logAppProcessStartIfNeeded(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void flushPackageRestrictionsAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setPackageStoppedState(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorageAndNotify(String str, long j, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorage(String str, long j, int i, IntentSender intentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationProfileData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageStatsObserver);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getSystemSharedLibraryNames() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String> getSystemSharedLibraryNamesAndPaths() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), Parcel.this.readString());
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemFeature(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getInitialNonStoppedSystemPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void enterSafeMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isSafeMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemUidErrors() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackageUse(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyDexLoad(String str, Map<String, String> map, String str2) throws RemoteException {
                final Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.Proxy.lambda$notifyDexLoad$1(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    obtain.writeString(str2);
                    this.mRemote.transact(111, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$notifyDexLoad$1(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.content.pm.IPackageManager
            public void registerDexModule(String str, String str2, boolean z, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDexModuleRegisterCallback);
                    this.mRemote.transact(112, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeString(str3);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptSecondary(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int performDexOptForADCP(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getMoveStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePackage(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iMemorySaverPackageMoveObserver);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePrimaryStorage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstallLocation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallLocation() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int installExistingPackageAsUser(String str, int i, int i2, int i3, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStringList(list);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyPendingInstall(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void extendVerificationTimeout(int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyIntentFilter(int i, int i2, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getIntentVerificationStatus(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean updateIntentVerificationStatus(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getIntentFilterVerifications(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getAllIntentFilters(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifierDeviceIdentity) obtain2.readTypedObject(VerifierDeviceIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isFirstBoot() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isDeviceUpgrading() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isStorageLow() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setApplicationHiddenSettingAsUser(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getApplicationHiddenSettingAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSystemAppHiddenUntilInstalled(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setSystemAppInstallState(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IPackageInstaller getPackageInstaller() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return IPackageInstaller.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setBlockUninstallForUser(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getBlockUninstallForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public KeySet getKeySetByAlias(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeySet) obtain2.readTypedObject(KeySet.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public KeySet getSigningKeySet(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeySet) obtain2.readTypedObject(KeySet.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySet(String str, KeySet keySet) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keySet, 0);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keySet, 0);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getPermissionControllerPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSdkSandboxPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstantApps(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getInstantAppCookie(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstantAppCookie(String str, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bitmap getInstantAppIcon(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bitmap) obtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isInstantApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setRequiredForSystemUser(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUpdateAvailable(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ChangedPackages getChangedPackages(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ChangedPackages) obtain2.readTypedObject(ChangedPackages.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageDeviceAdminOnAnyUser(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallReason(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getSharedLibraries(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canRequestPackageInstalls(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePreloadsFileCache() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppResolverComponent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppInstallerComponent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getInstantAppAndroidId(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IArtManager getArtManager() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return IArtManager.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHarmfulAppWarning(String str, CharSequence charSequence, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public CharSequence getHarmfulAppWarning(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSigningCertificate(String str, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getDefaultTextClassifierPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSystemTextClassifierPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getAttentionServicePackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getRotationResolverPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getWellbeingPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getAppPredictionServicePackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSystemCaptionsServicePackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSetupWizardPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getIncidentReportApproverPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStateProtected(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<ModuleInfo> getInstalledModules(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ModuleInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ModuleInfo getModuleInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ModuleInfo) obtain2.readTypedObject(ModuleInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getRuntimePermissionsVersion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setRuntimePermissionsVersion(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackagesReplacedReceived(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void requestPackageChecksums(String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeList(list);
                    obtain.writeStrongInterface(iOnChecksumsReadyListener);
                    obtain.writeInt(i3);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IntentSender getLaunchIntentSenderForPackage(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentSender) obtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getAppOpPermissionPackages(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PermissionGroupInfo) obtain2.readTypedObject(PermissionGroupInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermission(PermissionInfo permissionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(permissionInfo, 0);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(permissionInfo, 0);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void removePermission(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkPermission(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void grantRuntimePermission(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidPermission(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setMimeGroup(String str, String str2, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSplashScreenTheme(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSplashScreenTheme(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUserMinAspectRatio(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUserMinAspectRatio(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getMimeGroup(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAutoRevokeWhitelisted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeProviderVisible(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeUidVisible(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IBinder getHoldLockToken() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void holdLock(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackageManager.Property) obtain2.readTypedObject(PackageManager.Property.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryProperty(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setKeepUninstalledPackages(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int setLicensePermissionsForMDM(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getPackageGrantedPermissionsForMDM(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getGrantedPermissionsForMDM(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePreferredActivitiesAsUserForMDM(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean applyRuntimePermissionsForMDM(String str, List<String> list, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getRequestedRuntimePermissionsForMDM(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean[] canPackageQuery(String str, String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean waitForHandler(long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerPackageMonitorCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterPackageMonitorCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ArchivedPackageParcel getArchivedPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ArchivedPackageParcel) obtain2.readTypedObject(ArchivedPackageParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bitmap getArchivedAppIcon(String str, UserHandle userHandle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bitmap) obtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAppArchivable(String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getAppMetadataSource(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getDomainVerificationAgent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setPageSizeAppCompatFlagsSettingsOverride(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPageSizeCompatEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getPageSizeCompatWarningMessage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(232, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getAllApexDirectories() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getPackageListForDualDarPolicy(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean createEncAppData(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeEncUserDir(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeEncPkgDir(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getMetadataForIconTray(String str, String str2, int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readStringList(list);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean semIsPermissionRevokedByUserFixed(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean semIsInstalledPackageHiddenAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isUnknownSourcePackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(241, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getUnknownSourcePackagesAsUser(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageAutoDisabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(243, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isSystemCompressedPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(244, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void changeMonetizationBadgeState(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean shouldAppSupportBadgeIcon(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setAppCategoryHintUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(247, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearAppCategoryHintUser(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setAppCategoryHintDeveloper(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(249, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearAppCategoryHintDeveloper(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(250, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String> getAppCategoryHintUserMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), Parcel.this.readString());
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String[]> getAppCategoryInfos(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda2
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), Parcel.this.createStringArray());
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
