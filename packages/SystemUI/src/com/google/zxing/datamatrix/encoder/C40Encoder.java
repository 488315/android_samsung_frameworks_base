package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C40Encoder implements Encoder {
    public static void writeNextTriplet(EncoderContext encoderContext, StringBuilder sb) {
        int charAt = sb.charAt(2) + (sb.charAt(1) * '(') + (sb.charAt(0) * 1600) + 1;
        encoderContext.codewords.append(new String(new char[]{(char) (charAt / 256), (char) (charAt % 256)}));
        sb.delete(0, 3);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            int encodeChar = encodeChar(currentChar, sb);
            int length = encoderContext.codewords.length() + ((sb.length() / 3) * 2);
            encoderContext.updateSymbolInfo(length);
            int i = encoderContext.symbolInfo.dataCapacity - length;
            if (!encoderContext.hasMoreCharacters()) {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && i != 2) {
                    int length2 = sb.length();
                    sb.delete(length2 - encodeChar, length2);
                    encoderContext.pos--;
                    encodeChar = encodeChar(encoderContext.getCurrentChar(), sb2);
                    encoderContext.symbolInfo = null;
                }
                while (sb.length() % 3 == 1 && (encodeChar > 3 || i != 1)) {
                    int length3 = sb.length();
                    sb.delete(length3 - encodeChar, length3);
                    encoderContext.pos--;
                    encodeChar = encodeChar(encoderContext.getCurrentChar(), sb2);
                    encoderContext.symbolInfo = null;
                }
            } else if (sb.length() % 3 == 0 && HighLevelEncoder.lookAheadTest(encoderContext.pos, getEncodingMode(), encoderContext.msg) != getEncodingMode()) {
                encoderContext.newEncoding = 0;
                break;
            }
        }
        handleEOD(encoderContext, sb);
    }

    public int encodeChar(char c, StringBuilder sb) {
        if (c == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c >= '0' && c <= '9') {
            sb.append((char) (c - ','));
            return 1;
        }
        if (c >= 'A' && c <= 'Z') {
            sb.append((char) (c - '3'));
            return 1;
        }
        if (c < ' ') {
            sb.append((char) 0);
            sb.append(c);
            return 2;
        }
        if (c <= '/') {
            sb.append((char) 1);
            sb.append((char) (c - '!'));
            return 2;
        }
        if (c <= '@') {
            sb.append((char) 1);
            sb.append((char) (c - '+'));
            return 2;
        }
        if (c <= '_') {
            sb.append((char) 1);
            sb.append((char) (c - 'E'));
            return 2;
        }
        if (c > 127) {
            sb.append("\u0001\u001e");
            return encodeChar((char) (c - 128), sb) + 2;
        }
        sb.append((char) 2);
        sb.append((char) (c - '`'));
        return 2;
    }

    public int getEncodingMode() {
        return 1;
    }

    public void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        int length = (sb.length() / 3) * 2;
        int length2 = sb.length() % 3;
        int length3 = encoderContext.codewords.length() + length;
        encoderContext.updateSymbolInfo(length3);
        int i = encoderContext.symbolInfo.dataCapacity - length3;
        if (length2 == 2) {
            sb.append((char) 0);
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword((char) 254);
            }
        } else if (i == 1 && length2 == 1) {
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword((char) 254);
            }
            encoderContext.pos--;
        } else {
            if (length2 != 0) {
                throw new IllegalStateException("Unexpected case. Please report!");
            }
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (i > 0 || encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword((char) 254);
            }
        }
        encoderContext.newEncoding = 0;
    }
}
