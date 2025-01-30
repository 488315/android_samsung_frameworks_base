package com.samsung.android.knox.keystore;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes3.dex */
public abstract class EnrollmentProfile implements Parcelable {
    public static final Parcelable.Creator<EnrollmentProfile> CREATOR = new Parcelable.Creator<EnrollmentProfile>() { // from class: com.samsung.android.knox.keystore.EnrollmentProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnrollmentProfile[] newArray(int i) {
            return new EnrollmentProfile[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnrollmentProfile createFromParcel(Parcel parcel) {
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

        @Override // android.os.Parcelable.Creator
        public final EnrollmentProfile[] newArray(int i) {
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

    public final String getCertificateAlias() {
        return this.certificateAlias;
    }

    public final String getKeyPairAlgorithm() {
        return this.keyPairAlgorithm;
    }

    public final int getKeySize() {
        return this.keySize;
    }

    public final String getKeystoreType() {
        return this.keystoreType;
    }

    public abstract String getProfileType();

    public final void setCertificateAlias(String str) {
        this.certificateAlias = str;
    }

    public final void setKeyPairAlgorithm(String str) {
        this.keyPairAlgorithm = str;
    }

    public final void setKeySize(int i) {
        this.keySize = i;
    }

    public final void setKeystoreType(String str) {
        this.keystoreType = str;
    }
}
