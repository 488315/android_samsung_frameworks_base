package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BinaryShiftToken extends Token {
    public final short binaryShiftByteCount;
    public final short binaryShiftStart;

    public BinaryShiftToken(Token token, int i, int i2) {
        super(token);
        this.binaryShiftStart = (short) i;
        this.binaryShiftByteCount = (short) i2;
    }

    @Override // com.google.zxing.aztec.encoder.Token
    public final void appendTo(BitArray bitArray, byte[] bArr) {
        int i = 0;
        while (true) {
            short s = this.binaryShiftByteCount;
            if (i >= s) {
                return;
            }
            if (i == 0 || (i == 31 && s <= 62)) {
                bitArray.appendBits(31, 5);
                if (s > 62) {
                    bitArray.appendBits(s - 31, 16);
                } else if (i == 0) {
                    bitArray.appendBits(Math.min((int) s, 31), 5);
                } else {
                    bitArray.appendBits(s - 31, 5);
                }
            }
            bitArray.appendBits(bArr[this.binaryShiftStart + i], 8);
            i++;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append((int) this.binaryShiftStart);
        sb.append("::");
        sb.append((r1 + this.binaryShiftByteCount) - 1);
        sb.append('>');
        return sb.toString();
    }
}
