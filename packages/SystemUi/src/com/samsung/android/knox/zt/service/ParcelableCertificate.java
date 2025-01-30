package com.samsung.android.knox.zt.service;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.Certificate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ParcelableCertificate implements Parcelable {
    public static final Parcelable.Creator<ParcelableCertificate> CREATOR = new Parcelable.Creator<ParcelableCertificate>() { // from class: com.samsung.android.knox.zt.service.ParcelableCertificate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ParcelableCertificate createFromParcel(Parcel parcel) {
            return new ParcelableCertificate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ParcelableCertificate[] newArray(int i) {
            return new ParcelableCertificate[i];
        }

        @Override // android.os.Parcelable.Creator
        public final ParcelableCertificate createFromParcel(Parcel parcel) {
            return new ParcelableCertificate(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final ParcelableCertificate[] newArray(int i) {
            return new ParcelableCertificate[i];
        }
    };
    public Certificate mCertificate;

    public ParcelableCertificate(Certificate certificate) {
        this.mCertificate = certificate;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final Certificate getCertificate() {
        return this.mCertificate;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this.mCertificate);
            objectOutputStream.flush();
            objectOutputStream.close();
            parcel.writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParcelableCertificate(Parcel parcel) {
        try {
            this.mCertificate = (Certificate) new ObjectInputStream(new ByteArrayInputStream(parcel.createByteArray())).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
