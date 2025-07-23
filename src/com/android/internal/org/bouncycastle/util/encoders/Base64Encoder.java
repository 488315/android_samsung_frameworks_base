package com.android.internal.org.bouncycastle.util.encoders;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class Base64Encoder implements Encoder {
    protected final byte[] encodingTable = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, SprAttributeBase.TYPE_ANIMATOR_SET, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, SprAttributeBase.TYPE_SHADOW, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, 50, 51, 52, 53, 54, 55, 56, 57, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80};
    protected byte padding = 61;
    protected final byte[] decodingTable = new byte[128];

    private boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
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
            if (i >= bArr2.length) {
                return;
            }
            this.decodingTable[bArr2[i]] = (byte) i;
            i++;
        }
    }

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    public int encode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IOException {
        int i4 = (i + i2) - 2;
        int i5 = i;
        int i6 = i3;
        while (i5 < i4) {
            byte b = bArr[i5];
            int i7 = i5 + 2;
            int i8 = bArr[i5 + 1] & 255;
            i5 += 3;
            byte b2 = bArr[i7];
            byte[] bArr3 = this.encodingTable;
            bArr2[i6] = bArr3[(b >>> 2) & 63];
            bArr2[i6 + 1] = bArr3[((b << 4) | (i8 >>> 4)) & 63];
            int i9 = i6 + 3;
            bArr2[i6 + 2] = bArr3[((i8 << 2) | ((b2 & 255) >>> 6)) & 63];
            i6 += 4;
            bArr2[i9] = bArr3[b2 & 63];
        }
        int i10 = i2 - (i5 - i);
        if (i10 == 1) {
            int i11 = bArr[i5] & 255;
            byte[] bArr4 = this.encodingTable;
            bArr2[i6] = bArr4[(i11 >>> 2) & 63];
            bArr2[i6 + 1] = bArr4[(i11 << 4) & 63];
            int i12 = i6 + 3;
            byte b3 = this.padding;
            bArr2[i6 + 2] = b3;
            i6 += 4;
            bArr2[i12] = b3;
        } else if (i10 == 2) {
            int i13 = bArr[i5] & 255;
            int i14 = bArr[i5 + 1] & 255;
            byte[] bArr5 = this.encodingTable;
            bArr2[i6] = bArr5[(i13 >>> 2) & 63];
            bArr2[i6 + 1] = bArr5[((i13 << 4) | (i14 >>> 4)) & 63];
            int i15 = i6 + 3;
            bArr2[i6 + 2] = bArr5[(i14 << 2) & 63];
            i6 += 4;
            bArr2[i15] = this.padding;
        }
        return i6 - i3;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int getEncodedLength(int i) {
        return ((i + 2) / 3) * 4;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int getMaxDecodedLength(int i) {
        return (i / 4) * 3;
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
            int min = Math.min(54, i4);
            Base64Encoder base64Encoder = this;
            outputStream.write(bArr2, 0, base64Encoder.encode(bArr, i3, min, bArr2, 0));
            i3 += min;
            i4 -= min;
            this = base64Encoder;
        }
        return ((i2 + 2) / 3) * 4;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        byte[] bArr2 = new byte[54];
        int i3 = i + i2;
        while (i3 > i && ignore((char) bArr[i3 - 1])) {
            i3--;
        }
        if (i3 == 0) {
            return 0;
        }
        int i4 = i3;
        int i5 = 0;
        while (i4 > i && i5 != 4) {
            if (!ignore((char) bArr[i4 - 1])) {
                i5++;
            }
            i4--;
        }
        int nextI = nextI(bArr, i, i4);
        int i6 = 0;
        int i7 = 0;
        while (nextI < i4) {
            int i8 = nextI + 1;
            byte b = this.decodingTable[bArr[nextI]];
            int nextI2 = nextI(bArr, i8, i4);
            int i9 = nextI2 + 1;
            byte b2 = this.decodingTable[bArr[nextI2]];
            int nextI3 = nextI(bArr, i9, i4);
            int i10 = nextI3 + 1;
            byte b3 = this.decodingTable[bArr[nextI3]];
            int nextI4 = nextI(bArr, i10, i4);
            int i11 = nextI4 + 1;
            byte b4 = this.decodingTable[bArr[nextI4]];
            if ((b | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            bArr2[i6] = (byte) ((b << 2) | (b2 >> 4));
            int i12 = i6 + 2;
            bArr2[i6 + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            i6 += 3;
            bArr2[i12] = (byte) ((b3 << 6) | b4);
            if (i6 == 54) {
                outputStream.write(bArr2);
                i6 = 0;
            }
            i7 += 3;
            nextI = nextI(bArr, i11, i4);
        }
        if (i6 > 0) {
            outputStream.write(bArr2, 0, i6);
        }
        int nextI5 = nextI(bArr, nextI, i3);
        int nextI6 = nextI(bArr, nextI5 + 1, i3);
        int nextI7 = nextI(bArr, nextI6 + 1, i3);
        return i7 + decodeLastBlock(outputStream, (char) bArr[nextI5], (char) bArr[nextI6], (char) bArr[nextI7], (char) bArr[nextI(bArr, nextI7 + 1, i3)]);
    }

    private int nextI(byte[] bArr, int i, int i2) {
        while (i < i2 && ignore((char) bArr[i])) {
            i++;
        }
        return i;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(String str, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[54];
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        if (length == 0) {
            return 0;
        }
        int i = length;
        int i2 = 0;
        while (i > 0 && i2 != 4) {
            if (!ignore(str.charAt(i - 1))) {
                i2++;
            }
            i--;
        }
        int nextI = nextI(str, 0, i);
        int i3 = 0;
        int i4 = 0;
        while (nextI < i) {
            int i5 = nextI + 1;
            byte b = this.decodingTable[str.charAt(nextI)];
            int nextI2 = nextI(str, i5, i);
            int i6 = nextI2 + 1;
            byte b2 = this.decodingTable[str.charAt(nextI2)];
            int nextI3 = nextI(str, i6, i);
            int i7 = nextI3 + 1;
            byte b3 = this.decodingTable[str.charAt(nextI3)];
            int nextI4 = nextI(str, i7, i);
            int i8 = nextI4 + 1;
            byte b4 = this.decodingTable[str.charAt(nextI4)];
            if ((b | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            bArr[i3] = (byte) ((b << 2) | (b2 >> 4));
            int i9 = i3 + 2;
            bArr[i3 + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            i3 += 3;
            bArr[i9] = (byte) ((b3 << 6) | b4);
            i4 += 3;
            if (i3 == 54) {
                outputStream.write(bArr);
                i3 = 0;
            }
            nextI = nextI(str, i8, i);
        }
        if (i3 > 0) {
            outputStream.write(bArr, 0, i3);
        }
        int nextI5 = nextI(str, nextI, length);
        int nextI6 = nextI(str, nextI5 + 1, length);
        int nextI7 = nextI(str, nextI6 + 1, length);
        return i4 + decodeLastBlock(outputStream, str.charAt(nextI5), str.charAt(nextI6), str.charAt(nextI7), str.charAt(nextI(str, nextI7 + 1, length)));
    }

    private int decodeLastBlock(OutputStream outputStream, char c, char c2, char c3, char c4) throws IOException {
        char c5 = this.padding;
        if (c3 == c5) {
            if (c4 != c5) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            byte[] bArr = this.decodingTable;
            byte b = bArr[c];
            byte b2 = bArr[c2];
            if ((b | b2) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b2 >> 4) | (b << 2));
            return 1;
        }
        if (c4 == c5) {
            byte[] bArr2 = this.decodingTable;
            byte b3 = bArr2[c];
            byte b4 = bArr2[c2];
            byte b5 = bArr2[c3];
            if ((b3 | b4 | b5) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b3 << 2) | (b4 >> 4));
            outputStream.write((b5 >> 2) | (b4 << 4));
            return 2;
        }
        byte[] bArr3 = this.decodingTable;
        byte b6 = bArr3[c];
        byte b7 = bArr3[c2];
        byte b8 = bArr3[c3];
        byte b9 = bArr3[c4];
        if ((b6 | b7 | b8 | b9) < 0) {
            throw new IOException("invalid characters encountered at end of base64 data");
        }
        outputStream.write((b6 << 2) | (b7 >> 4));
        outputStream.write((b7 << 4) | (b8 >> 2));
        outputStream.write(b9 | (b8 << 6));
        return 3;
    }

    private int nextI(String str, int i, int i2) {
        while (i < i2 && ignore(str.charAt(i))) {
            i++;
        }
        return i;
    }
}
