package com.samsung.security.securekeyblob;

import android.security.keystore.ArrayUtils;
import java.util.Collections;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SecureKeyGenParameterSpec {
    public final String[] mBlockModes;
    public final X500Principal mCertificateSubject;
    public final byte[] mChallenge;
    public final boolean mDeviceAttestation;
    public final String[] mDigests;
    public final String[] mEncryptionPaddings;
    public final int mKeySize;
    public final Set mMgf1Digests;
    public final byte[] mServiceTAName;
    public final String[] mSignaturePaddings;
    public final boolean mVerifiableIntegrity;
    public final String mAlgorithm = "EC";
    public final byte[] mDNQualifier = null;
    public final int mPurposes = 12;

    public SecureKeyGenParameterSpec(byte[] bArr, int i, String[] strArr, Set set, String[] strArr2, String[] strArr3, String[] strArr4, byte[] bArr2) {
        this.mServiceTAName = bArr;
        this.mKeySize = i;
        this.mDigests = ArrayUtils.cloneIfNotEmpty(strArr);
        this.mMgf1Digests = set == null ? Collections.emptySet() : set;
        this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr2));
        this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr3));
        this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr4));
        this.mChallenge = bArr2 != null ? (byte[]) bArr2.clone() : null;
        this.mDeviceAttestation = false;
        this.mVerifiableIntegrity = false;
        this.mCertificateSubject = null;
    }
}
