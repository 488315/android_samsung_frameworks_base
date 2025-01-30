package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ActionHandlerType {
    public static final Companion Companion = new Companion(null);
    public final KeyguardTouchAnimator parent;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
