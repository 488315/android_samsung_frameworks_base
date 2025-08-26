package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes4.dex */
public class SCEPProfile extends EnrollmentProfile implements Parcelable {
    public int challengeLength;
    public byte[] challengePassword;
    public String scepProfileName;
    public String scepUrl;
    public String subjectAlternativeName;
    public String subjectName;
    public long validitytimeForChallenge;

    public SCEPProfile(Parcel parcel) {
        this.profileType = parcel.readString();
        try {
            this.scepUrl = parcel.readString();
            this.scepProfileName = parcel.readString();
            int i = parcel.readInt();
            this.challengeLength = i;
            byte[] bArr = new byte[i];
            this.challengePassword = bArr;
            parcel.readByteArray(bArr);
            this.subjectName = parcel.readString();
            this.validitytimeForChallenge = parcel.readLong();
            this.keySize = parcel.readInt();
            this.keyPairAlgorithm = parcel.readString();
            this.subjectAlternativeName = parcel.readString();
            this.certificateAlias = parcel.readString();
            this.keystoreType = parcel.readString();
            this.credentialStorageBundle = parcel.readBundle();
            this.hashAlgorithmType = parcel.readString();
            this.csrExtra = parcel.readBundle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.keystore.EnrollmentProfile
    public String getProfileType() {
        return this.profileType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getClass().getName());
        parcel.writeString(CEPConstants.CERT_PROFILE_TYPE_SCEP);
        parcel.writeString(this.scepUrl);
        parcel.writeString(this.scepProfileName);
        parcel.writeInt(this.challengePassword.length);
        parcel.writeByteArray(this.challengePassword);
        parcel.writeString(this.subjectName);
        parcel.writeLong(this.validitytimeForChallenge);
        parcel.writeInt(this.keySize);
        parcel.writeString(this.keyPairAlgorithm);
        parcel.writeString(this.subjectAlternativeName);
        parcel.writeString(this.certificateAlias);
        parcel.writeString(this.keystoreType);
        parcel.writeBundle(this.credentialStorageBundle);
        parcel.writeString(this.hashAlgorithmType);
        parcel.writeBundle(this.csrExtra);
    }

    public SCEPProfile() {
    }
}
