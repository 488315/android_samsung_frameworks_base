package com.samsung.android.knox.integrity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.integrity.IEnhancedAttestation;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.sec.ims.configuration.DATA;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EnhancedAttestationPolicy {
    public static final String EA_BIND_ACTION = "com.samsung.android.knox.intent.action.BIND_KNOX_EA_SERVICE";
    public static final String EA_PACKAGE_CLASS = "com.samsung.android.knox.attestation.controller.EnhancedAttestation";
    public static final String EA_PACKAGE_NAME = "com.samsung.android.knox.attestation";
    public static final String TAG = "EAPolicy";
    public static EnhancedAttestationPolicy mEaPolicy;
    public Context mContext;
    public final HashMap<String, RequestInfo> mTrackOpsHash = new HashMap<>();
    public ServiceConnection conn = new ServiceConnection() { // from class: com.samsung.android.knox.integrity.EnhancedAttestationPolicy.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (EnhancedAttestationPolicy.class) {
                EnhancedAttestationPolicy.this.mEnhancedAttestation = IEnhancedAttestation.Stub.asInterface(iBinder);
                Log.i(EnhancedAttestationPolicy.TAG, "On onServiceConnected");
            }
            EnhancedAttestationPolicy.this.handlePendingRequest();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (EnhancedAttestationPolicy.class) {
                EnhancedAttestationPolicy.this.mEnhancedAttestation = null;
                Log.i(EnhancedAttestationPolicy.TAG, "On onServiceDisconnected");
            }
        }
    };
    public IEnhancedAttestation mEnhancedAttestation = null;
    public boolean mProcessPendingRequest = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class RequestInfo {
        public String mAuk;
        public EnhancedAttestationPolicyCallback mCb;
        public String mNonce;
        public boolean mOnPrem;

        public RequestInfo(String str, String str2, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback, boolean z) {
            this.mAuk = str;
            this.mNonce = str2;
            this.mCb = enhancedAttestationPolicyCallback;
            this.mOnPrem = z;
        }
    }

    private EnhancedAttestationPolicy(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static synchronized EnhancedAttestationPolicy getInstance(Context context) {
        synchronized (EnhancedAttestationPolicy.class) {
            if (context == null) {
                Log.e(TAG, "context is null");
                return null;
            }
            if (mEaPolicy == null) {
                mEaPolicy = new EnhancedAttestationPolicy(context);
            }
            return mEaPolicy;
        }
    }

    public static int getKnoxVersion() {
        return Integer.parseInt(DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND) - 5;
    }

    public final synchronized boolean addToTrackMap(String str, RequestInfo requestInfo) {
        if (this.mTrackOpsHash.get(str) != null) {
            Log.i(TAG, "same nonce onProcessing");
            return false;
        }
        this.mTrackOpsHash.put(str, requestInfo);
        Log.d(TAG, "addToTrackMap:  " + getTrackMapSize());
        return true;
    }

    public final boolean bindService() {
        synchronized (EnhancedAttestationPolicy.class) {
            Log.d(TAG, "bindService: " + this.mEnhancedAttestation);
            try {
                IEnhancedAttestation iEnhancedAttestation = this.mEnhancedAttestation;
                if (iEnhancedAttestation != null) {
                    if (iEnhancedAttestation.asBinder().isBinderAlive()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                Log.i(TAG, "bindService: " + e.toString());
            }
            Intent intent = new Intent();
            intent.setClassName(EA_PACKAGE_NAME, EA_PACKAGE_CLASS);
            intent.setAction(EA_BIND_ACTION);
            boolean bindServiceAsUser = this.mContext.bindServiceAsUser(intent, this.conn, 1, Process.myUserHandle());
            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("bind service:", bindServiceAsUser, TAG);
            return bindServiceAsUser;
        }
    }

    public final synchronized void clearTrackMap() {
        this.mTrackOpsHash.clear();
    }

    public final EnhancedAttestationResult getErrorResult(String str, int i) {
        EnhancedAttestationResult enhancedAttestationResult = new EnhancedAttestationResult();
        enhancedAttestationResult.errorCode = i;
        Bundle bundle = new Bundle();
        bundle.putString(EnhancedAttestationResult.DATA_FIELD_UNIQUE_ID, str);
        enhancedAttestationResult.data = bundle;
        return enhancedAttestationResult;
    }

    public final synchronized int getTrackMapSize() {
        return this.mTrackOpsHash.size();
    }

    public final void handlePendingRequest() {
        HashMap hashMap;
        if (getTrackMapSize() < 1) {
            return;
        }
        synchronized (EnhancedAttestationPolicy.class) {
            hashMap = new HashMap(this.mTrackOpsHash);
            clearTrackMap();
            this.mProcessPendingRequest = true;
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            RequestInfo requestInfo = (RequestInfo) entry.getValue();
            Log.d(TAG, "process pending request: nonce len: " + str.length());
            startAttestation(requestInfo.mAuk, requestInfo.mNonce, requestInfo.mCb, requestInfo.mOnPrem);
        }
        synchronized (EnhancedAttestationPolicy.class) {
            this.mProcessPendingRequest = false;
        }
    }

    public final boolean isDongleDevice() {
        return false;
    }

    public final boolean isEaSupportedFromSepLite() {
        return false;
    }

    public final boolean isJdmDevice() {
        return false;
    }

    public final boolean isKnoxVersionSupported() {
        return getKnoxVersion() >= 24;
    }

    public final boolean isMposSupported() {
        return false;
    }

    public final boolean isSepLiteDevice() {
        return false;
    }

    public final boolean isSupported() {
        return isKnoxVersionSupported();
    }

    public final void logApiUsage(String str) {
        try {
            EnterpriseLicenseManager.log(new ContextInfo(Binder.getCallingUid()), str);
        } catch (Exception e) {
            Log.e(TAG, "logApiUsage exception : " + e.toString());
        }
    }

    public final synchronized void removeFromTrackMap(String str) {
        this.mTrackOpsHash.remove(str);
        Log.d(TAG, "removeFromTrackMap: size: " + this.mTrackOpsHash.size() + ", pending: " + this.mProcessPendingRequest);
        if (this.mTrackOpsHash.isEmpty() && !this.mProcessPendingRequest) {
            Log.i(TAG, "Map is empty, call unBindService: ");
            this.mEnhancedAttestation = null;
            this.mContext.unbindService(this.conn);
        }
    }

    public final void startAttestation(String str, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback) {
        Log.d(TAG, "startAttestation on-prem");
        logApiUsage("EnhancedAttestationPolicy.START_ATTESTATION_OnPREM");
        startAttestation(null, str, enhancedAttestationPolicyCallback, true);
    }

    public final void startAttestation(String str, String str2, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback) {
        Log.d(TAG, "startAttestation");
        logApiUsage("EnhancedAttestationPolicy.START_ATTESTATION");
        startAttestation(str, str2, enhancedAttestationPolicyCallback, false);
    }

    public static synchronized EnhancedAttestationPolicy getInstance() {
        EnhancedAttestationPolicy enhancedAttestationPolicy;
        synchronized (EnhancedAttestationPolicy.class) {
            enhancedAttestationPolicy = mEaPolicy;
        }
        return enhancedAttestationPolicy;
    }

    public final void startAttestation(String str, String str2, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback, boolean z) {
        if (enhancedAttestationPolicyCallback == null) {
            Log.e(TAG, "startAttestation: cb == null");
            return;
        }
        if (!isSupported()) {
            Log.e(TAG, "EA is not supported");
            enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -4));
            return;
        }
        if ((str == null || str.length() < 1) && !z) {
            Log.e(TAG, "auk is null");
            enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -6));
            return;
        }
        if (str2 != null && str2.getBytes().length >= 16 && str2.getBytes().length <= 128) {
            try {
                if (!bindService()) {
                    Log.e(TAG, "bind request fail");
                    enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -7));
                    return;
                }
                RequestInfo requestInfo = new RequestInfo(str, str2, enhancedAttestationPolicyCallback, z);
                if (!addToTrackMap(str2, requestInfo)) {
                    enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -5));
                    return;
                }
                IEnhancedAttestation iEnhancedAttestation = this.mEnhancedAttestation;
                if (iEnhancedAttestation != null) {
                    iEnhancedAttestation.enhancedAttestation(requestInfo.mNonce, requestInfo.mAuk, requestInfo.mCb.getEaAttestationCb(str2), requestInfo.mOnPrem);
                }
                Log.d(TAG, "enhancedAttestation requested");
                return;
            } catch (Exception e) {
                Log.e(TAG, "startAttestation: " + e.toString());
                e.printStackTrace();
                removeFromTrackMap(str2);
                enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -1));
                return;
            }
        }
        StringBuilder sb = new StringBuilder("nonce len: ");
        sb.append(str2 == null ? "null" : Integer.valueOf(str2.getBytes().length));
        Log.e(TAG, sb.toString());
        enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -5));
    }
}
