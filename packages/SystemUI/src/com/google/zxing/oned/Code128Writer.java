package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum CType {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MinimalEncoder {
        public int[][] memoizedCost;
        public Latch[][] minPath;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        enum Charset {
            A,
            B,
            C,
            NONE
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Removed duplicated region for block: B:19:0x005a A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0059 A[ADDED_TO_REGION, RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static boolean canEncode(java.lang.CharSequence r7, com.google.zxing.oned.Code128Writer.MinimalEncoder.Charset r8, int r9) {
            /*
                java.lang.String r7 = (java.lang.String) r7
                char r0 = r7.charAt(r9)
                int r8 = r8.ordinal()
                r1 = 244(0xf4, float:3.42E-43)
                r2 = 243(0xf3, float:3.4E-43)
                r3 = 242(0xf2, float:3.39E-43)
                r4 = 241(0xf1, float:3.38E-43)
                r5 = 0
                r6 = 1
                if (r8 == 0) goto L48
                if (r8 == r6) goto L37
                r1 = 2
                if (r8 == r1) goto L1c
                goto L59
            L1c:
                if (r0 == r4) goto L5a
                int r9 = r9 + r6
                int r8 = r7.length()
                if (r9 >= r8) goto L59
                r8 = 48
                if (r0 < r8) goto L36
                r1 = 57
                if (r0 > r1) goto L36
                char r7 = r7.charAt(r9)
                if (r7 < r8) goto L36
                if (r7 > r1) goto L36
                return r6
            L36:
                return r5
            L37:
                if (r0 == r4) goto L5a
                if (r0 == r3) goto L5a
                if (r0 == r2) goto L5a
                if (r0 == r1) goto L5a
                java.lang.String r7 = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007fÿ"
                int r7 = r7.indexOf(r0)
                if (r7 < 0) goto L59
                goto L5a
            L48:
                if (r0 == r4) goto L5a
                if (r0 == r3) goto L5a
                if (r0 == r2) goto L5a
                if (r0 == r1) goto L5a
                java.lang.String r7 = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001fÿ"
                int r7 = r7.indexOf(r0)
                if (r7 < 0) goto L59
                goto L5a
            L59:
                return r5
            L5a:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Writer.MinimalEncoder.canEncode(java.lang.CharSequence, com.google.zxing.oned.Code128Writer$MinimalEncoder$Charset, int):boolean");
        }

        public final int encode(CharSequence charSequence, Charset charset, int i) {
            int i2;
            int i3;
            int i4 = this.memoizedCost[charset.ordinal()][i];
            if (i4 > 0) {
                return i4;
            }
            Latch latch = Latch.NONE;
            int i5 = i + 1;
            String str = (String) charSequence;
            int i6 = 0;
            boolean z = i5 >= str.length();
            Charset[] charsetArr = {Charset.A, Charset.B};
            int i7 = Integer.MAX_VALUE;
            while (true) {
                if (i6 > 1) {
                    break;
                }
                if (canEncode(str, charsetArr[i6], i)) {
                    Latch latch2 = Latch.NONE;
                    Charset charset2 = charsetArr[i6];
                    if (charset != charset2) {
                        latch2 = Latch.valueOf(charset2.toString());
                        i3 = 2;
                    } else {
                        i3 = 1;
                    }
                    if (!z) {
                        i3 += encode(str, charsetArr[i6], i5);
                    }
                    if (i3 < i7) {
                        latch = latch2;
                        i7 = i3;
                    }
                    if (charset == charsetArr[(i6 + 1) % 2]) {
                        Latch latch3 = Latch.SHIFT;
                        int encode = z ? 2 : encode(str, charset, i5) + 2;
                        if (encode < i7) {
                            i7 = encode;
                            latch = latch3;
                        }
                    }
                }
                i6++;
            }
            Charset charset3 = Charset.C;
            if (canEncode(str, charset3, i)) {
                Latch latch4 = Latch.NONE;
                if (charset != charset3) {
                    latch4 = Latch.C;
                    i2 = 2;
                } else {
                    i2 = 1;
                }
                int i8 = (str.charAt(i) != 241 ? 2 : 1) + i;
                if (i8 < str.length()) {
                    i2 += encode(str, charset3, i8);
                }
                if (i2 < i7) {
                    latch = latch4;
                    i7 = i2;
                }
            }
            if (i7 != Integer.MAX_VALUE) {
                this.memoizedCost[charset.ordinal()][i] = i7;
                this.minPath[charset.ordinal()][i] = latch;
                return i7;
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
        char charAt = str.charAt(i);
        if (charAt == 241) {
            return CType.FNC_1;
        }
        if (charAt < '0' || charAt > '9') {
            return CType.UNCODABLE;
        }
        int i2 = i + 1;
        if (i2 >= length) {
            return CType.ONE_DIGIT;
        }
        char charAt2 = str.charAt(i2);
        return (charAt2 < '0' || charAt2 > '9') ? CType.ONE_DIGIT : CType.TWO_DIGITS;
    }

    public static boolean[] produceResult(int i, Collection collection) {
        int[][] iArr = Code128Reader.CODE_PATTERNS;
        ArrayList arrayList = (ArrayList) collection;
        arrayList.add(iArr[i % 103]);
        arrayList.add(iArr[106]);
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            for (int i5 : (int[]) obj) {
                i3 += i5;
            }
        }
        boolean[] zArr = new boolean[i3];
        int size2 = arrayList.size();
        int i6 = 0;
        while (i6 < size2) {
            Object obj2 = arrayList.get(i6);
            i6++;
            i2 += OneDimensionalCodeWriter.appendPattern(zArr, i2, (int[]) obj2, true);
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
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0260, code lost:
    
        if (r6 > 244) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x029a, code lost:
    
        if (findCType(r5 + 3, r24) == com.google.zxing.oned.Code128Writer.CType.TWO_DIGITS) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x029c, code lost:
    
        r8 = 99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x02ae, code lost:
    
        if (r7 == com.google.zxing.oned.Code128Writer.CType.ONE_DIGIT) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x02bd, code lost:
    
        if (r14 == com.google.zxing.oned.Code128Writer.CType.TWO_DIGITS) goto L183;
     */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01b1  */
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean[] encode(java.lang.String r24, java.util.Map r25) {
        /*
            Method dump skipped, instructions count: 926
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Writer.encode(java.lang.String, java.util.Map):boolean[]");
    }
}
