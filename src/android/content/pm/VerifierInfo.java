package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.PublicKey;

/* loaded from: classes.dex */
public class VerifierInfo implements Parcelable {
    public static final Parcelable.Creator<VerifierInfo> CREATOR = new Parcelable.Creator<VerifierInfo>() { // from class: android.content.pm.VerifierInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifierInfo createFromParcel(Parcel parcel) {
            return new VerifierInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifierInfo[] newArray(int i) {
            return new VerifierInfo[i];
        }
    };
    public final String packageName;
    public final PublicKey publicKey;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VerifierInfo(String str, PublicKey publicKey) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("packageName must not be null or empty");
        }
        if (publicKey == null) {
            throw new IllegalArgumentException("publicKey must not be null");
        }
        this.packageName = str;
        this.publicKey = publicKey;
    }

    private VerifierInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.publicKey = (PublicKey) parcel.readSerializable(PublicKey.class.getClassLoader(), PublicKey.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeSerializable(this.publicKey);
    }
}
