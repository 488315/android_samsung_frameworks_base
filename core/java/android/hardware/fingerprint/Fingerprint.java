package android.hardware.fingerprint;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Parcel;
import android.os.Parcelable;

public final class Fingerprint extends BiometricAuthenticator.Identifier {
    public static final Parcelable.Creator<Fingerprint> CREATOR =
            new Parcelable.Creator<
                    Fingerprint>() { // from class: android.hardware.fingerprint.Fingerprint.1
                @Override // android.os.Parcelable.Creator
                public Fingerprint createFromParcel(Parcel in) {
                    return new Fingerprint(in);
                }

                @Override // android.os.Parcelable.Creator
                public Fingerprint[] newArray(int size) {
                    return new Fingerprint[size];
                }
            };
    private int mDuplicatedImgCount;
    private int mGroupId;

    public Fingerprint(
            CharSequence name, int groupId, int fingerId, long deviceId, int duplicatedCnt) {
        super(name, fingerId, deviceId);
        this.mGroupId = groupId;
        this.mDuplicatedImgCount = duplicatedCnt;
    }

    public Fingerprint(CharSequence name, int groupId, int fingerId, long deviceId) {
        super(name, fingerId, deviceId);
        this.mGroupId = groupId;
    }

    public Fingerprint(CharSequence name, int fingerId, long deviceId) {
        super(name, fingerId, deviceId);
    }

    private Fingerprint(Parcel in) {
        super(in.readString(), in.readInt(), in.readLong());
        this.mGroupId = in.readInt();
        this.mDuplicatedImgCount = in.readInt();
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getName().toString());
        out.writeInt(getBiometricId());
        out.writeLong(getDeviceId());
        out.writeInt(this.mGroupId);
        out.writeInt(this.mDuplicatedImgCount);
    }

    public void semSetDuplicatedImgCount(int count) {
        this.mDuplicatedImgCount = count;
    }

    public int semGetDuplicatedImageCount() {
        return this.mDuplicatedImgCount;
    }
}
