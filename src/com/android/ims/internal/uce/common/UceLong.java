package com.android.ims.internal.uce.common;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class UceLong implements Parcelable {
    public static final Parcelable.Creator<UceLong> CREATOR = new Parcelable.Creator<UceLong>() { // from class: com.android.ims.internal.uce.common.UceLong.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UceLong createFromParcel(Parcel parcel) {
            return new UceLong(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UceLong[] newArray(int i) {
            return new UceLong[i];
        }
    };
    private int mClientId;
    private long mUceLong;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UceLong() {
        this.mClientId = 1001;
    }

    public long getUceLong() {
        return this.mUceLong;
    }

    public void setUceLong(long j) {
        this.mUceLong = j;
    }

    public int getClientId() {
        return this.mClientId;
    }

    public void setClientId(int i) {
        this.mClientId = i;
    }

    public static UceLong getUceLongInstance() {
        return new UceLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcel(parcel);
    }

    private void writeToParcel(Parcel parcel) {
        parcel.writeLong(this.mUceLong);
        parcel.writeInt(this.mClientId);
    }

    private UceLong(Parcel parcel) {
        this.mClientId = 1001;
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mUceLong = parcel.readLong();
        this.mClientId = parcel.readInt();
    }
}
