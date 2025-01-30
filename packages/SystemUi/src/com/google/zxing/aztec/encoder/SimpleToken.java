package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SimpleToken extends Token {
    public final short bitCount;
    public final short value;

    public SimpleToken(Token token, int i, int i2) {
        super(token);
        this.value = (short) i;
        this.bitCount = (short) i2;
    }

    @Override // com.google.zxing.aztec.encoder.Token
    public final void appendTo(BitArray bitArray, byte[] bArr) {
        bitArray.appendBits(this.value, this.bitCount);
    }

    public final String toString() {
        short s = this.bitCount;
        return "<" + Integer.toBinaryString((this.value & ((1 << s) - 1)) | (1 << s) | (1 << s)).substring(1) + '>';
    }
}
