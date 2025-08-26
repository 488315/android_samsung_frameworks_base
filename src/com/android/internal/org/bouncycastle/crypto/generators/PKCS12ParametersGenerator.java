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
        } else {
            throw new IllegalArgumentException("Digest " + digest.getAlgorithmName() + " unsupported");
        }
    }

    private void adjust(byte[] bArr, int i, byte[] bArr2) {
        int i2 = (bArr2[bArr2.length - 1] & 255) + (bArr[(bArr2.length + i) - 1] & 255) + 1;
        bArr[(bArr2.length + i) - 1] = (byte) i2;
        int i3 = i2 >>> 8;
        for (int length = bArr2.length - 2; length >= 0; length--) {
            int i4 = i + length;
            int i5 = i3 + (bArr2[length] & 255) + (bArr[i4] & 255);
            bArr[i4] = (byte) i5;
            i3 = i5 >>> 8;
        }
    }

    private byte[] generateDerivedKey(int i, int i2) {
        byte[] bArr;
        byte[] bArr2;
        int i3;
        int i4 = this.v;
        byte[] bArr3 = new byte[i4];
        byte[] bArr4 = new byte[i2];
        int i5 = 0;
        for (int i6 = 0; i6 != i4; i6++) {
            bArr3[i6] = (byte) i;
        }
        int i7 = 1;
        if (this.salt != null && this.salt.length != 0) {
            int i8 = this.v;
            int length = this.salt.length;
            int i9 = this.v;
            int i10 = i8 * (((length + i9) - 1) / i9);
            bArr = new byte[i10];
            for (int i11 = 0; i11 != i10; i11++) {
                bArr[i11] = this.salt[i11 % this.salt.length];
            }
        } else {
            bArr = new byte[0];
        }
        if (this.password != null && this.password.length != 0) {
            int i12 = this.v;
            int length2 = this.password.length;
            int i13 = this.v;
            int i14 = i12 * (((length2 + i13) - 1) / i13);
            bArr2 = new byte[i14];
            for (int i15 = 0; i15 != i14; i15++) {
                bArr2[i15] = this.password[i15 % this.password.length];
            }
        } else {
            bArr2 = new byte[0];
        }
        int length3 = bArr.length + bArr2.length;
        byte[] bArr5 = new byte[length3];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr5, bArr.length, bArr2.length);
        int i16 = this.v;
        byte[] bArr6 = new byte[i16];
        int i17 = this.u;
        int i18 = ((i2 + i17) - 1) / i17;
        byte[] bArr7 = new byte[i17];
        int i19 = 1;
        while (i19 <= i18) {
            this.digest.update(bArr3, i5, i4);
            this.digest.update(bArr5, i5, length3);
            this.digest.doFinal(bArr7, i5);
            for (int i20 = i7; i20 < this.iterationCount; i20++) {
                this.digest.update(bArr7, i5, i17);
                this.digest.doFinal(bArr7, i5);
            }
            for (int i21 = i5; i21 != i16; i21++) {
                bArr6[i21] = bArr7[i21 % i17];
            }
            int i22 = i5;
            while (true) {
                int i23 = this.v;
                if (i22 == length3 / i23) {
                    break;
                }
                adjust(bArr5, i23 * i22, bArr6);
                i22++;
            }
            if (i19 == i18) {
                int i24 = i19 - 1;
                int i25 = this.u;
                int i26 = i24 * i25;
                int i27 = i2 - (i24 * i25);
                i3 = 0;
                System.arraycopy(bArr7, 0, bArr4, i26, i27);
            } else {
                i3 = 0;
                System.arraycopy(bArr7, 0, bArr4, (i19 - 1) * this.u, i17);
            }
            i19++;
            i5 = i3;
            i7 = 1;
        }
        return bArr4;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        return new KeyParameter(generateDerivedKey(1, i2), 0, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        byte[] bArrGenerateDerivedKey = generateDerivedKey(1, i3);
        return new ParametersWithIV(new KeyParameter(bArrGenerateDerivedKey, 0, i3), generateDerivedKey(2, i4), 0, i4);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedMacParameters(int i) {
        int i2 = i / 8;
        return new KeyParameter(generateDerivedKey(3, i2), 0, i2);
    }
}
