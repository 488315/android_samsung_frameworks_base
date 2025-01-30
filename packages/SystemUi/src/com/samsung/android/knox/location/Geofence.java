package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class Geofence implements Parcelable {
    public static final Parcelable.Creator<Geofence> CREATOR = new Parcelable.Creator<Geofence>() { // from class: com.samsung.android.knox.location.Geofence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final Geofence[] newArray(int i) {
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final Geofence createFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            parcel.setDataPosition(dataPosition);
            return GeofenceFactory.createGeofence(readInt, parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Geofence[] newArray(int i) {
            return null;
        }
    };

    /* renamed from: id */
    public int f486id;
    public int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.type = parcel.readInt();
        this.f486id = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeInt(this.f486id);
    }
}
