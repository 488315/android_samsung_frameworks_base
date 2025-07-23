package com.samsung.android.knox.location;

import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
