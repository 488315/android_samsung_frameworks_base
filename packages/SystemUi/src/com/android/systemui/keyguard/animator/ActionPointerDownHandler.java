package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActionPointerDownHandler extends ActionHandlerType {
    public ActionPointerDownHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        KeyguardTouchAnimator keyguardTouchAnimator = this.parent;
        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = keyguardTouchAnimator.callback;
        if (keyguardTouchSwipeCallback == null) {
            keyguardTouchSwipeCallback = null;
        }
        keyguardTouchSwipeCallback.setMotionAborted();
        keyguardTouchAnimator.isMultiTouch = motionEvent.getPointerCount() >= 2;
        return true;
    }
}
