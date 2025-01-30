package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EdifactEncoder implements Encoder {
    public static String encodeToCodewords(CharSequence charSequence) {
        StringBuilder sb = (StringBuilder) charSequence;
        int length = sb.length() - 0;
        if (length == 0) {
            throw new IllegalStateException("StringBuilder must not be empty");
        }
        int charAt = (sb.charAt(0) << 18) + ((length >= 2 ? sb.charAt(1) : (char) 0) << '\f') + ((length >= 3 ? sb.charAt(2) : (char) 0) << 6) + (length >= 4 ? sb.charAt(3) : (char) 0);
        char c = (char) ((charAt >> 16) & 255);
        char c2 = (char) ((charAt >> 8) & 255);
        char c3 = (char) (charAt & 255);
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

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0051, code lost:
    
        com.google.zxing.datamatrix.encoder.HighLevelEncoder.illegalCharacter(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0054, code lost:
    
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
            boolean hasMoreCharacters = encoderContext.hasMoreCharacters();
            str = encoderContext.msg;
            sb = encoderContext.codewords;
            z = true;
            if (!hasMoreCharacters) {
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
                encoderContext.updateSymbolInfo(encoderContext.getCodewordCount());
                int codewordCount = encoderContext.symbolInfo.dataCapacity - encoderContext.getCodewordCount();
                if ((str.length() - encoderContext.skipAtEnd) - encoderContext.pos == 0 && codewordCount <= 2) {
                }
            }
            if (length > 4) {
                throw new IllegalStateException("Count must not exceed 4");
            }
            int i = length - 1;
            String encodeToCodewords = encodeToCodewords(sb2);
            if (!(!encoderContext.hasMoreCharacters()) || i > 2) {
                z = false;
            }
            if (i <= 2) {
                encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + i);
                if (encoderContext.symbolInfo.dataCapacity - encoderContext.getCodewordCount() >= 3) {
                    encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + encodeToCodewords.length());
                    z = false;
                }
            }
            if (z) {
                encoderContext.symbolInfo = null;
                encoderContext.pos -= i;
            } else {
                sb.append(encodeToCodewords);
            }
        } finally {
            encoderContext.newEncoding = 0;
        }
    }
}
