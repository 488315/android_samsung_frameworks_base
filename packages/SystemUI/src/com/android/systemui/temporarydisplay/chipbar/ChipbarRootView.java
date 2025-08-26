package com.android.systemui.temporarydisplay.chipbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;

/* loaded from: classes3.dex */
public final class ChipbarRootView extends FrameLayout {
    public ChipbarCoordinator.AnonymousClass1 touchHandler;

    public ChipbarRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        ChipbarCoordinator.AnonymousClass1 anonymousClass1 = this.touchHandler;
        if (anonymousClass1 != null) {
            ChipbarCoordinator.this.falsingCollector.onTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
