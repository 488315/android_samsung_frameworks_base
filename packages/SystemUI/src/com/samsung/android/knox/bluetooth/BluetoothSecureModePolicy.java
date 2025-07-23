package com.samsung.android.knox.bluetooth;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BluetoothSecureModePolicy {
    public static final String TAG = "BTSecureModePolicy";
    public static IBluetoothSecureModePolicy mBTSecureModeService;
    public ContextInfo mContextInfo;

    public BluetoothSecureModePolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static IBluetoothSecureModePolicy getService() {
        if (mBTSecureModeService == null) {
            mBTSecureModeService = IBluetoothSecureModePolicy.Stub.asInterface(ServiceManager.getService("bluetooth_secure_mode_policy"));
        }
        return mBTSecureModeService;
    }

    public boolean addBluetoothDevicesToWhiteList(List<BluetoothSecureModeWhitelistConfig> list) {
        try {
            EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothSecureModePolicy.addBluetoothDevicesToWhiteList");
            if (getService() != null) {
                return mBTSecureModeService.addBluetoothDevicesToWhiteList(this.mContextInfo, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return false;
        }
    }

    public boolean disableSecureMode() {
        try {
            EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothSecureModePolicy.disableSecureMode");
            if (getService() != null) {
                return mBTSecureModeService.disableSecureMode(this.mContextInfo);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return false;
        }
    }

    public boolean enableDeviceWhiteList(boolean z) {
        try {
            EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothSecureModePolicy.enableDeviceWhiteList");
            if (getService() != null) {
                return mBTSecureModeService.enableDeviceWhiteList(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return false;
        }
    }

    public boolean enableSecureMode(BluetoothSecureModeConfig bluetoothSecureModeConfig, List<BluetoothSecureModeWhitelistConfig> list) {
        try {
            EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothSecureModePolicy.enableSecureMode");
            if (getService() != null) {
                return mBTSecureModeService.enableSecureMode(this.mContextInfo, bluetoothSecureModeConfig, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return false;
        }
    }

    public List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList() {
        try {
            if (getService() != null) {
                return mBTSecureModeService.getBluetoothDevicesFromWhiteList(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return null;
        }
    }

    public BluetoothSecureModeConfig getSecureModeConfiguration() {
        try {
            if (getService() != null) {
                return mBTSecureModeService.getSecureModeConfiguration(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return null;
        }
    }

    public boolean isDeviceWhiteListEnabled() {
        try {
            if (getService() != null) {
                return mBTSecureModeService.isDeviceWhiteListEnabled(this.mContextInfo);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return false;
        }
    }

    public boolean isSecureModeEnabled() {
        try {
            if (getService() != null) {
                return mBTSecureModeService.isSecureModeEnabled(this.mContextInfo);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return false;
        }
    }

    public boolean removeBluetoothDevicesFromWhiteList(List<BluetoothSecureModeWhitelistConfig> list) {
        try {
            EnterpriseLicenseManager.log(this.mContextInfo, "BluetoothSecureModePolicy.removeBluetoothDevicesFromWhiteList");
            if (getService() != null) {
                return mBTSecureModeService.removeBluetoothDevicesFromWhiteList(this.mContextInfo, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to BT Secure Mode service ", e);
            return false;
        }
    }
}
