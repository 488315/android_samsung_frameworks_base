package com.samsung.android.knox.dar.ddar.proxy;

import android.p009os.Bundle;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.securesession.SecureClient;

/* loaded from: classes5.dex */
public abstract class IProxyAgentService {
  private static final String TAG = "IProxyAgentService::Abstract";
  protected SecureClient mSecureClientForInAPI;

  public abstract Bundle onMessage(int i, String str, Bundle bundle);

  public String initializeSecureSession(
      int callingUid, String svcName, String secureClientId, String secureClientPubKey) {
    try {
      Log.m94d(TAG, "initializeSecureSession between: " + svcName + " --- " + secureClientId);
      if (this.mSecureClientForInAPI == null) {
        this.mSecureClientForInAPI = new SecureClient(svcName);
      }
      this.mSecureClientForInAPI.initializeSecureSession(secureClientId, secureClientPubKey);
      return this.mSecureClientForInAPI.getPublicKeyString();
    } catch (Exception e) {
      e.printStackTrace();
      Log.m96e(TAG, "initializeSecureSession failed!");
      return null;
    }
  }

  public boolean terminateSecureSession(int callingUid, String svcName, String secureClientId) {
    try {
      Log.m94d(TAG, "terminateSecureSession between: " + svcName + " --- " + secureClientId);
      this.mSecureClientForInAPI.terminateSecureSession(secureClientId);
      if (!this.mSecureClientForInAPI.hasActiveSecureSessions()) {
        this.mSecureClientForInAPI.destroy();
        this.mSecureClientForInAPI = null;
        return true;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      Log.m96e(TAG, "terminateSecureSession failed!");
      return false;
    }
  }
}
