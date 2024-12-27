package com.android.server.inputmethod;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.util.Slog;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;

import com.android.server.LocalServices;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowState;

public final class DefaultImeVisibilityApplier implements ImeVisibilityApplier {
    public final InputMethodManagerService mService;
    public final WindowManagerInternal mWindowManagerInternal =
            (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    public final WindowManagerService.ImeTargetVisibilityPolicyImpl mImeTargetVisibilityPolicy =
            (WindowManagerService.ImeTargetVisibilityPolicyImpl)
                    LocalServices.getService(
                            WindowManagerService.ImeTargetVisibilityPolicyImpl.class);

    public DefaultImeVisibilityApplier(InputMethodManagerService inputMethodManagerService) {
        this.mService = inputMethodManagerService;
    }

    public final void applyImeVisibility(
            IBinder iBinder, ImeTracker.Token token, int i, int i2, int i3) {
        ProxyManager$$ExternalSyntheticOutline0.m(
                i, "applyImeVisibility state=", "InputMethodManagerService");
        int i4 = this.mService.getInputMethodBindingController(i3).mDisplayIdToShowIme;
        if (i == 0) {
            if (Flags.refactorInsetsController()) {
                return;
            }
            if (this.mService.mCurClient != null) {
                ImeTracker.forLogging().onProgress(token, 17);
                this.mWindowManagerInternal.hideIme(iBinder, i4, token);
                return;
            } else {
                Slog.i("InputMethodManagerService", "applyImeVisibility: client is null.");
                ImeTracker.forLogging().onFailed(token, 17);
                return;
            }
        }
        if (i == 1) {
            if (Flags.refactorInsetsController()) {
                return;
            }
            ImeTracker.forLogging().onProgress(token, 17);
            this.mWindowManagerInternal.showImePostLayout(iBinder, token);
            return;
        }
        switch (i) {
            case 4:
                WindowManagerService.ImeTargetVisibilityPolicyImpl imeTargetVisibilityPolicyImpl =
                        this.mImeTargetVisibilityPolicy;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowState windowState =
                                (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                        if (windowState == null) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        } else {
                            DisplayContent displayContent =
                                    WindowManagerService.this.mRoot.getDisplayContent(i4);
                            if (displayContent == null) {
                                Slog.w(
                                        "WindowManager",
                                        "Invalid displayId:"
                                                + i4
                                                + ", fail to show ime screenshot");
                                WindowManagerService.resetPriorityAfterLockedSection();
                            } else {
                                displayContent.showImeScreenshot(windowState);
                                WindowManagerService.resetPriorityAfterLockedSection();
                                this.mService.onShowHideSoftInputRequested(
                                        false, iBinder, 34, null);
                            }
                        }
                    } finally {
                    }
                }
                return;
            case 5:
                if (Flags.refactorInsetsController()) {
                    setImeVisibilityOnFocusedWindowClient(false);
                    return;
                } else {
                    this.mService.hideCurrentInputLocked(iBinder, token, 0, null, i2);
                    return;
                }
            case 6:
                if (Flags.refactorInsetsController()) {
                    setImeVisibilityOnFocusedWindowClient(false);
                    return;
                } else {
                    this.mService.hideCurrentInputLocked(iBinder, token, 2, null, i2);
                    return;
                }
            case 7:
                if (Flags.refactorInsetsController()) {
                    setImeVisibilityOnFocusedWindowClient(true);
                    return;
                } else {
                    this.mService.showCurrentInputLocked(iBinder, token, 1, 0, null, i2);
                    return;
                }
            case 8:
                WindowManagerService.ImeTargetVisibilityPolicyImpl imeTargetVisibilityPolicyImpl2 =
                        this.mImeTargetVisibilityPolicy;
                WindowManagerGlobalLock windowManagerGlobalLock2 =
                        WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        DisplayContent displayContent2 =
                                WindowManagerService.this.mRoot.getDisplayContent(i4);
                        if (displayContent2 == null) {
                            Slog.w(
                                    "WindowManager",
                                    "Invalid displayId:" + i4 + ", fail to remove ime screenshot");
                        } else {
                            displayContent2.removeImeSurfaceImmediately();
                            WindowManagerService.resetPriorityAfterLockedSection();
                            InputMethodManagerService inputMethodManagerService = this.mService;
                            inputMethodManagerService.onShowHideSoftInputRequested(
                                    false,
                                    inputMethodManagerService.mImeBindingState.mFocusedWindow,
                                    35,
                                    null);
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                return;
            default:
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i, "Invalid IME visibility state: "));
        }
    }

    public final void setImeVisibilityOnFocusedWindowClient(boolean z) {
        ClientState clientState;
        IInputMethodClientInvoker iInputMethodClientInvoker;
        ImeBindingState imeBindingState = this.mService.mImeBindingState;
        if (imeBindingState == null
                || (clientState = imeBindingState.mFocusedWindowClient) == null
                || (iInputMethodClientInvoker = clientState.mClient) == null) {
            return;
        }
        iInputMethodClientInvoker.setImeVisibility(z);
    }
}
