package com.samsung.android.knox.mtd;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class PackageInfo implements Parcelable {
    public static final Parcelable.Creator<PackageInfo> CREATOR =
            new Parcelable.Creator<
                    PackageInfo>() { // from class: com.samsung.android.knox.mtd.PackageInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PackageInfo createFromParcel(Parcel in) {
                    return new PackageInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PackageInfo[] newArray(int size) {
                    return new PackageInfo[size];
                }
            };
    private String category;
    private String packageName;

    public PackageInfo(String packageName, String category) {
        this.packageName = packageName;
        this.category = category;
    }

    protected PackageInfo(Parcel in) {
        this.packageName = in.readString();
        this.category = in.readString();
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPackageName() {
        return this.packageName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.category);
    }

    public String toString() {
        return this.packageName + ", " + this.category;
    }
}
