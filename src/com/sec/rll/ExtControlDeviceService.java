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
        ExtControlDeviceService extControlDeviceService;
        synchronized (ExtControlDeviceService.class) {
            if (sService == null) {
                sService = new ExtControlDeviceService();
            }
            extControlDeviceService = sService;
        }
        return extControlDeviceService;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0047, code lost:
    
        if (r5 == 1) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004c, code lost:
    
        r0 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
    
        if (r5 == 1) goto L21;
     */
    @Override // com.sec.rll.IExtControlDeviceService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setStatus(int r4, int r5) throws android.os.RemoteException {
        /*
            r3 = this;
            java.lang.String r0 = "setStatus called"
            java.lang.String r1 = "SRIB-ExtControlDeviceService"
            android.util.Log.d(r1, r0)
            boolean r3 = r3.isAccessPermitted()
            if (r3 != 0) goto Lf
            goto L6f
        Lf:
            r3 = 4097(0x1001, float:5.741E-42)
            if (r4 != r3) goto L57
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Set gps state called with state : "
            r3.<init>(r4)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r1, r3)
            android.os.Binder.clearCallingIdentity()
            android.content.Context r3 = com.sec.rll.ExtControlDeviceService.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            java.lang.String r4 = "location_mode"
            r0 = 0
            int r3 = android.provider.Settings.Secure.getInt(r3, r4, r0)
            r1 = 1
            if (r3 == 0) goto L4a
            if (r3 == r1) goto L47
            r0 = 2
            r2 = 3
            if (r3 == r0) goto L44
            if (r3 == r2) goto L41
        L3f:
            r0 = r2
            goto L4d
        L41:
            if (r5 != r1) goto L4d
            goto L3f
        L44:
            if (r5 != r1) goto L4d
            goto L3f
        L47:
            if (r5 != r1) goto L4d
            goto L4c
        L4a:
            if (r5 != r1) goto L4d
        L4c:
            r0 = r1
        L4d:
            android.content.Context r3 = com.sec.rll.ExtControlDeviceService.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            android.provider.Settings.Secure.putInt(r3, r4, r0)
            return
        L57:
            r3 = 8193(0x2001, float:1.1481E-41)
            if (r4 != r3) goto L6f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Set NFC/Felica state called with state : "
            r3.<init>(r4)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r1, r3)
            setNfcState(r5)
        L6f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.rll.ExtControlDeviceService.setStatus(int, int):void");
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

    public static boolean isENGDevice() {
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

    public static String get(String str) {
        try {
            Class<?> loadClass = mContext.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) loadClass.getMethod("get", String.class).invoke(loadClass, str);
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
