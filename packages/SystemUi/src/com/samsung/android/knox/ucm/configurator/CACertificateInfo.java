package com.samsung.android.knox.ucm.configurator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CACertificateInfo implements Parcelable {
    public static final Parcelable.Creator<CACertificateInfo> CREATOR = new Parcelable.Creator<CACertificateInfo>() { // from class: com.samsung.android.knox.ucm.configurator.CACertificateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CACertificateInfo createFromParcel(Parcel parcel) {
            return new CACertificateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CACertificateInfo[] newArray(int i) {
            return new CACertificateInfo[i];
        }

        @Override // android.os.Parcelable.Creator
        public final CACertificateInfo createFromParcel(Parcel parcel) {
            return new CACertificateInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final CACertificateInfo[] newArray(int i) {
            return new CACertificateInfo[i];
        }
    };
    public int certLength;
    public byte[] certificate = null;
    public Bundle bundle = null;

    public CACertificateInfo() {
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.bundle = (Bundle) parcel.readParcelable(Bundle.class.getClassLoader());
        int readInt = parcel.readInt();
        this.certLength = readInt;
        if (readInt > 0) {
            byte[] bArr = new byte[readInt];
            this.certificate = bArr;
            parcel.readByteArray(bArr);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
