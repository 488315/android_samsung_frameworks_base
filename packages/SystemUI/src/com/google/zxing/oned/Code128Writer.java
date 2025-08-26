package com.google.zxing.oned;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {

    enum CType {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    public final class MinimalEncoder {
        public int[][] memoizedCost;
        public Latch[][] minPath;

        enum Charset {
            A,
            B,
            C,
            NONE
        }

        enum Latch {
            /* JADX INFO: Fake field, exist only in values array */
            A,
            /* JADX INFO: Fake field, exist only in values array */
            B,
            C,
            SHIFT,
            NONE
        }

        public /* synthetic */ MinimalEncoder(int i) {
            this();
        }

        public static void addPattern(Collection collection, int i, int[] iArr, int[] iArr2, int i2) {
            ((ArrayList) collection).add(Code128Reader.CODE_PATTERNS[i]);
            if (i2 != 0) {
                iArr2[0] = iArr2[0] + 1;
            }
            iArr[0] = (i * iArr2[0]) + iArr[0];
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x0059 A[ADDED_TO_REGION, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x005a A[RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static boolean canEncode(CharSequence charSequence, Charset charset, int i) {
            char cCharAt;
            String str = (String) charSequence;
            char cCharAt2 = str.charAt(i);
            int iOrdinal = charset.ordinal();
            if (iOrdinal != 0) {
                if (iOrdinal == 1) {
                    return cCharAt2 == 241 || cCharAt2 == 242 || cCharAt2 == 243 || cCharAt2 == 244 || " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007fÿ".indexOf(cCharAt2) >= 0;
                }
                if (iOrdinal == 2) {
                    if (cCharAt2 != 241) {
                        int i2 = i + 1;
                        return i2 < str.length() && cCharAt2 >= '0' && cCharAt2 <= '9' && (cCharAt = str.charAt(i2)) >= '0' && cCharAt <= '9';
                    }
                }
            }
            if (cCharAt2 == 241 || cCharAt2 == 242 || cCharAt2 == 243 || cCharAt2 == 244 || " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001fÿ".indexOf(cCharAt2) >= 0) {
            }
        }

        public final int encode(CharSequence charSequence, Charset charset, int i) {
            int iEncode;
            int iEncode2;
            int i2 = this.memoizedCost[charset.ordinal()][i];
            if (i2 > 0) {
                return i2;
            }
            Latch latch = Latch.NONE;
            int i3 = i + 1;
            String str = (String) charSequence;
            int i4 = 0;
            boolean z = i3 >= str.length();
            Charset[] charsetArr = {Charset.A, Charset.B};
            int i5 = Integer.MAX_VALUE;
            while (true) {
                if (i4 > 1) {
                    break;
                }
                if (canEncode(str, charsetArr[i4], i)) {
                    Latch latchValueOf = Latch.NONE;
                    Charset charset2 = charsetArr[i4];
                    if (charset != charset2) {
                        latchValueOf = Latch.valueOf(charset2.toString());
                        iEncode2 = 2;
                    } else {
                        iEncode2 = 1;
                    }
                    if (!z) {
                        iEncode2 += encode(str, charsetArr[i4], i3);
                    }
                    if (iEncode2 < i5) {
                        latch = latchValueOf;
                        i5 = iEncode2;
                    }
                    if (charset == charsetArr[(i4 + 1) % 2]) {
                        Latch latch2 = Latch.SHIFT;
                        int iEncode3 = z ? 2 : encode(str, charset, i3) + 2;
                        if (iEncode3 < i5) {
                            i5 = iEncode3;
                            latch = latch2;
                        }
                    }
                }
                i4++;
            }
            Charset charset3 = Charset.C;
            if (canEncode(str, charset3, i)) {
                Latch latch3 = Latch.NONE;
                if (charset != charset3) {
                    latch3 = Latch.C;
                    iEncode = 2;
                } else {
                    iEncode = 1;
                }
                int i6 = (str.charAt(i) != 241 ? 2 : 1) + i;
                if (i6 < str.length()) {
                    iEncode += encode(str, charset3, i6);
                }
                if (iEncode < i5) {
                    latch = latch3;
                    i5 = iEncode;
                }
            }
            if (i5 != Integer.MAX_VALUE) {
                this.memoizedCost[charset.ordinal()][i] = i5;
                this.minPath[charset.ordinal()][i] = latch;
                return i5;
            }
            throw new IllegalArgumentException("Bad character in input: ASCII value=" + ((int) str.charAt(i)));
        }

        private MinimalEncoder() {
        }
    }

    public static CType findCType(int i, CharSequence charSequence) {
        String str = (String) charSequence;
        int length = str.length();
        if (i >= length) {
            return CType.UNCODABLE;
        }
        char cCharAt = str.charAt(i);
        if (cCharAt == 241) {
            return CType.FNC_1;
        }
        if (cCharAt < '0' || cCharAt > '9') {
            return CType.UNCODABLE;
        }
        int i2 = i + 1;
        if (i2 >= length) {
            return CType.ONE_DIGIT;
        }
        char cCharAt2 = str.charAt(i2);
        return (cCharAt2 < '0' || cCharAt2 > '9') ? CType.ONE_DIGIT : CType.TWO_DIGITS;
    }

    public static boolean[] produceResult(int i, Collection collection) {
        int[][] iArr = Code128Reader.CODE_PATTERNS;
        ArrayList arrayList = (ArrayList) collection;
        arrayList.add(iArr[i % 103]);
        arrayList.add(iArr[106]);
        int size = arrayList.size();
        int iAppendPattern = 0;
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            for (int i4 : (int[]) obj) {
                i2 += i4;
            }
        }
        boolean[] zArr = new boolean[i2];
        int size2 = arrayList.size();
        int i5 = 0;
        while (i5 < size2) {
            Object obj2 = arrayList.get(i5);
            i5++;
            iAppendPattern += OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern, (int[]) obj2, true);
        }
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        return encode(str, null);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.CODE_128);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0260, code lost:
    
        if (r6 > 244) goto L162;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x018f  */
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean[] encode(String str, Map map) throws NumberFormatException {
        int i;
        char c;
        boolean z;
        int i2;
        int iCharAt;
        CType cTypeFindCType;
        char c2;
        MinimalEncoder.Charset charset;
        int i3;
        int iCharAt2;
        String string;
        int i4 = 3;
        char c3 = ' ';
        int i5 = 0;
        int i6 = -1;
        char c4 = 2;
        boolean z2 = true;
        int length = str.length();
        if (length < 1 || length > 80) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(length, "Contents length should be between 1 and 80 characters, but got "));
        }
        if (map != null) {
            EncodeHintType encodeHintType = EncodeHintType.FORCE_CODE_SET;
            EnumMap enumMap = (EnumMap) map;
            if (enumMap.containsKey(encodeHintType)) {
                string = enumMap.get(encodeHintType).toString();
                string.getClass();
                switch (string) {
                    case "A":
                        i = 101;
                        break;
                    case "B":
                        i = 100;
                        break;
                    case "C":
                        i = 99;
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported code set hint: ".concat(string));
                }
            } else {
                i = -1;
            }
        }
        for (int i7 = 0; i7 < length; i7++) {
            char cCharAt = str.charAt(i7);
            switch (cCharAt) {
                case IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState /* 241 */:
                case IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState /* 242 */:
                case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                case IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode /* 244 */:
                    break;
                default:
                    if (cCharAt > 127) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(cCharAt, "Bad character in input: ASCII value="));
                    }
                    break;
            }
            switch (i) {
                case 99:
                    if (cCharAt < '0' || ((cCharAt > '9' && cCharAt <= 127) || cCharAt == 242 || cCharAt == 243 || cCharAt == 244)) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(cCharAt, "Bad character in input for forced code set C: ASCII value="));
                    }
                    break;
                    break;
                case 100:
                    if (cCharAt < ' ') {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(cCharAt, "Bad character in input for forced code set B: ASCII value="));
                    }
                    break;
                case 101:
                    if (cCharAt > '_' && cCharAt <= 127) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(cCharAt, "Bad character in input for forced code set A: ASCII value="));
                    }
                    break;
            }
        }
        if (map != null) {
            EncodeHintType encodeHintType2 = EncodeHintType.CODE128_COMPACT;
            EnumMap enumMap2 = (EnumMap) map;
            if (enumMap2.containsKey(encodeHintType2) && Boolean.parseBoolean(enumMap2.get(encodeHintType2).toString())) {
                MinimalEncoder minimalEncoder = new MinimalEncoder(i5);
                minimalEncoder.memoizedCost = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 4, str.length());
                minimalEncoder.minPath = (MinimalEncoder.Latch[][]) Array.newInstance((Class<?>) MinimalEncoder.Latch.class, 4, str.length());
                MinimalEncoder.Charset charset2 = MinimalEncoder.Charset.NONE;
                minimalEncoder.encode(str, charset2, 0);
                ArrayList arrayList = new ArrayList();
                int[] iArr = {0};
                int[] iArr2 = {1};
                int length2 = str.length();
                int i8 = 0;
                while (i8 < length2) {
                    MinimalEncoder.Latch latch = minimalEncoder.minPath[charset2.ordinal()][i8];
                    char c5 = c3;
                    int iOrdinal = latch.ordinal();
                    if (iOrdinal == 0) {
                        charset = MinimalEncoder.Charset.A;
                        MinimalEncoder.addPattern(arrayList, i8 == 0 ? 103 : 101, iArr, iArr2, i8);
                    } else if (iOrdinal == 1) {
                        charset = MinimalEncoder.Charset.B;
                        MinimalEncoder.addPattern(arrayList, i8 == 0 ? 104 : 100, iArr, iArr2, i8);
                    } else if (iOrdinal != 2) {
                        if (iOrdinal == i4) {
                            MinimalEncoder.addPattern(arrayList, 98, iArr, iArr2, i8);
                        }
                        if (charset2 == MinimalEncoder.Charset.C) {
                            switch (str.charAt(i8)) {
                                case IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState /* 241 */:
                                    i3 = i4;
                                    iCharAt2 = 102;
                                    break;
                                case IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState /* 242 */:
                                    i3 = i4;
                                    iCharAt2 = 97;
                                    break;
                                case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                                    i3 = i4;
                                    iCharAt2 = 96;
                                    break;
                                case IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode /* 244 */:
                                    if ((charset2 != MinimalEncoder.Charset.A || latch == MinimalEncoder.Latch.SHIFT) && (charset2 != MinimalEncoder.Charset.B || latch != MinimalEncoder.Latch.SHIFT)) {
                                        i3 = i4;
                                        iCharAt2 = 100;
                                        break;
                                    } else {
                                        i3 = i4;
                                        iCharAt2 = 101;
                                        break;
                                    }
                                default:
                                    iCharAt2 = str.charAt(i8) - ' ';
                                    i3 = i4;
                                    break;
                            }
                            if (((charset2 == MinimalEncoder.Charset.A && latch != MinimalEncoder.Latch.SHIFT) || (charset2 == MinimalEncoder.Charset.B && latch == MinimalEncoder.Latch.SHIFT)) && iCharAt2 < 0) {
                                iCharAt2 += 96;
                            }
                            MinimalEncoder.addPattern(arrayList, iCharAt2, iArr, iArr2, i8);
                        } else if (str.charAt(i8) == 241) {
                            MinimalEncoder.addPattern(arrayList, 102, iArr, iArr2, i8);
                            i3 = i4;
                        } else {
                            MinimalEncoder.addPattern(arrayList, Integer.parseInt(str.substring(i8, i8 + 2)), iArr, iArr2, i8);
                            int i9 = i8 + 1;
                            i3 = i4;
                            if (i9 < length2) {
                                i8 = i9;
                            }
                        }
                        i8++;
                        c3 = c5;
                        i4 = i3;
                    } else {
                        charset = MinimalEncoder.Charset.C;
                        MinimalEncoder.addPattern(arrayList, i8 == 0 ? 105 : 99, iArr, iArr2, i8);
                    }
                    charset2 = charset;
                    if (charset2 == MinimalEncoder.Charset.C) {
                    }
                    i8++;
                    c3 = c5;
                    i4 = i3;
                }
                minimalEncoder.memoizedCost = null;
                minimalEncoder.minPath = null;
                return produceResult(iArr[0], arrayList);
            }
        }
        char c6 = ' ';
        char c7 = '`';
        int length3 = str.length();
        ArrayList arrayList2 = new ArrayList();
        int i10 = 1;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (i13 < length3) {
            if (i == i6) {
                CType cTypeFindCType2 = findCType(i13, str);
                CType cType = CType.ONE_DIGIT;
                if (cTypeFindCType2 != cType) {
                    c = c4;
                    CType cType2 = CType.UNCODABLE;
                    if (cTypeFindCType2 != cType2) {
                        z = z2;
                        if (i12 == 101 && cTypeFindCType2 == CType.FNC_1) {
                            i2 = 101;
                        } else {
                            i2 = 99;
                            if (i12 != 99) {
                                if (i12 == 100) {
                                    CType cType3 = CType.FNC_1;
                                    if (cTypeFindCType2 != cType3 && (cTypeFindCType = findCType(i13 + 2, str)) != cType2 && cTypeFindCType != cType) {
                                        if (cTypeFindCType != cType3) {
                                            int i14 = i13 + 4;
                                            while (true) {
                                                CType cTypeFindCType3 = findCType(i14, str);
                                                if (cTypeFindCType3 == CType.TWO_DIGITS) {
                                                    i14 += 2;
                                                } else if (cTypeFindCType3 == CType.ONE_DIGIT) {
                                                }
                                            }
                                        } else if (findCType(i13 + 3, str) == CType.TWO_DIGITS) {
                                            i2 = 99;
                                        }
                                    }
                                    i2 = 100;
                                } else {
                                    if (cTypeFindCType2 == CType.FNC_1) {
                                        cTypeFindCType2 = findCType(i13 + 1, str);
                                    }
                                    if (cTypeFindCType2 != CType.TWO_DIGITS) {
                                        i2 = 100;
                                    }
                                }
                            }
                        }
                    } else if (i13 >= str.length()) {
                        c2 = c7;
                        c7 = c2;
                        z = z2;
                        i2 = 100;
                    } else {
                        char cCharAt2 = str.charAt(i13);
                        if (cCharAt2 >= c6) {
                            if (i12 == 101) {
                                c2 = c7;
                                if (cCharAt2 >= c2) {
                                    if (cCharAt2 >= 241) {
                                    }
                                }
                            }
                            c7 = c2;
                            z = z2;
                            i2 = 100;
                        } else {
                            c2 = c7;
                        }
                        c7 = c2;
                        z = z2;
                        i2 = 101;
                    }
                } else if (i12 == 101) {
                    c = c4;
                    z = z2;
                    i2 = 101;
                } else {
                    c = c4;
                    z = z2;
                    i2 = 100;
                }
            } else {
                c = c4;
                z = z2;
                i2 = i;
            }
            if (i2 == i12) {
                switch (str.charAt(i13)) {
                    case IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState /* 241 */:
                        c6 = ' ';
                        iCharAt = 102;
                        break;
                    case IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState /* 242 */:
                        c6 = ' ';
                        iCharAt = 97;
                        break;
                    case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                        c6 = ' ';
                        iCharAt = c7;
                        break;
                    case IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode /* 244 */:
                        c6 = ' ';
                        if (i12 == 101) {
                            iCharAt = 101;
                            break;
                        } else {
                            iCharAt = 100;
                            break;
                        }
                    default:
                        if (i12 != 100) {
                            if (i12 != 101) {
                                int i15 = i13 + 1;
                                if (i15 == length3) {
                                    throw new IllegalArgumentException("Bad number of characters for digit only encoding.");
                                }
                                iCharAt = Integer.parseInt(str.substring(i13, i13 + 2));
                                i13 = i15;
                                c6 = ' ';
                                break;
                            } else {
                                char cCharAt3 = str.charAt(i13);
                                c6 = ' ';
                                iCharAt = cCharAt3 - ' ';
                                if (iCharAt < 0) {
                                    iCharAt = cCharAt3 + '@';
                                    break;
                                }
                            }
                        } else {
                            c6 = ' ';
                            iCharAt = str.charAt(i13) - ' ';
                            break;
                        }
                        break;
                }
                i13++;
                i2 = i12;
            } else {
                c6 = ' ';
                iCharAt = i12 == 0 ? i2 != 100 ? i2 != 101 ? 105 : 103 : 104 : i2;
            }
            arrayList2.add(Code128Reader.CODE_PATTERNS[iCharAt]);
            i11 += iCharAt * i10;
            if (i13 != 0) {
                i10++;
            }
            i12 = i2;
            c4 = c;
            z2 = z;
            i6 = -1;
        }
        return produceResult(i11, arrayList2);
    }
}
