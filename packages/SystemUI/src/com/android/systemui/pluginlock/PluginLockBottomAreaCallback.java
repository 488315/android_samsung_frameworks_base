package com.android.systemui.pluginlock;

import android.content.ComponentName;
import android.os.Bundle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
