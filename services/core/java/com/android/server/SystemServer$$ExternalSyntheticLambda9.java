package com.android.server;

import android.net.ConnectivityModuleConnector;
import android.os.IBinder;
import android.os.ServiceManager;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemServer$$ExternalSyntheticLambda9 implements ConnectivityModuleConnector.ModuleServiceCallback {
    @Override // android.net.ConnectivityModuleConnector.ModuleServiceCallback
    public final void onModuleServiceConnected(IBinder iBinder) {
        LinkedList linkedList = SystemServer.sPendingWtfs;
        ServiceManager.addService("tethering", iBinder, false, 6);
    }
}
