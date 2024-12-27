package com.android.server.enterprise.proxy;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkRequest;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.net.IProxyCallback;
import com.android.server.enterprise.proxy.LocalProxyManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocalProxyManager$$ExternalSyntheticLambda2 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocalProxyManager f$0;

    public /* synthetic */ LocalProxyManager$$ExternalSyntheticLambda2(LocalProxyManager localProxyManager, int i) {
        this.$r8$classId = i;
        this.f$0 = localProxyManager;
    }

    private final void runOrThrow$com$android$server$enterprise$proxy$LocalProxyManager$$ExternalSyntheticLambda6() {
        LocalProxyManager localProxyManager = this.f$0;
        synchronized (localProxyManager) {
            try {
                if (localProxyManager.mProxyConnection != null) {
                    Log.i("LocalProxyManager", "Stopping enterprise proxy server");
                    localProxyManager.clearProxyServerCache();
                    Log.d("LocalProxyManager", "Clear notification dialog");
                    if (localProxyManager.mNotificationManager != null) {
                        Binder.withCleanCallingIdentity(new LocalProxyManager$$ExternalSyntheticLambda2(localProxyManager, 7));
                    } else {
                        Log.d("LocalProxyManager", "NotificationManager is null");
                    }
                    Context context = localProxyManager.mContext;
                    if (context != null) {
                        context.unbindService(localProxyManager.mProxyConnection);
                    }
                    localProxyManager.mProxyConnection = null;
                    localProxyManager.mCallbackService = null;
                    synchronized (LocalProxyManager.mProxyLock) {
                        LocalProxyManager.sLocalProxyInfo = null;
                        LocalProxyManager.sIsLocalProxyServerRunning = false;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void runOrThrow() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                LocalProxyManager localProxyManager = this.f$0;
                localProxyManager.getConnectivityManagerService().unregisterNetworkCallback(localProxyManager.mNetworkCallback);
                synchronized (LocalProxyManager.mNetworkCallbackLock) {
                    LocalProxyManager.sIsNetworkCallbackRunning = false;
                }
                localProxyManager.handleLocalProxyServer();
                return;
            case 1:
                LocalProxyManager localProxyManager2 = this.f$0;
                IProxyCallback iProxyCallback = localProxyManager2.mCallbackService;
                if (iProxyCallback == null) {
                    ((ArrayList) LocalProxyManager.sPendinOperationsList).add("clearCache");
                    localProxyManager2.bindProxyService();
                    return;
                }
                try {
                    iProxyCallback.clearProxyServerCache();
                    IProxyCallback iProxyCallback2 = localProxyManager2.mCallbackService;
                    if (LocalProxyManager.getGlobalProxy() == null && LocalProxyManager.getDefaultProxy() == null) {
                        z = false;
                        iProxyCallback2.setEnterpriseProxy(z);
                        return;
                    }
                    z = true;
                    iProxyCallback2.setEnterpriseProxy(z);
                    return;
                } catch (RemoteException e) {
                    Log.e("LocalProxyManager", "Failed to clear proxy server cache");
                    e.printStackTrace();
                    return;
                }
            case 2:
                LocalProxyManager localProxyManager3 = this.f$0;
                synchronized (localProxyManager3) {
                    Log.i("LocalProxyManager", "Starting enterprise proxy server");
                    if (localProxyManager3.mContext == null) {
                        Log.e("LocalProxyManager", "No context for binding");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClassName("com.android.proxyhandler", "com.android.proxyhandler.ProxyService");
                    LocalProxyManager.AnonymousClass1 anonymousClass1 = new LocalProxyManager.AnonymousClass1(localProxyManager3, 0);
                    localProxyManager3.mProxyConnection = anonymousClass1;
                    localProxyManager3.mContext.bindService(intent, anonymousClass1, 1073741829);
                    return;
                }
            case 3:
                this.f$0.mConnectivityManager.setGlobalProxy(LocalProxyManager.sLocalProxyInfo);
                return;
            case 4:
                LocalProxyManager localProxyManager4 = this.f$0;
                localProxyManager4.getClass();
                localProxyManager4.getConnectivityManagerService().registerNetworkCallback(new NetworkRequest.Builder().addTransportType(1).addCapability(12).build(), localProxyManager4.mNetworkCallback);
                synchronized (LocalProxyManager.mNetworkCallbackLock) {
                    LocalProxyManager.sIsNetworkCallbackRunning = true;
                }
                return;
            case 5:
                LocalProxyManager localProxyManager5 = this.f$0;
                localProxyManager5.getClass();
                Intent intent2 = new Intent("com.samsung.android.knox.intent.action.PROXY_REFRESH_CREDENTIALS_DIALOG_INTERNAL");
                intent2.setPackage(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME);
                localProxyManager5.mContext.sendStickyBroadcastAsUser(intent2, UserHandle.ALL);
                return;
            case 6:
                runOrThrow$com$android$server$enterprise$proxy$LocalProxyManager$$ExternalSyntheticLambda6();
                return;
            default:
                this.f$0.mNotificationManager.cancelAsUser("LocalProxyManager", 993, UserHandle.ALL);
                return;
        }
    }
}
