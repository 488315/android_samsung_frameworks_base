package com.google.zxing.qrcode.encoder;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.StringUtils;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.MinimalEncoder;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Encoder {
    public static final int[] ALPHANUMERIC_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    public static final Charset DEFAULT_BYTE_MODE_ENCODING = StandardCharsets.ISO_8859_1;

    /* renamed from: com.google.zxing.qrcode.encoder.Encoder$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$zxing$qrcode$decoder$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$qrcode$decoder$Mode = iArr;
            try {
                iArr[Mode.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.ALPHANUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.KANJI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private Encoder() {
    }

    public static void appendBytes(String str, Mode mode, BitArray bitArray, Charset charset) throws WriterException {
        int i;
        int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
        int i3 = 0;
        if (i2 == 1) {
            int length = str.length();
            while (i3 < length) {
                int iCharAt = str.charAt(i3) - '0';
                int i4 = i3 + 2;
                if (i4 < length) {
                    bitArray.appendBits(((str.charAt(i3 + 1) - '0') * 10) + (iCharAt * 100) + (str.charAt(i4) - '0'), 10);
                    i3 += 3;
                } else {
                    i3++;
                    if (i3 < length) {
                        bitArray.appendBits((iCharAt * 10) + (str.charAt(i3) - '0'), 7);
                        i3 = i4;
                    } else {
                        bitArray.appendBits(iCharAt, 4);
                    }
                }
            }
            return;
        }
        if (i2 == 2) {
            int length2 = str.length();
            while (i3 < length2) {
                char cCharAt = str.charAt(i3);
                int[] iArr = ALPHANUMERIC_TABLE;
                int i5 = cCharAt < '`' ? iArr[cCharAt] : -1;
                if (i5 == -1) {
                    throw new WriterException();
                }
                int i6 = i3 + 1;
                if (i6 < length2) {
                    char cCharAt2 = str.charAt(i6);
                    int i7 = cCharAt2 < '`' ? iArr[cCharAt2] : -1;
                    if (i7 == -1) {
                        throw new WriterException();
                    }
                    bitArray.appendBits((i5 * 45) + i7, 11);
                    i3 += 2;
                } else {
                    bitArray.appendBits(i5, 6);
                    i3 = i6;
                }
            }
            return;
        }
        if (i2 == 3) {
            byte[] bytes = str.getBytes(charset);
            int length3 = bytes.length;
            while (i3 < length3) {
                bitArray.appendBits(bytes[i3], 8);
                i3++;
            }
            return;
        }
        if (i2 != 4) {
            throw new WriterException("Invalid mode: " + mode);
        }
        byte[] bytes2 = str.getBytes(StringUtils.SHIFT_JIS_CHARSET);
        if (bytes2.length % 2 != 0) {
            throw new WriterException("Kanji byte size not even");
        }
        int length4 = bytes2.length - 1;
        while (i3 < length4) {
            int i8 = ((bytes2[i3] & 255) << 8) | (bytes2[i3 + 1] & 255);
            int i9 = 33088;
            if (i8 >= 33088 && i8 <= 40956) {
                i = i8 - i9;
            } else if (i8 < 57408 || i8 > 60351) {
                i = -1;
            } else {
                i9 = 49472;
                i = i8 - i9;
            }
            if (i == -1) {
                throw new WriterException("Invalid byte sequence");
            }
            bitArray.appendBits(((i >> 8) * 192) + (i & 255), 13);
            i3 += 2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:204:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x05a6  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x05ca  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x05d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static QRCode encode(String str, ErrorCorrectionLevel errorCorrectionLevel, Map map) throws NumberFormatException, WriterException {
        int i;
        Mode mode;
        Version versionForNumber;
        BitArray bitArray;
        Version version;
        CharacterSetECI characterSetECI;
        int i2;
        int i3;
        int i4;
        byte[][] bArr;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        char c;
        EncodeHintType encodeHintType = EncodeHintType.GS1_FORMAT;
        EnumMap enumMap = (EnumMap) map;
        int i10 = 1;
        boolean z5 = enumMap.containsKey(encodeHintType) && Boolean.parseBoolean(enumMap.get(encodeHintType).toString());
        EncodeHintType encodeHintType2 = EncodeHintType.QR_COMPACT;
        EnumMap enumMap2 = (EnumMap) map;
        boolean z6 = enumMap2.containsKey(encodeHintType2) && Boolean.parseBoolean(enumMap2.get(encodeHintType2).toString());
        Object obj = DEFAULT_BYTE_MODE_ENCODING;
        EncodeHintType encodeHintType3 = EncodeHintType.CHARACTER_SET;
        boolean zContainsKey = ((EnumMap) map).containsKey(encodeHintType3);
        Charset charsetForName = zContainsKey ? Charset.forName(((EnumMap) map).get(encodeHintType3).toString()) : obj;
        int i11 = 8;
        char c2 = 4;
        if (z6) {
            mode = Mode.BYTE;
            if (charsetForName.equals(obj)) {
                charsetForName = null;
            }
            MinimalEncoder minimalEncoder = new MinimalEncoder(str, charsetForName, z5, errorCorrectionLevel);
            Version[] versionArr = {MinimalEncoder.getVersion(MinimalEncoder.VersionSize.SMALL), MinimalEncoder.getVersion(MinimalEncoder.VersionSize.MEDIUM), MinimalEncoder.getVersion(MinimalEncoder.VersionSize.LARGE)};
            MinimalEncoder.ResultList[] resultListArr = {minimalEncoder.encodeSpecificVersion(versionArr[0]), minimalEncoder.encodeSpecificVersion(versionArr[1]), minimalEncoder.encodeSpecificVersion(versionArr[2])};
            int i12 = -1;
            int i13 = Integer.MAX_VALUE;
            for (int i14 = 0; i14 < 3; i14++) {
                MinimalEncoder.ResultList resultList = resultListArr[i14];
                int size = resultList.getSize(resultList.version);
                if (willFit(size, versionArr[i14], minimalEncoder.ecLevel) && size < i13) {
                    i12 = i14;
                    i13 = size;
                }
            }
            if (i12 < 0) {
                throw new WriterException("Data too big for any version");
            }
            MinimalEncoder.ResultList resultList2 = resultListArr[i12];
            bitArray = new BitArray();
            ArrayList arrayList = (ArrayList) resultList2.list;
            int size2 = arrayList.size();
            int i15 = 0;
            while (i15 < size2) {
                Object obj2 = arrayList.get(i15);
                i15++;
                MinimalEncoder.ResultList.ResultNode resultNode = (MinimalEncoder.ResultList.ResultNode) obj2;
                Mode mode2 = resultNode.mode;
                bitArray.appendBits(mode2.getBits(), 4);
                MinimalEncoder.ResultList resultList3 = MinimalEncoder.ResultList.this;
                int i16 = resultNode.characterLength;
                int i17 = i10;
                if (i16 > 0) {
                    bitArray.appendBits(resultNode.getCharacterCountIndicator(), mode2.getCharacterCountBits(resultList3.version));
                }
                Mode mode3 = Mode.ECI;
                int i18 = resultNode.charsetEncoderIndex;
                if (mode2 == mode3) {
                    bitArray.appendBits(CharacterSetECI.getCharacterSetECI(MinimalEncoder.this.encoders.encoders[i18].charset()).getValue(), 8);
                } else if (i16 > 0) {
                    String str2 = MinimalEncoder.this.stringToEncode;
                    int i19 = resultNode.fromPosition;
                    appendBytes(str2.substring(i19, i16 + i19), mode2, bitArray, MinimalEncoder.this.encoders.encoders[i18].charset());
                }
                i10 = i17;
            }
            i = i10;
            version = resultList2.version;
        } else {
            i = 1;
            if (StringUtils.SHIFT_JIS_CHARSET.equals(charsetForName) && isOnlyDoubleByteKanji(str)) {
                mode = Mode.KANJI;
            } else {
                boolean z7 = false;
                boolean z8 = false;
                int i20 = 0;
                while (true) {
                    if (i20 < str.length()) {
                        char cCharAt = str.charAt(i20);
                        if (cCharAt < '0' || cCharAt > '9') {
                            if ((cCharAt < '`' ? ALPHANUMERIC_TABLE[cCharAt] : -1) == -1) {
                                mode = Mode.BYTE;
                                break;
                            }
                            z7 = true;
                        } else {
                            z8 = true;
                        }
                        i20++;
                    } else {
                        mode = z7 ? Mode.ALPHANUMERIC : z8 ? Mode.NUMERIC : Mode.BYTE;
                    }
                }
            }
            BitArray bitArray2 = new BitArray();
            Mode mode4 = Mode.BYTE;
            if (mode == mode4 && zContainsKey && (characterSetECI = CharacterSetECI.getCharacterSetECI(charsetForName)) != null) {
                bitArray2.appendBits(Mode.ECI.getBits(), 4);
                bitArray2.appendBits(characterSetECI.getValue(), 8);
            }
            if (z5) {
                bitArray2.appendBits(Mode.FNC1_FIRST_POSITION.getBits(), 4);
            }
            bitArray2.appendBits(mode.getBits(), 4);
            BitArray bitArray3 = new BitArray();
            appendBytes(str, mode, bitArray3, charsetForName);
            EncodeHintType encodeHintType4 = EncodeHintType.QR_VERSION;
            EnumMap enumMap3 = (EnumMap) map;
            if (!enumMap3.containsKey(encodeHintType4)) {
                int characterCountBits = mode.getCharacterCountBits(Version.getVersionForNumber(1)) + bitArray2.size + bitArray3.size;
                int i21 = 1;
                while (i21 <= 40) {
                    Version versionForNumber2 = Version.getVersionForNumber(i21);
                    if (willFit(characterCountBits, versionForNumber2, errorCorrectionLevel)) {
                        int characterCountBits2 = mode.getCharacterCountBits(versionForNumber2) + bitArray2.size + bitArray3.size;
                        int i22 = 1;
                        while (i22 <= 40) {
                            versionForNumber = Version.getVersionForNumber(i22);
                            if (!willFit(characterCountBits2, versionForNumber, errorCorrectionLevel)) {
                                i22++;
                                i11 = 8;
                            }
                        }
                        throw new WriterException("Data too big");
                    }
                    i21++;
                    i11 = 8;
                }
                throw new WriterException("Data too big");
            }
            Version versionForNumber3 = Version.getVersionForNumber(Integer.parseInt(enumMap3.get(encodeHintType4).toString()));
            if (!willFit(mode.getCharacterCountBits(versionForNumber3) + bitArray2.size + bitArray3.size, versionForNumber3, errorCorrectionLevel)) {
                throw new WriterException("Data too big for requested version");
            }
            versionForNumber = versionForNumber3;
            BitArray bitArray4 = new BitArray();
            int i23 = bitArray2.size;
            bitArray4.ensureCapacity(bitArray4.size + i23);
            for (int i24 = 0; i24 < i23; i24++) {
                bitArray4.appendBit(bitArray2.get(i24));
            }
            int sizeInBytes = mode == mode4 ? bitArray3.getSizeInBytes() : str.length();
            int characterCountBits3 = mode.getCharacterCountBits(versionForNumber);
            int i25 = 1 << characterCountBits3;
            if (sizeInBytes >= i25) {
                StringBuilder sb = new StringBuilder();
                sb.append(sizeInBytes);
                sb.append(" is bigger than ");
                sb.append(i25 - 1);
                throw new WriterException(sb.toString());
            }
            bitArray4.appendBits(sizeInBytes, characterCountBits3);
            int i26 = bitArray3.size;
            bitArray4.ensureCapacity(bitArray4.size + i26);
            for (int i27 = 0; i27 < i26; i27++) {
                bitArray4.appendBit(bitArray3.get(i27));
            }
            bitArray = bitArray4;
            version = versionForNumber;
        }
        Version.ECBlocks eCBlocks = version.ecBlocks[errorCorrectionLevel.ordinal()];
        int i28 = 0;
        for (Version.ECB ecb : eCBlocks.ecBlocks) {
            i28 += ecb.count;
        }
        int i29 = i28 * eCBlocks.ecCodewordsPerBlock;
        int i30 = version.totalCodewords;
        int i31 = i30 - i29;
        int i32 = i31 * 8;
        if (bitArray.size > i32) {
            throw new WriterException("data bits cannot fit in the QR Code" + bitArray.size + " > " + i32);
        }
        for (int i33 = 0; i33 < 4 && bitArray.size < i32; i33++) {
            bitArray.appendBit(false);
        }
        int i34 = bitArray.size & 7;
        if (i34 > 0) {
            while (i34 < i11) {
                bitArray.appendBit(false);
                i34++;
            }
        }
        int sizeInBytes2 = i31 - bitArray.getSizeInBytes();
        for (int i35 = 0; i35 < sizeInBytes2; i35++) {
            bitArray.appendBits((i35 & 1) == 0 ? IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState : 17, i11);
        }
        if (bitArray.size != i32) {
            throw new WriterException("Bits size does not equal capacity");
        }
        int i36 = 0;
        for (Version.ECB ecb2 : eCBlocks.ecBlocks) {
            i36 += ecb2.count;
        }
        if (bitArray.getSizeInBytes() != i31) {
            throw new WriterException("Number of bits and data bytes does not match");
        }
        ArrayList arrayList2 = new ArrayList(i36);
        int i37 = 0;
        int i38 = 0;
        int iMax = 0;
        int iMax2 = 0;
        while (i37 < i36) {
            char c3 = c2;
            int i39 = i;
            int[] iArr = new int[i39];
            int[] iArr2 = new int[i39];
            if (i37 >= i36) {
                throw new WriterException("Block ID too large");
            }
            int i40 = i30 % i36;
            int i41 = i36 - i40;
            int i42 = i30 / i36;
            int i43 = i31 / i36;
            int i44 = i43 + 1;
            int i45 = i42 - i43;
            int i46 = (i42 + 1) - i44;
            if (i45 != i46) {
                throw new WriterException("EC bytes mismatch");
            }
            if (i36 != i41 + i40) {
                throw new WriterException("RS blocks mismatch");
            }
            if (i30 != ((i44 + i46) * i40) + ((i43 + i45) * i41)) {
                throw new WriterException("Total bytes mismatch");
            }
            if (i37 < i41) {
                c = 0;
                iArr[0] = i43;
                iArr2[0] = i45;
            } else {
                c = 0;
                iArr[0] = i44;
                iArr2[0] = i46;
            }
            int i47 = iArr[c];
            byte[] bArr2 = new byte[i47];
            int i48 = i38 * 8;
            int i49 = i37;
            int i50 = 0;
            while (i50 < i47) {
                int i51 = i50;
                int i52 = i36;
                int[] iArr3 = iArr;
                int i53 = 0;
                for (int i54 = 0; i54 < 8; i54++) {
                    if (bitArray.get(i48)) {
                        i53 |= 1 << (7 - i54);
                    }
                    i48++;
                }
                bArr2[i51] = (byte) i53;
                i50 = i51 + 1;
                i36 = i52;
                iArr = iArr3;
            }
            int i55 = i36;
            int[] iArr4 = iArr;
            int i56 = iArr2[0];
            int[] iArr5 = new int[i47 + i56];
            for (int i57 = 0; i57 < i47; i57++) {
                iArr5[i57] = bArr2[i57] & 255;
            }
            new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(i56, iArr5);
            byte[] bArr3 = new byte[i56];
            int i58 = 0;
            while (i58 < i56) {
                int[] iArr6 = iArr5;
                bArr3[i58] = (byte) iArr6[i47 + i58];
                i58++;
                iArr5 = iArr6;
            }
            arrayList2.add(new BlockPair(bArr2, bArr3));
            iMax = Math.max(iMax, i47);
            iMax2 = Math.max(iMax2, i56);
            i38 += iArr4[0];
            i37 = i49 + 1;
            c2 = c3;
            i36 = i55;
            i = 1;
        }
        if (i31 != i38) {
            throw new WriterException("Data bytes does not match offset");
        }
        BitArray bitArray5 = new BitArray();
        for (int i59 = 0; i59 < iMax; i59++) {
            int size3 = arrayList2.size();
            int i60 = 0;
            while (i60 < size3) {
                Object obj3 = arrayList2.get(i60);
                i60++;
                byte[] bArr4 = ((BlockPair) obj3).dataBytes;
                if (i59 < bArr4.length) {
                    bitArray5.appendBits(bArr4[i59], 8);
                }
            }
        }
        for (int i61 = 0; i61 < iMax2; i61++) {
            int size4 = arrayList2.size();
            int i62 = 0;
            while (i62 < size4) {
                Object obj4 = arrayList2.get(i62);
                i62++;
                byte[] bArr5 = ((BlockPair) obj4).errorCorrectionBytes;
                if (i61 < bArr5.length) {
                    bitArray5.appendBits(bArr5[i61], 8);
                }
            }
        }
        if (i30 != bitArray5.getSizeInBytes()) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i30, "Interleaving error: ", " and ");
            sbM.append(bitArray5.getSizeInBytes());
            sbM.append(" differ.");
            throw new WriterException(sbM.toString());
        }
        QRCode qRCode = new QRCode();
        qRCode.ecLevel = errorCorrectionLevel;
        qRCode.mode = mode;
        qRCode.version = version;
        int i63 = (version.versionNumber * 4) + 17;
        ByteMatrix byteMatrix = new ByteMatrix(i63, i63);
        EncodeHintType encodeHintType5 = EncodeHintType.QR_MASK_PATTERN;
        EnumMap enumMap4 = (EnumMap) map;
        if (enumMap4.containsKey(encodeHintType5)) {
            i2 = Integer.parseInt(enumMap4.get(encodeHintType5).toString());
            if (!(i2 >= 0 && i2 < 8)) {
            }
        } else {
            i2 = -1;
        }
        int i64 = -1;
        if (i2 == -1) {
            int i65 = Integer.MAX_VALUE;
            for (int i66 = 0; i66 < 8; i66++) {
                MatrixUtil.buildMatrix(bitArray5, errorCorrectionLevel, version, i66, byteMatrix);
                int iApplyMaskPenaltyRule1Internal = MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, false) + MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, true);
                int i67 = 0;
                int i68 = 0;
                while (true) {
                    i3 = byteMatrix.height;
                    int i69 = i3 - 1;
                    i4 = byteMatrix.width;
                    bArr = byteMatrix.bytes;
                    if (i67 >= i69) {
                        break;
                    }
                    byte[] bArr6 = bArr[i67];
                    int i70 = 0;
                    while (i70 < i4 - 1) {
                        byte b = bArr6[i70];
                        int i71 = i70 + 1;
                        int i72 = i67;
                        if (b == bArr6[i71]) {
                            byte[] bArr7 = bArr[i72 + 1];
                            if (b == bArr7[i70] && b == bArr7[i71]) {
                                i68++;
                            }
                        }
                        i70 = i71;
                        i67 = i72;
                    }
                    i67++;
                }
                int i73 = (i68 * 3) + iApplyMaskPenaltyRule1Internal;
                int i74 = 0;
                int i75 = 0;
                while (i74 < i3) {
                    int i76 = 0;
                    while (i76 < i4) {
                        byte[] bArr8 = bArr[i74];
                        int i77 = i75;
                        int i78 = i76 + 6;
                        if (i78 < i4) {
                            i5 = i73;
                            if (bArr8[i76] == 1 && bArr8[i76 + 1] == 0 && bArr8[i76 + 2] == 1 && bArr8[i76 + 3] == 1 && bArr8[i76 + 4] == 1 && bArr8[i76 + 5] == 0 && bArr8[i78] == 1) {
                                int i79 = i76 - 4;
                                if (i79 < 0 || bArr8.length < i76) {
                                    z3 = false;
                                    if (!z3) {
                                        int i80 = i76 + 7;
                                        int i81 = i76 + 11;
                                        if (i80 < 0 || bArr8.length < i81) {
                                            z4 = false;
                                            if (!z4) {
                                            }
                                        } else {
                                            int i82 = i80;
                                            while (i82 < i81) {
                                                int i83 = i82;
                                                int i84 = i81;
                                                if (bArr8[i83] == 1) {
                                                    z4 = false;
                                                    break;
                                                }
                                                i82 = i83 + 1;
                                                i81 = i84;
                                            }
                                            z4 = true;
                                            if (!z4) {
                                            }
                                        }
                                    }
                                    i6 = i77 + 1;
                                } else {
                                    while (i79 < i76) {
                                        int i85 = i79;
                                        if (bArr8[i79] == 1) {
                                            z3 = false;
                                            break;
                                        }
                                        i79 = i85 + 1;
                                    }
                                    z3 = true;
                                    if (!z3) {
                                    }
                                    i6 = i77 + 1;
                                }
                            }
                            i7 = i74 + 6;
                            if (i7 >= i3) {
                                i9 = i6;
                                if (bArr[i74][i76] == 1 && bArr[i74 + 1][i76] == 0 && bArr[i74 + 2][i76] == 1 && bArr[i74 + 3][i76] == 1 && bArr[i74 + 4][i76] == 1 && bArr[i74 + 5][i76] == 0 && bArr[i7][i76] == 1) {
                                    int i86 = i74 - 4;
                                    if (i86 < 0 || bArr.length < i74) {
                                        z = false;
                                        if (z) {
                                            int i87 = i74 + 7;
                                            int i88 = i74 + 11;
                                            if (i87 < 0 || bArr.length < i88) {
                                                i8 = i74;
                                            } else {
                                                while (i87 < i88) {
                                                    i8 = i74;
                                                    if (bArr[i87][i76] != 1) {
                                                        i87++;
                                                        i74 = i8;
                                                    }
                                                }
                                                i8 = i74;
                                                z2 = true;
                                                if (!z2) {
                                                }
                                            }
                                            z2 = false;
                                            if (!z2) {
                                            }
                                        } else {
                                            i8 = i74;
                                        }
                                        i75 = i9 + 1;
                                    } else {
                                        while (i86 < i74) {
                                            if (bArr[i86][i76] == 1) {
                                                z = false;
                                                break;
                                            }
                                            i86++;
                                        }
                                        z = true;
                                        if (z) {
                                        }
                                        i75 = i9 + 1;
                                    }
                                    i76++;
                                    i73 = i5;
                                    i74 = i8;
                                } else {
                                    i8 = i74;
                                }
                            } else {
                                i8 = i74;
                                i9 = i6;
                            }
                            i75 = i9;
                            i76++;
                            i73 = i5;
                            i74 = i8;
                        } else {
                            i5 = i73;
                        }
                        i6 = i77;
                        i7 = i74 + 6;
                        if (i7 >= i3) {
                        }
                        i75 = i9;
                        i76++;
                        i73 = i5;
                        i74 = i8;
                    }
                    i74++;
                }
                int i89 = (i75 * 40) + i73;
                int i90 = 0;
                int i91 = 0;
                while (i90 < i3) {
                    byte[] bArr9 = bArr[i90];
                    int i92 = 0;
                    while (i92 < i4) {
                        int i93 = i90;
                        int i94 = i89;
                        if (bArr9[i92] == 1) {
                            i91++;
                        }
                        i92++;
                        i89 = i94;
                        i90 = i93;
                    }
                    i90++;
                }
                int i95 = i3 * i4;
                int iAbs = (((Math.abs((i91 * 2) - i95) * 10) / i95) * 10) + i89;
                if (iAbs < i65) {
                    i65 = iAbs;
                    i64 = i66;
                }
            }
            i2 = i64;
        }
        qRCode.maskPattern = i2;
        MatrixUtil.buildMatrix(bitArray5, errorCorrectionLevel, version, i2, byteMatrix);
        qRCode.matrix = byteMatrix;
        return qRCode;
    }

    public static boolean isOnlyDoubleByteKanji(String str) {
        byte[] bytes = str.getBytes(StringUtils.SHIFT_JIS_CHARSET);
        int length = bytes.length;
        if (length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < length; i += 2) {
            int i2 = bytes[i] & 255;
            if ((i2 < 129 || i2 > 159) && (i2 < 224 || i2 > 235)) {
                return false;
            }
        }
        return true;
    }

    public static boolean willFit(int i, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        int i2 = version.totalCodewords;
        Version.ECBlocks eCBlocks = version.ecBlocks[errorCorrectionLevel.ordinal()];
        int i3 = 0;
        for (Version.ECB ecb : eCBlocks.ecBlocks) {
            i3 += ecb.count;
        }
        return i2 - (i3 * eCBlocks.ecCodewordsPerBlock) >= (i + 7) / 8;
    }
}
