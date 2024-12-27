package com.android.server;

import android.net.ConnectivityModuleConnector;
import android.os.IBinder;
import android.os.ServiceManager;

import java.util.LinkedList;

public final /* synthetic */ class SystemServer$$ExternalSyntheticLambda9
        implements ConnectivityModuleConnector.ModuleServiceCallback {
    @Override // android.net.ConnectivityModuleConnector.ModuleServiceCallback
    public final void onModuleServiceConnected(IBinder iBinder) {
        LinkedList linkedList = SystemServer.sPendingWtfs;
        ServiceManager.addService("tethering", iBinder, false, 6);
    }
}
