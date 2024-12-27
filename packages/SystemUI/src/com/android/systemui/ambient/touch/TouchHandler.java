package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import android.graphics.Region;
import com.android.systemui.ambient.touch.TouchMonitor;

public interface TouchHandler {
    default Boolean isEnabled() {
        return Boolean.TRUE;
    }

    void onSessionStart(TouchMonitor.TouchSessionImpl touchSessionImpl);

    default void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
    }
}
