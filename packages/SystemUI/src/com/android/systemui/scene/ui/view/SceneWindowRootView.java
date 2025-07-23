package com.android.systemui.scene.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowInsets;
import com.android.systemui.shade.TouchLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneWindowRootView extends WindowRootView {
    public final StateFlowImpl windowInsets;

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

    public SceneWindowRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.windowInsets = StateFlowKt.MutableStateFlow(null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "SceneWindowRootView", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override // com.android.systemui.scene.ui.view.WindowRootView, android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.windowInsets.setValue(windowInsets);
        return windowInsets;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
    }
}
