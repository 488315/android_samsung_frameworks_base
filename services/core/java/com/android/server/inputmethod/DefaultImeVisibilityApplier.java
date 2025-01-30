package com.android.server.inputmethod;

import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.EventLog;
import android.util.Slog;
import android.view.inputmethod.ImeTracker;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.server.LocalServices;
import com.android.server.wm.ImeTargetVisibilityPolicy;
import com.android.server.wm.WindowManagerInternal;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DefaultImeVisibilityApplier implements ImeVisibilityApplier {
    public InputMethodManagerService mService;
    public final WindowManagerInternal mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    public final ImeTargetVisibilityPolicy mImeTargetVisibilityPolicy = (ImeTargetVisibilityPolicy) LocalServices.getService(ImeTargetVisibilityPolicy.class);

    public DefaultImeVisibilityApplier(InputMethodManagerService inputMethodManagerService) {
        this.mService = inputMethodManagerService;
    }

    public void performShowIme(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2) {
        IInputMethodInvoker curMethodLocked = this.mService.getCurMethodLocked();
        if (curMethodLocked != null) {
            Slog.v("InputMethodManagerService", "Calling " + curMethodLocked + ".showSoftInput(" + iBinder + ", " + i + ", " + resultReceiver + ") for reason: " + InputMethodDebug.softInputDisplayReasonToString(i2));
            if (curMethodLocked.showSoftInput(iBinder, token, i, resultReceiver)) {
                if (ImeTracker.DEBUG_IME_VISIBILITY) {
                    EventLog.writeEvent(32001, token.getTag(), Objects.toString(this.mService.mCurFocusedWindow), InputMethodDebug.softInputDisplayReasonToString(i2), InputMethodDebug.softInputModeToString(this.mService.mCurFocusedWindowSoftInputMode));
                }
                this.mService.onShowHideSoftInputRequested(true, iBinder, i2, token);
            }
        }
    }

    public void performHideIme(IBinder iBinder, ImeTracker.Token token, ResultReceiver resultReceiver, int i) {
        IInputMethodInvoker curMethodLocked = this.mService.getCurMethodLocked();
        if (curMethodLocked != null) {
            Slog.v("InputMethodManagerService", "Calling " + curMethodLocked + ".hideSoftInput(0, " + iBinder + ", " + resultReceiver + ") for reason: " + InputMethodDebug.softInputDisplayReasonToString(i));
            if (curMethodLocked.hideSoftInput(iBinder, token, 0, resultReceiver)) {
                if (ImeTracker.DEBUG_IME_VISIBILITY) {
                    EventLog.writeEvent(32002, token.getTag(), Objects.toString(this.mService.mCurFocusedWindow), InputMethodDebug.softInputDisplayReasonToString(i), InputMethodDebug.softInputModeToString(this.mService.mCurFocusedWindowSoftInputMode));
                }
                this.mService.onShowHideSoftInputRequested(false, iBinder, i, token);
            }
        }
    }

    public void applyImeVisibility(IBinder iBinder, ImeTracker.Token token, int i) {
        applyImeVisibility(iBinder, token, i, -1);
    }

    public void applyImeVisibility(IBinder iBinder, ImeTracker.Token token, int i, int i2) {
        Slog.v("InputMethodManagerService", "applyImeVisibility state=" + i);
        if (i == 0) {
            if (this.mService.hasAttachedClient()) {
                ImeTracker.forLogging().onProgress(token, 17);
                this.mWindowManagerInternal.hideIme(iBinder, this.mService.getDisplayIdToShowImeLocked(), token);
                return;
            } else {
                Slog.i("InputMethodManagerService", "applyImeVisibility: client is null.");
                ImeTracker.forLogging().onFailed(token, 17);
                return;
            }
        }
        if (i == 1) {
            ImeTracker.forLogging().onProgress(token, 17);
            this.mWindowManagerInternal.showImePostLayout(iBinder, token);
            return;
        }
        switch (i) {
            case 4:
                showImeScreenshot(iBinder, this.mService.getDisplayIdToShowImeLocked(), null);
                return;
            case 5:
                this.mService.hideCurrentInputLocked(iBinder, token, 0, null, i2);
                return;
            case 6:
                this.mService.hideCurrentInputLocked(iBinder, token, 2, null, i2);
                return;
            case 7:
                this.mService.showCurrentInputLocked(iBinder, token, 1, null, i2);
                return;
            case 8:
                removeImeScreenshot(this.mService.getDisplayIdToShowImeLocked());
                return;
            default:
                throw new IllegalArgumentException("Invalid IME visibility state: " + i);
        }
    }

    public boolean showImeScreenshot(IBinder iBinder, int i, ImeTracker.Token token) {
        if (!this.mImeTargetVisibilityPolicy.showImeScreenshot(iBinder, i)) {
            return false;
        }
        this.mService.onShowHideSoftInputRequested(false, iBinder, 34, token);
        return true;
    }

    public boolean removeImeScreenshot(int i) {
        if (!this.mImeTargetVisibilityPolicy.removeImeScreenshot(i)) {
            return false;
        }
        InputMethodManagerService inputMethodManagerService = this.mService;
        inputMethodManagerService.onShowHideSoftInputRequested(false, inputMethodManagerService.mCurFocusedWindow, 35, null);
        return true;
    }
}
