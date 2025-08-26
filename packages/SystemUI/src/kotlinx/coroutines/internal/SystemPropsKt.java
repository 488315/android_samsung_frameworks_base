package kotlinx.coroutines.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;

/* loaded from: classes4.dex */
public abstract class SystemPropsKt {
    /* JADX WARN: Removed duplicated region for block: B:25:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final long systemProp(String str, long j, long j2, long j3) {
        String property;
        Long lValueOf;
        int i;
        int i2;
        int i3;
        int i4 = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            property = null;
        }
        if (property == null) {
            return j;
        }
        CharsKt__CharJVMKt.checkRadix(10);
        int length = property.length();
        if (length != 0) {
            int i5 = 0;
            char cCharAt = property.charAt(0);
            long j4 = -9223372036854775807L;
            if (Intrinsics.compare(cCharAt, 48) < 0) {
                if (length != 1) {
                    if (cCharAt == '+') {
                        i = 0;
                        i5 = 1;
                        boolean z = true;
                        long j5 = 0;
                        long j6 = -256204778801521550L;
                        while (i5 < length) {
                        }
                        if (i == 0) {
                        }
                        lValueOf = Long.valueOf(j5);
                    } else if (cCharAt == '-') {
                        j4 = Long.MIN_VALUE;
                        i5 = 1;
                        i = i5;
                        boolean z2 = true;
                        long j52 = 0;
                        long j62 = -256204778801521550L;
                        while (i5 < length) {
                        }
                        if (i == 0) {
                        }
                        lValueOf = Long.valueOf(j52);
                    }
                }
                lValueOf = null;
                break;
            } else {
                i = i5;
                boolean z22 = true;
                long j522 = 0;
                long j622 = -256204778801521550L;
                while (i5 < length) {
                    boolean z3 = z22;
                    int iDigit = Character.digit((int) property.charAt(i5), 10);
                    if (iDigit >= 0) {
                        if (j522 >= j622) {
                            i2 = length;
                            i3 = i5;
                        } else if (j622 == -256204778801521550L) {
                            i2 = length;
                            i3 = i5;
                            j622 = j4 / 10;
                            if (j522 < j622) {
                            }
                        }
                        long j7 = j522 * 10;
                        long j8 = iDigit;
                        if (j7 >= j4 + j8) {
                            j522 = j7 - j8;
                            i5 = i3 + 1;
                            z22 = z3;
                            length = i2;
                        }
                    }
                    lValueOf = null;
                    break;
                }
                if (i == 0) {
                    j522 = -j522;
                }
                lValueOf = Long.valueOf(j522);
            }
        } else {
            lValueOf = null;
            break;
        }
        if (lValueOf == null) {
            throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + property + "'").toString());
        }
        long jLongValue = lValueOf.longValue();
        if (j2 <= jLongValue && jLongValue <= j3) {
            return jLongValue;
        }
        throw new IllegalStateException(("System property '" + str + "' should be in range " + j2 + ".." + j3 + ", but is '" + jLongValue + "'").toString());
    }

    public static int systemProp$default(int i, int i2, String str) {
        return (int) systemProp(str, i, 1, (i2 & 8) != 0 ? Integer.MAX_VALUE : 2097150);
    }
}
