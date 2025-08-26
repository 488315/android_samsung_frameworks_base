package com.android.systemui.pluginlock;

import android.content.ComponentName;
import android.os.Bundle;

/* loaded from: classes2.dex */
public interface PluginLockBottomAreaCallback {
    default boolean isNoUnlockNeed(String str) {
        return false;
    }

    default Bundle onUiInfoRequested(boolean z) {
        return null;
    }

    default void launchApp(ComponentName componentName) {
    }

    default void onViewModeChanged(int i) {
    }

    default void updateBottomView() {
    }
}
