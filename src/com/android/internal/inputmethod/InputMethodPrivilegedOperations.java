package com.android.internal.inputmethod;

import android.graphics.Region;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.inputmethod.IInputContentUriToken;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class InputMethodPrivilegedOperations {
    private static final String TAG = "InputMethodPrivilegedOperations";
    private final OpsHolder mOps = new OpsHolder();

    private static final class OpsHolder {
        private IInputMethodPrivilegedOperations mPrivOps;

        private OpsHolder() {
        }

        public synchronized void set(IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
            if (this.mPrivOps != null) {
                throw new IllegalStateException("IInputMethodPrivilegedOperations must be set at most once. privOps=" + iInputMethodPrivilegedOperations);
            }
            this.mPrivOps = iInputMethodPrivilegedOperations;
        }

        private static String getCallerMethodName() {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace.length <= 4) {
                return "<bottom of call stack>";
            }
            return stackTrace[4].getMethodName();
        }

        public synchronized IInputMethodPrivilegedOperations getAndWarnIfNull() {
            if (this.mPrivOps == null) {
                Log.e(InputMethodPrivilegedOperations.TAG, getCallerMethodName() + " is ignored. Call it within attachToken() and InputMethodService.onDestroy()");
            }
            return this.mPrivOps;
        }
    }

    public void set(IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
        Objects.requireNonNull(iInputMethodPrivilegedOperations, "privOps must not be null");
        this.mOps.set(iInputMethodPrivilegedOperations);
    }

    public void setImeWindowStatusAsync(int i, int i2) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.setImeWindowStatusAsync(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportStartInputAsync(IBinder iBinder) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.reportStartInputAsync(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHandwritingSurfaceNotTouchable(boolean z) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.setHandwritingSurfaceNotTouchable(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHandwritingTouchableRegion(Region region) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.setHandwritingTouchableRegion(region);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IInputContentUriToken createInputContentUriToken(Uri uri, String str) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return null;
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.createInputContentUriToken(uri, str, androidFuture);
            return IInputContentUriToken.Stub.asInterface((IBinder) CompletableFutureUtil.getResult(androidFuture));
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void reportFullscreenModeAsync(boolean z) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.reportFullscreenModeAsync(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateStatusIconAsync(String str, int i) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.updateStatusIconAsync(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInputMethod(String str) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.setInputMethod(str, androidFuture);
            CompletableFutureUtil.getResult(androidFuture);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInputMethodAndSubtype(String str, InputMethodSubtype inputMethodSubtype) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.setInputMethodAndSubtype(str, inputMethodSubtype, androidFuture);
            CompletableFutureUtil.getResult(androidFuture);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void hideMySoftInput(ImeTracker.Token token, int i, int i2) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            ImeTracker.forLogging().onFailed(token, 46);
            return;
        }
        ImeTracker.forLogging().onProgress(token, 46);
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.hideMySoftInput(token, i, i2, androidFuture);
            CompletableFutureUtil.getResult(androidFuture);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void showMySoftInput(ImeTracker.Token token, int i, int i2) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            ImeTracker.forLogging().onFailed(token, 46);
            return;
        }
        ImeTracker.forLogging().onProgress(token, 46);
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.showMySoftInput(token, i, i2, androidFuture);
            CompletableFutureUtil.getResult(androidFuture);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean switchToPreviousInputMethod() {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return false;
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.switchToPreviousInputMethod(androidFuture);
            return ((Boolean) CompletableFutureUtil.getResult(androidFuture)).booleanValue();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean switchToNextInputMethod(boolean z) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return false;
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.switchToNextInputMethod(z, androidFuture);
            return ((Boolean) CompletableFutureUtil.getResult(androidFuture)).booleanValue();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldOfferSwitchingToNextInputMethod() {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return false;
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            andWarnIfNull.shouldOfferSwitchingToNextInputMethod(androidFuture);
            return ((Boolean) CompletableFutureUtil.getResult(androidFuture)).booleanValue();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onImeSwitchButtonClickFromClient(int i) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.onImeSwitchButtonClickFromClient(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyUserActionAsync() {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.notifyUserActionAsync();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void applyImeVisibilityAsync(IBinder iBinder, boolean z, ImeTracker.Token token) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            ImeTracker.forLogging().onFailed(token, 46);
            return;
        }
        ImeTracker.forLogging().onProgress(token, 46);
        try {
            andWarnIfNull.applyImeVisibilityAsync(iBinder, z, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onStylusHandwritingReady(int i, int i2) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.onStylusHandwritingReady(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetStylusHandwriting(int i) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.resetStylusHandwriting(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void switchKeyboardLayoutAsync(int i) {
        IInputMethodPrivilegedOperations andWarnIfNull = this.mOps.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        try {
            andWarnIfNull.switchKeyboardLayoutAsync(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
