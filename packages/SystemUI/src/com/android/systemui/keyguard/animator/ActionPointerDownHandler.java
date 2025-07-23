package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import com.android.systemui.shade.NotificationPanelViewController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ActionPointerDownHandler extends ActionHandlerType {
    public ActionPointerDownHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        KeyguardTouchAnimator keyguardTouchAnimator = this.parent;
        NotificationPanelViewController.AnonymousClass4 anonymousClass4 = keyguardTouchAnimator.callback;
        if (anonymousClass4 == null) {
            anonymousClass4 = null;
        }
        NotificationPanelViewController.this.setMotionAborted();
        keyguardTouchAnimator.isMultiTouch = motionEvent.getPointerCount() >= 2;
        return true;
    }
}
