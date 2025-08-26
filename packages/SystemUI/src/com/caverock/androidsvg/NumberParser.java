package com.caverock.androidsvg;

/* loaded from: classes3.dex */
public class NumberParser {
    public int pos;
    public static final float[] positivePowersOf10 = {1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f, 1.0E11f, 1.0E12f, 1.0E13f, 1.0E14f, 1.0E15f, 1.0E16f, 1.0E17f, 1.0E18f, 1.0E19f, 1.0E20f, 1.0E21f, 1.0E22f, 1.0E23f, 1.0E24f, 1.0E25f, 1.0E26f, 1.0E27f, 1.0E28f, 1.0E29f, 1.0E30f, 1.0E31f, 1.0E32f, 1.0E33f, 1.0E34f, 1.0E35f, 1.0E36f, 1.0E37f, 1.0E38f};
    public static final float[] negativePowersOf10 = {1.0f, 0.1f, 0.01f, 0.001f, 1.0E-4f, 1.0E-5f, 1.0E-6f, 1.0E-7f, 1.0E-8f, 1.0E-9f, 1.0E-10f, 1.0E-11f, 1.0E-12f, 1.0E-13f, 1.0E-14f, 1.0E-15f, 1.0E-16f, 1.0E-17f, 1.0E-18f, 1.0E-19f, 1.0E-20f, 1.0E-21f, 1.0E-22f, 1.0E-23f, 1.0E-24f, 1.0E-25f, 1.0E-26f, 1.0E-27f, 1.0E-28f, 1.0E-29f, 1.0E-30f, 1.0E-31f, 1.0E-32f, 1.0E-33f, 1.0E-34f, 1.0E-35f, 1.0E-36f, 1.0E-37f, 1.0E-38f};

    /* JADX WARN: Code restructure failed: missing block: B:83:0x0103, code lost:
    
        if (r23.pos != r5) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0105, code lost:
    
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0106, code lost:
    
        if (r4 == false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0108, code lost:
    
        r13 = r13 - r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x010a, code lost:
    
        r13 = r13 + r6;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x008a A[EDGE_INSN: B:107:0x008a->B:42:0x008a BREAK  A[LOOP:0: B:13:0x0034->B:41:0x0083], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b8 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float parseNumber(int i, int i2, String str) {
        boolean z;
        int i3;
        int i4;
        boolean z2;
        int i5;
        int i6;
        int i7;
        float f;
        char cCharAt;
        int i8;
        boolean z3;
        boolean z4;
        char cCharAt2;
        this.pos = i;
        if (i >= i2) {
            return Float.NaN;
        }
        char cCharAt3 = str.charAt(i);
        if (cCharAt3 == '+') {
            z = false;
        } else {
            if (cCharAt3 != '-') {
                z = false;
                int i9 = this.pos;
                long j = 0;
                i3 = 0;
                i4 = 0;
                int i10 = 0;
                z2 = false;
                int i11 = 0;
                while (true) {
                    i5 = this.pos;
                    if (i5 >= i2) {
                        break;
                    }
                    char cCharAt4 = str.charAt(i5);
                    if (cCharAt4 == '0') {
                        if (i3 == 0) {
                            i10++;
                        } else {
                            i4++;
                        }
                    } else if (cCharAt4 >= '1' && cCharAt4 <= '9') {
                        int i12 = i3 + i4;
                        while (i4 > 0) {
                            if (j > 922337203685477580L) {
                                return Float.NaN;
                            }
                            j *= 10;
                            i4--;
                        }
                        if (j > 922337203685477580L) {
                            return Float.NaN;
                        }
                        j = (j * 10) + (cCharAt4 - '0');
                        i3 = i12 + 1;
                        if (j < 0) {
                            return Float.NaN;
                        }
                    } else {
                        if (cCharAt4 != '.' || z2) {
                            break;
                        }
                        i11 = this.pos - i9;
                        z2 = true;
                    }
                    this.pos++;
                }
                if (!z2 && this.pos == i11 + 1) {
                    return Float.NaN;
                }
                if (i3 == 0) {
                    if (i10 == 0) {
                        return Float.NaN;
                    }
                    i3 = 1;
                }
                if (z2) {
                    i4 = (i11 - i10) - i3;
                }
                i6 = this.pos;
                if (i6 < i2 && ((cCharAt = str.charAt(i6)) == 'E' || cCharAt == 'e')) {
                    i8 = this.pos + 1;
                    this.pos = i8;
                    if (i8 != i2) {
                        return Float.NaN;
                    }
                    char cCharAt5 = str.charAt(i8);
                    if (cCharAt5 == '+') {
                        z3 = false;
                    } else if (cCharAt5 != '-') {
                        switch (cCharAt5) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                z3 = false;
                                z4 = false;
                                break;
                            default:
                                this.pos--;
                                z4 = true;
                                z3 = false;
                                break;
                        }
                        if (!z4) {
                            int i13 = this.pos;
                            int i14 = 0;
                            while (true) {
                                int i15 = this.pos;
                                if (i15 < i2 && (cCharAt2 = str.charAt(i15)) >= '0' && cCharAt2 <= '9') {
                                    if (i14 > 922337203685477580L) {
                                        return Float.NaN;
                                    }
                                    i14 = (i14 * 10) + (cCharAt2 - '0');
                                    this.pos++;
                                }
                            }
                        }
                    } else {
                        z3 = true;
                    }
                    this.pos++;
                    z4 = false;
                    if (!z4) {
                    }
                }
                i7 = i3 + i4;
                if (i7 <= 39 || i7 < -44) {
                    return Float.NaN;
                }
                float f2 = j;
                if (j != 0) {
                    if (i4 > 0) {
                        f = positivePowersOf10[i4];
                    } else if (i4 < 0) {
                        if (i4 < -38) {
                            f2 = (float) (f2 * 1.0E-20d);
                            i4 += 20;
                        }
                        f = negativePowersOf10[-i4];
                    }
                    f2 *= f;
                }
                return z ? -f2 : f2;
            }
            z = true;
        }
        this.pos++;
        int i92 = this.pos;
        long j2 = 0;
        i3 = 0;
        i4 = 0;
        int i102 = 0;
        z2 = false;
        int i112 = 0;
        while (true) {
            i5 = this.pos;
            if (i5 >= i2) {
            }
            this.pos++;
        }
        if (!z2) {
        }
        if (i3 == 0) {
        }
        if (z2) {
        }
        i6 = this.pos;
        if (i6 < i2) {
            i8 = this.pos + 1;
            this.pos = i8;
            if (i8 != i2) {
            }
        }
        i7 = i3 + i4;
        if (i7 <= 39) {
        }
        return Float.NaN;
    }
}
