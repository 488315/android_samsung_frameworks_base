package com.samsung.android.iccc;

import android.os.RemoteException;
import android.util.Log;

import java.nio.charset.Charset;

/* loaded from: classes6.dex */
public class IntegrityControlCheckCenter {
    public static final int COMPONENT_TYPE_HARD_INTEGRITY = 1;
    public static final int COMPONENT_TYPE_SOFT_INTEGRITY = 2;
    public static final int FLAG_ABOOT_RP_VER = -1048576;
    public static final int FLAG_AFW_STATUS = -1048565;
    public static final int FLAG_BOOT_IMAGE = -1048562;
    public static final int FLAG_CACHE_IMAGE = -1048559;
    public static final int FLAG_CC_MODE = -1048568;
    public static final int FLAG_CURRENT_BIN_STATUS = -1048566;
    public static final int FLAG_DMV_STATUS = -15728640;
    public static final int FLAG_FRP_LOCK = -1048569;
    public static final int FLAG_KAP_STATUS = -1048563;
    public static final int FLAG_KERNEL_RP_VER = -1048575;
    public static final int FLAG_KIWI_LOCK = -1048570;
    public static final int FLAG_MDM_MODE = -1048567;
    public static final int FLAG_PKM_RO = -16777215;
    public static final int FLAG_PKM_TEXT = -16777216;
    public static final int FLAG_REACT_LOCK = -1048571;
    public static final int FLAG_RECOVERY_IMAGE = -1048561;
    public static final int FLAG_RESERVE_IMAGE1 = -1048558;
    public static final int FLAG_RESERVE_IMAGE2 = -1048557;
    public static final int FLAG_SEC_BOOT = -1048572;
    public static final int FLAG_SELINUX_STATUS = -16777214;
    public static final int FLAG_SYSSCOPE_STATUS = -14680064;
    public static final int FLAG_SYSTEM_IMAGE = -1048560;
    public static final int FLAG_SYSTEM_RP_VER = -1048574;
    public static final int FLAG_TEST_BIT = -1048573;
    public static final int FLAG_TIMA_VERSION = -14680062;
    public static final int FLAG_TRUSTBOOT_STATUS = -14680063;
    public static final int FLAG_WB_STATUS = -1048564;
    IIntegrityControlCheckCenter mService;

    public IntegrityControlCheckCenter(IIntegrityControlCheckCenter service) {
        this.mService = service;
    }

    public synchronized int getSecureData(int type) throws RemoteException {
        Log.d("ICCC", "Method getSecureData in IntegrityControlCheckCenter Class");
        if (this.mService == null) {
            return -1;
        }
        return this.mService.getSecureData(type);
    }

    public synchronized int setSecureData(int type, int value) throws RemoteException {
        Log.d("ICCC", "Method setSecureData in IntegrityControlCheckCenter Class");
        if (this.mService == null) {
            return -1;
        }
        return this.mService.setSecureData(type, value);
    }

    public synchronized int getTrustedBootData() throws RemoteException {
        Log.d("ICCC", "Method getTrustedBootData in IntegrityControlCheckCenter Class");
        if (this.mService == null) {
            return -1;
        }
        return this.mService.getTrustedBootData();
    }

    public synchronized String getDeviceStatus(int comp_type, String nonce) throws RemoteException {
        byte[] responseBytes;
        Log.d("ICCC", "Method getDeviceStatus in IntegrityControlCheckCenter Class");
        if (this.mService == null
                || nonce == null
                || (responseBytes =
                                this.mService.getDeviceStatus(
                                        comp_type, nonce.getBytes(Charset.defaultCharset())))
                        == null
                || responseBytes.length <= 1) {
            return null;
        }
        return new String(responseBytes, Charset.defaultCharset());
    }

    public synchronized String getDeviceInfo(String nonce) throws RemoteException {
        byte[] responseBytes;
        Log.d("ICCC", "Method getDeviceInfo in IntegrityControlCheckCenter Class");
        if (this.mService == null
                || nonce == null
                || (responseBytes =
                                this.mService.getDeviceInfo(
                                        nonce.getBytes(Charset.defaultCharset())))
                        == null
                || responseBytes.length <= 1) {
            return null;
        }
        return new String(responseBytes, Charset.defaultCharset());
    }

    public synchronized byte[] getBldpData() throws RemoteException {
        byte[] responseBytes;
        Log.d("ICCC", "Method getBldpData in IntegrityControlCheckCenter Class");
        if (this.mService != null && (responseBytes = this.mService.getBldpData()) != null) {
            if (responseBytes.length > 1) {
                return responseBytes;
            }
        }
        return null;
    }
}
