package com.android.systemui.plugins.cover;

import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = PluginDisplayCover.ACTION, version = 1)
public interface PluginDisplayCover extends PluginCover {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_DISPLAY_COVER";
    public static final int VERSION = 1;

    default void onUserUnlocked() {
    }

    default void onLockDisabledChanged(boolean z) {
    }

    default void onWindowFocusChanged(boolean z) {
    }
}
