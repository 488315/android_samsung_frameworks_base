package com.facebook.rebound;

import android.os.SystemClock;
import android.view.Choreographer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper extends SpringLooper {
    public final Choreographer mChoreographer;
    public final ChoreographerFrameCallbackC41971 mFrameCallback = new Choreographer.FrameCallback() { // from class: com.facebook.rebound.AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.1
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper androidSpringLooperFactory$ChoreographerAndroidSpringLooper = AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.this;
            if (!androidSpringLooperFactory$ChoreographerAndroidSpringLooper.mStarted || androidSpringLooperFactory$ChoreographerAndroidSpringLooper.mSpringSystem == null) {
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.this.mSpringSystem.loop(uptimeMillis - r0.mLastTime);
            AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper androidSpringLooperFactory$ChoreographerAndroidSpringLooper2 = AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.this;
            androidSpringLooperFactory$ChoreographerAndroidSpringLooper2.mLastTime = uptimeMillis;
            androidSpringLooperFactory$ChoreographerAndroidSpringLooper2.mChoreographer.postFrameCallback(androidSpringLooperFactory$ChoreographerAndroidSpringLooper2.mFrameCallback);
        }
    };
    public long mLastTime;
    public boolean mStarted;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.facebook.rebound.AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper$1] */
    public AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper(Choreographer choreographer) {
        this.mChoreographer = choreographer;
    }

    @Override // com.facebook.rebound.SpringLooper
    public final void start() {
        if (this.mStarted) {
            return;
        }
        this.mStarted = true;
        this.mLastTime = SystemClock.uptimeMillis();
        Choreographer choreographer = this.mChoreographer;
        ChoreographerFrameCallbackC41971 choreographerFrameCallbackC41971 = this.mFrameCallback;
        choreographer.removeFrameCallback(choreographerFrameCallbackC41971);
        choreographer.postFrameCallback(choreographerFrameCallbackC41971);
    }

    @Override // com.facebook.rebound.SpringLooper
    public final void stop() {
        this.mStarted = false;
        this.mChoreographer.removeFrameCallback(this.mFrameCallback);
    }
}
