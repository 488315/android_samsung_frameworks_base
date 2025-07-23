package com.android.net.module.util;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes6.dex */
public class BitUtils {

    @FunctionalInterface
    public interface NameOf {
        String nameOf(int i);
    }

    public static int[] unpackBits(long j) {
        int[] iArr = new int[Long.bitCount(j)];
        int i = 0;
        int i2 = 0;
        while (j != 0) {
            if ((j & 1) == 1) {
                iArr[i] = i2;
                i++;
            }
            j >>>= 1;
            i2++;
        }
        return iArr;
    }

    public static long packBitList(int... iArr) {
        return packBits(iArr);
    }

    public static long packBits(int[] iArr) {
        long j = 0;
        for (int i : iArr) {
            j |= 1 << i;
        }
        return j;
    }

    public static void appendStringRepresentationOfBitMaskToStringBuilder(StringBuilder sb, long j, NameOf nameOf, String str) {
        boolean z = false;
        int i = 0;
        while (j != 0) {
            if ((1 & j) != 0) {
                if (z) {
                    sb.append(str);
                } else {
                    z = true;
                }
                sb.append(nameOf.nameOf(i));
            }
            j >>>= 1;
            i++;
        }
    }

    public static String describeDifferences(long j, long j2, NameOf nameOf) {
        long j3 = j ^ j2;
        if (0 == j3) {
            return null;
        }
        long j4 = j & j3;
        long j5 = j2 & j3;
        StringBuilder sb = new StringBuilder();
        if (0 != j4) {
            sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            appendStringRepresentationOfBitMaskToStringBuilder(sb, j4, nameOf, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        }
        if (0 != j5) {
            sb.append("+");
            appendStringRepresentationOfBitMaskToStringBuilder(sb, j5, nameOf, "+");
        }
        return sb.toString();
    }
}
