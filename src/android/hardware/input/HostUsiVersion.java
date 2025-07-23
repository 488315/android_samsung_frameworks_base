package android.hardware.input;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class HostUsiVersion implements Parcelable {
    public static final Parcelable.Creator<HostUsiVersion> CREATOR = new Parcelable.Creator<HostUsiVersion>() { // from class: android.hardware.input.HostUsiVersion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HostUsiVersion[] newArray(int i) {
            return new HostUsiVersion[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HostUsiVersion createFromParcel(Parcel parcel) {
            return new HostUsiVersion(parcel);
        }
    };
    private final int mMajorVersion;
    private final int mMinorVersion;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isValid() {
        return this.mMajorVersion >= 0 && this.mMinorVersion >= 0;
    }

    public HostUsiVersion(int i, int i2) {
        this.mMajorVersion = i;
        this.mMinorVersion = i2;
    }

    public int getMajorVersion() {
        return this.mMajorVersion;
    }

    public int getMinorVersion() {
        return this.mMinorVersion;
    }

    public String toString() {
        return "HostUsiVersion { majorVersion = " + this.mMajorVersion + ", minorVersion = " + this.mMinorVersion + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            HostUsiVersion hostUsiVersion = (HostUsiVersion) obj;
            if (this.mMajorVersion == hostUsiVersion.mMajorVersion && this.mMinorVersion == hostUsiVersion.mMinorVersion) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((this.mMajorVersion + 31) * 31) + this.mMinorVersion;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMajorVersion);
        parcel.writeInt(this.mMinorVersion);
    }

    HostUsiVersion(Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.mMajorVersion = readInt;
        this.mMinorVersion = readInt2;
    }
}
