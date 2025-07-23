package com.android.internal.org.bouncycastle.jcajce;

import java.io.OutputStream;
import java.security.KeyStore;

/* loaded from: classes5.dex */
public class PKCS12StoreParameter implements KeyStore.LoadStoreParameter {
    private final boolean forDEREncoding;
    private final OutputStream out;
    private final KeyStore.ProtectionParameter protectionParameter;

    public PKCS12StoreParameter(OutputStream outputStream, char[] cArr) {
        this(outputStream, cArr, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter) {
        this(outputStream, protectionParameter, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, char[] cArr, boolean z) {
        this(outputStream, new KeyStore.PasswordProtection(cArr), z);
    }

    public PKCS12StoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter, boolean z) {
        this.out = outputStream;
        this.protectionParameter = protectionParameter;
        this.forDEREncoding = z;
    }

    public OutputStream getOutputStream() {
        return this.out;
    }

    @Override // java.security.KeyStore.LoadStoreParameter
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return this.protectionParameter;
    }

    public boolean isForDEREncoding() {
        return this.forDEREncoding;
    }
}
