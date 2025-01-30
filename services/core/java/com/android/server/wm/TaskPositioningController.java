package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Slog;
import android.view.Display;
import android.view.IWindow;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;
import com.android.server.display.DisplayPowerController2;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class TaskPositioningController {
    public SurfaceControl mInputSurface;
    public DisplayContent mPositioningDisplay;
    public final WindowManagerService mService;
    public TaskPositioner mTaskPositioner;
    public final Rect mTmpClipRect = new Rect();
    public final SurfaceControl.Transaction mTransaction;

    public InputWindowHandle getDragWindowHandleLocked() {
        TaskPositioner taskPositioner = this.mTaskPositioner;
        if (taskPositioner != null) {
            return taskPositioner.mDragWindowHandle;
        }
        return null;
    }

    public TaskPositioningController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
    }

    public void hideInputSurface(int i) {
        SurfaceControl surfaceControl;
        DisplayContent displayContent = this.mPositioningDisplay;
        if (displayContent == null || displayContent.getDisplayId() != i || (surfaceControl = this.mInputSurface) == null) {
            return;
        }
        this.mTransaction.hide(surfaceControl).apply();
    }

    public CompletableFuture showInputSurface(int i) {
        DisplayContent displayContent = this.mPositioningDisplay;
        if (displayContent == null || displayContent.getDisplayId() != i) {
            return CompletableFuture.completedFuture(null);
        }
        DisplayContent displayContent2 = this.mService.mRoot.getDisplayContent(i);
        SurfaceControl surfaceControl = this.mInputSurface;
        if (surfaceControl == null) {
            this.mInputSurface = this.mService.makeSurfaceBuilder(displayContent2.getSession()).setContainerLayer().setName("Drag and Drop Input Consumer").setCallsite("TaskPositioningController.showInputSurface").setParent(displayContent2.getOverlayLayer()).build();
        } else {
            displayContent2.reparentToOverlay(this.mTransaction, surfaceControl);
        }
        InputWindowHandle dragWindowHandleLocked = getDragWindowHandleLocked();
        if (dragWindowHandleLocked == null) {
            Slog.w(StartingSurfaceController.TAG, "Drag is in progress but there is no drag window handle.");
            return CompletableFuture.completedFuture(null);
        }
        Display display = displayContent2.getDisplay();
        Point point = new Point();
        display.getRealSize(point);
        this.mTmpClipRect.set(0, 0, point.x, point.y);
        final CompletableFuture completableFuture = new CompletableFuture();
        this.mTransaction.show(this.mInputSurface).setInputWindowInfo(this.mInputSurface, dragWindowHandleLocked).setLayer(this.mInputSurface, Integer.MAX_VALUE).setPosition(this.mInputSurface, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON).setCrop(this.mInputSurface, this.mTmpClipRect).addWindowInfosReportedListener(new Runnable() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                completableFuture.complete(null);
            }
        }).apply();
        return completableFuture;
    }

    public boolean startMovingTask(IWindow iWindow, float f, float f2) {
        WindowState windowForClientLocked;
        CompletableFuture startPositioningLocked;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowForClientLocked = this.mService.windowForClientLocked((Session) null, iWindow, false);
                startPositioningLocked = startPositioningLocked(windowForClientLocked, false, false, f, f2);
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
                    this.mService.mAtmService.setFocusedTask(windowForClientLocked.getTask().mTaskId);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return true;
        } catch (Exception e) {
            Slog.e(StartingSurfaceController.TAG, "Exception thrown while waiting for startPositionLocked future", e);
            return false;
        }
    }

    public void handleTapOutsideTask(final DisplayContent displayContent, final int i, final int i2) {
        this.mService.f1749mH.post(new Runnable() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TaskPositioningController.this.lambda$handleTapOutsideTask$2(displayContent, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleTapOutsideTask$2(DisplayContent displayContent, int i, int i2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task findTaskForResizePoint = displayContent.findTaskForResizePoint(i, i2);
                if (findTaskForResizePoint != null && findTaskForResizePoint.isResizeable()) {
                    CompletableFuture startPositioningLocked = startPositioningLocked(findTaskForResizePoint.getTopVisibleAppMainWindow(), true, findTaskForResizePoint.preserveOrientationOnResize(), i, i2);
                    try {
                        if (((Boolean) startPositioningLocked.get()).booleanValue()) {
                            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock2) {
                                try {
                                    this.mService.mAtmService.setFocusedTask(findTaskForResizePoint.mTaskId);
                                } finally {
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        Slog.e(StartingSurfaceController.TAG, "Exception thrown while waiting for startPositionLocked future", e);
                        return;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    public final CompletableFuture startPositioningLocked(final WindowState windowState, final boolean z, final boolean z2, final float f, final float f2) {
        if (windowState == null || windowState.mActivityRecord == null) {
            Slog.w(StartingSurfaceController.TAG, "startPositioningLocked: Bad window " + windowState);
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        if (windowState.mInputChannel == null) {
            Slog.wtf(StartingSurfaceController.TAG, "startPositioningLocked: " + windowState + " has no input channel,  probably being removed");
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        final DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent == null) {
            Slog.w(StartingSurfaceController.TAG, "startPositioningLocked: Invalid display content " + windowState);
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        this.mPositioningDisplay = displayContent;
        TaskPositioner create = TaskPositioner.create(this.mService);
        this.mTaskPositioner = create;
        return create.register(displayContent, windowState).thenApply(new Function() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$startPositioningLocked$3;
                lambda$startPositioningLocked$3 = TaskPositioningController.this.lambda$startPositioningLocked$3(windowState, displayContent, z, z2, f, f2, (Void) obj);
                return lambda$startPositioningLocked$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$startPositioningLocked$3(WindowState windowState, DisplayContent displayContent, boolean z, boolean z2, float f, float f2, Void r9) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState2 = displayContent.mCurrentFocus;
                if (windowState2 != null && windowState2 != windowState && windowState2.mActivityRecord == windowState.mActivityRecord) {
                    windowState = windowState2;
                }
                if (!this.mService.mInputManager.transferTouchFocus(windowState.mInputChannel, this.mTaskPositioner.mClientChannel, false)) {
                    Slog.e(StartingSurfaceController.TAG, "startPositioningLocked: Unable to transfer touch focus");
                    cleanUpTaskPositioner();
                    Boolean bool = Boolean.FALSE;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return bool;
                }
                this.mTaskPositioner.startDrag(z, z2, f, f2);
                Boolean bool2 = Boolean.TRUE;
                WindowManagerService.resetPriorityAfterLockedSection();
                return bool2;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void finishTaskPositioning(IWindow iWindow) {
        TaskPositioner taskPositioner = this.mTaskPositioner;
        if (taskPositioner == null || taskPositioner.mClientCallback != iWindow.asBinder()) {
            return;
        }
        finishTaskPositioning();
    }

    public void finishTaskPositioning() {
        this.mService.mAnimationHandler.post(new Runnable() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TaskPositioningController.this.lambda$finishTaskPositioning$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishTaskPositioning$4() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                cleanUpTaskPositioner();
                this.mPositioningDisplay = null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void cleanUpTaskPositioner() {
        TaskPositioner taskPositioner = this.mTaskPositioner;
        if (taskPositioner == null) {
            return;
        }
        this.mTaskPositioner = null;
        taskPositioner.unregister();
    }
}
