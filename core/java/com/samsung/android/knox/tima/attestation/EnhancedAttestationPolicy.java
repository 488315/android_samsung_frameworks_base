package com.samsung.android.knox.tima.attestation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import com.samsung.android.ims.options.SemCapabilities;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class EnhancedAttestationPolicy {
  private static final String EA_BIND_ACTION =
      "com.samsung.android.knox.intent.action.BIND_KNOX_EA_SERVICE";
  private static final String EA_PACKAGE_CLASS =
      "com.samsung.android.knox.attestation.controller.SemEnhancedAttestation";
  private static final String EA_PACKAGE_NAME = "com.samsung.android.knox.attestation";
  private static final String TAG = "SEMEAPolicy";
  private static EnhancedAttestationPolicy mEaPolicy;
  private Context mContext;
  private final HashMap<String, RequestInfo> mTrackOpsHash = new HashMap<>();
  private ServiceConnection conn =
      new ServiceConnection() { // from class:
                                // com.samsung.android.knox.tima.attestation.EnhancedAttestationPolicy.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
          synchronized (EnhancedAttestationPolicy.class) {
            EnhancedAttestationPolicy.this.mEnhancedAttestation = null;
            Log.m98i(EnhancedAttestationPolicy.TAG, "On onServiceDisconnected");
          }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
          synchronized (EnhancedAttestationPolicy.class) {
            EnhancedAttestationPolicy.this.mEnhancedAttestation =
                IEnhancedAttestation.Stub.asInterface(service);
            Log.m98i(EnhancedAttestationPolicy.TAG, "On onServiceConnected");
          }
          EnhancedAttestationPolicy.this.handlePendingRequest();
        }
      };
  private IEnhancedAttestation mEnhancedAttestation = null;
  private boolean mProcessPendingRequest = false;

  static synchronized EnhancedAttestationPolicy getInstance(Context context) {
    synchronized (EnhancedAttestationPolicy.class) {
      if (context == null) {
        Log.m96e(TAG, "context is null");
        return null;
      }
      if (mEaPolicy == null) {
        mEaPolicy = new EnhancedAttestationPolicy(context);
      }
      return mEaPolicy;
    }
  }

  static synchronized EnhancedAttestationPolicy getInstance() {
    EnhancedAttestationPolicy enhancedAttestationPolicy;
    synchronized (EnhancedAttestationPolicy.class) {
      enhancedAttestationPolicy = mEaPolicy;
    }
    return enhancedAttestationPolicy;
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

  private boolean isDongleDevice() {
    return false;
  }

  private boolean isEaSupportedFromSepLite() {
    return false;
  }

  private boolean isSepLiteDevice() {
    if ("sep_lite".equals("sep_basic") || "sep_lite_new".equals("sep_basic")) {
      Log.m98i(TAG, "Sep Lite Device : sepCategory: sep_basic");
      return true;
    }
    return false;
  }

  private boolean isJdmDevice() {
    if ("jdm".equals("in_house")) {
      Log.m98i(TAG, "jdm device");
      return true;
    }
    return false;
  }

  private boolean isKnoxVersionSupported() {
    int knoxSdkver = getKnoxVersion();
    if (knoxSdkver >= 24) {
      return true;
    }
    return false;
  }

  static int getKnoxVersion() {
    return Integer.parseInt("37") - 5;
  }

  void startAttestation(String nonce, EnhancedAttestationPolicyCallback cb) {
    Log.m94d(TAG, "startAttestation on-prem");
    startAttestation(null, nonce, cb, true);
  }

  void startAttestation(String auk, String nonce, EnhancedAttestationPolicyCallback cb) {
    Log.m94d(TAG, "startAttestation");
    startAttestation(auk, nonce, cb, false);
  }

  private void startAttestation(
      String auk, String nonce, EnhancedAttestationPolicyCallback cb, boolean onPrem) {
    if (cb == null) {
      Log.m96e(TAG, "startAttestation: cb == null");
      return;
    }
    if (!isSupported()) {
      Log.m96e(TAG, "EA is not supported");
      cb.onAttestationFinished(getErrorResult(nonce, -4));
      return;
    }
    if ((auk == null || auk.length() < 1) && !onPrem) {
      Log.m96e(TAG, "auk is null");
      cb.onAttestationFinished(getErrorResult(nonce, -6));
      return;
    }
    if (nonce == null || nonce.getBytes().length < 16 || nonce.getBytes().length > 128) {
      Log.m96e(
          TAG,
          "nonce len: "
              + (nonce == null
                  ? SemCapabilities.FEATURE_TAG_NULL
                  : Integer.valueOf(nonce.getBytes().length)));
      cb.onAttestationFinished(getErrorResult(nonce, -5));
      return;
    }
    try {
      if (!bindService()) {
        Log.m96e(TAG, "bind request fail");
        cb.onAttestationFinished(getErrorResult(nonce, -7));
        return;
      }
      RequestInfo requestInfo = new RequestInfo(auk, nonce, cb, onPrem);
      if (!addToTrackMap(nonce, requestInfo)) {
        cb.onAttestationFinished(getErrorResult(nonce, -5));
        return;
      }
      IEnhancedAttestation iEnhancedAttestation = this.mEnhancedAttestation;
      if (iEnhancedAttestation != null) {
        iEnhancedAttestation.enhancedAttestation(
            requestInfo.mNonce,
            requestInfo.mAuk,
            requestInfo.mCb.getEaAttestationCb(nonce),
            requestInfo.mOnPrem);
      }
      Log.m94d(TAG, "enhancedAttestation requested");
    } catch (Exception e) {
      Log.m96e(TAG, "startAttestation: " + e.toString());
      e.printStackTrace();
      removeFromTrackMap(nonce);
      cb.onAttestationFinished(getErrorResult(nonce, -1));
    }
  }

  private EnhancedAttestationResult getErrorResult(String nonce, int errorCode) {
    EnhancedAttestationResult result = new EnhancedAttestationResult();
    result.setErrorCode(errorCode);
    Bundle data = new Bundle();
    data.putString("dataFieldUniqueId", nonce);
    result.setData(data);
    return result;
  }

  private boolean bindService() {
    synchronized (EnhancedAttestationPolicy.class) {
      Log.m94d(TAG, "bindService: " + this.mEnhancedAttestation);
      try {
        IEnhancedAttestation iEnhancedAttestation = this.mEnhancedAttestation;
        if (iEnhancedAttestation != null) {
          if (iEnhancedAttestation.asBinder().isBinderAlive()) {
            return true;
          }
        }
      } catch (Exception e) {
        Log.m98i(TAG, "bindService: " + e.toString());
      }
      Intent i = new Intent();
      i.setClassName(EA_PACKAGE_NAME, EA_PACKAGE_CLASS);
      i.setAction(EA_BIND_ACTION);
      boolean result = this.mContext.bindServiceAsUser(i, this.conn, 1, Process.myUserHandle());
      Log.m98i(TAG, "bind service:" + result);
      return result;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handlePendingRequest() {
    HashMap<String, RequestInfo> trackOpsHash;
    if (getTrackMapSize() < 1) {
      return;
    }
    synchronized (EnhancedAttestationPolicy.class) {
      trackOpsHash = new HashMap<>(this.mTrackOpsHash);
      clearTrackMap();
      this.mProcessPendingRequest = true;
    }
    for (Map.Entry<String, RequestInfo> entry : trackOpsHash.entrySet()) {
      String nonce = entry.getKey();
      RequestInfo key = entry.getValue();
      Log.m94d(TAG, "process pending request: nonce len: " + nonce.length());
      startAttestation(key.mAuk, key.mNonce, key.mCb, key.mOnPrem);
    }
    synchronized (EnhancedAttestationPolicy.class) {
      this.mProcessPendingRequest = false;
    }
  }

  private synchronized boolean addToTrackMap(String nonce, RequestInfo opt) {
    if (this.mTrackOpsHash.get(nonce) != null) {
      Log.m98i(TAG, "same nonce onProcessing");
      return false;
    }
    this.mTrackOpsHash.put(nonce, opt);
    Log.m94d(TAG, "addToTrackMap:  " + getTrackMapSize());
    return true;
  }

  synchronized void removeFromTrackMap(String nonce) {
    this.mTrackOpsHash.remove(nonce);
    Log.m94d(
        TAG,
        "removeFromTrackMap: size: "
            + this.mTrackOpsHash.size()
            + ", pending: "
            + this.mProcessPendingRequest);
    if (this.mTrackOpsHash.isEmpty() && !this.mProcessPendingRequest) {
      Log.m98i(TAG, "Map is empty, call unBindService: ");
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

    RequestInfo(String auk, String nonce, EnhancedAttestationPolicyCallback cb, boolean onPrem) {
      this.mAuk = auk;
      this.mNonce = nonce;
      this.mCb = cb;
      this.mOnPrem = onPrem;
    }
  }
}
