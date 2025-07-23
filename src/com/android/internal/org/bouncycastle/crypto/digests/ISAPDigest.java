package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.util.Pack;
import java.io.ByteArrayOutputStream;

/* loaded from: classes5.dex */
public class ISAPDigest implements Digest {
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private long t0;
    private long t1;
    private long t2;
    private long t3;
    private long t4;
    private long x0;
    private long x1;
    private long x2;
    private long x3;
    private long x4;

    private long ROTR(long j, long j2) {
        return (j << ((int) (64 - j2))) | (j >>> ((int) j2));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    private void ROUND(long j) {
        long j2 = this.x0;
        long j3 = this.x1;
        long j4 = this.x2;
        long j5 = this.x3;
        long j6 = this.x4;
        long j7 = ((((j2 ^ j3) ^ j4) ^ j5) ^ j) ^ ((((j2 ^ j4) ^ j6) ^ j) & j3);
        this.t0 = j7;
        this.t1 = ((((j2 ^ j4) ^ j5) ^ j6) ^ j) ^ (((j3 ^ j4) ^ j) & (j3 ^ j5));
        this.t2 = (((j3 ^ j4) ^ j6) ^ j) ^ (j5 & j6);
        this.t3 = ((j4 ^ (j2 ^ j3)) ^ j) ^ ((~j2) & (j5 ^ j6));
        this.t4 = ((j2 ^ j6) & j3) ^ ((j3 ^ j5) ^ j6);
        this.x0 = (ROTR(j7, 19L) ^ j7) ^ ROTR(this.t0, 28L);
        long j8 = this.t1;
        this.x1 = (j8 ^ ROTR(j8, 39L)) ^ ROTR(this.t1, 61L);
        long j9 = this.t2;
        this.x2 = ~((j9 ^ ROTR(j9, 1L)) ^ ROTR(this.t2, 6L));
        long j10 = this.t3;
        this.x3 = (j10 ^ ROTR(j10, 10L)) ^ ROTR(this.t3, 17L);
        long j11 = this.t4;
        this.x4 = (j11 ^ ROTR(j11, 7L)) ^ ROTR(this.t4, 41L);
    }

    private void P12() {
        ROUND(240L);
        ROUND(225L);
        ROUND(210L);
        ROUND(195L);
        ROUND(180L);
        ROUND(165L);
        ROUND(150L);
        ROUND(135L);
        ROUND(120L);
        ROUND(105L);
        ROUND(90L);
        ROUND(75L);
    }

    protected long U64BIG(long j) {
        return (ROTR(j, 56L) & 1095216660735L) | (ROTR(j, 8L) & (-72057589759737856L)) | (ROTR(j, 24L) & 71776119077928960L) | (ROTR(j, 40L) & 280375465148160L);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "ISAP Hash";
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
        this.t4 = 0L;
        this.t3 = 0L;
        this.t2 = 0L;
        this.t1 = 0L;
        this.t0 = 0L;
        this.x0 = -1255492011513352131L;
        this.x1 = -8380609354527731710L;
        this.x2 = -5437372128236807582L;
        this.x3 = 4834782570098516968L;
        this.x4 = 3787428097924915520L;
        byte[] byteArray = this.buffer.toByteArray();
        int length = byteArray.length;
        int i2 = length >> 3;
        long[] jArr = new long[i2];
        int i3 = 0;
        Pack.littleEndianToLong(byteArray, 0, jArr, 0, i2);
        int i4 = 0;
        while (length >= 8) {
            this.x0 ^= U64BIG(jArr[i4]);
            P12();
            length -= 8;
            i4++;
        }
        this.x0 ^= 128 << ((7 - length) << 3);
        while (length > 0) {
            length--;
            this.x0 ^= (byteArray[(i4 << 3) + length] & 255) << ((7 - length) << 3);
        }
        P12();
        long[] jArr2 = new long[4];
        while (i3 < 3) {
            jArr2[i3] = U64BIG(this.x0);
            P12();
            i3++;
        }
        jArr2[i3] = U64BIG(this.x0);
        Pack.longToLittleEndian(jArr2, bArr, i);
        this.buffer.reset();
        return 32;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        this.buffer.reset();
    }
}
