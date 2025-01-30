package com.google.zxing.datamatrix.encoder;

import android.support.v4.media.AbstractC0000x2c234b15;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ASCIIEncoder implements Encoder {
    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public final void encode(EncoderContext encoderContext) {
        int i;
        int i2 = encoderContext.pos;
        String str = encoderContext.msg;
        int length = str.length();
        if (i2 < length) {
            char charAt = str.charAt(i2);
            i = 0;
            while (true) {
                if (!(charAt >= '0' && charAt <= '9') || i2 >= length) {
                    break;
                }
                i++;
                i2++;
                if (i2 < length) {
                    charAt = str.charAt(i2);
                }
            }
        } else {
            i = 0;
        }
        if (i >= 2) {
            char charAt2 = str.charAt(encoderContext.pos);
            char charAt3 = str.charAt(encoderContext.pos + 1);
            if (charAt2 >= '0' && charAt2 <= '9') {
                if (charAt3 >= '0' && charAt3 <= '9') {
                    encoderContext.writeCodeword((char) ((charAt3 - '0') + ((charAt2 - '0') * 10) + 130));
                    encoderContext.pos += 2;
                    return;
                }
            }
            throw new IllegalArgumentException("not digits: " + charAt2 + charAt3);
        }
        char currentChar = encoderContext.getCurrentChar();
        int lookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.pos, 0, str);
        if (lookAheadTest == 0) {
            if (!HighLevelEncoder.isExtendedASCII(currentChar)) {
                encoderContext.writeCodeword((char) (currentChar + 1));
                encoderContext.pos++;
                return;
            } else {
                encoderContext.writeCodeword((char) 235);
                encoderContext.writeCodeword((char) ((currentChar - 128) + 1));
                encoderContext.pos++;
                return;
            }
        }
        if (lookAheadTest == 1) {
            encoderContext.writeCodeword((char) 230);
            encoderContext.newEncoding = 1;
            return;
        }
        if (lookAheadTest == 2) {
            encoderContext.writeCodeword((char) 239);
            encoderContext.newEncoding = 2;
            return;
        }
        if (lookAheadTest == 3) {
            encoderContext.writeCodeword((char) 238);
            encoderContext.newEncoding = 3;
        } else if (lookAheadTest == 4) {
            encoderContext.writeCodeword((char) 240);
            encoderContext.newEncoding = 4;
        } else {
            if (lookAheadTest != 5) {
                throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Illegal mode: ", lookAheadTest));
            }
            encoderContext.writeCodeword((char) 231);
            encoderContext.newEncoding = 5;
        }
    }
}
