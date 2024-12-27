package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class ActionHandlerType {
    public static final Companion Companion = new Companion(null);
    public final KeyguardTouchAnimator parent;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ActionHandlerType(KeyguardTouchAnimator keyguardTouchAnimator) {
        this.parent = keyguardTouchAnimator;
    }

    public abstract boolean handleMotionEvent(MotionEvent motionEvent);
}
