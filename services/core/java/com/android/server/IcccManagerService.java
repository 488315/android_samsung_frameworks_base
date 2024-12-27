package com.android.server;

import android.content.Context;
import android.os.Binder;
import android.util.Log;

import com.samsung.android.iccc.IIntegrityControlCheckCenter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class IcccManagerService extends IIntegrityControlCheckCenter.Stub {
    public static Context mContext;
    public AnonymousClass1 icccBroadcastReceiver;

    /* renamed from: -$$Nest$mget_iccc_response_data, reason: not valid java name */
    public static String m66$$Nest$mget_iccc_response_data(IcccManagerService icccManagerService) {
        String str;
        synchronized (icccManagerService) {
            str =
                    (("AT+ICCCINFO=1,0,0\r\n+ICCCINFO:1,1,"
                                            + Integer.toString(iccc_readData_platform(-1048564))
                                            + ",")
                                    + Integer.toString(iccc_readData_platform(-1048566))
                                    + ",")
                            + Integer.toString(0);
        }
        return str;
    }

    public static void checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        mContext,
                        "IcccManagerService",
                        str)
                == 0) {
            Log.d("IcccManagerService", "IcccManagerService() - Valid Caller");
            return;
        }
        SecurityException securityException =
                new SecurityException(
                        "Security Exception Occurred while pid["
                                + Binder.getCallingPid()
                                + "] with uid["
                                + Binder.getCallingUid()
                                + "] trying to access methodName ["
                                + str
                                + "] in [IcccManagerService] service");
        Log.e("IcccManagerService", "IcccManagerService() - Invalid Caller");
        throw securityException;
    }

    public static native int get_Trusted_Boot_Data();

    public static native byte[] iccc_attestation_platform(byte[] bArr);

    public static native byte[] iccc_device_info(byte[] bArr);

    public static native byte[] iccc_device_status(int i, byte[] bArr);

    public static native byte[] iccc_get_bldp_data();

    public static native int iccc_load();

    public static native int iccc_readData_platform(int i);

    public static native int iccc_saveData_platform(int i, int i2);

    public static native int iccc_unload();

    public final synchronized byte[] getBldpData() {
        checkCallerPermissionFor("getBldpData");
        return iccc_get_bldp_data();
    }

    public final synchronized byte[] getDeviceInfo(byte[] bArr) {
        checkCallerPermissionFor("getDeviceInfo");
        return iccc_device_info(bArr);
    }

    public final synchronized byte[] getDeviceStatus(int i, byte[] bArr) {
        checkCallerPermissionFor("getDeviceStatus");
        if (iccc_load() != 0) {
            Log.e("IcccManagerService", "iccc_load failure");
            return null;
        }
        byte[] iccc_device_status = iccc_device_status(i, bArr);
        if (iccc_unload() != 0) {
            Log.e("IcccManagerService", "iccc_unload failure");
        }
        return iccc_device_status;
    }

    public final synchronized int getSecureData(int i) {
        return iccc_readData_platform(i);
    }

    public final synchronized int getTrustedBootData() {
        return get_Trusted_Boot_Data();
    }

    public final synchronized byte[] setAttestationData(byte[] bArr) {
        checkCallerPermissionFor("setAttestationData");
        return iccc_attestation_platform(bArr);
    }

    public final synchronized int setSecureData(int i, int i2) {
        int i3;
        try {
            if (Binder.getCallingUid() == 1000) {
                i3 = iccc_saveData_platform(i, i2);
            } else {
                Log.e(
                        "IcccManagerService",
                        "Not System UID. Only system UID caller can change status");
                i3 = -1;
            }
        } catch (Throwable th) {
            throw th;
        }
        return i3;
    }
}
