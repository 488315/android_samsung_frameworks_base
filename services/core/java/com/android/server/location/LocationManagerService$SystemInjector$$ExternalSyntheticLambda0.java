package com.android.server.location;

import android.util.Log;

import com.samsung.android.location.ISLocationManager;

import java.util.function.Consumer;

public final /* synthetic */ class LocationManagerService$SystemInjector$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int intValue = ((Integer) obj).intValue();
        ISLocationManager iSLocationManager = LocationManagerService.mISLocationManager;
        if (iSLocationManager == null) {
            Log.e("LocationManagerService", "sLocation is null");
            return;
        }
        try {
            iSLocationManager.onPermissionsChangedForSLocation(intValue);
        } catch (Throwable th) {
            Log.e("LocationManagerService", th.toString());
        }
    }
}
