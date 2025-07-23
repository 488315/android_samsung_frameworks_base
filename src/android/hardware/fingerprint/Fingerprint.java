package android.hardware.fingerprint;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class Fingerprint extends BiometricAuthenticator.Identifier {
    public static final Parcelable.Creator<Fingerprint> CREATOR = new Parcelable.Creator<Fingerprint>() { // from class: android.hardware.fingerprint.Fingerprint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Fingerprint createFromParcel(Parcel parcel) {
            return new Fingerprint(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Fingerprint[] newArray(int i) {
            return new Fingerprint[i];
        }
    };
    private int mDuplicatedImgCount;
    private int mGroupId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Fingerprint(CharSequence charSequence, int i, int i2, long j, int i3) {
        super(charSequence, i2, j);
        this.mGroupId = i;
        this.mDuplicatedImgCount = i3;
    }

    public Fingerprint(CharSequence charSequence, int i, int i2, long j) {
        super(charSequence, i2, j);
        this.mGroupId = i;
    }

    public Fingerprint(CharSequence charSequence, int i, long j) {
        super(charSequence, i, j);
    }

    private Fingerprint(Parcel parcel) {
        super(parcel.readString(), parcel.readInt(), parcel.readLong());
        this.mGroupId = parcel.readInt();
        this.mDuplicatedImgCount = parcel.readInt();
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName().toString());
        parcel.writeInt(getBiometricId());
        parcel.writeLong(getDeviceId());
        parcel.writeInt(this.mGroupId);
        parcel.writeInt(this.mDuplicatedImgCount);
    }

    public void semSetDuplicatedImgCount(int i) {
        this.mDuplicatedImgCount = i;
    }

    public int semGetDuplicatedImageCount() {
        return this.mDuplicatedImgCount;
    }
}
