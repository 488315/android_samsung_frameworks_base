package com.android.server.wm;

import android.content.Context;
import android.view.Choreographer;
import android.view.SurfaceControl;

import java.util.ArrayList;

public final class WindowAnimator {
    public final WindowAnimator$$ExternalSyntheticLambda1 mAnimationFrameCallback;
    public boolean mAnimationFrameCallbackScheduled;
    public Choreographer mChoreographer;
    public long mCurrentTime;
    public boolean mInExecuteAfterPrepareSurfacesRunnables;
    public boolean mLastRootAnimating;
    public WindowState mLastWindowFreezeSource;
    public boolean mRunningExpensiveAnimations;
    public final WindowManagerService mService;
    public final SurfaceControl.Transaction mTransaction;
    public int mBulkUpdateParams = 0;
    public boolean mInitialized = false;
    public boolean mNotifyWhenNoAnimation = false;
    public final ArrayList mAfterPrepareSurfacesRunnables = new ArrayList();

    public WindowAnimator(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        Context context = windowManagerService.mContext;
        this.mTransaction =
                (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        windowManagerService.mAnimationHandler.runWithScissors(
                new Runnable() { // from class:
                                 // com.android.server.wm.WindowAnimator$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WindowAnimator windowAnimator = WindowAnimator.this;
                        windowAnimator.getClass();
                        windowAnimator.mChoreographer = Choreographer.getSfInstance();
                    }
                },
                0L);
        this.mAnimationFrameCallback =
                new Choreographer
                        .FrameCallback() { // from class:
                                           // com.android.server.wm.WindowAnimator$$ExternalSyntheticLambda1
                    @Override // android.view.Choreographer.FrameCallback
                    public final void doFrame(long j) {
                        WindowAnimator windowAnimator = WindowAnimator.this;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                windowAnimator.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                windowAnimator.mAnimationFrameCallbackScheduled = false;
                                windowAnimator.animate(j);
                                if (windowAnimator.mNotifyWhenNoAnimation
                                        && !windowAnimator.mLastRootAnimating) {
                                    windowAnimator.mService.mGlobalLock.notifyAll();
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                };
    }

    public final void addAfterPrepareSurfacesRunnable(Runnable runnable) {
        if (this.mInExecuteAfterPrepareSurfacesRunnables) {
            runnable.run();
            return;
        }
        this.mAfterPrepareSurfacesRunnables.add(runnable);
        if (this.mAnimationFrameCallbackScheduled) {
            return;
        }
        this.mAnimationFrameCallbackScheduled = true;
        this.mChoreographer.postFrameCallback(this.mAnimationFrameCallback);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void animate(long r14) {
        /*
            Method dump skipped, instructions count: 482
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.wm.WindowAnimator.animate(long):void");
    }
}
