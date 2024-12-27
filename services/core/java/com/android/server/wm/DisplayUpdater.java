package com.android.server.wm;

import android.os.Message;
import android.window.DisplayAreaInfo;

public interface DisplayUpdater {
    void onDisplayContentDisplayPropertiesPostChanged(
            int i, int i2, DisplayAreaInfo displayAreaInfo);

    default void onDisplayContentDisplayPropertiesPreChanged(
            int i, int i2, int i3, int i4, int i5) {}

    default void onDisplaySwitching(boolean z) {}

    void updateDisplayInfo(Runnable runnable);

    default boolean waitForTransition(Message message) {
        return false;
    }
}
