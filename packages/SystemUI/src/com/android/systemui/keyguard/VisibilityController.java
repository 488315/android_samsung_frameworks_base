package com.android.systemui.keyguard;

import android.view.SurfaceControl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface VisibilityController {
    void invalidate();

    default boolean needToBeInvisibleWindow() {
        return false;
    }

    void registerFrameUpdateCallback(Function0 function0);

    void resetForceInvisible(boolean z);

    boolean setForceInvisible(SurfaceControl.Transaction transaction);
}
