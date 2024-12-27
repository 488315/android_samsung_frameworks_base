package com.android.server.accessibility.gestures;

import android.util.MathUtils;
import android.view.MotionEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class GestureUtils {
    public static double distance(MotionEvent motionEvent, MotionEvent motionEvent2) {
        return MathUtils.dist(
                motionEvent.getX(), motionEvent.getY(), motionEvent2.getX(), motionEvent2.getY());
    }

    public static int getActionIndex(MotionEvent motionEvent) {
        return (motionEvent.getAction() & 65280) >> 8;
    }
}
