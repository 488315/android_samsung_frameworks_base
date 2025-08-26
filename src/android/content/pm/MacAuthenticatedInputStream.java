package android.content.pm;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.Mac;

/* loaded from: classes.dex */
public class MacAuthenticatedInputStream extends FilterInputStream {
    private final Mac mMac;

    public MacAuthenticatedInputStream(InputStream inputStream, Mac mac) {
        super(inputStream);
        this.mMac = mac;
    }

    public boolean isTagEqual(byte[] bArr) throws IllegalStateException {
        byte[] bArrDoFinal = this.mMac.doFinal();
        if (bArr != null && bArrDoFinal != null && bArr.length == bArrDoFinal.length) {
            int i = 0;
            for (int i2 = 0; i2 < bArr.length; i2++) {
                i |= bArr[i2] ^ bArrDoFinal[i2];
            }
            if (i == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IllegalStateException, IOException {
        int i = super.read();
        if (i >= 0) {
            this.mMac.update((byte) i);
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IllegalStateException, IOException {
        int i3 = super.read(bArr, i, i2);
        if (i3 > 0) {
            this.mMac.update(bArr, i, i3);
        }
        return i3;
    }
}
