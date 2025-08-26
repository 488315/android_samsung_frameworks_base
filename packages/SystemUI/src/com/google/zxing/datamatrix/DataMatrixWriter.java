package com.google.zxing.datamatrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Dimension;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.ASCIIEncoder;
import com.google.zxing.datamatrix.encoder.Base256Encoder;
import com.google.zxing.datamatrix.encoder.C40Encoder;
import com.google.zxing.datamatrix.encoder.DefaultPlacement;
import com.google.zxing.datamatrix.encoder.EdifactEncoder;
import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.ErrorCorrection;
import com.google.zxing.datamatrix.encoder.MinimalEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.datamatrix.encoder.TextEncoder;
import com.google.zxing.datamatrix.encoder.X12Encoder;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class DataMatrixWriter implements Writer {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0499 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x04b2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x04c4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x04dd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x04ed A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0616 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:301:0x04b4 A[ADDED_TO_REGION, EDGE_INSN: B:301:0x04b4->B:200:0x04b4 BREAK  A[LOOP:5: B:191:0x0495->B:201:0x04b7], REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:304:0x04df A[ADDED_TO_REGION, EDGE_INSN: B:304:0x04df->B:211:0x04df BREAK  A[LOOP:6: B:203:0x04c2->B:212:0x04e2], REMOVE, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* JADX WARN: Type inference failed for: r13v57 */
    @Override // com.google.zxing.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        int i3;
        SymbolShapeHint symbolShapeHint;
        Encoder[] encoderArr;
        int i4;
        String string;
        int i5;
        int i6;
        int i7;
        int i8;
        SymbolInfo symbolInfo;
        char c;
        int i9;
        char c2;
        int i10;
        int i11;
        byte[] bArr;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        BitMatrix bitMatrix;
        int i18;
        int i19;
        boolean z;
        int i20;
        String strSubstring;
        int i21;
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (barcodeFormat != BarcodeFormat.DATA_MATRIX) {
            throw new IllegalArgumentException("Can only encode DATA_MATRIX, but got " + barcodeFormat);
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Requested dimensions can't be negative: " + i + 'x' + i2);
        }
        SymbolShapeHint symbolShapeHint2 = SymbolShapeHint.FORCE_NONE;
        EnumMap enumMap = (EnumMap) map;
        SymbolShapeHint symbolShapeHint3 = (SymbolShapeHint) enumMap.get(EncodeHintType.DATA_MATRIX_SHAPE);
        SymbolShapeHint symbolShapeHint4 = symbolShapeHint3 != null ? symbolShapeHint3 : symbolShapeHint2;
        Dimension dimension = (Dimension) enumMap.get(EncodeHintType.MIN_SIZE);
        MinimalEncoder.Edge edge = null;
        if (dimension == null) {
            dimension = null;
        }
        Dimension dimension2 = (Dimension) enumMap.get(EncodeHintType.MAX_SIZE);
        if (dimension2 == null) {
            dimension2 = null;
        }
        EncodeHintType encodeHintType = EncodeHintType.DATA_MATRIX_COMPACT;
        EnumMap enumMap2 = (EnumMap) map;
        int i22 = 6;
        int i23 = 1;
        int i24 = 7;
        if (enumMap2.containsKey(encodeHintType) && Boolean.parseBoolean(enumMap2.get(encodeHintType).toString())) {
            EncodeHintType encodeHintType2 = EncodeHintType.GS1_FORMAT;
            boolean z2 = enumMap2.containsKey(encodeHintType2) && Boolean.parseBoolean(enumMap2.get(encodeHintType2).toString());
            EncodeHintType encodeHintType3 = EncodeHintType.CHARACTER_SET;
            Charset charsetForName = enumMap2.containsKey(encodeHintType3) ? Charset.forName(enumMap2.get(encodeHintType3).toString()) : null;
            int i25 = z2 ? 29 : -1;
            if (str.startsWith("[)>\u001e05\u001d") && str.endsWith("\u001e\u0004")) {
                strSubstring = str.substring(7, str.length() - 2);
                i21 = 5;
            } else if (str.startsWith("[)>\u001e06\u001d") && str.endsWith("\u001e\u0004")) {
                strSubstring = str.substring(7, str.length() - 2);
                i21 = 6;
            } else {
                strSubstring = str;
                i21 = 0;
            }
            MinimalEncoder.Input input = new MinimalEncoder.Input(strSubstring, charsetForName, i25, symbolShapeHint4, i21, 0);
            symbolShapeHint = symbolShapeHint4;
            int length = input.bytes.length;
            MinimalEncoder.Edge[][] edgeArr = (MinimalEncoder.Edge[][]) Array.newInstance((Class<?>) MinimalEncoder.Edge.class, length + 1, 6);
            MinimalEncoder.addEdges(input, edgeArr, 0, null);
            int i26 = 1;
            while (i26 <= length) {
                int i27 = 0;
                while (i27 < 6) {
                    MinimalEncoder.Edge edge2 = edge;
                    MinimalEncoder.Edge edge3 = edgeArr[i26][i27];
                    if (edge3 != null && i26 < length) {
                        MinimalEncoder.addEdges(input, edgeArr, i26, edge3);
                    }
                    i27++;
                    edge = edge2;
                }
                MinimalEncoder.Edge edge4 = edge;
                for (int i28 = 0; i28 < 6; i28++) {
                    edgeArr[i26 - 1][i28] = edge4;
                }
                i26++;
                edge = edge4;
            }
            int i29 = Integer.MAX_VALUE;
            int i30 = 0;
            int i31 = -1;
            while (i30 < i22) {
                int i32 = i24;
                MinimalEncoder.Edge edge5 = edgeArr[length][i30];
                if (edge5 != null) {
                    int i33 = edge5.cachedTotalSize;
                    if (i30 >= i23 && i30 <= 3) {
                        i33++;
                    }
                    if (i33 < i29) {
                        i29 = i33;
                        i31 = i30;
                    }
                }
                i30++;
                i24 = i32;
                i22 = 6;
                i23 = 1;
            }
            i3 = i24;
            if (i31 < 0) {
                throw new IllegalStateException("Failed to encode \"" + input + "\"");
            }
            string = new String(new MinimalEncoder.Result(edgeArr[length][i31]).bytes, StandardCharsets.ISO_8859_1);
        } else {
            i3 = 7;
            symbolShapeHint = symbolShapeHint4;
            EncodeHintType encodeHintType4 = EncodeHintType.FORCE_C40;
            EnumMap enumMap3 = (EnumMap) map;
            boolean z3 = enumMap3.containsKey(encodeHintType4) && Boolean.parseBoolean(enumMap3.get(encodeHintType4).toString());
            C40Encoder c40Encoder = new C40Encoder();
            Encoder[] encoderArr2 = {new ASCIIEncoder(), c40Encoder, new TextEncoder(), new X12Encoder(), new EdifactEncoder(), new Base256Encoder()};
            EncoderContext encoderContext = new EncoderContext(str);
            encoderContext.shape = symbolShapeHint;
            encoderContext.minSize = dimension;
            encoderContext.maxSize = dimension2;
            if (str.startsWith("[)>\u001e05\u001d") && str.endsWith("\u001e\u0004")) {
                encoderContext.writeCodeword((char) 236);
                encoderContext.skipAtEnd = 2;
                encoderContext.pos += 7;
            } else if (str.startsWith("[)>\u001e06\u001d") && str.endsWith("\u001e\u0004")) {
                encoderContext.writeCodeword((char) 237);
                encoderContext.skipAtEnd = 2;
                encoderContext.pos += 7;
            }
            StringBuilder sb = encoderContext.codewords;
            if (z3) {
                StringBuilder sb2 = new StringBuilder();
                int i34 = encoderContext.pos;
                int length2 = 0;
                int iEncodeChar = 0;
                while (encoderContext.hasMoreCharacters()) {
                    char currentChar = encoderContext.getCurrentChar();
                    encoderContext.pos++;
                    iEncodeChar = c40Encoder.encodeChar(currentChar, sb2);
                    if (sb2.length() % 3 == 0) {
                        i34 = encoderContext.pos;
                        length2 = sb2.length();
                    }
                }
                if (length2 != sb2.length()) {
                    int length3 = sb.length() + ((sb2.length() / 3) * 2) + 1;
                    encoderContext.updateSymbolInfo(length3);
                    int i35 = encoderContext.symbolInfo.dataCapacity - length3;
                    encoderArr = encoderArr2;
                    int length4 = sb2.length() % 3;
                    if ((length4 == 2 && i35 != 2) || (length4 == 1 && (iEncodeChar > 3 || i35 != 1))) {
                        sb2.setLength(length2);
                        encoderContext.pos = i34;
                    }
                } else {
                    encoderArr = encoderArr2;
                }
                if (sb2.length() > 0) {
                    encoderContext.writeCodeword((char) 230);
                }
                c40Encoder.handleEOD(encoderContext, sb2);
                i4 = encoderContext.newEncoding;
                encoderContext.newEncoding = -1;
            } else {
                encoderArr = encoderArr2;
                i4 = 0;
            }
            while (encoderContext.hasMoreCharacters()) {
                encoderArr[i4].encode(encoderContext);
                int i36 = encoderContext.newEncoding;
                if (i36 >= 0) {
                    encoderContext.newEncoding = -1;
                    i4 = i36;
                }
            }
            int length5 = sb.length();
            encoderContext.updateSymbolInfo(sb.length());
            int i37 = encoderContext.symbolInfo.dataCapacity;
            if (length5 < i37 && i4 != 0 && i4 != 5 && i4 != 4) {
                encoderContext.writeCodeword((char) 254);
            }
            if (sb.length() < i37) {
                sb.append((char) 129);
            }
            while (sb.length() < i37) {
                int length6 = ((sb.length() + 1) * 149) % IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList;
                int i38 = length6 + 130;
                if (i38 > 254) {
                    i38 = length6 - 124;
                }
                sb.append((char) i38);
            }
            string = sb.toString();
        }
        SymbolInfo symbolInfoLookup = SymbolInfo.lookup(string.length(), symbolShapeHint, dimension, dimension2);
        int[] iArr = ErrorCorrection.FACTOR_SETS;
        int length7 = string.length();
        int i39 = symbolInfoLookup.dataCapacity;
        if (length7 != i39) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        int i40 = symbolInfoLookup.errorCodewords;
        StringBuilder sb3 = new StringBuilder(i39 + i40);
        sb3.append(string);
        int interleavedBlockCount = symbolInfoLookup.getInterleavedBlockCount();
        if (interleavedBlockCount == 1) {
            sb3.append(ErrorCorrection.createECCBlock(i40, string));
        } else {
            sb3.setLength(sb3.capacity());
            int[] iArr2 = new int[interleavedBlockCount];
            int[] iArr3 = new int[interleavedBlockCount];
            int i41 = 0;
            while (i41 < interleavedBlockCount) {
                int i42 = i41 + 1;
                iArr2[i41] = symbolInfoLookup.getDataLengthForInterleavedBlock(i42);
                iArr3[i41] = symbolInfoLookup.rsBlockError;
                i41 = i42;
            }
            for (int i43 = 0; i43 < interleavedBlockCount; i43++) {
                StringBuilder sb4 = new StringBuilder(iArr2[i43]);
                for (int i44 = i43; i44 < i39; i44 += interleavedBlockCount) {
                    sb4.append(string.charAt(i44));
                }
                String strCreateECCBlock = ErrorCorrection.createECCBlock(iArr3[i43], sb4.toString());
                int i45 = i43;
                int i46 = 0;
                while (i45 < iArr3[i43] * interleavedBlockCount) {
                    sb3.setCharAt(i39 + i45, strCreateECCBlock.charAt(i46));
                    i45 += interleavedBlockCount;
                    i46++;
                }
            }
        }
        String string2 = sb3.toString();
        int horizontalDataRegions = symbolInfoLookup.getHorizontalDataRegions();
        int i47 = symbolInfoLookup.matrixWidth;
        int verticalDataRegions = symbolInfoLookup.getVerticalDataRegions();
        int i48 = symbolInfoLookup.matrixHeight;
        DefaultPlacement defaultPlacement = new DefaultPlacement(string2, horizontalDataRegions * i47, verticalDataRegions * i48);
        int i49 = 0;
        int i50 = 4;
        int i51 = 0;
        while (true) {
            i5 = defaultPlacement.numcols;
            i6 = defaultPlacement.numrows;
            if (i50 == i6 && i51 == 0) {
                int i52 = i6 - 1;
                defaultPlacement.module(i52, 0, i49, 1);
                i7 = 2;
                defaultPlacement.module(i52, 1, i49, 2);
                defaultPlacement.module(i52, 2, i49, 3);
                defaultPlacement.module(0, i5 - 2, i49, 4);
                int i53 = i5 - 1;
                defaultPlacement.module(0, i53, i49, 5);
                defaultPlacement.module(1, i53, i49, 6);
                defaultPlacement.module(2, i53, i49, i3);
                defaultPlacement.module(3, i53, i49, 8);
                i49++;
            } else {
                i7 = 2;
            }
            i8 = i6 - 2;
            if (i50 == i8 && i51 == 0 && i5 % 4 != 0) {
                i9 = 1;
                symbolInfo = symbolInfoLookup;
                defaultPlacement.module(i6 - 3, 0, i49, 1);
                defaultPlacement.module(i8, 0, i49, i7);
                defaultPlacement.module(i6 - 1, 0, i49, 3);
                defaultPlacement.module(0, i5 - 4, i49, 4);
                defaultPlacement.module(0, i5 - 3, i49, 5);
                defaultPlacement.module(0, i5 - 2, i49, 6);
                int i54 = i5 - 1;
                defaultPlacement.module(0, i54, i49, 7);
                c = '\b';
                defaultPlacement.module(1, i54, i49, 8);
                i49++;
            } else {
                symbolInfo = symbolInfoLookup;
                c = '\b';
                i9 = 1;
            }
            if (i50 != i8 || i51 != 0) {
                c2 = c;
            } else if (i5 % 8 == 4) {
                defaultPlacement.module(i6 - 3, 0, i49, i9);
                defaultPlacement.module(i8, 0, i49, 2);
                defaultPlacement.module(i6 - 1, 0, i49, 3);
                defaultPlacement.module(0, i5 - 2, i49, 4);
                int i55 = i5 - 1;
                defaultPlacement.module(0, i55, i49, 5);
                defaultPlacement.module(i9, i55, i49, 6);
                i10 = 2;
                defaultPlacement.module(2, i55, i49, 7);
                c2 = '\b';
                defaultPlacement.module(3, i55, i49, 8);
                i49++;
                if (i50 != i6 + 4 && i51 == i10 && i5 % 8 == 0) {
                    int i56 = i6 - 1;
                    defaultPlacement.module(i56, 0, i49, 1);
                    int i57 = i5 - 1;
                    defaultPlacement.module(i56, i57, i49, i10);
                    int i58 = i5 - 3;
                    defaultPlacement.module(0, i58, i49, 3);
                    int i59 = i5 - 2;
                    defaultPlacement.module(0, i59, i49, 4);
                    defaultPlacement.module(0, i57, i49, 5);
                    defaultPlacement.module(1, i58, i49, 6);
                    i11 = 7;
                    defaultPlacement.module(1, i59, i49, 7);
                    defaultPlacement.module(1, i57, i49, 8);
                    i49++;
                    while (true) {
                        bArr = defaultPlacement.bits;
                        if (i50 < i6) {
                            defaultPlacement.utah(i50, i51, i49);
                            i49++;
                        }
                        i12 = i50 - 2;
                        i13 = i51 + 2;
                        if (i12 >= 0) {
                            break;
                        }
                        break;
                        break;
                        i51 = i13;
                        i50 = i12;
                    }
                    i14 = i50 - 1;
                    i15 = i51 + 5;
                    while (true) {
                        if (i14 >= 0) {
                            defaultPlacement.utah(i14, i15, i49);
                            i49++;
                        }
                        i16 = i14 + 2;
                        i17 = i15 - 2;
                        if (i16 < i6) {
                            break;
                        }
                        break;
                        break;
                        i14 = i16;
                        i15 = i17;
                    }
                    i50 = i14 + 5;
                    i51 = i15 - 1;
                    if (i50 >= i6) {
                    }
                    symbolInfoLookup = symbolInfo;
                    i3 = i11;
                } else {
                    while (true) {
                        bArr = defaultPlacement.bits;
                        if (i50 < i6 && i51 >= 0 && bArr[(i50 * i5) + i51] < 0) {
                            defaultPlacement.utah(i50, i51, i49);
                            i49++;
                        }
                        i12 = i50 - 2;
                        i13 = i51 + 2;
                        if (i12 >= 0 || i13 >= i5) {
                            break;
                        }
                        i51 = i13;
                        i50 = i12;
                    }
                    i14 = i50 - 1;
                    i15 = i51 + 5;
                    while (true) {
                        if (i14 >= 0 && i15 < i5 && bArr[(i14 * i5) + i15] < 0) {
                            defaultPlacement.utah(i14, i15, i49);
                            i49++;
                        }
                        i16 = i14 + 2;
                        i17 = i15 - 2;
                        if (i16 < i6 || i17 < 0) {
                            break;
                        }
                        i14 = i16;
                        i15 = i17;
                    }
                    i50 = i14 + 5;
                    i51 = i15 - 1;
                    if (i50 >= i6 && i51 >= i5) {
                        break;
                    }
                    symbolInfoLookup = symbolInfo;
                    i3 = i11;
                }
            } else {
                c2 = '\b';
            }
            i10 = 2;
            i11 = i50 != i6 + 4 ? 7 : 7;
            while (true) {
                bArr = defaultPlacement.bits;
                if (i50 < i6) {
                }
                i12 = i50 - 2;
                i13 = i51 + 2;
                if (i12 >= 0) {
                }
                i51 = i13;
                i50 = i12;
            }
            i14 = i50 - 1;
            i15 = i51 + 5;
            while (true) {
                if (i14 >= 0) {
                }
                i16 = i14 + 2;
                i17 = i15 - 2;
                if (i16 < i6) {
                }
                i14 = i16;
                i15 = i17;
            }
            i50 = i14 + 5;
            i51 = i15 - 1;
            if (i50 >= i6) {
            }
            symbolInfoLookup = symbolInfo;
            i3 = i11;
        }
        int i60 = i5 - 1;
        int i61 = i6 - 1;
        if (bArr[(i61 * i5) + i60] < 0) {
            int i62 = (i61 * i5) + i60;
            byte b = (byte) 1;
            bArr[i62] = b;
            bArr[(i8 * i5) + (i5 - 2)] = b;
        }
        int horizontalDataRegions2 = symbolInfo.getHorizontalDataRegions() * i47;
        int verticalDataRegions2 = symbolInfo.getVerticalDataRegions() * i48;
        ByteMatrix byteMatrix = new ByteMatrix(symbolInfo.getSymbolWidth(), (symbolInfo.getVerticalDataRegions() * 2) + (symbolInfo.getVerticalDataRegions() * i48));
        int i63 = 0;
        int i64 = 0;
        while (i64 < verticalDataRegions2) {
            int i65 = i64 % i48;
            if (i65 == 0) {
                int i66 = 0;
                for (int i67 = 0; i67 < symbolInfo.getSymbolWidth(); i67++) {
                    byteMatrix.set(i66, i63, i67 % 2 == 0);
                    i66++;
                }
                z = true;
                i63++;
            } else {
                z = true;
            }
            int i68 = 0;
            int i69 = 0;
            ?? r13 = z;
            while (i68 < horizontalDataRegions2) {
                int i70 = i68 % i47;
                if (i70 == 0) {
                    byteMatrix.set(i69, i63, (boolean) r13);
                    i69 += r13;
                }
                int i71 = horizontalDataRegions2;
                byteMatrix.set(i69, i63, bArr[(i64 * i5) + i68] == r13 ? r13 : false);
                int i72 = i69 + 1;
                boolean z4 = r13;
                if (i70 == i47 - 1) {
                    byteMatrix.set(i72, i63, i64 % 2 == 0 ? z4 : false);
                    i69 += 2;
                } else {
                    i69 = i72;
                }
                i68++;
                horizontalDataRegions2 = i71;
                r13 = z4;
            }
            int i73 = horizontalDataRegions2;
            boolean z5 = r13;
            int i74 = i63 + 1;
            if (i65 == i48 - 1) {
                int i75 = 0;
                for (int i76 = 0; i76 < symbolInfo.getSymbolWidth(); i76 += z5 ? 1 : 0) {
                    byteMatrix.set(i75, i74, z5);
                    i75 += z5 ? 1 : 0;
                }
                i20 = z5 ? 1 : 0;
                i63 += 2;
            } else {
                i20 = z5 ? 1 : 0;
                i63 = i74;
            }
            i64 += i20;
            horizontalDataRegions2 = i73;
        }
        int i77 = byteMatrix.width;
        int iMax = Math.max(i, i77);
        int i78 = byteMatrix.height;
        int iMax2 = Math.max(i2, i78);
        int iMin = Math.min(iMax / i77, iMax2 / i78);
        int i79 = (iMax - (i77 * iMin)) / 2;
        int i80 = (iMax2 - (i78 * iMin)) / 2;
        if (i2 < i78 || i < i77) {
            bitMatrix = new BitMatrix(i77, i78);
            i18 = 0;
            i19 = 0;
        } else {
            bitMatrix = new BitMatrix(i, i2);
            i19 = i80;
            i18 = i79;
        }
        int length8 = bitMatrix.bits.length;
        for (int i81 = 0; i81 < length8; i81++) {
            bitMatrix.bits[i81] = 0;
        }
        int i82 = i19;
        int i83 = 0;
        while (i83 < i78) {
            int i84 = i18;
            int i85 = 0;
            while (i85 < i77) {
                if (byteMatrix.get(i85, i83) == 1) {
                    bitMatrix.setRegion(i84, i82, iMin, iMin);
                }
                i85++;
                i84 += iMin;
            }
            i83++;
            i82 += iMin;
        }
        return bitMatrix;
    }
}
