package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.TypedValue;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.windowdecor.TaskMotionAnimator;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int width = bounds.width();
            int height = bounds.height();
            int i = rect4.left;
            int i2 = taskMotionController.mMinVisibleWidth;
            rect3.set((i + i2) - width, rect4.top, (rect4.right - i2) + width, (rect4.bottom - i2) + height);
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

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0049, code lost:
    
        if (r9 != 2) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064 A[Catch: all -> 0x0066, DONT_GENERATE, TryCatch #0 {all -> 0x0066, blocks: (B:8:0x0004, B:11:0x000c, B:15:0x001f, B:17:0x0026, B:19:0x002a, B:24:0x0064, B:26:0x0033, B:28:0x003b, B:31:0x0042, B:35:0x004b, B:37:0x0052, B:41:0x0058, B:44:0x0069, B:46:0x007c, B:48:0x007e, B:50:0x0086, B:52:0x008c, B:54:0x0092, B:56:0x0094, B:58:0x009e, B:59:0x00a4, B:61:0x00bf, B:63:0x00cd, B:68:0x00c9, B:70:0x00cf, B:74:0x0016, B:77:0x00d1, B:4:0x00d3), top: B:7:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int computeStashState(android.graphics.Rect r7, android.graphics.Rect r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.TaskMotionController.computeStashState(android.graphics.Rect, android.graphics.Rect, boolean):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:128:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleFreeformMotion(final android.graphics.Rect r18, android.graphics.Rect r19, android.graphics.PointF r20, android.graphics.Rect r21, float r22, float r23) {
        /*
            Method dump skipped, instructions count: 570
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.TaskMotionController.handleFreeformMotion(android.graphics.Rect, android.graphics.Rect, android.graphics.PointF, android.graphics.Rect, float, float):void");
    }

    public final boolean isBoundsAnimating() {
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        return physicsAnimator != null && physicsAnimator.isRunning();
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x008b, code lost:
    
        if (r5 < (-7000.0f)) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004c A[Catch: all -> 0x000b, TryCatch #0 {all -> 0x000b, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x000e, B:11:0x0033, B:13:0x003e, B:17:0x004c, B:19:0x0057, B:22:0x0063, B:26:0x006e, B:33:0x0084, B:34:0x00a3, B:36:0x00c5, B:37:0x00ca, B:39:0x00f3, B:41:0x0103, B:43:0x010e, B:44:0x0115, B:46:0x0124, B:47:0x013d, B:48:0x0142, B:51:0x00c8, B:60:0x0099), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c5 A[Catch: all -> 0x000b, TryCatch #0 {all -> 0x000b, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x000e, B:11:0x0033, B:13:0x003e, B:17:0x004c, B:19:0x0057, B:22:0x0063, B:26:0x006e, B:33:0x0084, B:34:0x00a3, B:36:0x00c5, B:37:0x00ca, B:39:0x00f3, B:41:0x0103, B:43:0x010e, B:44:0x0115, B:46:0x0124, B:47:0x013d, B:48:0x0142, B:51:0x00c8, B:60:0x0099), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00f3 A[Catch: all -> 0x000b, TryCatch #0 {all -> 0x000b, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x000e, B:11:0x0033, B:13:0x003e, B:17:0x004c, B:19:0x0057, B:22:0x0063, B:26:0x006e, B:33:0x0084, B:34:0x00a3, B:36:0x00c5, B:37:0x00ca, B:39:0x00f3, B:41:0x0103, B:43:0x010e, B:44:0x0115, B:46:0x0124, B:47:0x013d, B:48:0x0142, B:51:0x00c8, B:60:0x0099), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x010e A[Catch: all -> 0x000b, TryCatch #0 {all -> 0x000b, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x000e, B:11:0x0033, B:13:0x003e, B:17:0x004c, B:19:0x0057, B:22:0x0063, B:26:0x006e, B:33:0x0084, B:34:0x00a3, B:36:0x00c5, B:37:0x00ca, B:39:0x00f3, B:41:0x0103, B:43:0x010e, B:44:0x0115, B:46:0x0124, B:47:0x013d, B:48:0x0142, B:51:0x00c8, B:60:0x0099), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0124 A[Catch: all -> 0x000b, TryCatch #0 {all -> 0x000b, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x000e, B:11:0x0033, B:13:0x003e, B:17:0x004c, B:19:0x0057, B:22:0x0063, B:26:0x006e, B:33:0x0084, B:34:0x00a3, B:36:0x00c5, B:37:0x00ca, B:39:0x00f3, B:41:0x0103, B:43:0x010e, B:44:0x0115, B:46:0x0124, B:47:0x013d, B:48:0x0142, B:51:0x00c8, B:60:0x0099), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00c8 A[Catch: all -> 0x000b, TryCatch #0 {all -> 0x000b, blocks: (B:4:0x0005, B:6:0x0009, B:9:0x000e, B:11:0x0033, B:13:0x003e, B:17:0x004c, B:19:0x0057, B:22:0x0063, B:26:0x006e, B:33:0x0084, B:34:0x00a3, B:36:0x00c5, B:37:0x00ca, B:39:0x00f3, B:41:0x0103, B:43:0x010e, B:44:0x0115, B:46:0x0124, B:47:0x013d, B:48:0x0142, B:51:0x00c8, B:60:0x0099), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x008f  */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda6] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void moveToTarget(final android.graphics.Rect r22, java.lang.Runnable r23, boolean r24) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.TaskMotionController.moveToTarget(android.graphics.Rect, java.lang.Runnable, boolean):void");
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
        int width = rect.width();
        int height = rect.height();
        Rect rect2 = this.mTaskMotionInfo.mSafeBounds;
        this.mFlingConfigX = new PhysicsAnimator.FlingConfig(1.9f, rect2.left, rect2.right - width);
        Rect rect3 = this.mTaskMotionInfo.mSafeBounds;
        this.mFlingConfigY = new PhysicsAnimator.FlingConfig(1.9f, rect3.top, rect3.bottom - height);
        Rect rect4 = this.mTaskMotionInfo.mMaxBounds;
        this.mStashConfigX = new PhysicsAnimator.FlingConfig(1.9f, rect4.left, rect4.right - width);
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
            ActivityInfo.WindowLayout windowLayout = activityInfo != null ? activityInfo.windowLayout : null;
            if (windowLayout != null) {
                float f = runningTaskInfo.configuration.densityDpi;
                float initialDensity = SemWindowManager.getInstance().getInitialDensity();
                ActivityInfo activityInfo2 = runningTaskInfo.topActivityInfo;
                windowLayout = MultiWindowUtils.recalculateWindowLayout(f, initialDensity, windowLayout, activityInfo2 != null ? activityInfo2.packageName : null);
            }
            TaskMotionInfo taskMotionInfo2 = this.mTaskMotionInfo;
            MultiWindowUtils.getDefaultFreeformBounds(taskMotionInfo2.mDisplayBounds, taskMotionInfo2.mStableBounds, windowLayout, rect2);
            if (this.mTaskMotionInfo.mStableBounds.width() < rect2.width()) {
                rect2.right = this.mTaskMotionInfo.mStableBounds.width();
            }
            if (this.mTaskMotionInfo.mStableBounds.height() < rect2.height()) {
                rect2.bottom = this.mTaskMotionInfo.mStableBounds.height();
            }
        }
        int computeStashState = computeStashState(this.mTaskMotionInfo.mStableBounds, rect2, z);
        if (computeStashState != 0) {
            Slog.d("TaskMotionController", "scheduleAnimateRestore adjust restore bounds: taskInfo=" + runningTaskInfo + " startBounds=" + rect + " endBounds=" + rect2);
            rect2.offsetTo((this.mTaskMotionInfo.mDisplayBounds.width() - rect2.width()) / 2, (this.mTaskMotionInfo.mDisplayBounds.height() - rect2.height()) / 2);
        }
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        if (bounds.width() != rect2.width() || bounds.height() != rect2.height()) {
            rect2.right = bounds.width() + rect2.left;
            rect2.bottom = bounds.height() + rect2.top;
        }
        if (computeStashState == 0) {
            if (this.mTaskMotionInfo.mMaxBounds.top > rect2.top) {
                int height = rect2.height();
                int i = this.mTaskMotionInfo.mMaxBounds.top;
                rect2.top = i;
                rect2.bottom = i + height;
            }
            if (this.mTaskMotionInfo.mMaxBounds.bottom < rect2.bottom) {
                int height2 = rect2.height();
                int i2 = this.mTaskMotionInfo.mMaxBounds.bottom;
                rect2.bottom = i2;
                rect2.top = i2 - height2;
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
                TaskMotionController taskMotionController = TaskMotionController.this;
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0023 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0025 A[Catch: all -> 0x004e, TryCatch #0 {all -> 0x004e, blocks: (B:4:0x0004, B:6:0x000a, B:8:0x0010, B:17:0x0025, B:19:0x002d, B:20:0x004c, B:23:0x0052, B:26:0x005c, B:28:0x0062, B:29:0x0067, B:33:0x008a, B:34:0x00bb, B:36:0x0073, B:42:0x00bd), top: B:3:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean scheduleAnimateScale(final int r12, android.graphics.Rect r13) {
        /*
            r11 = this;
            java.lang.String r0 = "scheduleAnimateScale: animType="
            monitor-enter(r11)
            android.view.SurfaceControl r1 = r11.mTaskSurface     // Catch: java.lang.Throwable -> L4e
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L16
            boolean r1 = r1.isValid()     // Catch: java.lang.Throwable -> L4e
            if (r1 == 0) goto L16
            com.android.wm.shell.windowdecor.TaskMotionController$TaskMotionInfo r1 = r11.mTaskMotionInfo     // Catch: java.lang.Throwable -> L4e
            if (r1 == 0) goto L16
            r1 = r3
            goto L17
        L16:
            r1 = r2
        L17:
            if (r1 == 0) goto Lbd
            if (r12 != r3) goto L1c
            goto L1e
        L1c:
            if (r12 != 0) goto L20
        L1e:
            r1 = r3
            goto L21
        L20:
            r1 = r2
        L21:
            if (r1 != 0) goto L25
            goto Lbd
        L25:
            com.android.wm.shell.windowdecor.TaskMotionController$TaskMotionInfo r1 = r11.mTaskMotionInfo     // Catch: java.lang.Throwable -> L4e
            boolean r1 = r1.isAnimating(r12)     // Catch: java.lang.Throwable -> L4e
            if (r1 == 0) goto L52
            java.lang.String r13 = "TaskMotionController"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4e
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L4e
            r1.append(r12)     // Catch: java.lang.Throwable -> L4e
            java.lang.String r12 = " failed, already animating, t #"
            r1.append(r12)     // Catch: java.lang.Throwable -> L4e
            com.android.wm.shell.windowdecor.DesktopModeWindowDecoration r12 = r11.mWindowDecoration     // Catch: java.lang.Throwable -> L4e
            android.app.ActivityManager$RunningTaskInfo r12 = r12.mTaskInfo     // Catch: java.lang.Throwable -> L4e
            int r12 = r12.taskId     // Catch: java.lang.Throwable -> L4e
            r1.append(r12)     // Catch: java.lang.Throwable -> L4e
            java.lang.String r12 = r1.toString()     // Catch: java.lang.Throwable -> L4e
            android.util.Slog.d(r13, r12)     // Catch: java.lang.Throwable -> L4e
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L4e
            return r2
        L4e:
            r0 = move-exception
            r12 = r0
            goto Lbf
        L52:
            com.android.wm.shell.windowdecor.TaskMotionController$TaskMotionInfo r0 = r11.mTaskMotionInfo     // Catch: java.lang.Throwable -> L4e
            if (r12 != 0) goto L58
            r2 = r3
            goto L5c
        L58:
            if (r12 != r3) goto L5b
            goto L5c
        L5b:
            r2 = -1
        L5c:
            boolean r0 = r0.isAnimating(r2)     // Catch: java.lang.Throwable -> L4e
            if (r0 == 0) goto L67
            com.android.wm.shell.windowdecor.TaskMotionController$TaskMotionInfo r0 = r11.mTaskMotionInfo     // Catch: java.lang.Throwable -> L4e
            r0.clearAnimator()     // Catch: java.lang.Throwable -> L4e
        L67:
            com.android.wm.shell.windowdecor.FreeformStashState r0 = r11.mFreeformStashState     // Catch: java.lang.Throwable -> L4e
            r0.mAnimType = r12     // Catch: java.lang.Throwable -> L4e
            r0.mAnimating = r3     // Catch: java.lang.Throwable -> L4e
            if (r12 != r3) goto L73
            r0 = 1065353216(0x3f800000, float:1.0)
        L71:
            r10 = r0
            goto L8a
        L73:
            com.android.wm.shell.windowdecor.DesktopModeWindowDecoration r0 = r11.mWindowDecoration     // Catch: java.lang.Throwable -> L4e
            android.app.ActivityManager$RunningTaskInfo r0 = r0.mTaskInfo     // Catch: java.lang.Throwable -> L4e
            android.content.res.Configuration r0 = r0.configuration     // Catch: java.lang.Throwable -> L4e
            android.app.WindowConfiguration r0 = r0.windowConfiguration     // Catch: java.lang.Throwable -> L4e
            android.graphics.Rect r0 = r0.getBounds()     // Catch: java.lang.Throwable -> L4e
            int r1 = r11.mScaledFreeformHeight     // Catch: java.lang.Throwable -> L4e
            float r1 = (float) r1     // Catch: java.lang.Throwable -> L4e
            int r0 = r0.height()     // Catch: java.lang.Throwable -> L4e
            float r0 = (float) r0     // Catch: java.lang.Throwable -> L4e
            float r0 = r1 / r0
            goto L71
        L8a:
            com.android.wm.shell.windowdecor.TaskMotionAnimValue r4 = new com.android.wm.shell.windowdecor.TaskMotionAnimValue     // Catch: java.lang.Throwable -> L4e
            com.android.wm.shell.windowdecor.FreeformStashState r6 = r11.mFreeformStashState     // Catch: java.lang.Throwable -> L4e
            android.view.SurfaceControl r7 = r11.mTaskSurface     // Catch: java.lang.Throwable -> L4e
            r9 = 0
            r5 = r12
            r8 = r13
            r4.<init>(r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L4e
            com.android.wm.shell.windowdecor.TaskMotionController$TaskMotionInfo r12 = r11.mTaskMotionInfo     // Catch: java.lang.Throwable -> L4e
            com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda2 r13 = new com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda2     // Catch: java.lang.Throwable -> L4e
            r13.<init>()     // Catch: java.lang.Throwable -> L4e
            r12.getClass()     // Catch: java.lang.Throwable -> L4e
            com.android.wm.shell.windowdecor.TaskMotionAnimator r0 = new com.android.wm.shell.windowdecor.TaskMotionAnimator     // Catch: java.lang.Throwable -> L4e
            r0.<init>(r4, r13)     // Catch: java.lang.Throwable -> L4e
            android.util.ArrayMap r12 = r12.mMotionAnimators     // Catch: java.lang.Throwable -> L4e
            int r13 = r4.mAnimType     // Catch: java.lang.Throwable -> L4e
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch: java.lang.Throwable -> L4e
            r12.put(r13, r0)     // Catch: java.lang.Throwable -> L4e
            com.android.wm.shell.common.ShellExecutor r12 = r11.mAnimExecutor     // Catch: java.lang.Throwable -> L4e
            com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda3 r13 = new com.android.wm.shell.windowdecor.TaskMotionController$$ExternalSyntheticLambda3     // Catch: java.lang.Throwable -> L4e
            r1 = 2
            r13.<init>(r0, r1)     // Catch: java.lang.Throwable -> L4e
            r12.execute(r13)     // Catch: java.lang.Throwable -> L4e
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L4e
            return r3
        Lbd:
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L4e
            return r2
        Lbf:
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L4e
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.TaskMotionController.scheduleAnimateScale(int, android.graphics.Rect):boolean");
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
                    desktopModeWindowDecoration.updateDragResizeListener(desktopModeWindowDecoration.mDecorationContainerSurface, new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(desktopModeWindowDecoration, !desktopModeWindowDecoration.mTaskInfo.positionInParent.equals(desktopModeWindowDecoration.mPositionInParent), desktopModeWindowDecoration.mDesktopUserRepositories.getProfile(desktopModeWindowDecoration.mTaskInfo.userId).isTaskInFullImmersiveState(desktopModeWindowDecoration.mTaskInfo.taskId), 1));
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
