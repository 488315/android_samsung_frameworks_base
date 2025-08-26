package com.samsung.android.knox.kpm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.SemSystemProperties;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.integrity.EnhancedAttestationPolicy;
import com.samsung.android.knox.kpm.IKnoxPushService;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class KnoxPushService {
    public static final String KPM_BIND_ACTION = "com.samsung.android.knox.intent.action.BIND_KNOX_PUSH_SERVICE";
    public static final String KPM_PACKAGE_NAME = "com.samsung.android.knox.pushmanager";
    public static final String KPM_SERVICE_CLASS = "com.samsung.android.knox.pushmanager.controller.RegiControllerFromSdk";
    public static final int SUPPORTED_KNOX_VERSION = 27;
    public static final String TAG = "KnoxPushService";
    public static KnoxPushService mPushPolicy;
    public Context mContext;
    public HashMap<KnoxPushServiceCallback, RequestInfo> mTrackOpsHash = new HashMap<>();
    public ServiceConnection conn = new ServiceConnection() { // from class: com.samsung.android.knox.kpm.KnoxPushService.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (KnoxPushService.this) {
                KnoxPushService.this.mKnoxPushService = IKnoxPushService.Stub.asInterface(iBinder);
                Log.i(KnoxPushService.TAG, "On onServiceConnected");
            }
            KnoxPushService.this.handlePendingRequest();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (KnoxPushService.this) {
                KnoxPushService.this.mKnoxPushService = null;
                Log.i(KnoxPushService.TAG, "On onServiceDisconnected");
            }
        }
    };
    public IKnoxPushService mKnoxPushService = null;
    public boolean mProcessPendingRequest = false;

    private KnoxPushService(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static synchronized KnoxPushService getInstance(Context context) {
        if (context == null) {
            Log.e(TAG, "context is null");
            return null;
        }
        if (mPushPolicy == null) {
            mPushPolicy = new KnoxPushService(context);
        }
        return mPushPolicy;
    }

    public static boolean isOSVersionSupported() {
        int i = SemSystemProperties.getInt("ro.product.first_api_level", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "isOSVersionSupported: ", TAG);
        if (i >= 28) {
            return true;
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "os is not supported firstApiLevel: ", TAG);
        return false;
    }

    public static boolean sAKmKeytypeExist() {
        try {
            if (!SemSystemProperties.get("ro.security.keystore.keytype", "").contains("sakm")) {
                return false;
            }
            Log.i(TAG, "sakm exist");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "sAKmKeytypeExist: " + e.toString());
            return false;
        }
    }

    public final synchronized boolean addToTrackMap(KnoxPushServiceCallback knoxPushServiceCallback, RequestInfo requestInfo) {
        if (getTrackMapSize() > 0) {
            Log.i(TAG, "previous request is not finished");
            return false;
        }
        this.mTrackOpsHash.put(knoxPushServiceCallback, requestInfo);
        Log.d(TAG, "addToTrackMap:  " + getTrackMapSize());
        return true;
    }

    public final boolean attestDeviceSupported() {
        if (sAKKeytypeExist()) {
            return isOSVersionSupported();
        }
        return false;
    }

    public final boolean bindService() {
        synchronized (this) {
            try {
                try {
                    Log.d(TAG, "bindService: " + this.mKnoxPushService);
                    if (this.mKnoxPushService.asBinder().isBinderAlive()) {
                        return true;
                    }
                } catch (Exception e) {
                    Log.d(TAG, "bindService: " + e.toString());
                    this.mKnoxPushService = null;
                }
                Intent intent = new Intent();
                intent.setClassName(KPM_PACKAGE_NAME, KPM_SERVICE_CLASS);
                intent.setAction(KPM_BIND_ACTION);
                boolean zBindService = this.mContext.bindService(intent, this.conn, 1);
                AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("bind service:", TAG, zBindService);
                return zBindService;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void clearTrackMap() {
        this.mTrackOpsHash.clear();
    }

    public final synchronized int getTrackMapSize() {
        return this.mTrackOpsHash.size();
    }

    public final void handlePendingRequest() {
        HashMap map;
        if (getTrackMapSize() < 1) {
            return;
        }
        synchronized (this) {
            map = new HashMap(this.mTrackOpsHash);
            clearTrackMap();
            this.mProcessPendingRequest = true;
        }
        for (Map.Entry entry : map.entrySet()) {
            KnoxPushServiceCallback knoxPushServiceCallback = (KnoxPushServiceCallback) entry.getKey();
            RequestInfo requestInfo = (RequestInfo) entry.getValue();
            int cmd = requestInfo.getCmd();
            boolean zIsForce = requestInfo.isForce();
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("process pending request: cmd: ", cmd, ", force: ", zIsForce, TAG);
            if (cmd == 1) {
                registerDevice(zIsForce, knoxPushServiceCallback);
            } else if (cmd == 2) {
                unRegisterDevice(knoxPushServiceCallback);
            } else if (cmd != 3) {
                ClockEventController$$ExternalSyntheticOutline0.m(cmd, "wrong cmd: ", TAG);
            } else {
                isRegistered(knoxPushServiceCallback);
            }
        }
        synchronized (this) {
            this.mProcessPendingRequest = false;
        }
    }

    public final boolean hasPackage(String str) throws PackageManager.NameNotFoundException {
        try {
            Log.d(TAG, "appInfo: " + this.mContext.getPackageManager().getApplicationInfo(str, 128));
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "Package not found : " + str);
            return false;
        }
    }

    public final boolean isChinaModel() {
        String str = SemSystemProperties.get("ro.csc.countryiso_code", "");
        Log.d(TAG, "countryIsoCode: " + str);
        if (!"CN".equalsIgnoreCase(str)) {
            return false;
        }
        Log.e(TAG, "CN is not supported");
        return true;
    }

    public final boolean isEaSupported() {
        EnhancedAttestationPolicy enhancedAttestationPolicy = EnhancedAttestationPolicy.getInstance(this.mContext);
        if (enhancedAttestationPolicy != null && enhancedAttestationPolicy.isSupported()) {
            return true;
        }
        Log.i(TAG, "EA is not supported");
        return false;
    }

    public final boolean isGMSCoreEnabled() throws PackageManager.NameNotFoundException {
        boolean zHasPackage = hasPackage("com.google.android.gms");
        EmergencyButtonController$$ExternalSyntheticOutline0.m("GMS Core Enabled : ", TAG, zHasPackage);
        return zHasPackage;
    }

    public final boolean isKnoxVersionSupported() {
        int i = KnoxInternalFeature.KNOX_CONFIG_VERSION;
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "knox version: ", TAG);
        return i >= 27;
    }

    public synchronized int isRegistered(KnoxPushServiceCallback knoxPushServiceCallback) {
        Log.d(TAG, "isRegistered: ");
        try {
            if (knoxPushServiceCallback == null) {
                Log.e(TAG, "isRegistered: cb == null");
                return -9;
            }
            if (!isSupported()) {
                Log.e(TAG, "KPM is not supported");
                return -7;
            }
            if (!bindService()) {
                Log.e(TAG, "bind request fail");
                return -2;
            }
            if (!addToTrackMap(knoxPushServiceCallback, new RequestInfo(3))) {
                return -4;
            }
            IKnoxPushService iKnoxPushService = this.mKnoxPushService;
            if (iKnoxPushService != null) {
                iKnoxPushService.isRegistered(knoxPushServiceCallback.getKnoxPushServiceCb());
            }
            Log.d(TAG, "isRegistered requested");
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "isRegistered: " + e.toString());
            e.printStackTrace();
            removeFromTrackMap(knoxPushServiceCallback);
            return -1;
        }
    }

    public boolean isSupported() {
        Log.d(TAG, "isSupported: ");
        return isKnoxVersionSupported() && isEaSupported() && !sAKmKeytypeExist() && attestDeviceSupported() && !isChinaModel() && isGMSCoreEnabled();
    }

    public synchronized int registerDevice(boolean z, KnoxPushServiceCallback knoxPushServiceCallback) {
        Log.d(TAG, "registerDevice: " + z);
        try {
            if (knoxPushServiceCallback == null) {
                Log.e(TAG, "registerDevice: cb == null");
                return -9;
            }
            if (!isSupported()) {
                Log.e(TAG, "KPM is not supported");
                return -7;
            }
            if (!bindService()) {
                Log.e(TAG, "bind request fail");
                return -2;
            }
            if (!addToTrackMap(knoxPushServiceCallback, new RequestInfo(1, z))) {
                return -4;
            }
            IKnoxPushService iKnoxPushService = this.mKnoxPushService;
            if (iKnoxPushService != null) {
                iKnoxPushService.registerDevice(z, knoxPushServiceCallback.getKnoxPushServiceCb());
            }
            Log.d(TAG, "registerDevice requested");
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "registerDevice: " + e.toString());
            e.printStackTrace();
            removeFromTrackMap(knoxPushServiceCallback);
            return -1;
        }
    }

    public final synchronized void removeFromTrackMap(KnoxPushServiceCallback knoxPushServiceCallback) {
        this.mTrackOpsHash.remove(knoxPushServiceCallback);
        Log.d(TAG, "removeFromTrackMap: size: " + this.mTrackOpsHash.size() + ", pending: " + this.mProcessPendingRequest);
        if (this.mTrackOpsHash.isEmpty() && !this.mProcessPendingRequest) {
            Log.i(TAG, "Map is empty, call unBindService: ");
            this.mKnoxPushService = null;
            this.mContext.unbindService(this.conn);
        }
    }

    public final boolean sAKKeytypeExist() {
        try {
            if (SemSystemProperties.get("ro.security.keystore.keytype", "").contains("sak")) {
                return true;
            }
            Log.e(TAG, "sak keytype not supported");
            return false;
        } catch (Exception e) {
            Log.e(TAG, "sAKKeytypeExist: " + e.toString());
            return false;
        }
    }

    public synchronized int unRegisterDevice(KnoxPushServiceCallback knoxPushServiceCallback) {
        Log.d(TAG, "unRegisterDevice: ");
        try {
            if (knoxPushServiceCallback == null) {
                Log.e(TAG, "unRegisterDevice: cb == null");
                return -9;
            }
            if (!isSupported()) {
                Log.e(TAG, "KPM is not supported");
                return -7;
            }
            if (!bindService()) {
                Log.e(TAG, "bind request fail");
                return -2;
            }
            if (!addToTrackMap(knoxPushServiceCallback, new RequestInfo(2))) {
                return -4;
            }
            IKnoxPushService iKnoxPushService = this.mKnoxPushService;
            if (iKnoxPushService != null) {
                iKnoxPushService.unRegisterDevice(knoxPushServiceCallback.getKnoxPushServiceCb());
            }
            Log.d(TAG, "unRegisterDevice requested");
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "unRegisterDevice: " + e.toString());
            e.printStackTrace();
            removeFromTrackMap(knoxPushServiceCallback);
            return -1;
        }
    }

    public static synchronized KnoxPushService getInstance() {
        return mPushPolicy;
    }
}
