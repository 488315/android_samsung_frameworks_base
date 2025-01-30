package com.google.zxing.aztec;

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
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AztecWriter implements Writer {
    public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");

    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        byte[] bArr;
        int i3;
        char c;
        BitArray bitArray;
        int i4;
        boolean z;
        int i5;
        int i6;
        BitArray generateCheckWords;
        int i7;
        int i8;
        int i9;
        int i10;
        String str2 = (String) ((HashMap) map).get(EncodeHintType.CHARACTER_SET);
        Number number = (Number) ((HashMap) map).get(EncodeHintType.ERROR_CORRECTION);
        Integer num = (Integer) ((HashMap) map).get(EncodeHintType.AZTEC_LAYERS);
        Charset forName = str2 == null ? DEFAULT_CHARSET : Charset.forName(str2);
        int intValue = number == null ? 33 : number.intValue();
        int intValue2 = num == null ? 0 : num.intValue();
        if (barcodeFormat != BarcodeFormat.AZTEC) {
            throw new IllegalArgumentException("Can only encode AZTEC, but got " + barcodeFormat);
        }
        HighLevelEncoder highLevelEncoder = new HighLevelEncoder(str.getBytes(forName));
        List<State> singletonList = Collections.singletonList(State.INITIAL_STATE);
        int i11 = 0;
        while (true) {
            bArr = highLevelEncoder.text;
            i3 = 3;
            c = '\n';
            int i12 = 4;
            int i13 = 2;
            boolean z2 = true;
            if (i11 >= bArr.length) {
                break;
            }
            int i14 = i11 + 1;
            byte b = i14 < bArr.length ? bArr[i14] : (byte) 0;
            byte b2 = bArr[i11];
            if (b2 == 13) {
                if (b == 10) {
                    i8 = 2;
                }
                i8 = 0;
            } else if (b2 == 44) {
                if (b == 32) {
                    i8 = 4;
                }
                i8 = 0;
            } else if (b2 != 46) {
                if (b2 == 58 && b == 32) {
                    i8 = 5;
                }
                i8 = 0;
            } else {
                if (b == 32) {
                    i8 = 3;
                }
                i8 = 0;
            }
            if (i8 > 0) {
                LinkedList linkedList = new LinkedList();
                for (State state : singletonList) {
                    State endBinaryShift = state.endBinaryShift(i11);
                    linkedList.add(endBinaryShift.latchAndAppend(4, i8));
                    if (state.mode != 4) {
                        linkedList.add(endBinaryShift.shiftAndAppend(4, i8));
                    }
                    if (i8 == 3 || i8 == 4) {
                        linkedList.add(endBinaryShift.latchAndAppend(2, 16 - i8).latchAndAppend(2, 1));
                    }
                    if (state.binaryShiftByteCount > 0) {
                        linkedList.add(state.addBinaryShiftChar(i11).addBinaryShiftChar(i14));
                    }
                }
                singletonList = HighLevelEncoder.simplifyStates(linkedList);
                i11 = i14;
                i9 = 1;
            } else {
                LinkedList linkedList2 = new LinkedList();
                for (State state2 : singletonList) {
                    char c2 = (char) (bArr[i11] & 255);
                    int i15 = state2.mode;
                    int[][] iArr = HighLevelEncoder.CHAR_MAP;
                    boolean z3 = iArr[i15][c2] > 0 ? z2 : false;
                    int i16 = 0;
                    State state3 = null;
                    while (true) {
                        i10 = state2.mode;
                        if (i16 > i12) {
                            break;
                        }
                        int i17 = iArr[i16][c2];
                        if (i17 > 0) {
                            if (state3 == null) {
                                state3 = state2.endBinaryShift(i11);
                            }
                            if (!z3 || i16 == i10 || i16 == i13) {
                                linkedList2.add(state3.latchAndAppend(i16, i17));
                            }
                            if (!z3 && HighLevelEncoder.SHIFT_TABLE[i10][i16] >= 0) {
                                linkedList2.add(state3.shiftAndAppend(i16, i17));
                            }
                        }
                        i16++;
                        i12 = 4;
                        i13 = 2;
                    }
                    if (state2.binaryShiftByteCount > 0 || iArr[i10][c2] == 0) {
                        linkedList2.add(state2.addBinaryShiftChar(i11));
                    }
                    i12 = 4;
                    i13 = 2;
                    z2 = true;
                }
                singletonList = HighLevelEncoder.simplifyStates(linkedList2);
                i9 = 1;
            }
            i11 += i9;
        }
        State state4 = (State) Collections.min(singletonList, new Comparator(highLevelEncoder) { // from class: com.google.zxing.aztec.encoder.HighLevelEncoder.1
            public C45481(HighLevelEncoder highLevelEncoder2) {
            }

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((State) obj).bitCount - ((State) obj2).bitCount;
            }
        });
        state4.getClass();
        LinkedList linkedList3 = new LinkedList();
        for (Token token = state4.endBinaryShift(bArr.length).token; token != null; token = token.previous) {
            linkedList3.addFirst(token);
        }
        BitArray bitArray2 = new BitArray();
        Iterator it = linkedList3.iterator();
        while (it.hasNext()) {
            ((Token) it.next()).appendTo(bitArray2, bArr);
        }
        int i18 = bitArray2.size;
        int i19 = ((intValue * i18) / 100) + 11;
        int i20 = i18 + i19;
        int[] iArr2 = Encoder.WORD_SIZE;
        if (intValue2 == 0) {
            int i21 = 0;
            BitArray bitArray3 = null;
            int i22 = 0;
            while (i21 <= 32) {
                boolean z4 = i21 <= i3;
                int i23 = z4 ? i21 + 1 : i21;
                int i24 = ((i23 * 16) + (z4 ? 88 : 112)) * i23;
                if (i20 <= i24) {
                    int i25 = iArr2[i23];
                    if (i22 != i25) {
                        bitArray3 = Encoder.stuffBits(i25, bitArray2);
                        i22 = i25;
                    }
                    int i26 = i24 - (i24 % i22);
                    if ((!z4 || bitArray3.size <= i22 * 64) && bitArray3.size + i19 <= i26) {
                        bitArray = bitArray3;
                        i4 = i22;
                        z = z4;
                        i5 = i23;
                        i6 = i24;
                    }
                }
                i21++;
                c = c;
                i3 = 3;
            }
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        z = intValue2 < 0;
        i5 = Math.abs(intValue2);
        if (i5 > (z ? 4 : 32)) {
            throw new IllegalArgumentException(String.format("Illegal value %s for layers", Integer.valueOf(intValue2)));
        }
        i6 = ((i5 * 16) + (z ? 88 : 112)) * i5;
        i4 = iArr2[i5];
        int i27 = i6 - (i6 % i4);
        bitArray = Encoder.stuffBits(i4, bitArray2);
        int i28 = bitArray.size;
        if (i19 + i28 > i27) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        if (z && i28 > i4 * 64) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        BitArray generateCheckWords2 = Encoder.generateCheckWords(bitArray, i6, i4);
        int i29 = bitArray.size / i4;
        BitArray bitArray4 = new BitArray();
        if (z) {
            bitArray4.appendBits(i5 - 1, 2);
            bitArray4.appendBits(i29 - 1, 6);
            generateCheckWords = Encoder.generateCheckWords(bitArray4, 28, 4);
        } else {
            bitArray4.appendBits(i5 - 1, 5);
            bitArray4.appendBits(i29 - 1, 11);
            generateCheckWords = Encoder.generateCheckWords(bitArray4, 40, 4);
        }
        int i30 = i5 * 4;
        int i31 = z ? i30 + 11 : i30 + 14;
        int[] iArr3 = new int[i31];
        if (z) {
            for (int i32 = 0; i32 < i31; i32++) {
                iArr3[i32] = i32;
            }
            i7 = i31;
        } else {
            int i33 = i31 / 2;
            i7 = (((i33 - 1) / 15) * 2) + i31 + 1;
            int i34 = i7 / 2;
            for (int i35 = 0; i35 < i33; i35++) {
                int i36 = (i35 / 15) + i35;
                iArr3[(i33 - i35) - 1] = (i34 - i36) - 1;
                iArr3[i33 + i35] = i36 + i34 + 1;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i7);
        int i37 = 0;
        for (int i38 = 0; i38 < i5; i38++) {
            int i39 = z ? ((i5 - i38) * 4) + 9 : ((i5 - i38) * 4) + 12;
            for (int i40 = 0; i40 < i39; i40++) {
                int i41 = i40 * 2;
                int i42 = 0;
                for (int i43 = 2; i42 < i43; i43 = 2) {
                    if (generateCheckWords2.get(i37 + i41 + i42)) {
                        int i44 = i38 * 2;
                        bitMatrix.set(iArr3[i44 + i42], iArr3[i44 + i40]);
                    }
                    if (generateCheckWords2.get((i39 * 2) + i37 + i41 + i42)) {
                        int i45 = i38 * 2;
                        bitMatrix.set(iArr3[i45 + i40], iArr3[((i31 - 1) - i45) - i42]);
                    }
                    if (generateCheckWords2.get((i39 * 4) + i37 + i41 + i42)) {
                        int i46 = (i31 - 1) - (i38 * 2);
                        bitMatrix.set(iArr3[i46 - i42], iArr3[i46 - i40]);
                    }
                    if (generateCheckWords2.get((i39 * 6) + i37 + i41 + i42)) {
                        int i47 = i38 * 2;
                        bitMatrix.set(iArr3[((i31 - 1) - i47) - i40], iArr3[i47 + i42]);
                    }
                    i42++;
                }
            }
            i37 += i39 * 8;
        }
        int i48 = i7 / 2;
        if (z) {
            for (int i49 = 0; i49 < 7; i49++) {
                int i50 = (i48 - 3) + i49;
                if (generateCheckWords.get(i49)) {
                    bitMatrix.set(i50, i48 - 5);
                }
                if (generateCheckWords.get(i49 + 7)) {
                    bitMatrix.set(i48 + 5, i50);
                }
                if (generateCheckWords.get(20 - i49)) {
                    bitMatrix.set(i50, i48 + 5);
                }
                if (generateCheckWords.get(27 - i49)) {
                    bitMatrix.set(i48 - 5, i50);
                }
            }
        } else {
            for (int i51 = 0; i51 < 10; i51++) {
                int i52 = (i51 / 5) + (i48 - 5) + i51;
                if (generateCheckWords.get(i51)) {
                    bitMatrix.set(i52, i48 - 7);
                }
                if (generateCheckWords.get(i51 + 10)) {
                    bitMatrix.set(i48 + 7, i52);
                }
                if (generateCheckWords.get(29 - i51)) {
                    bitMatrix.set(i52, i48 + 7);
                }
                if (generateCheckWords.get(39 - i51)) {
                    bitMatrix.set(i48 - 7, i52);
                }
            }
        }
        if (z) {
            Encoder.drawBullsEye(bitMatrix, i48, 5);
        } else {
            Encoder.drawBullsEye(bitMatrix, i48, 7);
            int i53 = 0;
            int i54 = 0;
            while (i53 < (i31 / 2) - 1) {
                for (int i55 = i48 & 1; i55 < i7; i55 += 2) {
                    int i56 = i48 - i54;
                    bitMatrix.set(i56, i55);
                    int i57 = i48 + i54;
                    bitMatrix.set(i57, i55);
                    bitMatrix.set(i55, i56);
                    bitMatrix.set(i55, i57);
                }
                i53 += 15;
                i54 += 16;
            }
        }
        new AztecCode();
        int i58 = bitMatrix.width;
        int max = Math.max(i, i58);
        int i59 = bitMatrix.height;
        int max2 = Math.max(i2, i59);
        int min = Math.min(max / i58, max2 / i59);
        int i60 = (max - (i58 * min)) / 2;
        int i61 = (max2 - (i59 * min)) / 2;
        BitMatrix bitMatrix2 = new BitMatrix(max, max2);
        int i62 = 0;
        while (i62 < i59) {
            int i63 = i60;
            int i64 = 0;
            while (i64 < i58) {
                if (bitMatrix.get(i64, i62)) {
                    bitMatrix2.setRegion(i63, i61, min, min);
                }
                i64++;
                i63 += min;
            }
            i62++;
            i61 += min;
        }
        return bitMatrix2;
    }
}
