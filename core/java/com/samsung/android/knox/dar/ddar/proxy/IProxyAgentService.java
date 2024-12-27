package com.samsung.android.knox.dar.ddar.proxy;

import android.os.Bundle;
import android.util.Log;

import com.samsung.android.knox.dar.ddar.securesession.SecureClient;

public abstract class IProxyAgentService {
    private static final String TAG = "IProxyAgentService::Abstract";
    protected SecureClient mSecureClientForInAPI;

    public abstract Bundle onMessage(int i, String str, Bundle bundle);

    public String initializeSecureSession(
            int callingUid, String svcName, String secureClientId, String secureClientPubKey) {
        try {
            Log.d(TAG, "initializeSecureSession between: " + svcName + " --- " + secureClientId);
            if (this.mSecureClientForInAPI == null) {
                this.mSecureClientForInAPI = new SecureClient(svcName);
            }
            this.mSecureClientForInAPI.initializeSecureSession(secureClientId, secureClientPubKey);
            return this.mSecureClientForInAPI.getPublicKeyString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "initializeSecureSession failed!");
            return null;
        }
    }

    public boolean terminateSecureSession(int callingUid, String svcName, String secureClientId) {
        try {
            Log.d(TAG, "terminateSecureSession between: " + svcName + " --- " + secureClientId);
            this.mSecureClientForInAPI.terminateSecureSession(secureClientId);
            if (!this.mSecureClientForInAPI.hasActiveSecureSessions()) {
                this.mSecureClientForInAPI.destroy();
                this.mSecureClientForInAPI = null;
                return true;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "terminateSecureSession failed!");
            return false;
        }
    }
}
