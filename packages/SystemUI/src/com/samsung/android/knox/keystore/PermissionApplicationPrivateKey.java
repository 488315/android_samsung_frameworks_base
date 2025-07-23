package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PermissionApplicationPrivateKey implements Parcelable {
    public static final Parcelable.Creator<PermissionApplicationPrivateKey> CREATOR = new Parcelable.Creator<PermissionApplicationPrivateKey>() { // from class: com.samsung.android.knox.keystore.PermissionApplicationPrivateKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionApplicationPrivateKey createFromParcel(Parcel parcel) {
            return new PermissionApplicationPrivateKey(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionApplicationPrivateKey[] newArray(int i) {
            return new PermissionApplicationPrivateKey[i];
        }
    };
    public String mAdminPkgName;
    public String mAlias;
    public String mHost;
    public String mPackageName;
    public int mPort;
    public String mStorageName;

    public /* synthetic */ PermissionApplicationPrivateKey(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAdminPkgName() {
        return this.mAdminPkgName;
    }

    public String getAlias() {
        return this.mAlias;
    }

    public String getHost() {
        return this.mHost;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getPort() {
        return this.mPort;
    }

    public String getStorageName() {
        return this.mStorageName;
    }

    public void readFromParcel(Parcel parcel) {
        this.mAdminPkgName = parcel.readString();
        this.mPackageName = parcel.readString();
        this.mHost = parcel.readString();
        this.mPort = parcel.readInt();
        this.mAlias = parcel.readString();
        this.mStorageName = parcel.readString();
    }

    public void setAdminPkgName(String str) {
        this.mAdminPkgName = str;
    }

    public String toString() {
        return "PermissionApplicationPrivateKey\nmAdminPackageName: " + this.mAdminPkgName + "\nmPackageName: " + this.mPackageName + "\nmHost: " + this.mHost + "\nmPort: " + this.mPort + "\nmAlias: " + this.mAlias + "\nmStorageName: " + this.mStorageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAdminPkgName);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mHost);
        parcel.writeInt(this.mPort);
        parcel.writeString(this.mAlias);
        parcel.writeString(this.mStorageName);
    }

    public PermissionApplicationPrivateKey(String str, String str2, int i, String str3) {
        this(str, str2, i, str3, null);
    }

    public PermissionApplicationPrivateKey(String str, String str2, int i, String str3, String str4) {
        this.mAdminPkgName = null;
        this.mPackageName = str;
        this.mHost = str2;
        this.mPort = i;
        this.mAlias = str3;
        this.mStorageName = str4;
    }

    private PermissionApplicationPrivateKey(Parcel parcel) {
        this.mAdminPkgName = null;
        this.mPackageName = null;
        this.mHost = null;
        this.mPort = -1;
        this.mAlias = null;
        this.mStorageName = null;
        readFromParcel(parcel);
    }
}
