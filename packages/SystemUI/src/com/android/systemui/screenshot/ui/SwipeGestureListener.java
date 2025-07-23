package com.android.systemui.screenshot.ui;

import android.util.DisplayMetrics;
import android.view.VelocityTracker;
import android.view.View;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SwipeGestureListener {
    public final DisplayMetrics displayMetrics;
    public final Function0 onCancel;
    public final Function1 onDismiss;
    public float startX;
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public final View view;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SwipeGestureListener(View view, Function1 function1, Function0 function0) {
        this.view = view;
        this.onDismiss = function1;
        this.onCancel = function0;
        this.displayMetrics = view.getResources().getDisplayMetrics();
    }
}
