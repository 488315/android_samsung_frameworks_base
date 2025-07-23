package com.android.internal.org.bouncycastle.util.encoders;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class HexEncoder implements Encoder {
    protected final byte[] encodingTable = {SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, 50, 51, 52, 53, 54, 55, 56, 57, SprAttributeBase.TYPE_ANIMATOR_SET, 98, 99, 100, 101, 102};
    protected final byte[] decodingTable = new byte[128];

    private static boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int getEncodedLength(int i) {
        return i * 2;
    }

    protected void initialiseDecodingTable() {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.decodingTable;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = -1;
            i2++;
        }
        while (true) {
            byte[] bArr2 = this.encodingTable;
            if (i < bArr2.length) {
                this.decodingTable[bArr2[i]] = (byte) i;
                i++;
            } else {
                byte[] bArr3 = this.decodingTable;
                bArr3[65] = bArr3[97];
                bArr3[66] = bArr3[98];
                bArr3[67] = bArr3[99];
                bArr3[68] = bArr3[100];
                bArr3[69] = bArr3[101];
                bArr3[70] = bArr3[102];
                return;
            }
        }
    }

    public HexEncoder() {
        initialiseDecodingTable();
    }

    public int encode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IOException {
        int i4 = i2 + i;
        int i5 = i3;
        while (i < i4) {
            int i6 = i + 1;
            byte b = bArr[i];
            int i7 = i5 + 1;
            byte[] bArr3 = this.encodingTable;
            bArr2[i5] = bArr3[(b & 255) >>> 4];
            i5 += 2;
            bArr2[i7] = bArr3[b & 15];
            i = i6;
        }
        return i5 - i3;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int getMaxDecodedLength(int i) {
        return i / 2;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int encode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        if (i2 < 0) {
            return 0;
        }
        byte[] bArr2 = new byte[72];
        int i3 = i;
        int i4 = i2;
        while (i4 > 0) {
            int min = Math.min(36, i4);
            HexEncoder hexEncoder = this;
            outputStream.write(bArr2, 0, hexEncoder.encode(bArr, i3, min, bArr2, 0));
            i3 += min;
            i4 -= min;
            this = hexEncoder;
        }
        return i2 * 2;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        byte[] bArr2 = new byte[36];
        int i3 = i2 + i;
        while (i3 > i && ignore((char) bArr[i3 - 1])) {
            i3--;
        }
        int i4 = 0;
        int i5 = 0;
        while (i < i3) {
            while (i < i3 && ignore((char) bArr[i])) {
                i++;
            }
            int i6 = i + 1;
            byte b = this.decodingTable[bArr[i]];
            while (i6 < i3 && ignore((char) bArr[i6])) {
                i6++;
            }
            int i7 = i6 + 1;
            byte b2 = this.decodingTable[bArr[i6]];
            if ((b | b2) < 0) {
                throw new IOException("invalid characters encountered in Hex data");
            }
            int i8 = i4 + 1;
            bArr2[i4] = (byte) ((b << 4) | b2);
            if (i8 == 36) {
                outputStream.write(bArr2);
                i4 = 0;
            } else {
                i4 = i8;
            }
            i5++;
            i = i7;
        }
        if (i4 > 0) {
            outputStream.write(bArr2, 0, i4);
        }
        return i5;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(String str, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[36];
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            while (i < length && ignore(str.charAt(i))) {
                i++;
            }
            int i4 = i + 1;
            byte b = this.decodingTable[str.charAt(i)];
            while (i4 < length && ignore(str.charAt(i4))) {
                i4++;
            }
            int i5 = i4 + 1;
            byte b2 = this.decodingTable[str.charAt(i4)];
            if ((b | b2) < 0) {
                throw new IOException("invalid characters encountered in Hex string");
            }
            int i6 = i2 + 1;
            bArr[i2] = (byte) ((b << 4) | b2);
            if (i6 == 36) {
                outputStream.write(bArr);
                i2 = 0;
            } else {
                i2 = i6;
            }
            i3++;
            i = i5;
        }
        if (i2 > 0) {
            outputStream.write(bArr, 0, i2);
        }
        return i3;
    }

    byte[] decodeStrict(String str, int i, int i2) throws IOException {
        if (str == null) {
            throw new NullPointerException("'str' cannot be null");
        }
        if (i < 0 || i2 < 0 || i > str.length() - i2) {
            throw new IndexOutOfBoundsException("invalid offset and/or length specified");
        }
        if ((i2 & 1) != 0) {
            throw new IOException("a hexadecimal encoding must have an even number of characters");
        }
        int i3 = i2 >>> 1;
        byte[] bArr = new byte[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i + 1;
            byte b = this.decodingTable[str.charAt(i)];
            i += 2;
            int i6 = (b << 4) | this.decodingTable[str.charAt(i5)];
            if (i6 < 0) {
                throw new IOException("invalid characters encountered in Hex string");
            }
            bArr[i4] = (byte) i6;
        }
        return bArr;
    }
}
