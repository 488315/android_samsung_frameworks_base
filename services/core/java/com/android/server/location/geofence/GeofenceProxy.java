package com.android.server.location.geofence;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.hardware.location.IGeofenceHardware;
import android.location.IGeofenceProvider;
import android.location.IGpsGeofenceHardware;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.server.FgThread;
import com.android.server.location.gnss.GnssGeofenceProxy;
import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher$ServiceListener;
import com.android.server.servicewatcher.ServiceWatcherImpl;

import java.util.Objects;

public final class GeofenceProxy implements ServiceWatcher$ServiceListener {
    public volatile IGeofenceHardware mGeofenceHardware;
    public final IGpsGeofenceHardware mGpsGeofenceHardware;
    public final ServiceWatcherImpl mServiceWatcher;

    public final class GeofenceProxyServiceConnection implements ServiceConnection {
        public GeofenceProxyServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IGeofenceHardware asInterface = IGeofenceHardware.Stub.asInterface(iBinder);
            try {
                asInterface.setGpsGeofenceHardware(GeofenceProxy.this.mGpsGeofenceHardware);
                GeofenceProxy.this.mGeofenceHardware = asInterface;
                GeofenceProxy geofenceProxy = GeofenceProxy.this;
                geofenceProxy.mServiceWatcher.runOnBinder(
                        new GeofenceProxy$GeofenceProxyServiceConnection$$ExternalSyntheticLambda0(
                                geofenceProxy));
            } catch (RemoteException e) {
                Log.w("GeofenceProxy", "unable to initialize geofence hardware", e);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            GeofenceProxy.this.mGeofenceHardware = null;
            GeofenceProxy geofenceProxy = GeofenceProxy.this;
            geofenceProxy.mServiceWatcher.runOnBinder(
                    new GeofenceProxy$GeofenceProxyServiceConnection$$ExternalSyntheticLambda0(
                            geofenceProxy));
        }
    }

    public GeofenceProxy(Context context, GnssGeofenceProxy gnssGeofenceProxy) {
        Objects.requireNonNull(gnssGeofenceProxy);
        this.mGpsGeofenceHardware = gnssGeofenceProxy;
        this.mServiceWatcher =
                new ServiceWatcherImpl(
                        context,
                        FgThread.getHandler(),
                        "GeofenceProxy",
                        CurrentUserServiceSupplier.createFromConfig(
                                context,
                                "com.android.location.service.GeofenceProvider",
                                R.bool.config_enableMultiUserUI,
                                R.string.duration_years_relative),
                        this,
                        null);
        this.mGeofenceHardware = null;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onBind(
            IBinder iBinder, CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) {
        IGeofenceProvider.Stub.asInterface(iBinder).setGeofenceHardware(this.mGeofenceHardware);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onUnbind() {}
}
