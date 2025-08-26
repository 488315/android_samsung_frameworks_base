package com.sec.rll;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.sec.rll.IExtControlDeviceService;

/* loaded from: classes6.dex */
public class ExtControlDeviceService extends IExtControlDeviceService.Stub {
    private static final String ACTION_NFC_POLICY_CHANGED = "com.sec.android.intent.action.NFC_POLICY_CHANGED";
    private static final boolean DEBUG = isENGDevice();
    private static final int DEVICE_GPS = 4097;
    private static final int DEVICE_NFC = 8193;
    private static final String PROPERTY_NFC_LOCKOUT = "persist.nfc.remotelock";
    private static final int STATUS_DISABLED = 0;
    private static final int STATUS_ENABLED = 1;
    private static final String TAG = "SRIB-ExtControlDeviceService";
    private static Context mContext;
    private static PackageManager mPackageManager;
    private static int mUid;
    private static ExtControlDeviceService sService;

    public static void init(Context context) {
        mContext = context;
        mUid = Process.myUid();
        mPackageManager = mContext.getPackageManager();
    }

    public static synchronized ExtControlDeviceService getInstance() {
        if (sService == null) {
            sService = new ExtControlDeviceService();
        }
        return sService;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004c  */
    @Override // com.sec.rll.IExtControlDeviceService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setStatus(int i, int i2) throws RemoteException {
        Log.d(TAG, "setStatus called");
        if (isAccessPermitted()) {
            if (i != 4097) {
                if (i == 8193) {
                    Log.e(TAG, "Set NFC/Felica state called with state : " + i2);
                    setNfcState(i2);
                    return;
                }
                return;
            }
            Log.e(TAG, "Set gps state called with state : " + i2);
            Binder.clearCallingIdentity();
            int i3 = 0;
            int i4 = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, 0);
            if (i4 != 0) {
                if (i4 != 1) {
                    i3 = 2;
                    if (i4 == 2 ? i2 == 1 : !(i4 == 3 && i2 != 1)) {
                        i3 = 3;
                    }
                } else if (i2 == 1) {
                    i3 = 1;
                }
            } else if (i2 == 1) {
            }
            Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, i3);
        }
    }

    private static void setNfcState(int i) {
        if (mContext.getPackageManager().hasSystemFeature("com.samsung.android.nfc.gpfelica") && i == 0) {
            SystemProperties.set(PROPERTY_NFC_LOCKOUT, Integer.toString(i));
            Intent intent = new Intent(ACTION_NFC_POLICY_CHANGED);
            intent.putExtra("NfcState", i);
            intent.addFlags(268435456);
            mContext.sendBroadcast(intent);
        }
    }

    private static int getNfcState() {
        return SystemProperties.getInt(PROPERTY_NFC_LOCKOUT, 1);
    }

    private static boolean setLocationMode(int i) {
        Binder.clearCallingIdentity();
        return Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, i);
    }

    private boolean isAccessPermitted() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == mUid) {
            Log.d(TAG, "UID matches - access granted to uid:" + callingUid);
            return true;
        }
        for (String str : mPackageManager.getPackagesForUid(callingUid)) {
            boolean z = DEBUG;
            if (z) {
                Log.d(TAG, "Looking up pkg info for:" + str);
            }
            if (z && str.equals("com.rll.test")) {
                Log.d(TAG, "Lets allow our test app access RLL");
                return true;
            }
            if (str.equals("com.kddi.extcontroldevice") && isSystemApp(str)) {
                Log.d(TAG, "Allowing RLL access");
                return true;
            }
        }
        Log.w(TAG, "Access denied to UID:" + callingUid);
        return false;
    }

    @Override // com.sec.rll.IExtControlDeviceService
    public int getStatus(int i) throws RemoteException {
        Log.d(TAG, "getStatus called");
        if (!isAccessPermitted()) {
            return -1;
        }
        if (i == 4097) {
            Binder.clearCallingIdentity();
            Log.e(TAG, "get gps state called return value  : " + Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, 0));
            int i2 = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, 0);
            return (i2 == 3 || i2 == 1) ? 1 : 0;
        }
        if (i != 8193) {
            return 0;
        }
        Log.e(TAG, "get nfc/felica state called return value : " + getNfcState());
        return getNfcState();
    }

    public static boolean isENGDevice() throws ClassNotFoundException {
        boolean z;
        if (Build.DISPLAY.contains("-eng")) {
            Log.d(TAG, "isENGDevice: eng build");
            z = true;
        } else {
            z = false;
        }
        String str = get("ro.build.type");
        if (str == null || !str.equalsIgnoreCase("eng")) {
            str = "user";
        }
        Log.d(TAG, "buildType: " + str);
        if ("eng".equals(str)) {
            return true;
        }
        return z;
    }

    public static String get(String str) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = mContext.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) clsLoadClass.getMethod("get", String.class).invoke(clsLoadClass, str);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "get: ", e);
            return "";
        } catch (Exception e2) {
            Log.e(TAG, "get: ", e2);
            return "";
        }
    }

    public static boolean isSystemApp(String str) {
        return (mContext.getPackageManager().getApplicationInfo(str, 0).flags & 1) != 0;
    }
}
