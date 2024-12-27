package com.android.internal.widget;

import java.util.Random;

/* compiled from: LockPatternUtils.java */
/* loaded from: classes5.dex */
class RandomString {
    RandomString() {}

    public static String randomstring(int lo, int hi) {
        int n = rand(lo, hi);
        byte[] b = new byte[n];
        for (int i = 0; i < n; i++) {
            if (rand(0, 10) % 2 == 0) {
                b[i] = (byte) rand(48, 57);
            } else {
                b[i] = (byte) rand(97, 122);
            }
        }
        return new String(b);
    }

    private static int rand(int lo, int hi) {
        Random rn = new Random();
        int n = (hi - lo) + 1;
        int i = rn.nextInt(n);
        if (i < 0) {
            i = -i;
        }
        return lo + i;
    }

    public static String randomstring() {
        return randomstring(5, 10);
    }
}
