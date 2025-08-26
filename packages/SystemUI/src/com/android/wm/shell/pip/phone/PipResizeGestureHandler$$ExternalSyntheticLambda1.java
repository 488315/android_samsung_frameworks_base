package com.android.wm.shell.pip.phone;

import android.os.Looper;
import android.util.Log;
import com.android.wm.shell.pip.phone.PipResizeGestureHandler;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipResizeGestureHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipResizeGestureHandler f$0;

    public /* synthetic */ PipResizeGestureHandler$$ExternalSyntheticLambda1(PipResizeGestureHandler pipResizeGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = pipResizeGestureHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PipResizeGestureHandler pipResizeGestureHandler = this.f$0;
        switch (i) {
            case 0:
                PipTouchState pipTouchState = pipResizeGestureHandler.mPipTouchState;
                if (!pipTouchState.mAllowTouches) {
                    Log.w("PipResizeGestureHandler", "reset touch state after a certain period of time");
                    pipTouchState.mAllowTouches = true;
                    if (pipTouchState.mIsUserInteracting) {
                        pipTouchState.reset();
                    }
                    pipResizeGestureHandler.resetState();
                    break;
                }
                break;
            case 1:
                pipResizeGestureHandler.mPipTouchState.mAllowInputEvents = true;
                break;
            default:
                pipResizeGestureHandler.getClass();
                pipResizeGestureHandler.mInputEventReceiver = new PipResizeGestureHandler.PipResizeInputEventReceiver(pipResizeGestureHandler, pipResizeGestureHandler.mInputMonitor.getInputChannel(), Looper.myLooper());
                break;
        }
    }
}
