package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.iccc.IIntegrityControlCheckCenter;

/* loaded from: classes.dex */
public class IcccManagerService extends IIntegrityControlCheckCenter.Stub {
    public static String TAG = "IcccManagerService";
    public static Context mContext;
    public BroadcastReceiver icccBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.IcccManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(IcccManagerService.TAG, "receive intent action is " + action);
            if ("com.samsung.intent.action.BCS_REQUEST".equals(action)) {
                try {
                    if (intent.getExtras() == null) {
                        Log.d(IcccManagerService.TAG, "data is null ");
                    } else if (intent.hasExtra(KnoxVpnFirewallHelper.CMD)) {
                        String stringExtra = intent.getStringExtra(KnoxVpnFirewallHelper.CMD);
                        Log.d(IcccManagerService.TAG, "command: " + stringExtra);
                        if ("AT+ICCCINFO=1,0,0".equalsIgnoreCase(stringExtra)) {
                            Intent intent2 = new Intent("com.samsung.intent.action.BCS_RESPONSE");
                            String str = IcccManagerService.this.get_iccc_response_data("AT+ICCCINFO=1,0,0\r\n+ICCCINFO:1,1,");
                            Log.d(IcccManagerService.TAG, "iccc_response - " + str);
                            intent2.putExtra("response", str);
                            IcccManagerService.mContext.sendBroadcast(intent2, "com.sec.android.phone.permission.AT_COMMAND");
                        }
                    }
                } catch (RuntimeException e) {
                    Log.d(IcccManagerService.TAG, "onReceive:ACTION_BCS_REQUEST-exception");
                    e.printStackTrace();
                }
            }
        }
    };

    public static native int get_Trusted_Boot_Data();

    public static native byte[] iccc_attestation_platform(byte[] bArr);

    public static native byte[] iccc_device_info(byte[] bArr);

    public static native byte[] iccc_device_status(int i, byte[] bArr);

    public static native int iccc_load();

    public static native int iccc_readData_platform(int i);

    public static native int iccc_saveData_platform(int i, int i2);

    public static native int iccc_unload();

    public IcccManagerService(Context context) {
        Log.d(TAG, "Start IcccManagerService");
        mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.BCS_REQUEST");
        mContext.registerReceiver(this.icccBroadcastReceiver, intentFilter);
    }

    public final boolean enforcePermission() {
        return Binder.getCallingUid() == 1000;
    }

    public static int checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(mContext, Binder.getCallingPid(), Binder.getCallingUid(), "IcccManagerService", str) != 0) {
            SecurityException securityException = new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [IcccManagerService] service");
            Log.e(TAG, "IcccManagerService() - Invalid Caller");
            throw securityException;
        }
        Log.d(TAG, "IcccManagerService() - Valid Caller");
        return 0;
    }

    public final synchronized String get_iccc_response_data(String str) {
        return ((str + Integer.toString(iccc_readData_platform(-1048564)) + ",") + Integer.toString(iccc_readData_platform(-1048566)) + ",") + Integer.toString(0);
    }

    public synchronized int getSecureData(int i) {
        return iccc_readData_platform(i);
    }

    public synchronized int setSecureData(int i, int i2) {
        int i3;
        if (enforcePermission()) {
            i3 = iccc_saveData_platform(i, i2);
        } else {
            Log.e(TAG, "Not System UID. Only system UID caller can change status");
            i3 = -1;
        }
        return i3;
    }

    public synchronized int getTrustedBootData() {
        return get_Trusted_Boot_Data();
    }

    public synchronized byte[] setAttestationData(byte[] bArr) {
        checkCallerPermissionFor("setAttestationData");
        return iccc_attestation_platform(bArr);
    }

    public synchronized byte[] getDeviceStatus(int i, byte[] bArr) {
        checkCallerPermissionFor("getDeviceStatus");
        if (iccc_load() != 0) {
            Log.e(TAG, "iccc_load failure");
            return null;
        }
        byte[] iccc_device_status = iccc_device_status(i, bArr);
        if (iccc_unload() != 0) {
            Log.e(TAG, "iccc_unload failure");
        }
        return iccc_device_status;
    }

    public synchronized byte[] getDeviceInfo(byte[] bArr) {
        checkCallerPermissionFor("getDeviceInfo");
        return iccc_device_info(bArr);
    }
}
