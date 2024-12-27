package com.samsung.android.graphics.spr.animation.interpolator;

import android.animation.TimeInterpolator;

import java.util.Hashtable;

public class SprTimeInterpolatorFactory {
    private static Hashtable<Integer, SprTimeInterpolator> mTable;

    public static TimeInterpolator get(int animationMode, int duration, int type, int quotient) {
        if (mTable == null) {
            mTable = new Hashtable<>();
        }
        SprTimeInterpolator item = mTable.get(Integer.valueOf(duration - quotient));
        if (item == null) {
            SprTimeInterpolator item2 = new SprTimeInterpolator(duration, type, quotient);
            mTable.put(Integer.valueOf(duration - quotient), item2);
            return item2;
        }
        return item;
    }
}
