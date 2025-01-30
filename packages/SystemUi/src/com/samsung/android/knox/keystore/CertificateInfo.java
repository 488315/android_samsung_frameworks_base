package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.Key;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CertificateInfo implements Parcelable {
    public static final Parcelable.Creator<CertificateInfo> CREATOR = new Parcelable.Creator<CertificateInfo>() { // from class: com.samsung.android.knox.keystore.CertificateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CertificateInfo createFromParcel(Parcel parcel) {
            return new CertificateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CertificateInfo[] newArray(int i) {
            return new CertificateInfo[i];
        }

        @Override // android.os.Parcelable.Creator
        public final CertificateInfo createFromParcel(Parcel parcel) {
            return new CertificateInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final CertificateInfo[] newArray(int i) {
            return new CertificateInfo[i];
        }
    };
    public String mAlias;
    public Certificate mCertificate;
    public boolean mEnabled;
    public boolean mHasPrivateKey;
    public Key mKey;
    public int mKeystore;
    public boolean mSystemPreloaded;

    public CertificateInfo() {
        this.mCertificate = null;
        this.mKey = null;
        this.mAlias = "";
        this.mKeystore = -1;
        this.mSystemPreloaded = false;
        this.mEnabled = true;
        this.mHasPrivateKey = false;
    }

    public final boolean compareKeys(Key key, Key key2) {
        if (key == key2) {
            return true;
        }
        if (key == null || key2 == null) {
            return false;
        }
        return Arrays.equals(key.getEncoded(), key2.getEncoded());
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof CertificateInfo)) {
            CertificateInfo certificateInfo = (CertificateInfo) obj;
            Certificate certificate = this.mCertificate;
            if (certificate != null && certificate.equals(certificateInfo.mCertificate) && compareKeys(this.mKey, certificateInfo.mKey)) {
                return true;
            }
        }
        return super.equals(obj);
    }

    public final String getAlias() {
        return this.mAlias;
    }

    public final Certificate getCertificate() {
        return this.mCertificate;
    }

    public final boolean getEnabled() {
        return this.mEnabled;
    }

    public final boolean getHasPrivateKey() {
        return this.mHasPrivateKey;
    }

    public final int getKeystore() {
        return this.mKeystore;
    }

    public final Key getPrivateKey() {
        return this.mKey;
    }

    public final String getSubject() {
        Certificate certificate = this.mCertificate;
        if (certificate instanceof X509Certificate) {
            return ((X509Certificate) certificate).getSubjectDN().getName();
        }
        return null;
    }

    public final boolean getSystemPreloaded() {
        return this.mSystemPreloaded;
    }

    public final void readFromParcel(Parcel parcel) {
        this.mCertificate = (Certificate) parcel.readSerializable();
        this.mKey = (Key) parcel.readSerializable();
        this.mAlias = (String) parcel.readSerializable();
        this.mKeystore = ((Integer) parcel.readSerializable()).intValue();
        this.mSystemPreloaded = ((Boolean) parcel.readSerializable()).booleanValue();
        this.mEnabled = ((Boolean) parcel.readSerializable()).booleanValue();
        this.mHasPrivateKey = ((Boolean) parcel.readSerializable()).booleanValue();
    }

    public final void setAlias(String str) {
        this.mAlias = str;
    }

    public final void setCertificate(Certificate certificate) {
        this.mCertificate = certificate;
    }

    public final void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public final void setHasPrivateKey(boolean z) {
        this.mHasPrivateKey = z;
    }

    public final void setKeystore(int i) {
        this.mKeystore = i;
    }

    public final void setPrivateKey(Key key) {
        this.mKey = key;
    }

    public final void setSystemPreloaded(boolean z) {
        this.mSystemPreloaded = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeSerializable(this.mCertificate);
            parcel.writeSerializable(this.mKey);
            parcel.writeSerializable(this.mAlias);
            parcel.writeSerializable(Integer.valueOf(this.mKeystore));
            parcel.writeSerializable(Boolean.valueOf(this.mSystemPreloaded));
            parcel.writeSerializable(Boolean.valueOf(this.mEnabled));
            parcel.writeSerializable(Boolean.valueOf(this.mHasPrivateKey));
        }
    }

    public CertificateInfo(Parcel parcel) {
        this.mCertificate = null;
        this.mKey = null;
        this.mAlias = "";
        this.mKeystore = -1;
        this.mSystemPreloaded = false;
        this.mEnabled = true;
        this.mHasPrivateKey = false;
        readFromParcel(parcel);
    }

    public CertificateInfo(Certificate certificate) {
        this.mKey = null;
        this.mAlias = "";
        this.mKeystore = -1;
        this.mSystemPreloaded = false;
        this.mEnabled = true;
        this.mHasPrivateKey = false;
        this.mCertificate = certificate;
    }
}
