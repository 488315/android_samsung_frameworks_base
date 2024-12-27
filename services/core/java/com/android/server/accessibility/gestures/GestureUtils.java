package com.android.server.accessibility.gestures;

import android.util.MathUtils;
import android.view.MotionEvent;

public abstract class GestureUtils {
    public static double distance(MotionEvent motionEvent, MotionEvent motionEvent2) {
        return MathUtils.dist(
                motionEvent.getX(), motionEvent.getY(), motionEvent2.getX(), motionEvent2.getY());
    }

    public static int getActionIndex(MotionEvent motionEvent) {
        return (motionEvent.getAction() & 65280) >> 8;
    }
}
