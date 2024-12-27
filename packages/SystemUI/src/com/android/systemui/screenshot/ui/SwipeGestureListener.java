package com.android.systemui.screenshot.ui;

import android.util.DisplayMetrics;
import android.view.VelocityTracker;
import android.view.View;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SwipeGestureListener {
    public final DisplayMetrics displayMetrics;
    public final Function0 onCancel;
    public final Function1 onDismiss;
    public float startX;
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public final View view;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
