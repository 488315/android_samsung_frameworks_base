package com.android.server.wm;

import android.os.Message;
import android.window.DisplayAreaInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
