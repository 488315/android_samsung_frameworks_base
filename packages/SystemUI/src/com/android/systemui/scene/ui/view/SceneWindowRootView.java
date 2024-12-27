package com.android.systemui.scene.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowInsets;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SceneWindowRootView extends WindowRootView {
    public final StateFlowImpl windowInsets;

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
