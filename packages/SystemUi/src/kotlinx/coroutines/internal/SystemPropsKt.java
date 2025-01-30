package kotlinx.coroutines.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SystemPropsKt {
    /* JADX WARN: Removed duplicated region for block: B:19:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final long systemProp(String str, long j, long j2, long j3) {
        String str2;
        Long l;
        long j4;
        boolean z;
        int i;
        int i2;
        int i3 = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        if (str2 == null) {
            return j;
        }
        CharsKt__CharJVMKt.checkRadix(10);
        int length = str2.length();
        if (length != 0) {
            char charAt = str2.charAt(0);
            long j5 = -9223372036854775807L;
            if (Intrinsics.compare(charAt, 48) >= 0) {
                i = 0;
            } else if (length != 1) {
                if (charAt == '-') {
                    j5 = Long.MIN_VALUE;
                    i = 1;
                } else if (charAt == '+') {
                    i2 = 0;
                    i = 1;
                    long j6 = 0;
                    long j7 = -256204778801521550L;
                    while (i < length) {
                        int digit = Character.digit((int) str2.charAt(i), 10);
                        if (digit >= 0) {
                            if (j6 < j7) {
                                if (j7 == -256204778801521550L) {
                                    j7 = j5 / 10;
                                    if (j6 < j7) {
                                    }
                                }
                            }
                            long j8 = j6 * 10;
                            long j9 = digit;
                            if (j8 >= j5 + j9) {
                                j6 = j8 - j9;
                                i++;
                            }
                        }
                    }
                    l = i2 == 0 ? Long.valueOf(j6) : Long.valueOf(-j6);
                    if (l == null) {
                        throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + str2 + "'").toString());
                    }
                    long longValue = l.longValue();
                    if (j2 <= longValue) {
                        j4 = j3;
                        if (longValue <= j4) {
                            z = true;
                            if (!z) {
                                return longValue;
                            }
                            throw new IllegalStateException(("System property '" + str + "' should be in range " + j2 + ".." + j4 + ", but is '" + longValue + "'").toString());
                        }
                    } else {
                        j4 = j3;
                    }
                    z = false;
                    if (!z) {
                    }
                }
            }
            i2 = i;
            long j62 = 0;
            long j72 = -256204778801521550L;
            while (i < length) {
            }
            if (i2 == 0) {
            }
            if (l == null) {
            }
        }
        l = null;
        if (l == null) {
        }
    }

    public static int systemProp$default(String str, int i, int i2, int i3, int i4) {
        if ((i4 & 4) != 0) {
            i2 = 1;
        }
        if ((i4 & 8) != 0) {
            i3 = Integer.MAX_VALUE;
        }
        return (int) systemProp(str, i, i2, i3);
    }
}
