package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.util.Collection;

/* loaded from: classes.dex */
public final class SigningInfo implements Parcelable {
    public static final Parcelable.Creator<SigningInfo> CREATOR = new Parcelable.Creator<SigningInfo>() { // from class: android.content.pm.SigningInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SigningInfo createFromParcel(Parcel parcel) {
            return new SigningInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SigningInfo[] newArray(int i) {
            return new SigningInfo[i];
        }
    };
    public static final int VERSION_JAR = 1;
    public static final int VERSION_SIGNING_BLOCK_V2 = 2;
    public static final int VERSION_SIGNING_BLOCK_V3 = 3;
    public static final int VERSION_SIGNING_BLOCK_V4 = 4;
    private final SigningDetails mSigningDetails;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppSigningSchemeVersion {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SigningInfo() {
        this.mSigningDetails = SigningDetails.UNKNOWN;
    }

    public SigningInfo(int i, Collection<Signature> collection, Collection<PublicKey> collection2, Collection<Signature> collection3) {
        if (i <= 0 || collection == null) {
            this.mSigningDetails = SigningDetails.UNKNOWN;
            return;
        }
        ArraySet arraySet = null;
        Signature[] signatureArr = (collection == null || collection.isEmpty()) ? null : (Signature[]) collection.toArray(new Signature[collection.size()]);
        Signature[] signatureArr2 = (collection3 == null || collection3.isEmpty()) ? null : (Signature[]) collection3.toArray(new Signature[collection3.size()]);
        signatureArr2 = Signature.areExactArraysMatch(signatureArr, signatureArr2) ? null : signatureArr2;
        if (collection2 != null && !collection2.isEmpty()) {
            arraySet = new ArraySet(collection2);
        }
        this.mSigningDetails = new SigningDetails(signatureArr, i, arraySet, signatureArr2);
    }

    public SigningInfo(SigningDetails signingDetails) {
        this.mSigningDetails = new SigningDetails(signingDetails);
    }

    public SigningInfo(SigningInfo signingInfo) {
        this.mSigningDetails = new SigningDetails(signingInfo.mSigningDetails);
    }

    private SigningInfo(Parcel parcel) {
        this.mSigningDetails = SigningDetails.CREATOR.createFromParcel(parcel);
    }

    public boolean hasMultipleSigners() {
        return this.mSigningDetails.getSignatures() != null && this.mSigningDetails.getSignatures().length > 1;
    }

    public boolean hasPastSigningCertificates() {
        return this.mSigningDetails.getPastSigningCertificates() != null && this.mSigningDetails.getPastSigningCertificates().length > 0;
    }

    public Signature[] getSigningCertificateHistory() {
        if (hasMultipleSigners()) {
            return null;
        }
        if (!hasPastSigningCertificates()) {
            return this.mSigningDetails.getSignatures();
        }
        return this.mSigningDetails.getPastSigningCertificates();
    }

    public Signature[] getApkContentsSigners() {
        return this.mSigningDetails.getSignatures();
    }

    public int getSchemeVersion() {
        return this.mSigningDetails.getSignatureSchemeVersion();
    }

    public Collection<PublicKey> getPublicKeys() {
        return this.mSigningDetails.getPublicKeys();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mSigningDetails.writeToParcel(parcel, i);
    }

    public SigningDetails getSigningDetails() {
        return this.mSigningDetails;
    }

    public boolean signersMatchExactly(SigningInfo signingInfo) {
        return this.mSigningDetails.signaturesMatchExactly(signingInfo.mSigningDetails);
    }
}
