package com.samsung.android.knox.custom;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.appconfig.IApplicationRestrictionsResultCallback;
import com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IKnoxCustomManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.custom.IKnoxCustomManager";

    int addAutoCallNumber(String str, int i, int i2) throws RemoteException;

    int addDexShortcut(int i, int i2, ComponentName componentName) throws RemoteException;

    int addDexURLShortcut(int i, int i2, String str, String str2, ComponentName componentName) throws RemoteException;

    int addDexURLShortcutExtend(int i, int i2, String str, String str2, String str3, ComponentName componentName, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int addPackagesToUltraPowerSaving(List<String> list) throws RemoteException;

    boolean addRoleHolder(String str, String str2) throws RemoteException;

    int addShortcut(int i, int i2, int i3, String str) throws RemoteException;

    int addWidget(int i, int i2, int i3, int i4, int i5, String str) throws RemoteException;

    int allowDexAutoOpenLastApp(int i) throws RemoteException;

    boolean checkEnterprisePermission(String str) throws RemoteException;

    int clearAnimation(int i) throws RemoteException;

    int clearDexLoadingLogo() throws RemoteException;

    int clearForcedDisplaySizeDensity() throws RemoteException;

    int deleteHomeScreenPage(int i) throws RemoteException;

    int dialEmergencyNumber(String str) throws RemoteException;

    int getAccessibilitySettingsItems() throws RemoteException;

    boolean getAirGestureOptionState(int i) throws RemoteException;

    List<String> getAppBlockDownloadNamespaces() throws RemoteException;

    boolean getAppBlockDownloadState() throws RemoteException;

    Bundle getApplicationRestrictionsInternal(String str, int i) throws RemoteException;

    int getAppsButtonState() throws RemoteException;

    String getAsoc() throws RemoteException;

    int getAutoCallNumberAnswerMode(String str) throws RemoteException;

    int getAutoCallNumberDelay(String str) throws RemoteException;

    List<String> getAutoCallNumberList() throws RemoteException;

    int getAutoCallPickupState() throws RemoteException;

    boolean getAutoRotationState() throws RemoteException;

    boolean getBackupRestoreState(int i) throws RemoteException;

    StatusbarIconItem getBatteryLevelColourItem() throws RemoteException;

    String getBsoh() throws RemoteException;

    String getBsohUnbiased() throws RemoteException;

    int getCallScreenDisabledItems() throws RemoteException;

    boolean getChargerConnectionSoundEnabledState() throws RemoteException;

    boolean getChargingLEDState() throws RemoteException;

    boolean getDeviceSpeakerEnabledState() throws RemoteException;

    List<String> getDexForegroundModePackageList() throws RemoteException;

    int getDexHDMIAutoEnterState() throws RemoteException;

    int getDexHomeAlignment() throws RemoteException;

    int getDexScreenTimeout() throws RemoteException;

    boolean getDisplayMirroringState() throws RemoteException;

    int getEthernetConfigurationType() throws RemoteException;

    boolean getEthernetState() throws RemoteException;

    String getExitUI(int i) throws RemoteException;

    boolean getExtendedCallInfoState() throws RemoteException;

    String getFavoriteApp(int i) throws RemoteException;

    int getFavoriteAppsMaxCount() throws RemoteException;

    int getForceAutoShutDownState() throws RemoteException;

    int getForceAutoStartUpState() throws RemoteException;

    boolean getForceSingleView() throws RemoteException;

    boolean getGearNotificationState() throws RemoteException;

    int getHardKeyBlockState(int i, int i2) throws RemoteException;

    int getHardKeyIntentBroadcast(int i, int i2) throws RemoteException;

    int getHardKeyIntentMode() throws RemoteException;

    boolean getHardKeyIntentState() throws RemoteException;

    int getHardKeyReportState(int i, int i2) throws RemoteException;

    int getHideNotificationMessages() throws RemoteException;

    String getHomeActivity() throws RemoteException;

    int getHomeScreenMode() throws RemoteException;

    boolean getInfraredState() throws RemoteException;

    boolean getInputMethodRestrictionState() throws RemoteException;

    int getKeyboardMode() throws RemoteException;

    boolean getKeyboardModeOverriden(int i) throws RemoteException;

    boolean getLTESettingState() throws RemoteException;

    boolean getLcdBacklightState() throws RemoteException;

    String getLoadingLogoPath() throws RemoteException;

    int getLockScreenHiddenItems() throws RemoteException;

    int getLockScreenOverrideMode() throws RemoteException;

    String getLockScreenShortcut(int i) throws RemoteException;

    String getMacAddress() throws RemoteException;

    int getMobileNetworkType() throws RemoteException;

    boolean getMotionControlState(int i) throws RemoteException;

    List<PowerItem> getPowerDialogCustomItems() throws RemoteException;

    boolean getPowerDialogCustomItemsState() throws RemoteException;

    int getPowerDialogItems() throws RemoteException;

    int getPowerDialogOptionMode() throws RemoteException;

    boolean getPowerMenuLockedState() throws RemoteException;

    int getPowerSavingMode() throws RemoteException;

    boolean getProKioskNotificationMessagesState() throws RemoteException;

    List<PowerItem> getProKioskPowerDialogCustomItems() throws RemoteException;

    boolean getProKioskPowerDialogCustomItemsState() throws RemoteException;

    boolean getProKioskState() throws RemoteException;

    boolean getProKioskStatusBarClockState() throws RemoteException;

    boolean getProKioskStatusBarIconsState() throws RemoteException;

    int getProKioskStatusBarMode() throws RemoteException;

    String getProKioskString(int i) throws RemoteException;

    boolean getProKioskUsbMassStorageState() throws RemoteException;

    String getProKioskUsbNetAddress(int i) throws RemoteException;

    boolean getProKioskUsbNetState() throws RemoteException;

    boolean getProtectBatteryState() throws RemoteException;

    int getQuickPanelButtons() throws RemoteException;

    int getQuickPanelEditMode() throws RemoteException;

    String getQuickPanelItems() throws RemoteException;

    String getRecentLongPressActivity() throws RemoteException;

    int getRecentLongPressMode() throws RemoteException;

    List<String> getRoleHolders(String str) throws RemoteException;

    boolean getScreenOffOnHomeLongPressState() throws RemoteException;

    boolean getScreenOffOnStatusBarDoubleTapState() throws RemoteException;

    int getScreenTimeout() throws RemoteException;

    boolean getScreenWakeupOnPowerState() throws RemoteException;

    int getSensorDisabled() throws RemoteException;

    String getSerialNumber() throws RemoteException;

    int getSettingsEnabledItems() throws RemoteException;

    int getSettingsHiddenState() throws RemoteException;

    int getShowIMEWithHardKeyboard() throws RemoteException;

    boolean getStatusBarClockState() throws RemoteException;

    boolean getStatusBarIconsState() throws RemoteException;

    int getStatusBarMode() throws RemoteException;

    boolean getStatusBarNotificationsState() throws RemoteException;

    String getStatusBarText() throws RemoteException;

    int getStatusBarTextScrollWidth() throws RemoteException;

    int getStatusBarTextSize() throws RemoteException;

    int getStatusBarTextStyle() throws RemoteException;

    int getSystemSoundsEnabledState() throws RemoteException;

    ParcelFileDescriptor getTcpDump() throws RemoteException;

    boolean getToastEnabledState() throws RemoteException;

    int getToastGravity() throws RemoteException;

    boolean getToastGravityEnabledState() throws RemoteException;

    int getToastGravityXOffset() throws RemoteException;

    int getToastGravityYOffset() throws RemoteException;

    boolean getToastShowPackageNameState() throws RemoteException;

    boolean getTorchOnVolumeButtonsState() throws RemoteException;

    List<String> getUltraPowerSavingPackages() throws RemoteException;

    boolean getUnlockSimOnBootState() throws RemoteException;

    String getUnlockSimPin() throws RemoteException;

    int getUsbConnectionType() throws RemoteException;

    int getUsbConnectionTypeInternal() throws RemoteException;

    boolean getUsbMassStorageState() throws RemoteException;

    String getUsbNetAddress(int i) throws RemoteException;

    boolean getUsbNetState() throws RemoteException;

    boolean getUsbNetStateInternal() throws RemoteException;

    int getUserInactivityTimeout() throws RemoteException;

    int getVibrationIntensity(int i) throws RemoteException;

    boolean getVolumeButtonRotationState() throws RemoteException;

    int getVolumeControlStream() throws RemoteException;

    boolean getVolumeKeyAppState() throws RemoteException;

    List<String> getVolumeKeyAppsList() throws RemoteException;

    boolean getVolumePanelEnabledState() throws RemoteException;

    boolean getWifiConnectionMonitorState() throws RemoteException;

    int getWifiFrequencyBand() throws RemoteException;

    int getWifiHotspotEnabledState() throws RemoteException;

    boolean getWifiState() throws RemoteException;

    int getZeroPageState() throws RemoteException;

    int isDexAutoOpenLastAppAllowed() throws RemoteException;

    boolean isKnoxPrivacyPolicyAcceptedInitially() throws RemoteException;

    boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() throws RemoteException;

    boolean isSupportedForceAutoStartUpState() throws RemoteException;

    void migrateApplicationRestrictions() throws RemoteException;

    int powerOff() throws RemoteException;

    String readFile(String str) throws RemoteException;

    boolean registerSystemUiCallback(IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback) throws RemoteException;

    int removeAutoCallNumber(String str) throws RemoteException;

    int removeDexShortcut(ComponentName componentName) throws RemoteException;

    int removeDexURLShortcut(String str, ComponentName componentName) throws RemoteException;

    int removeFavoriteApp(int i) throws RemoteException;

    int removeLockScreen() throws RemoteException;

    int removePackagesFromUltraPowerSaving(List<String> list) throws RemoteException;

    boolean removeRoleHolder(String str, String str2) throws RemoteException;

    int removeShortcut(String str) throws RemoteException;

    int removeWidget(String str) throws RemoteException;

    int setAccessibilitySettingsItems(int i, int i2) throws RemoteException;

    int setAdbState(boolean z) throws RemoteException;

    int setAirGestureOptionState(int i, boolean z) throws RemoteException;

    int setAppBlockDownloadNamespaces(List<String> list) throws RemoteException;

    int setAppBlockDownloadState(boolean z) throws RemoteException;

    Bundle setApplicationRestrictionsInternal(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i, IApplicationRestrictionsResultCallback iApplicationRestrictionsResultCallback) throws RemoteException;

    int setAppsButtonState(int i) throws RemoteException;

    int setAsoc(int i) throws RemoteException;

    int setAudioVolume(int i, int i2) throws RemoteException;

    int setAutoCallPickupState(int i) throws RemoteException;

    int setAutoRotationState(boolean z, int i) throws RemoteException;

    int setBackupRestoreState(int i, boolean z) throws RemoteException;

    int setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) throws RemoteException;

    int setBluetoothState(boolean z) throws RemoteException;

    int setBootingAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, int i) throws RemoteException;

    int setBootingAnimationSub(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException;

    int setBrightness(int i) throws RemoteException;

    int setBrowserHomepage(String str) throws RemoteException;

    int setCallScreenDisabledItems(boolean z, int i) throws RemoteException;

    int setChargerConnectionSoundEnabledState(boolean z) throws RemoteException;

    int setChargingLEDState(boolean z) throws RemoteException;

    int setCpuPowerSavingState(boolean z) throws RemoteException;

    int setDeveloperOptionsHidden() throws RemoteException;

    int setDeviceSpeakerEnabledState(boolean z) throws RemoteException;

    int setDexForegroundModePackageList(int i, List<String> list) throws RemoteException;

    int setDexHDMIAutoEnterState(int i) throws RemoteException;

    int setDexHomeAlignment(int i) throws RemoteException;

    int setDexLoadingLogo(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int setDexScreenTimeout(int i) throws RemoteException;

    int setDisplayMirroringState(boolean z) throws RemoteException;

    int setEthernetConfiguration(int i, String str, String str2, String str3, String str4) throws RemoteException;

    int setEthernetState(boolean z) throws RemoteException;

    int setExitUI(String str, String str2) throws RemoteException;

    int setExtendedCallInfoState(boolean z) throws RemoteException;

    int setFavoriteApp(String str, int i) throws RemoteException;

    int setFlightModeState(int i) throws RemoteException;

    int setForceAutoShutDownState(int i) throws RemoteException;

    int setForceAutoStartUpState(int i) throws RemoteException;

    int setForceSingleView(boolean z) throws RemoteException;

    int setForcedDisplaySizeDensity(int i, int i2, int i3) throws RemoteException;

    int setGearNotificationState(boolean z) throws RemoteException;

    int setHardKeyIntentBroadcast(boolean z, int i, Intent intent, String str, boolean z2, boolean z3) throws RemoteException;

    int setHardKeyIntentBroadcastExternal(boolean z, int i, int i2, Intent intent, String str, boolean z2) throws RemoteException;

    int setHardKeyIntentBroadcastInternal(String str, boolean z, int i, Intent intent, String str2, boolean z2, boolean z3) throws RemoteException;

    int setHardKeyIntentMode(int i) throws RemoteException;

    int setHardKeyIntentState(boolean z) throws RemoteException;

    int setHardKeyReportState(int i, int i2, int i3, int i4) throws RemoteException;

    int setHideNotificationMessages(int i) throws RemoteException;

    int setHomeActivity(String str) throws RemoteException;

    int setHomeScreenMode(int i) throws RemoteException;

    int setInfraredState(boolean z) throws RemoteException;

    int setInputMethod(String str, boolean z) throws RemoteException;

    int setInputMethodRestrictionState(boolean z) throws RemoteException;

    int setKeyboardMode(int i) throws RemoteException;

    void setKeyedAppStatesReport(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i) throws RemoteException;

    void setKnoxPrivacyPolicyByUserSettings(boolean z) throws RemoteException;

    int setLTESettingState(boolean z) throws RemoteException;

    int setLcdBacklightState(boolean z) throws RemoteException;

    int setLockScreenHiddenItems(boolean z, int i) throws RemoteException;

    int setLockScreenOverrideMode(int i) throws RemoteException;

    int setLockScreenShortcut(int i, String str) throws RemoteException;

    int setLockscreenWallpaper(String str, int i) throws RemoteException;

    int setMobileDataRoamingState(boolean z) throws RemoteException;

    int setMobileDataState(boolean z) throws RemoteException;

    int setMobileNetworkType(int i) throws RemoteException;

    int setMotionControlState(int i, boolean z) throws RemoteException;

    int setMultiWindowState(boolean z) throws RemoteException;

    int setPassCode(String str, String str2) throws RemoteException;

    int setPowerDialogCustomItems(List<PowerItem> list) throws RemoteException;

    int setPowerDialogCustomItemsState(boolean z) throws RemoteException;

    int setPowerDialogItems(int i) throws RemoteException;

    int setPowerDialogOptionMode(int i) throws RemoteException;

    int setPowerMenuLockedState(boolean z) throws RemoteException;

    int setPowerSavingMode(int i) throws RemoteException;

    int setProKioskNotificationMessagesState(boolean z) throws RemoteException;

    int setProKioskPowerDialogCustomItems(List<PowerItem> list) throws RemoteException;

    int setProKioskPowerDialogCustomItemsState(boolean z) throws RemoteException;

    int setProKioskState(boolean z, String str) throws RemoteException;

    int setProKioskStatusBarClockState(boolean z) throws RemoteException;

    int setProKioskStatusBarIconsState(boolean z) throws RemoteException;

    int setProKioskStatusBarMode(int i) throws RemoteException;

    int setProKioskString(int i, String str) throws RemoteException;

    int setProKioskUsbMassStorageState(boolean z) throws RemoteException;

    int setProKioskUsbNetAddresses(String str, String str2) throws RemoteException;

    int setProKioskUsbNetState(boolean z) throws RemoteException;

    int setProtectBatteryState(boolean z) throws RemoteException;

    int setQuickPanelButtons(int i) throws RemoteException;

    int setQuickPanelEditMode(int i) throws RemoteException;

    int setQuickPanelItems(String str) throws RemoteException;

    int setQuickPanelItemsInternal(Bundle bundle) throws RemoteException;

    int setRecentLongPressActivity(String str) throws RemoteException;

    int setRecentLongPressMode(int i) throws RemoteException;

    int setScreenOffOnHomeLongPressState(boolean z) throws RemoteException;

    int setScreenOffOnStatusBarDoubleTapState(boolean z) throws RemoteException;

    int setScreenPowerSavingState(boolean z) throws RemoteException;

    int setScreenTimeout(int i) throws RemoteException;

    int setScreenWakeupOnPowerState(boolean z) throws RemoteException;

    int setSensorDisabled(boolean z, int i) throws RemoteException;

    int setSettingsEnabledItems(boolean z, int i) throws RemoteException;

    int setSettingsHiddenState(boolean z, int i) throws RemoteException;

    int setShowIMEWithHardKeyboard(int i) throws RemoteException;

    int setShuttingDownAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException;

    int setShuttingDownAnimationSub(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int setStatusBarClockState(boolean z) throws RemoteException;

    int setStatusBarIconsState(boolean z) throws RemoteException;

    int setStatusBarMode(int i) throws RemoteException;

    int setStatusBarNotificationsState(boolean z) throws RemoteException;

    int setStatusBarText(String str, int i, int i2) throws RemoteException;

    int setStatusBarTextScrollWidth(String str, int i, int i2, int i3) throws RemoteException;

    int setStayAwakeState(boolean z) throws RemoteException;

    int setSystemLocale(String str, String str2) throws RemoteException;

    int setSystemRingtone(int i, String str) throws RemoteException;

    int setSystemSoundsEnabledState(int i, int i2) throws RemoteException;

    int setSystemSoundsSilent() throws RemoteException;

    int setToastEnabledState(boolean z) throws RemoteException;

    int setToastGravity(int i, int i2, int i3) throws RemoteException;

    int setToastGravityEnabledState(boolean z) throws RemoteException;

    int setToastShowPackageNameState(boolean z) throws RemoteException;

    int setTorchOnVolumeButtonsState(boolean z) throws RemoteException;

    int setUnlockSimOnBootState(boolean z) throws RemoteException;

    int setUnlockSimPin(String str) throws RemoteException;

    int setUsbConnectionType(int i) throws RemoteException;

    int setUsbDeviceDefaultPackage(UsbDevice usbDevice, String str, int i) throws RemoteException;

    int setUsbMassStorageState(boolean z) throws RemoteException;

    int setUsbNetAddresses(String str, String str2) throws RemoteException;

    int setUsbNetState(boolean z) throws RemoteException;

    int setUserInactivityTimeout(int i) throws RemoteException;

    int setVibrationIntensity(int i, int i2) throws RemoteException;

    int setVolumeButtonRotationState(boolean z) throws RemoteException;

    int setVolumeControlStream(int i) throws RemoteException;

    int setVolumeKeyAppState(boolean z) throws RemoteException;

    int setVolumeKeyAppsList(List<String> list) throws RemoteException;

    int setVolumePanelEnabledState(boolean z) throws RemoteException;

    int setWallpaper(Bundle bundle, Rect rect, boolean z, int i) throws RemoteException;

    int setWifiConnectionMonitorState(boolean z) throws RemoteException;

    int setWifiFrequencyBand(int i) throws RemoteException;

    int setWifiHotspotEnabledState(int i) throws RemoteException;

    int setWifiState(boolean z, String str, String str2) throws RemoteException;

    int setWifiStateEap(boolean z, String str, String str2, String str3) throws RemoteException;

    int setZeroPageState(int i) throws RemoteException;

    int startProKioskMode(String str, String str2) throws RemoteException;

    int startSmartView() throws RemoteException;

    int startTcpDump(String str, int i) throws RemoteException;

    boolean stayInDexForegroundMode(ComponentName componentName) throws RemoteException;

    int stopProKioskMode(String str) throws RemoteException;

    int stopTcpDump() throws RemoteException;

    public class Default implements IKnoxCustomManager {
        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int addAutoCallNumber(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int addDexShortcut(int i, int i2, ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int addDexURLShortcut(int i, int i2, String str, String str2, ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int addDexURLShortcutExtend(int i, int i2, String str, String str2, String str3, ComponentName componentName, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int addPackagesToUltraPowerSaving(List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean addRoleHolder(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int addShortcut(int i, int i2, int i3, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int addWidget(int i, int i2, int i3, int i4, int i5, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int allowDexAutoOpenLastApp(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean checkEnterprisePermission(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int clearAnimation(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int clearDexLoadingLogo() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int clearForcedDisplaySizeDensity() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int deleteHomeScreenPage(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int dialEmergencyNumber(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getAccessibilitySettingsItems() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getAirGestureOptionState(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<String> getAppBlockDownloadNamespaces() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getAppBlockDownloadState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public Bundle getApplicationRestrictionsInternal(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getAppsButtonState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getAsoc() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getAutoCallNumberAnswerMode(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getAutoCallNumberDelay(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<String> getAutoCallNumberList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getAutoCallPickupState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getAutoRotationState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getBackupRestoreState(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public StatusbarIconItem getBatteryLevelColourItem() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getBsoh() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getBsohUnbiased() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getCallScreenDisabledItems() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getChargerConnectionSoundEnabledState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getChargingLEDState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getDeviceSpeakerEnabledState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<String> getDexForegroundModePackageList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getDexHDMIAutoEnterState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getDexHomeAlignment() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getDexScreenTimeout() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getDisplayMirroringState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getEthernetConfigurationType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getEthernetState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getExitUI(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getExtendedCallInfoState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getFavoriteApp(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getFavoriteAppsMaxCount() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getForceAutoShutDownState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getForceAutoStartUpState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getForceSingleView() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getGearNotificationState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getHardKeyBlockState(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getHardKeyIntentBroadcast(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getHardKeyIntentMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getHardKeyIntentState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getHardKeyReportState(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getHideNotificationMessages() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getHomeActivity() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getHomeScreenMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getInfraredState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getInputMethodRestrictionState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getKeyboardMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getKeyboardModeOverriden(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getLTESettingState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getLcdBacklightState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getLoadingLogoPath() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getLockScreenHiddenItems() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getLockScreenOverrideMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getLockScreenShortcut(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getMacAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getMobileNetworkType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getMotionControlState(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<PowerItem> getPowerDialogCustomItems() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getPowerDialogCustomItemsState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getPowerDialogItems() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getPowerDialogOptionMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getPowerMenuLockedState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getPowerSavingMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProKioskNotificationMessagesState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<PowerItem> getProKioskPowerDialogCustomItems() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProKioskPowerDialogCustomItemsState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProKioskState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProKioskStatusBarClockState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProKioskStatusBarIconsState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getProKioskStatusBarMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getProKioskString(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProKioskUsbMassStorageState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getProKioskUsbNetAddress(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProKioskUsbNetState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getProtectBatteryState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getQuickPanelButtons() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getQuickPanelEditMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getQuickPanelItems() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getRecentLongPressActivity() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getRecentLongPressMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<String> getRoleHolders(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getScreenOffOnHomeLongPressState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getScreenOffOnStatusBarDoubleTapState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getScreenTimeout() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getScreenWakeupOnPowerState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getSensorDisabled() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getSerialNumber() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getSettingsEnabledItems() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getSettingsHiddenState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getShowIMEWithHardKeyboard() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getStatusBarClockState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getStatusBarIconsState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getStatusBarMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getStatusBarNotificationsState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getStatusBarText() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getStatusBarTextScrollWidth() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getStatusBarTextSize() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getStatusBarTextStyle() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getSystemSoundsEnabledState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public ParcelFileDescriptor getTcpDump() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getToastEnabledState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getToastGravity() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getToastGravityEnabledState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getToastGravityXOffset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getToastGravityYOffset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getToastShowPackageNameState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getTorchOnVolumeButtonsState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<String> getUltraPowerSavingPackages() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getUnlockSimOnBootState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getUnlockSimPin() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getUsbConnectionType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getUsbConnectionTypeInternal() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getUsbMassStorageState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String getUsbNetAddress(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getUsbNetState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getUsbNetStateInternal() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getUserInactivityTimeout() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getVibrationIntensity(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getVolumeButtonRotationState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getVolumeControlStream() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getVolumeKeyAppState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public List<String> getVolumeKeyAppsList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getVolumePanelEnabledState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getWifiConnectionMonitorState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getWifiFrequencyBand() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getWifiHotspotEnabledState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean getWifiState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int getZeroPageState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int isDexAutoOpenLastAppAllowed() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean isKnoxPrivacyPolicyAcceptedInitially() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean isSupportedForceAutoStartUpState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int powerOff() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public String readFile(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean registerSystemUiCallback(IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removeAutoCallNumber(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removeDexShortcut(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removeDexURLShortcut(String str, ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removeFavoriteApp(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removeLockScreen() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removePackagesFromUltraPowerSaving(List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean removeRoleHolder(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removeShortcut(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int removeWidget(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAccessibilitySettingsItems(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAdbState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAirGestureOptionState(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAppBlockDownloadNamespaces(List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAppBlockDownloadState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public Bundle setApplicationRestrictionsInternal(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i, IApplicationRestrictionsResultCallback iApplicationRestrictionsResultCallback) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAppsButtonState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAsoc(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAudioVolume(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAutoCallPickupState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setAutoRotationState(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setBackupRestoreState(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setBluetoothState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setBootingAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setBootingAnimationSub(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setBrightness(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setBrowserHomepage(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setCallScreenDisabledItems(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setChargerConnectionSoundEnabledState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setChargingLEDState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setCpuPowerSavingState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDeveloperOptionsHidden() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDeviceSpeakerEnabledState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDexForegroundModePackageList(int i, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDexHDMIAutoEnterState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDexHomeAlignment(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDexLoadingLogo(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDexScreenTimeout(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setDisplayMirroringState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setEthernetConfiguration(int i, String str, String str2, String str3, String str4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setEthernetState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setExitUI(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setExtendedCallInfoState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setFavoriteApp(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setFlightModeState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setForceAutoShutDownState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setForceAutoStartUpState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setForceSingleView(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setForcedDisplaySizeDensity(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setGearNotificationState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHardKeyIntentBroadcast(boolean z, int i, Intent intent, String str, boolean z2, boolean z3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHardKeyIntentBroadcastExternal(boolean z, int i, int i2, Intent intent, String str, boolean z2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHardKeyIntentBroadcastInternal(String str, boolean z, int i, Intent intent, String str2, boolean z2, boolean z3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHardKeyIntentMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHardKeyIntentState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHardKeyReportState(int i, int i2, int i3, int i4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHideNotificationMessages(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHomeActivity(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setHomeScreenMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setInfraredState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setInputMethod(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setInputMethodRestrictionState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setKeyboardMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setLTESettingState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setLcdBacklightState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setLockScreenHiddenItems(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setLockScreenOverrideMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setLockScreenShortcut(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setLockscreenWallpaper(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setMobileDataRoamingState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setMobileDataState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setMobileNetworkType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setMotionControlState(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setMultiWindowState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setPassCode(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setPowerDialogCustomItems(List<PowerItem> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setPowerDialogCustomItemsState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setPowerDialogItems(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setPowerDialogOptionMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setPowerMenuLockedState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setPowerSavingMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskNotificationMessagesState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskPowerDialogCustomItems(List<PowerItem> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskPowerDialogCustomItemsState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskState(boolean z, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskStatusBarClockState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskStatusBarIconsState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskStatusBarMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskString(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskUsbMassStorageState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskUsbNetAddresses(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProKioskUsbNetState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setProtectBatteryState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setQuickPanelButtons(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setQuickPanelEditMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setQuickPanelItems(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setQuickPanelItemsInternal(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setRecentLongPressActivity(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setRecentLongPressMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setScreenOffOnHomeLongPressState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setScreenOffOnStatusBarDoubleTapState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setScreenPowerSavingState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setScreenTimeout(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setScreenWakeupOnPowerState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setSensorDisabled(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setSettingsEnabledItems(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setSettingsHiddenState(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setShowIMEWithHardKeyboard(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setShuttingDownAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setShuttingDownAnimationSub(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setStatusBarClockState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setStatusBarIconsState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setStatusBarMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setStatusBarNotificationsState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setStatusBarText(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setStatusBarTextScrollWidth(String str, int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setStayAwakeState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setSystemLocale(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setSystemRingtone(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setSystemSoundsEnabledState(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setSystemSoundsSilent() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setToastEnabledState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setToastGravity(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setToastGravityEnabledState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setToastShowPackageNameState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setTorchOnVolumeButtonsState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUnlockSimOnBootState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUnlockSimPin(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUsbConnectionType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUsbDeviceDefaultPackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUsbMassStorageState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUsbNetAddresses(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUsbNetState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setUserInactivityTimeout(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setVibrationIntensity(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setVolumeButtonRotationState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setVolumeControlStream(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setVolumeKeyAppState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setVolumeKeyAppsList(List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setVolumePanelEnabledState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setWallpaper(Bundle bundle, Rect rect, boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setWifiConnectionMonitorState(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setWifiFrequencyBand(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setWifiHotspotEnabledState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setWifiState(boolean z, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setWifiStateEap(boolean z, String str, String str2, String str3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int setZeroPageState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int startProKioskMode(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int startSmartView() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int startTcpDump(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public boolean stayInDexForegroundMode(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int stopProKioskMode(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public int stopTcpDump() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public void migrateApplicationRestrictions() throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public void setKnoxPrivacyPolicyByUserSettings(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.custom.IKnoxCustomManager
        public void setKeyedAppStatesReport(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IKnoxCustomManager {
        public static final int TRANSACTION_addAutoCallNumber = 213;
        public static final int TRANSACTION_addDexShortcut = 247;
        public static final int TRANSACTION_addDexURLShortcut = 249;
        public static final int TRANSACTION_addDexURLShortcutExtend = 250;
        public static final int TRANSACTION_addPackagesToUltraPowerSaving = 139;
        public static final int TRANSACTION_addRoleHolder = 301;
        public static final int TRANSACTION_addShortcut = 230;
        public static final int TRANSACTION_addWidget = 232;
        public static final int TRANSACTION_allowDexAutoOpenLastApp = 260;
        public static final int TRANSACTION_checkEnterprisePermission = 1;
        public static final int TRANSACTION_clearAnimation = 192;
        public static final int TRANSACTION_clearDexLoadingLogo = 255;
        public static final int TRANSACTION_clearForcedDisplaySizeDensity = 277;
        public static final int TRANSACTION_deleteHomeScreenPage = 234;
        public static final int TRANSACTION_dialEmergencyNumber = 2;
        public static final int TRANSACTION_getAccessibilitySettingsItems = 189;
        public static final int TRANSACTION_getAirGestureOptionState = 144;
        public static final int TRANSACTION_getAppBlockDownloadNamespaces = 73;
        public static final int TRANSACTION_getAppBlockDownloadState = 75;
        public static final int TRANSACTION_getApplicationRestrictionsInternal = 297;
        public static final int TRANSACTION_getAppsButtonState = 236;
        public static final int TRANSACTION_getAsoc = 288;
        public static final int TRANSACTION_getAutoCallNumberAnswerMode = 216;
        public static final int TRANSACTION_getAutoCallNumberDelay = 215;
        public static final int TRANSACTION_getAutoCallNumberList = 217;
        public static final int TRANSACTION_getAutoCallPickupState = 219;
        public static final int TRANSACTION_getAutoRotationState = 7;
        public static final int TRANSACTION_getBackupRestoreState = 60;
        public static final int TRANSACTION_getBatteryLevelColourItem = 77;
        public static final int TRANSACTION_getBsoh = 290;
        public static final int TRANSACTION_getBsohUnbiased = 291;
        public static final int TRANSACTION_getCallScreenDisabledItems = 79;
        public static final int TRANSACTION_getChargerConnectionSoundEnabledState = 147;
        public static final int TRANSACTION_getChargingLEDState = 81;
        public static final int TRANSACTION_getDeviceSpeakerEnabledState = 149;
        public static final int TRANSACTION_getDexForegroundModePackageList = 253;
        public static final int TRANSACTION_getDexHDMIAutoEnterState = 263;
        public static final int TRANSACTION_getDexHomeAlignment = 259;
        public static final int TRANSACTION_getDexScreenTimeout = 257;
        public static final int TRANSACTION_getDisplayMirroringState = 151;
        public static final int TRANSACTION_getEthernetConfigurationType = 83;
        public static final int TRANSACTION_getEthernetState = 85;
        public static final int TRANSACTION_getExitUI = 12;
        public static final int TRANSACTION_getExtendedCallInfoState = 14;
        public static final int TRANSACTION_getFavoriteApp = 240;
        public static final int TRANSACTION_getFavoriteAppsMaxCount = 239;
        public static final int TRANSACTION_getForceAutoShutDownState = 228;
        public static final int TRANSACTION_getForceAutoStartUpState = 195;
        public static final int TRANSACTION_getForceSingleView = 280;
        public static final int TRANSACTION_getGearNotificationState = 87;
        public static final int TRANSACTION_getHardKeyBlockState = 271;
        public static final int TRANSACTION_getHardKeyIntentBroadcast = 275;
        public static final int TRANSACTION_getHardKeyIntentMode = 244;
        public static final int TRANSACTION_getHardKeyIntentState = 89;
        public static final int TRANSACTION_getHardKeyReportState = 270;
        public static final int TRANSACTION_getHideNotificationMessages = 62;
        public static final int TRANSACTION_getHomeActivity = 16;
        public static final int TRANSACTION_getHomeScreenMode = 246;
        public static final int TRANSACTION_getInfraredState = 91;
        public static final int TRANSACTION_getInputMethodRestrictionState = 19;
        public static final int TRANSACTION_getKeyboardMode = 153;
        public static final int TRANSACTION_getKeyboardModeOverriden = 154;
        public static final int TRANSACTION_getLTESettingState = 96;
        public static final int TRANSACTION_getLcdBacklightState = 156;
        public static final int TRANSACTION_getLoadingLogoPath = 283;
        public static final int TRANSACTION_getLockScreenHiddenItems = 93;
        public static final int TRANSACTION_getLockScreenOverrideMode = 158;
        public static final int TRANSACTION_getLockScreenShortcut = 223;
        public static final int TRANSACTION_getMacAddress = 220;
        public static final int TRANSACTION_getMobileNetworkType = 198;
        public static final int TRANSACTION_getMotionControlState = 65;
        public static final int TRANSACTION_getPowerDialogCustomItems = 160;
        public static final int TRANSACTION_getPowerDialogCustomItemsState = 162;
        public static final int TRANSACTION_getPowerDialogItems = 24;
        public static final int TRANSACTION_getPowerDialogOptionMode = 26;
        public static final int TRANSACTION_getPowerMenuLockedState = 98;
        public static final int TRANSACTION_getPowerSavingMode = 100;
        public static final int TRANSACTION_getProKioskNotificationMessagesState = 28;
        public static final int TRANSACTION_getProKioskPowerDialogCustomItems = 30;
        public static final int TRANSACTION_getProKioskPowerDialogCustomItemsState = 32;
        public static final int TRANSACTION_getProKioskState = 34;
        public static final int TRANSACTION_getProKioskStatusBarClockState = 36;
        public static final int TRANSACTION_getProKioskStatusBarIconsState = 38;
        public static final int TRANSACTION_getProKioskStatusBarMode = 40;
        public static final int TRANSACTION_getProKioskString = 42;
        public static final int TRANSACTION_getProKioskUsbMassStorageState = 44;
        public static final int TRANSACTION_getProKioskUsbNetAddress = 46;
        public static final int TRANSACTION_getProKioskUsbNetState = 48;
        public static final int TRANSACTION_getProtectBatteryState = 265;
        public static final int TRANSACTION_getQuickPanelButtons = 200;
        public static final int TRANSACTION_getQuickPanelEditMode = 202;
        public static final int TRANSACTION_getQuickPanelItems = 205;
        public static final int TRANSACTION_getRecentLongPressActivity = 102;
        public static final int TRANSACTION_getRecentLongPressMode = 104;
        public static final int TRANSACTION_getRoleHolders = 300;
        public static final int TRANSACTION_getScreenOffOnHomeLongPressState = 106;
        public static final int TRANSACTION_getScreenOffOnStatusBarDoubleTapState = 108;
        public static final int TRANSACTION_getScreenTimeout = 51;
        public static final int TRANSACTION_getScreenWakeupOnPowerState = 110;
        public static final int TRANSACTION_getSensorDisabled = 112;
        public static final int TRANSACTION_getSerialNumber = 142;
        public static final int TRANSACTION_getSettingsEnabledItems = 164;
        public static final int TRANSACTION_getSettingsHiddenState = 67;
        public static final int TRANSACTION_getShowIMEWithHardKeyboard = 267;
        public static final int TRANSACTION_getStatusBarClockState = 166;
        public static final int TRANSACTION_getStatusBarIconsState = 168;
        public static final int TRANSACTION_getStatusBarMode = 170;
        public static final int TRANSACTION_getStatusBarNotificationsState = 172;
        public static final int TRANSACTION_getStatusBarText = 114;
        public static final int TRANSACTION_getStatusBarTextScrollWidth = 174;
        public static final int TRANSACTION_getStatusBarTextSize = 116;
        public static final int TRANSACTION_getStatusBarTextStyle = 115;
        public static final int TRANSACTION_getSystemSoundsEnabledState = 207;
        public static final int TRANSACTION_getTcpDump = 294;
        public static final int TRANSACTION_getToastEnabledState = 118;
        public static final int TRANSACTION_getToastGravity = 120;
        public static final int TRANSACTION_getToastGravityEnabledState = 124;
        public static final int TRANSACTION_getToastGravityXOffset = 121;
        public static final int TRANSACTION_getToastGravityYOffset = 122;
        public static final int TRANSACTION_getToastShowPackageNameState = 126;
        public static final int TRANSACTION_getTorchOnVolumeButtonsState = 128;
        public static final int TRANSACTION_getUltraPowerSavingPackages = 141;
        public static final int TRANSACTION_getUnlockSimOnBootState = 176;
        public static final int TRANSACTION_getUnlockSimPin = 178;
        public static final int TRANSACTION_getUsbConnectionType = 225;
        public static final int TRANSACTION_getUsbConnectionTypeInternal = 226;
        public static final int TRANSACTION_getUsbMassStorageState = 180;
        public static final int TRANSACTION_getUsbNetAddress = 182;
        public static final int TRANSACTION_getUsbNetState = 184;
        public static final int TRANSACTION_getUsbNetStateInternal = 185;
        public static final int TRANSACTION_getUserInactivityTimeout = 56;
        public static final int TRANSACTION_getVibrationIntensity = 209;
        public static final int TRANSACTION_getVolumeButtonRotationState = 130;
        public static final int TRANSACTION_getVolumeControlStream = 132;
        public static final int TRANSACTION_getVolumeKeyAppState = 136;
        public static final int TRANSACTION_getVolumeKeyAppsList = 134;
        public static final int TRANSACTION_getVolumePanelEnabledState = 138;
        public static final int TRANSACTION_getWifiConnectionMonitorState = 71;
        public static final int TRANSACTION_getWifiFrequencyBand = 187;
        public static final int TRANSACTION_getWifiHotspotEnabledState = 211;
        public static final int TRANSACTION_getWifiState = 212;
        public static final int TRANSACTION_getZeroPageState = 242;
        public static final int TRANSACTION_isDexAutoOpenLastAppAllowed = 261;
        public static final int TRANSACTION_isKnoxPrivacyPolicyAcceptedInitially = 303;
        public static final int TRANSACTION_isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings = 304;
        public static final int TRANSACTION_isSupportedForceAutoStartUpState = 196;
        public static final int TRANSACTION_migrateApplicationRestrictions = 299;
        public static final int TRANSACTION_powerOff = 221;
        public static final int TRANSACTION_readFile = 295;
        public static final int TRANSACTION_registerSystemUiCallback = 284;
        public static final int TRANSACTION_removeAutoCallNumber = 214;
        public static final int TRANSACTION_removeDexShortcut = 248;
        public static final int TRANSACTION_removeDexURLShortcut = 251;
        public static final int TRANSACTION_removeFavoriteApp = 238;
        public static final int TRANSACTION_removeLockScreen = 3;
        public static final int TRANSACTION_removePackagesFromUltraPowerSaving = 140;
        public static final int TRANSACTION_removeRoleHolder = 302;
        public static final int TRANSACTION_removeShortcut = 231;
        public static final int TRANSACTION_removeWidget = 233;
        public static final int TRANSACTION_setAccessibilitySettingsItems = 188;
        public static final int TRANSACTION_setAdbState = 4;
        public static final int TRANSACTION_setAirGestureOptionState = 143;
        public static final int TRANSACTION_setAppBlockDownloadNamespaces = 72;
        public static final int TRANSACTION_setAppBlockDownloadState = 74;
        public static final int TRANSACTION_setApplicationRestrictionsInternal = 296;
        public static final int TRANSACTION_setAppsButtonState = 235;
        public static final int TRANSACTION_setAsoc = 289;
        public static final int TRANSACTION_setAudioVolume = 5;
        public static final int TRANSACTION_setAutoCallPickupState = 218;
        public static final int TRANSACTION_setAutoRotationState = 6;
        public static final int TRANSACTION_setBackupRestoreState = 59;
        public static final int TRANSACTION_setBatteryLevelColourItem = 76;
        public static final int TRANSACTION_setBluetoothState = 8;
        public static final int TRANSACTION_setBootingAnimation = 190;
        public static final int TRANSACTION_setBootingAnimationSub = 281;
        public static final int TRANSACTION_setBrightness = 229;
        public static final int TRANSACTION_setBrowserHomepage = 145;
        public static final int TRANSACTION_setCallScreenDisabledItems = 78;
        public static final int TRANSACTION_setChargerConnectionSoundEnabledState = 146;
        public static final int TRANSACTION_setChargingLEDState = 80;
        public static final int TRANSACTION_setCpuPowerSavingState = 9;
        public static final int TRANSACTION_setDeveloperOptionsHidden = 10;
        public static final int TRANSACTION_setDeviceSpeakerEnabledState = 148;
        public static final int TRANSACTION_setDexForegroundModePackageList = 252;
        public static final int TRANSACTION_setDexHDMIAutoEnterState = 262;
        public static final int TRANSACTION_setDexHomeAlignment = 258;
        public static final int TRANSACTION_setDexLoadingLogo = 254;
        public static final int TRANSACTION_setDexScreenTimeout = 256;
        public static final int TRANSACTION_setDisplayMirroringState = 150;
        public static final int TRANSACTION_setEthernetConfiguration = 82;
        public static final int TRANSACTION_setEthernetState = 84;
        public static final int TRANSACTION_setExitUI = 11;
        public static final int TRANSACTION_setExtendedCallInfoState = 13;
        public static final int TRANSACTION_setFavoriteApp = 237;
        public static final int TRANSACTION_setFlightModeState = 193;
        public static final int TRANSACTION_setForceAutoShutDownState = 227;
        public static final int TRANSACTION_setForceAutoStartUpState = 194;
        public static final int TRANSACTION_setForceSingleView = 279;
        public static final int TRANSACTION_setForcedDisplaySizeDensity = 276;
        public static final int TRANSACTION_setGearNotificationState = 86;
        public static final int TRANSACTION_setHardKeyIntentBroadcast = 272;
        public static final int TRANSACTION_setHardKeyIntentBroadcastExternal = 273;
        public static final int TRANSACTION_setHardKeyIntentBroadcastInternal = 274;
        public static final int TRANSACTION_setHardKeyIntentMode = 243;
        public static final int TRANSACTION_setHardKeyIntentState = 88;
        public static final int TRANSACTION_setHardKeyReportState = 269;
        public static final int TRANSACTION_setHideNotificationMessages = 61;
        public static final int TRANSACTION_setHomeActivity = 15;
        public static final int TRANSACTION_setHomeScreenMode = 245;
        public static final int TRANSACTION_setInfraredState = 90;
        public static final int TRANSACTION_setInputMethod = 17;
        public static final int TRANSACTION_setInputMethodRestrictionState = 18;
        public static final int TRANSACTION_setKeyboardMode = 152;
        public static final int TRANSACTION_setKeyedAppStatesReport = 298;
        public static final int TRANSACTION_setKnoxPrivacyPolicyByUserSettings = 305;
        public static final int TRANSACTION_setLTESettingState = 95;
        public static final int TRANSACTION_setLcdBacklightState = 155;
        public static final int TRANSACTION_setLockScreenHiddenItems = 92;
        public static final int TRANSACTION_setLockScreenOverrideMode = 157;
        public static final int TRANSACTION_setLockScreenShortcut = 222;
        public static final int TRANSACTION_setLockscreenWallpaper = 94;
        public static final int TRANSACTION_setMobileDataRoamingState = 63;
        public static final int TRANSACTION_setMobileDataState = 20;
        public static final int TRANSACTION_setMobileNetworkType = 197;
        public static final int TRANSACTION_setMotionControlState = 64;
        public static final int TRANSACTION_setMultiWindowState = 21;
        public static final int TRANSACTION_setPassCode = 22;
        public static final int TRANSACTION_setPowerDialogCustomItems = 159;
        public static final int TRANSACTION_setPowerDialogCustomItemsState = 161;
        public static final int TRANSACTION_setPowerDialogItems = 23;
        public static final int TRANSACTION_setPowerDialogOptionMode = 25;
        public static final int TRANSACTION_setPowerMenuLockedState = 97;
        public static final int TRANSACTION_setPowerSavingMode = 99;
        public static final int TRANSACTION_setProKioskNotificationMessagesState = 27;
        public static final int TRANSACTION_setProKioskPowerDialogCustomItems = 29;
        public static final int TRANSACTION_setProKioskPowerDialogCustomItemsState = 31;
        public static final int TRANSACTION_setProKioskState = 33;
        public static final int TRANSACTION_setProKioskStatusBarClockState = 35;
        public static final int TRANSACTION_setProKioskStatusBarIconsState = 37;
        public static final int TRANSACTION_setProKioskStatusBarMode = 39;
        public static final int TRANSACTION_setProKioskString = 41;
        public static final int TRANSACTION_setProKioskUsbMassStorageState = 43;
        public static final int TRANSACTION_setProKioskUsbNetAddresses = 45;
        public static final int TRANSACTION_setProKioskUsbNetState = 47;
        public static final int TRANSACTION_setProtectBatteryState = 264;
        public static final int TRANSACTION_setQuickPanelButtons = 199;
        public static final int TRANSACTION_setQuickPanelEditMode = 201;
        public static final int TRANSACTION_setQuickPanelItems = 203;
        public static final int TRANSACTION_setQuickPanelItemsInternal = 204;
        public static final int TRANSACTION_setRecentLongPressActivity = 101;
        public static final int TRANSACTION_setRecentLongPressMode = 103;
        public static final int TRANSACTION_setScreenOffOnHomeLongPressState = 105;
        public static final int TRANSACTION_setScreenOffOnStatusBarDoubleTapState = 107;
        public static final int TRANSACTION_setScreenPowerSavingState = 49;
        public static final int TRANSACTION_setScreenTimeout = 50;
        public static final int TRANSACTION_setScreenWakeupOnPowerState = 109;
        public static final int TRANSACTION_setSensorDisabled = 111;
        public static final int TRANSACTION_setSettingsEnabledItems = 163;
        public static final int TRANSACTION_setSettingsHiddenState = 66;
        public static final int TRANSACTION_setShowIMEWithHardKeyboard = 266;
        public static final int TRANSACTION_setShuttingDownAnimation = 191;
        public static final int TRANSACTION_setShuttingDownAnimationSub = 282;
        public static final int TRANSACTION_setStatusBarClockState = 165;
        public static final int TRANSACTION_setStatusBarIconsState = 167;
        public static final int TRANSACTION_setStatusBarMode = 169;
        public static final int TRANSACTION_setStatusBarNotificationsState = 171;
        public static final int TRANSACTION_setStatusBarText = 113;
        public static final int TRANSACTION_setStatusBarTextScrollWidth = 173;
        public static final int TRANSACTION_setStayAwakeState = 68;
        public static final int TRANSACTION_setSystemLocale = 52;
        public static final int TRANSACTION_setSystemRingtone = 53;
        public static final int TRANSACTION_setSystemSoundsEnabledState = 206;
        public static final int TRANSACTION_setSystemSoundsSilent = 69;
        public static final int TRANSACTION_setToastEnabledState = 117;
        public static final int TRANSACTION_setToastGravity = 119;
        public static final int TRANSACTION_setToastGravityEnabledState = 123;
        public static final int TRANSACTION_setToastShowPackageNameState = 125;
        public static final int TRANSACTION_setTorchOnVolumeButtonsState = 127;
        public static final int TRANSACTION_setUnlockSimOnBootState = 175;
        public static final int TRANSACTION_setUnlockSimPin = 177;
        public static final int TRANSACTION_setUsbConnectionType = 224;
        public static final int TRANSACTION_setUsbDeviceDefaultPackage = 54;
        public static final int TRANSACTION_setUsbMassStorageState = 179;
        public static final int TRANSACTION_setUsbNetAddresses = 181;
        public static final int TRANSACTION_setUsbNetState = 183;
        public static final int TRANSACTION_setUserInactivityTimeout = 55;
        public static final int TRANSACTION_setVibrationIntensity = 208;
        public static final int TRANSACTION_setVolumeButtonRotationState = 129;
        public static final int TRANSACTION_setVolumeControlStream = 131;
        public static final int TRANSACTION_setVolumeKeyAppState = 135;
        public static final int TRANSACTION_setVolumeKeyAppsList = 133;
        public static final int TRANSACTION_setVolumePanelEnabledState = 137;
        public static final int TRANSACTION_setWallpaper = 268;
        public static final int TRANSACTION_setWifiConnectionMonitorState = 70;
        public static final int TRANSACTION_setWifiFrequencyBand = 186;
        public static final int TRANSACTION_setWifiHotspotEnabledState = 210;
        public static final int TRANSACTION_setWifiState = 57;
        public static final int TRANSACTION_setWifiStateEap = 58;
        public static final int TRANSACTION_setZeroPageState = 241;
        public static final int TRANSACTION_startProKioskMode = 285;
        public static final int TRANSACTION_startSmartView = 278;
        public static final int TRANSACTION_startTcpDump = 292;
        public static final int TRANSACTION_stayInDexForegroundMode = 287;
        public static final int TRANSACTION_stopProKioskMode = 286;
        public static final int TRANSACTION_stopTcpDump = 293;

        class Proxy implements IKnoxCustomManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int addAutoCallNumber(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(Stub.TRANSACTION_addAutoCallNumber, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int addDexShortcut(int i, int i2, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(Stub.TRANSACTION_addDexShortcut, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int addDexURLShortcut(int i, int i2, String str, String str2, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(249, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int addDexURLShortcutExtend(int i, int i2, String str, String str2, String str3, ComponentName componentName, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(Stub.TRANSACTION_addDexURLShortcutExtend, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int addPackagesToUltraPowerSaving(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean addRoleHolder(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(301, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int addShortcut(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int addWidget(int i, int i2, int i3, int i4, int i5, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_addWidget, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int allowDexAutoOpenLastApp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(260, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean checkEnterprisePermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int clearAnimation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int clearDexLoadingLogo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(255, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int clearForcedDisplaySizeDensity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_clearForcedDisplaySizeDensity, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int deleteHomeScreenPage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_deleteHomeScreenPage, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int dialEmergencyNumber(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getAccessibilitySettingsItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getAirGestureOptionState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<String> getAppBlockDownloadNamespaces() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getAppBlockDownloadState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public Bundle getApplicationRestrictionsInternal(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_getApplicationRestrictionsInternal, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getAppsButtonState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getAppsButtonState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getAsoc() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getAsoc, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getAutoCallNumberAnswerMode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_getAutoCallNumberAnswerMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getAutoCallNumberDelay(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_getAutoCallNumberDelay, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<String> getAutoCallNumberList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getAutoCallNumberList, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getAutoCallPickupState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getAutoCallPickupState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getAutoRotationState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getBackupRestoreState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public StatusbarIconItem getBatteryLevelColourItem() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusbarIconItem) parcelObtain2.readTypedObject(StatusbarIconItem.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getBsoh() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getBsoh, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getBsohUnbiased() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getBsohUnbiased, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getCallScreenDisabledItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getChargerConnectionSoundEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getChargingLEDState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getDeviceSpeakerEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<String> getDexForegroundModePackageList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getDexForegroundModePackageList, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getDexHDMIAutoEnterState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(263, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getDexHomeAlignment() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(259, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getDexScreenTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(257, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getDisplayMirroringState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getEthernetConfigurationType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getEthernetState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getExitUI(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getExtendedCallInfoState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getFavoriteApp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_getFavoriteApp, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getFavoriteAppsMaxCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getFavoriteAppsMaxCount, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getForceAutoShutDownState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getForceAutoShutDownState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getForceAutoStartUpState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getForceSingleView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getForceSingleView, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getGearNotificationState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getHardKeyBlockState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(271, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getHardKeyIntentBroadcast(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(Stub.TRANSACTION_getHardKeyIntentBroadcast, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getHardKeyIntentMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getHardKeyIntentMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getHardKeyIntentState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getHardKeyReportState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(270, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getHideNotificationMessages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getHomeActivity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getHomeScreenMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getHomeScreenMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getInfraredState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getInputMethodRestrictionState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxCustomManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getKeyboardMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getKeyboardModeOverriden(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getLTESettingState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getLcdBacklightState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getLoadingLogoPath() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getLoadingLogoPath, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getLockScreenHiddenItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getLockScreenOverrideMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getLockScreenShortcut(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_getLockScreenShortcut, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getMacAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(220, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getMobileNetworkType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getMotionControlState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<PowerItem> getPowerDialogCustomItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PowerItem.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getPowerDialogCustomItemsState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getPowerDialogItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getPowerDialogOptionMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getPowerMenuLockedState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getPowerSavingMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProKioskNotificationMessagesState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<PowerItem> getProKioskPowerDialogCustomItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PowerItem.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProKioskPowerDialogCustomItemsState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProKioskState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProKioskStatusBarClockState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProKioskStatusBarIconsState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getProKioskStatusBarMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getProKioskString(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProKioskUsbMassStorageState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getProKioskUsbNetAddress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProKioskUsbNetState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getProtectBatteryState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(265, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getQuickPanelButtons() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getQuickPanelEditMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getQuickPanelItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getRecentLongPressActivity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getRecentLongPressMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<String> getRoleHolders(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(300, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getScreenOffOnHomeLongPressState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getScreenOffOnStatusBarDoubleTapState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getScreenTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getScreenWakeupOnPowerState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getSensorDisabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getSerialNumber() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getSettingsEnabledItems() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getSettingsHiddenState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getShowIMEWithHardKeyboard() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(267, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getStatusBarClockState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getStatusBarIconsState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getStatusBarMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getStatusBarNotificationsState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getStatusBarText() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getStatusBarTextScrollWidth() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getStatusBarTextSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getStatusBarTextStyle() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getSystemSoundsEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public ParcelFileDescriptor getTcpDump() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getTcpDump, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getToastEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getToastGravity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getToastGravityEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getToastGravityXOffset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getToastGravityYOffset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getToastShowPackageNameState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getTorchOnVolumeButtonsState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<String> getUltraPowerSavingPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getUnlockSimOnBootState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getUnlockSimPin() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getUsbConnectionType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getUsbConnectionType, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getUsbConnectionTypeInternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getUsbConnectionTypeInternal, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getUsbMassStorageState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String getUsbNetAddress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getUsbNetState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getUsbNetStateInternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getUserInactivityTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getVibrationIntensity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getVolumeButtonRotationState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getVolumeControlStream() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getVolumeKeyAppState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public List<String> getVolumeKeyAppsList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getVolumePanelEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getWifiConnectionMonitorState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getWifiFrequencyBand() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getWifiHotspotEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getWifiHotspotEnabledState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean getWifiState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getWifiState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int getZeroPageState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getZeroPageState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int isDexAutoOpenLastAppAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(261, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean isKnoxPrivacyPolicyAcceptedInitially() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(303, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(304, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean isSupportedForceAutoStartUpState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public void migrateApplicationRestrictions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_migrateApplicationRestrictions, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int powerOff() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(221, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public String readFile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_readFile, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean registerSystemUiCallback(IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKnoxCustomManagerSystemUiCallback);
                    this.mRemote.transact(Stub.TRANSACTION_registerSystemUiCallback, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removeAutoCallNumber(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_removeAutoCallNumber, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removeDexShortcut(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(Stub.TRANSACTION_removeDexShortcut, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removeDexURLShortcut(String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(Stub.TRANSACTION_removeDexURLShortcut, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removeFavoriteApp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_removeFavoriteApp, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removeLockScreen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removePackagesFromUltraPowerSaving(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean removeRoleHolder(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(302, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removeShortcut(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_removeShortcut, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int removeWidget(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_removeWidget, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAccessibilitySettingsItems(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAdbState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAirGestureOptionState(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAppBlockDownloadNamespaces(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAppBlockDownloadState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public Bundle setApplicationRestrictionsInternal(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i, IApplicationRestrictionsResultCallback iApplicationRestrictionsResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iApplicationRestrictionsResultCallback);
                    this.mRemote.transact(Stub.TRANSACTION_setApplicationRestrictionsInternal, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAppsButtonState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setAppsButtonState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAsoc(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setAsoc, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAudioVolume(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAutoCallPickupState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setAutoCallPickupState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setAutoRotationState(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setBackupRestoreState(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusbarIconItem, 0);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setBluetoothState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setBootingAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor3, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setBootingAnimationSub(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    this.mRemote.transact(Stub.TRANSACTION_setBootingAnimationSub, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setBrightness(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setBrightness, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setBrowserHomepage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setCallScreenDisabledItems(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setChargerConnectionSoundEnabledState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setChargingLEDState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setCpuPowerSavingState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDeveloperOptionsHidden() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDeviceSpeakerEnabledState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDexForegroundModePackageList(int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(Stub.TRANSACTION_setDexForegroundModePackageList, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDexHDMIAutoEnterState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(262, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDexHomeAlignment(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(258, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDexLoadingLogo(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(254, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDexScreenTimeout(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(256, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setDisplayMirroringState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setEthernetConfiguration(int i, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setEthernetState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setExitUI(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setExtendedCallInfoState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setFavoriteApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setFavoriteApp, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setFlightModeState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setForceAutoShutDownState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setForceAutoShutDownState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setForceAutoStartUpState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setForceSingleView(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(Stub.TRANSACTION_setForceSingleView, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setForcedDisplaySizeDensity(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(Stub.TRANSACTION_setForcedDisplaySizeDensity, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setGearNotificationState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHardKeyIntentBroadcast(boolean z, int i, Intent intent, String str, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(272, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHardKeyIntentBroadcastExternal(boolean z, int i, int i2, Intent intent, String str, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(Stub.TRANSACTION_setHardKeyIntentBroadcastExternal, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHardKeyIntentBroadcastInternal(String str, boolean z, int i, Intent intent, String str2, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(Stub.TRANSACTION_setHardKeyIntentBroadcastInternal, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHardKeyIntentMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setHardKeyIntentMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHardKeyIntentState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHardKeyReportState(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(269, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHideNotificationMessages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHomeActivity(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setHomeScreenMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setHomeScreenMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setInfraredState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setInputMethod(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setInputMethodRestrictionState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setKeyboardMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public void setKeyedAppStatesReport(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setKeyedAppStatesReport, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public void setKnoxPrivacyPolicyByUserSettings(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(305, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setLTESettingState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setLcdBacklightState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setLockScreenHiddenItems(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setLockScreenOverrideMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setLockScreenShortcut(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(222, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setLockscreenWallpaper(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setMobileDataRoamingState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setMobileDataState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setMobileNetworkType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setMotionControlState(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setMultiWindowState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setPassCode(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setPowerDialogCustomItems(List<PowerItem> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setPowerDialogCustomItemsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setPowerDialogItems(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setPowerDialogOptionMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setPowerMenuLockedState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setPowerSavingMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskNotificationMessagesState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskPowerDialogCustomItems(List<PowerItem> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskPowerDialogCustomItemsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskState(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskStatusBarClockState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskStatusBarIconsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskStatusBarMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskString(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskUsbMassStorageState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskUsbNetAddresses(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProKioskUsbNetState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setProtectBatteryState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(264, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setQuickPanelButtons(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setQuickPanelEditMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setQuickPanelItems(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setQuickPanelItemsInternal(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setRecentLongPressActivity(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setRecentLongPressMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setScreenOffOnHomeLongPressState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setScreenOffOnStatusBarDoubleTapState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setScreenPowerSavingState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setScreenTimeout(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setScreenWakeupOnPowerState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setSensorDisabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setSettingsEnabledItems(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setSettingsHiddenState(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setShowIMEWithHardKeyboard(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(266, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setShuttingDownAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setShuttingDownAnimationSub(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(Stub.TRANSACTION_setShuttingDownAnimationSub, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setStatusBarClockState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setStatusBarIconsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setStatusBarMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setStatusBarNotificationsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setStatusBarText(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setStatusBarTextScrollWidth(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setStayAwakeState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setSystemLocale(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setSystemRingtone(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setSystemSoundsEnabledState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setSystemSoundsSilent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setToastEnabledState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setToastGravity(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setToastGravityEnabledState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setToastShowPackageNameState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setTorchOnVolumeButtonsState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUnlockSimOnBootState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUnlockSimPin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUsbConnectionType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setUsbConnectionType, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUsbDeviceDefaultPackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUsbMassStorageState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUsbNetAddresses(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUsbNetState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setUserInactivityTimeout(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setVibrationIntensity(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setVolumeButtonRotationState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setVolumeControlStream(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setVolumeKeyAppState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setVolumeKeyAppsList(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setVolumePanelEnabledState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setWallpaper(Bundle bundle, Rect rect, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(268, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setWifiConnectionMonitorState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setWifiFrequencyBand(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setWifiHotspotEnabledState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setWifiState(boolean z, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setWifiStateEap(boolean z, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int setZeroPageState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setZeroPageState, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int startProKioskMode(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(Stub.TRANSACTION_startProKioskMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int startSmartView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_startSmartView, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int startTcpDump(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_startTcpDump, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public boolean stayInDexForegroundMode(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(Stub.TRANSACTION_stayInDexForegroundMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int stopProKioskMode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_stopProKioskMode, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.custom.IKnoxCustomManager
            public int stopTcpDump() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxCustomManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_stopTcpDump, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxCustomManager.DESCRIPTOR);
        }

        public static IKnoxCustomManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxCustomManager.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxCustomManager)) ? new Proxy(iBinder) : (IKnoxCustomManager) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkEnterprisePermission";
                case 2:
                    return "dialEmergencyNumber";
                case 3:
                    return "removeLockScreen";
                case 4:
                    return "setAdbState";
                case 5:
                    return "setAudioVolume";
                case 6:
                    return "setAutoRotationState";
                case 7:
                    return "getAutoRotationState";
                case 8:
                    return "setBluetoothState";
                case 9:
                    return "setCpuPowerSavingState";
                case 10:
                    return "setDeveloperOptionsHidden";
                case 11:
                    return "setExitUI";
                case 12:
                    return "getExitUI";
                case 13:
                    return "setExtendedCallInfoState";
                case 14:
                    return "getExtendedCallInfoState";
                case 15:
                    return "setHomeActivity";
                case 16:
                    return "getHomeActivity";
                case 17:
                    return "setInputMethod";
                case 18:
                    return "setInputMethodRestrictionState";
                case 19:
                    return "getInputMethodRestrictionState";
                case 20:
                    return "setMobileDataState";
                case 21:
                    return "setMultiWindowState";
                case 22:
                    return "setPassCode";
                case 23:
                    return "setPowerDialogItems";
                case 24:
                    return "getPowerDialogItems";
                case 25:
                    return "setPowerDialogOptionMode";
                case 26:
                    return "getPowerDialogOptionMode";
                case 27:
                    return "setProKioskNotificationMessagesState";
                case 28:
                    return "getProKioskNotificationMessagesState";
                case 29:
                    return "setProKioskPowerDialogCustomItems";
                case 30:
                    return "getProKioskPowerDialogCustomItems";
                case 31:
                    return "setProKioskPowerDialogCustomItemsState";
                case 32:
                    return "getProKioskPowerDialogCustomItemsState";
                case 33:
                    return "setProKioskState";
                case 34:
                    return "getProKioskState";
                case 35:
                    return "setProKioskStatusBarClockState";
                case 36:
                    return "getProKioskStatusBarClockState";
                case 37:
                    return "setProKioskStatusBarIconsState";
                case 38:
                    return "getProKioskStatusBarIconsState";
                case 39:
                    return "setProKioskStatusBarMode";
                case 40:
                    return "getProKioskStatusBarMode";
                case 41:
                    return "setProKioskString";
                case 42:
                    return "getProKioskString";
                case 43:
                    return "setProKioskUsbMassStorageState";
                case 44:
                    return "getProKioskUsbMassStorageState";
                case 45:
                    return "setProKioskUsbNetAddresses";
                case 46:
                    return "getProKioskUsbNetAddress";
                case 47:
                    return "setProKioskUsbNetState";
                case 48:
                    return "getProKioskUsbNetState";
                case 49:
                    return "setScreenPowerSavingState";
                case 50:
                    return "setScreenTimeout";
                case 51:
                    return "getScreenTimeout";
                case 52:
                    return "setSystemLocale";
                case 53:
                    return "setSystemRingtone";
                case 54:
                    return "setUsbDeviceDefaultPackage";
                case 55:
                    return "setUserInactivityTimeout";
                case 56:
                    return "getUserInactivityTimeout";
                case 57:
                    return "setWifiState";
                case 58:
                    return "setWifiStateEap";
                case 59:
                    return "setBackupRestoreState";
                case 60:
                    return "getBackupRestoreState";
                case 61:
                    return "setHideNotificationMessages";
                case 62:
                    return "getHideNotificationMessages";
                case 63:
                    return "setMobileDataRoamingState";
                case 64:
                    return "setMotionControlState";
                case 65:
                    return "getMotionControlState";
                case 66:
                    return "setSettingsHiddenState";
                case 67:
                    return "getSettingsHiddenState";
                case 68:
                    return "setStayAwakeState";
                case 69:
                    return "setSystemSoundsSilent";
                case 70:
                    return "setWifiConnectionMonitorState";
                case 71:
                    return "getWifiConnectionMonitorState";
                case 72:
                    return "setAppBlockDownloadNamespaces";
                case 73:
                    return "getAppBlockDownloadNamespaces";
                case 74:
                    return "setAppBlockDownloadState";
                case 75:
                    return "getAppBlockDownloadState";
                case 76:
                    return "setBatteryLevelColourItem";
                case 77:
                    return "getBatteryLevelColourItem";
                case 78:
                    return "setCallScreenDisabledItems";
                case 79:
                    return "getCallScreenDisabledItems";
                case 80:
                    return "setChargingLEDState";
                case 81:
                    return "getChargingLEDState";
                case 82:
                    return "setEthernetConfiguration";
                case 83:
                    return "getEthernetConfigurationType";
                case 84:
                    return "setEthernetState";
                case 85:
                    return "getEthernetState";
                case 86:
                    return "setGearNotificationState";
                case 87:
                    return "getGearNotificationState";
                case 88:
                    return "setHardKeyIntentState";
                case 89:
                    return CustomDeviceManager.INTENT_STATE_API_ENABLED;
                case 90:
                    return "setInfraredState";
                case 91:
                    return "getInfraredState";
                case 92:
                    return "setLockScreenHiddenItems";
                case 93:
                    return "getLockScreenHiddenItems";
                case 94:
                    return "setLockscreenWallpaper";
                case 95:
                    return "setLTESettingState";
                case 96:
                    return "getLTESettingState";
                case 97:
                    return "setPowerMenuLockedState";
                case 98:
                    return "getPowerMenuLockedState";
                case 99:
                    return "setPowerSavingMode";
                case 100:
                    return "getPowerSavingMode";
                case 101:
                    return "setRecentLongPressActivity";
                case 102:
                    return "getRecentLongPressActivity";
                case 103:
                    return "setRecentLongPressMode";
                case 104:
                    return "getRecentLongPressMode";
                case 105:
                    return "setScreenOffOnHomeLongPressState";
                case 106:
                    return "getScreenOffOnHomeLongPressState";
                case 107:
                    return "setScreenOffOnStatusBarDoubleTapState";
                case 108:
                    return "getScreenOffOnStatusBarDoubleTapState";
                case 109:
                    return "setScreenWakeupOnPowerState";
                case 110:
                    return "getScreenWakeupOnPowerState";
                case 111:
                    return "setSensorDisabled";
                case 112:
                    return "getSensorDisabled";
                case 113:
                    return "setStatusBarText";
                case 114:
                    return "getStatusBarText";
                case 115:
                    return "getStatusBarTextStyle";
                case 116:
                    return "getStatusBarTextSize";
                case 117:
                    return "setToastEnabledState";
                case 118:
                    return "getToastEnabledState";
                case 119:
                    return "setToastGravity";
                case 120:
                    return "getToastGravity";
                case 121:
                    return "getToastGravityXOffset";
                case 122:
                    return "getToastGravityYOffset";
                case 123:
                    return "setToastGravityEnabledState";
                case 124:
                    return "getToastGravityEnabledState";
                case 125:
                    return "setToastShowPackageNameState";
                case 126:
                    return "getToastShowPackageNameState";
                case 127:
                    return "setTorchOnVolumeButtonsState";
                case 128:
                    return "getTorchOnVolumeButtonsState";
                case 129:
                    return "setVolumeButtonRotationState";
                case 130:
                    return "getVolumeButtonRotationState";
                case 131:
                    return "setVolumeControlStream";
                case 132:
                    return "getVolumeControlStream";
                case 133:
                    return "setVolumeKeyAppsList";
                case 134:
                    return "getVolumeKeyAppsList";
                case 135:
                    return "setVolumeKeyAppState";
                case 136:
                    return "getVolumeKeyAppState";
                case 137:
                    return "setVolumePanelEnabledState";
                case 138:
                    return "getVolumePanelEnabledState";
                case 139:
                    return "addPackagesToUltraPowerSaving";
                case 140:
                    return "removePackagesFromUltraPowerSaving";
                case 141:
                    return "getUltraPowerSavingPackages";
                case 142:
                    return "getSerialNumber";
                case 143:
                    return "setAirGestureOptionState";
                case 144:
                    return "getAirGestureOptionState";
                case 145:
                    return "setBrowserHomepage";
                case 146:
                    return "setChargerConnectionSoundEnabledState";
                case 147:
                    return "getChargerConnectionSoundEnabledState";
                case 148:
                    return "setDeviceSpeakerEnabledState";
                case 149:
                    return "getDeviceSpeakerEnabledState";
                case 150:
                    return "setDisplayMirroringState";
                case 151:
                    return "getDisplayMirroringState";
                case 152:
                    return "setKeyboardMode";
                case 153:
                    return "getKeyboardMode";
                case 154:
                    return "getKeyboardModeOverriden";
                case 155:
                    return "setLcdBacklightState";
                case 156:
                    return "getLcdBacklightState";
                case 157:
                    return "setLockScreenOverrideMode";
                case 158:
                    return "getLockScreenOverrideMode";
                case 159:
                    return "setPowerDialogCustomItems";
                case 160:
                    return "getPowerDialogCustomItems";
                case 161:
                    return "setPowerDialogCustomItemsState";
                case 162:
                    return "getPowerDialogCustomItemsState";
                case 163:
                    return "setSettingsEnabledItems";
                case 164:
                    return "getSettingsEnabledItems";
                case 165:
                    return "setStatusBarClockState";
                case 166:
                    return "getStatusBarClockState";
                case 167:
                    return "setStatusBarIconsState";
                case 168:
                    return "getStatusBarIconsState";
                case 169:
                    return "setStatusBarMode";
                case 170:
                    return "getStatusBarMode";
                case 171:
                    return "setStatusBarNotificationsState";
                case 172:
                    return "getStatusBarNotificationsState";
                case 173:
                    return "setStatusBarTextScrollWidth";
                case 174:
                    return "getStatusBarTextScrollWidth";
                case 175:
                    return "setUnlockSimOnBootState";
                case 176:
                    return "getUnlockSimOnBootState";
                case 177:
                    return "setUnlockSimPin";
                case 178:
                    return "getUnlockSimPin";
                case 179:
                    return "setUsbMassStorageState";
                case 180:
                    return "getUsbMassStorageState";
                case 181:
                    return "setUsbNetAddresses";
                case 182:
                    return "getUsbNetAddress";
                case 183:
                    return "setUsbNetState";
                case 184:
                    return "getUsbNetState";
                case 185:
                    return "getUsbNetStateInternal";
                case 186:
                    return "setWifiFrequencyBand";
                case 187:
                    return "getWifiFrequencyBand";
                case 188:
                    return "setAccessibilitySettingsItems";
                case 189:
                    return "getAccessibilitySettingsItems";
                case 190:
                    return "setBootingAnimation";
                case 191:
                    return "setShuttingDownAnimation";
                case 192:
                    return "clearAnimation";
                case 193:
                    return "setFlightModeState";
                case 194:
                    return "setForceAutoStartUpState";
                case 195:
                    return "getForceAutoStartUpState";
                case 196:
                    return "isSupportedForceAutoStartUpState";
                case 197:
                    return "setMobileNetworkType";
                case 198:
                    return "getMobileNetworkType";
                case 199:
                    return "setQuickPanelButtons";
                case 200:
                    return "getQuickPanelButtons";
                case 201:
                    return "setQuickPanelEditMode";
                case 202:
                    return "getQuickPanelEditMode";
                case 203:
                    return "setQuickPanelItems";
                case 204:
                    return "setQuickPanelItemsInternal";
                case 205:
                    return "getQuickPanelItems";
                case 206:
                    return "setSystemSoundsEnabledState";
                case 207:
                    return "getSystemSoundsEnabledState";
                case 208:
                    return "setVibrationIntensity";
                case 209:
                    return "getVibrationIntensity";
                case 210:
                    return "setWifiHotspotEnabledState";
                case TRANSACTION_getWifiHotspotEnabledState /* 211 */:
                    return "getWifiHotspotEnabledState";
                case TRANSACTION_getWifiState /* 212 */:
                    return "getWifiState";
                case TRANSACTION_addAutoCallNumber /* 213 */:
                    return "addAutoCallNumber";
                case TRANSACTION_removeAutoCallNumber /* 214 */:
                    return "removeAutoCallNumber";
                case TRANSACTION_getAutoCallNumberDelay /* 215 */:
                    return "getAutoCallNumberDelay";
                case TRANSACTION_getAutoCallNumberAnswerMode /* 216 */:
                    return "getAutoCallNumberAnswerMode";
                case TRANSACTION_getAutoCallNumberList /* 217 */:
                    return "getAutoCallNumberList";
                case TRANSACTION_setAutoCallPickupState /* 218 */:
                    return "setAutoCallPickupState";
                case TRANSACTION_getAutoCallPickupState /* 219 */:
                    return "getAutoCallPickupState";
                case 220:
                    return "getMacAddress";
                case 221:
                    return "powerOff";
                case 222:
                    return "setLockScreenShortcut";
                case TRANSACTION_getLockScreenShortcut /* 223 */:
                    return "getLockScreenShortcut";
                case TRANSACTION_setUsbConnectionType /* 224 */:
                    return "setUsbConnectionType";
                case TRANSACTION_getUsbConnectionType /* 225 */:
                    return "getUsbConnectionType";
                case TRANSACTION_getUsbConnectionTypeInternal /* 226 */:
                    return "getUsbConnectionTypeInternal";
                case TRANSACTION_setForceAutoShutDownState /* 227 */:
                    return "setForceAutoShutDownState";
                case TRANSACTION_getForceAutoShutDownState /* 228 */:
                    return "getForceAutoShutDownState";
                case TRANSACTION_setBrightness /* 229 */:
                    return "setBrightness";
                case 230:
                    return "addShortcut";
                case TRANSACTION_removeShortcut /* 231 */:
                    return "removeShortcut";
                case TRANSACTION_addWidget /* 232 */:
                    return "addWidget";
                case TRANSACTION_removeWidget /* 233 */:
                    return "removeWidget";
                case TRANSACTION_deleteHomeScreenPage /* 234 */:
                    return "deleteHomeScreenPage";
                case TRANSACTION_setAppsButtonState /* 235 */:
                    return "setAppsButtonState";
                case TRANSACTION_getAppsButtonState /* 236 */:
                    return "getAppsButtonState";
                case TRANSACTION_setFavoriteApp /* 237 */:
                    return "setFavoriteApp";
                case TRANSACTION_removeFavoriteApp /* 238 */:
                    return "removeFavoriteApp";
                case TRANSACTION_getFavoriteAppsMaxCount /* 239 */:
                    return "getFavoriteAppsMaxCount";
                case TRANSACTION_getFavoriteApp /* 240 */:
                    return "getFavoriteApp";
                case TRANSACTION_setZeroPageState /* 241 */:
                    return "setZeroPageState";
                case TRANSACTION_getZeroPageState /* 242 */:
                    return "getZeroPageState";
                case TRANSACTION_setHardKeyIntentMode /* 243 */:
                    return "setHardKeyIntentMode";
                case TRANSACTION_getHardKeyIntentMode /* 244 */:
                    return "getHardKeyIntentMode";
                case TRANSACTION_setHomeScreenMode /* 245 */:
                    return "setHomeScreenMode";
                case TRANSACTION_getHomeScreenMode /* 246 */:
                    return "getHomeScreenMode";
                case TRANSACTION_addDexShortcut /* 247 */:
                    return "addDexShortcut";
                case TRANSACTION_removeDexShortcut /* 248 */:
                    return "removeDexShortcut";
                case 249:
                    return "addDexURLShortcut";
                case TRANSACTION_addDexURLShortcutExtend /* 250 */:
                    return "addDexURLShortcutExtend";
                case TRANSACTION_removeDexURLShortcut /* 251 */:
                    return "removeDexURLShortcut";
                case TRANSACTION_setDexForegroundModePackageList /* 252 */:
                    return "setDexForegroundModePackageList";
                case TRANSACTION_getDexForegroundModePackageList /* 253 */:
                    return "getDexForegroundModePackageList";
                case 254:
                    return "setDexLoadingLogo";
                case 255:
                    return "clearDexLoadingLogo";
                case 256:
                    return "setDexScreenTimeout";
                case 257:
                    return "getDexScreenTimeout";
                case 258:
                    return "setDexHomeAlignment";
                case 259:
                    return "getDexHomeAlignment";
                case 260:
                    return "allowDexAutoOpenLastApp";
                case 261:
                    return "isDexAutoOpenLastAppAllowed";
                case 262:
                    return "setDexHDMIAutoEnterState";
                case 263:
                    return "getDexHDMIAutoEnterState";
                case 264:
                    return "setProtectBatteryState";
                case 265:
                    return "getProtectBatteryState";
                case 266:
                    return "setShowIMEWithHardKeyboard";
                case 267:
                    return "getShowIMEWithHardKeyboard";
                case 268:
                    return "setWallpaper";
                case 269:
                    return "setHardKeyReportState";
                case 270:
                    return CustomDeviceManager.REPORT_STATE_API_ENABLED;
                case 271:
                    return "getHardKeyBlockState";
                case 272:
                    return "setHardKeyIntentBroadcast";
                case TRANSACTION_setHardKeyIntentBroadcastExternal /* 273 */:
                    return "setHardKeyIntentBroadcastExternal";
                case TRANSACTION_setHardKeyIntentBroadcastInternal /* 274 */:
                    return "setHardKeyIntentBroadcastInternal";
                case TRANSACTION_getHardKeyIntentBroadcast /* 275 */:
                    return "getHardKeyIntentBroadcast";
                case TRANSACTION_setForcedDisplaySizeDensity /* 276 */:
                    return "setForcedDisplaySizeDensity";
                case TRANSACTION_clearForcedDisplaySizeDensity /* 277 */:
                    return "clearForcedDisplaySizeDensity";
                case TRANSACTION_startSmartView /* 278 */:
                    return "startSmartView";
                case TRANSACTION_setForceSingleView /* 279 */:
                    return "setForceSingleView";
                case TRANSACTION_getForceSingleView /* 280 */:
                    return "getForceSingleView";
                case TRANSACTION_setBootingAnimationSub /* 281 */:
                    return "setBootingAnimationSub";
                case TRANSACTION_setShuttingDownAnimationSub /* 282 */:
                    return "setShuttingDownAnimationSub";
                case TRANSACTION_getLoadingLogoPath /* 283 */:
                    return "getLoadingLogoPath";
                case TRANSACTION_registerSystemUiCallback /* 284 */:
                    return "registerSystemUiCallback";
                case TRANSACTION_startProKioskMode /* 285 */:
                    return "startProKioskMode";
                case TRANSACTION_stopProKioskMode /* 286 */:
                    return "stopProKioskMode";
                case TRANSACTION_stayInDexForegroundMode /* 287 */:
                    return "stayInDexForegroundMode";
                case TRANSACTION_getAsoc /* 288 */:
                    return "getAsoc";
                case TRANSACTION_setAsoc /* 289 */:
                    return "setAsoc";
                case TRANSACTION_getBsoh /* 290 */:
                    return "getBsoh";
                case TRANSACTION_getBsohUnbiased /* 291 */:
                    return "getBsohUnbiased";
                case TRANSACTION_startTcpDump /* 292 */:
                    return "startTcpDump";
                case TRANSACTION_stopTcpDump /* 293 */:
                    return "stopTcpDump";
                case TRANSACTION_getTcpDump /* 294 */:
                    return "getTcpDump";
                case TRANSACTION_readFile /* 295 */:
                    return "readFile";
                case TRANSACTION_setApplicationRestrictionsInternal /* 296 */:
                    return "setApplicationRestrictionsInternal";
                case TRANSACTION_getApplicationRestrictionsInternal /* 297 */:
                    return "getApplicationRestrictionsInternal";
                case TRANSACTION_setKeyedAppStatesReport /* 298 */:
                    return "setKeyedAppStatesReport";
                case TRANSACTION_migrateApplicationRestrictions /* 299 */:
                    return "migrateApplicationRestrictions";
                case 300:
                    return "getRoleHolders";
                case 301:
                    return "addRoleHolder";
                case 302:
                    return "removeRoleHolder";
                case 303:
                    return "isKnoxPrivacyPolicyAcceptedInitially";
                case 304:
                    return "isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings";
                case 305:
                    return "setKnoxPrivacyPolicyByUserSettings";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 304;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxCustomManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxCustomManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCheckEnterprisePermission = checkEnterprisePermission(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckEnterprisePermission);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iDialEmergencyNumber = dialEmergencyNumber(string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDialEmergencyNumber);
                    return true;
                case 3:
                    int iRemoveLockScreen = removeLockScreen();
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveLockScreen);
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int adbState = setAdbState(z);
                    parcel2.writeNoException();
                    parcel2.writeInt(adbState);
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int audioVolume = setAudioVolume(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(audioVolume);
                    return true;
                case 6:
                    boolean z2 = parcel.readBoolean();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int autoRotationState = setAutoRotationState(z2, i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoRotationState);
                    return true;
                case 7:
                    boolean autoRotationState2 = getAutoRotationState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRotationState2);
                    return true;
                case 8:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int bluetoothState = setBluetoothState(z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(bluetoothState);
                    return true;
                case 9:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int cpuPowerSavingState = setCpuPowerSavingState(z4);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpuPowerSavingState);
                    return true;
                case 10:
                    int developerOptionsHidden = setDeveloperOptionsHidden();
                    parcel2.writeNoException();
                    parcel2.writeInt(developerOptionsHidden);
                    return true;
                case 11:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int exitUI = setExitUI(string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(exitUI);
                    return true;
                case 12:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String exitUI2 = getExitUI(i6);
                    parcel2.writeNoException();
                    parcel2.writeString(exitUI2);
                    return true;
                case 13:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int extendedCallInfoState = setExtendedCallInfoState(z5);
                    parcel2.writeNoException();
                    parcel2.writeInt(extendedCallInfoState);
                    return true;
                case 14:
                    boolean extendedCallInfoState2 = getExtendedCallInfoState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(extendedCallInfoState2);
                    return true;
                case 15:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int homeActivity = setHomeActivity(string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(homeActivity);
                    return true;
                case 16:
                    String homeActivity2 = getHomeActivity();
                    parcel2.writeNoException();
                    parcel2.writeString(homeActivity2);
                    return true;
                case 17:
                    String string6 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int inputMethod = setInputMethod(string6, z6);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethod);
                    return true;
                case 18:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int inputMethodRestrictionState = setInputMethodRestrictionState(z7);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethodRestrictionState);
                    return true;
                case 19:
                    boolean inputMethodRestrictionState2 = getInputMethodRestrictionState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inputMethodRestrictionState2);
                    return true;
                case 20:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int mobileDataState = setMobileDataState(z8);
                    parcel2.writeNoException();
                    parcel2.writeInt(mobileDataState);
                    return true;
                case 21:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int multiWindowState = setMultiWindowState(z9);
                    parcel2.writeNoException();
                    parcel2.writeInt(multiWindowState);
                    return true;
                case 22:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int passCode = setPassCode(string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(passCode);
                    return true;
                case 23:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int powerDialogItems = setPowerDialogItems(i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(powerDialogItems);
                    return true;
                case 24:
                    int powerDialogItems2 = getPowerDialogItems();
                    parcel2.writeNoException();
                    parcel2.writeInt(powerDialogItems2);
                    return true;
                case 25:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int powerDialogOptionMode = setPowerDialogOptionMode(i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(powerDialogOptionMode);
                    return true;
                case 26:
                    int powerDialogOptionMode2 = getPowerDialogOptionMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(powerDialogOptionMode2);
                    return true;
                case 27:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int proKioskNotificationMessagesState = setProKioskNotificationMessagesState(z10);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskNotificationMessagesState);
                    return true;
                case 28:
                    boolean proKioskNotificationMessagesState2 = getProKioskNotificationMessagesState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskNotificationMessagesState2);
                    return true;
                case 29:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(PowerItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    int proKioskPowerDialogCustomItems = setProKioskPowerDialogCustomItems(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskPowerDialogCustomItems);
                    return true;
                case 30:
                    List<PowerItem> proKioskPowerDialogCustomItems2 = getProKioskPowerDialogCustomItems();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(proKioskPowerDialogCustomItems2, 1);
                    return true;
                case 31:
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int proKioskPowerDialogCustomItemsState = setProKioskPowerDialogCustomItemsState(z11);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskPowerDialogCustomItemsState);
                    return true;
                case 32:
                    boolean proKioskPowerDialogCustomItemsState2 = getProKioskPowerDialogCustomItemsState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskPowerDialogCustomItemsState2);
                    return true;
                case 33:
                    boolean z12 = parcel.readBoolean();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int proKioskState = setProKioskState(z12, string9);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskState);
                    return true;
                case 34:
                    boolean proKioskState2 = getProKioskState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskState2);
                    return true;
                case 35:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int proKioskStatusBarClockState = setProKioskStatusBarClockState(z13);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskStatusBarClockState);
                    return true;
                case 36:
                    boolean proKioskStatusBarClockState2 = getProKioskStatusBarClockState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskStatusBarClockState2);
                    return true;
                case 37:
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int proKioskStatusBarIconsState = setProKioskStatusBarIconsState(z14);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskStatusBarIconsState);
                    return true;
                case 38:
                    boolean proKioskStatusBarIconsState2 = getProKioskStatusBarIconsState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskStatusBarIconsState2);
                    return true;
                case 39:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int proKioskStatusBarMode = setProKioskStatusBarMode(i9);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskStatusBarMode);
                    return true;
                case 40:
                    int proKioskStatusBarMode2 = getProKioskStatusBarMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskStatusBarMode2);
                    return true;
                case 41:
                    int i10 = parcel.readInt();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int proKioskString = setProKioskString(i10, string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskString);
                    return true;
                case 42:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String proKioskString2 = getProKioskString(i11);
                    parcel2.writeNoException();
                    parcel2.writeString(proKioskString2);
                    return true;
                case 43:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int proKioskUsbMassStorageState = setProKioskUsbMassStorageState(z15);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskUsbMassStorageState);
                    return true;
                case 44:
                    boolean proKioskUsbMassStorageState2 = getProKioskUsbMassStorageState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskUsbMassStorageState2);
                    return true;
                case 45:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int proKioskUsbNetAddresses = setProKioskUsbNetAddresses(string11, string12);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskUsbNetAddresses);
                    return true;
                case 46:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String proKioskUsbNetAddress = getProKioskUsbNetAddress(i12);
                    parcel2.writeNoException();
                    parcel2.writeString(proKioskUsbNetAddress);
                    return true;
                case 47:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int proKioskUsbNetState = setProKioskUsbNetState(z16);
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskUsbNetState);
                    return true;
                case 48:
                    boolean proKioskUsbNetState2 = getProKioskUsbNetState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskUsbNetState2);
                    return true;
                case 49:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int screenPowerSavingState = setScreenPowerSavingState(z17);
                    parcel2.writeNoException();
                    parcel2.writeInt(screenPowerSavingState);
                    return true;
                case 50:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int screenTimeout = setScreenTimeout(i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(screenTimeout);
                    return true;
                case 51:
                    int screenTimeout2 = getScreenTimeout();
                    parcel2.writeNoException();
                    parcel2.writeInt(screenTimeout2);
                    return true;
                case 52:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int systemLocale = setSystemLocale(string13, string14);
                    parcel2.writeNoException();
                    parcel2.writeInt(systemLocale);
                    return true;
                case 53:
                    int i14 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int systemRingtone = setSystemRingtone(i14, string15);
                    parcel2.writeNoException();
                    parcel2.writeInt(systemRingtone);
                    return true;
                case 54:
                    onTransact$setUsbDeviceDefaultPackage$(parcel, parcel2);
                    return true;
                case 55:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userInactivityTimeout = setUserInactivityTimeout(i15);
                    parcel2.writeNoException();
                    parcel2.writeInt(userInactivityTimeout);
                    return true;
                case 56:
                    int userInactivityTimeout2 = getUserInactivityTimeout();
                    parcel2.writeNoException();
                    parcel2.writeInt(userInactivityTimeout2);
                    return true;
                case 57:
                    onTransact$setWifiState$(parcel, parcel2);
                    return true;
                case 58:
                    onTransact$setWifiStateEap$(parcel, parcel2);
                    return true;
                case 59:
                    int i16 = parcel.readInt();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int backupRestoreState = setBackupRestoreState(i16, z18);
                    parcel2.writeNoException();
                    parcel2.writeInt(backupRestoreState);
                    return true;
                case 60:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean backupRestoreState2 = getBackupRestoreState(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backupRestoreState2);
                    return true;
                case 61:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int hideNotificationMessages = setHideNotificationMessages(i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(hideNotificationMessages);
                    return true;
                case 62:
                    int hideNotificationMessages2 = getHideNotificationMessages();
                    parcel2.writeNoException();
                    parcel2.writeInt(hideNotificationMessages2);
                    return true;
                case 63:
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int mobileDataRoamingState = setMobileDataRoamingState(z19);
                    parcel2.writeNoException();
                    parcel2.writeInt(mobileDataRoamingState);
                    return true;
                case 64:
                    int i19 = parcel.readInt();
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int motionControlState = setMotionControlState(i19, z20);
                    parcel2.writeNoException();
                    parcel2.writeInt(motionControlState);
                    return true;
                case 65:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean motionControlState2 = getMotionControlState(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(motionControlState2);
                    return true;
                case 66:
                    boolean z21 = parcel.readBoolean();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int settingsHiddenState = setSettingsHiddenState(z21, i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(settingsHiddenState);
                    return true;
                case 67:
                    int settingsHiddenState2 = getSettingsHiddenState();
                    parcel2.writeNoException();
                    parcel2.writeInt(settingsHiddenState2);
                    return true;
                case 68:
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int stayAwakeState = setStayAwakeState(z22);
                    parcel2.writeNoException();
                    parcel2.writeInt(stayAwakeState);
                    return true;
                case 69:
                    int systemSoundsSilent = setSystemSoundsSilent();
                    parcel2.writeNoException();
                    parcel2.writeInt(systemSoundsSilent);
                    return true;
                case 70:
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int wifiConnectionMonitorState = setWifiConnectionMonitorState(z23);
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiConnectionMonitorState);
                    return true;
                case 71:
                    boolean wifiConnectionMonitorState2 = getWifiConnectionMonitorState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiConnectionMonitorState2);
                    return true;
                case 72:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int appBlockDownloadNamespaces = setAppBlockDownloadNamespaces(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(appBlockDownloadNamespaces);
                    return true;
                case 73:
                    List<String> appBlockDownloadNamespaces2 = getAppBlockDownloadNamespaces();
                    parcel2.writeNoException();
                    parcel2.writeStringList(appBlockDownloadNamespaces2);
                    return true;
                case 74:
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int appBlockDownloadState = setAppBlockDownloadState(z24);
                    parcel2.writeNoException();
                    parcel2.writeInt(appBlockDownloadState);
                    return true;
                case 75:
                    boolean appBlockDownloadState2 = getAppBlockDownloadState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appBlockDownloadState2);
                    return true;
                case 76:
                    StatusbarIconItem statusbarIconItem = (StatusbarIconItem) parcel.readTypedObject(StatusbarIconItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    int batteryLevelColourItem = setBatteryLevelColourItem(statusbarIconItem);
                    parcel2.writeNoException();
                    parcel2.writeInt(batteryLevelColourItem);
                    return true;
                case 77:
                    StatusbarIconItem batteryLevelColourItem2 = getBatteryLevelColourItem();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryLevelColourItem2, 1);
                    return true;
                case 78:
                    boolean z25 = parcel.readBoolean();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int callScreenDisabledItems = setCallScreenDisabledItems(z25, i22);
                    parcel2.writeNoException();
                    parcel2.writeInt(callScreenDisabledItems);
                    return true;
                case 79:
                    int callScreenDisabledItems2 = getCallScreenDisabledItems();
                    parcel2.writeNoException();
                    parcel2.writeInt(callScreenDisabledItems2);
                    return true;
                case 80:
                    boolean z26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int chargingLEDState = setChargingLEDState(z26);
                    parcel2.writeNoException();
                    parcel2.writeInt(chargingLEDState);
                    return true;
                case 81:
                    boolean chargingLEDState2 = getChargingLEDState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(chargingLEDState2);
                    return true;
                case 82:
                    onTransact$setEthernetConfiguration$(parcel, parcel2);
                    return true;
                case 83:
                    int ethernetConfigurationType = getEthernetConfigurationType();
                    parcel2.writeNoException();
                    parcel2.writeInt(ethernetConfigurationType);
                    return true;
                case 84:
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int ethernetState = setEthernetState(z27);
                    parcel2.writeNoException();
                    parcel2.writeInt(ethernetState);
                    return true;
                case 85:
                    boolean ethernetState2 = getEthernetState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ethernetState2);
                    return true;
                case 86:
                    boolean z28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int gearNotificationState = setGearNotificationState(z28);
                    parcel2.writeNoException();
                    parcel2.writeInt(gearNotificationState);
                    return true;
                case 87:
                    boolean gearNotificationState2 = getGearNotificationState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gearNotificationState2);
                    return true;
                case 88:
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int hardKeyIntentState = setHardKeyIntentState(z29);
                    parcel2.writeNoException();
                    parcel2.writeInt(hardKeyIntentState);
                    return true;
                case 89:
                    boolean hardKeyIntentState2 = getHardKeyIntentState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hardKeyIntentState2);
                    return true;
                case 90:
                    boolean z30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int infraredState = setInfraredState(z30);
                    parcel2.writeNoException();
                    parcel2.writeInt(infraredState);
                    return true;
                case 91:
                    boolean infraredState2 = getInfraredState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(infraredState2);
                    return true;
                case 92:
                    boolean z31 = parcel.readBoolean();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockScreenHiddenItems = setLockScreenHiddenItems(z31, i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreenHiddenItems);
                    return true;
                case 93:
                    int lockScreenHiddenItems2 = getLockScreenHiddenItems();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreenHiddenItems2);
                    return true;
                case 94:
                    String string16 = parcel.readString();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockscreenWallpaper = setLockscreenWallpaper(string16, i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockscreenWallpaper);
                    return true;
                case 95:
                    boolean z32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int lTESettingState = setLTESettingState(z32);
                    parcel2.writeNoException();
                    parcel2.writeInt(lTESettingState);
                    return true;
                case 96:
                    boolean lTESettingState2 = getLTESettingState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lTESettingState2);
                    return true;
                case 97:
                    boolean z33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int powerMenuLockedState = setPowerMenuLockedState(z33);
                    parcel2.writeNoException();
                    parcel2.writeInt(powerMenuLockedState);
                    return true;
                case 98:
                    boolean powerMenuLockedState2 = getPowerMenuLockedState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerMenuLockedState2);
                    return true;
                case 99:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int powerSavingMode = setPowerSavingMode(i25);
                    parcel2.writeNoException();
                    parcel2.writeInt(powerSavingMode);
                    return true;
                case 100:
                    int powerSavingMode2 = getPowerSavingMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(powerSavingMode2);
                    return true;
                case 101:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int recentLongPressActivity = setRecentLongPressActivity(string17);
                    parcel2.writeNoException();
                    parcel2.writeInt(recentLongPressActivity);
                    return true;
                case 102:
                    String recentLongPressActivity2 = getRecentLongPressActivity();
                    parcel2.writeNoException();
                    parcel2.writeString(recentLongPressActivity2);
                    return true;
                case 103:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int recentLongPressMode = setRecentLongPressMode(i26);
                    parcel2.writeNoException();
                    parcel2.writeInt(recentLongPressMode);
                    return true;
                case 104:
                    int recentLongPressMode2 = getRecentLongPressMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(recentLongPressMode2);
                    return true;
                case 105:
                    boolean z34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int screenOffOnHomeLongPressState = setScreenOffOnHomeLongPressState(z34);
                    parcel2.writeNoException();
                    parcel2.writeInt(screenOffOnHomeLongPressState);
                    return true;
                case 106:
                    boolean screenOffOnHomeLongPressState2 = getScreenOffOnHomeLongPressState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenOffOnHomeLongPressState2);
                    return true;
                case 107:
                    boolean z35 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int screenOffOnStatusBarDoubleTapState = setScreenOffOnStatusBarDoubleTapState(z35);
                    parcel2.writeNoException();
                    parcel2.writeInt(screenOffOnStatusBarDoubleTapState);
                    return true;
                case 108:
                    boolean screenOffOnStatusBarDoubleTapState2 = getScreenOffOnStatusBarDoubleTapState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenOffOnStatusBarDoubleTapState2);
                    return true;
                case 109:
                    boolean z36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int screenWakeupOnPowerState = setScreenWakeupOnPowerState(z36);
                    parcel2.writeNoException();
                    parcel2.writeInt(screenWakeupOnPowerState);
                    return true;
                case 110:
                    boolean screenWakeupOnPowerState2 = getScreenWakeupOnPowerState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenWakeupOnPowerState2);
                    return true;
                case 111:
                    boolean z37 = parcel.readBoolean();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int sensorDisabled = setSensorDisabled(z37, i27);
                    parcel2.writeNoException();
                    parcel2.writeInt(sensorDisabled);
                    return true;
                case 112:
                    int sensorDisabled2 = getSensorDisabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(sensorDisabled2);
                    return true;
                case 113:
                    onTransact$setStatusBarText$(parcel, parcel2);
                    return true;
                case 114:
                    String statusBarText = getStatusBarText();
                    parcel2.writeNoException();
                    parcel2.writeString(statusBarText);
                    return true;
                case 115:
                    int statusBarTextStyle = getStatusBarTextStyle();
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarTextStyle);
                    return true;
                case 116:
                    int statusBarTextSize = getStatusBarTextSize();
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarTextSize);
                    return true;
                case 117:
                    boolean z38 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int toastEnabledState = setToastEnabledState(z38);
                    parcel2.writeNoException();
                    parcel2.writeInt(toastEnabledState);
                    return true;
                case 118:
                    boolean toastEnabledState2 = getToastEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(toastEnabledState2);
                    return true;
                case 119:
                    onTransact$setToastGravity$(parcel, parcel2);
                    return true;
                case 120:
                    int toastGravity = getToastGravity();
                    parcel2.writeNoException();
                    parcel2.writeInt(toastGravity);
                    return true;
                case 121:
                    int toastGravityXOffset = getToastGravityXOffset();
                    parcel2.writeNoException();
                    parcel2.writeInt(toastGravityXOffset);
                    return true;
                case 122:
                    int toastGravityYOffset = getToastGravityYOffset();
                    parcel2.writeNoException();
                    parcel2.writeInt(toastGravityYOffset);
                    return true;
                case 123:
                    boolean z39 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int toastGravityEnabledState = setToastGravityEnabledState(z39);
                    parcel2.writeNoException();
                    parcel2.writeInt(toastGravityEnabledState);
                    return true;
                case 124:
                    boolean toastGravityEnabledState2 = getToastGravityEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(toastGravityEnabledState2);
                    return true;
                case 125:
                    boolean z40 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int toastShowPackageNameState = setToastShowPackageNameState(z40);
                    parcel2.writeNoException();
                    parcel2.writeInt(toastShowPackageNameState);
                    return true;
                case 126:
                    boolean toastShowPackageNameState2 = getToastShowPackageNameState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(toastShowPackageNameState2);
                    return true;
                case 127:
                    boolean z41 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int torchOnVolumeButtonsState = setTorchOnVolumeButtonsState(z41);
                    parcel2.writeNoException();
                    parcel2.writeInt(torchOnVolumeButtonsState);
                    return true;
                case 128:
                    boolean torchOnVolumeButtonsState2 = getTorchOnVolumeButtonsState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(torchOnVolumeButtonsState2);
                    return true;
                case 129:
                    boolean z42 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int volumeButtonRotationState = setVolumeButtonRotationState(z42);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeButtonRotationState);
                    return true;
                case 130:
                    boolean volumeButtonRotationState2 = getVolumeButtonRotationState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(volumeButtonRotationState2);
                    return true;
                case 131:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int volumeControlStream = setVolumeControlStream(i28);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeControlStream);
                    return true;
                case 132:
                    int volumeControlStream2 = getVolumeControlStream();
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeControlStream2);
                    return true;
                case 133:
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int volumeKeyAppsList = setVolumeKeyAppsList(arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeKeyAppsList);
                    return true;
                case 134:
                    List<String> volumeKeyAppsList2 = getVolumeKeyAppsList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(volumeKeyAppsList2);
                    return true;
                case 135:
                    boolean z43 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int volumeKeyAppState = setVolumeKeyAppState(z43);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeKeyAppState);
                    return true;
                case 136:
                    boolean volumeKeyAppState2 = getVolumeKeyAppState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(volumeKeyAppState2);
                    return true;
                case 137:
                    boolean z44 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int volumePanelEnabledState = setVolumePanelEnabledState(z44);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumePanelEnabledState);
                    return true;
                case 138:
                    boolean volumePanelEnabledState2 = getVolumePanelEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(volumePanelEnabledState2);
                    return true;
                case 139:
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int iAddPackagesToUltraPowerSaving = addPackagesToUltraPowerSaving(arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackagesToUltraPowerSaving);
                    return true;
                case 140:
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int iRemovePackagesFromUltraPowerSaving = removePackagesFromUltraPowerSaving(arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackagesFromUltraPowerSaving);
                    return true;
                case 141:
                    List<String> ultraPowerSavingPackages = getUltraPowerSavingPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(ultraPowerSavingPackages);
                    return true;
                case 142:
                    String serialNumber = getSerialNumber();
                    parcel2.writeNoException();
                    parcel2.writeString(serialNumber);
                    return true;
                case 143:
                    int i29 = parcel.readInt();
                    boolean z45 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int airGestureOptionState = setAirGestureOptionState(i29, z45);
                    parcel2.writeNoException();
                    parcel2.writeInt(airGestureOptionState);
                    return true;
                case 144:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean airGestureOptionState2 = getAirGestureOptionState(i30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(airGestureOptionState2);
                    return true;
                case 145:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int browserHomepage = setBrowserHomepage(string18);
                    parcel2.writeNoException();
                    parcel2.writeInt(browserHomepage);
                    return true;
                case 146:
                    boolean z46 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int chargerConnectionSoundEnabledState = setChargerConnectionSoundEnabledState(z46);
                    parcel2.writeNoException();
                    parcel2.writeInt(chargerConnectionSoundEnabledState);
                    return true;
                case 147:
                    boolean chargerConnectionSoundEnabledState2 = getChargerConnectionSoundEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(chargerConnectionSoundEnabledState2);
                    return true;
                case 148:
                    boolean z47 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int deviceSpeakerEnabledState = setDeviceSpeakerEnabledState(z47);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceSpeakerEnabledState);
                    return true;
                case 149:
                    boolean deviceSpeakerEnabledState2 = getDeviceSpeakerEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceSpeakerEnabledState2);
                    return true;
                case 150:
                    boolean z48 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int displayMirroringState = setDisplayMirroringState(z48);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayMirroringState);
                    return true;
                case 151:
                    boolean displayMirroringState2 = getDisplayMirroringState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(displayMirroringState2);
                    return true;
                case 152:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyboardMode = setKeyboardMode(i31);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyboardMode);
                    return true;
                case 153:
                    int keyboardMode2 = getKeyboardMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(keyboardMode2);
                    return true;
                case 154:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean keyboardModeOverriden = getKeyboardModeOverriden(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyboardModeOverriden);
                    return true;
                case 155:
                    boolean z49 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int lcdBacklightState = setLcdBacklightState(z49);
                    parcel2.writeNoException();
                    parcel2.writeInt(lcdBacklightState);
                    return true;
                case 156:
                    boolean lcdBacklightState2 = getLcdBacklightState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lcdBacklightState2);
                    return true;
                case 157:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockScreenOverrideMode = setLockScreenOverrideMode(i33);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreenOverrideMode);
                    return true;
                case 158:
                    int lockScreenOverrideMode2 = getLockScreenOverrideMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreenOverrideMode2);
                    return true;
                case 159:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(PowerItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    int powerDialogCustomItems = setPowerDialogCustomItems(arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(powerDialogCustomItems);
                    return true;
                case 160:
                    List<PowerItem> powerDialogCustomItems2 = getPowerDialogCustomItems();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(powerDialogCustomItems2, 1);
                    return true;
                case 161:
                    boolean z50 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int powerDialogCustomItemsState = setPowerDialogCustomItemsState(z50);
                    parcel2.writeNoException();
                    parcel2.writeInt(powerDialogCustomItemsState);
                    return true;
                case 162:
                    boolean powerDialogCustomItemsState2 = getPowerDialogCustomItemsState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerDialogCustomItemsState2);
                    return true;
                case 163:
                    boolean z51 = parcel.readBoolean();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int settingsEnabledItems = setSettingsEnabledItems(z51, i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(settingsEnabledItems);
                    return true;
                case 164:
                    int settingsEnabledItems2 = getSettingsEnabledItems();
                    parcel2.writeNoException();
                    parcel2.writeInt(settingsEnabledItems2);
                    return true;
                case 165:
                    boolean z52 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int statusBarClockState = setStatusBarClockState(z52);
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarClockState);
                    return true;
                case 166:
                    boolean statusBarClockState2 = getStatusBarClockState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(statusBarClockState2);
                    return true;
                case 167:
                    boolean z53 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int statusBarIconsState = setStatusBarIconsState(z53);
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarIconsState);
                    return true;
                case 168:
                    boolean statusBarIconsState2 = getStatusBarIconsState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(statusBarIconsState2);
                    return true;
                case 169:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int statusBarMode = setStatusBarMode(i35);
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarMode);
                    return true;
                case 170:
                    int statusBarMode2 = getStatusBarMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarMode2);
                    return true;
                case 171:
                    boolean z54 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int statusBarNotificationsState = setStatusBarNotificationsState(z54);
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarNotificationsState);
                    return true;
                case 172:
                    boolean statusBarNotificationsState2 = getStatusBarNotificationsState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(statusBarNotificationsState2);
                    return true;
                case 173:
                    onTransact$setStatusBarTextScrollWidth$(parcel, parcel2);
                    return true;
                case 174:
                    int statusBarTextScrollWidth = getStatusBarTextScrollWidth();
                    parcel2.writeNoException();
                    parcel2.writeInt(statusBarTextScrollWidth);
                    return true;
                case 175:
                    boolean z55 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int unlockSimOnBootState = setUnlockSimOnBootState(z55);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlockSimOnBootState);
                    return true;
                case 176:
                    boolean unlockSimOnBootState2 = getUnlockSimOnBootState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unlockSimOnBootState2);
                    return true;
                case 177:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int unlockSimPin = setUnlockSimPin(string19);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlockSimPin);
                    return true;
                case 178:
                    String unlockSimPin2 = getUnlockSimPin();
                    parcel2.writeNoException();
                    parcel2.writeString(unlockSimPin2);
                    return true;
                case 179:
                    boolean z56 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int usbMassStorageState = setUsbMassStorageState(z56);
                    parcel2.writeNoException();
                    parcel2.writeInt(usbMassStorageState);
                    return true;
                case 180:
                    boolean usbMassStorageState2 = getUsbMassStorageState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbMassStorageState2);
                    return true;
                case 181:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int usbNetAddresses = setUsbNetAddresses(string20, string21);
                    parcel2.writeNoException();
                    parcel2.writeInt(usbNetAddresses);
                    return true;
                case 182:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String usbNetAddress = getUsbNetAddress(i36);
                    parcel2.writeNoException();
                    parcel2.writeString(usbNetAddress);
                    return true;
                case 183:
                    boolean z57 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int usbNetState = setUsbNetState(z57);
                    parcel2.writeNoException();
                    parcel2.writeInt(usbNetState);
                    return true;
                case 184:
                    boolean usbNetState2 = getUsbNetState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbNetState2);
                    return true;
                case 185:
                    boolean usbNetStateInternal = getUsbNetStateInternal();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbNetStateInternal);
                    return true;
                case 186:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int wifiFrequencyBand = setWifiFrequencyBand(i37);
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiFrequencyBand);
                    return true;
                case 187:
                    int wifiFrequencyBand2 = getWifiFrequencyBand();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiFrequencyBand2);
                    return true;
                case 188:
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int accessibilitySettingsItems = setAccessibilitySettingsItems(i38, i39);
                    parcel2.writeNoException();
                    parcel2.writeInt(accessibilitySettingsItems);
                    return true;
                case 189:
                    int accessibilitySettingsItems2 = getAccessibilitySettingsItems();
                    parcel2.writeNoException();
                    parcel2.writeInt(accessibilitySettingsItems2);
                    return true;
                case 190:
                    onTransact$setBootingAnimation$(parcel, parcel2);
                    return true;
                case 191:
                    Parcelable.Creator creator = ParcelFileDescriptor.CREATOR;
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(creator);
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(creator);
                    parcel.enforceNoDataAvail();
                    int shuttingDownAnimation = setShuttingDownAnimation(parcelFileDescriptor, parcelFileDescriptor2);
                    parcel2.writeNoException();
                    parcel2.writeInt(shuttingDownAnimation);
                    return true;
                case 192:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iClearAnimation = clearAnimation(i40);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearAnimation);
                    return true;
                case 193:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int flightModeState = setFlightModeState(i41);
                    parcel2.writeNoException();
                    parcel2.writeInt(flightModeState);
                    return true;
                case 194:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int forceAutoStartUpState = setForceAutoStartUpState(i42);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceAutoStartUpState);
                    return true;
                case 195:
                    int forceAutoStartUpState2 = getForceAutoStartUpState();
                    parcel2.writeNoException();
                    parcel2.writeInt(forceAutoStartUpState2);
                    return true;
                case 196:
                    boolean zIsSupportedForceAutoStartUpState = isSupportedForceAutoStartUpState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportedForceAutoStartUpState);
                    return true;
                case 197:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int mobileNetworkType = setMobileNetworkType(i43);
                    parcel2.writeNoException();
                    parcel2.writeInt(mobileNetworkType);
                    return true;
                case 198:
                    int mobileNetworkType2 = getMobileNetworkType();
                    parcel2.writeNoException();
                    parcel2.writeInt(mobileNetworkType2);
                    return true;
                case 199:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int quickPanelButtons = setQuickPanelButtons(i44);
                    parcel2.writeNoException();
                    parcel2.writeInt(quickPanelButtons);
                    return true;
                case 200:
                    int quickPanelButtons2 = getQuickPanelButtons();
                    parcel2.writeNoException();
                    parcel2.writeInt(quickPanelButtons2);
                    return true;
                case 201:
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int quickPanelEditMode = setQuickPanelEditMode(i45);
                    parcel2.writeNoException();
                    parcel2.writeInt(quickPanelEditMode);
                    return true;
                case 202:
                    int quickPanelEditMode2 = getQuickPanelEditMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(quickPanelEditMode2);
                    return true;
                case 203:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int quickPanelItems = setQuickPanelItems(string22);
                    parcel2.writeNoException();
                    parcel2.writeInt(quickPanelItems);
                    return true;
                case 204:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int quickPanelItemsInternal = setQuickPanelItemsInternal(bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(quickPanelItemsInternal);
                    return true;
                case 205:
                    String quickPanelItems2 = getQuickPanelItems();
                    parcel2.writeNoException();
                    parcel2.writeString(quickPanelItems2);
                    return true;
                case 206:
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int systemSoundsEnabledState = setSystemSoundsEnabledState(i46, i47);
                    parcel2.writeNoException();
                    parcel2.writeInt(systemSoundsEnabledState);
                    return true;
                case 207:
                    int systemSoundsEnabledState2 = getSystemSoundsEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeInt(systemSoundsEnabledState2);
                    return true;
                case 208:
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vibrationIntensity = setVibrationIntensity(i48, i49);
                    parcel2.writeNoException();
                    parcel2.writeInt(vibrationIntensity);
                    return true;
                case 209:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vibrationIntensity2 = getVibrationIntensity(i50);
                    parcel2.writeNoException();
                    parcel2.writeInt(vibrationIntensity2);
                    return true;
                case 210:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int wifiHotspotEnabledState = setWifiHotspotEnabledState(i51);
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiHotspotEnabledState);
                    return true;
                case TRANSACTION_getWifiHotspotEnabledState /* 211 */:
                    int wifiHotspotEnabledState2 = getWifiHotspotEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiHotspotEnabledState2);
                    return true;
                case TRANSACTION_getWifiState /* 212 */:
                    boolean wifiState = getWifiState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiState);
                    return true;
                case TRANSACTION_addAutoCallNumber /* 213 */:
                    onTransact$addAutoCallNumber$(parcel, parcel2);
                    return true;
                case TRANSACTION_removeAutoCallNumber /* 214 */:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveAutoCallNumber = removeAutoCallNumber(string23);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveAutoCallNumber);
                    return true;
                case TRANSACTION_getAutoCallNumberDelay /* 215 */:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoCallNumberDelay = getAutoCallNumberDelay(string24);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoCallNumberDelay);
                    return true;
                case TRANSACTION_getAutoCallNumberAnswerMode /* 216 */:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoCallNumberAnswerMode = getAutoCallNumberAnswerMode(string25);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoCallNumberAnswerMode);
                    return true;
                case TRANSACTION_getAutoCallNumberList /* 217 */:
                    List<String> autoCallNumberList = getAutoCallNumberList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(autoCallNumberList);
                    return true;
                case TRANSACTION_setAutoCallPickupState /* 218 */:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int autoCallPickupState = setAutoCallPickupState(i52);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoCallPickupState);
                    return true;
                case TRANSACTION_getAutoCallPickupState /* 219 */:
                    int autoCallPickupState2 = getAutoCallPickupState();
                    parcel2.writeNoException();
                    parcel2.writeInt(autoCallPickupState2);
                    return true;
                case 220:
                    String macAddress = getMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(macAddress);
                    return true;
                case 221:
                    int iPowerOff = powerOff();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPowerOff);
                    return true;
                case 222:
                    int i53 = parcel.readInt();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int lockScreenShortcut = setLockScreenShortcut(i53, string26);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreenShortcut);
                    return true;
                case TRANSACTION_getLockScreenShortcut /* 223 */:
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String lockScreenShortcut2 = getLockScreenShortcut(i54);
                    parcel2.writeNoException();
                    parcel2.writeString(lockScreenShortcut2);
                    return true;
                case TRANSACTION_setUsbConnectionType /* 224 */:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int usbConnectionType = setUsbConnectionType(i55);
                    parcel2.writeNoException();
                    parcel2.writeInt(usbConnectionType);
                    return true;
                case TRANSACTION_getUsbConnectionType /* 225 */:
                    int usbConnectionType2 = getUsbConnectionType();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbConnectionType2);
                    return true;
                case TRANSACTION_getUsbConnectionTypeInternal /* 226 */:
                    int usbConnectionTypeInternal = getUsbConnectionTypeInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbConnectionTypeInternal);
                    return true;
                case TRANSACTION_setForceAutoShutDownState /* 227 */:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int forceAutoShutDownState = setForceAutoShutDownState(i56);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceAutoShutDownState);
                    return true;
                case TRANSACTION_getForceAutoShutDownState /* 228 */:
                    int forceAutoShutDownState2 = getForceAutoShutDownState();
                    parcel2.writeNoException();
                    parcel2.writeInt(forceAutoShutDownState2);
                    return true;
                case TRANSACTION_setBrightness /* 229 */:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int brightness = setBrightness(i57);
                    parcel2.writeNoException();
                    parcel2.writeInt(brightness);
                    return true;
                case 230:
                    onTransact$addShortcut$(parcel, parcel2);
                    return true;
                case TRANSACTION_removeShortcut /* 231 */:
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveShortcut = removeShortcut(string27);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveShortcut);
                    return true;
                case TRANSACTION_addWidget /* 232 */:
                    onTransact$addWidget$(parcel, parcel2);
                    return true;
                case TRANSACTION_removeWidget /* 233 */:
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveWidget = removeWidget(string28);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveWidget);
                    return true;
                case TRANSACTION_deleteHomeScreenPage /* 234 */:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iDeleteHomeScreenPage = deleteHomeScreenPage(i58);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDeleteHomeScreenPage);
                    return true;
                case TRANSACTION_setAppsButtonState /* 235 */:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appsButtonState = setAppsButtonState(i59);
                    parcel2.writeNoException();
                    parcel2.writeInt(appsButtonState);
                    return true;
                case TRANSACTION_getAppsButtonState /* 236 */:
                    int appsButtonState2 = getAppsButtonState();
                    parcel2.writeNoException();
                    parcel2.writeInt(appsButtonState2);
                    return true;
                case TRANSACTION_setFavoriteApp /* 237 */:
                    String string29 = parcel.readString();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int favoriteApp = setFavoriteApp(string29, i60);
                    parcel2.writeNoException();
                    parcel2.writeInt(favoriteApp);
                    return true;
                case TRANSACTION_removeFavoriteApp /* 238 */:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRemoveFavoriteApp = removeFavoriteApp(i61);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveFavoriteApp);
                    return true;
                case TRANSACTION_getFavoriteAppsMaxCount /* 239 */:
                    int favoriteAppsMaxCount = getFavoriteAppsMaxCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(favoriteAppsMaxCount);
                    return true;
                case TRANSACTION_getFavoriteApp /* 240 */:
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String favoriteApp2 = getFavoriteApp(i62);
                    parcel2.writeNoException();
                    parcel2.writeString(favoriteApp2);
                    return true;
                case TRANSACTION_setZeroPageState /* 241 */:
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int zeroPageState = setZeroPageState(i63);
                    parcel2.writeNoException();
                    parcel2.writeInt(zeroPageState);
                    return true;
                case TRANSACTION_getZeroPageState /* 242 */:
                    int zeroPageState2 = getZeroPageState();
                    parcel2.writeNoException();
                    parcel2.writeInt(zeroPageState2);
                    return true;
                case TRANSACTION_setHardKeyIntentMode /* 243 */:
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int hardKeyIntentMode = setHardKeyIntentMode(i64);
                    parcel2.writeNoException();
                    parcel2.writeInt(hardKeyIntentMode);
                    return true;
                case TRANSACTION_getHardKeyIntentMode /* 244 */:
                    int hardKeyIntentMode2 = getHardKeyIntentMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(hardKeyIntentMode2);
                    return true;
                case TRANSACTION_setHomeScreenMode /* 245 */:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int homeScreenMode = setHomeScreenMode(i65);
                    parcel2.writeNoException();
                    parcel2.writeInt(homeScreenMode);
                    return true;
                case TRANSACTION_getHomeScreenMode /* 246 */:
                    int homeScreenMode2 = getHomeScreenMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(homeScreenMode2);
                    return true;
                case TRANSACTION_addDexShortcut /* 247 */:
                    onTransact$addDexShortcut$(parcel, parcel2);
                    return true;
                case TRANSACTION_removeDexShortcut /* 248 */:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemoveDexShortcut = removeDexShortcut(componentName);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveDexShortcut);
                    return true;
                case 249:
                    onTransact$addDexURLShortcut$(parcel, parcel2);
                    return true;
                case TRANSACTION_addDexURLShortcutExtend /* 250 */:
                    onTransact$addDexURLShortcutExtend$(parcel, parcel2);
                    return true;
                case TRANSACTION_removeDexURLShortcut /* 251 */:
                    String string30 = parcel.readString();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemoveDexURLShortcut = removeDexURLShortcut(string30, componentName2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveDexURLShortcut);
                    return true;
                case TRANSACTION_setDexForegroundModePackageList /* 252 */:
                    int i66 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int dexForegroundModePackageList = setDexForegroundModePackageList(i66, arrayListCreateStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeInt(dexForegroundModePackageList);
                    return true;
                case TRANSACTION_getDexForegroundModePackageList /* 253 */:
                    List<String> dexForegroundModePackageList2 = getDexForegroundModePackageList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(dexForegroundModePackageList2);
                    return true;
                case 254:
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    int dexLoadingLogo = setDexLoadingLogo(parcelFileDescriptor3);
                    parcel2.writeNoException();
                    parcel2.writeInt(dexLoadingLogo);
                    return true;
                case 255:
                    int iClearDexLoadingLogo = clearDexLoadingLogo();
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearDexLoadingLogo);
                    return true;
                case 256:
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dexScreenTimeout = setDexScreenTimeout(i67);
                    parcel2.writeNoException();
                    parcel2.writeInt(dexScreenTimeout);
                    return true;
                case 257:
                    int dexScreenTimeout2 = getDexScreenTimeout();
                    parcel2.writeNoException();
                    parcel2.writeInt(dexScreenTimeout2);
                    return true;
                case 258:
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dexHomeAlignment = setDexHomeAlignment(i68);
                    parcel2.writeNoException();
                    parcel2.writeInt(dexHomeAlignment);
                    return true;
                case 259:
                    int dexHomeAlignment2 = getDexHomeAlignment();
                    parcel2.writeNoException();
                    parcel2.writeInt(dexHomeAlignment2);
                    return true;
                case 260:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAllowDexAutoOpenLastApp = allowDexAutoOpenLastApp(i69);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowDexAutoOpenLastApp);
                    return true;
                case 261:
                    int iIsDexAutoOpenLastAppAllowed = isDexAutoOpenLastAppAllowed();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsDexAutoOpenLastAppAllowed);
                    return true;
                case 262:
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dexHDMIAutoEnterState = setDexHDMIAutoEnterState(i70);
                    parcel2.writeNoException();
                    parcel2.writeInt(dexHDMIAutoEnterState);
                    return true;
                case 263:
                    int dexHDMIAutoEnterState2 = getDexHDMIAutoEnterState();
                    parcel2.writeNoException();
                    parcel2.writeInt(dexHDMIAutoEnterState2);
                    return true;
                case 264:
                    boolean z58 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int protectBatteryState = setProtectBatteryState(z58);
                    parcel2.writeNoException();
                    parcel2.writeInt(protectBatteryState);
                    return true;
                case 265:
                    boolean protectBatteryState2 = getProtectBatteryState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(protectBatteryState2);
                    return true;
                case 266:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int showIMEWithHardKeyboard = setShowIMEWithHardKeyboard(i71);
                    parcel2.writeNoException();
                    parcel2.writeInt(showIMEWithHardKeyboard);
                    return true;
                case 267:
                    int showIMEWithHardKeyboard2 = getShowIMEWithHardKeyboard();
                    parcel2.writeNoException();
                    parcel2.writeInt(showIMEWithHardKeyboard2);
                    return true;
                case 268:
                    onTransact$setWallpaper$(parcel, parcel2);
                    return true;
                case 269:
                    onTransact$setHardKeyReportState$(parcel, parcel2);
                    return true;
                case 270:
                    int i72 = parcel.readInt();
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int hardKeyReportState = getHardKeyReportState(i72, i73);
                    parcel2.writeNoException();
                    parcel2.writeInt(hardKeyReportState);
                    return true;
                case 271:
                    onTransact$getHardKeyBlockState$(parcel, parcel2);
                    return true;
                case 272:
                    onTransact$setHardKeyIntentBroadcast$(parcel, parcel2);
                    return true;
                case TRANSACTION_setHardKeyIntentBroadcastExternal /* 273 */:
                    onTransact$setHardKeyIntentBroadcastExternal$(parcel, parcel2);
                    return true;
                case TRANSACTION_setHardKeyIntentBroadcastInternal /* 274 */:
                    onTransact$setHardKeyIntentBroadcastInternal$(parcel, parcel2);
                    return true;
                case TRANSACTION_getHardKeyIntentBroadcast /* 275 */:
                    onTransact$getHardKeyIntentBroadcast$(parcel, parcel2);
                    return true;
                case TRANSACTION_setForcedDisplaySizeDensity /* 276 */:
                    onTransact$setForcedDisplaySizeDensity$(parcel, parcel2);
                    return true;
                case TRANSACTION_clearForcedDisplaySizeDensity /* 277 */:
                    int iClearForcedDisplaySizeDensity = clearForcedDisplaySizeDensity();
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearForcedDisplaySizeDensity);
                    return true;
                case TRANSACTION_startSmartView /* 278 */:
                    int iStartSmartView = startSmartView();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartSmartView);
                    return true;
                case TRANSACTION_setForceSingleView /* 279 */:
                    boolean z59 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int forceSingleView = setForceSingleView(z59);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSingleView);
                    return true;
                case TRANSACTION_getForceSingleView /* 280 */:
                    boolean forceSingleView2 = getForceSingleView();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSingleView2);
                    return true;
                case TRANSACTION_setBootingAnimationSub /* 281 */:
                    onTransact$setBootingAnimationSub$(parcel, parcel2);
                    return true;
                case TRANSACTION_setShuttingDownAnimationSub /* 282 */:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    int shuttingDownAnimationSub = setShuttingDownAnimationSub(parcelFileDescriptor4);
                    parcel2.writeNoException();
                    parcel2.writeInt(shuttingDownAnimationSub);
                    return true;
                case TRANSACTION_getLoadingLogoPath /* 283 */:
                    String loadingLogoPath = getLoadingLogoPath();
                    parcel2.writeNoException();
                    parcel2.writeString(loadingLogoPath);
                    return true;
                case TRANSACTION_registerSystemUiCallback /* 284 */:
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallbackAsInterface = IKnoxCustomManagerSystemUiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterSystemUiCallback = registerSystemUiCallback(iKnoxCustomManagerSystemUiCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterSystemUiCallback);
                    return true;
                case TRANSACTION_startProKioskMode /* 285 */:
                    onTransact$startProKioskMode$(parcel, parcel2);
                    return true;
                case TRANSACTION_stopProKioskMode /* 286 */:
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStopProKioskMode = stopProKioskMode(string31);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopProKioskMode);
                    return true;
                case TRANSACTION_stayInDexForegroundMode /* 287 */:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStayInDexForegroundMode = stayInDexForegroundMode(componentName3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStayInDexForegroundMode);
                    return true;
                case TRANSACTION_getAsoc /* 288 */:
                    String asoc = getAsoc();
                    parcel2.writeNoException();
                    parcel2.writeString(asoc);
                    return true;
                case TRANSACTION_setAsoc /* 289 */:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int asoc2 = setAsoc(i74);
                    parcel2.writeNoException();
                    parcel2.writeInt(asoc2);
                    return true;
                case TRANSACTION_getBsoh /* 290 */:
                    String bsoh = getBsoh();
                    parcel2.writeNoException();
                    parcel2.writeString(bsoh);
                    return true;
                case TRANSACTION_getBsohUnbiased /* 291 */:
                    String bsohUnbiased = getBsohUnbiased();
                    parcel2.writeNoException();
                    parcel2.writeString(bsohUnbiased);
                    return true;
                case TRANSACTION_startTcpDump /* 292 */:
                    onTransact$startTcpDump$(parcel, parcel2);
                    return true;
                case TRANSACTION_stopTcpDump /* 293 */:
                    int iStopTcpDump = stopTcpDump();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopTcpDump);
                    return true;
                case TRANSACTION_getTcpDump /* 294 */:
                    ParcelFileDescriptor tcpDump = getTcpDump();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tcpDump, 1);
                    return true;
                case TRANSACTION_readFile /* 295 */:
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String file = readFile(string32);
                    parcel2.writeNoException();
                    parcel2.writeString(file);
                    return true;
                case TRANSACTION_setApplicationRestrictionsInternal /* 296 */:
                    onTransact$setApplicationRestrictionsInternal$(parcel, parcel2);
                    return true;
                case TRANSACTION_getApplicationRestrictionsInternal /* 297 */:
                    onTransact$getApplicationRestrictionsInternal$(parcel, parcel2);
                    return true;
                case TRANSACTION_setKeyedAppStatesReport /* 298 */:
                    onTransact$setKeyedAppStatesReport$(parcel, parcel2);
                    return true;
                case TRANSACTION_migrateApplicationRestrictions /* 299 */:
                    migrateApplicationRestrictions();
                    parcel2.writeNoException();
                    return true;
                case 300:
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> roleHolders = getRoleHolders(string33);
                    parcel2.writeNoException();
                    parcel2.writeStringList(roleHolders);
                    return true;
                case 301:
                    onTransact$addRoleHolder$(parcel, parcel2);
                    return true;
                case 302:
                    onTransact$removeRoleHolder$(parcel, parcel2);
                    return true;
                case 303:
                    boolean zIsKnoxPrivacyPolicyAcceptedInitially = isKnoxPrivacyPolicyAcceptedInitially();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKnoxPrivacyPolicyAcceptedInitially);
                    return true;
                case 304:
                    boolean zIsKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings = isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings);
                    return true;
                case 305:
                    boolean z60 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKnoxPrivacyPolicyByUserSettings(z60);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        public final boolean onTransact$addAutoCallNumber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iAddAutoCallNumber = addAutoCallNumber(string, i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(iAddAutoCallNumber);
            return true;
        }

        public final boolean onTransact$addDexShortcut$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            parcel.enforceNoDataAvail();
            int iAddDexShortcut = addDexShortcut(i, i2, componentName);
            parcel2.writeNoException();
            parcel2.writeInt(iAddDexShortcut);
            return true;
        }

        public final boolean onTransact$addDexURLShortcut$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            parcel.enforceNoDataAvail();
            int iAddDexURLShortcut = addDexURLShortcut(i, i2, string, string2, componentName);
            parcel2.writeNoException();
            parcel2.writeInt(iAddDexURLShortcut);
            return true;
        }

        public final boolean onTransact$addDexURLShortcutExtend$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
            parcel.enforceNoDataAvail();
            int iAddDexURLShortcutExtend = addDexURLShortcutExtend(i, i2, string, string2, string3, componentName, parcelFileDescriptor);
            parcel2.writeNoException();
            parcel2.writeInt(iAddDexURLShortcutExtend);
            return true;
        }

        public final boolean onTransact$addRoleHolder$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zAddRoleHolder = addRoleHolder(string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zAddRoleHolder);
            return true;
        }

        public final boolean onTransact$addShortcut$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            int iAddShortcut = addShortcut(i, i2, i3, string);
            parcel2.writeNoException();
            parcel2.writeInt(iAddShortcut);
            return true;
        }

        public final boolean onTransact$addWidget$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            int iAddWidget = addWidget(i, i2, i3, i4, i5, string);
            parcel2.writeNoException();
            parcel2.writeInt(iAddWidget);
            return true;
        }

        public final boolean onTransact$getApplicationRestrictionsInternal$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal(string, i);
            parcel2.writeNoException();
            parcel2.writeTypedObject(applicationRestrictionsInternal, 1);
            return true;
        }

        public final boolean onTransact$getHardKeyBlockState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int hardKeyBlockState = getHardKeyBlockState(i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(hardKeyBlockState);
            return true;
        }

        public final boolean onTransact$getHardKeyIntentBroadcast$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int hardKeyIntentBroadcast = getHardKeyIntentBroadcast(i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(hardKeyIntentBroadcast);
            return true;
        }

        public final boolean onTransact$removeRoleHolder$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zRemoveRoleHolder = removeRoleHolder(string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zRemoveRoleHolder);
            return true;
        }

        public final boolean onTransact$setApplicationRestrictionsInternal$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int i = parcel.readInt();
            IApplicationRestrictionsResultCallback iApplicationRestrictionsResultCallbackAsInterface = IApplicationRestrictionsResultCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            Bundle applicationRestrictionsInternal = setApplicationRestrictionsInternal(contextInfo, string, string2, bundle, i, iApplicationRestrictionsResultCallbackAsInterface);
            parcel2.writeNoException();
            parcel2.writeTypedObject(applicationRestrictionsInternal, 1);
            return true;
        }

        public final boolean onTransact$setBootingAnimation$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Parcelable.Creator creator = ParcelFileDescriptor.CREATOR;
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(creator);
            ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(creator);
            ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(creator);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int bootingAnimation = setBootingAnimation(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, i);
            parcel2.writeNoException();
            parcel2.writeInt(bootingAnimation);
            return true;
        }

        public final boolean onTransact$setBootingAnimationSub$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Parcelable.Creator creator = ParcelFileDescriptor.CREATOR;
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(creator);
            ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(creator);
            parcel.enforceNoDataAvail();
            int bootingAnimationSub = setBootingAnimationSub(parcelFileDescriptor, parcelFileDescriptor2);
            parcel2.writeNoException();
            parcel2.writeInt(bootingAnimationSub);
            return true;
        }

        public final boolean onTransact$setEthernetConfiguration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            parcel.enforceNoDataAvail();
            int ethernetConfiguration = setEthernetConfiguration(i, string, string2, string3, string4);
            parcel2.writeNoException();
            parcel2.writeInt(ethernetConfiguration);
            return true;
        }

        public final boolean onTransact$setForcedDisplaySizeDensity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int forcedDisplaySizeDensity = setForcedDisplaySizeDensity(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeInt(forcedDisplaySizeDensity);
            return true;
        }

        public final boolean onTransact$setHardKeyIntentBroadcast$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string = parcel.readString();
            boolean z2 = parcel.readBoolean();
            boolean z3 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int hardKeyIntentBroadcast = setHardKeyIntentBroadcast(z, i, intent, string, z2, z3);
            parcel2.writeNoException();
            parcel2.writeInt(hardKeyIntentBroadcast);
            return true;
        }

        public final boolean onTransact$setHardKeyIntentBroadcastExternal$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string = parcel.readString();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int hardKeyIntentBroadcastExternal = setHardKeyIntentBroadcastExternal(z, i, i2, intent, string, z2);
            parcel2.writeNoException();
            parcel2.writeInt(hardKeyIntentBroadcastExternal);
            return true;
        }

        public final boolean onTransact$setHardKeyIntentBroadcastInternal$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string2 = parcel.readString();
            boolean z2 = parcel.readBoolean();
            boolean z3 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int hardKeyIntentBroadcastInternal = setHardKeyIntentBroadcastInternal(string, z, i, intent, string2, z2, z3);
            parcel2.writeNoException();
            parcel2.writeInt(hardKeyIntentBroadcastInternal);
            return true;
        }

        public final boolean onTransact$setHardKeyReportState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int hardKeyReportState = setHardKeyReportState(i, i2, i3, i4);
            parcel2.writeNoException();
            parcel2.writeInt(hardKeyReportState);
            return true;
        }

        public final boolean onTransact$setKeyedAppStatesReport$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            setKeyedAppStatesReport(contextInfo, string, string2, bundle, i);
            parcel2.writeNoException();
            return true;
        }

        public final boolean onTransact$setStatusBarText$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int statusBarText = setStatusBarText(string, i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(statusBarText);
            return true;
        }

        public final boolean onTransact$setStatusBarTextScrollWidth$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int statusBarTextScrollWidth = setStatusBarTextScrollWidth(string, i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeInt(statusBarTextScrollWidth);
            return true;
        }

        public final boolean onTransact$setToastGravity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int toastGravity = setToastGravity(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeInt(toastGravity);
            return true;
        }

        public final boolean onTransact$setUsbDeviceDefaultPackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            UsbDevice usbDevice = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int usbDeviceDefaultPackage = setUsbDeviceDefaultPackage(usbDevice, string, i);
            parcel2.writeNoException();
            parcel2.writeInt(usbDeviceDefaultPackage);
            return true;
        }

        public final boolean onTransact$setWallpaper$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int wallpaper = setWallpaper(bundle, rect, z, i);
            parcel2.writeNoException();
            parcel2.writeInt(wallpaper);
            return true;
        }

        public final boolean onTransact$setWifiState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int wifiState = setWifiState(z, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(wifiState);
            return true;
        }

        public final boolean onTransact$setWifiStateEap$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            parcel.enforceNoDataAvail();
            int wifiStateEap = setWifiStateEap(z, string, string2, string3);
            parcel2.writeNoException();
            parcel2.writeInt(wifiStateEap);
            return true;
        }

        public final boolean onTransact$startProKioskMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iStartProKioskMode = startProKioskMode(string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iStartProKioskMode);
            return true;
        }

        public final boolean onTransact$startTcpDump$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iStartTcpDump = startTcpDump(string, i);
            parcel2.writeNoException();
            parcel2.writeInt(iStartTcpDump);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
