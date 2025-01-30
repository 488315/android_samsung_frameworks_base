package com.samsung.android.knox.ucm.configurator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CredentialStorage implements Parcelable {
    public static final Parcelable.Creator<CredentialStorage> CREATOR = new Parcelable.Creator<CredentialStorage>() { // from class: com.samsung.android.knox.ucm.configurator.CredentialStorage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CredentialStorage[] newArray(int i) {
            return new CredentialStorage[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CredentialStorage createFromParcel(Parcel parcel) {
            return new CredentialStorage(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final CredentialStorage[] newArray(int i) {
            return new CredentialStorage[i];
        }
    };
    public Bundle bundle;
    public String manufacturer;
    public String name;
    public String packageName;
    public String signature;

    public /* synthetic */ CredentialStorage(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.manufacturer);
        parcel.writeString(this.packageName);
        parcel.writeParcelable(this.bundle, i);
        parcel.writeString(this.signature);
    }

    public CredentialStorage() {
        this.name = null;
        this.manufacturer = null;
        this.packageName = null;
        this.bundle = null;
    }

    private CredentialStorage(Parcel parcel) {
        this.name = null;
        this.manufacturer = null;
        this.packageName = null;
        this.bundle = null;
        this.name = parcel.readString();
        this.manufacturer = parcel.readString();
        this.packageName = parcel.readString();
        this.bundle = (Bundle) parcel.readParcelable(Bundle.class.getClassLoader());
        this.signature = parcel.readString();
    }
}
