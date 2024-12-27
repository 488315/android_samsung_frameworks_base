package com.android.systemui.unfold.updates;

import com.android.systemui.unfold.util.CallbackController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface FoldStateProvider extends CallbackController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface FoldUpdatesListener {
        default void onUnfoldedScreenAvailable() {
        }

        default void onFoldUpdate(int i) {
        }

        default void onHingeAngleUpdate(float f) {
        }
    }
}
