package com.android.server.wm;

import android.R;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.IPinnedTaskListener;
import android.window.PictureInPictureSurfaceTransaction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PinnedTaskController {
    public boolean mDeferOrientationChanging;
    public Rect mDestRotatedBounds;
    public final DisplayContent mDisplayContent;
    public boolean mFreezingTaskConfig;
    public int mImeHeight;
    public boolean mIsImeShowing;
    public float mMaxAspectRatio;
    public float mMinAspectRatio;
    public IPinnedTaskListener mPinnedTaskListener;
    public PictureInPictureSurfaceTransaction mPipTransaction;
    public final WindowManagerService mService;
    public final PinnedTaskListenerDeathHandler mPinnedTaskListenerDeathHandler =
            new PinnedTaskListenerDeathHandler();
    public final PinnedTaskController$$ExternalSyntheticLambda0 mDeferOrientationTimeoutRunnable =
            new PinnedTaskController$$ExternalSyntheticLambda0(this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PinnedTaskListenerDeathHandler implements IBinder.DeathRecipient {
        public PinnedTaskListenerDeathHandler() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    PinnedTaskController.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    PinnedTaskController pinnedTaskController = PinnedTaskController.this;
                    pinnedTaskController.mPinnedTaskListener = null;
                    pinnedTaskController.mFreezingTaskConfig = false;
                    pinnedTaskController.mDeferOrientationTimeoutRunnable.run();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public PinnedTaskController(
            WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        Resources resources = windowManagerService.mContext.getResources();
        this.mMinAspectRatio = resources.getFloat(R.dimen.config_viewMinFlingVelocity);
        this.mMaxAspectRatio = resources.getFloat(R.dimen.config_viewMaxRotaryEncoderFlingVelocity);
    }

    public final void continueOrientationChange() {
        this.mDeferOrientationChanging = false;
        this.mService.mH.removeCallbacks(this.mDeferOrientationTimeoutRunnable);
        DisplayContent displayContent = this.mDisplayContent;
        WindowContainer lastOrientationSource = displayContent.getLastOrientationSource();
        if (lastOrientationSource == null || lastOrientationSource.isAppTransitioning()) {
            return;
        }
        displayContent.continueUpdateOrientationForDiffOrienLaunchingApp();
    }

    public final void deferOrientationChangeForEnteringPipFromFullScreenIfNeeded() {
        int rotationForActivityInDifferentOrientation;
        PinnedTaskController$$ExternalSyntheticLambda1
                pinnedTaskController$$ExternalSyntheticLambda1 =
                        new PinnedTaskController$$ExternalSyntheticLambda1();
        DisplayContent displayContent = this.mDisplayContent;
        ActivityRecord activity =
                displayContent.getActivity(pinnedTaskController$$ExternalSyntheticLambda1);
        if (activity == null
                || activity.hasFixedRotationTransform()
                || (rotationForActivityInDifferentOrientation =
                                displayContent.rotationForActivityInDifferentOrientation(activity))
                        == -1) {
            return;
        }
        displayContent.setFixedRotationLaunchingApp(
                rotationForActivityInDifferentOrientation, activity);
        this.mDeferOrientationChanging = true;
        WindowManagerService windowManagerService = this.mService;
        WindowManagerService.H h = windowManagerService.mH;
        PinnedTaskController$$ExternalSyntheticLambda0
                pinnedTaskController$$ExternalSyntheticLambda0 =
                        this.mDeferOrientationTimeoutRunnable;
        h.removeCallbacks(pinnedTaskController$$ExternalSyntheticLambda0);
        windowManagerService.mH.postDelayed(
                pinnedTaskController$$ExternalSyntheticLambda0,
                (int) (Math.max(1.0f, windowManagerService.getCurrentAnimatorScale()) * 1000.0f));
    }

    public final void notifyMovementBoundsChanged(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                IPinnedTaskListener iPinnedTaskListener = this.mPinnedTaskListener;
                if (iPinnedTaskListener == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                try {
                    iPinnedTaskListener.onMovementBoundsChanged(z);
                } catch (RemoteException e) {
                    Slog.e("WindowManager", "Error delivering actions changed event.", e);
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }
}
