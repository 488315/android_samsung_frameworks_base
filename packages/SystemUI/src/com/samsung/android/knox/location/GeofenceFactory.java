package com.samsung.android.knox.location;

import android.os.Parcel;

/* loaded from: classes4.dex */
public class GeofenceFactory {
    public static Geofence createGeofence(int i, Parcel parcel) {
        if (i == 1) {
            return new CircularGeofence(parcel);
        }
        if (i == 2) {
            return new PolygonalGeofence(parcel);
        }
        if (i != 3) {
            return null;
        }
        return new LinearGeofence(parcel);
    }
}
