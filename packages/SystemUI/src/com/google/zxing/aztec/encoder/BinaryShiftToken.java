package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class BinaryShiftToken extends Token {
    public final int binaryShiftByteCount;
    public final int binaryShiftStart;

    public BinaryShiftToken(Token token, int i, int i2) {
        super(token);
        this.binaryShiftStart = i;
        this.binaryShiftByteCount = i2;
    }

    @Override // com.google.zxing.aztec.encoder.Token
    public final void appendTo(BitArray bitArray, byte[] bArr) {
        int i = 0;
        while (true) {
            int i2 = this.binaryShiftByteCount;
            if (i >= i2) {
                return;
            }
            if (i == 0 || (i == 31 && i2 <= 62)) {
                bitArray.appendBits(31, 5);
                if (i2 > 62) {
                    bitArray.appendBits(i2 - 31, 16);
                } else if (i == 0) {
                    bitArray.appendBits(Math.min(i2, 31), 5);
                } else {
                    bitArray.appendBits(i2 - 31, 5);
                }
            }
            bitArray.appendBits(bArr[this.binaryShiftStart + i], 8);
            i++;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append(this.binaryShiftStart);
        sb.append("::");
        sb.append((r1 + this.binaryShiftByteCount) - 1);
        sb.append('>');
        return sb.toString();
    }
}
