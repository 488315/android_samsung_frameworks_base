package com.android.systemui.temporarydisplay.chipbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ChipbarRootView extends FrameLayout {
    public ChipbarCoordinator$updateView$1 touchHandler;

    public ChipbarRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        ChipbarCoordinator$updateView$1 chipbarCoordinator$updateView$1 = this.touchHandler;
        if (chipbarCoordinator$updateView$1 != null) {
            chipbarCoordinator$updateView$1.this$0.falsingCollector.onTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
