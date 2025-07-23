package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes5.dex */
public abstract class StreamBlockCipher extends DefaultMultiBlockCipher implements StreamCipher {
    private final BlockCipher cipher;

    protected abstract byte calculateByte(byte b);

    protected StreamBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public final byte returnByte(byte b) {
        return calculateByte(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        int i4 = i + i2;
        if (i4 > bArr.length) {
            throw new DataLengthException("input buffer too small");
        }
        if (i3 + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        while (i < i4) {
            bArr2[i3] = calculateByte(bArr[i]);
            i3++;
            i++;
        }
        return i2;
    }
}
