package com.android.internal.org.bouncycastle.crypto.generators;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.crypto.ExtendedDigest;
import com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes5.dex */
public class PKCS12ParametersGenerator extends PBEParametersGenerator {
    public static final int IV_MATERIAL = 2;
    public static final int KEY_MATERIAL = 1;
    public static final int MAC_MATERIAL = 3;
    private Digest digest;
    private int u;
    private int v;

    public PKCS12ParametersGenerator(Digest digest) {
        this.digest = digest;
        if (digest instanceof ExtendedDigest) {
            this.u = digest.getDigestSize();
            this.v = ((ExtendedDigest) digest).getByteLength();
            return;
        }
        throw new IllegalArgumentException("Digest " + digest.getAlgorithmName() + " unsupported");
    }

    private void adjust(byte[] a, int aOff, byte[] b) {
        int x = (b[b.length - 1] & 255) + (a[(b.length + aOff) - 1] & 255) + 1;
        a[(b.length + aOff) - 1] = (byte) x;
        int x2 = x >>> 8;
        for (int i = b.length - 2; i >= 0; i--) {
            int x3 = x2 + (b[i] & 255) + (a[aOff + i] & 255);
            a[aOff + i] = (byte) x3;
            x2 = x3 >>> 8;
        }
    }

    private byte[] generateDerivedKey(int idByte, int n) {
        byte[] S;
        byte[] P;
        byte[] D = new byte[this.v];
        byte[] dKey = new byte[n];
        for (int i = 0; i != D.length; i++) {
            D[i] = (byte) idByte;
        }
        int i2 = 0;
        if (this.salt != null && this.salt.length != 0) {
            S = new byte[this.v * (((this.salt.length + this.v) - 1) / this.v)];
            for (int i3 = 0; i3 != S.length; i3++) {
                S[i3] = this.salt[i3 % this.salt.length];
            }
        } else {
            S = new byte[0];
        }
        if (this.password != null && this.password.length != 0) {
            P = new byte[this.v * (((this.password.length + this.v) - 1) / this.v)];
            for (int i4 = 0; i4 != P.length; i4++) {
                P[i4] = this.password[i4 % this.password.length];
            }
        } else {
            P = new byte[0];
        }
        byte[] I = new byte[S.length + P.length];
        System.arraycopy(S, 0, I, 0, S.length);
        System.arraycopy(P, 0, I, S.length, P.length);
        byte[] B = new byte[this.v];
        int c = ((this.u + n) - 1) / this.u;
        byte[] A = new byte[this.u];
        for (int i5 = 1; i5 <= c; i5++) {
            this.digest.update(D, i2, D.length);
            this.digest.update(I, i2, I.length);
            this.digest.doFinal(A, i2);
            for (int j = 1; j < this.iterationCount; j++) {
                this.digest.update(A, i2, A.length);
                this.digest.doFinal(A, i2);
            }
            for (int j2 = 0; j2 != B.length; j2++) {
                B[j2] = A[j2 % A.length];
            }
            for (int j3 = 0; j3 != I.length / this.v; j3++) {
                adjust(I, this.v * j3, B);
            }
            if (i5 == c) {
                i2 = 0;
                System.arraycopy(A, 0, dKey, (i5 - 1) * this.u, dKey.length - ((i5 - 1) * this.u));
            } else {
                System.arraycopy(A, i2, dKey, (i5 - 1) * this.u, A.length);
            }
        }
        return dKey;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int keySize) {
        int keySize2 = keySize / 8;
        byte[] dKey = generateDerivedKey(1, keySize2);
        return new KeyParameter(dKey, 0, keySize2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int keySize, int ivSize) {
        int keySize2 = keySize / 8;
        int ivSize2 = ivSize / 8;
        byte[] dKey = generateDerivedKey(1, keySize2);
        byte[] iv = generateDerivedKey(2, ivSize2);
        return new ParametersWithIV(new KeyParameter(dKey, 0, keySize2), iv, 0, ivSize2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedMacParameters(int keySize) {
        int keySize2 = keySize / 8;
        byte[] dKey = generateDerivedKey(3, keySize2);
        return new KeyParameter(dKey, 0, keySize2);
    }
}
