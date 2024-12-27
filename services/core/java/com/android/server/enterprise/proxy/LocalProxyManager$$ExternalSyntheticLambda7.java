package com.android.server.enterprise.proxy;

import android.os.Bundle;
import android.os.RemoteException;
import android.sec.enterprise.proxy.IProxyCredentialsCallback;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.net.IProxyCallback;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocalProxyManager$$ExternalSyntheticLambda7
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ LocalProxyManager f$0;
    public final /* synthetic */ IProxyCredentialsCallback f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ LocalProxyManager$$ExternalSyntheticLambda7(
            LocalProxyManager localProxyManager,
            IProxyCredentialsCallback iProxyCredentialsCallback,
            Bundle bundle) {
        this.f$0 = localProxyManager;
        this.f$1 = iProxyCredentialsCallback;
        this.f$2 = bundle;
    }

    public final void runOrThrow() {
        LocalProxyManager localProxyManager = this.f$0;
        IProxyCredentialsCallback iProxyCredentialsCallback = this.f$1;
        Bundle bundle = this.f$2;
        IProxyCallback iProxyCallback = localProxyManager.mCallbackService;
        if (iProxyCallback == null) {
            localProxyManager.mLastCredentialsCallback = iProxyCredentialsCallback;
            localProxyManager.mLastCredentialsResponse = bundle;
            ((ArrayList) LocalProxyManager.sPendinOperationsList).add("setProxyCredentials");
            localProxyManager.bindProxyService();
            return;
        }
        try {
            iProxyCallback.onCredentialsReceived(bundle, iProxyCredentialsCallback);
        } catch (RemoteException e) {
            Log.e("LocalProxyManager", "Faield to set received credentials");
            e.printStackTrace();
        }
    }
}
