package com.android.ims;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class RcsTypeIdPair implements Parcelable {
    public static final Parcelable.Creator<RcsTypeIdPair> CREATOR = new Parcelable.Creator<RcsTypeIdPair>() { // from class: com.android.ims.RcsTypeIdPair.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsTypeIdPair createFromParcel(Parcel parcel) {
            return new RcsTypeIdPair(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsTypeIdPair[] newArray(int i) {
            return new RcsTypeIdPair[i];
        }
    };
    private int mId;
    private int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RcsTypeIdPair(int i, int i2) {
        this.mType = i;
        this.mId = i2;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public RcsTypeIdPair(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mId);
    }
}
