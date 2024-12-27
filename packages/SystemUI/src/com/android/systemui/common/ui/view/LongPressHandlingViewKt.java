package com.android.systemui.common.ui.view;

import android.view.MotionEvent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
