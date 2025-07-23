package com.samsung.android.graphics.spr.animation.interpolator;

import android.animation.TimeInterpolator;
import java.util.Hashtable;

/* loaded from: classes6.dex */
public class SprTimeInterpolatorFactory {
    private static Hashtable<Integer, SprTimeInterpolator> mTable;

    public static TimeInterpolator get(int i, int i2, int i3, int i4) {
        if (mTable == null) {
            mTable = new Hashtable<>();
        }
        int i5 = i2 - i4;
        SprTimeInterpolator sprTimeInterpolator = mTable.get(Integer.valueOf(i5));
        if (sprTimeInterpolator != null) {
            return sprTimeInterpolator;
        }
        SprTimeInterpolator sprTimeInterpolator2 = new SprTimeInterpolator(i2, i3, i4);
        mTable.put(Integer.valueOf(i5), sprTimeInterpolator2);
        return sprTimeInterpolator2;
    }
}
