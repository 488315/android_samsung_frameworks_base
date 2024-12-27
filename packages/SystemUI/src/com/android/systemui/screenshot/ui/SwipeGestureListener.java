package com.android.systemui.screenshot.ui;

import android.util.DisplayMetrics;
import android.view.VelocityTracker;
import android.view.View;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SwipeGestureListener {
    public final DisplayMetrics displayMetrics;
    public final Function0 onCancel;
    public final Function1 onDismiss;
    public float startX;
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public final View view;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
