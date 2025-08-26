package com.android.systemui.qs.buttons;

import android.view.MotionEvent;
import android.view.View;
import com.android.settingslib.applications.InterestingConfigChanges;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSPowerButton$$ExternalSyntheticLambda0 implements View.OnHoverListener {
    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        InterestingConfigChanges interestingConfigChanges = QSPowerButton.configChanges;
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
