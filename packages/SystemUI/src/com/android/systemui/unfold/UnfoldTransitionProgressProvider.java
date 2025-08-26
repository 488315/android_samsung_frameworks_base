package com.android.systemui.unfold;

import com.android.systemui.unfold.util.CallbackController;

/* loaded from: classes3.dex */
public interface UnfoldTransitionProgressProvider extends CallbackController {

    public interface TransitionProgressListener {
        default void onTransitionProgress(float f) {
        }

        default void onTransitionFinished() {
        }

        default void onTransitionFinishing() {
        }

        default void onTransitionStarted() {
        }
    }
}
