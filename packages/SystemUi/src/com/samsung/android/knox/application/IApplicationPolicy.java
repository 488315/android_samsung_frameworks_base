package com.samsung.android.knox.application;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IApplicationPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.application.IApplicationPolicy";

    boolean addAppNotificationBlackList(ContextInfo contextInfo, List<String> list);

    boolean addAppNotificationWhiteList(ContextInfo contextInfo, List<String> list);

    boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str);

    boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str);

    boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str);

    boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str);

    boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str);

    int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity);

    boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2);

    int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity);

    int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity);

    int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity);

    boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List<String> list);

    List<String> addPackagesToPreventStartBlackList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List<String> list);

    boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List<String> list);

    boolean addUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str, List<UsbDeviceConfig> list);

    void applicationUsageAppLaunchCount(String str, int i);

    void applicationUsageAppPauseTime(String str, int i);

    int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List<String> list, int i);

    boolean changeApplicationIcon(ContextInfo contextInfo, String str, byte[] bArr);

    boolean changeApplicationName(ContextInfo contextInfo, String str, String str2);

    boolean clearDisableClipboardBlackList(ContextInfo contextInfo);

    boolean clearDisableClipboardWhiteList(ContextInfo contextInfo);

    boolean clearDisableUpdateBlackList(ContextInfo contextInfo);

    boolean clearDisableUpdateWhiteList(ContextInfo contextInfo);

    boolean clearFocusMonitoringList(ContextInfo contextInfo);

    int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo);

    boolean clearPreventStartBlackList(ContextInfo contextInfo);

    boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str);

    IntentFilter createIntentFilter(Intent intent);

    boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2);

    boolean deleteManagedAppInfo(ContextInfo contextInfo, String str);

    void doSelfUninstall(ContextInfo contextInfo);

    boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z);

    boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z);

    boolean getAddHomeShorcutRequested();

    AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo);

    List<DefaultAppConfiguration> getAllDefaultApplications(ContextInfo contextInfo);

    List<DefaultAppConfiguration> getAllDefaultApplicationsInternal(int i);

    List<String> getAllPackagesFromBatteryOptimizationWhiteList();

    Map getAllWidgets(ContextInfo contextInfo, String str);

    boolean getAppInstallToSdCard(ContextInfo contextInfo);

    int getAppInstallationMode(ContextInfo contextInfo);

    List<String> getAppNotificationBlackList(ContextInfo contextInfo, boolean z);

    List<String> getAppNotificationWhiteList(ContextInfo contextInfo, boolean z);

    List<AppControlInfo> getAppPackageNamesAllBlackLists(ContextInfo contextInfo);

    List<AppControlInfo> getAppPackageNamesAllWhiteLists(ContextInfo contextInfo);

    List<AppControlInfo> getAppPermissionsAllBlackLists(ContextInfo contextInfo);

    String[] getAppPermissionsBlackList(ContextInfo contextInfo);

    String[] getAppSignatureBlackList(ContextInfo contextInfo);

    List<AppControlInfo> getAppSignaturesAllBlackLists(ContextInfo contextInfo);

    List<AppControlInfo> getAppSignaturesAllWhiteLists(ContextInfo contextInfo);

    String[] getAppSignaturesWhiteList(ContextInfo contextInfo);

    long getApplicationCacheSize(ContextInfo contextInfo, String str);

    long getApplicationCodeSize(ContextInfo contextInfo, String str);

    boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName);

    long getApplicationCpuUsage(ContextInfo contextInfo, String str);

    long getApplicationDataSize(ContextInfo contextInfo, String str);

    List<String> getApplicationGrantedPermissions(ContextInfo contextInfo, String str);

    byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str);

    boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str);

    long getApplicationMemoryUsage(ContextInfo contextInfo, String str);

    String getApplicationName(ContextInfo contextInfo, String str);

    String getApplicationNameFromDb(String str, int i);

    int getApplicationNotificationMode(ContextInfo contextInfo, boolean z);

    int getApplicationNotificationModeAsUser(int i);

    List<String> getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo);

    boolean getApplicationStateEnabled(ContextInfo contextInfo, String str);

    boolean getApplicationStateEnabledAsUser(String str, boolean z, int i);

    String[] getApplicationStateList(ContextInfo contextInfo, boolean z);

    long getApplicationTotalSize(ContextInfo contextInfo, String str);

    int getApplicationUid(ContextInfo contextInfo, String str);

    boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str);

    boolean getApplicationUninstallationEnabledAsUser(String str, int i);

    int getApplicationUninstallationMode(ContextInfo contextInfo);

    String getApplicationVersion(ContextInfo contextInfo, String str);

    int getApplicationVersionCode(ContextInfo contextInfo, String str);

    ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str);

    AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo);

    boolean getConcentrationMode();

    ComponentName getDefaultApplication(ContextInfo contextInfo, Intent intent);

    ComponentName getDefaultApplicationInternal(Intent intent, int i);

    List<String> getDisabledPackages(int i);

    List<ComponentName> getHomeShortcuts(ContextInfo contextInfo, String str, boolean z);

    String[] getInstalledApplicationsIDList(ContextInfo contextInfo);

    String[] getInstalledManagedApplicationsList(ContextInfo contextInfo);

    List<NetworkStats> getNetworkStats(ContextInfo contextInfo);

    Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str);

    List<String> getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo);

    List<String> getPackagesFromBlackList(ContextInfo contextInfo, int i);

    List<String> getPackagesFromClearCacheBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromClearCacheWhiteList(ContextInfo contextInfo);

    List<String> getPackagesFromClearDataBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromClearDataWhiteList(ContextInfo contextInfo);

    List<String> getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i);

    List<String> getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo);

    List<String> getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i);

    List<String> getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo);

    List<String> getPackagesFromFocusMonitoringList(ContextInfo contextInfo);

    List<String> getPackagesFromForceStopBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromForceStopWhiteList(ContextInfo contextInfo);

    List<String> getPackagesFromPreventStartBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromWhiteList(ContextInfo contextInfo, int i);

    List<String> getPackagesFromWidgetBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromWidgetWhiteList(ContextInfo contextInfo);

    List<String> getRuntimePermissions(ContextInfo contextInfo, String str, int i);

    List<String> getRuntimePermissionsEnforced(int i, String str, int i2);

    List<AppInfo> getTopNCPUUsageApp(ContextInfo contextInfo, int i, boolean z);

    List<AppInfo> getTopNDataUsageApp(ContextInfo contextInfo, int i);

    List<AppInfo> getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z);

    List<UsbDeviceConfig> getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str);

    boolean handleStatusBarNotificationNotAllowedAsUser(String str, int i, Notification notification2);

    boolean installApplication(ContextInfo contextInfo, String str, boolean z, ParcelFileDescriptor parcelFileDescriptor);

    boolean installExistingApplication(ContextInfo contextInfo, String str);

    boolean isAnyApplicationIconChangedAsUser(int i);

    boolean isAnyApplicationNameChangedAsUser(int i);

    boolean isApplicationClearCacheDisabled(String str, int i, boolean z);

    boolean isApplicationClearDataDisabled(String str, int i, boolean z);

    boolean isApplicationExternalStorageBlacklisted(int i, String str);

    boolean isApplicationExternalStorageWhitelisted(int i, String str);

    boolean isApplicationFocusMonitoredAsUser(String str, int i);

    boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z);

    boolean isApplicationInstallationEnabled(String str, Signature[] signatureArr, List<String> list, int i);

    boolean isApplicationInstalled(ContextInfo contextInfo, String str);

    boolean isApplicationRunning(ContextInfo contextInfo, String str);

    boolean isApplicationSetToDefault(String str, int i);

    boolean isApplicationStartDisabledAsUser(String str, int i);

    boolean isCameraAllowlistedApp(int i, int i2);

    boolean isFromApprovedInstaller(int i, int i2);

    boolean isIntentDisabled(Intent intent);

    boolean isOcspCheckEnabled(ContextInfo contextInfo, String str);

    boolean isPackageClipboardAllowed(String str, int i);

    boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str);

    boolean isPackageInBlacklistInternal(int i, int i2, int i3);

    boolean isPackageInWhitelistInternal(int i, int i2, int i3);

    boolean isPackageUpdateAllowed(String str, boolean z);

    boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str);

    boolean isStatusBarNotificationAllowedAsUser(String str, int i);

    boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str);

    boolean isWidgetAllowed(ContextInfo contextInfo, String str);

    void reapplyRuntimePermissions(int i);

    boolean removeAppNotificationBlackList(ContextInfo contextInfo, List<String> list);

    boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List<String> list);

    boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str);

    boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str);

    boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str);

    boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str);

    boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str);

    int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity);

    boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName);

    List<String> removeManagedApplications(ContextInfo contextInfo, List<String> list);

    int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity);

    int removePackageFromBlackList(ContextInfo contextInfo, int i, String str);

    int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str);

    boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List<String> list);

    boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List<String> list);

    void setAndroidMarketState(ContextInfo contextInfo, boolean z);

    boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z);

    boolean setAppInstallationMode(ContextInfo contextInfo, int i);

    boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z);

    void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z);

    boolean setApplicationNotificationMode(ContextInfo contextInfo, int i);

    boolean setApplicationState(ContextInfo contextInfo, String str, boolean z);

    String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z);

    void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z);

    boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i);

    boolean setAsManagedApp(ContextInfo contextInfo, String str);

    boolean setConcentrationMode(ContextInfo contextInfo, List<String> list, boolean z);

    boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName);

    boolean startApp(ContextInfo contextInfo, String str, String str2);

    boolean stopApp(ContextInfo contextInfo, String str);

    boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z);

    boolean updateApplicationTable(int i, int i2, int i3);

    void updateDataUsageDb();

    void updateWidgetStatus(ComponentName componentName, int i);

    boolean verifyRuntimePermissionPackageSignature(String str);

    boolean wipeApplicationData(ContextInfo contextInfo, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IApplicationPolicy {
        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addAppNotificationBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> addPackagesToPreventStartBlackList(ContextInfo contextInfo, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean addUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str, List<UsbDeviceConfig> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List<String> list, int i) {
            return 0;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean changeApplicationIcon(ContextInfo contextInfo, String str, byte[] bArr) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean changeApplicationName(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean clearDisableClipboardBlackList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean clearDisableClipboardWhiteList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean clearDisableUpdateBlackList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean clearDisableUpdateWhiteList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean clearFocusMonitoringList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean clearPreventStartBlackList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final IntentFilter createIntentFilter(Intent intent) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean deleteManagedAppInfo(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getAddHomeShorcutRequested() {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<DefaultAppConfiguration> getAllDefaultApplications(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<DefaultAppConfiguration> getAllDefaultApplicationsInternal(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getAllPackagesFromBatteryOptimizationWhiteList() {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final Map getAllWidgets(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getAppInstallToSdCard(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int getAppInstallationMode(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getAppNotificationBlackList(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getAppNotificationWhiteList(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppControlInfo> getAppPackageNamesAllBlackLists(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppControlInfo> getAppPackageNamesAllWhiteLists(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppControlInfo> getAppPermissionsAllBlackLists(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String[] getAppPermissionsBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String[] getAppSignatureBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppControlInfo> getAppSignaturesAllBlackLists(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppControlInfo> getAppSignaturesAllWhiteLists(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String[] getAppSignaturesWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final long getApplicationCacheSize(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final long getApplicationCodeSize(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final long getApplicationCpuUsage(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final long getApplicationDataSize(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getApplicationGrantedPermissions(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final long getApplicationMemoryUsage(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String getApplicationName(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String getApplicationNameFromDb(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int getApplicationNotificationMode(ContextInfo contextInfo, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int getApplicationNotificationModeAsUser(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getApplicationStateEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String[] getApplicationStateList(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final long getApplicationTotalSize(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int getApplicationUid(ContextInfo contextInfo, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getApplicationUninstallationEnabledAsUser(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int getApplicationUninstallationMode(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String getApplicationVersion(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int getApplicationVersionCode(ContextInfo contextInfo, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean getConcentrationMode() {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final ComponentName getDefaultApplication(ContextInfo contextInfo, Intent intent) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final ComponentName getDefaultApplicationInternal(Intent intent, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getDisabledPackages(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<ComponentName> getHomeShortcuts(ContextInfo contextInfo, String str, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String[] getInstalledApplicationsIDList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String[] getInstalledManagedApplicationsList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<NetworkStats> getNetworkStats(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromBlackList(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromClearCacheBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromClearCacheWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromClearDataBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromClearDataWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromFocusMonitoringList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromForceStopBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromForceStopWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromPreventStartBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromWhiteList(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromWidgetBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getPackagesFromWidgetWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getRuntimePermissions(ContextInfo contextInfo, String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> getRuntimePermissionsEnforced(int i, String str, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppInfo> getTopNCPUUsageApp(ContextInfo contextInfo, int i, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppInfo> getTopNDataUsageApp(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<AppInfo> getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<UsbDeviceConfig> getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean handleStatusBarNotificationNotAllowedAsUser(String str, int i, Notification notification2) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean installApplication(ContextInfo contextInfo, String str, boolean z, ParcelFileDescriptor parcelFileDescriptor) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean installExistingApplication(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isAnyApplicationIconChangedAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isAnyApplicationNameChangedAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationClearCacheDisabled(String str, int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationClearDataDisabled(String str, int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationExternalStorageBlacklisted(int i, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationExternalStorageWhitelisted(int i, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationFocusMonitoredAsUser(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationInstallationEnabled(String str, Signature[] signatureArr, List<String> list, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationInstalled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationRunning(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationSetToDefault(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isApplicationStartDisabledAsUser(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isCameraAllowlistedApp(int i, int i2) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isFromApprovedInstaller(int i, int i2) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isIntentDisabled(Intent intent) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isOcspCheckEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isPackageClipboardAllowed(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isPackageInBlacklistInternal(int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isPackageInWhitelistInternal(int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isPackageUpdateAllowed(String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isStatusBarNotificationAllowedAsUser(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean isWidgetAllowed(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeAppNotificationBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final List<String> removeManagedApplications(ContextInfo contextInfo, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int removePackageFromBlackList(ContextInfo contextInfo, int i, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setAppInstallationMode(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setApplicationNotificationMode(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setApplicationState(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setAsManagedApp(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setConcentrationMode(ContextInfo contextInfo, List<String> list, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean startApp(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean stopApp(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean updateApplicationTable(int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean verifyRuntimePermissionPackageSignature(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final boolean wipeApplicationData(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void updateDataUsageDb() {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void doSelfUninstall(ContextInfo contextInfo) {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void reapplyRuntimePermissions(int i) {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void applicationUsageAppLaunchCount(String str, int i) {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void applicationUsageAppPauseTime(String str, int i) {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void setAndroidMarketState(ContextInfo contextInfo, boolean z) {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void updateWidgetStatus(ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        }

        @Override // com.samsung.android.knox.application.IApplicationPolicy
        public final void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IApplicationPolicy {
        public static final int TRANSACTION_addAppNotificationBlackList = 60;
        public static final int TRANSACTION_addAppNotificationWhiteList = 63;
        public static final int TRANSACTION_addAppPackageNameToBlackList = 45;
        public static final int TRANSACTION_addAppPackageNameToWhiteList = 48;
        public static final int TRANSACTION_addAppPermissionToBlackList = 34;
        public static final int TRANSACTION_addAppSignatureToBlackList = 37;
        public static final int TRANSACTION_addAppSignatureToWhiteList = 88;
        public static final int TRANSACTION_addApplicationToCameraAllowList = 181;
        public static final int TRANSACTION_addHomeShortcut = 70;
        public static final int TRANSACTION_addPackageToBatteryOptimizationWhiteList = 157;
        public static final int TRANSACTION_addPackageToBlackList = 168;
        public static final int TRANSACTION_addPackageToWhiteList = 165;
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
        public static final int TRANSACTION_applicationUsageAppLaunchCount = 191;
        public static final int TRANSACTION_applicationUsageAppPauseTime = 192;
        public static final int TRANSACTION_applyRuntimePermissions = 152;
        public static final int TRANSACTION_changeApplicationIcon = 31;
        public static final int TRANSACTION_changeApplicationName = 107;
        public static final int TRANSACTION_clearDisableClipboardBlackList = 134;
        public static final int TRANSACTION_clearDisableClipboardWhiteList = 135;
        public static final int TRANSACTION_clearDisableUpdateBlackList = 119;
        public static final int TRANSACTION_clearDisableUpdateWhiteList = 120;
        public static final int TRANSACTION_clearFocusMonitoringList = 140;
        public static final int TRANSACTION_clearPackagesFromExternalStorageWhiteList = 164;
        public static final int TRANSACTION_clearPreventStartBlackList = 124;
        public static final int TRANSACTION_clearUsbDevicesForDefaultAccess = 147;
        public static final int TRANSACTION_createIntentFilter = 199;
        public static final int TRANSACTION_deleteHomeShortcut = 71;
        public static final int TRANSACTION_deleteManagedAppInfo = 175;
        public static final int TRANSACTION_doSelfUninstall = 202;
        public static final int TRANSACTION_enableOcspCheck = 86;
        public static final int TRANSACTION_enableRevocationCheck = 84;
        public static final int TRANSACTION_getAddHomeShorcutRequested = 151;
        public static final int TRANSACTION_getAllAppLastUsage = 28;
        public static final int TRANSACTION_getAllDefaultApplications = 196;
        public static final int TRANSACTION_getAllDefaultApplicationsInternal = 186;
        public static final int TRANSACTION_getAllPackagesFromBatteryOptimizationWhiteList = 160;
        public static final int TRANSACTION_getAllWidgets = 72;
        public static final int TRANSACTION_getAppInstallToSdCard = 177;
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
        public static final int TRANSACTION_getApplicationGrantedPermissions = 161;
        public static final int TRANSACTION_getApplicationIconFromDb = 32;
        public static final int TRANSACTION_getApplicationInstallationEnabled = 11;
        public static final int TRANSACTION_getApplicationMemoryUsage = 22;
        public static final int TRANSACTION_getApplicationName = 14;
        public static final int TRANSACTION_getApplicationNameFromDb = 108;
        public static final int TRANSACTION_getApplicationNotificationMode = 67;
        public static final int TRANSACTION_getApplicationNotificationModeAsUser = 68;
        public static final int TRANSACTION_getApplicationPackagesFromCameraAllowList = 182;
        public static final int TRANSACTION_getApplicationStateEnabled = 10;
        public static final int TRANSACTION_getApplicationStateEnabledAsUser = 187;
        public static final int TRANSACTION_getApplicationStateList = 42;
        public static final int TRANSACTION_getApplicationTotalSize = 18;
        public static final int TRANSACTION_getApplicationUid = 15;
        public static final int TRANSACTION_getApplicationUninstallationEnabled = 12;
        public static final int TRANSACTION_getApplicationUninstallationEnabledAsUser = 190;
        public static final int TRANSACTION_getApplicationUninstallationMode = 73;
        public static final int TRANSACTION_getApplicationVersion = 16;
        public static final int TRANSACTION_getApplicationVersionCode = 17;
        public static final int TRANSACTION_getApplicationsList = 179;
        public static final int TRANSACTION_getAvgNoAppUsagePerMonth = 27;
        public static final int TRANSACTION_getConcentrationMode = 201;
        public static final int TRANSACTION_getDefaultApplication = 194;
        public static final int TRANSACTION_getDefaultApplicationInternal = 195;
        public static final int TRANSACTION_getDisabledPackages = 150;
        public static final int TRANSACTION_getHomeShortcuts = 142;
        public static final int TRANSACTION_getInstalledApplicationsIDList = 13;
        public static final int TRANSACTION_getInstalledManagedApplicationsList = 178;
        public static final int TRANSACTION_getNetworkStats = 29;
        public static final int TRANSACTION_getPackageSignaturesFromExternalStorageWhiteList = 163;
        public static final int TRANSACTION_getPackagesFromBatteryOptimizationWhiteList = 159;
        public static final int TRANSACTION_getPackagesFromBlackList = 169;
        public static final int TRANSACTION_getPackagesFromClearCacheBlackList = 102;
        public static final int TRANSACTION_getPackagesFromClearCacheWhiteList = 104;
        public static final int TRANSACTION_getPackagesFromClearDataBlackList = 95;
        public static final int TRANSACTION_getPackagesFromClearDataWhiteList = 97;
        public static final int TRANSACTION_getPackagesFromDisableClipboardBlackList = 128;

        /* renamed from: TRANSACTION_getPackagesFromDisableClipboardBlackListAsUserInternal */
        public static final int f478x69ac7d5d = 129;
        public static final int TRANSACTION_getPackagesFromDisableClipboardWhiteList = 132;

        /* renamed from: TRANSACTION_getPackagesFromDisableClipboardWhiteListAsUserInternal */
        public static final int f479x5a2fc307 = 133;
        public static final int TRANSACTION_getPackagesFromDisableUpdateBlackList = 114;
        public static final int TRANSACTION_getPackagesFromDisableUpdateWhiteList = 116;
        public static final int TRANSACTION_getPackagesFromFocusMonitoringList = 138;
        public static final int TRANSACTION_getPackagesFromForceStopBlackList = 74;
        public static final int TRANSACTION_getPackagesFromForceStopWhiteList = 76;
        public static final int TRANSACTION_getPackagesFromPreventStartBlackList = 122;
        public static final int TRANSACTION_getPackagesFromWhiteList = 166;
        public static final int TRANSACTION_getPackagesFromWidgetBlackList = 77;
        public static final int TRANSACTION_getPackagesFromWidgetWhiteList = 58;
        public static final int TRANSACTION_getRuntimePermissions = 156;
        public static final int TRANSACTION_getRuntimePermissionsEnforced = 153;
        public static final int TRANSACTION_getTopNCPUUsageApp = 26;
        public static final int TRANSACTION_getTopNDataUsageApp = 25;
        public static final int TRANSACTION_getTopNMemoryUsageApp = 24;
        public static final int TRANSACTION_getUsbDevicesForDefaultAccess = 144;
        public static final int TRANSACTION_handleStatusBarNotificationNotAllowedAsUser = 185;
        public static final int TRANSACTION_installApplication = 5;
        public static final int TRANSACTION_installExistingApplication = 148;
        public static final int TRANSACTION_isAnyApplicationIconChangedAsUser = 33;
        public static final int TRANSACTION_isAnyApplicationNameChangedAsUser = 109;
        public static final int TRANSACTION_isApplicationClearCacheDisabled = 106;
        public static final int TRANSACTION_isApplicationClearDataDisabled = 99;
        public static final int TRANSACTION_isApplicationExternalStorageBlacklisted = 174;
        public static final int TRANSACTION_isApplicationExternalStorageWhitelisted = 173;
        public static final int TRANSACTION_isApplicationFocusMonitoredAsUser = 141;
        public static final int TRANSACTION_isApplicationForceStopDisabled = 57;
        public static final int TRANSACTION_isApplicationInstallationEnabled = 188;
        public static final int TRANSACTION_isApplicationInstalled = 3;
        public static final int TRANSACTION_isApplicationRunning = 4;
        public static final int TRANSACTION_isApplicationSetToDefault = 198;
        public static final int TRANSACTION_isApplicationStartDisabledAsUser = 125;
        public static final int TRANSACTION_isCameraAllowlistedApp = 184;
        public static final int TRANSACTION_isFromApprovedInstaller = 189;
        public static final int TRANSACTION_isIntentDisabled = 44;
        public static final int TRANSACTION_isOcspCheckEnabled = 87;
        public static final int TRANSACTION_isPackageClipboardAllowed = 136;
        public static final int TRANSACTION_isPackageInApprovedInstallerWhiteList = 92;
        public static final int TRANSACTION_isPackageInBlacklistInternal = 172;
        public static final int TRANSACTION_isPackageInWhitelistInternal = 171;
        public static final int TRANSACTION_isPackageUpdateAllowed = 118;
        public static final int TRANSACTION_isRevocationCheckEnabled = 85;
        public static final int TRANSACTION_isStatusBarNotificationAllowedAsUser = 69;
        public static final int TRANSACTION_isUsbDevicePermittedForPackage = 145;
        public static final int TRANSACTION_isWidgetAllowed = 59;
        public static final int TRANSACTION_reapplyRuntimePermissions = 155;
        public static final int TRANSACTION_removeAppNotificationBlackList = 61;
        public static final int TRANSACTION_removeAppNotificationWhiteList = 64;
        public static final int TRANSACTION_removeAppPackageNameFromBlackList = 46;
        public static final int TRANSACTION_removeAppPackageNameFromWhiteList = 49;
        public static final int TRANSACTION_removeAppPermissionFromBlackList = 35;
        public static final int TRANSACTION_removeAppSignatureFromBlackList = 38;
        public static final int TRANSACTION_removeAppSignatureFromWhiteList = 89;
        public static final int TRANSACTION_removeApplicationFromCameraAllowList = 183;
        public static final int TRANSACTION_removeDefaultApplication = 197;
        public static final int TRANSACTION_removeManagedApplications = 1;
        public static final int TRANSACTION_removePackageFromBatteryOptimizationWhiteList = 158;
        public static final int TRANSACTION_removePackageFromBlackList = 170;
        public static final int TRANSACTION_removePackageFromWhiteList = 167;
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
        public static final int TRANSACTION_setAndroidMarketState = 162;
        public static final int TRANSACTION_setAppInstallToSdCard = 176;
        public static final int TRANSACTION_setAppInstallationMode = 53;
        public static final int TRANSACTION_setApplicationComponentState = 110;
        public static final int TRANSACTION_setApplicationInstallationDisabled = 8;
        public static final int TRANSACTION_setApplicationNotificationMode = 66;
        public static final int TRANSACTION_setApplicationState = 7;
        public static final int TRANSACTION_setApplicationStateList = 43;
        public static final int TRANSACTION_setApplicationUninstallationDisabled = 9;
        public static final int TRANSACTION_setApplicationUninstallationMode = 55;
        public static final int TRANSACTION_setAsManagedApp = 180;
        public static final int TRANSACTION_setConcentrationMode = 200;
        public static final int TRANSACTION_setDefaultApplication = 193;
        public static final int TRANSACTION_startApp = 41;
        public static final int TRANSACTION_stopApp = 40;
        public static final int TRANSACTION_uninstallApplication = 6;
        public static final int TRANSACTION_updateApplicationTable = 149;
        public static final int TRANSACTION_updateDataUsageDb = 30;
        public static final int TRANSACTION_updateWidgetStatus = 143;
        public static final int TRANSACTION_verifyRuntimePermissionPackageSignature = 154;
        public static final int TRANSACTION_wipeApplicationData = 2;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IApplicationPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addAppNotificationBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> addPackagesToPreventStartBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean addUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str, List<UsbDeviceConfig> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void applicationUsageAppLaunchCount(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void applicationUsageAppPauseTime(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List<String> list, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean changeApplicationIcon(ContextInfo contextInfo, String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean changeApplicationName(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean clearDisableClipboardBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean clearDisableClipboardWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean clearDisableUpdateBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean clearDisableUpdateWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean clearFocusMonitoringList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean clearPreventStartBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final IntentFilter createIntentFilter(Intent intent) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentFilter) obtain2.readTypedObject(IntentFilter.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean deleteManagedAppInfo(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void doSelfUninstall(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getAddHomeShorcutRequested() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AppInfoLastUsage[]) obtain2.createTypedArray(AppInfoLastUsage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<DefaultAppConfiguration> getAllDefaultApplications(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(DefaultAppConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<DefaultAppConfiguration> getAllDefaultApplicationsInternal(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(DefaultAppConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getAllPackagesFromBatteryOptimizationWhiteList() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final Map getAllWidgets(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getAppInstallToSdCard(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int getAppInstallationMode(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getAppNotificationBlackList(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getAppNotificationWhiteList(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppControlInfo> getAppPackageNamesAllBlackLists(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppControlInfo> getAppPackageNamesAllWhiteLists(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppControlInfo> getAppPermissionsAllBlackLists(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String[] getAppPermissionsBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String[] getAppSignatureBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppControlInfo> getAppSignaturesAllBlackLists(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppControlInfo> getAppSignaturesAllWhiteLists(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String[] getAppSignaturesWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final long getApplicationCacheSize(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final long getApplicationCodeSize(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final long getApplicationCpuUsage(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final long getApplicationDataSize(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getApplicationGrantedPermissions(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final long getApplicationMemoryUsage(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String getApplicationName(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String getApplicationNameFromDb(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int getApplicationNotificationMode(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int getApplicationNotificationModeAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getApplicationStateEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String[] getApplicationStateList(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final long getApplicationTotalSize(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int getApplicationUid(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getApplicationUninstallationEnabledAsUser(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int getApplicationUninstallationMode(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String getApplicationVersion(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int getApplicationVersionCode(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ManagedAppInfo[]) obtain2.createTypedArray(ManagedAppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AppInfoLastUsage[]) obtain2.createTypedArray(AppInfoLastUsage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean getConcentrationMode() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final ComponentName getDefaultApplication(ContextInfo contextInfo, Intent intent) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final ComponentName getDefaultApplicationInternal(Intent intent, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getDisabledPackages(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<ComponentName> getHomeShortcuts(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String[] getInstalledApplicationsIDList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String[] getInstalledManagedApplicationsList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IApplicationPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<NetworkStats> getNetworkStats(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(NetworkStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Signature[]) obtain2.createTypedArray(Signature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromBlackList(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromClearCacheBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromClearCacheWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromClearDataBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromClearDataWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromFocusMonitoringList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromForceStopBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromForceStopWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromPreventStartBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromWhiteList(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromWidgetBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getPackagesFromWidgetWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getRuntimePermissions(ContextInfo contextInfo, String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> getRuntimePermissionsEnforced(int i, String str, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppInfo> getTopNCPUUsageApp(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppInfo> getTopNDataUsageApp(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<AppInfo> getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<UsbDeviceConfig> getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UsbDeviceConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean handleStatusBarNotificationNotAllowedAsUser(String str, int i, Notification notification2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notification2, 0);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean installApplication(ContextInfo contextInfo, String str, boolean z, ParcelFileDescriptor parcelFileDescriptor) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean installExistingApplication(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isAnyApplicationIconChangedAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isAnyApplicationNameChangedAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationClearCacheDisabled(String str, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationClearDataDisabled(String str, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationExternalStorageBlacklisted(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationExternalStorageWhitelisted(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationFocusMonitoredAsUser(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationInstallationEnabled(String str, Signature[] signatureArr, List<String> list, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(signatureArr, 0);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationInstalled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationRunning(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationSetToDefault(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isApplicationStartDisabledAsUser(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isCameraAllowlistedApp(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isFromApprovedInstaller(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isIntentDisabled(Intent intent) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isOcspCheckEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isPackageClipboardAllowed(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isPackageInBlacklistInternal(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isPackageInWhitelistInternal(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isPackageUpdateAllowed(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isStatusBarNotificationAllowedAsUser(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean isWidgetAllowed(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void reapplyRuntimePermissions(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeAppNotificationBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final List<String> removeManagedApplications(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int removePackageFromBlackList(ContextInfo contextInfo, int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void setAndroidMarketState(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setAppInstallationMode(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setApplicationNotificationMode(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setApplicationState(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setAsManagedApp(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setConcentrationMode(ContextInfo contextInfo, List<String> list, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean startApp(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean stopApp(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean updateApplicationTable(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void updateDataUsageDb() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final void updateWidgetStatus(ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean verifyRuntimePermissionPackageSignature(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.application.IApplicationPolicy
            public final boolean wipeApplicationData(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApplicationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IApplicationPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IApplicationPolicy)) ? new Proxy(iBinder) : (IApplicationPolicy) queryLocalInterface;
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
                    return "applyRuntimePermissions";
                case 153:
                    return "getRuntimePermissionsEnforced";
                case 154:
                    return "verifyRuntimePermissionPackageSignature";
                case 155:
                    return "reapplyRuntimePermissions";
                case 156:
                    return "getRuntimePermissions";
                case 157:
                    return "addPackageToBatteryOptimizationWhiteList";
                case 158:
                    return "removePackageFromBatteryOptimizationWhiteList";
                case 159:
                    return "getPackagesFromBatteryOptimizationWhiteList";
                case 160:
                    return "getAllPackagesFromBatteryOptimizationWhiteList";
                case 161:
                    return "getApplicationGrantedPermissions";
                case 162:
                    return "setAndroidMarketState";
                case 163:
                    return "getPackageSignaturesFromExternalStorageWhiteList";
                case 164:
                    return "clearPackagesFromExternalStorageWhiteList";
                case 165:
                    return "addPackageToWhiteList";
                case 166:
                    return "getPackagesFromWhiteList";
                case 167:
                    return "removePackageFromWhiteList";
                case 168:
                    return "addPackageToBlackList";
                case 169:
                    return "getPackagesFromBlackList";
                case 170:
                    return "removePackageFromBlackList";
                case 171:
                    return "isPackageInWhitelistInternal";
                case 172:
                    return "isPackageInBlacklistInternal";
                case 173:
                    return "isApplicationExternalStorageWhitelisted";
                case 174:
                    return "isApplicationExternalStorageBlacklisted";
                case 175:
                    return "deleteManagedAppInfo";
                case 176:
                    return "setAppInstallToSdCard";
                case 177:
                    return "getAppInstallToSdCard";
                case 178:
                    return "getInstalledManagedApplicationsList";
                case 179:
                    return "getApplicationsList";
                case 180:
                    return "setAsManagedApp";
                case 181:
                    return "addApplicationToCameraAllowList";
                case 182:
                    return "getApplicationPackagesFromCameraAllowList";
                case 183:
                    return "removeApplicationFromCameraAllowList";
                case 184:
                    return "isCameraAllowlistedApp";
                case 185:
                    return "handleStatusBarNotificationNotAllowedAsUser";
                case 186:
                    return "getAllDefaultApplicationsInternal";
                case 187:
                    return "getApplicationStateEnabledAsUser";
                case 188:
                    return "isApplicationInstallationEnabled";
                case 189:
                    return "isFromApprovedInstaller";
                case 190:
                    return "getApplicationUninstallationEnabledAsUser";
                case 191:
                    return "applicationUsageAppLaunchCount";
                case 192:
                    return "applicationUsageAppPauseTime";
                case 193:
                    return "setDefaultApplication";
                case 194:
                    return "getDefaultApplication";
                case 195:
                    return "getDefaultApplicationInternal";
                case 196:
                    return "getAllDefaultApplications";
                case 197:
                    return "removeDefaultApplication";
                case 198:
                    return "isApplicationSetToDefault";
                case 199:
                    return "createIntentFilter";
                case 200:
                    return "setConcentrationMode";
                case 201:
                    return "getConcentrationMode";
                case 202:
                    return "doSelfUninstall";
                default:
                    return null;
            }
        }

        public final int getMaxTransactionId() {
            return 201;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<String> removeManagedApplications = removeManagedApplications(contextInfo, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeStringList(removeManagedApplications);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean wipeApplicationData = wipeApplicationData(contextInfo2, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wipeApplicationData);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationInstalled = isApplicationInstalled(contextInfo3, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationInstalled);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationRunning = isApplicationRunning(contextInfo4, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationRunning);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean installApplication = installApplication(contextInfo5, readString4, readBoolean, parcelFileDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installApplication);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString5 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uninstallApplication = uninstallApplication(contextInfo6, readString5, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uninstallApplication);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean applicationState = setApplicationState(contextInfo7, readString6, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationState);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplicationInstallationDisabled(contextInfo8, readString7, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplicationUninstallationDisabled(contextInfo9, readString8, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationStateEnabled = getApplicationStateEnabled(contextInfo10, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationStateEnabled);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationInstallationEnabled = getApplicationInstallationEnabled(contextInfo11, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationInstallationEnabled);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationUninstallationEnabled = getApplicationUninstallationEnabled(contextInfo12, readString11);
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
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String applicationName = getApplicationName(contextInfo14, readString12);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationName);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int applicationUid = getApplicationUid(contextInfo15, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationUid);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String applicationVersion = getApplicationVersion(contextInfo16, readString14);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationVersion);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int applicationVersionCode = getApplicationVersionCode(contextInfo17, readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationVersionCode);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationTotalSize = getApplicationTotalSize(contextInfo18, readString16);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationTotalSize);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationCodeSize = getApplicationCodeSize(contextInfo19, readString17);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationCodeSize);
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationDataSize = getApplicationDataSize(contextInfo20, readString18);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationDataSize);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationCacheSize = getApplicationCacheSize(contextInfo21, readString19);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationCacheSize);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationMemoryUsage = getApplicationMemoryUsage(contextInfo22, readString20);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationMemoryUsage);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long applicationCpuUsage = getApplicationCpuUsage(contextInfo23, readString21);
                    parcel2.writeNoException();
                    parcel2.writeLong(applicationCpuUsage);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<AppInfo> topNMemoryUsageApp = getTopNMemoryUsageApp(contextInfo24, readInt, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(topNMemoryUsageApp, 1);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AppInfo> topNDataUsageApp = getTopNDataUsageApp(contextInfo25, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(topNDataUsageApp, 1);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<AppInfo> topNCPUUsageApp = getTopNCPUUsageApp(contextInfo26, readInt3, readBoolean7);
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
                    String readString22 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean changeApplicationIcon = changeApplicationIcon(contextInfo30, readString22, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeApplicationIcon);
                    return true;
                case 32:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] applicationIconFromDb = getApplicationIconFromDb(contextInfo31, readString23);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(applicationIconFromDb);
                    return true;
                case 33:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAnyApplicationIconChangedAsUser = isAnyApplicationIconChangedAsUser(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAnyApplicationIconChangedAsUser);
                    return true;
                case 34:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAppPermissionToBlackList = addAppPermissionToBlackList(contextInfo32, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAppPermissionToBlackList);
                    return true;
                case 35:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeAppPermissionFromBlackList = removeAppPermissionFromBlackList(contextInfo33, readString25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAppPermissionFromBlackList);
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
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAppSignatureToBlackList = addAppSignatureToBlackList(contextInfo35, readString26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAppSignatureToBlackList);
                    return true;
                case 38:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeAppSignatureFromBlackList = removeAppSignatureFromBlackList(contextInfo36, readString27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAppSignatureFromBlackList);
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
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean stopApp = stopApp(contextInfo38, readString28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopApp);
                    return true;
                case 41:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean startApp = startApp(contextInfo39, readString29, readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startApp);
                    return true;
                case 42:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String[] applicationStateList = getApplicationStateList(contextInfo40, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(applicationStateList);
                    return true;
                case 43:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String[] applicationStateList2 = setApplicationStateList(contextInfo41, createStringArray, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(applicationStateList2);
                    return true;
                case 44:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isIntentDisabled = isIntentDisabled(intent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIntentDisabled);
                    return true;
                case 45:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAppPackageNameToBlackList = addAppPackageNameToBlackList(contextInfo42, readString31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAppPackageNameToBlackList);
                    return true;
                case 46:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeAppPackageNameFromBlackList = removeAppPackageNameFromBlackList(contextInfo43, readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAppPackageNameFromBlackList);
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
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAppPackageNameToWhiteList = addAppPackageNameToWhiteList(contextInfo45, readString33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAppPackageNameToWhiteList);
                    return true;
                case 49:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeAppPackageNameFromWhiteList = removeAppPackageNameFromWhiteList(contextInfo46, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAppPackageNameFromWhiteList);
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
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean appInstallationMode = setAppInstallationMode(contextInfo50, readInt5);
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
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationUninstallationMode = setApplicationUninstallationMode(contextInfo52, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationUninstallationMode);
                    return true;
                case 56:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToForceStopBlackList = addPackagesToForceStopBlackList(contextInfo53, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToForceStopBlackList);
                    return true;
                case 57:
                    String readString35 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationForceStopDisabled = isApplicationForceStopDisabled(readString35, readInt7, readString36, readString37, readString38, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationForceStopDisabled);
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
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isWidgetAllowed = isWidgetAllowed(contextInfo55, readString39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWidgetAllowed);
                    return true;
                case 60:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addAppNotificationBlackList = addAppNotificationBlackList(contextInfo56, createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAppNotificationBlackList);
                    return true;
                case 61:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeAppNotificationBlackList = removeAppNotificationBlackList(contextInfo57, createStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAppNotificationBlackList);
                    return true;
                case 62:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> appNotificationBlackList = getAppNotificationBlackList(contextInfo58, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(appNotificationBlackList);
                    return true;
                case 63:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addAppNotificationWhiteList = addAppNotificationWhiteList(contextInfo59, createStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAppNotificationWhiteList);
                    return true;
                case 64:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeAppNotificationWhiteList = removeAppNotificationWhiteList(contextInfo60, createStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAppNotificationWhiteList);
                    return true;
                case 65:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> appNotificationWhiteList = getAppNotificationWhiteList(contextInfo61, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(appNotificationWhiteList);
                    return true;
                case 66:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationNotificationMode = setApplicationNotificationMode(contextInfo62, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationNotificationMode);
                    return true;
                case 67:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int applicationNotificationMode2 = getApplicationNotificationMode(contextInfo63, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationNotificationMode2);
                    return true;
                case 68:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applicationNotificationModeAsUser = getApplicationNotificationModeAsUser(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationNotificationModeAsUser);
                    return true;
                case 69:
                    String readString40 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStatusBarNotificationAllowedAsUser = isStatusBarNotificationAllowedAsUser(readString40, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStatusBarNotificationAllowedAsUser);
                    return true;
                case 70:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addHomeShortcut = addHomeShortcut(contextInfo64, readString41, readString42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addHomeShortcut);
                    return true;
                case 71:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteHomeShortcut = deleteHomeShortcut(contextInfo65, readString43, readString44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteHomeShortcut);
                    return true;
                case 72:
                    ContextInfo contextInfo66 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Map allWidgets = getAllWidgets(contextInfo66, readString45);
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
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToForceStopWhiteList = addPackagesToForceStopWhiteList(contextInfo69, createStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToForceStopWhiteList);
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
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToWidgetWhiteList = addPackagesToWidgetWhiteList(contextInfo72, createStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToWidgetWhiteList);
                    return true;
                case 79:
                    ContextInfo contextInfo73 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList9 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromWidgetWhiteList = removePackagesFromWidgetWhiteList(contextInfo73, createStringArrayList9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromWidgetWhiteList);
                    return true;
                case 80:
                    ContextInfo contextInfo74 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList10 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToWidgetBlackList = addPackagesToWidgetBlackList(contextInfo74, createStringArrayList10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToWidgetBlackList);
                    return true;
                case 81:
                    ContextInfo contextInfo75 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList11 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromWidgetBlackList = removePackagesFromWidgetBlackList(contextInfo75, createStringArrayList11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromWidgetBlackList);
                    return true;
                case 82:
                    ContextInfo contextInfo76 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList12 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromForceStopWhiteList = removePackagesFromForceStopWhiteList(contextInfo76, createStringArrayList12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromForceStopWhiteList);
                    return true;
                case 83:
                    ContextInfo contextInfo77 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList13 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromForceStopBlackList = removePackagesFromForceStopBlackList(contextInfo77, createStringArrayList13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromForceStopBlackList);
                    return true;
                case 84:
                    ContextInfo contextInfo78 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString46 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableRevocationCheck = enableRevocationCheck(contextInfo78, readString46, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableRevocationCheck);
                    return true;
                case 85:
                    ContextInfo contextInfo79 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRevocationCheckEnabled = isRevocationCheckEnabled(contextInfo79, readString47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRevocationCheckEnabled);
                    return true;
                case 86:
                    ContextInfo contextInfo80 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString48 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableOcspCheck = enableOcspCheck(contextInfo80, readString48, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableOcspCheck);
                    return true;
                case 87:
                    ContextInfo contextInfo81 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isOcspCheckEnabled = isOcspCheckEnabled(contextInfo81, readString49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOcspCheckEnabled);
                    return true;
                case 88:
                    ContextInfo contextInfo82 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAppSignatureToWhiteList = addAppSignatureToWhiteList(contextInfo82, readString50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAppSignatureToWhiteList);
                    return true;
                case 89:
                    ContextInfo contextInfo83 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeAppSignatureFromWhiteList = removeAppSignatureFromWhiteList(contextInfo83, readString51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAppSignatureFromWhiteList);
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
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackageInApprovedInstallerWhiteList = isPackageInApprovedInstallerWhiteList(contextInfo86, readString52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageInApprovedInstallerWhiteList);
                    return true;
                case 93:
                    ContextInfo contextInfo87 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList14 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToClearDataBlackList = addPackagesToClearDataBlackList(contextInfo87, createStringArrayList14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToClearDataBlackList);
                    return true;
                case 94:
                    ContextInfo contextInfo88 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList15 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromClearDataBlackList = removePackagesFromClearDataBlackList(contextInfo88, createStringArrayList15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromClearDataBlackList);
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
                    ArrayList<String> createStringArrayList16 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToClearDataWhiteList = addPackagesToClearDataWhiteList(contextInfo90, createStringArrayList16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToClearDataWhiteList);
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
                    ArrayList<String> createStringArrayList17 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromClearDataWhiteList = removePackagesFromClearDataWhiteList(contextInfo92, createStringArrayList17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromClearDataWhiteList);
                    return true;
                case 99:
                    String readString53 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationClearDataDisabled = isApplicationClearDataDisabled(readString53, readInt11, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationClearDataDisabled);
                    return true;
                case 100:
                    ContextInfo contextInfo93 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList18 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToClearCacheBlackList = addPackagesToClearCacheBlackList(contextInfo93, createStringArrayList18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToClearCacheBlackList);
                    return true;
                case 101:
                    ContextInfo contextInfo94 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList19 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromClearCacheBlackList = removePackagesFromClearCacheBlackList(contextInfo94, createStringArrayList19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromClearCacheBlackList);
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
                    ArrayList<String> createStringArrayList20 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToClearCacheWhiteList = addPackagesToClearCacheWhiteList(contextInfo96, createStringArrayList20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToClearCacheWhiteList);
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
                    ArrayList<String> createStringArrayList21 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromClearCacheWhiteList = removePackagesFromClearCacheWhiteList(contextInfo98, createStringArrayList21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromClearCacheWhiteList);
                    return true;
                case 106:
                    String readString54 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationClearCacheDisabled = isApplicationClearCacheDisabled(readString54, readInt12, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationClearCacheDisabled);
                    return true;
                case 107:
                    ContextInfo contextInfo99 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString55 = parcel.readString();
                    String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean changeApplicationName = changeApplicationName(contextInfo99, readString55, readString56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeApplicationName);
                    return true;
                case 108:
                    String readString57 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String applicationNameFromDb = getApplicationNameFromDb(readString57, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationNameFromDb);
                    return true;
                case 109:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAnyApplicationNameChangedAsUser = isAnyApplicationNameChangedAsUser(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAnyApplicationNameChangedAsUser);
                    return true;
                case 110:
                    ContextInfo contextInfo100 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean applicationComponentState = setApplicationComponentState(contextInfo100, componentName, readBoolean18);
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
                    ArrayList<String> createStringArrayList22 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToDisableUpdateBlackList = addPackagesToDisableUpdateBlackList(contextInfo102, createStringArrayList22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToDisableUpdateBlackList);
                    return true;
                case 113:
                    ContextInfo contextInfo103 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList23 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromDisableUpdateBlackList = removePackagesFromDisableUpdateBlackList(contextInfo103, createStringArrayList23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromDisableUpdateBlackList);
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
                    ArrayList<String> createStringArrayList24 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToDisableUpdateWhiteList = addPackagesToDisableUpdateWhiteList(contextInfo105, createStringArrayList24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToDisableUpdateWhiteList);
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
                    ArrayList<String> createStringArrayList25 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromDisableUpdateWhiteList = removePackagesFromDisableUpdateWhiteList(contextInfo107, createStringArrayList25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromDisableUpdateWhiteList);
                    return true;
                case 118:
                    String readString58 = parcel.readString();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isPackageUpdateAllowed = isPackageUpdateAllowed(readString58, readBoolean19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageUpdateAllowed);
                    return true;
                case 119:
                    ContextInfo contextInfo108 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearDisableUpdateBlackList = clearDisableUpdateBlackList(contextInfo108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearDisableUpdateBlackList);
                    return true;
                case 120:
                    ContextInfo contextInfo109 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearDisableUpdateWhiteList = clearDisableUpdateWhiteList(contextInfo109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearDisableUpdateWhiteList);
                    return true;
                case 121:
                    ContextInfo contextInfo110 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList26 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<String> addPackagesToPreventStartBlackList = addPackagesToPreventStartBlackList(contextInfo110, createStringArrayList26);
                    parcel2.writeNoException();
                    parcel2.writeStringList(addPackagesToPreventStartBlackList);
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
                    ArrayList<String> createStringArrayList27 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromPreventStartBlackList = removePackagesFromPreventStartBlackList(contextInfo112, createStringArrayList27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromPreventStartBlackList);
                    return true;
                case 124:
                    ContextInfo contextInfo113 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearPreventStartBlackList = clearPreventStartBlackList(contextInfo113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearPreventStartBlackList);
                    return true;
                case 125:
                    String readString59 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationStartDisabledAsUser = isApplicationStartDisabledAsUser(readString59, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationStartDisabledAsUser);
                    return true;
                case 126:
                    ContextInfo contextInfo114 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList28 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToDisableClipboardBlackList = addPackagesToDisableClipboardBlackList(contextInfo114, createStringArrayList28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToDisableClipboardBlackList);
                    return true;
                case 127:
                    ContextInfo contextInfo115 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList29 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromDisableClipboardBlackList = removePackagesFromDisableClipboardBlackList(contextInfo115, createStringArrayList29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromDisableClipboardBlackList);
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
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableClipboardBlackListAsUserInternal = getPackagesFromDisableClipboardBlackListAsUserInternal(contextInfo117, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableClipboardBlackListAsUserInternal);
                    return true;
                case 130:
                    ContextInfo contextInfo118 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList30 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToDisableClipboardWhiteList = addPackagesToDisableClipboardWhiteList(contextInfo118, createStringArrayList30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToDisableClipboardWhiteList);
                    return true;
                case 131:
                    ContextInfo contextInfo119 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList31 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromDisableClipboardWhiteList = removePackagesFromDisableClipboardWhiteList(contextInfo119, createStringArrayList31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromDisableClipboardWhiteList);
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
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableClipboardWhiteListAsUserInternal = getPackagesFromDisableClipboardWhiteListAsUserInternal(contextInfo121, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableClipboardWhiteListAsUserInternal);
                    return true;
                case 134:
                    ContextInfo contextInfo122 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearDisableClipboardBlackList = clearDisableClipboardBlackList(contextInfo122);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearDisableClipboardBlackList);
                    return true;
                case 135:
                    ContextInfo contextInfo123 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearDisableClipboardWhiteList = clearDisableClipboardWhiteList(contextInfo123);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearDisableClipboardWhiteList);
                    return true;
                case 136:
                    String readString60 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageClipboardAllowed = isPackageClipboardAllowed(readString60, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageClipboardAllowed);
                    return true;
                case 137:
                    ContextInfo contextInfo124 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList32 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToFocusMonitoringList = addPackagesToFocusMonitoringList(contextInfo124, createStringArrayList32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToFocusMonitoringList);
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
                    ArrayList<String> createStringArrayList33 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromFocusMonitoringList = removePackagesFromFocusMonitoringList(contextInfo126, createStringArrayList33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromFocusMonitoringList);
                    return true;
                case 140:
                    ContextInfo contextInfo127 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearFocusMonitoringList = clearFocusMonitoringList(contextInfo127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearFocusMonitoringList);
                    return true;
                case 141:
                    String readString61 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationFocusMonitoredAsUser = isApplicationFocusMonitoredAsUser(readString61, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationFocusMonitoredAsUser);
                    return true;
                case 142:
                    ContextInfo contextInfo128 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString62 = parcel.readString();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> homeShortcuts = getHomeShortcuts(contextInfo128, readString62, readBoolean20);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(homeShortcuts, 1);
                    return true;
                case 143:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateWidgetStatus(componentName3, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    ContextInfo contextInfo129 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<UsbDeviceConfig> usbDevicesForDefaultAccess = getUsbDevicesForDefaultAccess(contextInfo129, readString63);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(usbDevicesForDefaultAccess, 1);
                    return true;
                case 145:
                    int readInt21 = parcel.readInt();
                    UsbDevice usbDevice = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUsbDevicePermittedForPackage = isUsbDevicePermittedForPackage(readInt21, usbDevice, readString64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbDevicePermittedForPackage);
                    return true;
                case 146:
                    ContextInfo contextInfo130 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString65 = parcel.readString();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(UsbDeviceConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addUsbDevicesForDefaultAccess = addUsbDevicesForDefaultAccess(contextInfo130, readString65, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addUsbDevicesForDefaultAccess);
                    return true;
                case 147:
                    ContextInfo contextInfo131 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearUsbDevicesForDefaultAccess = clearUsbDevicesForDefaultAccess(contextInfo131, readString66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearUsbDevicesForDefaultAccess);
                    return true;
                case 148:
                    ContextInfo contextInfo132 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean installExistingApplication = installExistingApplication(contextInfo132, readString67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installExistingApplication);
                    return true;
                case 149:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateApplicationTable = updateApplicationTable(readInt22, readInt23, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateApplicationTable);
                    return true;
                case 150:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> disabledPackages = getDisabledPackages(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeStringList(disabledPackages);
                    return true;
                case 151:
                    boolean addHomeShorcutRequested = getAddHomeShorcutRequested();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addHomeShorcutRequested);
                    return true;
                case 152:
                    ContextInfo contextInfo133 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    ArrayList<String> createStringArrayList34 = parcel.createStringArrayList();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applyRuntimePermissions = applyRuntimePermissions(contextInfo133, appIdentity, createStringArrayList34, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeInt(applyRuntimePermissions);
                    return true;
                case 153:
                    int readInt27 = parcel.readInt();
                    String readString68 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> runtimePermissionsEnforced = getRuntimePermissionsEnforced(readInt27, readString68, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeStringList(runtimePermissionsEnforced);
                    return true;
                case 154:
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean verifyRuntimePermissionPackageSignature = verifyRuntimePermissionPackageSignature(readString69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(verifyRuntimePermissionPackageSignature);
                    return true;
                case 155:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reapplyRuntimePermissions(readInt29);
                    parcel2.writeNoException();
                    return true;
                case 156:
                    ContextInfo contextInfo134 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString70 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> runtimePermissions = getRuntimePermissions(contextInfo134, readString70, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeStringList(runtimePermissions);
                    return true;
                case 157:
                    ContextInfo contextInfo135 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity2 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackageToBatteryOptimizationWhiteList = addPackageToBatteryOptimizationWhiteList(contextInfo135, appIdentity2);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackageToBatteryOptimizationWhiteList);
                    return true;
                case 158:
                    ContextInfo contextInfo136 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity3 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removePackageFromBatteryOptimizationWhiteList = removePackageFromBatteryOptimizationWhiteList(contextInfo136, appIdentity3);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackageFromBatteryOptimizationWhiteList);
                    return true;
                case 159:
                    ContextInfo contextInfo137 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromBatteryOptimizationWhiteList = getPackagesFromBatteryOptimizationWhiteList(contextInfo137);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromBatteryOptimizationWhiteList);
                    return true;
                case 160:
                    List<String> allPackagesFromBatteryOptimizationWhiteList = getAllPackagesFromBatteryOptimizationWhiteList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allPackagesFromBatteryOptimizationWhiteList);
                    return true;
                case 161:
                    ContextInfo contextInfo138 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> applicationGrantedPermissions = getApplicationGrantedPermissions(contextInfo138, readString71);
                    parcel2.writeNoException();
                    parcel2.writeStringList(applicationGrantedPermissions);
                    return true;
                case 162:
                    ContextInfo contextInfo139 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAndroidMarketState(contextInfo139, readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 163:
                    ContextInfo contextInfo140 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Signature[] packageSignaturesFromExternalStorageWhiteList = getPackageSignaturesFromExternalStorageWhiteList(contextInfo140, readString72);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(packageSignaturesFromExternalStorageWhiteList, 1);
                    return true;
                case 164:
                    ContextInfo contextInfo141 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int clearPackagesFromExternalStorageWhiteList = clearPackagesFromExternalStorageWhiteList(contextInfo141);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearPackagesFromExternalStorageWhiteList);
                    return true;
                case 165:
                    ContextInfo contextInfo142 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt31 = parcel.readInt();
                    AppIdentity appIdentity4 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackageToWhiteList = addPackageToWhiteList(contextInfo142, readInt31, appIdentity4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackageToWhiteList);
                    return true;
                case 166:
                    ContextInfo contextInfo143 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromWhiteList = getPackagesFromWhiteList(contextInfo143, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromWhiteList);
                    return true;
                case 167:
                    ContextInfo contextInfo144 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt33 = parcel.readInt();
                    String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removePackageFromWhiteList = removePackageFromWhiteList(contextInfo144, readInt33, readString73);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackageFromWhiteList);
                    return true;
                case 168:
                    ContextInfo contextInfo145 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt34 = parcel.readInt();
                    AppIdentity appIdentity5 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackageToBlackList = addPackageToBlackList(contextInfo145, readInt34, appIdentity5);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackageToBlackList);
                    return true;
                case 169:
                    ContextInfo contextInfo146 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromBlackList = getPackagesFromBlackList(contextInfo146, readInt35);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromBlackList);
                    return true;
                case 170:
                    ContextInfo contextInfo147 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt36 = parcel.readInt();
                    String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removePackageFromBlackList = removePackageFromBlackList(contextInfo147, readInt36, readString74);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackageFromBlackList);
                    return true;
                case 171:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageInWhitelistInternal = isPackageInWhitelistInternal(readInt37, readInt38, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageInWhitelistInternal);
                    return true;
                case 172:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageInBlacklistInternal = isPackageInBlacklistInternal(readInt40, readInt41, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageInBlacklistInternal);
                    return true;
                case 173:
                    int readInt43 = parcel.readInt();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationExternalStorageWhitelisted = isApplicationExternalStorageWhitelisted(readInt43, readString75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationExternalStorageWhitelisted);
                    return true;
                case 174:
                    int readInt44 = parcel.readInt();
                    String readString76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationExternalStorageBlacklisted = isApplicationExternalStorageBlacklisted(readInt44, readString76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationExternalStorageBlacklisted);
                    return true;
                case 175:
                    ContextInfo contextInfo148 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteManagedAppInfo = deleteManagedAppInfo(contextInfo148, readString77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteManagedAppInfo);
                    return true;
                case 176:
                    ContextInfo contextInfo149 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean appInstallToSdCard = setAppInstallToSdCard(contextInfo149, readBoolean22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appInstallToSdCard);
                    return true;
                case 177:
                    ContextInfo contextInfo150 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean appInstallToSdCard2 = getAppInstallToSdCard(contextInfo150);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appInstallToSdCard2);
                    return true;
                case 178:
                    ContextInfo contextInfo151 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] installedManagedApplicationsList = getInstalledManagedApplicationsList(contextInfo151);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(installedManagedApplicationsList);
                    return true;
                case 179:
                    ContextInfo contextInfo152 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ManagedAppInfo[] applicationsList = getApplicationsList(contextInfo152, readString78);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(applicationsList, 1);
                    return true;
                case 180:
                    ContextInfo contextInfo153 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean asManagedApp = setAsManagedApp(contextInfo153, readString79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(asManagedApp);
                    return true;
                case 181:
                    ContextInfo contextInfo154 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity6 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addApplicationToCameraAllowList = addApplicationToCameraAllowList(contextInfo154, appIdentity6);
                    parcel2.writeNoException();
                    parcel2.writeInt(addApplicationToCameraAllowList);
                    return true;
                case 182:
                    ContextInfo contextInfo155 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> applicationPackagesFromCameraAllowList = getApplicationPackagesFromCameraAllowList(contextInfo155);
                    parcel2.writeNoException();
                    parcel2.writeStringList(applicationPackagesFromCameraAllowList);
                    return true;
                case 183:
                    ContextInfo contextInfo156 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity7 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removeApplicationFromCameraAllowList = removeApplicationFromCameraAllowList(contextInfo156, appIdentity7);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeApplicationFromCameraAllowList);
                    return true;
                case 184:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCameraAllowlistedApp = isCameraAllowlistedApp(readInt45, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraAllowlistedApp);
                    return true;
                case 185:
                    String readString80 = parcel.readString();
                    int readInt47 = parcel.readInt();
                    Notification notification2 = (Notification) parcel.readTypedObject(Notification.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean handleStatusBarNotificationNotAllowedAsUser = handleStatusBarNotificationNotAllowedAsUser(readString80, readInt47, notification2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handleStatusBarNotificationNotAllowedAsUser);
                    return true;
                case 186:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DefaultAppConfiguration> allDefaultApplicationsInternal = getAllDefaultApplicationsInternal(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allDefaultApplicationsInternal, 1);
                    return true;
                case 187:
                    String readString81 = parcel.readString();
                    boolean readBoolean23 = parcel.readBoolean();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationStateEnabledAsUser = getApplicationStateEnabledAsUser(readString81, readBoolean23, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationStateEnabledAsUser);
                    return true;
                case 188:
                    String readString82 = parcel.readString();
                    Signature[] signatureArr = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
                    ArrayList<String> createStringArrayList35 = parcel.createStringArrayList();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationInstallationEnabled = isApplicationInstallationEnabled(readString82, signatureArr, createStringArrayList35, readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationInstallationEnabled);
                    return true;
                case 189:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isFromApprovedInstaller = isFromApprovedInstaller(readInt51, readInt52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFromApprovedInstaller);
                    return true;
                case 190:
                    String readString83 = parcel.readString();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationUninstallationEnabledAsUser = getApplicationUninstallationEnabledAsUser(readString83, readInt53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationUninstallationEnabledAsUser);
                    return true;
                case 191:
                    String readString84 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applicationUsageAppLaunchCount(readString84, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 192:
                    String readString85 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applicationUsageAppPauseTime(readString85, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 193:
                    ContextInfo contextInfo157 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean defaultApplication = setDefaultApplication(contextInfo157, intent2, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultApplication);
                    return true;
                case 194:
                    ContextInfo contextInfo158 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    ComponentName defaultApplication2 = getDefaultApplication(contextInfo158, intent3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultApplication2, 1);
                    return true;
                case 195:
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName defaultApplicationInternal = getDefaultApplicationInternal(intent4, readInt56);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultApplicationInternal, 1);
                    return true;
                case 196:
                    ContextInfo contextInfo159 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<DefaultAppConfiguration> allDefaultApplications = getAllDefaultApplications(contextInfo159);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allDefaultApplications, 1);
                    return true;
                case 197:
                    ContextInfo contextInfo160 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeDefaultApplication = removeDefaultApplication(contextInfo160, intent5, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeDefaultApplication);
                    return true;
                case 198:
                    String readString86 = parcel.readString();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationSetToDefault = isApplicationSetToDefault(readString86, readInt57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationSetToDefault);
                    return true;
                case 199:
                    Intent intent6 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    IntentFilter createIntentFilter = createIntentFilter(intent6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createIntentFilter, 1);
                    return true;
                case 200:
                    ContextInfo contextInfo161 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList36 = parcel.createStringArrayList();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean concentrationMode = setConcentrationMode(contextInfo161, createStringArrayList36, readBoolean24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(concentrationMode);
                    return true;
                case 201:
                    boolean concentrationMode2 = getConcentrationMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(concentrationMode2);
                    return true;
                case 202:
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
        public final IBinder asBinder() {
            return this;
        }
    }
}
