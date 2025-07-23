package com.android.systemui.qs.buttons;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSettingsButton$$ExternalSyntheticLambda0 implements View.OnHoverListener {
    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        int i = QSSettingsButton.$r8$clinit;
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
