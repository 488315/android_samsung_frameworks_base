package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class Token {
    public static final SimpleToken EMPTY = new SimpleToken(null, 0, 0);
    public final Token previous;

    public Token(Token token) {
        this.previous = token;
    }

    public abstract void appendTo(BitArray bitArray, byte[] bArr);
}
