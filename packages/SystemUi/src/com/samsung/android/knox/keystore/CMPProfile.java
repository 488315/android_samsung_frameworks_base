package com.samsung.android.knox.keystore;

import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes3.dex */
public final class CMPProfile extends EnrollmentProfile {
    public static final int CMP_HTTP = 0;
    public static final int CMP_POP_CHALLENGERESP = 1;
    public static final int CMP_POP_INDIRECTENCRYPTCERT = 2;
    public static final int CMP_POP_SIGNATURE = 0;
    public static final int CMP_TCP = 1;
    public String cmpServerURL;
    public long iakLength;
    public byte[] initialAuthenticationKey;
    public String issuerDN;
    public int keyUsage;
    public long notAfterDate;
    public long notBeforeDate;
    public int popType;
    public String subjectDN;
    public int transport;
    public String userName;

    public CMPProfile() {
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.keystore.EnrollmentProfile
    public final String getProfileType() {
        return this.profileType;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(CMPProfile.class.getName());
        parcel.writeString(this.profileType);
        parcel.writeString(this.cmpServerURL);
        parcel.writeString(this.userName);
        parcel.writeLong(this.iakLength);
        parcel.writeByteArray(this.initialAuthenticationKey);
        parcel.writeString(this.subjectDN);
        parcel.writeString(this.issuerDN);
        parcel.writeInt(this.keySize);
        parcel.writeString(this.keyPairAlgorithm);
        parcel.writeInt(this.popType);
        parcel.writeInt(this.keyUsage);
        parcel.writeInt(this.transport);
        parcel.writeLong(this.notBeforeDate);
        parcel.writeLong(this.notAfterDate);
        parcel.writeString(this.certificateAlias);
        parcel.writeString(this.keystoreType);
        parcel.writeBundle(this.credentialStorageBundle);
        parcel.writeString(this.hashAlgorithmType);
    }

    public CMPProfile(String str, String str2, byte[] bArr, long j, String str3, String str4, int i, String str5, int i2, int i3, int i4, long j2, long j3, String str6, String str7) {
        this.profileType = CEPConstants.CERT_PROFILE_TYPE_CMP;
        this.cmpServerURL = str;
        this.userName = str2;
        this.initialAuthenticationKey = bArr;
        this.iakLength = j;
        this.subjectDN = str3;
        this.issuerDN = str4;
        this.keySize = i;
        this.keyPairAlgorithm = str5;
        this.popType = i2;
        this.keyUsage = i3;
        this.transport = i4;
        this.notBeforeDate = j2;
        this.notAfterDate = j3;
        this.certificateAlias = str6;
        this.keystoreType = str7;
    }

    public CMPProfile(Parcel parcel) {
        this.profileType = parcel.readString();
        this.cmpServerURL = parcel.readString();
        this.userName = parcel.readString();
        long readLong = parcel.readLong();
        this.iakLength = readLong;
        byte[] bArr = new byte[(int) readLong];
        this.initialAuthenticationKey = bArr;
        parcel.readByteArray(bArr);
        this.subjectDN = parcel.readString();
        this.issuerDN = parcel.readString();
        this.keySize = parcel.readInt();
        this.keyPairAlgorithm = parcel.readString();
        this.popType = parcel.readInt();
        this.keyUsage = parcel.readInt();
        this.transport = parcel.readInt();
        this.notBeforeDate = parcel.readLong();
        this.notAfterDate = parcel.readLong();
        this.certificateAlias = parcel.readString();
        this.keystoreType = parcel.readString();
        this.credentialStorageBundle = parcel.readBundle();
        this.hashAlgorithmType = parcel.readString();
    }
}
