package com.samsung.security.securekeyblob;

import android.os.IInstalld;
import android.security.keystore.ArrayUtils;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes2.dex */
public final class SecureKeyGenParameterSpec {
    public final String mAlgorithm;
    public final String[] mBlockModes;
    public final X500Principal mCertificateSubject;
    public final byte[] mChallenge;
    public final boolean mDeviceAttestation;
    public final boolean mDevicePropertiesAttestationIncluded;
    public final String[] mDigests;
    public final String[] mEncryptionPaddings;
    public final int mKeySize;
    public final int mPurposes;
    public final byte[] mServiceTAName;
    public final String[] mSignaturePaddings;
    public final boolean mVerifiableIntegrity;

    public SecureKeyGenParameterSpec(String str, byte[] bArr, int i, int i2, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, byte[] bArr2, boolean z, boolean z2, boolean z3, X500Principal x500Principal) {
        this.mAlgorithm = str;
        this.mServiceTAName = bArr;
        this.mKeySize = i;
        this.mPurposes = i2;
        this.mDigests = ArrayUtils.cloneIfNotEmpty(strArr);
        this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr2));
        this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr3));
        this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr4));
        this.mChallenge = cloneIfNotNull(bArr2);
        this.mDeviceAttestation = z;
        this.mVerifiableIntegrity = z2;
        this.mDevicePropertiesAttestationIncluded = z3;
        this.mCertificateSubject = x500Principal;
    }

    public String getAlgorithm() {
        return this.mAlgorithm;
    }

    public byte[] getServiceTAName() {
        return this.mServiceTAName;
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public int getPurposes() {
        return this.mPurposes;
    }

    public String[] getDigests() {
        String[] strArr = this.mDigests;
        if (strArr == null) {
            throw new IllegalStateException("Digests not specified");
        }
        return ArrayUtils.cloneIfNotEmpty(strArr);
    }

    public String[] getEncryptionPaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public String[] getSignaturePaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    public String[] getBlockModes() {
        return ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    public byte[] getChallenge() {
        return this.mChallenge;
    }

    public boolean isDeviceAttestation() {
        return this.mDeviceAttestation;
    }

    public boolean isVerifiableIntegrity() {
        return this.mVerifiableIntegrity;
    }

    public X500Principal getCertificateSubject() {
        return this.mCertificateSubject;
    }

    public final class Builder {
        public String mAlgorithm;
        public byte[] mServiceTAName;
        public int mKeySize = -1;
        public int mPurposes = 0;
        public String[] mDigests = null;
        public String[] mEncryptionPaddings = null;
        public String[] mSignaturePaddings = null;
        public String[] mBlockModes = null;
        public byte[] mChallenge = null;
        public boolean mDeviceAttestation = false;
        public boolean mVerifiableIntegrity = false;
        public boolean mDevicePropertiesAttestationIncluded = false;
        public X500Principal mCertificateSubject = null;

        public Builder(byte[] bArr, String str) {
            this.mAlgorithm = null;
            this.mServiceTAName = null;
            if (str == null) {
                throw new NullPointerException("algorithm == null");
            }
            if (bArr == null) {
                throw new NullPointerException("serviceName == null");
            }
            this.mAlgorithm = str;
            this.mServiceTAName = bArr;
            setChallenge(getChallenge());
            setDigests("SHA-256");
            if ("EC".equals(str)) {
                setKeySize(256);
                setPurpose(12);
            } else {
                if ("RSA".equals(str)) {
                    setKeySize(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
                    setPurpose(15);
                    setBlockModes("ECB");
                    setEncryptionPaddings("PKCS1Padding");
                    setSignaturePaddings("PSS");
                    return;
                }
                throw new IllegalArgumentException("Unsupported algorithm: " + str);
            }
        }

        public Builder setKeySize(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("keySize < 0");
            }
            this.mKeySize = i;
            return this;
        }

        public Builder setPurpose(int i) {
            this.mPurposes = i;
            return this;
        }

        public Builder setDigests(String... strArr) {
            this.mDigests = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setEncryptionPaddings(String... strArr) {
            this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setSignaturePaddings(String... strArr) {
            this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setBlockModes(String... strArr) {
            this.mBlockModes = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setChallenge(byte[] bArr) {
            this.mChallenge = bArr;
            return this;
        }

        public final byte[] getChallenge() {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            sb.append(random.nextLong());
            sb.append(random.nextLong());
            sb.append(random.nextLong());
            sb.append(random.nextLong());
            return sb.toString().getBytes(StandardCharsets.UTF_8);
        }

        public SecureKeyGenParameterSpec build() {
            return new SecureKeyGenParameterSpec(this.mAlgorithm, this.mServiceTAName, this.mKeySize, this.mPurposes, this.mDigests, this.mEncryptionPaddings, this.mSignaturePaddings, this.mBlockModes, this.mChallenge, this.mDeviceAttestation, this.mVerifiableIntegrity, this.mDevicePropertiesAttestationIncluded, this.mCertificateSubject);
        }
    }

    public final byte[] cloneIfNotNull(byte[] bArr) {
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }
}
