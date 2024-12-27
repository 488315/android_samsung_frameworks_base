package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.InputConstants;
import android.os.RemoteException;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.BatchedInputEventReceiver;
import android.view.Display;
import android.view.IWindow;
import android.view.InputApplicationHandle;
import android.view.InputEvent;
import android.view.InputWindowHandle;
import android.view.MagnificationSpec;
import android.view.MotionEvent;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.rune.CoreRune;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TaskPositioningController {
    public SurfaceControl mInputSurface;
    public DisplayContent mPositioningDisplay;
    public final WindowManagerService mService;
    public TaskPositioner mTaskPositioner;
    public final Rect mTmpClipRect = new Rect();
    public final SurfaceControl.Transaction mTransaction;

    public TaskPositioningController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mTransaction =
                (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
    }

    public final void cleanUpTaskPositioner() {
        SurfaceControl surfaceControl;
        TaskPositioner taskPositioner = this.mTaskPositioner;
        if (taskPositioner == null) {
            return;
        }
        this.mTaskPositioner = null;
        if (taskPositioner.mClientChannel == null) {
            Slog.e("TaskPositioner", "Task positioner not registered");
            return;
        }
        TaskPositioningController taskPositioningController =
                taskPositioner.mService.mTaskPositioningController;
        int i = taskPositioner.mDisplayContent.mDisplayId;
        DisplayContent displayContent = taskPositioningController.mPositioningDisplay;
        if (displayContent != null
                && displayContent.mDisplayId == i
                && (surfaceControl = taskPositioningController.mInputSurface) != null) {
            taskPositioningController.mTransaction.hide(surfaceControl).apply();
        }
        taskPositioner.mService.mInputManager.removeInputChannel(
                taskPositioner.mClientChannel.getToken());
        taskPositioner.mInputEventReceiver.dispose();
        taskPositioner.mInputEventReceiver = null;
        taskPositioner.mClientChannel.dispose();
        taskPositioner.mClientChannel = null;
        taskPositioner.mDragWindowHandle = null;
        taskPositioner.mDragApplicationHandle = null;
        taskPositioner.mDragEnded = true;
        taskPositioner.mDisplayContent.mInputMonitor.updateInputWindowsLw(true);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_ORIENTATION, 5478864901888225320L, 0, null, null);
        }
        DisplayRotation displayRotation = taskPositioner.mDisplayContent.mDisplayRotation;
        int i2 = displayRotation.mDeferredRotationPauseCount;
        if (i2 > 0) {
            int i3 = i2 - 1;
            displayRotation.mDeferredRotationPauseCount = i3;
            if (i3 == 0) {
                displayRotation.updateRotationAndSendNewConfigIfChanged();
            }
        }
        taskPositioner.mDisplayContent = null;
        IBinder iBinder = taskPositioner.mClientCallback;
        if (iBinder != null) {
            iBinder.unlinkToDeath(taskPositioner, 0);
        }
    }

    public final void finishTaskPositioning$1() {
        this.mService.mAnimationHandler.post(
                new TaskPositioningController$$ExternalSyntheticLambda2(2, this));
    }

    public final boolean startMovingTask(IWindow iWindow, float f, float f2) {
        WindowState windowForClientLocked;
        CompletableFuture startPositioningLocked;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowForClientLocked =
                        this.mService.windowForClientLocked((Session) null, iWindow, false);
                startPositioningLocked = startPositioningLocked(windowForClientLocked, f, f2);
            } finally {
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        try {
            if (!((Boolean) startPositioningLocked.get()).booleanValue()) {
                return false;
            }
            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    this.mService.mAtmService.setFocusedTask(
                            windowForClientLocked.getTask().mTaskId);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return true;
        } catch (Exception e) {
            Slog.e(
                    "WindowManager",
                    "Exception thrown while waiting for startPositionLocked future",
                    e);
            return false;
        }
    }

    public final CompletableFuture startPositioningLocked(
            final WindowState windowState, final float f, final float f2) {
        CompletableFuture completedFuture;
        CompletableFuture<Void> thenRun;
        MagnificationSpec magnificationSpec;
        if (windowState == null || windowState.mActivityRecord == null) {
            Slog.w("WindowManager", "startPositioningLocked: Bad window " + windowState);
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        if (windowState.mInputChannel == null) {
            Slog.wtf(
                    "WindowManager",
                    "startPositioningLocked: "
                            + windowState
                            + " has no input channel,  probably being removed");
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        final DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent == null) {
            Slog.w(
                    "WindowManager",
                    "startPositioningLocked: Invalid display content " + windowState);
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        this.mPositioningDisplay = displayContent;
        if (TaskPositioner.sFactory == null) {
            TaskPositioner.sFactory = new TaskPositioner.AnonymousClass1();
        }
        TaskPositioner.sFactory.getClass();
        final TaskPositioner taskPositioner = new TaskPositioner(this.mService);
        this.mTaskPositioner = taskPositioner;
        if (taskPositioner.mClientChannel != null) {
            Slog.e("TaskPositioner", "Task positioner already registered");
            thenRun = CompletableFuture.completedFuture(null);
        } else {
            taskPositioner.mDisplayContent = displayContent;
            taskPositioner.mClientChannel =
                    taskPositioner.mService.mInputManager.createInputChannel("TaskPositioner");
            taskPositioner.mInputEventReceiver =
                    new BatchedInputEventReceiver.SimpleBatchedInputEventReceiver(
                            taskPositioner.mClientChannel,
                            taskPositioner.mService.mAnimationHandler.getLooper(),
                            taskPositioner.mService.mAnimator.mChoreographer,
                            new BatchedInputEventReceiver.SimpleBatchedInputEventReceiver
                                    .InputEventListener() { // from class:
                                                            // com.android.server.wm.TaskPositioner$$ExternalSyntheticLambda0
                                public final boolean onInputEvent(InputEvent inputEvent) {
                                    ActivityRecord activityRecord;
                                    TaskPositioner taskPositioner2 = TaskPositioner.this;
                                    taskPositioner2.getClass();
                                    if (!(inputEvent instanceof MotionEvent)
                                            || (inputEvent.getSource() & 2) == 0) {
                                        return false;
                                    }
                                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                                    if (!taskPositioner2.mDragEnded) {
                                        float rawX = motionEvent.getRawX();
                                        float rawY = motionEvent.getRawY();
                                        int action = motionEvent.getAction();
                                        if (action == 1) {
                                            taskPositioner2.mDragEnded = true;
                                        } else if (action == 2) {
                                            WindowManagerGlobalLock windowManagerGlobalLock =
                                                    taskPositioner2.mService.mGlobalLock;
                                            WindowManagerService.boostPriorityForLockedSection();
                                            synchronized (windowManagerGlobalLock) {
                                                try {
                                                    taskPositioner2.mDragEnded =
                                                            taskPositioner2.notifyMoveLocked(
                                                                    rawX, rawY);
                                                    taskPositioner2.mTask.getDimBounds(
                                                            taskPositioner2.mTmpRect);
                                                } finally {
                                                    WindowManagerService
                                                            .resetPriorityAfterLockedSection();
                                                }
                                            }
                                            WindowManagerService.resetPriorityAfterLockedSection();
                                            if (!taskPositioner2.mTmpRect.equals(
                                                    taskPositioner2.mWindowDragBounds)) {
                                                Trace.traceBegin(
                                                        32L, "wm.TaskPositioner.resizeTask");
                                                taskPositioner2.mService.mAtmService.resizeTask(
                                                        taskPositioner2.mTask.mTaskId,
                                                        taskPositioner2.mWindowDragBounds,
                                                        1);
                                                Trace.traceEnd(32L);
                                            }
                                        } else if (action == 3) {
                                            taskPositioner2.mDragEnded = true;
                                        }
                                        if (taskPositioner2.mDragEnded) {
                                            boolean z = taskPositioner2.mResizing;
                                            WindowManagerGlobalLock windowManagerGlobalLock2 =
                                                    taskPositioner2.mService.mGlobalLock;
                                            WindowManagerService.boostPriorityForLockedSection();
                                            synchronized (windowManagerGlobalLock2) {
                                                try {
                                                    taskPositioner2.mResizing = false;
                                                    taskPositioner2.mTask.setDragResizing(false);
                                                    taskPositioner2.mTask.getDimBounds(
                                                            taskPositioner2.mTmpRect);
                                                } finally {
                                                    WindowManagerService
                                                            .resetPriorityAfterLockedSection();
                                                }
                                            }
                                            WindowManagerService.resetPriorityAfterLockedSection();
                                            if (z
                                                    && !taskPositioner2.mTmpRect.equals(
                                                            taskPositioner2.mWindowDragBounds)) {
                                                WindowManagerGlobalLock windowManagerGlobalLock3 =
                                                        taskPositioner2.mService.mGlobalLock;
                                                WindowManagerService
                                                        .boostPriorityForLockedSection();
                                                synchronized (windowManagerGlobalLock3) {
                                                    try {
                                                        ActivityTaskManagerService
                                                                activityTaskManagerService =
                                                                        taskPositioner2
                                                                                .mService
                                                                                .mAtmService;
                                                        ActivityManagerPerformance
                                                                activityManagerPerformance =
                                                                        activityTaskManagerService
                                                                                .mAMBooster;
                                                        if (activityManagerPerformance != null
                                                                && (activityRecord =
                                                                                activityTaskManagerService
                                                                                        .mLastResumedActivity)
                                                                        != null) {
                                                            activityManagerPerformance
                                                                    .onActivityRelaunchLocked(
                                                                            activityRecord);
                                                        }
                                                    } catch (Throwable th) {
                                                        throw th;
                                                    }
                                                }
                                                WindowManagerService
                                                        .resetPriorityAfterLockedSection();
                                                taskPositioner2.mService.mAtmService.resizeTask(
                                                        taskPositioner2.mTask.mTaskId,
                                                        taskPositioner2.mWindowDragBounds,
                                                        3);
                                            }
                                            taskPositioner2.mService.mTaskPositioningController
                                                    .finishTaskPositioning$1();
                                        }
                                    }
                                    return true;
                                }
                            });
            Binder binder = new Binder();
            long j = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
            taskPositioner.mDragApplicationHandle =
                    new InputApplicationHandle(binder, "TaskPositioner", j);
            InputWindowHandle inputWindowHandle =
                    new InputWindowHandle(
                            taskPositioner.mDragApplicationHandle, displayContent.mDisplayId);
            taskPositioner.mDragWindowHandle = inputWindowHandle;
            inputWindowHandle.name = "TaskPositioner";
            inputWindowHandle.token = taskPositioner.mClientChannel.getToken();
            InputWindowHandle inputWindowHandle2 = taskPositioner.mDragWindowHandle;
            inputWindowHandle2.layoutParamsType = 2016;
            inputWindowHandle2.dispatchingTimeoutMillis = j;
            inputWindowHandle2.ownerPid = WindowManagerService.MY_PID;
            inputWindowHandle2.ownerUid = WindowManagerService.MY_UID;
            inputWindowHandle2.scaleFactor = 1.0f;
            inputWindowHandle2.inputConfig = 4;
            inputWindowHandle2.touchableRegion.setEmpty();
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION, 3007492640459931179L, 0, null, null);
            }
            taskPositioner.mDisplayContent.mDisplayRotation.mDeferredRotationPauseCount++;
            TaskPositioningController taskPositioningController =
                    taskPositioner.mService.mTaskPositioningController;
            int displayId = windowState.getDisplayId();
            DisplayContent displayContent2 = taskPositioningController.mPositioningDisplay;
            if (displayContent2 == null || displayContent2.mDisplayId != displayId) {
                completedFuture = CompletableFuture.completedFuture(null);
            } else {
                WindowManagerService windowManagerService = taskPositioningController.mService;
                DisplayContent displayContent3 =
                        windowManagerService.mRoot.getDisplayContent(displayId);
                SurfaceControl surfaceControl = taskPositioningController.mInputSurface;
                if (surfaceControl == null) {
                    taskPositioningController.mInputSurface =
                            windowManagerService
                                    .makeSurfaceBuilder(displayContent3.mSession)
                                    .setContainerLayer()
                                    .setName("Drag and Drop Input Consumer")
                                    .setCallsite("TaskPositioningController.showInputSurface")
                                    .setParent(displayContent3.mOverlayLayer)
                                    .build();
                } else if (CoreRune.MW_SHELL_FREEFORM_TASK_POSITIONER) {
                    taskPositioningController.mTransaction.reparent(
                            surfaceControl, displayContent3.mOverlayLayer);
                }
                TaskPositioner taskPositioner2 = taskPositioningController.mTaskPositioner;
                InputWindowHandle inputWindowHandle3 =
                        taskPositioner2 != null ? taskPositioner2.mDragWindowHandle : null;
                if (inputWindowHandle3 == null) {
                    Slog.w(
                            "WindowManager",
                            "Drag is in progress but there is no drag window handle.");
                    completedFuture = CompletableFuture.completedFuture(null);
                } else {
                    Display display = displayContent3.mDisplay;
                    Point point = new Point();
                    display.getRealSize(point);
                    taskPositioningController.mTmpClipRect.set(0, 0, point.x, point.y);
                    completedFuture = new CompletableFuture();
                    if (!displayContent3.hasOneHandOpSpec()
                            || (magnificationSpec =
                                            taskPositioningController
                                                    .mPositioningDisplay
                                                    .mMagnificationSpec)
                                    == null) {
                        taskPositioningController
                                .mTransaction
                                .show(taskPositioningController.mInputSurface)
                                .setInputWindowInfo(
                                        taskPositioningController.mInputSurface, inputWindowHandle3)
                                .setLayer(
                                        taskPositioningController.mInputSurface, Integer.MAX_VALUE)
                                .setPosition(
                                        taskPositioningController.mInputSurface,
                                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                                        FullScreenMagnificationGestureHandler.MAX_SCALE)
                                .setCrop(
                                        taskPositioningController.mInputSurface,
                                        taskPositioningController.mTmpClipRect)
                                .addWindowInfosReportedListener(
                                        new TaskPositioningController$$ExternalSyntheticLambda2(
                                                1, completedFuture))
                                .apply();
                    } else {
                        SurfaceControl.Transaction layer =
                                taskPositioningController
                                        .mTransaction
                                        .show(taskPositioningController.mInputSurface)
                                        .setInputWindowInfo(
                                                taskPositioningController.mInputSurface,
                                                inputWindowHandle3)
                                        .setLayer(
                                                taskPositioningController.mInputSurface,
                                                Integer.MAX_VALUE);
                        SurfaceControl surfaceControl2 = taskPositioningController.mInputSurface;
                        float f3 = magnificationSpec.scale;
                        layer.setMatrix(
                                        surfaceControl2,
                                        f3,
                                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                                        f3)
                                .setPosition(
                                        taskPositioningController.mInputSurface,
                                        magnificationSpec.offsetX,
                                        magnificationSpec.offsetY)
                                .setCrop(
                                        taskPositioningController.mInputSurface,
                                        taskPositioningController.mTmpClipRect)
                                .addWindowInfosReportedListener(
                                        new TaskPositioningController$$ExternalSyntheticLambda2(
                                                0, completedFuture))
                                .apply();
                    }
                }
            }
            thenRun =
                    completedFuture.thenRun(
                            new Runnable() { // from class:
                                             // com.android.server.wm.TaskPositioner$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TaskPositioner taskPositioner3 = TaskPositioner.this;
                                    DisplayContent displayContent4 = displayContent;
                                    WindowState windowState2 = windowState;
                                    WindowManagerGlobalLock windowManagerGlobalLock =
                                            taskPositioner3.mService.mGlobalLock;
                                    WindowManagerService.boostPriorityForLockedSection();
                                    synchronized (windowManagerGlobalLock) {
                                        try {
                                            Rect rect = taskPositioner3.mTmpRect;
                                            displayContent4.getBounds(rect);
                                            DisplayMetrics displayMetrics =
                                                    displayContent4.mDisplayMetrics;
                                            taskPositioner3.mMinVisibleWidth =
                                                    WindowManagerService.dipToPixel(
                                                            WindowState.MINIMUM_VISIBLE_WIDTH_IN_DP,
                                                            displayMetrics);
                                            taskPositioner3.mMinVisibleHeight =
                                                    WindowManagerService.dipToPixel(
                                                            32, displayMetrics);
                                            taskPositioner3.mMaxVisibleSize.set(
                                                    rect.width(), rect.height());
                                            taskPositioner3.mDragEnded = false;
                                            try {
                                                IBinder asBinder = windowState2.mClient.asBinder();
                                                taskPositioner3.mClientCallback = asBinder;
                                                asBinder.linkToDeath(taskPositioner3, 0);
                                                taskPositioner3.mTask = windowState2.getTask();
                                            } catch (RemoteException unused) {
                                                taskPositioner3.mService.mTaskPositioningController
                                                        .finishTaskPositioning$1();
                                                WindowManagerService
                                                        .resetPriorityAfterLockedSection();
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            WindowManagerService.resetPriorityAfterLockedSection();
                                            throw th;
                                        }
                                    }
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                }
                            });
        }
        return thenRun.thenApply(
                new Function() { // from class:
                    // com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda0
                    public final /* synthetic */ boolean f$3;
                    public final /* synthetic */ boolean f$4;

                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Boolean bool;
                        TaskPositioningController taskPositioningController2 =
                                TaskPositioningController.this;
                        WindowState windowState2 = windowState;
                        DisplayContent displayContent4 = displayContent;
                        float f4 = f;
                        float f5 = f2;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                taskPositioningController2.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                WindowState windowState3 = displayContent4.mCurrentFocus;
                                if (windowState3 != null
                                        && windowState3 != windowState2
                                        && windowState3.mActivityRecord
                                                == windowState2.mActivityRecord) {
                                    windowState2 = windowState3;
                                }
                                if (taskPositioningController2.mService.mInputManager
                                        .transferTouchGesture(
                                                windowState2.mInputChannel.getToken(),
                                                taskPositioningController2.mTaskPositioner
                                                        .mClientChannel.getToken())) {
                                    taskPositioningController2.mTaskPositioner.startDrag(f4, f5);
                                    bool = Boolean.TRUE;
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                } else {
                                    Slog.e(
                                            "WindowManager",
                                            "startPositioningLocked: Unable to transfer touch"
                                                + " focus");
                                    taskPositioningController2.cleanUpTaskPositioner();
                                    bool = Boolean.FALSE;
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        return bool;
                    }
                });
    }
}
