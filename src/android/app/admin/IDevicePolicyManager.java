package android.app.admin;

import android.accounts.Account;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.admin.IAuditLogEventsCallback;
import android.app.admin.StartInstallingUpdateCallback;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageDataObserver;
import android.content.pm.ParceledListSlice;
import android.content.pm.StringParceledListSlice;
import android.graphics.Bitmap;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.content.SecContentProviderURI;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import com.android.internal.infra.AndroidFuture;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IDevicePolicyManager extends IInterface {

    public static class Default implements IDevicePolicyManager {
        @Override // android.app.admin.IDevicePolicyManager
        public void acknowledgeDeviceCompliant() throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void acknowledgeNewUserDisclaimer(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void addCrossProfileIntentFilter(ComponentName componentName, String str, IntentFilter intentFilter, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean addCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void addPersistentPreferredActivity(ComponentName componentName, String str, IntentFilter intentFilter, ComponentName componentName2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean approveCaCert(String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, long j, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void calculateHasIncompatibleAccounts() throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canAdminGrantSensorsPermissions() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canProfileOwnerResetPasswordWhenLocked(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canUsbDataSignalingBeDisabled() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean checkDeviceIdentifierAccess(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int checkProvisioningPrecondition(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void choosePrivateKeyAlias(int i, Uri uri, String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearApplicationUserData(ComponentName componentName, String str, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearCrossProfileIntentFilters(ComponentName componentName, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearDeviceOwner(String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearOrganizationIdForUser(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearPackagePersistentPreferredActivities(ComponentName componentName, String str, String str2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearProfileOwner(ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean clearResetPasswordToken(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Intent createAdminSupportIntent(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public UserHandle createAndManageUser(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public UserHandle createAndProvisionManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public UserHandle createManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void enableSystemApp(ComponentName componentName, String str, String str2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int enableSystemAppWithIntent(ComponentName componentName, String str, Intent intent) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void enforceCanManageCaCerts(ComponentName componentName, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void finalizeCreateManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void finalizeWorkProfileProvisioning(UserHandle userHandle, Account account) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long forceNetworkLogs() throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void forceRemoveActiveAdmin(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long forceSecurityLogs() throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void forceSetMaxPolicyStorageLimit(String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void forceUpdateUserSetupComplete(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean generateKeyPair(ComponentName componentName, String str, String str2, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String[] getAccountTypesWithManagementDisabled(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String[] getAccountTypesWithManagementDisabledAsUser(int i, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<ComponentName> getActiveAdmins(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getAffiliationIds(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getAggregatedPasswordComplexityForUser(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getAllCrossProfilePackages(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getAlwaysOnVpnPackage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getAlwaysOnVpnPackageForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getAppFunctionsPolicy(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int[] getApplicationExemptions(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getApplicationRestrictions(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getApplicationRestrictionsManagingPackage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeEnabled(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getAutoTimePolicy(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeRequired() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeZoneEnabled(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getAutoTimeZonePolicy(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getBluetoothContactSharingDisabledForUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCameraDisabled(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getCertInstallerPackage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public PackagePolicy getCredentialManagerPolicy(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfileCalendarPackages(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfileCalendarPackagesForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileCallerIdDisabledForUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileContactsSearchDisabledForUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfilePackages(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfileWidgetProviders(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getCurrentFailedBiometricAttempts(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getCurrentFailedPasswordAttempts(String str, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDefaultCrossProfilePackages() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDelegatePackages(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDelegatedScopes(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getDeviceOwnerComponent(boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getDeviceOwnerComponentOnUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getDeviceOwnerName() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getDeviceOwnerType(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getDeviceOwnerUserId() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public DevicePolicyState getDevicePolicyState() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDisallowedSystemApps(ComponentName componentName, int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParcelableResource getDrawable(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getEndUserSessionMessage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public EnforcingAdmin getEnforcingAdmin(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getEnforcingAdminAndUserDetails(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<EnforcingAdmin> getEnforcingAdminsForRestriction(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getEnrollmentSpecificId(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getFinancedDeviceKioskRoleHolder(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getGlobalPrivateDnsHost(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getGlobalProxyAdmin(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getHeadlessDeviceOwnerMode(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getKeepUninstalledPackages(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParcelableGranteeMap getKeyPairGrants(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getKeyguardDisabledFeatures(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getLastBugReportRequestTime() throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getLastNetworkLogRetrievalTime() throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getLastSecurityLogRetrievalTime() throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getLockTaskFeatures(ComponentName componentName, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String[] getLockTaskPackages(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getLogoutUserId() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getLongSupportMessage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getLongSupportMessageForUser(ComponentName componentName, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public PackagePolicy getManagedProfileCallerIdAccessPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public PackagePolicy getManagedProfileContactsAccessPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getManagedProfileMaximumTimeOff(ComponentName componentName) throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMaxPolicyStorageLimit(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMaximumFailedPasswordsForWipe(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getMaximumTimeToLock(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getMeteredDataDisabledPackages(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMinimumRequiredWifiSecurityLevel() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMtePolicy(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getNearbyAppStreamingPolicy(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getNearbyNotificationStreamingPolicy(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getOrganizationColor(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getOrganizationColorForUser(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getOrganizationName(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getOrganizationNameForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<ApnSetting> getOverrideApns(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordComplexity(boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getPasswordExpiration(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getPasswordExpirationTimeout(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPermissionGrantState(ComponentName componentName, String str, String str2, String str3) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPermissionPolicy(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedAccessibilityServices(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedAccessibilityServicesForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedInputMethods(ComponentName componentName, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedInputMethodsAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPersonalAppsSuspendedReasons(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPolicySizeForAdmin(String str, EnforcingAdmin enforcingAdmin) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getProfileOwnerAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getProfileOwnerName(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getRequiredPasswordComplexity(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getRequiredStrongAuthTimeout(ComponentName componentName, int i, boolean z) throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getRestrictionsProvider(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getSamsungSDcardEncryptionStatus(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getScreenCaptureDisabled(ComponentName componentName, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> getSecondaryUsers(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getShortSupportMessage(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getShortSupportMessageForUser(ComponentName componentName, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getStartUserSessionMessage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getStorageEncryption(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getStorageEncryptionStatus(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParcelableResource getString(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int[] getSubscriptionIds(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getUserControlDisabledPackages(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getUserProvisioningState(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getUserRestrictions(ComponentName componentName, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getUserRestrictionsGlobally(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getWifiMacAddress(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public WifiSsidPolicy getWifiSsidPolicy(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasDeviceOwner() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasGrantedPolicy(ComponentName componentName, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasKeyPair(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasLockdownAdminConfiguredNetworks(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasManagedProfileCallerIdAccess(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasManagedProfileContactsAccess(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasUserSetupCompleted() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installCaCert(ComponentName componentName, String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installExistingPackage(ComponentName componentName, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installKeyPair(ComponentName componentName, String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void installUpdateFromFile(ComponentName componentName, String str, ParcelFileDescriptor parcelFileDescriptor, StartInstallingUpdateCallback startInstallingUpdateCallback) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isActivePasswordSufficient(String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isActivePasswordSufficientForDeviceRequirement() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAdminActive(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAffiliatedUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isApplicationHidden(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAuditLogEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isBackupServiceEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCaCertApproved(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCallerApplicationRestrictionsManagingPackage(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCallingUserAffiliated() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCommonCriteriaModeEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isComplianceAcknowledgementRequired() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceFinanced(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDevicePotentiallyStolen(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceProvisioned() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDpcDownloaded() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isEphemeralUser(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isFactoryResetProtectionPolicySupported() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isInputMethodPermittedByAdmin(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isKeyPairGrantedToWifiAuth(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isLockTaskPermitted(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isLogoutEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isManagedKiosk() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isManagedProfile(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNetworkLoggingEnabled(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNewUserDisclaimerAcknowledged(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNotificationListenerServicePermitted(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isOrganizationOwnedDeviceWithManagedProfile() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPackageAllowedToAccessCalendarForUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPackageSuspended(ComponentName componentName, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isProvisioningAllowed(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isRemovingAdmin(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isResetPasswordTokenActive(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSafeOperation(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSecondaryLockscreenEnabled(UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSecurityLoggingEnabled(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isStatusBarDisabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSupervisionComponent(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUnattendedManagedKiosk() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUninstallBlocked(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUninstallInQueue(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUsbDataSignalingEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUsingUnifiedPassword(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> listForegroundAffiliatedUsers() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> listPolicyExemptApps() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void lockNow(int i, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int logoutUser(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int logoutUserInternal() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void notifyLockTaskModeChanged(boolean z, String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void notifyPendingSystemUpdate(SystemUpdateInfo systemUpdateInfo) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean packageHasActiveAdmins(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void provisionFullyManagedDevice(FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reboot(ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void removeActiveAdmin(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeKeyPair(ComponentName componentName, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeManagedProfile(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeOverrideApn(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedBiometricAttempt(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedPasswordAttempt(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedPasswordAttemptWithFailureCount(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportKeyguardDismissed(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportKeyguardSecured(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportPasswordChanged(PasswordMetrics passwordMetrics, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportSuccessfulBiometricAttempt(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportSuccessfulPasswordAttempt(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean requestBugreport(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetDefaultCrossProfileIntentFilters(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetDrawables(List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean resetPassword(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean resetPasswordWithToken(ComponentName componentName, String str, String str2, byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetStrings(List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, String str, long j) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParceledListSlice retrieveSecurityLogs(ComponentName componentName, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int semGetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowBrowser(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowDesktopSync(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowInternetSharing(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowIrda(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowPopImapEmail(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowStorageCard(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowTextMessaging(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowWifi(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetRequireStorageCardEncryption(ComponentName componentName, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semIsActivePasswordSufficient(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semIsSimplePasswordEnabled(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowBrowser(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowDesktopSync(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowInternetSharing(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowIrda(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowPopImapEmail(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowStorageCard(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowTextMessaging(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowWifi(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetChangeNotificationEnabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetKeyguardDisabledFeatures(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordExpirationTimeout(ComponentName componentName, long j) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordHistoryLength(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumLength(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumLowerCase(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumNonLetter(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumUpperCase(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordQuality(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetRequireStorageCardEncryption(ComponentName componentName, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetSimplePasswordEnabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void sendLostModeLocationUpdate(AndroidFuture<Boolean> androidFuture) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAccountManagementDisabled(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setActiveAdmin(ComponentName componentName, boolean z, int i, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setAlwaysOnVpnPackage(ComponentName componentName, String str, boolean z, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAppFunctionsPolicy(String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setApplicationExemptions(String str, String str2, int[] iArr) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setApplicationHidden(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setApplicationRestrictions(ComponentName componentName, String str, String str2, Bundle bundle, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAuditLogEnabled(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAuditLogEventsCallback(String str, IAuditLogEventsCallback iAuditLogEventsCallback) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimePolicy(String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeRequired(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeZoneEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeZonePolicy(String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setBackupServiceEnabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setBluetoothContactSharingDisabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCameraDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCertInstallerPackage(ComponentName componentName, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCommonCriteriaModeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setConfiguredNetworksLockdownState(ComponentName componentName, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCredentialManagerPolicy(PackagePolicy packagePolicy) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileAppToIgnored(int i, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfilePackages(ComponentName componentName, List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDefaultDialerApplication(String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDefaultSmsApplication(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDelegatedScopes(ComponentName componentName, String str, List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setDeviceOwner(ComponentName componentName, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceOwnerType(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceProvisioningConfigApplied() throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDpcDownloaded(boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDrawables(List<DevicePolicyDrawableResource> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setFactoryResetProtectionPolicy(ComponentName componentName, String str, FactoryResetProtectionPolicy factoryResetProtectionPolicy) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setForceEphemeralUsers(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int setGlobalPrivateDns(ComponentName componentName, int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName setGlobalProxy(ComponentName componentName, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setGlobalSetting(ComponentName componentName, String str, String str2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setKeepUninstalledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyGrantForApp(ComponentName componentName, String str, String str2, String str3, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyGrantToWifiAuth(String str, String str2, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyPairCertificate(ComponentName componentName, String str, String str2, byte[] bArr, byte[] bArr2, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyguardDisabled(ComponentName componentName, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setKeyguardDisabledFeatures(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLocationEnabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLockTaskFeatures(ComponentName componentName, String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLockTaskPackages(ComponentName componentName, String str, String[] strArr) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLogoutEnabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileCallerIdAccessPolicy(PackagePolicy packagePolicy) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileContactsAccessPolicy(PackagePolicy packagePolicy) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileMaximumTimeOff(ComponentName componentName, long j) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy managedSubscriptionsPolicy) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMasterVolumeMuted(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaxPolicyStorageLimit(String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumFailedPasswordsForWipe(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumTimeToLock(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMinimumRequiredWifiSecurityLevel(String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMtePolicy(int i, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMtePolicyBySystem(String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNearbyAppStreamingPolicy(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNearbyNotificationStreamingPolicy(int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNetworkLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNextOperationSafety(int i, int i2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationColor(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationColorForUser(int i, int i2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationIdForUser(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationName(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOverrideApnsEnabled(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String[] setPackagesSuspended(ComponentName componentName, String str, String[] strArr, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordExpirationTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPermissionGrantState(ComponentName componentName, String str, String str2, String str3, int i, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPermissionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedAccessibilityServices(ComponentName componentName, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedInputMethods(ComponentName componentName, String str, List<String> list, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPersonalAppsSuspended(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPreferentialNetworkServiceConfigs(List<PreferentialNetworkServiceConfig> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileEnabled(ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileName(ComponentName componentName, String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setProfileOwner(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileOwnerOnOrganizationOwnedDevice(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRequiredPasswordComplexity(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRequiredStrongAuthTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setResetPasswordToken(ComponentName componentName, String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setScreenCaptureDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecondaryLockscreenEnabled(ComponentName componentName, boolean z, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecureSetting(ComponentName componentName, String str, String str2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecurityLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setShortSupportMessage(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setStatusBarDisabled(ComponentName componentName, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int setStorageEncryption(ComponentName componentName, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setStrings(List<DevicePolicyStringResource> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSystemSetting(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSystemUpdatePolicy(ComponentName componentName, String str, SystemUpdatePolicy systemUpdatePolicy) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setTime(ComponentName componentName, String str, long j) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setTimeZone(ComponentName componentName, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setTrustAgentConfiguration(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUninstallBlocked(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUsbDataSignalingEnabled(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserControlDisabledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserProvisioningState(int i, int i2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestriction(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestrictionForUser(String str, String str2, boolean z, int i) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestrictionGlobally(String str, String str2) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestrictionGloballyFromSystem(String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setWifiSsidPolicy(String str, WifiSsidPolicy wifiSsidPolicy) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void startManagedQuickContact(String str, long j, boolean z, long j2, Intent intent) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int startUserInBackground(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean startViewCalendarEventInManagedProfile(String str, long j, long j2, long j3, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean triggerDevicePolicyEngineMigration(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void uninstallCaCerts(ComponentName componentName, String str, String[] strArr) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void uninstallPackageWithActiveAdmins(String str) throws RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean updateOverrideApn(ComponentName componentName, int i, ApnSetting apnSetting) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void wipeDataWithReason(String str, int i, String str2, boolean z, boolean z2) throws RemoteException {
        }
    }

    void acknowledgeDeviceCompliant() throws RemoteException;

    void acknowledgeNewUserDisclaimer(int i) throws RemoteException;

    void addCrossProfileIntentFilter(ComponentName componentName, String str, IntentFilter intentFilter, int i) throws RemoteException;

    boolean addCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException;

    int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException;

    void addPersistentPreferredActivity(ComponentName componentName, String str, IntentFilter intentFilter, ComponentName componentName2) throws RemoteException;

    boolean approveCaCert(String str, int i, boolean z) throws RemoteException;

    boolean bindDeviceAdminServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, long j, int i) throws RemoteException;

    void calculateHasIncompatibleAccounts() throws RemoteException;

    boolean canAdminGrantSensorsPermissions() throws RemoteException;

    boolean canProfileOwnerResetPasswordWhenLocked(int i) throws RemoteException;

    boolean canUsbDataSignalingBeDisabled() throws RemoteException;

    boolean checkDeviceIdentifierAccess(String str, int i, int i2) throws RemoteException;

    int checkProvisioningPrecondition(String str, String str2) throws RemoteException;

    void choosePrivateKeyAlias(int i, Uri uri, String str, IBinder iBinder) throws RemoteException;

    void clearApplicationUserData(ComponentName componentName, String str, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void clearCrossProfileIntentFilters(ComponentName componentName, String str) throws RemoteException;

    void clearDeviceOwner(String str) throws RemoteException;

    void clearOrganizationIdForUser(int i) throws RemoteException;

    void clearPackagePersistentPreferredActivities(ComponentName componentName, String str, String str2) throws RemoteException;

    void clearProfileOwner(ComponentName componentName) throws RemoteException;

    boolean clearResetPasswordToken(ComponentName componentName, String str) throws RemoteException;

    void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException;

    Intent createAdminSupportIntent(String str) throws RemoteException;

    UserHandle createAndManageUser(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, int i) throws RemoteException;

    UserHandle createAndProvisionManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException;

    UserHandle createManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException;

    void enableSystemApp(ComponentName componentName, String str, String str2) throws RemoteException;

    int enableSystemAppWithIntent(ComponentName componentName, String str, Intent intent) throws RemoteException;

    void enforceCanManageCaCerts(ComponentName componentName, String str) throws RemoteException;

    void finalizeCreateManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, UserHandle userHandle) throws RemoteException;

    void finalizeWorkProfileProvisioning(UserHandle userHandle, Account account) throws RemoteException;

    long forceNetworkLogs() throws RemoteException;

    void forceRemoveActiveAdmin(ComponentName componentName, int i) throws RemoteException;

    long forceSecurityLogs() throws RemoteException;

    void forceSetMaxPolicyStorageLimit(String str, int i) throws RemoteException;

    void forceUpdateUserSetupComplete(int i) throws RemoteException;

    boolean generateKeyPair(ComponentName componentName, String str, String str2, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException;

    String[] getAccountTypesWithManagementDisabled(String str) throws RemoteException;

    String[] getAccountTypesWithManagementDisabledAsUser(int i, String str, boolean z) throws RemoteException;

    List<ComponentName> getActiveAdmins(int i) throws RemoteException;

    List<String> getAffiliationIds(ComponentName componentName) throws RemoteException;

    int getAggregatedPasswordComplexityForUser(int i, boolean z) throws RemoteException;

    List<String> getAllCrossProfilePackages(int i) throws RemoteException;

    List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName componentName) throws RemoteException;

    String getAlwaysOnVpnPackage(ComponentName componentName) throws RemoteException;

    String getAlwaysOnVpnPackageForUser(int i) throws RemoteException;

    int getAppFunctionsPolicy(String str, int i) throws RemoteException;

    int[] getApplicationExemptions(String str) throws RemoteException;

    Bundle getApplicationRestrictions(ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    String getApplicationRestrictionsManagingPackage(ComponentName componentName) throws RemoteException;

    boolean getAutoTimeEnabled(ComponentName componentName, String str) throws RemoteException;

    int getAutoTimePolicy(String str) throws RemoteException;

    boolean getAutoTimeRequired() throws RemoteException;

    boolean getAutoTimeZoneEnabled(ComponentName componentName, String str) throws RemoteException;

    int getAutoTimeZonePolicy(String str) throws RemoteException;

    List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName) throws RemoteException;

    boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException;

    boolean getBluetoothContactSharingDisabledForUser(int i) throws RemoteException;

    boolean getCameraDisabled(ComponentName componentName, String str, int i, boolean z) throws RemoteException;

    String getCertInstallerPackage(ComponentName componentName) throws RemoteException;

    int getContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException;

    PackagePolicy getCredentialManagerPolicy(int i) throws RemoteException;

    List<String> getCrossProfileCalendarPackages(ComponentName componentName) throws RemoteException;

    List<String> getCrossProfileCalendarPackagesForUser(int i) throws RemoteException;

    boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException;

    boolean getCrossProfileCallerIdDisabledForUser(int i) throws RemoteException;

    boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) throws RemoteException;

    boolean getCrossProfileContactsSearchDisabledForUser(int i) throws RemoteException;

    List<String> getCrossProfilePackages(ComponentName componentName) throws RemoteException;

    List<String> getCrossProfileWidgetProviders(ComponentName componentName, String str) throws RemoteException;

    int getCurrentFailedBiometricAttempts(int i) throws RemoteException;

    int getCurrentFailedPasswordAttempts(String str, int i, boolean z) throws RemoteException;

    List<String> getDefaultCrossProfilePackages() throws RemoteException;

    List<String> getDelegatePackages(ComponentName componentName, String str) throws RemoteException;

    List<String> getDelegatedScopes(ComponentName componentName, String str) throws RemoteException;

    ComponentName getDeviceOwnerComponent(boolean z) throws RemoteException;

    ComponentName getDeviceOwnerComponentOnUser(int i) throws RemoteException;

    CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException;

    String getDeviceOwnerName() throws RemoteException;

    CharSequence getDeviceOwnerOrganizationName() throws RemoteException;

    int getDeviceOwnerType(ComponentName componentName) throws RemoteException;

    int getDeviceOwnerUserId() throws RemoteException;

    DevicePolicyState getDevicePolicyState() throws RemoteException;

    List<String> getDisallowedSystemApps(ComponentName componentName, int i, String str) throws RemoteException;

    boolean getDoNotAskCredentialsOnBoot() throws RemoteException;

    ParcelableResource getDrawable(String str, String str2, String str3) throws RemoteException;

    CharSequence getEndUserSessionMessage(ComponentName componentName) throws RemoteException;

    EnforcingAdmin getEnforcingAdmin(int i, String str) throws RemoteException;

    Bundle getEnforcingAdminAndUserDetails(int i, String str) throws RemoteException;

    List<EnforcingAdmin> getEnforcingAdminsForRestriction(int i, String str) throws RemoteException;

    String getEnrollmentSpecificId(String str) throws RemoteException;

    FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName componentName) throws RemoteException;

    String getFinancedDeviceKioskRoleHolder(String str) throws RemoteException;

    boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException;

    String getGlobalPrivateDnsHost(ComponentName componentName) throws RemoteException;

    int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException;

    ComponentName getGlobalProxyAdmin(int i) throws RemoteException;

    int getHeadlessDeviceOwnerMode(String str) throws RemoteException;

    List<String> getKeepUninstalledPackages(ComponentName componentName, String str) throws RemoteException;

    ParcelableGranteeMap getKeyPairGrants(String str, String str2) throws RemoteException;

    int getKeyguardDisabledFeatures(ComponentName componentName, int i, boolean z) throws RemoteException;

    long getLastBugReportRequestTime() throws RemoteException;

    long getLastNetworkLogRetrievalTime() throws RemoteException;

    long getLastSecurityLogRetrievalTime() throws RemoteException;

    int getLockTaskFeatures(ComponentName componentName, String str) throws RemoteException;

    String[] getLockTaskPackages(ComponentName componentName, String str) throws RemoteException;

    int getLogoutUserId() throws RemoteException;

    CharSequence getLongSupportMessage(ComponentName componentName) throws RemoteException;

    CharSequence getLongSupportMessageForUser(ComponentName componentName, int i) throws RemoteException;

    PackagePolicy getManagedProfileCallerIdAccessPolicy() throws RemoteException;

    PackagePolicy getManagedProfileContactsAccessPolicy() throws RemoteException;

    long getManagedProfileMaximumTimeOff(ComponentName componentName) throws RemoteException;

    ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws RemoteException;

    int getMaxPolicyStorageLimit(String str) throws RemoteException;

    int getMaximumFailedPasswordsForWipe(ComponentName componentName, int i, boolean z) throws RemoteException;

    long getMaximumTimeToLock(ComponentName componentName, int i, boolean z) throws RemoteException;

    List<String> getMeteredDataDisabledPackages(ComponentName componentName) throws RemoteException;

    int getMinimumRequiredWifiSecurityLevel() throws RemoteException;

    int getMtePolicy(String str) throws RemoteException;

    int getNearbyAppStreamingPolicy(int i) throws RemoteException;

    int getNearbyNotificationStreamingPolicy(int i) throws RemoteException;

    int getOrganizationColor(ComponentName componentName) throws RemoteException;

    int getOrganizationColorForUser(int i) throws RemoteException;

    CharSequence getOrganizationName(ComponentName componentName, String str) throws RemoteException;

    CharSequence getOrganizationNameForUser(int i) throws RemoteException;

    List<ApnSetting> getOverrideApns(ComponentName componentName) throws RemoteException;

    StringParceledListSlice getOwnerInstalledCaCerts(UserHandle userHandle) throws RemoteException;

    int getPasswordComplexity(boolean z) throws RemoteException;

    long getPasswordExpiration(ComponentName componentName, int i, boolean z) throws RemoteException;

    long getPasswordExpirationTimeout(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws RemoteException;

    int getPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException;

    SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName, String str) throws RemoteException;

    int getPermissionGrantState(ComponentName componentName, String str, String str2, String str3) throws RemoteException;

    int getPermissionPolicy(ComponentName componentName) throws RemoteException;

    List<String> getPermittedAccessibilityServices(ComponentName componentName) throws RemoteException;

    List<String> getPermittedAccessibilityServicesForUser(int i) throws RemoteException;

    List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName) throws RemoteException;

    List<String> getPermittedInputMethods(ComponentName componentName, String str, boolean z) throws RemoteException;

    List<String> getPermittedInputMethodsAsUser(int i) throws RemoteException;

    int getPersonalAppsSuspendedReasons(ComponentName componentName) throws RemoteException;

    List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle) throws RemoteException;

    int getPolicySizeForAdmin(String str, EnforcingAdmin enforcingAdmin) throws RemoteException;

    List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() throws RemoteException;

    ComponentName getProfileOwnerAsUser(int i) throws RemoteException;

    String getProfileOwnerName(int i) throws RemoteException;

    ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle) throws RemoteException;

    int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws RemoteException;

    void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int i) throws RemoteException;

    int getRequiredPasswordComplexity(String str, boolean z) throws RemoteException;

    long getRequiredStrongAuthTimeout(ComponentName componentName, int i, boolean z) throws RemoteException;

    ComponentName getRestrictionsProvider(int i) throws RemoteException;

    boolean getSamsungSDcardEncryptionStatus(ComponentName componentName, int i) throws RemoteException;

    boolean getScreenCaptureDisabled(ComponentName componentName, int i, boolean z) throws RemoteException;

    List<UserHandle> getSecondaryUsers(ComponentName componentName) throws RemoteException;

    CharSequence getShortSupportMessage(ComponentName componentName, String str) throws RemoteException;

    CharSequence getShortSupportMessageForUser(ComponentName componentName, int i) throws RemoteException;

    CharSequence getStartUserSessionMessage(ComponentName componentName) throws RemoteException;

    boolean getStorageEncryption(ComponentName componentName, int i) throws RemoteException;

    int getStorageEncryptionStatus(String str, int i) throws RemoteException;

    ParcelableResource getString(String str) throws RemoteException;

    int[] getSubscriptionIds(String str) throws RemoteException;

    SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException;

    PersistableBundle getTransferOwnershipBundle() throws RemoteException;

    List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, int i, boolean z) throws RemoteException;

    List<String> getUserControlDisabledPackages(ComponentName componentName, String str) throws RemoteException;

    int getUserProvisioningState(int i) throws RemoteException;

    Bundle getUserRestrictions(ComponentName componentName, String str, boolean z) throws RemoteException;

    Bundle getUserRestrictionsGlobally(String str) throws RemoteException;

    String getWifiMacAddress(ComponentName componentName, String str) throws RemoteException;

    WifiSsidPolicy getWifiSsidPolicy(String str) throws RemoteException;

    boolean hasDeviceOwner() throws RemoteException;

    boolean hasGrantedPolicy(ComponentName componentName, int i, int i2) throws RemoteException;

    boolean hasKeyPair(String str, String str2) throws RemoteException;

    boolean hasLockdownAdminConfiguredNetworks(ComponentName componentName) throws RemoteException;

    boolean hasManagedProfileCallerIdAccess(int i, String str) throws RemoteException;

    boolean hasManagedProfileContactsAccess(int i, String str) throws RemoteException;

    boolean hasUserSetupCompleted() throws RemoteException;

    boolean installCaCert(ComponentName componentName, String str, byte[] bArr) throws RemoteException;

    boolean installExistingPackage(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean installKeyPair(ComponentName componentName, String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, boolean z, boolean z2) throws RemoteException;

    void installUpdateFromFile(ComponentName componentName, String str, ParcelFileDescriptor parcelFileDescriptor, StartInstallingUpdateCallback startInstallingUpdateCallback) throws RemoteException;

    boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String str, int i) throws RemoteException;

    boolean isActivePasswordSufficient(String str, int i, boolean z) throws RemoteException;

    boolean isActivePasswordSufficientForDeviceRequirement() throws RemoteException;

    boolean isAdminActive(ComponentName componentName, int i) throws RemoteException;

    boolean isAffiliatedUser(int i) throws RemoteException;

    boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException;

    boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws RemoteException;

    boolean isApplicationHidden(ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    boolean isAuditLogEnabled(String str) throws RemoteException;

    boolean isBackupServiceEnabled(ComponentName componentName) throws RemoteException;

    boolean isCaCertApproved(String str, int i) throws RemoteException;

    boolean isCallerApplicationRestrictionsManagingPackage(String str) throws RemoteException;

    boolean isCallingUserAffiliated() throws RemoteException;

    boolean isCommonCriteriaModeEnabled(ComponentName componentName) throws RemoteException;

    boolean isComplianceAcknowledgementRequired() throws RemoteException;

    boolean isCurrentInputMethodSetByOwner() throws RemoteException;

    boolean isDeviceFinanced(String str) throws RemoteException;

    boolean isDevicePotentiallyStolen(String str) throws RemoteException;

    boolean isDeviceProvisioned() throws RemoteException;

    boolean isDeviceProvisioningConfigApplied() throws RemoteException;

    boolean isDpcDownloaded() throws RemoteException;

    boolean isEphemeralUser(ComponentName componentName) throws RemoteException;

    boolean isFactoryResetProtectionPolicySupported() throws RemoteException;

    boolean isInputMethodPermittedByAdmin(ComponentName componentName, String str, int i, boolean z) throws RemoteException;

    boolean isKeyPairGrantedToWifiAuth(String str, String str2) throws RemoteException;

    boolean isLockTaskPermitted(String str) throws RemoteException;

    boolean isLogoutEnabled() throws RemoteException;

    boolean isManagedKiosk() throws RemoteException;

    boolean isManagedProfile(ComponentName componentName) throws RemoteException;

    boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException;

    boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String str, int i) throws RemoteException;

    boolean isNetworkLoggingEnabled(ComponentName componentName, String str) throws RemoteException;

    boolean isNewUserDisclaimerAcknowledged(int i) throws RemoteException;

    boolean isNotificationListenerServicePermitted(String str, int i) throws RemoteException;

    boolean isOrganizationOwnedDeviceWithManagedProfile() throws RemoteException;

    boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException;

    boolean isPackageAllowedToAccessCalendarForUser(String str, int i) throws RemoteException;

    boolean isPackageSuspended(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws RemoteException;

    boolean isProvisioningAllowed(String str, String str2) throws RemoteException;

    boolean isRemovingAdmin(ComponentName componentName, int i) throws RemoteException;

    boolean isResetPasswordTokenActive(ComponentName componentName, String str) throws RemoteException;

    boolean isSafeOperation(int i) throws RemoteException;

    boolean isSecondaryLockscreenEnabled(UserHandle userHandle) throws RemoteException;

    boolean isSecurityLoggingEnabled(ComponentName componentName, String str) throws RemoteException;

    boolean isStatusBarDisabled(String str) throws RemoteException;

    boolean isSupervisionComponent(ComponentName componentName) throws RemoteException;

    boolean isUnattendedManagedKiosk() throws RemoteException;

    boolean isUninstallBlocked(String str) throws RemoteException;

    boolean isUninstallInQueue(String str) throws RemoteException;

    boolean isUsbDataSignalingEnabled(String str) throws RemoteException;

    boolean isUsingUnifiedPassword(ComponentName componentName) throws RemoteException;

    List<UserHandle> listForegroundAffiliatedUsers() throws RemoteException;

    List<String> listPolicyExemptApps() throws RemoteException;

    void lockNow(int i, String str, boolean z) throws RemoteException;

    int logoutUser(ComponentName componentName) throws RemoteException;

    int logoutUserInternal() throws RemoteException;

    void notifyLockTaskModeChanged(boolean z, String str, int i) throws RemoteException;

    void notifyPendingSystemUpdate(SystemUpdateInfo systemUpdateInfo) throws RemoteException;

    boolean packageHasActiveAdmins(String str, int i) throws RemoteException;

    void provisionFullyManagedDevice(FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, String str) throws RemoteException;

    void reboot(ComponentName componentName) throws RemoteException;

    void removeActiveAdmin(ComponentName componentName, int i) throws RemoteException;

    boolean removeCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean removeKeyPair(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean removeManagedProfile(int i) throws RemoteException;

    boolean removeOverrideApn(ComponentName componentName, int i) throws RemoteException;

    boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void reportFailedBiometricAttempt(int i) throws RemoteException;

    void reportFailedPasswordAttempt(int i, boolean z) throws RemoteException;

    void reportFailedPasswordAttemptWithFailureCount(int i, int i2, boolean z) throws RemoteException;

    void reportKeyguardDismissed(int i) throws RemoteException;

    void reportKeyguardSecured(int i) throws RemoteException;

    void reportPasswordChanged(PasswordMetrics passwordMetrics, int i) throws RemoteException;

    void reportSuccessfulBiometricAttempt(int i) throws RemoteException;

    void reportSuccessfulPasswordAttempt(int i) throws RemoteException;

    boolean requestBugreport(ComponentName componentName) throws RemoteException;

    void resetDefaultCrossProfileIntentFilters(int i) throws RemoteException;

    void resetDrawables(List<String> list) throws RemoteException;

    boolean resetPassword(String str, int i) throws RemoteException;

    boolean resetPasswordWithToken(ComponentName componentName, String str, String str2, byte[] bArr, int i) throws RemoteException;

    void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() throws RemoteException;

    void resetStrings(List<String> list) throws RemoteException;

    List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, String str, long j) throws RemoteException;

    ParceledListSlice retrievePreRebootSecurityLogs(ComponentName componentName, String str) throws RemoteException;

    ParceledListSlice retrieveSecurityLogs(ComponentName componentName, String str) throws RemoteException;

    int semGetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowBrowser(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowDesktopSync(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowInternetSharing(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowIrda(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowPopImapEmail(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowStorageCard(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowTextMessaging(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowWifi(ComponentName componentName, int i) throws RemoteException;

    boolean semGetRequireStorageCardEncryption(ComponentName componentName, int i, boolean z) throws RemoteException;

    boolean semIsActivePasswordSufficient(int i) throws RemoteException;

    boolean semIsSimplePasswordEnabled(ComponentName componentName, int i) throws RemoteException;

    void semSetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException;

    void semSetAllowBrowser(ComponentName componentName, boolean z) throws RemoteException;

    void semSetAllowDesktopSync(ComponentName componentName, boolean z) throws RemoteException;

    void semSetAllowInternetSharing(ComponentName componentName, boolean z) throws RemoteException;

    void semSetAllowIrda(ComponentName componentName, boolean z) throws RemoteException;

    void semSetAllowPopImapEmail(ComponentName componentName, boolean z) throws RemoteException;

    void semSetAllowStorageCard(ComponentName componentName, boolean z) throws RemoteException;

    void semSetAllowTextMessaging(ComponentName componentName, boolean z) throws RemoteException;

    void semSetAllowWifi(ComponentName componentName, boolean z) throws RemoteException;

    void semSetChangeNotificationEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void semSetKeyguardDisabledFeatures(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordExpirationTimeout(ComponentName componentName, long j) throws RemoteException;

    void semSetPasswordHistoryLength(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumLength(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumLowerCase(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumNonLetter(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumUpperCase(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordQuality(ComponentName componentName, int i) throws RemoteException;

    void semSetRequireStorageCardEncryption(ComponentName componentName, boolean z, boolean z2) throws RemoteException;

    void semSetSimplePasswordEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void sendLostModeLocationUpdate(AndroidFuture<Boolean> androidFuture) throws RemoteException;

    void setAccountManagementDisabled(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException;

    void setActiveAdmin(ComponentName componentName, boolean z, int i, String str) throws RemoteException;

    void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException;

    boolean setAlwaysOnVpnPackage(ComponentName componentName, String str, boolean z, List<String> list) throws RemoteException;

    void setAppFunctionsPolicy(String str, int i) throws RemoteException;

    void setApplicationExemptions(String str, String str2, int[] iArr) throws RemoteException;

    boolean setApplicationHidden(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException;

    void setApplicationRestrictions(ComponentName componentName, String str, String str2, Bundle bundle, boolean z) throws RemoteException;

    boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String str) throws RemoteException;

    void setAuditLogEnabled(String str, boolean z) throws RemoteException;

    void setAuditLogEventsCallback(String str, IAuditLogEventsCallback iAuditLogEventsCallback) throws RemoteException;

    void setAutoTimeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setAutoTimePolicy(String str, int i) throws RemoteException;

    void setAutoTimeRequired(ComponentName componentName, boolean z) throws RemoteException;

    void setAutoTimeZoneEnabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setAutoTimeZonePolicy(String str, int i) throws RemoteException;

    void setBackupServiceEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setBluetoothContactSharingDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setCameraDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException;

    void setCertInstallerPackage(ComponentName componentName, String str) throws RemoteException;

    void setCommonCriteriaModeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setConfiguredNetworksLockdownState(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException;

    void setCredentialManagerPolicy(PackagePolicy packagePolicy) throws RemoteException;

    void setCrossProfileAppToIgnored(int i, String str) throws RemoteException;

    void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list) throws RemoteException;

    void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setCrossProfilePackages(ComponentName componentName, List<String> list) throws RemoteException;

    void setDefaultDialerApplication(String str) throws RemoteException;

    void setDefaultSmsApplication(ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    void setDelegatedScopes(ComponentName componentName, String str, List<String> list) throws RemoteException;

    boolean setDeviceOwner(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setDeviceOwnerType(ComponentName componentName, int i) throws RemoteException;

    void setDeviceProvisioningConfigApplied() throws RemoteException;

    void setDpcDownloaded(boolean z) throws RemoteException;

    void setDrawables(List<DevicePolicyDrawableResource> list) throws RemoteException;

    void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setFactoryResetProtectionPolicy(ComponentName componentName, String str, FactoryResetProtectionPolicy factoryResetProtectionPolicy) throws RemoteException;

    void setForceEphemeralUsers(ComponentName componentName, boolean z) throws RemoteException;

    int setGlobalPrivateDns(ComponentName componentName, int i, String str) throws RemoteException;

    ComponentName setGlobalProxy(ComponentName componentName, String str, String str2) throws RemoteException;

    void setGlobalSetting(ComponentName componentName, String str, String str2) throws RemoteException;

    void setKeepUninstalledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException;

    boolean setKeyGrantForApp(ComponentName componentName, String str, String str2, String str3, boolean z) throws RemoteException;

    boolean setKeyGrantToWifiAuth(String str, String str2, boolean z) throws RemoteException;

    boolean setKeyPairCertificate(ComponentName componentName, String str, String str2, byte[] bArr, byte[] bArr2, boolean z) throws RemoteException;

    boolean setKeyguardDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setKeyguardDisabledFeatures(ComponentName componentName, String str, int i, boolean z) throws RemoteException;

    void setLocationEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setLockTaskFeatures(ComponentName componentName, String str, int i) throws RemoteException;

    void setLockTaskPackages(ComponentName componentName, String str, String[] strArr) throws RemoteException;

    void setLogoutEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setManagedProfileCallerIdAccessPolicy(PackagePolicy packagePolicy) throws RemoteException;

    void setManagedProfileContactsAccessPolicy(PackagePolicy packagePolicy) throws RemoteException;

    void setManagedProfileMaximumTimeOff(ComponentName componentName, long j) throws RemoteException;

    void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy managedSubscriptionsPolicy) throws RemoteException;

    void setMasterVolumeMuted(ComponentName componentName, boolean z) throws RemoteException;

    void setMaxPolicyStorageLimit(String str, int i) throws RemoteException;

    void setMaximumFailedPasswordsForWipe(ComponentName componentName, String str, int i, boolean z) throws RemoteException;

    void setMaximumTimeToLock(ComponentName componentName, String str, long j, boolean z) throws RemoteException;

    List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list) throws RemoteException;

    void setMinimumRequiredWifiSecurityLevel(String str, int i) throws RemoteException;

    void setMtePolicy(int i, String str) throws RemoteException;

    void setMtePolicyBySystem(String str, int i) throws RemoteException;

    void setNearbyAppStreamingPolicy(int i) throws RemoteException;

    void setNearbyNotificationStreamingPolicy(int i) throws RemoteException;

    void setNetworkLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setNextOperationSafety(int i, int i2) throws RemoteException;

    void setOrganizationColor(ComponentName componentName, int i) throws RemoteException;

    void setOrganizationColorForUser(int i, int i2) throws RemoteException;

    void setOrganizationIdForUser(String str, String str2, int i) throws RemoteException;

    void setOrganizationName(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException;

    void setOverrideApnsEnabled(ComponentName componentName, boolean z) throws RemoteException;

    String[] setPackagesSuspended(ComponentName componentName, String str, String[] strArr, boolean z) throws RemoteException;

    void setPasswordExpirationTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException;

    void setPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPermissionGrantState(ComponentName componentName, String str, String str2, String str3, int i, RemoteCallback remoteCallback) throws RemoteException;

    void setPermissionPolicy(ComponentName componentName, String str, int i) throws RemoteException;

    boolean setPermittedAccessibilityServices(ComponentName componentName, List<String> list) throws RemoteException;

    boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) throws RemoteException;

    boolean setPermittedInputMethods(ComponentName componentName, String str, List<String> list, boolean z) throws RemoteException;

    void setPersonalAppsSuspended(ComponentName componentName, boolean z) throws RemoteException;

    void setPreferentialNetworkServiceConfigs(List<PreferentialNetworkServiceConfig> list) throws RemoteException;

    void setProfileEnabled(ComponentName componentName) throws RemoteException;

    void setProfileName(ComponentName componentName, String str) throws RemoteException;

    boolean setProfileOwner(ComponentName componentName, int i) throws RemoteException;

    void setProfileOwnerOnOrganizationOwnedDevice(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) throws RemoteException;

    void setRequiredPasswordComplexity(String str, int i, boolean z) throws RemoteException;

    void setRequiredStrongAuthTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException;

    boolean setResetPasswordToken(ComponentName componentName, String str, byte[] bArr) throws RemoteException;

    void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) throws RemoteException;

    void setScreenCaptureDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException;

    void setSecondaryLockscreenEnabled(ComponentName componentName, boolean z, PersistableBundle persistableBundle) throws RemoteException;

    void setSecureSetting(ComponentName componentName, String str, String str2) throws RemoteException;

    void setSecurityLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setShortSupportMessage(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException;

    void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    boolean setStatusBarDisabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    int setStorageEncryption(ComponentName componentName, boolean z) throws RemoteException;

    void setStrings(List<DevicePolicyStringResource> list) throws RemoteException;

    void setSystemSetting(ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    void setSystemUpdatePolicy(ComponentName componentName, String str, SystemUpdatePolicy systemUpdatePolicy) throws RemoteException;

    boolean setTime(ComponentName componentName, String str, long j) throws RemoteException;

    boolean setTimeZone(ComponentName componentName, String str, String str2) throws RemoteException;

    void setTrustAgentConfiguration(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, boolean z) throws RemoteException;

    void setUninstallBlocked(ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    void setUsbDataSignalingEnabled(String str, boolean z) throws RemoteException;

    void setUserControlDisabledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException;

    void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException;

    void setUserProvisioningState(int i, int i2) throws RemoteException;

    void setUserRestriction(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException;

    void setUserRestrictionForUser(String str, String str2, boolean z, int i) throws RemoteException;

    void setUserRestrictionGlobally(String str, String str2) throws RemoteException;

    void setUserRestrictionGloballyFromSystem(String str, String str2, boolean z) throws RemoteException;

    void setWifiSsidPolicy(String str, WifiSsidPolicy wifiSsidPolicy) throws RemoteException;

    boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws RemoteException;

    void startManagedQuickContact(String str, long j, boolean z, long j2, Intent intent) throws RemoteException;

    int startUserInBackground(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean startViewCalendarEventInManagedProfile(String str, long j, long j2, long j3, boolean z, int i) throws RemoteException;

    int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) throws RemoteException;

    boolean triggerDevicePolicyEngineMigration(boolean z) throws RemoteException;

    void uninstallCaCerts(ComponentName componentName, String str, String[] strArr) throws RemoteException;

    void uninstallPackageWithActiveAdmins(String str) throws RemoteException;

    boolean updateOverrideApn(ComponentName componentName, int i, ApnSetting apnSetting) throws RemoteException;

    void wipeDataWithReason(String str, int i, String str2, boolean z, boolean z2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDevicePolicyManager {
        public static final String DESCRIPTOR = "android.app.admin.IDevicePolicyManager";
        static final int TRANSACTION_acknowledgeDeviceCompliant = 357;
        static final int TRANSACTION_acknowledgeNewUserDisclaimer = 169;
        static final int TRANSACTION_addCrossProfileIntentFilter = 141;
        static final int TRANSACTION_addCrossProfileWidgetProvider = 219;
        static final int TRANSACTION_addOverrideApn = 321;
        static final int TRANSACTION_addPersistentPreferredActivity = 124;
        static final int TRANSACTION_approveCaCert = 105;
        static final int TRANSACTION_bindDeviceAdminServiceAsUser = 297;
        static final int TRANSACTION_calculateHasIncompatibleAccounts = 439;
        static final int TRANSACTION_canAdminGrantSensorsPermissions = 373;
        static final int TRANSACTION_canProfileOwnerResetPasswordWhenLocked = 359;
        static final int TRANSACTION_canUsbDataSignalingBeDisabled = 376;
        static final int TRANSACTION_checkDeviceIdentifierAccess = 96;
        static final int TRANSACTION_checkProvisioningPrecondition = 250;
        static final int TRANSACTION_choosePrivateKeyAlias = 112;
        static final int TRANSACTION_clearApplicationUserData = 309;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 142;
        static final int TRANSACTION_clearDeviceOwner = 84;
        static final int TRANSACTION_clearOrganizationIdForUser = 264;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 125;
        static final int TRANSACTION_clearProfileOwner = 93;
        static final int TRANSACTION_clearResetPasswordToken = 304;
        static final int TRANSACTION_clearSystemUpdatePolicyFreezePeriodRecord = 238;
        static final int TRANSACTION_createAdminSupportIntent = 154;
        static final int TRANSACTION_createAndManageUser = 160;
        static final int TRANSACTION_createAndProvisionManagedProfile = 364;
        static final int TRANSACTION_createManagedProfile = 365;
        static final int TRANSACTION_enableSystemApp = 171;
        static final int TRANSACTION_enableSystemAppWithIntent = 172;
        static final int TRANSACTION_enforceCanManageCaCerts = 104;
        static final int TRANSACTION_finalizeCreateManagedProfile = 366;
        static final int TRANSACTION_finalizeWorkProfileProvisioning = 368;
        static final int TRANSACTION_forceNetworkLogs = 281;
        static final int TRANSACTION_forceRemoveActiveAdmin = 70;
        static final int TRANSACTION_forceSecurityLogs = 282;
        static final int TRANSACTION_forceSetMaxPolicyStorageLimit = 444;
        static final int TRANSACTION_forceUpdateUserSetupComplete = 291;
        static final int TRANSACTION_generateKeyPair = 110;
        static final int TRANSACTION_getAccountTypesWithManagementDisabled = 175;
        static final int TRANSACTION_getAccountTypesWithManagementDisabledAsUser = 176;
        static final int TRANSACTION_getActiveAdmins = 66;
        static final int TRANSACTION_getAffiliationIds = 274;
        static final int TRANSACTION_getAggregatedPasswordComplexityForUser = 29;
        static final int TRANSACTION_getAllCrossProfilePackages = 340;
        static final int TRANSACTION_getAlwaysOnVpnLockdownAllowlist = 123;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 119;
        static final int TRANSACTION_getAlwaysOnVpnPackageForUser = 120;
        static final int TRANSACTION_getAppFunctionsPolicy = 449;
        static final int TRANSACTION_getApplicationExemptions = 427;
        static final int TRANSACTION_getApplicationRestrictions = 129;
        static final int TRANSACTION_getApplicationRestrictionsManagingPackage = 131;
        static final int TRANSACTION_getAutoTimeEnabled = 225;
        static final int TRANSACTION_getAutoTimePolicy = 227;
        static final int TRANSACTION_getAutoTimeRequired = 223;
        static final int TRANSACTION_getAutoTimeZoneEnabled = 229;
        static final int TRANSACTION_getAutoTimeZonePolicy = 231;
        static final int TRANSACTION_getBindDeviceAdminTargetUsers = 298;
        static final int TRANSACTION_getBluetoothContactSharingDisabled = 215;
        static final int TRANSACTION_getBluetoothContactSharingDisabledForUser = 216;
        static final int TRANSACTION_getCameraDisabled = 55;
        static final int TRANSACTION_getCertInstallerPackage = 117;
        static final int TRANSACTION_getContentProtectionPolicy = 441;
        static final int TRANSACTION_getCredentialManagerPolicy = 210;
        static final int TRANSACTION_getCrossProfileCalendarPackages = 335;
        static final int TRANSACTION_getCrossProfileCalendarPackagesForUser = 337;
        static final int TRANSACTION_getCrossProfileCallerIdDisabled = 200;
        static final int TRANSACTION_getCrossProfileCallerIdDisabledForUser = 201;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabled = 203;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabledForUser = 204;
        static final int TRANSACTION_getCrossProfilePackages = 339;
        static final int TRANSACTION_getCrossProfileWidgetProviders = 221;
        static final int TRANSACTION_getCurrentFailedBiometricAttempts = 32;
        static final int TRANSACTION_getCurrentFailedPasswordAttempts = 31;
        static final int TRANSACTION_getDefaultCrossProfilePackages = 341;
        static final int TRANSACTION_getDelegatePackages = 115;
        static final int TRANSACTION_getDelegatedScopes = 114;
        static final int TRANSACTION_getDeviceOwnerComponent = 80;
        static final int TRANSACTION_getDeviceOwnerComponentOnUser = 81;
        static final int TRANSACTION_getDeviceOwnerLockScreenInfo = 98;
        static final int TRANSACTION_getDeviceOwnerName = 83;
        static final int TRANSACTION_getDeviceOwnerOrganizationName = 269;
        static final int TRANSACTION_getDeviceOwnerType = 371;
        static final int TRANSACTION_getDeviceOwnerUserId = 85;
        static final int TRANSACTION_getDevicePolicyState = 433;
        static final int TRANSACTION_getDisallowedSystemApps = 312;
        static final int TRANSACTION_getDoNotAskCredentialsOnBoot = 242;
        static final int TRANSACTION_getDrawable = 385;
        static final int TRANSACTION_getEndUserSessionMessage = 318;
        static final int TRANSACTION_getEnforcingAdmin = 156;
        static final int TRANSACTION_getEnforcingAdminAndUserDetails = 155;
        static final int TRANSACTION_getEnforcingAdminsForRestriction = 157;
        static final int TRANSACTION_getEnrollmentSpecificId = 362;
        static final int TRANSACTION_getFactoryResetProtectionPolicy = 44;
        static final int TRANSACTION_getFinancedDeviceKioskRoleHolder = 436;
        static final int TRANSACTION_getForceEphemeralUsers = 233;
        static final int TRANSACTION_getGlobalPrivateDnsHost = 331;
        static final int TRANSACTION_getGlobalPrivateDnsMode = 330;
        static final int TRANSACTION_getGlobalProxyAdmin = 48;
        static final int TRANSACTION_getHeadlessDeviceOwnerMode = 447;
        static final int TRANSACTION_getKeepUninstalledPackages = 252;
        static final int TRANSACTION_getKeyPairGrants = 346;
        static final int TRANSACTION_getKeyguardDisabledFeatures = 63;
        static final int TRANSACTION_getLastBugReportRequestTime = 301;
        static final int TRANSACTION_getLastNetworkLogRetrievalTime = 302;
        static final int TRANSACTION_getLastSecurityLogRetrievalTime = 300;
        static final int TRANSACTION_getLockTaskFeatures = 185;
        static final int TRANSACTION_getLockTaskPackages = 182;
        static final int TRANSACTION_getLogoutUserId = 167;
        static final int TRANSACTION_getLongSupportMessage = 259;
        static final int TRANSACTION_getLongSupportMessageForUser = 261;
        static final int TRANSACTION_getManagedProfileCallerIdAccessPolicy = 207;
        static final int TRANSACTION_getManagedProfileContactsAccessPolicy = 212;
        static final int TRANSACTION_getManagedProfileMaximumTimeOff = 355;
        static final int TRANSACTION_getManagedSubscriptionsPolicy = 432;
        static final int TRANSACTION_getMaxPolicyStorageLimit = 445;
        static final int TRANSACTION_getMaximumFailedPasswordsForWipe = 35;
        static final int TRANSACTION_getMaximumTimeToLock = 38;
        static final int TRANSACTION_getMeteredDataDisabledPackages = 320;
        static final int TRANSACTION_getMinimumRequiredWifiSecurityLevel = 378;
        static final int TRANSACTION_getMtePolicy = 430;
        static final int TRANSACTION_getNearbyAppStreamingPolicy = 61;
        static final int TRANSACTION_getNearbyNotificationStreamingPolicy = 59;
        static final int TRANSACTION_getOrganizationColor = 265;
        static final int TRANSACTION_getOrganizationColorForUser = 266;
        static final int TRANSACTION_getOrganizationName = 268;
        static final int TRANSACTION_getOrganizationNameForUser = 270;
        static final int TRANSACTION_getOverrideApns = 324;
        static final int TRANSACTION_getOwnerInstalledCaCerts = 308;
        static final int TRANSACTION_getPasswordComplexity = 26;
        static final int TRANSACTION_getPasswordExpiration = 22;
        static final int TRANSACTION_getPasswordExpirationTimeout = 21;
        static final int TRANSACTION_getPasswordHistoryLength = 19;
        static final int TRANSACTION_getPasswordMinimumLength = 4;
        static final int TRANSACTION_getPasswordMinimumLetters = 10;
        static final int TRANSACTION_getPasswordMinimumLowerCase = 8;
        static final int TRANSACTION_getPasswordMinimumMetrics = 17;
        static final int TRANSACTION_getPasswordMinimumNonLetter = 16;
        static final int TRANSACTION_getPasswordMinimumNumeric = 12;
        static final int TRANSACTION_getPasswordMinimumSymbols = 14;
        static final int TRANSACTION_getPasswordMinimumUpperCase = 6;
        static final int TRANSACTION_getPasswordQuality = 2;
        static final int TRANSACTION_getPendingSystemUpdate = 244;
        static final int TRANSACTION_getPermissionGrantState = 248;
        static final int TRANSACTION_getPermissionPolicy = 246;
        static final int TRANSACTION_getPermittedAccessibilityServices = 144;
        static final int TRANSACTION_getPermittedAccessibilityServicesForUser = 145;
        static final int TRANSACTION_getPermittedCrossProfileNotificationListeners = 152;
        static final int TRANSACTION_getPermittedInputMethods = 148;
        static final int TRANSACTION_getPermittedInputMethodsAsUser = 149;
        static final int TRANSACTION_getPersonalAppsSuspendedReasons = 353;
        static final int TRANSACTION_getPolicyManagedProfiles = 393;
        static final int TRANSACTION_getPolicySizeForAdmin = 446;
        static final int TRANSACTION_getPreferentialNetworkServiceConfigs = 180;
        static final int TRANSACTION_getProfileOwnerAsUser = 87;
        static final int TRANSACTION_getProfileOwnerName = 90;
        static final int TRANSACTION_getProfileOwnerOrDeviceOwnerSupervisionComponent = 88;
        static final int TRANSACTION_getProfileWithMinimumFailedPasswordsForWipe = 33;
        static final int TRANSACTION_getRemoveWarning = 68;
        static final int TRANSACTION_getRequiredPasswordComplexity = 28;
        static final int TRANSACTION_getRequiredStrongAuthTimeout = 40;
        static final int TRANSACTION_getRestrictionsProvider = 134;
        static final int TRANSACTION_getSamsungSDcardEncryptionStatus = 438;
        static final int TRANSACTION_getScreenCaptureDisabled = 57;
        static final int TRANSACTION_getSecondaryUsers = 168;
        static final int TRANSACTION_getShortSupportMessage = 257;
        static final int TRANSACTION_getShortSupportMessageForUser = 260;
        static final int TRANSACTION_getStartUserSessionMessage = 317;
        static final int TRANSACTION_getStorageEncryption = 51;
        static final int TRANSACTION_getStorageEncryptionStatus = 52;
        static final int TRANSACTION_getString = 390;
        static final int TRANSACTION_getSubscriptionIds = 442;
        static final int TRANSACTION_getSystemUpdatePolicy = 237;
        static final int TRANSACTION_getTransferOwnershipBundle = 314;
        static final int TRANSACTION_getTrustAgentConfiguration = 218;
        static final int TRANSACTION_getUserControlDisabledPackages = 350;
        static final int TRANSACTION_getUserProvisioningState = 271;
        static final int TRANSACTION_getUserRestrictions = 139;
        static final int TRANSACTION_getUserRestrictionsGlobally = 140;
        static final int TRANSACTION_getWifiMacAddress = 254;
        static final int TRANSACTION_getWifiSsidPolicy = 380;
        static final int TRANSACTION_hasDeviceOwner = 82;
        static final int TRANSACTION_hasGrantedPolicy = 71;
        static final int TRANSACTION_hasKeyPair = 109;
        static final int TRANSACTION_hasLockdownAdminConfiguredNetworks = 190;
        static final int TRANSACTION_hasManagedProfileCallerIdAccess = 208;
        static final int TRANSACTION_hasManagedProfileContactsAccess = 213;
        static final int TRANSACTION_hasUserSetupCompleted = 94;
        static final int TRANSACTION_installCaCert = 102;
        static final int TRANSACTION_installExistingPackage = 173;
        static final int TRANSACTION_installKeyPair = 107;
        static final int TRANSACTION_installUpdateFromFile = 333;
        static final int TRANSACTION_isAccessibilityServicePermittedByAdmin = 146;
        static final int TRANSACTION_isActivePasswordSufficient = 23;
        static final int TRANSACTION_isActivePasswordSufficientForDeviceRequirement = 24;
        static final int TRANSACTION_isAdminActive = 65;
        static final int TRANSACTION_isAffiliatedUser = 276;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabled = 121;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabledForUser = 122;
        static final int TRANSACTION_isApplicationHidden = 159;
        static final int TRANSACTION_isAuditLogEnabled = 284;
        static final int TRANSACTION_isBackupServiceEnabled = 293;
        static final int TRANSACTION_isCaCertApproved = 106;
        static final int TRANSACTION_isCallerApplicationRestrictionsManagingPackage = 132;
        static final int TRANSACTION_isCallingUserAffiliated = 275;
        static final int TRANSACTION_isCommonCriteriaModeEnabled = 352;
        static final int TRANSACTION_isComplianceAcknowledgementRequired = 358;
        static final int TRANSACTION_isCurrentInputMethodSetByOwner = 307;
        static final int TRANSACTION_isDeviceFinanced = 435;
        static final int TRANSACTION_isDevicePotentiallyStolen = 381;
        static final int TRANSACTION_isDeviceProvisioned = 288;
        static final int TRANSACTION_isDeviceProvisioningConfigApplied = 289;
        static final int TRANSACTION_isDpcDownloaded = 386;
        static final int TRANSACTION_isEphemeralUser = 299;
        static final int TRANSACTION_isFactoryResetProtectionPolicySupported = 45;
        static final int TRANSACTION_isInputMethodPermittedByAdmin = 150;
        static final int TRANSACTION_isKeyPairGrantedToWifiAuth = 348;
        static final int TRANSACTION_isLockTaskPermitted = 183;
        static final int TRANSACTION_isLogoutEnabled = 311;
        static final int TRANSACTION_isManagedKiosk = 342;
        static final int TRANSACTION_isManagedProfile = 253;
        static final int TRANSACTION_isMasterVolumeMuted = 195;
        static final int TRANSACTION_isMeteredDataDisabledPackageForUser = 327;
        static final int TRANSACTION_isNetworkLoggingEnabled = 295;
        static final int TRANSACTION_isNewUserDisclaimerAcknowledged = 170;
        static final int TRANSACTION_isNotificationListenerServicePermitted = 153;
        static final int TRANSACTION_isOrganizationOwnedDeviceWithManagedProfile = 95;
        static final int TRANSACTION_isOverrideApnEnabled = 326;
        static final int TRANSACTION_isPackageAllowedToAccessCalendarForUser = 336;
        static final int TRANSACTION_isPackageSuspended = 100;
        static final int TRANSACTION_isPasswordSufficientAfterProfileUnification = 25;
        static final int TRANSACTION_isProvisioningAllowed = 249;
        static final int TRANSACTION_isRemovingAdmin = 234;
        static final int TRANSACTION_isResetPasswordTokenActive = 305;
        static final int TRANSACTION_isSafeOperation = 361;
        static final int TRANSACTION_isSecondaryLockscreenEnabled = 178;
        static final int TRANSACTION_isSecurityLoggingEnabled = 278;
        static final int TRANSACTION_isStatusBarDisabled = 241;
        static final int TRANSACTION_isSupervisionComponent = 89;
        static final int TRANSACTION_isUnattendedManagedKiosk = 343;
        static final int TRANSACTION_isUninstallBlocked = 198;
        static final int TRANSACTION_isUninstallInQueue = 286;
        static final int TRANSACTION_isUsbDataSignalingEnabled = 375;
        static final int TRANSACTION_isUsingUnifiedPassword = 30;
        static final int TRANSACTION_listForegroundAffiliatedUsers = 382;
        static final int TRANSACTION_listPolicyExemptApps = 101;
        static final int TRANSACTION_lockNow = 41;
        static final int TRANSACTION_logoutUser = 165;
        static final int TRANSACTION_logoutUserInternal = 166;
        static final int TRANSACTION_notifyLockTaskModeChanged = 196;
        static final int TRANSACTION_notifyPendingSystemUpdate = 243;
        static final int TRANSACTION_packageHasActiveAdmins = 67;
        static final int TRANSACTION_provisionFullyManagedDevice = 367;
        static final int TRANSACTION_reboot = 255;
        static final int TRANSACTION_removeActiveAdmin = 69;
        static final int TRANSACTION_removeCrossProfileWidgetProvider = 220;
        static final int TRANSACTION_removeKeyPair = 108;
        static final int TRANSACTION_removeManagedProfile = 369;
        static final int TRANSACTION_removeOverrideApn = 323;
        static final int TRANSACTION_removeUser = 161;
        static final int TRANSACTION_reportFailedBiometricAttempt = 75;
        static final int TRANSACTION_reportFailedPasswordAttempt = 73;
        static final int TRANSACTION_reportFailedPasswordAttemptWithFailureCount = 328;
        static final int TRANSACTION_reportKeyguardDismissed = 77;
        static final int TRANSACTION_reportKeyguardSecured = 78;
        static final int TRANSACTION_reportPasswordChanged = 72;
        static final int TRANSACTION_reportSuccessfulBiometricAttempt = 76;
        static final int TRANSACTION_reportSuccessfulPasswordAttempt = 74;
        static final int TRANSACTION_requestBugreport = 53;
        static final int TRANSACTION_resetDefaultCrossProfileIntentFilters = 372;
        static final int TRANSACTION_resetDrawables = 384;
        static final int TRANSACTION_resetPassword = 36;
        static final int TRANSACTION_resetPasswordWithToken = 306;
        static final int TRANSACTION_resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState = 391;
        static final int TRANSACTION_resetStrings = 389;
        static final int TRANSACTION_retrieveNetworkLogs = 296;
        static final int TRANSACTION_retrievePreRebootSecurityLogs = 280;
        static final int TRANSACTION_retrieveSecurityLogs = 279;
        static final int TRANSACTION_semGetAllowBluetoothMode = 418;
        static final int TRANSACTION_semGetAllowBrowser = 414;
        static final int TRANSACTION_semGetAllowDesktopSync = 420;
        static final int TRANSACTION_semGetAllowInternetSharing = 416;
        static final int TRANSACTION_semGetAllowIrda = 422;
        static final int TRANSACTION_semGetAllowPopImapEmail = 412;
        static final int TRANSACTION_semGetAllowStorageCard = 406;
        static final int TRANSACTION_semGetAllowTextMessaging = 410;
        static final int TRANSACTION_semGetAllowWifi = 408;
        static final int TRANSACTION_semGetRequireStorageCardEncryption = 424;
        static final int TRANSACTION_semIsActivePasswordSufficient = 401;
        static final int TRANSACTION_semIsSimplePasswordEnabled = 403;
        static final int TRANSACTION_semSetAllowBluetoothMode = 417;
        static final int TRANSACTION_semSetAllowBrowser = 413;
        static final int TRANSACTION_semSetAllowDesktopSync = 419;
        static final int TRANSACTION_semSetAllowInternetSharing = 415;
        static final int TRANSACTION_semSetAllowIrda = 421;
        static final int TRANSACTION_semSetAllowPopImapEmail = 411;
        static final int TRANSACTION_semSetAllowStorageCard = 405;
        static final int TRANSACTION_semSetAllowTextMessaging = 409;
        static final int TRANSACTION_semSetAllowWifi = 407;
        static final int TRANSACTION_semSetChangeNotificationEnabled = 425;
        static final int TRANSACTION_semSetKeyguardDisabledFeatures = 404;
        static final int TRANSACTION_semSetPasswordExpirationTimeout = 400;
        static final int TRANSACTION_semSetPasswordHistoryLength = 399;
        static final int TRANSACTION_semSetPasswordMinimumLength = 395;
        static final int TRANSACTION_semSetPasswordMinimumLowerCase = 397;
        static final int TRANSACTION_semSetPasswordMinimumNonLetter = 398;
        static final int TRANSACTION_semSetPasswordMinimumUpperCase = 396;
        static final int TRANSACTION_semSetPasswordQuality = 394;
        static final int TRANSACTION_semSetRequireStorageCardEncryption = 423;
        static final int TRANSACTION_semSetSimplePasswordEnabled = 402;
        static final int TRANSACTION_sendLostModeLocationUpdate = 46;
        static final int TRANSACTION_setAccountManagementDisabled = 174;
        static final int TRANSACTION_setActiveAdmin = 64;
        static final int TRANSACTION_setAffiliationIds = 273;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 118;
        static final int TRANSACTION_setAppFunctionsPolicy = 448;
        static final int TRANSACTION_setApplicationExemptions = 426;
        static final int TRANSACTION_setApplicationHidden = 158;
        static final int TRANSACTION_setApplicationRestrictions = 128;
        static final int TRANSACTION_setApplicationRestrictionsManagingPackage = 130;
        static final int TRANSACTION_setAuditLogEnabled = 283;
        static final int TRANSACTION_setAuditLogEventsCallback = 285;
        static final int TRANSACTION_setAutoTimeEnabled = 224;
        static final int TRANSACTION_setAutoTimePolicy = 226;
        static final int TRANSACTION_setAutoTimeRequired = 222;
        static final int TRANSACTION_setAutoTimeZoneEnabled = 228;
        static final int TRANSACTION_setAutoTimeZonePolicy = 230;
        static final int TRANSACTION_setBackupServiceEnabled = 292;
        static final int TRANSACTION_setBluetoothContactSharingDisabled = 214;
        static final int TRANSACTION_setCameraDisabled = 54;
        static final int TRANSACTION_setCertInstallerPackage = 116;
        static final int TRANSACTION_setCommonCriteriaModeEnabled = 351;
        static final int TRANSACTION_setConfiguredNetworksLockdownState = 189;
        static final int TRANSACTION_setContentProtectionPolicy = 440;
        static final int TRANSACTION_setCredentialManagerPolicy = 209;
        static final int TRANSACTION_setCrossProfileAppToIgnored = 437;
        static final int TRANSACTION_setCrossProfileCalendarPackages = 334;
        static final int TRANSACTION_setCrossProfileCallerIdDisabled = 199;
        static final int TRANSACTION_setCrossProfileContactsSearchDisabled = 202;
        static final int TRANSACTION_setCrossProfilePackages = 338;
        static final int TRANSACTION_setDefaultDialerApplication = 127;
        static final int TRANSACTION_setDefaultSmsApplication = 126;
        static final int TRANSACTION_setDelegatedScopes = 113;
        static final int TRANSACTION_setDeviceOwner = 79;
        static final int TRANSACTION_setDeviceOwnerLockScreenInfo = 97;
        static final int TRANSACTION_setDeviceOwnerType = 370;
        static final int TRANSACTION_setDeviceProvisioningConfigApplied = 290;
        static final int TRANSACTION_setDpcDownloaded = 387;
        static final int TRANSACTION_setDrawables = 383;
        static final int TRANSACTION_setEndUserSessionMessage = 316;
        static final int TRANSACTION_setFactoryResetProtectionPolicy = 43;
        static final int TRANSACTION_setForceEphemeralUsers = 232;
        static final int TRANSACTION_setGlobalPrivateDns = 329;
        static final int TRANSACTION_setGlobalProxy = 47;
        static final int TRANSACTION_setGlobalSetting = 186;
        static final int TRANSACTION_setKeepUninstalledPackages = 251;
        static final int TRANSACTION_setKeyGrantForApp = 345;
        static final int TRANSACTION_setKeyGrantToWifiAuth = 347;
        static final int TRANSACTION_setKeyPairCertificate = 111;
        static final int TRANSACTION_setKeyguardDisabled = 239;
        static final int TRANSACTION_setKeyguardDisabledFeatures = 62;
        static final int TRANSACTION_setLocationEnabled = 191;
        static final int TRANSACTION_setLockTaskFeatures = 184;
        static final int TRANSACTION_setLockTaskPackages = 181;
        static final int TRANSACTION_setLogoutEnabled = 310;
        static final int TRANSACTION_setLongSupportMessage = 258;
        static final int TRANSACTION_setManagedProfileCallerIdAccessPolicy = 206;
        static final int TRANSACTION_setManagedProfileContactsAccessPolicy = 211;
        static final int TRANSACTION_setManagedProfileMaximumTimeOff = 356;
        static final int TRANSACTION_setManagedSubscriptionsPolicy = 431;
        static final int TRANSACTION_setMasterVolumeMuted = 194;
        static final int TRANSACTION_setMaxPolicyStorageLimit = 443;
        static final int TRANSACTION_setMaximumFailedPasswordsForWipe = 34;
        static final int TRANSACTION_setMaximumTimeToLock = 37;
        static final int TRANSACTION_setMeteredDataDisabledPackages = 319;
        static final int TRANSACTION_setMinimumRequiredWifiSecurityLevel = 377;
        static final int TRANSACTION_setMtePolicy = 428;
        static final int TRANSACTION_setMtePolicyBySystem = 429;
        static final int TRANSACTION_setNearbyAppStreamingPolicy = 60;
        static final int TRANSACTION_setNearbyNotificationStreamingPolicy = 58;
        static final int TRANSACTION_setNetworkLoggingEnabled = 294;
        static final int TRANSACTION_setNextOperationSafety = 360;
        static final int TRANSACTION_setOrganizationColor = 262;
        static final int TRANSACTION_setOrganizationColorForUser = 263;
        static final int TRANSACTION_setOrganizationIdForUser = 363;
        static final int TRANSACTION_setOrganizationName = 267;
        static final int TRANSACTION_setOverrideApnsEnabled = 325;
        static final int TRANSACTION_setPackagesSuspended = 99;
        static final int TRANSACTION_setPasswordExpirationTimeout = 20;
        static final int TRANSACTION_setPasswordHistoryLength = 18;
        static final int TRANSACTION_setPasswordMinimumLength = 3;
        static final int TRANSACTION_setPasswordMinimumLetters = 9;
        static final int TRANSACTION_setPasswordMinimumLowerCase = 7;
        static final int TRANSACTION_setPasswordMinimumNonLetter = 15;
        static final int TRANSACTION_setPasswordMinimumNumeric = 11;
        static final int TRANSACTION_setPasswordMinimumSymbols = 13;
        static final int TRANSACTION_setPasswordMinimumUpperCase = 5;
        static final int TRANSACTION_setPasswordQuality = 1;
        static final int TRANSACTION_setPermissionGrantState = 247;
        static final int TRANSACTION_setPermissionPolicy = 245;
        static final int TRANSACTION_setPermittedAccessibilityServices = 143;
        static final int TRANSACTION_setPermittedCrossProfileNotificationListeners = 151;
        static final int TRANSACTION_setPermittedInputMethods = 147;
        static final int TRANSACTION_setPersonalAppsSuspended = 354;
        static final int TRANSACTION_setPreferentialNetworkServiceConfigs = 179;
        static final int TRANSACTION_setProfileEnabled = 91;
        static final int TRANSACTION_setProfileName = 92;
        static final int TRANSACTION_setProfileOwner = 86;
        static final int TRANSACTION_setProfileOwnerOnOrganizationOwnedDevice = 332;
        static final int TRANSACTION_setRecommendedGlobalProxy = 49;
        static final int TRANSACTION_setRequiredPasswordComplexity = 27;
        static final int TRANSACTION_setRequiredStrongAuthTimeout = 39;
        static final int TRANSACTION_setResetPasswordToken = 303;
        static final int TRANSACTION_setRestrictionsProvider = 133;
        static final int TRANSACTION_setScreenCaptureDisabled = 56;
        static final int TRANSACTION_setSecondaryLockscreenEnabled = 177;
        static final int TRANSACTION_setSecureSetting = 188;
        static final int TRANSACTION_setSecurityLoggingEnabled = 277;
        static final int TRANSACTION_setShortSupportMessage = 256;
        static final int TRANSACTION_setStartUserSessionMessage = 315;
        static final int TRANSACTION_setStatusBarDisabled = 240;
        static final int TRANSACTION_setStorageEncryption = 50;
        static final int TRANSACTION_setStrings = 388;
        static final int TRANSACTION_setSystemSetting = 187;
        static final int TRANSACTION_setSystemUpdatePolicy = 236;
        static final int TRANSACTION_setTime = 192;
        static final int TRANSACTION_setTimeZone = 193;
        static final int TRANSACTION_setTrustAgentConfiguration = 217;
        static final int TRANSACTION_setUninstallBlocked = 197;
        static final int TRANSACTION_setUsbDataSignalingEnabled = 374;
        static final int TRANSACTION_setUserControlDisabledPackages = 349;
        static final int TRANSACTION_setUserIcon = 235;
        static final int TRANSACTION_setUserProvisioningState = 272;
        static final int TRANSACTION_setUserRestriction = 135;
        static final int TRANSACTION_setUserRestrictionForUser = 136;
        static final int TRANSACTION_setUserRestrictionGlobally = 137;
        static final int TRANSACTION_setUserRestrictionGloballyFromSystem = 138;
        static final int TRANSACTION_setWifiSsidPolicy = 379;
        static final int TRANSACTION_shouldAllowBypassingDevicePolicyManagementRoleQualification = 392;
        static final int TRANSACTION_startManagedQuickContact = 205;
        static final int TRANSACTION_startUserInBackground = 163;
        static final int TRANSACTION_startViewCalendarEventInManagedProfile = 344;
        static final int TRANSACTION_stopUser = 164;
        static final int TRANSACTION_switchUser = 162;
        static final int TRANSACTION_transferOwnership = 313;
        static final int TRANSACTION_triggerDevicePolicyEngineMigration = 434;
        static final int TRANSACTION_uninstallCaCerts = 103;
        static final int TRANSACTION_uninstallPackageWithActiveAdmins = 287;
        static final int TRANSACTION_updateOverrideApn = 322;
        static final int TRANSACTION_wipeDataWithReason = 42;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 448;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDevicePolicyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDevicePolicyManager)) {
                return (IDevicePolicyManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setPasswordQuality";
                case 2:
                    return "getPasswordQuality";
                case 3:
                    return "setPasswordMinimumLength";
                case 4:
                    return "getPasswordMinimumLength";
                case 5:
                    return "setPasswordMinimumUpperCase";
                case 6:
                    return "getPasswordMinimumUpperCase";
                case 7:
                    return "setPasswordMinimumLowerCase";
                case 8:
                    return "getPasswordMinimumLowerCase";
                case 9:
                    return "setPasswordMinimumLetters";
                case 10:
                    return "getPasswordMinimumLetters";
                case 11:
                    return "setPasswordMinimumNumeric";
                case 12:
                    return "getPasswordMinimumNumeric";
                case 13:
                    return "setPasswordMinimumSymbols";
                case 14:
                    return "getPasswordMinimumSymbols";
                case 15:
                    return "setPasswordMinimumNonLetter";
                case 16:
                    return "getPasswordMinimumNonLetter";
                case 17:
                    return "getPasswordMinimumMetrics";
                case 18:
                    return "setPasswordHistoryLength";
                case 19:
                    return "getPasswordHistoryLength";
                case 20:
                    return "setPasswordExpirationTimeout";
                case 21:
                    return "getPasswordExpirationTimeout";
                case 22:
                    return "getPasswordExpiration";
                case 23:
                    return "isActivePasswordSufficient";
                case 24:
                    return "isActivePasswordSufficientForDeviceRequirement";
                case 25:
                    return "isPasswordSufficientAfterProfileUnification";
                case 26:
                    return "getPasswordComplexity";
                case 27:
                    return "setRequiredPasswordComplexity";
                case 28:
                    return "getRequiredPasswordComplexity";
                case 29:
                    return "getAggregatedPasswordComplexityForUser";
                case 30:
                    return "isUsingUnifiedPassword";
                case 31:
                    return SecContentProviderURI.PASSWORDPOLICY_GETCURRENTFAILEDPASSWORDATEEMPTS_METHOD;
                case 32:
                    return "getCurrentFailedBiometricAttempts";
                case 33:
                    return "getProfileWithMinimumFailedPasswordsForWipe";
                case 34:
                    return "setMaximumFailedPasswordsForWipe";
                case 35:
                    return "getMaximumFailedPasswordsForWipe";
                case 36:
                    return "resetPassword";
                case 37:
                    return "setMaximumTimeToLock";
                case 38:
                    return "getMaximumTimeToLock";
                case 39:
                    return "setRequiredStrongAuthTimeout";
                case 40:
                    return "getRequiredStrongAuthTimeout";
                case 41:
                    return "lockNow";
                case 42:
                    return "wipeDataWithReason";
                case 43:
                    return "setFactoryResetProtectionPolicy";
                case 44:
                    return "getFactoryResetProtectionPolicy";
                case 45:
                    return "isFactoryResetProtectionPolicySupported";
                case 46:
                    return "sendLostModeLocationUpdate";
                case 47:
                    return "setGlobalProxy";
                case 48:
                    return "getGlobalProxyAdmin";
                case 49:
                    return "setRecommendedGlobalProxy";
                case 50:
                    return "setStorageEncryption";
                case 51:
                    return "getStorageEncryption";
                case 52:
                    return "getStorageEncryptionStatus";
                case 53:
                    return "requestBugreport";
                case 54:
                    return "setCameraDisabled";
                case 55:
                    return "getCameraDisabled";
                case 56:
                    return "setScreenCaptureDisabled";
                case 57:
                    return "getScreenCaptureDisabled";
                case 58:
                    return "setNearbyNotificationStreamingPolicy";
                case 59:
                    return "getNearbyNotificationStreamingPolicy";
                case 60:
                    return "setNearbyAppStreamingPolicy";
                case 61:
                    return "getNearbyAppStreamingPolicy";
                case 62:
                    return "setKeyguardDisabledFeatures";
                case 63:
                    return "getKeyguardDisabledFeatures";
                case 64:
                    return "setActiveAdmin";
                case 65:
                    return "isAdminActive";
                case 66:
                    return SecContentProviderURI.ENTERPRISEDEVICEMANAGERPOLICY_ACTIVEADMINS_METHOD;
                case 67:
                    return "packageHasActiveAdmins";
                case 68:
                    return "getRemoveWarning";
                case 69:
                    return "removeActiveAdmin";
                case 70:
                    return "forceRemoveActiveAdmin";
                case 71:
                    return "hasGrantedPolicy";
                case 72:
                    return "reportPasswordChanged";
                case 73:
                    return "reportFailedPasswordAttempt";
                case 74:
                    return "reportSuccessfulPasswordAttempt";
                case 75:
                    return "reportFailedBiometricAttempt";
                case 76:
                    return "reportSuccessfulBiometricAttempt";
                case 77:
                    return "reportKeyguardDismissed";
                case 78:
                    return "reportKeyguardSecured";
                case 79:
                    return "setDeviceOwner";
                case 80:
                    return "getDeviceOwnerComponent";
                case 81:
                    return "getDeviceOwnerComponentOnUser";
                case 82:
                    return "hasDeviceOwner";
                case 83:
                    return "getDeviceOwnerName";
                case 84:
                    return "clearDeviceOwner";
                case 85:
                    return "getDeviceOwnerUserId";
                case 86:
                    return "setProfileOwner";
                case 87:
                    return "getProfileOwnerAsUser";
                case 88:
                    return "getProfileOwnerOrDeviceOwnerSupervisionComponent";
                case 89:
                    return "isSupervisionComponent";
                case 90:
                    return "getProfileOwnerName";
                case 91:
                    return "setProfileEnabled";
                case 92:
                    return "setProfileName";
                case 93:
                    return "clearProfileOwner";
                case 94:
                    return "hasUserSetupCompleted";
                case 95:
                    return "isOrganizationOwnedDeviceWithManagedProfile";
                case 96:
                    return "checkDeviceIdentifierAccess";
                case 97:
                    return "setDeviceOwnerLockScreenInfo";
                case 98:
                    return "getDeviceOwnerLockScreenInfo";
                case 99:
                    return "setPackagesSuspended";
                case 100:
                    return "isPackageSuspended";
                case 101:
                    return "listPolicyExemptApps";
                case 102:
                    return "installCaCert";
                case 103:
                    return "uninstallCaCerts";
                case 104:
                    return "enforceCanManageCaCerts";
                case 105:
                    return "approveCaCert";
                case 106:
                    return "isCaCertApproved";
                case 107:
                    return "installKeyPair";
                case 108:
                    return "removeKeyPair";
                case 109:
                    return "hasKeyPair";
                case 110:
                    return "generateKeyPair";
                case 111:
                    return "setKeyPairCertificate";
                case 112:
                    return "choosePrivateKeyAlias";
                case 113:
                    return "setDelegatedScopes";
                case 114:
                    return "getDelegatedScopes";
                case 115:
                    return "getDelegatePackages";
                case 116:
                    return "setCertInstallerPackage";
                case 117:
                    return "getCertInstallerPackage";
                case 118:
                    return "setAlwaysOnVpnPackage";
                case 119:
                    return "getAlwaysOnVpnPackage";
                case 120:
                    return "getAlwaysOnVpnPackageForUser";
                case 121:
                    return "isAlwaysOnVpnLockdownEnabled";
                case 122:
                    return "isAlwaysOnVpnLockdownEnabledForUser";
                case 123:
                    return "getAlwaysOnVpnLockdownAllowlist";
                case 124:
                    return "addPersistentPreferredActivity";
                case 125:
                    return "clearPackagePersistentPreferredActivities";
                case 126:
                    return "setDefaultSmsApplication";
                case 127:
                    return "setDefaultDialerApplication";
                case 128:
                    return "setApplicationRestrictions";
                case 129:
                    return "getApplicationRestrictions";
                case 130:
                    return "setApplicationRestrictionsManagingPackage";
                case 131:
                    return "getApplicationRestrictionsManagingPackage";
                case 132:
                    return "isCallerApplicationRestrictionsManagingPackage";
                case 133:
                    return "setRestrictionsProvider";
                case 134:
                    return "getRestrictionsProvider";
                case 135:
                    return "setUserRestriction";
                case 136:
                    return "setUserRestrictionForUser";
                case 137:
                    return "setUserRestrictionGlobally";
                case 138:
                    return "setUserRestrictionGloballyFromSystem";
                case 139:
                    return "getUserRestrictions";
                case 140:
                    return "getUserRestrictionsGlobally";
                case 141:
                    return "addCrossProfileIntentFilter";
                case 142:
                    return "clearCrossProfileIntentFilters";
                case 143:
                    return "setPermittedAccessibilityServices";
                case 144:
                    return "getPermittedAccessibilityServices";
                case 145:
                    return "getPermittedAccessibilityServicesForUser";
                case 146:
                    return "isAccessibilityServicePermittedByAdmin";
                case 147:
                    return "setPermittedInputMethods";
                case 148:
                    return "getPermittedInputMethods";
                case 149:
                    return "getPermittedInputMethodsAsUser";
                case 150:
                    return "isInputMethodPermittedByAdmin";
                case 151:
                    return "setPermittedCrossProfileNotificationListeners";
                case 152:
                    return "getPermittedCrossProfileNotificationListeners";
                case 153:
                    return "isNotificationListenerServicePermitted";
                case 154:
                    return "createAdminSupportIntent";
                case 155:
                    return "getEnforcingAdminAndUserDetails";
                case 156:
                    return "getEnforcingAdmin";
                case 157:
                    return "getEnforcingAdminsForRestriction";
                case 158:
                    return "setApplicationHidden";
                case 159:
                    return "isApplicationHidden";
                case 160:
                    return "createAndManageUser";
                case 161:
                    return "removeUser";
                case 162:
                    return "switchUser";
                case 163:
                    return "startUserInBackground";
                case 164:
                    return "stopUser";
                case 165:
                    return "logoutUser";
                case 166:
                    return "logoutUserInternal";
                case 167:
                    return "getLogoutUserId";
                case 168:
                    return "getSecondaryUsers";
                case 169:
                    return "acknowledgeNewUserDisclaimer";
                case 170:
                    return "isNewUserDisclaimerAcknowledged";
                case 171:
                    return "enableSystemApp";
                case 172:
                    return "enableSystemAppWithIntent";
                case 173:
                    return "installExistingPackage";
                case 174:
                    return "setAccountManagementDisabled";
                case 175:
                    return "getAccountTypesWithManagementDisabled";
                case 176:
                    return "getAccountTypesWithManagementDisabledAsUser";
                case 177:
                    return "setSecondaryLockscreenEnabled";
                case 178:
                    return "isSecondaryLockscreenEnabled";
                case 179:
                    return "setPreferentialNetworkServiceConfigs";
                case 180:
                    return "getPreferentialNetworkServiceConfigs";
                case 181:
                    return "setLockTaskPackages";
                case 182:
                    return "getLockTaskPackages";
                case 183:
                    return "isLockTaskPermitted";
                case 184:
                    return "setLockTaskFeatures";
                case 185:
                    return "getLockTaskFeatures";
                case 186:
                    return "setGlobalSetting";
                case 187:
                    return "setSystemSetting";
                case 188:
                    return "setSecureSetting";
                case 189:
                    return "setConfiguredNetworksLockdownState";
                case 190:
                    return "hasLockdownAdminConfiguredNetworks";
                case 191:
                    return "setLocationEnabled";
                case 192:
                    return "setTime";
                case 193:
                    return "setTimeZone";
                case 194:
                    return "setMasterVolumeMuted";
                case 195:
                    return "isMasterVolumeMuted";
                case 196:
                    return "notifyLockTaskModeChanged";
                case 197:
                    return "setUninstallBlocked";
                case 198:
                    return "isUninstallBlocked";
                case 199:
                    return "setCrossProfileCallerIdDisabled";
                case 200:
                    return "getCrossProfileCallerIdDisabled";
                case 201:
                    return "getCrossProfileCallerIdDisabledForUser";
                case 202:
                    return "setCrossProfileContactsSearchDisabled";
                case 203:
                    return "getCrossProfileContactsSearchDisabled";
                case 204:
                    return "getCrossProfileContactsSearchDisabledForUser";
                case 205:
                    return "startManagedQuickContact";
                case 206:
                    return "setManagedProfileCallerIdAccessPolicy";
                case 207:
                    return "getManagedProfileCallerIdAccessPolicy";
                case 208:
                    return "hasManagedProfileCallerIdAccess";
                case 209:
                    return "setCredentialManagerPolicy";
                case 210:
                    return "getCredentialManagerPolicy";
                case 211:
                    return "setManagedProfileContactsAccessPolicy";
                case 212:
                    return "getManagedProfileContactsAccessPolicy";
                case 213:
                    return "hasManagedProfileContactsAccess";
                case 214:
                    return "setBluetoothContactSharingDisabled";
                case 215:
                    return "getBluetoothContactSharingDisabled";
                case 216:
                    return "getBluetoothContactSharingDisabledForUser";
                case 217:
                    return "setTrustAgentConfiguration";
                case 218:
                    return "getTrustAgentConfiguration";
                case 219:
                    return "addCrossProfileWidgetProvider";
                case 220:
                    return "removeCrossProfileWidgetProvider";
                case 221:
                    return "getCrossProfileWidgetProviders";
                case 222:
                    return "setAutoTimeRequired";
                case 223:
                    return "getAutoTimeRequired";
                case 224:
                    return "setAutoTimeEnabled";
                case 225:
                    return "getAutoTimeEnabled";
                case 226:
                    return "setAutoTimePolicy";
                case 227:
                    return "getAutoTimePolicy";
                case 228:
                    return "setAutoTimeZoneEnabled";
                case 229:
                    return "getAutoTimeZoneEnabled";
                case 230:
                    return "setAutoTimeZonePolicy";
                case 231:
                    return "getAutoTimeZonePolicy";
                case 232:
                    return "setForceEphemeralUsers";
                case 233:
                    return "getForceEphemeralUsers";
                case 234:
                    return "isRemovingAdmin";
                case 235:
                    return "setUserIcon";
                case 236:
                    return "setSystemUpdatePolicy";
                case 237:
                    return "getSystemUpdatePolicy";
                case 238:
                    return "clearSystemUpdatePolicyFreezePeriodRecord";
                case 239:
                    return "setKeyguardDisabled";
                case 240:
                    return "setStatusBarDisabled";
                case 241:
                    return "isStatusBarDisabled";
                case 242:
                    return "getDoNotAskCredentialsOnBoot";
                case 243:
                    return "notifyPendingSystemUpdate";
                case 244:
                    return "getPendingSystemUpdate";
                case 245:
                    return "setPermissionPolicy";
                case 246:
                    return "getPermissionPolicy";
                case 247:
                    return "setPermissionGrantState";
                case 248:
                    return "getPermissionGrantState";
                case 249:
                    return "isProvisioningAllowed";
                case 250:
                    return "checkProvisioningPrecondition";
                case 251:
                    return "setKeepUninstalledPackages";
                case 252:
                    return "getKeepUninstalledPackages";
                case 253:
                    return "isManagedProfile";
                case 254:
                    return "getWifiMacAddress";
                case 255:
                    return "reboot";
                case 256:
                    return "setShortSupportMessage";
                case 257:
                    return "getShortSupportMessage";
                case 258:
                    return "setLongSupportMessage";
                case 259:
                    return "getLongSupportMessage";
                case 260:
                    return "getShortSupportMessageForUser";
                case 261:
                    return "getLongSupportMessageForUser";
                case 262:
                    return "setOrganizationColor";
                case 263:
                    return "setOrganizationColorForUser";
                case 264:
                    return "clearOrganizationIdForUser";
                case 265:
                    return "getOrganizationColor";
                case 266:
                    return "getOrganizationColorForUser";
                case 267:
                    return "setOrganizationName";
                case 268:
                    return "getOrganizationName";
                case 269:
                    return "getDeviceOwnerOrganizationName";
                case 270:
                    return "getOrganizationNameForUser";
                case 271:
                    return "getUserProvisioningState";
                case 272:
                    return "setUserProvisioningState";
                case 273:
                    return "setAffiliationIds";
                case 274:
                    return "getAffiliationIds";
                case 275:
                    return "isCallingUserAffiliated";
                case 276:
                    return "isAffiliatedUser";
                case 277:
                    return "setSecurityLoggingEnabled";
                case 278:
                    return "isSecurityLoggingEnabled";
                case 279:
                    return "retrieveSecurityLogs";
                case 280:
                    return "retrievePreRebootSecurityLogs";
                case 281:
                    return "forceNetworkLogs";
                case 282:
                    return "forceSecurityLogs";
                case 283:
                    return "setAuditLogEnabled";
                case 284:
                    return SecContentProviderURI.AUDITLOGPOLICY_AUDITLOGENABLED_METHOD;
                case 285:
                    return "setAuditLogEventsCallback";
                case 286:
                    return "isUninstallInQueue";
                case 287:
                    return "uninstallPackageWithActiveAdmins";
                case 288:
                    return "isDeviceProvisioned";
                case 289:
                    return "isDeviceProvisioningConfigApplied";
                case 290:
                    return "setDeviceProvisioningConfigApplied";
                case 291:
                    return "forceUpdateUserSetupComplete";
                case 292:
                    return "setBackupServiceEnabled";
                case 293:
                    return "isBackupServiceEnabled";
                case 294:
                    return "setNetworkLoggingEnabled";
                case 295:
                    return "isNetworkLoggingEnabled";
                case 296:
                    return "retrieveNetworkLogs";
                case 297:
                    return "bindDeviceAdminServiceAsUser";
                case 298:
                    return "getBindDeviceAdminTargetUsers";
                case 299:
                    return "isEphemeralUser";
                case 300:
                    return "getLastSecurityLogRetrievalTime";
                case 301:
                    return "getLastBugReportRequestTime";
                case 302:
                    return "getLastNetworkLogRetrievalTime";
                case 303:
                    return "setResetPasswordToken";
                case 304:
                    return "clearResetPasswordToken";
                case 305:
                    return "isResetPasswordTokenActive";
                case 306:
                    return "resetPasswordWithToken";
                case 307:
                    return "isCurrentInputMethodSetByOwner";
                case 308:
                    return "getOwnerInstalledCaCerts";
                case 309:
                    return "clearApplicationUserData";
                case 310:
                    return "setLogoutEnabled";
                case 311:
                    return "isLogoutEnabled";
                case 312:
                    return "getDisallowedSystemApps";
                case 313:
                    return "transferOwnership";
                case 314:
                    return "getTransferOwnershipBundle";
                case 315:
                    return "setStartUserSessionMessage";
                case 316:
                    return "setEndUserSessionMessage";
                case 317:
                    return "getStartUserSessionMessage";
                case 318:
                    return "getEndUserSessionMessage";
                case 319:
                    return "setMeteredDataDisabledPackages";
                case 320:
                    return "getMeteredDataDisabledPackages";
                case 321:
                    return "addOverrideApn";
                case 322:
                    return "updateOverrideApn";
                case 323:
                    return "removeOverrideApn";
                case 324:
                    return "getOverrideApns";
                case 325:
                    return "setOverrideApnsEnabled";
                case 326:
                    return "isOverrideApnEnabled";
                case 327:
                    return "isMeteredDataDisabledPackageForUser";
                case 328:
                    return "reportFailedPasswordAttemptWithFailureCount";
                case 329:
                    return "setGlobalPrivateDns";
                case 330:
                    return "getGlobalPrivateDnsMode";
                case 331:
                    return "getGlobalPrivateDnsHost";
                case 332:
                    return "setProfileOwnerOnOrganizationOwnedDevice";
                case 333:
                    return "installUpdateFromFile";
                case 334:
                    return "setCrossProfileCalendarPackages";
                case 335:
                    return "getCrossProfileCalendarPackages";
                case 336:
                    return "isPackageAllowedToAccessCalendarForUser";
                case 337:
                    return "getCrossProfileCalendarPackagesForUser";
                case 338:
                    return "setCrossProfilePackages";
                case 339:
                    return "getCrossProfilePackages";
                case 340:
                    return "getAllCrossProfilePackages";
                case 341:
                    return "getDefaultCrossProfilePackages";
                case 342:
                    return "isManagedKiosk";
                case 343:
                    return "isUnattendedManagedKiosk";
                case 344:
                    return "startViewCalendarEventInManagedProfile";
                case 345:
                    return "setKeyGrantForApp";
                case 346:
                    return "getKeyPairGrants";
                case 347:
                    return "setKeyGrantToWifiAuth";
                case 348:
                    return "isKeyPairGrantedToWifiAuth";
                case 349:
                    return "setUserControlDisabledPackages";
                case 350:
                    return "getUserControlDisabledPackages";
                case 351:
                    return "setCommonCriteriaModeEnabled";
                case 352:
                    return "isCommonCriteriaModeEnabled";
                case 353:
                    return "getPersonalAppsSuspendedReasons";
                case 354:
                    return "setPersonalAppsSuspended";
                case 355:
                    return "getManagedProfileMaximumTimeOff";
                case 356:
                    return "setManagedProfileMaximumTimeOff";
                case 357:
                    return "acknowledgeDeviceCompliant";
                case 358:
                    return "isComplianceAcknowledgementRequired";
                case 359:
                    return "canProfileOwnerResetPasswordWhenLocked";
                case 360:
                    return "setNextOperationSafety";
                case 361:
                    return "isSafeOperation";
                case 362:
                    return "getEnrollmentSpecificId";
                case 363:
                    return "setOrganizationIdForUser";
                case 364:
                    return "createAndProvisionManagedProfile";
                case 365:
                    return "createManagedProfile";
                case 366:
                    return "finalizeCreateManagedProfile";
                case 367:
                    return "provisionFullyManagedDevice";
                case 368:
                    return "finalizeWorkProfileProvisioning";
                case 369:
                    return "removeManagedProfile";
                case 370:
                    return "setDeviceOwnerType";
                case 371:
                    return "getDeviceOwnerType";
                case 372:
                    return "resetDefaultCrossProfileIntentFilters";
                case 373:
                    return "canAdminGrantSensorsPermissions";
                case 374:
                    return "setUsbDataSignalingEnabled";
                case 375:
                    return "isUsbDataSignalingEnabled";
                case 376:
                    return "canUsbDataSignalingBeDisabled";
                case 377:
                    return "setMinimumRequiredWifiSecurityLevel";
                case 378:
                    return "getMinimumRequiredWifiSecurityLevel";
                case 379:
                    return "setWifiSsidPolicy";
                case 380:
                    return "getWifiSsidPolicy";
                case 381:
                    return "isDevicePotentiallyStolen";
                case 382:
                    return "listForegroundAffiliatedUsers";
                case 383:
                    return "setDrawables";
                case 384:
                    return "resetDrawables";
                case 385:
                    return "getDrawable";
                case 386:
                    return "isDpcDownloaded";
                case 387:
                    return "setDpcDownloaded";
                case 388:
                    return "setStrings";
                case 389:
                    return "resetStrings";
                case 390:
                    return "getString";
                case 391:
                    return "resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState";
                case 392:
                    return "shouldAllowBypassingDevicePolicyManagementRoleQualification";
                case 393:
                    return "getPolicyManagedProfiles";
                case 394:
                    return "semSetPasswordQuality";
                case 395:
                    return "semSetPasswordMinimumLength";
                case 396:
                    return "semSetPasswordMinimumUpperCase";
                case 397:
                    return "semSetPasswordMinimumLowerCase";
                case 398:
                    return "semSetPasswordMinimumNonLetter";
                case 399:
                    return "semSetPasswordHistoryLength";
                case 400:
                    return "semSetPasswordExpirationTimeout";
                case 401:
                    return "semIsActivePasswordSufficient";
                case 402:
                    return "semSetSimplePasswordEnabled";
                case 403:
                    return "semIsSimplePasswordEnabled";
                case 404:
                    return "semSetKeyguardDisabledFeatures";
                case 405:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_STORAGE_CARD;
                case 406:
                    return "semGetAllowStorageCard";
                case 407:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_WIFI_POLICY;
                case 408:
                    return "semGetAllowWifi";
                case 409:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_TEXT_MESSAGING;
                case 410:
                    return "semGetAllowTextMessaging";
                case 411:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_POP_IMAP_EMAIL;
                case 412:
                    return "semGetAllowPopImapEmail";
                case 413:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_BROWSER;
                case 414:
                    return "semGetAllowBrowser";
                case 415:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_INTERNET_SHARING;
                case 416:
                    return "semGetAllowInternetSharing";
                case 417:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_BLUETOOTH_MODE;
                case 418:
                    return "semGetAllowBluetoothMode";
                case 419:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_DESKTOP_SYNC;
                case 420:
                    return "semGetAllowDesktopSync";
                case 421:
                    return EasPolicyIdentifiers.EAS_POLICY_ALLOW_IRDA;
                case 422:
                    return "semGetAllowIrda";
                case 423:
                    return EasPolicyIdentifiers.EAS_POLICY_SET_REQUIRE_STORAGE_CARD_ENCRYPTION;
                case 424:
                    return "semGetRequireStorageCardEncryption";
                case 425:
                    return EasPolicyIdentifiers.EAS_POLICY_SET_CHANGE_NOTIFICATION_ENABLED;
                case 426:
                    return "setApplicationExemptions";
                case 427:
                    return "getApplicationExemptions";
                case 428:
                    return "setMtePolicy";
                case 429:
                    return "setMtePolicyBySystem";
                case 430:
                    return "getMtePolicy";
                case 431:
                    return "setManagedSubscriptionsPolicy";
                case 432:
                    return "getManagedSubscriptionsPolicy";
                case 433:
                    return "getDevicePolicyState";
                case 434:
                    return "triggerDevicePolicyEngineMigration";
                case 435:
                    return "isDeviceFinanced";
                case 436:
                    return "getFinancedDeviceKioskRoleHolder";
                case 437:
                    return "setCrossProfileAppToIgnored";
                case 438:
                    return "getSamsungSDcardEncryptionStatus";
                case 439:
                    return "calculateHasIncompatibleAccounts";
                case 440:
                    return "setContentProtectionPolicy";
                case 441:
                    return "getContentProtectionPolicy";
                case 442:
                    return "getSubscriptionIds";
                case 443:
                    return "setMaxPolicyStorageLimit";
                case 444:
                    return "forceSetMaxPolicyStorageLimit";
                case 445:
                    return "getMaxPolicyStorageLimit";
                case 446:
                    return "getPolicySizeForAdmin";
                case 447:
                    return "getHeadlessDeviceOwnerMode";
                case 448:
                    return "setAppFunctionsPolicy";
                case 449:
                    return "getAppFunctionsPolicy";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    return onTransact$setPasswordQuality$(parcel, parcel2);
                case 2:
                    return onTransact$getPasswordQuality$(parcel, parcel2);
                case 3:
                    return onTransact$setPasswordMinimumLength$(parcel, parcel2);
                case 4:
                    return onTransact$getPasswordMinimumLength$(parcel, parcel2);
                case 5:
                    return onTransact$setPasswordMinimumUpperCase$(parcel, parcel2);
                case 6:
                    return onTransact$getPasswordMinimumUpperCase$(parcel, parcel2);
                case 7:
                    return onTransact$setPasswordMinimumLowerCase$(parcel, parcel2);
                case 8:
                    return onTransact$getPasswordMinimumLowerCase$(parcel, parcel2);
                case 9:
                    return onTransact$setPasswordMinimumLetters$(parcel, parcel2);
                case 10:
                    return onTransact$getPasswordMinimumLetters$(parcel, parcel2);
                case 11:
                    return onTransact$setPasswordMinimumNumeric$(parcel, parcel2);
                case 12:
                    return onTransact$getPasswordMinimumNumeric$(parcel, parcel2);
                case 13:
                    return onTransact$setPasswordMinimumSymbols$(parcel, parcel2);
                case 14:
                    return onTransact$getPasswordMinimumSymbols$(parcel, parcel2);
                case 15:
                    return onTransact$setPasswordMinimumNonLetter$(parcel, parcel2);
                case 16:
                    return onTransact$getPasswordMinimumNonLetter$(parcel, parcel2);
                case 17:
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    PasswordMetrics passwordMinimumMetrics = getPasswordMinimumMetrics(readInt, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(passwordMinimumMetrics, 1);
                    return true;
                case 18:
                    return onTransact$setPasswordHistoryLength$(parcel, parcel2);
                case 19:
                    return onTransact$getPasswordHistoryLength$(parcel, parcel2);
                case 20:
                    return onTransact$setPasswordExpirationTimeout$(parcel, parcel2);
                case 21:
                    return onTransact$getPasswordExpirationTimeout$(parcel, parcel2);
                case 22:
                    return onTransact$getPasswordExpiration$(parcel, parcel2);
                case 23:
                    return onTransact$isActivePasswordSufficient$(parcel, parcel2);
                case 24:
                    boolean isActivePasswordSufficientForDeviceRequirement = isActivePasswordSufficientForDeviceRequirement();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivePasswordSufficientForDeviceRequirement);
                    return true;
                case 25:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPasswordSufficientAfterProfileUnification = isPasswordSufficientAfterProfileUnification(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPasswordSufficientAfterProfileUnification);
                    return true;
                case 26:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int passwordComplexity = getPasswordComplexity(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordComplexity);
                    return true;
                case 27:
                    return onTransact$setRequiredPasswordComplexity$(parcel, parcel2);
                case 28:
                    String readString = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int requiredPasswordComplexity = getRequiredPasswordComplexity(readString, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(requiredPasswordComplexity);
                    return true;
                case 29:
                    int readInt4 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int aggregatedPasswordComplexityForUser = getAggregatedPasswordComplexityForUser(readInt4, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeInt(aggregatedPasswordComplexityForUser);
                    return true;
                case 30:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUsingUnifiedPassword = isUsingUnifiedPassword(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsingUnifiedPassword);
                    return true;
                case 31:
                    return onTransact$getCurrentFailedPasswordAttempts$(parcel, parcel2);
                case 32:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentFailedBiometricAttempts = getCurrentFailedBiometricAttempts(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentFailedBiometricAttempts);
                    return true;
                case 33:
                    int readInt6 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int profileWithMinimumFailedPasswordsForWipe = getProfileWithMinimumFailedPasswordsForWipe(readInt6, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileWithMinimumFailedPasswordsForWipe);
                    return true;
                case 34:
                    return onTransact$setMaximumFailedPasswordsForWipe$(parcel, parcel2);
                case 35:
                    return onTransact$getMaximumFailedPasswordsForWipe$(parcel, parcel2);
                case 36:
                    String readString2 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetPassword = resetPassword(readString2, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPassword);
                    return true;
                case 37:
                    return onTransact$setMaximumTimeToLock$(parcel, parcel2);
                case 38:
                    return onTransact$getMaximumTimeToLock$(parcel, parcel2);
                case 39:
                    return onTransact$setRequiredStrongAuthTimeout$(parcel, parcel2);
                case 40:
                    return onTransact$getRequiredStrongAuthTimeout$(parcel, parcel2);
                case 41:
                    return onTransact$lockNow$(parcel, parcel2);
                case 42:
                    return onTransact$wipeDataWithReason$(parcel, parcel2);
                case 43:
                    return onTransact$setFactoryResetProtectionPolicy$(parcel, parcel2);
                case 44:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    FactoryResetProtectionPolicy factoryResetProtectionPolicy = getFactoryResetProtectionPolicy(componentName2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(factoryResetProtectionPolicy, 1);
                    return true;
                case 45:
                    boolean isFactoryResetProtectionPolicySupported = isFactoryResetProtectionPolicySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFactoryResetProtectionPolicySupported);
                    return true;
                case 46:
                    AndroidFuture<Boolean> androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendLostModeLocationUpdate(androidFuture);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    return onTransact$setGlobalProxy$(parcel, parcel2);
                case 48:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName globalProxyAdmin = getGlobalProxyAdmin(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(globalProxyAdmin, 1);
                    return true;
                case 49:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ProxyInfo proxyInfo = (ProxyInfo) parcel.readTypedObject(ProxyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRecommendedGlobalProxy(componentName3, proxyInfo);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int storageEncryption = setStorageEncryption(componentName4, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageEncryption);
                    return true;
                case 51:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean storageEncryption2 = getStorageEncryption(componentName5, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(storageEncryption2);
                    return true;
                case 52:
                    String readString3 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int storageEncryptionStatus = getStorageEncryptionStatus(readString3, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageEncryptionStatus);
                    return true;
                case 53:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestBugreport = requestBugreport(componentName6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestBugreport);
                    return true;
                case 54:
                    return onTransact$setCameraDisabled$(parcel, parcel2);
                case 55:
                    return onTransact$getCameraDisabled$(parcel, parcel2);
                case 56:
                    return onTransact$setScreenCaptureDisabled$(parcel, parcel2);
                case 57:
                    return onTransact$getScreenCaptureDisabled$(parcel, parcel2);
                case 58:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNearbyNotificationStreamingPolicy(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nearbyNotificationStreamingPolicy = getNearbyNotificationStreamingPolicy(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(nearbyNotificationStreamingPolicy);
                    return true;
                case 60:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNearbyAppStreamingPolicy(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nearbyAppStreamingPolicy = getNearbyAppStreamingPolicy(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeInt(nearbyAppStreamingPolicy);
                    return true;
                case 62:
                    return onTransact$setKeyguardDisabledFeatures$(parcel, parcel2);
                case 63:
                    return onTransact$getKeyguardDisabledFeatures$(parcel, parcel2);
                case 64:
                    return onTransact$setActiveAdmin$(parcel, parcel2);
                case 65:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAdminActive = isAdminActive(componentName7, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminActive);
                    return true;
                case 66:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> activeAdmins = getActiveAdmins(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeAdmins, 1);
                    return true;
                case 67:
                    String readString4 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean packageHasActiveAdmins = packageHasActiveAdmins(readString4, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageHasActiveAdmins);
                    return true;
                case 68:
                    return onTransact$getRemoveWarning$(parcel, parcel2);
                case 69:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeActiveAdmin(componentName8, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRemoveActiveAdmin(componentName9, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    return onTransact$hasGrantedPolicy$(parcel, parcel2);
                case 72:
                    PasswordMetrics passwordMetrics = (PasswordMetrics) parcel.readTypedObject(PasswordMetrics.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPasswordChanged(passwordMetrics, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int readInt21 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportFailedPasswordAttempt(readInt21, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulPasswordAttempt(readInt22);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportFailedBiometricAttempt(readInt23);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulBiometricAttempt(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportKeyguardDismissed(readInt25);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportKeyguardSecured(readInt26);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    return onTransact$setDeviceOwner$(parcel, parcel2);
                case 80:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ComponentName deviceOwnerComponent = getDeviceOwnerComponent(readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceOwnerComponent, 1);
                    return true;
                case 81:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName deviceOwnerComponentOnUser = getDeviceOwnerComponentOnUser(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceOwnerComponentOnUser, 1);
                    return true;
                case 82:
                    boolean hasDeviceOwner = hasDeviceOwner();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDeviceOwner);
                    return true;
                case 83:
                    String deviceOwnerName = getDeviceOwnerName();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceOwnerName);
                    return true;
                case 84:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearDeviceOwner(readString5);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    int deviceOwnerUserId = getDeviceOwnerUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceOwnerUserId);
                    return true;
                case 86:
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean profileOwner = setProfileOwner(componentName10, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileOwner);
                    return true;
                case 87:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName profileOwnerAsUser = getProfileOwnerAsUser(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileOwnerAsUser, 1);
                    return true;
                case 88:
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileOwnerOrDeviceOwnerSupervisionComponent, 1);
                    return true;
                case 89:
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSupervisionComponent = isSupervisionComponent(componentName11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupervisionComponent);
                    return true;
                case 90:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileOwnerName = getProfileOwnerName(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeString(profileOwnerName);
                    return true;
                case 91:
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setProfileEnabled(componentName12);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProfileName(componentName13, readString6);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    clearProfileOwner(componentName14);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    boolean hasUserSetupCompleted = hasUserSetupCompleted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserSetupCompleted);
                    return true;
                case 95:
                    boolean isOrganizationOwnedDeviceWithManagedProfile = isOrganizationOwnedDeviceWithManagedProfile();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOrganizationOwnedDeviceWithManagedProfile);
                    return true;
                case 96:
                    return onTransact$checkDeviceIdentifierAccess$(parcel, parcel2);
                case 97:
                    ComponentName componentName15 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceOwnerLockScreenInfo(componentName15, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    CharSequence deviceOwnerLockScreenInfo = getDeviceOwnerLockScreenInfo();
                    parcel2.writeNoException();
                    if (deviceOwnerLockScreenInfo != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(deviceOwnerLockScreenInfo, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 99:
                    return onTransact$setPackagesSuspended$(parcel, parcel2);
                case 100:
                    return onTransact$isPackageSuspended$(parcel, parcel2);
                case 101:
                    List<String> listPolicyExemptApps = listPolicyExemptApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listPolicyExemptApps);
                    return true;
                case 102:
                    return onTransact$installCaCert$(parcel, parcel2);
                case 103:
                    return onTransact$uninstallCaCerts$(parcel, parcel2);
                case 104:
                    ComponentName componentName16 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceCanManageCaCerts(componentName16, readString7);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    return onTransact$approveCaCert$(parcel, parcel2);
                case 106:
                    String readString8 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCaCertApproved = isCaCertApproved(readString8, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaCertApproved);
                    return true;
                case 107:
                    return onTransact$installKeyPair$(parcel, parcel2);
                case 108:
                    return onTransact$removeKeyPair$(parcel, parcel2);
                case 109:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasKeyPair = hasKeyPair(readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasKeyPair);
                    return true;
                case 110:
                    return onTransact$generateKeyPair$(parcel, parcel2);
                case 111:
                    return onTransact$setKeyPairCertificate$(parcel, parcel2);
                case 112:
                    return onTransact$choosePrivateKeyAlias$(parcel, parcel2);
                case 113:
                    return onTransact$setDelegatedScopes$(parcel, parcel2);
                case 114:
                    ComponentName componentName17 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> delegatedScopes = getDelegatedScopes(componentName17, readString11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatedScopes);
                    return true;
                case 115:
                    ComponentName componentName18 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> delegatePackages = getDelegatePackages(componentName18, readString12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatePackages);
                    return true;
                case 116:
                    ComponentName componentName19 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCertInstallerPackage(componentName19, readString13);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    ComponentName componentName20 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    String certInstallerPackage = getCertInstallerPackage(componentName20);
                    parcel2.writeNoException();
                    parcel2.writeString(certInstallerPackage);
                    return true;
                case 118:
                    return onTransact$setAlwaysOnVpnPackage$(parcel, parcel2);
                case 119:
                    ComponentName componentName21 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    String alwaysOnVpnPackage = getAlwaysOnVpnPackage(componentName21);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackage);
                    return true;
                case 120:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String alwaysOnVpnPackageForUser = getAlwaysOnVpnPackageForUser(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackageForUser);
                    return true;
                case 121:
                    ComponentName componentName22 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAlwaysOnVpnLockdownEnabled = isAlwaysOnVpnLockdownEnabled(componentName22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlwaysOnVpnLockdownEnabled);
                    return true;
                case 122:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAlwaysOnVpnLockdownEnabledForUser = isAlwaysOnVpnLockdownEnabledForUser(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlwaysOnVpnLockdownEnabledForUser);
                    return true;
                case 123:
                    ComponentName componentName23 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> alwaysOnVpnLockdownAllowlist = getAlwaysOnVpnLockdownAllowlist(componentName23);
                    parcel2.writeNoException();
                    parcel2.writeStringList(alwaysOnVpnLockdownAllowlist);
                    return true;
                case 124:
                    return onTransact$addPersistentPreferredActivity$(parcel, parcel2);
                case 125:
                    return onTransact$clearPackagePersistentPreferredActivities$(parcel, parcel2);
                case 126:
                    return onTransact$setDefaultSmsApplication$(parcel, parcel2);
                case 127:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDefaultDialerApplication(readString14);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    return onTransact$setApplicationRestrictions$(parcel, parcel2);
                case 129:
                    return onTransact$getApplicationRestrictions$(parcel, parcel2);
                case 130:
                    ComponentName componentName24 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationRestrictionsManagingPackage = setApplicationRestrictionsManagingPackage(componentName24, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationRestrictionsManagingPackage);
                    return true;
                case 131:
                    ComponentName componentName25 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    String applicationRestrictionsManagingPackage2 = getApplicationRestrictionsManagingPackage(componentName25);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationRestrictionsManagingPackage2);
                    return true;
                case 132:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isCallerApplicationRestrictionsManagingPackage = isCallerApplicationRestrictionsManagingPackage(readString16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerApplicationRestrictionsManagingPackage);
                    return true;
                case 133:
                    ComponentName componentName26 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ComponentName componentName27 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRestrictionsProvider(componentName26, componentName27);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName restrictionsProvider = getRestrictionsProvider(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(restrictionsProvider, 1);
                    return true;
                case 135:
                    return onTransact$setUserRestriction$(parcel, parcel2);
                case 136:
                    return onTransact$setUserRestrictionForUser$(parcel, parcel2);
                case 137:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserRestrictionGlobally(readString17, readString18);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    return onTransact$setUserRestrictionGloballyFromSystem$(parcel, parcel2);
                case 139:
                    return onTransact$getUserRestrictions$(parcel, parcel2);
                case 140:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle userRestrictionsGlobally = getUserRestrictionsGlobally(readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userRestrictionsGlobally, 1);
                    return true;
                case 141:
                    return onTransact$addCrossProfileIntentFilter$(parcel, parcel2);
                case 142:
                    ComponentName componentName28 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(componentName28, readString20);
                    parcel2.writeNoException();
                    return true;
                case 143:
                    ComponentName componentName29 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean permittedAccessibilityServices = setPermittedAccessibilityServices(componentName29, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(permittedAccessibilityServices);
                    return true;
                case 144:
                    ComponentName componentName30 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> permittedAccessibilityServices2 = getPermittedAccessibilityServices(componentName30);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedAccessibilityServices2);
                    return true;
                case 145:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> permittedAccessibilityServicesForUser = getPermittedAccessibilityServicesForUser(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedAccessibilityServicesForUser);
                    return true;
                case 146:
                    return onTransact$isAccessibilityServicePermittedByAdmin$(parcel, parcel2);
                case 147:
                    return onTransact$setPermittedInputMethods$(parcel, parcel2);
                case 148:
                    return onTransact$getPermittedInputMethods$(parcel, parcel2);
                case 149:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> permittedInputMethodsAsUser = getPermittedInputMethodsAsUser(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedInputMethodsAsUser);
                    return true;
                case 150:
                    return onTransact$isInputMethodPermittedByAdmin$(parcel, parcel2);
                case 151:
                    ComponentName componentName31 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean permittedCrossProfileNotificationListeners = setPermittedCrossProfileNotificationListeners(componentName31, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(permittedCrossProfileNotificationListeners);
                    return true;
                case 152:
                    ComponentName componentName32 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> permittedCrossProfileNotificationListeners2 = getPermittedCrossProfileNotificationListeners(componentName32);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedCrossProfileNotificationListeners2);
                    return true;
                case 153:
                    String readString21 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationListenerServicePermitted = isNotificationListenerServicePermitted(readString21, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationListenerServicePermitted);
                    return true;
                case 154:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent createAdminSupportIntent = createAdminSupportIntent(readString22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAdminSupportIntent, 1);
                    return true;
                case 155:
                    int readInt38 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle enforcingAdminAndUserDetails = getEnforcingAdminAndUserDetails(readInt38, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcingAdminAndUserDetails, 1);
                    return true;
                case 156:
                    int readInt39 = parcel.readInt();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnforcingAdmin enforcingAdmin = getEnforcingAdmin(readInt39, readString24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcingAdmin, 1);
                    return true;
                case 157:
                    int readInt40 = parcel.readInt();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<EnforcingAdmin> enforcingAdminsForRestriction = getEnforcingAdminsForRestriction(readInt40, readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enforcingAdminsForRestriction, 1);
                    return true;
                case 158:
                    return onTransact$setApplicationHidden$(parcel, parcel2);
                case 159:
                    return onTransact$isApplicationHidden$(parcel, parcel2);
                case 160:
                    return onTransact$createAndManageUser$(parcel, parcel2);
                case 161:
                    ComponentName componentName33 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeUser = removeUser(componentName33, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUser);
                    return true;
                case 162:
                    ComponentName componentName34 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean switchUser = switchUser(componentName34, userHandle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchUser);
                    return true;
                case 163:
                    ComponentName componentName35 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startUserInBackground = startUserInBackground(componentName35, userHandle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(startUserInBackground);
                    return true;
                case 164:
                    ComponentName componentName36 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int stopUser = stopUser(componentName36, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUser);
                    return true;
                case 165:
                    ComponentName componentName37 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int logoutUser = logoutUser(componentName37);
                    parcel2.writeNoException();
                    parcel2.writeInt(logoutUser);
                    return true;
                case 166:
                    int logoutUserInternal = logoutUserInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(logoutUserInternal);
                    return true;
                case 167:
                    int logoutUserId = getLogoutUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(logoutUserId);
                    return true;
                case 168:
                    ComponentName componentName38 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<UserHandle> secondaryUsers = getSecondaryUsers(componentName38);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(secondaryUsers, 1);
                    return true;
                case 169:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeNewUserDisclaimer(readInt41);
                    parcel2.writeNoException();
                    return true;
                case 170:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNewUserDisclaimerAcknowledged = isNewUserDisclaimerAcknowledged(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNewUserDisclaimerAcknowledged);
                    return true;
                case 171:
                    return onTransact$enableSystemApp$(parcel, parcel2);
                case 172:
                    return onTransact$enableSystemAppWithIntent$(parcel, parcel2);
                case 173:
                    return onTransact$installExistingPackage$(parcel, parcel2);
                case 174:
                    return onTransact$setAccountManagementDisabled$(parcel, parcel2);
                case 175:
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] accountTypesWithManagementDisabled = getAccountTypesWithManagementDisabled(readString26);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(accountTypesWithManagementDisabled);
                    return true;
                case 176:
                    return onTransact$getAccountTypesWithManagementDisabledAsUser$(parcel, parcel2);
                case 177:
                    return onTransact$setSecondaryLockscreenEnabled$(parcel, parcel2);
                case 178:
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSecondaryLockscreenEnabled = isSecondaryLockscreenEnabled(userHandle6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSecondaryLockscreenEnabled);
                    return true;
                case 179:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(PreferentialNetworkServiceConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferentialNetworkServiceConfigs(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    List<PreferentialNetworkServiceConfig> preferentialNetworkServiceConfigs = getPreferentialNetworkServiceConfigs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(preferentialNetworkServiceConfigs, 1);
                    return true;
                case 181:
                    return onTransact$setLockTaskPackages$(parcel, parcel2);
                case 182:
                    ComponentName componentName39 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] lockTaskPackages = getLockTaskPackages(componentName39, readString27);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(lockTaskPackages);
                    return true;
                case 183:
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isLockTaskPermitted = isLockTaskPermitted(readString28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLockTaskPermitted);
                    return true;
                case 184:
                    return onTransact$setLockTaskFeatures$(parcel, parcel2);
                case 185:
                    ComponentName componentName40 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int lockTaskFeatures = getLockTaskFeatures(componentName40, readString29);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskFeatures);
                    return true;
                case 186:
                    return onTransact$setGlobalSetting$(parcel, parcel2);
                case 187:
                    return onTransact$setSystemSetting$(parcel, parcel2);
                case 188:
                    return onTransact$setSecureSetting$(parcel, parcel2);
                case 189:
                    return onTransact$setConfiguredNetworksLockdownState$(parcel, parcel2);
                case 190:
                    ComponentName componentName41 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasLockdownAdminConfiguredNetworks = hasLockdownAdminConfiguredNetworks(componentName41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasLockdownAdminConfiguredNetworks);
                    return true;
                case 191:
                    ComponentName componentName42 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLocationEnabled(componentName42, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 192:
                    return onTransact$setTime$(parcel, parcel2);
                case 193:
                    return onTransact$setTimeZone$(parcel, parcel2);
                case 194:
                    ComponentName componentName43 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterVolumeMuted(componentName43, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 195:
                    ComponentName componentName44 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMasterVolumeMuted = isMasterVolumeMuted(componentName44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMasterVolumeMuted);
                    return true;
                case 196:
                    return onTransact$notifyLockTaskModeChanged$(parcel, parcel2);
                case 197:
                    return onTransact$setUninstallBlocked$(parcel, parcel2);
                case 198:
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUninstallBlocked = isUninstallBlocked(readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUninstallBlocked);
                    return true;
                case 199:
                    ComponentName componentName45 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossProfileCallerIdDisabled(componentName45, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 200:
                    ComponentName componentName46 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean crossProfileCallerIdDisabled = getCrossProfileCallerIdDisabled(componentName46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileCallerIdDisabled);
                    return true;
                case 201:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean crossProfileCallerIdDisabledForUser = getCrossProfileCallerIdDisabledForUser(readInt43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileCallerIdDisabledForUser);
                    return true;
                case 202:
                    ComponentName componentName47 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossProfileContactsSearchDisabled(componentName47, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 203:
                    ComponentName componentName48 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean crossProfileContactsSearchDisabled = getCrossProfileContactsSearchDisabled(componentName48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileContactsSearchDisabled);
                    return true;
                case 204:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean crossProfileContactsSearchDisabledForUser = getCrossProfileContactsSearchDisabledForUser(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileContactsSearchDisabledForUser);
                    return true;
                case 205:
                    return onTransact$startManagedQuickContact$(parcel, parcel2);
                case 206:
                    PackagePolicy packagePolicy = (PackagePolicy) parcel.readTypedObject(PackagePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManagedProfileCallerIdAccessPolicy(packagePolicy);
                    parcel2.writeNoException();
                    return true;
                case 207:
                    PackagePolicy managedProfileCallerIdAccessPolicy = getManagedProfileCallerIdAccessPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(managedProfileCallerIdAccessPolicy, 1);
                    return true;
                case 208:
                    int readInt45 = parcel.readInt();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasManagedProfileCallerIdAccess = hasManagedProfileCallerIdAccess(readInt45, readString31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasManagedProfileCallerIdAccess);
                    return true;
                case 209:
                    PackagePolicy packagePolicy2 = (PackagePolicy) parcel.readTypedObject(PackagePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCredentialManagerPolicy(packagePolicy2);
                    parcel2.writeNoException();
                    return true;
                case 210:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackagePolicy credentialManagerPolicy = getCredentialManagerPolicy(readInt46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialManagerPolicy, 1);
                    return true;
                case 211:
                    PackagePolicy packagePolicy3 = (PackagePolicy) parcel.readTypedObject(PackagePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManagedProfileContactsAccessPolicy(packagePolicy3);
                    parcel2.writeNoException();
                    return true;
                case 212:
                    PackagePolicy managedProfileContactsAccessPolicy = getManagedProfileContactsAccessPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(managedProfileContactsAccessPolicy, 1);
                    return true;
                case 213:
                    int readInt47 = parcel.readInt();
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasManagedProfileContactsAccess = hasManagedProfileContactsAccess(readInt47, readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasManagedProfileContactsAccess);
                    return true;
                case 214:
                    ComponentName componentName49 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothContactSharingDisabled(componentName49, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    ComponentName componentName50 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean bluetoothContactSharingDisabled = getBluetoothContactSharingDisabled(componentName50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothContactSharingDisabled);
                    return true;
                case 216:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothContactSharingDisabledForUser = getBluetoothContactSharingDisabledForUser(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothContactSharingDisabledForUser);
                    return true;
                case 217:
                    return onTransact$setTrustAgentConfiguration$(parcel, parcel2);
                case 218:
                    return onTransact$getTrustAgentConfiguration$(parcel, parcel2);
                case 219:
                    return onTransact$addCrossProfileWidgetProvider$(parcel, parcel2);
                case 220:
                    return onTransact$removeCrossProfileWidgetProvider$(parcel, parcel2);
                case 221:
                    ComponentName componentName51 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> crossProfileWidgetProviders = getCrossProfileWidgetProviders(componentName51, readString33);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileWidgetProviders);
                    return true;
                case 222:
                    ComponentName componentName52 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoTimeRequired(componentName52, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 223:
                    boolean autoTimeRequired = getAutoTimeRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeRequired);
                    return true;
                case 224:
                    return onTransact$setAutoTimeEnabled$(parcel, parcel2);
                case 225:
                    ComponentName componentName53 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean autoTimeEnabled = getAutoTimeEnabled(componentName53, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeEnabled);
                    return true;
                case 226:
                    String readString35 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoTimePolicy(readString35, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 227:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoTimePolicy = getAutoTimePolicy(readString36);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoTimePolicy);
                    return true;
                case 228:
                    return onTransact$setAutoTimeZoneEnabled$(parcel, parcel2);
                case 229:
                    ComponentName componentName54 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean autoTimeZoneEnabled = getAutoTimeZoneEnabled(componentName54, readString37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeZoneEnabled);
                    return true;
                case 230:
                    String readString38 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoTimeZonePolicy(readString38, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoTimeZonePolicy = getAutoTimeZonePolicy(readString39);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoTimeZonePolicy);
                    return true;
                case 232:
                    ComponentName componentName55 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setForceEphemeralUsers(componentName55, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 233:
                    ComponentName componentName56 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean forceEphemeralUsers = getForceEphemeralUsers(componentName56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceEphemeralUsers);
                    return true;
                case 234:
                    ComponentName componentName57 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRemovingAdmin = isRemovingAdmin(componentName57, readInt51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRemovingAdmin);
                    return true;
                case 235:
                    ComponentName componentName58 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIcon(componentName58, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 236:
                    return onTransact$setSystemUpdatePolicy$(parcel, parcel2);
                case 237:
                    SystemUpdatePolicy systemUpdatePolicy = getSystemUpdatePolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemUpdatePolicy, 1);
                    return true;
                case 238:
                    clearSystemUpdatePolicyFreezePeriodRecord();
                    parcel2.writeNoException();
                    return true;
                case 239:
                    ComponentName componentName59 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean keyguardDisabled = setKeyguardDisabled(componentName59, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyguardDisabled);
                    return true;
                case 240:
                    return onTransact$setStatusBarDisabled$(parcel, parcel2);
                case 241:
                    String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isStatusBarDisabled = isStatusBarDisabled(readString40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStatusBarDisabled);
                    return true;
                case 242:
                    boolean doNotAskCredentialsOnBoot = getDoNotAskCredentialsOnBoot();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(doNotAskCredentialsOnBoot);
                    return true;
                case 243:
                    SystemUpdateInfo systemUpdateInfo = (SystemUpdateInfo) parcel.readTypedObject(SystemUpdateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPendingSystemUpdate(systemUpdateInfo);
                    parcel2.writeNoException();
                    return true;
                case 244:
                    ComponentName componentName60 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SystemUpdateInfo pendingSystemUpdate = getPendingSystemUpdate(componentName60, readString41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingSystemUpdate, 1);
                    return true;
                case 245:
                    return onTransact$setPermissionPolicy$(parcel, parcel2);
                case 246:
                    ComponentName componentName61 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int permissionPolicy = getPermissionPolicy(componentName61);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionPolicy);
                    return true;
                case 247:
                    return onTransact$setPermissionGrantState$(parcel, parcel2);
                case 248:
                    return onTransact$getPermissionGrantState$(parcel, parcel2);
                case 249:
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProvisioningAllowed = isProvisioningAllowed(readString42, readString43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProvisioningAllowed);
                    return true;
                case 250:
                    String readString44 = parcel.readString();
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkProvisioningPrecondition = checkProvisioningPrecondition(readString44, readString45);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkProvisioningPrecondition);
                    return true;
                case 251:
                    return onTransact$setKeepUninstalledPackages$(parcel, parcel2);
                case 252:
                    ComponentName componentName62 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> keepUninstalledPackages = getKeepUninstalledPackages(componentName62, readString46);
                    parcel2.writeNoException();
                    parcel2.writeStringList(keepUninstalledPackages);
                    return true;
                case 253:
                    ComponentName componentName63 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isManagedProfile = isManagedProfile(componentName63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isManagedProfile);
                    return true;
                case 254:
                    ComponentName componentName64 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiMacAddress = getWifiMacAddress(componentName64, readString47);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiMacAddress);
                    return true;
                case 255:
                    ComponentName componentName65 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    reboot(componentName65);
                    parcel2.writeNoException();
                    return true;
                case 256:
                    return onTransact$setShortSupportMessage$(parcel, parcel2);
                case 257:
                    ComponentName componentName66 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence shortSupportMessage = getShortSupportMessage(componentName66, readString48);
                    parcel2.writeNoException();
                    if (shortSupportMessage != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(shortSupportMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 258:
                    ComponentName componentName67 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setLongSupportMessage(componentName67, charSequence2);
                    parcel2.writeNoException();
                    return true;
                case 259:
                    ComponentName componentName68 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    CharSequence longSupportMessage = getLongSupportMessage(componentName68);
                    parcel2.writeNoException();
                    if (longSupportMessage != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(longSupportMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 260:
                    ComponentName componentName69 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence shortSupportMessageForUser = getShortSupportMessageForUser(componentName69, readInt52);
                    parcel2.writeNoException();
                    if (shortSupportMessageForUser != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(shortSupportMessageForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 261:
                    ComponentName componentName70 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence longSupportMessageForUser = getLongSupportMessageForUser(componentName70, readInt53);
                    parcel2.writeNoException();
                    if (longSupportMessageForUser != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(longSupportMessageForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 262:
                    ComponentName componentName71 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrganizationColor(componentName71, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 263:
                    int readInt55 = parcel.readInt();
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrganizationColorForUser(readInt55, readInt56);
                    parcel2.writeNoException();
                    return true;
                case 264:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearOrganizationIdForUser(readInt57);
                    parcel2.writeNoException();
                    return true;
                case 265:
                    ComponentName componentName72 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int organizationColor = getOrganizationColor(componentName72);
                    parcel2.writeNoException();
                    parcel2.writeInt(organizationColor);
                    return true;
                case 266:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int organizationColorForUser = getOrganizationColorForUser(readInt58);
                    parcel2.writeNoException();
                    parcel2.writeInt(organizationColorForUser);
                    return true;
                case 267:
                    return onTransact$setOrganizationName$(parcel, parcel2);
                case 268:
                    ComponentName componentName73 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence organizationName = getOrganizationName(componentName73, readString49);
                    parcel2.writeNoException();
                    if (organizationName != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(organizationName, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 269:
                    CharSequence deviceOwnerOrganizationName = getDeviceOwnerOrganizationName();
                    parcel2.writeNoException();
                    if (deviceOwnerOrganizationName != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(deviceOwnerOrganizationName, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 270:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence organizationNameForUser = getOrganizationNameForUser(readInt59);
                    parcel2.writeNoException();
                    if (organizationNameForUser != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(organizationNameForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 271:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userProvisioningState = getUserProvisioningState(readInt60);
                    parcel2.writeNoException();
                    parcel2.writeInt(userProvisioningState);
                    return true;
                case 272:
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserProvisioningState(readInt61, readInt62);
                    parcel2.writeNoException();
                    return true;
                case 273:
                    ComponentName componentName74 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setAffiliationIds(componentName74, createStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 274:
                    ComponentName componentName75 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> affiliationIds = getAffiliationIds(componentName75);
                    parcel2.writeNoException();
                    parcel2.writeStringList(affiliationIds);
                    return true;
                case 275:
                    boolean isCallingUserAffiliated = isCallingUserAffiliated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallingUserAffiliated);
                    return true;
                case 276:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAffiliatedUser = isAffiliatedUser(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAffiliatedUser);
                    return true;
                case 277:
                    return onTransact$setSecurityLoggingEnabled$(parcel, parcel2);
                case 278:
                    ComponentName componentName76 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSecurityLoggingEnabled = isSecurityLoggingEnabled(componentName76, readString50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSecurityLoggingEnabled);
                    return true;
                case 279:
                    ComponentName componentName77 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice retrieveSecurityLogs = retrieveSecurityLogs(componentName77, readString51);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(retrieveSecurityLogs, 1);
                    return true;
                case 280:
                    ComponentName componentName78 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice retrievePreRebootSecurityLogs = retrievePreRebootSecurityLogs(componentName78, readString52);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(retrievePreRebootSecurityLogs, 1);
                    return true;
                case 281:
                    long forceNetworkLogs = forceNetworkLogs();
                    parcel2.writeNoException();
                    parcel2.writeLong(forceNetworkLogs);
                    return true;
                case 282:
                    long forceSecurityLogs = forceSecurityLogs();
                    parcel2.writeNoException();
                    parcel2.writeLong(forceSecurityLogs);
                    return true;
                case 283:
                    String readString53 = parcel.readString();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAuditLogEnabled(readString53, readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 284:
                    String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAuditLogEnabled = isAuditLogEnabled(readString54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAuditLogEnabled);
                    return true;
                case 285:
                    String readString55 = parcel.readString();
                    IAuditLogEventsCallback asInterface = IAuditLogEventsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setAuditLogEventsCallback(readString55, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 286:
                    String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUninstallInQueue = isUninstallInQueue(readString56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUninstallInQueue);
                    return true;
                case 287:
                    String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    uninstallPackageWithActiveAdmins(readString57);
                    parcel2.writeNoException();
                    return true;
                case 288:
                    boolean isDeviceProvisioned = isDeviceProvisioned();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceProvisioned);
                    return true;
                case 289:
                    boolean isDeviceProvisioningConfigApplied = isDeviceProvisioningConfigApplied();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceProvisioningConfigApplied);
                    return true;
                case 290:
                    setDeviceProvisioningConfigApplied();
                    parcel2.writeNoException();
                    return true;
                case 291:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceUpdateUserSetupComplete(readInt64);
                    parcel2.writeNoException();
                    return true;
                case 292:
                    ComponentName componentName79 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupServiceEnabled(componentName79, readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 293:
                    ComponentName componentName80 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBackupServiceEnabled = isBackupServiceEnabled(componentName80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupServiceEnabled);
                    return true;
                case 294:
                    return onTransact$setNetworkLoggingEnabled$(parcel, parcel2);
                case 295:
                    ComponentName componentName81 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNetworkLoggingEnabled = isNetworkLoggingEnabled(componentName81, readString58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNetworkLoggingEnabled);
                    return true;
                case 296:
                    return onTransact$retrieveNetworkLogs$(parcel, parcel2);
                case 297:
                    return onTransact$bindDeviceAdminServiceAsUser$(parcel, parcel2);
                case 298:
                    ComponentName componentName82 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<UserHandle> bindDeviceAdminTargetUsers = getBindDeviceAdminTargetUsers(componentName82);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(bindDeviceAdminTargetUsers, 1);
                    return true;
                case 299:
                    ComponentName componentName83 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isEphemeralUser = isEphemeralUser(componentName83);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEphemeralUser);
                    return true;
                case 300:
                    long lastSecurityLogRetrievalTime = getLastSecurityLogRetrievalTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastSecurityLogRetrievalTime);
                    return true;
                case 301:
                    long lastBugReportRequestTime = getLastBugReportRequestTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastBugReportRequestTime);
                    return true;
                case 302:
                    long lastNetworkLogRetrievalTime = getLastNetworkLogRetrievalTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastNetworkLogRetrievalTime);
                    return true;
                case 303:
                    return onTransact$setResetPasswordToken$(parcel, parcel2);
                case 304:
                    ComponentName componentName84 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearResetPasswordToken = clearResetPasswordToken(componentName84, readString59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearResetPasswordToken);
                    return true;
                case 305:
                    ComponentName componentName85 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isResetPasswordTokenActive = isResetPasswordTokenActive(componentName85, readString60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isResetPasswordTokenActive);
                    return true;
                case 306:
                    return onTransact$resetPasswordWithToken$(parcel, parcel2);
                case 307:
                    boolean isCurrentInputMethodSetByOwner = isCurrentInputMethodSetByOwner();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCurrentInputMethodSetByOwner);
                    return true;
                case 308:
                    UserHandle userHandle7 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    StringParceledListSlice ownerInstalledCaCerts = getOwnerInstalledCaCerts(userHandle7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownerInstalledCaCerts, 1);
                    return true;
                case 309:
                    return onTransact$clearApplicationUserData$(parcel, parcel2);
                case 310:
                    ComponentName componentName86 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLogoutEnabled(componentName86, readBoolean19);
                    parcel2.writeNoException();
                    return true;
                case 311:
                    boolean isLogoutEnabled = isLogoutEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLogoutEnabled);
                    return true;
                case 312:
                    return onTransact$getDisallowedSystemApps$(parcel, parcel2);
                case 313:
                    return onTransact$transferOwnership$(parcel, parcel2);
                case 314:
                    PersistableBundle transferOwnershipBundle = getTransferOwnershipBundle();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(transferOwnershipBundle, 1);
                    return true;
                case 315:
                    ComponentName componentName87 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence3 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStartUserSessionMessage(componentName87, charSequence3);
                    parcel2.writeNoException();
                    return true;
                case 316:
                    ComponentName componentName88 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence4 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setEndUserSessionMessage(componentName88, charSequence4);
                    parcel2.writeNoException();
                    return true;
                case 317:
                    ComponentName componentName89 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    CharSequence startUserSessionMessage = getStartUserSessionMessage(componentName89);
                    parcel2.writeNoException();
                    if (startUserSessionMessage != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(startUserSessionMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 318:
                    ComponentName componentName90 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    CharSequence endUserSessionMessage = getEndUserSessionMessage(componentName90);
                    parcel2.writeNoException();
                    if (endUserSessionMessage != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(endUserSessionMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 319:
                    ComponentName componentName91 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<String> meteredDataDisabledPackages = setMeteredDataDisabledPackages(componentName91, createStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeStringList(meteredDataDisabledPackages);
                    return true;
                case 320:
                    ComponentName componentName92 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> meteredDataDisabledPackages2 = getMeteredDataDisabledPackages(componentName92);
                    parcel2.writeNoException();
                    parcel2.writeStringList(meteredDataDisabledPackages2);
                    return true;
                case 321:
                    ComponentName componentName93 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ApnSetting apnSetting = (ApnSetting) parcel.readTypedObject(ApnSetting.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addOverrideApn = addOverrideApn(componentName93, apnSetting);
                    parcel2.writeNoException();
                    parcel2.writeInt(addOverrideApn);
                    return true;
                case 322:
                    return onTransact$updateOverrideApn$(parcel, parcel2);
                case 323:
                    ComponentName componentName94 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeOverrideApn = removeOverrideApn(componentName94, readInt65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeOverrideApn);
                    return true;
                case 324:
                    ComponentName componentName95 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<ApnSetting> overrideApns = getOverrideApns(componentName95);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(overrideApns, 1);
                    return true;
                case 325:
                    ComponentName componentName96 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOverrideApnsEnabled(componentName96, readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 326:
                    ComponentName componentName97 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isOverrideApnEnabled = isOverrideApnEnabled(componentName97);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOverrideApnEnabled);
                    return true;
                case 327:
                    return onTransact$isMeteredDataDisabledPackageForUser$(parcel, parcel2);
                case 328:
                    return onTransact$reportFailedPasswordAttemptWithFailureCount$(parcel, parcel2);
                case 329:
                    return onTransact$setGlobalPrivateDns$(parcel, parcel2);
                case 330:
                    ComponentName componentName98 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int globalPrivateDnsMode = getGlobalPrivateDnsMode(componentName98);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalPrivateDnsMode);
                    return true;
                case 331:
                    ComponentName componentName99 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    String globalPrivateDnsHost = getGlobalPrivateDnsHost(componentName99);
                    parcel2.writeNoException();
                    parcel2.writeString(globalPrivateDnsHost);
                    return true;
                case 332:
                    return onTransact$setProfileOwnerOnOrganizationOwnedDevice$(parcel, parcel2);
                case 333:
                    return onTransact$installUpdateFromFile$(parcel, parcel2);
                case 334:
                    ComponentName componentName100 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCrossProfileCalendarPackages(componentName100, createStringArrayList5);
                    parcel2.writeNoException();
                    return true;
                case 335:
                    ComponentName componentName101 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> crossProfileCalendarPackages = getCrossProfileCalendarPackages(componentName101);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileCalendarPackages);
                    return true;
                case 336:
                    String readString61 = parcel.readString();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAllowedToAccessCalendarForUser = isPackageAllowedToAccessCalendarForUser(readString61, readInt66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAllowedToAccessCalendarForUser);
                    return true;
                case 337:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> crossProfileCalendarPackagesForUser = getCrossProfileCalendarPackagesForUser(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileCalendarPackagesForUser);
                    return true;
                case 338:
                    ComponentName componentName102 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCrossProfilePackages(componentName102, createStringArrayList6);
                    parcel2.writeNoException();
                    return true;
                case 339:
                    ComponentName componentName103 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> crossProfilePackages = getCrossProfilePackages(componentName103);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfilePackages);
                    return true;
                case 340:
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> allCrossProfilePackages = getAllCrossProfilePackages(readInt68);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allCrossProfilePackages);
                    return true;
                case 341:
                    List<String> defaultCrossProfilePackages = getDefaultCrossProfilePackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(defaultCrossProfilePackages);
                    return true;
                case 342:
                    boolean isManagedKiosk = isManagedKiosk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isManagedKiosk);
                    return true;
                case 343:
                    boolean isUnattendedManagedKiosk = isUnattendedManagedKiosk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUnattendedManagedKiosk);
                    return true;
                case 344:
                    return onTransact$startViewCalendarEventInManagedProfile$(parcel, parcel2);
                case 345:
                    return onTransact$setKeyGrantForApp$(parcel, parcel2);
                case 346:
                    String readString62 = parcel.readString();
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelableGranteeMap keyPairGrants = getKeyPairGrants(readString62, readString63);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyPairGrants, 1);
                    return true;
                case 347:
                    return onTransact$setKeyGrantToWifiAuth$(parcel, parcel2);
                case 348:
                    String readString64 = parcel.readString();
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isKeyPairGrantedToWifiAuth = isKeyPairGrantedToWifiAuth(readString64, readString65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyPairGrantedToWifiAuth);
                    return true;
                case 349:
                    return onTransact$setUserControlDisabledPackages$(parcel, parcel2);
                case 350:
                    ComponentName componentName104 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> userControlDisabledPackages = getUserControlDisabledPackages(componentName104, readString66);
                    parcel2.writeNoException();
                    parcel2.writeStringList(userControlDisabledPackages);
                    return true;
                case 351:
                    return onTransact$setCommonCriteriaModeEnabled$(parcel, parcel2);
                case 352:
                    ComponentName componentName105 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCommonCriteriaModeEnabled = isCommonCriteriaModeEnabled(componentName105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCommonCriteriaModeEnabled);
                    return true;
                case 353:
                    ComponentName componentName106 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int personalAppsSuspendedReasons = getPersonalAppsSuspendedReasons(componentName106);
                    parcel2.writeNoException();
                    parcel2.writeInt(personalAppsSuspendedReasons);
                    return true;
                case 354:
                    ComponentName componentName107 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPersonalAppsSuspended(componentName107, readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 355:
                    ComponentName componentName108 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    long managedProfileMaximumTimeOff = getManagedProfileMaximumTimeOff(componentName108);
                    parcel2.writeNoException();
                    parcel2.writeLong(managedProfileMaximumTimeOff);
                    return true;
                case 356:
                    ComponentName componentName109 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setManagedProfileMaximumTimeOff(componentName109, readLong);
                    parcel2.writeNoException();
                    return true;
                case 357:
                    acknowledgeDeviceCompliant();
                    parcel2.writeNoException();
                    return true;
                case 358:
                    boolean isComplianceAcknowledgementRequired = isComplianceAcknowledgementRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isComplianceAcknowledgementRequired);
                    return true;
                case 359:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canProfileOwnerResetPasswordWhenLocked = canProfileOwnerResetPasswordWhenLocked(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canProfileOwnerResetPasswordWhenLocked);
                    return true;
                case 360:
                    int readInt70 = parcel.readInt();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNextOperationSafety(readInt70, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 361:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSafeOperation = isSafeOperation(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSafeOperation);
                    return true;
                case 362:
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String enrollmentSpecificId = getEnrollmentSpecificId(readString67);
                    parcel2.writeNoException();
                    parcel2.writeString(enrollmentSpecificId);
                    return true;
                case 363:
                    return onTransact$setOrganizationIdForUser$(parcel, parcel2);
                case 364:
                    ManagedProfileProvisioningParams managedProfileProvisioningParams = (ManagedProfileProvisioningParams) parcel.readTypedObject(ManagedProfileProvisioningParams.CREATOR);
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UserHandle createAndProvisionManagedProfile = createAndProvisionManagedProfile(managedProfileProvisioningParams, readString68);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAndProvisionManagedProfile, 1);
                    return true;
                case 365:
                    ManagedProfileProvisioningParams managedProfileProvisioningParams2 = (ManagedProfileProvisioningParams) parcel.readTypedObject(ManagedProfileProvisioningParams.CREATOR);
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UserHandle createManagedProfile = createManagedProfile(managedProfileProvisioningParams2, readString69);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createManagedProfile, 1);
                    return true;
                case 366:
                    ManagedProfileProvisioningParams managedProfileProvisioningParams3 = (ManagedProfileProvisioningParams) parcel.readTypedObject(ManagedProfileProvisioningParams.CREATOR);
                    UserHandle userHandle8 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    finalizeCreateManagedProfile(managedProfileProvisioningParams3, userHandle8);
                    parcel2.writeNoException();
                    return true;
                case 367:
                    FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams = (FullyManagedDeviceProvisioningParams) parcel.readTypedObject(FullyManagedDeviceProvisioningParams.CREATOR);
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    provisionFullyManagedDevice(fullyManagedDeviceProvisioningParams, readString70);
                    parcel2.writeNoException();
                    return true;
                case 368:
                    UserHandle userHandle9 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    Account account = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    finalizeWorkProfileProvisioning(userHandle9, account);
                    parcel2.writeNoException();
                    return true;
                case 369:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeManagedProfile = removeManagedProfile(readInt73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeManagedProfile);
                    return true;
                case 370:
                    return onTransact$setDeviceOwnerType$(parcel, parcel2);
                case 371:
                    ComponentName componentName110 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceOwnerType = getDeviceOwnerType(componentName110);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceOwnerType);
                    return true;
                case 372:
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetDefaultCrossProfileIntentFilters(readInt74);
                    parcel2.writeNoException();
                    return true;
                case 373:
                    boolean canAdminGrantSensorsPermissions = canAdminGrantSensorsPermissions();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAdminGrantSensorsPermissions);
                    return true;
                case 374:
                    return onTransact$setUsbDataSignalingEnabled$(parcel, parcel2);
                case 375:
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUsbDataSignalingEnabled = isUsbDataSignalingEnabled(readString71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbDataSignalingEnabled);
                    return true;
                case 376:
                    boolean canUsbDataSignalingBeDisabled = canUsbDataSignalingBeDisabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canUsbDataSignalingBeDisabled);
                    return true;
                case 377:
                    return onTransact$setMinimumRequiredWifiSecurityLevel$(parcel, parcel2);
                case 378:
                    int minimumRequiredWifiSecurityLevel = getMinimumRequiredWifiSecurityLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(minimumRequiredWifiSecurityLevel);
                    return true;
                case 379:
                    return onTransact$setWifiSsidPolicy$(parcel, parcel2);
                case 380:
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    WifiSsidPolicy wifiSsidPolicy = getWifiSsidPolicy(readString72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiSsidPolicy, 1);
                    return true;
                case 381:
                    String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isDevicePotentiallyStolen = isDevicePotentiallyStolen(readString73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDevicePotentiallyStolen);
                    return true;
                case 382:
                    List<UserHandle> listForegroundAffiliatedUsers = listForegroundAffiliatedUsers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listForegroundAffiliatedUsers, 1);
                    return true;
                case 383:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(DevicePolicyDrawableResource.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDrawables(createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 384:
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetDrawables(createStringArrayList7);
                    parcel2.writeNoException();
                    return true;
                case 385:
                    return onTransact$getDrawable$(parcel, parcel2);
                case 386:
                    boolean isDpcDownloaded = isDpcDownloaded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDpcDownloaded);
                    return true;
                case 387:
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDpcDownloaded(readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 388:
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(DevicePolicyStringResource.CREATOR);
                    parcel.enforceNoDataAvail();
                    setStrings(createTypedArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 389:
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetStrings(createStringArrayList8);
                    parcel2.writeNoException();
                    return true;
                case 390:
                    String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelableResource string = getString(readString74);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(string, 1);
                    return true;
                case 391:
                    resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState();
                    parcel2.writeNoException();
                    return true;
                case 392:
                    boolean shouldAllowBypassingDevicePolicyManagementRoleQualification = shouldAllowBypassingDevicePolicyManagementRoleQualification();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldAllowBypassingDevicePolicyManagementRoleQualification);
                    return true;
                case 393:
                    UserHandle userHandle10 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<UserHandle> policyManagedProfiles = getPolicyManagedProfiles(userHandle10);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(policyManagedProfiles, 1);
                    return true;
                case 394:
                    return onTransact$semSetPasswordQuality$(parcel, parcel2);
                case 395:
                    return onTransact$semSetPasswordMinimumLength$(parcel, parcel2);
                case 396:
                    return onTransact$semSetPasswordMinimumUpperCase$(parcel, parcel2);
                case 397:
                    return onTransact$semSetPasswordMinimumLowerCase$(parcel, parcel2);
                case 398:
                    return onTransact$semSetPasswordMinimumNonLetter$(parcel, parcel2);
                case 399:
                    return onTransact$semSetPasswordHistoryLength$(parcel, parcel2);
                case 400:
                    return onTransact$semSetPasswordExpirationTimeout$(parcel, parcel2);
                case 401:
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semIsActivePasswordSufficient = semIsActivePasswordSufficient(readInt75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsActivePasswordSufficient);
                    return true;
                case 402:
                    return onTransact$semSetSimplePasswordEnabled$(parcel, parcel2);
                case 403:
                    return onTransact$semIsSimplePasswordEnabled$(parcel, parcel2);
                case 404:
                    return onTransact$semSetKeyguardDisabledFeatures$(parcel, parcel2);
                case 405:
                    return onTransact$semSetAllowStorageCard$(parcel, parcel2);
                case 406:
                    return onTransact$semGetAllowStorageCard$(parcel, parcel2);
                case 407:
                    return onTransact$semSetAllowWifi$(parcel, parcel2);
                case 408:
                    return onTransact$semGetAllowWifi$(parcel, parcel2);
                case 409:
                    return onTransact$semSetAllowTextMessaging$(parcel, parcel2);
                case 410:
                    return onTransact$semGetAllowTextMessaging$(parcel, parcel2);
                case 411:
                    return onTransact$semSetAllowPopImapEmail$(parcel, parcel2);
                case 412:
                    return onTransact$semGetAllowPopImapEmail$(parcel, parcel2);
                case 413:
                    return onTransact$semSetAllowBrowser$(parcel, parcel2);
                case 414:
                    return onTransact$semGetAllowBrowser$(parcel, parcel2);
                case 415:
                    return onTransact$semSetAllowInternetSharing$(parcel, parcel2);
                case 416:
                    return onTransact$semGetAllowInternetSharing$(parcel, parcel2);
                case 417:
                    return onTransact$semSetAllowBluetoothMode$(parcel, parcel2);
                case 418:
                    return onTransact$semGetAllowBluetoothMode$(parcel, parcel2);
                case 419:
                    return onTransact$semSetAllowDesktopSync$(parcel, parcel2);
                case 420:
                    return onTransact$semGetAllowDesktopSync$(parcel, parcel2);
                case 421:
                    return onTransact$semSetAllowIrda$(parcel, parcel2);
                case 422:
                    return onTransact$semGetAllowIrda$(parcel, parcel2);
                case 423:
                    return onTransact$semSetRequireStorageCardEncryption$(parcel, parcel2);
                case 424:
                    return onTransact$semGetRequireStorageCardEncryption$(parcel, parcel2);
                case 425:
                    return onTransact$semSetChangeNotificationEnabled$(parcel, parcel2);
                case 426:
                    return onTransact$setApplicationExemptions$(parcel, parcel2);
                case 427:
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] applicationExemptions = getApplicationExemptions(readString75);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(applicationExemptions);
                    return true;
                case 428:
                    return onTransact$setMtePolicy$(parcel, parcel2);
                case 429:
                    return onTransact$setMtePolicyBySystem$(parcel, parcel2);
                case 430:
                    String readString76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mtePolicy = getMtePolicy(readString76);
                    parcel2.writeNoException();
                    parcel2.writeInt(mtePolicy);
                    return true;
                case 431:
                    ManagedSubscriptionsPolicy managedSubscriptionsPolicy = (ManagedSubscriptionsPolicy) parcel.readTypedObject(ManagedSubscriptionsPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManagedSubscriptionsPolicy(managedSubscriptionsPolicy);
                    parcel2.writeNoException();
                    return true;
                case 432:
                    ManagedSubscriptionsPolicy managedSubscriptionsPolicy2 = getManagedSubscriptionsPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(managedSubscriptionsPolicy2, 1);
                    return true;
                case 433:
                    DevicePolicyState devicePolicyState = getDevicePolicyState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(devicePolicyState, 1);
                    return true;
                case 434:
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean triggerDevicePolicyEngineMigration = triggerDevicePolicyEngineMigration(readBoolean23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(triggerDevicePolicyEngineMigration);
                    return true;
                case 435:
                    String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceFinanced = isDeviceFinanced(readString77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceFinanced);
                    return true;
                case 436:
                    String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String financedDeviceKioskRoleHolder = getFinancedDeviceKioskRoleHolder(readString78);
                    parcel2.writeNoException();
                    parcel2.writeString(financedDeviceKioskRoleHolder);
                    return true;
                case 437:
                    return onTransact$setCrossProfileAppToIgnored$(parcel, parcel2);
                case 438:
                    return onTransact$getSamsungSDcardEncryptionStatus$(parcel, parcel2);
                case 439:
                    calculateHasIncompatibleAccounts();
                    parcel2.writeNoException();
                    return true;
                case 440:
                    return onTransact$setContentProtectionPolicy$(parcel, parcel2);
                case 441:
                    return onTransact$getContentProtectionPolicy$(parcel, parcel2);
                case 442:
                    String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] subscriptionIds = getSubscriptionIds(readString79);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(subscriptionIds);
                    return true;
                case 443:
                    return onTransact$setMaxPolicyStorageLimit$(parcel, parcel2);
                case 444:
                    return onTransact$forceSetMaxPolicyStorageLimit$(parcel, parcel2);
                case 445:
                    String readString80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int maxPolicyStorageLimit = getMaxPolicyStorageLimit(readString80);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxPolicyStorageLimit);
                    return true;
                case 446:
                    return onTransact$getPolicySizeForAdmin$(parcel, parcel2);
                case 447:
                    String readString81 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int headlessDeviceOwnerMode = getHeadlessDeviceOwnerMode(readString81);
                    parcel2.writeNoException();
                    parcel2.writeInt(headlessDeviceOwnerMode);
                    return true;
                case 448:
                    return onTransact$setAppFunctionsPolicy$(parcel, parcel2);
                case 449:
                    return onTransact$getAppFunctionsPolicy$(parcel, parcel2);
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDevicePolicyManager {
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

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PasswordMetrics) obtain2.readTypedObject(PasswordMetrics.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordExpirationTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpirationTimeout(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpiration(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficient(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficientForDeviceRequirement() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordComplexity(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredPasswordComplexity(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getRequiredPasswordComplexity(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAggregatedPasswordComplexityForUser(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsingUnifiedPassword(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getCurrentFailedPasswordAttempts(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getCurrentFailedBiometricAttempts(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumFailedPasswordsForWipe(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaximumFailedPasswordsForWipe(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPassword(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumTimeToLock(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getMaximumTimeToLock(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredStrongAuthTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getRequiredStrongAuthTimeout(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void lockNow(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void wipeDataWithReason(String str, int i, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setFactoryResetProtectionPolicy(ComponentName componentName, String str, FactoryResetProtectionPolicy factoryResetProtectionPolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(factoryResetProtectionPolicy, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FactoryResetProtectionPolicy) obtain2.readTypedObject(FactoryResetProtectionPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isFactoryResetProtectionPolicySupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void sendLostModeLocationUpdate(AndroidFuture<Boolean> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName setGlobalProxy(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getGlobalProxyAdmin(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setStorageEncryption(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getStorageEncryption(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getStorageEncryptionStatus(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean requestBugreport(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCameraDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCameraDisabled(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setScreenCaptureDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getScreenCaptureDisabled(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyNotificationStreamingPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyNotificationStreamingPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyAppStreamingPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyAppStreamingPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeyguardDisabledFeatures(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getKeyguardDisabledFeatures(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setActiveAdmin(ComponentName componentName, boolean z, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAdminActive(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<ComponentName> getActiveAdmins(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean packageHasActiveAdmins(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void removeActiveAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceRemoveActiveAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasGrantedPolicy(ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportPasswordChanged(PasswordMetrics passwordMetrics, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(passwordMetrics, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedPasswordAttempt(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulPasswordAttempt(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedBiometricAttempt(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulBiometricAttempt(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardDismissed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardSecured(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setDeviceOwner(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getDeviceOwnerComponent(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getDeviceOwnerComponentOnUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasDeviceOwner() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getDeviceOwnerName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearDeviceOwner(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerUserId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setProfileOwner(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getProfileOwnerAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSupervisionComponent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getProfileOwnerName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileEnabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileName(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearProfileOwner(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasUserSetupCompleted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOrganizationOwnedDeviceWithManagedProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean checkDeviceIdentifierAccess(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] setPackagesSuspended(ComponentName componentName, String str, String[] strArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageSuspended(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> listPolicyExemptApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installCaCert(ComponentName componentName, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallCaCerts(ComponentName componentName, String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enforceCanManageCaCerts(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean approveCaCert(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCaCertApproved(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installKeyPair(ComponentName componentName, String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeKeyPair(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasKeyPair(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean generateKeyPair(ComponentName componentName, String str, String str2, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(parcelableKeyGenParameterSpec, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        keymasterCertificateChain.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyPairCertificate(ComponentName componentName, String str, String str2, byte[] bArr, byte[] bArr2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void choosePrivateKeyAlias(int i, Uri uri, String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDelegatedScopes(ComponentName componentName, String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDelegatedScopes(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDelegatePackages(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCertInstallerPackage(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getCertInstallerPackage(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setAlwaysOnVpnPackage(ComponentName componentName, String str, boolean z, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getAlwaysOnVpnPackage(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getAlwaysOnVpnPackageForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws RemoteException {
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

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addPersistentPreferredActivity(ComponentName componentName, String str, IntentFilter intentFilter, ComponentName componentName2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearPackagePersistentPreferredActivities(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultSmsApplication(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultDialerApplication(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationRestrictions(ComponentName componentName, String str, String str2, Bundle bundle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getApplicationRestrictions(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getApplicationRestrictionsManagingPackage(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallerApplicationRestrictionsManagingPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getRestrictionsProvider(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestriction(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionForUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionGlobally(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionGloballyFromSystem(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getUserRestrictions(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getUserRestrictionsGlobally(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addCrossProfileIntentFilter(ComponentName componentName, String str, IntentFilter intentFilter, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearCrossProfileIntentFilters(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedAccessibilityServices(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedAccessibilityServices(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedAccessibilityServicesForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedInputMethods(ComponentName componentName, String str, List<String> list, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedInputMethods(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedInputMethodsAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isInputMethodPermittedByAdmin(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNotificationListenerServicePermitted(String str, int i) throws RemoteException {
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

            @Override // android.app.admin.IDevicePolicyManager
            public Intent createAdminSupportIntent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getEnforcingAdminAndUserDetails(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public EnforcingAdmin getEnforcingAdmin(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnforcingAdmin) obtain2.readTypedObject(EnforcingAdmin.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<EnforcingAdmin> getEnforcingAdminsForRestriction(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(EnforcingAdmin.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationHidden(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isApplicationHidden(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createAndManageUser(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserHandle) obtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int startUserInBackground(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUser(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUserInternal() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLogoutUserId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getSecondaryUsers(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeNewUserDisclaimer(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNewUserDisclaimerAcknowledged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enableSystemApp(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int enableSystemAppWithIntent(ComponentName componentName, String str, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installExistingPackage(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAccountManagementDisabled(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getAccountTypesWithManagementDisabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getAccountTypesWithManagementDisabledAsUser(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecondaryLockscreenEnabled(ComponentName componentName, boolean z, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecondaryLockscreenEnabled(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPreferentialNetworkServiceConfigs(List<PreferentialNetworkServiceConfig> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PreferentialNetworkServiceConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskPackages(ComponentName componentName, String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getLockTaskPackages(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLockTaskPermitted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskFeatures(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLockTaskFeatures(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setGlobalSetting(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemSetting(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecureSetting(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setConfiguredNetworksLockdownState(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasLockdownAdminConfiguredNetworks(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLocationEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTime(ComponentName componentName, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTimeZone(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMasterVolumeMuted(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyLockTaskModeChanged(boolean z, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUninstallBlocked(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallBlocked(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabledForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabledForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void startManagedQuickContact(String str, long j, boolean z, long j2, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileCallerIdAccessPolicy(PackagePolicy packagePolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getManagedProfileCallerIdAccessPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackagePolicy) obtain2.readTypedObject(PackagePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileCallerIdAccess(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCredentialManagerPolicy(PackagePolicy packagePolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getCredentialManagerPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackagePolicy) obtain2.readTypedObject(PackagePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileContactsAccessPolicy(PackagePolicy packagePolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getManagedProfileContactsAccessPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackagePolicy) obtain2.readTypedObject(PackagePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileContactsAccess(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBluetoothContactSharingDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabledForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setTrustAgentConfiguration(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean addCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileWidgetProviders(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeRequired(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeRequired() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimePolicy(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAutoTimePolicy(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeZoneEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeZoneEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeZonePolicy(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAutoTimeZonePolicy(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setForceEphemeralUsers(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(232, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isRemovingAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemUpdatePolicy(ComponentName componentName, String str, SystemUpdatePolicy systemUpdatePolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(systemUpdatePolicy, 0);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SystemUpdatePolicy) obtain2.readTypedObject(SystemUpdatePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyguardDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setStatusBarDisabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isStatusBarDisabled(String str) throws RemoteException {
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

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyPendingSystemUpdate(SystemUpdateInfo systemUpdateInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(systemUpdateInfo, 0);
                    this.mRemote.transact(243, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(244, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SystemUpdateInfo) obtain2.readTypedObject(SystemUpdateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionPolicy(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionGrantState(ComponentName componentName, String str, String str2, String str3, int i, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(247, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionGrantState(ComponentName componentName, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isProvisioningAllowed(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(249, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int checkProvisioningPrecondition(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(250, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeepUninstalledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getKeepUninstalledPackages(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedProfile(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(253, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getWifiMacAddress(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(254, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reboot(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(255, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setShortSupportMessage(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(256, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getShortSupportMessage(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(257, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(258, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getLongSupportMessage(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(259, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getShortSupportMessageForUser(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(260, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getLongSupportMessageForUser(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(261, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColor(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(262, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColorForUser(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(263, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearOrganizationIdForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(264, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColor(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(265, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColorForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(266, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationName(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(267, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getOrganizationName(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(268, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(269, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getOrganizationNameForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(270, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getUserProvisioningState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(271, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserProvisioningState(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(272, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(273, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAffiliationIds(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(274, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallingUserAffiliated() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(275, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAffiliatedUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(276, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecurityLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(277, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecurityLoggingEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(278, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParceledListSlice retrieveSecurityLogs(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(279, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(280, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceNetworkLogs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(281, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceSecurityLogs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(282, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEnabled(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(283, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAuditLogEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(284, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEventsCallback(String str, IAuditLogEventsCallback iAuditLogEventsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAuditLogEventsCallback);
                    this.mRemote.transact(285, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallInQueue(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(286, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallPackageWithActiveAdmins(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(287, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioned() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(288, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(289, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(290, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceUpdateUserSetupComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(291, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBackupServiceEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(292, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isBackupServiceEnabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(293, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNetworkLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(294, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNetworkLoggingEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(295, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(296, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(NetworkEvent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(297, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(298, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isEphemeralUser(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(299, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastSecurityLogRetrievalTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(300, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastBugReportRequestTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(301, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastNetworkLogRetrievalTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(302, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setResetPasswordToken(ComponentName componentName, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(303, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean clearResetPasswordToken(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(304, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isResetPasswordTokenActive(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(305, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPasswordWithToken(ComponentName componentName, String str, String str2, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(306, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(307, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(308, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StringParceledListSlice) obtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearApplicationUserData(ComponentName componentName, String str, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(309, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLogoutEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(310, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLogoutEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(311, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDisallowedSystemApps(ComponentName componentName, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(312, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(313, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(314, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PersistableBundle) obtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(315, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(316, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getStartUserSessionMessage(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(317, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getEndUserSessionMessage(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(318, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(319, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getMeteredDataDisabledPackages(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(320, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(321, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean updateOverrideApn(ComponentName componentName, int i, ApnSetting apnSetting) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(322, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeOverrideApn(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(323, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<ApnSetting> getOverrideApns(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(324, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ApnSetting.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOverrideApnsEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(325, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(326, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(327, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedPasswordAttemptWithFailureCount(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(328, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setGlobalPrivateDns(ComponentName componentName, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(329, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(330, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getGlobalPrivateDnsHost(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(331, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileOwnerOnOrganizationOwnedDevice(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(332, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void installUpdateFromFile(ComponentName componentName, String str, ParcelFileDescriptor parcelFileDescriptor, StartInstallingUpdateCallback startInstallingUpdateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(startInstallingUpdateCallback);
                    this.mRemote.transact(333, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(334, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileCalendarPackages(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(335, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageAllowedToAccessCalendarForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(336, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileCalendarPackagesForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(337, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfilePackages(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(338, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfilePackages(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(339, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAllCrossProfilePackages(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(340, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDefaultCrossProfilePackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(341, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedKiosk() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(342, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUnattendedManagedKiosk() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(343, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean startViewCalendarEventInManagedProfile(String str, long j, long j2, long j3, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(344, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantForApp(ComponentName componentName, String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(345, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableGranteeMap getKeyPairGrants(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(346, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelableGranteeMap) obtain2.readTypedObject(ParcelableGranteeMap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantToWifiAuth(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(347, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isKeyPairGrantedToWifiAuth(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(348, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserControlDisabledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(349, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getUserControlDisabledPackages(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(350, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCommonCriteriaModeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(351, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCommonCriteriaModeEnabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(352, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPersonalAppsSuspendedReasons(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(353, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPersonalAppsSuspended(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(354, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getManagedProfileMaximumTimeOff(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(355, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileMaximumTimeOff(ComponentName componentName, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(356, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeDeviceCompliant() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(357, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isComplianceAcknowledgementRequired() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(358, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canProfileOwnerResetPasswordWhenLocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(359, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNextOperationSafety(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(360, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSafeOperation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(361, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getEnrollmentSpecificId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(362, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationIdForUser(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(363, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createAndProvisionManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(managedProfileProvisioningParams, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(364, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserHandle) obtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(managedProfileProvisioningParams, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(365, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserHandle) obtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void finalizeCreateManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(managedProfileProvisioningParams, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(366, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void provisionFullyManagedDevice(FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fullyManagedDeviceProvisioningParams, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(367, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void finalizeWorkProfileProvisioning(UserHandle userHandle, Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(368, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeManagedProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(369, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerType(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(370, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerType(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(371, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDefaultCrossProfileIntentFilters(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(372, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canAdminGrantSensorsPermissions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(373, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUsbDataSignalingEnabled(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(374, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsbDataSignalingEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(375, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canUsbDataSignalingBeDisabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(376, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMinimumRequiredWifiSecurityLevel(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(377, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMinimumRequiredWifiSecurityLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(378, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setWifiSsidPolicy(String str, WifiSsidPolicy wifiSsidPolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(wifiSsidPolicy, 0);
                    this.mRemote.transact(379, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public WifiSsidPolicy getWifiSsidPolicy(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(380, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WifiSsidPolicy) obtain2.readTypedObject(WifiSsidPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDevicePotentiallyStolen(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(381, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> listForegroundAffiliatedUsers() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(382, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDrawables(List<DevicePolicyDrawableResource> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(383, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDrawables(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(384, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableResource getDrawable(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(385, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelableResource) obtain2.readTypedObject(ParcelableResource.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDpcDownloaded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(386, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDpcDownloaded(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(387, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStrings(List<DevicePolicyStringResource> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(388, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetStrings(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(389, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableResource getString(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(390, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelableResource) obtain2.readTypedObject(ParcelableResource.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(391, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(392, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(393, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordQuality(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(394, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumLength(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(395, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumUpperCase(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(396, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumLowerCase(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(397, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumNonLetter(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(398, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordHistoryLength(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(399, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordExpirationTimeout(ComponentName componentName, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(400, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semIsActivePasswordSufficient(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(401, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetSimplePasswordEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(402, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semIsSimplePasswordEnabled(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(403, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetKeyguardDisabledFeatures(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(404, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowStorageCard(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(405, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowStorageCard(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(406, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowWifi(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(407, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowWifi(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(408, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowTextMessaging(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(409, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowTextMessaging(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(410, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowPopImapEmail(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(411, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowPopImapEmail(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(412, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowBrowser(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(413, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowBrowser(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(414, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowInternetSharing(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(415, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowInternetSharing(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(416, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(417, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int semGetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(418, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowDesktopSync(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(419, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowDesktopSync(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(420, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowIrda(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(421, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowIrda(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(422, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetRequireStorageCardEncryption(ComponentName componentName, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(423, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetRequireStorageCardEncryption(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(424, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetChangeNotificationEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(425, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationExemptions(String str, String str2, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(426, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getApplicationExemptions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(427, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMtePolicy(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(428, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMtePolicyBySystem(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(429, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMtePolicy(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(430, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy managedSubscriptionsPolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(managedSubscriptionsPolicy, 0);
                    this.mRemote.transact(431, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(432, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ManagedSubscriptionsPolicy) obtain2.readTypedObject(ManagedSubscriptionsPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public DevicePolicyState getDevicePolicyState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(433, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DevicePolicyState) obtain2.readTypedObject(DevicePolicyState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean triggerDevicePolicyEngineMigration(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(434, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceFinanced(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(435, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getFinancedDeviceKioskRoleHolder(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(436, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileAppToIgnored(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(437, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getSamsungSDcardEncryptionStatus(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(438, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void calculateHasIncompatibleAccounts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(439, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(440, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(441, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getSubscriptionIds(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(442, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaxPolicyStorageLimit(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(443, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceSetMaxPolicyStorageLimit(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(444, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaxPolicyStorageLimit(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(445, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPolicySizeForAdmin(String str, EnforcingAdmin enforcingAdmin) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(enforcingAdmin, 0);
                    this.mRemote.transact(446, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getHeadlessDeviceOwnerMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(447, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAppFunctionsPolicy(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(448, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAppFunctionsPolicy(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(449, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        private boolean onTransact$setPasswordQuality$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordQuality(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordQuality$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordQuality = getPasswordQuality(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordQuality);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLength = getPasswordMinimumLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLength);
            return true;
        }

        private boolean onTransact$setPasswordMinimumUpperCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumUpperCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumUpperCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumUpperCase = getPasswordMinimumUpperCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumUpperCase);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLowerCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLowerCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLowerCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLowerCase = getPasswordMinimumLowerCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLowerCase);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLetters$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLetters(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLetters$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLetters = getPasswordMinimumLetters(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLetters);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNumeric$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumNumeric(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNumeric$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumNumeric = getPasswordMinimumNumeric(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumNumeric);
            return true;
        }

        private boolean onTransact$setPasswordMinimumSymbols$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumSymbols(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumSymbols$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumSymbols = getPasswordMinimumSymbols(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumSymbols);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNonLetter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumNonLetter(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNonLetter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumNonLetter = getPasswordMinimumNonLetter(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumNonLetter);
            return true;
        }

        private boolean onTransact$setPasswordHistoryLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordHistoryLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordHistoryLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordHistoryLength = getPasswordHistoryLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordHistoryLength);
            return true;
        }

        private boolean onTransact$setPasswordExpirationTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordExpirationTimeout(componentName, readString, readLong, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordExpirationTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long passwordExpirationTimeout = getPasswordExpirationTimeout(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(passwordExpirationTimeout);
            return true;
        }

        private boolean onTransact$getPasswordExpiration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long passwordExpiration = getPasswordExpiration(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(passwordExpiration);
            return true;
        }

        private boolean onTransact$isActivePasswordSufficient$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean isActivePasswordSufficient = isActivePasswordSufficient(readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(isActivePasswordSufficient);
            return true;
        }

        private boolean onTransact$setRequiredPasswordComplexity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRequiredPasswordComplexity(readString, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCurrentFailedPasswordAttempts$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int currentFailedPasswordAttempts = getCurrentFailedPasswordAttempts(readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(currentFailedPasswordAttempts);
            return true;
        }

        private boolean onTransact$setMaximumFailedPasswordsForWipe$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMaximumFailedPasswordsForWipe(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumFailedPasswordsForWipe$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int maximumFailedPasswordsForWipe = getMaximumFailedPasswordsForWipe(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(maximumFailedPasswordsForWipe);
            return true;
        }

        private boolean onTransact$setMaximumTimeToLock$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMaximumTimeToLock(componentName, readString, readLong, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumTimeToLock$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long maximumTimeToLock = getMaximumTimeToLock(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(maximumTimeToLock);
            return true;
        }

        private boolean onTransact$setRequiredStrongAuthTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRequiredStrongAuthTimeout(componentName, readString, readLong, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRequiredStrongAuthTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long requiredStrongAuthTimeout = getRequiredStrongAuthTimeout(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(requiredStrongAuthTimeout);
            return true;
        }

        private boolean onTransact$lockNow$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            lockNow(readInt, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$wipeDataWithReason$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            wipeDataWithReason(readString, readInt, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setFactoryResetProtectionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            FactoryResetProtectionPolicy factoryResetProtectionPolicy = (FactoryResetProtectionPolicy) parcel.readTypedObject(FactoryResetProtectionPolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setFactoryResetProtectionPolicy(componentName, readString, factoryResetProtectionPolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalProxy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            ComponentName globalProxy = setGlobalProxy(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(globalProxy, 1);
            return true;
        }

        private boolean onTransact$setCameraDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setCameraDisabled(componentName, readString, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCameraDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean cameraDisabled = getCameraDisabled(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(cameraDisabled);
            return true;
        }

        private boolean onTransact$setScreenCaptureDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setScreenCaptureDisabled(componentName, readString, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getScreenCaptureDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean screenCaptureDisabled = getScreenCaptureDisabled(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(screenCaptureDisabled);
            return true;
        }

        private boolean onTransact$setKeyguardDisabledFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setKeyguardDisabledFeatures(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getKeyguardDisabledFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int keyguardDisabledFeatures = getKeyguardDisabledFeatures(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(keyguardDisabledFeatures);
            return true;
        }

        private boolean onTransact$setActiveAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setActiveAdmin(componentName, readBoolean, readInt, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRemoveWarning$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            getRemoveWarning(componentName, remoteCallback, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$hasGrantedPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean hasGrantedPolicy = hasGrantedPolicy(componentName, readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeBoolean(hasGrantedPolicy);
            return true;
        }

        private boolean onTransact$setDeviceOwner$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean deviceOwner = setDeviceOwner(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(deviceOwner);
            return true;
        }

        private boolean onTransact$checkDeviceIdentifierAccess$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean checkDeviceIdentifierAccess = checkDeviceIdentifierAccess(readString, readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeBoolean(checkDeviceIdentifierAccess);
            return true;
        }

        private boolean onTransact$setPackagesSuspended$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String[] createStringArray = parcel.createStringArray();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            String[] packagesSuspended = setPackagesSuspended(componentName, readString, createStringArray, readBoolean);
            parcel2.writeNoException();
            parcel2.writeStringArray(packagesSuspended);
            return true;
        }

        private boolean onTransact$isPackageSuspended$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean isPackageSuspended = isPackageSuspended(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(isPackageSuspended);
            return true;
        }

        private boolean onTransact$installCaCert$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            boolean installCaCert = installCaCert(componentName, readString, createByteArray);
            parcel2.writeNoException();
            parcel2.writeBoolean(installCaCert);
            return true;
        }

        private boolean onTransact$uninstallCaCerts$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String[] createStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            uninstallCaCerts(componentName, readString, createStringArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$approveCaCert$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean approveCaCert = approveCaCert(readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(approveCaCert);
            return true;
        }

        private boolean onTransact$installKeyPair$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            byte[] createByteArray2 = parcel.createByteArray();
            byte[] createByteArray3 = parcel.createByteArray();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean installKeyPair = installKeyPair(componentName, readString, createByteArray, createByteArray2, createByteArray3, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            parcel2.writeBoolean(installKeyPair);
            return true;
        }

        private boolean onTransact$removeKeyPair$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean removeKeyPair = removeKeyPair(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(removeKeyPair);
            return true;
        }

        private boolean onTransact$generateKeyPair$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = (ParcelableKeyGenParameterSpec) parcel.readTypedObject(ParcelableKeyGenParameterSpec.CREATOR);
            int readInt = parcel.readInt();
            KeymasterCertificateChain keymasterCertificateChain = new KeymasterCertificateChain();
            parcel.enforceNoDataAvail();
            boolean generateKeyPair = generateKeyPair(componentName, readString, readString2, parcelableKeyGenParameterSpec, readInt, keymasterCertificateChain);
            parcel2.writeNoException();
            parcel2.writeBoolean(generateKeyPair);
            parcel2.writeTypedObject(keymasterCertificateChain, 1);
            return true;
        }

        private boolean onTransact$setKeyPairCertificate$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            byte[] createByteArray2 = parcel.createByteArray();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyPairCertificate = setKeyPairCertificate(componentName, readString, readString2, createByteArray, createByteArray2, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyPairCertificate);
            return true;
        }

        private boolean onTransact$choosePrivateKeyAlias$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            String readString = parcel.readString();
            IBinder readStrongBinder = parcel.readStrongBinder();
            parcel.enforceNoDataAvail();
            choosePrivateKeyAlias(readInt, uri, readString, readStrongBinder);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDelegatedScopes$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setDelegatedScopes(componentName, readString, createStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAlwaysOnVpnPackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            boolean alwaysOnVpnPackage = setAlwaysOnVpnPackage(componentName, readString, readBoolean, createStringArrayList);
            parcel2.writeNoException();
            parcel2.writeBoolean(alwaysOnVpnPackage);
            return true;
        }

        private boolean onTransact$addPersistentPreferredActivity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            parcel.enforceNoDataAvail();
            addPersistentPreferredActivity(componentName, readString, intentFilter, componentName2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$clearPackagePersistentPreferredActivities$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            clearPackagePersistentPreferredActivities(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDefaultSmsApplication$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setDefaultSmsApplication(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationRestrictions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setApplicationRestrictions(componentName, readString, readString2, bundle, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getApplicationRestrictions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            Bundle applicationRestrictions = getApplicationRestrictions(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            parcel2.writeTypedObject(applicationRestrictions, 1);
            return true;
        }

        private boolean onTransact$setUserRestriction$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUserRestriction(componentName, readString, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUserRestrictionForUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setUserRestrictionForUser(readString, readString2, readBoolean, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUserRestrictionGloballyFromSystem$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUserRestrictionGloballyFromSystem(readString, readString2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getUserRestrictions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            Bundle userRestrictions = getUserRestrictions(componentName, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeTypedObject(userRestrictions, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileIntentFilter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            addCrossProfileIntentFilter(componentName, readString, intentFilter, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$isAccessibilityServicePermittedByAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isAccessibilityServicePermittedByAdmin = isAccessibilityServicePermittedByAdmin(componentName, readString, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(isAccessibilityServicePermittedByAdmin);
            return true;
        }

        private boolean onTransact$setPermittedInputMethods$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean permittedInputMethods = setPermittedInputMethods(componentName, readString, createStringArrayList, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(permittedInputMethods);
            return true;
        }

        private boolean onTransact$getPermittedInputMethods$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            List<String> permittedInputMethods = getPermittedInputMethods(componentName, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeStringList(permittedInputMethods);
            return true;
        }

        private boolean onTransact$isInputMethodPermittedByAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean isInputMethodPermittedByAdmin = isInputMethodPermittedByAdmin(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(isInputMethodPermittedByAdmin);
            return true;
        }

        private boolean onTransact$setApplicationHidden$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean applicationHidden = setApplicationHidden(componentName, readString, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            parcel2.writeBoolean(applicationHidden);
            return true;
        }

        private boolean onTransact$isApplicationHidden$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean isApplicationHidden = isApplicationHidden(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(isApplicationHidden);
            return true;
        }

        private boolean onTransact$createAndManageUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            UserHandle createAndManageUser = createAndManageUser(componentName, readString, componentName2, persistableBundle, readInt);
            parcel2.writeNoException();
            parcel2.writeTypedObject(createAndManageUser, 1);
            return true;
        }

        private boolean onTransact$enableSystemApp$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            enableSystemApp(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$enableSystemAppWithIntent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            parcel.enforceNoDataAvail();
            int enableSystemAppWithIntent = enableSystemAppWithIntent(componentName, readString, intent);
            parcel2.writeNoException();
            parcel2.writeInt(enableSystemAppWithIntent);
            return true;
        }

        private boolean onTransact$installExistingPackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean installExistingPackage = installExistingPackage(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(installExistingPackage);
            return true;
        }

        private boolean onTransact$setAccountManagementDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAccountManagementDisabled(componentName, readString, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getAccountTypesWithManagementDisabledAsUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            String[] accountTypesWithManagementDisabledAsUser = getAccountTypesWithManagementDisabledAsUser(readInt, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeStringArray(accountTypesWithManagementDisabledAsUser);
            return true;
        }

        private boolean onTransact$setSecondaryLockscreenEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            parcel.enforceNoDataAvail();
            setSecondaryLockscreenEnabled(componentName, readBoolean, persistableBundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setLockTaskPackages$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String[] createStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            setLockTaskPackages(componentName, readString, createStringArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setLockTaskFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setLockTaskFeatures(componentName, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setGlobalSetting(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setSystemSetting(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSecureSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setSecureSetting(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setConfiguredNetworksLockdownState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setConfiguredNetworksLockdownState(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTime$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean time = setTime(componentName, readString, readLong);
            parcel2.writeNoException();
            parcel2.writeBoolean(time);
            return true;
        }

        private boolean onTransact$setTimeZone$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean timeZone = setTimeZone(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(timeZone);
            return true;
        }

        private boolean onTransact$notifyLockTaskModeChanged$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            notifyLockTaskModeChanged(readBoolean, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUninstallBlocked$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUninstallBlocked(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startManagedQuickContact$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            long readLong2 = parcel.readLong();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            parcel.enforceNoDataAvail();
            startManagedQuickContact(readString, readLong, readBoolean, readLong2, intent);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTrustAgentConfiguration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setTrustAgentConfiguration(componentName, readString, componentName2, persistableBundle, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getTrustAgentConfiguration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            List<PersistableBundle> trustAgentConfiguration = getTrustAgentConfiguration(componentName, componentName2, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeTypedList(trustAgentConfiguration, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileWidgetProvider$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean addCrossProfileWidgetProvider = addCrossProfileWidgetProvider(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(addCrossProfileWidgetProvider);
            return true;
        }

        private boolean onTransact$removeCrossProfileWidgetProvider$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean removeCrossProfileWidgetProvider = removeCrossProfileWidgetProvider(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(removeCrossProfileWidgetProvider);
            return true;
        }

        private boolean onTransact$setAutoTimeEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAutoTimeEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAutoTimeZoneEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAutoTimeZoneEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemUpdatePolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            SystemUpdatePolicy systemUpdatePolicy = (SystemUpdatePolicy) parcel.readTypedObject(SystemUpdatePolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setSystemUpdatePolicy(componentName, readString, systemUpdatePolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setStatusBarDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean statusBarDisabled = setStatusBarDisabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(statusBarDisabled);
            return true;
        }

        private boolean onTransact$setPermissionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setPermissionPolicy(componentName, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setPermissionGrantState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
            parcel.enforceNoDataAvail();
            setPermissionGrantState(componentName, readString, readString2, readString3, readInt, remoteCallback);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPermissionGrantState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            parcel.enforceNoDataAvail();
            int permissionGrantState = getPermissionGrantState(componentName, readString, readString2, readString3);
            parcel2.writeNoException();
            parcel2.writeInt(permissionGrantState);
            return true;
        }

        private boolean onTransact$setKeepUninstalledPackages$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setKeepUninstalledPackages(componentName, readString, createStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setShortSupportMessage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
            parcel.enforceNoDataAvail();
            setShortSupportMessage(componentName, readString, charSequence);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationName$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
            parcel.enforceNoDataAvail();
            setOrganizationName(componentName, readString, charSequence);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSecurityLoggingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setSecurityLoggingEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setNetworkLoggingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setNetworkLoggingEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$retrieveNetworkLogs$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            List<NetworkEvent> retrieveNetworkLogs = retrieveNetworkLogs(componentName, readString, readLong);
            parcel2.writeNoException();
            parcel2.writeTypedList(retrieveNetworkLogs, 1);
            return true;
        }

        private boolean onTransact$bindDeviceAdminServiceAsUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IBinder readStrongBinder = parcel.readStrongBinder();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            IServiceConnection asInterface2 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean bindDeviceAdminServiceAsUser = bindDeviceAdminServiceAsUser(componentName, asInterface, readStrongBinder, intent, asInterface2, readLong, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(bindDeviceAdminServiceAsUser);
            return true;
        }

        private boolean onTransact$setResetPasswordToken$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            boolean resetPasswordToken = setResetPasswordToken(componentName, readString, createByteArray);
            parcel2.writeNoException();
            parcel2.writeBoolean(resetPasswordToken);
            return true;
        }

        private boolean onTransact$resetPasswordWithToken$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean resetPasswordWithToken = resetPasswordWithToken(componentName, readString, readString2, createByteArray, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(resetPasswordWithToken);
            return true;
        }

        private boolean onTransact$clearApplicationUserData$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            IPackageDataObserver asInterface = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            clearApplicationUserData(componentName, readString, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDisallowedSystemApps$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            List<String> disallowedSystemApps = getDisallowedSystemApps(componentName, readInt, readString);
            parcel2.writeNoException();
            parcel2.writeStringList(disallowedSystemApps);
            return true;
        }

        private boolean onTransact$transferOwnership$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            parcel.enforceNoDataAvail();
            transferOwnership(componentName, componentName2, persistableBundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$updateOverrideApn$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            ApnSetting apnSetting = (ApnSetting) parcel.readTypedObject(ApnSetting.CREATOR);
            parcel.enforceNoDataAvail();
            boolean updateOverrideApn = updateOverrideApn(componentName, readInt, apnSetting);
            parcel2.writeNoException();
            parcel2.writeBoolean(updateOverrideApn);
            return true;
        }

        private boolean onTransact$isMeteredDataDisabledPackageForUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isMeteredDataDisabledPackageForUser = isMeteredDataDisabledPackageForUser(componentName, readString, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(isMeteredDataDisabledPackageForUser);
            return true;
        }

        private boolean onTransact$reportFailedPasswordAttemptWithFailureCount$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            reportFailedPasswordAttemptWithFailureCount(readInt, readInt2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalPrivateDns$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            int globalPrivateDns = setGlobalPrivateDns(componentName, readInt, readString);
            parcel2.writeNoException();
            parcel2.writeInt(globalPrivateDns);
            return true;
        }

        private boolean onTransact$setProfileOwnerOnOrganizationOwnedDevice$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setProfileOwnerOnOrganizationOwnedDevice(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$installUpdateFromFile$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
            StartInstallingUpdateCallback asInterface = StartInstallingUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            installUpdateFromFile(componentName, readString, parcelFileDescriptor, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startViewCalendarEventInManagedProfile$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            long readLong2 = parcel.readLong();
            long readLong3 = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean startViewCalendarEventInManagedProfile = startViewCalendarEventInManagedProfile(readString, readLong, readLong2, readLong3, readBoolean, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(startViewCalendarEventInManagedProfile);
            return true;
        }

        private boolean onTransact$setKeyGrantForApp$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyGrantForApp = setKeyGrantForApp(componentName, readString, readString2, readString3, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyGrantForApp);
            return true;
        }

        private boolean onTransact$setKeyGrantToWifiAuth$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyGrantToWifiAuth = setKeyGrantToWifiAuth(readString, readString2, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyGrantToWifiAuth);
            return true;
        }

        private boolean onTransact$setUserControlDisabledPackages$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setUserControlDisabledPackages(componentName, readString, createStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCommonCriteriaModeEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setCommonCriteriaModeEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationIdForUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setOrganizationIdForUser(readString, readString2, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDeviceOwnerType$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setDeviceOwnerType(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUsbDataSignalingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUsbDataSignalingEnabled(readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMinimumRequiredWifiSecurityLevel$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setMinimumRequiredWifiSecurityLevel(readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiSsidPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            WifiSsidPolicy wifiSsidPolicy = (WifiSsidPolicy) parcel.readTypedObject(WifiSsidPolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setWifiSsidPolicy(readString, wifiSsidPolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDrawable$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            parcel.enforceNoDataAvail();
            ParcelableResource drawable = getDrawable(readString, readString2, readString3);
            parcel2.writeNoException();
            parcel2.writeTypedObject(drawable, 1);
            return true;
        }

        private boolean onTransact$semSetPasswordQuality$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordQuality(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumLength(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumUpperCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumUpperCase(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumLowerCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumLowerCase(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumNonLetter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumNonLetter(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordHistoryLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordHistoryLength(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordExpirationTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            semSetPasswordExpirationTimeout(componentName, readLong);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetSimplePasswordEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetSimplePasswordEnabled(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semIsSimplePasswordEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semIsSimplePasswordEnabled = semIsSimplePasswordEnabled(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semIsSimplePasswordEnabled);
            return true;
        }

        private boolean onTransact$semSetKeyguardDisabledFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetKeyguardDisabledFeatures(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetAllowStorageCard$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowStorageCard(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowStorageCard$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowStorageCard = semGetAllowStorageCard(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowStorageCard);
            return true;
        }

        private boolean onTransact$semSetAllowWifi$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowWifi(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowWifi$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowWifi = semGetAllowWifi(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowWifi);
            return true;
        }

        private boolean onTransact$semSetAllowTextMessaging$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowTextMessaging(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowTextMessaging$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowTextMessaging = semGetAllowTextMessaging(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowTextMessaging);
            return true;
        }

        private boolean onTransact$semSetAllowPopImapEmail$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowPopImapEmail(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowPopImapEmail$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowPopImapEmail = semGetAllowPopImapEmail(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowPopImapEmail);
            return true;
        }

        private boolean onTransact$semSetAllowBrowser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowBrowser(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowBrowser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowBrowser = semGetAllowBrowser(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowBrowser);
            return true;
        }

        private boolean onTransact$semSetAllowInternetSharing$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowInternetSharing(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowInternetSharing$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowInternetSharing = semGetAllowInternetSharing(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowInternetSharing);
            return true;
        }

        private boolean onTransact$semSetAllowBluetoothMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetAllowBluetoothMode(componentName, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowBluetoothMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            int semGetAllowBluetoothMode = semGetAllowBluetoothMode(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeInt(semGetAllowBluetoothMode);
            return true;
        }

        private boolean onTransact$semSetAllowDesktopSync$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowDesktopSync(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowDesktopSync$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowDesktopSync = semGetAllowDesktopSync(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowDesktopSync);
            return true;
        }

        private boolean onTransact$semSetAllowIrda$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowIrda(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowIrda$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean semGetAllowIrda = semGetAllowIrda(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetAllowIrda);
            return true;
        }

        private boolean onTransact$semSetRequireStorageCardEncryption$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetRequireStorageCardEncryption(componentName, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetRequireStorageCardEncryption$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean semGetRequireStorageCardEncryption = semGetRequireStorageCardEncryption(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(semGetRequireStorageCardEncryption);
            return true;
        }

        private boolean onTransact$semSetChangeNotificationEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetChangeNotificationEnabled(componentName, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationExemptions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            int[] createIntArray = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            setApplicationExemptions(readString, readString2, createIntArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMtePolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setMtePolicy(readInt, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMtePolicyBySystem$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setMtePolicyBySystem(readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCrossProfileAppToIgnored$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setCrossProfileAppToIgnored(readInt, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getSamsungSDcardEncryptionStatus$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean samsungSDcardEncryptionStatus = getSamsungSDcardEncryptionStatus(componentName, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(samsungSDcardEncryptionStatus);
            return true;
        }

        private boolean onTransact$setContentProtectionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setContentProtectionPolicy(componentName, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getContentProtectionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            int contentProtectionPolicy = getContentProtectionPolicy(componentName, readString, readInt);
            parcel2.writeNoException();
            parcel2.writeInt(contentProtectionPolicy);
            return true;
        }

        private boolean onTransact$setMaxPolicyStorageLimit$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setMaxPolicyStorageLimit(readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$forceSetMaxPolicyStorageLimit$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            forceSetMaxPolicyStorageLimit(readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPolicySizeForAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            EnforcingAdmin enforcingAdmin = (EnforcingAdmin) parcel.readTypedObject(EnforcingAdmin.CREATOR);
            parcel.enforceNoDataAvail();
            int policySizeForAdmin = getPolicySizeForAdmin(readString, enforcingAdmin);
            parcel2.writeNoException();
            parcel2.writeInt(policySizeForAdmin);
            return true;
        }

        private boolean onTransact$setAppFunctionsPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setAppFunctionsPolicy(readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getAppFunctionsPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            int appFunctionsPolicy = getAppFunctionsPolicy(readString, readInt);
            parcel2.writeNoException();
            parcel2.writeInt(appFunctionsPolicy);
            return true;
        }
    }
}
