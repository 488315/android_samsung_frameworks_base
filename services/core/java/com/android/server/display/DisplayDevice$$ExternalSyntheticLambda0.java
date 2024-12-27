package com.android.server.display;

import android.graphics.Point;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayDevice$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Point point = (Point) obj;
        Point point2 = (Point) obj2;
        return (point.x * point.y) - (point2.x * point2.y);
    }
}
