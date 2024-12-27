package com.android.server.wm;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import android.view.DragEvent;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityManager;
import android.window.IGlobalDragListener;
import android.window.IUnhandledDragCallback;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.window.flags.Flags;

import com.samsung.android.rune.CoreRune;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public final class DragDropController {
    public Task mDragSourceTask;
    public DragState mDragState;
    public IGlobalDragListener mGlobalDragListener;
    public final DragHandler mHandler;
    public final WindowManagerService mService;
    public boolean mUpdateTaskVisibilityAfterDragClosed = false;
    public final AnonymousClass1 mGlobalDragListenerDeathRecipient =
            new IBinder.DeathRecipient() { // from class: com.android.server.wm.DragDropController.1
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            DragDropController.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (DragDropController.this.mHandler.hasMessages(4)) {
                                DragDropController.this.onUnhandledDropCallback(false);
                            }
                            DragDropController.this.setGlobalDragListener(null);
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            };
    public final AtomicReference mCallback = new AtomicReference(new AnonymousClass2());

    /* renamed from: com.android.server.wm.DragDropController$2, reason: invalid class name */
    public final class AnonymousClass2 implements WindowManagerInternal.IDragDropCallback {}

    public final class DragHandler extends Handler {
        public final WindowManagerService mService;

        public DragHandler(WindowManagerService windowManagerService, Looper looper) {
            super(looper);
            this.mService = windowManagerService;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                Slog.w("WindowManager", "Timeout ending drag to win " + ((IBinder) message.obj));
                WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        DragState dragState = DragDropController.this.mDragState;
                        if (dragState != null) {
                            dragState.endDragLocked(false, false);
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 1) {
                Slog.d("WindowManager", "Drag ending; tearing down input channel");
                DragState.InputInterceptor inputInterceptor =
                        (DragState.InputInterceptor) message.obj;
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
                        DragState dragState2 = DragDropController.this.mDragState;
                        if (dragState2 == null) {
                            Slog.wtf(
                                    "WindowManager",
                                    "mDragState unexpectedly became null while playing animation");
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        } else {
                            dragState2.closeLocked(false);
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
                        ((SurfaceControl.Transaction) this.mService.mTransactionFactory.get())
                                .remove((SurfaceControl) message.obj)
                                .apply();
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 4) {
                WindowManagerGlobalLock windowManagerGlobalLock5 = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock5) {
                    try {
                        DragDropController.this.onUnhandledDropCallback(false);
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i != 100) {
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock6 = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock6) {
                try {
                    DragDropController dragDropController = DragDropController.this;
                    if (dragDropController.mUpdateTaskVisibilityAfterDragClosed) {
                        dragDropController.mUpdateTaskVisibilityAfterDragClosed = false;
                        this.mService.mAtmService.mRootWindowContainer.ensureActivitiesVisible(
                                true, null);
                        this.mService.mAtmService.mTaskSupervisor.activityIdleInternal(
                                null, false, true, null);
                    }
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public DragDropController(WindowManagerService windowManagerService, Looper looper) {
        this.mService = windowManagerService;
        this.mHandler = new DragHandler(windowManagerService, looper);
    }

    public final void cancelDragAndDrop(IBinder iBinder, boolean z) {
        Slog.d("WindowManager", "cancelDragAndDrop");
        ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).getClass();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DragState dragState = this.mDragState;
                    if (dragState == null) {
                        Slog.w("WindowManager", "cancelDragAndDrop() without prepareDrag()");
                        throw new IllegalStateException(
                                "cancelDragAndDrop() without prepareDrag()");
                    }
                    if (dragState.mToken != iBinder) {
                        Slog.w("WindowManager", "cancelDragAndDrop() does not match prepareDrag()");
                        throw new IllegalStateException(
                                "cancelDragAndDrop() does not match prepareDrag()");
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
            ((WindowManagerInternal.IDragDropCallback) this.mCallback.get()).getClass();
        }
    }

    public final float dpToPixel(int i) {
        return (this.mService.mContext.getResources().getDisplayMetrics().densityDpi / 160.0f) * i;
    }

    public final boolean dragDropActiveLocked() {
        DragState dragState = this.mDragState;
        return (dragState == null || dragState.mIsClosing) ? false : true;
    }

    public boolean dragSurfaceRelinquishedToDropTarget() {
        DragState dragState = this.mDragState;
        return dragState != null && dragState.mRelinquishDragSurfaceToDropTarget;
    }

    public final boolean dropForAccessibility(IWindow iWindow, float f, float f2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean isEnabled =
                        ((AccessibilityManager)
                                        this.mService.mContext.getSystemService("accessibility"))
                                .isEnabled();
                if (!dragDropActiveLocked()) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if ((this.mDragState.mFlags & 1024) == 0 || !isEnabled) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                WindowState windowForClientLocked =
                        this.mService.windowForClientLocked((Session) null, iWindow, false);
                if (!this.mDragState.isWindowNotified(windowForClientLocked)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean reportDropWindowLock =
                        this.mDragState.reportDropWindowLock(
                                windowForClientLocked.mInputChannelToken, f, f2);
                WindowManagerService.resetPriorityAfterLockedSection();
                return reportDropWindowLock;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public final void handleDragEvent() {
        if (CoreRune.MT_DND_ANIMATION) {
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

    public final void handleMotionEvent(boolean z, float f, float f2) {
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

    public final boolean notifyUnhandledDrop(DragEvent dragEvent, String str) {
        int i = this.mDragState.mFlags;
        boolean z = (i & 4352) == 0;
        boolean z2 = (i & 8192) != 0;
        if (!Flags.delegateUnhandledDrags() || this.mGlobalDragListener == null || !z2 || z) {
            StringBuilder sb = new StringBuilder("Skipping unhandled listener (listener=");
            sb.append(this.mGlobalDragListener);
            sb.append(", flags=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, this.mDragState.mFlags, ")", "WindowManager");
            return false;
        }
        final int nextInt = new Random().nextInt();
        Trace.asyncTraceBegin(32L, "DragDropController#notifyUnhandledDrop", nextInt);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "Sending DROP to unhandled listener (", str, ")", "WindowManager");
        try {
            DragHandler dragHandler = this.mHandler;
            dragHandler.removeMessages(4, null);
            dragHandler.sendMessageDelayed(dragHandler.obtainMessage(4, null), 5000L);
            this.mGlobalDragListener.onUnhandledDrop(
                    dragEvent,
                    new IUnhandledDragCallback
                            .Stub() { // from class: com.android.server.wm.DragDropController.3
                        public final void notifyUnhandledDropComplete(boolean z3) {
                            Slog.d("WindowManager", "Unhandled listener finished handling DROP");
                            WindowManagerGlobalLock windowManagerGlobalLock =
                                    DragDropController.this.mService.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    DragDropController.this.onUnhandledDropCallback(z3);
                                    Trace.asyncTraceEnd(
                                            32L, "DragDropController#notifyUnhandledDrop", nextInt);
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    });
            return true;
        } catch (RemoteException e) {
            Slog.e("WindowManager", "Failed to call global drag listener for unhandled drop", e);
            return false;
        }
    }

    public void onUnhandledDropCallback(boolean z) {
        this.mHandler.removeMessages(4, null);
        DragState dragState = this.mDragState;
        dragState.mDragResult = z;
        dragState.mRelinquishDragSurfaceToDropTarget = z;
        dragState.closeLocked(false);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.IBinder performDragWithArea(
            int r23,
            int r24,
            android.view.IWindow r25,
            int r26,
            android.view.SurfaceControl r27,
            int r28,
            int r29,
            int r30,
            float r31,
            float r32,
            float r33,
            float r34,
            android.content.ClipData r35,
            android.graphics.RectF r36) {
        /*
            Method dump skipped, instructions count: 1076
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DragDropController.performDragWithArea(int, int,"
                    + " android.view.IWindow, int, android.view.SurfaceControl, int, int, int,"
                    + " float, float, float, float, android.content.ClipData,"
                    + " android.graphics.RectF):android.os.IBinder");
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00cc, code lost:

       if (r2.type == 2024) goto L42;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportDropResult(android.view.IWindow r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DragDropController.reportDropResult(android.view.IWindow,"
                    + " boolean):void");
    }

    public final void setGlobalDragListener(IGlobalDragListener iGlobalDragListener) {
        IGlobalDragListener iGlobalDragListener2 = this.mGlobalDragListener;
        AnonymousClass1 anonymousClass1 = this.mGlobalDragListenerDeathRecipient;
        if (iGlobalDragListener2 != null && iGlobalDragListener2.asBinder() != null) {
            this.mGlobalDragListener.asBinder().unlinkToDeath(anonymousClass1, 0);
        }
        this.mGlobalDragListener = iGlobalDragListener;
        if (iGlobalDragListener == null || iGlobalDragListener.asBinder() == null) {
            return;
        }
        try {
            this.mGlobalDragListener.asBinder().linkToDeath(anonymousClass1, 0);
        } catch (RemoteException unused) {
            this.mGlobalDragListener = null;
        }
    }
}
