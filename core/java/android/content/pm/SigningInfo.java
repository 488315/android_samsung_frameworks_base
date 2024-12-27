package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.security.PublicKey;
import java.util.Collection;

/* loaded from: classes.dex */
public final class SigningInfo implements Parcelable {
    public static final Parcelable.Creator<SigningInfo> CREATOR = new Parcelable.Creator<SigningInfo>() { // from class: android.content.pm.SigningInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SigningInfo createFromParcel(Parcel source) {
            return new SigningInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SigningInfo[] newArray(int size) {
            return new SigningInfo[size];
        }
    };
    private final SigningDetails mSigningDetails;

    public SigningInfo() {
        this.mSigningDetails = SigningDetails.UNKNOWN;
    }

    public SigningInfo(int schemeVersion, Collection<Signature> apkContentsSigners, Collection<PublicKey> publicKeys, Collection<Signature> signingCertificateHistory) {
        Signature[] signatures;
        Signature[] pastSignatures;
        if (schemeVersion <= 0 || apkContentsSigners == null) {
            this.mSigningDetails = SigningDetails.UNKNOWN;
            return;
        }
        ArraySet<PublicKey> keys = null;
        if (apkContentsSigners != null && !apkContentsSigners.isEmpty()) {
            signatures = (Signature[]) apkContentsSigners.toArray(new Signature[apkContentsSigners.size()]);
        } else {
            signatures = null;
        }
        if (signingCertificateHistory != null && !signingCertificateHistory.isEmpty()) {
            pastSignatures = (Signature[]) signingCertificateHistory.toArray(new Signature[signingCertificateHistory.size()]);
        } else {
            pastSignatures = null;
        }
        pastSignatures = Signature.areExactArraysMatch(signatures, pastSignatures) ? null : pastSignatures;
        if (publicKeys != null && !publicKeys.isEmpty()) {
            keys = new ArraySet<>(publicKeys);
        }
        this.mSigningDetails = new SigningDetails(signatures, schemeVersion, keys, pastSignatures);
    }

    public SigningInfo(SigningDetails signingDetails) {
        this.mSigningDetails = new SigningDetails(signingDetails);
    }

    public SigningInfo(SigningInfo orig) {
        this.mSigningDetails = new SigningDetails(orig.mSigningDetails);
    }

    private SigningInfo(Parcel source) {
        this.mSigningDetails = SigningDetails.CREATOR.createFromParcel(source);
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        this.mSigningDetails.writeToParcel(dest, parcelableFlags);
    }

    public SigningDetails getSigningDetails() {
        return this.mSigningDetails;
    }
}
