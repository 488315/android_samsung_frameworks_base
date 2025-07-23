package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class InstallSourceInfo implements Parcelable {
    public static final Parcelable.Creator<InstallSourceInfo> CREATOR = new Parcelable.Creator<InstallSourceInfo>() { // from class: android.content.pm.InstallSourceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallSourceInfo createFromParcel(Parcel parcel) {
            return new InstallSourceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallSourceInfo[] newArray(int i) {
            return new InstallSourceInfo[i];
        }
    };
    private final String mInitiatingPackageName;
    private final SigningInfo mInitiatingPackageSigningInfo;
    private final String mInstallingPackageName;
    private final String mOriginatingPackageName;
    private final int mPackageSource;
    private final String mUpdateOwnerPackageName;

    public InstallSourceInfo(String str, SigningInfo signingInfo, String str2, String str3) {
        this(str, signingInfo, str2, str3, null, 0);
    }

    public InstallSourceInfo(String str, SigningInfo signingInfo, String str2, String str3, String str4, int i) {
        this.mInitiatingPackageName = str;
        this.mInitiatingPackageSigningInfo = signingInfo;
        this.mOriginatingPackageName = str2;
        this.mInstallingPackageName = str3;
        this.mUpdateOwnerPackageName = str4;
        this.mPackageSource = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        SigningInfo signingInfo = this.mInitiatingPackageSigningInfo;
        if (signingInfo == null) {
            return 0;
        }
        return signingInfo.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mInitiatingPackageName);
        parcel.writeParcelable(this.mInitiatingPackageSigningInfo, i);
        parcel.writeString(this.mOriginatingPackageName);
        parcel.writeString(this.mInstallingPackageName);
        parcel.writeString8(this.mUpdateOwnerPackageName);
        parcel.writeInt(this.mPackageSource);
    }

    private InstallSourceInfo(Parcel parcel) {
        this.mInitiatingPackageName = parcel.readString();
        this.mInitiatingPackageSigningInfo = (SigningInfo) parcel.readParcelable(SigningInfo.class.getClassLoader(), SigningInfo.class);
        this.mOriginatingPackageName = parcel.readString();
        this.mInstallingPackageName = parcel.readString();
        this.mUpdateOwnerPackageName = parcel.readString8();
        this.mPackageSource = parcel.readInt();
    }

    public String getInitiatingPackageName() {
        return this.mInitiatingPackageName;
    }

    public SigningInfo getInitiatingPackageSigningInfo() {
        return this.mInitiatingPackageSigningInfo;
    }

    public String getOriginatingPackageName() {
        return this.mOriginatingPackageName;
    }

    public String getInstallingPackageName() {
        return this.mInstallingPackageName;
    }

    public String getUpdateOwnerPackageName() {
        return this.mUpdateOwnerPackageName;
    }

    public int getPackageSource() {
        return this.mPackageSource;
    }
}
