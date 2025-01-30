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
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.integrity.EnhancedAttestationPolicy;
import com.samsung.android.knox.kpm.IKnoxPushService;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KnoxPushService {
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
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (KnoxPushService.this) {
                KnoxPushService.this.mKnoxPushService = IKnoxPushService.Stub.asInterface(iBinder);
                Log.i(KnoxPushService.TAG, "On onServiceConnected");
            }
            KnoxPushService.this.handlePendingRequest();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
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
        synchronized (KnoxPushService.class) {
            if (context == null) {
                Log.e(TAG, "context is null");
                return null;
            }
            if (mPushPolicy == null) {
                mPushPolicy = new KnoxPushService(context);
            }
            return mPushPolicy;
        }
    }

    public static boolean isOSVersionSupported() {
        int i = SemSystemProperties.getInt("ro.product.first_api_level", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("isOSVersionSupported: ", i, TAG);
        if (i >= 28) {
            return true;
        }
        NestedScrollView$$ExternalSyntheticOutline0.m34m("os is not supported firstApiLevel: ", i, TAG);
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
                boolean bindService = this.mContext.bindService(intent, this.conn, 1);
                ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("bind service:", bindService, TAG);
                return bindService;
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
        HashMap hashMap;
        if (getTrackMapSize() < 1) {
            return;
        }
        synchronized (this) {
            hashMap = new HashMap(this.mTrackOpsHash);
            clearTrackMap();
            this.mProcessPendingRequest = true;
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            KnoxPushServiceCallback knoxPushServiceCallback = (KnoxPushServiceCallback) entry.getKey();
            RequestInfo requestInfo = (RequestInfo) entry.getValue();
            int i = requestInfo.mCmd;
            boolean z = requestInfo.mForce;
            AbstractC0731x5bb8a836.m72m("process pending request: cmd: ", i, ", force: ", z, TAG);
            if (i == 1) {
                registerDevice(z, knoxPushServiceCallback);
            } else if (i == 2) {
                unRegisterDevice(knoxPushServiceCallback);
            } else if (i != 3) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("wrong cmd: ", i, TAG);
            } else {
                isRegistered(knoxPushServiceCallback);
            }
        }
        synchronized (this) {
            this.mProcessPendingRequest = false;
        }
    }

    public final boolean hasPackage(String str) {
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

    public final boolean isGMSCoreEnabled() {
        boolean hasPackage = hasPackage("com.google.android.gms");
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("GMS Core Enabled : ", hasPackage, TAG);
        return hasPackage;
    }

    public final boolean isKnoxVersionSupported() {
        int i = KnoxInternalFeature.KNOX_CONFIG_VERSION;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("knox version: ", i, TAG);
        return i >= 27;
    }

    public final synchronized int isRegistered(KnoxPushServiceCallback knoxPushServiceCallback) {
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

    public final boolean isSupported() {
        Log.d(TAG, "isSupported: ");
        return isKnoxVersionSupported() && isEaSupported() && !sAKmKeytypeExist() && attestDeviceSupported() && !isChinaModel() && isGMSCoreEnabled();
    }

    public final synchronized int registerDevice(boolean z, KnoxPushServiceCallback knoxPushServiceCallback) {
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

    public final synchronized int unRegisterDevice(KnoxPushServiceCallback knoxPushServiceCallback) {
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
        KnoxPushService knoxPushService;
        synchronized (KnoxPushService.class) {
            knoxPushService = mPushPolicy;
        }
        return knoxPushService;
    }
}
