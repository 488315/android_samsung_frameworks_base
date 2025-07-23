package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class AsksParcel implements Parcelable {
    public static final Parcelable.Creator<AsksParcel> CREATOR = new Parcelable.Creator<AsksParcel>() { // from class: android.content.pm.AsksParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AsksParcel createFromParcel(Parcel parcel) {
            return new AsksParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AsksParcel[] newArray(int i) {
            return new AsksParcel[i];
        }
    };
    private String safeInstallCert;
    private String safeInstallToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AsksParcel(String str, String str2) {
        this.safeInstallToken = str;
        this.safeInstallCert = str2;
    }

    public String getSafeInstallToken() {
        return this.safeInstallToken;
    }

    public String getSafeInstallCert() {
        return this.safeInstallCert;
    }

    public String toString() {
        return "SafeInstall {" + this.safeInstallToken + " " + this.safeInstallCert + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.safeInstallToken);
        parcel.writeString(this.safeInstallCert);
    }

    private AsksParcel(Parcel parcel) {
        this.safeInstallToken = parcel.readString();
        this.safeInstallCert = parcel.readString();
    }
}
