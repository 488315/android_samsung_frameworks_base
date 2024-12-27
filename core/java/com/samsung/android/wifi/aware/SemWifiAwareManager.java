package com.samsung.android.wifi.aware;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemWifiAwareManager {
    public static final String EXTRA_VENDOR_NAN_COMMAND_ID = "wifi_vendor_nan_command_id";
    public static final String EXTRA_VENDOR_NAN_RESPONSE = "wifi_vendor_nan_response";
    public static final String EXTRA_VENDOR_NAN_STATUS_CODE = "wifi_vendor_nan_status_code";
    public static final String SEM_WIFI_VENDOR_NAN_COMMAND_RESPONSE_ACTION = "com.samsung.android.wifi.aware.NAN_COMMAND_RESPONSE";
    public static final int STATUS_FALSE = 2;
    public static final int STATUS_TRUE = 1;
    public static final int STATUS_UNABLE_TO_CHECK = 0;
    private static final String TAG = "SemWifiAwareManager";
    private final Context mContext;
    private final ISemWifiAwareManager mService;

    public SemWifiAwareManager(Context context, ISemWifiAwareManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public void setClusterMergingEnabled(boolean enable) {
        try {
            this.mService.setClusterMergingEnabled(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int isPreEnabled() {
        try {
            return this.mService.isPreEnabled();
        } catch (RemoteException e) {
            return 0;
        }
    }

    public void setNanCommand(int cmdId, byte[] cmd) {
        try {
            this.mService.setNanCommand(cmdId, cmd);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVendorNanServiceAvailable() {
        try {
            return this.mService.isVendorNanServiceAvailable();
        } catch (RemoteException e) {
            return false;
        }
    }

    public long getStdPlusFeature() {
        try {
            return this.mService.getStdPlusFeature();
        } catch (RemoteException e) {
            return 0L;
        }
    }

    public boolean isAwareSoftApConcurrencySupported() {
        try {
            return this.mService.isAwareSoftApConcurrencySupported();
        } catch (RemoteException e) {
            Log.w(TAG, "isAwareSoftApConcurrencySupported:" + e.getMessage());
            return false;
        }
    }

    public boolean isAwareP2pConcurrencySupported() {
        try {
            return this.mService.isAwareP2pConcurrencySupported();
        } catch (RemoteException e) {
            Log.w(TAG, "isAwareP2pConcurrencySupported:" + e.getMessage());
            return false;
        }
    }

    public int getMaxNdpCountForAwareP2p() {
        try {
            return this.mService.getMaxNdpCountForAwareP2p();
        } catch (RemoteException e) {
            return 2;
        }
    }

    public int getMaxNdpCountForAwareSoftAp() {
        try {
            return this.mService.getMaxNdpCountForAwareSoftAp();
        } catch (RemoteException e) {
            return 8;
        }
    }
}
