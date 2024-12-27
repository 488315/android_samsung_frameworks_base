package com.android.systemui.qs.buttons;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSPowerButton$$ExternalSyntheticLambda0 implements View.OnHoverListener {
    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        int i = QSPowerButton.$r8$clinit;
        int action = motionEvent.getAction();
        if (action == 9) {
            view.sendAccessibilityEvent(128);
            view.setHovered(false);
            return true;
        }
        if (action != 10) {
            return false;
        }
        view.sendAccessibilityEvent(256);
        view.setHovered(false);
        return true;
    }
}
