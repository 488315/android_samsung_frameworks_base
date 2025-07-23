package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EdifactEncoder implements Encoder {
    public static String encodeToCodewords(CharSequence charSequence) {
        StringBuilder sb = (StringBuilder) charSequence;
        int length = sb.length();
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

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0051, code lost:
    
        com.google.zxing.datamatrix.encoder.HighLevelEncoder.illegalCharacter(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0054, code lost:
    
        throw null;
     */
    @Override // com.google.zxing.datamatrix.encoder.Encoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void encode(com.google.zxing.datamatrix.encoder.EncoderContext r11) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.EdifactEncoder.encode(com.google.zxing.datamatrix.encoder.EncoderContext):void");
    }
}
