package com.android.server.wm.utils;

import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;

import java.util.function.Consumer;

public abstract class RegionUtils {
    public static void forEachRect(Region region, Consumer consumer) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (regionIterator.next(rect)) {
            consumer.accept(rect);
        }
    }
}
