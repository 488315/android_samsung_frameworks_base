package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

public class ASN1OutputStream {
    private OutputStream os;

    public static ASN1OutputStream create(OutputStream out) {
        return new ASN1OutputStream(out);
    }

    public static ASN1OutputStream create(OutputStream out, String encoding) {
        if (encoding.equals(ASN1Encoding.DER)) {
            return new DEROutputStream(out);
        }
        if (encoding.equals(ASN1Encoding.DL)) {
            return new DLOutputStream(out);
        }
        return new ASN1OutputStream(out);
    }

    public ASN1OutputStream(OutputStream os) {
        this.os = os;
    }

    final void writeLength(int length) throws IOException {
        if (length > 127) {
            int size = 1;
            int val = length;
            while (true) {
                int i = val >>> 8;
                val = i;
                if (i == 0) {
                    break;
                } else {
                    size++;
                }
            }
            write((byte) (size | 128));
            for (int i2 = (size - 1) * 8; i2 >= 0; i2 -= 8) {
                write((byte) (length >> i2));
            }
            return;
        }
        write((byte) length);
    }

    final void write(int b) throws IOException {
        this.os.write(b);
    }

    final void write(byte[] bytes, int off, int len) throws IOException {
        this.os.write(bytes, off, len);
    }

    final void writeElements(ASN1Encodable[] elements) throws IOException {
        for (ASN1Encodable aSN1Encodable : elements) {
            ASN1Primitive primitive = aSN1Encodable.toASN1Primitive();
            writePrimitive(primitive, true);
        }
    }

    final void writeElements(Enumeration elements) throws IOException {
        while (elements.hasMoreElements()) {
            ASN1Primitive primitive = ((ASN1Encodable) elements.nextElement()).toASN1Primitive();
            writePrimitive(primitive, true);
        }
    }

    final void writeEncoded(boolean withTag, int tag, byte contents) throws IOException {
        if (withTag) {
            write(tag);
        }
        writeLength(1);
        write(contents);
    }

    final void writeEncoded(boolean withTag, int tag, byte[] contents) throws IOException {
        if (withTag) {
            write(tag);
        }
        writeLength(contents.length);
        write(contents, 0, contents.length);
    }

    final void writeEncoded(
            boolean withTag, int tag, byte[] contents, int contentsOff, int contentsLen)
            throws IOException {
        if (withTag) {
            write(tag);
        }
        writeLength(contentsLen);
        write(contents, contentsOff, contentsLen);
    }

    final void writeEncoded(boolean withTag, int tag, byte headByte, byte[] tailBytes)
            throws IOException {
        if (withTag) {
            write(tag);
        }
        writeLength(tailBytes.length + 1);
        write(headByte);
        write(tailBytes, 0, tailBytes.length);
    }

    final void writeEncoded(
            boolean withTag,
            int tag,
            byte headByte,
            byte[] body,
            int bodyOff,
            int bodyLen,
            byte tailByte)
            throws IOException {
        if (withTag) {
            write(tag);
        }
        writeLength(bodyLen + 2);
        write(headByte);
        write(body, bodyOff, bodyLen);
        write(tailByte);
    }

    final void writeEncoded(boolean withTag, int flags, int tagNo, byte[] contents)
            throws IOException {
        writeTag(withTag, flags, tagNo);
        writeLength(contents.length);
        write(contents, 0, contents.length);
    }

    final void writeEncodedIndef(boolean withTag, int flags, int tagNo, byte[] contents)
            throws IOException {
        writeTag(withTag, flags, tagNo);
        write(128);
        write(contents, 0, contents.length);
        write(0);
        write(0);
    }

    final void writeEncodedIndef(boolean withTag, int tag, ASN1Encodable[] elements)
            throws IOException {
        if (withTag) {
            write(tag);
        }
        write(128);
        writeElements(elements);
        write(0);
        write(0);
    }

    final void writeEncodedIndef(boolean withTag, int tag, Enumeration elements)
            throws IOException {
        if (withTag) {
            write(tag);
        }
        write(128);
        writeElements(elements);
        write(0);
        write(0);
    }

    final void writeTag(boolean withTag, int flags, int tagNo) throws IOException {
        if (!withTag) {
            return;
        }
        if (tagNo < 31) {
            write(flags | tagNo);
            return;
        }
        write(flags | 31);
        if (tagNo < 128) {
            write(tagNo);
            return;
        }
        byte[] stack = new byte[5];
        int pos = stack.length - 1;
        stack[pos] = (byte) (tagNo & 127);
        do {
            tagNo >>= 7;
            pos--;
            stack[pos] = (byte) ((tagNo & 127) | 128);
        } while (tagNo > 127);
        write(stack, pos, stack.length - pos);
    }

    public void writeObject(ASN1Encodable obj) throws IOException {
        if (obj == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(obj.toASN1Primitive(), true);
        flushInternal();
    }

    public void writeObject(ASN1Primitive primitive) throws IOException {
        if (primitive == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(primitive, true);
        flushInternal();
    }

    void writePrimitive(ASN1Primitive primitive, boolean withTag) throws IOException {
        primitive.encode(this, withTag);
    }

    public void close() throws IOException {
        this.os.close();
    }

    public void flush() throws IOException {
        this.os.flush();
    }

    void flushInternal() throws IOException {}

    DEROutputStream getDERSubStream() {
        return new DEROutputStream(this.os);
    }

    ASN1OutputStream getDLSubStream() {
        return new DLOutputStream(this.os);
    }
}
