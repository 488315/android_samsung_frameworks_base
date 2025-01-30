package com.android.systemui.unfold.updates;

import com.android.systemui.unfold.util.CallbackController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface FoldStateProvider extends CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FoldUpdatesListener {
        void onFoldUpdate(int i);

        default void onHingeAngleUpdate(float f) {
        }

        default void onUnfoldedScreenAvailable() {
        }
    }
}
