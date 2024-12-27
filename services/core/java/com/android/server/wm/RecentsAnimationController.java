package com.android.server.wm;

import android.graphics.GraphicBuffer;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.IRecentsAnimationController;
import android.view.IRecentsAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.WindowInsets;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.TaskSnapshot;
import android.window.WindowAnimationState;

import com.android.internal.os.IResultReceiver;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.statusbar.IStatusBar;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.utils.InsetUtils;

import com.google.android.collect.Sets;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RecentsAnimationController implements IBinder.DeathRecipient {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final RecentsAnimation mCallbacks;
    public boolean mCancelDeferredWithScreenshot;
    public boolean mCancelOnNextTransitionStart;
    public boolean mCanceled;
    public final DisplayContent mDisplayContent;
    public final int mDisplayId;
    public boolean mInputConsumerEnabled;
    boolean mIsAddingTaskToTargets;
    public boolean mLinkedToDeathOfRunner;
    public ActivityRecord mNavBarAttachedApp;
    public boolean mNavigationBarAttachedToApp;
    public boolean mRequestDeferCancelUntilNextTransition;
    public IRecentsAnimationRunner mRunner;
    public final WindowManagerService mService;
    public ActivityRecord mTargetActivityRecord;
    public int mTargetActivityType;
    public final ArrayList mPendingAnimations = new ArrayList();
    public final IntArray mPendingNewTaskTargets = new IntArray(0);
    public final ArrayList mPendingWallpaperAnimations = new ArrayList();
    public boolean mWillFinishToHome = false;
    public final RecentsAnimationController$$ExternalSyntheticLambda0 mFailsafeRunnable =
            new Runnable() { // from class:
                             // com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RecentsAnimationController recentsAnimationController =
                            RecentsAnimationController.this;
                    int i = recentsAnimationController.mWillFinishToHome ? 1 : 2;
                    if (recentsAnimationController.mCanceled) {
                        recentsAnimationController.continueDeferredCancelAnimation();
                    } else {
                        recentsAnimationController.cancelAnimation(i, "onFailsafe", false);
                    }
                }
            };
    public boolean mPendingStart = true;
    public final Rect mTmpRect = new Rect();
    public int mPendingCancelWithScreenshotReorderMode = 2;
    public final ArrayList mPendingTaskAppears = new ArrayList();
    public final AnonymousClass1 mAppTransitionListener =
            new WindowManagerInternal
                    .AppTransitionListener() { // from class:
                                               // com.android.server.wm.RecentsAnimationController.1
                public final void continueDeferredCancel() {
                    RecentsAnimationController recentsAnimationController =
                            RecentsAnimationController.this;
                    recentsAnimationController.mDisplayContent.mAppTransition.mListeners.remove(
                            this);
                    if (!recentsAnimationController.mCanceled
                            && recentsAnimationController.mCancelOnNextTransitionStart) {
                        recentsAnimationController.mCancelOnNextTransitionStart = false;
                        recentsAnimationController.cancelAnimation(
                                0,
                                "rootTaskOrderChanged",
                                recentsAnimationController.mCancelDeferredWithScreenshot);
                    }
                }

                @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
                public final void onAppTransitionCancelledLocked(boolean z) {
                    continueDeferredCancel();
                }

                @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
                public final void onAppTransitionStartingLocked(long j) {
                    continueDeferredCancel();
                }
            };
    public final AnonymousClass2 mController =
            new IRecentsAnimationController
                    .Stub() { // from class: com.android.server.wm.RecentsAnimationController.2
                public final void animateNavigationBarToApp(long j) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                            RecentsAnimationController recentsAnimationController =
                                    RecentsAnimationController.this;
                            if (recentsAnimationController.mDisplayContent.mDisplayPolicy
                                            .shouldAttachNavBarToAppDuringTransition()
                                    && recentsAnimationController.mDisplayContent
                                                    .getAsyncRotationController()
                                            == null
                                    && !recentsAnimationController.mNavigationBarAttachedToApp
                                    && recentsAnimationController.mNavBarAttachedApp != null) {
                                new NavBarFadeAnimationController(
                                                recentsAnimationController.mDisplayContent)
                                        .fadeOutAndInSequentially(
                                                j,
                                                recentsAnimationController
                                                        .mNavBarAttachedApp
                                                        .mSurfaceControl);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void cleanupScreenshot() {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        RecentsAnimationController.this.continueDeferredCancelAnimation();
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void detachNavigationBarFromApp(boolean z) {
                    boolean z2;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                            try {
                                RecentsAnimationController recentsAnimationController =
                                        RecentsAnimationController.this;
                                if (!z && !recentsAnimationController.mIsAddingTaskToTargets) {
                                    z2 = false;
                                    recentsAnimationController.restoreNavigationBarFromApp(z2);
                                    RecentsAnimationController.this.mService.mWindowPlacerLocked
                                            .requestTraversal();
                                }
                                z2 = true;
                                recentsAnimationController.restoreNavigationBarFromApp(z2);
                                RecentsAnimationController.this.mService.mWindowPlacerLocked
                                        .requestTraversal();
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void finish(boolean z, boolean z2, IResultReceiver iResultReceiver) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                5187133389446459984L,
                                15,
                                null,
                                Boolean.valueOf(z),
                                Boolean.valueOf(RecentsAnimationController.this.mCanceled));
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        RecentsAnimationController.this.mCallbacks.onAnimationFinished(
                                z ? 1 : 2, z2);
                        if (iResultReceiver != null) {
                            try {
                                iResultReceiver.send(0, new Bundle());
                            } catch (RemoteException e) {
                                int i = RecentsAnimationController.$r8$clinit;
                                Slog.e(
                                        "RecentsAnimationController",
                                        "Failed to report animation finished",
                                        e);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void handOffAnimation(
                        RemoteAnimationTarget[] remoteAnimationTargetArr,
                        WindowAnimationState[] windowAnimationStateArr) {}

                public final boolean removeTask(int i) {
                    boolean z;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                            RecentsAnimationController recentsAnimationController =
                                    RecentsAnimationController.this;
                            z = true;
                            int size = recentsAnimationController.mPendingAnimations.size() - 1;
                            while (true) {
                                if (size < 0) {
                                    z = false;
                                    break;
                                }
                                TaskAnimationAdapter taskAnimationAdapter =
                                        (TaskAnimationAdapter)
                                                recentsAnimationController.mPendingAnimations.get(
                                                        size);
                                Task task = taskAnimationAdapter.mTask;
                                if (task.mTaskId == i && task.isOnTop()) {
                                    recentsAnimationController.removeAnimation(
                                            taskAnimationAdapter);
                                    int indexOf =
                                            recentsAnimationController.mPendingNewTaskTargets
                                                    .indexOf(i);
                                    if (indexOf != -1) {
                                        recentsAnimationController.mPendingNewTaskTargets.remove(
                                                indexOf);
                                    }
                                } else {
                                    size--;
                                }
                            }
                        }
                        return z;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final TaskSnapshot screenshotTask(int i) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                6530904107141905844L,
                                13,
                                null,
                                Long.valueOf(i),
                                Boolean.valueOf(RecentsAnimationController.this.mCanceled));
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                            RecentsAnimationController recentsAnimationController =
                                    RecentsAnimationController.this;
                            if (recentsAnimationController.mCanceled) {
                                return null;
                            }
                            for (int size =
                                            recentsAnimationController.mPendingAnimations.size()
                                                    - 1;
                                    size >= 0;
                                    size--) {
                                Task task =
                                        ((TaskAnimationAdapter)
                                                        RecentsAnimationController.this
                                                                .mPendingAnimations.get(size))
                                                .mTask;
                                if (task.mTaskId == i) {
                                    TaskSnapshotController taskSnapshotController =
                                            RecentsAnimationController.this
                                                    .mService
                                                    .mTaskSnapshotController;
                                    ArraySet newArraySet = Sets.newArraySet(new Task[] {task});
                                    taskSnapshotController.snapshotTasks(newArraySet);
                                    taskSnapshotController.addSkipClosingAppSnapshotTasks(
                                            newArraySet);
                                    return taskSnapshotController.getSnapshot(
                                            i, task.mUserId, false, false, false);
                                }
                            }
                            return null;
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void setAnimationTargetsBehindSystemBars(boolean z) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                            try {
                                for (int size =
                                                RecentsAnimationController.this.mPendingAnimations
                                                                .size()
                                                        - 1;
                                        size >= 0;
                                        size--) {
                                    Task task =
                                            ((TaskAnimationAdapter)
                                                            RecentsAnimationController.this
                                                                    .mPendingAnimations.get(size))
                                                    .mTask;
                                    if (task.getActivityType()
                                            != RecentsAnimationController.this
                                                    .mTargetActivityType) {
                                        task.mCanAffectSystemUiFlags = z;
                                    }
                                }
                                InputMethodManagerInternal.get().maybeFinishStylusHandwriting();
                                RecentsAnimationController.this.mService.mWindowPlacerLocked
                                        .requestTraversal();
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void setDeferCancelUntilNextTransition(boolean z, boolean z2) {
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            RecentsAnimationController.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            RecentsAnimationController recentsAnimationController =
                                    RecentsAnimationController.this;
                            recentsAnimationController.mRequestDeferCancelUntilNextTransition = z;
                            recentsAnimationController.mCancelDeferredWithScreenshot = z2;
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }

                public final void setFinishTaskTransaction(
                        int i,
                        PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction,
                        SurfaceControl surfaceControl) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                -3286551982713129633L,
                                1,
                                null,
                                Long.valueOf(i),
                                String.valueOf(pictureInPictureSurfaceTransaction));
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                            try {
                                int size =
                                        RecentsAnimationController.this.mPendingAnimations.size()
                                                - 1;
                                while (true) {
                                    if (size < 0) {
                                        break;
                                    }
                                    TaskAnimationAdapter taskAnimationAdapter =
                                            (TaskAnimationAdapter)
                                                    RecentsAnimationController.this
                                                            .mPendingAnimations.get(size);
                                    if (taskAnimationAdapter.mTask.mTaskId == i) {
                                        taskAnimationAdapter.mFinishTransaction =
                                                pictureInPictureSurfaceTransaction;
                                        taskAnimationAdapter.mFinishOverlay = surfaceControl;
                                        break;
                                    }
                                    size--;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void setInputConsumerEnabled(boolean z) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                6879496555046975661L,
                                12,
                                null,
                                String.valueOf(z),
                                Boolean.valueOf(RecentsAnimationController.this.mCanceled));
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                            RecentsAnimationController recentsAnimationController =
                                    RecentsAnimationController.this;
                            if (recentsAnimationController.mCanceled) {
                                return;
                            }
                            recentsAnimationController.mInputConsumerEnabled = z;
                            recentsAnimationController.mDisplayContent.mInputMonitor
                                    .updateInputWindowsLw(true);
                            RecentsAnimationController.this.mService.scheduleAnimationLocked();
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                public final void setWillFinishToHome(boolean z) {
                    synchronized (RecentsAnimationController.this.mService.mGlobalLock) {
                        RecentsAnimationController.this.setWillFinishToHome(z);
                    }
                }

                public final void setWillForceFinishToHome(boolean z) {}
            };
    final StatusBarManagerInternal mStatusBar =
            (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class TaskAnimationAdapter implements AnimationAdapter {
        public final Rect mBounds;
        public SurfaceAnimator.OnAnimationFinishedCallback mCapturedFinishCallback;
        public SurfaceControl mCapturedLeash;
        public SurfaceControl mFinishOverlay;
        public PictureInPictureSurfaceTransaction mFinishTransaction;
        public final boolean mIsRecentTaskInvisible;
        public int mLastAnimationType;
        public final Rect mLocalBounds;
        public SurfaceControl mSnapshotOverlay;
        public RemoteAnimationTarget mTarget;
        public final Task mTask;

        public TaskAnimationAdapter(Task task, boolean z) {
            Rect rect = new Rect();
            this.mBounds = rect;
            Rect rect2 = new Rect();
            this.mLocalBounds = rect2;
            this.mTask = task;
            this.mIsRecentTaskInvisible = z;
            rect.set(task.getBounds());
            rect2.set(rect);
            Point point = new Point();
            task.getRelativePosition(point);
            rect2.offsetTo(point.x, point.y);
        }

        public final RemoteAnimationTarget createRemoteAnimationTarget(int i, int i2) {
            StartingData startingData;
            Task task = this.mTask;
            task.getClass();
            ActivityRecord activity = task.getActivity(new Task$$ExternalSyntheticLambda0(1));
            if (activity == null) {
                activity = task.getTopVisibleActivity(false, false);
            }
            WindowState findMainWindow = activity != null ? activity.findMainWindow(true) : null;
            if (findMainWindow == null) {
                return null;
            }
            Rect rect =
                    findMainWindow
                            .getInsetsStateWithVisibilityOverride()
                            .calculateInsets(this.mBounds, WindowInsets.Type.systemBars(), false)
                            .toRect();
            InsetUtils.addInsets(rect, findMainWindow.mActivityRecord.getLetterboxInsets());
            int i3 =
                    i2 != -1
                            ? i2
                            : activity.getActivityType()
                                            == RecentsAnimationController.this.mTargetActivityType
                                    ? 0
                                    : 1;
            int i4 = i < 0 ? task.mTaskId : i;
            SurfaceControl surfaceControl = this.mCapturedLeash;
            boolean z = !activity.occludesParent(true);
            Rect rect2 = new Rect();
            int prefixOrderIndex = task.getPrefixOrderIndex();
            Rect rect3 = this.mBounds;
            this.mTarget =
                    new RemoteAnimationTarget(
                            i4,
                            i3,
                            surfaceControl,
                            z,
                            rect2,
                            rect,
                            prefixOrderIndex,
                            new Point(rect3.left, rect3.top),
                            this.mLocalBounds,
                            this.mBounds,
                            task.getWindowConfiguration(),
                            this.mIsRecentTaskInvisible,
                            (SurfaceControl) null,
                            (Rect) null,
                            task.getTaskInfo(),
                            activity.checkEnterPictureInPictureAppOpsState());
            ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity(true, true);
            if (topNonFinishingActivity != null
                    && (startingData = topNonFinishingActivity.mStartingData) != null
                    && startingData.hasImeSurface()) {
                this.mTarget.setWillShowImeOnTarget(true);
            }
            return this.mTarget;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dump(PrintWriter printWriter, String str) {
            StringBuilder m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter, str, "task=");
            m.append(this.mTask);
            printWriter.println(m.toString());
            if (this.mTarget != null) {
                printWriter.print(str);
                printWriter.println("Target:");
                this.mTarget.dump(printWriter, str + "  ");
            } else {
                printWriter.print(str);
                printWriter.println("Target: null");
            }
            StringBuilder sb = new StringBuilder("mIsRecentTaskInvisible=");
            boolean z = this.mIsRecentTaskInvisible;
            StringBuilder m2 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            sb, z, printWriter, "mLocalBounds=");
            m2.append(this.mLocalBounds);
            printWriter.println(m2.toString());
            printWriter.println("mFinishTransaction=" + this.mFinishTransaction);
            printWriter.println("mBounds=" + this.mBounds);
            printWriter.println("mIsRecentTaskInvisible=" + z);
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268034L);
            RemoteAnimationTarget remoteAnimationTarget = this.mTarget;
            if (remoteAnimationTarget != null) {
                remoteAnimationTarget.dumpDebug(protoOutputStream, 1146756268033L);
            }
            protoOutputStream.end(start);
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final long getDurationHint() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final boolean getShowWallpaper() {
            return false;
        }

        public SurfaceControl getSnapshotOverlay() {
            return this.mSnapshotOverlay;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final long getStatusBarTransitionsStartTime() {
            return SystemClock.uptimeMillis();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void onAnimationCancelled(SurfaceControl surfaceControl) {
            RecentsAnimationController.this.cancelAnimation(
                    2, "taskAnimationAdapterCanceled", false);
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void startAnimation(
                SurfaceControl surfaceControl,
                SurfaceControl.Transaction transaction,
                int i,
                SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            Rect rect = this.mLocalBounds;
            transaction.setPosition(surfaceControl, rect.left, rect.top);
            RecentsAnimationController recentsAnimationController = RecentsAnimationController.this;
            recentsAnimationController.mTmpRect.set(this.mLocalBounds);
            recentsAnimationController.mTmpRect.offsetTo(0, 0);
            transaction.setWindowCrop(surfaceControl, recentsAnimationController.mTmpRect);
            this.mCapturedLeash = surfaceControl;
            this.mCapturedFinishCallback = onAnimationFinishedCallback;
            this.mLastAnimationType = i;
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.wm.RecentsAnimationController$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.wm.RecentsAnimationController$2] */
    public RecentsAnimationController(
            WindowManagerService windowManagerService,
            IRecentsAnimationRunner iRecentsAnimationRunner,
            RecentsAnimation recentsAnimation,
            int i) {
        this.mService = windowManagerService;
        this.mRunner = iRecentsAnimationRunner;
        this.mCallbacks = recentsAnimation;
        this.mDisplayId = i;
        this.mDisplayContent = windowManagerService.mRoot.getDisplayContent(i);
    }

    public TaskAnimationAdapter addAnimation(Task task, boolean z) {
        return addAnimation(task, z, false, null);
    }

    public TaskAnimationAdapter addAnimation(
            Task task,
            boolean z,
            boolean z2,
            SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                    -3801497203749932106L,
                    0,
                    null,
                    String.valueOf(task.getName()));
        }
        TaskAnimationAdapter taskAnimationAdapter = new TaskAnimationAdapter(task, z);
        task.startAnimation(
                task.getPendingTransaction(),
                taskAnimationAdapter,
                z2,
                8,
                onAnimationFinishedCallback);
        task.scheduleAnimation();
        this.mPendingAnimations.add(taskAnimationAdapter);
        return taskAnimationAdapter;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        if (this.mCanceled) {
            continueDeferredCancelAnimation();
        } else {
            cancelAnimation(2, "binderDied", false);
        }
        synchronized (this.mService.mGlobalLock) {
            try {
                InputMonitor inputMonitor = this.mDisplayContent.mInputMonitor;
                InputConsumerImpl inputConsumer =
                        inputMonitor.getInputConsumer("recents_animation_input_consumer");
                if (inputConsumer != null) {
                    inputMonitor.destroyInputConsumer(inputConsumer.mToken);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cancelAnimation(int i, String str, boolean z) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                    5444932814080651576L,
                    0,
                    null,
                    String.valueOf(str));
        }
        synchronized (this.mService.mGlobalLock) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mService.mH.removeCallbacks(this.mFailsafeRunnable);
                this.mCanceled = true;
                if (z && !this.mPendingAnimations.isEmpty()) {
                    ArrayMap screenshotRecentTasks = screenshotRecentTasks();
                    this.mPendingCancelWithScreenshotReorderMode = i;
                    if (!screenshotRecentTasks.isEmpty()) {
                        try {
                            int[] iArr = new int[screenshotRecentTasks.size()];
                            TaskSnapshot[] taskSnapshotArr =
                                    new TaskSnapshot[screenshotRecentTasks.size()];
                            for (int size = screenshotRecentTasks.size() - 1; size >= 0; size--) {
                                iArr[size] = ((Task) screenshotRecentTasks.keyAt(size)).mTaskId;
                                taskSnapshotArr[size] =
                                        (TaskSnapshot) screenshotRecentTasks.valueAt(size);
                            }
                            this.mRunner.onAnimationCanceled(iArr, taskSnapshotArr);
                        } catch (RemoteException e) {
                            Slog.e(
                                    "RecentsAnimationController",
                                    "Failed to cancel recents animation",
                                    e);
                        }
                        this.mService.mH.postDelayed(this.mFailsafeRunnable, 1000L);
                        return;
                    }
                }
                try {
                    this.mRunner.onAnimationCanceled((int[]) null, (TaskSnapshot[]) null);
                } catch (RemoteException e2) {
                    Slog.e("RecentsAnimationController", "Failed to cancel recents animation", e2);
                }
                this.mCallbacks.onAnimationFinished(i, false);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void continueDeferredCancelAnimation() {
        this.mCallbacks.onAnimationFinished(this.mPendingCancelWithScreenshotReorderMode, false);
    }

    public WindowState getNavigationBarWindow() {
        return this.mDisplayContent.mDisplayPolicy.mNavigationBar;
    }

    public final boolean isAnimatingTask(Task task) {
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            if (task == ((TaskAnimationAdapter) this.mPendingAnimations.get(size)).mTask) {
                return true;
            }
        }
        return false;
    }

    public final boolean isTargetApp(ActivityRecord activityRecord) {
        ActivityRecord activityRecord2 = this.mTargetActivityRecord;
        return activityRecord2 != null && activityRecord == activityRecord2;
    }

    public void removeAnimation(TaskAnimationAdapter taskAnimationAdapter) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                    3721473589747203697L,
                    1,
                    null,
                    Long.valueOf(taskAnimationAdapter.mTask.mTaskId));
        }
        SurfaceControl surfaceControl = taskAnimationAdapter.mSnapshotOverlay;
        Task task = taskAnimationAdapter.mTask;
        if (surfaceControl != null) {
            task.getPendingTransaction().remove(taskAnimationAdapter.mSnapshotOverlay).apply();
            taskAnimationAdapter.mSnapshotOverlay = null;
        }
        task.mCanAffectSystemUiFlags = true;
        taskAnimationAdapter.mCapturedFinishCallback.onAnimationFinished(
                taskAnimationAdapter.mLastAnimationType, taskAnimationAdapter);
        this.mPendingAnimations.remove(taskAnimationAdapter);
    }

    public void removeWallpaperAnimation(WallpaperAnimationAdapter wallpaperAnimationAdapter) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 5156407755139006078L, 0, null, null);
        }
        wallpaperAnimationAdapter.mCapturedLeashFinishCallback.onAnimationFinished(
                wallpaperAnimationAdapter.mLastAnimationType, wallpaperAnimationAdapter);
        this.mPendingWallpaperAnimations.remove(wallpaperAnimationAdapter);
    }

    public void restoreNavigationBarFromApp(boolean z) {
        if (this.mNavigationBarAttachedToApp) {
            this.mNavigationBarAttachedToApp = false;
            StatusBarManagerInternal statusBarManagerInternal = this.mStatusBar;
            if (statusBarManagerInternal != null) {
                int i = this.mDisplayId;
                IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                if (iStatusBar != null) {
                    try {
                        iStatusBar.setNavigationBarLumaSamplingEnabled(i, true);
                    } catch (RemoteException unused) {
                    }
                }
            }
            WindowState navigationBarWindow = getNavigationBarWindow();
            if (navigationBarWindow == null) {
                return;
            }
            navigationBarWindow.mSurfaceTranslationY = 0;
            WindowToken windowToken = navigationBarWindow.mToken;
            if (windowToken == null) {
                return;
            }
            SurfaceControl.Transaction pendingTransaction =
                    this.mDisplayContent.getPendingTransaction();
            WindowContainer parent = windowToken.getParent();
            pendingTransaction.setLayer(windowToken.mSurfaceControl, windowToken.getLastLayer());
            if (z) {
                new NavBarFadeAnimationController(this.mDisplayContent).fadeWindowToken(true);
            } else {
                pendingTransaction.reparent(
                        windowToken.mSurfaceControl, parent.getSurfaceControl());
            }
        }
    }

    public final ArrayMap screenshotRecentTasks() {
        TaskSnapshotController taskSnapshotController = this.mService.mTaskSnapshotController;
        ArrayMap arrayMap = new ArrayMap();
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            TaskAnimationAdapter taskAnimationAdapter =
                    (TaskAnimationAdapter) this.mPendingAnimations.get(size);
            Task task = taskAnimationAdapter.mTask;
            if (!task.isActivityTypeHome()) {
                taskSnapshotController.recordSnapshot(task);
                TaskSnapshot snapshot =
                        taskSnapshotController.getSnapshot(
                                task.mTaskId, task.mUserId, false, false, false);
                if (snapshot != null) {
                    arrayMap.put(task, snapshot);
                    HardwareBuffer hardwareBuffer = snapshot.getHardwareBuffer();
                    if (hardwareBuffer != null) {
                        taskAnimationAdapter.mSnapshotOverlay =
                                ((SurfaceControl.Builder)
                                                RecentsAnimationController.this.mService
                                                        .mSurfaceControlFactory.apply(
                                                        new SurfaceSession()))
                                        .setName("RecentTaskScreenshotSurface")
                                        .setCallsite("TaskAnimationAdapter.setSnapshotOverlay")
                                        .setFormat(hardwareBuffer.getFormat())
                                        .setParent(taskAnimationAdapter.mCapturedLeash)
                                        .setBLASTLayer()
                                        .build();
                        float width = (r4.getBounds().width() * 1.0f) / hardwareBuffer.getWidth();
                        taskAnimationAdapter
                                .mTask
                                .getPendingTransaction()
                                .setBuffer(
                                        taskAnimationAdapter.mSnapshotOverlay,
                                        GraphicBuffer.createFromHardwareBuffer(hardwareBuffer))
                                .setColorSpace(
                                        taskAnimationAdapter.mSnapshotOverlay,
                                        snapshot.getColorSpace())
                                .setLayer(taskAnimationAdapter.mSnapshotOverlay, Integer.MAX_VALUE)
                                .setMatrix(
                                        taskAnimationAdapter.mSnapshotOverlay,
                                        width,
                                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                                        width)
                                .show(taskAnimationAdapter.mSnapshotOverlay)
                                .apply();
                    }
                }
            }
        }
        taskSnapshotController.addSkipClosingAppSnapshotTasks(arrayMap.keySet());
        return arrayMap;
    }

    public final void sendTasksAppeared() {
        if (this.mPendingTaskAppears.isEmpty() || this.mRunner == null) {
            return;
        }
        try {
            this.mRunner.onTasksAppeared(
                    (RemoteAnimationTarget[])
                            this.mPendingTaskAppears.toArray(new RemoteAnimationTarget[0]));
            this.mPendingTaskAppears.clear();
        } catch (RemoteException e) {
            Slog.e("RecentsAnimationController", "Failed to report task appeared", e);
        }
    }

    public void setWillFinishToHome(boolean z) {
        this.mWillFinishToHome = z;
    }

    public final boolean shouldApplyInputConsumer(ActivityRecord activityRecord) {
        if (this.mInputConsumerEnabled && activityRecord != null && !isTargetApp(activityRecord)) {
            for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
                if (activityRecord.isDescendantOf(
                        ((TaskAnimationAdapter) this.mPendingAnimations.get(size)).mTask)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void startAnimation() {
        RemoteAnimationTarget[] remoteAnimationTargetArr;
        Rect rect;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled;
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                    -1997836523186474317L,
                    15,
                    null,
                    Boolean.valueOf(this.mPendingStart),
                    Boolean.valueOf(this.mCanceled));
        }
        if (!this.mPendingStart || this.mCanceled) {
            return;
        }
        try {
            ArrayList arrayList = new ArrayList();
            for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
                TaskAnimationAdapter taskAnimationAdapter =
                        (TaskAnimationAdapter) this.mPendingAnimations.get(size);
                RemoteAnimationTarget createRemoteAnimationTarget =
                        taskAnimationAdapter.createRemoteAnimationTarget(-1, -1);
                if (createRemoteAnimationTarget != null) {
                    arrayList.add(createRemoteAnimationTarget);
                } else {
                    removeAnimation(taskAnimationAdapter);
                }
            }
            remoteAnimationTargetArr =
                    (RemoteAnimationTarget[])
                            arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
        } catch (RemoteException e) {
            Slog.e("RecentsAnimationController", "Failed to start recents animation", e);
        }
        if (remoteAnimationTargetArr.length == 0) {
            cancelAnimation(2, "startAnimation-noAppWindows", false);
            return;
        }
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 2547528895718568379L, 0, null, null);
        }
        RemoteAnimationTarget[] startWallpaperAnimations =
                WallpaperAnimationAdapter.startWallpaperAnimations(
                        this.mDisplayContent,
                        0L,
                        0L,
                        new RecentsAnimationController$$ExternalSyntheticLambda1(1, this),
                        this.mPendingWallpaperAnimations);
        this.mPendingStart = false;
        ActivityRecord activityRecord = this.mTargetActivityRecord;
        WindowState findMainWindow =
                activityRecord == null ? null : activityRecord.findMainWindow(true);
        if (findMainWindow != null) {
            rect =
                    findMainWindow
                            .getInsetsStateWithVisibilityOverride()
                            .calculateInsets(
                                    this.mTargetActivityRecord.getBounds(),
                                    WindowInsets.Type.systemBars(),
                                    false)
                            .toRect();
        } else {
            this.mService.getStableInsets(this.mDisplayId, this.mTmpRect);
            rect = this.mTmpRect;
        }
        this.mRunner.onAnimationStart(
                this.mController,
                remoteAnimationTargetArr,
                startWallpaperAnimations,
                rect,
                (Rect) null,
                new Bundle());
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                    -7532294363367395195L,
                    0,
                    null,
                    String.valueOf(
                            this.mPendingAnimations.stream()
                                    .map(new RecentsAnimationController$$ExternalSyntheticLambda5())
                                    .collect(Collectors.toList())));
        }
        if (this.mTargetActivityRecord != null) {
            ArrayMap arrayMap = new ArrayMap(1);
            arrayMap.put(this.mTargetActivityRecord, 5);
            this.mService.mAtmService.mTaskSupervisor.mActivityMetricsLogger
                    .notifyTransitionStarting(arrayMap);
        }
    }
}
