package com.android.server.enterprise.hdm;

import android.os.IBinder;
import android.os.IInterface;
import android.os.ServiceManager;
import android.util.Log;

import vendor.samsung.hardware.khdm.ISehKhdm;
import vendor.samsung.hardware.khdm.SehDeviceInfo;

public class HdmVendorController {
    public final boolean hidlSupport;

    public HdmVendorController() {
        boolean z;
        int parseInt = Integer.parseInt("34");
        try {
            z = ServiceManager.isDeclared("vendor.samsung.hardware.khdm.ISehKhdm/default");
            Log.i("Hdm - VendorInterface", "isAidlServiceDeclared: " + z);
        } catch (Exception e) {
            Log.i("Hdm - VendorInterface", "isAidlServiceDeclared failed: " + e);
            z = false;
        }
        boolean z2 = !z;
        this.hidlSupport = z2;
        Log.i("Hdm - VendorInterface", "apiLevel: " + parseInt + ", hidlSupport: " + z2);
    }

    public static ISehKhdm getService() {
        IBinder waitForService =
                ServiceManager.waitForService("vendor.samsung.hardware.khdm.ISehKhdm/default");
        int i = ISehKhdm.Stub.$r8$clinit;
        if (waitForService == null) {
            return null;
        }
        IInterface queryLocalInterface = waitForService.queryLocalInterface(ISehKhdm.DESCRIPTOR);
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISehKhdm)) {
            return (ISehKhdm) queryLocalInterface;
        }
        ISehKhdm.Stub.Proxy proxy = new ISehKhdm.Stub.Proxy();
        proxy.mRemote = waitForService;
        return proxy;
    }

    public static native byte[] hdm_apply_policy(
            byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, boolean z);

    public static native int hdm_delete_keys();

    public static native byte[] hdm_get_id(byte[] bArr, byte[] bArr2, boolean z);

    public static native int hdm_get_key();

    public static native byte[] hdm_get_policy(boolean z, byte[] bArr, byte[] bArr2);

    public static native int hdm_kg_cmd(int i);

    public static native int hdm_load();

    public static native int hdm_set_key(byte[] bArr);

    public static native int hdm_unload();

    public static byte[] parseDeviceResponse(int i, String str, byte[] bArr) {
        Log.i("Hdm - VendorInterface", str + " using ISehKhdm: len: " + i);
        if (i < 10) {
            return null;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    public final byte[] hdmApplyPolicy(
            byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, boolean z) {
        try {
            Log.i("Hdm - VendorInterface", "hdmApplyPolicy policy: ");
            if (this.hidlSupport) {
                return hdm_apply_policy(bArr, bArr2, bArr3, bArr4, bArr5, z);
            }
            byte[] bArr6 = new byte[15000];
            SehDeviceInfo sehDeviceInfo = new SehDeviceInfo();
            byte[] bArr7 = new byte[128];
            if (bArr2 != null) {
                System.arraycopy(bArr2, 0, bArr7, 0, bArr2.length);
            }
            sehDeviceInfo.serialNumber = bArr7;
            byte[] bArr8 = new byte[128];
            if (bArr3 != null) {
                System.arraycopy(bArr3, 0, bArr8, 0, bArr3.length);
            }
            sehDeviceInfo.imei0 = bArr8;
            byte[] bArr9 = new byte[128];
            if (bArr4 != null) {
                System.arraycopy(bArr4, 0, bArr9, 0, bArr4.length);
            }
            sehDeviceInfo.hashedImei = bArr9;
            byte[] bArr10 = new byte[128];
            if (bArr5 != null) {
                System.arraycopy(bArr5, 0, bArr10, 0, bArr5.length);
            }
            sehDeviceInfo.macAddr = bArr10;
            sehDeviceInfo.isWrappedKey = z;
            return parseDeviceResponse(
                    ((ISehKhdm.Stub.Proxy) getService()).applyPolicy(bArr, sehDeviceInfo, bArr6),
                    "hdmApplyPolicy",
                    bArr6);
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmApplyPolicy failed: " + e, e);
            return null;
        }
    }

    public final byte[] hdmGetId(boolean z, byte[] bArr, byte[] bArr2) {
        try {
            Log.i("Hdm - VendorInterface", "hdmGetId");
            if (this.hidlSupport) {
                return hdm_get_id(bArr, bArr2, z);
            }
            byte[] bArr3 = new byte[15000];
            SehDeviceInfo sehDeviceInfo = new SehDeviceInfo();
            sehDeviceInfo.serialNumber = new byte[128];
            byte[] bArr4 = new byte[128];
            if (bArr != null) {
                System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
            }
            sehDeviceInfo.imei0 = bArr4;
            sehDeviceInfo.hashedImei = new byte[128];
            byte[] bArr5 = new byte[128];
            if (bArr2 != null) {
                System.arraycopy(bArr2, 0, bArr5, 0, bArr2.length);
            }
            sehDeviceInfo.macAddr = bArr5;
            sehDeviceInfo.isWrappedKey = z;
            return parseDeviceResponse(
                    ((ISehKhdm.Stub.Proxy) getService()).getDeviceId(sehDeviceInfo, bArr3),
                    "hdmGetId",
                    bArr3);
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmGetId failed: " + e, e);
            return null;
        }
    }

    public final int hdmGetKey() {
        try {
            if (this.hidlSupport) {
                return hdm_get_key();
            }
            int checkKey = ((ISehKhdm.Stub.Proxy) getService()).checkKey();
            Log.i("Hdm - VendorInterface", "hdmGetKey using ISehKhdm: " + checkKey);
            return checkKey;
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "checkKey failed: " + e, e);
            return -1;
        }
    }

    public final byte[] hdmGetPolicy(boolean z, byte[] bArr, byte[] bArr2) {
        try {
            if (this.hidlSupport) {
                return hdm_get_policy(z, bArr, bArr2);
            }
            byte[] bArr3 = new byte[15000];
            return parseDeviceResponse(
                    ((ISehKhdm.Stub.Proxy) getService()).getPolicy(z, bArr, bArr2, bArr3),
                    "hdmGetPolicy",
                    bArr3);
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmGetPolicy failed: " + e, e);
            return null;
        }
    }

    public final int hdmKgCmd(int i) {
        try {
            if (this.hidlSupport) {
                return hdm_kg_cmd(i);
            }
            ISehKhdm.Stub.Proxy proxy = (ISehKhdm.Stub.Proxy) getService();
            int executeHdmCmd = proxy.executeHdmCmd(i);
            Log.i("Hdm - VendorInterface", "hdmKgCmd using ISehKhdm: " + executeHdmCmd);
            if (executeHdmCmd != 0) {
                proxy.terminate();
            }
            return executeHdmCmd;
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmKgCmd failed: " + e, e);
            return -1;
        }
    }

    public final int hdmLoad() {
        try {
            if (this.hidlSupport) {
                return hdm_load();
            }
            ISehKhdm.Stub.Proxy proxy = (ISehKhdm.Stub.Proxy) getService();
            int initiate = proxy.initiate();
            Log.i("Hdm - VendorInterface", "hdmLoad using ISehKhdm: " + initiate);
            if (initiate != 0) {
                proxy.terminate();
            }
            return initiate;
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmLoad failed: " + e, e);
            return -1;
        }
    }

    public final int hdmSetKey(byte[] bArr) {
        try {
            if (this.hidlSupport) {
                return hdm_set_key(bArr);
            }
            int key = ((ISehKhdm.Stub.Proxy) getService()).setKey(bArr);
            Log.i("Hdm - VendorInterface", "hdmSetKey using ISehKhdm: " + key);
            return key;
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmSetKey failed: " + e, e);
            return -1;
        }
    }

    public final int hdmUnload() {
        try {
            if (this.hidlSupport) {
                return hdm_unload();
            }
            int terminate = ((ISehKhdm.Stub.Proxy) getService()).terminate();
            Log.i("Hdm - VendorInterface", "hdmUnload using ISehKhdm: " + terminate);
            return terminate;
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmUnload failed: " + e, e);
            return -1;
        }
    }
}
