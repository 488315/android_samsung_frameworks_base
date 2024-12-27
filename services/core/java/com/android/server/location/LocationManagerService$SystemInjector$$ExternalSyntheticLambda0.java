package com.android.server.location;

import android.util.Log;

import com.samsung.android.location.ISLocationManager;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
