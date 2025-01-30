package com.google.zxing.pdf417;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import com.google.zxing.pdf417.encoder.PDF417ErrorCorrection;
import com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PDF417Writer implements Writer {
    public static BitMatrix bitMatrixFrombitArray(byte[][] bArr, int i) {
        int i2 = i * 2;
        BitMatrix bitMatrix = new BitMatrix(bArr[0].length + i2, bArr.length + i2);
        int[] iArr = bitMatrix.bits;
        int length = iArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = 0;
        }
        int i4 = (bitMatrix.height - i) - 1;
        int i5 = 0;
        while (i5 < bArr.length) {
            for (int i6 = 0; i6 < bArr[0].length; i6++) {
                if (bArr[i5][i6] == 1) {
                    bitMatrix.set(i6 + i, i4);
                }
            }
            i5++;
            i4--;
        }
        return bitMatrix;
    }

    public static byte[][] rotateArray(byte[][] bArr) {
        byte[][] bArr2 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bArr[0].length, bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            int length = (bArr.length - i) - 1;
            for (int i2 = 0; i2 < bArr[0].length; i2++) {
                bArr2[i2][length] = bArr[i][i2];
            }
        }
        return bArr2;
    }

    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z;
        int i10;
        int i11;
        int i12;
        int[][] iArr;
        if (barcodeFormat != BarcodeFormat.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + barcodeFormat);
        }
        PDF417 pdf417 = new PDF417();
        EncodeHintType encodeHintType = EncodeHintType.PDF417_COMPACT;
        HashMap hashMap = (HashMap) map;
        if (hashMap.containsKey(encodeHintType)) {
            pdf417.compact = ((Boolean) hashMap.get(encodeHintType)).booleanValue();
        }
        EncodeHintType encodeHintType2 = EncodeHintType.PDF417_COMPACTION;
        if (hashMap.containsKey(encodeHintType2)) {
            pdf417.compaction = (Compaction) hashMap.get(encodeHintType2);
        }
        EncodeHintType encodeHintType3 = EncodeHintType.PDF417_DIMENSIONS;
        if (hashMap.containsKey(encodeHintType3)) {
            Dimensions dimensions = (Dimensions) hashMap.get(encodeHintType3);
            pdf417.maxCols = dimensions.maxCols;
            pdf417.minCols = dimensions.minCols;
            pdf417.maxRows = dimensions.maxRows;
            pdf417.minRows = dimensions.minRows;
        }
        EncodeHintType encodeHintType4 = EncodeHintType.MARGIN;
        int intValue = hashMap.containsKey(encodeHintType4) ? ((Number) hashMap.get(encodeHintType4)).intValue() : 30;
        Compaction compaction = pdf417.compaction;
        byte[] bArr = PDF417HighLevelEncoder.TEXT_MIXED_RAW;
        StringBuilder sb = new StringBuilder(str.length());
        int length = str.length();
        int i13 = 0;
        if (compaction == Compaction.TEXT) {
            PDF417HighLevelEncoder.encodeText(str, 0, length, sb, 0);
        } else if (compaction == Compaction.BYTE) {
            byte[] bytes = str.getBytes();
            PDF417HighLevelEncoder.encodeBinary(bytes, 0, bytes.length, 1, sb);
        } else if (compaction == Compaction.NUMERIC) {
            sb.append((char) 902);
            PDF417HighLevelEncoder.encodeNumeric(0, length, str, sb);
        } else {
            byte[] bArr2 = null;
            int i14 = 0;
            int i15 = 0;
            char c = 902;
            int i16 = 0;
            while (i13 < length) {
                int length2 = str.length();
                char c2 = '0';
                if (i13 < length2) {
                    i4 = i16;
                    char charAt = str.charAt(i13);
                    i3 = intValue;
                    int i17 = i13;
                    while (true) {
                        if (!(charAt >= '0' && charAt <= '9') || i17 >= length2) {
                            break;
                        }
                        i14++;
                        i17++;
                        if (i17 < length2) {
                            charAt = str.charAt(i17);
                        }
                    }
                } else {
                    i3 = intValue;
                    i4 = i16;
                    i14 = 0;
                }
                char c3 = '\r';
                if (i14 >= 13) {
                    sb.append(c);
                    PDF417HighLevelEncoder.encodeNumeric(i13, i14, str, sb);
                    i13 += i14;
                    i15 = 2;
                    i16 = 0;
                } else {
                    int length3 = str.length();
                    int i18 = i13;
                    while (i18 < length3) {
                        char charAt2 = str.charAt(i18);
                        int i19 = 0;
                        while (i19 < 13) {
                            if (!(charAt2 >= c2 && charAt2 <= '9') || i18 >= length3) {
                                break;
                            }
                            i19++;
                            i18++;
                            if (i18 < length3) {
                                charAt2 = str.charAt(i18);
                            }
                            c2 = '0';
                        }
                        if (i19 >= 13) {
                            i5 = (i18 - i13) - i19;
                            break;
                        }
                        if (i19 <= 0) {
                            char charAt3 = str.charAt(i18);
                            if (!(charAt3 == '\t' || charAt3 == '\n' || charAt3 == '\r' || (charAt3 >= ' ' && charAt3 <= '~'))) {
                                break;
                            }
                            i18++;
                        }
                        c2 = '0';
                    }
                    i5 = i18 - i13;
                    int i20 = 5;
                    if (i5 >= 5 || i14 == length) {
                        if (i15 != 0) {
                            sb.append((char) 900);
                            i6 = 0;
                            i15 = 0;
                        } else {
                            i6 = i4;
                        }
                        int encodeText = PDF417HighLevelEncoder.encodeText(str, i13, i5, sb, i6);
                        i13 += i5;
                        i16 = encodeText;
                    } else {
                        if (bArr2 == null) {
                            bArr2 = str.getBytes();
                        }
                        int length4 = str.length();
                        int i21 = i13;
                        while (i21 < length4) {
                            char charAt4 = str.charAt(i21);
                            int i22 = 0;
                            while (i22 < c3) {
                                if (!(charAt4 >= '0' && charAt4 <= '9') || (i8 = i21 + (i22 = i22 + 1)) >= length4) {
                                    break;
                                }
                                charAt4 = str.charAt(i8);
                            }
                            if (i22 >= c3) {
                                break;
                            }
                            int i23 = 0;
                            while (i23 < i20) {
                                if (!(charAt4 == '\t' || charAt4 == '\n' || charAt4 == c3 || (charAt4 >= ' ' && charAt4 <= '~')) || (i7 = i21 + (i23 = i23 + 1)) >= length4) {
                                    break;
                                }
                                charAt4 = str.charAt(i7);
                                c3 = '\r';
                            }
                            if (i23 >= i20) {
                                break;
                            }
                            char charAt5 = str.charAt(i21);
                            if (bArr2[i21] == 63 && charAt5 != '?') {
                                throw new WriterException("Non-encodable character detected: " + charAt5 + " (Unicode: " + ((int) charAt5) + ')');
                            }
                            i21++;
                            i20 = 5;
                            c3 = '\r';
                        }
                        int i24 = i21 - i13;
                        if (i24 == 0) {
                            i24 = 1;
                        }
                        if (i24 == 1 && i15 == 0) {
                            PDF417HighLevelEncoder.encodeBinary(bArr2, i13, 1, 0, sb);
                            i16 = i4;
                        } else {
                            PDF417HighLevelEncoder.encodeBinary(bArr2, i13, i24, i15, sb);
                            i15 = 1;
                            i16 = 0;
                        }
                        i13 += i24;
                    }
                }
                i14 = 0;
                c = 902;
                intValue = i3;
            }
        }
        int i25 = intValue;
        String sb2 = sb.toString();
        int length5 = sb2.length();
        float f = 0.0f;
        int[] iArr2 = null;
        for (int i26 = pdf417.minCols; i26 <= pdf417.maxCols; i26++) {
            int i27 = length5 + 1 + 8;
            int i28 = (i27 / i26) + 1;
            if (i26 * i28 >= i27 + i26) {
                i28--;
            }
            if (i28 < pdf417.minRows) {
                break;
            }
            if (i28 <= pdf417.maxRows) {
                float f2 = (((i26 * 17) + 69) * 0.357f) / (i28 * 2.0f);
                if (iArr2 == null || Math.abs(f2 - 3.0f) <= Math.abs(f - 3.0f)) {
                    iArr2 = new int[]{i26, i28};
                    f = f2;
                }
            }
        }
        if (iArr2 == null) {
            int i29 = pdf417.minCols;
            int i30 = length5 + 1 + 8;
            int i31 = (i30 / i29) + 1;
            if (i29 * i31 >= i30 + i29) {
                i31--;
            }
            int i32 = pdf417.minRows;
            if (i31 < i32) {
                iArr2 = new int[]{i29, i32};
            }
        }
        if (iArr2 == null) {
            throw new WriterException("Unable to fit message in columns");
        }
        int i33 = iArr2[0];
        int i34 = iArr2[1];
        int i35 = (i33 * i34) - 8 > length5 + 1 ? (r8 - length5) - 1 : 0;
        if (length5 + 8 + 1 > 929) {
            throw new WriterException("Encoded message contains to many code words, message to big (" + str.length() + " bytes)");
        }
        int i36 = length5 + i35 + 1;
        StringBuilder sb3 = new StringBuilder(i36);
        sb3.append((char) i36);
        sb3.append(sb2);
        for (int i37 = 0; i37 < i35; i37++) {
            sb3.append((char) 900);
        }
        String sb4 = sb3.toString();
        char[] cArr = new char[8];
        int length6 = sb4.length();
        int i38 = 0;
        while (true) {
            i9 = 7;
            if (i38 >= length6) {
                break;
            }
            int charAt6 = (sb4.charAt(i38) + cArr[7]) % 929;
            while (true) {
                iArr = PDF417ErrorCorrection.EC_COEFFICIENTS;
                if (i9 >= 1) {
                    int i39 = i9 - 1;
                    cArr[i9] = (char) ((cArr[i39] + (929 - ((iArr[2][i9] * charAt6) % 929))) % 929);
                    i9 = i39;
                }
            }
            cArr[0] = (char) ((929 - ((charAt6 * iArr[2][0]) % 929)) % 929);
            i38++;
        }
        StringBuilder sb5 = new StringBuilder(8);
        while (i9 >= 0) {
            char c4 = cArr[i9];
            if (c4 != 0) {
                cArr[i9] = (char) (929 - c4);
            }
            sb5.append(cArr[i9]);
            i9--;
        }
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(sb4, sb5.toString());
        BarcodeMatrix barcodeMatrix = new BarcodeMatrix(i34, i33);
        pdf417.barcodeMatrix = barcodeMatrix;
        int i40 = 0;
        for (int i41 = 0; i41 < i34; i41++) {
            int i42 = i41 % 3;
            barcodeMatrix.currentRow++;
            PDF417.encodeChar(130728, 17, barcodeMatrix.getCurrentRow());
            if (i42 == 0) {
                i11 = (i41 / 3) * 30;
                i10 = ((i34 - 1) / 3) + i11;
                i12 = i33 - 1;
            } else if (i42 == 1) {
                i11 = (i41 / 3) * 30;
                int i43 = i34 - 1;
                i10 = i11 + 6 + (i43 % 3);
                i12 = i43 / 3;
            } else {
                int i44 = (i41 / 3) * 30;
                i10 = (i33 - 1) + i44;
                i11 = i44 + 6;
                i12 = (i34 - 1) % 3;
            }
            int i45 = i12 + i11;
            int[][] iArr3 = PDF417.CODEWORD_TABLE;
            PDF417.encodeChar(iArr3[i42][i10], 17, barcodeMatrix.getCurrentRow());
            for (int i46 = 0; i46 < i33; i46++) {
                PDF417.encodeChar(iArr3[i42][m14m.charAt(i40)], 17, barcodeMatrix.getCurrentRow());
                i40++;
            }
            if (pdf417.compact) {
                PDF417.encodeChar(260649, 1, barcodeMatrix.getCurrentRow());
            } else {
                PDF417.encodeChar(iArr3[i42][i45], 17, barcodeMatrix.getCurrentRow());
                PDF417.encodeChar(260649, 18, barcodeMatrix.getCurrentRow());
            }
        }
        byte[][] scaledMatrix = pdf417.barcodeMatrix.getScaledMatrix(2, 8);
        if ((i2 > i) ^ (scaledMatrix[0].length < scaledMatrix.length)) {
            scaledMatrix = rotateArray(scaledMatrix);
            z = true;
        } else {
            z = false;
        }
        int length7 = i / scaledMatrix[0].length;
        int length8 = i2 / scaledMatrix.length;
        if (length7 >= length8) {
            length7 = length8;
        }
        if (length7 <= 1) {
            return bitMatrixFrombitArray(scaledMatrix, i25);
        }
        byte[][] scaledMatrix2 = pdf417.barcodeMatrix.getScaledMatrix(length7 * 2, length7 * 4 * 2);
        if (z) {
            scaledMatrix2 = rotateArray(scaledMatrix2);
        }
        return bitMatrixFrombitArray(scaledMatrix2, i25);
    }
}
