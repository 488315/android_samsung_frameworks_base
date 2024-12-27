package com.android.systemui.scene.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowInsets;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class SceneWindowRootView extends WindowRootView {
    public final StateFlowImpl windowInsets;

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

    public SceneWindowRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.windowInsets = StateFlowKt.MutableStateFlow(null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        throw null;
    }

    @Override // com.android.systemui.scene.ui.view.WindowRootView, android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.windowInsets.updateState(null, windowInsets);
        return windowInsets;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
    }
}
