package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class X12Encoder extends C40Encoder {
    @Override // com.google.zxing.datamatrix.encoder.C40Encoder, com.google.zxing.datamatrix.encoder.Encoder
    public final void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            encodeChar(currentChar, sb);
            if (sb.length() % 3 == 0) {
                C40Encoder.writeNextTriplet(encoderContext, sb);
                int lookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.pos, 3, encoderContext.msg);
                if (lookAheadTest != 3) {
                    encoderContext.newEncoding = lookAheadTest;
                    break;
                }
            }
        }
        handleEOD(encoderContext, sb);
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public final int encodeChar(char c, StringBuilder sb) {
        if (c == '\r') {
            sb.append((char) 0);
        } else if (c == '*') {
            sb.append((char) 1);
        } else if (c == '>') {
            sb.append((char) 2);
        } else if (c == ' ') {
            sb.append((char) 3);
        } else if (c >= '0' && c <= '9') {
            sb.append((char) ((c - '0') + 4));
        } else {
            if (c < 'A' || c > 'Z') {
                HighLevelEncoder.illegalCharacter(c);
                throw null;
            }
            sb.append((char) ((c - 'A') + 14));
        }
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public final int getEncodingMode() {
        return 3;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public final void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        encoderContext.updateSymbolInfo(encoderContext.getCodewordCount());
        int codewordCount = encoderContext.symbolInfo.dataCapacity - encoderContext.getCodewordCount();
        int length = sb.length();
        if (length == 2) {
            encoderContext.writeCodeword((char) 254);
            encoderContext.pos -= 2;
            encoderContext.newEncoding = 0;
        } else if (length == 1) {
            encoderContext.pos--;
            if (codewordCount > 1) {
                encoderContext.writeCodeword((char) 254);
            }
            encoderContext.newEncoding = 0;
        }
    }
}
