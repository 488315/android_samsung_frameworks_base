package com.android.systemui.pluginlock;

import android.content.ComponentName;
import android.os.Bundle;

public interface PluginLockBottomAreaCallback {
    default boolean isNoUnlockNeed(String str) {
        return false;
    }

    default Bundle onUiInfoRequested(boolean z) {
        return null;
    }

    default void updateBottomView() {
    }

    default void launchApp(ComponentName componentName) {
    }

    default void onViewModeChanged(int i) {
    }
}
