package com.android.internal.org.bouncycastle.crypto.io;

import com.android.internal.org.bouncycastle.crypto.Digest;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class DigestInputStream extends FilterInputStream {
    protected Digest digest;

    public DigestInputStream(InputStream inputStream, Digest digest) {
        super(inputStream);
        this.digest = digest;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int read = this.in.read();
        if (read >= 0) {
            this.digest.update((byte) read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (read > 0) {
            this.digest.update(bArr, i, read);
        }
        return read;
    }

    public Digest getDigest() {
        return this.digest;
    }
}
