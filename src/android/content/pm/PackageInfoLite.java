package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class PackageInfoLite implements Parcelable {
    public static final Parcelable.Creator<PackageInfoLite> CREATOR = new Parcelable.Creator<PackageInfoLite>() { // from class: android.content.pm.PackageInfoLite.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfoLite createFromParcel(Parcel parcel) {
            return new PackageInfoLite(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfoLite[] newArray(int i) {
            return new PackageInfoLite[i];
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

    @Deprecated
    public int versionCode;
    public int versionCodeMajor;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public PackageInfoLite() {
    }

    public String toString() {
        return "PackageInfoLite{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
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
        VerifierInfo[] verifierInfoArr = this.verifiers;
        if (verifierInfoArr == null || verifierInfoArr.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(verifierInfoArr.length);
            parcel.writeTypedArray(this.verifiers, i);
        }
    }

    private PackageInfoLite(Parcel parcel) {
        this.packageName = parcel.readString();
        this.splitNames = parcel.createStringArray();
        this.versionCode = parcel.readInt();
        this.versionCodeMajor = parcel.readInt();
        this.baseRevisionCode = parcel.readInt();
        this.splitRevisionCodes = parcel.createIntArray();
        this.recommendedInstallLocation = parcel.readInt();
        this.installLocation = parcel.readInt();
        this.multiArch = parcel.readInt() != 0;
        this.debuggable = parcel.readInt() != 0;
        int readInt = parcel.readInt();
        if (readInt == 0) {
            this.verifiers = new VerifierInfo[0];
            return;
        }
        VerifierInfo[] verifierInfoArr = new VerifierInfo[readInt];
        this.verifiers = verifierInfoArr;
        parcel.readTypedArray(verifierInfoArr, VerifierInfo.CREATOR);
    }
}
