package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import android.graphics.Region;
import com.android.systemui.ambient.touch.TouchMonitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface TouchHandler {
    default Boolean isEnabled() {
        return Boolean.TRUE;
    }

    void onSessionStart(TouchMonitor.TouchSessionImpl touchSessionImpl);

    default void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
    }
}
