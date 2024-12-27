package com.android.systemui.keyguard;

import android.view.SurfaceControl;
import kotlin.jvm.functions.Function0;

public interface VisibilityController {
    void invalidate();

    default boolean needToBeInvisibleWindow() {
        return false;
    }

    void registerFrameUpdateCallback(Function0 function0);

    void resetForceInvisible(boolean z);

    boolean setForceInvisible(SurfaceControl.Transaction transaction);
}
