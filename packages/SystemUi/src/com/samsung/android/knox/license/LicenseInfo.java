package com.samsung.android.knox.license;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LicenseInfo implements Parcelable {
    public static final Parcelable.Creator<LicenseInfo> CREATOR = new Parcelable.Creator<LicenseInfo>() { // from class: com.samsung.android.knox.license.LicenseInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LicenseInfo[] newArray(int i) {
            return new LicenseInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LicenseInfo createFromParcel(Parcel parcel) {
            return new LicenseInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final LicenseInfo[] newArray(int i) {
            return new LicenseInfo[i];
        }
    };
    public String instanceId;
    public String packageName;
    public String packageVersion;

    public /* synthetic */ LicenseInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String getInstanceId() {
        return this.instanceId;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getPackageVersion() {
        return this.packageVersion;
    }

    public final void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        this.instanceId = parcel.readString();
        this.packageVersion = parcel.readString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
