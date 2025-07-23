package com.samsung.android.knox.mtd;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class PackageInfo implements Parcelable {
    public static final Parcelable.Creator<PackageInfo> CREATOR = new Parcelable.Creator<PackageInfo>() { // from class: com.samsung.android.knox.mtd.PackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfo createFromParcel(Parcel parcel) {
            return new PackageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageInfo[] newArray(int i) {
            return new PackageInfo[i];
        }
    };
    private String category;
    private String packageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PackageInfo(String str, String str2) {
        this.packageName = str;
        this.category = str2;
    }

    protected PackageInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.category = parcel.readString();
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public String getPackageName() {
        return this.packageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.category);
    }

    public String toString() {
        return this.packageName + ", " + this.category;
    }
}
