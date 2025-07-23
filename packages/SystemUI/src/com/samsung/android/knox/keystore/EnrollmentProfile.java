package com.samsung.android.knox.keystore;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Deprecated
/* loaded from: classes4.dex */
public abstract class EnrollmentProfile implements Parcelable {
    public static final Parcelable.Creator<EnrollmentProfile> CREATOR = new Parcelable.Creator<EnrollmentProfile>() { // from class: com.samsung.android.knox.keystore.EnrollmentProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollmentProfile createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            if (readString.equals(SCEPProfile.class.getName())) {
                return new SCEPProfile(parcel);
            }
            if (readString.equals(CMCProfile.class.getName())) {
                return new CMCProfile(parcel);
            }
            if (readString.equals(CMPProfile.class.getName())) {
                return new CMPProfile(parcel);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollmentProfile[] newArray(int i) {
            return new EnrollmentProfile[i];
        }
    };
    public String certificateAlias;
    public Bundle credentialStorageBundle = null;
    public Bundle csrExtra = null;
    public String hashAlgorithmType;
    public String keyPairAlgorithm;
    public int keySize;
    public String keystoreType;
    public String profileType;

    public String getCertificateAlias() {
        return this.certificateAlias;
    }

    public String getKeyPairAlgorithm() {
        return this.keyPairAlgorithm;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public String getKeystoreType() {
        return this.keystoreType;
    }

    public abstract String getProfileType();

    public void setCertificateAlias(String str) {
        this.certificateAlias = str;
    }

    public void setKeyPairAlgorithm(String str) {
        this.keyPairAlgorithm = str;
    }

    public void setKeySize(int i) {
        this.keySize = i;
    }

    public void setKeystoreType(String str) {
        this.keystoreType = str;
    }
}
