package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.vector.PathNode;
import java.util.ArrayList;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class PathParser {
    public float[] nodeData = new float[64];
    public ArrayList nodes;

    public final void parsePathString(String str) {
        ArrayList arrayList = this.nodes;
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.nodes = arrayList;
        } else {
            arrayList.clear();
        }
        pathStringToNodes(str, arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:311:0x016d, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x041d A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void pathStringToNodes(String str, ArrayList arrayList) {
        int i;
        char cCharAt;
        int i2;
        int i3;
        char c;
        int i4;
        char c2;
        char cCharAt2;
        int i5;
        int i6;
        int i7;
        char c3;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        long j;
        int i16;
        boolean z;
        long j2;
        int iFloatToRawIntBits;
        long jFloatToRawIntBits;
        int i17;
        long j3;
        char cCharAt3;
        long j4;
        long jFloatToRawIntBits2;
        int iFloatToRawIntBits2;
        float fIntBitsToFloat;
        int i18;
        char c4 = '\n';
        char c5 = 'z';
        int i19 = 32;
        char c6 = '0';
        int i20 = -1;
        int i21 = 4;
        boolean z2 = true;
        int length = str.length();
        int i22 = 0;
        while (i22 < length && Intrinsics.compare(str.charAt(i22), 32) <= 0) {
            i22++;
        }
        while (length > i22 && Intrinsics.compare(str.charAt(length - 1), 32) <= 0) {
            length--;
        }
        int i23 = 0;
        while (i22 < length) {
            while (true) {
                i = i22 + 1;
                cCharAt = str.charAt(i22);
                i2 = i20;
                int i24 = cCharAt | ' ';
                if ((i24 - 122) * (i24 - 97) > 0 || i24 == 101) {
                    if (i >= length) {
                        cCharAt = 0;
                    } else {
                        i22 = i;
                        i20 = i2;
                    }
                }
            }
            if (cCharAt != 0) {
                boolean z3 = z2;
                if ((cCharAt | ' ') != c5) {
                    int i25 = 0;
                    while (true) {
                        if (i >= length || Intrinsics.compare(str.charAt(i), i19) > 0) {
                            if (i == length) {
                                i3 = i19;
                                j4 = i << i3;
                                iFloatToRawIntBits2 = Float.floatToRawIntBits(Float.NaN);
                            } else {
                                i3 = i19;
                                char cCharAt4 = str.charAt(i);
                                boolean z4 = cCharAt4 == '-' ? z3 ? 1 : 0 : false;
                                if (z4) {
                                    i5 = i + 1;
                                    if (i5 == length) {
                                        j4 = i5 << i3;
                                        iFloatToRawIntBits2 = Float.floatToRawIntBits(Float.NaN);
                                    } else {
                                        cCharAt2 = str.charAt(i5);
                                        if (((char) (cCharAt2 - '0')) >= c4 && cCharAt2 != '.') {
                                            j4 = i5 << i3;
                                            jFloatToRawIntBits2 = Float.floatToRawIntBits(Float.NaN);
                                            jFloatToRawIntBits = j4 | (jFloatToRawIntBits2 & 4294967295L);
                                            c = c6;
                                            i12 = i25;
                                            i4 = i21;
                                            i15 = length;
                                            c2 = cCharAt;
                                            int i26 = (int) (jFloatToRawIntBits >>> i3);
                                            fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
                                            if (Float.isNaN(fIntBitsToFloat)) {
                                                float[] fArr = this.nodeData;
                                                i18 = i12 + 1;
                                                fArr[i12] = fIntBitsToFloat;
                                                if (i18 >= fArr.length) {
                                                    float[] fArr2 = new float[i18 * 2];
                                                    this.nodeData = fArr2;
                                                    System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                                                }
                                                i = i26;
                                                length = i15;
                                            } else {
                                                i = i26;
                                                length = i15;
                                                i18 = i12;
                                            }
                                            while (i < length && str.charAt(i) == ',') {
                                                i++;
                                            }
                                            if (i >= length && !Float.isNaN(fIntBitsToFloat)) {
                                                cCharAt = c2;
                                                i19 = i3;
                                                i21 = i4;
                                                c4 = '\n';
                                                i25 = i18;
                                                c6 = c;
                                            }
                                        }
                                    }
                                } else {
                                    cCharAt2 = cCharAt4;
                                    i5 = i;
                                }
                                int length2 = str.length();
                                c = c6;
                                long j5 = 0;
                                int i27 = i5;
                                while (i27 != length) {
                                    int i28 = cCharAt2 - '0';
                                    if (((char) i28) < c4) {
                                        j5 = (j5 * 10) + i28;
                                        i27++;
                                        cCharAt2 = i27 < length2 ? str.charAt(i27) : (char) 0;
                                    } else {
                                        i6 = i27 - i5;
                                        if (i27 == length && cCharAt2 == '.') {
                                            int i29 = i27 + 1;
                                            i9 = i29;
                                            c3 = 16;
                                            while (true) {
                                                if (length - i9 >= i21) {
                                                    i7 = i5;
                                                    i4 = i21;
                                                    int i30 = i9;
                                                    long jCharAt = str.charAt(i9) | (str.charAt(i9 + 1) << 16) | (str.charAt(i30 + 2) << i3) | (str.charAt(i30 + 3) << c);
                                                    long j6 = jCharAt - 13511005043687472L;
                                                    int i31 = (((jCharAt + 19703549022044230L) | j6) & (-35747867511423104L)) != 0 ? i2 : (int) ((j6 * 281475406208040961L) >>> c);
                                                    if (i31 >= 0) {
                                                        j5 = (j5 * 10000) + i31;
                                                        i9 = i30 + 4;
                                                        i5 = i7;
                                                        i21 = i4;
                                                    } else {
                                                        i9 = i30;
                                                    }
                                                } else {
                                                    i7 = i5;
                                                    i4 = i21;
                                                }
                                            }
                                            if (i9 < length2) {
                                                cCharAt3 = str.charAt(i9);
                                                while (i9 != length) {
                                                    int i32 = cCharAt3 - '0';
                                                    if (((char) i32) < '\n') {
                                                        j5 = (j5 * 10) + i32;
                                                        i9++;
                                                        if (i9 < length2) {
                                                            cCharAt3 = str.charAt(i9);
                                                        }
                                                    } else {
                                                        i10 = i29 - i9;
                                                        i6 -= i10;
                                                        cCharAt2 = cCharAt3;
                                                        i8 = i29;
                                                    }
                                                }
                                                i10 = i29 - i9;
                                                i6 -= i10;
                                                cCharAt2 = cCharAt3;
                                                i8 = i29;
                                            }
                                            cCharAt3 = 0;
                                        } else {
                                            i7 = i5;
                                            i4 = i21;
                                            c3 = 16;
                                            i8 = i27;
                                            i9 = i8;
                                            i10 = 0;
                                        }
                                        if (i6 != 0) {
                                            jFloatToRawIntBits = (i9 << i3) | (Float.floatToRawIntBits(Float.NaN) & 4294967295L);
                                            i12 = i25;
                                            i15 = length;
                                            c2 = cCharAt;
                                            int i262 = (int) (jFloatToRawIntBits >>> i3);
                                            fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
                                            if (Float.isNaN(fIntBitsToFloat)) {
                                            }
                                            while (i < length) {
                                                i++;
                                            }
                                            if (i >= length) {
                                            }
                                        } else {
                                            if ((cCharAt2 | ' ') == 101) {
                                                i13 = i9 + 1;
                                                char cCharAt5 = i13 < length2 ? str.charAt(i13) : (char) 0;
                                                boolean z5 = cCharAt5 == '-' ? z3 ? 1 : 0 : false;
                                                int i33 = i10;
                                                if (z5 || cCharAt5 == '+') {
                                                    i13 = i9 + 2;
                                                }
                                                char cCharAt6 = str.charAt(i13);
                                                i14 = 0;
                                                while (true) {
                                                    if (i13 != length) {
                                                        int i34 = cCharAt6 - '0';
                                                        i12 = i25;
                                                        if (((char) i34) < '\n') {
                                                            if (i14 < 1024) {
                                                                i14 = (i14 * 10) + i34;
                                                            }
                                                            i13++;
                                                            cCharAt6 = i13 < length2 ? str.charAt(i13) : (char) 0;
                                                            i25 = i12;
                                                        }
                                                    } else {
                                                        i12 = i25;
                                                    }
                                                }
                                                if (z5) {
                                                    i14 = -i14;
                                                }
                                                i11 = i33 + i14;
                                            } else {
                                                i11 = i10;
                                                i12 = i25;
                                                i13 = i9;
                                                i14 = 0;
                                            }
                                            int i35 = 19;
                                            if (i6 > 19) {
                                                int i36 = i7;
                                                char cCharAt7 = str.charAt(i36);
                                                while (true) {
                                                    if (i13 == length) {
                                                        i17 = i35;
                                                    } else if (cCharAt7 == c || cCharAt7 == '.') {
                                                        if (cCharAt7 == '0') {
                                                            i6--;
                                                        }
                                                        int i37 = i7 + 1;
                                                        i7 = i37;
                                                        cCharAt7 = i37 < length2 ? str.charAt(i37) : (char) 0;
                                                        i35 = 19;
                                                        c = '0';
                                                    } else {
                                                        i17 = 19;
                                                    }
                                                }
                                                if (i6 > i17) {
                                                    char cCharAt8 = str.charAt(i36);
                                                    int i38 = i36;
                                                    i15 = length;
                                                    c2 = cCharAt;
                                                    int i39 = i14;
                                                    j = 0;
                                                    while (true) {
                                                        if (i38 != i27) {
                                                            int i40 = ULong.$r8$clinit;
                                                            if (Long.compareUnsigned(j, 1000000000000000000L) < 0) {
                                                                j = (j * 10) + (cCharAt8 - '0');
                                                                i38++;
                                                                cCharAt8 = i38 < length2 ? str.charAt(i38) : (char) 0;
                                                            }
                                                        }
                                                    }
                                                    int i41 = ULong.$r8$clinit;
                                                    if (Long.compareUnsigned(j, 1000000000000000000L) >= 0) {
                                                        z = z3 ? 1 : 0;
                                                        i16 = (i27 - i38) + i39;
                                                        c = '0';
                                                    } else {
                                                        char cCharAt9 = str.charAt(i8);
                                                        int i42 = i8;
                                                        for (j3 = 1000000000000000000L; i42 != i9 && Long.compareUnsigned(j, j3) < 0; j3 = 1000000000000000000L) {
                                                            j = (j * 10) + (cCharAt9 - '0');
                                                            i42++;
                                                            cCharAt9 = i42 < length2 ? str.charAt(i42) : (char) 0;
                                                        }
                                                        c = '0';
                                                        int i43 = (i8 - i42) + i39;
                                                        z = z3 ? 1 : 0;
                                                        i16 = i43;
                                                    }
                                                    if (-10 > i16 || i16 >= 11 || z) {
                                                        if (j != 0) {
                                                            j2 = i13 << i3;
                                                            iFloatToRawIntBits = Float.floatToRawIntBits(z4 ? -0.0f : 0.0f);
                                                        } else if (-126 > i16 || i16 >= 128) {
                                                            j2 = i13 << i3;
                                                            iFloatToRawIntBits = Float.floatToRawIntBits(Float.parseFloat(str.substring(i, i13)));
                                                        } else {
                                                            long j7 = FastFloatParserKt.Mantissa64[i16 + 325];
                                                            int i44 = ULong.$r8$clinit;
                                                            int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(j);
                                                            long j8 = j << iNumberOfLeadingZeros;
                                                            long j9 = j8 & 4294967295L;
                                                            long j10 = j8 >>> i3;
                                                            long j11 = j7 & 4294967295L;
                                                            long j12 = j7 >>> i3;
                                                            long j13 = j10 * j12;
                                                            long j14 = j12 * j9;
                                                            long j15 = j13 + ((((j10 * j11) + ((j9 * j11) >>> i3)) + (j14 & 4294967295L)) >>> i3) + (j14 >>> i3);
                                                            int i45 = (int) (j15 >>> 63);
                                                            long j16 = j15 >>> (i45 + 9);
                                                            int i46 = iNumberOfLeadingZeros + (i45 ^ 1);
                                                            long j17 = j15 & 511;
                                                            if (j17 == 511 || (j17 == 0 && (3 & j16) == 1)) {
                                                                j2 = i13 << i3;
                                                                iFloatToRawIntBits = Float.floatToRawIntBits(Float.parseFloat(str.substring(i, i13)));
                                                            } else {
                                                                long j18 = (j16 + 1) >>> (z3 ? 1L : 0L);
                                                                if (j18 >= 9007199254740992L) {
                                                                    i46--;
                                                                    j18 = 4503599627370496L;
                                                                }
                                                                long j19 = j18 & (-4503599627370497L);
                                                                long j20 = ((((i16 * 217706) >> c3) + 1024) + 63) - i46;
                                                                if (j20 < 1 || j20 > 2046) {
                                                                    j2 = i13 << i3;
                                                                    iFloatToRawIntBits = Float.floatToRawIntBits(Float.parseFloat(str.substring(i, i13)));
                                                                } else {
                                                                    j2 = i13 << i3;
                                                                    iFloatToRawIntBits = Float.floatToRawIntBits((float) Double.longBitsToDouble((j20 << 52) | j19 | (z4 ? Long.MIN_VALUE : 0L)));
                                                                }
                                                            }
                                                        }
                                                        jFloatToRawIntBits = j2 | (iFloatToRawIntBits & 4294967295L);
                                                        int i2622 = (int) (jFloatToRawIntBits >>> i3);
                                                        fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
                                                        if (Float.isNaN(fIntBitsToFloat)) {
                                                        }
                                                        while (i < length) {
                                                        }
                                                        if (i >= length) {
                                                        }
                                                    } else {
                                                        int i47 = ULong.$r8$clinit;
                                                        if (Long.compareUnsigned(j, 16777216L) <= 0) {
                                                            float f = j;
                                                            float[] fArr3 = FastFloatParserKt.PowersOfTen;
                                                            float f2 = i16 < 0 ? f / fArr3[-i16] : f * fArr3[i16];
                                                            if (z4) {
                                                                f2 = -f2;
                                                            }
                                                            j2 = i13 << i3;
                                                            iFloatToRawIntBits = Float.floatToRawIntBits(f2);
                                                        }
                                                        jFloatToRawIntBits = j2 | (iFloatToRawIntBits & 4294967295L);
                                                        int i26222 = (int) (jFloatToRawIntBits >>> i3);
                                                        fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
                                                        if (Float.isNaN(fIntBitsToFloat)) {
                                                        }
                                                        while (i < length) {
                                                        }
                                                        if (i >= length) {
                                                        }
                                                    }
                                                } else {
                                                    c = '0';
                                                    i15 = length;
                                                    c2 = cCharAt;
                                                    j = j5;
                                                    i16 = i11;
                                                    z = false;
                                                    if (-10 > i16) {
                                                        if (j != 0) {
                                                        }
                                                        jFloatToRawIntBits = j2 | (iFloatToRawIntBits & 4294967295L);
                                                        int i262222 = (int) (jFloatToRawIntBits >>> i3);
                                                        fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
                                                        if (Float.isNaN(fIntBitsToFloat)) {
                                                        }
                                                        while (i < length) {
                                                        }
                                                        if (i >= length) {
                                                        }
                                                    }
                                                }
                                            } else {
                                                i15 = length;
                                                c2 = cCharAt;
                                                j = j5;
                                                i16 = i11;
                                                z = false;
                                                if (-10 > i16) {
                                                }
                                            }
                                        }
                                    }
                                }
                                i6 = i27 - i5;
                                if (i27 == length) {
                                    i7 = i5;
                                    i4 = i21;
                                    c3 = 16;
                                    i8 = i27;
                                    i9 = i8;
                                    i10 = 0;
                                    if (i6 != 0) {
                                    }
                                }
                            }
                            jFloatToRawIntBits2 = iFloatToRawIntBits2;
                            jFloatToRawIntBits = j4 | (jFloatToRawIntBits2 & 4294967295L);
                            c = c6;
                            i12 = i25;
                            i4 = i21;
                            i15 = length;
                            c2 = cCharAt;
                            int i2622222 = (int) (jFloatToRawIntBits >>> i3);
                            fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
                            if (Float.isNaN(fIntBitsToFloat)) {
                            }
                            while (i < length) {
                            }
                            if (i >= length) {
                            }
                        } else {
                            i++;
                        }
                    }
                    i23 = i18;
                } else {
                    i3 = i19;
                    c = c6;
                    i4 = i21;
                    c2 = cCharAt;
                }
                i22 = i;
                float[] fArr4 = this.nodeData;
                switch (c2) {
                    case 'A':
                        int i48 = i23 - 7;
                        for (int i49 = 0; i49 <= i48; i49 += 7) {
                            arrayList.add(new PathNode.ArcTo(fArr4[i49], fArr4[i49 + 1], fArr4[i49 + 2], Float.compare(fArr4[i49 + 3], 0.0f) != 0 ? z3 ? 1 : 0 : false, Float.compare(fArr4[i49 + 4], 0.0f) != 0 ? z3 ? 1 : 0 : false, fArr4[i49 + 5], fArr4[i49 + 6]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'C':
                        int i50 = i23 - 6;
                        for (int i51 = 0; i51 <= i50; i51 += 6) {
                            arrayList.add(new PathNode.CurveTo(fArr4[i51], fArr4[i51 + 1], fArr4[i51 + 2], fArr4[i51 + 3], fArr4[i51 + 4], fArr4[i51 + 5]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'H':
                        int i52 = i23 - 1;
                        for (int i53 = 0; i53 <= i52; i53++) {
                            arrayList.add(new PathNode.HorizontalTo(fArr4[i53]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'L':
                        int i54 = i23 - 2;
                        for (int i55 = 0; i55 <= i54; i55 += 2) {
                            arrayList.add(new PathNode.LineTo(fArr4[i55], fArr4[i55 + 1]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'M':
                        int i56 = i23 - 2;
                        if (i56 >= 0) {
                            arrayList.add(new PathNode.MoveTo(fArr4[0], fArr4[z3 ? 1 : 0]));
                            for (int i57 = 2; i57 <= i56; i57 += 2) {
                                arrayList.add(new PathNode.LineTo(fArr4[i57], fArr4[i57 + 1]));
                            }
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'Q':
                        int i58 = i23 - 4;
                        for (int i59 = 0; i59 <= i58; i59 += 4) {
                            arrayList.add(new PathNode.QuadTo(fArr4[i59], fArr4[i59 + 1], fArr4[i59 + 2], fArr4[i59 + 3]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'S':
                        int i60 = i23 - 4;
                        for (int i61 = 0; i61 <= i60; i61 += 4) {
                            arrayList.add(new PathNode.ReflectiveCurveTo(fArr4[i61], fArr4[i61 + 1], fArr4[i61 + 2], fArr4[i61 + 3]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'T':
                        int i62 = i23 - 2;
                        for (int i63 = 0; i63 <= i62; i63 += 2) {
                            arrayList.add(new PathNode.ReflectiveQuadTo(fArr4[i63], fArr4[i63 + 1]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'V':
                        int i64 = i23 - 1;
                        for (int i65 = 0; i65 <= i64; i65++) {
                            arrayList.add(new PathNode.VerticalTo(fArr4[i65]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'Z':
                    case 'z':
                        arrayList.add(PathNode.Close.INSTANCE);
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'a':
                        int i66 = i23 - 7;
                        for (int i67 = 0; i67 <= i66; i67 += 7) {
                            arrayList.add(new PathNode.RelativeArcTo(fArr4[i67], fArr4[i67 + 1], fArr4[i67 + 2], Float.compare(fArr4[i67 + 3], 0.0f) != 0 ? z3 ? 1 : 0 : false, Float.compare(fArr4[i67 + 4], 0.0f) != 0 ? z3 ? 1 : 0 : false, fArr4[i67 + 5], fArr4[i67 + 6]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'c':
                        int i68 = i23 - 6;
                        for (int i69 = 0; i69 <= i68; i69 += 6) {
                            arrayList.add(new PathNode.RelativeCurveTo(fArr4[i69], fArr4[i69 + 1], fArr4[i69 + 2], fArr4[i69 + 3], fArr4[i69 + 4], fArr4[i69 + 5]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'h':
                        int i70 = i23 - 1;
                        for (int i71 = 0; i71 <= i70; i71++) {
                            arrayList.add(new PathNode.RelativeHorizontalTo(fArr4[i71]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'l':
                        int i72 = i23 - 2;
                        for (int i73 = 0; i73 <= i72; i73 += 2) {
                            arrayList.add(new PathNode.RelativeLineTo(fArr4[i73], fArr4[i73 + 1]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'm':
                        int i74 = i23 - 2;
                        if (i74 >= 0) {
                            arrayList.add(new PathNode.RelativeMoveTo(fArr4[0], fArr4[z3 ? 1 : 0]));
                            for (int i75 = 2; i75 <= i74; i75 += 2) {
                                arrayList.add(new PathNode.RelativeLineTo(fArr4[i75], fArr4[i75 + 1]));
                            }
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'q':
                        int i76 = i23 - 4;
                        for (int i77 = 0; i77 <= i76; i77 += 4) {
                            arrayList.add(new PathNode.RelativeQuadTo(fArr4[i77], fArr4[i77 + 1], fArr4[i77 + 2], fArr4[i77 + 3]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 's':
                        int i78 = i23 - 4;
                        for (int i79 = 0; i79 <= i78; i79 += 4) {
                            arrayList.add(new PathNode.RelativeReflectiveCurveTo(fArr4[i79], fArr4[i79 + 1], fArr4[i79 + 2], fArr4[i79 + 3]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 't':
                        int i80 = i23 - 2;
                        for (int i81 = 0; i81 <= i80; i81 += 2) {
                            arrayList.add(new PathNode.RelativeReflectiveQuadTo(fArr4[i81], fArr4[i81 + 1]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    case 'v':
                        int i82 = i23 - 1;
                        for (int i83 = 0; i83 <= i82; i83++) {
                            arrayList.add(new PathNode.RelativeVerticalTo(fArr4[i83]));
                        }
                        i20 = i2;
                        z2 = z3 ? 1 : 0;
                        i19 = i3;
                        c6 = c;
                        i21 = i4;
                        c4 = '\n';
                        c5 = 'z';
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown command for: " + c2);
                }
            } else {
                i22 = i;
                i20 = i2;
            }
        }
    }
}
