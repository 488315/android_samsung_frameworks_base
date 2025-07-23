package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import android.graphics.Region;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface TouchHandler {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TouchSession {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
