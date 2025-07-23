package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LatLongPoint implements Serializable, Parcelable {
    public static final Parcelable.Creator<LatLongPoint> CREATOR = new Parcelable.Creator<LatLongPoint>() { // from class: com.samsung.android.knox.location.LatLongPoint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLongPoint createFromParcel(Parcel parcel) {
            return new LatLongPoint(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLongPoint[] newArray(int i) {
            return new LatLongPoint[i];
        }
    };
    private static final long serialVersionUID = 1;
    public double latitude;
    public double longitude;

    public /* synthetic */ LatLongPoint(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
    }

    public LatLongPoint(double d, double d2) {
        this.latitude = d;
        this.longitude = d2;
    }

    private LatLongPoint(Parcel parcel) {
        readFromParcel(parcel);
    }
}
