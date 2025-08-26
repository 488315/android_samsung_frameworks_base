package com.android.internal.org.bouncycastle.jcajce.io;

import com.android.internal.org.bouncycastle.util.Exceptions;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;

/* loaded from: classes5.dex */
class SignatureUpdatingOutputStream extends OutputStream {
    private Signature sig;

    SignatureUpdatingOutputStream(Signature signature) {
        this.sig = signature;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws SignatureException, IOException {
        try {
            this.sig.update(bArr, i, i2);
        } catch (SignatureException e) {
            throw Exceptions.ioException(e.getMessage(), e);
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws SignatureException, IOException {
        try {
            this.sig.update(bArr);
        } catch (SignatureException e) {
            throw Exceptions.ioException(e.getMessage(), e);
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws SignatureException, IOException {
        try {
            this.sig.update((byte) i);
        } catch (SignatureException e) {
            throw Exceptions.ioException(e.getMessage(), e);
        }
    }
}
