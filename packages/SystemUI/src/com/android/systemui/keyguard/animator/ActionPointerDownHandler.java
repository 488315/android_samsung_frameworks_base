package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
