package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;

/* loaded from: classes5.dex */
public class NullDigest implements Digest {
    private OpenByteArrayOutputStream bOut = new OpenByteArrayOutputStream();

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "NULL";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.bOut.size();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte b) {
        this.bOut.write(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        this.bOut.write(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        int size = this.bOut.size();
        this.bOut.copy(bArr, i);
        reset();
        return size;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        this.bOut.reset();
    }

    private static class OpenByteArrayOutputStream extends ByteArrayOutputStream {
        private OpenByteArrayOutputStream() {
        }

        @Override // java.io.ByteArrayOutputStream
        public void reset() {
            super.reset();
            Arrays.clear(this.buf);
        }

        void copy(byte[] bArr, int i) {
            System.arraycopy(this.buf, 0, bArr, i, size());
        }
    }
}
