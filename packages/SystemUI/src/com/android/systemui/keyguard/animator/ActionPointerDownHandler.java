package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;

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
