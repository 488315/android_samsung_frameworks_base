package com.android.server.wm;

import android.view.Choreographer;

public final /* synthetic */ class SurfaceAnimationRunner$$ExternalSyntheticLambda5
        implements Choreographer.FrameCallback {
    public final /* synthetic */ SurfaceAnimationRunner f$0;

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        SurfaceAnimationRunner surfaceAnimationRunner = this.f$0;
        synchronized (surfaceAnimationRunner.mLock) {
            try {
                if (surfaceAnimationRunner.mPreProcessingAnimations.isEmpty()) {
                    surfaceAnimationRunner.startPendingAnimationsLocked();
                    surfaceAnimationRunner.mPowerManagerInternal.setPowerBoost(0, 0);
                }
            } finally {
            }
        }
    }
}
