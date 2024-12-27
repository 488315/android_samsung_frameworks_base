package com.android.server.inputmethod;

import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.IBinder;
import android.util.PrintWriterPrinter;
import android.util.Slog;

import com.android.internal.inputmethod.InputMethodDebug;
import com.android.server.wm.ImeTargetChangeListener;
import com.android.server.wm.WindowManagerInternal;

import java.io.PrintWriter;
import java.util.WeakHashMap;

public final class ImeVisibilityStateComputer {
    public IBinder mCurVisibleImeInputTarget;
    public IBinder mCurVisibleImeLayeringOverlay;
    public final ImeVisibilityStateComputer$$ExternalSyntheticLambda0 mImeDisplayValidator;
    public boolean mInputShown;
    public final ImeVisibilityPolicy mPolicy;
    public final WeakHashMap mRequestWindowStateMap;
    public boolean mRequestedImeScreenshot;
    public boolean mRequestedShowExplicitly;
    public final InputMethodManagerService mService;
    public boolean mShowForced;
    public final WindowManagerInternal mWindowManagerInternal;

    /* renamed from: com.android.server.inputmethod.ImeVisibilityStateComputer$1, reason: invalid class name */
    public final class AnonymousClass1 implements ImeTargetChangeListener {
        public AnonymousClass1() {}
    }

    public final class ImeTargetWindowState {
        public final boolean mHasFocusedEditor;
        public int mImeDisplayId = 0;
        public final boolean mImeFocusChanged;
        public final boolean mIsStartInputByGainFocus;
        public IBinder mRequestImeToken;
        public boolean mRequestedImeVisible;
        public final int mSoftInputModeState;

        public ImeTargetWindowState(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
            this.mSoftInputModeState = i;
            this.mImeFocusChanged = z;
            this.mHasFocusedEditor = z2;
            this.mIsStartInputByGainFocus = z3;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ImeTargetWindowState{ imeToken ");
            sb.append(this.mRequestImeToken);
            sb.append(" imeFocusChanged ");
            sb.append(this.mImeFocusChanged);
            sb.append(" hasEditorFocused ");
            sb.append(this.mHasFocusedEditor);
            sb.append(" requestedImeVisible ");
            sb.append(this.mRequestedImeVisible);
            sb.append(" imeDisplayId ");
            sb.append(this.mImeDisplayId);
            sb.append(" softInputModeState ");
            sb.append(InputMethodDebug.softInputModeToString(this.mSoftInputModeState));
            sb.append(" isStartInputByGainFocus ");
            return OptionalBool$$ExternalSyntheticOutline0.m(
                    "}", sb, this.mIsStartInputByGainFocus);
        }
    }

    public final class ImeVisibilityPolicy {
        public boolean mA11yRequestingNoSoftKeyboard;
        public boolean mImeHiddenByDisplayPolicy;
        public boolean mPendingA11yRequestingHideKeyboard;
    }

    public final class ImeVisibilityResult {
        public final int mReason;
        public final int mState;

        public ImeVisibilityResult(int i, int i2) {
            this.mState = i;
            this.mReason = i2;
        }
    }

    public interface Injector {
        ImeVisibilityStateComputer$$ExternalSyntheticLambda0 getImeValidator();

        WindowManagerInternal getWmService();
    }

    public ImeVisibilityStateComputer(
            InputMethodManagerService inputMethodManagerService, Injector injector) {
        this(
                inputMethodManagerService,
                injector.getWmService(),
                injector.getImeValidator(),
                new ImeVisibilityPolicy());
    }

    public ImeVisibilityStateComputer(
            InputMethodManagerService inputMethodManagerService,
            WindowManagerInternal windowManagerInternal,
            ImeVisibilityStateComputer$$ExternalSyntheticLambda0
                    imeVisibilityStateComputer$$ExternalSyntheticLambda0,
            ImeVisibilityPolicy imeVisibilityPolicy) {
        this.mRequestWindowStateMap = new WeakHashMap();
        this.mService = inputMethodManagerService;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mImeDisplayValidator = imeVisibilityStateComputer$$ExternalSyntheticLambda0;
        this.mPolicy = imeVisibilityPolicy;
        windowManagerInternal.setInputMethodTargetChangeListener(new AnonymousClass1());
    }

    public final void dump(PrintWriter printWriter) {
        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(printWriter);
        printWriterPrinter.println(
                "  mRequestedShowExplicitly="
                        + this.mRequestedShowExplicitly
                        + " mShowForced="
                        + this.mShowForced);
        StringBuilder sb = new StringBuilder("  mImeHiddenByDisplayPolicy=");
        sb.append(this.mPolicy.mImeHiddenByDisplayPolicy);
        printWriterPrinter.println(sb.toString());
        printWriterPrinter.println("  mInputShown=" + this.mInputShown);
    }

    public final IBinder getWindowTokenFrom(IBinder iBinder) {
        for (IBinder iBinder2 : this.mRequestWindowStateMap.keySet()) {
            if (((ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder2)).mRequestImeToken
                    == iBinder) {
                return iBinder2;
            }
        }
        return this.mService.mImeBindingState.mFocusedWindow;
    }

    public final IBinder getWindowTokenFrom(ImeTargetWindowState imeTargetWindowState) {
        for (IBinder iBinder : this.mRequestWindowStateMap.keySet()) {
            if (((ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder))
                    == imeTargetWindowState) {
                return iBinder;
            }
        }
        return null;
    }

    public ImeVisibilityResult onInteractiveChanged(IBinder iBinder, boolean z) {
        ImeTargetWindowState imeTargetWindowState =
                (ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder);
        if (imeTargetWindowState != null
                && imeTargetWindowState.mRequestedImeVisible
                && this.mInputShown
                && !z) {
            this.mRequestedImeScreenshot = true;
            return new ImeVisibilityResult(4, 34);
        }
        if (!z || !this.mRequestedImeScreenshot) {
            return null;
        }
        this.mRequestedImeScreenshot = false;
        return new ImeVisibilityResult(8, 35);
    }

    public final void requestImeVisibility(IBinder iBinder, boolean z) {
        ImeTargetWindowState imeTargetWindowState =
                (ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder);
        if (imeTargetWindowState == null) {
            imeTargetWindowState = new ImeTargetWindowState(0, 0, 0, false, false, false);
        }
        ImeVisibilityPolicy imeVisibilityPolicy = this.mPolicy;
        if (imeVisibilityPolicy.mPendingA11yRequestingHideKeyboard) {
            imeVisibilityPolicy.mPendingA11yRequestingHideKeyboard = false;
        } else {
            imeTargetWindowState.mRequestedImeVisible = z;
        }
        imeTargetWindowState.mRequestImeToken = new Binder();
        setWindowStateInner(iBinder, imeTargetWindowState);
    }

    public final void setWindowStateInner(
            IBinder iBinder, ImeTargetWindowState imeTargetWindowState) {
        Slog.d(
                "InputMethodManagerService",
                "setWindowStateInner, windowToken=" + iBinder + ", state=" + imeTargetWindowState);
        this.mRequestWindowStateMap.put(iBinder, imeTargetWindowState);
    }
}
