package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Bytes;
import java.lang.reflect.Array;

/* loaded from: classes5.dex */
public class Haraka256Digest extends HarakaBase {
    private final byte[] buffer;
    private int off;
    private final CryptoServicePurpose purpose;

    private void mix256(byte[][] bArr, byte[][] bArr2) {
        System.arraycopy(bArr[0], 0, bArr2[0], 0, 4);
        System.arraycopy(bArr[1], 0, bArr2[0], 4, 4);
        System.arraycopy(bArr[0], 4, bArr2[0], 8, 4);
        System.arraycopy(bArr[1], 4, bArr2[0], 12, 4);
        System.arraycopy(bArr[0], 8, bArr2[1], 0, 4);
        System.arraycopy(bArr[1], 8, bArr2[1], 4, 4);
        System.arraycopy(bArr[0], 12, bArr2[1], 8, 4);
        System.arraycopy(bArr[1], 12, bArr2[1], 12, 4);
    }

    private int haraka256256(byte[] bArr, byte[] bArr2, int i) {
        byte[][] bArr3 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 2, 16);
        byte[][] bArr4 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 2, 16);
        System.arraycopy(bArr, 0, bArr3[0], 0, 16);
        System.arraycopy(bArr, 16, bArr3[1], 0, 16);
        bArr3[0] = aesEnc(bArr3[0], RC[0]);
        bArr3[1] = aesEnc(bArr3[1], RC[1]);
        bArr3[0] = aesEnc(bArr3[0], RC[2]);
        bArr3[1] = aesEnc(bArr3[1], RC[3]);
        mix256(bArr3, bArr4);
        bArr3[0] = aesEnc(bArr4[0], RC[4]);
        bArr3[1] = aesEnc(bArr4[1], RC[5]);
        bArr3[0] = aesEnc(bArr3[0], RC[6]);
        bArr3[1] = aesEnc(bArr3[1], RC[7]);
        mix256(bArr3, bArr4);
        bArr3[0] = aesEnc(bArr4[0], RC[8]);
        bArr3[1] = aesEnc(bArr4[1], RC[9]);
        bArr3[0] = aesEnc(bArr3[0], RC[10]);
        bArr3[1] = aesEnc(bArr3[1], RC[11]);
        mix256(bArr3, bArr4);
        bArr3[0] = aesEnc(bArr4[0], RC[12]);
        bArr3[1] = aesEnc(bArr4[1], RC[13]);
        bArr3[0] = aesEnc(bArr3[0], RC[14]);
        bArr3[1] = aesEnc(bArr3[1], RC[15]);
        mix256(bArr3, bArr4);
        bArr3[0] = aesEnc(bArr4[0], RC[16]);
        bArr3[1] = aesEnc(bArr4[1], RC[17]);
        bArr3[0] = aesEnc(bArr3[0], RC[18]);
        bArr3[1] = aesEnc(bArr3[1], RC[19]);
        mix256(bArr3, bArr4);
        Bytes.xor(16, bArr4[0], 0, bArr, 0, bArr2, i);
        Bytes.xor(16, bArr4[1], 0, bArr, 16, bArr2, i + 16);
        return 32;
    }

    public Haraka256Digest() {
        this(CryptoServicePurpose.ANY);
    }

    public Haraka256Digest(CryptoServicePurpose cryptoServicePurpose) {
        this.purpose = cryptoServicePurpose;
        this.buffer = new byte[32];
        CryptoServicesRegistrar.checkConstraints(Utils.getDefaultProperties(this, getDigestSize() * 4, cryptoServicePurpose));
    }

    public Haraka256Digest(Haraka256Digest haraka256Digest) {
        CryptoServicePurpose cryptoServicePurpose = haraka256Digest.purpose;
        this.purpose = cryptoServicePurpose;
        this.buffer = Arrays.clone(haraka256Digest.buffer);
        this.off = haraka256Digest.off;
        CryptoServicesRegistrar.checkConstraints(Utils.getDefaultProperties(this, getDigestSize() * 4, cryptoServicePurpose));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "Haraka-256";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte b) {
        int i = this.off;
        if (i > 31) {
            throw new IllegalArgumentException("total input cannot be more than 32 bytes");
        }
        byte[] bArr = this.buffer;
        this.off = i + 1;
        bArr[i] = b;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        int i3 = this.off;
        if (i3 > 32 - i2) {
            throw new IllegalArgumentException("total input cannot be more than 32 bytes");
        }
        System.arraycopy(bArr, i, this.buffer, i3, i2);
        this.off += i2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        if (this.off != 32) {
            throw new IllegalStateException("input must be exactly 32 bytes");
        }
        if (bArr.length - i < 32) {
            throw new IllegalArgumentException("output too short to receive digest");
        }
        int haraka256256 = haraka256256(this.buffer, bArr, i);
        reset();
        return haraka256256;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        this.off = 0;
        Arrays.clear(this.buffer);
    }
}
