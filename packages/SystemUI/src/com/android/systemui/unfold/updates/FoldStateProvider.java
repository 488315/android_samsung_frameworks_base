package com.android.systemui.unfold.updates;

import com.android.systemui.unfold.util.CallbackController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface FoldStateProvider extends CallbackController {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface FoldUpdatesListener {
        default void onFoldUpdate(int i) {
        }

        default void onHingeAngleUpdate(float f) {
        }

        default void onUnfoldedScreenAvailable() {
        }
    }
}
