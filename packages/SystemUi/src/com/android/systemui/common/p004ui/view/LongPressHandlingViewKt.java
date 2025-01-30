package com.android.systemui.common.p004ui.view;

import android.view.MotionEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class LongPressHandlingViewKt {
    public static final float distanceMoved(MotionEvent motionEvent) {
        if (motionEvent.getHistorySize() <= 0) {
            return 0.0f;
        }
        double d = 2;
        return (float) Math.sqrt(((float) Math.pow(motionEvent.getX() - motionEvent.getHistoricalX(0), d)) + ((float) Math.pow(motionEvent.getY() - motionEvent.getHistoricalY(0), d)));
    }
}
