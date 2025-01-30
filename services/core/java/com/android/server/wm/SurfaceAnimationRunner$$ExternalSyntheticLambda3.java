package com.android.server.wm;

import android.view.Choreographer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class SurfaceAnimationRunner$$ExternalSyntheticLambda3 implements Choreographer.FrameCallback {
    public final /* synthetic */ SurfaceAnimationRunner f$0;

    public /* synthetic */ SurfaceAnimationRunner$$ExternalSyntheticLambda3(SurfaceAnimationRunner surfaceAnimationRunner) {
        this.f$0 = surfaceAnimationRunner;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        this.f$0.startAnimations(j);
    }
}
