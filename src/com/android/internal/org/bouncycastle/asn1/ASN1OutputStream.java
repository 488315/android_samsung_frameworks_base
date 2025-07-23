package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class ASN1OutputStream {
    private OutputStream os;

    static int getLengthOfDL(int i) {
        if (i < 128) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 8;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    static int getLengthOfIdentifier(int i) {
        if (i < 31) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 7;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    void flushInternal() throws IOException {
    }

    public static ASN1OutputStream create(OutputStream outputStream) {
        return new ASN1OutputStream(outputStream);
    }

    public static ASN1OutputStream create(OutputStream outputStream, String str) {
        if (str.equals(ASN1Encoding.DER)) {
            return new DEROutputStream(outputStream);
        }
        if (str.equals(ASN1Encoding.DL)) {
            return new DLOutputStream(outputStream);
        }
        return new ASN1OutputStream(outputStream);
    }

    ASN1OutputStream(OutputStream outputStream) {
        this.os = outputStream;
    }

    public void close() throws IOException {
        this.os.close();
    }

    public void flush() throws IOException {
        this.os.flush();
    }

    public final void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(aSN1Encodable.toASN1Primitive(), true);
        flushInternal();
    }

    public final void writeObject(ASN1Primitive aSN1Primitive) throws IOException {
        if (aSN1Primitive == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(aSN1Primitive, true);
        flushInternal();
    }

    DEROutputStream getDERSubStream() {
        return new DEROutputStream(this.os);
    }

    DLOutputStream getDLSubStream() {
        return new DLOutputStream(this.os);
    }

    final void writeDL(int i) throws IOException {
        if (i < 128) {
            write(i);
            return;
        }
        int i2 = 5;
        byte[] bArr = new byte[5];
        while (true) {
            int i3 = i2 - 1;
            bArr[i3] = (byte) i;
            i >>>= 8;
            if (i == 0) {
                int i4 = i2 - 2;
                bArr[i4] = (byte) ((5 - i3) | 128);
                write(bArr, i4, 6 - i3);
                return;
            }
            i2 = i3;
        }
    }

    final void write(int i) throws IOException {
        this.os.write(i);
    }

    final void write(byte[] bArr, int i, int i2) throws IOException {
        this.os.write(bArr, i, i2);
    }

    void writeElements(ASN1Encodable[] aSN1EncodableArr) throws IOException {
        for (ASN1Encodable aSN1Encodable : aSN1EncodableArr) {
            aSN1Encodable.toASN1Primitive().encode(this, true);
        }
    }

    final void writeEncodingDL(boolean z, int i, byte b) throws IOException {
        writeIdentifier(z, i);
        writeDL(1);
        write(b);
    }

    final void writeEncodingDL(boolean z, int i, byte[] bArr) throws IOException {
        writeIdentifier(z, i);
        writeDL(bArr.length);
        write(bArr, 0, bArr.length);
    }

    final void writeEncodingDL(boolean z, int i, byte[] bArr, int i2, int i3) throws IOException {
        writeIdentifier(z, i);
        writeDL(i3);
        write(bArr, i2, i3);
    }

    final void writeEncodingDL(boolean z, int i, byte b, byte[] bArr, int i2, int i3) throws IOException {
        writeIdentifier(z, i);
        writeDL(i3 + 1);
        write(b);
        write(bArr, i2, i3);
    }

    final void writeEncodingDL(boolean z, int i, byte[] bArr, int i2, int i3, byte b) throws IOException {
        writeIdentifier(z, i);
        writeDL(i3 + 1);
        write(bArr, i2, i3);
        write(b);
    }

    final void writeEncodingDL(boolean z, int i, int i2, byte[] bArr) throws IOException {
        writeIdentifier(z, i, i2);
        writeDL(bArr.length);
        write(bArr, 0, bArr.length);
    }

    final void writeEncodingIL(boolean z, int i, ASN1Encodable[] aSN1EncodableArr) throws IOException {
        writeIdentifier(z, i);
        write(128);
        writeElements(aSN1EncodableArr);
        write(0);
        write(0);
    }

    final void writeIdentifier(boolean z, int i) throws IOException {
        if (z) {
            write(i);
        }
    }

    final void writeIdentifier(boolean z, int i, int i2) throws IOException {
        if (z) {
            if (i2 < 31) {
                write(i | i2);
                return;
            }
            byte[] bArr = new byte[6];
            int i3 = 5;
            bArr[5] = (byte) (i2 & 127);
            while (i2 > 127) {
                i2 >>>= 7;
                i3--;
                bArr[i3] = (byte) ((i2 & 127) | 128);
            }
            int i4 = i3 - 1;
            bArr[i4] = (byte) (31 | i);
            write(bArr, i4, 6 - i4);
        }
    }

    void writePrimitive(ASN1Primitive aSN1Primitive, boolean z) throws IOException {
        aSN1Primitive.encode(this, z);
    }

    void writePrimitives(ASN1Primitive[] aSN1PrimitiveArr) throws IOException {
        for (ASN1Primitive aSN1Primitive : aSN1PrimitiveArr) {
            aSN1Primitive.encode(this, true);
        }
    }

    static int getLengthOfEncodingDL(boolean z, int i) {
        return (z ? 1 : 0) + getLengthOfDL(i) + i;
    }
}
