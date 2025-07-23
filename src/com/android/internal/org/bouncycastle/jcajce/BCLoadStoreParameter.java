package com.android.internal.org.bouncycastle.jcajce;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

/* loaded from: classes5.dex */
public class BCLoadStoreParameter implements KeyStore.LoadStoreParameter {
    private final InputStream in;
    private final OutputStream out;
    private final KeyStore.ProtectionParameter protectionParameter;

    public BCLoadStoreParameter(OutputStream outputStream, char[] cArr) {
        this(outputStream, new KeyStore.PasswordProtection(cArr));
    }

    public BCLoadStoreParameter(InputStream inputStream, char[] cArr) {
        this(inputStream, new KeyStore.PasswordProtection(cArr));
    }

    public BCLoadStoreParameter(InputStream inputStream, KeyStore.ProtectionParameter protectionParameter) {
        this(inputStream, null, protectionParameter);
    }

    public BCLoadStoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter) {
        this(null, outputStream, protectionParameter);
    }

    BCLoadStoreParameter(InputStream inputStream, OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter) {
        this.in = inputStream;
        this.out = outputStream;
        this.protectionParameter = protectionParameter;
    }

    @Override // java.security.KeyStore.LoadStoreParameter
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return this.protectionParameter;
    }

    public OutputStream getOutputStream() {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            return outputStream;
        }
        throw new UnsupportedOperationException("parameter not configured for storage - no OutputStream");
    }

    public InputStream getInputStream() {
        if (this.out != null) {
            throw new UnsupportedOperationException("parameter configured for storage OutputStream present");
        }
        return this.in;
    }
}
