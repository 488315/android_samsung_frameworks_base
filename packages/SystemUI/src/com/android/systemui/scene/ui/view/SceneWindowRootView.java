package com.android.systemui.scene.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowInsets;
import com.android.systemui.shade.TouchLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class SceneWindowRootView extends WindowRootView {
    public final StateFlowImpl windowInsets;

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
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "SceneWindowRootView", zDispatchTouchEvent);
        return zDispatchTouchEvent;
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
