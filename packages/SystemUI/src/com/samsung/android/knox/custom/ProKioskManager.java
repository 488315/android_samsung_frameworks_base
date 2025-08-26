package com.samsung.android.knox.custom;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* loaded from: classes4.dex */
public class ProKioskManager {
    public static final String TAG = "ProKioskManager";
    public static ContextInfo sContextInfo;
    public static ProKioskManager sProKioskManager;
    public IKnoxCustomManager mService;

    private ProKioskManager() {
    }

    public static synchronized ProKioskManager getInstance() {
        try {
            if (sProKioskManager == null) {
                sProKioskManager = new ProKioskManager();
            }
            if (sContextInfo == null) {
                sContextInfo = new ContextInfo();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sProKioskManager;
    }

    public String getExitUI(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getExitUI(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public boolean getHardKeyIntentState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getHardKeyIntentState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public int getHideNotificationMessages() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getHideNotificationMessages();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public String getHomeActivity() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getHomeActivity();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public boolean getInputMethodRestrictionState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getInputMethodRestrictionState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public int getMultiWindowFixedState(int i) {
        return 0;
    }

    public List<PowerItem> getPowerDialogCustomItems() {
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

    public boolean getPowerDialogCustomItemsState() {
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

    public int getPowerDialogItems() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getPowerDialogItems();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int getPowerDialogOptionMode() {
        if (getService() == null) {
            return 2;
        }
        try {
            return this.mService.getPowerDialogOptionMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 2;
        }
    }

    public boolean getProKioskState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getProKioskState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public String getProKioskString(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getProKioskString(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public int getSettingsEnabledItems() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getSettingsEnabledItems();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public boolean getStatusBarClockState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getProKioskStatusBarClockState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public boolean getStatusBarIconsState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getProKioskStatusBarIconsState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public int getStatusBarMode() {
        if (getService() == null) {
            return 2;
        }
        try {
            return this.mService.getProKioskStatusBarMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 2;
        }
    }

    public boolean getStatusBarNotificationsState() {
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

    public boolean getUsbMassStorageState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getProKioskUsbMassStorageState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public String getUsbNetAddress(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getUsbNetAddress(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public boolean getUsbNetState() {
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

    public boolean getVolumeKeyAppState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getVolumeKeyAppState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public List<String> getVolumeKeyAppsList() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getVolumeKeyAppsList();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public int setExitUI(String str, String str2) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setExitUI");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setExitUI(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setHardKeyIntentState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setHardKeyIntentState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHardKeyIntentState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setHideNotificationMessages(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setHideNotificationMessages");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHideNotificationMessages(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setHomeActivity(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setHomeActivity");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setHomeActivity(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setInputMethodRestrictionState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setInputMethodRestrictionState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setInputMethodRestrictionState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setMultiWindowFixedState(int i, int i2) {
        return -6;
    }

    public int setPassCode(String str, String str2) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setPassCode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setPassCode(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setPowerDialogCustomItems(List<PowerItem> list) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setPowerDialogCustomItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
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

    public int setPowerDialogCustomItemsState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setPowerDialogCustomItemsState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
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

    public int setPowerDialogItems(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setPowerDialogItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setPowerDialogItems(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setPowerDialogOptionMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setPowerDialogOptionMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setPowerDialogOptionMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setProKioskState(boolean z, String str) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setProKioskState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setProKioskState(z, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setProKioskString(int i, String str) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setProKioskString");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setProKioskString(i, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setSettingsEnabledItems(boolean z, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setSettingsEnabledItems");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setSettingsEnabledItems(z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setStatusBarClockState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setStatusBarClockState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setProKioskStatusBarClockState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setStatusBarIconsState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setStatusBarIconsState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setProKioskStatusBarIconsState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setStatusBarMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setStatusBarMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setProKioskStatusBarMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setStatusBarNotificationsState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setStatusBarNotificationsState");
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

    public int setUsbMassStorageState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setUsbMassStorageState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setProKioskUsbMassStorageState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setUsbNetAddresses(String str, String str2) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setUsbNetAddresses");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
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

    public int setUsbNetState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setUsbNetState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
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

    public int setVolumeKeyAppState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setVolumeKeyAppState");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setVolumeKeyAppState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setVolumeKeyAppsList(List<String> list) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.setVolumeKeyAppsList");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setVolumeKeyAppsList(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int startProKioskMode(String str, String str2) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.startProKioskMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.startProKioskMode(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int stopProKioskMode(String str) {
        EnterpriseLicenseManager.log(sContextInfo, "ProKioskManager.stopProKioskMode");
        if (CustomDeviceManager.getInstance().earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.stopProKioskMode(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }
}
