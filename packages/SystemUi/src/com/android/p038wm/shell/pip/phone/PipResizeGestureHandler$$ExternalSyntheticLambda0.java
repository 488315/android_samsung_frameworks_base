package com.android.p038wm.shell.pip.phone;

import android.graphics.Rect;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipResizeGestureHandler$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ PipResizeGestureHandler f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PipResizeGestureHandler pipResizeGestureHandler = this.f$0;
        pipResizeGestureHandler.mUserResizeBounds.set((Rect) obj);
        pipResizeGestureHandler.mMotionHelper.synchronizePinnedStackBounds();
        pipResizeGestureHandler.mUpdateMovementBoundsRunnable.run();
        pipResizeGestureHandler.resetState();
    }
}
