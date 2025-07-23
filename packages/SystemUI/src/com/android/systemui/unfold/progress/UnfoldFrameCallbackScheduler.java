package com.android.systemui.unfold.progress;

import android.os.Looper;
import android.view.Choreographer;
import androidx.dynamicanimation.animation.AnimationHandler$$ExternalSyntheticLambda0;
import androidx.dynamicanimation.animation.FrameCallbackScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UnfoldFrameCallbackScheduler implements FrameCallbackScheduler {
    public final Choreographer choreographer = Choreographer.getInstance();
    public final Looper looper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        UnfoldFrameCallbackScheduler create();
    }

    public UnfoldFrameCallbackScheduler() {
        Looper myLooper = Looper.myLooper();
        if (myLooper == null) {
            throw new IllegalStateException("This should be created in a thread with a looper.");
        }
        this.looper = myLooper;
    }

    @Override // androidx.dynamicanimation.animation.FrameCallbackScheduler
    public final boolean isCurrentThread() {
        return this.looper.isCurrentThread();
    }

    @Override // androidx.dynamicanimation.animation.FrameCallbackScheduler
    public final void postFrameCallback(final AnimationHandler$$ExternalSyntheticLambda0 animationHandler$$ExternalSyntheticLambda0) {
        this.choreographer.postFrameCallback(new Choreographer.FrameCallback() { // from class: com.android.systemui.unfold.progress.UnfoldFrameCallbackScheduler$postFrameCallback$1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                animationHandler$$ExternalSyntheticLambda0.run();
            }
        });
    }
}
