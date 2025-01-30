package com.google.zxing.qrcode;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.BlockPair;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.MaskUtil;
import com.google.zxing.qrcode.encoder.MatrixUtil;
import com.google.zxing.qrcode.encoder.QRCode;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QRCodeWriter implements Writer {
    /* JADX WARN: Removed duplicated region for block: B:466:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x013c A[LOOP:1: B:54:0x010d->B:63:0x013c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x014b A[SYNTHETIC] */
    @Override // com.google.zxing.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        boolean z;
        Mode mode;
        byte[] bytes;
        int length;
        int i3;
        int i4;
        int i5;
        byte[][] bArr;
        Version version;
        ErrorCorrectionLevel errorCorrectionLevel;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        CharacterSetECI characterSetECI;
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (barcodeFormat != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + barcodeFormat);
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + i + 'x' + i2);
        }
        ErrorCorrectionLevel errorCorrectionLevel2 = ErrorCorrectionLevel.L;
        HashMap hashMap = (HashMap) map;
        ErrorCorrectionLevel errorCorrectionLevel3 = (ErrorCorrectionLevel) hashMap.get(EncodeHintType.ERROR_CORRECTION);
        if (errorCorrectionLevel3 != null) {
            errorCorrectionLevel2 = errorCorrectionLevel3;
        }
        Integer num = (Integer) hashMap.get(EncodeHintType.MARGIN);
        int intValue = num != null ? num.intValue() : 4;
        String str2 = (String) ((HashMap) map).get(EncodeHintType.CHARACTER_SET);
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        boolean equals = "Shift_JIS".equals(str2);
        int[] iArr = Encoder.ALPHANUMERIC_TABLE;
        if (equals) {
            try {
                bytes = str.getBytes("Shift_JIS");
                length = bytes.length;
            } catch (UnsupportedEncodingException unused) {
            }
            if (length % 2 == 0) {
                for (int i12 = 0; i12 < length; i12 += 2) {
                    int i13 = bytes[i12] & 255;
                    if ((i13 >= 129 && i13 <= 159) || (i13 >= 224 && i13 <= 235)) {
                    }
                }
                z = true;
                mode = !z ? Mode.KANJI : Mode.BYTE;
            }
            z = false;
            if (!z) {
            }
        } else {
            int i14 = 0;
            boolean z2 = false;
            boolean z3 = false;
            while (true) {
                if (i14 < str.length()) {
                    char charAt = str.charAt(i14);
                    if (charAt < '0' || charAt > '9') {
                        if ((charAt < '`' ? iArr[charAt] : -1) == -1) {
                            mode = Mode.BYTE;
                            break;
                        }
                        z2 = true;
                    } else {
                        z3 = true;
                    }
                    i14++;
                } else {
                    mode = z2 ? Mode.ALPHANUMERIC : z3 ? Mode.NUMERIC : Mode.BYTE;
                }
            }
        }
        BitArray bitArray = new BitArray();
        if (mode != Mode.BYTE || "ISO-8859-1".equals(str2) || (characterSetECI = (CharacterSetECI) ((HashMap) CharacterSetECI.NAME_TO_ECI).get(str2)) == null) {
            i3 = 4;
        } else {
            i3 = 4;
            bitArray.appendBits(Mode.ECI.getBits(), 4);
            bitArray.appendBits(characterSetECI.getValue(), 8);
        }
        bitArray.appendBits(mode.getBits(), i3);
        BitArray bitArray2 = new BitArray();
        int i15 = Encoder.AbstractC45491.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
        if (i15 == 1) {
            int length2 = str.length();
            int i16 = 0;
            while (i16 < length2) {
                int charAt2 = str.charAt(i16) - '0';
                int i17 = i16 + 2;
                if (i17 < length2) {
                    bitArray2.appendBits(((str.charAt(i16 + 1) - '0') * 10) + (charAt2 * 100) + (str.charAt(i17) - '0'), 10);
                    i16 += 3;
                } else {
                    i16++;
                    if (i16 < length2) {
                        bitArray2.appendBits((charAt2 * 10) + (str.charAt(i16) - '0'), 7);
                        i16 = i17;
                    } else {
                        bitArray2.appendBits(charAt2, 4);
                    }
                }
            }
        } else if (i15 == 2) {
            int length3 = str.length();
            int i18 = 0;
            while (i18 < length3) {
                char charAt3 = str.charAt(i18);
                int i19 = charAt3 < '`' ? iArr[charAt3] : -1;
                if (i19 == -1) {
                    throw new WriterException();
                }
                int i20 = i18 + 1;
                if (i20 < length3) {
                    char charAt4 = str.charAt(i20);
                    int i21 = charAt4 < '`' ? iArr[charAt4] : -1;
                    if (i21 == -1) {
                        throw new WriterException();
                    }
                    bitArray2.appendBits((i19 * 45) + i21, 11);
                    i18 += 2;
                } else {
                    bitArray2.appendBits(i19, 6);
                    i18 = i20;
                }
            }
        } else if (i15 == 3) {
            try {
                for (byte b : str.getBytes(str2)) {
                    bitArray2.appendBits(b, 8);
                }
            } catch (UnsupportedEncodingException e) {
                throw new WriterException(e);
            }
        } else {
            if (i15 != 4) {
                throw new WriterException("Invalid mode: " + mode);
            }
            try {
                byte[] bytes2 = str.getBytes("Shift_JIS");
                int length4 = bytes2.length;
                for (int i22 = 0; i22 < length4; i22 += 2) {
                    int i23 = ((bytes2[i22] & 255) << 8) | (bytes2[i22 + 1] & 255);
                    if (i23 >= 33088 && i23 <= 40956) {
                        i11 = 33088;
                    } else if (i23 < 57408 || i23 > 60351) {
                        i10 = -1;
                        if (i10 != -1) {
                            throw new WriterException("Invalid byte sequence");
                        }
                        bitArray2.appendBits(((i10 >> 8) * 192) + (i10 & 255), 13);
                    } else {
                        i11 = 49472;
                    }
                    i10 = i23 - i11;
                    if (i10 != -1) {
                    }
                }
            } catch (UnsupportedEncodingException e2) {
                throw new WriterException(e2);
            }
        }
        Version chooseVersion = Encoder.chooseVersion(mode.getCharacterCountBits(Encoder.chooseVersion(mode.getCharacterCountBits(Version.VERSIONS[0]) + bitArray.size + bitArray2.size, errorCorrectionLevel2)) + bitArray.size + bitArray2.size, errorCorrectionLevel2);
        BitArray bitArray3 = new BitArray();
        int i24 = bitArray.size;
        bitArray3.ensureCapacity(bitArray3.size + i24);
        for (int i25 = 0; i25 < i24; i25++) {
            bitArray3.appendBit(bitArray.get(i25));
        }
        int length5 = mode == Mode.BYTE ? (bitArray2.size + 7) >> 3 : str.length();
        int characterCountBits = mode.getCharacterCountBits(chooseVersion);
        int i26 = 1 << characterCountBits;
        if (length5 >= i26) {
            StringBuilder sb = new StringBuilder();
            sb.append(length5);
            sb.append(" is bigger than ");
            sb.append(i26 - 1);
            throw new WriterException(sb.toString());
        }
        bitArray3.appendBits(length5, characterCountBits);
        int i27 = bitArray2.size;
        bitArray3.ensureCapacity(bitArray3.size + i27);
        for (int i28 = 0; i28 < i27; i28++) {
            bitArray3.appendBit(bitArray2.get(i28));
        }
        Version.ECBlocks eCBlocks = chooseVersion.ecBlocks[errorCorrectionLevel2.ordinal()];
        int i29 = 0;
        for (Version.ECB ecb : eCBlocks.ecBlocks) {
            i29 += ecb.count;
        }
        int i30 = i29 * eCBlocks.ecCodewordsPerBlock;
        int i31 = chooseVersion.totalCodewords;
        int i32 = i31 - i30;
        int i33 = i32 << 3;
        if (bitArray3.size > i33) {
            throw new WriterException("data bits cannot fit in the QR Code" + bitArray3.size + " > " + i33);
        }
        for (int i34 = 0; i34 < 4 && bitArray3.size < i33; i34++) {
            bitArray3.appendBit(false);
        }
        boolean z4 = false;
        int i35 = bitArray3.size & 7;
        if (i35 > 0) {
            while (i35 < 8) {
                bitArray3.appendBit(z4);
                i35++;
                z4 = false;
            }
        }
        int i36 = i32 - ((bitArray3.size + 7) >> 3);
        for (int i37 = 0; i37 < i36; i37++) {
            bitArray3.appendBits((i37 & 1) == 0 ? IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState : 17, 8);
        }
        if (bitArray3.size != i33) {
            throw new WriterException("Bits size does not equal capacity");
        }
        int i38 = 0;
        for (Version.ECB ecb2 : eCBlocks.ecBlocks) {
            i38 += ecb2.count;
        }
        if (((bitArray3.size + 7) >> 3) != i32) {
            throw new WriterException("Number of bits and data bytes does not match");
        }
        ArrayList arrayList = new ArrayList(i38);
        int i39 = 0;
        int i40 = 0;
        int i41 = 0;
        int i42 = 0;
        while (i39 < i38) {
            int[] iArr2 = new int[1];
            int[] iArr3 = new int[1];
            if (i39 >= i38) {
                throw new WriterException("Block ID too large");
            }
            int i43 = i31 % i38;
            int i44 = i38 - i43;
            int i45 = i31 / i38;
            int i46 = i32 / i38;
            int i47 = i46 + 1;
            int i48 = intValue;
            int i49 = i45 - i46;
            Version version2 = chooseVersion;
            int i50 = (i45 + 1) - i47;
            if (i49 != i50) {
                throw new WriterException("EC bytes mismatch");
            }
            Mode mode2 = mode;
            if (i38 != i44 + i43) {
                throw new WriterException("RS blocks mismatch");
            }
            if (i31 != ((i47 + i50) * i43) + ((i46 + i49) * i44)) {
                throw new WriterException("Total bytes mismatch");
            }
            if (i39 < i44) {
                iArr2[0] = i46;
                iArr3[0] = i49;
            } else {
                iArr2[0] = i47;
                iArr3[0] = i50;
            }
            int i51 = iArr2[0];
            byte[] bArr2 = new byte[i51];
            int i52 = i41 * 8;
            int i53 = 0;
            while (i53 < i51) {
                int i54 = i38;
                ErrorCorrectionLevel errorCorrectionLevel4 = errorCorrectionLevel2;
                int i55 = 0;
                int i56 = i31;
                int i57 = 0;
                for (int i58 = 8; i57 < i58; i58 = 8) {
                    if (bitArray3.get(i52)) {
                        i55 |= 1 << (7 - i57);
                    }
                    i52++;
                    i57++;
                }
                bArr2[i53 + 0] = (byte) i55;
                i53++;
                i38 = i54;
                errorCorrectionLevel2 = errorCorrectionLevel4;
                i31 = i56;
            }
            ErrorCorrectionLevel errorCorrectionLevel5 = errorCorrectionLevel2;
            int i59 = i31;
            int i60 = i38;
            int i61 = iArr3[0];
            int[] iArr4 = new int[i51 + i61];
            for (int i62 = 0; i62 < i51; i62++) {
                iArr4[i62] = bArr2[i62] & 255;
            }
            new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(i61, iArr4);
            byte[] bArr3 = new byte[i61];
            for (int i63 = 0; i63 < i61; i63++) {
                bArr3[i63] = (byte) iArr4[i51 + i63];
            }
            arrayList.add(new BlockPair(bArr2, bArr3));
            i42 = Math.max(i42, i51);
            i40 = Math.max(i40, i61);
            i41 += iArr2[0];
            i39++;
            intValue = i48;
            i38 = i60;
            chooseVersion = version2;
            errorCorrectionLevel2 = errorCorrectionLevel5;
            mode = mode2;
            i31 = i59;
        }
        ErrorCorrectionLevel errorCorrectionLevel6 = errorCorrectionLevel2;
        int i64 = intValue;
        Version version3 = chooseVersion;
        int i65 = i31;
        Mode mode3 = mode;
        if (i32 != i41) {
            throw new WriterException("Data bytes does not match offset");
        }
        BitArray bitArray4 = new BitArray();
        for (int i66 = 0; i66 < i42; i66++) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                byte[] bArr4 = ((BlockPair) it.next()).dataBytes;
                if (i66 < bArr4.length) {
                    bitArray4.appendBits(bArr4[i66], 8);
                }
            }
        }
        for (int i67 = 0; i67 < i40; i67++) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                byte[] bArr5 = ((BlockPair) it2.next()).errorCorrectionBytes;
                if (i67 < bArr5.length) {
                    bitArray4.appendBits(bArr5[i67], 8);
                }
            }
        }
        if (i65 != ((bitArray4.size + 7) >> 3)) {
            throw new WriterException(ConstraintWidget$$ExternalSyntheticOutline0.m19m(AbstractC0000x2c234b15.m1m("Interleaving error: ", i65, " and "), (bitArray4.size + 7) >> 3, " differ."));
        }
        QRCode qRCode = new QRCode();
        ErrorCorrectionLevel errorCorrectionLevel7 = errorCorrectionLevel6;
        qRCode.ecLevel = errorCorrectionLevel7;
        qRCode.mode = mode3;
        Version version4 = version3;
        qRCode.version = version4;
        int i68 = (version4.versionNumber * 4) + 17;
        ByteMatrix byteMatrix = new ByteMatrix(i68, i68);
        int i69 = Integer.MAX_VALUE;
        int i70 = 0;
        int i71 = -1;
        while (true) {
            i4 = byteMatrix.height;
            i5 = byteMatrix.width;
            if (i70 >= 8) {
                break;
            }
            MatrixUtil.buildMatrix(bitArray4, errorCorrectionLevel7, version4, i70, byteMatrix);
            int i72 = 0;
            int applyMaskPenaltyRule1Internal = MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, false) + MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, true);
            int i73 = 0;
            int i74 = 0;
            while (true) {
                int i75 = i4 - 1;
                bArr = byteMatrix.bytes;
                if (i72 >= i75) {
                    break;
                }
                while (i74 < i5 - 1) {
                    byte[] bArr6 = bArr[i72];
                    BitArray bitArray5 = bitArray4;
                    byte b2 = bArr6[i74];
                    int i76 = i74 + 1;
                    if (b2 == bArr6[i76]) {
                        byte[] bArr7 = bArr[i72 + 1];
                        if (b2 == bArr7[i74] && b2 == bArr7[i76]) {
                            i73++;
                        }
                    }
                    bitArray4 = bitArray5;
                    i74 = i76;
                }
                i72++;
                i74 = 0;
            }
            BitArray bitArray6 = bitArray4;
            int i77 = (i73 * 3) + applyMaskPenaltyRule1Internal;
            int i78 = 0;
            for (int i79 = 0; i79 < i4; i79++) {
                int i80 = 0;
                while (i80 < i5) {
                    int i81 = i80 + 6;
                    if (i81 < i5) {
                        byte[] bArr8 = bArr[i79];
                        version = version4;
                        errorCorrectionLevel = errorCorrectionLevel7;
                        if (bArr8[i80] == 1 && bArr8[i80 + 1] == 0 && bArr8[i80 + 2] == 1 && bArr8[i80 + 3] == 1 && bArr8[i80 + 4] == 1 && bArr8[i80 + 5] == 0 && bArr8[i81] == 1 && (((i8 = i80 + 10) < i5 && bArr8[i80 + 7] == 0 && bArr8[i80 + 8] == 0 && bArr8[i80 + 9] == 0 && bArr8[i8] == 0) || (i80 - 4 >= 0 && bArr8[i80 - 1] == 0 && bArr8[i80 - 2] == 0 && bArr8[i80 - 3] == 0 && bArr8[i9] == 0))) {
                            i78 += 40;
                        }
                    } else {
                        version = version4;
                        errorCorrectionLevel = errorCorrectionLevel7;
                    }
                    int i82 = i79 + 6;
                    if (i82 < i4 && bArr[i79][i80] == 1 && bArr[i79 + 1][i80] == 0 && bArr[i79 + 2][i80] == 1 && bArr[i79 + 3][i80] == 1 && bArr[i79 + 4][i80] == 1 && bArr[i79 + 5][i80] == 0 && bArr[i82][i80] == 1 && (((i6 = i79 + 10) < i4 && bArr[i79 + 7][i80] == 0 && bArr[i79 + 8][i80] == 0 && bArr[i79 + 9][i80] == 0 && bArr[i6][i80] == 0) || (i79 - 4 >= 0 && bArr[i79 - 1][i80] == 0 && bArr[i79 - 2][i80] == 0 && bArr[i79 - 3][i80] == 0 && bArr[i7][i80] == 0))) {
                        i78 += 40;
                    }
                    i80++;
                    version4 = version;
                    errorCorrectionLevel7 = errorCorrectionLevel;
                }
            }
            Version version5 = version4;
            ErrorCorrectionLevel errorCorrectionLevel8 = errorCorrectionLevel7;
            int i83 = i77 + i78;
            int i84 = 0;
            for (int i85 = 0; i85 < i4; i85++) {
                byte[] bArr9 = bArr[i85];
                for (int i86 = 0; i86 < i5; i86++) {
                    if (bArr9[i86] == 1) {
                        i84++;
                    }
                }
            }
            int abs = (((int) (Math.abs((i84 / (i4 * i5)) - 0.5d) * 20.0d)) * 10) + i83;
            if (abs < i69) {
                i69 = abs;
                i71 = i70;
            }
            i70++;
            bitArray4 = bitArray6;
            version4 = version5;
            errorCorrectionLevel7 = errorCorrectionLevel8;
        }
        qRCode.maskPattern = i71;
        MatrixUtil.buildMatrix(bitArray4, errorCorrectionLevel7, version4, i71, byteMatrix);
        qRCode.matrix = byteMatrix;
        int i87 = i64 << 1;
        int i88 = i5 + i87;
        int i89 = i87 + i4;
        int max = Math.max(i, i88);
        int max2 = Math.max(i2, i89);
        int min = Math.min(max / i88, max2 / i89);
        int i90 = (max - (i5 * min)) / 2;
        int i91 = (max2 - (i4 * min)) / 2;
        BitMatrix bitMatrix = new BitMatrix(max, max2);
        int i92 = 0;
        while (i92 < i4) {
            int i93 = 0;
            int i94 = i90;
            while (i93 < i5) {
                if (byteMatrix.get(i93, i92) == 1) {
                    bitMatrix.setRegion(i94, i91, min, min);
                }
                i93++;
                i94 += min;
            }
            i92++;
            i91 += min;
        }
        return bitMatrix;
    }
}
