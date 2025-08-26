package com.google.zxing.pdf417;

import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.ECIInput;
import com.google.zxing.common.MinimalECIInput;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import com.google.zxing.pdf417.encoder.PDF417ErrorCorrection;
import com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class PDF417Writer implements Writer {
    public static BitMatrix bitMatrixFromBitArray(byte[][] bArr, int i) {
        int i2 = i * 2;
        BitMatrix bitMatrix = new BitMatrix(bArr[0].length + i2, bArr.length + i2);
        int length = bitMatrix.bits.length;
        for (int i3 = 0; i3 < length; i3++) {
            bitMatrix.bits[i3] = 0;
        }
        int i4 = (bitMatrix.height - i) - 1;
        int i5 = 0;
        while (i5 < bArr.length) {
            byte[] bArr2 = bArr[i5];
            for (int i6 = 0; i6 < bArr[0].length; i6++) {
                if (bArr2[i6] == 1) {
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

    /* JADX WARN: Code restructure failed: missing block: B:150:0x027d, code lost:
    
        r10 = r10 - r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02b7, code lost:
    
        if (r10 != 0) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02b9, code lost:
    
        r10 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02ba, code lost:
    
        if (r25 == false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02bc, code lost:
    
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02bf, code lost:
    
        r2 = r9.subSequence(r0, r0 + r10).toString().getBytes(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x02ce, code lost:
    
        if (r2 != null) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x02d0, code lost:
    
        if (r10 == 1) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x02d2, code lost:
    
        if (r2 == null) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x02d5, code lost:
    
        if (r2.length != 1) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x02d7, code lost:
    
        if (r4 != 0) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x02d9, code lost:
    
        if (r25 == false) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x02db, code lost:
    
        com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeMultiECIBinary(r0, 1, 0, r9, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x02e0, code lost:
    
        com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeBinary(r2, 1, 0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x02e5, code lost:
    
        if (r25 == false) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x02e7, code lost:
    
        com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeMultiECIBinary(r0, r0 + r10, r4, r9, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x02ed, code lost:
    
        com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeBinary(r2, r2.length, r4, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x02f1, code lost:
    
        r4 = 1;
        r24 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x02f4, code lost:
    
        r0 = r0 + r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:207:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x03d0  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x04dc A[LOOP:14: B:256:0x04da->B:257:0x04dc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:260:0x04f9  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0503  */
    @Override // com.google.zxing.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) throws WriterException {
        int i3;
        ECIInput noECIInput;
        CharacterSetECI characterSetECI;
        int i4;
        int i5;
        String str2;
        char c;
        boolean z;
        int iM;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int[][] iArr;
        float f;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        char cCharAt;
        int i17;
        int i18;
        char cCharAt2;
        if (barcodeFormat != BarcodeFormat.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + barcodeFormat);
        }
        PDF417 pdf417 = new PDF417();
        EncodeHintType encodeHintType = EncodeHintType.PDF417_COMPACT;
        EnumMap enumMap = (EnumMap) map;
        if (enumMap.containsKey(encodeHintType)) {
            pdf417.compact = Boolean.parseBoolean(enumMap.get(encodeHintType).toString());
        }
        EncodeHintType encodeHintType2 = EncodeHintType.PDF417_COMPACTION;
        if (enumMap.containsKey(encodeHintType2)) {
            pdf417.compaction = Compaction.valueOf(enumMap.get(encodeHintType2).toString());
        }
        EncodeHintType encodeHintType3 = EncodeHintType.PDF417_DIMENSIONS;
        if (enumMap.containsKey(encodeHintType3)) {
            Dimensions dimensions = (Dimensions) enumMap.get(encodeHintType3);
            pdf417.maxCols = dimensions.maxCols;
            pdf417.minCols = dimensions.minCols;
            pdf417.maxRows = dimensions.maxRows;
            pdf417.minRows = dimensions.minRows;
        }
        EncodeHintType encodeHintType4 = EncodeHintType.MARGIN;
        int i19 = enumMap.containsKey(encodeHintType4) ? Integer.parseInt(enumMap.get(encodeHintType4).toString()) : 30;
        EncodeHintType encodeHintType5 = EncodeHintType.ERROR_CORRECTION;
        int i20 = enumMap.containsKey(encodeHintType5) ? Integer.parseInt(enumMap.get(encodeHintType5).toString()) : 2;
        EncodeHintType encodeHintType6 = EncodeHintType.CHARACTER_SET;
        if (enumMap.containsKey(encodeHintType6)) {
            pdf417.encoding = Charset.forName(enumMap.get(encodeHintType6).toString());
        }
        EncodeHintType encodeHintType7 = EncodeHintType.PDF417_AUTO_ECI;
        boolean z2 = enumMap.containsKey(encodeHintType7) && Boolean.parseBoolean(enumMap.get(encodeHintType7).toString());
        String str3 = "Error correction level must be between 0 and 8!";
        if (i20 < 0 || i20 > 8) {
            throw new IllegalArgumentException("Error correction level must be between 0 and 8!");
        }
        int i21 = 17;
        int i22 = 1 << (i20 + 1);
        Compaction compaction = pdf417.compaction;
        Charset charset = pdf417.encoding;
        byte[] bArr = PDF417HighLevelEncoder.TEXT_MIXED_RAW;
        if (str.isEmpty()) {
            throw new WriterException("Empty message not allowed");
        }
        if (charset != null || z2) {
            i3 = 1;
        } else {
            i3 = 1;
            for (int i23 = 0; i23 < str.length(); i23++) {
                if (str.charAt(i23) > 255) {
                    throw new WriterException("Non-encodable character detected: " + str.charAt(i23) + " (Unicode: " + ((int) str.charAt(i23)) + "). Consider specifying EncodeHintType.PDF417_AUTO_ECI and/or EncodeTypeHint.CHARACTER_SET.");
                }
            }
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (z2) {
            noECIInput = new MinimalECIInput(str, charset, -1);
        } else {
            noECIInput = new PDF417HighLevelEncoder.NoECIInput(str, 0);
            if (charset == null) {
                charset = PDF417HighLevelEncoder.DEFAULT_ENCODING;
            } else if (!PDF417HighLevelEncoder.DEFAULT_ENCODING.equals(charset) && (characterSetECI = CharacterSetECI.getCharacterSetECI(charset)) != null) {
                PDF417HighLevelEncoder.encodingECI(sb, characterSetECI.getValue());
            }
        }
        int length = noECIInput.length();
        int i24 = PDF417HighLevelEncoder.AnonymousClass1.$SwitchMap$com$google$zxing$pdf417$encoder$Compaction[compaction.ordinal()];
        int[] iArr2 = null;
        if (i24 == i3) {
            i4 = i22;
            i5 = i19;
            str2 = "Error correction level must be between 0 and 8!";
            PDF417HighLevelEncoder.encodeText(0, length, 0, noECIInput, sb);
        } else if (i24 == 2) {
            i4 = i22;
            i5 = i19;
            str2 = "Error correction level must be between 0 and 8!";
            if (z2) {
                PDF417HighLevelEncoder.encodeMultiECIBinary(0, noECIInput.length(), 0, noECIInput, sb);
            } else {
                byte[] bytes = noECIInput.toString().getBytes(charset);
                PDF417HighLevelEncoder.encodeBinary(bytes, bytes.length, 1, sb);
            }
        } else if (i24 != 3) {
            int i25 = 0;
            int i26 = 0;
            int i27 = 0;
            while (i25 < length) {
                while (i25 < length && noECIInput.isECI(i25)) {
                    PDF417HighLevelEncoder.encodingECI(sb, noECIInput.getECIValue(i25));
                    i25++;
                    z2 = z2;
                }
                boolean z3 = z2;
                if (i25 >= length) {
                    break;
                }
                int length2 = noECIInput.length();
                String str4 = str3;
                if (i25 < length2) {
                    int i28 = i25;
                    int i29 = 0;
                    while (i28 < length2 && !noECIInput.isECI(i28)) {
                        int i30 = length2;
                        char cCharAt3 = noECIInput.charAt(i28);
                        int i31 = i28;
                        if (cCharAt3 < '0' || cCharAt3 > '9') {
                            break;
                        }
                        i29++;
                        i28 = i31 + 1;
                        length2 = i30;
                    }
                    i12 = i29;
                } else {
                    i12 = 0;
                }
                if (i12 < 13) {
                    int length3 = noECIInput.length();
                    i13 = i19;
                    int i32 = i25;
                    while (i32 < length3) {
                        int i33 = 0;
                        while (true) {
                            i17 = 13;
                            if (i33 >= 13) {
                                i18 = length3;
                                break;
                            }
                            if (i32 >= length3 || noECIInput.isECI(i32)) {
                                break;
                            }
                            char cCharAt4 = noECIInput.charAt(i32);
                            i18 = length3;
                            if (cCharAt4 < '0' || cCharAt4 > '9') {
                                break;
                            }
                            i33++;
                            i32++;
                            length3 = i18;
                        }
                        i18 = length3;
                        i17 = 13;
                        if (i33 >= i17) {
                            i14 = (i32 - i25) - i33;
                            break;
                        }
                        if (i33 <= 0) {
                            if (noECIInput.isECI(i32) || !((cCharAt2 = noECIInput.charAt(i32)) == '\t' || cCharAt2 == '\n' || cCharAt2 == '\r' || (cCharAt2 >= ' ' && cCharAt2 <= '~'))) {
                                break;
                            }
                            i32++;
                        }
                        length3 = i18;
                    }
                    i14 = i32 - i25;
                    if (i14 < 5 && i12 != length) {
                        Charset charset2 = z3 ? null : charset;
                        CharsetEncoder charsetEncoderNewEncoder = charset2 == null ? null : charset2.newEncoder();
                        int length4 = noECIInput.length();
                        int i34 = i25;
                        while (true) {
                            if (i34 >= length4) {
                                i15 = i22;
                                break;
                            }
                            i15 = i22;
                            int i35 = i34;
                            int i36 = 0;
                            while (i36 < 13 && !noECIInput.isECI(i35) && (cCharAt = noECIInput.charAt(i35)) >= '0') {
                                if (cCharAt > '9' || (i35 = i34 + (i36 = i36 + 1)) >= length4) {
                                    break;
                                }
                            }
                            if (i36 >= 13) {
                                break;
                            }
                            if (charsetEncoderNewEncoder != null && !charsetEncoderNewEncoder.canEncode(noECIInput.charAt(i34))) {
                                char cCharAt5 = noECIInput.charAt(i34);
                                throw new WriterException("Non-encodable character detected: " + cCharAt5 + " (Unicode: " + ((int) cCharAt5) + ')');
                            }
                            i34++;
                            i22 = i15;
                        }
                    } else {
                        i15 = i22;
                        if (i26 != 0) {
                            sb.append((char) 900);
                            i16 = 0;
                            i26 = 0;
                        } else {
                            i16 = i27;
                        }
                        int iEncodeText = PDF417HighLevelEncoder.encodeText(i25, i14, i16, noECIInput, sb);
                        i25 += i14;
                        i27 = iEncodeText;
                    }
                } else {
                    sb.append((char) 902);
                    PDF417HighLevelEncoder.encodeNumeric(noECIInput, i25, i12, sb);
                    i25 += i12;
                    i15 = i22;
                    i13 = i19;
                    i26 = 2;
                    i27 = 0;
                }
                z2 = z3;
                str3 = str4;
                i19 = i13;
                i22 = i15;
            }
            i4 = i22;
            i5 = i19;
            str2 = str3;
        } else {
            i4 = i22;
            i5 = i19;
            str2 = "Error correction level must be between 0 and 8!";
            sb.append((char) 902);
            PDF417HighLevelEncoder.encodeNumeric(noECIInput, 0, length, sb);
        }
        String string = sb.toString();
        int length5 = string.length();
        int i37 = pdf417.minCols;
        float f2 = 0.0f;
        while (i37 <= pdf417.maxCols) {
            int i38 = length5 + 1 + i4;
            int i39 = i38 / i37;
            int i40 = i39 + 1;
            if (i37 * i40 < i38 + i37) {
                i39 = i40;
            }
            if (i39 < pdf417.minRows) {
                break;
            }
            if (i39 > pdf417.maxRows) {
                f = f2;
                i11 = 1;
            } else {
                f = (((i37 * 17) + 69) * 0.357f) / (i39 * 2.0f);
                if (iArr2 == null || Math.abs(f - 3.0f) <= Math.abs(f2 - 3.0f)) {
                    i11 = 1;
                    iArr2 = new int[]{i37, i39};
                }
            }
            i37 += i11;
            f2 = f;
        }
        int i41 = 1;
        if (iArr2 == null) {
            int i42 = pdf417.minCols;
            int i43 = length5 + 1 + i4;
            int i44 = i43 / i42;
            int i45 = i44 + 1;
            if (i42 * i45 < i43 + i42) {
                i44 = i45;
            }
            int i46 = pdf417.minRows;
            if (i44 < i46) {
                c = 0;
                iArr2 = new int[]{i42, i46};
            } else {
                c = 0;
            }
        }
        if (iArr2 == null) {
            throw new WriterException("Unable to fit message in columns");
        }
        int i47 = iArr2[c];
        int i48 = iArr2[1];
        int i49 = (i47 * i48) - i4;
        int i50 = i49 > length5 + 1 ? (i49 - length5) - 1 : 0;
        if (length5 + i4 + 1 > 929) {
            throw new WriterException("Encoded message contains too many code words, message too big (" + str.length() + " bytes)");
        }
        int i51 = length5 + i50 + 1;
        StringBuilder sb2 = new StringBuilder(i51);
        sb2.append((char) i51);
        sb2.append(string);
        for (int i52 = 0; i52 < i50; i52++) {
            sb2.append((char) 900);
        }
        String string2 = sb2.toString();
        if (i20 < 0 || i20 > 8) {
            throw new IllegalArgumentException(str2);
        }
        int i53 = i4;
        char[] cArr = new char[i53];
        int length6 = string2.length();
        int i54 = 0;
        while (i54 < length6) {
            int i55 = i53 - 1;
            int iCharAt = (string2.charAt(i54) + cArr[i55]) % 929;
            while (true) {
                iArr = PDF417ErrorCorrection.EC_COEFFICIENTS;
                if (i55 >= i41) {
                    cArr[i55] = (char) ((cArr[i55 - 1] + (929 - ((iArr[i20][i55] * iCharAt) % 929))) % 929);
                    i55--;
                    i41 = 1;
                }
            }
            cArr[0] = (char) ((929 - ((iCharAt * iArr[i20][0]) % 929)) % 929);
            i54++;
            i41 = 1;
        }
        StringBuilder sb3 = new StringBuilder(i53);
        for (int i56 = i53 - 1; i56 >= 0; i56--) {
            char c2 = cArr[i56];
            if (c2 != 0) {
                cArr[i56] = (char) (929 - c2);
            }
            sb3.append(cArr[i56]);
        }
        String string3 = sb3.toString();
        pdf417.barcodeMatrix = new BarcodeMatrix(i48, i47);
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string2, string3);
        BarcodeMatrix barcodeMatrix = pdf417.barcodeMatrix;
        int i57 = 0;
        int i58 = 0;
        while (i58 < i48) {
            int i59 = i58 % 3;
            barcodeMatrix.currentRow++;
            PDF417.encodeChar(130728, i21, barcodeMatrix.getCurrentRow());
            if (i59 == 0) {
                i7 = (i58 / 3) * 30;
                iM = AbsActionBarView$$ExternalSyntheticOutline0.m(i48, 1, 3, i7);
                i8 = i47 - 1;
            } else if (i59 == 1) {
                i7 = (i58 / 3) * 30;
                int i60 = i48 - 1;
                iM = (i20 * 3) + i7 + (i60 % 3);
                i8 = i60 / 3;
            } else {
                int i61 = (i58 / 3) * 30;
                iM = (i47 - 1) + i61;
                i6 = (i20 * 3) + i61 + ((i48 - 1) % 3);
                int[][] iArr3 = PDF417.CODEWORD_TABLE;
                int i62 = 17;
                PDF417.encodeChar(iArr3[i59][iM], 17, barcodeMatrix.getCurrentRow());
                i9 = 0;
                while (i9 < i47) {
                    PDF417.encodeChar(iArr3[i59][strM.charAt(i57)], i62, barcodeMatrix.getCurrentRow());
                    i57++;
                    i9++;
                    i62 = 17;
                }
                if (pdf417.compact) {
                    i10 = 17;
                    PDF417.encodeChar(iArr3[i59][i6], 17, barcodeMatrix.getCurrentRow());
                    PDF417.encodeChar(260649, 18, barcodeMatrix.getCurrentRow());
                } else {
                    PDF417.encodeChar(260649, 1, barcodeMatrix.getCurrentRow());
                    i10 = 17;
                }
                i58++;
                i21 = i10;
            }
            i6 = i8 + i7;
            int[][] iArr32 = PDF417.CODEWORD_TABLE;
            int i622 = 17;
            PDF417.encodeChar(iArr32[i59][iM], 17, barcodeMatrix.getCurrentRow());
            i9 = 0;
            while (i9 < i47) {
            }
            if (pdf417.compact) {
            }
            i58++;
            i21 = i10;
        }
        byte[][] scaledMatrix = pdf417.barcodeMatrix.getScaledMatrix(1, 4);
        if ((i2 > i) != (scaledMatrix[0].length < scaledMatrix.length)) {
            scaledMatrix = rotateArray(scaledMatrix);
            z = true;
        } else {
            z = false;
        }
        int iMin = Math.min(i / scaledMatrix[0].length, i2 / scaledMatrix.length);
        if (iMin <= 1) {
            return bitMatrixFromBitArray(scaledMatrix, i5);
        }
        byte[][] scaledMatrix2 = pdf417.barcodeMatrix.getScaledMatrix(iMin, iMin * 4);
        if (z) {
            scaledMatrix2 = rotateArray(scaledMatrix2);
        }
        return bitMatrixFromBitArray(scaledMatrix2, i5);
    }
}
