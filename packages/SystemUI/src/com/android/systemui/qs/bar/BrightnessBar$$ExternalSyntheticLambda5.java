package com.android.systemui.qs.bar;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BrightnessBar$$ExternalSyntheticLambda5 implements View.OnHoverListener {
    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        int i = BrightnessBar.$r8$clinit;
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
