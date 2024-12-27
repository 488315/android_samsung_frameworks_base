package com.samsung.android.knox.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.cert.Certificate;

public class SemCertAndroidKeyStore implements Parcelable {

    @Deprecated(forRemoval = true, since = "16.0")
    public static final Parcelable.Creator<SemCertAndroidKeyStore> CREATOR =
            new Parcelable.Creator<
                    SemCertAndroidKeyStore>() { // from class:
                                                // com.samsung.android.knox.util.SemCertAndroidKeyStore.1
                @Override // android.os.Parcelable.Creator
                public SemCertAndroidKeyStore createFromParcel(Parcel source) {
                    return new SemCertAndroidKeyStore(source);
                }

                @Override // android.os.Parcelable.Creator
                public SemCertAndroidKeyStore[] newArray(int size) {
                    return null;
                }
            };

    public Certificate[] certs;

    public SemCertAndroidKeyStore() {}

    public SemCertAndroidKeyStore(Parcel source) {
        this.certs = (Certificate[]) source.readSerializable();
    }

    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeSerializable(this.certs);
    }
}
