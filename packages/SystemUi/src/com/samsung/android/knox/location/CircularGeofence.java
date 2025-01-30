package com.samsung.android.knox.location;

import android.os.Parcel;
import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CircularGeofence extends Geofence implements Serializable {
    private static final long serialVersionUID = 1;
    public LatLongPoint center;
    public double radius;

    public CircularGeofence(LatLongPoint latLongPoint, double d) {
        this.type = 1;
        this.center = latLongPoint;
        this.radius = d;
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.location.Geofence
    public final void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.center = LatLongPoint.CREATOR.createFromParcel(parcel);
        this.radius = parcel.readDouble();
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.center.writeToParcel(parcel, i);
        parcel.writeDouble(this.radius);
    }

    public CircularGeofence(Parcel parcel) {
        readFromParcel(parcel);
    }
}
