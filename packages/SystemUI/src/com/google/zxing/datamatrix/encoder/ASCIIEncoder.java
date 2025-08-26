package com.google.zxing.datamatrix.encoder;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public final class ASCIIEncoder implements Encoder {
    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public final void encode(EncoderContext encoderContext) {
        int i = encoderContext.pos;
        String str = encoderContext.msg;
        int length = str.length();
        int i2 = i;
        while (i2 < length && HighLevelEncoder.isDigit(str.charAt(i2))) {
            i2++;
        }
        if (i2 - i >= 2) {
            char cCharAt = str.charAt(encoderContext.pos);
            char cCharAt2 = str.charAt(encoderContext.pos + 1);
            if (HighLevelEncoder.isDigit(cCharAt) && HighLevelEncoder.isDigit(cCharAt2)) {
                encoderContext.writeCodeword((char) ((cCharAt2 - '0') + ((cCharAt - '0') * 10) + 130));
                encoderContext.pos += 2;
                return;
            } else {
                throw new IllegalArgumentException("not digits: " + cCharAt + cCharAt2);
            }
        }
        char currentChar = encoderContext.getCurrentChar();
        int iLookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.pos, 0, str);
        if (iLookAheadTest == 0) {
            if (!HighLevelEncoder.isExtendedASCII(currentChar)) {
                encoderContext.writeCodeword((char) (currentChar + 1));
                encoderContext.pos++;
                return;
            } else {
                encoderContext.writeCodeword((char) 235);
                encoderContext.writeCodeword((char) (currentChar - 127));
                encoderContext.pos++;
                return;
            }
        }
        if (iLookAheadTest == 1) {
            encoderContext.writeCodeword((char) 230);
            encoderContext.newEncoding = 1;
            return;
        }
        if (iLookAheadTest == 2) {
            encoderContext.writeCodeword((char) 239);
            encoderContext.newEncoding = 2;
            return;
        }
        if (iLookAheadTest == 3) {
            encoderContext.writeCodeword((char) 238);
            encoderContext.newEncoding = 3;
        } else if (iLookAheadTest == 4) {
            encoderContext.writeCodeword((char) 240);
            encoderContext.newEncoding = 4;
        } else {
            if (iLookAheadTest != 5) {
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iLookAheadTest, "Illegal mode: "));
            }
            encoderContext.writeCodeword((char) 231);
            encoderContext.newEncoding = 5;
        }
    }
}
