package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

public class PackageInfoLite implements Parcelable {
    public static final Parcelable.Creator<PackageInfoLite> CREATOR =
            new Parcelable.Creator<
                    PackageInfoLite>() { // from class: android.content.pm.PackageInfoLite.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PackageInfoLite createFromParcel(Parcel source) {
                    return new PackageInfoLite(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PackageInfoLite[] newArray(int size) {
                    return new PackageInfoLite[size];
                }
            };
    public int baseRevisionCode;
    public boolean debuggable;
    public int installLocation;
    public boolean isSdkLibrary;
    public boolean multiArch;
    public String packageName;
    public int recommendedInstallLocation;
    public String[] splitNames;
    public int[] splitRevisionCodes;
    public VerifierInfo[] verifiers;

    @Deprecated public int versionCode;
    public int versionCodeMajor;

    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public PackageInfoLite() {}

    public String toString() {
        return "PackageInfoLite{"
                + Integer.toHexString(System.identityHashCode(this))
                + " "
                + this.packageName
                + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeStringArray(this.splitNames);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeInt(this.baseRevisionCode);
        parcel.writeIntArray(this.splitRevisionCodes);
        parcel.writeInt(this.recommendedInstallLocation);
        parcel.writeInt(this.installLocation);
        parcel.writeInt(this.multiArch ? 1 : 0);
        parcel.writeInt(this.debuggable ? 1 : 0);
        if (this.verifiers == null || this.verifiers.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(this.verifiers.length);
            parcel.writeTypedArray(this.verifiers, i);
        }
    }

    private PackageInfoLite(Parcel source) {
        this.packageName = source.readString();
        this.splitNames = source.createStringArray();
        this.versionCode = source.readInt();
        this.versionCodeMajor = source.readInt();
        this.baseRevisionCode = source.readInt();
        this.splitRevisionCodes = source.createIntArray();
        this.recommendedInstallLocation = source.readInt();
        this.installLocation = source.readInt();
        this.multiArch = source.readInt() != 0;
        this.debuggable = source.readInt() != 0;
        int verifiersLength = source.readInt();
        if (verifiersLength == 0) {
            this.verifiers = new VerifierInfo[0];
        } else {
            this.verifiers = new VerifierInfo[verifiersLength];
            source.readTypedArray(this.verifiers, VerifierInfo.CREATOR);
        }
    }
}
