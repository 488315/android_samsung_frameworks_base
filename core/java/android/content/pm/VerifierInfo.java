package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;

public class VerifierInfo implements Parcelable {
    public static final Parcelable.Creator<VerifierInfo> CREATOR =
            new Parcelable.Creator<
                    VerifierInfo>() { // from class: android.content.pm.VerifierInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VerifierInfo createFromParcel(Parcel source) {
                    return new VerifierInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VerifierInfo[] newArray(int size) {
                    return new VerifierInfo[size];
                }
            };
    public final String packageName;
    public final PublicKey publicKey;

    public VerifierInfo(String packageName, PublicKey publicKey) {
        if (packageName == null || packageName.length() == 0) {
            throw new IllegalArgumentException("packageName must not be null or empty");
        }
        if (publicKey == null) {
            throw new IllegalArgumentException("publicKey must not be null");
        }
        this.packageName = packageName;
        this.publicKey = publicKey;
    }

    private VerifierInfo(Parcel source) {
        this.packageName = source.readString();
        this.publicKey =
                (PublicKey)
                        source.readSerializable(PublicKey.class.getClassLoader(), PublicKey.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeSerializable(this.publicKey);
    }
}
