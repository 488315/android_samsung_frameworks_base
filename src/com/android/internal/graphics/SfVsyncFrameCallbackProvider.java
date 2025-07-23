package com.android.internal.graphics;

import android.animation.AnimationHandler;
import android.view.Choreographer;

@Deprecated
/* loaded from: classes5.dex */
public final class SfVsyncFrameCallbackProvider implements AnimationHandler.AnimationFrameCallbackProvider {
    private final Choreographer mChoreographer;

    public SfVsyncFrameCallbackProvider() {
        this.mChoreographer = Choreographer.getSfInstance();
    }

    public SfVsyncFrameCallbackProvider(Choreographer choreographer) {
        this.mChoreographer = choreographer;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public void postFrameCallback(Choreographer.FrameCallback frameCallback) {
        this.mChoreographer.postFrameCallback(frameCallback);
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public void postCommitCallback(Runnable runnable) {
        this.mChoreographer.postCallback(4, runnable, null);
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public long getFrameTime() {
        return this.mChoreographer.getFrameTime();
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public long getFrameDelay() {
        return Choreographer.getFrameDelay();
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public void setFrameDelay(long j) {
        Choreographer.setFrameDelay(j);
    }
}
