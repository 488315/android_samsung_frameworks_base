package com.android.server.wm;

/* loaded from: classes3.dex */
public interface WindowContainerListener extends ConfigurationContainerListener {
    default void onDisplayChanged(DisplayContent displayContent) {
    }

    default void onRemoved() {
    }

    default void onVisibleRequestedChanged(boolean z) {
    }
}
