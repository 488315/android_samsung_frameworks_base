package com.samsung.android.knox.location;

import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class GeofenceFactory {
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
