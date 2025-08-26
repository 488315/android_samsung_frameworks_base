package com.samsung.android.knox.license;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class LicenseInfo implements Parcelable {
    public static final Parcelable.Creator<LicenseInfo> CREATOR = new Parcelable.Creator<LicenseInfo>() { // from class: com.samsung.android.knox.license.LicenseInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LicenseInfo createFromParcel(Parcel parcel) {
            return new LicenseInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LicenseInfo[] newArray(int i) {
            return new LicenseInfo[i];
        }
    };
    private String instanceId;
    private String packageName;
    private String packageVersion;

    public /* synthetic */ LicenseInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPackageVersion() {
        return this.packageVersion;
    }

    public void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        this.instanceId = parcel.readString();
        this.packageVersion = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.instanceId);
        parcel.writeString(this.packageVersion);
    }

    public LicenseInfo(String str, String str2, String str3) {
        this.packageName = str;
        this.instanceId = str2;
        this.packageVersion = str3;
    }

    private LicenseInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public LicenseInfo() {
    }
}
