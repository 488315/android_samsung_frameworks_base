package com.android.server.knox.dar.ddar.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.server.knox.dar.ddar.DDLog;

import com.samsung.android.knox.dar.ddar.proxy.IProxyAgent;

public final class ProxyAgentWrapper {
    public final Context mContext;
    public final ProxyAgentInfo mInfo;
    public IProxyAgent mProxyAgent;
    public final DualDARComnService mService;
    public final Object mProxyAgentLock = new Object();
    public boolean mBindPending = false;
    public boolean mIsReconnection = false;
    public boolean mIsStale = false;
    public boolean mIsNotify = false;
    public final AnonymousClass1 mConnection =
            new ServiceConnection() { // from class:
                                      // com.android.server.knox.dar.ddar.proxy.ProxyAgentWrapper.1
                @Override // android.content.ServiceConnection
                public final void onBindingDied(ComponentName componentName) {
                    DDLog.d(
                            "KnoxService::ProxyAgentWrapper",
                            "onBindingDied : ".concat(ProxyAgentWrapper.this.mInfo.toString()),
                            new Object[0]);
                    synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                        ProxyAgentWrapper proxyAgentWrapper = ProxyAgentWrapper.this;
                        proxyAgentWrapper.mProxyAgent = null;
                        proxyAgentWrapper.mBindPending = false;
                        ProxyAgentWrapper.m633$$Nest$mtriggerRestart(proxyAgentWrapper);
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onNullBinding(ComponentName componentName) {
                    DDLog.d(
                            "KnoxService::ProxyAgentWrapper",
                            "onNullBinding : ".concat(ProxyAgentWrapper.this.mInfo.toString()),
                            new Object[0]);
                    synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                        ProxyAgentWrapper proxyAgentWrapper = ProxyAgentWrapper.this;
                        proxyAgentWrapper.mProxyAgent = null;
                        proxyAgentWrapper.mBindPending = false;
                        ProxyAgentWrapper.m633$$Nest$mtriggerRestart(proxyAgentWrapper);
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    DDLog.v(
                            "KnoxService::ProxyAgentWrapper",
                            "Knox Proxy Agent started : "
                                    .concat(ProxyAgentWrapper.this.mInfo.toString()),
                            new Object[0]);
                    ProxyAgentWrapper.this.mService.mHandler.removeMessages(1);
                    synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                        try {
                            ProxyAgentWrapper.this.mProxyAgent =
                                    IProxyAgent.Stub.asInterface(iBinder);
                            ProxyAgentWrapper proxyAgentWrapper = ProxyAgentWrapper.this;
                            if (proxyAgentWrapper.mProxyAgent == null) {
                                DDLog.e(
                                        "KnoxService::ProxyAgentWrapper",
                                        "onServiceConnected: Unable to find Knox Proxy Agent!",
                                        new Object[0]);
                                return;
                            }
                            proxyAgentWrapper.mBindPending = false;
                            proxyAgentWrapper.mIsNotify = true;
                            proxyAgentWrapper.mProxyAgentLock.notifyAll();
                            ProxyAgentWrapper proxyAgentWrapper2 = ProxyAgentWrapper.this;
                            if (proxyAgentWrapper2.mIsReconnection) {
                                try {
                                    if (proxyAgentWrapper2.mProxyAgent != null) {
                                        DDLog.d(
                                                "KnoxService::ProxyAgentWrapper",
                                                "sending onAgentReconnected signal",
                                                new Object[0]);
                                        proxyAgentWrapper2.mProxyAgent.onAgentReconnected();
                                    }
                                } catch (RemoteException e) {
                                    DDLog.e(
                                            "KnoxService::ProxyAgentWrapper",
                                            "RemoteException: name:"
                                                    + proxyAgentWrapper2.mInfo.mCompName
                                                            .flattenToShortString()
                                                    + " -- onAgentReconnected",
                                            new Object[0]);
                                    e.printStackTrace();
                                }
                                ProxyAgentWrapper.this.mIsReconnection = false;
                            }
                            ProxyAgentWrapper.this.mService.mHandler.removeMessages(4);
                            ProxyAgentWrapper proxyAgentWrapper3 = ProxyAgentWrapper.this;
                            DualDARComnService.AnonymousClass3 anonymousClass3 =
                                    proxyAgentWrapper3.mService.mHandler;
                            anonymousClass3.sendMessage(
                                    anonymousClass3.obtainMessage(4, proxyAgentWrapper3.mInfo));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    DDLog.d(
                            "KnoxService::ProxyAgentWrapper",
                            "Knox Proxy Agent disconnected : "
                                    .concat(ProxyAgentWrapper.this.mInfo.toString()),
                            new Object[0]);
                    synchronized (ProxyAgentWrapper.this.mProxyAgentLock) {
                        ProxyAgentWrapper proxyAgentWrapper = ProxyAgentWrapper.this;
                        proxyAgentWrapper.mProxyAgent = null;
                        proxyAgentWrapper.mBindPending = false;
                        ProxyAgentWrapper.m633$$Nest$mtriggerRestart(proxyAgentWrapper);
                    }
                }
            };

    /* renamed from: -$$Nest$mtriggerRestart, reason: not valid java name */
    public static void m633$$Nest$mtriggerRestart(ProxyAgentWrapper proxyAgentWrapper) {
        if (proxyAgentWrapper.mIsStale) {
            DDLog.d(
                    "KnoxService::ProxyAgentWrapper",
                    "skipping triggerRestart because this is a stale object",
                    new Object[0]);
            return;
        }
        DDLog.d("KnoxService::ProxyAgentWrapper", "triggerRestart", new Object[0]);
        DualDARComnService dualDARComnService = proxyAgentWrapper.mService;
        dualDARComnService.mHandler.removeMessages(1);
        DualDARComnService.AnonymousClass3 anonymousClass3 = dualDARComnService.mHandler;
        anonymousClass3.sendMessage(anonymousClass3.obtainMessage(1, proxyAgentWrapper.mInfo));
    }

    public ProxyAgentWrapper(
            Context context, DualDARComnService dualDARComnService, ProxyAgentInfo proxyAgentInfo) {
        DDLog.d("KnoxService::ProxyAgentWrapper", "ProxyAgentWrapper()", new Object[0]);
        this.mContext = context;
        this.mService = dualDARComnService;
        this.mInfo = proxyAgentInfo;
    }

    public final boolean connectAsync() {
        DDLog.d(
                "KnoxService::ProxyAgentWrapper",
                "try connectAsync : ".concat(this.mInfo.toString()),
                new Object[0]);
        synchronized (this.mProxyAgentLock) {
            ComponentName componentName = this.mInfo.mCompName;
            if (isServiceConnected()) {
                DDLog.e(
                        "KnoxService::ProxyAgentWrapper",
                        "service " + componentName.flattenToShortString() + " already bound",
                        new Object[0]);
                return true;
            }
            try {
                boolean bindServiceAsUser =
                        this.mContext.bindServiceAsUser(
                                new Intent().setComponent(componentName),
                                this.mConnection,
                                67108865,
                                new UserHandle(this.mInfo.mUserId));
                this.mBindPending = bindServiceAsUser;
                if (bindServiceAsUser) {
                    return true;
                }
                DDLog.e(
                        "KnoxService::ProxyAgentWrapper",
                        "Can't bind to container service " + componentName.flattenToShortString(),
                        new Object[0]);
                return false;
            } catch (Exception e) {
                DDLog.e(
                        "KnoxService::ProxyAgentWrapper",
                        "Exception: " + e.getMessage(),
                        new Object[0]);
                e.printStackTrace();
                return false;
            }
        }
    }

    public final boolean connectSync() {
        boolean connectAsync;
        DDLog.d(
                "KnoxService::ProxyAgentWrapper",
                "try connectSync : ".concat(this.mInfo.toString()),
                new Object[0]);
        try {
            synchronized (this.mProxyAgentLock) {
                try {
                    if (!isServiceConnected()) {
                        if (!this.mBindPending && !(connectAsync = connectAsync())) {
                            DDLog.e(
                                    "KnoxService::ProxyAgentWrapper",
                                    "connection to Proxy Agent failed",
                                    new Object[0]);
                            return connectAsync;
                        }
                        this.mIsNotify = false;
                        this.mProxyAgentLock.wait(3000L);
                        if (!this.mIsNotify) {
                            DDLog.d(
                                    "KnoxService::ProxyAgentWrapper",
                                    "thread waken up without notify",
                                    new Object[0]);
                            return false;
                        }
                    }
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void disconnect() {
        DDLog.d("KnoxService::ProxyAgentWrapper", "disconnect", new Object[0]);
        if (isServiceConnected()) {
            synchronized (this.mProxyAgentLock) {
                this.mContext.unbindService(this.mConnection);
                this.mProxyAgent = null;
                this.mBindPending = false;
            }
            DDLog.d("KnoxService::ProxyAgentWrapper", "Unbinding to agent done", new Object[0]);
        }
    }

    public final boolean isServiceConnected() {
        boolean z;
        synchronized (this.mProxyAgentLock) {
            z = this.mProxyAgent != null;
        }
        return z;
    }

    public final void markStale() {
        DDLog.d("KnoxService::ProxyAgentWrapper", "markStale: " + this, new Object[0]);
        this.mIsStale = true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle relay(
            int r8, java.lang.String r9, java.lang.String r10, android.os.Bundle r11) {
        /*
            r7 = this;
            java.lang.String r0 = "relay to Service : "
            java.lang.String r0 = android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(r0, r9)
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "KnoxService::ProxyAgentWrapper"
            com.android.server.knox.dar.ddar.DDLog.d(r3, r0, r2)
            com.samsung.android.knox.dar.ddar.proxy.IProxyAgent r0 = r7.mProxyAgent     // Catch: android.os.RemoteException -> L39
            if (r0 == 0) goto L9a
            java.lang.String r0 = "SECURE_CLIENT_ID"
            java.lang.String r0 = r11.getString(r0)     // Catch: android.os.RemoteException -> L39
            java.lang.String r2 = "SECURE_CLIENT_PUB_KEY"
            java.lang.String r2 = r11.getString(r2)     // Catch: android.os.RemoteException -> L39
            int r4 = r10.hashCode()     // Catch: android.os.RemoteException -> L39
            r5 = 636043837(0x25e9423d, float:4.0463942E-16)
            r6 = 1
            if (r4 == r5) goto L3b
            r5 = 681038700(0x2897d36c, float:1.6856057E-14)
            if (r4 == r5) goto L2f
            goto L45
        L2f:
            java.lang.String r4 = "TERMINATE_SECURE_SESSION"
            boolean r4 = r10.equals(r4)     // Catch: android.os.RemoteException -> L39
            if (r4 == 0) goto L45
            r4 = r6
            goto L46
        L39:
            r8 = move-exception
            goto L74
        L3b:
            java.lang.String r4 = "INITIALIZE_SECURE_SESSION"
            boolean r4 = r10.equals(r4)     // Catch: android.os.RemoteException -> L39
            if (r4 == 0) goto L45
            r4 = r1
            goto L46
        L45:
            r4 = -1
        L46:
            java.lang.String r5 = "dual_dar_response"
            if (r4 == 0) goto L64
            if (r4 == r6) goto L54
            com.samsung.android.knox.dar.ddar.proxy.IProxyAgent r0 = r7.mProxyAgent     // Catch: android.os.RemoteException -> L39
            android.os.Bundle r7 = r0.onMessage(r8, r9, r10, r11)     // Catch: android.os.RemoteException -> L39
            goto L73
        L54:
            com.samsung.android.knox.dar.ddar.proxy.IProxyAgent r11 = r7.mProxyAgent     // Catch: android.os.RemoteException -> L39
            boolean r8 = r11.terminateSecureSession(r8, r9, r0)     // Catch: android.os.RemoteException -> L39
            android.os.Bundle r9 = new android.os.Bundle     // Catch: android.os.RemoteException -> L39
            r9.<init>()     // Catch: android.os.RemoteException -> L39
            r9.putBoolean(r5, r8)     // Catch: android.os.RemoteException -> L39
        L62:
            r7 = r9
            goto L73
        L64:
            com.samsung.android.knox.dar.ddar.proxy.IProxyAgent r11 = r7.mProxyAgent     // Catch: android.os.RemoteException -> L39
            java.lang.String r8 = r11.initializeSecureSession(r8, r9, r0, r2)     // Catch: android.os.RemoteException -> L39
            android.os.Bundle r9 = new android.os.Bundle     // Catch: android.os.RemoteException -> L39
            r9.<init>()     // Catch: android.os.RemoteException -> L39
            r9.putString(r5, r8)     // Catch: android.os.RemoteException -> L39
            goto L62
        L73:
            return r7
        L74:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r11 = "RemoteException: name:"
            r9.<init>(r11)
            com.android.server.knox.dar.ddar.proxy.ProxyAgentInfo r7 = r7.mInfo
            android.content.ComponentName r7 = r7.mCompName
            java.lang.String r7 = r7.flattenToShortString()
            r9.append(r7)
            java.lang.String r7 = " command:"
            r9.append(r7)
            r9.append(r10)
            java.lang.String r7 = r9.toString()
            java.lang.Object[] r9 = new java.lang.Object[r1]
            com.android.server.knox.dar.ddar.DDLog.e(r3, r7, r9)
            r8.printStackTrace()
        L9a:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.dar.ddar.proxy.ProxyAgentWrapper.relay(int,"
                    + " java.lang.String, java.lang.String, android.os.Bundle):android.os.Bundle");
    }
}
