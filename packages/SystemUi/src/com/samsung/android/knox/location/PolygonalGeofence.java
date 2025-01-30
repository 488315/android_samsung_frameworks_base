package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.ParcelFormatException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PolygonalGeofence extends Geofence implements Serializable {
    private static final long serialVersionUID = 1;
    public BoundingBox boundingBox;
    public double graceDistance;
    public List<LatLongPoint> optimizedPoints;
    public List<LatLongPoint> points;
    public List<LatLongPoint> pointsWithinGraceLimit;

    public PolygonalGeofence(List<LatLongPoint> list, double d) {
        this.type = 2;
        this.points = list;
        this.graceDistance = d;
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.location.Geofence
    public final void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            LatLongPoint createFromParcel = LatLongPoint.CREATOR.createFromParcel(parcel);
            if (createFromParcel == null) {
                throw new ParcelFormatException("Parcel format exception");
            }
            this.points.add(createFromParcel);
        }
        this.graceDistance = parcel.readDouble();
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.points.size());
        for (int i2 = 0; i2 < this.points.size(); i2++) {
            this.points.get(i2).writeToParcel(parcel, i);
        }
        parcel.writeDouble(this.graceDistance);
    }

    public PolygonalGeofence(List<LatLongPoint> list, double d, List<LatLongPoint> list2, List<LatLongPoint> list3, BoundingBox boundingBox) {
        this.type = 2;
        this.points = list;
        this.graceDistance = d;
        this.optimizedPoints = list2;
        this.pointsWithinGraceLimit = list3;
        this.boundingBox = boundingBox;
    }

    public PolygonalGeofence(Parcel parcel) {
        this.points = new ArrayList();
        readFromParcel(parcel);
    }
}
