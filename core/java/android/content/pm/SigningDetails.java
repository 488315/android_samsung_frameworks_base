package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.PackageUtils;

import libcore.util.HexEncoding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Set;

public final class SigningDetails implements Parcelable {
    private static final int PAST_CERT_EXISTS = 0;
    private static final String TAG = "SigningDetails";
    private final Signature[] mPastSigningCertificates;
    private final ArraySet<PublicKey> mPublicKeys;
    private final int mSignatureSchemeVersion;
    private final Signature[] mSignatures;
    public static final SigningDetails UNKNOWN = new SigningDetails(null, 0, null, null);
    public static final Parcelable.Creator<SigningDetails> CREATOR =
            new Parcelable.Creator<
                    SigningDetails>() { // from class: android.content.pm.SigningDetails.1
                @Override // android.os.Parcelable.Creator
                public SigningDetails createFromParcel(Parcel source) {
                    if (source.readBoolean()) {
                        return SigningDetails.UNKNOWN;
                    }
                    return new SigningDetails(source);
                }

                @Override // android.os.Parcelable.Creator
                public SigningDetails[] newArray(int size) {
                    return new SigningDetails[size];
                }
            };

    public @interface CapabilityMergeRule {
        public static final int MERGE_OTHER_CAPABILITY = 1;
        public static final int MERGE_RESTRICTED_CAPABILITY = 2;
        public static final int MERGE_SELF_CAPABILITY = 0;
    }

    public @interface CertCapabilities {
        public static final int AUTH = 16;
        public static final int INSTALLED_DATA = 1;
        public static final int PERMISSION = 4;
        public static final int ROLLBACK = 8;
        public static final int SHARED_USER_ID = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignatureSchemeVersion {
        public static final int JAR = 1;
        public static final int SIGNING_BLOCK_V2 = 2;
        public static final int SIGNING_BLOCK_V3 = 3;
        public static final int SIGNING_BLOCK_V4 = 4;
        public static final int UNKNOWN = 0;
    }

    public SigningDetails(
            Signature[] signatures,
            int signatureSchemeVersion,
            ArraySet<PublicKey> keys,
            Signature[] pastSigningCertificates) {
        this.mSignatures = signatures;
        this.mSignatureSchemeVersion = signatureSchemeVersion;
        this.mPublicKeys = keys;
        this.mPastSigningCertificates = pastSigningCertificates;
    }

    public SigningDetails(
            Signature[] signatures, int signatureSchemeVersion, Signature[] pastSigningCertificates)
            throws CertificateException {
        this(
                signatures,
                signatureSchemeVersion,
                toSigningKeys(signatures),
                pastSigningCertificates);
    }

    public SigningDetails(Signature[] signatures, int signatureSchemeVersion)
            throws CertificateException {
        this(signatures, signatureSchemeVersion, null);
    }

    public SigningDetails(SigningDetails orig) {
        if (orig != null) {
            if (orig.mSignatures != null) {
                this.mSignatures = (Signature[]) orig.mSignatures.clone();
            } else {
                this.mSignatures = null;
            }
            this.mSignatureSchemeVersion = orig.mSignatureSchemeVersion;
            this.mPublicKeys = new ArraySet<>((ArraySet) orig.mPublicKeys);
            if (orig.mPastSigningCertificates != null) {
                this.mPastSigningCertificates = (Signature[]) orig.mPastSigningCertificates.clone();
                return;
            } else {
                this.mPastSigningCertificates = null;
                return;
            }
        }
        this.mSignatures = null;
        this.mSignatureSchemeVersion = 0;
        this.mPublicKeys = null;
        this.mPastSigningCertificates = null;
    }

    public SigningDetails mergeLineageWith(SigningDetails otherSigningDetails) {
        return mergeLineageWith(otherSigningDetails, 1);
    }

    public SigningDetails mergeLineageWith(SigningDetails otherSigningDetails, int mergeRule) {
        if (!hasPastSigningCertificates()) {
            return (otherSigningDetails.hasPastSigningCertificates()
                            && otherSigningDetails.hasAncestorOrSelf(this))
                    ? otherSigningDetails
                    : this;
        }
        if (!otherSigningDetails.hasPastSigningCertificates()) {
            return this;
        }
        SigningDetails descendantSigningDetails = getDescendantOrSelf(otherSigningDetails);
        if (descendantSigningDetails == null) {
            return this;
        }
        if (descendantSigningDetails == this) {
            return mergeLineageWithAncestorOrSelf(otherSigningDetails, mergeRule);
        }
        switch (mergeRule) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0091, code lost:

       if (r7 < 0) goto L54;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0093, code lost:

       return r10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.SigningDetails mergeLineageWithAncestorOrSelf(
            android.content.pm.SigningDetails r11, int r12) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.content.pm.SigningDetails.mergeLineageWithAncestorOrSelf(android.content.pm.SigningDetails,"
                    + " int):android.content.pm.SigningDetails");
    }

    public boolean hasCommonAncestor(SigningDetails otherSigningDetails) {
        if (!hasPastSigningCertificates()) {
            return otherSigningDetails.hasAncestorOrSelf(this);
        }
        if (otherSigningDetails.hasPastSigningCertificates()) {
            return getDescendantOrSelf(otherSigningDetails) != null;
        }
        return hasAncestorOrSelf(otherSigningDetails);
    }

    public boolean hasAncestorOrSelfWithDigest(Set<String> certDigests) {
        if (this == UNKNOWN || certDigests == null || certDigests.size() == 0) {
            return false;
        }
        if (this.mSignatures.length > 1) {
            if (certDigests.size() < this.mSignatures.length) {
                return false;
            }
            for (Signature signature : this.mSignatures) {
                String signatureDigest = PackageUtils.computeSha256Digest(signature.toByteArray());
                if (!certDigests.contains(signatureDigest)) {
                    return false;
                }
            }
            return true;
        }
        String signatureDigest2 =
                PackageUtils.computeSha256Digest(this.mSignatures[0].toByteArray());
        if (certDigests.contains(signatureDigest2)) {
            return true;
        }
        if (hasPastSigningCertificates()) {
            for (int i = 0; i < this.mPastSigningCertificates.length - 1; i++) {
                String signatureDigest3 =
                        PackageUtils.computeSha256Digest(
                                this.mPastSigningCertificates[i].toByteArray());
                if (certDigests.contains(signatureDigest3)) {
                    return true;
                }
            }
        }
        return false;
    }

    private SigningDetails getDescendantOrSelf(SigningDetails otherSigningDetails) {
        SigningDetails descendantSigningDetails;
        SigningDetails ancestorSigningDetails;
        if (hasAncestorOrSelf(otherSigningDetails)) {
            descendantSigningDetails = this;
            ancestorSigningDetails = otherSigningDetails;
        } else {
            if (!otherSigningDetails.hasAncestor(this)) {
                return null;
            }
            descendantSigningDetails = otherSigningDetails;
            ancestorSigningDetails = this;
        }
        int descendantIndex = descendantSigningDetails.mPastSigningCertificates.length - 1;
        int ancestorIndex = ancestorSigningDetails.mPastSigningCertificates.length - 1;
        while (descendantIndex >= 0
                && !descendantSigningDetails.mPastSigningCertificates[descendantIndex].equals(
                        ancestorSigningDetails.mPastSigningCertificates[ancestorIndex])) {
            descendantIndex--;
        }
        if (descendantIndex < 0) {
            return null;
        }
        do {
            descendantIndex--;
            ancestorIndex--;
            if (descendantIndex < 0 || ancestorIndex < 0) {
                break;
            }
        } while (descendantSigningDetails.mPastSigningCertificates[descendantIndex].equals(
                ancestorSigningDetails.mPastSigningCertificates[ancestorIndex]));
        if (descendantIndex < 0 || ancestorIndex < 0) {
            return descendantSigningDetails;
        }
        return null;
    }

    public boolean hasSignatures() {
        return this.mSignatures != null && this.mSignatures.length > 0;
    }

    public boolean hasPastSigningCertificates() {
        return this.mPastSigningCertificates != null && this.mPastSigningCertificates.length > 0;
    }

    public boolean hasAncestorOrSelf(SigningDetails oldDetails) {
        if (this == UNKNOWN || oldDetails == UNKNOWN) {
            return false;
        }
        if (oldDetails.mSignatures.length > 1) {
            return signaturesMatchExactly(oldDetails);
        }
        if (oldDetails.mSignatures.length == 0) {
            return false;
        }
        return hasCertificate(oldDetails.mSignatures[0]);
    }

    public boolean hasAncestor(SigningDetails oldDetails) {
        if (this != UNKNOWN
                && oldDetails != UNKNOWN
                && hasPastSigningCertificates()
                && oldDetails.mSignatures.length == 1) {
            for (int i = 0; i < this.mPastSigningCertificates.length - 1; i++) {
                if (this.mPastSigningCertificates[i].equals(oldDetails.mSignatures[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCommonSignerWithCapability(SigningDetails otherDetails, int flags) {
        if (this == UNKNOWN || otherDetails == UNKNOWN) {
            return false;
        }
        if (this.mSignatures.length > 1 || otherDetails.mSignatures.length > 1) {
            return signaturesMatchExactly(otherDetails);
        }
        Set<Signature> otherSignatures = new ArraySet<>();
        if (otherDetails.hasPastSigningCertificates()) {
            otherSignatures.addAll(Arrays.asList(otherDetails.mPastSigningCertificates));
        } else {
            otherSignatures.addAll(Arrays.asList(otherDetails.mSignatures));
        }
        if (otherSignatures.contains(this.mSignatures[0])) {
            return true;
        }
        if (hasPastSigningCertificates()) {
            for (int i = 0; i < this.mPastSigningCertificates.length - 1; i++) {
                if (otherSignatures.contains(this.mPastSigningCertificates[i])
                        && (this.mPastSigningCertificates[i].getFlags() & flags) == flags) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCapability(SigningDetails oldDetails, int flags) {
        if (this == UNKNOWN || oldDetails == UNKNOWN) {
            return false;
        }
        if (oldDetails.mSignatures.length > 1) {
            return signaturesMatchExactly(oldDetails);
        }
        if (oldDetails.mSignatures.length == 0) {
            return false;
        }
        return hasCertificate(oldDetails.mSignatures[0], flags);
    }

    public boolean checkCapabilityRecover(SigningDetails oldDetails, int flags)
            throws CertificateException {
        if (oldDetails == UNKNOWN || this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates() && oldDetails.mSignatures.length == 1) {
            for (int i = 0; i < this.mPastSigningCertificates.length; i++) {
                if (Signature.areEffectiveMatch(
                                oldDetails.mSignatures[0], this.mPastSigningCertificates[i])
                        && this.mPastSigningCertificates[i].getFlags() == flags) {
                    return true;
                }
            }
            return false;
        }
        return Signature.areEffectiveMatch(oldDetails, this);
    }

    public boolean hasCertificate(Signature signature) {
        return hasCertificateInternal(signature, 0);
    }

    public boolean hasCertificate(Signature signature, int flags) {
        return hasCertificateInternal(signature, flags);
    }

    public boolean hasCertificate(byte[] certificate) {
        Signature signature = new Signature(certificate);
        return hasCertificate(signature);
    }

    private boolean hasCertificateInternal(Signature signature, int flags) {
        if (this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates()) {
            for (int i = 0; i < this.mPastSigningCertificates.length - 1; i++) {
                if (this.mPastSigningCertificates[i].equals(signature)
                        && (flags == 0
                                || (this.mPastSigningCertificates[i].getFlags() & flags)
                                        == flags)) {
                    return true;
                }
            }
        }
        return this.mSignatures.length == 1 && this.mSignatures[0].equals(signature);
    }

    public boolean checkCapability(String sha256String, int flags) {
        if (this == UNKNOWN || TextUtils.isEmpty(sha256String)) {
            return false;
        }
        byte[] sha256Bytes = HexEncoding.decode(sha256String, false);
        if (hasSha256Certificate(sha256Bytes, flags)) {
            return true;
        }
        String[] mSignaturesSha256Digests =
                PackageUtils.computeSignaturesSha256Digests(this.mSignatures);
        String mSignaturesSha256Digest =
                PackageUtils.computeSignaturesSha256Digest(mSignaturesSha256Digests);
        return mSignaturesSha256Digest.equals(sha256String);
    }

    public boolean hasSha256Certificate(byte[] sha256Certificate) {
        return hasSha256CertificateInternal(sha256Certificate, 0);
    }

    public boolean hasSha256Certificate(byte[] sha256Certificate, int flags) {
        return hasSha256CertificateInternal(sha256Certificate, flags);
    }

    private boolean hasSha256CertificateInternal(byte[] sha256Certificate, int flags) {
        if (this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates()) {
            for (int i = 0; i < this.mPastSigningCertificates.length - 1; i++) {
                byte[] digest =
                        PackageUtils.computeSha256DigestBytes(
                                this.mPastSigningCertificates[i].toByteArray());
                if (Arrays.equals(sha256Certificate, digest)
                        && (flags == 0
                                || (this.mPastSigningCertificates[i].getFlags() & flags)
                                        == flags)) {
                    return true;
                }
            }
        }
        if (this.mSignatures.length != 1) {
            return false;
        }
        byte[] digest2 = PackageUtils.computeSha256DigestBytes(this.mSignatures[0].toByteArray());
        return Arrays.equals(sha256Certificate, digest2);
    }

    public boolean signaturesMatchExactly(SigningDetails other) {
        return Signature.areExactMatch(this, other);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        boolean isUnknown = UNKNOWN == this;
        dest.writeBoolean(isUnknown);
        if (isUnknown) {
            return;
        }
        dest.writeTypedArray(this.mSignatures, flags);
        dest.writeInt(this.mSignatureSchemeVersion);
        dest.writeArraySet(this.mPublicKeys);
        dest.writeTypedArray(this.mPastSigningCertificates, flags);
    }

    protected SigningDetails(Parcel in) {
        ClassLoader boot = Object.class.getClassLoader();
        this.mSignatures = (Signature[]) in.createTypedArray(Signature.CREATOR);
        this.mSignatureSchemeVersion = in.readInt();
        this.mPublicKeys = in.readArraySet(boot);
        this.mPastSigningCertificates = (Signature[]) in.createTypedArray(Signature.CREATOR);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SigningDetails)) {
            return false;
        }
        SigningDetails that = (SigningDetails) o;
        if (this.mSignatureSchemeVersion != that.mSignatureSchemeVersion
                || !Signature.areExactMatch(this, that)) {
            return false;
        }
        if (this.mPublicKeys != null) {
            if (!this.mPublicKeys.equals(that.mPublicKeys)) {
                return false;
            }
        } else if (that.mPublicKeys != null) {
            return false;
        }
        if (!Arrays.equals(this.mPastSigningCertificates, that.mPastSigningCertificates)) {
            return false;
        }
        if (this.mPastSigningCertificates != null) {
            for (int i = 0; i < this.mPastSigningCertificates.length; i++) {
                if (this.mPastSigningCertificates[i].getFlags()
                        != that.mPastSigningCertificates[i].getFlags()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int result = Arrays.hashCode(this.mSignatures);
        return (((((result * 31) + this.mSignatureSchemeVersion) * 31)
                                + (this.mPublicKeys != null ? this.mPublicKeys.hashCode() : 0))
                        * 31)
                + Arrays.hashCode(this.mPastSigningCertificates);
    }

    public static class Builder {
        private Signature[] mPastSigningCertificates;
        private int mSignatureSchemeVersion = 0;
        private Signature[] mSignatures;

        public Builder setSignatures(Signature[] signatures) {
            this.mSignatures = signatures;
            return this;
        }

        public Builder setSignatureSchemeVersion(int signatureSchemeVersion) {
            this.mSignatureSchemeVersion = signatureSchemeVersion;
            return this;
        }

        public Builder setPastSigningCertificates(Signature[] pastSigningCertificates) {
            this.mPastSigningCertificates = pastSigningCertificates;
            return this;
        }

        private void checkInvariants() {
            if (this.mSignatures == null) {
                throw new IllegalStateException(
                        "SigningDetails requires the current signing certificates.");
            }
        }

        public SigningDetails build() throws CertificateException {
            checkInvariants();
            return new SigningDetails(
                    this.mSignatures, this.mSignatureSchemeVersion, this.mPastSigningCertificates);
        }
    }

    public static ArraySet<PublicKey> toSigningKeys(Signature[] signatures)
            throws CertificateException {
        ArraySet<PublicKey> keys = new ArraySet<>(signatures.length);
        for (Signature signature : signatures) {
            keys.add(signature.getPublicKey());
        }
        return keys;
    }

    public Signature[] getSignatures() {
        return this.mSignatures;
    }

    public int getSignatureSchemeVersion() {
        return this.mSignatureSchemeVersion;
    }

    public ArraySet<PublicKey> getPublicKeys() {
        return this.mPublicKeys;
    }

    public Signature[] getPastSigningCertificates() {
        return this.mPastSigningCertificates;
    }

    @Deprecated
    private void __metadata() {}
}
