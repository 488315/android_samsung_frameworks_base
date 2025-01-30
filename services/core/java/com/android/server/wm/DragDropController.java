package com.android.server.wm;

import android.content.ClipData;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;
import android.view.IWindow;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityManager;
import com.android.server.wm.DragState;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public class DragDropController {
    public Task mDragSourceTask;
    public DragState mDragState;
    public final Handler mHandler;
    public WindowManagerService mService;
    public boolean mUpdateTaskVisibilityAfterDragClosed = false;
    public AtomicReference mCallback = new AtomicReference(new WindowManagerInternal.IDragDropCallback() { // from class: com.android.server.wm.DragDropController.1
    });

    public DragDropController(WindowManagerService windowManagerService, Looper looper) {
        this.mService = windowManagerService;
        this.mHandler = new DragHandler(windowManagerService, looper);
    }

    public boolean dragDropActiveLocked() {
        DragState dragState = this.mDragState;
        return (dragState == null || dragState.isClosing()) ? false : true;
    }

    public void registerCallback(WindowManagerInternal.IDragDropCallback iDragDropCallback) {
        Objects.requireNonNull(iDragDropCallback);
        this.mCallback.set(iDragDropCallback);
    }

    public void sendDragStartedIfNeededLocked(WindowState windowState, boolean z) {
        this.mDragState.sendDragStartedIfNeededLocked(windowState, z);
    }

    public void updateClipData(ClipData clipData, int i) {
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DragState dragState = this.mDragState;
                    if (dragState == null) {
                        Slog.w(StartingSurfaceController.TAG, "updateClipData: there is no clipdata to be updated.");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (dragState.mUid == i) {
                        ClipData clipData2 = dragState.mData;
                        if (clipData2 != null && clipData != null) {
                            int itemCount = clipData2.getItemCount();
                            int itemCount2 = clipData.getItemCount();
                            DragState dragState2 = this.mDragState;
                            dragState2.mData = clipData;
                            if (itemCount2 > itemCount) {
                                dragState2.notifyUpdateClipDataLocked();
                            }
                            this.mDragState.restartDragLocked(clipData);
                        } else {
                            Slog.w(StartingSurfaceController.TAG, "updateClipData: wrong clipdata mData=" + this.mDragState.mData + " data=" + clipData);
                        }
                    } else {
                        Slog.w(StartingSurfaceController.TAG, "updateClipData: caller's uid is not valid.");
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Exception e) {
            Slog.w(StartingSurfaceController.TAG, "updateClipData: exception e=" + e);
        }
    }

    public IBinder performDrag(int i, int i2, IWindow iWindow, int i3, SurfaceControl surfaceControl, int i4, float f, float f2, float f3, float f4, ClipData clipData) {
        return performDragWithArea(i, i2, iWindow, i3, surfaceControl, i4, f, f2, f3, f4, clipData, null, null);
    }

    /* JADX WARN: Finally extract failed */
    public IBinder performDragWithArea(int i, int i2, IWindow iWindow, int i3, SurfaceControl surfaceControl, int i4, float f, float f2, float f3, float f4, ClipData clipData, RectF rectF, Point point) {
        SurfaceControl surfaceControl2;
        Task task;
        AtomicReference atomicReference;
        Object obj;
        WindowManagerInternal.IDragDropCallback iDragDropCallback;
        boolean z;
        Slog.d(StartingSurfaceController.TAG, "perform drag: win=" + iWindow + " surface=" + surfaceControl + " flags=" + Integer.toHexString(i3) + " data=" + clipData + " touch(" + f + "," + f2 + ") thumb center(" + f3 + "," + f4 + ")");
        Binder binder = new Binder();
        boolean prePerformDrag = ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).prePerformDrag(iWindow, binder, i4, f, f2, f3, f4, clipData);
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    try {
                        if (prePerformDrag) {
                            try {
                                if (!dragDropActiveLocked()) {
                                    WindowState windowForClientLocked = this.mService.windowForClientLocked((Session) null, iWindow, false);
                                    if (windowForClientLocked != null && windowForClientLocked.canReceiveTouchInput()) {
                                        DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                                        if (displayContent != null) {
                                            if (displayContent.isDefaultDisplay) {
                                                WindowState windowState = (WindowState) this.mService.mWindowMap.get(iWindow.asBinder());
                                                task = windowState != null ? windowState.getTask() : null;
                                                if ((i3 & 1073741824) != 0 && (clipData == null || task == null || !task.inFullscreenWindowingMode())) {
                                                    Slog.d(StartingSurfaceController.TAG, "Not support to launch multi-window mode with drag-and-drop if your app is in multi-window mode.");
                                                    if (surfaceControl != null) {
                                                        surfaceControl.release();
                                                    }
                                                }
                                            } else {
                                                task = null;
                                            }
                                            float f5 = (i3 & 512) == 0 ? 0.7071f : 1.0f;
                                            Task task2 = task;
                                            DragState dragState = new DragState(this.mService, this, new Binder(), surfaceControl, i3, iWindow.asBinder());
                                            this.mDragState = dragState;
                                            try {
                                                dragState.mPid = i;
                                                dragState.mUid = i2;
                                                dragState.mOriginalAlpha = f5;
                                                dragState.mAnimatedScale = windowForClientLocked.mGlobalScale;
                                                dragState.mToken = binder;
                                                dragState.mDisplayContent = displayContent;
                                                dragState.mData = clipData;
                                                this.mUpdateTaskVisibilityAfterDragClosed = false;
                                                if (task2 != null && (i3 & 256) != 0) {
                                                    Slog.d(StartingSurfaceController.TAG, "[TWODND] Set DragSourceTask=" + task2);
                                                    task2.mIsDragSourceTask = true;
                                                    this.mDragSourceTask = task2;
                                                    if ((1073741824 & i3) != 0) {
                                                        clipData.getDescription().setDragSourceTaskId(task2.mTaskId);
                                                        task2.mHiddenWhileActivatingDrag = true;
                                                        task2.mIsAnimatingByRecentsAndDragSourceTask = true;
                                                        task2.updateSurfaceVisibilityForDragAndDrop();
                                                    }
                                                }
                                                if (CoreRune.FW_DND_OBJECT_CAPTURE && (4194304 & i3) != 0 && rectF != null) {
                                                    DragState dragState2 = this.mDragState;
                                                    dragState2.mIsObjectCapture = true;
                                                    dragState2.mObjectCaptureRect = rectF;
                                                }
                                                if ((i3 & 1024) != 0) {
                                                    this.mDragState.broadcastDragStartedLocked(f, f2);
                                                    sendTimeoutMessage(0, windowForClientLocked.mClient.asBinder(), getAccessibilityManager().getRecommendedTimeoutMillis(60000, 4));
                                                    WindowManagerService.resetPriorityAfterLockedSection();
                                                    return binder;
                                                }
                                                CompletableFuture registerInputChannel = ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).registerInputChannel(this.mDragState, displayContent.getDisplay(), this.mService.mInputManager, windowForClientLocked.mInputChannel);
                                                this.mDragState.mCallingPackageName = windowForClientLocked.getOwningPackage();
                                                if ((2097152 & i3) != 0 && clipData != null) {
                                                    if (windowForClientLocked.isActivityTypeHomeOrRecents()) {
                                                        clipData.getDescription().setDragFromRecent(true);
                                                        this.mDragState.mDragInProgressByRecents = true;
                                                    } else {
                                                        clipData.getDescription().setDragFromRecent(false);
                                                    }
                                                }
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                                try {
                                                    z = ((Boolean) registerInputChannel.get(5000L, TimeUnit.MILLISECONDS)).booleanValue();
                                                } catch (Exception e) {
                                                    Slog.e(StartingSurfaceController.TAG, "Exception thrown while waiting for touch focus transfer", e);
                                                    z = false;
                                                }
                                                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                                                WindowManagerService.boostPriorityForLockedSection();
                                                synchronized (windowManagerGlobalLock2) {
                                                    try {
                                                        if (!z) {
                                                            Slog.e(StartingSurfaceController.TAG, "Unable to transfer touch focus");
                                                            this.mDragState.closeLocked();
                                                            WindowManagerService.resetPriorityAfterLockedSection();
                                                            ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).postPerformDrag();
                                                            return null;
                                                        }
                                                        DragState dragState3 = this.mDragState;
                                                        SurfaceControl surfaceControl3 = dragState3.mSurfaceControl;
                                                        dragState3.broadcastDragStartedLocked(f, f2);
                                                        this.mDragState.overridePointerIconLocked(i4);
                                                        DragState dragState4 = this.mDragState;
                                                        dragState4.mThumbOffsetX = f3;
                                                        dragState4.mThumbOffsetY = f4;
                                                        if (CoreRune.FW_DND_ANIMATION && clipData != null) {
                                                            Slog.d(StartingSurfaceController.TAG, clipData.getDescription().toString());
                                                            if (clipData.getDescription().hasMimeType("image/*")) {
                                                                this.mDragState.mMimeType = 0;
                                                            } else if (clipData.getDescription().hasMimeType("text/*")) {
                                                                this.mDragState.mMimeType = 1;
                                                            }
                                                        }
                                                        SurfaceControl.Transaction transaction = this.mDragState.mTransaction;
                                                        transaction.setPosition(surfaceControl3, f - f3, f2 - f4);
                                                        transaction.setAlpha(surfaceControl3, this.mDragState.mOriginalAlpha);
                                                        transaction.show(surfaceControl3);
                                                        displayContent.reparentToOverlay(transaction, surfaceControl3);
                                                        if (CoreRune.FW_DND_ANIMATION) {
                                                            transaction.apply();
                                                        }
                                                        this.mDragState.updateDragSurfaceLocked(true, f, f2);
                                                        if (CoreRune.FW_DND_ANIMATION) {
                                                            handleDragEvent();
                                                        }
                                                        WindowManagerService.resetPriorityAfterLockedSection();
                                                        return binder;
                                                    } catch (Throwable th) {
                                                        WindowManagerService.resetPriorityAfterLockedSection();
                                                        throw th;
                                                    }
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                                surfaceControl2 = null;
                                                if (surfaceControl2 != null) {
                                                    surfaceControl2.release();
                                                }
                                                throw th;
                                            }
                                        }
                                        Slog.w(StartingSurfaceController.TAG, "display content is null");
                                        if (surfaceControl != null) {
                                            surfaceControl.release();
                                        }
                                    }
                                    Slog.w(StartingSurfaceController.TAG, "Bad requesting window " + iWindow);
                                    if (surfaceControl != null) {
                                        surfaceControl.release();
                                    }
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).postPerformDrag();
                                    return null;
                                }
                                Slog.w(StartingSurfaceController.TAG, "Drag already in progress");
                                if (surfaceControl != null) {
                                    surfaceControl.release();
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                surfaceControl2 = surfaceControl;
                            }
                        } else {
                            Slog.w(StartingSurfaceController.TAG, "IDragDropCallback rejects the performDrag request");
                            if (surfaceControl != null) {
                                surfaceControl.release();
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    } catch (Throwable th4) {
                        th = th4;
                        surfaceControl2 = surfaceControl;
                    }
                } catch (Throwable th5) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th5;
                }
            }
        } finally {
            ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).postPerformDrag();
        }
    }

    /* JADX WARN: Finally extract failed */
    public void reportDropResult(IWindow iWindow, boolean z) {
        IBinder asBinder = iWindow.asBinder();
        Slog.d(StartingSurfaceController.TAG, "Drop result=" + z + " reported by " + asBinder);
        ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).preReportDropResult(iWindow, z);
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DragState dragState = this.mDragState;
                    if (dragState == null) {
                        Slog.w(StartingSurfaceController.TAG, "Drop result given but no drag in progress");
                    } else {
                        if (dragState.mToken != asBinder) {
                            Slog.w(StartingSurfaceController.TAG, "Invalid drop-result claim by " + iWindow);
                            throw new IllegalStateException("reportDropResult() by non-recipient");
                        }
                        this.mHandler.removeMessages(0, iWindow.asBinder());
                        WindowState windowForClientLocked = this.mService.windowForClientLocked((Session) null, iWindow, false);
                        if (windowForClientLocked == null) {
                            Slog.w(StartingSurfaceController.TAG, "Bad result-reporting window " + iWindow);
                        } else {
                            DragState dragState2 = this.mDragState;
                            dragState2.mDragResult = z;
                            dragState2.mRelinquishDragSurfaceToDropTarget = z && dragState2.targetInterceptsGlobalDrag(windowForClientLocked);
                            if (z && (windowForClientLocked.isEavesdropDragEvent() || (this.mDragState.mRelinquishDragSurfaceToDropTarget && windowForClientLocked.getWindowType() == 2024))) {
                                DragState dragState3 = this.mDragState;
                                dragState3.mRelinquishDragSurfaceToDropTarget = false;
                                dragState3.mFlags &= -2049;
                            }
                            this.mDragState.endDragLocked();
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).postReportDropResult();
        }
    }

    public void cancelDragAndDrop(IBinder iBinder, boolean z) {
        Slog.d(StartingSurfaceController.TAG, "cancelDragAndDrop");
        ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).preCancelDragAndDrop(iBinder);
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DragState dragState = this.mDragState;
                    if (dragState == null) {
                        Slog.w(StartingSurfaceController.TAG, "cancelDragAndDrop() without prepareDrag()");
                        throw new IllegalStateException("cancelDragAndDrop() without prepareDrag()");
                    }
                    if (dragState.mToken != iBinder) {
                        Slog.w(StartingSurfaceController.TAG, "cancelDragAndDrop() does not match prepareDrag()");
                        throw new IllegalStateException("cancelDragAndDrop() does not match prepareDrag()");
                    }
                    dragState.mDragResult = false;
                    dragState.cancelDragLocked(z);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).postCancelDragAndDrop();
        }
    }

    public void handleMotionEvent(boolean z, float f, float f2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!dragDropActiveLocked()) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (z) {
                    this.mDragState.notifyLocationToEavesdropDragEventWindowLocked(f, f2);
                }
                this.mDragState.updateDragSurfaceLocked(z, f, f2);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void handleDragEvent() {
        if (CoreRune.FW_DND_ANIMATION) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (dragDropActiveLocked() && this.mDragState.isAnimationSet()) {
                        this.mDragState.notifyDownEventLocked();
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public float dpToPixel(int i) {
        return i * (this.mService.mContext.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }

    public void dragRecipientEntered(IWindow iWindow) {
        Slog.d(StartingSurfaceController.TAG, "Drag into new candidate view @ " + iWindow.asBinder());
        ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).dragRecipientEntered(iWindow);
    }

    public void dragRecipientExited(IWindow iWindow) {
        Slog.d(StartingSurfaceController.TAG, "Drag from old candidate view @ " + iWindow.asBinder());
        ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).dragRecipientExited(iWindow);
    }

    public void sendHandlerMessage(int i, Object obj) {
        this.mHandler.obtainMessage(i, obj).sendToTarget();
    }

    public void sendTimeoutMessage(int i, Object obj, long j) {
        this.mHandler.removeMessages(i, obj);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i, obj), j);
    }

    public void onDragStateClosedLocked(DragState dragState) {
        if (this.mDragState != dragState) {
            Slog.wtf(StartingSurfaceController.TAG, "Unknown drag state is closed");
            return;
        }
        this.mDragState = null;
        if (this.mDragSourceTask != null) {
            Slog.d(StartingSurfaceController.TAG, "[TWODND] onDragStateClosedLocked");
            Task task = this.mDragSourceTask;
            task.mIsDragSourceTask = false;
            task.mHiddenWhileActivatingDrag = false;
            this.mDragSourceTask = null;
            if (this.mUpdateTaskVisibilityAfterDragClosed) {
                this.mHandler.sendEmptyMessage(100);
            }
        }
    }

    public void reportDropWindow(IBinder iBinder, float f, float f2) {
        if (this.mDragState == null) {
            Slog.w(StartingSurfaceController.TAG, "Drag state is closed.");
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDragState.reportDropWindowLock(iBinder, f, f2);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean dropForAccessibility(IWindow iWindow, float f, float f2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean isEnabled = getAccessibilityManager().isEnabled();
                if (!dragDropActiveLocked()) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (!this.mDragState.isAccessibilityDragDrop() || !isEnabled) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                WindowState windowForClientLocked = this.mService.windowForClientLocked((Session) null, iWindow, false);
                if (!this.mDragState.isWindowNotified(windowForClientLocked)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean reportDropWindowLock = this.mDragState.reportDropWindowLock(windowForClientLocked.mInputChannelToken, f, f2);
                WindowManagerService.resetPriorityAfterLockedSection();
                return reportDropWindowLock;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public AccessibilityManager getAccessibilityManager() {
        return (AccessibilityManager) this.mService.mContext.getSystemService("accessibility");
    }

    public void notifyDragSplitAppIconHasDrawable(boolean z) {
        DragState dragState = this.mDragState;
        if (dragState != null) {
            dragState.setDragSplitAppIconHasDrawable(z);
        }
    }

    public class DragHandler extends Handler {
        public final WindowManagerService mService;

        public DragHandler(WindowManagerService windowManagerService, Looper looper) {
            super(looper);
            this.mService = windowManagerService;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                Slog.w(StartingSurfaceController.TAG, "Timeout ending drag to win " + ((IBinder) message.obj));
                WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (DragDropController.this.mDragState != null) {
                            DragDropController.this.mDragState.mDragResult = false;
                            DragDropController.this.mDragState.endDragLocked();
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 1) {
                Slog.d(StartingSurfaceController.TAG, "Drag ending; tearing down input channel");
                DragState.InputInterceptor inputInterceptor = (DragState.InputInterceptor) message.obj;
                if (inputInterceptor == null) {
                    return;
                }
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        inputInterceptor.tearDown();
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 2) {
                WindowManagerGlobalLock windowManagerGlobalLock3 = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock3) {
                    try {
                        if (DragDropController.this.mDragState == null) {
                            Slog.wtf(StartingSurfaceController.TAG, "mDragState unexpectedly became null while playing animation");
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        } else {
                            DragDropController.this.mDragState.closeLocked();
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
            }
            if (i == 3) {
                WindowManagerGlobalLock windowManagerGlobalLock4 = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock4) {
                    try {
                        ((SurfaceControl.Transaction) this.mService.mTransactionFactory.get()).reparent((SurfaceControl) message.obj, null).apply();
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i != 100) {
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock5 = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock5) {
                try {
                    if (DragDropController.this.mUpdateTaskVisibilityAfterDragClosed) {
                        DragDropController.this.mUpdateTaskVisibilityAfterDragClosed = false;
                        this.mService.mAtmService.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
                        this.mService.mAtmService.mTaskSupervisor.activityIdleInternal(null, false, true, null);
                    }
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public void setDragSurfaceToOverlay(boolean z) {
        DisplayContent displayContent;
        DragState dragState = this.mDragState;
        if (dragState == null || (displayContent = dragState.mDisplayContent) == null) {
            return;
        }
        if (z) {
            dragState.mTransaction.setRelativeLayer(dragState.mSurfaceControl, displayContent.getOverlayLayer(), -1);
            return;
        }
        WindowState windowEavesdropDragEvent = displayContent.getWindowEavesdropDragEvent();
        if (windowEavesdropDragEvent == null || windowEavesdropDragEvent.getSurfaceControl() == null) {
            return;
        }
        DragState dragState2 = this.mDragState;
        dragState2.mTransaction.setRelativeLayer(dragState2.mSurfaceControl, windowEavesdropDragEvent.getSurfaceControl(), -1);
    }

    public void dump(PrintWriter printWriter, String str) {
        if (this.mDragState != null) {
            printWriter.print(str + "DragState: ");
            printWriter.print("CallingWindowToken=" + this.mDragState.mLocalWin);
            InputWindowHandle inputWindowHandle = this.mDragState.getInputWindowHandle();
            if (inputWindowHandle != null) {
                printWriter.print(" InputWindowHandle=" + inputWindowHandle);
                printWriter.print(" Token=" + inputWindowHandle.token);
            }
            printWriter.println(" InputSurface=" + this.mDragState.mInputSurface);
        }
    }

    public void setUpdateTaskVisibilityAfterDragClosed(boolean z) {
        this.mUpdateTaskVisibilityAfterDragClosed = z;
    }

    public Task getDragSourceTask() {
        return this.mDragSourceTask;
    }

    public boolean isDragInProgressByRecents() {
        DragState dragState = this.mDragState;
        return dragState != null && dragState.mDragInProgressByRecents;
    }
}
