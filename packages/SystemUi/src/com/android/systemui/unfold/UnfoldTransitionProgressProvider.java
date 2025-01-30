package com.android.systemui.unfold;

import com.android.systemui.unfold.util.CallbackController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface UnfoldTransitionProgressProvider extends CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
