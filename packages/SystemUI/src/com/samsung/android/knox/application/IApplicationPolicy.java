package com.samsung.android.knox.application;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface IApplicationPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.application.IApplicationPolicy";

    boolean addAppNotificationBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity) throws RemoteException;

    int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity) throws RemoteException;

    boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    List<String> addPackagesToPreventStartBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str, List<UsbDeviceConfig> list) throws RemoteException;

    void applicationUsageAppLaunchCount(String str, int i) throws RemoteException;

    void applicationUsageAppPauseTime(String str, int i) throws RemoteException;

    int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List<String> list, int i) throws RemoteException;

    boolean changeApplicationIcon(ContextInfo contextInfo, String str, byte[] bArr) throws RemoteException;

    boolean changeApplicationName(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean clearDisableClipboardBlackList(ContextInfo contextInfo) throws RemoteException;

    boolean clearDisableClipboardWhiteList(ContextInfo contextInfo) throws RemoteException;

    boolean clearDisableUpdateBlackList(ContextInfo contextInfo) throws RemoteException;

    boolean clearDisableUpdateWhiteList(ContextInfo contextInfo) throws RemoteException;

    boolean clearFocusMonitoringList(ContextInfo contextInfo) throws RemoteException;

    int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException;

    boolean clearPreventStartBlackList(ContextInfo contextInfo) throws RemoteException;

    boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) throws RemoteException;

    IntentFilter createIntentFilter(Intent intent) throws RemoteException;

    boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean deleteManagedAppInfo(ContextInfo contextInfo, String str) throws RemoteException;

    void doSelfUninstall(ContextInfo contextInfo) throws RemoteException;

    boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean getAddHomeShorcutRequested() throws RemoteException;

    AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo) throws RemoteException;

    List<DefaultAppConfiguration> getAllDefaultApplications(ContextInfo contextInfo) throws RemoteException;

    List<DefaultAppConfiguration> getAllDefaultApplicationsInternal(int i) throws RemoteException;

    List<String> getAllPackagesFromBatteryOptimizationWhiteList() throws RemoteException;

    Map getAllWidgets(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getAppInstallToSdCard(ContextInfo contextInfo) throws RemoteException;

    int getAppInstallationMode(ContextInfo contextInfo) throws RemoteException;

    List<String> getAppNotificationBlackList(ContextInfo contextInfo, boolean z) throws RemoteException;

    List<String> getAppNotificationWhiteList(ContextInfo contextInfo, boolean z) throws RemoteException;

    List<AppControlInfo> getAppPackageNamesAllBlackLists(ContextInfo contextInfo) throws RemoteException;

    List<AppControlInfo> getAppPackageNamesAllWhiteLists(ContextInfo contextInfo) throws RemoteException;

    List<AppControlInfo> getAppPermissionsAllBlackLists(ContextInfo contextInfo) throws RemoteException;

    String[] getAppPermissionsBlackList(ContextInfo contextInfo) throws RemoteException;

    String[] getAppSignatureBlackList(ContextInfo contextInfo) throws RemoteException;

    List<AppControlInfo> getAppSignaturesAllBlackLists(ContextInfo contextInfo) throws RemoteException;

    List<AppControlInfo> getAppSignaturesAllWhiteLists(ContextInfo contextInfo) throws RemoteException;

    String[] getAppSignaturesWhiteList(ContextInfo contextInfo) throws RemoteException;

    long getApplicationCacheSize(ContextInfo contextInfo, String str) throws RemoteException;

    long getApplicationCodeSize(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName) throws RemoteException;

    long getApplicationCpuUsage(ContextInfo contextInfo, String str) throws RemoteException;

    long getApplicationDataSize(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getApplicationGrantedPermissions(ContextInfo contextInfo, String str) throws RemoteException;

    byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    long getApplicationMemoryUsage(ContextInfo contextInfo, String str) throws RemoteException;

    String getApplicationName(ContextInfo contextInfo, String str) throws RemoteException;

    String getApplicationNameFromDb(String str, int i) throws RemoteException;

    int getApplicationNotificationMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    int getApplicationNotificationModeAsUser(int i) throws RemoteException;

    List<String> getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo) throws RemoteException;

    Bundle getApplicationRestrictions(ComponentName componentName, String str, int i) throws RemoteException;

    boolean getApplicationStateEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) throws RemoteException;

    String[] getApplicationStateList(ContextInfo contextInfo, boolean z) throws RemoteException;

    long getApplicationTotalSize(ContextInfo contextInfo, String str) throws RemoteException;

    int getApplicationUid(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getApplicationUninstallationEnabledAsUser(String str, int i) throws RemoteException;

    int getApplicationUninstallationMode(ContextInfo contextInfo) throws RemoteException;

    String getApplicationVersion(ContextInfo contextInfo, String str) throws RemoteException;

    int getApplicationVersionCode(ContextInfo contextInfo, String str) throws RemoteException;

    ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getAuthorizedScopes(String str) throws RemoteException;

    AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo) throws RemoteException;

    boolean getConcentrationMode() throws RemoteException;

    ComponentName getDefaultApplication(ContextInfo contextInfo, Intent intent) throws RemoteException;

    ComponentName getDefaultApplicationInternal(Intent intent, int i) throws RemoteException;

    List<String> getDisabledPackages(int i) throws RemoteException;

    List<ComponentName> getHomeShortcuts(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    String[] getInstalledApplicationsIDList(ContextInfo contextInfo) throws RemoteException;

    String[] getInstalledManagedApplicationsList(ContextInfo contextInfo) throws RemoteException;

    List<NetworkStats> getNetworkStats(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromBlackList(ContextInfo contextInfo, int i) throws RemoteException;

    List<String> getPackagesFromClearCacheBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromClearCacheWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromClearDataBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromClearDataWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i) throws RemoteException;

    List<String> getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i) throws RemoteException;

    List<String> getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromFocusMonitoringList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromForceStopBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromForceStopWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromPreventStartBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromWhiteList(ContextInfo contextInfo, int i) throws RemoteException;

    List<String> getPackagesFromWidgetBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromWidgetWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getRuntimePermissions(ContextInfo contextInfo, String str, int i) throws RemoteException;

    List<String> getRuntimePermissionsEnforced(int i, String str, int i2) throws RemoteException;

    List<AppInfo> getTopNCPUUsageApp(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    List<AppInfo> getTopNDataUsageApp(ContextInfo contextInfo, int i) throws RemoteException;

    List<AppInfo> getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    List<UsbDeviceConfig> getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) throws RemoteException;

    boolean handleStatusBarNotificationNotAllowedAsUser(String str, int i, Notification notification2) throws RemoteException;

    boolean installApplication(ContextInfo contextInfo, String str, boolean z, ParcelFileDescriptor parcelFileDescriptor, boolean z2) throws RemoteException;

    boolean installExistingApplication(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean isAnyApplicationIconChangedAsUser(int i) throws RemoteException;

    boolean isAnyApplicationNameChangedAsUser(int i) throws RemoteException;

    boolean isApplicationClearCacheDisabled(String str, int i, boolean z) throws RemoteException;

    boolean isApplicationClearDataDisabled(String str, int i, boolean z) throws RemoteException;

    boolean isApplicationExternalStorageBlacklisted(int i, String str) throws RemoteException;

    boolean isApplicationExternalStorageWhitelisted(int i, String str) throws RemoteException;

    boolean isApplicationFocusMonitoredAsUser(String str, int i) throws RemoteException;

    boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) throws RemoteException;

    boolean isApplicationInstallationEnabled(String str, List<String> list, List<String> list2, int i) throws RemoteException;

    boolean isApplicationInstalled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isApplicationRunning(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isApplicationSetToDefault(String str, int i) throws RemoteException;

    boolean isApplicationStartDisabledAsUser(String str, int i) throws RemoteException;

    boolean isCameraAllowlistedApp(int i, int i2) throws RemoteException;

    boolean isFromApprovedInstaller(int i, int i2) throws RemoteException;

    boolean isIntentDisabled(Intent intent) throws RemoteException;

    boolean isOcspCheckEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isPackageClipboardAllowed(String str, int i) throws RemoteException;

    boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isPackageInBlacklistInternal(int i, int i2, int i3) throws RemoteException;

    boolean isPackageInWhitelistInternal(int i, int i2, int i3) throws RemoteException;

    boolean isPackageUpdateAllowed(String str, boolean z) throws RemoteException;

    boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isStatusBarNotificationAllowedAsUser(String str, int i) throws RemoteException;

    boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str) throws RemoteException;

    boolean isWidgetAllowed(ContextInfo contextInfo, String str) throws RemoteException;

    void reapplyRuntimePermissions(int i) throws RemoteException;

    boolean removeAppNotificationBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) throws RemoteException;

    List<String> removeManagedApplications(ContextInfo contextInfo, List<String> list) throws RemoteException;

    int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    int removePackageFromBlackList(ContextInfo contextInfo, int i, String str) throws RemoteException;

    int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str) throws RemoteException;

    boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    void setAndroidMarketState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAppInstallationMode(ContextInfo contextInfo, int i) throws RemoteException;

    boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException;

    void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean setApplicationNotificationMode(ContextInfo contextInfo, int i) throws RemoteException;

    void setApplicationRestrictions(ComponentName componentName, String str, Bundle bundle, int i) throws RemoteException;

    boolean setApplicationState(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z) throws RemoteException;

    void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i) throws RemoteException;

    boolean setAsManagedApp(ContextInfo contextInfo, String str) throws RemoteException;

    int setAuthorizedScopes(AppIdentity appIdentity, List<String> list) throws RemoteException;

    boolean setConcentrationMode(ContextInfo contextInfo, List<String> list, boolean z) throws RemoteException;

    boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) throws RemoteException;

    boolean startApp(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean stopApp(ContextInfo contextInfo, String str) throws RemoteException;

    boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean updateApplicationTable(int i, int i2, int i3) throws RemoteException;

    void updateDataUsageDb() throws RemoteException;

    void updateWidgetStatus(ComponentName componentName, int i) throws RemoteException;

    boolean verifyRuntimePermissionPackageSignature(String str) throws RemoteException;

    boolean wipeApplicationData(ContextInfo contextInfo, String str) throws RemoteException;

    public class Default implements IApplicationPolicy {
        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addAppNotificationBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> addPackagesToPreventStartBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean addUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str, List<UsbDeviceConfig> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List<String> list, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean changeApplicationIcon(ContextInfo contextInfo, String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean changeApplicationName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean clearDisableClipboardBlackList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean clearDisableClipboardWhiteList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean clearDisableUpdateBlackList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean clearDisableUpdateWhiteList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean clearFocusMonitoringList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean clearPreventStartBlackList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public IntentFilter createIntentFilter(Intent intent) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean deleteManagedAppInfo(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getAddHomeShorcutRequested() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<DefaultAppConfiguration> getAllDefaultApplications(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<DefaultAppConfiguration> getAllDefaultApplicationsInternal(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getAllPackagesFromBatteryOptimizationWhiteList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public Map getAllWidgets(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getAppInstallToSdCard(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int getAppInstallationMode(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getAppNotificationBlackList(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getAppNotificationWhiteList(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppControlInfo> getAppPackageNamesAllBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppControlInfo> getAppPackageNamesAllWhiteLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppControlInfo> getAppPermissionsAllBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String[] getAppPermissionsBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String[] getAppSignatureBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppControlInfo> getAppSignaturesAllBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppControlInfo> getAppSignaturesAllWhiteLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String[] getAppSignaturesWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public long getApplicationCacheSize(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public long getApplicationCodeSize(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public long getApplicationCpuUsage(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public long getApplicationDataSize(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getApplicationGrantedPermissions(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public long getApplicationMemoryUsage(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String getApplicationName(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String getApplicationNameFromDb(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int getApplicationNotificationMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int getApplicationNotificationModeAsUser(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public Bundle getApplicationRestrictions(ComponentName componentName, String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getApplicationStateEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String[] getApplicationStateList(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public long getApplicationTotalSize(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int getApplicationUid(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getApplicationUninstallationEnabledAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int getApplicationUninstallationMode(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String getApplicationVersion(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int getApplicationVersionCode(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getAuthorizedScopes(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean getConcentrationMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public ComponentName getDefaultApplication(ContextInfo contextInfo, Intent intent) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public ComponentName getDefaultApplicationInternal(Intent intent, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getDisabledPackages(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<ComponentName> getHomeShortcuts(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String[] getInstalledApplicationsIDList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String[] getInstalledManagedApplicationsList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<NetworkStats> getNetworkStats(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromBlackList(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromClearCacheBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromClearCacheWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromClearDataBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromClearDataWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromFocusMonitoringList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromForceStopBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromForceStopWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromPreventStartBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromWhiteList(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromWidgetBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getPackagesFromWidgetWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getRuntimePermissions(ContextInfo contextInfo, String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> getRuntimePermissionsEnforced(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppInfo> getTopNCPUUsageApp(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppInfo> getTopNDataUsageApp(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<AppInfo> getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<UsbDeviceConfig> getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean handleStatusBarNotificationNotAllowedAsUser(String str, int i, Notification notification2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean installApplication(ContextInfo contextInfo, String str, boolean z, ParcelFileDescriptor parcelFileDescriptor, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean installExistingApplication(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isAnyApplicationIconChangedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isAnyApplicationNameChangedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationClearCacheDisabled(String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationClearDataDisabled(String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationExternalStorageBlacklisted(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationExternalStorageWhitelisted(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationFocusMonitoredAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationInstallationEnabled(String str, List<String> list, List<String> list2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationInstalled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationRunning(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationSetToDefault(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isApplicationStartDisabledAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isCameraAllowlistedApp(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isFromApprovedInstaller(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isIntentDisabled(Intent intent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isOcspCheckEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isPackageClipboardAllowed(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isPackageInBlacklistInternal(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isPackageInWhitelistInternal(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isPackageUpdateAllowed(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isStatusBarNotificationAllowedAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean isWidgetAllowed(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeAppNotificationBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public List<String> removeManagedApplications(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int removePackageFromBlackList(ContextInfo contextInfo, int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setAppInstallationMode(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setApplicationNotificationMode(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setApplicationState(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setAsManagedApp(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public int setAuthorizedScopes(AppIdentity appIdentity, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setConcentrationMode(ContextInfo contextInfo, List<String> list, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean startApp(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean stopApp(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean updateApplicationTable(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean verifyRuntimePermissionPackageSignature(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public boolean wipeApplicationData(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void updateDataUsageDb() throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void doSelfUninstall(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void reapplyRuntimePermissions(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void applicationUsageAppLaunchCount(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void applicationUsageAppPauseTime(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void setAndroidMarketState(ContextInfo contextInfo, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void updateWidgetStatus(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public void setApplicationRestrictions(ComponentName componentName, String str, Bundle bundle, int i) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IApplicationPolicy {
        public static final int TRANSACTION_addAppNotificationBlackList = 60;
        public static final int TRANSACTION_addAppNotificationWhiteList = 63;
        public static final int TRANSACTION_addAppPackageNameToBlackList = 45;
        public static final int TRANSACTION_addAppPackageNameToWhiteList = 48;
        public static final int TRANSACTION_addAppPermissionToBlackList = 34;
        public static final int TRANSACTION_addAppSignatureToBlackList = 37;
        public static final int TRANSACTION_addAppSignatureToWhiteList = 88;
        public static final int TRANSACTION_addApplicationToCameraAllowList = 183;
        public static final int TRANSACTION_addHomeShortcut = 70;
        public static final int TRANSACTION_addPackageToBatteryOptimizationWhiteList = 159;
        public static final int TRANSACTION_addPackageToBlackList = 170;
        public static final int TRANSACTION_addPackageToWhiteList = 167;
        public static final int TRANSACTION_addPackagesToClearCacheBlackList = 100;
        public static final int TRANSACTION_addPackagesToClearCacheWhiteList = 103;
        public static final int TRANSACTION_addPackagesToClearDataBlackList = 93;
        public static final int TRANSACTION_addPackagesToClearDataWhiteList = 96;
        public static final int TRANSACTION_addPackagesToDisableClipboardBlackList = 126;
        public static final int TRANSACTION_addPackagesToDisableClipboardWhiteList = 130;
        public static final int TRANSACTION_addPackagesToDisableUpdateBlackList = 112;
        public static final int TRANSACTION_addPackagesToDisableUpdateWhiteList = 115;
        public static final int TRANSACTION_addPackagesToFocusMonitoringList = 137;
        public static final int TRANSACTION_addPackagesToForceStopBlackList = 56;
        public static final int TRANSACTION_addPackagesToForceStopWhiteList = 75;
        public static final int TRANSACTION_addPackagesToPreventStartBlackList = 121;
        public static final int TRANSACTION_addPackagesToWidgetBlackList = 80;
        public static final int TRANSACTION_addPackagesToWidgetWhiteList = 78;
        public static final int TRANSACTION_addUsbDevicesForDefaultAccess = 146;
        public static final int TRANSACTION_applicationUsageAppLaunchCount = 193;
        public static final int TRANSACTION_applicationUsageAppPauseTime = 194;
        public static final int TRANSACTION_applyRuntimePermissions = 154;
        public static final int TRANSACTION_changeApplicationIcon = 31;
        public static final int TRANSACTION_changeApplicationName = 107;
        public static final int TRANSACTION_clearDisableClipboardBlackList = 134;
        public static final int TRANSACTION_clearDisableClipboardWhiteList = 135;
        public static final int TRANSACTION_clearDisableUpdateBlackList = 119;
        public static final int TRANSACTION_clearDisableUpdateWhiteList = 120;
        public static final int TRANSACTION_clearFocusMonitoringList = 140;
        public static final int TRANSACTION_clearPackagesFromExternalStorageWhiteList = 166;
        public static final int TRANSACTION_clearPreventStartBlackList = 124;
        public static final int TRANSACTION_clearUsbDevicesForDefaultAccess = 147;
        public static final int TRANSACTION_createIntentFilter = 201;
        public static final int TRANSACTION_deleteHomeShortcut = 71;
        public static final int TRANSACTION_deleteManagedAppInfo = 177;
        public static final int TRANSACTION_doSelfUninstall = 206;
        public static final int TRANSACTION_enableOcspCheck = 86;
        public static final int TRANSACTION_enableRevocationCheck = 84;
        public static final int TRANSACTION_getAddHomeShorcutRequested = 151;
        public static final int TRANSACTION_getAllAppLastUsage = 28;
        public static final int TRANSACTION_getAllDefaultApplications = 198;
        public static final int TRANSACTION_getAllDefaultApplicationsInternal = 188;
        public static final int TRANSACTION_getAllPackagesFromBatteryOptimizationWhiteList = 162;
        public static final int TRANSACTION_getAllWidgets = 72;
        public static final int TRANSACTION_getAppInstallToSdCard = 179;
        public static final int TRANSACTION_getAppInstallationMode = 54;
        public static final int TRANSACTION_getAppNotificationBlackList = 62;
        public static final int TRANSACTION_getAppNotificationWhiteList = 65;
        public static final int TRANSACTION_getAppPackageNamesAllBlackLists = 47;
        public static final int TRANSACTION_getAppPackageNamesAllWhiteLists = 50;
        public static final int TRANSACTION_getAppPermissionsAllBlackLists = 51;
        public static final int TRANSACTION_getAppPermissionsBlackList = 36;
        public static final int TRANSACTION_getAppSignatureBlackList = 39;
        public static final int TRANSACTION_getAppSignaturesAllBlackLists = 52;
        public static final int TRANSACTION_getAppSignaturesAllWhiteLists = 91;
        public static final int TRANSACTION_getAppSignaturesWhiteList = 90;
        public static final int TRANSACTION_getApplicationCacheSize = 21;
        public static final int TRANSACTION_getApplicationCodeSize = 19;
        public static final int TRANSACTION_getApplicationComponentState = 111;
        public static final int TRANSACTION_getApplicationCpuUsage = 23;
        public static final int TRANSACTION_getApplicationDataSize = 20;
        public static final int TRANSACTION_getApplicationGrantedPermissions = 163;
        public static final int TRANSACTION_getApplicationIconFromDb = 32;
        public static final int TRANSACTION_getApplicationInstallationEnabled = 11;
        public static final int TRANSACTION_getApplicationMemoryUsage = 22;
        public static final int TRANSACTION_getApplicationName = 14;
        public static final int TRANSACTION_getApplicationNameFromDb = 108;
        public static final int TRANSACTION_getApplicationNotificationMode = 67;
        public static final int TRANSACTION_getApplicationNotificationModeAsUser = 68;
        public static final int TRANSACTION_getApplicationPackagesFromCameraAllowList = 184;
        public static final int TRANSACTION_getApplicationRestrictions = 205;
        public static final int TRANSACTION_getApplicationStateEnabled = 10;
        public static final int TRANSACTION_getApplicationStateEnabledAsUser = 189;
        public static final int TRANSACTION_getApplicationStateList = 42;
        public static final int TRANSACTION_getApplicationTotalSize = 18;
        public static final int TRANSACTION_getApplicationUid = 15;
        public static final int TRANSACTION_getApplicationUninstallationEnabled = 12;
        public static final int TRANSACTION_getApplicationUninstallationEnabledAsUser = 192;
        public static final int TRANSACTION_getApplicationUninstallationMode = 73;
        public static final int TRANSACTION_getApplicationVersion = 16;
        public static final int TRANSACTION_getApplicationVersionCode = 17;
        public static final int TRANSACTION_getApplicationsList = 181;
        public static final int TRANSACTION_getAuthorizedScopes = 153;
        public static final int TRANSACTION_getAvgNoAppUsagePerMonth = 27;
        public static final int TRANSACTION_getConcentrationMode = 203;
        public static final int TRANSACTION_getDefaultApplication = 196;
        public static final int TRANSACTION_getDefaultApplicationInternal = 197;
        public static final int TRANSACTION_getDisabledPackages = 150;
        public static final int TRANSACTION_getHomeShortcuts = 142;
        public static final int TRANSACTION_getInstalledApplicationsIDList = 13;
        public static final int TRANSACTION_getInstalledManagedApplicationsList = 180;
        public static final int TRANSACTION_getNetworkStats = 29;
        public static final int TRANSACTION_getPackageSignaturesFromExternalStorageWhiteList = 165;
        public static final int TRANSACTION_getPackagesFromBatteryOptimizationWhiteList = 161;
        public static final int TRANSACTION_getPackagesFromBlackList = 171;
        public static final int TRANSACTION_getPackagesFromClearCacheBlackList = 102;
        public static final int TRANSACTION_getPackagesFromClearCacheWhiteList = 104;
        public static final int TRANSACTION_getPackagesFromClearDataBlackList = 95;
        public static final int TRANSACTION_getPackagesFromClearDataWhiteList = 97;
        public static final int TRANSACTION_getPackagesFromDisableClipboardBlackList = 128;
        public static final int TRANSACTION_getPackagesFromDisableClipboardBlackListAsUserInternal = 129;
        public static final int TRANSACTION_getPackagesFromDisableClipboardWhiteList = 132;
        public static final int TRANSACTION_getPackagesFromDisableClipboardWhiteListAsUserInternal = 133;
        public static final int TRANSACTION_getPackagesFromDisableUpdateBlackList = 114;
        public static final int TRANSACTION_getPackagesFromDisableUpdateWhiteList = 116;
        public static final int TRANSACTION_getPackagesFromFocusMonitoringList = 138;
        public static final int TRANSACTION_getPackagesFromForceStopBlackList = 74;
        public static final int TRANSACTION_getPackagesFromForceStopWhiteList = 76;
        public static final int TRANSACTION_getPackagesFromPreventStartBlackList = 122;
        public static final int TRANSACTION_getPackagesFromWhiteList = 168;
        public static final int TRANSACTION_getPackagesFromWidgetBlackList = 77;
        public static final int TRANSACTION_getPackagesFromWidgetWhiteList = 58;
        public static final int TRANSACTION_getRuntimePermissions = 158;
        public static final int TRANSACTION_getRuntimePermissionsEnforced = 155;
        public static final int TRANSACTION_getTopNCPUUsageApp = 26;
        public static final int TRANSACTION_getTopNDataUsageApp = 25;
        public static final int TRANSACTION_getTopNMemoryUsageApp = 24;
        public static final int TRANSACTION_getUsbDevicesForDefaultAccess = 144;
        public static final int TRANSACTION_handleStatusBarNotificationNotAllowedAsUser = 187;
        public static final int TRANSACTION_installApplication = 5;
        public static final int TRANSACTION_installExistingApplication = 148;
        public static final int TRANSACTION_isAnyApplicationIconChangedAsUser = 33;
        public static final int TRANSACTION_isAnyApplicationNameChangedAsUser = 109;
        public static final int TRANSACTION_isApplicationClearCacheDisabled = 106;
        public static final int TRANSACTION_isApplicationClearDataDisabled = 99;
        public static final int TRANSACTION_isApplicationExternalStorageBlacklisted = 176;
        public static final int TRANSACTION_isApplicationExternalStorageWhitelisted = 175;
        public static final int TRANSACTION_isApplicationFocusMonitoredAsUser = 141;
        public static final int TRANSACTION_isApplicationForceStopDisabled = 57;
        public static final int TRANSACTION_isApplicationInstallationEnabled = 190;
        public static final int TRANSACTION_isApplicationInstalled = 3;
        public static final int TRANSACTION_isApplicationRunning = 4;
        public static final int TRANSACTION_isApplicationSetToDefault = 200;
        public static final int TRANSACTION_isApplicationStartDisabledAsUser = 125;
        public static final int TRANSACTION_isCameraAllowlistedApp = 186;
        public static final int TRANSACTION_isFromApprovedInstaller = 191;
        public static final int TRANSACTION_isIntentDisabled = 44;
        public static final int TRANSACTION_isOcspCheckEnabled = 87;
        public static final int TRANSACTION_isPackageClipboardAllowed = 136;
        public static final int TRANSACTION_isPackageInApprovedInstallerWhiteList = 92;
        public static final int TRANSACTION_isPackageInBlacklistInternal = 174;
        public static final int TRANSACTION_isPackageInWhitelistInternal = 173;
        public static final int TRANSACTION_isPackageUpdateAllowed = 118;
        public static final int TRANSACTION_isRevocationCheckEnabled = 85;
        public static final int TRANSACTION_isStatusBarNotificationAllowedAsUser = 69;
        public static final int TRANSACTION_isUsbDevicePermittedForPackage = 145;
        public static final int TRANSACTION_isWidgetAllowed = 59;
        public static final int TRANSACTION_reapplyRuntimePermissions = 157;
        public static final int TRANSACTION_removeAppNotificationBlackList = 61;
        public static final int TRANSACTION_removeAppNotificationWhiteList = 64;
        public static final int TRANSACTION_removeAppPackageNameFromBlackList = 46;
        public static final int TRANSACTION_removeAppPackageNameFromWhiteList = 49;
        public static final int TRANSACTION_removeAppPermissionFromBlackList = 35;
        public static final int TRANSACTION_removeAppSignatureFromBlackList = 38;
        public static final int TRANSACTION_removeAppSignatureFromWhiteList = 89;
        public static final int TRANSACTION_removeApplicationFromCameraAllowList = 185;
        public static final int TRANSACTION_removeDefaultApplication = 199;
        public static final int TRANSACTION_removeManagedApplications = 1;
        public static final int TRANSACTION_removePackageFromBatteryOptimizationWhiteList = 160;
        public static final int TRANSACTION_removePackageFromBlackList = 172;
        public static final int TRANSACTION_removePackageFromWhiteList = 169;
        public static final int TRANSACTION_removePackagesFromClearCacheBlackList = 101;
        public static final int TRANSACTION_removePackagesFromClearCacheWhiteList = 105;
        public static final int TRANSACTION_removePackagesFromClearDataBlackList = 94;
        public static final int TRANSACTION_removePackagesFromClearDataWhiteList = 98;
        public static final int TRANSACTION_removePackagesFromDisableClipboardBlackList = 127;
        public static final int TRANSACTION_removePackagesFromDisableClipboardWhiteList = 131;
        public static final int TRANSACTION_removePackagesFromDisableUpdateBlackList = 113;
        public static final int TRANSACTION_removePackagesFromDisableUpdateWhiteList = 117;
        public static final int TRANSACTION_removePackagesFromFocusMonitoringList = 139;
        public static final int TRANSACTION_removePackagesFromForceStopBlackList = 83;
        public static final int TRANSACTION_removePackagesFromForceStopWhiteList = 82;
        public static final int TRANSACTION_removePackagesFromPreventStartBlackList = 123;
        public static final int TRANSACTION_removePackagesFromWidgetBlackList = 81;
        public static final int TRANSACTION_removePackagesFromWidgetWhiteList = 79;
        public static final int TRANSACTION_setAndroidMarketState = 164;
        public static final int TRANSACTION_setAppInstallToSdCard = 178;
        public static final int TRANSACTION_setAppInstallationMode = 53;
        public static final int TRANSACTION_setApplicationComponentState = 110;
        public static final int TRANSACTION_setApplicationInstallationDisabled = 8;
        public static final int TRANSACTION_setApplicationNotificationMode = 66;
        public static final int TRANSACTION_setApplicationRestrictions = 204;
        public static final int TRANSACTION_setApplicationState = 7;
        public static final int TRANSACTION_setApplicationStateList = 43;
        public static final int TRANSACTION_setApplicationUninstallationDisabled = 9;
        public static final int TRANSACTION_setApplicationUninstallationMode = 55;
        public static final int TRANSACTION_setAsManagedApp = 182;
        public static final int TRANSACTION_setAuthorizedScopes = 152;
        public static final int TRANSACTION_setConcentrationMode = 202;
        public static final int TRANSACTION_setDefaultApplication = 195;
        public static final int TRANSACTION_startApp = 41;
        public static final int TRANSACTION_stopApp = 40;
        public static final int TRANSACTION_uninstallApplication = 6;
        public static final int TRANSACTION_updateApplicationTable = 149;
        public static final int TRANSACTION_updateDataUsageDb = 30;
        public static final int TRANSACTION_updateWidgetStatus = 143;
        public static final int TRANSACTION_verifyRuntimePermissionPackageSignature = 156;
        public static final int TRANSACTION_wipeApplicationData = 2;

        class Proxy implements IApplicationPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addAppNotificationBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> addPackagesToPreventStartBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean addUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str, List<UsbDeviceConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void applicationUsageAppLaunchCount(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void applicationUsageAppPauseTime(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean changeApplicationIcon(ContextInfo contextInfo, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean changeApplicationName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean clearDisableClipboardBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean clearDisableClipboardWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean clearDisableUpdateBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean clearDisableUpdateWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean clearFocusMonitoringList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean clearPreventStartBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public IntentFilter createIntentFilter(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IntentFilter) parcelObtain2.readTypedObject(IntentFilter.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean deleteManagedAppInfo(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void doSelfUninstall(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getAddHomeShorcutRequested() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AppInfoLastUsage[]) parcelObtain2.createTypedArray(AppInfoLastUsage.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<DefaultAppConfiguration> getAllDefaultApplications(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DefaultAppConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<DefaultAppConfiguration> getAllDefaultApplicationsInternal(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DefaultAppConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getAllPackagesFromBatteryOptimizationWhiteList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public Map getAllWidgets(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getAppInstallToSdCard(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int getAppInstallationMode(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getAppNotificationBlackList(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getAppNotificationWhiteList(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppControlInfo> getAppPackageNamesAllBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppControlInfo> getAppPackageNamesAllWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppControlInfo> getAppPermissionsAllBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String[] getAppPermissionsBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String[] getAppSignatureBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppControlInfo> getAppSignaturesAllBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppControlInfo> getAppSignaturesAllWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String[] getAppSignaturesWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public long getApplicationCacheSize(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public long getApplicationCodeSize(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public long getApplicationCpuUsage(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public long getApplicationDataSize(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getApplicationGrantedPermissions(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public long getApplicationMemoryUsage(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String getApplicationName(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String getApplicationNameFromDb(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int getApplicationNotificationMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int getApplicationNotificationModeAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public Bundle getApplicationRestrictions(ComponentName componentName, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getApplicationStateEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String[] getApplicationStateList(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public long getApplicationTotalSize(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int getApplicationUid(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getApplicationUninstallationEnabledAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int getApplicationUninstallationMode(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String getApplicationVersion(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int getApplicationVersionCode(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ManagedAppInfo[]) parcelObtain2.createTypedArray(ManagedAppInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getAuthorizedScopes(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AppInfoLastUsage[]) parcelObtain2.createTypedArray(AppInfoLastUsage.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean getConcentrationMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public ComponentName getDefaultApplication(ContextInfo contextInfo, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public ComponentName getDefaultApplicationInternal(Intent intent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getDisabledPackages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<ComponentName> getHomeShortcuts(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String[] getInstalledApplicationsIDList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String[] getInstalledManagedApplicationsList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IApplicationPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<NetworkStats> getNetworkStats(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(NetworkStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromBlackList(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromClearCacheBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromClearCacheWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromClearDataBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromClearDataWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromFocusMonitoringList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromForceStopBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromForceStopWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromPreventStartBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromWhiteList(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromWidgetBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getPackagesFromWidgetWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getRuntimePermissions(ContextInfo contextInfo, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> getRuntimePermissionsEnforced(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppInfo> getTopNCPUUsageApp(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppInfo> getTopNDataUsageApp(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<AppInfo> getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<UsbDeviceConfig> getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UsbDeviceConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean handleStatusBarNotificationNotAllowedAsUser(String str, int i, Notification notification2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(notification2, 0);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean installApplication(ContextInfo contextInfo, String str, boolean z, ParcelFileDescriptor parcelFileDescriptor, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean installExistingApplication(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isAnyApplicationIconChangedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isAnyApplicationNameChangedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationClearCacheDisabled(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationClearDataDisabled(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationExternalStorageBlacklisted(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationExternalStorageWhitelisted(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationFocusMonitoredAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationInstallationEnabled(String str, List<String> list, List<String> list2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationInstalled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationRunning(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationSetToDefault(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isApplicationStartDisabledAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isCameraAllowlistedApp(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isFromApprovedInstaller(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isIntentDisabled(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isOcspCheckEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isPackageClipboardAllowed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isPackageInBlacklistInternal(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isPackageInWhitelistInternal(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isPackageUpdateAllowed(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isStatusBarNotificationAllowedAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean isWidgetAllowed(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void reapplyRuntimePermissions(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeAppNotificationBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public List<String> removeManagedApplications(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int removePackageFromBlackList(ContextInfo contextInfo, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void setAndroidMarketState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setAppInstallationMode(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setApplicationNotificationMode(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void setApplicationRestrictions(ComponentName componentName, String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setApplicationState(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setAsManagedApp(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public int setAuthorizedScopes(AppIdentity appIdentity, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setConcentrationMode(ContextInfo contextInfo, List<String> list, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean startApp(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean stopApp(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean updateApplicationTable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void updateDataUsageDb() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public void updateWidgetStatus(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean verifyRuntimePermissionPackageSignature(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public boolean wipeApplicationData(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IApplicationPolicy.DESCRIPTOR);
        }

        public static IApplicationPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IApplicationPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IApplicationPolicy)) ? new Proxy(iBinder) : (IApplicationPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "removeManagedApplications";
                case 2:
                    return "wipeApplicationData";
                case 3:
                    return "isApplicationInstalled";
                case 4:
                    return "isApplicationRunning";
                case 5:
                    return "installApplication";
                case 6:
                    return "uninstallApplication";
                case 7:
                    return "setApplicationState";
                case 8:
                    return "setApplicationInstallationDisabled";
                case 9:
                    return "setApplicationUninstallationDisabled";
                case 10:
                    return "getApplicationStateEnabled";
                case 11:
                    return "getApplicationInstallationEnabled";
                case 12:
                    return "getApplicationUninstallationEnabled";
                case 13:
                    return "getInstalledApplicationsIDList";
                case 14:
                    return "getApplicationName";
                case 15:
                    return "getApplicationUid";
                case 16:
                    return "getApplicationVersion";
                case 17:
                    return "getApplicationVersionCode";
                case 18:
                    return "getApplicationTotalSize";
                case 19:
                    return "getApplicationCodeSize";
                case 20:
                    return "getApplicationDataSize";
                case 21:
                    return "getApplicationCacheSize";
                case 22:
                    return "getApplicationMemoryUsage";
                case 23:
                    return "getApplicationCpuUsage";
                case 24:
                    return "getTopNMemoryUsageApp";
                case 25:
                    return "getTopNDataUsageApp";
                case 26:
                    return "getTopNCPUUsageApp";
                case 27:
                    return "getAvgNoAppUsagePerMonth";
                case 28:
                    return "getAllAppLastUsage";
                case 29:
                    return "getNetworkStats";
                case 30:
                    return "updateDataUsageDb";
                case 31:
                    return "changeApplicationIcon";
                case 32:
                    return "getApplicationIconFromDb";
                case 33:
                    return "isAnyApplicationIconChangedAsUser";
                case 34:
                    return "addAppPermissionToBlackList";
                case 35:
                    return "removeAppPermissionFromBlackList";
                case 36:
                    return "getAppPermissionsBlackList";
                case 37:
                    return "addAppSignatureToBlackList";
                case 38:
                    return "removeAppSignatureFromBlackList";
                case 39:
                    return "getAppSignatureBlackList";
                case 40:
                    return "stopApp";
                case 41:
                    return "startApp";
                case 42:
                    return "getApplicationStateList";
                case 43:
                    return "setApplicationStateList";
                case 44:
                    return "isIntentDisabled";
                case 45:
                    return "addAppPackageNameToBlackList";
                case 46:
                    return "removeAppPackageNameFromBlackList";
                case 47:
                    return "getAppPackageNamesAllBlackLists";
                case 48:
                    return "addAppPackageNameToWhiteList";
                case 49:
                    return "removeAppPackageNameFromWhiteList";
                case 50:
                    return "getAppPackageNamesAllWhiteLists";
                case 51:
                    return "getAppPermissionsAllBlackLists";
                case 52:
                    return "getAppSignaturesAllBlackLists";
                case 53:
                    return "setAppInstallationMode";
                case 54:
                    return "getAppInstallationMode";
                case 55:
                    return "setApplicationUninstallationMode";
                case 56:
                    return "addPackagesToForceStopBlackList";
                case 57:
                    return "isApplicationForceStopDisabled";
                case 58:
                    return "getPackagesFromWidgetWhiteList";
                case 59:
                    return "isWidgetAllowed";
                case 60:
                    return "addAppNotificationBlackList";
                case 61:
                    return "removeAppNotificationBlackList";
                case 62:
                    return "getAppNotificationBlackList";
                case 63:
                    return "addAppNotificationWhiteList";
                case 64:
                    return "removeAppNotificationWhiteList";
                case 65:
                    return "getAppNotificationWhiteList";
                case 66:
                    return "setApplicationNotificationMode";
                case 67:
                    return "getApplicationNotificationMode";
                case 68:
                    return "getApplicationNotificationModeAsUser";
                case 69:
                    return "isStatusBarNotificationAllowedAsUser";
                case 70:
                    return "addHomeShortcut";
                case 71:
                    return "deleteHomeShortcut";
                case 72:
                    return "getAllWidgets";
                case 73:
                    return "getApplicationUninstallationMode";
                case 74:
                    return "getPackagesFromForceStopBlackList";
                case 75:
                    return "addPackagesToForceStopWhiteList";
                case 76:
                    return "getPackagesFromForceStopWhiteList";
                case 77:
                    return "getPackagesFromWidgetBlackList";
                case 78:
                    return "addPackagesToWidgetWhiteList";
                case 79:
                    return "removePackagesFromWidgetWhiteList";
                case 80:
                    return "addPackagesToWidgetBlackList";
                case 81:
                    return "removePackagesFromWidgetBlackList";
                case 82:
                    return "removePackagesFromForceStopWhiteList";
                case 83:
                    return "removePackagesFromForceStopBlackList";
                case 84:
                    return "enableRevocationCheck";
                case 85:
                    return "isRevocationCheckEnabled";
                case 86:
                    return "enableOcspCheck";
                case 87:
                    return "isOcspCheckEnabled";
                case 88:
                    return "addAppSignatureToWhiteList";
                case 89:
                    return "removeAppSignatureFromWhiteList";
                case 90:
                    return "getAppSignaturesWhiteList";
                case 91:
                    return "getAppSignaturesAllWhiteLists";
                case 92:
                    return "isPackageInApprovedInstallerWhiteList";
                case 93:
                    return "addPackagesToClearDataBlackList";
                case 94:
                    return "removePackagesFromClearDataBlackList";
                case 95:
                    return "getPackagesFromClearDataBlackList";
                case 96:
                    return "addPackagesToClearDataWhiteList";
                case 97:
                    return "getPackagesFromClearDataWhiteList";
                case 98:
                    return "removePackagesFromClearDataWhiteList";
                case 99:
                    return "isApplicationClearDataDisabled";
                case 100:
                    return "addPackagesToClearCacheBlackList";
                case 101:
                    return "removePackagesFromClearCacheBlackList";
                case 102:
                    return "getPackagesFromClearCacheBlackList";
                case 103:
                    return "addPackagesToClearCacheWhiteList";
                case 104:
                    return "getPackagesFromClearCacheWhiteList";
                case 105:
                    return "removePackagesFromClearCacheWhiteList";
                case 106:
                    return "isApplicationClearCacheDisabled";
                case 107:
                    return "changeApplicationName";
                case 108:
                    return "getApplicationNameFromDb";
                case 109:
                    return "isAnyApplicationNameChangedAsUser";
                case 110:
                    return "setApplicationComponentState";
                case 111:
                    return "getApplicationComponentState";
                case 112:
                    return "addPackagesToDisableUpdateBlackList";
                case 113:
                    return "removePackagesFromDisableUpdateBlackList";
                case 114:
                    return "getPackagesFromDisableUpdateBlackList";
                case 115:
                    return "addPackagesToDisableUpdateWhiteList";
                case 116:
                    return "getPackagesFromDisableUpdateWhiteList";
                case 117:
                    return "removePackagesFromDisableUpdateWhiteList";
                case 118:
                    return "isPackageUpdateAllowed";
                case 119:
                    return "clearDisableUpdateBlackList";
                case 120:
                    return "clearDisableUpdateWhiteList";
                case 121:
                    return "addPackagesToPreventStartBlackList";
                case 122:
                    return "getPackagesFromPreventStartBlackList";
                case 123:
                    return "removePackagesFromPreventStartBlackList";
                case 124:
                    return "clearPreventStartBlackList";
                case 125:
                    return "isApplicationStartDisabledAsUser";
                case 126:
                    return "addPackagesToDisableClipboardBlackList";
                case 127:
                    return "removePackagesFromDisableClipboardBlackList";
                case 128:
                    return "getPackagesFromDisableClipboardBlackList";
                case 129:
                    return "getPackagesFromDisableClipboardBlackListAsUserInternal";
                case 130:
                    return "addPackagesToDisableClipboardWhiteList";
                case 131:
                    return "removePackagesFromDisableClipboardWhiteList";
                case 132:
                    return "getPackagesFromDisableClipboardWhiteList";
                case 133:
                    return "getPackagesFromDisableClipboardWhiteListAsUserInternal";
                case 134:
                    return "clearDisableClipboardBlackList";
                case 135:
                    return "clearDisableClipboardWhiteList";
                case 136:
                    return "isPackageClipboardAllowed";
                case 137:
                    return "addPackagesToFocusMonitoringList";
                case 138:
                    return "getPackagesFromFocusMonitoringList";
                case 139:
                    return "removePackagesFromFocusMonitoringList";
                case 140:
                    return "clearFocusMonitoringList";
                case 141:
                    return "isApplicationFocusMonitoredAsUser";
                case 142:
                    return "getHomeShortcuts";
                case 143:
                    return "updateWidgetStatus";
                case 144:
                    return "getUsbDevicesForDefaultAccess";
                case 145:
                    return "isUsbDevicePermittedForPackage";
                case 146:
                    return "addUsbDevicesForDefaultAccess";
                case 147:
                    return "clearUsbDevicesForDefaultAccess";
                case 148:
                    return "installExistingApplication";
                case 149:
                    return "updateApplicationTable";
                case 150:
                    return "getDisabledPackages";
                case 151:
                    return "getAddHomeShorcutRequested";
                case 152:
                    return "setAuthorizedScopes";
                case 153:
                    return "getAuthorizedScopes";
                case 154:
                    return "applyRuntimePermissions";
                case 155:
                    return "getRuntimePermissionsEnforced";
                case 156:
                    return "verifyRuntimePermissionPackageSignature";
                case 157:
                    return "reapplyRuntimePermissions";
                case 158:
                    return "getRuntimePermissions";
                case 159:
                    return "addPackageToBatteryOptimizationWhiteList";
                case 160:
                    return "removePackageFromBatteryOptimizationWhiteList";
                case 161:
                    return "getPackagesFromBatteryOptimizationWhiteList";
                case 162:
                    return "getAllPackagesFromBatteryOptimizationWhiteList";
                case 163:
                    return "getApplicationGrantedPermissions";
                case 164:
                    return "setAndroidMarketState";
                case 165:
                    return "getPackageSignaturesFromExternalStorageWhiteList";
                case 166:
                    return "clearPackagesFromExternalStorageWhiteList";
                case 167:
                    return "addPackageToWhiteList";
                case 168:
                    return "getPackagesFromWhiteList";
                case 169:
                    return "removePackageFromWhiteList";
                case 170:
                    return "addPackageToBlackList";
                case 171:
                    return "getPackagesFromBlackList";
                case 172:
                    return "removePackageFromBlackList";
                case 173:
                    return "isPackageInWhitelistInternal";
                case 174:
                    return "isPackageInBlacklistInternal";
                case 175:
                    return "isApplicationExternalStorageWhitelisted";
                case 176:
                    return "isApplicationExternalStorageBlacklisted";
                case 177:
                    return "deleteManagedAppInfo";
                case 178:
                    return "setAppInstallToSdCard";
                case 179:
                    return "getAppInstallToSdCard";
                case 180:
                    return "getInstalledManagedApplicationsList";
                case 181:
                    return "getApplicationsList";
                case 182:
                    return "setAsManagedApp";
                case 183:
                    return "addApplicationToCameraAllowList";
                case 184:
                    return "getApplicationPackagesFromCameraAllowList";
                case 185:
                    return "removeApplicationFromCameraAllowList";
                case 186:
                    return "isCameraAllowlistedApp";
                case 187:
                    return "handleStatusBarNotificationNotAllowedAsUser";
                case 188:
                    return "getAllDefaultApplicationsInternal";
                case 189:
                    return "getApplicationStateEnabledAsUser";
                case 190:
                    return "isApplicationInstallationEnabled";
                case 191:
                    return "isFromApprovedInstaller";
                case 192:
                    return "getApplicationUninstallationEnabledAsUser";
                case 193:
                    return "applicationUsageAppLaunchCount";
                case 194:
                    return "applicationUsageAppPauseTime";
                case 195:
                    return "setDefaultApplication";
                case 196:
                    return "getDefaultApplication";
                case 197:
                    return "getDefaultApplicationInternal";
                case 198:
                    return "getAllDefaultApplications";
                case 199:
                    return "removeDefaultApplication";
                case 200:
                    return "isApplicationSetToDefault";
                case 201:
                    return "createIntentFilter";
                case 202:
                    return "setConcentrationMode";
                case 203:
                    return "getConcentrationMode";
                case 204:
                    return "setApplicationRestrictions";
                case 205:
                    return "getApplicationRestrictions";
                case 206:
                    return "doSelfUninstall";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 205;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IApplicationPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApplicationPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<String> listRemoveManagedApplications = removeManagedApplications(contextInfo, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listRemoveManagedApplications);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zWipeApplicationData = wipeApplicationData(contextInfo2, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWipeApplicationData);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationInstalled = isApplicationInstalled(contextInfo3, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationInstalled);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationRunning = isApplicationRunning(contextInfo4, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationRunning);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zInstallApplication = installApplication(contextInfo5, string4, z, parcelFileDescriptor, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInstallApplication);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string5 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zUninstallApplication = uninstallApplication(contextInfo6, string5, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUninstallApplication);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string6 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean applicationState = setApplicationState(contextInfo7, string6, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationState);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplicationInstallationDisabled(contextInfo8, string7, z5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string8 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplicationUninstallationDisabled(contextInfo9, string8, z6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationStateEnabled = getApplicationStateEnabled(contextInfo10, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationStateEnabled);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationInstallationEnabled = getApplicationInstallationEnabled(contextInfo11, string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationInstallationEnabled);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationUninstallationEnabled = getApplicationUninstallationEnabled(contextInfo12, string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationUninstallationEnabled);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] installedApplicationsIDList = getInstalledApplicationsIDList(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(installedApplicationsIDList);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String applicationName = getApplicationName(contextInfo14, string12);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationName);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int applicationUid = getApplicationUid(contextInfo15, string13);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationUid);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String applicationVersion = getApplicationVersion(contextInfo16, string14);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationVersion);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int applicationVersionCode = getApplicationVersionCode(contextInfo17, string15);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationVersionCode);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationTotalSize = getApplicationTotalSize(contextInfo18, string16);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationTotalSize);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationCodeSize = getApplicationCodeSize(contextInfo19, string17);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationCodeSize);
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationDataSize = getApplicationDataSize(contextInfo20, string18);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationDataSize);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationCacheSize = getApplicationCacheSize(contextInfo21, string19);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationCacheSize);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationMemoryUsage = getApplicationMemoryUsage(contextInfo22, string20);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationMemoryUsage);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationCpuUsage = getApplicationCpuUsage(contextInfo23, string21);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationCpuUsage);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<AppInfo> topNMemoryUsageApp = getTopNMemoryUsageApp(contextInfo24, i3, z7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(topNMemoryUsageApp, 1);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AppInfo> topNDataUsageApp = getTopNDataUsageApp(contextInfo25, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(topNDataUsageApp, 1);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i5 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<AppInfo> topNCPUUsageApp = getTopNCPUUsageApp(contextInfo26, i5, z8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(topNCPUUsageApp, 1);
                    return true;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    AppInfoLastUsage[] avgNoAppUsagePerMonth = getAvgNoAppUsagePerMonth(contextInfo27);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(avgNoAppUsagePerMonth, 1);
                    return true;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    AppInfoLastUsage[] allAppLastUsage = getAllAppLastUsage(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allAppLastUsage, 1);
                    return true;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<NetworkStats> networkStats = getNetworkStats(contextInfo29);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(networkStats, 1);
                    return true;
                case 30:
                    updateDataUsageDb();
                    parcel2.writeNoException();
                    return true;
                case 31:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string22 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zChangeApplicationIcon = changeApplicationIcon(contextInfo30, string22, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeApplicationIcon);
                    return true;
                case 32:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] applicationIconFromDb = getApplicationIconFromDb(contextInfo31, string23);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(applicationIconFromDb);
                    return true;
                case 33:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAnyApplicationIconChangedAsUser = isAnyApplicationIconChangedAsUser(i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAnyApplicationIconChangedAsUser);
                    return true;
                case 34:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppPermissionToBlackList = addAppPermissionToBlackList(contextInfo32, string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppPermissionToBlackList);
                    return true;
                case 35:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAppPermissionFromBlackList = removeAppPermissionFromBlackList(contextInfo33, string25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAppPermissionFromBlackList);
                    return true;
                case 36:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] appPermissionsBlackList = getAppPermissionsBlackList(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(appPermissionsBlackList);
                    return true;
                case 37:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppSignatureToBlackList = addAppSignatureToBlackList(contextInfo35, string26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppSignatureToBlackList);
                    return true;
                case 38:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAppSignatureFromBlackList = removeAppSignatureFromBlackList(contextInfo36, string27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAppSignatureFromBlackList);
                    return true;
                case 39:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] appSignatureBlackList = getAppSignatureBlackList(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(appSignatureBlackList);
                    return true;
                case 40:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zStopApp = stopApp(contextInfo38, string28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopApp);
                    return true;
                case 41:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zStartApp = startApp(contextInfo39, string29, string30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartApp);
                    return true;
                case 42:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String[] applicationStateList = getApplicationStateList(contextInfo40, z9);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(applicationStateList);
                    return true;
                case 43:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String[] applicationStateList2 = setApplicationStateList(contextInfo41, strArrCreateStringArray, z10);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(applicationStateList2);
                    return true;
                case 44:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsIntentDisabled = isIntentDisabled(intent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIntentDisabled);
                    return true;
                case 45:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppPackageNameToBlackList = addAppPackageNameToBlackList(contextInfo42, string31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppPackageNameToBlackList);
                    return true;
                case 46:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAppPackageNameFromBlackList = removeAppPackageNameFromBlackList(contextInfo43, string32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAppPackageNameFromBlackList);
                    return true;
                case 47:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppControlInfo> appPackageNamesAllBlackLists = getAppPackageNamesAllBlackLists(contextInfo44);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(appPackageNamesAllBlackLists, 1);
                    return true;
                case 48:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppPackageNameToWhiteList = addAppPackageNameToWhiteList(contextInfo45, string33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppPackageNameToWhiteList);
                    return true;
                case 49:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAppPackageNameFromWhiteList = removeAppPackageNameFromWhiteList(contextInfo46, string34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAppPackageNameFromWhiteList);
                    return true;
                case 50:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppControlInfo> appPackageNamesAllWhiteLists = getAppPackageNamesAllWhiteLists(contextInfo47);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(appPackageNamesAllWhiteLists, 1);
                    return true;
                case 51:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppControlInfo> appPermissionsAllBlackLists = getAppPermissionsAllBlackLists(contextInfo48);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(appPermissionsAllBlackLists, 1);
                    return true;
                case 52:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppControlInfo> appSignaturesAllBlackLists = getAppSignaturesAllBlackLists(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(appSignaturesAllBlackLists, 1);
                    return true;
                case 53:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean appInstallationMode = setAppInstallationMode(contextInfo50, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appInstallationMode);
                    return true;
                case 54:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int appInstallationMode2 = getAppInstallationMode(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeInt(appInstallationMode2);
                    return true;
                case 55:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationUninstallationMode = setApplicationUninstallationMode(contextInfo52, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationUninstallationMode);
                    return true;
                case 56:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToForceStopBlackList = addPackagesToForceStopBlackList(contextInfo53, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToForceStopBlackList);
                    return true;
                case 57:
                    String string35 = parcel.readString();
                    int i9 = parcel.readInt();
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    String string38 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationForceStopDisabled = isApplicationForceStopDisabled(string35, i9, string36, string37, string38, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationForceStopDisabled);
                    return true;
                case 58:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromWidgetWhiteList = getPackagesFromWidgetWhiteList(contextInfo54);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromWidgetWhiteList);
                    return true;
                case 59:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsWidgetAllowed = isWidgetAllowed(contextInfo55, string39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWidgetAllowed);
                    return true;
                case 60:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppNotificationBlackList = addAppNotificationBlackList(contextInfo56, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppNotificationBlackList);
                    return true;
                case 61:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAppNotificationBlackList = removeAppNotificationBlackList(contextInfo57, arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAppNotificationBlackList);
                    return true;
                case 62:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> appNotificationBlackList = getAppNotificationBlackList(contextInfo58, z12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(appNotificationBlackList);
                    return true;
                case 63:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppNotificationWhiteList = addAppNotificationWhiteList(contextInfo59, arrayListCreateStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppNotificationWhiteList);
                    return true;
                case 64:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAppNotificationWhiteList = removeAppNotificationWhiteList(contextInfo60, arrayListCreateStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAppNotificationWhiteList);
                    return true;
                case 65:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> appNotificationWhiteList = getAppNotificationWhiteList(contextInfo61, z13);
                    parcel2.writeNoException();
                    parcel2.writeStringList(appNotificationWhiteList);
                    return true;
                case 66:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationNotificationMode = setApplicationNotificationMode(contextInfo62, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationNotificationMode);
                    return true;
                case 67:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int applicationNotificationMode2 = getApplicationNotificationMode(contextInfo63, z14);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationNotificationMode2);
                    return true;
                case 68:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applicationNotificationModeAsUser = getApplicationNotificationModeAsUser(i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationNotificationModeAsUser);
                    return true;
                case 69:
                    String string40 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStatusBarNotificationAllowedAsUser = isStatusBarNotificationAllowedAsUser(string40, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStatusBarNotificationAllowedAsUser);
                    return true;
                case 70:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string41 = parcel.readString();
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddHomeShortcut = addHomeShortcut(contextInfo64, string41, string42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddHomeShortcut);
                    return true;
                case 71:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string43 = parcel.readString();
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteHomeShortcut = deleteHomeShortcut(contextInfo65, string43, string44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteHomeShortcut);
                    return true;
                case 72:
                    ContextInfo contextInfo66 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Map allWidgets = getAllWidgets(contextInfo66, string45);
                    parcel2.writeNoException();
                    parcel2.writeMap(allWidgets);
                    return true;
                case 73:
                    ContextInfo contextInfo67 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int applicationUninstallationMode2 = getApplicationUninstallationMode(contextInfo67);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationUninstallationMode2);
                    return true;
                case 74:
                    ContextInfo contextInfo68 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromForceStopBlackList = getPackagesFromForceStopBlackList(contextInfo68);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromForceStopBlackList);
                    return true;
                case 75:
                    ContextInfo contextInfo69 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToForceStopWhiteList = addPackagesToForceStopWhiteList(contextInfo69, arrayListCreateStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToForceStopWhiteList);
                    return true;
                case 76:
                    ContextInfo contextInfo70 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromForceStopWhiteList = getPackagesFromForceStopWhiteList(contextInfo70);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromForceStopWhiteList);
                    return true;
                case 77:
                    ContextInfo contextInfo71 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromWidgetBlackList = getPackagesFromWidgetBlackList(contextInfo71);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromWidgetBlackList);
                    return true;
                case 78:
                    ContextInfo contextInfo72 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToWidgetWhiteList = addPackagesToWidgetWhiteList(contextInfo72, arrayListCreateStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToWidgetWhiteList);
                    return true;
                case 79:
                    ContextInfo contextInfo73 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList9 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromWidgetWhiteList = removePackagesFromWidgetWhiteList(contextInfo73, arrayListCreateStringArrayList9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromWidgetWhiteList);
                    return true;
                case 80:
                    ContextInfo contextInfo74 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList10 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToWidgetBlackList = addPackagesToWidgetBlackList(contextInfo74, arrayListCreateStringArrayList10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToWidgetBlackList);
                    return true;
                case 81:
                    ContextInfo contextInfo75 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList11 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromWidgetBlackList = removePackagesFromWidgetBlackList(contextInfo75, arrayListCreateStringArrayList11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromWidgetBlackList);
                    return true;
                case 82:
                    ContextInfo contextInfo76 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList12 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromForceStopWhiteList = removePackagesFromForceStopWhiteList(contextInfo76, arrayListCreateStringArrayList12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromForceStopWhiteList);
                    return true;
                case 83:
                    ContextInfo contextInfo77 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList13 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromForceStopBlackList = removePackagesFromForceStopBlackList(contextInfo77, arrayListCreateStringArrayList13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromForceStopBlackList);
                    return true;
                case 84:
                    ContextInfo contextInfo78 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string46 = parcel.readString();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableRevocationCheck = enableRevocationCheck(contextInfo78, string46, z15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableRevocationCheck);
                    return true;
                case 85:
                    ContextInfo contextInfo79 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRevocationCheckEnabled = isRevocationCheckEnabled(contextInfo79, string47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRevocationCheckEnabled);
                    return true;
                case 86:
                    ContextInfo contextInfo80 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string48 = parcel.readString();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableOcspCheck = enableOcspCheck(contextInfo80, string48, z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableOcspCheck);
                    return true;
                case 87:
                    ContextInfo contextInfo81 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsOcspCheckEnabled = isOcspCheckEnabled(contextInfo81, string49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOcspCheckEnabled);
                    return true;
                case 88:
                    ContextInfo contextInfo82 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddAppSignatureToWhiteList = addAppSignatureToWhiteList(contextInfo82, string50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAppSignatureToWhiteList);
                    return true;
                case 89:
                    ContextInfo contextInfo83 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAppSignatureFromWhiteList = removeAppSignatureFromWhiteList(contextInfo83, string51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAppSignatureFromWhiteList);
                    return true;
                case 90:
                    ContextInfo contextInfo84 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] appSignaturesWhiteList = getAppSignaturesWhiteList(contextInfo84);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(appSignaturesWhiteList);
                    return true;
                case 91:
                    ContextInfo contextInfo85 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppControlInfo> appSignaturesAllWhiteLists = getAppSignaturesAllWhiteLists(contextInfo85);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(appSignaturesAllWhiteLists, 1);
                    return true;
                case 92:
                    ContextInfo contextInfo86 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageInApprovedInstallerWhiteList = isPackageInApprovedInstallerWhiteList(contextInfo86, string52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageInApprovedInstallerWhiteList);
                    return true;
                case 93:
                    ContextInfo contextInfo87 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList14 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToClearDataBlackList = addPackagesToClearDataBlackList(contextInfo87, arrayListCreateStringArrayList14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToClearDataBlackList);
                    return true;
                case 94:
                    ContextInfo contextInfo88 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList15 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromClearDataBlackList = removePackagesFromClearDataBlackList(contextInfo88, arrayListCreateStringArrayList15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromClearDataBlackList);
                    return true;
                case 95:
                    ContextInfo contextInfo89 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromClearDataBlackList = getPackagesFromClearDataBlackList(contextInfo89);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromClearDataBlackList);
                    return true;
                case 96:
                    ContextInfo contextInfo90 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList16 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToClearDataWhiteList = addPackagesToClearDataWhiteList(contextInfo90, arrayListCreateStringArrayList16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToClearDataWhiteList);
                    return true;
                case 97:
                    ContextInfo contextInfo91 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromClearDataWhiteList = getPackagesFromClearDataWhiteList(contextInfo91);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromClearDataWhiteList);
                    return true;
                case 98:
                    ContextInfo contextInfo92 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList17 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromClearDataWhiteList = removePackagesFromClearDataWhiteList(contextInfo92, arrayListCreateStringArrayList17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromClearDataWhiteList);
                    return true;
                case 99:
                    String string53 = parcel.readString();
                    int i13 = parcel.readInt();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationClearDataDisabled = isApplicationClearDataDisabled(string53, i13, z17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationClearDataDisabled);
                    return true;
                case 100:
                    ContextInfo contextInfo93 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList18 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToClearCacheBlackList = addPackagesToClearCacheBlackList(contextInfo93, arrayListCreateStringArrayList18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToClearCacheBlackList);
                    return true;
                case 101:
                    ContextInfo contextInfo94 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList19 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromClearCacheBlackList = removePackagesFromClearCacheBlackList(contextInfo94, arrayListCreateStringArrayList19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromClearCacheBlackList);
                    return true;
                case 102:
                    ContextInfo contextInfo95 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromClearCacheBlackList = getPackagesFromClearCacheBlackList(contextInfo95);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromClearCacheBlackList);
                    return true;
                case 103:
                    ContextInfo contextInfo96 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList20 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToClearCacheWhiteList = addPackagesToClearCacheWhiteList(contextInfo96, arrayListCreateStringArrayList20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToClearCacheWhiteList);
                    return true;
                case 104:
                    ContextInfo contextInfo97 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromClearCacheWhiteList = getPackagesFromClearCacheWhiteList(contextInfo97);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromClearCacheWhiteList);
                    return true;
                case 105:
                    ContextInfo contextInfo98 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList21 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromClearCacheWhiteList = removePackagesFromClearCacheWhiteList(contextInfo98, arrayListCreateStringArrayList21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromClearCacheWhiteList);
                    return true;
                case 106:
                    String string54 = parcel.readString();
                    int i14 = parcel.readInt();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationClearCacheDisabled = isApplicationClearCacheDisabled(string54, i14, z18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationClearCacheDisabled);
                    return true;
                case 107:
                    ContextInfo contextInfo99 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string55 = parcel.readString();
                    String string56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zChangeApplicationName = changeApplicationName(contextInfo99, string55, string56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeApplicationName);
                    return true;
                case 108:
                    String string57 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String applicationNameFromDb = getApplicationNameFromDb(string57, i15);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationNameFromDb);
                    return true;
                case 109:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAnyApplicationNameChangedAsUser = isAnyApplicationNameChangedAsUser(i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAnyApplicationNameChangedAsUser);
                    return true;
                case 110:
                    ContextInfo contextInfo100 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean applicationComponentState = setApplicationComponentState(contextInfo100, componentName, z19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationComponentState);
                    return true;
                case 111:
                    ContextInfo contextInfo101 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean applicationComponentState2 = getApplicationComponentState(contextInfo101, componentName2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationComponentState2);
                    return true;
                case 112:
                    ContextInfo contextInfo102 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList22 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToDisableUpdateBlackList = addPackagesToDisableUpdateBlackList(contextInfo102, arrayListCreateStringArrayList22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToDisableUpdateBlackList);
                    return true;
                case 113:
                    ContextInfo contextInfo103 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList23 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromDisableUpdateBlackList = removePackagesFromDisableUpdateBlackList(contextInfo103, arrayListCreateStringArrayList23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromDisableUpdateBlackList);
                    return true;
                case 114:
                    ContextInfo contextInfo104 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableUpdateBlackList = getPackagesFromDisableUpdateBlackList(contextInfo104);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableUpdateBlackList);
                    return true;
                case 115:
                    ContextInfo contextInfo105 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList24 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToDisableUpdateWhiteList = addPackagesToDisableUpdateWhiteList(contextInfo105, arrayListCreateStringArrayList24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToDisableUpdateWhiteList);
                    return true;
                case 116:
                    ContextInfo contextInfo106 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableUpdateWhiteList = getPackagesFromDisableUpdateWhiteList(contextInfo106);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableUpdateWhiteList);
                    return true;
                case 117:
                    ContextInfo contextInfo107 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList25 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromDisableUpdateWhiteList = removePackagesFromDisableUpdateWhiteList(contextInfo107, arrayListCreateStringArrayList25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromDisableUpdateWhiteList);
                    return true;
                case 118:
                    String string58 = parcel.readString();
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageUpdateAllowed = isPackageUpdateAllowed(string58, z20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageUpdateAllowed);
                    return true;
                case 119:
                    ContextInfo contextInfo108 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearDisableUpdateBlackList = clearDisableUpdateBlackList(contextInfo108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearDisableUpdateBlackList);
                    return true;
                case 120:
                    ContextInfo contextInfo109 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearDisableUpdateWhiteList = clearDisableUpdateWhiteList(contextInfo109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearDisableUpdateWhiteList);
                    return true;
                case 121:
                    ContextInfo contextInfo110 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList26 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<String> listAddPackagesToPreventStartBlackList = addPackagesToPreventStartBlackList(contextInfo110, arrayListCreateStringArrayList26);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listAddPackagesToPreventStartBlackList);
                    return true;
                case 122:
                    ContextInfo contextInfo111 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromPreventStartBlackList = getPackagesFromPreventStartBlackList(contextInfo111);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromPreventStartBlackList);
                    return true;
                case 123:
                    ContextInfo contextInfo112 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList27 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromPreventStartBlackList = removePackagesFromPreventStartBlackList(contextInfo112, arrayListCreateStringArrayList27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromPreventStartBlackList);
                    return true;
                case 124:
                    ContextInfo contextInfo113 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearPreventStartBlackList = clearPreventStartBlackList(contextInfo113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearPreventStartBlackList);
                    return true;
                case 125:
                    String string59 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationStartDisabledAsUser = isApplicationStartDisabledAsUser(string59, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationStartDisabledAsUser);
                    return true;
                case 126:
                    ContextInfo contextInfo114 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList28 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToDisableClipboardBlackList = addPackagesToDisableClipboardBlackList(contextInfo114, arrayListCreateStringArrayList28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToDisableClipboardBlackList);
                    return true;
                case 127:
                    ContextInfo contextInfo115 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList29 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromDisableClipboardBlackList = removePackagesFromDisableClipboardBlackList(contextInfo115, arrayListCreateStringArrayList29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromDisableClipboardBlackList);
                    return true;
                case 128:
                    ContextInfo contextInfo116 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableClipboardBlackList = getPackagesFromDisableClipboardBlackList(contextInfo116);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableClipboardBlackList);
                    return true;
                case 129:
                    ContextInfo contextInfo117 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableClipboardBlackListAsUserInternal = getPackagesFromDisableClipboardBlackListAsUserInternal(contextInfo117, i18);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableClipboardBlackListAsUserInternal);
                    return true;
                case 130:
                    ContextInfo contextInfo118 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList30 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToDisableClipboardWhiteList = addPackagesToDisableClipboardWhiteList(contextInfo118, arrayListCreateStringArrayList30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToDisableClipboardWhiteList);
                    return true;
                case 131:
                    ContextInfo contextInfo119 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList31 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromDisableClipboardWhiteList = removePackagesFromDisableClipboardWhiteList(contextInfo119, arrayListCreateStringArrayList31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromDisableClipboardWhiteList);
                    return true;
                case 132:
                    ContextInfo contextInfo120 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableClipboardWhiteList = getPackagesFromDisableClipboardWhiteList(contextInfo120);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableClipboardWhiteList);
                    return true;
                case 133:
                    ContextInfo contextInfo121 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableClipboardWhiteListAsUserInternal = getPackagesFromDisableClipboardWhiteListAsUserInternal(contextInfo121, i19);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableClipboardWhiteListAsUserInternal);
                    return true;
                case 134:
                    ContextInfo contextInfo122 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearDisableClipboardBlackList = clearDisableClipboardBlackList(contextInfo122);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearDisableClipboardBlackList);
                    return true;
                case 135:
                    ContextInfo contextInfo123 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearDisableClipboardWhiteList = clearDisableClipboardWhiteList(contextInfo123);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearDisableClipboardWhiteList);
                    return true;
                case 136:
                    String string60 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageClipboardAllowed = isPackageClipboardAllowed(string60, i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageClipboardAllowed);
                    return true;
                case 137:
                    ContextInfo contextInfo124 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList32 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToFocusMonitoringList = addPackagesToFocusMonitoringList(contextInfo124, arrayListCreateStringArrayList32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToFocusMonitoringList);
                    return true;
                case 138:
                    ContextInfo contextInfo125 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromFocusMonitoringList = getPackagesFromFocusMonitoringList(contextInfo125);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromFocusMonitoringList);
                    return true;
                case 139:
                    ContextInfo contextInfo126 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList33 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromFocusMonitoringList = removePackagesFromFocusMonitoringList(contextInfo126, arrayListCreateStringArrayList33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromFocusMonitoringList);
                    return true;
                case 140:
                    ContextInfo contextInfo127 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearFocusMonitoringList = clearFocusMonitoringList(contextInfo127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearFocusMonitoringList);
                    return true;
                case 141:
                    String string61 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationFocusMonitoredAsUser = isApplicationFocusMonitoredAsUser(string61, i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationFocusMonitoredAsUser);
                    return true;
                case 142:
                    ContextInfo contextInfo128 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string62 = parcel.readString();
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> homeShortcuts = getHomeShortcuts(contextInfo128, string62, z21);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(homeShortcuts, 1);
                    return true;
                case 143:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateWidgetStatus(componentName3, i22);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    ContextInfo contextInfo129 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<UsbDeviceConfig> usbDevicesForDefaultAccess = getUsbDevicesForDefaultAccess(contextInfo129, string63);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(usbDevicesForDefaultAccess, 1);
                    return true;
                case 145:
                    int i23 = parcel.readInt();
                    UsbDevice usbDevice = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String string64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbDevicePermittedForPackage = isUsbDevicePermittedForPackage(i23, usbDevice, string64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbDevicePermittedForPackage);
                    return true;
                case 146:
                    ContextInfo contextInfo130 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string65 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(UsbDeviceConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddUsbDevicesForDefaultAccess = addUsbDevicesForDefaultAccess(contextInfo130, string65, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddUsbDevicesForDefaultAccess);
                    return true;
                case 147:
                    ContextInfo contextInfo131 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearUsbDevicesForDefaultAccess = clearUsbDevicesForDefaultAccess(contextInfo131, string66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearUsbDevicesForDefaultAccess);
                    return true;
                case 148:
                    ContextInfo contextInfo132 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string67 = parcel.readString();
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zInstallExistingApplication = installExistingApplication(contextInfo132, string67, z22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInstallExistingApplication);
                    return true;
                case 149:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateApplicationTable = updateApplicationTable(i24, i25, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateApplicationTable);
                    return true;
                case 150:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> disabledPackages = getDisabledPackages(i27);
                    parcel2.writeNoException();
                    parcel2.writeStringList(disabledPackages);
                    return true;
                case 151:
                    boolean addHomeShorcutRequested = getAddHomeShorcutRequested();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addHomeShorcutRequested);
                    return true;
                case 152:
                    AppIdentity appIdentity = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList34 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int authorizedScopes = setAuthorizedScopes(appIdentity, arrayListCreateStringArrayList34);
                    parcel2.writeNoException();
                    parcel2.writeInt(authorizedScopes);
                    return true;
                case 153:
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> authorizedScopes2 = getAuthorizedScopes(string68);
                    parcel2.writeNoException();
                    parcel2.writeStringList(authorizedScopes2);
                    return true;
                case 154:
                    ContextInfo contextInfo133 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity2 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList35 = parcel.createStringArrayList();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iApplyRuntimePermissions = applyRuntimePermissions(contextInfo133, appIdentity2, arrayListCreateStringArrayList35, i28);
                    parcel2.writeNoException();
                    parcel2.writeInt(iApplyRuntimePermissions);
                    return true;
                case 155:
                    int i29 = parcel.readInt();
                    String string69 = parcel.readString();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> runtimePermissionsEnforced = getRuntimePermissionsEnforced(i29, string69, i30);
                    parcel2.writeNoException();
                    parcel2.writeStringList(runtimePermissionsEnforced);
                    return true;
                case 156:
                    String string70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zVerifyRuntimePermissionPackageSignature = verifyRuntimePermissionPackageSignature(string70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zVerifyRuntimePermissionPackageSignature);
                    return true;
                case 157:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reapplyRuntimePermissions(i31);
                    parcel2.writeNoException();
                    return true;
                case 158:
                    ContextInfo contextInfo134 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string71 = parcel.readString();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> runtimePermissions = getRuntimePermissions(contextInfo134, string71, i32);
                    parcel2.writeNoException();
                    parcel2.writeStringList(runtimePermissions);
                    return true;
                case 159:
                    ContextInfo contextInfo135 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity3 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPackageToBatteryOptimizationWhiteList = addPackageToBatteryOptimizationWhiteList(contextInfo135, appIdentity3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackageToBatteryOptimizationWhiteList);
                    return true;
                case 160:
                    ContextInfo contextInfo136 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity4 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemovePackageFromBatteryOptimizationWhiteList = removePackageFromBatteryOptimizationWhiteList(contextInfo136, appIdentity4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackageFromBatteryOptimizationWhiteList);
                    return true;
                case 161:
                    ContextInfo contextInfo137 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromBatteryOptimizationWhiteList = getPackagesFromBatteryOptimizationWhiteList(contextInfo137);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromBatteryOptimizationWhiteList);
                    return true;
                case 162:
                    List<String> allPackagesFromBatteryOptimizationWhiteList = getAllPackagesFromBatteryOptimizationWhiteList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allPackagesFromBatteryOptimizationWhiteList);
                    return true;
                case 163:
                    ContextInfo contextInfo138 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> applicationGrantedPermissions = getApplicationGrantedPermissions(contextInfo138, string72);
                    parcel2.writeNoException();
                    parcel2.writeStringList(applicationGrantedPermissions);
                    return true;
                case 164:
                    ContextInfo contextInfo139 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAndroidMarketState(contextInfo139, z23);
                    parcel2.writeNoException();
                    return true;
                case 165:
                    ContextInfo contextInfo140 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageSignaturesFromExternalStorageWhiteList = getPackageSignaturesFromExternalStorageWhiteList(contextInfo140, string73);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageSignaturesFromExternalStorageWhiteList);
                    return true;
                case 166:
                    ContextInfo contextInfo141 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iClearPackagesFromExternalStorageWhiteList = clearPackagesFromExternalStorageWhiteList(contextInfo141);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearPackagesFromExternalStorageWhiteList);
                    return true;
                case 167:
                    ContextInfo contextInfo142 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i33 = parcel.readInt();
                    AppIdentity appIdentity5 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPackageToWhiteList = addPackageToWhiteList(contextInfo142, i33, appIdentity5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackageToWhiteList);
                    return true;
                case 168:
                    ContextInfo contextInfo143 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromWhiteList = getPackagesFromWhiteList(contextInfo143, i34);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromWhiteList);
                    return true;
                case 169:
                    ContextInfo contextInfo144 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i35 = parcel.readInt();
                    String string74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemovePackageFromWhiteList = removePackageFromWhiteList(contextInfo144, i35, string74);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackageFromWhiteList);
                    return true;
                case 170:
                    ContextInfo contextInfo145 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i36 = parcel.readInt();
                    AppIdentity appIdentity6 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPackageToBlackList = addPackageToBlackList(contextInfo145, i36, appIdentity6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackageToBlackList);
                    return true;
                case 171:
                    ContextInfo contextInfo146 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromBlackList = getPackagesFromBlackList(contextInfo146, i37);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromBlackList);
                    return true;
                case 172:
                    ContextInfo contextInfo147 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i38 = parcel.readInt();
                    String string75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemovePackageFromBlackList = removePackageFromBlackList(contextInfo147, i38, string75);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackageFromBlackList);
                    return true;
                case 173:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageInWhitelistInternal = isPackageInWhitelistInternal(i39, i40, i41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageInWhitelistInternal);
                    return true;
                case 174:
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageInBlacklistInternal = isPackageInBlacklistInternal(i42, i43, i44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageInBlacklistInternal);
                    return true;
                case 175:
                    int i45 = parcel.readInt();
                    String string76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationExternalStorageWhitelisted = isApplicationExternalStorageWhitelisted(i45, string76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationExternalStorageWhitelisted);
                    return true;
                case 176:
                    int i46 = parcel.readInt();
                    String string77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationExternalStorageBlacklisted = isApplicationExternalStorageBlacklisted(i46, string77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationExternalStorageBlacklisted);
                    return true;
                case 177:
                    ContextInfo contextInfo148 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteManagedAppInfo = deleteManagedAppInfo(contextInfo148, string78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteManagedAppInfo);
                    return true;
                case 178:
                    ContextInfo contextInfo149 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean appInstallToSdCard = setAppInstallToSdCard(contextInfo149, z24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appInstallToSdCard);
                    return true;
                case 179:
                    ContextInfo contextInfo150 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean appInstallToSdCard2 = getAppInstallToSdCard(contextInfo150);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appInstallToSdCard2);
                    return true;
                case 180:
                    ContextInfo contextInfo151 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] installedManagedApplicationsList = getInstalledManagedApplicationsList(contextInfo151);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(installedManagedApplicationsList);
                    return true;
                case 181:
                    ContextInfo contextInfo152 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ManagedAppInfo[] applicationsList = getApplicationsList(contextInfo152, string79);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(applicationsList, 1);
                    return true;
                case 182:
                    ContextInfo contextInfo153 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean asManagedApp = setAsManagedApp(contextInfo153, string80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(asManagedApp);
                    return true;
                case 183:
                    ContextInfo contextInfo154 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity7 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddApplicationToCameraAllowList = addApplicationToCameraAllowList(contextInfo154, appIdentity7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddApplicationToCameraAllowList);
                    return true;
                case 184:
                    ContextInfo contextInfo155 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> applicationPackagesFromCameraAllowList = getApplicationPackagesFromCameraAllowList(contextInfo155);
                    parcel2.writeNoException();
                    parcel2.writeStringList(applicationPackagesFromCameraAllowList);
                    return true;
                case 185:
                    ContextInfo contextInfo156 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity8 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemoveApplicationFromCameraAllowList = removeApplicationFromCameraAllowList(contextInfo156, appIdentity8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveApplicationFromCameraAllowList);
                    return true;
                case 186:
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCameraAllowlistedApp = isCameraAllowlistedApp(i47, i48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCameraAllowlistedApp);
                    return true;
                case 187:
                    String string81 = parcel.readString();
                    int i49 = parcel.readInt();
                    Notification notification2 = (Notification) parcel.readTypedObject(Notification.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zHandleStatusBarNotificationNotAllowedAsUser = handleStatusBarNotificationNotAllowedAsUser(string81, i49, notification2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHandleStatusBarNotificationNotAllowedAsUser);
                    return true;
                case 188:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DefaultAppConfiguration> allDefaultApplicationsInternal = getAllDefaultApplicationsInternal(i50);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allDefaultApplicationsInternal, 1);
                    return true;
                case 189:
                    String string82 = parcel.readString();
                    boolean z25 = parcel.readBoolean();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationStateEnabledAsUser = getApplicationStateEnabledAsUser(string82, z25, i51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationStateEnabledAsUser);
                    return true;
                case 190:
                    String string83 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList36 = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList37 = parcel.createStringArrayList();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationInstallationEnabled = isApplicationInstallationEnabled(string83, arrayListCreateStringArrayList36, arrayListCreateStringArrayList37, i52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationInstallationEnabled);
                    return true;
                case 191:
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsFromApprovedInstaller = isFromApprovedInstaller(i53, i54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFromApprovedInstaller);
                    return true;
                case 192:
                    String string84 = parcel.readString();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationUninstallationEnabledAsUser = getApplicationUninstallationEnabledAsUser(string84, i55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationUninstallationEnabledAsUser);
                    return true;
                case 193:
                    String string85 = parcel.readString();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applicationUsageAppLaunchCount(string85, i56);
                    parcel2.writeNoException();
                    return true;
                case 194:
                    String string86 = parcel.readString();
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applicationUsageAppPauseTime(string86, i57);
                    parcel2.writeNoException();
                    return true;
                case 195:
                    ContextInfo contextInfo157 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean defaultApplication = setDefaultApplication(contextInfo157, intent2, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultApplication);
                    return true;
                case 196:
                    ContextInfo contextInfo158 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    ComponentName defaultApplication2 = getDefaultApplication(contextInfo158, intent3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultApplication2, 1);
                    return true;
                case 197:
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName defaultApplicationInternal = getDefaultApplicationInternal(intent4, i58);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultApplicationInternal, 1);
                    return true;
                case 198:
                    ContextInfo contextInfo159 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<DefaultAppConfiguration> allDefaultApplications = getAllDefaultApplications(contextInfo159);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allDefaultApplications, 1);
                    return true;
                case 199:
                    ContextInfo contextInfo160 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveDefaultApplication = removeDefaultApplication(contextInfo160, intent5, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveDefaultApplication);
                    return true;
                case 200:
                    String string87 = parcel.readString();
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsApplicationSetToDefault = isApplicationSetToDefault(string87, i59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplicationSetToDefault);
                    return true;
                case 201:
                    Intent intent6 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    IntentFilter intentFilterCreateIntentFilter = createIntentFilter(intent6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentFilterCreateIntentFilter, 1);
                    return true;
                case 202:
                    ContextInfo contextInfo161 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList38 = parcel.createStringArrayList();
                    boolean z26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean concentrationMode = setConcentrationMode(contextInfo161, arrayListCreateStringArrayList38, z26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(concentrationMode);
                    return true;
                case 203:
                    boolean concentrationMode2 = getConcentrationMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(concentrationMode2);
                    return true;
                case 204:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string88 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplicationRestrictions(componentName6, string88, bundle, i60);
                    parcel2.writeNoException();
                    return true;
                case 205:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string89 = parcel.readString();
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictions = getApplicationRestrictions(componentName7, string89, i61);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 206:
                    ContextInfo contextInfo162 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    doSelfUninstall(contextInfo162);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
