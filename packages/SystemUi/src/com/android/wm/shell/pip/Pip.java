package com.android.wm.shell.pip;

import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface Pip {
    default boolean isExitingPipTask(int i) {
        return false;
    }

    default void addPipExclusionBoundsChangeListener(Consumer consumer) {
    }

    default void removePipExclusionBoundsChangeListener(Consumer consumer) {
    }

    default void setOnIsInPipStateChangedListener(EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0) {
    }

    default void showPictureInPictureMenu() {
    }

    default void onSystemUiStateChanged(long j, boolean z) {
    }
}
