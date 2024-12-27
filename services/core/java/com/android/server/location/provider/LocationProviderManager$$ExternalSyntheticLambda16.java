package com.android.server.location.provider;

import android.location.Location;
import android.location.provider.IProviderRequestListener;
import android.location.provider.ProviderRequest;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;

public final /* synthetic */ class LocationProviderManager$$ExternalSyntheticLambda16
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ LocationProviderManager f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ LocationProviderManager$$ExternalSyntheticLambda16(
            LocationProviderManager locationProviderManager, Location location) {
        this.f$0 = locationProviderManager;
        this.f$1 = location;
    }

    public /* synthetic */ LocationProviderManager$$ExternalSyntheticLambda16(
            LocationProviderManager locationProviderManager, ProviderRequest providerRequest) {
        this.f$0 = locationProviderManager;
        this.f$1 = providerRequest;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LocationProviderManager locationProviderManager = this.f$0;
                ProviderRequest providerRequest = (ProviderRequest) this.f$1;
                Iterator it = locationProviderManager.mProviderRequestListeners.iterator();
                while (it.hasNext()) {
                    IProviderRequestListener iProviderRequestListener =
                            (IProviderRequestListener) it.next();
                    try {
                        iProviderRequestListener.onProviderRequestChanged(
                                locationProviderManager.mName, providerRequest);
                    } catch (RemoteException unused) {
                        locationProviderManager.mProviderRequestListeners.remove(
                                iProviderRequestListener);
                    }
                }
                break;
            default:
                LocationProviderManager locationProviderManager2 = this.f$0;
                Location location = (Location) this.f$1;
                locationProviderManager2.getClass();
                try {
                    locationProviderManager2.mAltitudeConverter.addMslAltitudeToLocation(
                            locationProviderManager2.mContext, location);
                } catch (IOException e) {
                    Log.e("LocationManagerService", "not loading MSL altitude assets: " + e);
                }
                locationProviderManager2.mIsAltitudeConverterIdle = true;
                break;
        }
    }
}
