package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class C40Encoder implements Encoder {
    public static void writeNextTriplet(EncoderContext encoderContext, StringBuilder sb) {
        int charAt = (sb.charAt(1) * '(') + (sb.charAt(0) * 1600) + sb.charAt(2) + 1;
        encoderContext.codewords.append(new String(new char[]{(char) (charAt / 256), (char) (charAt % 256)}));
        sb.delete(0, 3);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        int lookAheadTest;
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            int encodeChar = encodeChar(currentChar, sb);
            int codewordCount = encoderContext.getCodewordCount() + ((sb.length() / 3) * 2);
            encoderContext.updateSymbolInfo(codewordCount);
            int i = encoderContext.symbolInfo.dataCapacity - codewordCount;
            if (!encoderContext.hasMoreCharacters()) {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && (i < 2 || i > 2)) {
                    int length = sb.length();
                    sb.delete(length - encodeChar, length);
                    encoderContext.pos--;
                    encodeChar = encodeChar(encoderContext.getCurrentChar(), sb2);
                    encoderContext.symbolInfo = null;
                }
                while (sb.length() % 3 == 1 && ((encodeChar <= 3 && i != 1) || encodeChar > 3)) {
                    int length2 = sb.length();
                    sb.delete(length2 - encodeChar, length2);
                    encoderContext.pos--;
                    encodeChar = encodeChar(encoderContext.getCurrentChar(), sb2);
                    encoderContext.symbolInfo = null;
                }
            } else if (sb.length() % 3 == 0 && (lookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.pos, getEncodingMode(), encoderContext.msg)) != getEncodingMode()) {
                encoderContext.newEncoding = lookAheadTest;
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
            sb.append((char) ((c - '0') + 4));
            return 1;
        }
        if (c >= 'A' && c <= 'Z') {
            sb.append((char) ((c - 'A') + 14));
            return 1;
        }
        if (c >= 0 && c <= 31) {
            sb.append((char) 0);
            sb.append(c);
            return 2;
        }
        if (c >= '!' && c <= '/') {
            sb.append((char) 1);
            sb.append((char) (c - '!'));
            return 2;
        }
        if (c >= ':' && c <= '@') {
            sb.append((char) 1);
            sb.append((char) ((c - ':') + 15));
            return 2;
        }
        if (c >= '[' && c <= '_') {
            sb.append((char) 1);
            sb.append((char) ((c - '[') + 22));
            return 2;
        }
        if (c >= '`' && c <= 127) {
            sb.append((char) 2);
            sb.append((char) (c - '`'));
            return 2;
        }
        if (c >= 128) {
            sb.append("\u0001\u001e");
            return encodeChar((char) (c - 128), sb) + 2;
        }
        throw new IllegalArgumentException("Illegal character: " + c);
    }

    public int getEncodingMode() {
        return 1;
    }

    public void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        int length = (sb.length() / 3) * 2;
        int length2 = sb.length() % 3;
        int codewordCount = encoderContext.getCodewordCount() + length;
        encoderContext.updateSymbolInfo(codewordCount);
        int i = encoderContext.symbolInfo.dataCapacity - codewordCount;
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
