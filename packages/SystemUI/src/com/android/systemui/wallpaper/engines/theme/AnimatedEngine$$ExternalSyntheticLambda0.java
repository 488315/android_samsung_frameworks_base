package com.android.systemui.wallpaper.engines.theme;

import android.graphics.Canvas;
import android.util.Log;
import android.view.Choreographer;

/* loaded from: classes3.dex */
public final /* synthetic */ class AnimatedEngine$$ExternalSyntheticLambda0 implements Choreographer.FrameCallback {
    public final /* synthetic */ AnimatedEngine f$0;

    public /* synthetic */ AnimatedEngine$$ExternalSyntheticLambda0(AnimatedEngine animatedEngine) {
        this.f$0 = animatedEngine;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        AnimatedEngine animatedEngine = this.f$0;
        synchronized (animatedEngine.mLock) {
            try {
                if (!animatedEngine.mShowing) {
                    animatedEngine.mChoreographer.removeFrameCallback(new AnimatedEngine$$ExternalSyntheticLambda0(animatedEngine));
                    return;
                }
                animatedEngine.mChoreographer.postFrameCallback(new AnimatedEngine$$ExternalSyntheticLambda0(animatedEngine));
                Canvas canvasLockHardwareCanvas = animatedEngine.mSurfaceHolder.lockHardwareCanvas();
                if (canvasLockHardwareCanvas == null) {
                    Log.e(animatedEngine.TAG, "onFrameChanged : canvas is null");
                    return;
                }
                try {
                    animatedEngine.mRootView.draw(canvasLockHardwareCanvas);
                } finally {
                    animatedEngine.mSurfaceHolder.unlockCanvasAndPost(canvasLockHardwareCanvas);
                }
            } finally {
            }
        }
    }
}
