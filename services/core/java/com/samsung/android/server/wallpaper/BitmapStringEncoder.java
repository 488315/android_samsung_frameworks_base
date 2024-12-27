package com.samsung.android.server.wallpaper;

import android.graphics.Bitmap;

import com.android.internal.util.FrameworkStatsLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class BitmapStringEncoder {
    public static char[] sDigits;

    public static String encodeToString(int[] iArr, Bitmap bitmap) {
        int length = iArr.length;
        int i = length + 4;
        int[] iArr2 = new int[i];
        int[] iArr3 = new int[60];
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, 30, 200, true);
        int width2 = createScaledBitmap.getWidth() / 4;
        int height2 = createScaledBitmap.getHeight() / 19;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= 20) {
                break;
            }
            int i4 = 0;
            for (int i5 = 3; i4 < i5; i5 = 3) {
                iArr3[i3] = createScaledBitmap.getPixel((width2 * i4) + width2, height2 * i2);
                i4++;
                i3++;
            }
            i2++;
        }
        createScaledBitmap.recycle();
        iArr2[0] = 3;
        iArr2[1] = 20;
        iArr2[2] = width;
        iArr2[3] = height;
        for (int i6 = 0; i6 < length; i6++) {
            iArr2[i6 + 4] = iArr[i6];
        }
        int i7 = length + 35;
        int[] iArr4 = new int[i7];
        for (int i8 = 0; i8 < 60; i8 += 2) {
            int i9 = iArr3[i8];
            int i10 = iArr3[i8 + 1];
            iArr4[i8 / 2] =
                    ((i9 & FrameworkStatsLog.INTEGRITY_RULES_PUSHED) << 13)
                            | ((i9 & 16252928) << 7)
                            | ((i9 & 63488) << 10)
                            | ((16252928 & i10) >> 8)
                            | ((i10 & 63488) >> 5)
                            | ((i10 & FrameworkStatsLog.INTEGRITY_RULES_PUSHED) >> 2);
        }
        iArr4[length + 34] = i;
        if (i > 0) {
            int i11 = length + 33;
            for (int i12 = 0; i12 < i; i12++) {
                iArr4[i11 - i12] = iArr2[i12];
            }
        }
        if (sDigits == null) {
            sDigits = new char[79];
            Random random = new Random(17171771L);
            ArrayList arrayList = new ArrayList();
            for (int i13 = 0; i13 < 79; i13++) {
                char c = (char) (i13 + 48);
                sDigits[i13] = c;
                arrayList.add(Character.valueOf(c));
            }
            Collections.shuffle(arrayList, random);
            for (int i14 = 0; i14 < 79; i14++) {
                sDigits[i14] = ((Character) arrayList.get(i14)).charValue();
            }
        }
        char[] cArr = sDigits;
        char[] cArr2 = new char[i7 * 5];
        int pow = (int) Math.pow(79.0d, 4.0d);
        Random random2 = new Random(19861212L);
        int i15 = 0;
        int i16 = 0;
        for (int i17 = 0; i17 < i7; i17++) {
            int i18 = iArr4[i17];
            int i19 = pow;
            while (i19 > 0) {
                i16 = (random2.nextInt(79) + ((i18 / i19) + i16)) % 79;
                cArr2[i15] = cArr[i16];
                i18 %= i19;
                i19 /= 79;
                i15++;
            }
        }
        return new String(cArr2);
    }
}
