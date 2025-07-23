package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ActionHandlerType {
    public static final Companion Companion = new Companion(null);
    public final KeyguardTouchAnimator parent;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
