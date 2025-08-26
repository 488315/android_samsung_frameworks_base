package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import com.android.systemui.shade.NotificationPanelViewController;

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
