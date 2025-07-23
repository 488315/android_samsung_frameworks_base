package com.samsung.android.knox.dar.ddar.proxy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgent;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class KnoxProxyAgent extends Service {
    private static final String TAG = "KnoxProxyAgent";
    Object mPolicyServiceLock = new Object();
    HashMap<String, IProxyAgentService> mPolicyServiceMap = new HashMap<>();

    protected void onAgentReconnected() {
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.i(TAG, "onCreate!");
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new ServiceImpl();
    }

    private final class ServiceImpl extends IProxyAgent.Stub {
        private ServiceImpl() {
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public Bundle onMessage(int i, String str, String str2, Bundle bundle) throws RemoteException {
            return KnoxProxyAgent.this.relay(i, str, str2, bundle);
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public String initializeSecureSession(int i, String str, String str2, String str3) {
            return KnoxProxyAgent.this.establishSecureSession(i, str, str2, str3);
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public boolean terminateSecureSession(int i, String str, String str2) {
            return KnoxProxyAgent.this.teardownSecureSession(i, str, str2);
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public void onAgentReconnected() {
            KnoxProxyAgent.this.onAgentReconnected();
        }
    }

    public Bundle relay(int i, String str, String str2, Bundle bundle) {
        IProxyAgentService service = getService(str);
        if (service == null) {
            Log.e(TAG, "Service not found");
            return null;
        }
        return service.onMessage(i, str2, bundle);
    }

    protected String establishSecureSession(int i, String str, String str2, String str3) {
        IProxyAgentService service = getService(str);
        if (service == null) {
            Log.e(TAG, "Service not found");
            return null;
        }
        return service.initializeSecureSession(i, str, str2, str3);
    }

    protected boolean teardownSecureSession(int i, String str, String str2) {
        IProxyAgentService service = getService(str);
        if (service == null) {
            Log.e(TAG, "Service not found");
            return false;
        }
        return service.terminateSecureSession(i, str, str2);
    }

    public boolean register(String str, IProxyAgentService iProxyAgentService) {
        synchronized (this.mPolicyServiceLock) {
            if (this.mPolicyServiceMap.containsKey(str)) {
                Log.i(TAG, "Proxy Agent Service " + str + " already exists");
                return false;
            }
            this.mPolicyServiceMap.put(str, iProxyAgentService);
            Log.i(TAG, "Proxy Agent Service " + str + " registered");
            return true;
        }
    }

    public IProxyAgentService getService(String str) {
        synchronized (this.mPolicyServiceLock) {
            if (!this.mPolicyServiceMap.containsKey(str)) {
                Log.i(TAG, "Proxy Agent Service " + str + " not found");
                return null;
            }
            return this.mPolicyServiceMap.get(str);
        }
    }
}
