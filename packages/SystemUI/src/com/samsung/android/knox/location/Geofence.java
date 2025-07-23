package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class Geofence implements Parcelable {
    public static final Parcelable.Creator<Geofence> CREATOR = new Parcelable.Creator<Geofence>() { // from class: com.samsung.android.knox.location.Geofence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Geofence[] newArray(int i) {
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Geofence createFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            parcel.setDataPosition(dataPosition);
            return GeofenceFactory.createGeofence(readInt, parcel);
        }
    };
    public int id;
    public int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.type = parcel.readInt();
        this.id = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeInt(this.id);
    }
}
