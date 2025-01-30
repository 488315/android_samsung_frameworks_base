package com.samsung.android.knox.bluetooth;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BluetoothSecureModePolicy {
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

    public final boolean addBluetoothDevicesToWhiteList(List<BluetoothSecureModeWhitelistConfig> list) {
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

    public final boolean disableSecureMode() {
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

    public final boolean enableDeviceWhiteList(boolean z) {
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

    public final boolean enableSecureMode(BluetoothSecureModeConfig bluetoothSecureModeConfig, List<BluetoothSecureModeWhitelistConfig> list) {
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

    public final List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList() {
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

    public final BluetoothSecureModeConfig getSecureModeConfiguration() {
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

    public final boolean isDeviceWhiteListEnabled() {
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

    public final boolean isSecureModeEnabled() {
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

    public final boolean removeBluetoothDevicesFromWhiteList(List<BluetoothSecureModeWhitelistConfig> list) {
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
