package com.samsung.android.knox.tima.attestation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.knox.tima.attestation.IEnhancedAttestation;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class EnhancedAttestationPolicy {
    private static final String EA_BIND_ACTION = "com.samsung.android.knox.intent.action.BIND_KNOX_EA_SERVICE";
    private static final String EA_PACKAGE_CLASS = "com.samsung.android.knox.attestation.controller.SemEnhancedAttestation";
    private static final String EA_PACKAGE_NAME = "com.samsung.android.knox.attestation";
    private static final String TAG = "SEMEAPolicy";
    private static EnhancedAttestationPolicy mEaPolicy;
    private Context mContext;
    private final HashMap<String, RequestInfo> mTrackOpsHash = new HashMap<>();
    private ServiceConnection conn = new ServiceConnection() { // from class: com.samsung.android.knox.tima.attestation.EnhancedAttestationPolicy.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (EnhancedAttestationPolicy.class) {
                EnhancedAttestationPolicy.this.mEnhancedAttestation = null;
                Log.i(EnhancedAttestationPolicy.TAG, "On onServiceDisconnected");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (EnhancedAttestationPolicy.class) {
                EnhancedAttestationPolicy.this.mEnhancedAttestation = IEnhancedAttestation.Stub.asInterface(iBinder);
                Log.i(EnhancedAttestationPolicy.TAG, "On onServiceConnected");
            }
            EnhancedAttestationPolicy.this.handlePendingRequest();
        }
    };
    private IEnhancedAttestation mEnhancedAttestation = null;
    private boolean mProcessPendingRequest = false;

    private boolean isDongleDevice() {
        return false;
    }

    private boolean isEaSupportedFromSepLite() {
        return false;
    }

    private boolean isJdmDevice() {
        return false;
    }

    private boolean isSepLiteDevice() {
        return false;
    }

    static synchronized EnhancedAttestationPolicy getInstance(Context context) {
        if (context == null) {
            Log.e(TAG, "context is null");
            return null;
        }
        if (mEaPolicy == null) {
            mEaPolicy = new EnhancedAttestationPolicy(context);
        }
        return mEaPolicy;
    }

    static synchronized EnhancedAttestationPolicy getInstance() {
        return mEaPolicy;
    }

    private EnhancedAttestationPolicy(Context context) {
        this.mContext = context.getApplicationContext();
    }

    boolean isSupported() {
        if (!isDongleDevice() && isKnoxVersionSupported()) {
            return !(isSepLiteDevice() || isJdmDevice()) || isEaSupportedFromSepLite();
        }
        return false;
    }

    private boolean isKnoxVersionSupported() {
        return getKnoxVersion() >= 24;
    }

    static int getKnoxVersion() {
        return Integer.parseInt("39") - 5;
    }

    void startAttestation(String str, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback) {
        Log.d(TAG, "startAttestation on-prem");
        startAttestation(null, str, enhancedAttestationPolicyCallback, true);
    }

    void startAttestation(String str, String str2, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback) {
        Log.d(TAG, "startAttestation");
        startAttestation(str, str2, enhancedAttestationPolicyCallback, false);
    }

    private void startAttestation(String str, String str2, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback, boolean z) {
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
        if (str2 == null || str2.getBytes().length < 16 || str2.getBytes().length > 128) {
            StringBuilder sb = new StringBuilder("nonce len: ");
            sb.append(str2 == null ? PerfettoProtoLogImpl.NULL_STRING : Integer.valueOf(str2.getBytes().length));
            Log.e(TAG, sb.toString());
            enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -5));
            return;
        }
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
        } catch (Exception e) {
            Log.e(TAG, "startAttestation: " + e.toString());
            e.printStackTrace();
            removeFromTrackMap(str2);
            enhancedAttestationPolicyCallback.onAttestationFinished(getErrorResult(str2, -1));
        }
    }

    private EnhancedAttestationResult getErrorResult(String str, int i) {
        EnhancedAttestationResult enhancedAttestationResult = new EnhancedAttestationResult();
        enhancedAttestationResult.setErrorCode(i);
        Bundle bundle = new Bundle();
        bundle.putString("dataFieldUniqueId", str);
        enhancedAttestationResult.setData(bundle);
        return enhancedAttestationResult;
    }

    private boolean bindService() {
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
            boolean zBindServiceAsUser = this.mContext.bindServiceAsUser(intent, this.conn, 1, Process.myUserHandle());
            Log.i(TAG, "bind service:" + zBindServiceAsUser);
            return zBindServiceAsUser;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePendingRequest() {
        HashMap map;
        if (getTrackMapSize() < 1) {
            return;
        }
        synchronized (EnhancedAttestationPolicy.class) {
            map = new HashMap(this.mTrackOpsHash);
            clearTrackMap();
            this.mProcessPendingRequest = true;
        }
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            RequestInfo requestInfo = (RequestInfo) entry.getValue();
            Log.d(TAG, "process pending request: nonce len: " + str.length());
            startAttestation(requestInfo.mAuk, requestInfo.mNonce, requestInfo.mCb, requestInfo.mOnPrem);
        }
        synchronized (EnhancedAttestationPolicy.class) {
            this.mProcessPendingRequest = false;
        }
    }

    private synchronized boolean addToTrackMap(String str, RequestInfo requestInfo) {
        if (this.mTrackOpsHash.get(str) != null) {
            Log.i(TAG, "same nonce onProcessing");
            return false;
        }
        this.mTrackOpsHash.put(str, requestInfo);
        Log.d(TAG, "addToTrackMap:  " + getTrackMapSize());
        return true;
    }

    synchronized void removeFromTrackMap(String str) {
        this.mTrackOpsHash.remove(str);
        Log.d(TAG, "removeFromTrackMap: size: " + this.mTrackOpsHash.size() + ", pending: " + this.mProcessPendingRequest);
        if (this.mTrackOpsHash.isEmpty() && !this.mProcessPendingRequest) {
            Log.i(TAG, "Map is empty, call unBindService: ");
            this.mEnhancedAttestation = null;
            this.mContext.unbindService(this.conn);
        }
    }

    private synchronized void clearTrackMap() {
        this.mTrackOpsHash.clear();
    }

    private synchronized int getTrackMapSize() {
        return this.mTrackOpsHash.size();
    }

    private static class RequestInfo {
        private String mAuk;
        private EnhancedAttestationPolicyCallback mCb;
        private String mNonce;
        private boolean mOnPrem;

        RequestInfo(String str, String str2, EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback, boolean z) {
            this.mAuk = str;
            this.mNonce = str2;
            this.mCb = enhancedAttestationPolicyCallback;
            this.mOnPrem = z;
        }
    }
}
