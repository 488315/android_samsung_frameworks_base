package com.android.internal.org.bouncycastle.util.encoders;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

import java.io.IOException;
import java.io.OutputStream;

public class Base64Encoder implements Encoder {
    protected final byte[] encodingTable = {
        65,
        66,
        67,
        68,
        69,
        70,
        71,
        72,
        73,
        74,
        75,
        76,
        77,
        78,
        79,
        80,
        81,
        82,
        83,
        84,
        85,
        86,
        87,
        88,
        89,
        90,
        SprAttributeBase.TYPE_ANIMATOR_SET,
        98,
        99,
        100,
        101,
        102,
        103,
        104,
        105,
        106,
        107,
        108,
        109,
        110,
        111,
        SprAttributeBase.TYPE_SHADOW,
        113,
        114,
        115,
        116,
        117,
        118,
        119,
        120,
        121,
        122,
        SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
        SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33,
        50,
        51,
        52,
        53,
        54,
        55,
        56,
        57,
        SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33,
        SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80
    };
    protected byte padding = 61;
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable() {
        for (int i = 0; i < this.decodingTable.length; i++) {
            this.decodingTable[i] = -1;
        }
        for (int i2 = 0; i2 < this.encodingTable.length; i2++) {
            this.decodingTable[this.encodingTable[i2]] = (byte) i2;
        }
    }

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    public int encode(byte[] inBuf, int inOff, int inLen, byte[] outBuf, int outOff)
            throws IOException {
        int a1 = inOff;
        int inEnd = (inOff + inLen) - 2;
        int outPos = outOff;
        while (a1 < inEnd) {
            int inPos = a1 + 1;
            int a12 = inBuf[a1];
            int inPos2 = inPos + 1;
            int a2 = inBuf[inPos] & 255;
            int inPos3 = inPos2 + 1;
            int a3 = inBuf[inPos2] & 255;
            int outPos2 = outPos + 1;
            outBuf[outPos] = this.encodingTable[(a12 >>> 2) & 63];
            int outPos3 = outPos2 + 1;
            outBuf[outPos2] = this.encodingTable[((a12 << 4) | (a2 >>> 4)) & 63];
            int outPos4 = outPos3 + 1;
            outBuf[outPos3] = this.encodingTable[((a2 << 2) | (a3 >>> 6)) & 63];
            outPos = outPos4 + 1;
            outBuf[outPos4] = this.encodingTable[a3 & 63];
            a1 = inPos3;
        }
        switch (inLen - (a1 - inOff)) {
            case 1:
                int a22 = a1 + 1;
                int inPos4 = inBuf[a1];
                int a13 = inPos4 & 255;
                int outPos5 = outPos + 1;
                outBuf[outPos] = this.encodingTable[(a13 >>> 2) & 63];
                int outPos6 = outPos5 + 1;
                outBuf[outPos5] = this.encodingTable[(a13 << 4) & 63];
                int outPos7 = outPos6 + 1;
                outBuf[outPos6] = this.padding;
                outPos = outPos7 + 1;
                outBuf[outPos7] = this.padding;
                break;
            case 2:
                int inPos5 = a1 + 1;
                int inPos6 = inBuf[a1];
                int a14 = inPos6 & 255;
                int i = inPos5 + 1;
                int a23 = inBuf[inPos5] & 255;
                int outPos8 = outPos + 1;
                outBuf[outPos] = this.encodingTable[(a14 >>> 2) & 63];
                int outPos9 = outPos8 + 1;
                outBuf[outPos8] = this.encodingTable[((a14 << 4) | (a23 >>> 4)) & 63];
                int outPos10 = outPos9 + 1;
                outBuf[outPos9] = this.encodingTable[(a23 << 2) & 63];
                outPos = outPos10 + 1;
                outBuf[outPos10] = this.padding;
                break;
        }
        int inPos7 = outPos - outOff;
        return inPos7;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int encode(byte[] buf, int off, int len, OutputStream out) throws IOException {
        byte[] tmp = new byte[72];
        while (len > 0) {
            int inLen = Math.min(54, len);
            int outLen = encode(buf, off, inLen, tmp, 0);
            out.write(tmp, 0, outLen);
            off += inLen;
            len -= inLen;
        }
        return ((len + 2) / 3) * 4;
    }

    private boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] data, int off, int length, OutputStream out) throws IOException {
        byte[] outBuffer = new byte[54];
        int end = off + length;
        while (end > off && ignore((char) data[end - 1])) {
            end--;
        }
        if (end == 0) {
            return 0;
        }
        int i = 0;
        int finish = end;
        while (finish > off && i != 4) {
            if (!ignore((char) data[finish - 1])) {
                i++;
            }
            finish--;
        }
        int i2 = nextI(data, off, finish);
        int bufOff = 0;
        int outLen = 0;
        int i3 = i2;
        while (i3 < finish) {
            byte b1 = this.decodingTable[data[i3]];
            int i4 = nextI(data, i3 + 1, finish);
            int i5 = i4 + 1;
            byte b2 = this.decodingTable[data[i4]];
            int i6 = nextI(data, i5, finish);
            int i7 = i6 + 1;
            byte b3 = this.decodingTable[data[i6]];
            int i8 = nextI(data, i7, finish);
            int i9 = i8 + 1;
            byte b4 = this.decodingTable[data[i8]];
            if ((b1 | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            int bufOff2 = bufOff + 1;
            outBuffer[bufOff] = (byte) ((b1 << 2) | (b2 >> 4));
            int bufOff3 = bufOff2 + 1;
            outBuffer[bufOff2] = (byte) ((b2 << 4) | (b3 >> 2));
            int bufOff4 = bufOff3 + 1;
            outBuffer[bufOff3] = (byte) ((b3 << 6) | b4);
            if (bufOff4 != outBuffer.length) {
                bufOff = bufOff4;
            } else {
                out.write(outBuffer);
                bufOff = 0;
            }
            outLen += 3;
            i3 = nextI(data, i9, finish);
        }
        if (bufOff > 0) {
            out.write(outBuffer, 0, bufOff);
        }
        int e0 = nextI(data, i3, end);
        int e1 = nextI(data, e0 + 1, end);
        int e2 = nextI(data, e1 + 1, end);
        int e3 = nextI(data, e2 + 1, end);
        return outLen
                + decodeLastBlock(
                        out, (char) data[e0], (char) data[e1], (char) data[e2], (char) data[e3]);
    }

    private int nextI(byte[] data, int i, int finish) {
        while (i < finish && ignore((char) data[i])) {
            i++;
        }
        return i;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(String data, OutputStream out) throws IOException {
        byte[] outBuffer = new byte[54];
        int end = data.length();
        while (end > 0 && ignore(data.charAt(end - 1))) {
            end--;
        }
        if (end == 0) {
            return 0;
        }
        int i = 0;
        int finish = end;
        while (finish > 0 && i != 4) {
            if (!ignore(data.charAt(finish - 1))) {
                i++;
            }
            finish--;
        }
        int i2 = nextI(data, 0, finish);
        int bufOff = 0;
        int length = 0;
        int i3 = i2;
        while (i3 < finish) {
            byte b1 = this.decodingTable[data.charAt(i3)];
            int i4 = nextI(data, i3 + 1, finish);
            int i5 = i4 + 1;
            byte b2 = this.decodingTable[data.charAt(i4)];
            int i6 = nextI(data, i5, finish);
            int i7 = i6 + 1;
            byte b3 = this.decodingTable[data.charAt(i6)];
            int i8 = nextI(data, i7, finish);
            int i9 = i8 + 1;
            byte b4 = this.decodingTable[data.charAt(i8)];
            if ((b1 | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            int bufOff2 = bufOff + 1;
            outBuffer[bufOff] = (byte) ((b1 << 2) | (b2 >> 4));
            int bufOff3 = bufOff2 + 1;
            outBuffer[bufOff2] = (byte) ((b2 << 4) | (b3 >> 2));
            int bufOff4 = bufOff3 + 1;
            outBuffer[bufOff3] = (byte) ((b3 << 6) | b4);
            length += 3;
            if (bufOff4 == outBuffer.length) {
                out.write(outBuffer);
                bufOff4 = 0;
            }
            bufOff = bufOff4;
            i3 = nextI(data, i9, finish);
        }
        if (bufOff > 0) {
            out.write(outBuffer, 0, bufOff);
        }
        int e0 = nextI(data, i3, end);
        int e1 = nextI(data, e0 + 1, end);
        int e2 = nextI(data, e1 + 1, end);
        int e3 = nextI(data, e2 + 1, end);
        return length
                + decodeLastBlock(
                        out, data.charAt(e0), data.charAt(e1), data.charAt(e2), data.charAt(e3));
    }

    private int decodeLastBlock(OutputStream out, char c1, char c2, char c3, char c4)
            throws IOException {
        if (c3 == this.padding) {
            if (c4 != this.padding) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            byte b1 = this.decodingTable[c1];
            byte b2 = this.decodingTable[c2];
            if ((b1 | b2) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            out.write((b1 << 2) | (b2 >> 4));
            return 1;
        }
        if (c4 == this.padding) {
            byte b12 = this.decodingTable[c1];
            byte b22 = this.decodingTable[c2];
            byte b3 = this.decodingTable[c3];
            if ((b12 | b22 | b3) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            out.write((b12 << 2) | (b22 >> 4));
            out.write((b22 << 4) | (b3 >> 2));
            return 2;
        }
        byte b13 = this.decodingTable[c1];
        byte b23 = this.decodingTable[c2];
        byte b32 = this.decodingTable[c3];
        byte b4 = this.decodingTable[c4];
        if ((b13 | b23 | b32 | b4) < 0) {
            throw new IOException("invalid characters encountered at end of base64 data");
        }
        out.write((b13 << 2) | (b23 >> 4));
        out.write((b23 << 4) | (b32 >> 2));
        out.write((b32 << 6) | b4);
        return 3;
    }

    private int nextI(String data, int i, int finish) {
        while (i < finish && ignore(data.charAt(i))) {
            i++;
        }
        return i;
    }
}
