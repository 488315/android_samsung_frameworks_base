package com.android.server.wm;

import android.content.Context;
import android.view.Choreographer;
import android.view.SurfaceControl;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.server.wm.WindowAnimator$$ExternalSyntheticLambda1] */
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

    /* JADX WARN: Removed duplicated region for block: B:100:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:106:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01a6  */
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
