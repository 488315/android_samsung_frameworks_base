package com.android.systemui.common.ui.view;

import android.view.MotionEvent;

public abstract class LongPressHandlingViewKt {
    public static final float distanceMoved(MotionEvent motionEvent) {
        if (motionEvent.getHistorySize() <= 0) {
            return 0.0f;
        }
        double d = 2;
        return (float) Math.sqrt(((float) Math.pow(motionEvent.getX() - motionEvent.getHistoricalX(0), d)) + ((float) Math.pow(motionEvent.getY() - motionEvent.getHistoricalY(0), d)));
    }
}
