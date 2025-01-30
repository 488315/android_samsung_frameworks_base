package com.android.systemui.dreams.touch;

import android.graphics.Rect;
import android.graphics.Region;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface DreamTouchHandler {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TouchSession {
    }

    void onSessionStart(TouchSession touchSession);

    default void getTouchInitiationRegion(Rect rect, Region region) {
    }
}
