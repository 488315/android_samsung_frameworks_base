package com.android.server.wm;

import android.os.Debug;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowSurfacePlacer {
    public int mDeferredRequests;
    public int mLayoutRepeatCount;
    public boolean mPrintLayoutCaller;
    public final WindowManagerService mService;
    public boolean mTraversalScheduled;
    public boolean mInLayout = false;
    public int mDeferDepth = 0;
    public final Traverser mPerformSurfacePlacement = new Traverser();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Traverser implements Runnable {
        public Traverser() {}

        @Override // java.lang.Runnable
        public final void run() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    WindowSurfacePlacer.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowSurfacePlacer.this.performSurfacePlacement(false);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public WindowSurfacePlacer(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    public final void performSurfacePlacement() {
        performSurfacePlacement(false);
    }

    public final void performSurfacePlacement(boolean z) {
        if (this.mDeferDepth > 0 && !z) {
            this.mDeferredRequests++;
            return;
        }
        int i = 6;
        do {
            this.mTraversalScheduled = false;
            if (this.mInLayout) {
                Slog.w(
                        "WindowManager",
                        "performLayoutAndPlaceSurfacesLocked called while in layout. Callers="
                                + Debug.getCallers(3));
            } else if (!this.mService.getDefaultDisplayContentLocked().mWaitingForConfig) {
                WindowManagerService windowManagerService = this.mService;
                if (windowManagerService.mDisplayReady) {
                    this.mInLayout = true;
                    if (!windowManagerService.mForceRemoves.isEmpty()) {
                        while (!this.mService.mForceRemoves.isEmpty()) {
                            WindowState windowState =
                                    (WindowState) this.mService.mForceRemoves.remove(0);
                            Slog.i("WindowManager", "Force removing: " + windowState);
                            windowState.removeImmediately();
                        }
                        Slog.w(
                                "WindowManager",
                                "Due to memory failure, waiting a bit for next layout");
                        Object obj = new Object();
                        synchronized (obj) {
                            try {
                                obj.wait(250L);
                            } catch (InterruptedException unused) {
                            }
                        }
                    }
                    try {
                        this.mService.mRoot.performSurfacePlacement();
                        this.mInLayout = false;
                        if (this.mService.mRoot.isLayoutNeeded()) {
                            int i2 = this.mLayoutRepeatCount + 1;
                            this.mLayoutRepeatCount = i2;
                            if (i2 < 6) {
                                requestTraversal();
                            } else {
                                Slog.e("WindowManager", "Performed 6 layouts in a row. Skipping");
                                this.mLayoutRepeatCount = 0;
                                this.mPrintLayoutCaller = true;
                            }
                        } else {
                            this.mLayoutRepeatCount = 0;
                        }
                        WindowManagerService windowManagerService2 = this.mService;
                        if (windowManagerService2.mWindowsChanged
                                && !windowManagerService2.mWindowChangeListeners.isEmpty()) {
                            this.mService.mH.removeMessages(19);
                            this.mService.mH.sendEmptyMessage(19);
                        }
                    } catch (RuntimeException e) {
                        this.mInLayout = false;
                        Slog.wtf(
                                "WindowManager", "Unhandled exception while laying out windows", e);
                    }
                }
            }
            this.mService.mAnimationHandler.removeCallbacks(this.mPerformSurfacePlacement);
            i--;
            if (!this.mTraversalScheduled) {
                break;
            }
        } while (i > 0);
        this.mService.mRoot.mWallpaperActionPending = false;
    }

    public final void requestTraversal() {
        if (this.mTraversalScheduled) {
            return;
        }
        this.mTraversalScheduled = true;
        if (this.mDeferDepth > 0) {
            this.mDeferredRequests++;
        } else {
            this.mService.mAnimationHandler.post(this.mPerformSurfacePlacement);
        }
    }
}
