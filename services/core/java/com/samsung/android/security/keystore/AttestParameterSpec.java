package com.samsung.android.security.keystore;

import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;

import javax.security.auth.x500.X500Principal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AttestParameterSpec {
    public final String mAlgorithm;
    public final X500Principal mCertificateSubject;
    public final byte[] mChallenge;
    public final boolean mDeviceAttestation;
    public final boolean mDevicePropertiesAttestationIncluded;
    public final String mExtendedKeyUsage;
    public final String mPackageName;
    public final boolean mSAKUidRequired;
    public final KeyGenParameterSpec mSpec;
    public final boolean mVerifiableIntegrity;

    public AttestParameterSpec(
            byte[] bArr, boolean z, boolean z2, KeyGenParameterSpec keyGenParameterSpec) {
        if (TextUtils.isEmpty("EC")) {
            this.mAlgorithm = "EC";
        } else {
            this.mAlgorithm = "EC";
        }
        this.mChallenge = bArr != null ? (byte[]) bArr.clone() : null;
        this.mDeviceAttestation = false;
        this.mVerifiableIntegrity = z;
        this.mDevicePropertiesAttestationIncluded = false;
        this.mSAKUidRequired = z2;
        this.mExtendedKeyUsage = null;
        this.mPackageName = null;
        this.mSpec = keyGenParameterSpec;
        this.mCertificateSubject = null;
    }
}
