package com.android.systemui.plugins.cover;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
