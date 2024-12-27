package com.android.systemui.unfold.updates;

import com.android.systemui.unfold.util.CallbackController;

public interface FoldStateProvider extends CallbackController {

    public interface FoldUpdatesListener {
        default void onUnfoldedScreenAvailable() {
        }

        default void onFoldUpdate(int i) {
        }

        default void onHingeAngleUpdate(float f) {
        }
    }
}
