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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDevicePolicyManager)) {
                return (IDevicePolicyManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    PasswordMetrics passwordMinimumMetrics = getPasswordMinimumMetrics(i3, z);
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
                    boolean zIsActivePasswordSufficientForDeviceRequirement = isActivePasswordSufficientForDeviceRequirement();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsActivePasswordSufficientForDeviceRequirement);
                    return true;
                case 25:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPasswordSufficientAfterProfileUnification = isPasswordSufficientAfterProfileUnification(i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPasswordSufficientAfterProfileUnification);
                    return true;
                case 26:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int passwordComplexity = getPasswordComplexity(z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordComplexity);
                    return true;
                case 27:
                    return onTransact$setRequiredPasswordComplexity$(parcel, parcel2);
                case 28:
                    String string = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int requiredPasswordComplexity = getRequiredPasswordComplexity(string, z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(requiredPasswordComplexity);
                    return true;
                case 29:
                    int i6 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int aggregatedPasswordComplexityForUser = getAggregatedPasswordComplexityForUser(i6, z4);
                    parcel2.writeNoException();
                    parcel2.writeInt(aggregatedPasswordComplexityForUser);
                    return true;
                case 30:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUsingUnifiedPassword = isUsingUnifiedPassword(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsingUnifiedPassword);
                    return true;
                case 31:
                    return onTransact$getCurrentFailedPasswordAttempts$(parcel, parcel2);
                case 32:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentFailedBiometricAttempts = getCurrentFailedBiometricAttempts(i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentFailedBiometricAttempts);
                    return true;
                case 33:
                    int i8 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int profileWithMinimumFailedPasswordsForWipe = getProfileWithMinimumFailedPasswordsForWipe(i8, z5);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileWithMinimumFailedPasswordsForWipe);
                    return true;
                case 34:
                    return onTransact$setMaximumFailedPasswordsForWipe$(parcel, parcel2);
                case 35:
                    return onTransact$getMaximumFailedPasswordsForWipe$(parcel, parcel2);
                case 36:
                    String string2 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zResetPassword = resetPassword(string2, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetPassword);
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
                    boolean zIsFactoryResetProtectionPolicySupported = isFactoryResetProtectionPolicySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFactoryResetProtectionPolicySupported);
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
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName globalProxyAdmin = getGlobalProxyAdmin(i10);
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
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int storageEncryption = setStorageEncryption(componentName4, z6);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageEncryption);
                    return true;
                case 51:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean storageEncryption2 = getStorageEncryption(componentName5, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(storageEncryption2);
                    return true;
                case 52:
                    String string3 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int storageEncryptionStatus = getStorageEncryptionStatus(string3, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageEncryptionStatus);
                    return true;
                case 53:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestBugreport = requestBugreport(componentName6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestBugreport);
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
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNearbyNotificationStreamingPolicy(i13);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nearbyNotificationStreamingPolicy = getNearbyNotificationStreamingPolicy(i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(nearbyNotificationStreamingPolicy);
                    return true;
                case 60:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNearbyAppStreamingPolicy(i15);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nearbyAppStreamingPolicy = getNearbyAppStreamingPolicy(i16);
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
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAdminActive = isAdminActive(componentName7, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdminActive);
                    return true;
                case 66:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> activeAdmins = getActiveAdmins(i18);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeAdmins, 1);
                    return true;
                case 67:
                    String string4 = parcel.readString();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zPackageHasActiveAdmins = packageHasActiveAdmins(string4, i19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPackageHasActiveAdmins);
                    return true;
                case 68:
                    return onTransact$getRemoveWarning$(parcel, parcel2);
                case 69:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeActiveAdmin(componentName8, i20);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRemoveActiveAdmin(componentName9, i21);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    return onTransact$hasGrantedPolicy$(parcel, parcel2);
                case 72:
                    PasswordMetrics passwordMetrics = (PasswordMetrics) parcel.readTypedObject(PasswordMetrics.CREATOR);
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPasswordChanged(passwordMetrics, i22);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int i23 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportFailedPasswordAttempt(i23, z7);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulPasswordAttempt(i24);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportFailedBiometricAttempt(i25);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulBiometricAttempt(i26);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportKeyguardDismissed(i27);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportKeyguardSecured(i28);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    return onTransact$setDeviceOwner$(parcel, parcel2);
                case 80:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ComponentName deviceOwnerComponent = getDeviceOwnerComponent(z8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceOwnerComponent, 1);
                    return true;
                case 81:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName deviceOwnerComponentOnUser = getDeviceOwnerComponentOnUser(i29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceOwnerComponentOnUser, 1);
                    return true;
                case 82:
                    boolean zHasDeviceOwner = hasDeviceOwner();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDeviceOwner);
                    return true;
                case 83:
                    String deviceOwnerName = getDeviceOwnerName();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceOwnerName);
                    return true;
                case 84:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearDeviceOwner(string5);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    int deviceOwnerUserId = getDeviceOwnerUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceOwnerUserId);
                    return true;
                case 86:
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean profileOwner = setProfileOwner(componentName10, i30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileOwner);
                    return true;
                case 87:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName profileOwnerAsUser = getProfileOwnerAsUser(i31);
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
                    boolean zIsSupervisionComponent = isSupervisionComponent(componentName11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupervisionComponent);
                    return true;
                case 90:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileOwnerName = getProfileOwnerName(i32);
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
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProfileName(componentName13, string6);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    clearProfileOwner(componentName14);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    boolean zHasUserSetupCompleted = hasUserSetupCompleted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasUserSetupCompleted);
                    return true;
                case 95:
                    boolean zIsOrganizationOwnedDeviceWithManagedProfile = isOrganizationOwnedDeviceWithManagedProfile();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOrganizationOwnedDeviceWithManagedProfile);
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
                    List<String> listListPolicyExemptApps = listPolicyExemptApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listListPolicyExemptApps);
                    return true;
                case 102:
                    return onTransact$installCaCert$(parcel, parcel2);
                case 103:
                    return onTransact$uninstallCaCerts$(parcel, parcel2);
                case 104:
                    ComponentName componentName16 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceCanManageCaCerts(componentName16, string7);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    return onTransact$approveCaCert$(parcel, parcel2);
                case 106:
                    String string8 = parcel.readString();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCaCertApproved = isCaCertApproved(string8, i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCaCertApproved);
                    return true;
                case 107:
                    return onTransact$installKeyPair$(parcel, parcel2);
                case 108:
                    return onTransact$removeKeyPair$(parcel, parcel2);
                case 109:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasKeyPair = hasKeyPair(string9, string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasKeyPair);
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
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> delegatedScopes = getDelegatedScopes(componentName17, string11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatedScopes);
                    return true;
                case 115:
                    ComponentName componentName18 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> delegatePackages = getDelegatePackages(componentName18, string12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatePackages);
                    return true;
                case 116:
                    ComponentName componentName19 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCertInstallerPackage(componentName19, string13);
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
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String alwaysOnVpnPackageForUser = getAlwaysOnVpnPackageForUser(i34);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackageForUser);
                    return true;
                case 121:
                    ComponentName componentName22 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAlwaysOnVpnLockdownEnabled = isAlwaysOnVpnLockdownEnabled(componentName22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAlwaysOnVpnLockdownEnabled);
                    return true;
                case 122:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAlwaysOnVpnLockdownEnabledForUser = isAlwaysOnVpnLockdownEnabledForUser(i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAlwaysOnVpnLockdownEnabledForUser);
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
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDefaultDialerApplication(string14);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    return onTransact$setApplicationRestrictions$(parcel, parcel2);
                case 129:
                    return onTransact$getApplicationRestrictions$(parcel, parcel2);
                case 130:
                    ComponentName componentName24 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationRestrictionsManagingPackage = setApplicationRestrictionsManagingPackage(componentName24, string15);
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
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsCallerApplicationRestrictionsManagingPackage = isCallerApplicationRestrictionsManagingPackage(string16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallerApplicationRestrictionsManagingPackage);
                    return true;
                case 133:
                    ComponentName componentName26 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ComponentName componentName27 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRestrictionsProvider(componentName26, componentName27);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName restrictionsProvider = getRestrictionsProvider(i36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(restrictionsProvider, 1);
                    return true;
                case 135:
                    return onTransact$setUserRestriction$(parcel, parcel2);
                case 136:
                    return onTransact$setUserRestrictionForUser$(parcel, parcel2);
                case 137:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserRestrictionGlobally(string17, string18);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    return onTransact$setUserRestrictionGloballyFromSystem$(parcel, parcel2);
                case 139:
                    return onTransact$getUserRestrictions$(parcel, parcel2);
                case 140:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle userRestrictionsGlobally = getUserRestrictionsGlobally(string19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userRestrictionsGlobally, 1);
                    return true;
                case 141:
                    return onTransact$addCrossProfileIntentFilter$(parcel, parcel2);
                case 142:
                    ComponentName componentName28 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(componentName28, string20);
                    parcel2.writeNoException();
                    return true;
                case 143:
                    ComponentName componentName29 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean permittedAccessibilityServices = setPermittedAccessibilityServices(componentName29, arrayListCreateStringArrayList);
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
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> permittedAccessibilityServicesForUser = getPermittedAccessibilityServicesForUser(i37);
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
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> permittedInputMethodsAsUser = getPermittedInputMethodsAsUser(i38);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedInputMethodsAsUser);
                    return true;
                case 150:
                    return onTransact$isInputMethodPermittedByAdmin$(parcel, parcel2);
                case 151:
                    ComponentName componentName31 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean permittedCrossProfileNotificationListeners = setPermittedCrossProfileNotificationListeners(componentName31, arrayListCreateStringArrayList2);
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
                    String string21 = parcel.readString();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNotificationListenerServicePermitted = isNotificationListenerServicePermitted(string21, i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNotificationListenerServicePermitted);
                    return true;
                case 154:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent intentCreateAdminSupportIntent = createAdminSupportIntent(string22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentCreateAdminSupportIntent, 1);
                    return true;
                case 155:
                    int i40 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle enforcingAdminAndUserDetails = getEnforcingAdminAndUserDetails(i40, string23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcingAdminAndUserDetails, 1);
                    return true;
                case 156:
                    int i41 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnforcingAdmin enforcingAdmin = getEnforcingAdmin(i41, string24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcingAdmin, 1);
                    return true;
                case 157:
                    int i42 = parcel.readInt();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<EnforcingAdmin> enforcingAdminsForRestriction = getEnforcingAdminsForRestriction(i42, string25);
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
                    boolean zRemoveUser = removeUser(componentName33, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveUser);
                    return true;
                case 162:
                    ComponentName componentName34 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSwitchUser = switchUser(componentName34, userHandle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSwitchUser);
                    return true;
                case 163:
                    ComponentName componentName35 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartUserInBackground = startUserInBackground(componentName35, userHandle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartUserInBackground);
                    return true;
                case 164:
                    ComponentName componentName36 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStopUser = stopUser(componentName36, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopUser);
                    return true;
                case 165:
                    ComponentName componentName37 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iLogoutUser = logoutUser(componentName37);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLogoutUser);
                    return true;
                case 166:
                    int iLogoutUserInternal = logoutUserInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(iLogoutUserInternal);
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
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeNewUserDisclaimer(i43);
                    parcel2.writeNoException();
                    return true;
                case 170:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNewUserDisclaimerAcknowledged = isNewUserDisclaimerAcknowledged(i44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNewUserDisclaimerAcknowledged);
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
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] accountTypesWithManagementDisabled = getAccountTypesWithManagementDisabled(string26);
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
                    boolean zIsSecondaryLockscreenEnabled = isSecondaryLockscreenEnabled(userHandle6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSecondaryLockscreenEnabled);
                    return true;
                case 179:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(PreferentialNetworkServiceConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferentialNetworkServiceConfigs(arrayListCreateTypedArrayList);
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
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] lockTaskPackages = getLockTaskPackages(componentName39, string27);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(lockTaskPackages);
                    return true;
                case 183:
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsLockTaskPermitted = isLockTaskPermitted(string28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLockTaskPermitted);
                    return true;
                case 184:
                    return onTransact$setLockTaskFeatures$(parcel, parcel2);
                case 185:
                    ComponentName componentName40 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int lockTaskFeatures = getLockTaskFeatures(componentName40, string29);
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
                    boolean zHasLockdownAdminConfiguredNetworks = hasLockdownAdminConfiguredNetworks(componentName41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasLockdownAdminConfiguredNetworks);
                    return true;
                case 191:
                    ComponentName componentName42 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLocationEnabled(componentName42, z9);
                    parcel2.writeNoException();
                    return true;
                case 192:
                    return onTransact$setTime$(parcel, parcel2);
                case 193:
                    return onTransact$setTimeZone$(parcel, parcel2);
                case 194:
                    ComponentName componentName43 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterVolumeMuted(componentName43, z10);
                    parcel2.writeNoException();
                    return true;
                case 195:
                    ComponentName componentName44 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMasterVolumeMuted = isMasterVolumeMuted(componentName44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMasterVolumeMuted);
                    return true;
                case 196:
                    return onTransact$notifyLockTaskModeChanged$(parcel, parcel2);
                case 197:
                    return onTransact$setUninstallBlocked$(parcel, parcel2);
                case 198:
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUninstallBlocked = isUninstallBlocked(string30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUninstallBlocked);
                    return true;
                case 199:
                    ComponentName componentName45 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossProfileCallerIdDisabled(componentName45, z11);
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
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean crossProfileCallerIdDisabledForUser = getCrossProfileCallerIdDisabledForUser(i45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileCallerIdDisabledForUser);
                    return true;
                case 202:
                    ComponentName componentName47 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossProfileContactsSearchDisabled(componentName47, z12);
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
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean crossProfileContactsSearchDisabledForUser = getCrossProfileContactsSearchDisabledForUser(i46);
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
                    int i47 = parcel.readInt();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasManagedProfileCallerIdAccess = hasManagedProfileCallerIdAccess(i47, string31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasManagedProfileCallerIdAccess);
                    return true;
                case 209:
                    PackagePolicy packagePolicy2 = (PackagePolicy) parcel.readTypedObject(PackagePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCredentialManagerPolicy(packagePolicy2);
                    parcel2.writeNoException();
                    return true;
                case 210:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PackagePolicy credentialManagerPolicy = getCredentialManagerPolicy(i48);
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
                    int i49 = parcel.readInt();
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasManagedProfileContactsAccess = hasManagedProfileContactsAccess(i49, string32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasManagedProfileContactsAccess);
                    return true;
                case 214:
                    ComponentName componentName49 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothContactSharingDisabled(componentName49, z13);
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
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothContactSharingDisabledForUser = getBluetoothContactSharingDisabledForUser(i50);
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
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> crossProfileWidgetProviders = getCrossProfileWidgetProviders(componentName51, string33);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileWidgetProviders);
                    return true;
                case 222:
                    ComponentName componentName52 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoTimeRequired(componentName52, z14);
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
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean autoTimeEnabled = getAutoTimeEnabled(componentName53, string34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeEnabled);
                    return true;
                case 226:
                    String string35 = parcel.readString();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoTimePolicy(string35, i51);
                    parcel2.writeNoException();
                    return true;
                case 227:
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoTimePolicy = getAutoTimePolicy(string36);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoTimePolicy);
                    return true;
                case 228:
                    return onTransact$setAutoTimeZoneEnabled$(parcel, parcel2);
                case 229:
                    ComponentName componentName54 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean autoTimeZoneEnabled = getAutoTimeZoneEnabled(componentName54, string37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeZoneEnabled);
                    return true;
                case 230:
                    String string38 = parcel.readString();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoTimeZonePolicy(string38, i52);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoTimeZonePolicy = getAutoTimeZonePolicy(string39);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoTimeZonePolicy);
                    return true;
                case 232:
                    ComponentName componentName55 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setForceEphemeralUsers(componentName55, z15);
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
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRemovingAdmin = isRemovingAdmin(componentName57, i53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRemovingAdmin);
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
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean keyguardDisabled = setKeyguardDisabled(componentName59, z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyguardDisabled);
                    return true;
                case 240:
                    return onTransact$setStatusBarDisabled$(parcel, parcel2);
                case 241:
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsStatusBarDisabled = isStatusBarDisabled(string40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStatusBarDisabled);
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
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SystemUpdateInfo pendingSystemUpdate = getPendingSystemUpdate(componentName60, string41);
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
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProvisioningAllowed = isProvisioningAllowed(string42, string43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProvisioningAllowed);
                    return true;
                case 250:
                    String string44 = parcel.readString();
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckProvisioningPrecondition = checkProvisioningPrecondition(string44, string45);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckProvisioningPrecondition);
                    return true;
                case 251:
                    return onTransact$setKeepUninstalledPackages$(parcel, parcel2);
                case 252:
                    ComponentName componentName62 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> keepUninstalledPackages = getKeepUninstalledPackages(componentName62, string46);
                    parcel2.writeNoException();
                    parcel2.writeStringList(keepUninstalledPackages);
                    return true;
                case 253:
                    ComponentName componentName63 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsManagedProfile = isManagedProfile(componentName63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsManagedProfile);
                    return true;
                case 254:
                    ComponentName componentName64 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiMacAddress = getWifiMacAddress(componentName64, string47);
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
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence shortSupportMessage = getShortSupportMessage(componentName66, string48);
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
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence shortSupportMessageForUser = getShortSupportMessageForUser(componentName69, i54);
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
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence longSupportMessageForUser = getLongSupportMessageForUser(componentName70, i55);
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
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrganizationColor(componentName71, i56);
                    parcel2.writeNoException();
                    return true;
                case 263:
                    int i57 = parcel.readInt();
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrganizationColorForUser(i57, i58);
                    parcel2.writeNoException();
                    return true;
                case 264:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearOrganizationIdForUser(i59);
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
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int organizationColorForUser = getOrganizationColorForUser(i60);
                    parcel2.writeNoException();
                    parcel2.writeInt(organizationColorForUser);
                    return true;
                case 267:
                    return onTransact$setOrganizationName$(parcel, parcel2);
                case 268:
                    ComponentName componentName73 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence organizationName = getOrganizationName(componentName73, string49);
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
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence organizationNameForUser = getOrganizationNameForUser(i61);
                    parcel2.writeNoException();
                    if (organizationNameForUser != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(organizationNameForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 271:
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userProvisioningState = getUserProvisioningState(i62);
                    parcel2.writeNoException();
                    parcel2.writeInt(userProvisioningState);
                    return true;
                case 272:
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserProvisioningState(i63, i64);
                    parcel2.writeNoException();
                    return true;
                case 273:
                    ComponentName componentName74 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setAffiliationIds(componentName74, arrayListCreateStringArrayList3);
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
                    boolean zIsCallingUserAffiliated = isCallingUserAffiliated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallingUserAffiliated);
                    return true;
                case 276:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAffiliatedUser = isAffiliatedUser(i65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAffiliatedUser);
                    return true;
                case 277:
                    return onTransact$setSecurityLoggingEnabled$(parcel, parcel2);
                case 278:
                    ComponentName componentName76 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSecurityLoggingEnabled = isSecurityLoggingEnabled(componentName76, string50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSecurityLoggingEnabled);
                    return true;
                case 279:
                    ComponentName componentName77 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceRetrieveSecurityLogs = retrieveSecurityLogs(componentName77, string51);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceRetrieveSecurityLogs, 1);
                    return true;
                case 280:
                    ComponentName componentName78 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceRetrievePreRebootSecurityLogs = retrievePreRebootSecurityLogs(componentName78, string52);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceRetrievePreRebootSecurityLogs, 1);
                    return true;
                case 281:
                    long jForceNetworkLogs = forceNetworkLogs();
                    parcel2.writeNoException();
                    parcel2.writeLong(jForceNetworkLogs);
                    return true;
                case 282:
                    long jForceSecurityLogs = forceSecurityLogs();
                    parcel2.writeNoException();
                    parcel2.writeLong(jForceSecurityLogs);
                    return true;
                case 283:
                    String string53 = parcel.readString();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAuditLogEnabled(string53, z17);
                    parcel2.writeNoException();
                    return true;
                case 284:
                    String string54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAuditLogEnabled = isAuditLogEnabled(string54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAuditLogEnabled);
                    return true;
                case 285:
                    String string55 = parcel.readString();
                    IAuditLogEventsCallback iAuditLogEventsCallbackAsInterface = IAuditLogEventsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setAuditLogEventsCallback(string55, iAuditLogEventsCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 286:
                    String string56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUninstallInQueue = isUninstallInQueue(string56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUninstallInQueue);
                    return true;
                case 287:
                    String string57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    uninstallPackageWithActiveAdmins(string57);
                    parcel2.writeNoException();
                    return true;
                case 288:
                    boolean zIsDeviceProvisioned = isDeviceProvisioned();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceProvisioned);
                    return true;
                case 289:
                    boolean zIsDeviceProvisioningConfigApplied = isDeviceProvisioningConfigApplied();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceProvisioningConfigApplied);
                    return true;
                case 290:
                    setDeviceProvisioningConfigApplied();
                    parcel2.writeNoException();
                    return true;
                case 291:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceUpdateUserSetupComplete(i66);
                    parcel2.writeNoException();
                    return true;
                case 292:
                    ComponentName componentName79 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupServiceEnabled(componentName79, z18);
                    parcel2.writeNoException();
                    return true;
                case 293:
                    ComponentName componentName80 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBackupServiceEnabled = isBackupServiceEnabled(componentName80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackupServiceEnabled);
                    return true;
                case 294:
                    return onTransact$setNetworkLoggingEnabled$(parcel, parcel2);
                case 295:
                    ComponentName componentName81 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsNetworkLoggingEnabled = isNetworkLoggingEnabled(componentName81, string58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNetworkLoggingEnabled);
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
                    boolean zIsEphemeralUser = isEphemeralUser(componentName83);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEphemeralUser);
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
                    String string59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearResetPasswordToken = clearResetPasswordToken(componentName84, string59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearResetPasswordToken);
                    return true;
                case 305:
                    ComponentName componentName85 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsResetPasswordTokenActive = isResetPasswordTokenActive(componentName85, string60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsResetPasswordTokenActive);
                    return true;
                case 306:
                    return onTransact$resetPasswordWithToken$(parcel, parcel2);
                case 307:
                    boolean zIsCurrentInputMethodSetByOwner = isCurrentInputMethodSetByOwner();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCurrentInputMethodSetByOwner);
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
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLogoutEnabled(componentName86, z19);
                    parcel2.writeNoException();
                    return true;
                case 311:
                    boolean zIsLogoutEnabled = isLogoutEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLogoutEnabled);
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
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<String> meteredDataDisabledPackages = setMeteredDataDisabledPackages(componentName91, arrayListCreateStringArrayList4);
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
                    int iAddOverrideApn = addOverrideApn(componentName93, apnSetting);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddOverrideApn);
                    return true;
                case 322:
                    return onTransact$updateOverrideApn$(parcel, parcel2);
                case 323:
                    ComponentName componentName94 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveOverrideApn = removeOverrideApn(componentName94, i67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveOverrideApn);
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
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOverrideApnsEnabled(componentName96, z20);
                    parcel2.writeNoException();
                    return true;
                case 326:
                    ComponentName componentName97 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsOverrideApnEnabled = isOverrideApnEnabled(componentName97);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOverrideApnEnabled);
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
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCrossProfileCalendarPackages(componentName100, arrayListCreateStringArrayList5);
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
                    String string61 = parcel.readString();
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageAllowedToAccessCalendarForUser = isPackageAllowedToAccessCalendarForUser(string61, i68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageAllowedToAccessCalendarForUser);
                    return true;
                case 337:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> crossProfileCalendarPackagesForUser = getCrossProfileCalendarPackagesForUser(i69);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileCalendarPackagesForUser);
                    return true;
                case 338:
                    ComponentName componentName102 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCrossProfilePackages(componentName102, arrayListCreateStringArrayList6);
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
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> allCrossProfilePackages = getAllCrossProfilePackages(i70);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allCrossProfilePackages);
                    return true;
                case 341:
                    List<String> defaultCrossProfilePackages = getDefaultCrossProfilePackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(defaultCrossProfilePackages);
                    return true;
                case 342:
                    boolean zIsManagedKiosk = isManagedKiosk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsManagedKiosk);
                    return true;
                case 343:
                    boolean zIsUnattendedManagedKiosk = isUnattendedManagedKiosk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUnattendedManagedKiosk);
                    return true;
                case 344:
                    return onTransact$startViewCalendarEventInManagedProfile$(parcel, parcel2);
                case 345:
                    return onTransact$setKeyGrantForApp$(parcel, parcel2);
                case 346:
                    String string62 = parcel.readString();
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelableGranteeMap keyPairGrants = getKeyPairGrants(string62, string63);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyPairGrants, 1);
                    return true;
                case 347:
                    return onTransact$setKeyGrantToWifiAuth$(parcel, parcel2);
                case 348:
                    String string64 = parcel.readString();
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsKeyPairGrantedToWifiAuth = isKeyPairGrantedToWifiAuth(string64, string65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKeyPairGrantedToWifiAuth);
                    return true;
                case 349:
                    return onTransact$setUserControlDisabledPackages$(parcel, parcel2);
                case 350:
                    ComponentName componentName104 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> userControlDisabledPackages = getUserControlDisabledPackages(componentName104, string66);
                    parcel2.writeNoException();
                    parcel2.writeStringList(userControlDisabledPackages);
                    return true;
                case 351:
                    return onTransact$setCommonCriteriaModeEnabled$(parcel, parcel2);
                case 352:
                    ComponentName componentName105 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCommonCriteriaModeEnabled = isCommonCriteriaModeEnabled(componentName105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCommonCriteriaModeEnabled);
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
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPersonalAppsSuspended(componentName107, z21);
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
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setManagedProfileMaximumTimeOff(componentName109, j);
                    parcel2.writeNoException();
                    return true;
                case 357:
                    acknowledgeDeviceCompliant();
                    parcel2.writeNoException();
                    return true;
                case 358:
                    boolean zIsComplianceAcknowledgementRequired = isComplianceAcknowledgementRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsComplianceAcknowledgementRequired);
                    return true;
                case 359:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanProfileOwnerResetPasswordWhenLocked = canProfileOwnerResetPasswordWhenLocked(i71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanProfileOwnerResetPasswordWhenLocked);
                    return true;
                case 360:
                    int i72 = parcel.readInt();
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNextOperationSafety(i72, i73);
                    parcel2.writeNoException();
                    return true;
                case 361:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSafeOperation = isSafeOperation(i74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSafeOperation);
                    return true;
                case 362:
                    String string67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String enrollmentSpecificId = getEnrollmentSpecificId(string67);
                    parcel2.writeNoException();
                    parcel2.writeString(enrollmentSpecificId);
                    return true;
                case 363:
                    return onTransact$setOrganizationIdForUser$(parcel, parcel2);
                case 364:
                    ManagedProfileProvisioningParams managedProfileProvisioningParams = (ManagedProfileProvisioningParams) parcel.readTypedObject(ManagedProfileProvisioningParams.CREATOR);
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UserHandle userHandleCreateAndProvisionManagedProfile = createAndProvisionManagedProfile(managedProfileProvisioningParams, string68);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userHandleCreateAndProvisionManagedProfile, 1);
                    return true;
                case 365:
                    ManagedProfileProvisioningParams managedProfileProvisioningParams2 = (ManagedProfileProvisioningParams) parcel.readTypedObject(ManagedProfileProvisioningParams.CREATOR);
                    String string69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UserHandle userHandleCreateManagedProfile = createManagedProfile(managedProfileProvisioningParams2, string69);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userHandleCreateManagedProfile, 1);
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
                    String string70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    provisionFullyManagedDevice(fullyManagedDeviceProvisioningParams, string70);
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
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveManagedProfile = removeManagedProfile(i75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveManagedProfile);
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
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetDefaultCrossProfileIntentFilters(i76);
                    parcel2.writeNoException();
                    return true;
                case 373:
                    boolean zCanAdminGrantSensorsPermissions = canAdminGrantSensorsPermissions();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanAdminGrantSensorsPermissions);
                    return true;
                case 374:
                    return onTransact$setUsbDataSignalingEnabled$(parcel, parcel2);
                case 375:
                    String string71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbDataSignalingEnabled = isUsbDataSignalingEnabled(string71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbDataSignalingEnabled);
                    return true;
                case 376:
                    boolean zCanUsbDataSignalingBeDisabled = canUsbDataSignalingBeDisabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanUsbDataSignalingBeDisabled);
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
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    WifiSsidPolicy wifiSsidPolicy = getWifiSsidPolicy(string72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiSsidPolicy, 1);
                    return true;
                case 381:
                    String string73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsDevicePotentiallyStolen = isDevicePotentiallyStolen(string73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDevicePotentiallyStolen);
                    return true;
                case 382:
                    List<UserHandle> listListForegroundAffiliatedUsers = listForegroundAffiliatedUsers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listListForegroundAffiliatedUsers, 1);
                    return true;
                case 383:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(DevicePolicyDrawableResource.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDrawables(arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 384:
                    ArrayList<String> arrayListCreateStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetDrawables(arrayListCreateStringArrayList7);
                    parcel2.writeNoException();
                    return true;
                case 385:
                    return onTransact$getDrawable$(parcel, parcel2);
                case 386:
                    boolean zIsDpcDownloaded = isDpcDownloaded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDpcDownloaded);
                    return true;
                case 387:
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDpcDownloaded(z22);
                    parcel2.writeNoException();
                    return true;
                case 388:
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(DevicePolicyStringResource.CREATOR);
                    parcel.enforceNoDataAvail();
                    setStrings(arrayListCreateTypedArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 389:
                    ArrayList<String> arrayListCreateStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetStrings(arrayListCreateStringArrayList8);
                    parcel2.writeNoException();
                    return true;
                case 390:
                    String string74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelableResource string75 = getString(string74);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(string75, 1);
                    return true;
                case 391:
                    resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState();
                    parcel2.writeNoException();
                    return true;
                case 392:
                    boolean zShouldAllowBypassingDevicePolicyManagementRoleQualification = shouldAllowBypassingDevicePolicyManagementRoleQualification();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldAllowBypassingDevicePolicyManagementRoleQualification);
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
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemIsActivePasswordSufficient = semIsActivePasswordSufficient(i77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsActivePasswordSufficient);
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
                    String string76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] applicationExemptions = getApplicationExemptions(string76);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(applicationExemptions);
                    return true;
                case 428:
                    return onTransact$setMtePolicy$(parcel, parcel2);
                case 429:
                    return onTransact$setMtePolicyBySystem$(parcel, parcel2);
                case 430:
                    String string77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mtePolicy = getMtePolicy(string77);
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
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zTriggerDevicePolicyEngineMigration = triggerDevicePolicyEngineMigration(z23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTriggerDevicePolicyEngineMigration);
                    return true;
                case 435:
                    String string78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsDeviceFinanced = isDeviceFinanced(string78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceFinanced);
                    return true;
                case 436:
                    String string79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String financedDeviceKioskRoleHolder = getFinancedDeviceKioskRoleHolder(string79);
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
                    String string80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] subscriptionIds = getSubscriptionIds(string80);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(subscriptionIds);
                    return true;
                case 443:
                    return onTransact$setMaxPolicyStorageLimit$(parcel, parcel2);
                case 444:
                    return onTransact$forceSetMaxPolicyStorageLimit$(parcel, parcel2);
                case 445:
                    String string81 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int maxPolicyStorageLimit = getMaxPolicyStorageLimit(string81);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxPolicyStorageLimit);
                    return true;
                case 446:
                    return onTransact$getPolicySizeForAdmin$(parcel, parcel2);
                case 447:
                    String string82 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int headlessDeviceOwnerMode = getHeadlessDeviceOwnerMode(string82);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PasswordMetrics) parcelObtain2.readTypedObject(PasswordMetrics.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordExpirationTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpirationTimeout(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpiration(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficient(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficientForDeviceRequirement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordComplexity(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredPasswordComplexity(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getRequiredPasswordComplexity(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAggregatedPasswordComplexityForUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsingUnifiedPassword(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getCurrentFailedPasswordAttempts(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getCurrentFailedBiometricAttempts(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumFailedPasswordsForWipe(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaximumFailedPasswordsForWipe(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPassword(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumTimeToLock(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getMaximumTimeToLock(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredStrongAuthTimeout(ComponentName componentName, String str, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getRequiredStrongAuthTimeout(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void lockNow(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void wipeDataWithReason(String str, int i, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setFactoryResetProtectionPolicy(ComponentName componentName, String str, FactoryResetProtectionPolicy factoryResetProtectionPolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(factoryResetProtectionPolicy, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FactoryResetProtectionPolicy) parcelObtain2.readTypedObject(FactoryResetProtectionPolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isFactoryResetProtectionPolicySupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void sendLostModeLocationUpdate(AndroidFuture<Boolean> androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName setGlobalProxy(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getGlobalProxyAdmin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setStorageEncryption(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getStorageEncryption(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getStorageEncryptionStatus(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean requestBugreport(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCameraDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCameraDisabled(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setScreenCaptureDisabled(ComponentName componentName, String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getScreenCaptureDisabled(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyNotificationStreamingPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyNotificationStreamingPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyAppStreamingPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyAppStreamingPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeyguardDisabledFeatures(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getKeyguardDisabledFeatures(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setActiveAdmin(ComponentName componentName, boolean z, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAdminActive(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<ComponentName> getActiveAdmins(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean packageHasActiveAdmins(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void removeActiveAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceRemoveActiveAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasGrantedPolicy(ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportPasswordChanged(PasswordMetrics passwordMetrics, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(passwordMetrics, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedPasswordAttempt(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulPasswordAttempt(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedBiometricAttempt(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulBiometricAttempt(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardDismissed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardSecured(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setDeviceOwner(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getDeviceOwnerComponent(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getDeviceOwnerComponentOnUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasDeviceOwner() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getDeviceOwnerName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearDeviceOwner(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerUserId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setProfileOwner(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getProfileOwnerAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSupervisionComponent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getProfileOwnerName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileEnabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileName(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearProfileOwner(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasUserSetupCompleted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOrganizationOwnedDeviceWithManagedProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean checkDeviceIdentifierAccess(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] setPackagesSuspended(ComponentName componentName, String str, String[] strArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageSuspended(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> listPolicyExemptApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installCaCert(ComponentName componentName, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallCaCerts(ComponentName componentName, String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enforceCanManageCaCerts(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean approveCaCert(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCaCertApproved(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installKeyPair(ComponentName componentName, String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeKeyPair(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasKeyPair(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean generateKeyPair(ComponentName componentName, String str, String str2, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(parcelableKeyGenParameterSpec, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    if (parcelObtain2.readInt() != 0) {
                        keymasterCertificateChain.readFromParcel(parcelObtain2);
                    }
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyPairCertificate(ComponentName componentName, String str, String str2, byte[] bArr, byte[] bArr2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void choosePrivateKeyAlias(int i, Uri uri, String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDelegatedScopes(ComponentName componentName, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDelegatedScopes(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDelegatePackages(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCertInstallerPackage(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getCertInstallerPackage(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setAlwaysOnVpnPackage(ComponentName componentName, String str, boolean z, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getAlwaysOnVpnPackage(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getAlwaysOnVpnPackageForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws RemoteException {
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

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addPersistentPreferredActivity(ComponentName componentName, String str, IntentFilter intentFilter, ComponentName componentName2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(componentName2, 0);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearPackagePersistentPreferredActivities(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultSmsApplication(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultDialerApplication(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationRestrictions(ComponentName componentName, String str, String str2, Bundle bundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getApplicationRestrictions(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getApplicationRestrictionsManagingPackage(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallerApplicationRestrictionsManagingPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(componentName2, 0);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getRestrictionsProvider(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestriction(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionForUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionGlobally(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionGloballyFromSystem(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getUserRestrictions(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getUserRestrictionsGlobally(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addCrossProfileIntentFilter(ComponentName componentName, String str, IntentFilter intentFilter, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearCrossProfileIntentFilters(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedAccessibilityServices(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedAccessibilityServices(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedAccessibilityServicesForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedInputMethods(ComponentName componentName, String str, List<String> list, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedInputMethods(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedInputMethodsAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isInputMethodPermittedByAdmin(ComponentName componentName, String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNotificationListenerServicePermitted(String str, int i) throws RemoteException {
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

            @Override // android.app.admin.IDevicePolicyManager
            public Intent createAdminSupportIntent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getEnforcingAdminAndUserDetails(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public EnforcingAdmin getEnforcingAdmin(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnforcingAdmin) parcelObtain2.readTypedObject(EnforcingAdmin.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<EnforcingAdmin> getEnforcingAdminsForRestriction(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(EnforcingAdmin.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationHidden(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isApplicationHidden(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createAndManageUser(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName2, 0);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserHandle) parcelObtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int startUserInBackground(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUser(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUserInternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLogoutUserId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getSecondaryUsers(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeNewUserDisclaimer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNewUserDisclaimerAcknowledged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enableSystemApp(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int enableSystemAppWithIntent(ComponentName componentName, String str, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installExistingPackage(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAccountManagementDisabled(ComponentName componentName, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getAccountTypesWithManagementDisabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getAccountTypesWithManagementDisabledAsUser(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecondaryLockscreenEnabled(ComponentName componentName, boolean z, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecondaryLockscreenEnabled(UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPreferentialNetworkServiceConfigs(List<PreferentialNetworkServiceConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PreferentialNetworkServiceConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskPackages(ComponentName componentName, String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getLockTaskPackages(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLockTaskPermitted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskFeatures(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLockTaskFeatures(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setGlobalSetting(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemSetting(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecureSetting(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setConfiguredNetworksLockdownState(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasLockdownAdminConfiguredNetworks(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLocationEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTime(ComponentName componentName, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTimeZone(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMasterVolumeMuted(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyLockTaskModeChanged(boolean z, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUninstallBlocked(ComponentName componentName, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallBlocked(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabledForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabledForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void startManagedQuickContact(String str, long j, boolean z, long j2, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileCallerIdAccessPolicy(PackagePolicy packagePolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getManagedProfileCallerIdAccessPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackagePolicy) parcelObtain2.readTypedObject(PackagePolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileCallerIdAccess(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCredentialManagerPolicy(PackagePolicy packagePolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getCredentialManagerPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackagePolicy) parcelObtain2.readTypedObject(PackagePolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileContactsAccessPolicy(PackagePolicy packagePolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getManagedProfileContactsAccessPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PackagePolicy) parcelObtain2.readTypedObject(PackagePolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileContactsAccess(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBluetoothContactSharingDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabledForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setTrustAgentConfiguration(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName2, 0);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(componentName2, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean addCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(219, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeCrossProfileWidgetProvider(ComponentName componentName, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(220, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileWidgetProviders(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(221, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeRequired(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(222, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeRequired() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(223, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(224, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(225, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimePolicy(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(226, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAutoTimePolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(227, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeZoneEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(228, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeZoneEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(229, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeZonePolicy(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAutoTimeZonePolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(231, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setForceEphemeralUsers(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(232, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(233, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isRemovingAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(234, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(235, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemUpdatePolicy(ComponentName componentName, String str, SystemUpdatePolicy systemUpdatePolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(systemUpdatePolicy, 0);
                    this.mRemote.transact(236, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(237, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SystemUpdatePolicy) parcelObtain2.readTypedObject(SystemUpdatePolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(238, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyguardDisabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(239, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setStatusBarDisabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(240, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isStatusBarDisabled(String str) throws RemoteException {
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

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(242, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyPendingSystemUpdate(SystemUpdateInfo systemUpdateInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(systemUpdateInfo, 0);
                    this.mRemote.transact(243, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(244, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SystemUpdateInfo) parcelObtain2.readTypedObject(SystemUpdateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(245, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionPolicy(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(246, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionGrantState(ComponentName componentName, String str, String str2, String str3, int i, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(247, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionGrantState(ComponentName componentName, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(248, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isProvisioningAllowed(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(249, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int checkProvisioningPrecondition(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(250, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeepUninstalledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(251, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getKeepUninstalledPackages(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(252, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedProfile(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(253, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getWifiMacAddress(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(254, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reboot(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(255, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setShortSupportMessage(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(256, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getShortSupportMessage(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(257, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(258, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getLongSupportMessage(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(259, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getShortSupportMessageForUser(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(260, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getLongSupportMessageForUser(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(261, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColor(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(262, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColorForUser(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(263, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearOrganizationIdForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(264, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColor(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(265, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColorForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(266, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationName(ComponentName componentName, String str, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(267, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getOrganizationName(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(268, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(269, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getOrganizationNameForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(270, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getUserProvisioningState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(271, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserProvisioningState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(272, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(273, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAffiliationIds(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(274, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallingUserAffiliated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(275, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAffiliatedUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(276, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecurityLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(277, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecurityLoggingEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(278, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParceledListSlice retrieveSecurityLogs(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(279, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(280, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceNetworkLogs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(281, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceSecurityLogs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(282, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEnabled(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(283, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAuditLogEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(284, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEventsCallback(String str, IAuditLogEventsCallback iAuditLogEventsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iAuditLogEventsCallback);
                    this.mRemote.transact(285, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallInQueue(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(286, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallPackageWithActiveAdmins(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(287, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioned() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(288, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(289, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(290, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceUpdateUserSetupComplete(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(291, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBackupServiceEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(292, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isBackupServiceEnabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(293, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNetworkLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(294, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNetworkLoggingEnabled(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(295, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(296, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(NetworkEvent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(297, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(298, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isEphemeralUser(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(299, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastSecurityLogRetrievalTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(300, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastBugReportRequestTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(301, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastNetworkLogRetrievalTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(302, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setResetPasswordToken(ComponentName componentName, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(303, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean clearResetPasswordToken(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(304, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isResetPasswordTokenActive(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(305, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPasswordWithToken(ComponentName componentName, String str, String str2, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(306, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(307, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(308, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StringParceledListSlice) parcelObtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearApplicationUserData(ComponentName componentName, String str, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(309, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLogoutEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(310, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLogoutEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(311, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDisallowedSystemApps(ComponentName componentName, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(312, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(componentName2, 0);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(313, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(314, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PersistableBundle) parcelObtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(315, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(316, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getStartUserSessionMessage(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(317, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getEndUserSessionMessage(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(318, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(319, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getMeteredDataDisabledPackages(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(320, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(321, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean updateOverrideApn(ComponentName componentName, int i, ApnSetting apnSetting) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(322, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeOverrideApn(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(323, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<ApnSetting> getOverrideApns(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(324, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ApnSetting.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOverrideApnsEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(325, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(326, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(327, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedPasswordAttemptWithFailureCount(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(328, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setGlobalPrivateDns(ComponentName componentName, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(329, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(330, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getGlobalPrivateDnsHost(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(331, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileOwnerOnOrganizationOwnedDevice(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(332, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void installUpdateFromFile(ComponentName componentName, String str, ParcelFileDescriptor parcelFileDescriptor, StartInstallingUpdateCallback startInstallingUpdateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStrongInterface(startInstallingUpdateCallback);
                    this.mRemote.transact(333, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(334, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileCalendarPackages(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(335, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageAllowedToAccessCalendarForUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(336, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileCalendarPackagesForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(337, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfilePackages(ComponentName componentName, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(338, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfilePackages(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(339, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAllCrossProfilePackages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(340, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDefaultCrossProfilePackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(341, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedKiosk() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(342, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUnattendedManagedKiosk() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(343, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean startViewCalendarEventInManagedProfile(String str, long j, long j2, long j3, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeLong(j3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(344, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantForApp(ComponentName componentName, String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(345, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableGranteeMap getKeyPairGrants(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(346, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelableGranteeMap) parcelObtain2.readTypedObject(ParcelableGranteeMap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantToWifiAuth(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(347, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isKeyPairGrantedToWifiAuth(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(348, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserControlDisabledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(349, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getUserControlDisabledPackages(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(350, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCommonCriteriaModeEnabled(ComponentName componentName, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(351, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCommonCriteriaModeEnabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(352, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPersonalAppsSuspendedReasons(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(353, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPersonalAppsSuspended(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(354, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getManagedProfileMaximumTimeOff(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(355, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileMaximumTimeOff(ComponentName componentName, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(356, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeDeviceCompliant() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(357, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isComplianceAcknowledgementRequired() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(358, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canProfileOwnerResetPasswordWhenLocked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(359, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNextOperationSafety(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(360, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSafeOperation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(361, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getEnrollmentSpecificId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(362, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationIdForUser(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(363, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createAndProvisionManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(managedProfileProvisioningParams, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(364, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserHandle) parcelObtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(managedProfileProvisioningParams, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(365, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserHandle) parcelObtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void finalizeCreateManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(managedProfileProvisioningParams, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(366, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void provisionFullyManagedDevice(FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fullyManagedDeviceProvisioningParams, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(367, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void finalizeWorkProfileProvisioning(UserHandle userHandle, Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(368, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeManagedProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(369, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerType(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(370, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerType(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(371, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDefaultCrossProfileIntentFilters(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(372, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canAdminGrantSensorsPermissions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(373, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUsbDataSignalingEnabled(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(374, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsbDataSignalingEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(375, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canUsbDataSignalingBeDisabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(376, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMinimumRequiredWifiSecurityLevel(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(377, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMinimumRequiredWifiSecurityLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(378, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setWifiSsidPolicy(String str, WifiSsidPolicy wifiSsidPolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(wifiSsidPolicy, 0);
                    this.mRemote.transact(379, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public WifiSsidPolicy getWifiSsidPolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(380, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WifiSsidPolicy) parcelObtain2.readTypedObject(WifiSsidPolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDevicePotentiallyStolen(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(381, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> listForegroundAffiliatedUsers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(382, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDrawables(List<DevicePolicyDrawableResource> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(383, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDrawables(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(384, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableResource getDrawable(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(385, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelableResource) parcelObtain2.readTypedObject(ParcelableResource.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDpcDownloaded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(386, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDpcDownloaded(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(387, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStrings(List<DevicePolicyStringResource> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(388, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetStrings(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(389, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableResource getString(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(390, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelableResource) parcelObtain2.readTypedObject(ParcelableResource.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(391, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(392, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(393, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordQuality(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(394, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumLength(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(395, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumUpperCase(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(396, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumLowerCase(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(397, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumNonLetter(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(398, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordHistoryLength(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(399, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordExpirationTimeout(ComponentName componentName, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(400, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semIsActivePasswordSufficient(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(401, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetSimplePasswordEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(402, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semIsSimplePasswordEnabled(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(403, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetKeyguardDisabledFeatures(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(404, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowStorageCard(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(405, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowStorageCard(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(406, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowWifi(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(407, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowWifi(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(408, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowTextMessaging(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(409, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowTextMessaging(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(410, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowPopImapEmail(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(411, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowPopImapEmail(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(412, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowBrowser(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(413, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowBrowser(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(414, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowInternetSharing(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(415, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowInternetSharing(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(416, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(417, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int semGetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(418, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowDesktopSync(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(419, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowDesktopSync(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(420, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowIrda(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(421, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowIrda(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(422, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetRequireStorageCardEncryption(ComponentName componentName, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(423, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetRequireStorageCardEncryption(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(424, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetChangeNotificationEnabled(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(425, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationExemptions(String str, String str2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(426, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getApplicationExemptions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(427, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMtePolicy(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(428, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMtePolicyBySystem(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(429, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMtePolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(430, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy managedSubscriptionsPolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(managedSubscriptionsPolicy, 0);
                    this.mRemote.transact(431, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(432, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ManagedSubscriptionsPolicy) parcelObtain2.readTypedObject(ManagedSubscriptionsPolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public DevicePolicyState getDevicePolicyState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(433, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DevicePolicyState) parcelObtain2.readTypedObject(DevicePolicyState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean triggerDevicePolicyEngineMigration(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(434, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceFinanced(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(435, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getFinancedDeviceKioskRoleHolder(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(436, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileAppToIgnored(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(437, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getSamsungSDcardEncryptionStatus(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(438, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void calculateHasIncompatibleAccounts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(439, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(440, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getContentProtectionPolicy(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(441, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getSubscriptionIds(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(442, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaxPolicyStorageLimit(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(443, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceSetMaxPolicyStorageLimit(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(444, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaxPolicyStorageLimit(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(445, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPolicySizeForAdmin(String str, EnforcingAdmin enforcingAdmin) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(enforcingAdmin, 0);
                    this.mRemote.transact(446, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getHeadlessDeviceOwnerMode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(447, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAppFunctionsPolicy(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(448, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAppFunctionsPolicy(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(449, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        private boolean onTransact$setPasswordQuality$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordQuality(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordQuality$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordQuality = getPasswordQuality(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordQuality);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLength(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLength = getPasswordMinimumLength(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLength);
            return true;
        }

        private boolean onTransact$setPasswordMinimumUpperCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumUpperCase(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumUpperCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumUpperCase = getPasswordMinimumUpperCase(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumUpperCase);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLowerCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLowerCase(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLowerCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLowerCase = getPasswordMinimumLowerCase(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLowerCase);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLetters$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLetters(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLetters$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLetters = getPasswordMinimumLetters(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLetters);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNumeric$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumNumeric(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNumeric$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumNumeric = getPasswordMinimumNumeric(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumNumeric);
            return true;
        }

        private boolean onTransact$setPasswordMinimumSymbols$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumSymbols(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumSymbols$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumSymbols = getPasswordMinimumSymbols(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumSymbols);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNonLetter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumNonLetter(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNonLetter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumNonLetter = getPasswordMinimumNonLetter(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumNonLetter);
            return true;
        }

        private boolean onTransact$setPasswordHistoryLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordHistoryLength(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordHistoryLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordHistoryLength = getPasswordHistoryLength(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(passwordHistoryLength);
            return true;
        }

        private boolean onTransact$setPasswordExpirationTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            long j = parcel.readLong();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordExpirationTimeout(componentName, string, j, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordExpirationTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long passwordExpirationTimeout = getPasswordExpirationTimeout(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeLong(passwordExpirationTimeout);
            return true;
        }

        private boolean onTransact$getPasswordExpiration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long passwordExpiration = getPasswordExpiration(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeLong(passwordExpiration);
            return true;
        }

        private boolean onTransact$isActivePasswordSufficient$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zIsActivePasswordSufficient = isActivePasswordSufficient(string, i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsActivePasswordSufficient);
            return true;
        }

        private boolean onTransact$setRequiredPasswordComplexity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRequiredPasswordComplexity(string, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCurrentFailedPasswordAttempts$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int currentFailedPasswordAttempts = getCurrentFailedPasswordAttempts(string, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(currentFailedPasswordAttempts);
            return true;
        }

        private boolean onTransact$setMaximumFailedPasswordsForWipe$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMaximumFailedPasswordsForWipe(componentName, string, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumFailedPasswordsForWipe$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int maximumFailedPasswordsForWipe = getMaximumFailedPasswordsForWipe(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(maximumFailedPasswordsForWipe);
            return true;
        }

        private boolean onTransact$setMaximumTimeToLock$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            long j = parcel.readLong();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMaximumTimeToLock(componentName, string, j, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumTimeToLock$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long maximumTimeToLock = getMaximumTimeToLock(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeLong(maximumTimeToLock);
            return true;
        }

        private boolean onTransact$setRequiredStrongAuthTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            long j = parcel.readLong();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRequiredStrongAuthTimeout(componentName, string, j, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRequiredStrongAuthTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long requiredStrongAuthTimeout = getRequiredStrongAuthTimeout(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeLong(requiredStrongAuthTimeout);
            return true;
        }

        private boolean onTransact$lockNow$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            lockNow(i, string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$wipeDataWithReason$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            wipeDataWithReason(string, i, string2, z, z2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setFactoryResetProtectionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            FactoryResetProtectionPolicy factoryResetProtectionPolicy = (FactoryResetProtectionPolicy) parcel.readTypedObject(FactoryResetProtectionPolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setFactoryResetProtectionPolicy(componentName, string, factoryResetProtectionPolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalProxy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            ComponentName globalProxy = setGlobalProxy(componentName, string, string2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(globalProxy, 1);
            return true;
        }

        private boolean onTransact$setCameraDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setCameraDisabled(componentName, string, z, z2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCameraDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean cameraDisabled = getCameraDisabled(componentName, string, i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(cameraDisabled);
            return true;
        }

        private boolean onTransact$setScreenCaptureDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setScreenCaptureDisabled(componentName, string, z, z2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getScreenCaptureDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean screenCaptureDisabled = getScreenCaptureDisabled(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(screenCaptureDisabled);
            return true;
        }

        private boolean onTransact$setKeyguardDisabledFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setKeyguardDisabledFeatures(componentName, string, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getKeyguardDisabledFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int keyguardDisabledFeatures = getKeyguardDisabledFeatures(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeInt(keyguardDisabledFeatures);
            return true;
        }

        private boolean onTransact$setActiveAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setActiveAdmin(componentName, z, i, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRemoveWarning$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            getRemoveWarning(componentName, remoteCallback, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$hasGrantedPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zHasGrantedPolicy = hasGrantedPolicy(componentName, i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zHasGrantedPolicy);
            return true;
        }

        private boolean onTransact$setDeviceOwner$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean deviceOwner = setDeviceOwner(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(deviceOwner);
            return true;
        }

        private boolean onTransact$checkDeviceIdentifierAccess$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zCheckDeviceIdentifierAccess = checkDeviceIdentifierAccess(string, i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zCheckDeviceIdentifierAccess);
            return true;
        }

        private boolean onTransact$setPackagesSuspended$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String[] strArrCreateStringArray = parcel.createStringArray();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            String[] packagesSuspended = setPackagesSuspended(componentName, string, strArrCreateStringArray, z);
            parcel2.writeNoException();
            parcel2.writeStringArray(packagesSuspended);
            return true;
        }

        private boolean onTransact$isPackageSuspended$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zIsPackageSuspended = isPackageSuspended(componentName, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsPackageSuspended);
            return true;
        }

        private boolean onTransact$installCaCert$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            boolean zInstallCaCert = installCaCert(componentName, string, bArrCreateByteArray);
            parcel2.writeNoException();
            parcel2.writeBoolean(zInstallCaCert);
            return true;
        }

        private boolean onTransact$uninstallCaCerts$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String[] strArrCreateStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            uninstallCaCerts(componentName, string, strArrCreateStringArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$approveCaCert$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zApproveCaCert = approveCaCert(string, i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zApproveCaCert);
            return true;
        }

        private boolean onTransact$installKeyPair$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            byte[] bArrCreateByteArray2 = parcel.createByteArray();
            byte[] bArrCreateByteArray3 = parcel.createByteArray();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zInstallKeyPair = installKeyPair(componentName, string, bArrCreateByteArray, bArrCreateByteArray2, bArrCreateByteArray3, string2, z, z2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zInstallKeyPair);
            return true;
        }

        private boolean onTransact$removeKeyPair$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zRemoveKeyPair = removeKeyPair(componentName, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zRemoveKeyPair);
            return true;
        }

        private boolean onTransact$generateKeyPair$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = (ParcelableKeyGenParameterSpec) parcel.readTypedObject(ParcelableKeyGenParameterSpec.CREATOR);
            int i = parcel.readInt();
            KeymasterCertificateChain keymasterCertificateChain = new KeymasterCertificateChain();
            parcel.enforceNoDataAvail();
            boolean zGenerateKeyPair = generateKeyPair(componentName, string, string2, parcelableKeyGenParameterSpec, i, keymasterCertificateChain);
            parcel2.writeNoException();
            parcel2.writeBoolean(zGenerateKeyPair);
            parcel2.writeTypedObject(keymasterCertificateChain, 1);
            return true;
        }

        private boolean onTransact$setKeyPairCertificate$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            byte[] bArrCreateByteArray2 = parcel.createByteArray();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyPairCertificate = setKeyPairCertificate(componentName, string, string2, bArrCreateByteArray, bArrCreateByteArray2, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyPairCertificate);
            return true;
        }

        private boolean onTransact$choosePrivateKeyAlias$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            String string = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            parcel.enforceNoDataAvail();
            choosePrivateKeyAlias(i, uri, string, strongBinder);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDelegatedScopes$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setDelegatedScopes(componentName, string, arrayListCreateStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAlwaysOnVpnPackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            boolean alwaysOnVpnPackage = setAlwaysOnVpnPackage(componentName, string, z, arrayListCreateStringArrayList);
            parcel2.writeNoException();
            parcel2.writeBoolean(alwaysOnVpnPackage);
            return true;
        }

        private boolean onTransact$addPersistentPreferredActivity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            parcel.enforceNoDataAvail();
            addPersistentPreferredActivity(componentName, string, intentFilter, componentName2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$clearPackagePersistentPreferredActivities$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            clearPackagePersistentPreferredActivities(componentName, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDefaultSmsApplication$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setDefaultSmsApplication(componentName, string, string2, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationRestrictions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setApplicationRestrictions(componentName, string, string2, bundle, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getApplicationRestrictions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            Bundle applicationRestrictions = getApplicationRestrictions(componentName, string, string2, z);
            parcel2.writeNoException();
            parcel2.writeTypedObject(applicationRestrictions, 1);
            return true;
        }

        private boolean onTransact$setUserRestriction$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUserRestriction(componentName, string, string2, z, z2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUserRestrictionForUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setUserRestrictionForUser(string, string2, z, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUserRestrictionGloballyFromSystem$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUserRestrictionGloballyFromSystem(string, string2, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getUserRestrictions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            Bundle userRestrictions = getUserRestrictions(componentName, string, z);
            parcel2.writeNoException();
            parcel2.writeTypedObject(userRestrictions, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileIntentFilter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            addCrossProfileIntentFilter(componentName, string, intentFilter, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$isAccessibilityServicePermittedByAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsAccessibilityServicePermittedByAdmin = isAccessibilityServicePermittedByAdmin(componentName, string, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsAccessibilityServicePermittedByAdmin);
            return true;
        }

        private boolean onTransact$setPermittedInputMethods$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean permittedInputMethods = setPermittedInputMethods(componentName, string, arrayListCreateStringArrayList, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(permittedInputMethods);
            return true;
        }

        private boolean onTransact$getPermittedInputMethods$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            List<String> permittedInputMethods = getPermittedInputMethods(componentName, string, z);
            parcel2.writeNoException();
            parcel2.writeStringList(permittedInputMethods);
            return true;
        }

        private boolean onTransact$isInputMethodPermittedByAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zIsInputMethodPermittedByAdmin = isInputMethodPermittedByAdmin(componentName, string, i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsInputMethodPermittedByAdmin);
            return true;
        }

        private boolean onTransact$setApplicationHidden$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean applicationHidden = setApplicationHidden(componentName, string, string2, z, z2);
            parcel2.writeNoException();
            parcel2.writeBoolean(applicationHidden);
            return true;
        }

        private boolean onTransact$isApplicationHidden$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zIsApplicationHidden = isApplicationHidden(componentName, string, string2, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsApplicationHidden);
            return true;
        }

        private boolean onTransact$createAndManageUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            UserHandle userHandleCreateAndManageUser = createAndManageUser(componentName, string, componentName2, persistableBundle, i);
            parcel2.writeNoException();
            parcel2.writeTypedObject(userHandleCreateAndManageUser, 1);
            return true;
        }

        private boolean onTransact$enableSystemApp$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            enableSystemApp(componentName, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$enableSystemAppWithIntent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            parcel.enforceNoDataAvail();
            int iEnableSystemAppWithIntent = enableSystemAppWithIntent(componentName, string, intent);
            parcel2.writeNoException();
            parcel2.writeInt(iEnableSystemAppWithIntent);
            return true;
        }

        private boolean onTransact$installExistingPackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zInstallExistingPackage = installExistingPackage(componentName, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zInstallExistingPackage);
            return true;
        }

        private boolean onTransact$setAccountManagementDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAccountManagementDisabled(componentName, string, string2, z, z2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getAccountTypesWithManagementDisabledAsUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            String[] accountTypesWithManagementDisabledAsUser = getAccountTypesWithManagementDisabledAsUser(i, string, z);
            parcel2.writeNoException();
            parcel2.writeStringArray(accountTypesWithManagementDisabledAsUser);
            return true;
        }

        private boolean onTransact$setSecondaryLockscreenEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            parcel.enforceNoDataAvail();
            setSecondaryLockscreenEnabled(componentName, z, persistableBundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setLockTaskPackages$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String[] strArrCreateStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            setLockTaskPackages(componentName, string, strArrCreateStringArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setLockTaskFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setLockTaskFeatures(componentName, string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setGlobalSetting(componentName, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setSystemSetting(componentName, string, string2, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSecureSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setSecureSetting(componentName, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setConfiguredNetworksLockdownState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setConfiguredNetworksLockdownState(componentName, string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTime$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean time = setTime(componentName, string, j);
            parcel2.writeNoException();
            parcel2.writeBoolean(time);
            return true;
        }

        private boolean onTransact$setTimeZone$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean timeZone = setTimeZone(componentName, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(timeZone);
            return true;
        }

        private boolean onTransact$notifyLockTaskModeChanged$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            notifyLockTaskModeChanged(z, string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUninstallBlocked$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUninstallBlocked(componentName, string, string2, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startManagedQuickContact$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            long j = parcel.readLong();
            boolean z = parcel.readBoolean();
            long j2 = parcel.readLong();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            parcel.enforceNoDataAvail();
            startManagedQuickContact(string, j, z, j2, intent);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTrustAgentConfiguration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setTrustAgentConfiguration(componentName, string, componentName2, persistableBundle, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getTrustAgentConfiguration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            List<PersistableBundle> trustAgentConfiguration = getTrustAgentConfiguration(componentName, componentName2, i, z);
            parcel2.writeNoException();
            parcel2.writeTypedList(trustAgentConfiguration, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileWidgetProvider$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zAddCrossProfileWidgetProvider = addCrossProfileWidgetProvider(componentName, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zAddCrossProfileWidgetProvider);
            return true;
        }

        private boolean onTransact$removeCrossProfileWidgetProvider$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zRemoveCrossProfileWidgetProvider = removeCrossProfileWidgetProvider(componentName, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zRemoveCrossProfileWidgetProvider);
            return true;
        }

        private boolean onTransact$setAutoTimeEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAutoTimeEnabled(componentName, string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAutoTimeZoneEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAutoTimeZoneEnabled(componentName, string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemUpdatePolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            SystemUpdatePolicy systemUpdatePolicy = (SystemUpdatePolicy) parcel.readTypedObject(SystemUpdatePolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setSystemUpdatePolicy(componentName, string, systemUpdatePolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setStatusBarDisabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean statusBarDisabled = setStatusBarDisabled(componentName, string, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(statusBarDisabled);
            return true;
        }

        private boolean onTransact$setPermissionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setPermissionPolicy(componentName, string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setPermissionGrantState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            int i = parcel.readInt();
            RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
            parcel.enforceNoDataAvail();
            setPermissionGrantState(componentName, string, string2, string3, i, remoteCallback);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPermissionGrantState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            parcel.enforceNoDataAvail();
            int permissionGrantState = getPermissionGrantState(componentName, string, string2, string3);
            parcel2.writeNoException();
            parcel2.writeInt(permissionGrantState);
            return true;
        }

        private boolean onTransact$setKeepUninstalledPackages$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setKeepUninstalledPackages(componentName, string, arrayListCreateStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setShortSupportMessage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
            parcel.enforceNoDataAvail();
            setShortSupportMessage(componentName, string, charSequence);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationName$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
            parcel.enforceNoDataAvail();
            setOrganizationName(componentName, string, charSequence);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSecurityLoggingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setSecurityLoggingEnabled(componentName, string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setNetworkLoggingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setNetworkLoggingEnabled(componentName, string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$retrieveNetworkLogs$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            List<NetworkEvent> listRetrieveNetworkLogs = retrieveNetworkLogs(componentName, string, j);
            parcel2.writeNoException();
            parcel2.writeTypedList(listRetrieveNetworkLogs, 1);
            return true;
        }

        private boolean onTransact$bindDeviceAdminServiceAsUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IBinder strongBinder = parcel.readStrongBinder();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            IServiceConnection iServiceConnectionAsInterface = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
            long j = parcel.readLong();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zBindDeviceAdminServiceAsUser = bindDeviceAdminServiceAsUser(componentName, iApplicationThreadAsInterface, strongBinder, intent, iServiceConnectionAsInterface, j, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zBindDeviceAdminServiceAsUser);
            return true;
        }

        private boolean onTransact$setResetPasswordToken$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            boolean resetPasswordToken = setResetPasswordToken(componentName, string, bArrCreateByteArray);
            parcel2.writeNoException();
            parcel2.writeBoolean(resetPasswordToken);
            return true;
        }

        private boolean onTransact$resetPasswordWithToken$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zResetPasswordWithToken = resetPasswordWithToken(componentName, string, string2, bArrCreateByteArray, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zResetPasswordWithToken);
            return true;
        }

        private boolean onTransact$clearApplicationUserData$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            IPackageDataObserver iPackageDataObserverAsInterface = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            clearApplicationUserData(componentName, string, iPackageDataObserverAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDisallowedSystemApps$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            List<String> disallowedSystemApps = getDisallowedSystemApps(componentName, i, string);
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
            int i = parcel.readInt();
            ApnSetting apnSetting = (ApnSetting) parcel.readTypedObject(ApnSetting.CREATOR);
            parcel.enforceNoDataAvail();
            boolean zUpdateOverrideApn = updateOverrideApn(componentName, i, apnSetting);
            parcel2.writeNoException();
            parcel2.writeBoolean(zUpdateOverrideApn);
            return true;
        }

        private boolean onTransact$isMeteredDataDisabledPackageForUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsMeteredDataDisabledPackageForUser = isMeteredDataDisabledPackageForUser(componentName, string, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsMeteredDataDisabledPackageForUser);
            return true;
        }

        private boolean onTransact$reportFailedPasswordAttemptWithFailureCount$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            reportFailedPasswordAttemptWithFailureCount(i, i2, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalPrivateDns$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            int globalPrivateDns = setGlobalPrivateDns(componentName, i, string);
            parcel2.writeNoException();
            parcel2.writeInt(globalPrivateDns);
            return true;
        }

        private boolean onTransact$setProfileOwnerOnOrganizationOwnedDevice$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setProfileOwnerOnOrganizationOwnedDevice(componentName, i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$installUpdateFromFile$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
            StartInstallingUpdateCallback startInstallingUpdateCallbackAsInterface = StartInstallingUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            installUpdateFromFile(componentName, string, parcelFileDescriptor, startInstallingUpdateCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startViewCalendarEventInManagedProfile$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            long j = parcel.readLong();
            long j2 = parcel.readLong();
            long j3 = parcel.readLong();
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zStartViewCalendarEventInManagedProfile = startViewCalendarEventInManagedProfile(string, j, j2, j3, z, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zStartViewCalendarEventInManagedProfile);
            return true;
        }

        private boolean onTransact$setKeyGrantForApp$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyGrantForApp = setKeyGrantForApp(componentName, string, string2, string3, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyGrantForApp);
            return true;
        }

        private boolean onTransact$setKeyGrantToWifiAuth$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyGrantToWifiAuth = setKeyGrantToWifiAuth(string, string2, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyGrantToWifiAuth);
            return true;
        }

        private boolean onTransact$setUserControlDisabledPackages$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setUserControlDisabledPackages(componentName, string, arrayListCreateStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCommonCriteriaModeEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setCommonCriteriaModeEnabled(componentName, string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationIdForUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setOrganizationIdForUser(string, string2, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDeviceOwnerType$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setDeviceOwnerType(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUsbDataSignalingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUsbDataSignalingEnabled(string, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMinimumRequiredWifiSecurityLevel$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setMinimumRequiredWifiSecurityLevel(string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiSsidPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            WifiSsidPolicy wifiSsidPolicy = (WifiSsidPolicy) parcel.readTypedObject(WifiSsidPolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setWifiSsidPolicy(string, wifiSsidPolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDrawable$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            parcel.enforceNoDataAvail();
            ParcelableResource drawable = getDrawable(string, string2, string3);
            parcel2.writeNoException();
            parcel2.writeTypedObject(drawable, 1);
            return true;
        }

        private boolean onTransact$semSetPasswordQuality$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordQuality(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumLength(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumUpperCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumUpperCase(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumLowerCase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumLowerCase(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumNonLetter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordMinimumNonLetter(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordHistoryLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetPasswordHistoryLength(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordExpirationTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            semSetPasswordExpirationTimeout(componentName, j);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetSimplePasswordEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetSimplePasswordEnabled(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semIsSimplePasswordEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemIsSimplePasswordEnabled = semIsSimplePasswordEnabled(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemIsSimplePasswordEnabled);
            return true;
        }

        private boolean onTransact$semSetKeyguardDisabledFeatures$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetKeyguardDisabledFeatures(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semSetAllowStorageCard$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowStorageCard(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowStorageCard$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowStorageCard = semGetAllowStorageCard(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowStorageCard);
            return true;
        }

        private boolean onTransact$semSetAllowWifi$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowWifi(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowWifi$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowWifi = semGetAllowWifi(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowWifi);
            return true;
        }

        private boolean onTransact$semSetAllowTextMessaging$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowTextMessaging(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowTextMessaging$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowTextMessaging = semGetAllowTextMessaging(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowTextMessaging);
            return true;
        }

        private boolean onTransact$semSetAllowPopImapEmail$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowPopImapEmail(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowPopImapEmail$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowPopImapEmail = semGetAllowPopImapEmail(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowPopImapEmail);
            return true;
        }

        private boolean onTransact$semSetAllowBrowser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowBrowser(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowBrowser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowBrowser = semGetAllowBrowser(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowBrowser);
            return true;
        }

        private boolean onTransact$semSetAllowInternetSharing$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowInternetSharing(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowInternetSharing$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowInternetSharing = semGetAllowInternetSharing(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowInternetSharing);
            return true;
        }

        private boolean onTransact$semSetAllowBluetoothMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            semSetAllowBluetoothMode(componentName, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowBluetoothMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iSemGetAllowBluetoothMode = semGetAllowBluetoothMode(componentName, i);
            parcel2.writeNoException();
            parcel2.writeInt(iSemGetAllowBluetoothMode);
            return true;
        }

        private boolean onTransact$semSetAllowDesktopSync$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowDesktopSync(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowDesktopSync$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowDesktopSync = semGetAllowDesktopSync(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowDesktopSync);
            return true;
        }

        private boolean onTransact$semSetAllowIrda$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetAllowIrda(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowIrda$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zSemGetAllowIrda = semGetAllowIrda(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetAllowIrda);
            return true;
        }

        private boolean onTransact$semSetRequireStorageCardEncryption$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetRequireStorageCardEncryption(componentName, z, z2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$semGetRequireStorageCardEncryption$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zSemGetRequireStorageCardEncryption = semGetRequireStorageCardEncryption(componentName, i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSemGetRequireStorageCardEncryption);
            return true;
        }

        private boolean onTransact$semSetChangeNotificationEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            semSetChangeNotificationEnabled(componentName, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationExemptions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            int[] iArrCreateIntArray = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            setApplicationExemptions(string, string2, iArrCreateIntArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMtePolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setMtePolicy(i, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMtePolicyBySystem$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setMtePolicyBySystem(string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCrossProfileAppToIgnored$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setCrossProfileAppToIgnored(i, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getSamsungSDcardEncryptionStatus$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean samsungSDcardEncryptionStatus = getSamsungSDcardEncryptionStatus(componentName, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(samsungSDcardEncryptionStatus);
            return true;
        }

        private boolean onTransact$setContentProtectionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setContentProtectionPolicy(componentName, string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getContentProtectionPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int contentProtectionPolicy = getContentProtectionPolicy(componentName, string, i);
            parcel2.writeNoException();
            parcel2.writeInt(contentProtectionPolicy);
            return true;
        }

        private boolean onTransact$setMaxPolicyStorageLimit$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setMaxPolicyStorageLimit(string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$forceSetMaxPolicyStorageLimit$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            forceSetMaxPolicyStorageLimit(string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPolicySizeForAdmin$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            EnforcingAdmin enforcingAdmin = (EnforcingAdmin) parcel.readTypedObject(EnforcingAdmin.CREATOR);
            parcel.enforceNoDataAvail();
            int policySizeForAdmin = getPolicySizeForAdmin(string, enforcingAdmin);
            parcel2.writeNoException();
            parcel2.writeInt(policySizeForAdmin);
            return true;
        }

        private boolean onTransact$setAppFunctionsPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setAppFunctionsPolicy(string, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getAppFunctionsPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int appFunctionsPolicy = getAppFunctionsPolicy(string, i);
            parcel2.writeNoException();
            parcel2.writeInt(appFunctionsPolicy);
            return true;
        }
    }
}
