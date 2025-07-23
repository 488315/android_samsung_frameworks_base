package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AppIdentity implements Parcelable, Serializable {
    public static final Parcelable.Creator<AppIdentity> CREATOR = new Parcelable.Creator<AppIdentity>() { // from class: com.samsung.android.knox.AppIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppIdentity createFromParcel(Parcel parcel) {
            return new AppIdentity(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppIdentity[] newArray(int i) {
            return new AppIdentity[i];
        }
    };
    private static final long serialVersionUID = 1;
    private String packageName;
    private String signature;

    public /* synthetic */ AppIdentity(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getSignature() {
        return this.signature;
    }

    public final void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        this.signature = parcel.readString();
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.signature);
    }

    public AppIdentity() {
    }

    public AppIdentity(String str, String str2) {
        this.packageName = str;
        this.signature = str2;
    }

    private AppIdentity(Parcel parcel) {
        readFromParcel(parcel);
    }
}
