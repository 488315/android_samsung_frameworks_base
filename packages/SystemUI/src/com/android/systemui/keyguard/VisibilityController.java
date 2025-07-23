package com.android.systemui.keyguard;

import android.view.SurfaceControl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface VisibilityController {
    void invalidate();

    default boolean needToBeInvisibleWindow() {
        return false;
    }

    void registerFrameUpdateCallback(Function0 function0);

    void resetForceInvisible(boolean z);

    boolean setForceInvisible(SurfaceControl.Transaction transaction);
}
