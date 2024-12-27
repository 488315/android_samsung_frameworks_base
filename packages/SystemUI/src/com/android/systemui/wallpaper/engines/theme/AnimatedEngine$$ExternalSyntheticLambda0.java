package com.android.systemui.wallpaper.engines.theme;

import android.graphics.Canvas;
import android.util.Log;
import android.view.Choreographer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                Canvas lockHardwareCanvas = animatedEngine.mSurfaceHolder.lockHardwareCanvas();
                if (lockHardwareCanvas == null) {
                    Log.e(animatedEngine.TAG, "onFrameChanged : canvas is null");
                    return;
                }
                try {
                    animatedEngine.mRootView.draw(lockHardwareCanvas);
                } finally {
                    animatedEngine.mSurfaceHolder.unlockCanvasAndPost(lockHardwareCanvas);
                }
            } finally {
            }
        }
    }
}
