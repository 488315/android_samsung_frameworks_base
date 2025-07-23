package com.samsung.android.bio.face;

import android.hardware.face.Face;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemBioFace implements Parcelable {
    public static final Parcelable.Creator<SemBioFace> CREATOR = new Parcelable.Creator<SemBioFace>() { // from class: com.samsung.android.bio.face.SemBioFace.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBioFace createFromParcel(Parcel parcel) {
            return new SemBioFace(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBioFace[] newArray(int i) {
            return new SemBioFace[i];
        }
    };
    private long mDeviceId;
    private int mFaceId;
    private int mGroupId;
    private CharSequence mName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemBioFace(CharSequence charSequence, int i, int i2, long j) {
        this.mName = charSequence;
        this.mGroupId = i;
        this.mFaceId = i2;
        this.mDeviceId = j;
    }

    public SemBioFace(Face face) {
        this.mName = face.getName();
        this.mGroupId = -1;
        this.mFaceId = face.getBiometricId();
        this.mDeviceId = face.getDeviceId();
    }

    private SemBioFace(Parcel parcel) {
        this.mName = parcel.readString();
        this.mGroupId = parcel.readInt();
        this.mFaceId = parcel.readInt();
        this.mDeviceId = parcel.readLong();
    }

    public CharSequence getName() {
        return this.mName;
    }

    public int getFaceId() {
        return this.mFaceId;
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
        parcel.writeInt(this.mFaceId);
        parcel.writeLong(this.mDeviceId);
    }
}
