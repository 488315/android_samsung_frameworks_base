package com.android.server.wm;

import android.os.IBinder;
import android.util.Slog;
import android.view.InputApplicationHandle;
import android.view.KeyEvent;
import android.view.SurfaceControl;
import com.android.internal.os.SomeArgs;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.input.InputManagerService;
import com.android.server.wm.WindowManagerService;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class InputManagerCallback implements InputManagerService.WindowManagerCallbacks {
    public boolean mInputDevicesReady;
    public boolean mInputDispatchEnabled;
    public boolean mInputDispatchFrozen;
    public final WindowManagerService mService;
    public final Object mInputDevicesReadyMonitor = new Object();
    public String mInputFreezeReason = null;

    public InputManagerCallback(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyInputChannelBroken(IBinder iBinder) {
        if (iBinder == null) {
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mService.mInputToWindowMap.get(iBinder);
                if (windowState != null) {
                    Slog.i(StartingSurfaceController.TAG, "WINDOW DIED " + windowState);
                    windowState.removeIfPossible();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyNoFocusedWindowAnr(InputApplicationHandle inputApplicationHandle) {
        this.mService.mAnrController.notifyAppUnresponsive(inputApplicationHandle, TimeoutRecord.forInputDispatchNoFocusedWindow(timeoutMessage("Application does not have a focused window")));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyWindowUnresponsive(IBinder iBinder, OptionalInt optionalInt, String str) {
        this.mService.mAnrController.notifyWindowUnresponsive(iBinder, optionalInt, TimeoutRecord.forInputDispatchWindowUnresponsive(timeoutMessage(str)));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyWindowResponsive(IBinder iBinder, OptionalInt optionalInt) {
        this.mService.mAnrController.notifyWindowResponsive(iBinder, optionalInt);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyConfigurationChanged() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mService.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DisplayContent) obj).sendNewConfiguration();
                    }
                });
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        synchronized (this.mInputDevicesReadyMonitor) {
            if (!this.mInputDevicesReady) {
                this.mInputDevicesReady = true;
                this.mInputDevicesReadyMonitor.notifyAll();
            }
        }
    }

    @Override // com.android.server.input.InputManagerInternal.LidSwitchCallback
    public void notifyLidSwitchChanged(long j, boolean z) {
        this.mService.mPolicy.notifyLidSwitchChanged(j, z);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyCameraLensCoverSwitchChanged(long j, boolean z) {
        this.mService.mPolicy.notifyCameraLensCoverSwitchChanged(j, z);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int interceptKeyBeforeQueueing(KeyEvent keyEvent, int i) {
        return this.mService.mPolicy.interceptKeyBeforeQueueing(keyEvent, i);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int interceptMotionBeforeQueueingNonInteractive(int i, long j, int i2) {
        return this.mService.mPolicy.interceptMotionBeforeQueueingNonInteractive(i, j, i2);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public long interceptKeyBeforeDispatching(IBinder iBinder, KeyEvent keyEvent, int i) {
        return this.mService.mPolicy.interceptKeyBeforeDispatching(iBinder, keyEvent, i);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public KeyEvent dispatchUnhandledKey(IBinder iBinder, KeyEvent keyEvent, int i) {
        return this.mService.mPolicy.dispatchUnhandledKey(iBinder, keyEvent, i);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int getPointerLayer() {
        return (this.mService.mPolicy.getWindowLayerFromTypeLw(2018) * 10000) + 1000;
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int getPointerDisplayId() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mService.mAtmService.mDexController.getDexModeLocked() == 2) {
                    int displayIdForPointerIcon = this.mService.mInputManager.getDisplayIdForPointerIcon();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return displayIdForPointerIcon;
                }
                WindowManagerService windowManagerService = this.mService;
                int i = 0;
                if (!windowManagerService.mForceDesktopModeOnExternalDisplays) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                }
                for (int size = windowManagerService.mRoot.mChildren.size() - 1; size >= 0; size--) {
                    DisplayContent displayContent = (DisplayContent) this.mService.mRoot.mChildren.get(size);
                    if (displayContent.getDisplayInfo().state != 1 && displayContent.getDisplay().getType() != 1) {
                        if (displayContent.getWindowingMode() == 5) {
                            int displayId = displayContent.getDisplayId();
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return displayId;
                        }
                        if (i == 0 && displayContent.getDisplayId() != 0) {
                            i = displayContent.getDisplayId();
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void onPointerDownOutsideFocus(IBinder iBinder) {
        this.mService.f1749mH.obtainMessage(62, iBinder).sendToTarget();
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void onPointerDownUpCancelOutsideFocus(IBinder iBinder, int i, int i2, int i3) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = iBinder;
        obtain.argi1 = i;
        obtain.argi2 = i2;
        obtain.argi3 = i3;
        this.mService.f1749mH.obtainMessage(62, obtain).sendToTarget();
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyFocusChanged(IBinder iBinder, IBinder iBinder2) {
        final WindowManagerService windowManagerService = this.mService;
        WindowManagerService.HandlerC2998H handlerC2998H = windowManagerService.f1749mH;
        Objects.requireNonNull(windowManagerService);
        handlerC2998H.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                WindowManagerService.this.reportFocusChanged((IBinder) obj, (IBinder) obj2);
            }
        }, iBinder, iBinder2));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyDropWindow(IBinder iBinder, float f, float f2) {
        WindowManagerService windowManagerService = this.mService;
        WindowManagerService.HandlerC2998H handlerC2998H = windowManagerService.f1749mH;
        final DragDropController dragDropController = windowManagerService.mDragDropController;
        Objects.requireNonNull(dragDropController);
        handlerC2998H.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda1
            public final void accept(Object obj, Object obj2, Object obj3) {
                DragDropController.this.reportDropWindow((IBinder) obj, ((Float) obj2).floatValue(), ((Float) obj3).floatValue());
            }
        }, iBinder, Float.valueOf(f), Float.valueOf(f2)));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public SurfaceControl getParentSurfaceForPointers(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.e(StartingSurfaceController.TAG, "Failed to get parent surface for pointers on display " + i + " - DisplayContent not found.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                SurfaceControl overlayLayer = displayContent.getOverlayLayer();
                WindowManagerService.resetPriorityAfterLockedSection();
                return overlayLayer;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public SurfaceControl createSurfaceForGestureMonitor(String str, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.e(StartingSurfaceController.TAG, "Failed to create a gesture monitor on display: " + i + " - DisplayContent not found.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                SurfaceControl inputOverlayLayer = displayContent.getInputOverlayLayer();
                if (inputOverlayLayer == null) {
                    Slog.e(StartingSurfaceController.TAG, "Failed to create a gesture monitor on display: " + i + " - Input overlay layer is not initialized.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                SurfaceControl build = this.mService.makeSurfaceBuilder(displayContent.getSession()).setContainerLayer().setName(str).setCallsite("createSurfaceForGestureMonitor").setParent(inputOverlayLayer).build();
                WindowManagerService.resetPriorityAfterLockedSection();
                return build;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyPointerDisplayIdChanged(int i, float f, float f2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mService.setMousePointerDisplayId(i);
                if (i == -1) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.wtf(StartingSurfaceController.TAG, "The mouse pointer was moved to display " + i + " that does not have a valid DisplayContent.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mService.restorePointerIconLocked(displayContent, f, f2);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean waitForInputDevicesReady(long j) {
        boolean z;
        synchronized (this.mInputDevicesReadyMonitor) {
            if (!this.mInputDevicesReady) {
                try {
                    this.mInputDevicesReadyMonitor.wait(j);
                } catch (InterruptedException unused) {
                }
            }
            z = this.mInputDevicesReady;
        }
        return z;
    }

    public void freezeInputDispatchingLw() {
        if (this.mInputDispatchFrozen) {
            return;
        }
        this.mInputDispatchFrozen = true;
        updateInputDispatchModeLw();
    }

    public void thawInputDispatchingLw() {
        if (this.mInputDispatchFrozen) {
            this.mInputDispatchFrozen = false;
            this.mInputFreezeReason = null;
            updateInputDispatchModeLw();
        }
    }

    public void setEventDispatchingLw(boolean z) {
        if (this.mInputDispatchEnabled != z) {
            this.mInputDispatchEnabled = z;
            updateInputDispatchModeLw();
        }
    }

    public final void updateInputDispatchModeLw() {
        this.mService.mInputManager.setInputDispatchMode(this.mInputDispatchEnabled, this.mInputDispatchFrozen);
    }

    public final String timeoutMessage(String str) {
        if (str == null) {
            return "Input dispatching timed out";
        }
        return "Input dispatching timed out (" + str + ")";
    }

    public void dump(PrintWriter printWriter, String str) {
        if (this.mInputFreezeReason != null) {
            printWriter.println(str + "mInputFreezeReason=" + this.mInputFreezeReason);
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyPenSwitchChanged(long j, boolean z, boolean z2) {
        this.mService.mExt.mPolicyExt.notifyPenSwitchChanged(j, z, z2);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void startGameToolsService(int i, int i2, boolean z) {
        this.mService.mExt.mPolicyExt.startGameToolsService(i, i2, z);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public boolean interceptQuickAccess(int i, float f, float f2) {
        return this.mService.mExt.mPolicyExt.interceptQuickAccess(i, f, f2);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyPogoKeyboardStatus(boolean z) {
        this.mService.f1749mH.removeMessages(200);
        this.mService.f1749mH.sendMessage(this.mService.f1749mH.obtainMessage(200, z ? 1 : 0, 0));
    }
}
