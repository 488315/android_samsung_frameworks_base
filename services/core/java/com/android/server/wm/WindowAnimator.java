package com.android.server.wm;

import android.content.Context;
import android.os.Trace;
import android.util.Slog;
import android.util.TimeUtils;
import android.view.Choreographer;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.policy.WindowManagerPolicy;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class WindowAnimator {
    public final Choreographer.FrameCallback mAnimationFrameCallback;
    public boolean mAnimationFrameCallbackScheduled;
    public Choreographer mChoreographer;
    public final Context mContext;
    public long mCurrentTime;
    public boolean mInExecuteAfterPrepareSurfacesRunnables;
    public boolean mLastRootAnimating;
    public Object mLastWindowFreezeSource;
    public final WindowManagerPolicy mPolicy;
    public boolean mRunningExpensiveAnimations;
    public final WindowManagerService mService;
    public final SurfaceControl.Transaction mTransaction;
    public int mBulkUpdateParams = 0;
    public boolean mInitialized = false;
    public boolean mNotifyWhenNoAnimation = false;
    public final ArrayList mAfterPrepareSurfacesRunnables = new ArrayList();

    public WindowAnimator(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mContext = windowManagerService.mContext;
        this.mPolicy = windowManagerService.mPolicy;
        this.mTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        windowManagerService.mAnimationHandler.runWithScissors(new Runnable() { // from class: com.android.server.wm.WindowAnimator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WindowAnimator.this.lambda$new$0();
            }
        }, 0L);
        this.mAnimationFrameCallback = new Choreographer.FrameCallback() { // from class: com.android.server.wm.WindowAnimator$$ExternalSyntheticLambda1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                WindowAnimator.this.lambda$new$1(j);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mChoreographer = Choreographer.getSfInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(long j) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAnimationFrameCallbackScheduled = false;
                animate(j);
                if (this.mNotifyWhenNoAnimation && !this.mLastRootAnimating) {
                    this.mService.mGlobalLock.notifyAll();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void ready() {
        this.mInitialized = true;
    }

    public final void animate(long j) {
        if (this.mInitialized) {
            scheduleAnimation();
            RootWindowContainer rootWindowContainer = this.mService.mRoot;
            this.mCurrentTime = j / 1000000;
            this.mBulkUpdateParams = 0;
            rootWindowContainer.mOrientationChangeComplete = true;
            if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_SHOW_TRANSACTIONS, 1984782949, 0, (String) null, (Object[]) null);
            }
            this.mService.openSurfaceTransaction();
            try {
                rootWindowContainer.handleCompleteDeferredRemoval();
                AccessibilityController accessibilityController = this.mService.mAccessibilityController;
                int childCount = rootWindowContainer.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    DisplayContent displayContent = (DisplayContent) rootWindowContainer.getChildAt(i);
                    displayContent.updateWindowsForAnimator();
                    displayContent.prepareSurfaces();
                }
                for (int i2 = 0; i2 < childCount; i2++) {
                    DisplayContent displayContent2 = (DisplayContent) rootWindowContainer.getChildAt(i2);
                    displayContent2.checkAppWindowsReadyToShow();
                    if (accessibilityController.hasCallbacks()) {
                        accessibilityController.drawMagnifiedRegionBorderIfNeeded(displayContent2.mDisplayId);
                    }
                }
                cancelAnimation();
                Watermark watermark = this.mService.mWatermark;
                if (watermark != null) {
                    watermark.drawIfNeeded();
                }
            } catch (RuntimeException e) {
                Slog.wtf(StartingSurfaceController.TAG, "Unhandled exception in Window Manager", e);
            }
            boolean hasPendingLayoutChanges = rootWindowContainer.hasPendingLayoutChanges(this);
            boolean z = (this.mBulkUpdateParams != 0 || rootWindowContainer.mOrientationChangeComplete) && rootWindowContainer.copyAnimToLayoutParams();
            if (hasPendingLayoutChanges || z) {
                this.mService.mWindowPlacerLocked.requestTraversal();
            }
            boolean isAnimating = rootWindowContainer.isAnimating(5, -1);
            if (isAnimating && !this.mLastRootAnimating) {
                Trace.asyncTraceBegin(32L, "animating", 0);
            }
            if (!isAnimating && this.mLastRootAnimating) {
                this.mService.mWindowPlacerLocked.requestTraversal();
                Trace.asyncTraceEnd(32L, "animating", 0);
            }
            this.mLastRootAnimating = isAnimating;
            boolean isAnimating2 = rootWindowContainer.isAnimating(5, 11);
            if (isAnimating2 && !this.mRunningExpensiveAnimations) {
                this.mService.mSnapshotController.setPause(true);
                this.mTransaction.setEarlyWakeupStart();
            } else if (!isAnimating2 && this.mRunningExpensiveAnimations) {
                this.mService.mSnapshotController.setPause(false);
                this.mTransaction.setEarlyWakeupEnd();
            }
            this.mRunningExpensiveAnimations = isAnimating2;
            SurfaceControl.mergeToGlobalTransaction(this.mTransaction);
            this.mService.closeSurfaceTransaction("WindowAnimator");
            if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_SHOW_TRANSACTIONS, -545190927, 0, (String) null, (Object[]) null);
            }
            this.mService.mAtmService.mTaskOrganizerController.dispatchPendingEvents();
            executeAfterPrepareSurfacesRunnables();
        }
    }

    public static String bulkUpdateParamsToString(int i) {
        StringBuilder sb = new StringBuilder(128);
        if ((i & 1) != 0) {
            sb.append(" UPDATE_ROTATION");
        }
        if ((i & 2) != 0) {
            sb.append(" SET_WALLPAPER_ACTION_PENDING");
        }
        return sb.toString();
    }

    public void dumpLocked(PrintWriter printWriter, String str, boolean z) {
        String str2 = "  " + str;
        for (int i = 0; i < this.mService.mRoot.getChildCount(); i++) {
            DisplayContent displayContent = (DisplayContent) this.mService.mRoot.getChildAt(i);
            printWriter.print(str);
            printWriter.print(displayContent);
            printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
            displayContent.dumpWindowAnimators(printWriter, str2);
            printWriter.println();
        }
        printWriter.println();
        if (z) {
            printWriter.print(str);
            printWriter.print("mCurrentTime=");
            printWriter.println(TimeUtils.formatUptime(this.mCurrentTime));
        }
        if (this.mBulkUpdateParams != 0) {
            printWriter.print(str);
            printWriter.print("mBulkUpdateParams=0x");
            printWriter.print(Integer.toHexString(this.mBulkUpdateParams));
            printWriter.println(bulkUpdateParamsToString(this.mBulkUpdateParams));
        }
    }

    public void scheduleAnimation() {
        if (this.mAnimationFrameCallbackScheduled) {
            return;
        }
        this.mAnimationFrameCallbackScheduled = true;
        this.mChoreographer.postFrameCallback(this.mAnimationFrameCallback);
    }

    public final void cancelAnimation() {
        if (this.mAnimationFrameCallbackScheduled) {
            this.mAnimationFrameCallbackScheduled = false;
            this.mChoreographer.removeFrameCallback(this.mAnimationFrameCallback);
        }
    }

    public boolean isAnimationScheduled() {
        return this.mAnimationFrameCallbackScheduled;
    }

    public Choreographer getChoreographer() {
        return this.mChoreographer;
    }

    public void addAfterPrepareSurfacesRunnable(Runnable runnable) {
        if (this.mInExecuteAfterPrepareSurfacesRunnables) {
            runnable.run();
        } else {
            this.mAfterPrepareSurfacesRunnables.add(runnable);
            scheduleAnimation();
        }
    }

    public void executeAfterPrepareSurfacesRunnables() {
        if (this.mInExecuteAfterPrepareSurfacesRunnables) {
            return;
        }
        this.mInExecuteAfterPrepareSurfacesRunnables = true;
        int size = this.mAfterPrepareSurfacesRunnables.size();
        for (int i = 0; i < size; i++) {
            ((Runnable) this.mAfterPrepareSurfacesRunnables.get(i)).run();
        }
        this.mAfterPrepareSurfacesRunnables.clear();
        this.mInExecuteAfterPrepareSurfacesRunnables = false;
    }
}
