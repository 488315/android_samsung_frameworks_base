package android.app.admin;

import android.accounts.Account;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
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

import java.util.List;
import java.util.Map;

public interface IDevicePolicyManager extends IInterface {
    void acknowledgeDeviceCompliant() throws RemoteException;

    void acknowledgeNewUserDisclaimer(int i) throws RemoteException;

    void addCrossProfileIntentFilter(
            ComponentName componentName, String str, IntentFilter intentFilter, int i)
            throws RemoteException;

    boolean addCrossProfileWidgetProvider(ComponentName componentName, String str, String str2)
            throws RemoteException;

    int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException;

    void addPersistentPreferredActivity(
            ComponentName componentName,
            String str,
            IntentFilter intentFilter,
            ComponentName componentName2)
            throws RemoteException;

    boolean approveCaCert(String str, int i, boolean z) throws RemoteException;

    boolean bindDeviceAdminServiceAsUser(
            ComponentName componentName,
            IApplicationThread iApplicationThread,
            IBinder iBinder,
            Intent intent,
            IServiceConnection iServiceConnection,
            long j,
            int i)
            throws RemoteException;

    void calculateHasIncompatibleAccounts() throws RemoteException;

    boolean canAdminGrantSensorsPermissions() throws RemoteException;

    boolean canProfileOwnerResetPasswordWhenLocked(int i) throws RemoteException;

    boolean canUsbDataSignalingBeDisabled() throws RemoteException;

    boolean checkDeviceIdentifierAccess(String str, int i, int i2) throws RemoteException;

    int checkProvisioningPrecondition(String str, String str2) throws RemoteException;

    void choosePrivateKeyAlias(int i, Uri uri, String str, IBinder iBinder) throws RemoteException;

    void clearApplicationUserData(
            ComponentName componentName, String str, IPackageDataObserver iPackageDataObserver)
            throws RemoteException;

    void clearCrossProfileIntentFilters(ComponentName componentName, String str)
            throws RemoteException;

    void clearDeviceOwner(String str) throws RemoteException;

    void clearOrganizationIdForUser(int i) throws RemoteException;

    void clearPackagePersistentPreferredActivities(
            ComponentName componentName, String str, String str2) throws RemoteException;

    void clearProfileOwner(ComponentName componentName) throws RemoteException;

    boolean clearResetPasswordToken(ComponentName componentName, String str) throws RemoteException;

    boolean clearResetPasswordTokenMDM(ComponentName componentName, int i) throws RemoteException;

    void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException;

    Intent createAdminSupportIntent(String str) throws RemoteException;

    UserHandle createAndManageUser(
            ComponentName componentName,
            String str,
            ComponentName componentName2,
            PersistableBundle persistableBundle,
            int i)
            throws RemoteException;

    UserHandle createAndProvisionManagedProfile(
            ManagedProfileProvisioningParams managedProfileProvisioningParams, String str)
            throws RemoteException;

    void enableSystemApp(ComponentName componentName, String str, String str2)
            throws RemoteException;

    int enableSystemAppWithIntent(ComponentName componentName, String str, Intent intent)
            throws RemoteException;

    void enforceCanManageCaCerts(ComponentName componentName, String str) throws RemoteException;

    void finalizeWorkProfileProvisioning(UserHandle userHandle, Account account)
            throws RemoteException;

    long forceNetworkLogs() throws RemoteException;

    void forceRemoveActiveAdmin(ComponentName componentName, int i) throws RemoteException;

    long forceSecurityLogs() throws RemoteException;

    void forceSetMaxPolicyStorageLimit(String str, int i) throws RemoteException;

    void forceUpdateUserSetupComplete(int i) throws RemoteException;

    boolean generateKeyPair(
            ComponentName componentName,
            String str,
            String str2,
            ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec,
            int i,
            KeymasterCertificateChain keymasterCertificateChain)
            throws RemoteException;

    String[] getAccountTypesWithManagementDisabled(String str) throws RemoteException;

    String[] getAccountTypesWithManagementDisabledAsUser(int i, String str, boolean z)
            throws RemoteException;

    List<ComponentName> getActiveAdmins(int i) throws RemoteException;

    List<String> getAffiliationIds(ComponentName componentName) throws RemoteException;

    int getAggregatedPasswordComplexityForUser(int i, boolean z) throws RemoteException;

    List<String> getAllCrossProfilePackages(int i) throws RemoteException;

    List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName componentName)
            throws RemoteException;

    String getAlwaysOnVpnPackage(ComponentName componentName) throws RemoteException;

    String getAlwaysOnVpnPackageForUser(int i) throws RemoteException;

    int[] getApplicationExemptions(String str) throws RemoteException;

    Bundle getApplicationRestrictions(
            ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    Bundle getApplicationRestrictionsMDM(ComponentName componentName, String str, int i)
            throws RemoteException;

    String getApplicationRestrictionsManagingPackage(ComponentName componentName)
            throws RemoteException;

    boolean getAutoTimeEnabled(ComponentName componentName, String str) throws RemoteException;

    boolean getAutoTimeRequired() throws RemoteException;

    boolean getAutoTimeZoneEnabled(ComponentName componentName, String str) throws RemoteException;

    List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName)
            throws RemoteException;

    boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException;

    boolean getBluetoothContactSharingDisabledForUser(int i) throws RemoteException;

    boolean getCameraDisabled(ComponentName componentName, String str, int i, boolean z)
            throws RemoteException;

    String getCertInstallerPackage(ComponentName componentName) throws RemoteException;

    int getContentProtectionPolicy(ComponentName componentName, String str) throws RemoteException;

    PackagePolicy getCredentialManagerPolicy(int i) throws RemoteException;

    List<String> getCrossProfileCalendarPackages(ComponentName componentName)
            throws RemoteException;

    List<String> getCrossProfileCalendarPackagesForUser(int i) throws RemoteException;

    boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException;

    boolean getCrossProfileCallerIdDisabledForUser(int i) throws RemoteException;

    boolean getCrossProfileContactsSearchDisabled(ComponentName componentName)
            throws RemoteException;

    boolean getCrossProfileContactsSearchDisabledForUser(int i) throws RemoteException;

    List<String> getCrossProfilePackages(ComponentName componentName) throws RemoteException;

    List<String> getCrossProfileWidgetProviders(ComponentName componentName, String str)
            throws RemoteException;

    int getCurrentFailedBiometricAttempts(int i) throws RemoteException;

    int getCurrentFailedPasswordAttempts(String str, int i, boolean z) throws RemoteException;

    List<String> getDefaultCrossProfilePackages() throws RemoteException;

    List<String> getDelegatePackages(ComponentName componentName, String str)
            throws RemoteException;

    Map getDelegatedPackages(int i) throws RemoteException;

    List<String> getDelegatedScopes(ComponentName componentName, String str) throws RemoteException;

    ComponentName getDeviceOwnerComponent(boolean z) throws RemoteException;

    ComponentName getDeviceOwnerComponentOnUser(int i) throws RemoteException;

    CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException;

    String getDeviceOwnerName() throws RemoteException;

    CharSequence getDeviceOwnerOrganizationName() throws RemoteException;

    int getDeviceOwnerType(ComponentName componentName) throws RemoteException;

    int getDeviceOwnerUserId() throws RemoteException;

    DevicePolicyState getDevicePolicyState() throws RemoteException;

    List<String> getDisallowedSystemApps(ComponentName componentName, int i, String str)
            throws RemoteException;

    boolean getDoNotAskCredentialsOnBoot() throws RemoteException;

    ParcelableResource getDrawable(String str, String str2, String str3) throws RemoteException;

    CharSequence getEndUserSessionMessage(ComponentName componentName) throws RemoteException;

    Bundle getEnforcingAdminAndUserDetails(int i, String str) throws RemoteException;

    List<EnforcingAdmin> getEnforcingAdminsForRestriction(int i, String str) throws RemoteException;

    String getEnrollmentSpecificId(String str) throws RemoteException;

    FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName componentName)
            throws RemoteException;

    String getFinancedDeviceKioskRoleHolder(String str) throws RemoteException;

    boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException;

    String getGlobalPrivateDnsHost(ComponentName componentName) throws RemoteException;

    int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException;

    ComponentName getGlobalProxyAdmin(int i) throws RemoteException;

    int getHeadlessDeviceOwnerMode(String str) throws RemoteException;

    List<String> getKeepUninstalledPackages(ComponentName componentName, String str)
            throws RemoteException;

    ParcelableGranteeMap getKeyPairGrants(String str, String str2) throws RemoteException;

    int getKeyguardDisabledFeatures(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    long getLastBugReportRequestTime() throws RemoteException;

    long getLastNetworkLogRetrievalTime() throws RemoteException;

    long getLastSecurityLogRetrievalTime() throws RemoteException;

    int getLockTaskFeatures(ComponentName componentName, String str) throws RemoteException;

    String[] getLockTaskPackages(ComponentName componentName, String str) throws RemoteException;

    int getLogoutUserId() throws RemoteException;

    CharSequence getLongSupportMessage(ComponentName componentName) throws RemoteException;

    CharSequence getLongSupportMessageForUser(ComponentName componentName, int i)
            throws RemoteException;

    PackagePolicy getManagedProfileCallerIdAccessPolicy() throws RemoteException;

    PackagePolicy getManagedProfileContactsAccessPolicy() throws RemoteException;

    long getManagedProfileMaximumTimeOff(ComponentName componentName) throws RemoteException;

    ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws RemoteException;

    int getMaxPolicyStorageLimit(String str) throws RemoteException;

    int getMaximumFailedPasswordsForWipe(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    long getMaximumTimeToLock(ComponentName componentName, int i, boolean z) throws RemoteException;

    List<String> getMeteredDataDisabledPackages(ComponentName componentName) throws RemoteException;

    int getMinimumRequiredWifiSecurityLevel() throws RemoteException;

    int getMtePolicy(String str) throws RemoteException;

    int getNearbyAppStreamingPolicy(int i) throws RemoteException;

    int getNearbyNotificationStreamingPolicy(int i) throws RemoteException;

    int getOrganizationColor(ComponentName componentName) throws RemoteException;

    int getOrganizationColorForUser(int i) throws RemoteException;

    CharSequence getOrganizationName(ComponentName componentName, String str)
            throws RemoteException;

    CharSequence getOrganizationNameForUser(int i) throws RemoteException;

    List<ApnSetting> getOverrideApns(ComponentName componentName) throws RemoteException;

    StringParceledListSlice getOwnerInstalledCaCerts(UserHandle userHandle) throws RemoteException;

    int getPasswordComplexity(boolean z) throws RemoteException;

    long getPasswordExpiration(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    long getPasswordExpirationTimeout(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordHistoryLength(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordMinimumLength(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordMinimumLetters(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws RemoteException;

    int getPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordMinimumNumeric(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordMinimumSymbols(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    int getPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException;

    SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName, String str)
            throws RemoteException;

    int getPermissionGrantState(ComponentName componentName, String str, String str2, String str3)
            throws RemoteException;

    int getPermissionPolicy(ComponentName componentName) throws RemoteException;

    List<String> getPermittedAccessibilityServices(ComponentName componentName)
            throws RemoteException;

    List<String> getPermittedAccessibilityServicesForUser(int i) throws RemoteException;

    List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName)
            throws RemoteException;

    List<String> getPermittedInputMethods(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    List<String> getPermittedInputMethodsAsUser(int i) throws RemoteException;

    int getPersonalAppsSuspendedReasons(ComponentName componentName) throws RemoteException;

    List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle) throws RemoteException;

    int getPolicySizeForAdmin(String str, EnforcingAdmin enforcingAdmin) throws RemoteException;

    List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs()
            throws RemoteException;

    ComponentName getProfileOwnerAsUser(int i) throws RemoteException;

    String getProfileOwnerName(int i) throws RemoteException;

    ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle)
            throws RemoteException;

    int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws RemoteException;

    void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int i)
            throws RemoteException;

    int getRequiredPasswordComplexity(String str, boolean z) throws RemoteException;

    long getRequiredStrongAuthTimeout(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    ComponentName getRestrictionsProvider(int i) throws RemoteException;

    boolean getSamsungSDcardEncryptionStatus(ComponentName componentName, int i)
            throws RemoteException;

    boolean getScreenCaptureDisabled(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    List<UserHandle> getSecondaryUsers(ComponentName componentName) throws RemoteException;

    CharSequence getShortSupportMessage(ComponentName componentName, String str)
            throws RemoteException;

    CharSequence getShortSupportMessageForUser(ComponentName componentName, int i)
            throws RemoteException;

    CharSequence getStartUserSessionMessage(ComponentName componentName) throws RemoteException;

    boolean getStorageEncryption(ComponentName componentName, int i) throws RemoteException;

    int getStorageEncryptionStatus(String str, int i) throws RemoteException;

    ParcelableResource getString(String str) throws RemoteException;

    int[] getSubscriptionIds(String str) throws RemoteException;

    SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException;

    PersistableBundle getTransferOwnershipBundle() throws RemoteException;

    List<PersistableBundle> getTrustAgentConfiguration(
            ComponentName componentName, ComponentName componentName2, int i, boolean z)
            throws RemoteException;

    List<String> getUserControlDisabledPackages(ComponentName componentName, String str)
            throws RemoteException;

    int getUserProvisioningState(int i) throws RemoteException;

    Bundle getUserRestrictions(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    Bundle getUserRestrictionsGlobally(String str) throws RemoteException;

    String getWifiMacAddress(ComponentName componentName, String str) throws RemoteException;

    WifiSsidPolicy getWifiSsidPolicy(String str) throws RemoteException;

    boolean hasDelegatedPermission(String str, int i, String str2) throws RemoteException;

    boolean hasDeviceOwner() throws RemoteException;

    boolean hasGrantedPolicy(ComponentName componentName, int i, int i2) throws RemoteException;

    boolean hasKeyPair(String str, String str2) throws RemoteException;

    boolean hasLockdownAdminConfiguredNetworks(ComponentName componentName) throws RemoteException;

    boolean hasManagedProfileCallerIdAccess(int i, String str) throws RemoteException;

    boolean hasManagedProfileContactsAccess(int i, String str) throws RemoteException;

    boolean hasUserSetupCompleted() throws RemoteException;

    boolean installCaCert(ComponentName componentName, String str, byte[] bArr)
            throws RemoteException;

    boolean installExistingPackage(ComponentName componentName, String str, String str2)
            throws RemoteException;

    boolean installKeyPair(
            ComponentName componentName,
            String str,
            byte[] bArr,
            byte[] bArr2,
            byte[] bArr3,
            String str2,
            boolean z,
            boolean z2)
            throws RemoteException;

    void installUpdateFromFile(
            ComponentName componentName,
            String str,
            ParcelFileDescriptor parcelFileDescriptor,
            StartInstallingUpdateCallback startInstallingUpdateCallback)
            throws RemoteException;

    boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String str, int i)
            throws RemoteException;

    boolean isActivePasswordSufficient(String str, int i, boolean z) throws RemoteException;

    boolean isActivePasswordSufficientForDeviceRequirement() throws RemoteException;

    boolean isAdminActive(ComponentName componentName, int i) throws RemoteException;

    boolean isAffiliatedUser(int i) throws RemoteException;

    boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException;

    boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws RemoteException;

    boolean isApplicationHidden(ComponentName componentName, String str, String str2, boolean z)
            throws RemoteException;

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

    boolean isInputMethodPermittedByAdmin(ComponentName componentName, String str, int i, boolean z)
            throws RemoteException;

    boolean isKeyPairGrantedToWifiAuth(String str, String str2) throws RemoteException;

    boolean isLockTaskPermitted(String str) throws RemoteException;

    boolean isLogoutEnabled() throws RemoteException;

    boolean isManagedKiosk() throws RemoteException;

    boolean isManagedProfile(ComponentName componentName) throws RemoteException;

    boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException;

    boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String str, int i)
            throws RemoteException;

    boolean isNetworkLoggingEnabled(ComponentName componentName, String str) throws RemoteException;

    boolean isNewUserDisclaimerAcknowledged(int i) throws RemoteException;

    boolean isNotificationListenerServicePermitted(String str, int i) throws RemoteException;

    boolean isOrganizationOwnedDeviceWithManagedProfile() throws RemoteException;

    boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException;

    boolean isPackageAllowedToAccessCalendarForUser(String str, int i) throws RemoteException;

    boolean isPackageSuspended(ComponentName componentName, String str, String str2)
            throws RemoteException;

    boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws RemoteException;

    boolean isProfileOwnerOfOrganizationOwnedDeviceMDM(int i) throws RemoteException;

    boolean isProvisioningAllowed(String str, String str2) throws RemoteException;

    boolean isRemovingAdmin(ComponentName componentName, int i) throws RemoteException;

    boolean isResetPasswordTokenActive(ComponentName componentName, String str)
            throws RemoteException;

    boolean isResetPasswordTokenActiveMDM(ComponentName componentName, int i)
            throws RemoteException;

    boolean isSafeOperation(int i) throws RemoteException;

    boolean isSecondaryLockscreenEnabled(UserHandle userHandle) throws RemoteException;

    boolean isSecurityLoggingEnabled(ComponentName componentName, String str)
            throws RemoteException;

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

    void provisionFullyManagedDevice(
            FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, String str)
            throws RemoteException;

    void reboot(ComponentName componentName) throws RemoteException;

    void removeActiveAdmin(ComponentName componentName, int i) throws RemoteException;

    boolean removeCrossProfileWidgetProvider(ComponentName componentName, String str, String str2)
            throws RemoteException;

    boolean removeKeyPair(ComponentName componentName, String str, String str2)
            throws RemoteException;

    boolean removeOverrideApn(ComponentName componentName, int i) throws RemoteException;

    boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void reportFailedBiometricAttempt(int i) throws RemoteException;

    void reportFailedPasswordAttempt(int i, boolean z) throws RemoteException;

    void reportFailedPasswordAttemptWithFailureCount(int i, int i2, boolean z)
            throws RemoteException;

    void reportKeyguardDismissed(int i) throws RemoteException;

    void reportKeyguardSecured(int i) throws RemoteException;

    void reportPasswordChanged(PasswordMetrics passwordMetrics, int i) throws RemoteException;

    void reportSuccessfulBiometricAttempt(int i) throws RemoteException;

    void reportSuccessfulPasswordAttempt(int i) throws RemoteException;

    boolean requestBugreport(ComponentName componentName) throws RemoteException;

    void resetDefaultCrossProfileIntentFilters(int i) throws RemoteException;

    void resetDrawables(List<String> list) throws RemoteException;

    boolean resetPassword(String str, int i) throws RemoteException;

    boolean resetPasswordWithToken(
            ComponentName componentName, String str, String str2, byte[] bArr, int i)
            throws RemoteException;

    boolean resetPasswordWithTokenMDM(
            ComponentName componentName, String str, byte[] bArr, int i, int i2)
            throws RemoteException;

    void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState()
            throws RemoteException;

    void resetStrings(List<String> list) throws RemoteException;

    List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, String str, long j)
            throws RemoteException;

    ParceledListSlice retrievePreRebootSecurityLogs(ComponentName componentName, String str)
            throws RemoteException;

    ParceledListSlice retrieveSecurityLogs(ComponentName componentName, String str)
            throws RemoteException;

    int semGetAllowBluetoothMode(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowBrowser(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowDesktopSync(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowInternetSharing(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowIrda(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowPopImapEmail(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowStorageCard(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowTextMessaging(ComponentName componentName, int i) throws RemoteException;

    boolean semGetAllowWifi(ComponentName componentName, int i) throws RemoteException;

    boolean semGetRequireStorageCardEncryption(ComponentName componentName, int i, boolean z)
            throws RemoteException;

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

    void semSetChangeNotificationEnabled(ComponentName componentName, boolean z)
            throws RemoteException;

    void semSetKeyguardDisabledFeatures(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordExpirationTimeout(ComponentName componentName, long j)
            throws RemoteException;

    void semSetPasswordHistoryLength(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumLength(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumLowerCase(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumNonLetter(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordMinimumUpperCase(ComponentName componentName, int i) throws RemoteException;

    void semSetPasswordQuality(ComponentName componentName, int i) throws RemoteException;

    void semSetRequireStorageCardEncryption(ComponentName componentName, boolean z, boolean z2)
            throws RemoteException;

    void semSetSimplePasswordEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void sendLostModeLocationUpdate(AndroidFuture<Boolean> androidFuture) throws RemoteException;

    void setAccountManagementDisabled(
            ComponentName componentName, String str, String str2, boolean z, boolean z2)
            throws RemoteException;

    void setActiveAdmin(ComponentName componentName, boolean z, int i) throws RemoteException;

    void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException;

    boolean setAlwaysOnVpnPackage(
            ComponentName componentName, String str, boolean z, List<String> list)
            throws RemoteException;

    void setApplicationExemptions(String str, String str2, int[] iArr) throws RemoteException;

    boolean setApplicationHidden(
            ComponentName componentName, String str, String str2, boolean z, boolean z2)
            throws RemoteException;

    void setApplicationRestrictions(
            ComponentName componentName, String str, String str2, Bundle bundle, boolean z)
            throws RemoteException;

    void setApplicationRestrictionsMDM(
            ComponentName componentName, String str, Bundle bundle, int i) throws RemoteException;

    boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String str)
            throws RemoteException;

    void setAuditLogEnabled(String str, boolean z) throws RemoteException;

    void setAuditLogEventsCallback(String str, IAuditLogEventsCallback iAuditLogEventsCallback)
            throws RemoteException;

    void setAutoTimeEnabled(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    void setAutoTimeRequired(ComponentName componentName, boolean z) throws RemoteException;

    void setAutoTimeZoneEnabled(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    void setBackupServiceEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setBluetoothContactSharingDisabled(ComponentName componentName, boolean z)
            throws RemoteException;

    void setCameraDisabled(ComponentName componentName, String str, boolean z, boolean z2)
            throws RemoteException;

    void setCertInstallerPackage(ComponentName componentName, String str) throws RemoteException;

    void setCommonCriteriaModeEnabled(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    void setConfiguredNetworksLockdownState(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    void setContentProtectionPolicy(ComponentName componentName, String str, int i)
            throws RemoteException;

    void setCredentialManagerPolicy(PackagePolicy packagePolicy) throws RemoteException;

    void setCrossProfileAppToIgnored(int i, String str) throws RemoteException;

    void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list)
            throws RemoteException;

    void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean z)
            throws RemoteException;

    void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean z)
            throws RemoteException;

    void setCrossProfilePackages(ComponentName componentName, List<String> list)
            throws RemoteException;

    void setDefaultDialerApplication(String str) throws RemoteException;

    void setDefaultSmsApplication(ComponentName componentName, String str, String str2, boolean z)
            throws RemoteException;

    void setDelegatedScopes(ComponentName componentName, String str, List<String> list)
            throws RemoteException;

    boolean setDeviceOwner(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence)
            throws RemoteException;

    void setDeviceOwnerType(ComponentName componentName, int i) throws RemoteException;

    void setDeviceProvisioningConfigApplied() throws RemoteException;

    void setDpcDownloaded(boolean z) throws RemoteException;

    void setDrawables(List<DevicePolicyDrawableResource> list) throws RemoteException;

    void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence)
            throws RemoteException;

    void setFactoryResetProtectionPolicy(
            ComponentName componentName,
            String str,
            FactoryResetProtectionPolicy factoryResetProtectionPolicy)
            throws RemoteException;

    void setForceEphemeralUsers(ComponentName componentName, boolean z) throws RemoteException;

    int setGlobalPrivateDns(ComponentName componentName, int i, String str) throws RemoteException;

    ComponentName setGlobalProxy(ComponentName componentName, String str, String str2)
            throws RemoteException;

    void setGlobalSetting(ComponentName componentName, String str, String str2)
            throws RemoteException;

    void setKeepUninstalledPackages(ComponentName componentName, String str, List<String> list)
            throws RemoteException;

    boolean setKeyGrantForApp(
            ComponentName componentName, String str, String str2, String str3, boolean z)
            throws RemoteException;

    boolean setKeyGrantToWifiAuth(String str, String str2, boolean z) throws RemoteException;

    boolean setKeyPairCertificate(
            ComponentName componentName,
            String str,
            String str2,
            byte[] bArr,
            byte[] bArr2,
            boolean z)
            throws RemoteException;

    boolean setKeyguardDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setKeyguardDisabledFeatures(ComponentName componentName, String str, int i, boolean z)
            throws RemoteException;

    void setKeyguardDisabledFeaturesMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setLocationEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setLockTaskFeatures(ComponentName componentName, String str, int i) throws RemoteException;

    void setLockTaskPackages(ComponentName componentName, String str, String[] strArr)
            throws RemoteException;

    void setLogoutEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setLongSupportMessage(ComponentName componentName, CharSequence charSequence)
            throws RemoteException;

    void setManagedProfileCallerIdAccessPolicy(PackagePolicy packagePolicy) throws RemoteException;

    void setManagedProfileContactsAccessPolicy(PackagePolicy packagePolicy) throws RemoteException;

    void setManagedProfileMaximumTimeOff(ComponentName componentName, long j)
            throws RemoteException;

    void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy managedSubscriptionsPolicy)
            throws RemoteException;

    void setMasterVolumeMuted(ComponentName componentName, boolean z) throws RemoteException;

    void setMaxPolicyStorageLimit(String str, int i) throws RemoteException;

    void setMaximumFailedPasswordsForWipe(ComponentName componentName, String str, int i, boolean z)
            throws RemoteException;

    void setMaximumFailedPasswordsForWipeMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setMaximumTimeToLock(ComponentName componentName, String str, long j, boolean z)
            throws RemoteException;

    void setMaximumTimeToLockMDM(ComponentName componentName, long j, int i) throws RemoteException;

    List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list)
            throws RemoteException;

    void setMinimumRequiredWifiSecurityLevel(String str, int i) throws RemoteException;

    void setMtePolicy(int i, String str) throws RemoteException;

    void setNearbyAppStreamingPolicy(int i) throws RemoteException;

    void setNearbyNotificationStreamingPolicy(int i) throws RemoteException;

    void setNetworkLoggingEnabled(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    void setNextOperationSafety(int i, int i2) throws RemoteException;

    void setOrganizationColor(ComponentName componentName, int i) throws RemoteException;

    void setOrganizationColorForUser(int i, int i2) throws RemoteException;

    void setOrganizationIdForUser(String str, String str2, int i) throws RemoteException;

    void setOrganizationName(ComponentName componentName, String str, CharSequence charSequence)
            throws RemoteException;

    void setOverrideApnsEnabled(ComponentName componentName, boolean z) throws RemoteException;

    String[] setPackagesSuspended(
            ComponentName componentName, String str, String[] strArr, boolean z)
            throws RemoteException;

    void setPasswordExpirationTimeout(ComponentName componentName, String str, long j, boolean z)
            throws RemoteException;

    void setPasswordExpirationTimeoutMDM(ComponentName componentName, long j, int i)
            throws RemoteException;

    void setPasswordHistoryLength(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordHistoryLengthMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordMinimumLength(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordMinimumLengthMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordMinimumLetters(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordMinimumLettersMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordMinimumLowerCaseMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordMinimumNonLetterMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordMinimumNumeric(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordMinimumNumericMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordMinimumSymbols(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordMinimumSymbolsMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setPasswordMinimumUpperCaseMDM(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void setPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordQualityMDM(ComponentName componentName, int i, int i2) throws RemoteException;

    void setPermissionGrantState(
            ComponentName componentName,
            String str,
            String str2,
            String str3,
            int i,
            RemoteCallback remoteCallback)
            throws RemoteException;

    void setPermissionPolicy(ComponentName componentName, String str, int i) throws RemoteException;

    boolean setPermittedAccessibilityServices(ComponentName componentName, List<String> list)
            throws RemoteException;

    boolean setPermittedCrossProfileNotificationListeners(
            ComponentName componentName, List<String> list) throws RemoteException;

    boolean setPermittedInputMethods(
            ComponentName componentName, String str, List<String> list, boolean z)
            throws RemoteException;

    void setPersonalAppsSuspended(ComponentName componentName, boolean z) throws RemoteException;

    void setPreferentialNetworkServiceConfigs(List<PreferentialNetworkServiceConfig> list)
            throws RemoteException;

    void setProfileEnabled(ComponentName componentName) throws RemoteException;

    void setProfileName(ComponentName componentName, String str) throws RemoteException;

    boolean setProfileOwner(ComponentName componentName, int i) throws RemoteException;

    void setProfileOwnerOnOrganizationOwnedDevice(ComponentName componentName, int i, boolean z)
            throws RemoteException;

    void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo)
            throws RemoteException;

    void setRequiredPasswordComplexity(String str, int i, boolean z) throws RemoteException;

    void setRequiredStrongAuthTimeout(ComponentName componentName, String str, long j, boolean z)
            throws RemoteException;

    boolean setResetPasswordToken(ComponentName componentName, String str, byte[] bArr)
            throws RemoteException;

    boolean setResetPasswordTokenMDM(ComponentName componentName, byte[] bArr, int i)
            throws RemoteException;

    void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2)
            throws RemoteException;

    void setScreenCaptureDisabled(ComponentName componentName, String str, boolean z, boolean z2)
            throws RemoteException;

    void setSecondaryLockscreenEnabled(ComponentName componentName, boolean z)
            throws RemoteException;

    void setSecureSetting(ComponentName componentName, String str, String str2)
            throws RemoteException;

    void setSecurityLoggingEnabled(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    void setShortSupportMessage(ComponentName componentName, String str, CharSequence charSequence)
            throws RemoteException;

    void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence)
            throws RemoteException;

    boolean setStatusBarDisabled(ComponentName componentName, String str, boolean z)
            throws RemoteException;

    int setStorageEncryption(ComponentName componentName, boolean z) throws RemoteException;

    void setStrings(List<DevicePolicyStringResource> list) throws RemoteException;

    void setSystemSetting(ComponentName componentName, String str, String str2, boolean z)
            throws RemoteException;

    void setSystemUpdatePolicy(
            ComponentName componentName, String str, SystemUpdatePolicy systemUpdatePolicy)
            throws RemoteException;

    boolean setTime(ComponentName componentName, String str, long j) throws RemoteException;

    boolean setTimeZone(ComponentName componentName, String str, String str2)
            throws RemoteException;

    void setTrustAgentConfiguration(
            ComponentName componentName,
            String str,
            ComponentName componentName2,
            PersistableBundle persistableBundle,
            boolean z)
            throws RemoteException;

    void setTrustAgentConfigurationMDM(
            int i,
            ComponentName componentName,
            ComponentName componentName2,
            PersistableBundle persistableBundle)
            throws RemoteException;

    void setUninstallBlocked(ComponentName componentName, String str, String str2, boolean z)
            throws RemoteException;

    void setUsbDataSignalingEnabled(String str, boolean z) throws RemoteException;

    void setUserControlDisabledPackages(ComponentName componentName, String str, List<String> list)
            throws RemoteException;

    void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException;

    void setUserProvisioningState(int i, int i2) throws RemoteException;

    void setUserRestriction(
            ComponentName componentName, String str, String str2, boolean z, boolean z2)
            throws RemoteException;

    void setUserRestrictionForKnox(ComponentName componentName, String str, boolean z, int i)
            throws RemoteException;

    void setUserRestrictionGlobally(String str, String str2) throws RemoteException;

    void setWifiSsidPolicy(String str, WifiSsidPolicy wifiSsidPolicy) throws RemoteException;

    boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws RemoteException;

    void startManagedQuickContact(String str, long j, boolean z, long j2, Intent intent)
            throws RemoteException;

    int startUserInBackground(ComponentName componentName, UserHandle userHandle)
            throws RemoteException;

    boolean startViewCalendarEventInManagedProfile(
            String str, long j, long j2, long j3, boolean z, int i) throws RemoteException;

    int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void transferOwnership(
            ComponentName componentName,
            ComponentName componentName2,
            PersistableBundle persistableBundle)
            throws RemoteException;

    boolean triggerDevicePolicyEngineMigration(boolean z) throws RemoteException;

    void uninstallCaCerts(ComponentName componentName, String str, String[] strArr)
            throws RemoteException;

    void uninstallPackageWithActiveAdmins(String str) throws RemoteException;

    boolean updateOverrideApn(ComponentName componentName, int i, ApnSetting apnSetting)
            throws RemoteException;

    void wipeDataWithReason(String str, int i, String str2, boolean z, boolean z2)
            throws RemoteException;

    public static class Default implements IDevicePolicyManager {
        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordQuality(ComponentName who, int quality, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordQuality(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLength(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLength(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumUpperCase(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumUpperCase(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLowerCase(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLowerCase(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLetters(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLetters(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNumeric(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumNumeric(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumSymbols(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumSymbols(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNonLetter(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumNonLetter(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public PasswordMetrics getPasswordMinimumMetrics(int userHandle, boolean deviceWideOnly)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordHistoryLength(ComponentName who, int length, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordHistoryLength(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordExpirationTimeout(
                ComponentName who, String callerPackageName, long expiration, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public long getPasswordExpirationTimeout(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getPasswordExpiration(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isActivePasswordSufficient(
                String callerPackageName, int userHandle, boolean parent) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isActivePasswordSufficientForDeviceRequirement() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPasswordSufficientAfterProfileUnification(int userHandle, int profileUser)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordComplexity(boolean parent) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRequiredPasswordComplexity(
                String callerPackageName, int passwordComplexity, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getRequiredPasswordComplexity(String callerPackageName, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getAggregatedPasswordComplexityForUser(int userId, boolean deviceWideOnly)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUsingUnifiedPassword(ComponentName admin) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getCurrentFailedPasswordAttempts(
                String callerPackageName, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getCurrentFailedBiometricAttempts(int userHandle) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getProfileWithMinimumFailedPasswordsForWipe(int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumFailedPasswordsForWipe(
                ComponentName admin, String callerPackageName, int num, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getMaximumFailedPasswordsForWipe(
                ComponentName admin, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean resetPassword(String password, int flags) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumTimeToLock(
                ComponentName who, String callerPackageName, long timeMs, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public long getMaximumTimeToLock(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRequiredStrongAuthTimeout(
                ComponentName who, String callerPackageName, long timeMs, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public long getRequiredStrongAuthTimeout(ComponentName who, int userId, boolean parent)
                throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void lockNow(int flags, String callerPackageName, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void wipeDataWithReason(
                String callerPackageName,
                int flags,
                String wipeReasonForUser,
                boolean parent,
                boolean factoryReset)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setFactoryResetProtectionPolicy(
                ComponentName who, String callerPackageName, FactoryResetProtectionPolicy policy)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName who)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isFactoryResetProtectionPolicySupported() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void sendLostModeLocationUpdate(AndroidFuture<Boolean> future)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName setGlobalProxy(
                ComponentName admin, String proxySpec, String exclusionList)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getGlobalProxyAdmin(int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRecommendedGlobalProxy(ComponentName admin, ProxyInfo proxyInfo)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int setStorageEncryption(ComponentName who, boolean encrypt) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getStorageEncryption(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getStorageEncryptionStatus(String callerPackage, int userHandle)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean requestBugreport(ComponentName who) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCameraDisabled(
                ComponentName who, String callerPackageName, boolean disabled, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCameraDisabled(
                ComponentName who, String callerPackageName, int userHandle, boolean parent)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setScreenCaptureDisabled(
                ComponentName who, String callerPackageName, boolean disabled, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getScreenCaptureDisabled(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNearbyNotificationStreamingPolicy(int policy) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getNearbyNotificationStreamingPolicy(int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNearbyAppStreamingPolicy(int policy) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getNearbyAppStreamingPolicy(int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setKeyguardDisabledFeatures(
                ComponentName who, String callerPackageName, int which, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getKeyguardDisabledFeatures(ComponentName who, int userHandle, boolean parent)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setActiveAdmin(ComponentName policyReceiver, boolean refreshing, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAdminActive(ComponentName policyReceiver, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<ComponentName> getActiveAdmins(int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean packageHasActiveAdmins(String packageName, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void getRemoveWarning(
                ComponentName policyReceiver, RemoteCallback result, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void removeActiveAdmin(ComponentName policyReceiver, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void forceRemoveActiveAdmin(ComponentName policyReceiver, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasGrantedPolicy(
                ComponentName policyReceiver, int usesPolicy, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportPasswordChanged(PasswordMetrics metrics, int userId)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedPasswordAttempt(int userHandle, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void reportSuccessfulPasswordAttempt(int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedBiometricAttempt(int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void reportSuccessfulBiometricAttempt(int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void reportKeyguardDismissed(int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void reportKeyguardSecured(int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setDeviceOwner(
                ComponentName who, int userId, boolean setProfileOwnerOnCurrentUserIfNecessary)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getDeviceOwnerComponent(boolean callingUserOnly)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getDeviceOwnerComponentOnUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasDeviceOwner() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getDeviceOwnerName() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearDeviceOwner(String packageName) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getDeviceOwnerUserId() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setProfileOwner(ComponentName who, int userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getProfileOwnerAsUser(int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSupervisionComponent(ComponentName who) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getProfileOwnerName(int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileEnabled(ComponentName who) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileName(ComponentName who, String profileName) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void clearProfileOwner(ComponentName who) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasUserSetupCompleted() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isOrganizationOwnedDeviceWithManagedProfile() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean checkDeviceIdentifierAccess(String packageName, int pid, int uid)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceOwnerLockScreenInfo(ComponentName who, CharSequence deviceOwnerInfo)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String[] setPackagesSuspended(
                ComponentName admin, String callerPackage, String[] packageNames, boolean suspended)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPackageSuspended(
                ComponentName admin, String callerPackage, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> listPolicyExemptApps() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installCaCert(ComponentName admin, String callerPackage, byte[] certBuffer)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void uninstallCaCerts(ComponentName admin, String callerPackage, String[] aliases)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void enforceCanManageCaCerts(ComponentName admin, String callerPackage)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean approveCaCert(String alias, int userHandle, boolean approval)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCaCertApproved(String alias, int userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installKeyPair(
                ComponentName who,
                String callerPackage,
                byte[] privKeyBuffer,
                byte[] certBuffer,
                byte[] certChainBuffer,
                String alias,
                boolean requestAccess,
                boolean isUserSelectable)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeKeyPair(ComponentName who, String callerPackage, String alias)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasKeyPair(String callerPackage, String alias) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean generateKeyPair(
                ComponentName who,
                String callerPackage,
                String algorithm,
                ParcelableKeyGenParameterSpec keySpec,
                int idAttestationFlags,
                KeymasterCertificateChain attestationChain)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyPairCertificate(
                ComponentName who,
                String callerPackage,
                String alias,
                byte[] certBuffer,
                byte[] certChainBuffer,
                boolean isUserSelectable)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void choosePrivateKeyAlias(int uid, Uri uri, String alias, IBinder aliasCallback)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setDelegatedScopes(
                ComponentName who, String delegatePackage, List<String> scopes)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDelegatedScopes(ComponentName who, String delegatePackage)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDelegatePackages(ComponentName who, String scope)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCertInstallerPackage(ComponentName who, String installerPackage)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public String getCertInstallerPackage(ComponentName who) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setAlwaysOnVpnPackage(
                ComponentName who,
                String vpnPackage,
                boolean lockdown,
                List<String> lockdownAllowlist)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getAlwaysOnVpnPackage(ComponentName who) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getAlwaysOnVpnPackageForUser(int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAlwaysOnVpnLockdownEnabled(ComponentName who) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAlwaysOnVpnLockdownEnabledForUser(int userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName who)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void addPersistentPreferredActivity(
                ComponentName admin,
                String callerPackageName,
                IntentFilter filter,
                ComponentName activity)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void clearPackagePersistentPreferredActivities(
                ComponentName admin, String callerPackageName, String packageName)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setDefaultSmsApplication(
                ComponentName admin, String callerPackageName, String packageName, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setDefaultDialerApplication(String packageName) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setApplicationRestrictions(
                ComponentName who,
                String callerPackage,
                String packageName,
                Bundle settings,
                boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getApplicationRestrictions(
                ComponentName who, String callerPackage, String packageName, boolean parent)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setApplicationRestrictionsManagingPackage(
                ComponentName admin, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getApplicationRestrictionsManagingPackage(ComponentName admin)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCallerApplicationRestrictionsManagingPackage(String callerPackage)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRestrictionsProvider(ComponentName who, ComponentName provider)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public ComponentName getRestrictionsProvider(int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestriction(
                ComponentName who, String callerPackage, String key, boolean enable, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestrictionGlobally(String callerPackage, String key)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getUserRestrictions(ComponentName who, String callerPackage, boolean parent)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getUserRestrictionsGlobally(String callerPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void addCrossProfileIntentFilter(
                ComponentName admin, String callerPackageName, IntentFilter filter, int flags)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void clearCrossProfileIntentFilters(ComponentName admin, String callerPackageName)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedAccessibilityServices(
                ComponentName admin, List<String> packageList) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedAccessibilityServices(ComponentName admin)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedAccessibilityServicesForUser(int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAccessibilityServicePermittedByAdmin(
                ComponentName admin, String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedInputMethods(
                ComponentName admin,
                String callerPackageName,
                List<String> packageList,
                boolean parent)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedInputMethods(
                ComponentName admin, String callerPackageName, boolean parent)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedInputMethodsAsUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isInputMethodPermittedByAdmin(
                ComponentName admin, String packageName, int userId, boolean parent)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedCrossProfileNotificationListeners(
                ComponentName admin, List<String> packageList) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getPermittedCrossProfileNotificationListeners(ComponentName admin)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNotificationListenerServicePermitted(String packageName, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Intent createAdminSupportIntent(String restriction) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getEnforcingAdminAndUserDetails(int userId, String restriction)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<EnforcingAdmin> getEnforcingAdminsForRestriction(int userId, String restriction)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setApplicationHidden(
                ComponentName admin,
                String callerPackage,
                String packageName,
                boolean hidden,
                boolean parent)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isApplicationHidden(
                ComponentName admin, String callerPackage, String packageName, boolean parent)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public UserHandle createAndManageUser(
                ComponentName who,
                String name,
                ComponentName profileOwner,
                PersistableBundle adminExtras,
                int flags)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeUser(ComponentName who, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean switchUser(ComponentName who, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int startUserInBackground(ComponentName who, UserHandle userHandle)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int stopUser(ComponentName who, UserHandle userHandle) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int logoutUser(ComponentName who) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int logoutUserInternal() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getLogoutUserId() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> getSecondaryUsers(ComponentName who) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void acknowledgeNewUserDisclaimer(int userId) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNewUserDisclaimerAcknowledged(int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void enableSystemApp(ComponentName admin, String callerPackage, String packageName)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int enableSystemAppWithIntent(
                ComponentName admin, String callerPackage, Intent intent) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installExistingPackage(
                ComponentName admin, String callerPackage, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAccountManagementDisabled(
                ComponentName who,
                String callerPackageName,
                String accountType,
                boolean disabled,
                boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public String[] getAccountTypesWithManagementDisabled(String callerPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String[] getAccountTypesWithManagementDisabledAsUser(
                int userId, String callerPackageName, boolean parent) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecondaryLockscreenEnabled(ComponentName who, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSecondaryLockscreenEnabled(UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPreferentialNetworkServiceConfigs(
                List<PreferentialNetworkServiceConfig> preferentialNetworkServiceConfigs)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs()
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLockTaskPackages(
                ComponentName who, String callerPackageName, String[] packages)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public String[] getLockTaskPackages(ComponentName who, String callerPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isLockTaskPermitted(String pkg) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLockTaskFeatures(ComponentName who, String callerPackageName, int flags)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getLockTaskFeatures(ComponentName who, String callerPackageName)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setGlobalSetting(ComponentName who, String setting, String value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setSystemSetting(
                ComponentName who, String setting, String value, boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecureSetting(ComponentName who, String setting, String value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setConfiguredNetworksLockdownState(
                ComponentName who, String callerPackageName, boolean lockdown)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasLockdownAdminConfiguredNetworks(ComponentName who)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLocationEnabled(ComponentName who, boolean locationEnabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setTime(ComponentName who, String callerPackageName, long millis)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setTimeZone(ComponentName who, String callerPackageName, String timeZone)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMasterVolumeMuted(ComponentName admin, boolean on) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isMasterVolumeMuted(ComponentName admin) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void notifyLockTaskModeChanged(boolean isEnabled, String pkg, int userId)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setUninstallBlocked(
                ComponentName admin,
                String callerPackage,
                String packageName,
                boolean uninstallBlocked)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUninstallBlocked(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileCallerIdDisabled(ComponentName who, boolean disabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileCallerIdDisabled(ComponentName who) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileCallerIdDisabledForUser(int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileContactsSearchDisabled(ComponentName who, boolean disabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileContactsSearchDisabled(ComponentName who)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileContactsSearchDisabledForUser(int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void startManagedQuickContact(
                String lookupKey,
                long contactId,
                boolean isContactIdIgnored,
                long directoryId,
                Intent originalIntent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileCallerIdAccessPolicy(PackagePolicy policy)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public PackagePolicy getManagedProfileCallerIdAccessPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasManagedProfileCallerIdAccess(int userId, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCredentialManagerPolicy(PackagePolicy policy) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public PackagePolicy getCredentialManagerPolicy(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileContactsAccessPolicy(PackagePolicy policy)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public PackagePolicy getManagedProfileContactsAccessPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasManagedProfileContactsAccess(int userId, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setBluetoothContactSharingDisabled(ComponentName who, boolean disabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getBluetoothContactSharingDisabled(ComponentName who)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getBluetoothContactSharingDisabledForUser(int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setTrustAgentConfiguration(
                ComponentName admin,
                String callerPackageName,
                ComponentName agent,
                PersistableBundle args,
                boolean parent)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<PersistableBundle> getTrustAgentConfiguration(
                ComponentName admin, ComponentName agent, int userId, boolean parent)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean addCrossProfileWidgetProvider(
                ComponentName admin, String callerPackageName, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeCrossProfileWidgetProvider(
                ComponentName admin, String callerPackageName, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfileWidgetProviders(
                ComponentName admin, String callerPackageName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeRequired(ComponentName who, boolean required)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeRequired() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeEnabled(ComponentName who, String callerPackageName, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeEnabled(ComponentName who, String callerPackageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeZoneEnabled(
                ComponentName who, String callerPackageName, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeZoneEnabled(ComponentName who, String callerPackageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setForceEphemeralUsers(ComponentName who, boolean forceEpehemeralUsers)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getForceEphemeralUsers(ComponentName who) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isRemovingAdmin(ComponentName adminReceiver, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserIcon(ComponentName admin, Bitmap icon) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setSystemUpdatePolicy(
                ComponentName who, String callerPackageName, SystemUpdatePolicy policy)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyguardDisabled(ComponentName admin, boolean disabled)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setStatusBarDisabled(
                ComponentName who, String callerPackageName, boolean disabled)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isStatusBarDisabled(String callerPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void notifyPendingSystemUpdate(SystemUpdateInfo info) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public SystemUpdateInfo getPendingSystemUpdate(ComponentName admin, String callerPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPermissionPolicy(ComponentName admin, String callerPackage, int policy)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPermissionPolicy(ComponentName admin) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPermissionGrantState(
                ComponentName admin,
                String callerPackage,
                String packageName,
                String permission,
                int grantState,
                RemoteCallback resultReceiver)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getPermissionGrantState(
                ComponentName admin, String callerPackage, String packageName, String permission)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isProvisioningAllowed(String action, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int checkProvisioningPrecondition(String action, String packageName)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setKeepUninstalledPackages(
                ComponentName admin, String callerPackage, List<String> packageList)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getKeepUninstalledPackages(ComponentName admin, String callerPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isManagedProfile(ComponentName admin) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getWifiMacAddress(ComponentName admin, String callerPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reboot(ComponentName admin) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setShortSupportMessage(
                ComponentName admin, String callerPackageName, CharSequence message)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getShortSupportMessage(ComponentName admin, String callerPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLongSupportMessage(ComponentName admin, CharSequence message)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getLongSupportMessage(ComponentName admin) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getShortSupportMessageForUser(ComponentName admin, int userHandle)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getLongSupportMessageForUser(ComponentName admin, int userHandle)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationColor(ComponentName admin, int color) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationColorForUser(int color, int userId) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void clearOrganizationIdForUser(int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getOrganizationColor(ComponentName admin) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getOrganizationColorForUser(int userHandle) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationName(
                ComponentName admin, String callerPackageName, CharSequence title)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getOrganizationName(ComponentName admin, String callerPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getOrganizationNameForUser(int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getUserProvisioningState(int userHandle) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserProvisioningState(int state, int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setAffiliationIds(ComponentName admin, List<String> ids)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getAffiliationIds(ComponentName admin) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCallingUserAffiliated() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAffiliatedUser(int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecurityLoggingEnabled(
                ComponentName admin, String packageName, boolean enabled) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSecurityLoggingEnabled(ComponentName admin, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParceledListSlice retrieveSecurityLogs(ComponentName admin, String packageName)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParceledListSlice retrievePreRebootSecurityLogs(
                ComponentName admin, String packageName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long forceNetworkLogs() throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long forceSecurityLogs() throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAuditLogEnabled(String callerPackage, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAuditLogEnabled(String callerPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAuditLogEventsCallback(
                String callerPackage, IAuditLogEventsCallback callback) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUninstallInQueue(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void uninstallPackageWithActiveAdmins(String packageName) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceProvisioned() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceProvisioningConfigApplied() throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void forceUpdateUserSetupComplete(int userId) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setBackupServiceEnabled(ComponentName admin, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isBackupServiceEnabled(ComponentName admin) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNetworkLoggingEnabled(
                ComponentName admin, String packageName, boolean enabled) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNetworkLoggingEnabled(ComponentName admin, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<NetworkEvent> retrieveNetworkLogs(
                ComponentName admin, String packageName, long batchToken) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean bindDeviceAdminServiceAsUser(
                ComponentName admin,
                IApplicationThread caller,
                IBinder token,
                Intent service,
                IServiceConnection connection,
                long flags,
                int targetUserId)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName admin)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isEphemeralUser(ComponentName admin) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getLastSecurityLogRetrievalTime() throws RemoteException {
            return 0L;
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
        public boolean setResetPasswordToken(
                ComponentName admin, String callerPackageName, byte[] token)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean clearResetPasswordToken(ComponentName admin, String callerPackageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isResetPasswordTokenActive(ComponentName admin, String callerPackageName)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean resetPasswordWithToken(
                ComponentName admin,
                String callerPackageName,
                String password,
                byte[] token,
                int flags)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle user)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearApplicationUserData(
                ComponentName admin, String packageName, IPackageDataObserver callback)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setLogoutEnabled(ComponentName admin, boolean enabled) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isLogoutEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDisallowedSystemApps(
                ComponentName admin, int userId, String provisioningAction) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void transferOwnership(
                ComponentName admin, ComponentName target, PersistableBundle bundle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setStartUserSessionMessage(
                ComponentName admin, CharSequence startUserSessionMessage) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setEndUserSessionMessage(
                ComponentName admin, CharSequence endUserSessionMessage) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getStartUserSessionMessage(ComponentName admin) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public CharSequence getEndUserSessionMessage(ComponentName admin) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> setMeteredDataDisabledPackages(
                ComponentName admin, List<String> packageNames) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getMeteredDataDisabledPackages(ComponentName admin)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int addOverrideApn(ComponentName admin, ApnSetting apnSetting)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean updateOverrideApn(ComponentName admin, int apnId, ApnSetting apnSetting)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeOverrideApn(ComponentName admin, int apnId) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<ApnSetting> getOverrideApns(ComponentName admin) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOverrideApnsEnabled(ComponentName admin, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isOverrideApnEnabled(ComponentName admin) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isMeteredDataDisabledPackageForUser(
                ComponentName admin, String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordQualityMDM(ComponentName admin, int quality, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLengthMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumUpperCaseMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLowerCaseMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLettersMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNumericMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumSymbolsMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNonLetterMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordHistoryLengthMDM(ComponentName admin, int length, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordExpirationTimeoutMDM(
                ComponentName admin, long expiration, int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumFailedPasswordsForWipeMDM(
                ComponentName admin, int num, int userHandle) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumTimeToLockMDM(ComponentName admin, long timeMs, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setKeyguardDisabledFeaturesMDM(ComponentName admin, int which, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setApplicationRestrictionsMDM(
                ComponentName admin, String packageName, Bundle settings, int userHandle)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public Bundle getApplicationRestrictionsMDM(
                ComponentName who, String packageName, int userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean resetPasswordWithTokenMDM(
                ComponentName admin, String password, byte[] token, int flags, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isResetPasswordTokenActiveMDM(ComponentName admin, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean clearResetPasswordTokenMDM(ComponentName admin, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setResetPasswordTokenMDM(ComponentName admin, byte[] token, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setTrustAgentConfigurationMDM(
                int userId, ComponentName admin, ComponentName agent, PersistableBundle args)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isProfileOwnerOfOrganizationOwnedDeviceMDM(int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasDelegatedPermission(String callerPackage, int callerUid, String scope)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public Map getDelegatedPackages(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedPasswordAttemptWithFailureCount(
                int userHandle, int count, boolean parent) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int setGlobalPrivateDns(ComponentName admin, int mode, String privateDnsHost)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getGlobalPrivateDnsMode(ComponentName admin) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getGlobalPrivateDnsHost(ComponentName admin) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileOwnerOnOrganizationOwnedDevice(
                ComponentName who, int userId, boolean isProfileOwnerOnOrganizationOwnedDevice)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void installUpdateFromFile(
                ComponentName admin,
                String callerPackageName,
                ParcelFileDescriptor updateFileDescriptor,
                StartInstallingUpdateCallback listener)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileCalendarPackages(ComponentName admin, List<String> packageNames)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfileCalendarPackages(ComponentName admin)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPackageAllowedToAccessCalendarForUser(String packageName, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfileCalendarPackagesForUser(int userHandle)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfilePackages(ComponentName admin, List<String> packageNames)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getCrossProfilePackages(ComponentName admin) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getAllCrossProfilePackages(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getDefaultCrossProfilePackages() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isManagedKiosk() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUnattendedManagedKiosk() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean startViewCalendarEventInManagedProfile(
                String packageName, long eventId, long start, long end, boolean allDay, int flags)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyGrantForApp(
                ComponentName admin,
                String callerPackage,
                String alias,
                String packageName,
                boolean hasGrant)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public ParcelableGranteeMap getKeyPairGrants(String callerPackage, String alias)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyGrantToWifiAuth(String callerPackage, String alias, boolean hasGrant)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isKeyPairGrantedToWifiAuth(String callerPackage, String alias)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserControlDisabledPackages(
                ComponentName admin, String callerPackageName, List<String> packages)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public List<String> getUserControlDisabledPackages(
                ComponentName admin, String callerPackageName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCommonCriteriaModeEnabled(
                ComponentName admin, String callerPackageName, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCommonCriteriaModeEnabled(ComponentName admin) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPersonalAppsSuspendedReasons(ComponentName admin) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPersonalAppsSuspended(ComponentName admin, boolean suspended)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public long getManagedProfileMaximumTimeOff(ComponentName admin) throws RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileMaximumTimeOff(ComponentName admin, long timeoutMs)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void acknowledgeDeviceCompliant() throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isComplianceAcknowledgementRequired() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canProfileOwnerResetPasswordWhenLocked(int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNextOperationSafety(int operation, int reason) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSafeOperation(int reason) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getEnrollmentSpecificId(String callerPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationIdForUser(String callerPackage, String enterpriseId, int userId)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public UserHandle createAndProvisionManagedProfile(
                ManagedProfileProvisioningParams provisioningParams, String callerPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void provisionFullyManagedDevice(
                FullyManagedDeviceProvisioningParams provisioningParams, String callerPackage)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void finalizeWorkProfileProvisioning(
                UserHandle managedProfileUser, Account migratedAccount) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceOwnerType(ComponentName admin, int deviceOwnerType)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getDeviceOwnerType(ComponentName admin) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetDefaultCrossProfileIntentFilters(int userId) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canAdminGrantSensorsPermissions() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUsbDataSignalingEnabled(String callerPackage, boolean enabled)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUsbDataSignalingEnabled(String callerPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canUsbDataSignalingBeDisabled() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMinimumRequiredWifiSecurityLevel(String callerPackageName, int level)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getMinimumRequiredWifiSecurityLevel() throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setWifiSsidPolicy(String callerPackageName, WifiSsidPolicy policy)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public WifiSsidPolicy getWifiSsidPolicy(String callerPackageName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDevicePotentiallyStolen(String callerPackageName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> listForegroundAffiliatedUsers() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDrawables(List<DevicePolicyDrawableResource> drawables)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void resetDrawables(List<String> drawableIds) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public ParcelableResource getDrawable(
                String drawableId, String drawableStyle, String drawableSource)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDpcDownloaded() throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDpcDownloaded(boolean downloaded) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setStrings(List<DevicePolicyStringResource> strings) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void resetStrings(List<String> stringIds) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public ParcelableResource getString(String stringId) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState()
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification()
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordQuality(ComponentName admin, int quality)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumLength(ComponentName admin, int length)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumUpperCase(ComponentName admin, int length)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumLowerCase(ComponentName admin, int length)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordMinimumNonLetter(ComponentName admin, int length)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordHistoryLength(ComponentName admin, int length)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetPasswordExpirationTimeout(ComponentName admin, long timeout)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semIsActivePasswordSufficient(int userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetSimplePasswordEnabled(ComponentName who, boolean value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semIsSimplePasswordEnabled(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetKeyguardDisabledFeatures(ComponentName who, int which)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowStorageCard(ComponentName who, boolean value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowStorageCard(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowWifi(ComponentName who, boolean value) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowWifi(ComponentName who, int userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowTextMessaging(ComponentName who, boolean value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowTextMessaging(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowPopImapEmail(ComponentName who, boolean value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowPopImapEmail(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowBrowser(ComponentName who, boolean value) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowBrowser(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowInternetSharing(ComponentName who, boolean value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowInternetSharing(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowBluetoothMode(ComponentName who, int size) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int semGetAllowBluetoothMode(ComponentName who, int userHandle)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowDesktopSync(ComponentName who, boolean value)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowDesktopSync(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetAllowIrda(ComponentName who, boolean value) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetAllowIrda(ComponentName who, int userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetRequireStorageCardEncryption(
                ComponentName who, boolean value, boolean isParent) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean semGetRequireStorageCardEncryption(
                ComponentName who, int userHandle, boolean isParent) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void semSetChangeNotificationEnabled(ComponentName who, boolean notifyChanges)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setApplicationExemptions(
                String callerPackage, String packageName, int[] exemptions)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int[] getApplicationExemptions(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMtePolicy(int flag, String callerPackageName) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getMtePolicy(String callerPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy policy)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public DevicePolicyState getDevicePolicyState() throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean triggerDevicePolicyEngineMigration(boolean forceMigration)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceFinanced(String callerPackageName) throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public String getFinancedDeviceKioskRoleHolder(String callerPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestrictionForKnox(
                ComponentName who, String key, boolean enable, int userId) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileAppToIgnored(int userId, String packageName)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getSamsungSDcardEncryptionStatus(ComponentName who, int userHandle)
                throws RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void calculateHasIncompatibleAccounts() throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void setContentProtectionPolicy(
                ComponentName who, String callerPackageName, int policy) throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getContentProtectionPolicy(ComponentName who, String callerPackageName)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int[] getSubscriptionIds(String callerPackageName) throws RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaxPolicyStorageLimit(String callerPackageName, int storageLimit)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public void forceSetMaxPolicyStorageLimit(String callerPackageName, int storageLimit)
                throws RemoteException {}

        @Override // android.app.admin.IDevicePolicyManager
        public int getMaxPolicyStorageLimit(String callerPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPolicySizeForAdmin(String callerPackageName, EnforcingAdmin admin)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getHeadlessDeviceOwnerMode(String callerPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDevicePolicyManager {
        public static final String DESCRIPTOR = "android.app.admin.IDevicePolicyManager";
        static final int TRANSACTION_acknowledgeDeviceCompliant = 373;
        static final int TRANSACTION_acknowledgeNewUserDisclaimer = 166;
        static final int TRANSACTION_addCrossProfileIntentFilter = 139;
        static final int TRANSACTION_addCrossProfileWidgetProvider = 216;
        static final int TRANSACTION_addOverrideApn = 314;
        static final int TRANSACTION_addPersistentPreferredActivity = 124;
        static final int TRANSACTION_approveCaCert = 105;
        static final int TRANSACTION_bindDeviceAdminServiceAsUser = 290;
        static final int TRANSACTION_calculateHasIncompatibleAccounts = 452;
        static final int TRANSACTION_canAdminGrantSensorsPermissions = 386;
        static final int TRANSACTION_canProfileOwnerResetPasswordWhenLocked = 375;
        static final int TRANSACTION_canUsbDataSignalingBeDisabled = 389;
        static final int TRANSACTION_checkDeviceIdentifierAccess = 96;
        static final int TRANSACTION_checkProvisioningPrecondition = 243;
        static final int TRANSACTION_choosePrivateKeyAlias = 112;
        static final int TRANSACTION_clearApplicationUserData = 302;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 140;
        static final int TRANSACTION_clearDeviceOwner = 84;
        static final int TRANSACTION_clearOrganizationIdForUser = 257;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 125;
        static final int TRANSACTION_clearProfileOwner = 93;
        static final int TRANSACTION_clearResetPasswordToken = 297;
        static final int TRANSACTION_clearResetPasswordTokenMDM = 338;
        static final int TRANSACTION_clearSystemUpdatePolicyFreezePeriodRecord = 231;
        static final int TRANSACTION_createAdminSupportIntent = 152;
        static final int TRANSACTION_createAndManageUser = 157;
        static final int TRANSACTION_createAndProvisionManagedProfile = 380;
        static final int TRANSACTION_enableSystemApp = 168;
        static final int TRANSACTION_enableSystemAppWithIntent = 169;
        static final int TRANSACTION_enforceCanManageCaCerts = 104;
        static final int TRANSACTION_finalizeWorkProfileProvisioning = 382;
        static final int TRANSACTION_forceNetworkLogs = 274;
        static final int TRANSACTION_forceRemoveActiveAdmin = 70;
        static final int TRANSACTION_forceSecurityLogs = 275;
        static final int TRANSACTION_forceSetMaxPolicyStorageLimit = 457;
        static final int TRANSACTION_forceUpdateUserSetupComplete = 284;
        static final int TRANSACTION_generateKeyPair = 110;
        static final int TRANSACTION_getAccountTypesWithManagementDisabled = 172;
        static final int TRANSACTION_getAccountTypesWithManagementDisabledAsUser = 173;
        static final int TRANSACTION_getActiveAdmins = 66;
        static final int TRANSACTION_getAffiliationIds = 267;
        static final int TRANSACTION_getAggregatedPasswordComplexityForUser = 29;
        static final int TRANSACTION_getAllCrossProfilePackages = 356;
        static final int TRANSACTION_getAlwaysOnVpnLockdownAllowlist = 123;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 119;
        static final int TRANSACTION_getAlwaysOnVpnPackageForUser = 120;
        static final int TRANSACTION_getApplicationExemptions = 440;
        static final int TRANSACTION_getApplicationRestrictions = 129;
        static final int TRANSACTION_getApplicationRestrictionsMDM = 335;
        static final int TRANSACTION_getApplicationRestrictionsManagingPackage = 131;
        static final int TRANSACTION_getAutoTimeEnabled = 222;
        static final int TRANSACTION_getAutoTimeRequired = 220;
        static final int TRANSACTION_getAutoTimeZoneEnabled = 224;
        static final int TRANSACTION_getBindDeviceAdminTargetUsers = 291;
        static final int TRANSACTION_getBluetoothContactSharingDisabled = 212;
        static final int TRANSACTION_getBluetoothContactSharingDisabledForUser = 213;
        static final int TRANSACTION_getCameraDisabled = 55;
        static final int TRANSACTION_getCertInstallerPackage = 117;
        static final int TRANSACTION_getContentProtectionPolicy = 454;
        static final int TRANSACTION_getCredentialManagerPolicy = 207;
        static final int TRANSACTION_getCrossProfileCalendarPackages = 351;
        static final int TRANSACTION_getCrossProfileCalendarPackagesForUser = 353;
        static final int TRANSACTION_getCrossProfileCallerIdDisabled = 197;
        static final int TRANSACTION_getCrossProfileCallerIdDisabledForUser = 198;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabled = 200;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabledForUser = 201;
        static final int TRANSACTION_getCrossProfilePackages = 355;
        static final int TRANSACTION_getCrossProfileWidgetProviders = 218;
        static final int TRANSACTION_getCurrentFailedBiometricAttempts = 32;
        static final int TRANSACTION_getCurrentFailedPasswordAttempts = 31;
        static final int TRANSACTION_getDefaultCrossProfilePackages = 357;
        static final int TRANSACTION_getDelegatePackages = 115;
        static final int TRANSACTION_getDelegatedPackages = 343;
        static final int TRANSACTION_getDelegatedScopes = 114;
        static final int TRANSACTION_getDeviceOwnerComponent = 80;
        static final int TRANSACTION_getDeviceOwnerComponentOnUser = 81;
        static final int TRANSACTION_getDeviceOwnerLockScreenInfo = 98;
        static final int TRANSACTION_getDeviceOwnerName = 83;
        static final int TRANSACTION_getDeviceOwnerOrganizationName = 262;
        static final int TRANSACTION_getDeviceOwnerType = 384;
        static final int TRANSACTION_getDeviceOwnerUserId = 85;
        static final int TRANSACTION_getDevicePolicyState = 445;
        static final int TRANSACTION_getDisallowedSystemApps = 305;
        static final int TRANSACTION_getDoNotAskCredentialsOnBoot = 235;
        static final int TRANSACTION_getDrawable = 398;
        static final int TRANSACTION_getEndUserSessionMessage = 311;
        static final int TRANSACTION_getEnforcingAdminAndUserDetails = 153;
        static final int TRANSACTION_getEnforcingAdminsForRestriction = 154;
        static final int TRANSACTION_getEnrollmentSpecificId = 378;
        static final int TRANSACTION_getFactoryResetProtectionPolicy = 44;
        static final int TRANSACTION_getFinancedDeviceKioskRoleHolder = 448;
        static final int TRANSACTION_getForceEphemeralUsers = 226;
        static final int TRANSACTION_getGlobalPrivateDnsHost = 347;
        static final int TRANSACTION_getGlobalPrivateDnsMode = 346;
        static final int TRANSACTION_getGlobalProxyAdmin = 48;
        static final int TRANSACTION_getHeadlessDeviceOwnerMode = 460;
        static final int TRANSACTION_getKeepUninstalledPackages = 245;
        static final int TRANSACTION_getKeyPairGrants = 362;
        static final int TRANSACTION_getKeyguardDisabledFeatures = 63;
        static final int TRANSACTION_getLastBugReportRequestTime = 294;
        static final int TRANSACTION_getLastNetworkLogRetrievalTime = 295;
        static final int TRANSACTION_getLastSecurityLogRetrievalTime = 293;
        static final int TRANSACTION_getLockTaskFeatures = 182;
        static final int TRANSACTION_getLockTaskPackages = 179;
        static final int TRANSACTION_getLogoutUserId = 164;
        static final int TRANSACTION_getLongSupportMessage = 252;
        static final int TRANSACTION_getLongSupportMessageForUser = 254;
        static final int TRANSACTION_getManagedProfileCallerIdAccessPolicy = 204;
        static final int TRANSACTION_getManagedProfileContactsAccessPolicy = 209;
        static final int TRANSACTION_getManagedProfileMaximumTimeOff = 371;
        static final int TRANSACTION_getManagedSubscriptionsPolicy = 444;
        static final int TRANSACTION_getMaxPolicyStorageLimit = 458;
        static final int TRANSACTION_getMaximumFailedPasswordsForWipe = 35;
        static final int TRANSACTION_getMaximumTimeToLock = 38;
        static final int TRANSACTION_getMeteredDataDisabledPackages = 313;
        static final int TRANSACTION_getMinimumRequiredWifiSecurityLevel = 391;
        static final int TRANSACTION_getMtePolicy = 442;
        static final int TRANSACTION_getNearbyAppStreamingPolicy = 61;
        static final int TRANSACTION_getNearbyNotificationStreamingPolicy = 59;
        static final int TRANSACTION_getOrganizationColor = 258;
        static final int TRANSACTION_getOrganizationColorForUser = 259;
        static final int TRANSACTION_getOrganizationName = 261;
        static final int TRANSACTION_getOrganizationNameForUser = 263;
        static final int TRANSACTION_getOverrideApns = 317;
        static final int TRANSACTION_getOwnerInstalledCaCerts = 301;
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
        static final int TRANSACTION_getPendingSystemUpdate = 237;
        static final int TRANSACTION_getPermissionGrantState = 241;
        static final int TRANSACTION_getPermissionPolicy = 239;
        static final int TRANSACTION_getPermittedAccessibilityServices = 142;
        static final int TRANSACTION_getPermittedAccessibilityServicesForUser = 143;
        static final int TRANSACTION_getPermittedCrossProfileNotificationListeners = 150;
        static final int TRANSACTION_getPermittedInputMethods = 146;
        static final int TRANSACTION_getPermittedInputMethodsAsUser = 147;
        static final int TRANSACTION_getPersonalAppsSuspendedReasons = 369;
        static final int TRANSACTION_getPolicyManagedProfiles = 406;
        static final int TRANSACTION_getPolicySizeForAdmin = 459;
        static final int TRANSACTION_getPreferentialNetworkServiceConfigs = 177;
        static final int TRANSACTION_getProfileOwnerAsUser = 87;
        static final int TRANSACTION_getProfileOwnerName = 90;
        static final int TRANSACTION_getProfileOwnerOrDeviceOwnerSupervisionComponent = 88;
        static final int TRANSACTION_getProfileWithMinimumFailedPasswordsForWipe = 33;
        static final int TRANSACTION_getRemoveWarning = 68;
        static final int TRANSACTION_getRequiredPasswordComplexity = 28;
        static final int TRANSACTION_getRequiredStrongAuthTimeout = 40;
        static final int TRANSACTION_getRestrictionsProvider = 134;
        static final int TRANSACTION_getSamsungSDcardEncryptionStatus = 451;
        static final int TRANSACTION_getScreenCaptureDisabled = 57;
        static final int TRANSACTION_getSecondaryUsers = 165;
        static final int TRANSACTION_getShortSupportMessage = 250;
        static final int TRANSACTION_getShortSupportMessageForUser = 253;
        static final int TRANSACTION_getStartUserSessionMessage = 310;
        static final int TRANSACTION_getStorageEncryption = 51;
        static final int TRANSACTION_getStorageEncryptionStatus = 52;
        static final int TRANSACTION_getString = 403;
        static final int TRANSACTION_getSubscriptionIds = 455;
        static final int TRANSACTION_getSystemUpdatePolicy = 230;
        static final int TRANSACTION_getTransferOwnershipBundle = 307;
        static final int TRANSACTION_getTrustAgentConfiguration = 215;
        static final int TRANSACTION_getUserControlDisabledPackages = 366;
        static final int TRANSACTION_getUserProvisioningState = 264;
        static final int TRANSACTION_getUserRestrictions = 137;
        static final int TRANSACTION_getUserRestrictionsGlobally = 138;
        static final int TRANSACTION_getWifiMacAddress = 247;
        static final int TRANSACTION_getWifiSsidPolicy = 393;
        static final int TRANSACTION_hasDelegatedPermission = 342;
        static final int TRANSACTION_hasDeviceOwner = 82;
        static final int TRANSACTION_hasGrantedPolicy = 71;
        static final int TRANSACTION_hasKeyPair = 109;
        static final int TRANSACTION_hasLockdownAdminConfiguredNetworks = 187;
        static final int TRANSACTION_hasManagedProfileCallerIdAccess = 205;
        static final int TRANSACTION_hasManagedProfileContactsAccess = 210;
        static final int TRANSACTION_hasUserSetupCompleted = 94;
        static final int TRANSACTION_installCaCert = 102;
        static final int TRANSACTION_installExistingPackage = 170;
        static final int TRANSACTION_installKeyPair = 107;
        static final int TRANSACTION_installUpdateFromFile = 349;
        static final int TRANSACTION_isAccessibilityServicePermittedByAdmin = 144;
        static final int TRANSACTION_isActivePasswordSufficient = 23;
        static final int TRANSACTION_isActivePasswordSufficientForDeviceRequirement = 24;
        static final int TRANSACTION_isAdminActive = 65;
        static final int TRANSACTION_isAffiliatedUser = 269;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabled = 121;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabledForUser = 122;
        static final int TRANSACTION_isApplicationHidden = 156;
        static final int TRANSACTION_isAuditLogEnabled = 277;
        static final int TRANSACTION_isBackupServiceEnabled = 286;
        static final int TRANSACTION_isCaCertApproved = 106;
        static final int TRANSACTION_isCallerApplicationRestrictionsManagingPackage = 132;
        static final int TRANSACTION_isCallingUserAffiliated = 268;
        static final int TRANSACTION_isCommonCriteriaModeEnabled = 368;
        static final int TRANSACTION_isComplianceAcknowledgementRequired = 374;
        static final int TRANSACTION_isCurrentInputMethodSetByOwner = 300;
        static final int TRANSACTION_isDeviceFinanced = 447;
        static final int TRANSACTION_isDevicePotentiallyStolen = 394;
        static final int TRANSACTION_isDeviceProvisioned = 281;
        static final int TRANSACTION_isDeviceProvisioningConfigApplied = 282;
        static final int TRANSACTION_isDpcDownloaded = 399;
        static final int TRANSACTION_isEphemeralUser = 292;
        static final int TRANSACTION_isFactoryResetProtectionPolicySupported = 45;
        static final int TRANSACTION_isInputMethodPermittedByAdmin = 148;
        static final int TRANSACTION_isKeyPairGrantedToWifiAuth = 364;
        static final int TRANSACTION_isLockTaskPermitted = 180;
        static final int TRANSACTION_isLogoutEnabled = 304;
        static final int TRANSACTION_isManagedKiosk = 358;
        static final int TRANSACTION_isManagedProfile = 246;
        static final int TRANSACTION_isMasterVolumeMuted = 192;
        static final int TRANSACTION_isMeteredDataDisabledPackageForUser = 320;
        static final int TRANSACTION_isNetworkLoggingEnabled = 288;
        static final int TRANSACTION_isNewUserDisclaimerAcknowledged = 167;
        static final int TRANSACTION_isNotificationListenerServicePermitted = 151;
        static final int TRANSACTION_isOrganizationOwnedDeviceWithManagedProfile = 95;
        static final int TRANSACTION_isOverrideApnEnabled = 319;
        static final int TRANSACTION_isPackageAllowedToAccessCalendarForUser = 352;
        static final int TRANSACTION_isPackageSuspended = 100;
        static final int TRANSACTION_isPasswordSufficientAfterProfileUnification = 25;
        static final int TRANSACTION_isProfileOwnerOfOrganizationOwnedDeviceMDM = 341;
        static final int TRANSACTION_isProvisioningAllowed = 242;
        static final int TRANSACTION_isRemovingAdmin = 227;
        static final int TRANSACTION_isResetPasswordTokenActive = 298;
        static final int TRANSACTION_isResetPasswordTokenActiveMDM = 337;
        static final int TRANSACTION_isSafeOperation = 377;
        static final int TRANSACTION_isSecondaryLockscreenEnabled = 175;
        static final int TRANSACTION_isSecurityLoggingEnabled = 271;
        static final int TRANSACTION_isStatusBarDisabled = 234;
        static final int TRANSACTION_isSupervisionComponent = 89;
        static final int TRANSACTION_isUnattendedManagedKiosk = 359;
        static final int TRANSACTION_isUninstallBlocked = 195;
        static final int TRANSACTION_isUninstallInQueue = 279;
        static final int TRANSACTION_isUsbDataSignalingEnabled = 388;
        static final int TRANSACTION_isUsingUnifiedPassword = 30;
        static final int TRANSACTION_listForegroundAffiliatedUsers = 395;
        static final int TRANSACTION_listPolicyExemptApps = 101;
        static final int TRANSACTION_lockNow = 41;
        static final int TRANSACTION_logoutUser = 162;
        static final int TRANSACTION_logoutUserInternal = 163;
        static final int TRANSACTION_notifyLockTaskModeChanged = 193;
        static final int TRANSACTION_notifyPendingSystemUpdate = 236;
        static final int TRANSACTION_packageHasActiveAdmins = 67;
        static final int TRANSACTION_provisionFullyManagedDevice = 381;
        static final int TRANSACTION_reboot = 248;
        static final int TRANSACTION_removeActiveAdmin = 69;
        static final int TRANSACTION_removeCrossProfileWidgetProvider = 217;
        static final int TRANSACTION_removeKeyPair = 108;
        static final int TRANSACTION_removeOverrideApn = 316;
        static final int TRANSACTION_removeUser = 158;
        static final int TRANSACTION_reportFailedBiometricAttempt = 75;
        static final int TRANSACTION_reportFailedPasswordAttempt = 73;
        static final int TRANSACTION_reportFailedPasswordAttemptWithFailureCount = 344;
        static final int TRANSACTION_reportKeyguardDismissed = 77;
        static final int TRANSACTION_reportKeyguardSecured = 78;
        static final int TRANSACTION_reportPasswordChanged = 72;
        static final int TRANSACTION_reportSuccessfulBiometricAttempt = 76;
        static final int TRANSACTION_reportSuccessfulPasswordAttempt = 74;
        static final int TRANSACTION_requestBugreport = 53;
        static final int TRANSACTION_resetDefaultCrossProfileIntentFilters = 385;
        static final int TRANSACTION_resetDrawables = 397;
        static final int TRANSACTION_resetPassword = 36;
        static final int TRANSACTION_resetPasswordWithToken = 299;
        static final int TRANSACTION_resetPasswordWithTokenMDM = 336;
        static final int
                TRANSACTION_resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState =
                        404;
        static final int TRANSACTION_resetStrings = 402;
        static final int TRANSACTION_retrieveNetworkLogs = 289;
        static final int TRANSACTION_retrievePreRebootSecurityLogs = 273;
        static final int TRANSACTION_retrieveSecurityLogs = 272;
        static final int TRANSACTION_semGetAllowBluetoothMode = 431;
        static final int TRANSACTION_semGetAllowBrowser = 427;
        static final int TRANSACTION_semGetAllowDesktopSync = 433;
        static final int TRANSACTION_semGetAllowInternetSharing = 429;
        static final int TRANSACTION_semGetAllowIrda = 435;
        static final int TRANSACTION_semGetAllowPopImapEmail = 425;
        static final int TRANSACTION_semGetAllowStorageCard = 419;
        static final int TRANSACTION_semGetAllowTextMessaging = 423;
        static final int TRANSACTION_semGetAllowWifi = 421;
        static final int TRANSACTION_semGetRequireStorageCardEncryption = 437;
        static final int TRANSACTION_semIsActivePasswordSufficient = 414;
        static final int TRANSACTION_semIsSimplePasswordEnabled = 416;
        static final int TRANSACTION_semSetAllowBluetoothMode = 430;
        static final int TRANSACTION_semSetAllowBrowser = 426;
        static final int TRANSACTION_semSetAllowDesktopSync = 432;
        static final int TRANSACTION_semSetAllowInternetSharing = 428;
        static final int TRANSACTION_semSetAllowIrda = 434;
        static final int TRANSACTION_semSetAllowPopImapEmail = 424;
        static final int TRANSACTION_semSetAllowStorageCard = 418;
        static final int TRANSACTION_semSetAllowTextMessaging = 422;
        static final int TRANSACTION_semSetAllowWifi = 420;
        static final int TRANSACTION_semSetChangeNotificationEnabled = 438;
        static final int TRANSACTION_semSetKeyguardDisabledFeatures = 417;
        static final int TRANSACTION_semSetPasswordExpirationTimeout = 413;
        static final int TRANSACTION_semSetPasswordHistoryLength = 412;
        static final int TRANSACTION_semSetPasswordMinimumLength = 408;
        static final int TRANSACTION_semSetPasswordMinimumLowerCase = 410;
        static final int TRANSACTION_semSetPasswordMinimumNonLetter = 411;
        static final int TRANSACTION_semSetPasswordMinimumUpperCase = 409;
        static final int TRANSACTION_semSetPasswordQuality = 407;
        static final int TRANSACTION_semSetRequireStorageCardEncryption = 436;
        static final int TRANSACTION_semSetSimplePasswordEnabled = 415;
        static final int TRANSACTION_sendLostModeLocationUpdate = 46;
        static final int TRANSACTION_setAccountManagementDisabled = 171;
        static final int TRANSACTION_setActiveAdmin = 64;
        static final int TRANSACTION_setAffiliationIds = 266;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 118;
        static final int TRANSACTION_setApplicationExemptions = 439;
        static final int TRANSACTION_setApplicationHidden = 155;
        static final int TRANSACTION_setApplicationRestrictions = 128;
        static final int TRANSACTION_setApplicationRestrictionsMDM = 334;
        static final int TRANSACTION_setApplicationRestrictionsManagingPackage = 130;
        static final int TRANSACTION_setAuditLogEnabled = 276;
        static final int TRANSACTION_setAuditLogEventsCallback = 278;
        static final int TRANSACTION_setAutoTimeEnabled = 221;
        static final int TRANSACTION_setAutoTimeRequired = 219;
        static final int TRANSACTION_setAutoTimeZoneEnabled = 223;
        static final int TRANSACTION_setBackupServiceEnabled = 285;
        static final int TRANSACTION_setBluetoothContactSharingDisabled = 211;
        static final int TRANSACTION_setCameraDisabled = 54;
        static final int TRANSACTION_setCertInstallerPackage = 116;
        static final int TRANSACTION_setCommonCriteriaModeEnabled = 367;
        static final int TRANSACTION_setConfiguredNetworksLockdownState = 186;
        static final int TRANSACTION_setContentProtectionPolicy = 453;
        static final int TRANSACTION_setCredentialManagerPolicy = 206;
        static final int TRANSACTION_setCrossProfileAppToIgnored = 450;
        static final int TRANSACTION_setCrossProfileCalendarPackages = 350;
        static final int TRANSACTION_setCrossProfileCallerIdDisabled = 196;
        static final int TRANSACTION_setCrossProfileContactsSearchDisabled = 199;
        static final int TRANSACTION_setCrossProfilePackages = 354;
        static final int TRANSACTION_setDefaultDialerApplication = 127;
        static final int TRANSACTION_setDefaultSmsApplication = 126;
        static final int TRANSACTION_setDelegatedScopes = 113;
        static final int TRANSACTION_setDeviceOwner = 79;
        static final int TRANSACTION_setDeviceOwnerLockScreenInfo = 97;
        static final int TRANSACTION_setDeviceOwnerType = 383;
        static final int TRANSACTION_setDeviceProvisioningConfigApplied = 283;
        static final int TRANSACTION_setDpcDownloaded = 400;
        static final int TRANSACTION_setDrawables = 396;
        static final int TRANSACTION_setEndUserSessionMessage = 309;
        static final int TRANSACTION_setFactoryResetProtectionPolicy = 43;
        static final int TRANSACTION_setForceEphemeralUsers = 225;
        static final int TRANSACTION_setGlobalPrivateDns = 345;
        static final int TRANSACTION_setGlobalProxy = 47;
        static final int TRANSACTION_setGlobalSetting = 183;
        static final int TRANSACTION_setKeepUninstalledPackages = 244;
        static final int TRANSACTION_setKeyGrantForApp = 361;
        static final int TRANSACTION_setKeyGrantToWifiAuth = 363;
        static final int TRANSACTION_setKeyPairCertificate = 111;
        static final int TRANSACTION_setKeyguardDisabled = 232;
        static final int TRANSACTION_setKeyguardDisabledFeatures = 62;
        static final int TRANSACTION_setKeyguardDisabledFeaturesMDM = 333;
        static final int TRANSACTION_setLocationEnabled = 188;
        static final int TRANSACTION_setLockTaskFeatures = 181;
        static final int TRANSACTION_setLockTaskPackages = 178;
        static final int TRANSACTION_setLogoutEnabled = 303;
        static final int TRANSACTION_setLongSupportMessage = 251;
        static final int TRANSACTION_setManagedProfileCallerIdAccessPolicy = 203;
        static final int TRANSACTION_setManagedProfileContactsAccessPolicy = 208;
        static final int TRANSACTION_setManagedProfileMaximumTimeOff = 372;
        static final int TRANSACTION_setManagedSubscriptionsPolicy = 443;
        static final int TRANSACTION_setMasterVolumeMuted = 191;
        static final int TRANSACTION_setMaxPolicyStorageLimit = 456;
        static final int TRANSACTION_setMaximumFailedPasswordsForWipe = 34;
        static final int TRANSACTION_setMaximumFailedPasswordsForWipeMDM = 331;
        static final int TRANSACTION_setMaximumTimeToLock = 37;
        static final int TRANSACTION_setMaximumTimeToLockMDM = 332;
        static final int TRANSACTION_setMeteredDataDisabledPackages = 312;
        static final int TRANSACTION_setMinimumRequiredWifiSecurityLevel = 390;
        static final int TRANSACTION_setMtePolicy = 441;
        static final int TRANSACTION_setNearbyAppStreamingPolicy = 60;
        static final int TRANSACTION_setNearbyNotificationStreamingPolicy = 58;
        static final int TRANSACTION_setNetworkLoggingEnabled = 287;
        static final int TRANSACTION_setNextOperationSafety = 376;
        static final int TRANSACTION_setOrganizationColor = 255;
        static final int TRANSACTION_setOrganizationColorForUser = 256;
        static final int TRANSACTION_setOrganizationIdForUser = 379;
        static final int TRANSACTION_setOrganizationName = 260;
        static final int TRANSACTION_setOverrideApnsEnabled = 318;
        static final int TRANSACTION_setPackagesSuspended = 99;
        static final int TRANSACTION_setPasswordExpirationTimeout = 20;
        static final int TRANSACTION_setPasswordExpirationTimeoutMDM = 330;
        static final int TRANSACTION_setPasswordHistoryLength = 18;
        static final int TRANSACTION_setPasswordHistoryLengthMDM = 329;
        static final int TRANSACTION_setPasswordMinimumLength = 3;
        static final int TRANSACTION_setPasswordMinimumLengthMDM = 322;
        static final int TRANSACTION_setPasswordMinimumLetters = 9;
        static final int TRANSACTION_setPasswordMinimumLettersMDM = 325;
        static final int TRANSACTION_setPasswordMinimumLowerCase = 7;
        static final int TRANSACTION_setPasswordMinimumLowerCaseMDM = 324;
        static final int TRANSACTION_setPasswordMinimumNonLetter = 15;
        static final int TRANSACTION_setPasswordMinimumNonLetterMDM = 328;
        static final int TRANSACTION_setPasswordMinimumNumeric = 11;
        static final int TRANSACTION_setPasswordMinimumNumericMDM = 326;
        static final int TRANSACTION_setPasswordMinimumSymbols = 13;
        static final int TRANSACTION_setPasswordMinimumSymbolsMDM = 327;
        static final int TRANSACTION_setPasswordMinimumUpperCase = 5;
        static final int TRANSACTION_setPasswordMinimumUpperCaseMDM = 323;
        static final int TRANSACTION_setPasswordQuality = 1;
        static final int TRANSACTION_setPasswordQualityMDM = 321;
        static final int TRANSACTION_setPermissionGrantState = 240;
        static final int TRANSACTION_setPermissionPolicy = 238;
        static final int TRANSACTION_setPermittedAccessibilityServices = 141;
        static final int TRANSACTION_setPermittedCrossProfileNotificationListeners = 149;
        static final int TRANSACTION_setPermittedInputMethods = 145;
        static final int TRANSACTION_setPersonalAppsSuspended = 370;
        static final int TRANSACTION_setPreferentialNetworkServiceConfigs = 176;
        static final int TRANSACTION_setProfileEnabled = 91;
        static final int TRANSACTION_setProfileName = 92;
        static final int TRANSACTION_setProfileOwner = 86;
        static final int TRANSACTION_setProfileOwnerOnOrganizationOwnedDevice = 348;
        static final int TRANSACTION_setRecommendedGlobalProxy = 49;
        static final int TRANSACTION_setRequiredPasswordComplexity = 27;
        static final int TRANSACTION_setRequiredStrongAuthTimeout = 39;
        static final int TRANSACTION_setResetPasswordToken = 296;
        static final int TRANSACTION_setResetPasswordTokenMDM = 339;
        static final int TRANSACTION_setRestrictionsProvider = 133;
        static final int TRANSACTION_setScreenCaptureDisabled = 56;
        static final int TRANSACTION_setSecondaryLockscreenEnabled = 174;
        static final int TRANSACTION_setSecureSetting = 185;
        static final int TRANSACTION_setSecurityLoggingEnabled = 270;
        static final int TRANSACTION_setShortSupportMessage = 249;
        static final int TRANSACTION_setStartUserSessionMessage = 308;
        static final int TRANSACTION_setStatusBarDisabled = 233;
        static final int TRANSACTION_setStorageEncryption = 50;
        static final int TRANSACTION_setStrings = 401;
        static final int TRANSACTION_setSystemSetting = 184;
        static final int TRANSACTION_setSystemUpdatePolicy = 229;
        static final int TRANSACTION_setTime = 189;
        static final int TRANSACTION_setTimeZone = 190;
        static final int TRANSACTION_setTrustAgentConfiguration = 214;
        static final int TRANSACTION_setTrustAgentConfigurationMDM = 340;
        static final int TRANSACTION_setUninstallBlocked = 194;
        static final int TRANSACTION_setUsbDataSignalingEnabled = 387;
        static final int TRANSACTION_setUserControlDisabledPackages = 365;
        static final int TRANSACTION_setUserIcon = 228;
        static final int TRANSACTION_setUserProvisioningState = 265;
        static final int TRANSACTION_setUserRestriction = 135;
        static final int TRANSACTION_setUserRestrictionForKnox = 449;
        static final int TRANSACTION_setUserRestrictionGlobally = 136;
        static final int TRANSACTION_setWifiSsidPolicy = 392;
        static final int TRANSACTION_shouldAllowBypassingDevicePolicyManagementRoleQualification =
                405;
        static final int TRANSACTION_startManagedQuickContact = 202;
        static final int TRANSACTION_startUserInBackground = 160;
        static final int TRANSACTION_startViewCalendarEventInManagedProfile = 360;
        static final int TRANSACTION_stopUser = 161;
        static final int TRANSACTION_switchUser = 159;
        static final int TRANSACTION_transferOwnership = 306;
        static final int TRANSACTION_triggerDevicePolicyEngineMigration = 446;
        static final int TRANSACTION_uninstallCaCerts = 103;
        static final int TRANSACTION_uninstallPackageWithActiveAdmins = 280;
        static final int TRANSACTION_updateOverrideApn = 315;
        static final int TRANSACTION_wipeDataWithReason = 42;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDevicePolicyManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDevicePolicyManager)) {
                return (IDevicePolicyManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return SecContentProviderURI
                            .PASSWORDPOLICY_GETCURRENTFAILEDPASSWORDATEEMPTS_METHOD;
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
                    return "setUserRestrictionGlobally";
                case 137:
                    return "getUserRestrictions";
                case 138:
                    return "getUserRestrictionsGlobally";
                case 139:
                    return "addCrossProfileIntentFilter";
                case 140:
                    return "clearCrossProfileIntentFilters";
                case 141:
                    return "setPermittedAccessibilityServices";
                case 142:
                    return "getPermittedAccessibilityServices";
                case 143:
                    return "getPermittedAccessibilityServicesForUser";
                case 144:
                    return "isAccessibilityServicePermittedByAdmin";
                case 145:
                    return "setPermittedInputMethods";
                case 146:
                    return "getPermittedInputMethods";
                case 147:
                    return "getPermittedInputMethodsAsUser";
                case 148:
                    return "isInputMethodPermittedByAdmin";
                case 149:
                    return "setPermittedCrossProfileNotificationListeners";
                case 150:
                    return "getPermittedCrossProfileNotificationListeners";
                case 151:
                    return "isNotificationListenerServicePermitted";
                case 152:
                    return "createAdminSupportIntent";
                case 153:
                    return "getEnforcingAdminAndUserDetails";
                case 154:
                    return "getEnforcingAdminsForRestriction";
                case 155:
                    return "setApplicationHidden";
                case 156:
                    return "isApplicationHidden";
                case 157:
                    return "createAndManageUser";
                case 158:
                    return "removeUser";
                case 159:
                    return "switchUser";
                case 160:
                    return "startUserInBackground";
                case 161:
                    return "stopUser";
                case 162:
                    return "logoutUser";
                case 163:
                    return "logoutUserInternal";
                case 164:
                    return "getLogoutUserId";
                case 165:
                    return "getSecondaryUsers";
                case 166:
                    return "acknowledgeNewUserDisclaimer";
                case 167:
                    return "isNewUserDisclaimerAcknowledged";
                case 168:
                    return "enableSystemApp";
                case 169:
                    return "enableSystemAppWithIntent";
                case 170:
                    return "installExistingPackage";
                case 171:
                    return "setAccountManagementDisabled";
                case 172:
                    return "getAccountTypesWithManagementDisabled";
                case 173:
                    return "getAccountTypesWithManagementDisabledAsUser";
                case 174:
                    return "setSecondaryLockscreenEnabled";
                case 175:
                    return "isSecondaryLockscreenEnabled";
                case 176:
                    return "setPreferentialNetworkServiceConfigs";
                case 177:
                    return "getPreferentialNetworkServiceConfigs";
                case 178:
                    return "setLockTaskPackages";
                case 179:
                    return "getLockTaskPackages";
                case 180:
                    return "isLockTaskPermitted";
                case 181:
                    return "setLockTaskFeatures";
                case 182:
                    return "getLockTaskFeatures";
                case 183:
                    return "setGlobalSetting";
                case 184:
                    return "setSystemSetting";
                case 185:
                    return "setSecureSetting";
                case 186:
                    return "setConfiguredNetworksLockdownState";
                case 187:
                    return "hasLockdownAdminConfiguredNetworks";
                case 188:
                    return "setLocationEnabled";
                case 189:
                    return "setTime";
                case 190:
                    return "setTimeZone";
                case 191:
                    return "setMasterVolumeMuted";
                case 192:
                    return "isMasterVolumeMuted";
                case 193:
                    return "notifyLockTaskModeChanged";
                case 194:
                    return "setUninstallBlocked";
                case 195:
                    return "isUninstallBlocked";
                case 196:
                    return "setCrossProfileCallerIdDisabled";
                case 197:
                    return "getCrossProfileCallerIdDisabled";
                case 198:
                    return "getCrossProfileCallerIdDisabledForUser";
                case 199:
                    return "setCrossProfileContactsSearchDisabled";
                case 200:
                    return "getCrossProfileContactsSearchDisabled";
                case 201:
                    return "getCrossProfileContactsSearchDisabledForUser";
                case 202:
                    return "startManagedQuickContact";
                case 203:
                    return "setManagedProfileCallerIdAccessPolicy";
                case 204:
                    return "getManagedProfileCallerIdAccessPolicy";
                case 205:
                    return "hasManagedProfileCallerIdAccess";
                case 206:
                    return "setCredentialManagerPolicy";
                case 207:
                    return "getCredentialManagerPolicy";
                case 208:
                    return "setManagedProfileContactsAccessPolicy";
                case 209:
                    return "getManagedProfileContactsAccessPolicy";
                case 210:
                    return "hasManagedProfileContactsAccess";
                case 211:
                    return "setBluetoothContactSharingDisabled";
                case 212:
                    return "getBluetoothContactSharingDisabled";
                case 213:
                    return "getBluetoothContactSharingDisabledForUser";
                case 214:
                    return "setTrustAgentConfiguration";
                case 215:
                    return "getTrustAgentConfiguration";
                case 216:
                    return "addCrossProfileWidgetProvider";
                case 217:
                    return "removeCrossProfileWidgetProvider";
                case 218:
                    return "getCrossProfileWidgetProviders";
                case 219:
                    return "setAutoTimeRequired";
                case 220:
                    return "getAutoTimeRequired";
                case 221:
                    return "setAutoTimeEnabled";
                case 222:
                    return "getAutoTimeEnabled";
                case 223:
                    return "setAutoTimeZoneEnabled";
                case 224:
                    return "getAutoTimeZoneEnabled";
                case 225:
                    return "setForceEphemeralUsers";
                case 226:
                    return "getForceEphemeralUsers";
                case 227:
                    return "isRemovingAdmin";
                case 228:
                    return "setUserIcon";
                case 229:
                    return "setSystemUpdatePolicy";
                case 230:
                    return "getSystemUpdatePolicy";
                case 231:
                    return "clearSystemUpdatePolicyFreezePeriodRecord";
                case 232:
                    return "setKeyguardDisabled";
                case 233:
                    return "setStatusBarDisabled";
                case 234:
                    return "isStatusBarDisabled";
                case 235:
                    return "getDoNotAskCredentialsOnBoot";
                case 236:
                    return "notifyPendingSystemUpdate";
                case 237:
                    return "getPendingSystemUpdate";
                case 238:
                    return "setPermissionPolicy";
                case 239:
                    return "getPermissionPolicy";
                case 240:
                    return "setPermissionGrantState";
                case 241:
                    return "getPermissionGrantState";
                case 242:
                    return "isProvisioningAllowed";
                case 243:
                    return "checkProvisioningPrecondition";
                case 244:
                    return "setKeepUninstalledPackages";
                case 245:
                    return "getKeepUninstalledPackages";
                case 246:
                    return "isManagedProfile";
                case 247:
                    return "getWifiMacAddress";
                case 248:
                    return "reboot";
                case 249:
                    return "setShortSupportMessage";
                case 250:
                    return "getShortSupportMessage";
                case 251:
                    return "setLongSupportMessage";
                case 252:
                    return "getLongSupportMessage";
                case 253:
                    return "getShortSupportMessageForUser";
                case 254:
                    return "getLongSupportMessageForUser";
                case 255:
                    return "setOrganizationColor";
                case 256:
                    return "setOrganizationColorForUser";
                case 257:
                    return "clearOrganizationIdForUser";
                case 258:
                    return "getOrganizationColor";
                case 259:
                    return "getOrganizationColorForUser";
                case 260:
                    return "setOrganizationName";
                case 261:
                    return "getOrganizationName";
                case 262:
                    return "getDeviceOwnerOrganizationName";
                case 263:
                    return "getOrganizationNameForUser";
                case 264:
                    return "getUserProvisioningState";
                case 265:
                    return "setUserProvisioningState";
                case 266:
                    return "setAffiliationIds";
                case 267:
                    return "getAffiliationIds";
                case 268:
                    return "isCallingUserAffiliated";
                case 269:
                    return "isAffiliatedUser";
                case 270:
                    return "setSecurityLoggingEnabled";
                case 271:
                    return "isSecurityLoggingEnabled";
                case 272:
                    return "retrieveSecurityLogs";
                case 273:
                    return "retrievePreRebootSecurityLogs";
                case 274:
                    return "forceNetworkLogs";
                case 275:
                    return "forceSecurityLogs";
                case 276:
                    return "setAuditLogEnabled";
                case 277:
                    return SecContentProviderURI.AUDITLOGPOLICY_AUDITLOGENABLED_METHOD;
                case 278:
                    return "setAuditLogEventsCallback";
                case 279:
                    return "isUninstallInQueue";
                case 280:
                    return "uninstallPackageWithActiveAdmins";
                case 281:
                    return "isDeviceProvisioned";
                case 282:
                    return "isDeviceProvisioningConfigApplied";
                case 283:
                    return "setDeviceProvisioningConfigApplied";
                case 284:
                    return "forceUpdateUserSetupComplete";
                case 285:
                    return "setBackupServiceEnabled";
                case 286:
                    return "isBackupServiceEnabled";
                case 287:
                    return "setNetworkLoggingEnabled";
                case 288:
                    return "isNetworkLoggingEnabled";
                case 289:
                    return "retrieveNetworkLogs";
                case 290:
                    return "bindDeviceAdminServiceAsUser";
                case 291:
                    return "getBindDeviceAdminTargetUsers";
                case 292:
                    return "isEphemeralUser";
                case 293:
                    return "getLastSecurityLogRetrievalTime";
                case 294:
                    return "getLastBugReportRequestTime";
                case 295:
                    return "getLastNetworkLogRetrievalTime";
                case 296:
                    return "setResetPasswordToken";
                case 297:
                    return "clearResetPasswordToken";
                case 298:
                    return "isResetPasswordTokenActive";
                case 299:
                    return "resetPasswordWithToken";
                case 300:
                    return "isCurrentInputMethodSetByOwner";
                case 301:
                    return "getOwnerInstalledCaCerts";
                case 302:
                    return "clearApplicationUserData";
                case 303:
                    return "setLogoutEnabled";
                case 304:
                    return "isLogoutEnabled";
                case 305:
                    return "getDisallowedSystemApps";
                case 306:
                    return "transferOwnership";
                case 307:
                    return "getTransferOwnershipBundle";
                case 308:
                    return "setStartUserSessionMessage";
                case 309:
                    return "setEndUserSessionMessage";
                case 310:
                    return "getStartUserSessionMessage";
                case 311:
                    return "getEndUserSessionMessage";
                case 312:
                    return "setMeteredDataDisabledPackages";
                case 313:
                    return "getMeteredDataDisabledPackages";
                case 314:
                    return "addOverrideApn";
                case 315:
                    return "updateOverrideApn";
                case 316:
                    return "removeOverrideApn";
                case 317:
                    return "getOverrideApns";
                case 318:
                    return "setOverrideApnsEnabled";
                case 319:
                    return "isOverrideApnEnabled";
                case 320:
                    return "isMeteredDataDisabledPackageForUser";
                case 321:
                    return "setPasswordQualityMDM";
                case 322:
                    return "setPasswordMinimumLengthMDM";
                case 323:
                    return "setPasswordMinimumUpperCaseMDM";
                case 324:
                    return "setPasswordMinimumLowerCaseMDM";
                case 325:
                    return "setPasswordMinimumLettersMDM";
                case 326:
                    return "setPasswordMinimumNumericMDM";
                case 327:
                    return "setPasswordMinimumSymbolsMDM";
                case 328:
                    return "setPasswordMinimumNonLetterMDM";
                case 329:
                    return "setPasswordHistoryLengthMDM";
                case 330:
                    return "setPasswordExpirationTimeoutMDM";
                case 331:
                    return "setMaximumFailedPasswordsForWipeMDM";
                case 332:
                    return "setMaximumTimeToLockMDM";
                case 333:
                    return "setKeyguardDisabledFeaturesMDM";
                case 334:
                    return "setApplicationRestrictionsMDM";
                case 335:
                    return "getApplicationRestrictionsMDM";
                case 336:
                    return "resetPasswordWithTokenMDM";
                case 337:
                    return "isResetPasswordTokenActiveMDM";
                case 338:
                    return "clearResetPasswordTokenMDM";
                case 339:
                    return "setResetPasswordTokenMDM";
                case 340:
                    return "setTrustAgentConfigurationMDM";
                case 341:
                    return "isProfileOwnerOfOrganizationOwnedDeviceMDM";
                case 342:
                    return "hasDelegatedPermission";
                case 343:
                    return "getDelegatedPackages";
                case 344:
                    return "reportFailedPasswordAttemptWithFailureCount";
                case 345:
                    return "setGlobalPrivateDns";
                case 346:
                    return "getGlobalPrivateDnsMode";
                case 347:
                    return "getGlobalPrivateDnsHost";
                case 348:
                    return "setProfileOwnerOnOrganizationOwnedDevice";
                case 349:
                    return "installUpdateFromFile";
                case 350:
                    return "setCrossProfileCalendarPackages";
                case 351:
                    return "getCrossProfileCalendarPackages";
                case 352:
                    return "isPackageAllowedToAccessCalendarForUser";
                case 353:
                    return "getCrossProfileCalendarPackagesForUser";
                case 354:
                    return "setCrossProfilePackages";
                case 355:
                    return "getCrossProfilePackages";
                case 356:
                    return "getAllCrossProfilePackages";
                case 357:
                    return "getDefaultCrossProfilePackages";
                case 358:
                    return "isManagedKiosk";
                case 359:
                    return "isUnattendedManagedKiosk";
                case 360:
                    return "startViewCalendarEventInManagedProfile";
                case 361:
                    return "setKeyGrantForApp";
                case 362:
                    return "getKeyPairGrants";
                case 363:
                    return "setKeyGrantToWifiAuth";
                case 364:
                    return "isKeyPairGrantedToWifiAuth";
                case 365:
                    return "setUserControlDisabledPackages";
                case 366:
                    return "getUserControlDisabledPackages";
                case 367:
                    return "setCommonCriteriaModeEnabled";
                case 368:
                    return "isCommonCriteriaModeEnabled";
                case 369:
                    return "getPersonalAppsSuspendedReasons";
                case 370:
                    return "setPersonalAppsSuspended";
                case 371:
                    return "getManagedProfileMaximumTimeOff";
                case 372:
                    return "setManagedProfileMaximumTimeOff";
                case 373:
                    return "acknowledgeDeviceCompliant";
                case 374:
                    return "isComplianceAcknowledgementRequired";
                case 375:
                    return "canProfileOwnerResetPasswordWhenLocked";
                case 376:
                    return "setNextOperationSafety";
                case 377:
                    return "isSafeOperation";
                case 378:
                    return "getEnrollmentSpecificId";
                case 379:
                    return "setOrganizationIdForUser";
                case 380:
                    return "createAndProvisionManagedProfile";
                case 381:
                    return "provisionFullyManagedDevice";
                case 382:
                    return "finalizeWorkProfileProvisioning";
                case 383:
                    return "setDeviceOwnerType";
                case 384:
                    return "getDeviceOwnerType";
                case 385:
                    return "resetDefaultCrossProfileIntentFilters";
                case 386:
                    return "canAdminGrantSensorsPermissions";
                case 387:
                    return "setUsbDataSignalingEnabled";
                case 388:
                    return "isUsbDataSignalingEnabled";
                case 389:
                    return "canUsbDataSignalingBeDisabled";
                case 390:
                    return "setMinimumRequiredWifiSecurityLevel";
                case 391:
                    return "getMinimumRequiredWifiSecurityLevel";
                case 392:
                    return "setWifiSsidPolicy";
                case 393:
                    return "getWifiSsidPolicy";
                case 394:
                    return "isDevicePotentiallyStolen";
                case 395:
                    return "listForegroundAffiliatedUsers";
                case 396:
                    return "setDrawables";
                case 397:
                    return "resetDrawables";
                case 398:
                    return "getDrawable";
                case 399:
                    return "isDpcDownloaded";
                case 400:
                    return "setDpcDownloaded";
                case 401:
                    return "setStrings";
                case 402:
                    return "resetStrings";
                case 403:
                    return "getString";
                case 404:
                    return "resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState";
                case 405:
                    return "shouldAllowBypassingDevicePolicyManagementRoleQualification";
                case 406:
                    return "getPolicyManagedProfiles";
                case 407:
                    return "semSetPasswordQuality";
                case 408:
                    return "semSetPasswordMinimumLength";
                case 409:
                    return "semSetPasswordMinimumUpperCase";
                case 410:
                    return "semSetPasswordMinimumLowerCase";
                case 411:
                    return "semSetPasswordMinimumNonLetter";
                case 412:
                    return "semSetPasswordHistoryLength";
                case 413:
                    return "semSetPasswordExpirationTimeout";
                case 414:
                    return "semIsActivePasswordSufficient";
                case 415:
                    return "semSetSimplePasswordEnabled";
                case 416:
                    return "semIsSimplePasswordEnabled";
                case 417:
                    return "semSetKeyguardDisabledFeatures";
                case 418:
                    return "semSetAllowStorageCard";
                case 419:
                    return "semGetAllowStorageCard";
                case 420:
                    return "semSetAllowWifi";
                case 421:
                    return "semGetAllowWifi";
                case 422:
                    return "semSetAllowTextMessaging";
                case 423:
                    return "semGetAllowTextMessaging";
                case 424:
                    return "semSetAllowPopImapEmail";
                case 425:
                    return "semGetAllowPopImapEmail";
                case 426:
                    return "semSetAllowBrowser";
                case 427:
                    return "semGetAllowBrowser";
                case 428:
                    return "semSetAllowInternetSharing";
                case 429:
                    return "semGetAllowInternetSharing";
                case 430:
                    return "semSetAllowBluetoothMode";
                case 431:
                    return "semGetAllowBluetoothMode";
                case 432:
                    return "semSetAllowDesktopSync";
                case 433:
                    return "semGetAllowDesktopSync";
                case 434:
                    return "semSetAllowIrda";
                case 435:
                    return "semGetAllowIrda";
                case 436:
                    return "semSetRequireStorageCardEncryption";
                case 437:
                    return "semGetRequireStorageCardEncryption";
                case 438:
                    return "semSetChangeNotificationEnabled";
                case 439:
                    return "setApplicationExemptions";
                case 440:
                    return "getApplicationExemptions";
                case 441:
                    return "setMtePolicy";
                case 442:
                    return "getMtePolicy";
                case 443:
                    return "setManagedSubscriptionsPolicy";
                case 444:
                    return "getManagedSubscriptionsPolicy";
                case 445:
                    return "getDevicePolicyState";
                case 446:
                    return "triggerDevicePolicyEngineMigration";
                case 447:
                    return "isDeviceFinanced";
                case 448:
                    return "getFinancedDeviceKioskRoleHolder";
                case 449:
                    return "setUserRestrictionForKnox";
                case 450:
                    return "setCrossProfileAppToIgnored";
                case 451:
                    return "getSamsungSDcardEncryptionStatus";
                case 452:
                    return "calculateHasIncompatibleAccounts";
                case 453:
                    return "setContentProtectionPolicy";
                case 454:
                    return "getContentProtectionPolicy";
                case 455:
                    return "getSubscriptionIds";
                case 456:
                    return "setMaxPolicyStorageLimit";
                case 457:
                    return "forceSetMaxPolicyStorageLimit";
                case 458:
                    return "getMaxPolicyStorageLimit";
                case 459:
                    return "getPolicySizeForAdmin";
                case 460:
                    return "getHeadlessDeviceOwnerMode";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    return onTransact$setPasswordQuality$(data, reply);
                case 2:
                    return onTransact$getPasswordQuality$(data, reply);
                case 3:
                    return onTransact$setPasswordMinimumLength$(data, reply);
                case 4:
                    return onTransact$getPasswordMinimumLength$(data, reply);
                case 5:
                    return onTransact$setPasswordMinimumUpperCase$(data, reply);
                case 6:
                    return onTransact$getPasswordMinimumUpperCase$(data, reply);
                case 7:
                    return onTransact$setPasswordMinimumLowerCase$(data, reply);
                case 8:
                    return onTransact$getPasswordMinimumLowerCase$(data, reply);
                case 9:
                    return onTransact$setPasswordMinimumLetters$(data, reply);
                case 10:
                    return onTransact$getPasswordMinimumLetters$(data, reply);
                case 11:
                    return onTransact$setPasswordMinimumNumeric$(data, reply);
                case 12:
                    return onTransact$getPasswordMinimumNumeric$(data, reply);
                case 13:
                    return onTransact$setPasswordMinimumSymbols$(data, reply);
                case 14:
                    return onTransact$getPasswordMinimumSymbols$(data, reply);
                case 15:
                    return onTransact$setPasswordMinimumNonLetter$(data, reply);
                case 16:
                    return onTransact$getPasswordMinimumNonLetter$(data, reply);
                case 17:
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    PasswordMetrics _result = getPasswordMinimumMetrics(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 18:
                    return onTransact$setPasswordHistoryLength$(data, reply);
                case 19:
                    return onTransact$getPasswordHistoryLength$(data, reply);
                case 20:
                    return onTransact$setPasswordExpirationTimeout$(data, reply);
                case 21:
                    return onTransact$getPasswordExpirationTimeout$(data, reply);
                case 22:
                    return onTransact$getPasswordExpiration$(data, reply);
                case 23:
                    return onTransact$isActivePasswordSufficient$(data, reply);
                case 24:
                    boolean _result2 = isActivePasswordSufficientForDeviceRequirement();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 25:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = isPasswordSufficientAfterProfileUnification(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 26:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result4 = getPasswordComplexity(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 27:
                    return onTransact$setRequiredPasswordComplexity$(data, reply);
                case 28:
                    String _arg04 = data.readString();
                    boolean _arg13 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result5 = getRequiredPasswordComplexity(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 29:
                    int _arg05 = data.readInt();
                    boolean _arg14 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result6 = getAggregatedPasswordComplexityForUser(_arg05, _arg14);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 30:
                    ComponentName _arg06 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result7 = isUsingUnifiedPassword(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 31:
                    return onTransact$getCurrentFailedPasswordAttempts$(data, reply);
                case 32:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result8 = getCurrentFailedBiometricAttempts(_arg07);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 33:
                    int _arg08 = data.readInt();
                    boolean _arg15 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result9 = getProfileWithMinimumFailedPasswordsForWipe(_arg08, _arg15);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 34:
                    return onTransact$setMaximumFailedPasswordsForWipe$(data, reply);
                case 35:
                    return onTransact$getMaximumFailedPasswordsForWipe$(data, reply);
                case 36:
                    String _arg09 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = resetPassword(_arg09, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 37:
                    return onTransact$setMaximumTimeToLock$(data, reply);
                case 38:
                    return onTransact$getMaximumTimeToLock$(data, reply);
                case 39:
                    return onTransact$setRequiredStrongAuthTimeout$(data, reply);
                case 40:
                    return onTransact$getRequiredStrongAuthTimeout$(data, reply);
                case 41:
                    return onTransact$lockNow$(data, reply);
                case 42:
                    return onTransact$wipeDataWithReason$(data, reply);
                case 43:
                    return onTransact$setFactoryResetProtectionPolicy$(data, reply);
                case 44:
                    ComponentName _arg010 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    FactoryResetProtectionPolicy _result11 =
                            getFactoryResetProtectionPolicy(_arg010);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 45:
                    boolean _result12 = isFactoryResetProtectionPolicySupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 46:
                    AndroidFuture<Boolean> _arg011 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    sendLostModeLocationUpdate(_arg011);
                    reply.writeNoException();
                    return true;
                case 47:
                    return onTransact$setGlobalProxy$(data, reply);
                case 48:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result13 = getGlobalProxyAdmin(_arg012);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 49:
                    ComponentName _arg013 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    ProxyInfo _arg17 = (ProxyInfo) data.readTypedObject(ProxyInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setRecommendedGlobalProxy(_arg013, _arg17);
                    reply.writeNoException();
                    return true;
                case 50:
                    ComponentName _arg014 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg18 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result14 = setStorageEncryption(_arg014, _arg18);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 51:
                    ComponentName _arg015 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = getStorageEncryption(_arg015, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 52:
                    String _arg016 = data.readString();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result16 = getStorageEncryptionStatus(_arg016, _arg110);
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 53:
                    ComponentName _arg017 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result17 = requestBugreport(_arg017);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 54:
                    return onTransact$setCameraDisabled$(data, reply);
                case 55:
                    return onTransact$getCameraDisabled$(data, reply);
                case 56:
                    return onTransact$setScreenCaptureDisabled$(data, reply);
                case 57:
                    return onTransact$getScreenCaptureDisabled$(data, reply);
                case 58:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    setNearbyNotificationStreamingPolicy(_arg018);
                    reply.writeNoException();
                    return true;
                case 59:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result18 = getNearbyNotificationStreamingPolicy(_arg019);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 60:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    setNearbyAppStreamingPolicy(_arg020);
                    reply.writeNoException();
                    return true;
                case 61:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result19 = getNearbyAppStreamingPolicy(_arg021);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 62:
                    return onTransact$setKeyguardDisabledFeatures$(data, reply);
                case 63:
                    return onTransact$getKeyguardDisabledFeatures$(data, reply);
                case 64:
                    return onTransact$setActiveAdmin$(data, reply);
                case 65:
                    ComponentName _arg022 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result20 = isAdminActive(_arg022, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 66:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    List<ComponentName> _result21 = getActiveAdmins(_arg023);
                    reply.writeNoException();
                    reply.writeTypedList(_result21, 1);
                    return true;
                case 67:
                    String _arg024 = data.readString();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result22 = packageHasActiveAdmins(_arg024, _arg112);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 68:
                    return onTransact$getRemoveWarning$(data, reply);
                case 69:
                    ComponentName _arg025 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    removeActiveAdmin(_arg025, _arg113);
                    reply.writeNoException();
                    return true;
                case 70:
                    ComponentName _arg026 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    forceRemoveActiveAdmin(_arg026, _arg114);
                    reply.writeNoException();
                    return true;
                case 71:
                    return onTransact$hasGrantedPolicy$(data, reply);
                case 72:
                    PasswordMetrics _arg027 =
                            (PasswordMetrics) data.readTypedObject(PasswordMetrics.CREATOR);
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    reportPasswordChanged(_arg027, _arg115);
                    reply.writeNoException();
                    return true;
                case 73:
                    int _arg028 = data.readInt();
                    boolean _arg116 = data.readBoolean();
                    data.enforceNoDataAvail();
                    reportFailedPasswordAttempt(_arg028, _arg116);
                    reply.writeNoException();
                    return true;
                case 74:
                    int _arg029 = data.readInt();
                    data.enforceNoDataAvail();
                    reportSuccessfulPasswordAttempt(_arg029);
                    reply.writeNoException();
                    return true;
                case 75:
                    int _arg030 = data.readInt();
                    data.enforceNoDataAvail();
                    reportFailedBiometricAttempt(_arg030);
                    reply.writeNoException();
                    return true;
                case 76:
                    int _arg031 = data.readInt();
                    data.enforceNoDataAvail();
                    reportSuccessfulBiometricAttempt(_arg031);
                    reply.writeNoException();
                    return true;
                case 77:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    reportKeyguardDismissed(_arg032);
                    reply.writeNoException();
                    return true;
                case 78:
                    int _arg033 = data.readInt();
                    data.enforceNoDataAvail();
                    reportKeyguardSecured(_arg033);
                    reply.writeNoException();
                    return true;
                case 79:
                    return onTransact$setDeviceOwner$(data, reply);
                case 80:
                    boolean _arg034 = data.readBoolean();
                    data.enforceNoDataAvail();
                    ComponentName _result23 = getDeviceOwnerComponent(_arg034);
                    reply.writeNoException();
                    reply.writeTypedObject(_result23, 1);
                    return true;
                case 81:
                    int _arg035 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result24 = getDeviceOwnerComponentOnUser(_arg035);
                    reply.writeNoException();
                    reply.writeTypedObject(_result24, 1);
                    return true;
                case 82:
                    boolean _result25 = hasDeviceOwner();
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 83:
                    String _result26 = getDeviceOwnerName();
                    reply.writeNoException();
                    reply.writeString(_result26);
                    return true;
                case 84:
                    String _arg036 = data.readString();
                    data.enforceNoDataAvail();
                    clearDeviceOwner(_arg036);
                    reply.writeNoException();
                    return true;
                case 85:
                    int _result27 = getDeviceOwnerUserId();
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case 86:
                    ComponentName _arg037 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result28 = setProfileOwner(_arg037, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 87:
                    int _arg038 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result29 = getProfileOwnerAsUser(_arg038);
                    reply.writeNoException();
                    reply.writeTypedObject(_result29, 1);
                    return true;
                case 88:
                    UserHandle _arg039 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    ComponentName _result30 =
                            getProfileOwnerOrDeviceOwnerSupervisionComponent(_arg039);
                    reply.writeNoException();
                    reply.writeTypedObject(_result30, 1);
                    return true;
                case 89:
                    ComponentName _arg040 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result31 = isSupervisionComponent(_arg040);
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 90:
                    int _arg041 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result32 = getProfileOwnerName(_arg041);
                    reply.writeNoException();
                    reply.writeString(_result32);
                    return true;
                case 91:
                    ComponentName _arg042 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setProfileEnabled(_arg042);
                    reply.writeNoException();
                    return true;
                case 92:
                    ComponentName _arg043 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg118 = data.readString();
                    data.enforceNoDataAvail();
                    setProfileName(_arg043, _arg118);
                    reply.writeNoException();
                    return true;
                case 93:
                    ComponentName _arg044 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    clearProfileOwner(_arg044);
                    reply.writeNoException();
                    return true;
                case 94:
                    boolean _result33 = hasUserSetupCompleted();
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 95:
                    boolean _result34 = isOrganizationOwnedDeviceWithManagedProfile();
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 96:
                    return onTransact$checkDeviceIdentifierAccess$(data, reply);
                case 97:
                    ComponentName _arg045 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    CharSequence _arg119 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    setDeviceOwnerLockScreenInfo(_arg045, _arg119);
                    reply.writeNoException();
                    return true;
                case 98:
                    CharSequence _result35 = getDeviceOwnerLockScreenInfo();
                    reply.writeNoException();
                    if (_result35 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result35, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 99:
                    return onTransact$setPackagesSuspended$(data, reply);
                case 100:
                    return onTransact$isPackageSuspended$(data, reply);
                case 101:
                    List<String> _result36 = listPolicyExemptApps();
                    reply.writeNoException();
                    reply.writeStringList(_result36);
                    return true;
                case 102:
                    return onTransact$installCaCert$(data, reply);
                case 103:
                    return onTransact$uninstallCaCerts$(data, reply);
                case 104:
                    ComponentName _arg046 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg120 = data.readString();
                    data.enforceNoDataAvail();
                    enforceCanManageCaCerts(_arg046, _arg120);
                    reply.writeNoException();
                    return true;
                case 105:
                    return onTransact$approveCaCert$(data, reply);
                case 106:
                    String _arg047 = data.readString();
                    int _arg121 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result37 = isCaCertApproved(_arg047, _arg121);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 107:
                    return onTransact$installKeyPair$(data, reply);
                case 108:
                    return onTransact$removeKeyPair$(data, reply);
                case 109:
                    String _arg048 = data.readString();
                    String _arg122 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result38 = hasKeyPair(_arg048, _arg122);
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 110:
                    return onTransact$generateKeyPair$(data, reply);
                case 111:
                    return onTransact$setKeyPairCertificate$(data, reply);
                case 112:
                    return onTransact$choosePrivateKeyAlias$(data, reply);
                case 113:
                    return onTransact$setDelegatedScopes$(data, reply);
                case 114:
                    ComponentName _arg049 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg123 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result39 = getDelegatedScopes(_arg049, _arg123);
                    reply.writeNoException();
                    reply.writeStringList(_result39);
                    return true;
                case 115:
                    ComponentName _arg050 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg124 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result40 = getDelegatePackages(_arg050, _arg124);
                    reply.writeNoException();
                    reply.writeStringList(_result40);
                    return true;
                case 116:
                    ComponentName _arg051 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg125 = data.readString();
                    data.enforceNoDataAvail();
                    setCertInstallerPackage(_arg051, _arg125);
                    reply.writeNoException();
                    return true;
                case 117:
                    ComponentName _arg052 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    String _result41 = getCertInstallerPackage(_arg052);
                    reply.writeNoException();
                    reply.writeString(_result41);
                    return true;
                case 118:
                    return onTransact$setAlwaysOnVpnPackage$(data, reply);
                case 119:
                    ComponentName _arg053 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    String _result42 = getAlwaysOnVpnPackage(_arg053);
                    reply.writeNoException();
                    reply.writeString(_result42);
                    return true;
                case 120:
                    int _arg054 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result43 = getAlwaysOnVpnPackageForUser(_arg054);
                    reply.writeNoException();
                    reply.writeString(_result43);
                    return true;
                case 121:
                    ComponentName _arg055 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result44 = isAlwaysOnVpnLockdownEnabled(_arg055);
                    reply.writeNoException();
                    reply.writeBoolean(_result44);
                    return true;
                case 122:
                    int _arg056 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result45 = isAlwaysOnVpnLockdownEnabledForUser(_arg056);
                    reply.writeNoException();
                    reply.writeBoolean(_result45);
                    return true;
                case 123:
                    ComponentName _arg057 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<String> _result46 = getAlwaysOnVpnLockdownAllowlist(_arg057);
                    reply.writeNoException();
                    reply.writeStringList(_result46);
                    return true;
                case 124:
                    return onTransact$addPersistentPreferredActivity$(data, reply);
                case 125:
                    return onTransact$clearPackagePersistentPreferredActivities$(data, reply);
                case 126:
                    return onTransact$setDefaultSmsApplication$(data, reply);
                case 127:
                    String _arg058 = data.readString();
                    data.enforceNoDataAvail();
                    setDefaultDialerApplication(_arg058);
                    reply.writeNoException();
                    return true;
                case 128:
                    return onTransact$setApplicationRestrictions$(data, reply);
                case 129:
                    return onTransact$getApplicationRestrictions$(data, reply);
                case 130:
                    ComponentName _arg059 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg126 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result47 = setApplicationRestrictionsManagingPackage(_arg059, _arg126);
                    reply.writeNoException();
                    reply.writeBoolean(_result47);
                    return true;
                case 131:
                    ComponentName _arg060 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    String _result48 = getApplicationRestrictionsManagingPackage(_arg060);
                    reply.writeNoException();
                    reply.writeString(_result48);
                    return true;
                case 132:
                    String _arg061 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result49 = isCallerApplicationRestrictionsManagingPackage(_arg061);
                    reply.writeNoException();
                    reply.writeBoolean(_result49);
                    return true;
                case 133:
                    ComponentName _arg062 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    ComponentName _arg127 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setRestrictionsProvider(_arg062, _arg127);
                    reply.writeNoException();
                    return true;
                case 134:
                    int _arg063 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result50 = getRestrictionsProvider(_arg063);
                    reply.writeNoException();
                    reply.writeTypedObject(_result50, 1);
                    return true;
                case 135:
                    return onTransact$setUserRestriction$(data, reply);
                case 136:
                    String _arg064 = data.readString();
                    String _arg128 = data.readString();
                    data.enforceNoDataAvail();
                    setUserRestrictionGlobally(_arg064, _arg128);
                    reply.writeNoException();
                    return true;
                case 137:
                    return onTransact$getUserRestrictions$(data, reply);
                case 138:
                    String _arg065 = data.readString();
                    data.enforceNoDataAvail();
                    Bundle _result51 = getUserRestrictionsGlobally(_arg065);
                    reply.writeNoException();
                    reply.writeTypedObject(_result51, 1);
                    return true;
                case 139:
                    return onTransact$addCrossProfileIntentFilter$(data, reply);
                case 140:
                    ComponentName _arg066 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg129 = data.readString();
                    data.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(_arg066, _arg129);
                    reply.writeNoException();
                    return true;
                case 141:
                    ComponentName _arg067 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    List<String> _arg130 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    boolean _result52 = setPermittedAccessibilityServices(_arg067, _arg130);
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 142:
                    ComponentName _arg068 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<String> _result53 = getPermittedAccessibilityServices(_arg068);
                    reply.writeNoException();
                    reply.writeStringList(_result53);
                    return true;
                case 143:
                    int _arg069 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result54 = getPermittedAccessibilityServicesForUser(_arg069);
                    reply.writeNoException();
                    reply.writeStringList(_result54);
                    return true;
                case 144:
                    return onTransact$isAccessibilityServicePermittedByAdmin$(data, reply);
                case 145:
                    return onTransact$setPermittedInputMethods$(data, reply);
                case 146:
                    return onTransact$getPermittedInputMethods$(data, reply);
                case 147:
                    int _arg070 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result55 = getPermittedInputMethodsAsUser(_arg070);
                    reply.writeNoException();
                    reply.writeStringList(_result55);
                    return true;
                case 148:
                    return onTransact$isInputMethodPermittedByAdmin$(data, reply);
                case 149:
                    ComponentName _arg071 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    List<String> _arg131 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    boolean _result56 =
                            setPermittedCrossProfileNotificationListeners(_arg071, _arg131);
                    reply.writeNoException();
                    reply.writeBoolean(_result56);
                    return true;
                case 150:
                    ComponentName _arg072 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<String> _result57 = getPermittedCrossProfileNotificationListeners(_arg072);
                    reply.writeNoException();
                    reply.writeStringList(_result57);
                    return true;
                case 151:
                    String _arg073 = data.readString();
                    int _arg132 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result58 = isNotificationListenerServicePermitted(_arg073, _arg132);
                    reply.writeNoException();
                    reply.writeBoolean(_result58);
                    return true;
                case 152:
                    String _arg074 = data.readString();
                    data.enforceNoDataAvail();
                    Intent _result59 = createAdminSupportIntent(_arg074);
                    reply.writeNoException();
                    reply.writeTypedObject(_result59, 1);
                    return true;
                case 153:
                    int _arg075 = data.readInt();
                    String _arg133 = data.readString();
                    data.enforceNoDataAvail();
                    Bundle _result60 = getEnforcingAdminAndUserDetails(_arg075, _arg133);
                    reply.writeNoException();
                    reply.writeTypedObject(_result60, 1);
                    return true;
                case 154:
                    int _arg076 = data.readInt();
                    String _arg134 = data.readString();
                    data.enforceNoDataAvail();
                    List<EnforcingAdmin> _result61 =
                            getEnforcingAdminsForRestriction(_arg076, _arg134);
                    reply.writeNoException();
                    reply.writeTypedList(_result61, 1);
                    return true;
                case 155:
                    return onTransact$setApplicationHidden$(data, reply);
                case 156:
                    return onTransact$isApplicationHidden$(data, reply);
                case 157:
                    return onTransact$createAndManageUser$(data, reply);
                case 158:
                    ComponentName _arg077 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    UserHandle _arg135 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result62 = removeUser(_arg077, _arg135);
                    reply.writeNoException();
                    reply.writeBoolean(_result62);
                    return true;
                case 159:
                    ComponentName _arg078 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    UserHandle _arg136 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result63 = switchUser(_arg078, _arg136);
                    reply.writeNoException();
                    reply.writeBoolean(_result63);
                    return true;
                case 160:
                    ComponentName _arg079 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    UserHandle _arg137 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    int _result64 = startUserInBackground(_arg079, _arg137);
                    reply.writeNoException();
                    reply.writeInt(_result64);
                    return true;
                case 161:
                    ComponentName _arg080 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    UserHandle _arg138 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    int _result65 = stopUser(_arg080, _arg138);
                    reply.writeNoException();
                    reply.writeInt(_result65);
                    return true;
                case 162:
                    ComponentName _arg081 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int _result66 = logoutUser(_arg081);
                    reply.writeNoException();
                    reply.writeInt(_result66);
                    return true;
                case 163:
                    int _result67 = logoutUserInternal();
                    reply.writeNoException();
                    reply.writeInt(_result67);
                    return true;
                case 164:
                    int _result68 = getLogoutUserId();
                    reply.writeNoException();
                    reply.writeInt(_result68);
                    return true;
                case 165:
                    ComponentName _arg082 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<UserHandle> _result69 = getSecondaryUsers(_arg082);
                    reply.writeNoException();
                    reply.writeTypedList(_result69, 1);
                    return true;
                case 166:
                    int _arg083 = data.readInt();
                    data.enforceNoDataAvail();
                    acknowledgeNewUserDisclaimer(_arg083);
                    reply.writeNoException();
                    return true;
                case 167:
                    int _arg084 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result70 = isNewUserDisclaimerAcknowledged(_arg084);
                    reply.writeNoException();
                    reply.writeBoolean(_result70);
                    return true;
                case 168:
                    return onTransact$enableSystemApp$(data, reply);
                case 169:
                    return onTransact$enableSystemAppWithIntent$(data, reply);
                case 170:
                    return onTransact$installExistingPackage$(data, reply);
                case 171:
                    return onTransact$setAccountManagementDisabled$(data, reply);
                case 172:
                    String _arg085 = data.readString();
                    data.enforceNoDataAvail();
                    String[] _result71 = getAccountTypesWithManagementDisabled(_arg085);
                    reply.writeNoException();
                    reply.writeStringArray(_result71);
                    return true;
                case 173:
                    return onTransact$getAccountTypesWithManagementDisabledAsUser$(data, reply);
                case 174:
                    ComponentName _arg086 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg139 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSecondaryLockscreenEnabled(_arg086, _arg139);
                    reply.writeNoException();
                    return true;
                case 175:
                    UserHandle _arg087 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result72 = isSecondaryLockscreenEnabled(_arg087);
                    reply.writeNoException();
                    reply.writeBoolean(_result72);
                    return true;
                case 176:
                    List<PreferentialNetworkServiceConfig> _arg088 =
                            data.createTypedArrayList(PreferentialNetworkServiceConfig.CREATOR);
                    data.enforceNoDataAvail();
                    setPreferentialNetworkServiceConfigs(_arg088);
                    reply.writeNoException();
                    return true;
                case 177:
                    List<PreferentialNetworkServiceConfig> _result73 =
                            getPreferentialNetworkServiceConfigs();
                    reply.writeNoException();
                    reply.writeTypedList(_result73, 1);
                    return true;
                case 178:
                    return onTransact$setLockTaskPackages$(data, reply);
                case 179:
                    ComponentName _arg089 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg140 = data.readString();
                    data.enforceNoDataAvail();
                    String[] _result74 = getLockTaskPackages(_arg089, _arg140);
                    reply.writeNoException();
                    reply.writeStringArray(_result74);
                    return true;
                case 180:
                    String _arg090 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result75 = isLockTaskPermitted(_arg090);
                    reply.writeNoException();
                    reply.writeBoolean(_result75);
                    return true;
                case 181:
                    return onTransact$setLockTaskFeatures$(data, reply);
                case 182:
                    ComponentName _arg091 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg141 = data.readString();
                    data.enforceNoDataAvail();
                    int _result76 = getLockTaskFeatures(_arg091, _arg141);
                    reply.writeNoException();
                    reply.writeInt(_result76);
                    return true;
                case 183:
                    return onTransact$setGlobalSetting$(data, reply);
                case 184:
                    return onTransact$setSystemSetting$(data, reply);
                case 185:
                    return onTransact$setSecureSetting$(data, reply);
                case 186:
                    return onTransact$setConfiguredNetworksLockdownState$(data, reply);
                case 187:
                    ComponentName _arg092 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result77 = hasLockdownAdminConfiguredNetworks(_arg092);
                    reply.writeNoException();
                    reply.writeBoolean(_result77);
                    return true;
                case 188:
                    ComponentName _arg093 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg142 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLocationEnabled(_arg093, _arg142);
                    reply.writeNoException();
                    return true;
                case 189:
                    return onTransact$setTime$(data, reply);
                case 190:
                    return onTransact$setTimeZone$(data, reply);
                case 191:
                    ComponentName _arg094 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg143 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMasterVolumeMuted(_arg094, _arg143);
                    reply.writeNoException();
                    return true;
                case 192:
                    ComponentName _arg095 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result78 = isMasterVolumeMuted(_arg095);
                    reply.writeNoException();
                    reply.writeBoolean(_result78);
                    return true;
                case 193:
                    return onTransact$notifyLockTaskModeChanged$(data, reply);
                case 194:
                    return onTransact$setUninstallBlocked$(data, reply);
                case 195:
                    String _arg096 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result79 = isUninstallBlocked(_arg096);
                    reply.writeNoException();
                    reply.writeBoolean(_result79);
                    return true;
                case 196:
                    ComponentName _arg097 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg144 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setCrossProfileCallerIdDisabled(_arg097, _arg144);
                    reply.writeNoException();
                    return true;
                case 197:
                    ComponentName _arg098 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result80 = getCrossProfileCallerIdDisabled(_arg098);
                    reply.writeNoException();
                    reply.writeBoolean(_result80);
                    return true;
                case 198:
                    int _arg099 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result81 = getCrossProfileCallerIdDisabledForUser(_arg099);
                    reply.writeNoException();
                    reply.writeBoolean(_result81);
                    return true;
                case 199:
                    ComponentName _arg0100 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg145 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setCrossProfileContactsSearchDisabled(_arg0100, _arg145);
                    reply.writeNoException();
                    return true;
                case 200:
                    ComponentName _arg0101 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result82 = getCrossProfileContactsSearchDisabled(_arg0101);
                    reply.writeNoException();
                    reply.writeBoolean(_result82);
                    return true;
                case 201:
                    int _arg0102 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result83 = getCrossProfileContactsSearchDisabledForUser(_arg0102);
                    reply.writeNoException();
                    reply.writeBoolean(_result83);
                    return true;
                case 202:
                    return onTransact$startManagedQuickContact$(data, reply);
                case 203:
                    PackagePolicy _arg0103 =
                            (PackagePolicy) data.readTypedObject(PackagePolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setManagedProfileCallerIdAccessPolicy(_arg0103);
                    reply.writeNoException();
                    return true;
                case 204:
                    PackagePolicy _result84 = getManagedProfileCallerIdAccessPolicy();
                    reply.writeNoException();
                    reply.writeTypedObject(_result84, 1);
                    return true;
                case 205:
                    int _arg0104 = data.readInt();
                    String _arg146 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result85 = hasManagedProfileCallerIdAccess(_arg0104, _arg146);
                    reply.writeNoException();
                    reply.writeBoolean(_result85);
                    return true;
                case 206:
                    PackagePolicy _arg0105 =
                            (PackagePolicy) data.readTypedObject(PackagePolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setCredentialManagerPolicy(_arg0105);
                    reply.writeNoException();
                    return true;
                case 207:
                    int _arg0106 = data.readInt();
                    data.enforceNoDataAvail();
                    PackagePolicy _result86 = getCredentialManagerPolicy(_arg0106);
                    reply.writeNoException();
                    reply.writeTypedObject(_result86, 1);
                    return true;
                case 208:
                    PackagePolicy _arg0107 =
                            (PackagePolicy) data.readTypedObject(PackagePolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setManagedProfileContactsAccessPolicy(_arg0107);
                    reply.writeNoException();
                    return true;
                case 209:
                    PackagePolicy _result87 = getManagedProfileContactsAccessPolicy();
                    reply.writeNoException();
                    reply.writeTypedObject(_result87, 1);
                    return true;
                case 210:
                    int _arg0108 = data.readInt();
                    String _arg147 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result88 = hasManagedProfileContactsAccess(_arg0108, _arg147);
                    reply.writeNoException();
                    reply.writeBoolean(_result88);
                    return true;
                case 211:
                    ComponentName _arg0109 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg148 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBluetoothContactSharingDisabled(_arg0109, _arg148);
                    reply.writeNoException();
                    return true;
                case 212:
                    ComponentName _arg0110 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result89 = getBluetoothContactSharingDisabled(_arg0110);
                    reply.writeNoException();
                    reply.writeBoolean(_result89);
                    return true;
                case 213:
                    int _arg0111 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result90 = getBluetoothContactSharingDisabledForUser(_arg0111);
                    reply.writeNoException();
                    reply.writeBoolean(_result90);
                    return true;
                case 214:
                    return onTransact$setTrustAgentConfiguration$(data, reply);
                case 215:
                    return onTransact$getTrustAgentConfiguration$(data, reply);
                case 216:
                    return onTransact$addCrossProfileWidgetProvider$(data, reply);
                case 217:
                    return onTransact$removeCrossProfileWidgetProvider$(data, reply);
                case 218:
                    ComponentName _arg0112 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg149 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result91 = getCrossProfileWidgetProviders(_arg0112, _arg149);
                    reply.writeNoException();
                    reply.writeStringList(_result91);
                    return true;
                case 219:
                    ComponentName _arg0113 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg150 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAutoTimeRequired(_arg0113, _arg150);
                    reply.writeNoException();
                    return true;
                case 220:
                    boolean _result92 = getAutoTimeRequired();
                    reply.writeNoException();
                    reply.writeBoolean(_result92);
                    return true;
                case 221:
                    return onTransact$setAutoTimeEnabled$(data, reply);
                case 222:
                    ComponentName _arg0114 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg151 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result93 = getAutoTimeEnabled(_arg0114, _arg151);
                    reply.writeNoException();
                    reply.writeBoolean(_result93);
                    return true;
                case 223:
                    return onTransact$setAutoTimeZoneEnabled$(data, reply);
                case 224:
                    ComponentName _arg0115 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg152 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result94 = getAutoTimeZoneEnabled(_arg0115, _arg152);
                    reply.writeNoException();
                    reply.writeBoolean(_result94);
                    return true;
                case 225:
                    ComponentName _arg0116 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg153 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setForceEphemeralUsers(_arg0116, _arg153);
                    reply.writeNoException();
                    return true;
                case 226:
                    ComponentName _arg0117 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result95 = getForceEphemeralUsers(_arg0117);
                    reply.writeNoException();
                    reply.writeBoolean(_result95);
                    return true;
                case 227:
                    ComponentName _arg0118 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg154 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result96 = isRemovingAdmin(_arg0118, _arg154);
                    reply.writeNoException();
                    reply.writeBoolean(_result96);
                    return true;
                case 228:
                    ComponentName _arg0119 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    Bitmap _arg155 = (Bitmap) data.readTypedObject(Bitmap.CREATOR);
                    data.enforceNoDataAvail();
                    setUserIcon(_arg0119, _arg155);
                    reply.writeNoException();
                    return true;
                case 229:
                    return onTransact$setSystemUpdatePolicy$(data, reply);
                case 230:
                    SystemUpdatePolicy _result97 = getSystemUpdatePolicy();
                    reply.writeNoException();
                    reply.writeTypedObject(_result97, 1);
                    return true;
                case 231:
                    clearSystemUpdatePolicyFreezePeriodRecord();
                    reply.writeNoException();
                    return true;
                case 232:
                    ComponentName _arg0120 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg156 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result98 = setKeyguardDisabled(_arg0120, _arg156);
                    reply.writeNoException();
                    reply.writeBoolean(_result98);
                    return true;
                case 233:
                    return onTransact$setStatusBarDisabled$(data, reply);
                case 234:
                    String _arg0121 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result99 = isStatusBarDisabled(_arg0121);
                    reply.writeNoException();
                    reply.writeBoolean(_result99);
                    return true;
                case 235:
                    boolean _result100 = getDoNotAskCredentialsOnBoot();
                    reply.writeNoException();
                    reply.writeBoolean(_result100);
                    return true;
                case 236:
                    SystemUpdateInfo _arg0122 =
                            (SystemUpdateInfo) data.readTypedObject(SystemUpdateInfo.CREATOR);
                    data.enforceNoDataAvail();
                    notifyPendingSystemUpdate(_arg0122);
                    reply.writeNoException();
                    return true;
                case 237:
                    ComponentName _arg0123 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg157 = data.readString();
                    data.enforceNoDataAvail();
                    SystemUpdateInfo _result101 = getPendingSystemUpdate(_arg0123, _arg157);
                    reply.writeNoException();
                    reply.writeTypedObject(_result101, 1);
                    return true;
                case 238:
                    return onTransact$setPermissionPolicy$(data, reply);
                case 239:
                    ComponentName _arg0124 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int _result102 = getPermissionPolicy(_arg0124);
                    reply.writeNoException();
                    reply.writeInt(_result102);
                    return true;
                case 240:
                    return onTransact$setPermissionGrantState$(data, reply);
                case 241:
                    return onTransact$getPermissionGrantState$(data, reply);
                case 242:
                    String _arg0125 = data.readString();
                    String _arg158 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result103 = isProvisioningAllowed(_arg0125, _arg158);
                    reply.writeNoException();
                    reply.writeBoolean(_result103);
                    return true;
                case 243:
                    String _arg0126 = data.readString();
                    String _arg159 = data.readString();
                    data.enforceNoDataAvail();
                    int _result104 = checkProvisioningPrecondition(_arg0126, _arg159);
                    reply.writeNoException();
                    reply.writeInt(_result104);
                    return true;
                case 244:
                    return onTransact$setKeepUninstalledPackages$(data, reply);
                case 245:
                    ComponentName _arg0127 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg160 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result105 = getKeepUninstalledPackages(_arg0127, _arg160);
                    reply.writeNoException();
                    reply.writeStringList(_result105);
                    return true;
                case 246:
                    ComponentName _arg0128 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result106 = isManagedProfile(_arg0128);
                    reply.writeNoException();
                    reply.writeBoolean(_result106);
                    return true;
                case 247:
                    ComponentName _arg0129 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg161 = data.readString();
                    data.enforceNoDataAvail();
                    String _result107 = getWifiMacAddress(_arg0129, _arg161);
                    reply.writeNoException();
                    reply.writeString(_result107);
                    return true;
                case 248:
                    ComponentName _arg0130 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    reboot(_arg0130);
                    reply.writeNoException();
                    return true;
                case 249:
                    return onTransact$setShortSupportMessage$(data, reply);
                case 250:
                    ComponentName _arg0131 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg162 = data.readString();
                    data.enforceNoDataAvail();
                    CharSequence _result108 = getShortSupportMessage(_arg0131, _arg162);
                    reply.writeNoException();
                    if (_result108 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result108, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 251:
                    ComponentName _arg0132 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    CharSequence _arg163 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    setLongSupportMessage(_arg0132, _arg163);
                    reply.writeNoException();
                    return true;
                case 252:
                    ComponentName _arg0133 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    CharSequence _result109 = getLongSupportMessage(_arg0133);
                    reply.writeNoException();
                    if (_result109 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result109, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 253:
                    ComponentName _arg0134 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg164 = data.readInt();
                    data.enforceNoDataAvail();
                    CharSequence _result110 = getShortSupportMessageForUser(_arg0134, _arg164);
                    reply.writeNoException();
                    if (_result110 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result110, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 254:
                    ComponentName _arg0135 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg165 = data.readInt();
                    data.enforceNoDataAvail();
                    CharSequence _result111 = getLongSupportMessageForUser(_arg0135, _arg165);
                    reply.writeNoException();
                    if (_result111 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result111, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 255:
                    ComponentName _arg0136 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg166 = data.readInt();
                    data.enforceNoDataAvail();
                    setOrganizationColor(_arg0136, _arg166);
                    reply.writeNoException();
                    return true;
                case 256:
                    int _arg0137 = data.readInt();
                    int _arg167 = data.readInt();
                    data.enforceNoDataAvail();
                    setOrganizationColorForUser(_arg0137, _arg167);
                    reply.writeNoException();
                    return true;
                case 257:
                    int _arg0138 = data.readInt();
                    data.enforceNoDataAvail();
                    clearOrganizationIdForUser(_arg0138);
                    reply.writeNoException();
                    return true;
                case 258:
                    ComponentName _arg0139 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int _result112 = getOrganizationColor(_arg0139);
                    reply.writeNoException();
                    reply.writeInt(_result112);
                    return true;
                case 259:
                    int _arg0140 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result113 = getOrganizationColorForUser(_arg0140);
                    reply.writeNoException();
                    reply.writeInt(_result113);
                    return true;
                case 260:
                    return onTransact$setOrganizationName$(data, reply);
                case 261:
                    ComponentName _arg0141 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg168 = data.readString();
                    data.enforceNoDataAvail();
                    CharSequence _result114 = getOrganizationName(_arg0141, _arg168);
                    reply.writeNoException();
                    if (_result114 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result114, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 262:
                    CharSequence _result115 = getDeviceOwnerOrganizationName();
                    reply.writeNoException();
                    if (_result115 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result115, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 263:
                    int _arg0142 = data.readInt();
                    data.enforceNoDataAvail();
                    CharSequence _result116 = getOrganizationNameForUser(_arg0142);
                    reply.writeNoException();
                    if (_result116 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result116, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 264:
                    int _arg0143 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result117 = getUserProvisioningState(_arg0143);
                    reply.writeNoException();
                    reply.writeInt(_result117);
                    return true;
                case 265:
                    int _arg0144 = data.readInt();
                    int _arg169 = data.readInt();
                    data.enforceNoDataAvail();
                    setUserProvisioningState(_arg0144, _arg169);
                    reply.writeNoException();
                    return true;
                case 266:
                    ComponentName _arg0145 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    List<String> _arg170 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    setAffiliationIds(_arg0145, _arg170);
                    reply.writeNoException();
                    return true;
                case 267:
                    ComponentName _arg0146 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<String> _result118 = getAffiliationIds(_arg0146);
                    reply.writeNoException();
                    reply.writeStringList(_result118);
                    return true;
                case 268:
                    boolean _result119 = isCallingUserAffiliated();
                    reply.writeNoException();
                    reply.writeBoolean(_result119);
                    return true;
                case 269:
                    int _arg0147 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result120 = isAffiliatedUser(_arg0147);
                    reply.writeNoException();
                    reply.writeBoolean(_result120);
                    return true;
                case 270:
                    return onTransact$setSecurityLoggingEnabled$(data, reply);
                case 271:
                    ComponentName _arg0148 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg171 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result121 = isSecurityLoggingEnabled(_arg0148, _arg171);
                    reply.writeNoException();
                    reply.writeBoolean(_result121);
                    return true;
                case 272:
                    ComponentName _arg0149 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg172 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result122 = retrieveSecurityLogs(_arg0149, _arg172);
                    reply.writeNoException();
                    reply.writeTypedObject(_result122, 1);
                    return true;
                case 273:
                    ComponentName _arg0150 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg173 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result123 = retrievePreRebootSecurityLogs(_arg0150, _arg173);
                    reply.writeNoException();
                    reply.writeTypedObject(_result123, 1);
                    return true;
                case 274:
                    long _result124 = forceNetworkLogs();
                    reply.writeNoException();
                    reply.writeLong(_result124);
                    return true;
                case 275:
                    long _result125 = forceSecurityLogs();
                    reply.writeNoException();
                    reply.writeLong(_result125);
                    return true;
                case 276:
                    String _arg0151 = data.readString();
                    boolean _arg174 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAuditLogEnabled(_arg0151, _arg174);
                    reply.writeNoException();
                    return true;
                case 277:
                    String _arg0152 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result126 = isAuditLogEnabled(_arg0152);
                    reply.writeNoException();
                    reply.writeBoolean(_result126);
                    return true;
                case 278:
                    String _arg0153 = data.readString();
                    IAuditLogEventsCallback _arg175 =
                            IAuditLogEventsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setAuditLogEventsCallback(_arg0153, _arg175);
                    reply.writeNoException();
                    return true;
                case 279:
                    String _arg0154 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result127 = isUninstallInQueue(_arg0154);
                    reply.writeNoException();
                    reply.writeBoolean(_result127);
                    return true;
                case 280:
                    String _arg0155 = data.readString();
                    data.enforceNoDataAvail();
                    uninstallPackageWithActiveAdmins(_arg0155);
                    reply.writeNoException();
                    return true;
                case 281:
                    boolean _result128 = isDeviceProvisioned();
                    reply.writeNoException();
                    reply.writeBoolean(_result128);
                    return true;
                case 282:
                    boolean _result129 = isDeviceProvisioningConfigApplied();
                    reply.writeNoException();
                    reply.writeBoolean(_result129);
                    return true;
                case 283:
                    setDeviceProvisioningConfigApplied();
                    reply.writeNoException();
                    return true;
                case 284:
                    int _arg0156 = data.readInt();
                    data.enforceNoDataAvail();
                    forceUpdateUserSetupComplete(_arg0156);
                    reply.writeNoException();
                    return true;
                case 285:
                    ComponentName _arg0157 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg176 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBackupServiceEnabled(_arg0157, _arg176);
                    reply.writeNoException();
                    return true;
                case 286:
                    ComponentName _arg0158 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result130 = isBackupServiceEnabled(_arg0158);
                    reply.writeNoException();
                    reply.writeBoolean(_result130);
                    return true;
                case 287:
                    return onTransact$setNetworkLoggingEnabled$(data, reply);
                case 288:
                    ComponentName _arg0159 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg177 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result131 = isNetworkLoggingEnabled(_arg0159, _arg177);
                    reply.writeNoException();
                    reply.writeBoolean(_result131);
                    return true;
                case 289:
                    return onTransact$retrieveNetworkLogs$(data, reply);
                case 290:
                    return onTransact$bindDeviceAdminServiceAsUser$(data, reply);
                case 291:
                    ComponentName _arg0160 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<UserHandle> _result132 = getBindDeviceAdminTargetUsers(_arg0160);
                    reply.writeNoException();
                    reply.writeTypedList(_result132, 1);
                    return true;
                case 292:
                    ComponentName _arg0161 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result133 = isEphemeralUser(_arg0161);
                    reply.writeNoException();
                    reply.writeBoolean(_result133);
                    return true;
                case 293:
                    long _result134 = getLastSecurityLogRetrievalTime();
                    reply.writeNoException();
                    reply.writeLong(_result134);
                    return true;
                case 294:
                    long _result135 = getLastBugReportRequestTime();
                    reply.writeNoException();
                    reply.writeLong(_result135);
                    return true;
                case 295:
                    long _result136 = getLastNetworkLogRetrievalTime();
                    reply.writeNoException();
                    reply.writeLong(_result136);
                    return true;
                case 296:
                    return onTransact$setResetPasswordToken$(data, reply);
                case 297:
                    ComponentName _arg0162 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg178 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result137 = clearResetPasswordToken(_arg0162, _arg178);
                    reply.writeNoException();
                    reply.writeBoolean(_result137);
                    return true;
                case 298:
                    ComponentName _arg0163 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg179 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result138 = isResetPasswordTokenActive(_arg0163, _arg179);
                    reply.writeNoException();
                    reply.writeBoolean(_result138);
                    return true;
                case 299:
                    return onTransact$resetPasswordWithToken$(data, reply);
                case 300:
                    boolean _result139 = isCurrentInputMethodSetByOwner();
                    reply.writeNoException();
                    reply.writeBoolean(_result139);
                    return true;
                case 301:
                    UserHandle _arg0164 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    StringParceledListSlice _result140 = getOwnerInstalledCaCerts(_arg0164);
                    reply.writeNoException();
                    reply.writeTypedObject(_result140, 1);
                    return true;
                case 302:
                    return onTransact$clearApplicationUserData$(data, reply);
                case 303:
                    ComponentName _arg0165 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg180 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLogoutEnabled(_arg0165, _arg180);
                    reply.writeNoException();
                    return true;
                case 304:
                    boolean _result141 = isLogoutEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result141);
                    return true;
                case 305:
                    return onTransact$getDisallowedSystemApps$(data, reply);
                case 306:
                    return onTransact$transferOwnership$(data, reply);
                case 307:
                    PersistableBundle _result142 = getTransferOwnershipBundle();
                    reply.writeNoException();
                    reply.writeTypedObject(_result142, 1);
                    return true;
                case 308:
                    ComponentName _arg0166 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    CharSequence _arg181 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    setStartUserSessionMessage(_arg0166, _arg181);
                    reply.writeNoException();
                    return true;
                case 309:
                    ComponentName _arg0167 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    CharSequence _arg182 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    setEndUserSessionMessage(_arg0167, _arg182);
                    reply.writeNoException();
                    return true;
                case 310:
                    ComponentName _arg0168 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    CharSequence _result143 = getStartUserSessionMessage(_arg0168);
                    reply.writeNoException();
                    if (_result143 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result143, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 311:
                    ComponentName _arg0169 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    CharSequence _result144 = getEndUserSessionMessage(_arg0169);
                    reply.writeNoException();
                    if (_result144 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result144, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 312:
                    ComponentName _arg0170 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    List<String> _arg183 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    List<String> _result145 = setMeteredDataDisabledPackages(_arg0170, _arg183);
                    reply.writeNoException();
                    reply.writeStringList(_result145);
                    return true;
                case 313:
                    ComponentName _arg0171 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<String> _result146 = getMeteredDataDisabledPackages(_arg0171);
                    reply.writeNoException();
                    reply.writeStringList(_result146);
                    return true;
                case 314:
                    ComponentName _arg0172 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    ApnSetting _arg184 = (ApnSetting) data.readTypedObject(ApnSetting.CREATOR);
                    data.enforceNoDataAvail();
                    int _result147 = addOverrideApn(_arg0172, _arg184);
                    reply.writeNoException();
                    reply.writeInt(_result147);
                    return true;
                case 315:
                    return onTransact$updateOverrideApn$(data, reply);
                case 316:
                    ComponentName _arg0173 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg185 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result148 = removeOverrideApn(_arg0173, _arg185);
                    reply.writeNoException();
                    reply.writeBoolean(_result148);
                    return true;
                case 317:
                    ComponentName _arg0174 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<ApnSetting> _result149 = getOverrideApns(_arg0174);
                    reply.writeNoException();
                    reply.writeTypedList(_result149, 1);
                    return true;
                case 318:
                    ComponentName _arg0175 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg186 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setOverrideApnsEnabled(_arg0175, _arg186);
                    reply.writeNoException();
                    return true;
                case 319:
                    ComponentName _arg0176 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result150 = isOverrideApnEnabled(_arg0176);
                    reply.writeNoException();
                    reply.writeBoolean(_result150);
                    return true;
                case 320:
                    return onTransact$isMeteredDataDisabledPackageForUser$(data, reply);
                case 321:
                    return onTransact$setPasswordQualityMDM$(data, reply);
                case 322:
                    return onTransact$setPasswordMinimumLengthMDM$(data, reply);
                case 323:
                    return onTransact$setPasswordMinimumUpperCaseMDM$(data, reply);
                case 324:
                    return onTransact$setPasswordMinimumLowerCaseMDM$(data, reply);
                case 325:
                    return onTransact$setPasswordMinimumLettersMDM$(data, reply);
                case 326:
                    return onTransact$setPasswordMinimumNumericMDM$(data, reply);
                case 327:
                    return onTransact$setPasswordMinimumSymbolsMDM$(data, reply);
                case 328:
                    return onTransact$setPasswordMinimumNonLetterMDM$(data, reply);
                case 329:
                    return onTransact$setPasswordHistoryLengthMDM$(data, reply);
                case 330:
                    return onTransact$setPasswordExpirationTimeoutMDM$(data, reply);
                case 331:
                    return onTransact$setMaximumFailedPasswordsForWipeMDM$(data, reply);
                case 332:
                    return onTransact$setMaximumTimeToLockMDM$(data, reply);
                case 333:
                    return onTransact$setKeyguardDisabledFeaturesMDM$(data, reply);
                case 334:
                    return onTransact$setApplicationRestrictionsMDM$(data, reply);
                case 335:
                    return onTransact$getApplicationRestrictionsMDM$(data, reply);
                case 336:
                    return onTransact$resetPasswordWithTokenMDM$(data, reply);
                case 337:
                    ComponentName _arg0177 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg187 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result151 = isResetPasswordTokenActiveMDM(_arg0177, _arg187);
                    reply.writeNoException();
                    reply.writeBoolean(_result151);
                    return true;
                case 338:
                    ComponentName _arg0178 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg188 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result152 = clearResetPasswordTokenMDM(_arg0178, _arg188);
                    reply.writeNoException();
                    reply.writeBoolean(_result152);
                    return true;
                case 339:
                    return onTransact$setResetPasswordTokenMDM$(data, reply);
                case 340:
                    return onTransact$setTrustAgentConfigurationMDM$(data, reply);
                case 341:
                    int _arg0179 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result153 = isProfileOwnerOfOrganizationOwnedDeviceMDM(_arg0179);
                    reply.writeNoException();
                    reply.writeBoolean(_result153);
                    return true;
                case 342:
                    return onTransact$hasDelegatedPermission$(data, reply);
                case 343:
                    int _arg0180 = data.readInt();
                    data.enforceNoDataAvail();
                    Map _result154 = getDelegatedPackages(_arg0180);
                    reply.writeNoException();
                    reply.writeMap(_result154);
                    return true;
                case 344:
                    return onTransact$reportFailedPasswordAttemptWithFailureCount$(data, reply);
                case 345:
                    return onTransact$setGlobalPrivateDns$(data, reply);
                case 346:
                    ComponentName _arg0181 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int _result155 = getGlobalPrivateDnsMode(_arg0181);
                    reply.writeNoException();
                    reply.writeInt(_result155);
                    return true;
                case 347:
                    ComponentName _arg0182 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    String _result156 = getGlobalPrivateDnsHost(_arg0182);
                    reply.writeNoException();
                    reply.writeString(_result156);
                    return true;
                case 348:
                    return onTransact$setProfileOwnerOnOrganizationOwnedDevice$(data, reply);
                case 349:
                    return onTransact$installUpdateFromFile$(data, reply);
                case 350:
                    ComponentName _arg0183 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    List<String> _arg189 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    setCrossProfileCalendarPackages(_arg0183, _arg189);
                    reply.writeNoException();
                    return true;
                case 351:
                    ComponentName _arg0184 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<String> _result157 = getCrossProfileCalendarPackages(_arg0184);
                    reply.writeNoException();
                    reply.writeStringList(_result157);
                    return true;
                case 352:
                    String _arg0185 = data.readString();
                    int _arg190 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result158 = isPackageAllowedToAccessCalendarForUser(_arg0185, _arg190);
                    reply.writeNoException();
                    reply.writeBoolean(_result158);
                    return true;
                case 353:
                    int _arg0186 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result159 = getCrossProfileCalendarPackagesForUser(_arg0186);
                    reply.writeNoException();
                    reply.writeStringList(_result159);
                    return true;
                case 354:
                    ComponentName _arg0187 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    List<String> _arg191 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    setCrossProfilePackages(_arg0187, _arg191);
                    reply.writeNoException();
                    return true;
                case 355:
                    ComponentName _arg0188 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    List<String> _result160 = getCrossProfilePackages(_arg0188);
                    reply.writeNoException();
                    reply.writeStringList(_result160);
                    return true;
                case 356:
                    int _arg0189 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result161 = getAllCrossProfilePackages(_arg0189);
                    reply.writeNoException();
                    reply.writeStringList(_result161);
                    return true;
                case 357:
                    List<String> _result162 = getDefaultCrossProfilePackages();
                    reply.writeNoException();
                    reply.writeStringList(_result162);
                    return true;
                case 358:
                    boolean _result163 = isManagedKiosk();
                    reply.writeNoException();
                    reply.writeBoolean(_result163);
                    return true;
                case 359:
                    boolean _result164 = isUnattendedManagedKiosk();
                    reply.writeNoException();
                    reply.writeBoolean(_result164);
                    return true;
                case 360:
                    return onTransact$startViewCalendarEventInManagedProfile$(data, reply);
                case 361:
                    return onTransact$setKeyGrantForApp$(data, reply);
                case 362:
                    String _arg0190 = data.readString();
                    String _arg192 = data.readString();
                    data.enforceNoDataAvail();
                    ParcelableGranteeMap _result165 = getKeyPairGrants(_arg0190, _arg192);
                    reply.writeNoException();
                    reply.writeTypedObject(_result165, 1);
                    return true;
                case 363:
                    return onTransact$setKeyGrantToWifiAuth$(data, reply);
                case 364:
                    String _arg0191 = data.readString();
                    String _arg193 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result166 = isKeyPairGrantedToWifiAuth(_arg0191, _arg193);
                    reply.writeNoException();
                    reply.writeBoolean(_result166);
                    return true;
                case 365:
                    return onTransact$setUserControlDisabledPackages$(data, reply);
                case 366:
                    ComponentName _arg0192 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg194 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result167 = getUserControlDisabledPackages(_arg0192, _arg194);
                    reply.writeNoException();
                    reply.writeStringList(_result167);
                    return true;
                case 367:
                    return onTransact$setCommonCriteriaModeEnabled$(data, reply);
                case 368:
                    ComponentName _arg0193 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result168 = isCommonCriteriaModeEnabled(_arg0193);
                    reply.writeNoException();
                    reply.writeBoolean(_result168);
                    return true;
                case 369:
                    ComponentName _arg0194 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int _result169 = getPersonalAppsSuspendedReasons(_arg0194);
                    reply.writeNoException();
                    reply.writeInt(_result169);
                    return true;
                case 370:
                    ComponentName _arg0195 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg195 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setPersonalAppsSuspended(_arg0195, _arg195);
                    reply.writeNoException();
                    return true;
                case 371:
                    ComponentName _arg0196 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    long _result170 = getManagedProfileMaximumTimeOff(_arg0196);
                    reply.writeNoException();
                    reply.writeLong(_result170);
                    return true;
                case 372:
                    ComponentName _arg0197 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    long _arg196 = data.readLong();
                    data.enforceNoDataAvail();
                    setManagedProfileMaximumTimeOff(_arg0197, _arg196);
                    reply.writeNoException();
                    return true;
                case 373:
                    acknowledgeDeviceCompliant();
                    reply.writeNoException();
                    return true;
                case 374:
                    boolean _result171 = isComplianceAcknowledgementRequired();
                    reply.writeNoException();
                    reply.writeBoolean(_result171);
                    return true;
                case 375:
                    int _arg0198 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result172 = canProfileOwnerResetPasswordWhenLocked(_arg0198);
                    reply.writeNoException();
                    reply.writeBoolean(_result172);
                    return true;
                case 376:
                    int _arg0199 = data.readInt();
                    int _arg197 = data.readInt();
                    data.enforceNoDataAvail();
                    setNextOperationSafety(_arg0199, _arg197);
                    reply.writeNoException();
                    return true;
                case 377:
                    int _arg0200 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result173 = isSafeOperation(_arg0200);
                    reply.writeNoException();
                    reply.writeBoolean(_result173);
                    return true;
                case 378:
                    String _arg0201 = data.readString();
                    data.enforceNoDataAvail();
                    String _result174 = getEnrollmentSpecificId(_arg0201);
                    reply.writeNoException();
                    reply.writeString(_result174);
                    return true;
                case 379:
                    return onTransact$setOrganizationIdForUser$(data, reply);
                case 380:
                    ManagedProfileProvisioningParams _arg0202 =
                            (ManagedProfileProvisioningParams)
                                    data.readTypedObject(ManagedProfileProvisioningParams.CREATOR);
                    String _arg198 = data.readString();
                    data.enforceNoDataAvail();
                    UserHandle _result175 = createAndProvisionManagedProfile(_arg0202, _arg198);
                    reply.writeNoException();
                    reply.writeTypedObject(_result175, 1);
                    return true;
                case 381:
                    FullyManagedDeviceProvisioningParams _arg0203 =
                            (FullyManagedDeviceProvisioningParams)
                                    data.readTypedObject(
                                            FullyManagedDeviceProvisioningParams.CREATOR);
                    String _arg199 = data.readString();
                    data.enforceNoDataAvail();
                    provisionFullyManagedDevice(_arg0203, _arg199);
                    reply.writeNoException();
                    return true;
                case 382:
                    UserHandle _arg0204 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    Account _arg1100 = (Account) data.readTypedObject(Account.CREATOR);
                    data.enforceNoDataAvail();
                    finalizeWorkProfileProvisioning(_arg0204, _arg1100);
                    reply.writeNoException();
                    return true;
                case 383:
                    ComponentName _arg0205 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg1101 = data.readInt();
                    data.enforceNoDataAvail();
                    setDeviceOwnerType(_arg0205, _arg1101);
                    reply.writeNoException();
                    return true;
                case 384:
                    ComponentName _arg0206 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int _result176 = getDeviceOwnerType(_arg0206);
                    reply.writeNoException();
                    reply.writeInt(_result176);
                    return true;
                case 385:
                    int _arg0207 = data.readInt();
                    data.enforceNoDataAvail();
                    resetDefaultCrossProfileIntentFilters(_arg0207);
                    reply.writeNoException();
                    return true;
                case 386:
                    boolean _result177 = canAdminGrantSensorsPermissions();
                    reply.writeNoException();
                    reply.writeBoolean(_result177);
                    return true;
                case 387:
                    String _arg0208 = data.readString();
                    boolean _arg1102 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUsbDataSignalingEnabled(_arg0208, _arg1102);
                    reply.writeNoException();
                    return true;
                case 388:
                    String _arg0209 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result178 = isUsbDataSignalingEnabled(_arg0209);
                    reply.writeNoException();
                    reply.writeBoolean(_result178);
                    return true;
                case 389:
                    boolean _result179 = canUsbDataSignalingBeDisabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result179);
                    return true;
                case 390:
                    String _arg0210 = data.readString();
                    int _arg1103 = data.readInt();
                    data.enforceNoDataAvail();
                    setMinimumRequiredWifiSecurityLevel(_arg0210, _arg1103);
                    reply.writeNoException();
                    return true;
                case 391:
                    int _result180 = getMinimumRequiredWifiSecurityLevel();
                    reply.writeNoException();
                    reply.writeInt(_result180);
                    return true;
                case 392:
                    return onTransact$setWifiSsidPolicy$(data, reply);
                case 393:
                    String _arg0211 = data.readString();
                    data.enforceNoDataAvail();
                    WifiSsidPolicy _result181 = getWifiSsidPolicy(_arg0211);
                    reply.writeNoException();
                    reply.writeTypedObject(_result181, 1);
                    return true;
                case 394:
                    String _arg0212 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result182 = isDevicePotentiallyStolen(_arg0212);
                    reply.writeNoException();
                    reply.writeBoolean(_result182);
                    return true;
                case 395:
                    List<UserHandle> _result183 = listForegroundAffiliatedUsers();
                    reply.writeNoException();
                    reply.writeTypedList(_result183, 1);
                    return true;
                case 396:
                    List<DevicePolicyDrawableResource> _arg0213 =
                            data.createTypedArrayList(DevicePolicyDrawableResource.CREATOR);
                    data.enforceNoDataAvail();
                    setDrawables(_arg0213);
                    reply.writeNoException();
                    return true;
                case 397:
                    List<String> _arg0214 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    resetDrawables(_arg0214);
                    reply.writeNoException();
                    return true;
                case 398:
                    return onTransact$getDrawable$(data, reply);
                case 399:
                    boolean _result184 = isDpcDownloaded();
                    reply.writeNoException();
                    reply.writeBoolean(_result184);
                    return true;
                case 400:
                    boolean _arg0215 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDpcDownloaded(_arg0215);
                    reply.writeNoException();
                    return true;
                case 401:
                    List<DevicePolicyStringResource> _arg0216 =
                            data.createTypedArrayList(DevicePolicyStringResource.CREATOR);
                    data.enforceNoDataAvail();
                    setStrings(_arg0216);
                    reply.writeNoException();
                    return true;
                case 402:
                    List<String> _arg0217 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    resetStrings(_arg0217);
                    reply.writeNoException();
                    return true;
                case 403:
                    String _arg0218 = data.readString();
                    data.enforceNoDataAvail();
                    ParcelableResource _result185 = getString(_arg0218);
                    reply.writeNoException();
                    reply.writeTypedObject(_result185, 1);
                    return true;
                case 404:
                    resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState();
                    reply.writeNoException();
                    return true;
                case 405:
                    boolean _result186 =
                            shouldAllowBypassingDevicePolicyManagementRoleQualification();
                    reply.writeNoException();
                    reply.writeBoolean(_result186);
                    return true;
                case 406:
                    UserHandle _arg0219 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    List<UserHandle> _result187 = getPolicyManagedProfiles(_arg0219);
                    reply.writeNoException();
                    reply.writeTypedList(_result187, 1);
                    return true;
                case 407:
                    return onTransact$semSetPasswordQuality$(data, reply);
                case 408:
                    return onTransact$semSetPasswordMinimumLength$(data, reply);
                case 409:
                    return onTransact$semSetPasswordMinimumUpperCase$(data, reply);
                case 410:
                    return onTransact$semSetPasswordMinimumLowerCase$(data, reply);
                case 411:
                    return onTransact$semSetPasswordMinimumNonLetter$(data, reply);
                case 412:
                    return onTransact$semSetPasswordHistoryLength$(data, reply);
                case 413:
                    return onTransact$semSetPasswordExpirationTimeout$(data, reply);
                case 414:
                    int _arg0220 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result188 = semIsActivePasswordSufficient(_arg0220);
                    reply.writeNoException();
                    reply.writeBoolean(_result188);
                    return true;
                case 415:
                    return onTransact$semSetSimplePasswordEnabled$(data, reply);
                case 416:
                    return onTransact$semIsSimplePasswordEnabled$(data, reply);
                case 417:
                    return onTransact$semSetKeyguardDisabledFeatures$(data, reply);
                case 418:
                    return onTransact$semSetAllowStorageCard$(data, reply);
                case 419:
                    return onTransact$semGetAllowStorageCard$(data, reply);
                case 420:
                    return onTransact$semSetAllowWifi$(data, reply);
                case 421:
                    return onTransact$semGetAllowWifi$(data, reply);
                case 422:
                    return onTransact$semSetAllowTextMessaging$(data, reply);
                case 423:
                    return onTransact$semGetAllowTextMessaging$(data, reply);
                case 424:
                    return onTransact$semSetAllowPopImapEmail$(data, reply);
                case 425:
                    return onTransact$semGetAllowPopImapEmail$(data, reply);
                case 426:
                    return onTransact$semSetAllowBrowser$(data, reply);
                case 427:
                    return onTransact$semGetAllowBrowser$(data, reply);
                case 428:
                    return onTransact$semSetAllowInternetSharing$(data, reply);
                case 429:
                    return onTransact$semGetAllowInternetSharing$(data, reply);
                case 430:
                    return onTransact$semSetAllowBluetoothMode$(data, reply);
                case 431:
                    return onTransact$semGetAllowBluetoothMode$(data, reply);
                case 432:
                    return onTransact$semSetAllowDesktopSync$(data, reply);
                case 433:
                    return onTransact$semGetAllowDesktopSync$(data, reply);
                case 434:
                    return onTransact$semSetAllowIrda$(data, reply);
                case 435:
                    return onTransact$semGetAllowIrda$(data, reply);
                case 436:
                    return onTransact$semSetRequireStorageCardEncryption$(data, reply);
                case 437:
                    return onTransact$semGetRequireStorageCardEncryption$(data, reply);
                case 438:
                    return onTransact$semSetChangeNotificationEnabled$(data, reply);
                case 439:
                    return onTransact$setApplicationExemptions$(data, reply);
                case 440:
                    String _arg0221 = data.readString();
                    data.enforceNoDataAvail();
                    int[] _result189 = getApplicationExemptions(_arg0221);
                    reply.writeNoException();
                    reply.writeIntArray(_result189);
                    return true;
                case 441:
                    return onTransact$setMtePolicy$(data, reply);
                case 442:
                    String _arg0222 = data.readString();
                    data.enforceNoDataAvail();
                    int _result190 = getMtePolicy(_arg0222);
                    reply.writeNoException();
                    reply.writeInt(_result190);
                    return true;
                case 443:
                    ManagedSubscriptionsPolicy _arg0223 =
                            (ManagedSubscriptionsPolicy)
                                    data.readTypedObject(ManagedSubscriptionsPolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setManagedSubscriptionsPolicy(_arg0223);
                    reply.writeNoException();
                    return true;
                case 444:
                    ManagedSubscriptionsPolicy _result191 = getManagedSubscriptionsPolicy();
                    reply.writeNoException();
                    reply.writeTypedObject(_result191, 1);
                    return true;
                case 445:
                    DevicePolicyState _result192 = getDevicePolicyState();
                    reply.writeNoException();
                    reply.writeTypedObject(_result192, 1);
                    return true;
                case 446:
                    boolean _arg0224 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result193 = triggerDevicePolicyEngineMigration(_arg0224);
                    reply.writeNoException();
                    reply.writeBoolean(_result193);
                    return true;
                case 447:
                    String _arg0225 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result194 = isDeviceFinanced(_arg0225);
                    reply.writeNoException();
                    reply.writeBoolean(_result194);
                    return true;
                case 448:
                    String _arg0226 = data.readString();
                    data.enforceNoDataAvail();
                    String _result195 = getFinancedDeviceKioskRoleHolder(_arg0226);
                    reply.writeNoException();
                    reply.writeString(_result195);
                    return true;
                case 449:
                    return onTransact$setUserRestrictionForKnox$(data, reply);
                case 450:
                    return onTransact$setCrossProfileAppToIgnored$(data, reply);
                case 451:
                    return onTransact$getSamsungSDcardEncryptionStatus$(data, reply);
                case 452:
                    calculateHasIncompatibleAccounts();
                    reply.writeNoException();
                    return true;
                case 453:
                    return onTransact$setContentProtectionPolicy$(data, reply);
                case 454:
                    return onTransact$getContentProtectionPolicy$(data, reply);
                case 455:
                    String _arg0227 = data.readString();
                    data.enforceNoDataAvail();
                    int[] _result196 = getSubscriptionIds(_arg0227);
                    reply.writeNoException();
                    reply.writeIntArray(_result196);
                    return true;
                case 456:
                    return onTransact$setMaxPolicyStorageLimit$(data, reply);
                case 457:
                    return onTransact$forceSetMaxPolicyStorageLimit$(data, reply);
                case 458:
                    String _arg0228 = data.readString();
                    data.enforceNoDataAvail();
                    int _result197 = getMaxPolicyStorageLimit(_arg0228);
                    reply.writeNoException();
                    reply.writeInt(_result197);
                    return true;
                case 459:
                    return onTransact$getPolicySizeForAdmin$(data, reply);
                case 460:
                    String _arg0229 = data.readString();
                    data.enforceNoDataAvail();
                    int _result198 = getHeadlessDeviceOwnerMode(_arg0229);
                    reply.writeNoException();
                    reply.writeInt(_result198);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDevicePolicyManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordQuality(ComponentName who, int quality, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(quality);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordQuality(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLength(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLength(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumUpperCase(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumUpperCase(
                    ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLowerCase(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLowerCase(
                    ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLetters(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLetters(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNumeric(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNumeric(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumSymbols(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumSymbols(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNonLetter(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNonLetter(
                    ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PasswordMetrics getPasswordMinimumMetrics(int userHandle, boolean deviceWideOnly)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(deviceWideOnly);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    PasswordMetrics _result =
                            (PasswordMetrics) _reply.readTypedObject(PasswordMetrics.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordHistoryLength(ComponentName who, int length, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(length);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordHistoryLength(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordExpirationTimeout(
                    ComponentName who, String callerPackageName, long expiration, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeLong(expiration);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpirationTimeout(
                    ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpiration(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficient(
                    String callerPackageName, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficientForDeviceRequirement() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPasswordSufficientAfterProfileUnification(
                    int userHandle, int profileUser) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(profileUser);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordComplexity(boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredPasswordComplexity(
                    String callerPackageName, int passwordComplexity, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeInt(passwordComplexity);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getRequiredPasswordComplexity(String callerPackageName, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAggregatedPasswordComplexityForUser(int userId, boolean deviceWideOnly)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeBoolean(deviceWideOnly);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsingUnifiedPassword(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getCurrentFailedPasswordAttempts(
                    String callerPackageName, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getCurrentFailedBiometricAttempts(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getProfileWithMinimumFailedPasswordsForWipe(int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumFailedPasswordsForWipe(
                    ComponentName admin, String callerPackageName, int num, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeInt(num);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaximumFailedPasswordsForWipe(
                    ComponentName admin, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPassword(String password, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(flags);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumTimeToLock(
                    ComponentName who, String callerPackageName, long timeMs, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeLong(timeMs);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getMaximumTimeToLock(ComponentName who, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredStrongAuthTimeout(
                    ComponentName who, String callerPackageName, long timeMs, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeLong(timeMs);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getRequiredStrongAuthTimeout(ComponentName who, int userId, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userId);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void lockNow(int flags, String callerPackageName, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void wipeDataWithReason(
                    String callerPackageName,
                    int flags,
                    String wipeReasonForUser,
                    boolean parent,
                    boolean factoryReset)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeInt(flags);
                    _data.writeString(wipeReasonForUser);
                    _data.writeBoolean(parent);
                    _data.writeBoolean(factoryReset);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setFactoryResetProtectionPolicy(
                    ComponentName who,
                    String callerPackageName,
                    FactoryResetProtectionPolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName who)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    FactoryResetProtectionPolicy _result =
                            (FactoryResetProtectionPolicy)
                                    _reply.readTypedObject(FactoryResetProtectionPolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isFactoryResetProtectionPolicySupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void sendLostModeLocationUpdate(AndroidFuture<Boolean> future)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(future, 0);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName setGlobalProxy(
                    ComponentName admin, String proxySpec, String exclusionList)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(proxySpec);
                    _data.writeString(exclusionList);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getGlobalProxyAdmin(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRecommendedGlobalProxy(ComponentName admin, ProxyInfo proxyInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setStorageEncryption(ComponentName who, boolean encrypt)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(encrypt);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getStorageEncryption(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getStorageEncryptionStatus(String callerPackage, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean requestBugreport(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCameraDisabled(
                    ComponentName who, String callerPackageName, boolean disabled, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(disabled);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCameraDisabled(
                    ComponentName who, String callerPackageName, int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setScreenCaptureDisabled(
                    ComponentName who, String callerPackageName, boolean disabled, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(disabled);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getScreenCaptureDisabled(
                    ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyNotificationStreamingPolicy(int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(policy);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyNotificationStreamingPolicy(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyAppStreamingPolicy(int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(policy);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyAppStreamingPolicy(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeyguardDisabledFeatures(
                    ComponentName who, String callerPackageName, int which, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeInt(which);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getKeyguardDisabledFeatures(
                    ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setActiveAdmin(
                    ComponentName policyReceiver, boolean refreshing, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyReceiver, 0);
                    _data.writeBoolean(refreshing);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAdminActive(ComponentName policyReceiver, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyReceiver, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<ComponentName> getActiveAdmins(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    List<ComponentName> _result =
                            _reply.createTypedArrayList(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean packageHasActiveAdmins(String packageName, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void getRemoveWarning(
                    ComponentName policyReceiver, RemoteCallback result, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyReceiver, 0);
                    _data.writeTypedObject(result, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void removeActiveAdmin(ComponentName policyReceiver, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyReceiver, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceRemoveActiveAdmin(ComponentName policyReceiver, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyReceiver, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasGrantedPolicy(
                    ComponentName policyReceiver, int usesPolicy, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyReceiver, 0);
                    _data.writeInt(usesPolicy);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportPasswordChanged(PasswordMetrics metrics, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(metrics, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedPasswordAttempt(int userHandle, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulPasswordAttempt(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedBiometricAttempt(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulBiometricAttempt(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardDismissed(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardSecured(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setDeviceOwner(
                    ComponentName who, int userId, boolean setProfileOwnerOnCurrentUserIfNecessary)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userId);
                    _data.writeBoolean(setProfileOwnerOnCurrentUserIfNecessary);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getDeviceOwnerComponent(boolean callingUserOnly)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(callingUserOnly);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getDeviceOwnerComponentOnUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasDeviceOwner() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getDeviceOwnerName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearDeviceOwner(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerUserId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setProfileOwner(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getProfileOwnerAsUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(
                    UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSupervisionComponent(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getProfileOwnerName(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileEnabled(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileName(ComponentName who, String profileName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(profileName);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearProfileOwner(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasUserSetupCompleted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOrganizationOwnedDeviceWithManagedProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean checkDeviceIdentifierAccess(String packageName, int pid, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerLockScreenInfo(
                    ComponentName who, CharSequence deviceOwnerInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    if (deviceOwnerInfo != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(deviceOwnerInfo, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] setPackagesSuspended(
                    ComponentName admin,
                    String callerPackage,
                    String[] packageNames,
                    boolean suspended)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeStringArray(packageNames);
                    _data.writeBoolean(suspended);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageSuspended(
                    ComponentName admin, String callerPackage, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> listPolicyExemptApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installCaCert(
                    ComponentName admin, String callerPackage, byte[] certBuffer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeByteArray(certBuffer);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallCaCerts(
                    ComponentName admin, String callerPackage, String[] aliases)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeStringArray(aliases);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enforceCanManageCaCerts(ComponentName admin, String callerPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean approveCaCert(String alias, int userHandle, boolean approval)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(approval);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCaCertApproved(String alias, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installKeyPair(
                    ComponentName who,
                    String callerPackage,
                    byte[] privKeyBuffer,
                    byte[] certBuffer,
                    byte[] certChainBuffer,
                    String alias,
                    boolean requestAccess,
                    boolean isUserSelectable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeByteArray(privKeyBuffer);
                    _data.writeByteArray(certBuffer);
                    _data.writeByteArray(certChainBuffer);
                    _data.writeString(alias);
                    _data.writeBoolean(requestAccess);
                    _data.writeBoolean(isUserSelectable);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeKeyPair(ComponentName who, String callerPackage, String alias)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasKeyPair(String callerPackage, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean generateKeyPair(
                    ComponentName who,
                    String callerPackage,
                    String algorithm,
                    ParcelableKeyGenParameterSpec keySpec,
                    int idAttestationFlags,
                    KeymasterCertificateChain attestationChain)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(algorithm);
                    _data.writeTypedObject(keySpec, 0);
                    _data.writeInt(idAttestationFlags);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    if (_reply.readInt() != 0) {
                        attestationChain.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyPairCertificate(
                    ComponentName who,
                    String callerPackage,
                    String alias,
                    byte[] certBuffer,
                    byte[] certChainBuffer,
                    boolean isUserSelectable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    _data.writeByteArray(certBuffer);
                    _data.writeByteArray(certChainBuffer);
                    _data.writeBoolean(isUserSelectable);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void choosePrivateKeyAlias(int uid, Uri uri, String alias, IBinder aliasCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeTypedObject(uri, 0);
                    _data.writeString(alias);
                    _data.writeStrongBinder(aliasCallback);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDelegatedScopes(
                    ComponentName who, String delegatePackage, List<String> scopes)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(delegatePackage);
                    _data.writeStringList(scopes);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDelegatedScopes(ComponentName who, String delegatePackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(delegatePackage);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDelegatePackages(ComponentName who, String scope)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(scope);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCertInstallerPackage(ComponentName who, String installerPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(installerPackage);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getCertInstallerPackage(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setAlwaysOnVpnPackage(
                    ComponentName who,
                    String vpnPackage,
                    boolean lockdown,
                    List<String> lockdownAllowlist)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(vpnPackage);
                    _data.writeBoolean(lockdown);
                    _data.writeStringList(lockdownAllowlist);
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getAlwaysOnVpnPackage(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getAlwaysOnVpnPackageForUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabled(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabledForUser(int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(122, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAlwaysOnVpnLockdownAllowlist(ComponentName who)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(123, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addPersistentPreferredActivity(
                    ComponentName admin,
                    String callerPackageName,
                    IntentFilter filter,
                    ComponentName activity)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(filter, 0);
                    _data.writeTypedObject(activity, 0);
                    this.mRemote.transact(124, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearPackagePersistentPreferredActivities(
                    ComponentName admin, String callerPackageName, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeString(packageName);
                    this.mRemote.transact(125, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultSmsApplication(
                    ComponentName admin,
                    String callerPackageName,
                    String packageName,
                    boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeString(packageName);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultDialerApplication(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationRestrictions(
                    ComponentName who,
                    String callerPackage,
                    String packageName,
                    Bundle settings,
                    boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeTypedObject(settings, 0);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(128, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getApplicationRestrictions(
                    ComponentName who, String callerPackage, String packageName, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(129, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationRestrictionsManagingPackage(
                    ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getApplicationRestrictionsManagingPackage(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(131, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallerApplicationRestrictionsManagingPackage(String callerPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(132, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRestrictionsProvider(ComponentName who, ComponentName provider)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeTypedObject(provider, 0);
                    this.mRemote.transact(133, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ComponentName getRestrictionsProvider(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(134, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestriction(
                    ComponentName who,
                    String callerPackage,
                    String key,
                    boolean enable,
                    boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(key);
                    _data.writeBoolean(enable);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(135, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionGlobally(String callerPackage, String key)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeString(key);
                    this.mRemote.transact(136, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getUserRestrictions(
                    ComponentName who, String callerPackage, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackage);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(137, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getUserRestrictionsGlobally(String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(138, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addCrossProfileIntentFilter(
                    ComponentName admin, String callerPackageName, IntentFilter filter, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(filter, 0);
                    _data.writeInt(flags);
                    this.mRemote.transact(139, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearCrossProfileIntentFilters(
                    ComponentName admin, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(140, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedAccessibilityServices(
                    ComponentName admin, List<String> packageList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStringList(packageList);
                    this.mRemote.transact(141, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedAccessibilityServices(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(142, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedAccessibilityServicesForUser(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(143, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAccessibilityServicePermittedByAdmin(
                    ComponentName admin, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(144, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedInputMethods(
                    ComponentName admin,
                    String callerPackageName,
                    List<String> packageList,
                    boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeStringList(packageList);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(145, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedInputMethods(
                    ComponentName admin, String callerPackageName, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(146, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedInputMethodsAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(147, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isInputMethodPermittedByAdmin(
                    ComponentName admin, String packageName, int userId, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(148, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedCrossProfileNotificationListeners(
                    ComponentName admin, List<String> packageList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStringList(packageList);
                    this.mRemote.transact(149, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getPermittedCrossProfileNotificationListeners(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(150, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNotificationListenerServicePermitted(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(151, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Intent createAdminSupportIntent(String restriction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(restriction);
                    this.mRemote.transact(152, _data, _reply, 0);
                    _reply.readException();
                    Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getEnforcingAdminAndUserDetails(int userId, String restriction)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(restriction);
                    this.mRemote.transact(153, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<EnforcingAdmin> getEnforcingAdminsForRestriction(
                    int userId, String restriction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(restriction);
                    this.mRemote.transact(154, _data, _reply, 0);
                    _reply.readException();
                    List<EnforcingAdmin> _result =
                            _reply.createTypedArrayList(EnforcingAdmin.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationHidden(
                    ComponentName admin,
                    String callerPackage,
                    String packageName,
                    boolean hidden,
                    boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeBoolean(hidden);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(155, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isApplicationHidden(
                    ComponentName admin, String callerPackage, String packageName, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(156, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createAndManageUser(
                    ComponentName who,
                    String name,
                    ComponentName profileOwner,
                    PersistableBundle adminExtras,
                    int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(name);
                    _data.writeTypedObject(profileOwner, 0);
                    _data.writeTypedObject(adminExtras, 0);
                    _data.writeInt(flags);
                    this.mRemote.transact(157, _data, _reply, 0);
                    _reply.readException();
                    UserHandle _result = (UserHandle) _reply.readTypedObject(UserHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeUser(ComponentName who, UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(158, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean switchUser(ComponentName who, UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(159, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int startUserInBackground(ComponentName who, UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(160, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int stopUser(ComponentName who, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(161, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUser(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(162, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUserInternal() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(163, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLogoutUserId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(164, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getSecondaryUsers(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(165, _data, _reply, 0);
                    _reply.readException();
                    List<UserHandle> _result = _reply.createTypedArrayList(UserHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeNewUserDisclaimer(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(166, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNewUserDisclaimerAcknowledged(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(167, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enableSystemApp(
                    ComponentName admin, String callerPackage, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    this.mRemote.transact(168, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int enableSystemAppWithIntent(
                    ComponentName admin, String callerPackage, Intent intent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(169, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installExistingPackage(
                    ComponentName admin, String callerPackage, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    this.mRemote.transact(170, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAccountManagementDisabled(
                    ComponentName who,
                    String callerPackageName,
                    String accountType,
                    boolean disabled,
                    boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeString(accountType);
                    _data.writeBoolean(disabled);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(171, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getAccountTypesWithManagementDisabled(String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(172, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getAccountTypesWithManagementDisabledAsUser(
                    int userId, String callerPackageName, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(173, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecondaryLockscreenEnabled(ComponentName who, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(174, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecondaryLockscreenEnabled(UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(175, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPreferentialNetworkServiceConfigs(
                    List<PreferentialNetworkServiceConfig> preferentialNetworkServiceConfigs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(preferentialNetworkServiceConfigs, 0);
                    this.mRemote.transact(176, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(177, _data, _reply, 0);
                    _reply.readException();
                    List<PreferentialNetworkServiceConfig> _result =
                            _reply.createTypedArrayList(PreferentialNetworkServiceConfig.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskPackages(
                    ComponentName who, String callerPackageName, String[] packages)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeStringArray(packages);
                    this.mRemote.transact(178, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String[] getLockTaskPackages(ComponentName who, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(179, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLockTaskPermitted(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    this.mRemote.transact(180, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskFeatures(ComponentName who, String callerPackageName, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeInt(flags);
                    this.mRemote.transact(181, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLockTaskFeatures(ComponentName who, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(182, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setGlobalSetting(ComponentName who, String setting, String value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(setting);
                    _data.writeString(value);
                    this.mRemote.transact(183, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemSetting(
                    ComponentName who, String setting, String value, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(setting);
                    _data.writeString(value);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(184, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecureSetting(ComponentName who, String setting, String value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(setting);
                    _data.writeString(value);
                    this.mRemote.transact(185, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setConfiguredNetworksLockdownState(
                    ComponentName who, String callerPackageName, boolean lockdown)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(lockdown);
                    this.mRemote.transact(186, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasLockdownAdminConfiguredNetworks(ComponentName who)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(187, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLocationEnabled(ComponentName who, boolean locationEnabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(locationEnabled);
                    this.mRemote.transact(188, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTime(ComponentName who, String callerPackageName, long millis)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeLong(millis);
                    this.mRemote.transact(189, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTimeZone(ComponentName who, String callerPackageName, String timeZone)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeString(timeZone);
                    this.mRemote.transact(190, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMasterVolumeMuted(ComponentName admin, boolean on)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeBoolean(on);
                    this.mRemote.transact(191, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMasterVolumeMuted(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(192, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyLockTaskModeChanged(boolean isEnabled, String pkg, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isEnabled);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    this.mRemote.transact(193, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUninstallBlocked(
                    ComponentName admin,
                    String callerPackage,
                    String packageName,
                    boolean uninstallBlocked)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeBoolean(uninstallBlocked);
                    this.mRemote.transact(194, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallBlocked(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(195, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCallerIdDisabled(ComponentName who, boolean disabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(196, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabled(ComponentName who)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(197, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabledForUser(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(198, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileContactsSearchDisabled(ComponentName who, boolean disabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(199, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabled(ComponentName who)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(200, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabledForUser(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(201, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void startManagedQuickContact(
                    String lookupKey,
                    long contactId,
                    boolean isContactIdIgnored,
                    long directoryId,
                    Intent originalIntent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(lookupKey);
                    _data.writeLong(contactId);
                    _data.writeBoolean(isContactIdIgnored);
                    _data.writeLong(directoryId);
                    _data.writeTypedObject(originalIntent, 0);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileCallerIdAccessPolicy(PackagePolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(203, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getManagedProfileCallerIdAccessPolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(204, _data, _reply, 0);
                    _reply.readException();
                    PackagePolicy _result =
                            (PackagePolicy) _reply.readTypedObject(PackagePolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileCallerIdAccess(int userId, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(205, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCredentialManagerPolicy(PackagePolicy policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(206, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getCredentialManagerPolicy(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(207, _data, _reply, 0);
                    _reply.readException();
                    PackagePolicy _result =
                            (PackagePolicy) _reply.readTypedObject(PackagePolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileContactsAccessPolicy(PackagePolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(208, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PackagePolicy getManagedProfileContactsAccessPolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(209, _data, _reply, 0);
                    _reply.readException();
                    PackagePolicy _result =
                            (PackagePolicy) _reply.readTypedObject(PackagePolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileContactsAccess(int userId, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(210, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBluetoothContactSharingDisabled(ComponentName who, boolean disabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(211, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabled(ComponentName who)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(212, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabledForUser(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(213, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setTrustAgentConfiguration(
                    ComponentName admin,
                    String callerPackageName,
                    ComponentName agent,
                    PersistableBundle args,
                    boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(agent, 0);
                    _data.writeTypedObject(args, 0);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(214, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<PersistableBundle> getTrustAgentConfiguration(
                    ComponentName admin, ComponentName agent, int userId, boolean parent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeTypedObject(agent, 0);
                    _data.writeInt(userId);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(215, _data, _reply, 0);
                    _reply.readException();
                    List<PersistableBundle> _result =
                            _reply.createTypedArrayList(PersistableBundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean addCrossProfileWidgetProvider(
                    ComponentName admin, String callerPackageName, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeString(packageName);
                    this.mRemote.transact(216, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeCrossProfileWidgetProvider(
                    ComponentName admin, String callerPackageName, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeString(packageName);
                    this.mRemote.transact(217, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileWidgetProviders(
                    ComponentName admin, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(218, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeRequired(ComponentName who, boolean required)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(required);
                    this.mRemote.transact(219, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeRequired() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(220, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeEnabled(
                    ComponentName who, String callerPackageName, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(221, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeEnabled(ComponentName who, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(222, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeZoneEnabled(
                    ComponentName who, String callerPackageName, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(223, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeZoneEnabled(ComponentName who, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(224, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setForceEphemeralUsers(ComponentName who, boolean forceEpehemeralUsers)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(forceEpehemeralUsers);
                    this.mRemote.transact(225, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getForceEphemeralUsers(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    this.mRemote.transact(226, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isRemovingAdmin(ComponentName adminReceiver, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(adminReceiver, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(227, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserIcon(ComponentName admin, Bitmap icon) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeTypedObject(icon, 0);
                    this.mRemote.transact(228, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemUpdatePolicy(
                    ComponentName who, String callerPackageName, SystemUpdatePolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(229, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(230, _data, _reply, 0);
                    _reply.readException();
                    SystemUpdatePolicy _result =
                            (SystemUpdatePolicy) _reply.readTypedObject(SystemUpdatePolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(231, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyguardDisabled(ComponentName admin, boolean disabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(232, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setStatusBarDisabled(
                    ComponentName who, String callerPackageName, boolean disabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(233, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isStatusBarDisabled(String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(234, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(235, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyPendingSystemUpdate(SystemUpdateInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(236, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public SystemUpdateInfo getPendingSystemUpdate(
                    ComponentName admin, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(237, _data, _reply, 0);
                    _reply.readException();
                    SystemUpdateInfo _result =
                            (SystemUpdateInfo) _reply.readTypedObject(SystemUpdateInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionPolicy(ComponentName admin, String callerPackage, int policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeInt(policy);
                    this.mRemote.transact(238, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionPolicy(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(239, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionGrantState(
                    ComponentName admin,
                    String callerPackage,
                    String packageName,
                    String permission,
                    int grantState,
                    RemoteCallback resultReceiver)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    _data.writeInt(grantState);
                    _data.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(240, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionGrantState(
                    ComponentName admin,
                    String callerPackage,
                    String packageName,
                    String permission)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    this.mRemote.transact(241, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isProvisioningAllowed(String action, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    _data.writeString(packageName);
                    this.mRemote.transact(242, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int checkProvisioningPrecondition(String action, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    _data.writeString(packageName);
                    this.mRemote.transact(243, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeepUninstalledPackages(
                    ComponentName admin, String callerPackage, List<String> packageList)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeStringList(packageList);
                    this.mRemote.transact(244, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getKeepUninstalledPackages(
                    ComponentName admin, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(245, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedProfile(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(246, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getWifiMacAddress(ComponentName admin, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(247, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reboot(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(248, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setShortSupportMessage(
                    ComponentName admin, String callerPackageName, CharSequence message)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(249, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getShortSupportMessage(
                    ComponentName admin, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(250, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLongSupportMessage(ComponentName admin, CharSequence message)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(251, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getLongSupportMessage(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(252, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getShortSupportMessageForUser(ComponentName admin, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(253, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getLongSupportMessageForUser(ComponentName admin, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(254, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColor(ComponentName admin, int color)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(color);
                    this.mRemote.transact(255, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColorForUser(int color, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(color);
                    _data.writeInt(userId);
                    this.mRemote.transact(256, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearOrganizationIdForUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(257, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColor(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(258, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColorForUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(259, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationName(
                    ComponentName admin, String callerPackageName, CharSequence title)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    if (title != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(title, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(260, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getOrganizationName(ComponentName admin, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(261, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(262, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getOrganizationNameForUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(263, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getUserProvisioningState(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(264, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserProvisioningState(int state, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(265, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAffiliationIds(ComponentName admin, List<String> ids)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStringList(ids);
                    this.mRemote.transact(266, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAffiliationIds(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(267, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallingUserAffiliated() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(268, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAffiliatedUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(269, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecurityLoggingEnabled(
                    ComponentName admin, String packageName, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(270, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecurityLoggingEnabled(ComponentName admin, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(271, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParceledListSlice retrieveSecurityLogs(ComponentName admin, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(272, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParceledListSlice retrievePreRebootSecurityLogs(
                    ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(273, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceNetworkLogs() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(274, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceSecurityLogs() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(275, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEnabled(String callerPackage, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(276, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAuditLogEnabled(String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(277, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEventsCallback(
                    String callerPackage, IAuditLogEventsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(278, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallInQueue(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(279, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallPackageWithActiveAdmins(String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(280, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioned() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(281, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(282, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(283, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceUpdateUserSetupComplete(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(284, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBackupServiceEnabled(ComponentName admin, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(285, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isBackupServiceEnabled(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(286, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNetworkLoggingEnabled(
                    ComponentName admin, String packageName, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(287, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNetworkLoggingEnabled(ComponentName admin, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(288, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<NetworkEvent> retrieveNetworkLogs(
                    ComponentName admin, String packageName, long batchToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeLong(batchToken);
                    this.mRemote.transact(289, _data, _reply, 0);
                    _reply.readException();
                    List<NetworkEvent> _result = _reply.createTypedArrayList(NetworkEvent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean bindDeviceAdminServiceAsUser(
                    ComponentName admin,
                    IApplicationThread caller,
                    IBinder token,
                    Intent service,
                    IServiceConnection connection,
                    long flags,
                    int targetUserId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStrongInterface(caller);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(service, 0);
                    _data.writeStrongInterface(connection);
                    _data.writeLong(flags);
                    _data.writeInt(targetUserId);
                    this.mRemote.transact(290, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(291, _data, _reply, 0);
                    _reply.readException();
                    List<UserHandle> _result = _reply.createTypedArrayList(UserHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isEphemeralUser(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(292, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastSecurityLogRetrievalTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(293, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastBugReportRequestTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(294, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastNetworkLogRetrievalTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(295, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setResetPasswordToken(
                    ComponentName admin, String callerPackageName, byte[] token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeByteArray(token);
                    this.mRemote.transact(296, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean clearResetPasswordToken(ComponentName admin, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(297, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isResetPasswordTokenActive(ComponentName admin, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(298, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPasswordWithToken(
                    ComponentName admin,
                    String callerPackageName,
                    String password,
                    byte[] token,
                    int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeString(password);
                    _data.writeByteArray(token);
                    _data.writeInt(flags);
                    this.mRemote.transact(299, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(300, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle user)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(301, _data, _reply, 0);
                    _reply.readException();
                    StringParceledListSlice _result =
                            (StringParceledListSlice)
                                    _reply.readTypedObject(StringParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearApplicationUserData(
                    ComponentName admin, String packageName, IPackageDataObserver callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(302, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLogoutEnabled(ComponentName admin, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(303, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLogoutEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(304, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDisallowedSystemApps(
                    ComponentName admin, int userId, String provisioningAction)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(userId);
                    _data.writeString(provisioningAction);
                    this.mRemote.transact(305, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void transferOwnership(
                    ComponentName admin, ComponentName target, PersistableBundle bundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeTypedObject(target, 0);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(306, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(307, _data, _reply, 0);
                    _reply.readException();
                    PersistableBundle _result =
                            (PersistableBundle) _reply.readTypedObject(PersistableBundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStartUserSessionMessage(
                    ComponentName admin, CharSequence startUserSessionMessage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    if (startUserSessionMessage != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(startUserSessionMessage, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(308, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setEndUserSessionMessage(
                    ComponentName admin, CharSequence endUserSessionMessage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    if (endUserSessionMessage != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(endUserSessionMessage, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(309, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getStartUserSessionMessage(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(310, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public CharSequence getEndUserSessionMessage(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(311, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> setMeteredDataDisabledPackages(
                    ComponentName admin, List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStringList(packageNames);
                    this.mRemote.transact(312, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getMeteredDataDisabledPackages(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(313, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int addOverrideApn(ComponentName admin, ApnSetting apnSetting)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(314, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean updateOverrideApn(ComponentName admin, int apnId, ApnSetting apnSetting)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(apnId);
                    _data.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(315, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeOverrideApn(ComponentName admin, int apnId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(apnId);
                    this.mRemote.transact(316, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<ApnSetting> getOverrideApns(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(317, _data, _reply, 0);
                    _reply.readException();
                    List<ApnSetting> _result = _reply.createTypedArrayList(ApnSetting.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOverrideApnsEnabled(ComponentName admin, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(318, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOverrideApnEnabled(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(319, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMeteredDataDisabledPackageForUser(
                    ComponentName admin, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(320, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordQualityMDM(ComponentName admin, int quality, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(quality);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(321, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLengthMDM(ComponentName admin, int length, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(322, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumUpperCaseMDM(
                    ComponentName admin, int length, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(323, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLowerCaseMDM(
                    ComponentName admin, int length, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(324, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLettersMDM(
                    ComponentName admin, int length, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(325, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNumericMDM(
                    ComponentName admin, int length, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(326, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumSymbolsMDM(
                    ComponentName admin, int length, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(327, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNonLetterMDM(
                    ComponentName admin, int length, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(328, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordHistoryLengthMDM(ComponentName admin, int length, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(329, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordExpirationTimeoutMDM(
                    ComponentName admin, long expiration, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeLong(expiration);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(330, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumFailedPasswordsForWipeMDM(
                    ComponentName admin, int num, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(num);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(331, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumTimeToLockMDM(ComponentName admin, long timeMs, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeLong(timeMs);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(332, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeyguardDisabledFeaturesMDM(
                    ComponentName admin, int which, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(which);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(333, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationRestrictionsMDM(
                    ComponentName admin, String packageName, Bundle settings, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(packageName);
                    _data.writeTypedObject(settings, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(334, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Bundle getApplicationRestrictionsMDM(
                    ComponentName who, String packageName, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(335, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPasswordWithTokenMDM(
                    ComponentName admin, String password, byte[] token, int flags, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(password);
                    _data.writeByteArray(token);
                    _data.writeInt(flags);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(336, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isResetPasswordTokenActiveMDM(ComponentName admin, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(337, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean clearResetPasswordTokenMDM(ComponentName admin, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(338, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setResetPasswordTokenMDM(
                    ComponentName admin, byte[] token, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeByteArray(token);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(339, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setTrustAgentConfigurationMDM(
                    int userId, ComponentName admin, ComponentName agent, PersistableBundle args)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeTypedObject(admin, 0);
                    _data.writeTypedObject(agent, 0);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(340, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isProfileOwnerOfOrganizationOwnedDeviceMDM(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(341, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasDelegatedPermission(String callerPackage, int callerUid, String scope)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeInt(callerUid);
                    _data.writeString(scope);
                    this.mRemote.transact(342, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public Map getDelegatedPackages(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(343, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedPasswordAttemptWithFailureCount(
                    int userHandle, int count, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(count);
                    _data.writeBoolean(parent);
                    this.mRemote.transact(344, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setGlobalPrivateDns(ComponentName admin, int mode, String privateDnsHost)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(mode);
                    _data.writeString(privateDnsHost);
                    this.mRemote.transact(345, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getGlobalPrivateDnsMode(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(346, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getGlobalPrivateDnsHost(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(347, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileOwnerOnOrganizationOwnedDevice(
                    ComponentName who, int userId, boolean isProfileOwnerOnOrganizationOwnedDevice)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userId);
                    _data.writeBoolean(isProfileOwnerOnOrganizationOwnedDevice);
                    this.mRemote.transact(348, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void installUpdateFromFile(
                    ComponentName admin,
                    String callerPackageName,
                    ParcelFileDescriptor updateFileDescriptor,
                    StartInstallingUpdateCallback listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(updateFileDescriptor, 0);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(349, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCalendarPackages(
                    ComponentName admin, List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStringList(packageNames);
                    this.mRemote.transact(350, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileCalendarPackages(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(351, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageAllowedToAccessCalendarForUser(
                    String packageName, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(352, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfileCalendarPackagesForUser(int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(353, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfilePackages(ComponentName admin, List<String> packageNames)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStringList(packageNames);
                    this.mRemote.transact(354, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getCrossProfilePackages(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(355, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getAllCrossProfilePackages(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(356, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getDefaultCrossProfilePackages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(357, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedKiosk() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(358, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUnattendedManagedKiosk() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(359, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean startViewCalendarEventInManagedProfile(
                    String packageName,
                    long eventId,
                    long start,
                    long end,
                    boolean allDay,
                    int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(eventId);
                    _data.writeLong(start);
                    _data.writeLong(end);
                    _data.writeBoolean(allDay);
                    _data.writeInt(flags);
                    this.mRemote.transact(360, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantForApp(
                    ComponentName admin,
                    String callerPackage,
                    String alias,
                    String packageName,
                    boolean hasGrant)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    _data.writeString(packageName);
                    _data.writeBoolean(hasGrant);
                    this.mRemote.transact(361, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableGranteeMap getKeyPairGrants(String callerPackage, String alias)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    this.mRemote.transact(362, _data, _reply, 0);
                    _reply.readException();
                    ParcelableGranteeMap _result =
                            (ParcelableGranteeMap)
                                    _reply.readTypedObject(ParcelableGranteeMap.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantToWifiAuth(
                    String callerPackage, String alias, boolean hasGrant) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    _data.writeBoolean(hasGrant);
                    this.mRemote.transact(363, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isKeyPairGrantedToWifiAuth(String callerPackage, String alias)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    this.mRemote.transact(364, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserControlDisabledPackages(
                    ComponentName admin, String callerPackageName, List<String> packages)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeStringList(packages);
                    this.mRemote.transact(365, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<String> getUserControlDisabledPackages(
                    ComponentName admin, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(366, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCommonCriteriaModeEnabled(
                    ComponentName admin, String callerPackageName, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(367, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCommonCriteriaModeEnabled(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(368, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPersonalAppsSuspendedReasons(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(369, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPersonalAppsSuspended(ComponentName admin, boolean suspended)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeBoolean(suspended);
                    this.mRemote.transact(370, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getManagedProfileMaximumTimeOff(ComponentName admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(371, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileMaximumTimeOff(ComponentName admin, long timeoutMs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeLong(timeoutMs);
                    this.mRemote.transact(372, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeDeviceCompliant() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(373, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isComplianceAcknowledgementRequired() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(374, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canProfileOwnerResetPasswordWhenLocked(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(375, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNextOperationSafety(int operation, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(operation);
                    _data.writeInt(reason);
                    this.mRemote.transact(376, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSafeOperation(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(377, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getEnrollmentSpecificId(String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(378, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationIdForUser(
                    String callerPackage, String enterpriseId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeString(enterpriseId);
                    _data.writeInt(userId);
                    this.mRemote.transact(379, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public UserHandle createAndProvisionManagedProfile(
                    ManagedProfileProvisioningParams provisioningParams, String callerPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(provisioningParams, 0);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(380, _data, _reply, 0);
                    _reply.readException();
                    UserHandle _result = (UserHandle) _reply.readTypedObject(UserHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void provisionFullyManagedDevice(
                    FullyManagedDeviceProvisioningParams provisioningParams, String callerPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(provisioningParams, 0);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(381, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void finalizeWorkProfileProvisioning(
                    UserHandle managedProfileUser, Account migratedAccount) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(managedProfileUser, 0);
                    _data.writeTypedObject(migratedAccount, 0);
                    this.mRemote.transact(382, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerType(ComponentName admin, int deviceOwnerType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(deviceOwnerType);
                    this.mRemote.transact(383, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerType(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(384, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDefaultCrossProfileIntentFilters(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(385, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canAdminGrantSensorsPermissions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(386, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUsbDataSignalingEnabled(String callerPackage, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(387, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsbDataSignalingEnabled(String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(388, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canUsbDataSignalingBeDisabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(389, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMinimumRequiredWifiSecurityLevel(String callerPackageName, int level)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeInt(level);
                    this.mRemote.transact(390, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMinimumRequiredWifiSecurityLevel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(391, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setWifiSsidPolicy(String callerPackageName, WifiSsidPolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(392, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public WifiSsidPolicy getWifiSsidPolicy(String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(393, _data, _reply, 0);
                    _reply.readException();
                    WifiSsidPolicy _result =
                            (WifiSsidPolicy) _reply.readTypedObject(WifiSsidPolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDevicePotentiallyStolen(String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(394, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> listForegroundAffiliatedUsers() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(395, _data, _reply, 0);
                    _reply.readException();
                    List<UserHandle> _result = _reply.createTypedArrayList(UserHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDrawables(List<DevicePolicyDrawableResource> drawables)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(drawables, 0);
                    this.mRemote.transact(396, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDrawables(List<String> drawableIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(drawableIds);
                    this.mRemote.transact(397, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableResource getDrawable(
                    String drawableId, String drawableStyle, String drawableSource)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(drawableId);
                    _data.writeString(drawableStyle);
                    _data.writeString(drawableSource);
                    this.mRemote.transact(398, _data, _reply, 0);
                    _reply.readException();
                    ParcelableResource _result =
                            (ParcelableResource) _reply.readTypedObject(ParcelableResource.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDpcDownloaded() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(399, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDpcDownloaded(boolean downloaded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(downloaded);
                    this.mRemote.transact(400, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStrings(List<DevicePolicyStringResource> strings)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(strings, 0);
                    this.mRemote.transact(401, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetStrings(List<String> stringIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(stringIds);
                    this.mRemote.transact(402, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ParcelableResource getString(String stringId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(stringId);
                    this.mRemote.transact(403, _data, _reply, 0);
                    _reply.readException();
                    ParcelableResource _result =
                            (ParcelableResource) _reply.readTypedObject(ParcelableResource.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(404, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(405, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(406, _data, _reply, 0);
                    _reply.readException();
                    List<UserHandle> _result = _reply.createTypedArrayList(UserHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordQuality(ComponentName admin, int quality)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(quality);
                    this.mRemote.transact(407, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumLength(ComponentName admin, int length)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    this.mRemote.transact(408, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumUpperCase(ComponentName admin, int length)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    this.mRemote.transact(409, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumLowerCase(ComponentName admin, int length)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    this.mRemote.transact(410, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordMinimumNonLetter(ComponentName admin, int length)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    this.mRemote.transact(411, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordHistoryLength(ComponentName admin, int length)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeInt(length);
                    this.mRemote.transact(412, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetPasswordExpirationTimeout(ComponentName admin, long timeout)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeLong(timeout);
                    this.mRemote.transact(413, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semIsActivePasswordSufficient(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(414, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetSimplePasswordEnabled(ComponentName who, boolean value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(415, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semIsSimplePasswordEnabled(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(416, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetKeyguardDisabledFeatures(ComponentName who, int which)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(which);
                    this.mRemote.transact(417, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowStorageCard(ComponentName who, boolean value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(418, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowStorageCard(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(419, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowWifi(ComponentName who, boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(420, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowWifi(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(421, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowTextMessaging(ComponentName who, boolean value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(422, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowTextMessaging(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(423, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowPopImapEmail(ComponentName who, boolean value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(424, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowPopImapEmail(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(425, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowBrowser(ComponentName who, boolean value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(426, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowBrowser(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(427, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowInternetSharing(ComponentName who, boolean value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(428, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowInternetSharing(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(429, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowBluetoothMode(ComponentName who, int size)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(size);
                    this.mRemote.transact(430, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int semGetAllowBluetoothMode(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(431, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowDesktopSync(ComponentName who, boolean value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(432, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowDesktopSync(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(433, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetAllowIrda(ComponentName who, boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    this.mRemote.transact(434, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetAllowIrda(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(435, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetRequireStorageCardEncryption(
                    ComponentName who, boolean value, boolean isParent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(value);
                    _data.writeBoolean(isParent);
                    this.mRemote.transact(436, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean semGetRequireStorageCardEncryption(
                    ComponentName who, int userHandle, boolean isParent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(isParent);
                    this.mRemote.transact(437, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void semSetChangeNotificationEnabled(ComponentName who, boolean notifyChanges)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeBoolean(notifyChanges);
                    this.mRemote.transact(438, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationExemptions(
                    String callerPackage, String packageName, int[] exemptions)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeIntArray(exemptions);
                    this.mRemote.transact(439, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getApplicationExemptions(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(440, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMtePolicy(int flag, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(441, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMtePolicy(String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(442, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(443, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(444, _data, _reply, 0);
                    _reply.readException();
                    ManagedSubscriptionsPolicy _result =
                            (ManagedSubscriptionsPolicy)
                                    _reply.readTypedObject(ManagedSubscriptionsPolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public DevicePolicyState getDevicePolicyState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(445, _data, _reply, 0);
                    _reply.readException();
                    DevicePolicyState _result =
                            (DevicePolicyState) _reply.readTypedObject(DevicePolicyState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean triggerDevicePolicyEngineMigration(boolean forceMigration)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(forceMigration);
                    this.mRemote.transact(446, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceFinanced(String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(447, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public String getFinancedDeviceKioskRoleHolder(String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(448, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionForKnox(
                    ComponentName who, String key, boolean enable, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(key);
                    _data.writeBoolean(enable);
                    _data.writeInt(userId);
                    this.mRemote.transact(449, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileAppToIgnored(int userId, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(450, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getSamsungSDcardEncryptionStatus(ComponentName who, int userHandle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(451, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void calculateHasIncompatibleAccounts() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(452, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setContentProtectionPolicy(
                    ComponentName who, String callerPackageName, int policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    _data.writeInt(policy);
                    this.mRemote.transact(453, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getContentProtectionPolicy(ComponentName who, String callerPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(who, 0);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(454, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getSubscriptionIds(String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(455, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaxPolicyStorageLimit(String callerPackageName, int storageLimit)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeInt(storageLimit);
                    this.mRemote.transact(456, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceSetMaxPolicyStorageLimit(String callerPackageName, int storageLimit)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeInt(storageLimit);
                    this.mRemote.transact(457, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaxPolicyStorageLimit(String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(458, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPolicySizeForAdmin(String callerPackageName, EnforcingAdmin admin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeTypedObject(admin, 0);
                    this.mRemote.transact(459, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getHeadlessDeviceOwnerMode(String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(460, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        private boolean onTransact$setPasswordQuality$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordQuality(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordQuality$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordQuality(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLength$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordMinimumLength(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLength$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordMinimumLength(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordMinimumUpperCase$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordMinimumUpperCase(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumUpperCase$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordMinimumUpperCase(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLowerCase$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordMinimumLowerCase(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLowerCase$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordMinimumLowerCase(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLetters$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordMinimumLetters(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLetters$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordMinimumLetters(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNumeric$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordMinimumNumeric(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNumeric$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordMinimumNumeric(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordMinimumSymbols$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordMinimumSymbols(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumSymbols$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordMinimumSymbols(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNonLetter$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordMinimumNonLetter(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNonLetter$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordMinimumNonLetter(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordHistoryLength$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordHistoryLength(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordHistoryLength$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getPasswordHistoryLength(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPasswordExpirationTimeout$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            long _arg2 = data.readLong();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setPasswordExpirationTimeout(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordExpirationTimeout$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            long _result = getPasswordExpirationTimeout(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeLong(_result);
            return true;
        }

        private boolean onTransact$getPasswordExpiration$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            long _result = getPasswordExpiration(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeLong(_result);
            return true;
        }

        private boolean onTransact$isActivePasswordSufficient$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = isActivePasswordSufficient(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setRequiredPasswordComplexity$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setRequiredPasswordComplexity(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getCurrentFailedPasswordAttempts$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getCurrentFailedPasswordAttempts(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setMaximumFailedPasswordsForWipe$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setMaximumFailedPasswordsForWipe(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumFailedPasswordsForWipe$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getMaximumFailedPasswordsForWipe(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setMaximumTimeToLock$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            long _arg2 = data.readLong();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setMaximumTimeToLock(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumTimeToLock$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            long _result = getMaximumTimeToLock(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeLong(_result);
            return true;
        }

        private boolean onTransact$setRequiredStrongAuthTimeout$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            long _arg2 = data.readLong();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setRequiredStrongAuthTimeout(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getRequiredStrongAuthTimeout$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            long _result = getRequiredStrongAuthTimeout(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeLong(_result);
            return true;
        }

        private boolean onTransact$lockNow$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            lockNow(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$wipeDataWithReason$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            wipeDataWithReason(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setFactoryResetProtectionPolicy$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            FactoryResetProtectionPolicy _arg2 =
                    (FactoryResetProtectionPolicy)
                            data.readTypedObject(FactoryResetProtectionPolicy.CREATOR);
            data.enforceNoDataAvail();
            setFactoryResetProtectionPolicy(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalProxy$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            ComponentName _result = setGlobalProxy(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$setCameraDisabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setCameraDisabled(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getCameraDisabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = getCameraDisabled(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setScreenCaptureDisabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setScreenCaptureDisabled(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getScreenCaptureDisabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = getScreenCaptureDisabled(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setKeyguardDisabledFeatures$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setKeyguardDisabledFeatures(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getKeyguardDisabledFeatures$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = getKeyguardDisabledFeatures(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setActiveAdmin$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setActiveAdmin(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getRemoveWarning$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            RemoteCallback _arg1 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            getRemoveWarning(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$hasGrantedPolicy$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = hasGrantedPolicy(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setDeviceOwner$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setDeviceOwner(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$checkDeviceIdentifierAccess$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = checkDeviceIdentifierAccess(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setPackagesSuspended$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String[] _arg2 = data.createStringArray();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            String[] _result = setPackagesSuspended(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeStringArray(_result);
            return true;
        }

        private boolean onTransact$isPackageSuspended$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = isPackageSuspended(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$installCaCert$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            byte[] _arg2 = data.createByteArray();
            data.enforceNoDataAvail();
            boolean _result = installCaCert(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$uninstallCaCerts$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String[] _arg2 = data.createStringArray();
            data.enforceNoDataAvail();
            uninstallCaCerts(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$approveCaCert$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = approveCaCert(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$installKeyPair$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            byte[] _arg2 = data.createByteArray();
            byte[] _arg3 = data.createByteArray();
            byte[] _arg4 = data.createByteArray();
            String _arg5 = data.readString();
            boolean _arg6 = data.readBoolean();
            boolean _arg7 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result =
                    installKeyPair(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$removeKeyPair$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = removeKeyPair(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$generateKeyPair$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            ParcelableKeyGenParameterSpec _arg3 =
                    (ParcelableKeyGenParameterSpec)
                            data.readTypedObject(ParcelableKeyGenParameterSpec.CREATOR);
            int _arg4 = data.readInt();
            KeymasterCertificateChain _arg5 = new KeymasterCertificateChain();
            data.enforceNoDataAvail();
            boolean _result = generateKeyPair(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            reply.writeBoolean(_result);
            reply.writeTypedObject(_arg5, 1);
            return true;
        }

        private boolean onTransact$setKeyPairCertificate$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            byte[] _arg3 = data.createByteArray();
            byte[] _arg4 = data.createByteArray();
            boolean _arg5 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setKeyPairCertificate(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$choosePrivateKeyAlias$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            Uri _arg1 = (Uri) data.readTypedObject(Uri.CREATOR);
            String _arg2 = data.readString();
            IBinder _arg3 = data.readStrongBinder();
            data.enforceNoDataAvail();
            choosePrivateKeyAlias(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setDelegatedScopes$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            List<String> _arg2 = data.createStringArrayList();
            data.enforceNoDataAvail();
            setDelegatedScopes(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setAlwaysOnVpnPackage$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            List<String> _arg3 = data.createStringArrayList();
            data.enforceNoDataAvail();
            boolean _result = setAlwaysOnVpnPackage(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$addPersistentPreferredActivity$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            IntentFilter _arg2 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
            ComponentName _arg3 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            data.enforceNoDataAvail();
            addPersistentPreferredActivity(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$clearPackagePersistentPreferredActivities$(
                Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            clearPackagePersistentPreferredActivities(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setDefaultSmsApplication$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setDefaultSmsApplication(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationRestrictions$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            setApplicationRestrictions(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getApplicationRestrictions$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            Bundle _result = getApplicationRestrictions(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$setUserRestriction$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            setUserRestriction(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getUserRestrictions$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            Bundle _result = getUserRestrictions(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileIntentFilter$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            IntentFilter _arg2 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
            int _arg3 = data.readInt();
            data.enforceNoDataAvail();
            addCrossProfileIntentFilter(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$isAccessibilityServicePermittedByAdmin$(
                Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = isAccessibilityServicePermittedByAdmin(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setPermittedInputMethods$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            List<String> _arg2 = data.createStringArrayList();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setPermittedInputMethods(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$getPermittedInputMethods$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            List<String> _result = getPermittedInputMethods(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeStringList(_result);
            return true;
        }

        private boolean onTransact$isInputMethodPermittedByAdmin$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = isInputMethodPermittedByAdmin(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setApplicationHidden$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setApplicationHidden(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$isApplicationHidden$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = isApplicationHidden(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$createAndManageUser$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            ComponentName _arg2 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            PersistableBundle _arg3 =
                    (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            UserHandle _result = createAndManageUser(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$enableSystemApp$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            enableSystemApp(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$enableSystemAppWithIntent$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            Intent _arg2 = (Intent) data.readTypedObject(Intent.CREATOR);
            data.enforceNoDataAvail();
            int _result = enableSystemAppWithIntent(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$installExistingPackage$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = installExistingPackage(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setAccountManagementDisabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            setAccountManagementDisabled(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getAccountTypesWithManagementDisabledAsUser$(
                Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            String[] _result = getAccountTypesWithManagementDisabledAsUser(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeStringArray(_result);
            return true;
        }

        private boolean onTransact$setLockTaskPackages$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String[] _arg2 = data.createStringArray();
            data.enforceNoDataAvail();
            setLockTaskPackages(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setLockTaskFeatures$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setLockTaskFeatures(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalSetting$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setGlobalSetting(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemSetting$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setSystemSetting(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setSecureSetting$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setSecureSetting(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setConfiguredNetworksLockdownState$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setConfiguredNetworksLockdownState(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setTime$(Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            long _arg2 = data.readLong();
            data.enforceNoDataAvail();
            boolean _result = setTime(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setTimeZone$(Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = setTimeZone(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$notifyLockTaskModeChanged$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            notifyLockTaskModeChanged(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setUninstallBlocked$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            setUninstallBlocked(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$startManagedQuickContact$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            long _arg1 = data.readLong();
            boolean _arg2 = data.readBoolean();
            long _arg3 = data.readLong();
            Intent _arg4 = (Intent) data.readTypedObject(Intent.CREATOR);
            data.enforceNoDataAvail();
            startManagedQuickContact(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setTrustAgentConfiguration$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            ComponentName _arg2 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            PersistableBundle _arg3 =
                    (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            setTrustAgentConfiguration(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getTrustAgentConfiguration$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            ComponentName _arg1 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg2 = data.readInt();
            boolean _arg3 = data.readBoolean();
            data.enforceNoDataAvail();
            List<PersistableBundle> _result =
                    getTrustAgentConfiguration(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeTypedList(_result, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileWidgetProvider$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = addCrossProfileWidgetProvider(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$removeCrossProfileWidgetProvider$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = removeCrossProfileWidgetProvider(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setAutoTimeEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setAutoTimeEnabled(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setAutoTimeZoneEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setAutoTimeZoneEnabled(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemUpdatePolicy$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            SystemUpdatePolicy _arg2 =
                    (SystemUpdatePolicy) data.readTypedObject(SystemUpdatePolicy.CREATOR);
            data.enforceNoDataAvail();
            setSystemUpdatePolicy(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setStatusBarDisabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setStatusBarDisabled(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setPermissionPolicy$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPermissionPolicy(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPermissionGrantState$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            RemoteCallback _arg5 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
            data.enforceNoDataAvail();
            setPermissionGrantState(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPermissionGrantState$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            int _result = getPermissionGrantState(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setKeepUninstalledPackages$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            List<String> _arg2 = data.createStringArrayList();
            data.enforceNoDataAvail();
            setKeepUninstalledPackages(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setShortSupportMessage$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            CharSequence _arg2 =
                    (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
            data.enforceNoDataAvail();
            setShortSupportMessage(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationName$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            CharSequence _arg2 =
                    (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
            data.enforceNoDataAvail();
            setOrganizationName(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setSecurityLoggingEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setSecurityLoggingEnabled(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setNetworkLoggingEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setNetworkLoggingEnabled(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$retrieveNetworkLogs$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            long _arg2 = data.readLong();
            data.enforceNoDataAvail();
            List<NetworkEvent> _result = retrieveNetworkLogs(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeTypedList(_result, 1);
            return true;
        }

        private boolean onTransact$bindDeviceAdminServiceAsUser$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            IApplicationThread _arg1 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            IBinder _arg2 = data.readStrongBinder();
            Intent _arg3 = (Intent) data.readTypedObject(Intent.CREATOR);
            IServiceConnection _arg4 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
            long _arg5 = data.readLong();
            int _arg6 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result =
                    bindDeviceAdminServiceAsUser(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setResetPasswordToken$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            byte[] _arg2 = data.createByteArray();
            data.enforceNoDataAvail();
            boolean _result = setResetPasswordToken(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$resetPasswordWithToken$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            byte[] _arg3 = data.createByteArray();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = resetPasswordWithToken(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$clearApplicationUserData$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            IPackageDataObserver _arg2 =
                    IPackageDataObserver.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            clearApplicationUserData(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getDisallowedSystemApps$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            List<String> _result = getDisallowedSystemApps(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeStringList(_result);
            return true;
        }

        private boolean onTransact$transferOwnership$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            ComponentName _arg1 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            PersistableBundle _arg2 =
                    (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
            data.enforceNoDataAvail();
            transferOwnership(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$updateOverrideApn$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            ApnSetting _arg2 = (ApnSetting) data.readTypedObject(ApnSetting.CREATOR);
            data.enforceNoDataAvail();
            boolean _result = updateOverrideApn(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$isMeteredDataDisabledPackageForUser$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = isMeteredDataDisabledPackageForUser(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setPasswordQualityMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordQualityMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordMinimumLengthMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordMinimumLengthMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordMinimumUpperCaseMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordMinimumUpperCaseMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordMinimumLowerCaseMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordMinimumLowerCaseMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordMinimumLettersMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordMinimumLettersMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordMinimumNumericMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordMinimumNumericMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordMinimumSymbolsMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordMinimumSymbolsMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordMinimumNonLetterMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordMinimumNonLetterMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordHistoryLengthMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordHistoryLengthMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPasswordExpirationTimeoutMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            long _arg1 = data.readLong();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setPasswordExpirationTimeoutMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setMaximumFailedPasswordsForWipeMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setMaximumFailedPasswordsForWipeMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setMaximumTimeToLockMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            long _arg1 = data.readLong();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setMaximumTimeToLockMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setKeyguardDisabledFeaturesMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setKeyguardDisabledFeaturesMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationRestrictionsMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            Bundle _arg2 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            int _arg3 = data.readInt();
            data.enforceNoDataAvail();
            setApplicationRestrictionsMDM(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getApplicationRestrictionsMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            Bundle _result = getApplicationRestrictionsMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$resetPasswordWithTokenMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            byte[] _arg2 = data.createByteArray();
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = resetPasswordWithTokenMDM(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setResetPasswordTokenMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            byte[] _arg1 = data.createByteArray();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = setResetPasswordTokenMDM(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setTrustAgentConfigurationMDM$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            ComponentName _arg1 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            ComponentName _arg2 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            PersistableBundle _arg3 =
                    (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
            data.enforceNoDataAvail();
            setTrustAgentConfigurationMDM(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$hasDelegatedPermission$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = hasDelegatedPermission(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$reportFailedPasswordAttemptWithFailureCount$(
                Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            reportFailedPasswordAttemptWithFailureCount(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalPrivateDns$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            int _result = setGlobalPrivateDns(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setProfileOwnerOnOrganizationOwnedDevice$(
                Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setProfileOwnerOnOrganizationOwnedDevice(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$installUpdateFromFile$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            ParcelFileDescriptor _arg2 =
                    (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
            StartInstallingUpdateCallback _arg3 =
                    StartInstallingUpdateCallback.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            installUpdateFromFile(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$startViewCalendarEventInManagedProfile$(
                Parcel data, Parcel reply) throws RemoteException {
            String _arg0 = data.readString();
            long _arg1 = data.readLong();
            long _arg2 = data.readLong();
            long _arg3 = data.readLong();
            boolean _arg4 = data.readBoolean();
            int _arg5 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result =
                    startViewCalendarEventInManagedProfile(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setKeyGrantForApp$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            String _arg3 = data.readString();
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setKeyGrantForApp(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setKeyGrantToWifiAuth$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setKeyGrantToWifiAuth(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setUserControlDisabledPackages$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            List<String> _arg2 = data.createStringArrayList();
            data.enforceNoDataAvail();
            setUserControlDisabledPackages(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setCommonCriteriaModeEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setCommonCriteriaModeEnabled(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationIdForUser$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setOrganizationIdForUser(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiSsidPolicy$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            WifiSsidPolicy _arg1 = (WifiSsidPolicy) data.readTypedObject(WifiSsidPolicy.CREATOR);
            data.enforceNoDataAvail();
            setWifiSsidPolicy(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getDrawable$(Parcel data, Parcel reply) throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            ParcelableResource _result = getDrawable(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$semSetPasswordQuality$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetPasswordQuality(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumLength$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetPasswordMinimumLength(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumUpperCase$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetPasswordMinimumUpperCase(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumLowerCase$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetPasswordMinimumLowerCase(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordMinimumNonLetter$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetPasswordMinimumNonLetter(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordHistoryLength$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetPasswordHistoryLength(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetPasswordExpirationTimeout$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            long _arg1 = data.readLong();
            data.enforceNoDataAvail();
            semSetPasswordExpirationTimeout(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetSimplePasswordEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetSimplePasswordEnabled(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semIsSimplePasswordEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semIsSimplePasswordEnabled(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetKeyguardDisabledFeatures$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetKeyguardDisabledFeatures(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semSetAllowStorageCard$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowStorageCard(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowStorageCard$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowStorageCard(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetAllowWifi$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowWifi(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowWifi$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowWifi(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetAllowTextMessaging$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowTextMessaging(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowTextMessaging$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowTextMessaging(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetAllowPopImapEmail$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowPopImapEmail(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowPopImapEmail$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowPopImapEmail(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetAllowBrowser$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowBrowser(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowBrowser$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowBrowser(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetAllowInternetSharing$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowInternetSharing(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowInternetSharing$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowInternetSharing(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetAllowBluetoothMode$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            semSetAllowBluetoothMode(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowBluetoothMode$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            int _result = semGetAllowBluetoothMode(_arg0, _arg1);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$semSetAllowDesktopSync$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowDesktopSync(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowDesktopSync$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowDesktopSync(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetAllowIrda$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetAllowIrda(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetAllowIrda$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = semGetAllowIrda(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetRequireStorageCardEncryption$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetRequireStorageCardEncryption(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$semGetRequireStorageCardEncryption$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = semGetRequireStorageCardEncryption(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$semSetChangeNotificationEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            semSetChangeNotificationEnabled(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationExemptions$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            int[] _arg2 = data.createIntArray();
            data.enforceNoDataAvail();
            setApplicationExemptions(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setMtePolicy$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            setMtePolicy(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setUserRestrictionForKnox$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            int _arg3 = data.readInt();
            data.enforceNoDataAvail();
            setUserRestrictionForKnox(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setCrossProfileAppToIgnored$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            setCrossProfileAppToIgnored(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getSamsungSDcardEncryptionStatus$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = getSamsungSDcardEncryptionStatus(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setContentProtectionPolicy$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setContentProtectionPolicy(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getContentProtectionPolicy$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            int _result = getContentProtectionPolicy(_arg0, _arg1);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setMaxPolicyStorageLimit$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            setMaxPolicyStorageLimit(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$forceSetMaxPolicyStorageLimit$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            forceSetMaxPolicyStorageLimit(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getPolicySizeForAdmin$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            EnforcingAdmin _arg1 = (EnforcingAdmin) data.readTypedObject(EnforcingAdmin.CREATOR);
            data.enforceNoDataAvail();
            int _result = getPolicySizeForAdmin(_arg0, _arg1);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 459;
        }
    }
}
