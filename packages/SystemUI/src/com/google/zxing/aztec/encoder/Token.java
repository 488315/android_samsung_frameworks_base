package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class Token {
    public static final SimpleToken EMPTY = new SimpleToken(null, 0, 0);
    public final Token previous;

    public Token(Token token) {
        this.previous = token;
    }

    public abstract void appendTo(BitArray bitArray, byte[] bArr);
}
