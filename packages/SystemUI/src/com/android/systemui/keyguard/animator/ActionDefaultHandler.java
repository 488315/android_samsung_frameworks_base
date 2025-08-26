package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;

/* loaded from: classes2.dex */
public final class ActionDefaultHandler extends ActionHandlerType {
    public ActionDefaultHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        return false;
    }
}
