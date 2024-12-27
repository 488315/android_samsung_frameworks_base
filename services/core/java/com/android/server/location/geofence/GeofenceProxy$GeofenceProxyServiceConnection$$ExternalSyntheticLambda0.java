package com.android.server.location.geofence;

import android.location.IGeofenceProvider;
import android.os.IBinder;

import com.android.server.servicewatcher.ServiceWatcher$BinderOperation;

public final /* synthetic */
class GeofenceProxy$GeofenceProxyServiceConnection$$ExternalSyntheticLambda0
        implements ServiceWatcher$BinderOperation {
    public final /* synthetic */ GeofenceProxy f$0;

    @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
    public final void run(IBinder iBinder) {
        GeofenceProxy geofenceProxy = this.f$0;
        geofenceProxy.getClass();
        IGeofenceProvider.Stub.asInterface(iBinder)
                .setGeofenceHardware(geofenceProxy.mGeofenceHardware);
    }
}
