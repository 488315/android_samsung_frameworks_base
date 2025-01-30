package com.samsung.android.security.keystore;

import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes2.dex */
public final class AttestParameterSpec {
    public final String mAlgorithm;
    public final X500Principal mCertificateSubject;
    public final byte[] mChallenge;
    public final boolean mDeviceAttestation;
    public final boolean mDevicePropertiesAttestationIncluded;
    public final String mPackageName;
    public final KeyGenParameterSpec mSpec;
    public final boolean mVerifiableIntegrity;

    public AttestParameterSpec(String str, byte[] bArr, boolean z, boolean z2, boolean z3, String str2, KeyGenParameterSpec keyGenParameterSpec, X500Principal x500Principal) {
        if (TextUtils.isEmpty(str)) {
            this.mAlgorithm = "EC";
        } else {
            this.mAlgorithm = str;
        }
        this.mChallenge = cloneIfNotNull(bArr);
        this.mDeviceAttestation = z;
        this.mVerifiableIntegrity = z2;
        this.mDevicePropertiesAttestationIncluded = z3;
        this.mPackageName = str2;
        this.mSpec = keyGenParameterSpec;
        this.mCertificateSubject = x500Principal;
    }

    public String getAlgorithm() {
        return this.mAlgorithm;
    }

    public byte[] getChallenge() {
        return this.mChallenge;
    }

    public X500Principal getCertificateSubject() {
        return this.mCertificateSubject;
    }

    public boolean isDeviceAttestation() {
        return this.mDeviceAttestation;
    }

    public boolean isVerifiableIntegrity() {
        return this.mVerifiableIntegrity;
    }

    public boolean isDevicePropertiesAttestationIncluded() {
        return this.mDevicePropertiesAttestationIncluded;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public KeyGenParameterSpec getKeyGenParameterSpec() {
        return this.mSpec;
    }

    public final class Builder {
        public final byte[] mChallenge;
        public KeyGenParameterSpec mSpec;
        public String mAlgorithm = "EC";
        public boolean mDeviceAttestation = false;
        public boolean mVerifiableIntegrity = false;
        public boolean mDevicePropertiesAttestationIncluded = false;
        public String mPackageName = null;
        public X500Principal mCertificateSubject = null;

        public Builder(String str, byte[] bArr) {
            if (str == null) {
                throw new NullPointerException("alias == null");
            }
            if (str.isEmpty()) {
                throw new IllegalArgumentException("alias must not be empty");
            }
            if (bArr == null) {
                throw new NullPointerException("challenge == null");
            }
            this.mSpec = new KeyGenParameterSpec.Builder(str, 4).setDigests("NONE", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512").build();
            this.mChallenge = bArr;
        }

        public Builder setVerifiableIntegrity(boolean z) {
            this.mVerifiableIntegrity = z;
            return this;
        }

        public AttestParameterSpec build() {
            return new AttestParameterSpec(this.mAlgorithm, this.mChallenge, this.mDeviceAttestation, this.mVerifiableIntegrity, this.mDevicePropertiesAttestationIncluded, this.mPackageName, this.mSpec, this.mCertificateSubject);
        }
    }

    public final byte[] cloneIfNotNull(byte[] bArr) {
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }
}
