package com.android.server.wm;

import android.view.Choreographer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
