package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LinearGeofence extends Geofence implements Serializable, Parcelable {
    private static final long serialVersionUID = 1;
    public BoundingBox boundingBox;
    public List<LatLongPoint> optimizedPoints;
    public List<LatLongPoint> points;
    public double width;

    public LinearGeofence(List<LatLongPoint> list, double d) {
        this.type = 3;
        this.points = list;
        this.width = d;
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.location.Geofence
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            LatLongPoint createFromParcel = LatLongPoint.CREATOR.createFromParcel(parcel);
            if (createFromParcel == null) {
                throw new ParcelFormatException("Parcel format exception");
            }
            this.points.add(createFromParcel);
        }
        this.width = parcel.readDouble();
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.points.size());
        for (int i2 = 0; i2 < this.points.size(); i2++) {
            this.points.get(i2).writeToParcel(parcel, i);
        }
        parcel.writeDouble(this.width);
    }

    public LinearGeofence(List<LatLongPoint> list, List<LatLongPoint> list2, BoundingBox boundingBox, double d) {
        this.type = 3;
        this.points = list;
        this.optimizedPoints = list2;
        this.boundingBox = boundingBox;
        this.width = d;
    }

    public LinearGeofence(Parcel parcel) {
        this.points = new ArrayList();
        readFromParcel(parcel);
    }
}
