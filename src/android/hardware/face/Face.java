package android.hardware.face;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class Face extends BiometricAuthenticator.Identifier {
    public static final Parcelable.Creator<Face> CREATOR = new Parcelable.Creator<Face>() { // from class: android.hardware.face.Face.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Face createFromParcel(Parcel parcel) {
            return new Face(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Face[] newArray(int i) {
            return new Face[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Face(CharSequence charSequence, int i, long j) {
        super(charSequence, i, j);
    }

    private Face(Parcel parcel) {
        super(parcel.readString(), parcel.readInt(), parcel.readLong());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName().toString());
        parcel.writeInt(getBiometricId());
        parcel.writeLong(getDeviceId());
    }
}
