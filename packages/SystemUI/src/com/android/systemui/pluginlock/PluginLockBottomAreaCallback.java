package com.android.systemui.pluginlock;

import android.content.ComponentName;
import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
