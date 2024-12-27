package com.samsung.android.bio.face;

import android.hardware.face.Face;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemBioFace implements Parcelable {
    public static final Parcelable.Creator<SemBioFace> CREATOR =
            new Parcelable.Creator<
                    SemBioFace>() { // from class: com.samsung.android.bio.face.SemBioFace.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemBioFace createFromParcel(Parcel in) {
                    return new SemBioFace(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemBioFace[] newArray(int size) {
                    return new SemBioFace[size];
                }
            };
    private long mDeviceId;
    private int mFaceId;
    private int mGroupId;
    private CharSequence mName;

    public SemBioFace(CharSequence name, int groupId, int faceId, long deviceId) {
        this.mName = name;
        this.mGroupId = groupId;
        this.mFaceId = faceId;
        this.mDeviceId = deviceId;
    }

    public SemBioFace(Face face) {
        this.mName = face.getName();
        this.mGroupId = -1;
        this.mFaceId = face.getBiometricId();
        this.mDeviceId = face.getDeviceId();
    }

    private SemBioFace(Parcel in) {
        this.mName = in.readString();
        this.mGroupId = in.readInt();
        this.mFaceId = in.readInt();
        this.mDeviceId = in.readLong();
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mName.toString());
        out.writeInt(this.mGroupId);
        out.writeInt(this.mFaceId);
        out.writeLong(this.mDeviceId);
    }
}
