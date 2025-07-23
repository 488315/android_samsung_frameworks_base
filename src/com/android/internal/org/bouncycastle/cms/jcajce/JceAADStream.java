package com.android.internal.org.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import javax.crypto.Cipher;

/* loaded from: classes5.dex */
class JceAADStream extends OutputStream {
    private static final byte[] SINGLE_BYTE = new byte[1];
    private Cipher cipher;

    JceAADStream(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.cipher.updateAAD(bArr, i, i2);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = SINGLE_BYTE;
        bArr[0] = (byte) i;
        this.cipher.updateAAD(bArr, 0, 1);
    }
}
