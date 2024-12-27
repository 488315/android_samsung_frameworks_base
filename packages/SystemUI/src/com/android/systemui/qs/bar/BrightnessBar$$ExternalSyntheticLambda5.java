package com.android.systemui.qs.bar;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class BrightnessBar$$ExternalSyntheticLambda5 implements View.OnHoverListener {
    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 9) {
            view.setHovered(true);
            view.sendAccessibilityEvent(128);
            return true;
        }
        if (action != 10) {
            return false;
        }
        view.setHovered(false);
        view.sendAccessibilityEvent(256);
        return true;
    }
}
