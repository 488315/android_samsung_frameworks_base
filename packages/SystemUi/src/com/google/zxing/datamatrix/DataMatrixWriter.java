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
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.datamatrix.encoder.TextEncoder;
import com.google.zxing.datamatrix.encoder.X12Encoder;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DataMatrixWriter implements Writer {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        int i3;
        int i4;
        byte[] bArr;
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (barcodeFormat != BarcodeFormat.DATA_MATRIX) {
            throw new IllegalArgumentException("Can only encode DATA_MATRIX, but got " + barcodeFormat);
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + i + 'x' + i2);
        }
        SymbolShapeHint symbolShapeHint = SymbolShapeHint.FORCE_NONE;
        HashMap hashMap = (HashMap) map;
        SymbolShapeHint symbolShapeHint2 = (SymbolShapeHint) hashMap.get(EncodeHintType.DATA_MATRIX_SHAPE);
        if (symbolShapeHint2 != null) {
            symbolShapeHint = symbolShapeHint2;
        }
        Dimension dimension = (Dimension) hashMap.get(EncodeHintType.MIN_SIZE);
        if (dimension == null) {
            dimension = null;
        }
        Dimension dimension2 = (Dimension) hashMap.get(EncodeHintType.MAX_SIZE);
        Dimension dimension3 = dimension2 != null ? dimension2 : null;
        Encoder[] encoderArr = {new ASCIIEncoder(), new C40Encoder(), new TextEncoder(), new X12Encoder(), new EdifactEncoder(), new Base256Encoder()};
        EncoderContext encoderContext = new EncoderContext(str);
        encoderContext.shape = symbolShapeHint;
        encoderContext.minSize = dimension;
        encoderContext.maxSize = dimension3;
        int i5 = 2;
        if (str.startsWith("[)>\u001e05\u001d") && str.endsWith("\u001e\u0004")) {
            encoderContext.writeCodeword((char) 236);
            encoderContext.skipAtEnd = 2;
            encoderContext.pos += 7;
        } else if (str.startsWith("[)>\u001e06\u001d") && str.endsWith("\u001e\u0004")) {
            encoderContext.writeCodeword((char) 237);
            encoderContext.skipAtEnd = 2;
            encoderContext.pos += 7;
        }
        int i6 = 0;
        int i7 = 0;
        while (encoderContext.hasMoreCharacters()) {
            encoderArr[i7].encode(encoderContext);
            int i8 = encoderContext.newEncoding;
            if (i8 >= 0) {
                encoderContext.newEncoding = -1;
                i7 = i8;
            }
        }
        int codewordCount = encoderContext.getCodewordCount();
        encoderContext.updateSymbolInfo(encoderContext.getCodewordCount());
        int i9 = encoderContext.symbolInfo.dataCapacity;
        int i10 = 5;
        if (codewordCount < i9 && i7 != 0 && i7 != 5) {
            encoderContext.writeCodeword((char) 254);
        }
        StringBuilder sb = encoderContext.codewords;
        if (sb.length() < i9) {
            sb.append((char) 129);
        }
        while (sb.length() < i9) {
            int length = (((sb.length() + 1) * 149) % IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList) + 1 + 129;
            if (length > 254) {
                length -= 254;
            }
            sb.append((char) length);
        }
        String sb2 = sb.toString();
        SymbolInfo lookup = SymbolInfo.lookup(sb2.length(), symbolShapeHint, dimension, dimension3);
        int[] iArr = ErrorCorrection.FACTOR_SETS;
        int length2 = sb2.length();
        int i11 = lookup.dataCapacity;
        if (length2 != i11) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        int i12 = lookup.errorCodewords;
        StringBuilder sb3 = new StringBuilder(i11 + i12);
        sb3.append(sb2);
        int interleavedBlockCount = lookup.getInterleavedBlockCount();
        if (interleavedBlockCount == 1) {
            sb3.append(ErrorCorrection.createECCBlock(i12, sb2));
        } else {
            sb3.setLength(sb3.capacity());
            int[] iArr2 = new int[interleavedBlockCount];
            int[] iArr3 = new int[interleavedBlockCount];
            int[] iArr4 = new int[interleavedBlockCount];
            int i13 = 0;
            while (i13 < interleavedBlockCount) {
                int i14 = i13 + 1;
                iArr2[i13] = lookup.getDataLengthForInterleavedBlock(i14);
                iArr3[i13] = lookup.rsBlockError;
                iArr4[i13] = 0;
                if (i13 > 0) {
                    iArr4[i13] = iArr4[i13 - 1] + iArr2[i13];
                }
                i13 = i14;
            }
            for (int i15 = 0; i15 < interleavedBlockCount; i15++) {
                StringBuilder sb4 = new StringBuilder(iArr2[i15]);
                for (int i16 = i15; i16 < i11; i16 += interleavedBlockCount) {
                    sb4.append(sb2.charAt(i16));
                }
                String createECCBlock = ErrorCorrection.createECCBlock(iArr3[i15], sb4.toString());
                int i17 = 0;
                int i18 = i15;
                while (i18 < iArr3[i15] * interleavedBlockCount) {
                    sb3.setCharAt(i11 + i18, createECCBlock.charAt(i17));
                    i18 += interleavedBlockCount;
                    i17++;
                }
            }
        }
        String sb5 = sb3.toString();
        int horizontalDataRegions = lookup.getHorizontalDataRegions();
        int i19 = lookup.matrixWidth;
        int verticalDataRegions = lookup.getVerticalDataRegions();
        int i20 = lookup.matrixHeight;
        DefaultPlacement defaultPlacement = new DefaultPlacement(sb5, horizontalDataRegions * i19, verticalDataRegions * i20);
        int i21 = 4;
        int i22 = 0;
        int i23 = 0;
        int i24 = 4;
        while (true) {
            i3 = defaultPlacement.numcols;
            i4 = defaultPlacement.numrows;
            if (i24 == i4 && i22 == 0) {
                int i25 = i4 - 1;
                defaultPlacement.module(i25, i6, i23, 1);
                defaultPlacement.module(i25, 1, i23, i5);
                defaultPlacement.module(i25, i5, i23, 3);
                defaultPlacement.module(i6, i3 - 2, i23, i21);
                int i26 = i3 - 1;
                defaultPlacement.module(i6, i26, i23, i10);
                defaultPlacement.module(1, i26, i23, 6);
                defaultPlacement.module(i5, i26, i23, 7);
                defaultPlacement.module(3, i26, i23, 8);
                i23++;
            }
            int i27 = i4 - 2;
            if (i24 == i27 && i22 == 0 && i3 % 4 != 0) {
                defaultPlacement.module(i4 - 3, i6, i23, 1);
                defaultPlacement.module(i27, i6, i23, i5);
                defaultPlacement.module(i4 - 1, i6, i23, 3);
                defaultPlacement.module(i6, i3 - 4, i23, 4);
                defaultPlacement.module(i6, i3 - 3, i23, 5);
                defaultPlacement.module(i6, i3 - 2, i23, 6);
                int i28 = i3 - 1;
                defaultPlacement.module(i6, i28, i23, 7);
                defaultPlacement.module(1, i28, i23, 8);
                i23++;
            }
            if (i24 == i27 && i22 == 0 && i3 % 8 == 4) {
                defaultPlacement.module(i4 - 3, i6, i23, 1);
                defaultPlacement.module(i27, i6, i23, i5);
                defaultPlacement.module(i4 - 1, i6, i23, 3);
                defaultPlacement.module(i6, i3 - 2, i23, 4);
                int i29 = i3 - 1;
                defaultPlacement.module(i6, i29, i23, 5);
                defaultPlacement.module(1, i29, i23, 6);
                defaultPlacement.module(i5, i29, i23, 7);
                defaultPlacement.module(3, i29, i23, 8);
                i23++;
            }
            if (i24 == i4 + 4 && i22 == i5 && i3 % 8 == 0) {
                int i30 = i4 - 1;
                defaultPlacement.module(i30, i6, i23, 1);
                int i31 = i3 - 1;
                defaultPlacement.module(i30, i31, i23, i5);
                int i32 = i3 - 3;
                defaultPlacement.module(i6, i32, i23, 3);
                int i33 = i3 - 2;
                defaultPlacement.module(i6, i33, i23, 4);
                defaultPlacement.module(i6, i31, i23, 5);
                defaultPlacement.module(1, i32, i23, 6);
                defaultPlacement.module(1, i33, i23, 7);
                defaultPlacement.module(1, i31, i23, 8);
                i23++;
            }
            do {
                bArr = defaultPlacement.bits;
                if (i24 < i4 && i22 >= 0) {
                    if ((bArr[(i24 * i3) + i22] >= 0 ? 1 : i6) == 0) {
                        defaultPlacement.utah(i24, i22, i23);
                        i23++;
                    }
                }
                i24 -= 2;
                i22 += 2;
                if (i24 < 0) {
                    break;
                }
            } while (i22 < i3);
            int i34 = i24 + 1;
            int i35 = i22 + 3;
            do {
                if (i34 >= 0 && i35 < i3) {
                    if ((bArr[(i34 * i3) + i35] >= 0 ? 1 : i6) == 0) {
                        defaultPlacement.utah(i34, i35, i23);
                        i23++;
                    }
                }
                i34 += 2;
                i35 -= 2;
                if (i34 >= i4) {
                    break;
                }
            } while (i35 >= 0);
            i24 = i34 + 3;
            i22 = i35 + 1;
            if (i24 >= i4 && i22 >= i3) {
                break;
            }
            i6 = i6;
            i5 = 2;
            i21 = 4;
            i10 = 5;
        }
        int i36 = i3 - 1;
        int i37 = i4 - 1;
        if ((bArr[(i37 * i3) + i36] >= 0 ? 1 : i6) == 0) {
            bArr[(i37 * i3) + i36] = 1;
            bArr[((i4 - 2) * i3) + (i3 - 2)] = 1;
        }
        int horizontalDataRegions2 = lookup.getHorizontalDataRegions() * i19;
        int verticalDataRegions2 = lookup.getVerticalDataRegions() * i20;
        ByteMatrix byteMatrix = new ByteMatrix(lookup.getSymbolWidth(), (lookup.getVerticalDataRegions() * 2) + (lookup.getVerticalDataRegions() * i20));
        int i38 = i6;
        int i39 = i38;
        while (i38 < verticalDataRegions2) {
            int i40 = i38 % i20;
            if (i40 == 0) {
                int i41 = i6;
                int i42 = i41;
                while (i41 < lookup.getSymbolWidth()) {
                    byteMatrix.set(i42, i39, i41 % 2 == 0 ? 1 : i6);
                    i42++;
                    i41++;
                }
                i39++;
            }
            int i43 = i6;
            int i44 = i43;
            while (i43 < horizontalDataRegions2) {
                int i45 = i43 % i19;
                if (i45 == 0) {
                    byteMatrix.set(i44, i39, true);
                    i44++;
                }
                byteMatrix.set(i44, i39, bArr[(i3 * i38) + i43] == 1);
                i44++;
                if (i45 == i19 - 1) {
                    byteMatrix.set(i44, i39, i38 % 2 == 0);
                    i44++;
                }
                i43++;
            }
            i39++;
            if (i40 == i20 - 1) {
                int i46 = 0;
                for (int i47 = 0; i47 < lookup.getSymbolWidth(); i47++) {
                    byteMatrix.set(i46, i39, true);
                    i46++;
                }
                i39++;
            }
            i38++;
            i6 = 0;
        }
        int i48 = byteMatrix.width;
        int i49 = byteMatrix.height;
        BitMatrix bitMatrix = new BitMatrix(i48, i49);
        int[] iArr5 = bitMatrix.bits;
        int length3 = iArr5.length;
        for (int i50 = 0; i50 < length3; i50++) {
            iArr5[i50] = 0;
        }
        for (int i51 = 0; i51 < i48; i51++) {
            for (int i52 = 0; i52 < i49; i52++) {
                if (byteMatrix.get(i51, i52) == 1) {
                    bitMatrix.set(i51, i52);
                }
            }
        }
        return bitMatrix;
    }
}
