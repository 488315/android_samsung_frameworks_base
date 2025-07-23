package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.ExtendedDigest;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import java.io.ByteArrayOutputStream;

/* loaded from: classes5.dex */
public class AsconDigest implements ExtendedDigest {
    private final int ASCON_PB_ROUNDS;
    private final String algorithmName;
    AsconParameters asconParameters;
    private long x0;
    private long x1;
    private long x2;
    private long x3;
    private long x4;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final int CRYPTO_BYTES = 32;

    public enum AsconParameters {
        AsconHash,
        AsconHashA
    }

    private long PAD(int i) {
        return 128 << (56 - (i << 3));
    }

    private long ROR(long j, int i) {
        return (j << (64 - i)) | (j >>> i);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    public AsconDigest(AsconParameters asconParameters) {
        this.asconParameters = asconParameters;
        int ordinal = asconParameters.ordinal();
        if (ordinal == 0) {
            this.ASCON_PB_ROUNDS = 12;
            this.algorithmName = "Ascon-Hash";
        } else if (ordinal == 1) {
            this.ASCON_PB_ROUNDS = 8;
            this.algorithmName = "Ascon-HashA";
        } else {
            throw new IllegalArgumentException("Invalid parameter settings for Ascon Hash");
        }
        reset();
    }

    private void ROUND(long j) {
        long j2 = this.x0;
        long j3 = this.x1;
        long j4 = this.x2;
        long j5 = this.x3;
        long j6 = this.x4;
        long j7 = ((((j2 ^ j3) ^ j4) ^ j5) ^ j) ^ ((((j2 ^ j4) ^ j6) ^ j) & j3);
        long j8 = ((((j2 ^ j4) ^ j5) ^ j6) ^ j) ^ (((j3 ^ j4) ^ j) & (j3 ^ j5));
        long j9 = (((j3 ^ j4) ^ j6) ^ j) ^ (j5 & j6);
        long j10 = ((j4 ^ (j2 ^ j3)) ^ j) ^ ((~j2) & (j5 ^ j6));
        long j11 = ((j2 ^ j6) & j3) ^ ((j3 ^ j5) ^ j6);
        this.x0 = ROR(j7, 28) ^ (ROR(j7, 19) ^ j7);
        this.x1 = (ROR(j8, 39) ^ j8) ^ ROR(j8, 61);
        this.x2 = ~(ROR(j9, 6) ^ (ROR(j9, 1) ^ j9));
        this.x3 = (ROR(j10, 10) ^ j10) ^ ROR(j10, 17);
        this.x4 = ROR(j11, 41) ^ (ROR(j11, 7) ^ j11);
    }

    private void P(int i) {
        if (i == 12) {
            ROUND(240L);
            ROUND(225L);
            ROUND(210L);
            ROUND(195L);
        }
        if (i >= 8) {
            ROUND(180L);
            ROUND(165L);
        }
        ROUND(150L);
        ROUND(135L);
        ROUND(120L);
        ROUND(105L);
        ROUND(90L);
        ROUND(75L);
    }

    private long LOADBYTES(byte[] bArr, int i, int i2) {
        long j = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j |= (bArr[i3 + i] & 255) << ((7 - i3) << 3);
        }
        return j;
    }

    private void STOREBYTES(byte[] bArr, int i, long j, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3 + i] = (byte) (j >>> ((7 - i3) << 3));
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return this.algorithmName;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte b) {
        this.buffer.write(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.buffer.write(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        if (i + 32 > bArr.length) {
            throw new OutputLengthException("output buffer is too short");
        }
        byte[] byteArray = this.buffer.toByteArray();
        int size = this.buffer.size();
        int i2 = 0;
        while (size >= 8) {
            this.x0 ^= LOADBYTES(byteArray, i2, 8);
            P(this.ASCON_PB_ROUNDS);
            i2 += 8;
            size -= 8;
        }
        long LOADBYTES = this.x0 ^ LOADBYTES(byteArray, i2, size);
        this.x0 = LOADBYTES;
        this.x0 = PAD(size) ^ LOADBYTES;
        P(12);
        int i3 = i;
        int i4 = 32;
        while (i4 > 8) {
            AsconDigest asconDigest = this;
            asconDigest.STOREBYTES(bArr, i3, this.x0, 8);
            asconDigest.P(asconDigest.ASCON_PB_ROUNDS);
            i3 += 8;
            i4 -= 8;
            this = asconDigest;
        }
        AsconDigest asconDigest2 = this;
        asconDigest2.STOREBYTES(bArr, i3, asconDigest2.x0, i4);
        asconDigest2.reset();
        return 32;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        this.buffer.reset();
        int ordinal = this.asconParameters.ordinal();
        if (ordinal == 0) {
            this.x0 = -1255492011513352131L;
            this.x1 = -8380609354527731710L;
            this.x2 = -5437372128236807582L;
            this.x3 = 4834782570098516968L;
            this.x4 = 3787428097924915520L;
            return;
        }
        if (ordinal != 1) {
            return;
        }
        this.x0 = 92044056785660070L;
        this.x1 = 8326807761760157607L;
        this.x2 = 3371194088139667532L;
        this.x3 = -2956994353054992515L;
        this.x4 = -6828509670848688761L;
    }
}
