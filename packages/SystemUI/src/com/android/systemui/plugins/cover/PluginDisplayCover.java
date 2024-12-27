package com.android.systemui.plugins.cover;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = PluginDisplayCover.ACTION, version = 1)
/* loaded from: classes2.dex */
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
