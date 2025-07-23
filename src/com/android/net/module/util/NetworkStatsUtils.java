package com.android.net.module.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes6.dex */
public class NetworkStatsUtils {
    public static final String LIMIT_GLOBAL_ALERT = "globalAlert";
    public static final int SUBSCRIBER_ID_MATCH_RULE_ALL = 1;
    public static final int SUBSCRIBER_ID_MATCH_RULE_EXACT = 0;

    public static long multiplySafeByRational(long j, long j2, long j3) {
        if (j3 == 0) {
            throw new ArithmeticException("Invalid Denominator");
        }
        long j4 = j * j2;
        return (((Math.abs(j) | Math.abs(j2)) >>> 31) == 0 || ((j2 == 0 || j4 / j2 == j) && !(j == Long.MIN_VALUE && j2 == -1))) ? j4 / j3 : (long) ((j2 / j3) * j);
    }

    public static int constrain(int i, int i2, int i3) {
        if (i2 <= i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }
        throw new IllegalArgumentException("low(" + i2 + ") > high(" + i3 + NavigationBarInflaterView.KEY_CODE_END);
    }

    public static long constrain(long j, long j2, long j3) {
        if (j2 <= j3) {
            return j < j2 ? j2 : j > j3 ? j3 : j;
        }
        throw new IllegalArgumentException("low(" + j2 + ") > high(" + j3 + NavigationBarInflaterView.KEY_CODE_END);
    }
}
