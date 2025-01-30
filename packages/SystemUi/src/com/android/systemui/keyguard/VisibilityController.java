package com.android.systemui.keyguard;

import android.view.SurfaceControl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface VisibilityController {
    void invalidate();

    boolean needToBeInvisibleWindow();

    void registerFrameUpdateCallback(Function0 function0);

    void resetForceInvisible(boolean z);

    boolean setForceInvisible(SurfaceControl.Transaction transaction, boolean z);
}
