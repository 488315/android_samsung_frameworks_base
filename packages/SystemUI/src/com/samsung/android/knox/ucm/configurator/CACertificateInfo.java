package com.samsung.android.knox.ucm.configurator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class CACertificateInfo implements Parcelable {
    public static final Parcelable.Creator<CACertificateInfo> CREATOR = new Parcelable.Creator<CACertificateInfo>() { // from class: com.samsung.android.knox.ucm.configurator.CACertificateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CACertificateInfo createFromParcel(Parcel parcel) {
            return new CACertificateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CACertificateInfo[] newArray(int i) {
            return new CACertificateInfo[i];
        }
    };
    public int certLength;
    public byte[] certificate = null;
    public Bundle bundle = null;

    public CACertificateInfo() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.bundle = (Bundle) parcel.readParcelable(Bundle.class.getClassLoader());
        int i = parcel.readInt();
        this.certLength = i;
        if (i > 0) {
            byte[] bArr = new byte[i];
            this.certificate = bArr;
            parcel.readByteArray(bArr);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeParcelable(this.bundle, i);
            parcel.writeInt(this.certLength);
            if (this.certLength > 0) {
                parcel.writeByteArray(this.certificate);
            }
        }
    }

    public CACertificateInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
