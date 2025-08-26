package com.android.systemui.unfold.updates;

import com.android.systemui.unfold.util.CallbackController;

/* loaded from: classes3.dex */
public interface FoldStateProvider extends CallbackController {

    public interface FoldUpdatesListener {
        default void onFoldUpdate(int i) {
        }

        default void onHingeAngleUpdate(float f) {
        }

        default void onUnfoldedScreenAvailable() {
        }
    }
}
