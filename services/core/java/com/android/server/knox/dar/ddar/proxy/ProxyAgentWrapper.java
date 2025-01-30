package com.android.server.knox.dar.ddar.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.server.knox.dar.ddar.DDLog;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgent;

/* loaded from: classes2.dex */
public class ProxyAgentWrapper {
    public Context mContext;
    public ProxyAgentInfo mInfo;
    public IProxyAgent mProxyAgent;
    public DualDARComnService mService;
    public final Object mProxyAgentLock = new Object();
    public boolean mBindPending = false;
    public boolean mIsReconnection = false;
    public boolean mIsStale = false;
    public boolean mIsNotify = false;
    public final ServiceConnection mConnection = new ServiceConnection() { // from class: com.android.server.knox.dar.ddar.proxy.ProxyAgentWrapper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DDLog.m36v("KnoxService::ProxyAgentWrapper", "Knox Proxy Agent started : " + ProxyAgentWrapper.this.mInfo.toString(), new Object[0]);
            ProxyAgentWrapper.this.mService.getHandler().removeMessages(1);
            synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                ProxyAgentWrapper.this.mProxyAgent = IProxyAgent.Stub.asInterface(iBinder);
                if (ProxyAgentWrapper.this.mProxyAgent == null) {
                    DDLog.m34e("KnoxService::ProxyAgentWrapper", "onServiceConnected: Unable to find Knox Proxy Agent!", new Object[0]);
                    return;
                }
                ProxyAgentWrapper.this.mBindPending = false;
                ProxyAgentWrapper.this.mIsNotify = true;
                ProxyAgentWrapper.this.mProxyAgentLock.notifyAll();
                if (ProxyAgentWrapper.this.mIsReconnection) {
                    ProxyAgentWrapper.this.onAgentReconnected();
                    ProxyAgentWrapper.this.mIsReconnection = false;
                }
                ProxyAgentWrapper.this.mService.getHandler().removeMessages(4);
                ProxyAgentWrapper.this.mService.getHandler().sendMessage(ProxyAgentWrapper.this.mService.getHandler().obtainMessage(4, ProxyAgentWrapper.this.mInfo));
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            DDLog.m33d("KnoxService::ProxyAgentWrapper", "Knox Proxy Agent disconnected : " + ProxyAgentWrapper.this.mInfo.toString(), new Object[0]);
            synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                ProxyAgentWrapper.this.mProxyAgent = null;
                ProxyAgentWrapper.this.mBindPending = false;
                ProxyAgentWrapper.this.triggerRestart();
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            DDLog.m33d("KnoxService::ProxyAgentWrapper", "onNullBinding : " + ProxyAgentWrapper.this.mInfo.toString(), new Object[0]);
            synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                ProxyAgentWrapper.this.mProxyAgent = null;
                ProxyAgentWrapper.this.mBindPending = false;
                ProxyAgentWrapper.this.triggerRestart();
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            DDLog.m33d("KnoxService::ProxyAgentWrapper", "onBindingDied : " + ProxyAgentWrapper.this.mInfo.toString(), new Object[0]);
            synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                ProxyAgentWrapper.this.mProxyAgent = null;
                ProxyAgentWrapper.this.mBindPending = false;
                ProxyAgentWrapper.this.triggerRestart();
            }
        }
    };

    public ProxyAgentWrapper(Context context, DualDARComnService dualDARComnService, ProxyAgentInfo proxyAgentInfo) {
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "ProxyAgentWrapper()", new Object[0]);
        this.mContext = context;
        this.mService = dualDARComnService;
        this.mInfo = proxyAgentInfo;
    }

    public boolean connectSync() {
        boolean connectAsync;
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "try connectSync : " + this.mInfo.toString(), new Object[0]);
        try {
            synchronized (this.mProxyAgentLock) {
                if (!isServiceConnected()) {
                    if (!this.mBindPending && !(connectAsync = connectAsync())) {
                        DDLog.m34e("KnoxService::ProxyAgentWrapper", "connection to Proxy Agent failed", new Object[0]);
                        return connectAsync;
                    }
                    this.mIsNotify = false;
                    this.mProxyAgentLock.wait(3000L);
                    if (!this.mIsNotify) {
                        DDLog.m33d("KnoxService::ProxyAgentWrapper", "thread waken up without notify", new Object[0]);
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean connectAsync() {
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "try connectAsync : " + this.mInfo.toString(), new Object[0]);
        synchronized (this.mProxyAgentLock) {
            ComponentName componentName = this.mInfo.mCompName;
            if (isServiceConnected()) {
                DDLog.m34e("KnoxService::ProxyAgentWrapper", "service " + componentName.flattenToShortString() + " already bound", new Object[0]);
                return true;
            }
            try {
                boolean bindServiceAsUser = this.mContext.bindServiceAsUser(new Intent().setComponent(componentName), this.mConnection, 67108865, new UserHandle(this.mInfo.mUserId));
                this.mBindPending = bindServiceAsUser;
                if (bindServiceAsUser) {
                    return true;
                }
                DDLog.m34e("KnoxService::ProxyAgentWrapper", "Can't bind to container service " + componentName.flattenToShortString(), new Object[0]);
                return false;
            } catch (Exception e) {
                DDLog.m34e("KnoxService::ProxyAgentWrapper", "Exception: " + e.getMessage(), new Object[0]);
                e.printStackTrace();
                return false;
            }
        }
    }

    public void disconnect() {
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "disconnect", new Object[0]);
        if (isServiceConnected()) {
            synchronized (this.mProxyAgentLock) {
                this.mContext.unbindService(this.mConnection);
                this.mProxyAgent = null;
                this.mBindPending = false;
            }
            DDLog.m33d("KnoxService::ProxyAgentWrapper", "Unbinding to agent done", new Object[0]);
        }
    }

    public boolean isServiceConnected() {
        boolean z;
        synchronized (this.mProxyAgentLock) {
            z = this.mProxyAgent != null;
        }
        return z;
    }

    public final void triggerRestart() {
        if (!this.mIsStale) {
            DDLog.m33d("KnoxService::ProxyAgentWrapper", "triggerRestart", new Object[0]);
            this.mService.getHandler().removeMessages(1);
            this.mService.getHandler().sendMessage(this.mService.getHandler().obtainMessage(1, this.mInfo));
            return;
        }
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "skipping triggerRestart because this is a stale object", new Object[0]);
    }

    public void markStale() {
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "markStale: " + this, new Object[0]);
        this.mIsStale = true;
    }

    public void enableReconnectionFlag() {
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "enableReconnectionFlag: " + this, new Object[0]);
        this.mIsReconnection = true;
    }

    public final void onAgentReconnected() {
        try {
            if (this.mProxyAgent != null) {
                DDLog.m33d("KnoxService::ProxyAgentWrapper", "sending onAgentReconnected signal", new Object[0]);
                this.mProxyAgent.onAgentReconnected();
            }
        } catch (RemoteException e) {
            DDLog.m34e("KnoxService::ProxyAgentWrapper", "RemoteException: name:" + this.mInfo.mCompName.flattenToShortString() + " -- onAgentReconnected", new Object[0]);
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006b A[Catch: RemoteException -> 0x007b, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x007b, blocks: (B:3:0x001a, B:5:0x001e, B:14:0x0055, B:17:0x005c, B:20:0x006b, B:21:0x003a, B:24:0x0044), top: B:2:0x001a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Bundle relay(int i, String str, String str2, Bundle bundle) {
        char c;
        Bundle bundle2;
        DDLog.m33d("KnoxService::ProxyAgentWrapper", "relay to Service : " + str, new Object[0]);
        try {
            if (this.mProxyAgent == null) {
                return null;
            }
            String string = bundle.getString("SECURE_CLIENT_ID");
            String string2 = bundle.getString("SECURE_CLIENT_PUB_KEY");
            int hashCode = str2.hashCode();
            if (hashCode == 636043837) {
                if (str2.equals("INITIALIZE_SECURE_SESSION")) {
                    c = 0;
                    if (c != 0) {
                    }
                    return bundle2;
                }
                c = 65535;
                if (c != 0) {
                }
                return bundle2;
            }
            if (hashCode == 681038700 && str2.equals("TERMINATE_SECURE_SESSION")) {
                c = 1;
                if (c != 0) {
                    String initializeSecureSession = this.mProxyAgent.initializeSecureSession(i, str, string, string2);
                    bundle2 = new Bundle();
                    bundle2.putString("dual_dar_response", initializeSecureSession);
                } else if (c == 1) {
                    boolean terminateSecureSession = this.mProxyAgent.terminateSecureSession(i, str, string);
                    bundle2 = new Bundle();
                    bundle2.putBoolean("dual_dar_response", terminateSecureSession);
                } else {
                    return this.mProxyAgent.onMessage(i, str, str2, bundle);
                }
                return bundle2;
            }
            c = 65535;
            if (c != 0) {
            }
            return bundle2;
        } catch (RemoteException e) {
            DDLog.m34e("KnoxService::ProxyAgentWrapper", "RemoteException: name:" + this.mInfo.mCompName.flattenToShortString() + " command:" + str2, new Object[0]);
            e.printStackTrace();
            return null;
        }
    }

    public boolean isProxyAgentBindPending() {
        boolean z;
        synchronized (this.mProxyAgentLock) {
            z = this.mBindPending;
        }
        return z;
    }
}
