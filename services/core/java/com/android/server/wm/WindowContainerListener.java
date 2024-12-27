package com.android.server.wm;

public interface WindowContainerListener extends ConfigurationContainerListener {
    default void onDisplayChanged(DisplayContent displayContent) {}

    default void onRemoved() {}

    default void onVisibleRequestedChanged(boolean z) {}
}
