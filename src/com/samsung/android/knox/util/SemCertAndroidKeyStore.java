package com.samsung.android.knox.util;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.cert.Certificate;

/* loaded from: classes6.dex */
public class SemCertAndroidKeyStore implements Parcelable {

    @Deprecated(forRemoval = true, since = "16.0")
    public static final Parcelable.Creator<SemCertAndroidKeyStore> CREATOR = new Parcelable.Creator<SemCertAndroidKeyStore>() { // from class: com.samsung.android.knox.util.SemCertAndroidKeyStore.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCertAndroidKeyStore[] newArray(int i) {
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCertAndroidKeyStore createFromParcel(Parcel parcel) {
            return new SemCertAndroidKeyStore(parcel);
        }
    };
    public Certificate[] certs;

    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public int describeContents() {
        return 0;
    }

    public SemCertAndroidKeyStore() {
    }

    public SemCertAndroidKeyStore(Parcel parcel) {
        this.certs = (Certificate[]) parcel.readSerializable();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.Serializable, java.security.cert.Certificate[]] */
    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(this.certs);
    }
}
