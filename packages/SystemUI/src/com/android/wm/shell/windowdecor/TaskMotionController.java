package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.TypedValue;
import android.view.SurfaceControl;
import android.view.View;
import android.window.WindowContainerTransaction;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.TaskMotionAnimator;
import com.android.wm.shell.windowdecor.TaskMotionController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes3.dex */
public class TaskMotionController {
    public final ShellExecutor mAnimExecutor;
    public final DisplayController mDisplayController;
    public PhysicsAnimator.FlingConfig mFlingConfigX;
    public PhysicsAnimator.FlingConfig mFlingConfigY;
    public FreeformCaptionTouchState mFreeformCaptionTouchState;
    public final FreeformStashState mFreeformStashState;
    public final Handler mHandler;
    public ActivityManager.RunningTaskInfo mLastTaskInfo;
    public final int mMinVisibleWidth;
    public TaskMotionController$$ExternalSyntheticLambda6 mResizeFreeformUpdateListener;
    public final int mScaledFreeformHeight;
    public final int mScreenEdgeInset;
    public PhysicsAnimator.FlingConfig mStashConfigX;
    public final int mStashMoveThreshold;
    public TaskMotionInfo mTaskMotionInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final SurfaceControl mTaskSurface;
    public PhysicsAnimator mTemporaryBoundsPhysicsAnimator;
    public final DesktopModeWindowDecoration mWindowDecoration;
    public final Rect mTmpRect = new Rect();
    public final Rect mTmpRect2 = new Rect();
    public final PhysicsAnimator.SpringConfig mSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 0.7f);
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public boolean mAllowTouches = true;
    public boolean mFlingCanceled = false;
    public final Rect mLastReportedTaskBounds = new Rect();
    public final Rect mTargetBounds = new Rect();
    public boolean mCanceled = false;

    public class TaskMotionInfo {
        public final Rect mDisplayBounds;
        public final Rect mMaxBounds;
        public final ArrayMap mMotionAnimators;
        public final Rect mSafeBounds;
        public final Rect mStableBounds;
        public final ActivityManager.RunningTaskInfo mTaskInfo;

        public TaskMotionInfo(TaskMotionController taskMotionController, ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, Rect rect2) {
            Rect rect3 = new Rect();
            this.mMaxBounds = rect3;
            Rect rect4 = new Rect();
            this.mStableBounds = rect4;
            Rect rect5 = new Rect();
            this.mDisplayBounds = rect5;
            Rect rect6 = new Rect();
            this.mSafeBounds = rect6;
            this.mMotionAnimators = new ArrayMap();
            this.mTaskInfo = runningTaskInfo;
            rect5.set(rect);
            rect4.set(rect2);
            Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
            int iWidth = bounds.width();
            int iHeight = bounds.height();
            int i = rect4.left;
            int i2 = taskMotionController.mMinVisibleWidth;
            rect3.set((i + i2) - iWidth, rect4.top, (rect4.right - i2) + iWidth, (rect4.bottom - i2) + iHeight);
            rect6.set(rect4);
            int i3 = rect6.left;
            int i4 = taskMotionController.mScreenEdgeInset;
            rect6.left = i3 + i4;
            rect6.right -= i4;
            rect6.top += i4;
            rect6.bottom = rect3.bottom - (i4 * 2);
        }

        public final void clearAnimator() {
            for (int i = 0; i <= 1; i++) {
                TaskMotionAnimator taskMotionAnimator = (TaskMotionAnimator) this.mMotionAnimators.get(Integer.valueOf(i));
                if (taskMotionAnimator != null) {
                    taskMotionAnimator.mAnimation.cancel();
                }
            }
            this.mMotionAnimators.clear();
        }

        public final boolean isAnimating(int i) {
            TaskMotionAnimator taskMotionAnimator = (TaskMotionAnimator) this.mMotionAnimators.get(Integer.valueOf(i));
            return taskMotionAnimator != null && taskMotionAnimator.mAnimation.isAnimating();
        }
    }

    public TaskMotionController(DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, DesktopModeWindowDecoration desktopModeWindowDecoration, ShellExecutor shellExecutor, Handler handler) {
        this.mDisplayController = displayController;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mTaskSurface = desktopModeWindowDecoration.mTaskSurface;
        this.mWindowDecoration = desktopModeWindowDecoration;
        this.mAnimExecutor = shellExecutor;
        this.mHandler = handler;
        this.mFreeformStashState = desktopModeWindowDecoration.mFreeformStashState;
        Resources resources = desktopModeWindowDecoration.mContext.getResources();
        this.mScreenEdgeInset = (int) TypedValue.applyDimension(1, 13, resources.getDisplayMetrics());
        if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
            this.mMinVisibleWidth = (int) TypedValue.applyDimension(1, 32, resources.getDisplayMetrics());
            this.mScaledFreeformHeight = (int) TypedValue.applyDimension(1, 220, resources.getDisplayMetrics());
            this.mStashMoveThreshold = (int) TypedValue.applyDimension(1, 10, resources.getDisplayMetrics());
        }
    }

    public final void addTaskToMotionInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(runningTaskInfo.displayId);
        if (displayLayout != null) {
            Rect rect = this.mTmpRect;
            Rect rect2 = this.mTmpRect2;
            displayLayout.getDisplayBounds(rect);
            displayLayout.getStableBounds(rect2, false);
            synchronized (this) {
                this.mTaskMotionInfo = new TaskMotionInfo(this, runningTaskInfo, rect, rect2);
            }
        }
    }

    public final void cancelBoundsAnimator(Rect rect, String str) {
        synchronized (this) {
            try {
                if (isBoundsAnimating()) {
                    Slog.d("TaskMotionController", "Bounds Animator canceled by ".concat(str));
                    this.mTemporaryBoundsPhysicsAnimator.cancel();
                    if (rect != null) {
                        rect.set(this.mTargetBounds);
                    }
                    this.mAllowTouches = true;
                    this.mCanceled = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0064 A[Catch: all -> 0x0066, DONT_GENERATE, TryCatch #0 {all -> 0x0066, blocks: (B:5:0x0004, B:8:0x000c, B:15:0x001f, B:17:0x0026, B:19:0x002a, B:42:0x0064, B:22:0x0033, B:24:0x003b, B:27:0x0042, B:32:0x004b, B:34:0x0052, B:37:0x0058, B:46:0x0069, B:48:0x007c, B:50:0x007e, B:52:0x0086, B:54:0x008c, B:56:0x0092, B:58:0x0094, B:60:0x009e, B:62:0x00a4, B:64:0x00bf, B:72:0x00cd, B:70:0x00c9, B:74:0x00cf, B:11:0x0016, B:76:0x00d1, B:78:0x00d3), top: B:82:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int computeStashState(Rect rect, Rect rect2, boolean z) {
        int i;
        int i2;
        boolean z2;
        synchronized (this) {
            if (rect2 != null) {
                try {
                    if (!rect2.isEmpty()) {
                        boolean z3 = true;
                        if (rect2.left <= rect.left) {
                            i = 1;
                        } else {
                            if (rect2.right < rect.right) {
                                return 0;
                            }
                            i = 2;
                        }
                        if (z) {
                            FreeformStashState freeformStashState = this.mFreeformStashState;
                            if (freeformStashState.mAnimType == -1 || !freeformStashState.mAnimating || freeformStashState.mScale == 1.0f) {
                                DesktopModeWindowDecoration desktopModeWindowDecoration = this.mWindowDecoration;
                                if ((!desktopModeWindowDecoration.isDecorHandleState() || desktopModeWindowDecoration.getHandleRootView() != null) && i == (i2 = freeformStashState.mStashType) && (i2 == 1 || i2 == 2)) {
                                    int i3 = this.mMinVisibleWidth + this.mStashMoveThreshold;
                                    if ((i2 == 1 && rect2.right > i3) || (i2 == 2 && rect2.left < rect.right - i3)) {
                                        z2 = true;
                                    }
                                    if (z2) {
                                        return 0;
                                    }
                                }
                            }
                            z2 = false;
                            if (z2) {
                            }
                        }
                        Rect rect3 = this.mTmpRect;
                        rect3.set(rect2);
                        rect3.intersect(rect);
                        if (rect3.width() <= rect2.width() / 2) {
                            return i;
                        }
                        DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.mWindowDecoration;
                        if (desktopModeWindowDecoration2.getHandleRootView() == null && desktopModeWindowDecoration2.isDecorHandleState() && desktopModeWindowDecoration2.mTaskInfo.isVisible) {
                            return 0;
                        }
                        DesktopModeWindowDecoration desktopModeWindowDecoration3 = this.mWindowDecoration;
                        int i4 = DesktopModeWindowDecoration.asMultiTaskingAppHandle(desktopModeWindowDecoration3.mWindowDecorViewHolder) != null ? desktopModeWindowDecoration3.mResult.mCaptionWidth : 0;
                        this.mTmpRect.set(rect2);
                        this.mTmpRect.left = ((rect2.width() - i4) / 2) + rect2.left;
                        Rect rect4 = this.mTmpRect;
                        int i5 = rect4.left;
                        int i6 = i4 + i5;
                        rect4.right = i6;
                        if (i != 1 || i5 < rect.left) {
                            if (i != 2) {
                                z3 = false;
                            }
                            if (!z3 || i6 > rect.right) {
                                return i;
                            }
                        }
                        return 0;
                    }
                } finally {
                }
            }
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleFreeformMotion(final Rect rect, Rect rect2, PointF pointF, Rect rect3, Boolean bool, float f, float f2, boolean z) {
        Rect rect4;
        boolean z2;
        int iWidth;
        int iWidth2;
        TaskMotionInfo taskMotionInfo = this.mTaskMotionInfo;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        FreeformStashState freeformStashState = this.mFreeformStashState;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.mWindowDecoration;
        boolean z3 = true;
        if (taskMotionInfo != null) {
            int i = desktopModeWindowDecoration.mTaskInfo.displayId;
            DesktopStateImpl.Companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                float f3 = this.mFreeformCaptionTouchState.mVelocity.x;
                boolean z4 = f3 < -12000.0f;
                boolean z5 = f3 > 12000.0f;
                boolean zIsStashed = freeformStashState.isStashed();
                if (zIsStashed) {
                    iWidth2 = this.mMinVisibleWidth + this.mStashMoveThreshold;
                } else {
                    int freeformCaptionType = shellTaskOrganizer.getFreeformCaptionType(desktopModeWindowDecoration.mTaskInfo);
                    if (freeformCaptionType == 0) {
                        View handleRootView = desktopModeWindowDecoration.getHandleRootView();
                        iWidth2 = handleRootView != null ? (rect.width() - handleRootView.getWidth()) / 2 : 0;
                    } else if (freeformCaptionType == 1) {
                        iWidth2 = rect.width() / 2;
                    }
                }
                boolean z6 = !zIsStashed ? rect.left >= this.mTaskMotionInfo.mDisplayBounds.left - iWidth2 : rect.right >= this.mTaskMotionInfo.mDisplayBounds.left + iWidth2;
                boolean z7 = !zIsStashed ? rect.right <= this.mTaskMotionInfo.mDisplayBounds.right + iWidth2 : rect.left <= this.mTaskMotionInfo.mDisplayBounds.right - iWidth2;
                int i2 = freeformStashState.mStashType;
                boolean z8 = (z4 && i2 != 2) || (z5 && i2 != 1);
                boolean z9 = z6 || z7;
                if (z8 || z9) {
                    this.mAllowTouches = false;
                    freeformStashState.mLastFreeformBoundsBeforeStash.set(rect2);
                    setStashDim(null, true);
                    final int i3 = 0;
                    moveToTarget(rect, new Runnable(this) { // from class: com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda4
                        public final /* synthetic */ TaskMotionController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    TaskMotionController taskMotionController = this.f$0;
                                    Rect rect5 = rect;
                                    Rect rect6 = taskMotionController.mTmpRect2;
                                    DesktopModeWindowDecoration desktopModeWindowDecoration2 = taskMotionController.mWindowDecoration;
                                    DisplayLayout displayLayout = taskMotionController.mDisplayController.getDisplayLayout(desktopModeWindowDecoration2.mDisplay.getDisplayId());
                                    if (displayLayout != null) {
                                        displayLayout.getStableBounds(rect6, false);
                                        taskMotionController.mFreeformStashState.mFreeformStashYFraction = rect5.top / rect6.height();
                                    }
                                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                    windowContainerTransaction.setChangeFreeformStashMode(desktopModeWindowDecoration2.mTaskInfo.token, 2);
                                    windowContainerTransaction.setChangeFreeformStashScale(desktopModeWindowDecoration2.mTaskInfo.token, taskMotionController.mScaledFreeformHeight / rect5.height());
                                    windowContainerTransaction.setBounds(desktopModeWindowDecoration2.mTaskInfo.token, rect5);
                                    taskMotionController.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                                    break;
                                default:
                                    TaskMotionController taskMotionController2 = this.f$0;
                                    Rect rect7 = rect;
                                    DesktopModeWindowDecoration desktopModeWindowDecoration3 = taskMotionController2.mWindowDecoration;
                                    int i4 = desktopModeWindowDecoration3.mTaskInfo.displayId;
                                    ShellTaskOrganizer shellTaskOrganizer2 = taskMotionController2.mTaskOrganizer;
                                    boolean zIsTargetTaskImeShowing = shellTaskOrganizer2.isTargetTaskImeShowing(i4);
                                    if (taskMotionController2.mFlingCanceled && !zIsTargetTaskImeShowing) {
                                        taskMotionController2.mFlingCanceled = false;
                                    }
                                    if (!taskMotionController2.mFlingCanceled) {
                                        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                        windowContainerTransaction2.setBounds(desktopModeWindowDecoration3.mTaskInfo.token, rect7);
                                        shellTaskOrganizer2.applyTransaction(windowContainerTransaction2);
                                    }
                                    taskMotionController2.mFlingCanceled = false;
                                    MultiWindowManager.getInstance().saveFreeformBounds(desktopModeWindowDecoration3.mTaskInfo.taskId);
                                    break;
                            }
                        }
                    }, true);
                    return;
                }
            }
        }
        if (z) {
            FreeformCaptionTouchState freeformCaptionTouchState = this.mFreeformCaptionTouchState;
            if (this.mTaskMotionInfo != null) {
                PointF pointF2 = new PointF(freeformCaptionTouchState.mVelocity);
                float f4 = pointF2.x;
                if (f4 != 0.0f || pointF2.y != 0.0f) {
                    float fAbs = Math.abs(f4);
                    float f5 = freeformCaptionTouchState.mMinimumFlingVelocity;
                    if (fAbs > f5 || Math.abs(pointF2.y) > f5) {
                        float f6 = pointF2.x;
                        boolean z10 = f6 < 0.0f;
                        if ((!z10 || rect2.left >= this.mTaskMotionInfo.mStableBounds.left) && ((z10 || rect2.right <= this.mTaskMotionInfo.mStableBounds.right) && ((Math.abs(f6) >= 700.0f || ((!z10 || rect.left >= this.mTaskMotionInfo.mStableBounds.left - 30) && (z10 || rect.right <= this.mTaskMotionInfo.mStableBounds.right + 30))) && (pointF2.y <= 0.0f || !shellTaskOrganizer.isTargetTaskImeShowing(desktopModeWindowDecoration.mTaskInfo.displayId))))) {
                            this.mAllowTouches = false;
                            setStashDim(null, false);
                            final int i4 = 1;
                            moveToTarget(rect, new Runnable(this) { // from class: com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda4
                                public final /* synthetic */ TaskMotionController f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i4) {
                                        case 0:
                                            TaskMotionController taskMotionController = this.f$0;
                                            Rect rect5 = rect;
                                            Rect rect6 = taskMotionController.mTmpRect2;
                                            DesktopModeWindowDecoration desktopModeWindowDecoration2 = taskMotionController.mWindowDecoration;
                                            DisplayLayout displayLayout = taskMotionController.mDisplayController.getDisplayLayout(desktopModeWindowDecoration2.mDisplay.getDisplayId());
                                            if (displayLayout != null) {
                                                displayLayout.getStableBounds(rect6, false);
                                                taskMotionController.mFreeformStashState.mFreeformStashYFraction = rect5.top / rect6.height();
                                            }
                                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                            windowContainerTransaction.setChangeFreeformStashMode(desktopModeWindowDecoration2.mTaskInfo.token, 2);
                                            windowContainerTransaction.setChangeFreeformStashScale(desktopModeWindowDecoration2.mTaskInfo.token, taskMotionController.mScaledFreeformHeight / rect5.height());
                                            windowContainerTransaction.setBounds(desktopModeWindowDecoration2.mTaskInfo.token, rect5);
                                            taskMotionController.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                                            break;
                                        default:
                                            TaskMotionController taskMotionController2 = this.f$0;
                                            Rect rect7 = rect;
                                            DesktopModeWindowDecoration desktopModeWindowDecoration3 = taskMotionController2.mWindowDecoration;
                                            int i42 = desktopModeWindowDecoration3.mTaskInfo.displayId;
                                            ShellTaskOrganizer shellTaskOrganizer2 = taskMotionController2.mTaskOrganizer;
                                            boolean zIsTargetTaskImeShowing = shellTaskOrganizer2.isTargetTaskImeShowing(i42);
                                            if (taskMotionController2.mFlingCanceled && !zIsTargetTaskImeShowing) {
                                                taskMotionController2.mFlingCanceled = false;
                                            }
                                            if (!taskMotionController2.mFlingCanceled) {
                                                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                                windowContainerTransaction2.setBounds(desktopModeWindowDecoration3.mTaskInfo.token, rect7);
                                                shellTaskOrganizer2.applyTransaction(windowContainerTransaction2);
                                            }
                                            taskMotionController2.mFlingCanceled = false;
                                            MultiWindowManager.getInstance().saveFreeformBounds(desktopModeWindowDecoration3.mTaskInfo.taskId);
                                            break;
                                    }
                                }
                            }, false);
                            return;
                        }
                    }
                }
            }
        }
        View handleRootView2 = desktopModeWindowDecoration.getHandleRootView();
        if (this.mTaskMotionInfo == null || (desktopModeWindowDecoration.isDecorHandleState() && handleRootView2 == null)) {
            rect4 = rect;
        } else {
            int iWidth3 = (rect.width() - (DesktopModeWindowDecoration.asMultiTaskingAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder) != null ? desktopModeWindowDecoration.mResult.mCaptionWidth : 0)) / 2;
            int i5 = rect.left;
            int i6 = i5 + iWidth3;
            Rect rect5 = this.mTaskMotionInfo.mDisplayBounds;
            int i7 = rect5.left;
            if (i6 < i7 || rect.right - iWidth3 > rect5.right) {
                if (i7 - i5 > iWidth3) {
                    iWidth = i7 - iWidth3;
                } else {
                    int i8 = rect.right;
                    int i9 = rect5.right;
                    iWidth = i8 - i9 > iWidth3 ? (i9 - rect.width()) + iWidth3 : 0;
                }
                rect4 = new Rect(iWidth, rect.top, rect.width() + iWidth, rect.bottom);
            }
        }
        if (rect4 != rect) {
            scheduleAnimateRestore(rect, rect4, true);
            z2 = true;
        } else {
            z2 = false;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (z2) {
            return;
        }
        if (freeformStashState.isStashed()) {
            synchronized (this) {
                try {
                    TaskMotionInfo taskMotionInfo2 = this.mTaskMotionInfo;
                    if (taskMotionInfo2 == null || !taskMotionInfo2.isAnimating(2)) {
                        z3 = false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (!z3) {
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                transaction.setMatrix(desktopModeWindowDecoration.mTaskSurface, 1.0f, 0.0f, 0.0f, 1.0f);
                transaction.apply();
                freeformStashState.setStashed(0);
                setStashDim(windowContainerTransaction, false);
                windowContainerTransaction.setChangeFreeformStashScale(desktopModeWindowDecoration.mTaskInfo.token, 1.0f);
                windowContainerTransaction.setBounds(desktopModeWindowDecoration.mTaskInfo.token, rect);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
        }
        DragPositioningCallbackUtility.updateTaskBounds(rect3, bool, rect, rect2, desktopModeWindowDecoration.getOutlineCaptionHeight(), pointF, f, f2);
    }

    public final boolean isBoundsAnimating() {
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        return physicsAnimator != null && physicsAnimator.isRunning();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064  */
    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda6] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void moveToTarget(final Rect rect, Runnable runnable, boolean z) {
        boolean z2;
        boolean z3;
        float f;
        boolean z4;
        String str;
        TaskMotionAnimator taskMotionAnimator;
        synchronized (this) {
            try {
                if (this.mTaskMotionInfo == null) {
                    return;
                }
                this.mCanceled = false;
                this.mTargetBounds.set(rect);
                rebuildFlingConfigs(rect);
                int iWidth = rect.width();
                int iHeight = rect.height();
                PointF pointF = this.mFreeformCaptionTouchState.mVelocity;
                float fEstimateFlingEndValue = PhysicsAnimator.estimateFlingEndValue(rect.left, pointF.x, this.mStashConfigX);
                if (z) {
                    Rect rect2 = this.mTaskMotionInfo.mDisplayBounds;
                    z2 = fEstimateFlingEndValue < ((float) rect2.left) && ((float) iWidth) + fEstimateFlingEndValue < ((float) rect2.right);
                }
                if (z) {
                    Rect rect3 = this.mTaskMotionInfo.mDisplayBounds;
                    z3 = fEstimateFlingEndValue > ((float) rect3.left) && ((float) iWidth) + fEstimateFlingEndValue > ((float) rect3.right);
                }
                float f2 = pointF.x;
                if (z2 && f2 == 0.0f) {
                    Slog.w("TaskMotionController", "moveToTarget: make velocity as negative");
                    f2 = -1.0f;
                }
                if (z2) {
                    if (f2 > -1.0f || f2 <= -4000.0f) {
                        f = -7000.0f;
                        if (f2 < -7000.0f) {
                            f2 = f;
                        }
                    } else {
                        f2 -= 4000.0f;
                    }
                } else if (z3) {
                    if (f2 < 0.0f || f2 >= 4000.0f) {
                        f = 7000.0f;
                        if (f2 > 7000.0f) {
                            f2 = f;
                        }
                    } else {
                        f2 += 4000.0f;
                    }
                }
                float f3 = pointF.y;
                PhysicsAnimator.Companion.getClass();
                PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(rect);
                this.mTemporaryBoundsPhysicsAnimator = companion;
                companion.spring(FloatProperties.RECT_WIDTH, iWidth, 0.0f, this.mSpringConfig);
                companion.spring(FloatProperties.RECT_HEIGHT, iHeight, 0.0f, this.mSpringConfig);
                companion.flingThenSpring(FloatProperties.RECT_X, f2, z ? this.mStashConfigX : this.mFlingConfigX, this.mSpringConfig, z);
                companion.flingThenSpring(FloatProperties.RECT_Y, f3, this.mFlingConfigY, this.mSpringConfig, false);
                boolean zIsStashed = this.mFreeformStashState.isStashed();
                final boolean zIsLeftStashed = (!this.mTaskMotionInfo.isAnimating(1) || (taskMotionAnimator = (TaskMotionAnimator) this.mTaskMotionInfo.mMotionAnimators.get(1)) == null) ? false : taskMotionAnimator.mFreeformStashState.isLeftStashed();
                if (zIsStashed != z) {
                    z4 = z2;
                    scheduleChangedScaleAnimation(zIsStashed, rect, pointF, z4, z3, fEstimateFlingEndValue);
                } else {
                    z4 = z2;
                }
                this.mResizeFreeformUpdateListener = new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda6
                    /* JADX WARN: Removed duplicated region for block: B:16:0x002a  */
                    @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onAnimationUpdateForProperty(Object obj) {
                        float fWidth;
                        boolean z5;
                        Rect rect4 = rect;
                        Rect rect5 = (Rect) obj;
                        TaskMotionController taskMotionController = this.f$0;
                        if (taskMotionController.mCanceled) {
                            return;
                        }
                        taskMotionController.mTargetBounds.set(rect5);
                        FreeformStashState freeformStashState = taskMotionController.mFreeformStashState;
                        if (!freeformStashState.isLeftStashed()) {
                            if (zIsLeftStashed) {
                                synchronized (taskMotionController) {
                                    try {
                                        TaskMotionController.TaskMotionInfo taskMotionInfo = taskMotionController.mTaskMotionInfo;
                                        if (taskMotionInfo != null) {
                                            z5 = true;
                                            if (!taskMotionInfo.isAnimating(1)) {
                                                z5 = false;
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                fWidth = z5 ? rect4.width() - (rect4.width() * freeformStashState.mScale) : 0.0f;
                            }
                        }
                        SurfaceControl surfaceControl = taskMotionController.mTaskSurface;
                        if (surfaceControl == null || !surfaceControl.isValid() || taskMotionController.mTaskMotionInfo == null) {
                            return;
                        }
                        taskMotionController.mTransaction.setPosition(taskMotionController.mTaskSurface, rect4.left + fWidth, rect4.top);
                        taskMotionController.mTransaction.apply();
                    }
                };
                if (CoreRune.MW_SA_LOGGING && z) {
                    String str2 = z4 ? SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT : SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT;
                    ComponentName componentName = this.mTaskMotionInfo.mTaskInfo.topActivity;
                    if (componentName != null) {
                        str = "[Detail] : " + str2 + " [Position]  " + componentName.getPackageName();
                    } else {
                        str = null;
                    }
                    CoreSaLogger.logForAdvanced("2010", str);
                }
                if (!this.mTemporaryBoundsPhysicsAnimator.isRunning()) {
                    PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
                    physicsAnimator.updateListeners.add(this.mResizeFreeformUpdateListener);
                    physicsAnimator.withEndActions(new TaskMotionController$$ExternalSyntheticLambda3(this, 1), runnable);
                }
                this.mTemporaryBoundsPhysicsAnimator.start();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void postAnimationFinished(int i, ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, WindowContainerTransaction windowContainerTransaction) {
        windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
        if (this.mFreeformStashState.isStashed()) {
            this.mHandler.post(new TaskMotionController$$ExternalSyntheticLambda3(this, 0));
        }
        windowContainerTransaction.setChangeFreeformStashScale(runningTaskInfo.token, 1.0f);
        if (i == 1) {
            windowContainerTransaction.requestForceTaskInfoChange(runningTaskInfo.token);
        }
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        removeMotionAnimator(i);
        this.mLastReportedTaskBounds.set(rect);
        this.mLastTaskInfo = runningTaskInfo;
    }

    public final void rebuildFlingConfigs(Rect rect) {
        int iWidth = rect.width();
        int iHeight = rect.height();
        Rect rect2 = this.mTaskMotionInfo.mSafeBounds;
        this.mFlingConfigX = new PhysicsAnimator.FlingConfig(1.9f, rect2.left, rect2.right - iWidth);
        Rect rect3 = this.mTaskMotionInfo.mSafeBounds;
        this.mFlingConfigY = new PhysicsAnimator.FlingConfig(1.9f, rect3.top, rect3.bottom - iHeight);
        Rect rect4 = this.mTaskMotionInfo.mMaxBounds;
        this.mStashConfigX = new PhysicsAnimator.FlingConfig(1.9f, rect4.left, rect4.right - iWidth);
    }

    public final void removeMotionAnimator(int i) {
        synchronized (this) {
            TaskMotionInfo taskMotionInfo = this.mTaskMotionInfo;
            if (taskMotionInfo != null) {
                taskMotionInfo.mMotionAnimators.remove(Integer.valueOf(i));
            }
        }
    }

    public final void scheduleAnimateRestore(Rect rect, Rect rect2, boolean z) {
        SurfaceControl surfaceControl = this.mTaskSurface;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        final ActivityManager.RunningTaskInfo runningTaskInfo = this.mWindowDecoration.mTaskInfo;
        FreeformStashState freeformStashState = this.mFreeformStashState;
        if (!freeformStashState.isStashed()) {
            Slog.d("TaskMotionController", "scheduleAnimateRestore fail: taskInfo=" + runningTaskInfo + " startBounds=" + rect + " endBounds=" + rect2);
            return;
        }
        TaskMotionInfo taskMotionInfo = this.mTaskMotionInfo;
        if (taskMotionInfo == null) {
            addTaskToMotionInfo(runningTaskInfo);
        } else {
            taskMotionInfo.clearAnimator();
        }
        if (rect2.isEmpty()) {
            ActivityInfo activityInfo = this.mTaskMotionInfo.mTaskInfo.topActivityInfo;
            ActivityInfo.WindowLayout windowLayoutRecalculateWindowLayout = activityInfo != null ? activityInfo.windowLayout : null;
            if (windowLayoutRecalculateWindowLayout != null) {
                float f = runningTaskInfo.configuration.densityDpi;
                float initialDensity = SemWindowManager.getInstance().getInitialDensity();
                ActivityInfo activityInfo2 = runningTaskInfo.topActivityInfo;
                windowLayoutRecalculateWindowLayout = MultiWindowUtils.recalculateWindowLayout(f, initialDensity, windowLayoutRecalculateWindowLayout, activityInfo2 != null ? activityInfo2.packageName : null);
            }
            TaskMotionInfo taskMotionInfo2 = this.mTaskMotionInfo;
            MultiWindowUtils.getDefaultFreeformBounds(taskMotionInfo2.mDisplayBounds, taskMotionInfo2.mStableBounds, windowLayoutRecalculateWindowLayout, rect2);
            if (this.mTaskMotionInfo.mStableBounds.width() < rect2.width()) {
                rect2.right = this.mTaskMotionInfo.mStableBounds.width();
            }
            if (this.mTaskMotionInfo.mStableBounds.height() < rect2.height()) {
                rect2.bottom = this.mTaskMotionInfo.mStableBounds.height();
            }
        }
        int iComputeStashState = computeStashState(this.mTaskMotionInfo.mStableBounds, rect2, z);
        if (iComputeStashState != 0) {
            Slog.d("TaskMotionController", "scheduleAnimateRestore adjust restore bounds: taskInfo=" + runningTaskInfo + " startBounds=" + rect + " endBounds=" + rect2);
            rect2.offsetTo((this.mTaskMotionInfo.mDisplayBounds.width() - rect2.width()) / 2, (this.mTaskMotionInfo.mDisplayBounds.height() - rect2.height()) / 2);
        }
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        if (bounds.width() != rect2.width() || bounds.height() != rect2.height()) {
            rect2.right = bounds.width() + rect2.left;
            rect2.bottom = bounds.height() + rect2.top;
        }
        if (iComputeStashState == 0) {
            if (this.mTaskMotionInfo.mMaxBounds.top > rect2.top) {
                int iHeight = rect2.height();
                int i = this.mTaskMotionInfo.mMaxBounds.top;
                rect2.top = i;
                rect2.bottom = i + iHeight;
            }
            if (this.mTaskMotionInfo.mMaxBounds.bottom < rect2.bottom) {
                int iHeight2 = rect2.height();
                int i2 = this.mTaskMotionInfo.mMaxBounds.bottom;
                rect2.bottom = i2;
                rect2.top = i2 - iHeight2;
            }
        }
        freeformStashState.mAnimType = 2;
        freeformStashState.mAnimating = true;
        TaskMotionAnimValue taskMotionAnimValue = new TaskMotionAnimValue(2, freeformStashState, this.mTaskSurface, rect, rect2, 1.0f);
        TaskMotionInfo taskMotionInfo3 = this.mTaskMotionInfo;
        TaskMotionAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback = new TaskMotionAnimator.OnAnimationFinishedCallback() { // from class: com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(Rect rect3) {
                ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                TaskMotionController taskMotionController = this.f$0;
                FreeformStashState freeformStashState2 = taskMotionController.mFreeformStashState;
                freeformStashState2.mAnimating = false;
                freeformStashState2.setStashed(0);
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                taskMotionController.setStashDim(windowContainerTransaction, false);
                taskMotionController.postAnimationFinished(2, runningTaskInfo2, rect3, windowContainerTransaction);
                freeformStashState2.mLastFreeformBoundsBeforeStash.set(rect3);
            }
        };
        taskMotionInfo3.getClass();
        TaskMotionAnimator taskMotionAnimator = new TaskMotionAnimator(taskMotionAnimValue, onAnimationFinishedCallback);
        taskMotionInfo3.mMotionAnimators.put(Integer.valueOf(taskMotionAnimValue.mAnimType), taskMotionAnimator);
        this.mAnimExecutor.execute(new TaskMotionController$$ExternalSyntheticLambda3(taskMotionAnimator, 2));
    }

    public final boolean scheduleAnimateScale(final int i, Rect rect) {
        synchronized (this) {
            try {
                SurfaceControl surfaceControl = this.mTaskSurface;
                int i2 = 0;
                if ((surfaceControl == null || !surfaceControl.isValid() || this.mTaskMotionInfo == null) ? false : true) {
                    if (i == 1 || i == 0) {
                        if (this.mTaskMotionInfo.isAnimating(i)) {
                            Slog.d("TaskMotionController", "scheduleAnimateScale: animType=" + i + " failed, already animating, t #" + this.mWindowDecoration.mTaskInfo.taskId);
                            return false;
                        }
                        TaskMotionInfo taskMotionInfo = this.mTaskMotionInfo;
                        if (i == 0) {
                            i2 = 1;
                        } else if (i != 1) {
                            i2 = -1;
                        }
                        if (taskMotionInfo.isAnimating(i2)) {
                            this.mTaskMotionInfo.clearAnimator();
                        }
                        FreeformStashState freeformStashState = this.mFreeformStashState;
                        freeformStashState.mAnimType = i;
                        freeformStashState.mAnimating = true;
                        TaskMotionAnimValue taskMotionAnimValue = new TaskMotionAnimValue(i, this.mFreeformStashState, this.mTaskSurface, rect, null, i == 1 ? 1.0f : this.mScaledFreeformHeight / this.mWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds().height());
                        TaskMotionInfo taskMotionInfo2 = this.mTaskMotionInfo;
                        TaskMotionAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback = new TaskMotionAnimator.OnAnimationFinishedCallback() { // from class: com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda2
                            @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.OnAnimationFinishedCallback
                            public final void onAnimationFinished(Rect rect2) {
                                TaskMotionController taskMotionController = this.f$0;
                                FreeformStashState freeformStashState2 = taskMotionController.mFreeformStashState;
                                freeformStashState2.mAnimating = false;
                                int i3 = i;
                                taskMotionController.removeMotionAnimator(i3);
                                if (i3 != 1 || freeformStashState2.isStashed()) {
                                    return;
                                }
                                taskMotionController.postAnimationFinished(i3, taskMotionController.mWindowDecoration.mTaskInfo, rect2, new WindowContainerTransaction());
                            }
                        };
                        taskMotionInfo2.getClass();
                        TaskMotionAnimator taskMotionAnimator = new TaskMotionAnimator(taskMotionAnimValue, onAnimationFinishedCallback);
                        taskMotionInfo2.mMotionAnimators.put(Integer.valueOf(taskMotionAnimValue.mAnimType), taskMotionAnimator);
                        this.mAnimExecutor.execute(new TaskMotionController$$ExternalSyntheticLambda3(taskMotionAnimator, 2));
                        return true;
                    }
                }
                return false;
            } finally {
            }
        }
    }

    public final void scheduleChangedScaleAnimation(boolean z, Rect rect, PointF pointF, boolean z2, boolean z3, float f) {
        int i = 1;
        FreeformStashState freeformStashState = this.mFreeformStashState;
        if (z) {
            freeformStashState.setStashed(0);
            scheduleAnimateScale(1, rect);
            return;
        }
        if (!z2) {
            if (z3) {
                i = 2;
            } else {
                Slog.w("TaskMotionController", "moveToTarget: stashState=0, velocity.x= " + pointF.x + ", estimatedStashXEndValue=" + f);
                i = 0;
            }
        }
        freeformStashState.setStashed(i);
        scheduleAnimateScale(0, rect);
    }

    public final void setStashDim(WindowContainerTransaction windowContainerTransaction, boolean z) {
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.mWindowDecoration;
        ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
        if (runningTaskInfo.isVisible) {
            if (z) {
                this.mFreeformStashState.createStashDimOverlay(this.mTaskSurface, this.mDisplayController.getDisplayContext(runningTaskInfo.displayId), runningTaskInfo);
            }
            if (desktopModeWindowDecoration.mDecorationContainerSurface != null && desktopModeWindowDecoration.mResult.mRootView != null) {
                if (!z) {
                    if (desktopModeWindowDecoration.mDragResizeListener == null) {
                        desktopModeWindowDecoration.closeFreeformDimInputListener();
                    }
                    desktopModeWindowDecoration.updateDragResizeListener(desktopModeWindowDecoration.mDecorationContainerSurface, new DesktopModeWindowDecoration$$ExternalSyntheticLambda5(desktopModeWindowDecoration, !desktopModeWindowDecoration.mTaskInfo.positionInParent.equals(desktopModeWindowDecoration.mPositionInParent), desktopModeWindowDecoration.mDesktopUserRepositories.getProfile(desktopModeWindowDecoration.mTaskInfo.userId).isTaskInFullImmersiveState(desktopModeWindowDecoration.mTaskInfo.taskId), 1));
                } else if (desktopModeWindowDecoration.mFreeformStashDimInputListener == null) {
                    DragResizeInputListener dragResizeInputListener = desktopModeWindowDecoration.mDragResizeListener;
                    if (dragResizeInputListener != null) {
                        dragResizeInputListener.close();
                        desktopModeWindowDecoration.mDragResizeListener = null;
                    }
                    desktopModeWindowDecoration.mFreeformStashDimInputListener = new FreeformDimInputListener(desktopModeWindowDecoration.mContext, desktopModeWindowDecoration.mHandler, desktopModeWindowDecoration.mChoreographer, desktopModeWindowDecoration.mDisplay.getDisplayId(), desktopModeWindowDecoration.mDecorationContainerSurface, desktopModeWindowDecoration.mTaskPositioner, desktopModeWindowDecoration.mTaskInfo.taskId, null);
                }
            }
            WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction != null ? windowContainerTransaction : new WindowContainerTransaction();
            windowContainerTransaction2.setChangeFreeformStashMode(runningTaskInfo.token, z ? 2 : 1);
            if (windowContainerTransaction != null) {
                return;
            }
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
        }
    }
}
