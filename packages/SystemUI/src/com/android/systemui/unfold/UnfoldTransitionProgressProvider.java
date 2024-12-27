package com.android.systemui.unfold;

import com.android.systemui.unfold.util.CallbackController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface UnfoldTransitionProgressProvider extends CallbackController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
