package com.android.server.wm.utils;

import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RegionUtils {
    public static void forEachRect(Region region, Consumer consumer) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (regionIterator.next(rect)) {
            consumer.accept(rect);
        }
    }
}
