package com.android.internal.org.bouncycastle.crypto.io;

import com.android.internal.org.bouncycastle.crypto.Mac;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class MacInputStream extends FilterInputStream {
    protected Mac mac;

    public MacInputStream(InputStream inputStream, Mac mac) {
        super(inputStream);
        this.mac = mac;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int read = this.in.read();
        if (read >= 0) {
            this.mac.update((byte) read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (read >= 0) {
            this.mac.update(bArr, i, read);
        }
        return read;
    }

    public Mac getMac() {
        return this.mac;
    }
}
