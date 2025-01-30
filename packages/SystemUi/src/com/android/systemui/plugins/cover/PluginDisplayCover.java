package com.android.systemui.plugins.cover;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = PluginDisplayCover.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface PluginDisplayCover extends PluginCover {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_DISPLAY_COVER";
    public static final int VERSION = 1;

    default void onLockDisabledChanged(boolean z) {
    }

    default void onWindowFocusChanged(boolean z) {
    }

    default void onUserUnlocked() {
    }
}
