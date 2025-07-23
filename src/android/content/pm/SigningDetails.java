package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.PackageUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Set;
import libcore.util.HexEncoding;

/* loaded from: classes.dex */
public final class SigningDetails implements Parcelable {
    private static final int PAST_CERT_EXISTS = 0;
    private static final String TAG = "SigningDetails";
    private final Signature[] mPastSigningCertificates;
    private final ArraySet<PublicKey> mPublicKeys;
    private final int mSignatureSchemeVersion;
    private final Signature[] mSignatures;
    public static final SigningDetails UNKNOWN = new SigningDetails(null, 0, null, null);
    public static final Parcelable.Creator<SigningDetails> CREATOR = new Parcelable.Creator<SigningDetails>() { // from class: android.content.pm.SigningDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SigningDetails createFromParcel(Parcel parcel) {
            if (parcel.readBoolean()) {
                return SigningDetails.UNKNOWN;
            }
            return new SigningDetails(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SigningDetails[] newArray(int i) {
            return new SigningDetails[i];
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

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SigningDetails(Signature[] signatureArr, int i, ArraySet<PublicKey> arraySet, Signature[] signatureArr2) {
        this.mSignatures = signatureArr;
        this.mSignatureSchemeVersion = i;
        this.mPublicKeys = arraySet;
        this.mPastSigningCertificates = signatureArr2;
    }

    public SigningDetails(Signature[] signatureArr, int i, Signature[] signatureArr2) throws CertificateException {
        this(signatureArr, i, toSigningKeys(signatureArr), signatureArr2);
    }

    public SigningDetails(Signature[] signatureArr, int i) throws CertificateException {
        this(signatureArr, i, null);
    }

    public SigningDetails(SigningDetails signingDetails) {
        if (signingDetails != null) {
            Signature[] signatureArr = signingDetails.mSignatures;
            if (signatureArr != null) {
                this.mSignatures = (Signature[]) signatureArr.clone();
            } else {
                this.mSignatures = null;
            }
            this.mSignatureSchemeVersion = signingDetails.mSignatureSchemeVersion;
            this.mPublicKeys = new ArraySet<>((ArraySet) signingDetails.mPublicKeys);
            Signature[] signatureArr2 = signingDetails.mPastSigningCertificates;
            if (signatureArr2 != null) {
                this.mPastSigningCertificates = (Signature[]) signatureArr2.clone();
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

    public SigningDetails mergeLineageWith(SigningDetails signingDetails) {
        return mergeLineageWith(signingDetails, 1);
    }

    public SigningDetails mergeLineageWith(SigningDetails signingDetails, int i) {
        SigningDetails descendantOrSelf;
        if (!hasPastSigningCertificates()) {
            if (signingDetails.hasPastSigningCertificates() && signingDetails.hasAncestorOrSelf(this)) {
                return signingDetails;
            }
        } else if (signingDetails.hasPastSigningCertificates() && (descendantOrSelf = getDescendantOrSelf(signingDetails)) != null) {
            if (descendantOrSelf == this) {
                return mergeLineageWithAncestorOrSelf(signingDetails, i);
            }
            if (i == 0) {
                return signingDetails.mergeLineageWithAncestorOrSelf(this, 1);
            }
            if (i == 1) {
                return signingDetails.mergeLineageWithAncestorOrSelf(this, 0);
            }
            if (i == 2) {
                return signingDetails.mergeLineageWithAncestorOrSelf(this, i);
            }
        }
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0095, code lost:
    
        if (r8 < 0) goto L57;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.SigningDetails mergeLineageWithAncestorOrSelf(android.content.pm.SigningDetails r12, int r13) {
        /*
            Method dump skipped, instructions count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.SigningDetails.mergeLineageWithAncestorOrSelf(android.content.pm.SigningDetails, int):android.content.pm.SigningDetails");
    }

    public boolean hasCommonAncestor(SigningDetails signingDetails) {
        if (!hasPastSigningCertificates()) {
            return signingDetails.hasAncestorOrSelf(this);
        }
        if (signingDetails.hasPastSigningCertificates()) {
            return getDescendantOrSelf(signingDetails) != null;
        }
        return hasAncestorOrSelf(signingDetails);
    }

    public boolean hasAncestorOrSelfWithDigest(Set<String> set) {
        if (this != UNKNOWN && set != null && set.size() != 0) {
            Signature[] signatureArr = this.mSignatures;
            if (signatureArr.length > 1) {
                int size = set.size();
                Signature[] signatureArr2 = this.mSignatures;
                if (size < signatureArr2.length) {
                    return false;
                }
                for (Signature signature : signatureArr2) {
                    if (!set.contains(PackageUtils.computeSha256Digest(signature.toByteArray()))) {
                        return false;
                    }
                }
                return true;
            }
            if (set.contains(PackageUtils.computeSha256Digest(signatureArr[0].toByteArray()))) {
                return true;
            }
            if (hasPastSigningCertificates()) {
                int i = 0;
                while (true) {
                    Signature[] signatureArr3 = this.mPastSigningCertificates;
                    if (i >= signatureArr3.length - 1) {
                        break;
                    }
                    if (set.contains(PackageUtils.computeSha256Digest(signatureArr3[i].toByteArray()))) {
                        return true;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    private SigningDetails getDescendantOrSelf(SigningDetails signingDetails) {
        if (!hasAncestorOrSelf(signingDetails)) {
            if (!signingDetails.hasAncestor(this)) {
                return null;
            }
            signingDetails = this;
            this = signingDetails;
        }
        int length = this.mPastSigningCertificates.length - 1;
        int length2 = signingDetails.mPastSigningCertificates.length - 1;
        while (length >= 0 && !this.mPastSigningCertificates[length].equals(signingDetails.mPastSigningCertificates[length2])) {
            length--;
        }
        if (length < 0) {
            return null;
        }
        do {
            length--;
            length2--;
            if (length < 0 || length2 < 0) {
                break;
            }
        } while (this.mPastSigningCertificates[length].equals(signingDetails.mPastSigningCertificates[length2]));
        if (length < 0 || length2 < 0) {
            return this;
        }
        return null;
    }

    public boolean hasSignatures() {
        Signature[] signatureArr = this.mSignatures;
        return signatureArr != null && signatureArr.length > 0;
    }

    public boolean hasPastSigningCertificates() {
        Signature[] signatureArr = this.mPastSigningCertificates;
        return signatureArr != null && signatureArr.length > 0;
    }

    public boolean hasAncestorOrSelf(SigningDetails signingDetails) {
        SigningDetails signingDetails2 = UNKNOWN;
        if (this == signingDetails2 || signingDetails == signingDetails2) {
            return false;
        }
        Signature[] signatureArr = signingDetails.mSignatures;
        if (signatureArr.length > 1) {
            return signaturesMatchExactly(signingDetails);
        }
        if (signatureArr.length == 0) {
            return false;
        }
        return hasCertificate(signatureArr[0]);
    }

    public boolean hasAncestor(SigningDetails signingDetails) {
        SigningDetails signingDetails2 = UNKNOWN;
        if (this != signingDetails2 && signingDetails != signingDetails2 && hasPastSigningCertificates() && signingDetails.mSignatures.length == 1) {
            int i = 0;
            while (true) {
                Signature[] signatureArr = this.mPastSigningCertificates;
                if (i >= signatureArr.length - 1) {
                    break;
                }
                if (signatureArr[i].equals(signingDetails.mSignatures[0])) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public boolean hasCommonSignerWithCapability(SigningDetails signingDetails, int i) {
        SigningDetails signingDetails2 = UNKNOWN;
        if (this == signingDetails2 || signingDetails == signingDetails2) {
            return false;
        }
        if (this.mSignatures.length > 1 || signingDetails.mSignatures.length > 1) {
            return signaturesMatchExactly(signingDetails);
        }
        ArraySet arraySet = new ArraySet();
        if (signingDetails.hasPastSigningCertificates()) {
            arraySet.addAll(Arrays.asList(signingDetails.mPastSigningCertificates));
        } else {
            arraySet.addAll(Arrays.asList(signingDetails.mSignatures));
        }
        if (arraySet.contains(this.mSignatures[0])) {
            return true;
        }
        if (hasPastSigningCertificates()) {
            int i2 = 0;
            while (true) {
                Signature[] signatureArr = this.mPastSigningCertificates;
                if (i2 >= signatureArr.length - 1) {
                    break;
                }
                if (arraySet.contains(signatureArr[i2]) && (this.mPastSigningCertificates[i2].getFlags() & i) == i) {
                    return true;
                }
                i2++;
            }
        }
        return false;
    }

    public boolean checkCapability(SigningDetails signingDetails, int i) {
        SigningDetails signingDetails2 = UNKNOWN;
        if (this == signingDetails2 || signingDetails == signingDetails2) {
            return false;
        }
        Signature[] signatureArr = signingDetails.mSignatures;
        if (signatureArr.length > 1) {
            return signaturesMatchExactly(signingDetails);
        }
        if (signatureArr.length == 0) {
            return false;
        }
        return hasCertificate(signatureArr[0], i);
    }

    public boolean checkCapabilityRecover(SigningDetails signingDetails, int i) throws CertificateException {
        SigningDetails signingDetails2 = UNKNOWN;
        if (signingDetails == signingDetails2 || this == signingDetails2) {
            return false;
        }
        if (!hasPastSigningCertificates() || signingDetails.mSignatures.length != 1) {
            return Signature.areEffectiveMatch(signingDetails, this);
        }
        int i2 = 0;
        while (true) {
            Signature[] signatureArr = this.mPastSigningCertificates;
            if (i2 >= signatureArr.length) {
                return false;
            }
            if (Signature.areEffectiveMatch(signingDetails.mSignatures[0], signatureArr[i2]) && this.mPastSigningCertificates[i2].getFlags() == i) {
                return true;
            }
            i2++;
        }
    }

    public boolean hasCertificate(Signature signature) {
        return hasCertificateInternal(signature, 0);
    }

    public boolean hasCertificate(Signature signature, int i) {
        return hasCertificateInternal(signature, i);
    }

    public boolean hasCertificate(byte[] bArr) {
        return hasCertificate(new Signature(bArr));
    }

    private boolean hasCertificateInternal(Signature signature, int i) {
        if (this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates()) {
            int i2 = 0;
            while (true) {
                Signature[] signatureArr = this.mPastSigningCertificates;
                if (i2 >= signatureArr.length - 1) {
                    break;
                }
                if (!signatureArr[i2].equals(signature) || (i != 0 && (this.mPastSigningCertificates[i2].getFlags() & i) != i)) {
                    i2++;
                }
            }
            return true;
        }
        Signature[] signatureArr2 = this.mSignatures;
        return signatureArr2.length == 1 && signatureArr2[0].equals(signature);
    }

    public boolean checkCapability(String str, int i) {
        if (this == UNKNOWN || TextUtils.isEmpty(str)) {
            return false;
        }
        if (hasSha256Certificate(HexEncoding.decode(str, false), i)) {
            return true;
        }
        return PackageUtils.computeSignaturesSha256Digest(PackageUtils.computeSignaturesSha256Digests(this.mSignatures)).equals(str);
    }

    public boolean hasSha256Certificate(byte[] bArr) {
        return hasSha256CertificateInternal(bArr, 0);
    }

    public boolean hasSha256Certificate(byte[] bArr, int i) {
        return hasSha256CertificateInternal(bArr, i);
    }

    private boolean hasSha256CertificateInternal(byte[] bArr, int i) {
        if (this == UNKNOWN) {
            return false;
        }
        if (hasPastSigningCertificates()) {
            int i2 = 0;
            while (true) {
                Signature[] signatureArr = this.mPastSigningCertificates;
                if (i2 >= signatureArr.length - 1) {
                    break;
                }
                if (!Arrays.equals(bArr, PackageUtils.computeSha256DigestBytes(signatureArr[i2].toByteArray())) || (i != 0 && (this.mPastSigningCertificates[i2].getFlags() & i) != i)) {
                    i2++;
                }
            }
            return true;
        }
        Signature[] signatureArr2 = this.mSignatures;
        if (signatureArr2.length == 1) {
            return Arrays.equals(bArr, PackageUtils.computeSha256DigestBytes(signatureArr2[0].toByteArray()));
        }
        return false;
    }

    public boolean signaturesMatchExactly(SigningDetails signingDetails) {
        return Signature.areExactMatch(this, signingDetails);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        boolean z = UNKNOWN == this;
        parcel.writeBoolean(z);
        if (z) {
            return;
        }
        parcel.writeTypedArray(this.mSignatures, i);
        parcel.writeInt(this.mSignatureSchemeVersion);
        parcel.writeArraySet(this.mPublicKeys);
        parcel.writeTypedArray(this.mPastSigningCertificates, i);
    }

    protected SigningDetails(Parcel parcel) {
        ClassLoader classLoader = Object.class.getClassLoader();
        this.mSignatures = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
        this.mSignatureSchemeVersion = parcel.readInt();
        this.mPublicKeys = parcel.readArraySet(classLoader);
        this.mPastSigningCertificates = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SigningDetails)) {
            return false;
        }
        SigningDetails signingDetails = (SigningDetails) obj;
        if (this.mSignatureSchemeVersion != signingDetails.mSignatureSchemeVersion || !Signature.areExactMatch(this, signingDetails)) {
            return false;
        }
        ArraySet<PublicKey> arraySet = this.mPublicKeys;
        if (arraySet != null) {
            if (!arraySet.equals(signingDetails.mPublicKeys)) {
                return false;
            }
        } else if (signingDetails.mPublicKeys != null) {
            return false;
        }
        if (!Arrays.equals(this.mPastSigningCertificates, signingDetails.mPastSigningCertificates)) {
            return false;
        }
        if (this.mPastSigningCertificates != null) {
            int i = 0;
            while (true) {
                Signature[] signatureArr = this.mPastSigningCertificates;
                if (i >= signatureArr.length) {
                    break;
                }
                if (signatureArr[i].getFlags() != signingDetails.mPastSigningCertificates[i].getFlags()) {
                    return false;
                }
                i++;
            }
        }
        return true;
    }

    public int hashCode() {
        int hashCode = ((Arrays.hashCode(this.mSignatures) * 31) + this.mSignatureSchemeVersion) * 31;
        ArraySet<PublicKey> arraySet = this.mPublicKeys;
        return ((hashCode + (arraySet != null ? arraySet.hashCode() : 0)) * 31) + Arrays.hashCode(this.mPastSigningCertificates);
    }

    public static class Builder {
        private Signature[] mPastSigningCertificates;
        private int mSignatureSchemeVersion = 0;
        private Signature[] mSignatures;

        public Builder setSignatures(Signature[] signatureArr) {
            this.mSignatures = signatureArr;
            return this;
        }

        public Builder setSignatureSchemeVersion(int i) {
            this.mSignatureSchemeVersion = i;
            return this;
        }

        public Builder setPastSigningCertificates(Signature[] signatureArr) {
            this.mPastSigningCertificates = signatureArr;
            return this;
        }

        private void checkInvariants() {
            if (this.mSignatures == null) {
                throw new IllegalStateException("SigningDetails requires the current signing certificates.");
            }
        }

        public SigningDetails build() throws CertificateException {
            checkInvariants();
            return new SigningDetails(this.mSignatures, this.mSignatureSchemeVersion, this.mPastSigningCertificates);
        }
    }

    public static ArraySet<PublicKey> toSigningKeys(Signature[] signatureArr) throws CertificateException {
        ArraySet<PublicKey> arraySet = new ArraySet<>(signatureArr.length);
        for (Signature signature : signatureArr) {
            arraySet.add(signature.getPublicKey());
        }
        return arraySet;
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
}
