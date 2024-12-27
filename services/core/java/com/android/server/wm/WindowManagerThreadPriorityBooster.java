package com.android.server.wm;

import android.os.Process;
import com.android.server.AnimationThread;
import com.android.server.ThreadPriorityBooster;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowManagerThreadPriorityBooster extends ThreadPriorityBooster {
    public final int mAnimationThreadId;
    public boolean mAppTransitionRunning;
    public final Object mLock;
    public final int mSurfaceAnimationThreadId;

    public WindowManagerThreadPriorityBooster() {
        super(-4, 5);
        SurfaceAnimationThread surfaceAnimationThread;
        this.mLock = new Object();
        this.mAnimationThreadId = AnimationThread.get().getThreadId();
        SurfaceAnimationThread surfaceAnimationThread2 = SurfaceAnimationThread.sInstance;
        synchronized (SurfaceAnimationThread.class) {
            SurfaceAnimationThread.ensureThreadLocked();
            surfaceAnimationThread = SurfaceAnimationThread.sInstance;
        }
        this.mSurfaceAnimationThreadId = surfaceAnimationThread.getThreadId();
    }

    @Override // com.android.server.ThreadPriorityBooster
    public final void boost() {
        int myTid = Process.myTid();
        if (myTid == this.mAnimationThreadId || myTid == this.mSurfaceAnimationThreadId) {
            return;
        }
        super.boost();
    }

    @Override // com.android.server.ThreadPriorityBooster
    public final void reset() {
        int myTid = Process.myTid();
        if (myTid == this.mAnimationThreadId || myTid == this.mSurfaceAnimationThreadId) {
            return;
        }
        super.reset();
    }
}
