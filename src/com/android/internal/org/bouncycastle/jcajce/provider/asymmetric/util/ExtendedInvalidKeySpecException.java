package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

import java.security.spec.InvalidKeySpecException;

/* loaded from: classes5.dex */
public class ExtendedInvalidKeySpecException extends InvalidKeySpecException {
    private Throwable cause;

    public ExtendedInvalidKeySpecException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
