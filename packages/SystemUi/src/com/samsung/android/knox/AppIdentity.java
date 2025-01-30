package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class AppIdentity implements Parcelable, Serializable {
    public static final Parcelable.Creator<AppIdentity> CREATOR = new Parcelable.Creator<AppIdentity>() { // from class: com.samsung.android.knox.AppIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AppIdentity[] newArray(int i) {
            return new AppIdentity[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AppIdentity createFromParcel(Parcel parcel) {
            return new AppIdentity(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final AppIdentity[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getSignature() {
        return this.signature;
    }

    public final void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        this.signature = parcel.readString();
    }

    public final void setPackageName(String str) {
        this.packageName = str;
    }

    public final void setSignature(String str) {
        this.signature = str;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
