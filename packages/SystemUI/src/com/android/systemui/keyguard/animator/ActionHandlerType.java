package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class ActionHandlerType {
    public static final Companion Companion = new Companion(null);
    public final KeyguardTouchAnimator parent;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ActionHandlerType(KeyguardTouchAnimator keyguardTouchAnimator) {
        this.parent = keyguardTouchAnimator;
    }

    public abstract boolean handleMotionEvent(MotionEvent motionEvent);
}
