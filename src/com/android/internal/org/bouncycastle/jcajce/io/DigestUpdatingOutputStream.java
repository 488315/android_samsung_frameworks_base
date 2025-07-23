package com.android.internal.org.bouncycastle.jcajce.io;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

/* loaded from: classes5.dex */
class DigestUpdatingOutputStream extends OutputStream {
    private MessageDigest digest;

    DigestUpdatingOutputStream(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.digest.update(bArr, i, i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        this.digest.update(bArr);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        this.digest.update((byte) i);
    }
}
