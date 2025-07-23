package com.samsung.android.camera.iris;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public final class Iris implements Parcelable {
    public static final Parcelable.Creator<Iris> CREATOR = new Parcelable.Creator<Iris>() { // from class: com.samsung.android.camera.iris.Iris.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Iris createFromParcel(Parcel parcel) {
            return new Iris(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Iris[] newArray(int i) {
            return new Iris[i];
        }
    };
    private long mDeviceId;
    private int mGroupId;
    private int mIrisId;
    private CharSequence mName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Iris(CharSequence charSequence, int i, int i2, long j) {
        this.mName = charSequence;
        this.mGroupId = i;
        this.mIrisId = i2;
        this.mDeviceId = j;
    }

    private Iris(Parcel parcel) {
        this.mName = parcel.readString();
        this.mGroupId = parcel.readInt();
        this.mIrisId = parcel.readInt();
        this.mDeviceId = parcel.readLong();
    }

    public CharSequence getName() {
        return this.mName;
    }

    public int getIrisId() {
        return this.mIrisId;
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    public long getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName.toString());
        parcel.writeInt(this.mGroupId);
        parcel.writeInt(this.mIrisId);
        parcel.writeLong(this.mDeviceId);
    }
}
