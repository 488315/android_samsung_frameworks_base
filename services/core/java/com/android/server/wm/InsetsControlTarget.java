package com.android.server.wm;

import android.view.WindowInsets;
import android.view.inputmethod.ImeTracker;

public interface InsetsControlTarget {
    default boolean canShowTransient() {
        return this instanceof InsetsPolicy.ImmersiveControlTarget;
    }

    default int getRequestedVisibleTypes() {
        return WindowInsets.Type.defaultVisible();
    }

    default WindowState getWindow() {
        return null;
    }

    default void hideInsets(int i, boolean z, ImeTracker.Token token) {}

    default boolean isRequestedVisible(int i) {
        return (WindowInsets.Type.defaultVisible() & i) != 0;
    }

    default void notifyInsetsControlChanged(int i) {}

    default void setImeInputTargetRequestedVisibility(boolean z) {}

    default void showInsets(int i, boolean z, ImeTracker.Token token) {}
}
