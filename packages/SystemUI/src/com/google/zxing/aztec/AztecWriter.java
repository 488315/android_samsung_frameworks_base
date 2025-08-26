package com.google.zxing.aztec;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.protobuf.BooleanArrayList$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.Encoder;
import com.google.zxing.aztec.encoder.HighLevelEncoder;
import com.google.zxing.aztec.encoder.State;
import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

/* loaded from: classes4.dex */
public final class AztecWriter implements Writer {
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b7  */
    @Override // com.google.zxing.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        byte[] bArr;
        char c;
        int i3;
        BitArray bitArrayStuffBits;
        int i4;
        boolean z;
        int iAbs;
        int i5;
        BitArray bitArrayGenerateCheckWords;
        int i6;
        int i7;
        int i8;
        EncodeHintType encodeHintType = EncodeHintType.CHARACTER_SET;
        EnumMap enumMap = (EnumMap) map;
        Charset charsetForName = enumMap.containsKey(encodeHintType) ? Charset.forName(enumMap.get(encodeHintType).toString()) : null;
        EncodeHintType encodeHintType2 = EncodeHintType.ERROR_CORRECTION;
        int i9 = enumMap.containsKey(encodeHintType2) ? Integer.parseInt(enumMap.get(encodeHintType2).toString()) : 33;
        EncodeHintType encodeHintType3 = EncodeHintType.AZTEC_LAYERS;
        int i10 = enumMap.containsKey(encodeHintType3) ? Integer.parseInt(enumMap.get(encodeHintType3).toString()) : 0;
        if (barcodeFormat != BarcodeFormat.AZTEC) {
            throw new IllegalArgumentException("Can only encode AZTEC, but got " + barcodeFormat);
        }
        HighLevelEncoder highLevelEncoder = new HighLevelEncoder(str.getBytes(charsetForName != null ? charsetForName : StandardCharsets.ISO_8859_1), charsetForName);
        State stateAppendFLGn = State.INITIAL_STATE;
        Charset charset = highLevelEncoder.charset;
        if (charset != null) {
            CharacterSetECI characterSetECI = CharacterSetECI.getCharacterSetECI(charset);
            if (characterSetECI == null) {
                throw new IllegalArgumentException("No ECI code for character set " + highLevelEncoder.charset);
            }
            stateAppendFLGn = stateAppendFLGn.appendFLGn(characterSetECI.getValue());
        }
        Collection<State> collectionSingletonList = Collections.singletonList(stateAppendFLGn);
        int i11 = 0;
        while (true) {
            bArr = highLevelEncoder.text;
            c = '\n';
            i3 = 3;
            int i12 = 4;
            int i13 = 2;
            boolean z2 = true;
            if (i11 >= bArr.length) {
                break;
            }
            int i14 = i11 + 1;
            byte b = i14 < bArr.length ? bArr[i14] : (byte) 0;
            byte b2 = bArr[i11];
            if (b2 != 13) {
                if (b2 != 44) {
                    if (b2 != 46) {
                        i7 = (b2 == 58 && b == 32) ? 5 : 0;
                    } else if (b == 32) {
                        i7 = 3;
                    }
                } else if (b == 32) {
                    i7 = 4;
                }
            } else if (b == 10) {
                i7 = 2;
            }
            if (i7 > 0) {
                LinkedList linkedList = new LinkedList();
                for (State state : collectionSingletonList) {
                    State stateEndBinaryShift = state.endBinaryShift(i11);
                    linkedList.add(stateEndBinaryShift.latchAndAppend(4, i7));
                    if (state.mode != 4) {
                        linkedList.add(stateEndBinaryShift.shiftAndAppend(4, i7));
                    }
                    if (i7 == 3 || i7 == 4) {
                        linkedList.add(stateEndBinaryShift.latchAndAppend(2, 16 - i7).latchAndAppend(2, 1));
                    }
                    if (state.binaryShiftByteCount > 0) {
                        linkedList.add(state.addBinaryShiftChar(i11).addBinaryShiftChar(i14));
                    }
                }
                collectionSingletonList = HighLevelEncoder.simplifyStates(linkedList);
                i11 = i14;
            } else {
                LinkedList linkedList2 = new LinkedList();
                for (State state2 : collectionSingletonList) {
                    char c2 = (char) (bArr[i11] & 255);
                    int i15 = state2.mode;
                    int[][] iArr = HighLevelEncoder.CHAR_MAP;
                    boolean z3 = iArr[i15][c2] > 0 ? z2 : false;
                    boolean z4 = z2;
                    int i16 = 0;
                    State stateEndBinaryShift2 = null;
                    while (true) {
                        i8 = state2.mode;
                        if (i16 > i12) {
                            break;
                        }
                        int i17 = iArr[i16][c2];
                        if (i17 > 0) {
                            if (stateEndBinaryShift2 == null) {
                                stateEndBinaryShift2 = state2.endBinaryShift(i11);
                            }
                            if (!z3 || i16 == i8 || i16 == i13) {
                                linkedList2.add(stateEndBinaryShift2.latchAndAppend(i16, i17));
                            }
                            if (!z3 && HighLevelEncoder.SHIFT_TABLE[i8][i16] >= 0) {
                                linkedList2.add(stateEndBinaryShift2.shiftAndAppend(i16, i17));
                            }
                        }
                        i16++;
                        i12 = 4;
                        i13 = 2;
                    }
                    if (state2.binaryShiftByteCount > 0 || iArr[i8][c2] == 0) {
                        linkedList2.add(state2.addBinaryShiftChar(i11));
                    }
                    z2 = z4;
                    i12 = 4;
                    i13 = 2;
                }
                collectionSingletonList = HighLevelEncoder.simplifyStates(linkedList2);
            }
            i11++;
        }
        State state3 = (State) Collections.min(collectionSingletonList, new Comparator(highLevelEncoder) { // from class: com.google.zxing.aztec.encoder.HighLevelEncoder.1
            public AnonymousClass1(HighLevelEncoder highLevelEncoder2) {
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((State) obj).bitCount - ((State) obj2).bitCount;
            }
        });
        state3.getClass();
        ArrayList arrayList = new ArrayList();
        for (Token token = state3.endBinaryShift(bArr.length).token; token != null; token = token.previous) {
            arrayList.add(token);
        }
        BitArray bitArray = new BitArray();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((Token) arrayList.get(size)).appendTo(bitArray, bArr);
        }
        int i18 = bitArray.size;
        int iM = BooleanArrayList$$ExternalSyntheticOutline0.m(i18, i9, 100, 11);
        int i19 = i18 + iM;
        int[] iArr2 = Encoder.WORD_SIZE;
        if (i10 == 0) {
            BitArray bitArrayStuffBits2 = null;
            int i20 = 0;
            int i21 = 0;
            while (i20 <= 32) {
                boolean z5 = i20 <= i3;
                int i22 = z5 ? i20 + 1 : i20;
                int i23 = ((i22 * 16) + (z5 ? 88 : 112)) * i22;
                if (i19 <= i23) {
                    if (bitArrayStuffBits2 == null || i21 != iArr2[i22]) {
                        int i24 = iArr2[i22];
                        i21 = i24;
                        bitArrayStuffBits2 = Encoder.stuffBits(bitArray, i24);
                    }
                    int i25 = i23 - (i23 % i21);
                    if ((!z5 || bitArrayStuffBits2.size <= i21 * 64) && bitArrayStuffBits2.size + iM <= i25) {
                        bitArrayStuffBits = bitArrayStuffBits2;
                        i4 = i21;
                        z = z5;
                        iAbs = i22;
                        i5 = i23;
                    }
                }
                i20++;
                c = c;
                i3 = 3;
            }
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        z = i10 < 0;
        iAbs = Math.abs(i10);
        if (iAbs > (z ? 4 : 32)) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i10, "Illegal value ", " for layers"));
        }
        i5 = ((iAbs * 16) + (z ? 88 : 112)) * iAbs;
        i4 = iArr2[iAbs];
        int i26 = i5 - (i5 % i4);
        bitArrayStuffBits = Encoder.stuffBits(bitArray, i4);
        int i27 = bitArrayStuffBits.size;
        if (iM + i27 > i26) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        if (z && i27 > i4 * 64) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        BitArray bitArrayGenerateCheckWords2 = Encoder.generateCheckWords(bitArrayStuffBits, i5, i4);
        int i28 = bitArrayStuffBits.size / i4;
        BitArray bitArray2 = new BitArray();
        if (z) {
            bitArray2.appendBits(iAbs - 1, 2);
            bitArray2.appendBits(i28 - 1, 6);
            bitArrayGenerateCheckWords = Encoder.generateCheckWords(bitArray2, 28, 4);
        } else {
            bitArray2.appendBits(iAbs - 1, 5);
            bitArray2.appendBits(i28 - 1, 11);
            bitArrayGenerateCheckWords = Encoder.generateCheckWords(bitArray2, 40, 4);
        }
        int i29 = (iAbs * 4) + (z ? 11 : 14);
        int[] iArr3 = new int[i29];
        if (z) {
            for (int i30 = 0; i30 < i29; i30++) {
                iArr3[i30] = i30;
            }
            i6 = i29;
        } else {
            int i31 = i29 / 2;
            i6 = (((i31 - 1) / 15) * 2) + i29 + 1;
            int i32 = i6 / 2;
            for (int i33 = 0; i33 < i31; i33++) {
                iArr3[(i31 - i33) - 1] = (i32 - r11) - 1;
                iArr3[i31 + i33] = (i33 / 15) + i33 + i32 + 1;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i6);
        int i34 = 0;
        for (int i35 = 0; i35 < iAbs; i35++) {
            int i36 = ((iAbs - i35) * 4) + (z ? 9 : 12);
            for (int i37 = 0; i37 < i36; i37++) {
                int i38 = i37 * 2;
                for (int i39 = 0; i39 < 2; i39++) {
                    if (bitArrayGenerateCheckWords2.get(i34 + i38 + i39)) {
                        int i40 = i35 * 2;
                        bitMatrix.set(iArr3[i40 + i39], iArr3[i40 + i37]);
                    }
                    if (bitArrayGenerateCheckWords2.get((i36 * 2) + i34 + i38 + i39)) {
                        int i41 = i35 * 2;
                        bitMatrix.set(iArr3[i41 + i37], iArr3[((i29 - 1) - i41) - i39]);
                    }
                    if (bitArrayGenerateCheckWords2.get((i36 * 4) + i34 + i38 + i39)) {
                        int i42 = (i29 - 1) - (i35 * 2);
                        bitMatrix.set(iArr3[i42 - i39], iArr3[i42 - i37]);
                    }
                    if (bitArrayGenerateCheckWords2.get((i36 * 6) + i34 + i38 + i39)) {
                        int i43 = i35 * 2;
                        bitMatrix.set(iArr3[((i29 - 1) - i43) - i37], iArr3[i43 + i39]);
                    }
                }
            }
            i34 += i36 * 8;
        }
        int i44 = i6 / 2;
        if (z) {
            for (int i45 = 0; i45 < 7; i45++) {
                int i46 = (i44 - 3) + i45;
                if (bitArrayGenerateCheckWords.get(i45)) {
                    bitMatrix.set(i46, i44 - 5);
                }
                if (bitArrayGenerateCheckWords.get(i45 + 7)) {
                    bitMatrix.set(i44 + 5, i46);
                }
                if (bitArrayGenerateCheckWords.get(20 - i45)) {
                    bitMatrix.set(i46, i44 + 5);
                }
                if (bitArrayGenerateCheckWords.get(27 - i45)) {
                    bitMatrix.set(i44 - 5, i46);
                }
            }
        } else {
            for (int i47 = 0; i47 < 10; i47++) {
                int i48 = (i47 / 5) + (i44 - 5) + i47;
                if (bitArrayGenerateCheckWords.get(i47)) {
                    bitMatrix.set(i48, i44 - 7);
                }
                if (bitArrayGenerateCheckWords.get(i47 + 10)) {
                    bitMatrix.set(i44 + 7, i48);
                }
                if (bitArrayGenerateCheckWords.get(29 - i47)) {
                    bitMatrix.set(i48, i44 + 7);
                }
                if (bitArrayGenerateCheckWords.get(39 - i47)) {
                    bitMatrix.set(i44 - 7, i48);
                }
            }
        }
        if (z) {
            Encoder.drawBullsEye(bitMatrix, i44, 5);
        } else {
            Encoder.drawBullsEye(bitMatrix, i44, 7);
            int i49 = 0;
            int i50 = 0;
            while (i49 < (i29 / 2) - 1) {
                for (int i51 = i44 & 1; i51 < i6; i51 += 2) {
                    int i52 = i44 - i50;
                    bitMatrix.set(i52, i51);
                    int i53 = i44 + i50;
                    bitMatrix.set(i53, i51);
                    bitMatrix.set(i51, i52);
                    bitMatrix.set(i51, i53);
                }
                i49 += 15;
                i50 += 16;
            }
        }
        new AztecCode();
        int i54 = bitMatrix.width;
        int i55 = bitMatrix.height;
        int iMax = Math.max(i, i54);
        int iMax2 = Math.max(i2, i55);
        int iMin = Math.min(iMax / i54, iMax2 / i55);
        int i56 = (iMax - (i54 * iMin)) / 2;
        int i57 = (iMax2 - (i55 * iMin)) / 2;
        BitMatrix bitMatrix2 = new BitMatrix(iMax, iMax2);
        int i58 = 0;
        while (i58 < i55) {
            int i59 = i56;
            int i60 = 0;
            while (i60 < i54) {
                if (bitMatrix.get(i60, i58)) {
                    bitMatrix2.setRegion(i59, i57, iMin, iMin);
                }
                i60++;
                i59 += iMin;
            }
            i58++;
            i57 += iMin;
        }
        return bitMatrix2;
    }
}
