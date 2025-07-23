package com.android.internal.widget;

import java.util.Random;

/* compiled from: LockPatternUtils.java */
/* loaded from: classes6.dex */
class RandomString {
    RandomString() {
    }

    public static String randomstring(int i, int i2) {
        int rand = rand(i, i2);
        byte[] bArr = new byte[rand];
        for (int i3 = 0; i3 < rand; i3++) {
            if (rand(0, 10) % 2 == 0) {
                bArr[i3] = (byte) rand(48, 57);
            } else {
                bArr[i3] = (byte) rand(97, 122);
            }
        }
        return new String(bArr);
    }

    private static int rand(int i, int i2) {
        int nextInt = new Random().nextInt((i2 - i) + 1);
        if (nextInt < 0) {
            nextInt = -nextInt;
        }
        return i + nextInt;
    }

    public static String randomstring() {
        return randomstring(5, 10);
    }
}
