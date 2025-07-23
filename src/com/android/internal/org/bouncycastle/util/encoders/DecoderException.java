package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes5.dex */
public class DecoderException extends IllegalStateException {
    private Throwable cause;

    DecoderException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
