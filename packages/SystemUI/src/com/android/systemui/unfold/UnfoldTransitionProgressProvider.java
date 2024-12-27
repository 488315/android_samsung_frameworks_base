package com.android.systemui.unfold;

import com.android.systemui.unfold.util.CallbackController;

public interface UnfoldTransitionProgressProvider extends CallbackController {

    public interface TransitionProgressListener {
        default void onTransitionFinished() {
        }

        default void onTransitionFinishing() {
        }

        default void onTransitionStarted() {
        }

        default void onTransitionProgress(float f) {
        }
    }
}
