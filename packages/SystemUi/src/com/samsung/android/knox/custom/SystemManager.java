package com.samsung.android.knox.custom;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SystemManager {
    public static final String TAG = "SystemManager";
    public static ContextInfo sContextInfo;
    public static SystemManager sSystemManager;
    public IKnoxCustomManager mService;

    private SystemManager() {
    }

    public static synchronized SystemManager getInstance() {
        SystemManager systemManager;
        synchronized (SystemManager.class) {
            if (sSystemManager == null) {
                sSystemManager = new SystemManager();
            }
            if (sContextInfo == null) {
                sContextInfo = new ContextInfo();
            }
            systemManager = sSystemManager;
        }
        return systemManager;
    }

    public final int addAutoCallNumber(String str, int i, int i2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.addAutoCallNumber");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.addAutoCallNumber(str, i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int addPackagesToUltraPowerSaving(List<String> list) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.addPackagesToUltraPowerSaving");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.addPackagesToUltraPowerSaving(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int addShortcut(int i, int i2, int i3, String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.removeWidget");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.addShortcut(i, i2, i3, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int addShortcutToHomeScreen(ShortcutItem shortcutItem) {
        return -1;
    }

    public final int addWidget(int i, int i2, int i3, int i4, int i5, String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.addWidget");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.addWidget(i, i2, i3, i4, i5, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int addWidgetToHomeScreen(WidgetItem widgetItem) {
        return -1;
    }

    public final int clearAnimation(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.clearAnimation");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.clearAnimation(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int copyAdbLog(int i) {
        return -6;
    }

    public final int deleteHomeScreenPage(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.deleteHomeScreenPage");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.deleteHomeScreenPage(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int deleteMessagesByIds(List<String> list) {
        return -6;
    }

    public final int deleteMessagesByNumber(String str) {
        return -6;
    }

    public final int dialEmergencyNumber(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.dialEmergencyNumber");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.dialEmergencyNumber(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int getAccessibilitySettingsItems() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getAccessibilitySettingsItems();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final List<String> getAppBlockDownloadNamespaces() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAppBlockDownloadNamespaces();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final boolean getAppBlockDownloadState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getAppBlockDownloadState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final int getAppsButtonState() {
        if (getService() == null) {
            return 2;
        }
        try {
            return this.mService.getAppsButtonState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 2;
        }
    }

    public final String getAsoc() {
        if (getService() == null) {
            return "";
        }
        try {
            return this.mService.getAsoc();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return "";
        }
    }

    public final int getAutoCallNumberAnswerMode(String str) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getAutoCallNumberAnswerMode(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getAutoCallNumberDelay(String str) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getAutoCallNumberDelay(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final List<String> getAutoCallNumberList() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAutoCallNumberList();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final int getAutoCallPickupState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getAutoCallPickupState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getAutoRotationState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAutoRotationState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final StatusbarIconItem getBatteryLevelColourItem() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getBatteryLevelColourItem();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final String getBsoh() {
        if (getService() == null) {
            return "";
        }
        try {
            return this.mService.getBsoh();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return "";
        }
    }

    public final String getBsohUnbiased() {
        if (getService() == null) {
            return "";
        }
        try {
            return this.mService.getBsohUnbiased();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return "";
        }
    }

    public final int getCallScreenDisabledItems() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getCallScreenDisabledItems();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getChargerConnectionSoundEnabledState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getChargerConnectionSoundEnabledState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final boolean getCheckCoverPopupState() {
        return true;
    }

    public final String getCustomOperatorName() {
        return null;
    }

    public final boolean getDeviceSpeakerEnabledState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getDeviceSpeakerEnabledState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final boolean getDisplayMirroringState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getDisplayMirroringState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final List<String> getExcludedMessagesNotifications() {
        return null;
    }

    public final boolean getExtendedCallInfoState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getExtendedCallInfoState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final String getFavoriteApp(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getFavoriteApp(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final int getFavoriteAppsMaxCount() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getFavoriteAppsMaxCount();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int getForceAutoShutDownState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getForceAutoShutDownState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getForceAutoStartUpState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getForceAutoStartUpState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getGearNotificationState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getGearNotificationState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final int getHardKeyBlockState(int i, int i2) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getHardKeyBlockState(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getHardKeyIntentBroadcast(int i, int i2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.getHardKeyIntentBroadcast");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getHardKeyIntentBroadcast(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int getHardKeyIntentState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getHardKeyIntentMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getHomeScreenMode() {
        if (getService() == null) {
            return 1;
        }
        try {
            return this.mService.getHomeScreenMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 1;
        }
    }

    public final boolean getInfraredState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getInfraredState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final int getKeyboardMode() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getKeyboardMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getLcdBacklightState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getLcdBacklightState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final int getLockScreenHiddenItems() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getLockScreenHiddenItems();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getLockScreenOverrideMode() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getLockScreenOverrideMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final String getLockScreenShortcut(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getLockScreenShortcut(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final String getMacAddress() {
        if (getService() == null) {
            return "02:00:00:00:00:00";
        }
        try {
            return this.mService.getMacAddress();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return "02:00:00:00:00:00";
        }
    }

    public final List<String> getMessageIdsMarkedToDelete() {
        return null;
    }

    public final int getMobileNetworkType() {
        return -1;
    }

    public final String getParentScreen(int i) {
        return AbstractC0000x2c234b15.m0m("CDM_SCREEN_NUMBER:", i);
    }

    public final List<PowerItem> getPowerDialogCustomItems() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPowerDialogCustomItems();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final boolean getPowerDialogCustomItemsState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getPowerDialogCustomItemsState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final boolean getPowerMenuLockedState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getPowerMenuLockedState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final int getQuickPanelButtons() {
        if (getService() == null) {
            return 7;
        }
        try {
            return this.mService.getQuickPanelButtons();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 7;
        }
    }

    public final int getQuickPanelEditMode() {
        if (getService() == null) {
            return 1;
        }
        try {
            return this.mService.getQuickPanelEditMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 1;
        }
    }

    public final List<Integer> getQuickPanelItems() {
        ArrayList arrayList = new ArrayList();
        if (getService() != null) {
            try {
                for (String str : this.mService.getQuickPanelItems().split(",")) {
                    if (str.length() > 0) {
                        arrayList.add(Integer.valueOf(Integer.parseInt(str)));
                    }
                }
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            }
        }
        return arrayList;
    }

    public final String getRecentLongPressActivity() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getRecentLongPressActivity();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final int getRecentLongPressMode() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getRecentLongPressMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final boolean getScreenOffOnHomeLongPressState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getScreenOffOnHomeLongPressState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final boolean getScreenOffOnStatusBarDoubleTapState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getScreenOffOnStatusBarDoubleTapState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final int getScreenTimeout() {
        int screenTimeout;
        if (getService() != null) {
            try {
                screenTimeout = this.mService.getScreenTimeout();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            }
            return screenTimeout / 1000;
        }
        screenTimeout = 0;
        return screenTimeout / 1000;
    }

    public final int getSensorDisabled() {
        return -6;
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public final boolean getStatusBarClockState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getStatusBarClockState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final boolean getStatusBarIconsState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getStatusBarIconsState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final int getStatusBarMode() {
        if (getService() == null) {
            return 2;
        }
        try {
            return this.mService.getStatusBarMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 2;
        }
    }

    public final boolean getStatusBarNotificationsState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getStatusBarNotificationsState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final String getStatusBarText() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getStatusBarText();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final int getStatusBarTextScrollWidth() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getStatusBarTextScrollWidth();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getStatusBarTextSize() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getStatusBarTextSize();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getStatusBarTextStyle() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getStatusBarTextStyle();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getSystemSoundsEnabledState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getSystemSoundsEnabledState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final ParcelFileDescriptor getTcpDump() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getTcpDump();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final boolean getToastEnabledState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getToastEnabledState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final int getToastGravity() {
        return -6;
    }

    public final boolean getToastGravityEnabledState() {
        return false;
    }

    public final int getToastGravityXOffset() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getToastGravityXOffset();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final int getToastGravityYOffset() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getToastGravityYOffset();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getToastShowPackageNameState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getToastShowPackageNameState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final boolean getTorchOnVolumeButtonsState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getTorchOnVolumeButtonsState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final List<String> getUltraPowerSavingPackages() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getUltraPowerSavingPackages();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final boolean getUnlockSimOnBootState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getUnlockSimOnBootState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final String getUnlockSimPin() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getUnlockSimPin();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        } catch (SecurityException unused) {
            return null;
        }
    }

    public final int getUsbConnectionType() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getUsbConnectionType();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getUsbMassStorageState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getUsbMassStorageState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final String getUsbNetAddress(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getUsbNetAddress(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        } catch (SecurityException unused) {
            return null;
        }
    }

    public final boolean getUsbNetState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getUsbNetState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final boolean getUsbNetStateInternal() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getUsbNetStateInternal();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final int getUserInactivityTimeout() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getUserInactivityTimeout();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int getVibrationIntensity(int i) {
        CustomDeviceManager.getInstance();
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getVibrationIntensity(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getVolumeButtonRotationState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getVolumeButtonRotationState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final int getVolumeControlStream() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getVolumeControlStream();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getVolumePanelEnabledState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getVolumePanelEnabledState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final int getWifiAutoSwitchDelay() {
        return 20;
    }

    public final boolean getWifiAutoSwitchState() {
        return false;
    }

    public final int getWifiAutoSwitchThreshold() {
        return -200;
    }

    public final boolean getWifiConnectedMessageState() {
        return true;
    }

    public final int getWifiHotspotEnabledState() {
        return 0;
    }

    public final int getZeroPageState() {
        if (getService() == null) {
            return 2;
        }
        try {
            return this.mService.getZeroPageState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 2;
        }
    }

    public final int powerOff() {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.powerOff");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.powerOff();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final String readFile(String str) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.readFile(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final int removeAutoCallNumber(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.removeAutoCallNumber");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.removeAutoCallNumber(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int removeFavoriteApp(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.removeFavoriteApp");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.removeFavoriteApp(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int removeKnoxCustomShortcutsFromHomeScreen() {
        return -1;
    }

    public final int removeLockScreen() {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.removeLockScreen");
        return -6;
    }

    public final int removePackagesFromUltraPowerSaving(List<String> list) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.removePackagesFromUltraPowerSaving");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.removePackagesFromUltraPowerSaving(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int removeShortcut(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.removeShortcut");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.removeShortcut(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int removeShortcutFromHomeScreen(int i, String str, int i2) {
        return -1;
    }

    public final int removeWidget(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.removeWidget");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.removeWidget(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int removeWidgetFromHomeScreen(Intent intent, int i, int i2) {
        return -1;
    }

    public final int sendDtmfTone(char c, int i) {
        return -6;
    }

    public final int setAccessibilitySettingsItems(int i, int i2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setAccessibilitySettingsItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAccessibilitySettingsItems(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setAppBlockDownloadNamespaces(List<String> list) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setAppBlockDownloadNamespaces");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAppBlockDownloadNamespaces(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setAppBlockDownloadState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setAppBlockDownloadState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAppBlockDownloadState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setAppsButtonState(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setAppsButtonState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAppsButtonState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setAsoc(int i) {
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_8)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAsoc(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setAudioVolume(int i, int i2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setAudioVolume");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAudioVolume(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setAutoCallPickupState(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setAutoCallPickupState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAutoCallPickupState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setAutoRotationState(boolean z, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setAutoRotationState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAutoRotationState(z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setBatteryLevelColourItem");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setBatteryLevelColourItem(statusbarIconItem);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setBootAnimation(String str, String str2, String str3, int i) {
        return -6;
    }

    public final int setBootingAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setBootingAnimation");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setBootingAnimation(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setBootingAnimationSub(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setBootingAnimationSub");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setBootingAnimationSub(parcelFileDescriptor, parcelFileDescriptor2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setBrowserHomepage(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setBrowserHomepage");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setBrowserHomepage(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setCallScreenDisabledItems(boolean z, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setCallScreenDisabledItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setCallScreenDisabledItems(z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setChargerConnectionSoundEnabledState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setChargerConnectionSoundEnabledState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setChargerConnectionSoundEnabledState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setCheckCoverPopupState(boolean z) {
        return -6;
    }

    public final int setCustomOperatorName(String str) {
        return -6;
    }

    public final int setDeviceSpeakerEnabledState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setDeviceSpeakerEnabledState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setDeviceSpeakerEnabledState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setDisplayMirroringState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setDisplayMirroringState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setDisplayMirroringState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setExcludedMessagesNotifications(boolean z, List<String> list) {
        return -6;
    }

    public final int setExtendedCallInfoState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setExtendedCallInfoState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setExtendedCallInfoState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setFavoriteApp(String str, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setFavoriteApp");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setFavoriteApp(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setForceAutoShutDownState(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setForceAutoShutDownState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_8)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setForceAutoShutDownState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setForceAutoStartUpState(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setForceAutoStartUpState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setForceAutoStartUpState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setGearNotificationState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setGearNotificationState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setGearNotificationState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setHardKeyIntentBroadcast(boolean z, int i, Intent intent, String str, boolean z2, boolean z3) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setHardKeyIntentBroadCast");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHardKeyIntentBroadcast(z, i, intent, str, z2, z3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setHardKeyIntentState(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setHardKeyIntentState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_0)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHardKeyIntentMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setHomeScreenMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setHomeScreenMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_0)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHomeScreenMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setInfraredState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setInfraredState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setInfraredState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setKeyboardMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setKeyboardMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setKeyboardMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setLcdBacklightState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setLcdBacklightState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setLcdBacklightState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setLockScreenHiddenItems(boolean z, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setLockScreenHiddenItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setLockScreenHiddenItems(z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setLockScreenOverrideMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setLockScreenOverrideMode");
        return -6;
    }

    public final int setLockScreenShortcut(int i, String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setLockScreenShortcut");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setLockScreenShortcut(i, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setLockscreenWallpaper(String str, int i) {
        return -6;
    }

    public final int setMobileNetworkType(int i) {
        return -6;
    }

    public final int setMultiWindowState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setMultiWindowState");
        return -6;
    }

    public final int setPowerDialogCustomItems(List<PowerItem> list) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setPowerDialogCustomItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setPowerDialogCustomItems(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setPowerDialogCustomItemsState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setPowerDialogCustomItemsState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setPowerDialogCustomItemsState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setPowerMenuLockedState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setPowerMenuLockedState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setPowerMenuLockedState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setQuickPanelButtons(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setQuickPanelButtons");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setQuickPanelButtons(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setQuickPanelEditMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setQuickPanelEditMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setQuickPanelEditMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setQuickPanelItems(List<Integer> list) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setQuickPanelItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (Integer num : list) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("" + num);
            }
            return this.mService.setQuickPanelItems(sb.toString());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setRecentLongPressActivity(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setRecentLongPressActivity");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setRecentLongPressActivity(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setRecentLongPressMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setRecentLongPressMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setRecentLongPressMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setScreenOffOnHomeLongPressState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setScreenOffOnHomeLongPressState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setScreenOffOnHomeLongPressState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setScreenOffOnStatusBarDoubleTapState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setScreenOffOnStatusBarDoubleTapState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setScreenOffOnStatusBarDoubleTapState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setScreenTimeout(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setScreenTimeout");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setScreenTimeout(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setSensorDisabled(boolean z, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setSensorDisabled");
        return -6;
    }

    public final int setShutdownAnimation(String str, String str2) {
        return -6;
    }

    public final int setShuttingDownAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setShuttingDownAnimation");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setShuttingDownAnimation(parcelFileDescriptor, parcelFileDescriptor2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setShuttingDownAnimationSub(ParcelFileDescriptor parcelFileDescriptor) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setShuttingDownAnimationSub");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setShuttingDownAnimationSub(parcelFileDescriptor);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setStatusBarClockState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setStatusBarClockState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setStatusBarClockState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setStatusBarIconsState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setStatusBarIconsState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setStatusBarIconsState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setStatusBarMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setStatusBarMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setStatusBarMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setStatusBarNotificationsState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setStatusBarNotificationsState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setStatusBarNotificationsState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setStatusBarText(String str, int i, int i2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setStatusBarText");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setStatusBarText(str, i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setStatusBarTextScrollWidth(String str, int i, int i2, int i3) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setStatusBarTextScrollWidth");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setStatusBarTextScrollWidth(str, i, i2, i3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setSystemRingtone(int i, String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setSystemRingtone");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setSystemRingtone(i, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setSystemSoundsEnabledState(int i, int i2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setSystemSoundsEnabledState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setSystemSoundsEnabledState(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setSystemSoundsSilent() {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setSystemSoundsSilent");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setSystemSoundsSilent();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setToastEnabledState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setToastEnabledState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setToastEnabledState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setToastGravity(int i, int i2, int i3) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setToastGravity");
        return -6;
    }

    public final int setToastGravityEnabledState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setToastGravityEnabledState");
        return -6;
    }

    public final int setToastShowPackageNameState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setToastShowPackageNameState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setToastShowPackageNameState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setTorchOnVolumeButtonsState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setTorchOnVolumeButtonsState");
        return -6;
    }

    public final int setUnlockSimOnBootState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setUnlockSimOnBootState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUnlockSimOnBootState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setUnlockSimPin(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setUnlockSimPin");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUnlockSimPin(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setUsbConnectionType(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setUsbConnectionType");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUsbConnectionType(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setUsbMassStorageState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setUsbMassStorageState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUsbMassStorageState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setUsbNetAddresses(String str, String str2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setUsbNetAddresses");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUsbNetAddresses(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setUsbNetState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setUsbNetState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUsbNetState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setUserInactivityTimeout(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setUserInactivityTimeout");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUserInactivityTimeout(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setVibrationIntensity(int i, int i2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setVibrationIntensity");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setVibrationIntensity(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setVolumeButtonRotationState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setVolumeButtonRotationState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setVolumeButtonRotationState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setVolumeControlStream(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setVolumeControlStream");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setVolumeControlStream(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setVolumePanelEnabledState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setVolumePanelEnabledState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setVolumePanelEnabledState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setWifiAutoSwitchDelay(int i) {
        return -6;
    }

    public final int setWifiAutoSwitchState(boolean z) {
        return -6;
    }

    public final int setWifiAutoSwitchThreshold(int i) {
        return -6;
    }

    public final int setWifiConnectedMessageState(boolean z) {
        return -6;
    }

    public final int setWifiHotspotEnabledState(int i) {
        return -6;
    }

    public final int setZeroPageState(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setZeroPageState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setZeroPageState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int startTcpDump(String str, int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.startTcpDump(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int stopTcpDump() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.stopTcpDump();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int getHardKeyIntentState(int i, int i2) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getHardKeyReportState(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public final boolean getKeyboardMode(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getKeyboardModeOverriden(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public static synchronized SystemManager getInstance(int i) {
        SystemManager systemManager;
        synchronized (SystemManager.class) {
            if (sSystemManager == null) {
                sSystemManager = new SystemManager();
            }
            sContextInfo = new ContextInfo(Process.myUid(), false, i);
            systemManager = sSystemManager;
        }
        return systemManager;
    }

    public final int setHardKeyIntentBroadcast(boolean z, int i, int i2, Intent intent, String str, boolean z2) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setHardKeyIntentBroadCast");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHardKeyIntentBroadcastExternal(z, i, i2, intent, str, z2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setHardKeyIntentState(int i, int i2, int i3, int i4) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setHardKeyIntentState(int, int, int, int)");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHardKeyReportState(i, i2, i3, i4);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setQuickPanelItems(Bundle bundle) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setQuickPanelItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_4_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setQuickPanelItemsInternal(bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final int setHardKeyIntentBroadcast(String str, boolean z, int i, Intent intent, String str2, boolean z2, boolean z3) {
        EnterpriseLicenseManager.log(sContextInfo, "SystemManager.setHardKeyIntentBroadCast");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHardKeyIntentBroadcastInternal(str, z, i, intent, str2, z2, z3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }
}
