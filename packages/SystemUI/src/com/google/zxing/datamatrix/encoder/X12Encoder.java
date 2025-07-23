package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
                if (HighLevelEncoder.lookAheadTest(encoderContext.pos, 3, encoderContext.msg) != 3) {
                    encoderContext.newEncoding = 0;
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
            return 1;
        }
        if (c == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c == '*') {
            sb.append((char) 1);
            return 1;
        }
        if (c == '>') {
            sb.append((char) 2);
            return 1;
        }
        if (c >= '0' && c <= '9') {
            sb.append((char) (c - ','));
            return 1;
        }
        if (c < 'A' || c > 'Z') {
            HighLevelEncoder.illegalCharacter(c);
            throw null;
        }
        sb.append((char) (c - '3'));
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public final int getEncodingMode() {
        return 3;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public final void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        StringBuilder sb2 = encoderContext.codewords;
        encoderContext.updateSymbolInfo(sb2.length());
        int length = encoderContext.symbolInfo.dataCapacity - sb2.length();
        encoderContext.pos -= sb.length();
        String str = encoderContext.msg;
        if ((str.length() - encoderContext.skipAtEnd) - encoderContext.pos > 1 || length > 1 || (str.length() - encoderContext.skipAtEnd) - encoderContext.pos != length) {
            encoderContext.writeCodeword((char) 254);
        }
        if (encoderContext.newEncoding < 0) {
            encoderContext.newEncoding = 0;
        }
    }
}
