package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import android.graphics.Region;

/* loaded from: classes.dex */
public interface TouchHandler {

    public interface TouchSession {

        public interface Callback {
            void onRemoved();
        }
    }

    default Boolean isEnabled() {
        return Boolean.TRUE;
    }

    void onSessionStart(TouchSession touchSession);

    default void onDestroy() {
    }

    default void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
    }
}
