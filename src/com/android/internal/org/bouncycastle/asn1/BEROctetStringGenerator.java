package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class BEROctetStringGenerator extends BERGenerator {
    public BEROctetStringGenerator(OutputStream outputStream) throws IOException {
        super(outputStream);
        writeBERHeader(36);
    }

    public BEROctetStringGenerator(OutputStream outputStream, int i, boolean z) throws IOException {
        super(outputStream, i, z);
        writeBERHeader(36);
    }

    public OutputStream getOctetOutputStream() {
        return getOctetOutputStream(new byte[1000]);
    }

    public OutputStream getOctetOutputStream(byte[] bArr) {
        return new BufferedBEROctetStream(bArr);
    }

    private class BufferedBEROctetStream extends OutputStream {
        private byte[] _buf;
        private DEROutputStream _derOut;
        private int _off = 0;

        BufferedBEROctetStream(byte[] bArr) {
            this._buf = bArr;
            this._derOut = new DEROutputStream(BEROctetStringGenerator.this._out);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            byte[] bArr = this._buf;
            int i2 = this._off;
            int i3 = i2 + 1;
            this._off = i3;
            bArr[i2] = (byte) i;
            if (i3 == bArr.length) {
                DEROctetString.encode(this._derOut, true, bArr, 0, bArr.length);
                this._off = 0;
            }
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            byte[] bArr2 = this._buf;
            int length = bArr2.length;
            int i3 = this._off;
            int i4 = length - i3;
            if (i2 < i4) {
                System.arraycopy(bArr, i, bArr2, i3, i2);
                this._off += i2;
                return;
            }
            if (i3 > 0) {
                System.arraycopy(bArr, i, bArr2, i3, i4);
                DEROctetString.encode(this._derOut, true, this._buf, 0, length);
            } else {
                i4 = 0;
            }
            while (true) {
                int i5 = i2 - i4;
                if (i5 >= length) {
                    DEROctetString.encode(this._derOut, true, bArr, i + i4, length);
                    i4 += length;
                } else {
                    System.arraycopy(bArr, i + i4, this._buf, 0, i5);
                    this._off = i5;
                    return;
                }
            }
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            int i = this._off;
            if (i != 0) {
                DEROctetString.encode(this._derOut, true, this._buf, 0, i);
            }
            this._derOut.flushInternal();
            BEROctetStringGenerator.this.writeBEREnd();
        }
    }
}
