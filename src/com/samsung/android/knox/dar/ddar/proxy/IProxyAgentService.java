package com.samsung.android.knox.dar.ddar.proxy;

import android.os.Bundle;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.securesession.SecureClient;

/* loaded from: classes6.dex */
public abstract class IProxyAgentService {
    private static final String TAG = "IProxyAgentService::Abstract";
    protected SecureClient mSecureClientForInAPI;

    public abstract Bundle onMessage(int i, String str, Bundle bundle);

    public String initializeSecureSession(int i, String str, String str2, String str3) {
        try {
            Log.d(TAG, "initializeSecureSession between: " + str + " --- " + str2);
            if (this.mSecureClientForInAPI == null) {
                this.mSecureClientForInAPI = new SecureClient(str);
            }
            this.mSecureClientForInAPI.initializeSecureSession(str2, str3);
            return this.mSecureClientForInAPI.getPublicKeyString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "initializeSecureSession failed!");
            return null;
        }
    }

    public boolean terminateSecureSession(int i, String str, String str2) {
        try {
            Log.d(TAG, "terminateSecureSession between: " + str + " --- " + str2);
            this.mSecureClientForInAPI.terminateSecureSession(str2);
            if (this.mSecureClientForInAPI.hasActiveSecureSessions()) {
                return true;
            }
            this.mSecureClientForInAPI.destroy();
            this.mSecureClientForInAPI = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "terminateSecureSession failed!");
            return false;
        }
    }
}
