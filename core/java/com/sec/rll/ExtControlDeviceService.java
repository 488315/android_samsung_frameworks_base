package com.sec.rll;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class ExtControlDeviceService extends IExtControlDeviceService.Stub {
    private static final String ACTION_NFC_POLICY_CHANGED =
            "com.sec.android.intent.action.NFC_POLICY_CHANGED";
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
        ExtControlDeviceService extControlDeviceService;
        synchronized (ExtControlDeviceService.class) {
            if (sService == null) {
                sService = new ExtControlDeviceService();
            }
            extControlDeviceService = sService;
        }
        return extControlDeviceService;
    }

    @Override // com.sec.rll.IExtControlDeviceService
    public void setStatus(int deviceType, int status) throws RemoteException {
        Log.d(TAG, "setStatus called");
        if (!isAccessPermitted()) {
            return;
        }
        if (deviceType == 4097) {
            Log.e(TAG, "Set gps state called with state : " + status);
            Binder.clearCallingIdentity();
            int currentMode =
                    Settings.Secure.getInt(
                            mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, 0);
            int mode = 3;
            switch (currentMode) {
                case 0:
                    if (status == 1) {
                        mode = 1;
                        break;
                    } else {
                        mode = 0;
                        break;
                    }
                case 1:
                    if (status == 1) {
                        mode = 1;
                        break;
                    } else {
                        mode = 0;
                        break;
                    }
                case 2:
                    if (status == 1) {
                        mode = 3;
                        break;
                    } else {
                        mode = 2;
                        break;
                    }
                case 3:
                    if (status == 1) {
                        mode = 3;
                        break;
                    } else {
                        mode = 2;
                        break;
                    }
            }
            Settings.Secure.putInt(
                    mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, mode);
            return;
        }
        if (deviceType == 8193) {
            Log.e(TAG, "Set NFC/Felica state called with state : " + status);
            setNfcState(status);
        }
    }

    private static void setNfcState(int state) {
        boolean isGpFelicaSupported =
                mContext.getPackageManager().hasSystemFeature("com.samsung.android.nfc.gpfelica");
        if (isGpFelicaSupported && state == 0) {
            SystemProperties.set(PROPERTY_NFC_LOCKOUT, Integer.toString(state));
            Intent nfcIntent = new Intent(ACTION_NFC_POLICY_CHANGED);
            nfcIntent.putExtra("NfcState", state);
            nfcIntent.addFlags(268435456);
            mContext.sendBroadcast(nfcIntent);
        }
    }

    private static int getNfcState() {
        return SystemProperties.getInt(PROPERTY_NFC_LOCKOUT, 1);
    }

    private static boolean setLocationMode(int mode) {
        Binder.clearCallingIdentity();
        return Settings.Secure.putInt(
                mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, mode);
    }

    private boolean isAccessPermitted() {
        int callerUid = Binder.getCallingUid();
        if (callerUid == mUid) {
            Log.d(TAG, "UID matches - access granted to uid:" + callerUid);
            return true;
        }
        String[] packages = mPackageManager.getPackagesForUid(callerUid);
        for (String pkg : packages) {
            if (DEBUG) {
                Log.d(TAG, "Looking up pkg info for:" + pkg);
            }
            if (DEBUG && pkg.equals("com.rll.test")) {
                Log.d(TAG, "Lets allow our test app access RLL");
                return true;
            }
            if (pkg.equals("com.kddi.extcontroldevice") && isSystemApp(pkg)) {
                Log.d(TAG, "Allowing RLL access");
                return true;
            }
        }
        Log.w(TAG, "Access denied to UID:" + callerUid);
        return false;
    }

    @Override // com.sec.rll.IExtControlDeviceService
    public int getStatus(int deviceType) throws RemoteException {
        Log.d(TAG, "getStatus called");
        if (!isAccessPermitted()) {
            return -1;
        }
        if (deviceType == 4097) {
            Binder.clearCallingIdentity();
            Log.e(
                    TAG,
                    "get gps state called return value  : "
                            + Settings.Secure.getInt(
                                    mContext.getContentResolver(),
                                    Settings.Secure.LOCATION_MODE,
                                    0));
            int currentMode =
                    Settings.Secure.getInt(
                            mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, 0);
            return (currentMode == 3 || currentMode == 1) ? 1 : 0;
        }
        if (deviceType != 8193) {
            return 0;
        }
        Log.e(TAG, "get nfc/felica state called return value : " + getNfcState());
        return getNfcState();
    }

    public static boolean isENGDevice() {
        boolean isEngDevice = false;
        if (Build.DISPLAY.contains("-eng")) {
            Log.d(TAG, "isENGDevice: eng build");
            isEngDevice = true;
        }
        String buildType = get("ro.build.type");
        if (buildType == null || !buildType.equalsIgnoreCase("eng")) {
            buildType = "user";
        }
        Log.d(TAG, "buildType: " + buildType);
        if ("eng".equals(buildType)) {
            return true;
        }
        return isEngDevice;
    }

    public static String get(String key) {
        try {
            ClassLoader cl = mContext.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            Class[] paramTypes = {String.class};
            Method get = SystemProperties.getMethod("get", paramTypes);
            Object[] params = {key};
            String ret = (String) get.invoke(SystemProperties, params);
            return ret;
        } catch (IllegalArgumentException iAE) {
            Log.e(TAG, "get: ", iAE);
            return "";
        } catch (Exception e) {
            Log.e(TAG, "get: ", e);
            return "";
        }
    }

    public static boolean isSystemApp(String packageName) {
        try {
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
            return (appInfo.flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
