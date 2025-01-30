package com.android.p038wm.shell.pip.phone;

import android.os.Looper;
import com.android.p038wm.shell.pip.phone.PipResizeGestureHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipResizeGestureHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipResizeGestureHandler f$0;

    public /* synthetic */ PipResizeGestureHandler$$ExternalSyntheticLambda1(PipResizeGestureHandler pipResizeGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = pipResizeGestureHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mPipTouchState.mAllowInputEvents = true;
                break;
            default:
                PipResizeGestureHandler pipResizeGestureHandler = this.f$0;
                pipResizeGestureHandler.getClass();
                pipResizeGestureHandler.mInputEventReceiver = new PipResizeGestureHandler.PipResizeInputEventReceiver(pipResizeGestureHandler, pipResizeGestureHandler.mInputMonitor.getInputChannel(), Looper.myLooper());
                break;
        }
    }
}
