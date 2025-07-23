package com.android.wm.shell.pip.phone;

import android.graphics.Rect;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipResizeGestureHandler$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipResizeGestureHandler f$0;

    public /* synthetic */ PipResizeGestureHandler$$ExternalSyntheticLambda0(PipResizeGestureHandler pipResizeGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = pipResizeGestureHandler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        PipResizeGestureHandler pipResizeGestureHandler = this.f$0;
        switch (i) {
            case 0:
                pipResizeGestureHandler.mUserResizeBounds.set((Rect) obj);
                pipResizeGestureHandler.mMotionHelper.synchronizePinnedStackBounds();
                pipResizeGestureHandler.mUpdateMovementBoundsRunnable.run();
                pipResizeGestureHandler.resetState();
                break;
            default:
                pipResizeGestureHandler.getClass();
                break;
        }
    }
}
