package com.samsung.android.knox.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.bluetooth.IBluetoothPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BluetoothPolicy {
    public static final int NO_PROFILE = -1;
    public static String TAG = "BluetoothPolicy";
    public ContextInfo mContextInfo;
    public IBluetoothPolicy mService;

    public class BluetoothProfile {
        public static final int BLUETOOTH_A2DP_PROFILE = 8;
        public static final int BLUETOOTH_AVRCP_PROFILE = 16;
        public static final int BLUETOOTH_BPP_PROFILE = 512;
        public static final int BLUETOOTH_DUN_PROFILE = 32;
        public static final int BLUETOOTH_FTP_PROFILE = 64;
        public static final int BLUETOOTH_HFP_PROFILE = 2;
        public static final int BLUETOOTH_HSP_PROFILE = 1;
        public static final int BLUETOOTH_PBAP_PROFILE = 4;
        public static final int BLUETOOTH_SAP_PROFILE = 256;
        public static final int BLUETOOTH_SPP_PROFILE = 128;

        public BluetoothProfile(BluetoothPolicy bluetoothPolicy) {
        }
    }

    public class BluetoothUUID {
        public static final String A2DP_ADVAUDIODIST_UUID = "0000110D-0000-1000-8000-00805F9B34FB";
        public static final String A2DP_AUDIOSINK_UUID = "0000110B-0000-1000-8000-00805F9B34FB";
        public static final String A2DP_AUDIOSOURCE_UUID = "0000110A-0000-1000-8000-00805F9B34FB";
        public static final String AVRCP_CONTROLLER_UUID = "0000110E-0000-1000-8000-00805F9B34FB";
        public static final String AVRCP_TARGET_UUID = "0000110C-0000-1000-8000-00805F9B34FB";
        public static final String BNEP_UUID = "0000000f-0000-1000-8000-00805F9B34FB";
        public static final String BPP_UUID = "00001122-0000-1000-8000-00805f9b34fb";
        public static final String DUN_UUID = "00001103-0000-1000-8000-00805f9b34fb";
        public static final String FTP_UUID = "00001106-0000-1000-8000-00805f9b34fb";
        public static final String HFP_AG_UUID = "0000111F-0000-1000-8000-00805F9B34FB";
        public static final String HFP_UUID = "0000111E-0000-1000-8000-00805F9B34FB";
        public static final String HID_UUID = "00001124-0000-1000-8000-00805f9b34fb";
        public static final String HSP_AG_UUID = "00001112-0000-1000-8000-00805F9B34FB";
        public static final String HSP_UUID = "00001108-0000-1000-8000-00805F9B34FB";
        public static final String NAP_UUID = "00001116-0000-1000-8000-00805F9B34FB";
        public static final String OBEXOBJECTPUSH_UUID = "00001105-0000-1000-8000-00805f9b34fb";
        public static final String PANU_UUID = "00001115-0000-1000-8000-00805F9B34FB";
        public static final String PBAP_PSE_UUID = "0000112f-0000-1000-8000-00805F9B34FB";
        public static final String PBAP_UUID = "00001130-0000-1000-8000-00805f9b34fb";
        public static final String SAP_UUID = "0000112D-0000-1000-8000-00805F9B34FB";
        public static final String SPP_UUID = "00001101-0000-1000-8000-00805f9b34fb";

        public BluetoothUUID(BluetoothPolicy bluetoothPolicy) {
        }
    }

    public BluetoothPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public boolean activateBluetoothDeviceRestriction(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.activateBluetoothDeviceRestriction");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.activateBluetoothDeviceRestriction(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean activateBluetoothUUIDRestriction(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.activateBluetoothUUIDRestriction");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.activateBluetoothUUIDRestriction(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean addBluetoothDevicesToBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.addBluetoothDevicesToBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addBluetoothDevicesToBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean addBluetoothDevicesToWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.addBluetoothDevicesToWhiteList(List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addBluetoothDevicesToWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean addBluetoothUUIDsToBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.addBluetoothUUIDsToBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addBluetoothUUIDsToBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean addBluetoothUUIDsToWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.addBluetoothUUIDsToWhiteList(List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addBluetoothUUIDsToWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean allowBLE(boolean z, ContextInfo contextInfo) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowBLE(contextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean allowBluetooth(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowBluetooth(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public boolean allowCallerIDDisplay(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.allowCallerIDDisplay");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowCallerIDDisplay(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to block caller id display ");
            return false;
        }
    }

    public boolean allowOutgoingCalls(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.allowOutgoingCalls");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowOutgoingCalls(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public void bluetoothLog(String str, String str2) {
        if (getService() != null) {
            try {
                this.mService.bluetoothLog(this.mContextInfo, str, str2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with bluetooth policy", e);
            }
        }
    }

    public boolean clearBluetoothDevicesFromBlackList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.clearBluetoothDevicesFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearBluetoothDevicesFromBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean clearBluetoothDevicesFromList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.clearBluetoothDevicesFromList");
        return clearBluetoothDevicesFromBlackList() && clearBluetoothDevicesFromWhiteList();
    }

    public boolean clearBluetoothDevicesFromWhiteList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.clearBluetoothDevicesFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearBluetoothDevicesFromWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean clearBluetoothUUIDsFromBlackList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.clearBluetoothUUIDsFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearBluetoothUUIDsFromBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean clearBluetoothUUIDsFromList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.clearBluetoothUUIDsFromList");
        return clearBluetoothUUIDsFromBlackList() && clearBluetoothUUIDsFromWhiteList();
    }

    public boolean clearBluetoothUUIDsFromWhiteList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.clearBluetoothUUIDsFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearBluetoothUUIDsFromWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public final String convertBluetoothProfile(int i) {
        if (i == 9) {
            return "Profile: MAP\n";
        }
        switch (i) {
            case 1:
                return "Profile: Headset and Handsfree\n";
            case 2:
                return "Profile: A2DP\n";
            case 3:
                return "Profile: HEALTH\n";
            case 4:
                return "Profile: INPUT DEVICE\n";
            case 5:
                return "Profile: PAN\n";
            case 6:
                return "Profile: PBAP\n";
            default:
                return "";
        }
    }

    public boolean getAllowBluetoothDataTransfer() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAllowBluetoothDataTransfer(this.mContextInfo, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public List<BluetoothControlInfo> getBluetoothDevicesFromBlackLists() {
        if (getService() != null) {
            try {
                return this.mService.getAllBluetoothDevicesBlackLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public List<BluetoothControlInfo> getBluetoothDevicesFromWhiteLists() {
        if (getService() != null) {
            try {
                return this.mService.getAllBluetoothDevicesWhiteLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getBluetoothLog() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getBluetoothLog");
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.getBluetoothLog");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getBluetoothLog(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return null;
        }
    }

    public List<BluetoothControlInfo> getBluetoothUUIDsFromBlackLists() {
        if (getService() != null) {
            try {
                return this.mService.getAllBluetoothUUIDsBlackLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public List<BluetoothControlInfo> getBluetoothUUIDsFromWhiteLists() {
        if (getService() != null) {
            try {
                return this.mService.getAllBluetoothUUIDsWhiteLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getEffectiveBluetoothDevicesBlackLists() {
        if (getService() != null) {
            try {
                return this.mService.getEffectiveBluetoothDevicesBlackLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getEffectiveBluetoothDevicesWhiteLists() {
        if (getService() != null) {
            try {
                return this.mService.getEffectiveBluetoothDevicesWhiteLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getEffectiveBluetoothUUIDsBlackLists() {
        if (getService() != null) {
            try {
                return this.mService.getEffectiveBluetoothUUIDsBlackLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getEffectiveBluetoothUUIDsWhiteLists() {
        if (getService() != null) {
            try {
                return this.mService.getEffectiveBluetoothUUIDsWhiteLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Bluetooth policy", e);
            }
        }
        return new ArrayList();
    }

    public final IBluetoothPolicy getService() {
        if (this.mService == null) {
            this.mService = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        }
        return this.mService;
    }

    public boolean isBLEAllowed() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isBLEAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean isBluetoothDeviceAllowed(String str) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBluetoothDeviceAllowed(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return true;
        }
    }

    public boolean isBluetoothDeviceRestrictionActive() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.isBluetoothDeviceRestrictionActive");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isBluetoothDeviceRestrictionActive(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean isBluetoothEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBluetoothEnabled(this.mContextInfo);
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean isBluetoothLogEnabled() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isBluetoothLogEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isBluetoothLogEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean isBluetoothUUIDAllowed(String str) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBluetoothUUIDAllowed(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return true;
        }
    }

    public boolean isBluetoothUUIDRestrictionActive() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isBluetoothUUIDRestrictionActive(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean isCallerIDDisplayAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isCallerIDDisplayAllowed(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed getting caller id display status");
            return true;
        }
    }

    public boolean isDesktopConnectivityEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isDesktopConnectivityEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public boolean isDiscoverableEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isDiscoverableEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public boolean isLimitedDiscoverableEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isLimitedDiscoverableEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public boolean isOutgoingCallsAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isOutgoingCallsAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public boolean isPairingEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPairingEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public boolean isProfileEnabled(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isProfileEnabled(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public boolean isRequiredPasswordForDiscovery() {
        return false;
    }

    public boolean isRequiredPasswordForEnable() {
        return false;
    }

    public boolean removeBluetoothDevicesFromBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.removeBluetoothDevicesFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeBluetoothDevicesFromBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean removeBluetoothDevicesFromWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.removeBluetoothDevicesFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeBluetoothDevicesFromWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean removeBluetoothUUIDsFromBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.removeBluetoothUUIDsFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeBluetoothUUIDsFromBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean removeBluetoothUUIDsFromWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.removeBluetoothUUIDsFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeBluetoothUUIDsFromWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Bluetooth policy", e);
            return false;
        }
    }

    public boolean setAllowBluetoothDataTransfer(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.setAllowBluetoothDataTransfer");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAllowBluetoothDataTransfer(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean setBluetoothLogEnabled(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setBluetoothLogEnabled");
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.setBluetoothLogEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setBluetoothLogEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean setBluetoothState(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setBluetooth(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public boolean setDesktopConnectivityState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.setDesktopConnectivityState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setDesktopConnectivityState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean setDiscoverableState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.setDiscoverableState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setDiscoverableState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean setLimitedDiscoverableState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.setLimitedDiscoverableState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setLimitedDiscoverableState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean setPairingState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.setPairingState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPairingState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean setProfileState(boolean z, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.setProfileState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setProfileState(this.mContextInfo, z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public boolean setRequiredPasswordForDiscovery(boolean z) {
        return false;
    }

    public boolean setRequiredPasswordForEnable(boolean z) {
        return false;
    }

    public boolean isBluetoothEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBluetoothEnabledWithMsg(z);
        } catch (Exception unused) {
            return true;
        }
    }

    public void bluetoothLog(String str, int i, BluetoothDevice bluetoothDevice) {
        String address;
        String name;
        String address2;
        String name2;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            address = "";
            name = address;
        } else {
            name = defaultAdapter.getName();
            address = defaultAdapter.getAddress();
        }
        if (bluetoothDevice == null) {
            address2 = "";
            name2 = address2;
        } else {
            name2 = bluetoothDevice.getName();
            address2 = bluetoothDevice.getAddress();
        }
        StringBuilder sb = new StringBuilder("");
        if (i != -1) {
            sb.append(convertBluetoothProfile(i));
        }
        if (address2 != null && address2.length() > 0) {
            sb.append("Remote Address: ");
            sb.append(address2);
            sb.append('\n');
        }
        if (address2 != null && address2.length() > 0) {
            sb.append("Remote Name: ");
            sb.append(name2);
            sb.append('\n');
        }
        if (address != null && address.length() > 0) {
            sb.append("Local Address: ");
            sb.append(address);
            sb.append('\n');
        }
        if (address != null && address.length() > 0) {
            sb.append("Local Name: ");
            sb.append(name);
            sb.append('\n');
        }
        bluetoothLog(str, sb.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean addBluetoothDevicesToWhiteList(List<String> list, boolean z) {
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.addBluetoothDevicesToWhiteList(List<String>, boolean)");
        if (z) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("*");
            if (addBluetoothDevicesToBlackList(arrayList)) {
                z2 = true;
            } else {
                Log.d(TAG, "Failed to update WildCard");
                z2 = false;
            }
        }
        return addBluetoothDevicesToWhiteList(list) && z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean addBluetoothUUIDsToWhiteList(List<String> list, boolean z) {
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothPolicy.addBluetoothUUIDsToWhiteList(List<String>, boolean)");
        if (z) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("*");
            if (addBluetoothUUIDsToBlackList(arrayList)) {
                z2 = true;
            } else {
                Log.d(TAG, "Failed to update wildCard");
                z2 = false;
            }
        }
        return addBluetoothUUIDsToWhiteList(list) && z2;
    }
}
