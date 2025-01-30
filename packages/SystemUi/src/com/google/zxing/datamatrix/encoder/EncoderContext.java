package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import java.nio.charset.Charset;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EncoderContext {
    public final StringBuilder codewords;
    public Dimension maxSize;
    public Dimension minSize;
    public final String msg;
    public int newEncoding;
    public int pos;
    public SymbolShapeHint shape;
    public int skipAtEnd;
    public SymbolInfo symbolInfo;

    public EncoderContext(String str) {
        byte[] bytes = str.getBytes(Charset.forName("ISO-8859-1"));
        StringBuilder sb = new StringBuilder(bytes.length);
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            char c = (char) (bytes[i] & 255);
            if (c == '?' && str.charAt(i) != '?') {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
            sb.append(c);
        }
        this.msg = sb.toString();
        this.shape = SymbolShapeHint.FORCE_NONE;
        this.codewords = new StringBuilder(str.length());
        this.newEncoding = -1;
    }

    public final int getCodewordCount() {
        return this.codewords.length();
    }

    public final char getCurrentChar() {
        return this.msg.charAt(this.pos);
    }

    public final boolean hasMoreCharacters() {
        return this.pos < this.msg.length() - this.skipAtEnd;
    }

    public final void updateSymbolInfo(int i) {
        SymbolInfo symbolInfo = this.symbolInfo;
        if (symbolInfo == null || i > symbolInfo.dataCapacity) {
            this.symbolInfo = SymbolInfo.lookup(i, this.shape, this.minSize, this.maxSize);
        }
    }

    public final void writeCodeword(char c) {
        this.codewords.append(c);
    }
}
