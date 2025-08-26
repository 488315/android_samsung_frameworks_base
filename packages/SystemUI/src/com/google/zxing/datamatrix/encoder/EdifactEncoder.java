package com.google.zxing.datamatrix.encoder;

/* loaded from: classes4.dex */
public final class EdifactEncoder implements Encoder {
    public static String encodeToCodewords(CharSequence charSequence) {
        StringBuilder sb = (StringBuilder) charSequence;
        int length = sb.length();
        if (length == 0) {
            throw new IllegalStateException("StringBuilder must not be empty");
        }
        int iCharAt = (sb.charAt(0) << 18) + ((length >= 2 ? sb.charAt(1) : (char) 0) << '\f') + ((length >= 3 ? sb.charAt(2) : (char) 0) << 6) + (length >= 4 ? sb.charAt(3) : (char) 0);
        char c = (char) ((iCharAt >> 16) & 255);
        char c2 = (char) ((iCharAt >> 8) & 255);
        char c3 = (char) (iCharAt & 255);
        StringBuilder sb2 = new StringBuilder(3);
        sb2.append(c);
        if (length >= 2) {
            sb2.append(c2);
        }
        if (length >= 3) {
            sb2.append(c3);
        }
        return sb2.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
    
        com.google.zxing.datamatrix.encoder.HighLevelEncoder.illegalCharacter(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0054, code lost:
    
        throw null;
     */
    @Override // com.google.zxing.datamatrix.encoder.Encoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void encode(EncoderContext encoderContext) {
        String str;
        StringBuilder sb;
        boolean z;
        StringBuilder sb2 = new StringBuilder();
        while (true) {
            boolean zHasMoreCharacters = encoderContext.hasMoreCharacters();
            str = encoderContext.msg;
            sb = encoderContext.codewords;
            z = true;
            if (!zHasMoreCharacters) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            if (currentChar >= ' ' && currentChar <= '?') {
                sb2.append(currentChar);
            } else if (currentChar < '@' || currentChar > '^') {
                break;
            } else {
                sb2.append((char) (currentChar - '@'));
            }
            encoderContext.pos++;
            if (sb2.length() >= 4) {
                sb.append(encodeToCodewords(sb2));
                sb2.delete(0, 4);
                if (HighLevelEncoder.lookAheadTest(encoderContext.pos, 4, str) != 4) {
                    encoderContext.newEncoding = 0;
                    break;
                }
            }
        }
        sb2.append((char) 31);
        try {
            int length = sb2.length();
            if (length == 0) {
                return;
            }
            if (length == 1) {
                encoderContext.updateSymbolInfo(sb.length());
                int length2 = encoderContext.symbolInfo.dataCapacity - sb.length();
                int length3 = (str.length() - encoderContext.skipAtEnd) - encoderContext.pos;
                if (length3 > length2) {
                    encoderContext.updateSymbolInfo(sb.length() + 1);
                    length2 = encoderContext.symbolInfo.dataCapacity - sb.length();
                }
                if (length3 <= length2 && length2 <= 2) {
                    return;
                }
            }
            if (length > 4) {
                throw new IllegalStateException("Count must not exceed 4");
            }
            int i = length - 1;
            String strEncodeToCodewords = encodeToCodewords(sb2);
            if (encoderContext.hasMoreCharacters() || i > 2) {
                z = false;
            }
            if (i <= 2) {
                encoderContext.updateSymbolInfo(sb.length() + i);
                if (encoderContext.symbolInfo.dataCapacity - sb.length() >= 3) {
                    encoderContext.updateSymbolInfo(sb.length() + strEncodeToCodewords.length());
                    z = false;
                }
            }
            if (z) {
                encoderContext.symbolInfo = null;
                encoderContext.pos -= i;
            } else {
                sb.append(strEncodeToCodewords);
            }
        } finally {
            encoderContext.newEncoding = 0;
        }
    }
}
