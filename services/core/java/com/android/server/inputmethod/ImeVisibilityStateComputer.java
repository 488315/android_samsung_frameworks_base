package com.android.server.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.WindowManager;
import android.view.inputmethod.ImeTracker;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.server.LocalServices;
import com.android.server.wm.ImeTargetChangeListener;
import com.android.server.wm.WindowManagerInternal;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.WeakHashMap;

/* loaded from: classes2.dex */
public final class ImeVisibilityStateComputer {
  public IBinder mCurVisibleImeInputTarget;
  public IBinder mCurVisibleImeLayeringOverlay;
  public final InputMethodManagerService.ImeDisplayValidator mImeDisplayValidator;
  public boolean mInputShown;
  public final ImeVisibilityPolicy mPolicy;
  public final WeakHashMap mRequestWindowStateMap;
  public boolean mRequestedImeScreenshot;
  public boolean mRequestedShowExplicitly;
  public final InputMethodManagerService mService;
  public boolean mShowForced;
  public final WindowManagerInternal mWindowManagerInternal;

  public interface Injector {
    InputMethodManagerService.ImeDisplayValidator getImeValidator();

    WindowManagerInternal getWmService();
  }

  /* JADX WARN: Illegal instructions before constructor call */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ImeVisibilityStateComputer(InputMethodManagerService inputMethodManagerService) {
    this(
        inputMethodManagerService,
        r1,
        new InputMethodManagerService
            .ImeDisplayValidator() { // from class:
                                     // com.android.server.inputmethod.ImeVisibilityStateComputer$$ExternalSyntheticLambda0
          @Override // com.android.server.inputmethod.InputMethodManagerService.ImeDisplayValidator
          public final int getDisplayImePolicy(int i) {
            return WindowManagerInternal.this.getDisplayImePolicy(i);
          }
        },
        new ImeVisibilityPolicy());
    WindowManagerInternal windowManagerInternal =
        (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    final WindowManagerInternal windowManagerInternal2 =
        (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    Objects.requireNonNull(windowManagerInternal2);
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
      InputMethodManagerService.ImeDisplayValidator imeDisplayValidator,
      ImeVisibilityPolicy imeVisibilityPolicy) {
    this.mRequestWindowStateMap = new WeakHashMap();
    this.mService = inputMethodManagerService;
    this.mWindowManagerInternal = windowManagerInternal;
    this.mImeDisplayValidator = imeDisplayValidator;
    this.mPolicy = imeVisibilityPolicy;
    windowManagerInternal.setInputMethodTargetChangeListener(
        new ImeTargetChangeListener() { // from class:
                                        // com.android.server.inputmethod.ImeVisibilityStateComputer.1
          @Override // com.android.server.wm.ImeTargetChangeListener
          public void onImeTargetOverlayVisibilityChanged(
              IBinder iBinder, int i, boolean z, boolean z2) {
            ImeVisibilityStateComputer imeVisibilityStateComputer = ImeVisibilityStateComputer.this;
            if (!z || z2 || i == 3) {
              iBinder = null;
            }
            imeVisibilityStateComputer.mCurVisibleImeLayeringOverlay = iBinder;
          }

          @Override // com.android.server.wm.ImeTargetChangeListener
          public void onImeInputTargetVisibilityChanged(IBinder iBinder, boolean z, boolean z2) {
            if (ImeVisibilityStateComputer.this.mCurVisibleImeInputTarget == iBinder
                && ((!z || z2)
                    && ImeVisibilityStateComputer.this.mCurVisibleImeLayeringOverlay != null)) {
              ImeVisibilityStateComputer.this.mService.onApplyImeVisibilityFromComputer(
                  iBinder, new ImeVisibilityResult(5, 37));
            }
            ImeVisibilityStateComputer imeVisibilityStateComputer = ImeVisibilityStateComputer.this;
            if (!z || z2) {
              iBinder = null;
            }
            imeVisibilityStateComputer.mCurVisibleImeInputTarget = iBinder;
          }
        });
  }

  public boolean onImeShowFlags(ImeTracker.Token token, int i) {
    if (this.mPolicy.mA11yRequestingNoSoftKeyboard || this.mPolicy.mImeHiddenByDisplayPolicy) {
      ImeTracker.forLogging().onFailed(token, 4);
      return false;
    }
    ImeTracker.forLogging().onProgress(token, 4);
    if ((i & 2) != 0) {
      this.mRequestedShowExplicitly = true;
      this.mShowForced = true;
    } else if ((i & 1) == 0) {
      this.mRequestedShowExplicitly = true;
    }
    return true;
  }

  public boolean canHideIme(ImeTracker.Token token, int i) {
    if ((i & 1) != 0 && (this.mRequestedShowExplicitly || this.mShowForced)) {
      Slog.v(
          "InputMethodManagerService",
          "Not hiding: explicit show not cancelled by non-explicit hide");
      ImeTracker.forLogging().onFailed(token, 6);
      return false;
    }
    if (this.mShowForced && (i & 2) != 0) {
      Slog.v(
          "InputMethodManagerService", "Not hiding: forced show not cancelled by not-always hide");
      ImeTracker.forLogging().onFailed(token, 7);
      return false;
    }
    ImeTracker.forLogging().onProgress(token, 7);
    return true;
  }

  public int getImeShowFlags() {
    return this.mShowForced ? 3 : 1;
  }

  public void clearImeShowFlags() {
    this.mRequestedShowExplicitly = false;
    this.mShowForced = false;
    this.mInputShown = false;
  }

  public int computeImeDisplayId(ImeTargetWindowState imeTargetWindowState, int i) {
    int computeImeDisplayIdForTarget =
        InputMethodManagerService.computeImeDisplayIdForTarget(i, this.mImeDisplayValidator);
    imeTargetWindowState.setImeDisplayId(computeImeDisplayIdForTarget);
    this.mPolicy.setImeHiddenByDisplayPolicy(computeImeDisplayIdForTarget == -1);
    return computeImeDisplayIdForTarget;
  }

  public int getImeDisplayIdForTarget(int i) {
    return InputMethodManagerService.computeImeDisplayIdForTarget(i, this.mImeDisplayValidator);
  }

  public void requestImeVisibility(IBinder iBinder, boolean z) {
    ImeTargetWindowState orCreateWindowState = getOrCreateWindowState(iBinder);
    if (!this.mPolicy.mPendingA11yRequestingHideKeyboard) {
      orCreateWindowState.setRequestedImeVisible(z);
    } else {
      this.mPolicy.mPendingA11yRequestingHideKeyboard = false;
    }
    orCreateWindowState.setRequestImeToken(new Binder());
    setWindowStateInner(iBinder, orCreateWindowState);
  }

  public ImeTargetWindowState getOrCreateWindowState(IBinder iBinder) {
    ImeTargetWindowState imeTargetWindowState =
        (ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder);
    return imeTargetWindowState == null
        ? new ImeTargetWindowState(0, 0, false, false, false)
        : imeTargetWindowState;
  }

  public ImeTargetWindowState getWindowStateOrNull(IBinder iBinder) {
    return (ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder);
  }

  public void setWindowState(IBinder iBinder, ImeTargetWindowState imeTargetWindowState) {
    ImeTargetWindowState imeTargetWindowState2 =
        (ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder);
    if (imeTargetWindowState2 != null
        && imeTargetWindowState.hasEditorFocused()
        && imeTargetWindowState.mToolType != 2) {
      imeTargetWindowState.setRequestedImeVisible(imeTargetWindowState2.mRequestedImeVisible);
    } else if ("com.google.android.tts/com.google.android.apps.speech.tts.googletts.settings.asr.voiceime.VoiceInputMethodService"
            .equals(this.mService.getSelectedMethodIdLocked())
        && imeTargetWindowState2 != null
        && imeTargetWindowState.hasEditorFocused()) {
      Slog.i(
          "InputMethodManagerService",
          "setWindowState: for gvi, set requestedImeVisible="
              + imeTargetWindowState2.mRequestedImeVisible);
      imeTargetWindowState.setRequestedImeVisible(imeTargetWindowState2.mRequestedImeVisible);
    }
    setWindowStateInner(iBinder, imeTargetWindowState);
  }

  public final void setWindowStateInner(
      IBinder iBinder, ImeTargetWindowState imeTargetWindowState) {
    Slog.d(
        "InputMethodManagerService",
        "setWindowStateInner, windowToken=" + iBinder + ", state=" + imeTargetWindowState);
    this.mRequestWindowStateMap.put(iBinder, imeTargetWindowState);
  }

  public class ImeVisibilityResult {
    public final int mReason;
    public final int mState;

    public ImeVisibilityResult(int i, int i2) {
      this.mState = i;
      this.mReason = i2;
    }

    public int getState() {
      return this.mState;
    }

    public int getReason() {
      return this.mReason;
    }
  }

  public ImeVisibilityResult computeState(ImeTargetWindowState imeTargetWindowState, boolean z) {
    int i = imeTargetWindowState.mSoftInputModeState & 15;
    boolean z2 =
        (imeTargetWindowState.mSoftInputModeState & 240) == 16
            || this.mService.mRes.getConfiguration().isLayoutSizeAtLeast(3);
    boolean z3 = (imeTargetWindowState.mSoftInputModeState & 256) != 0;
    if (imeTargetWindowState.hasEditorFocused()
        && shouldRestoreImeVisibility(imeTargetWindowState)) {
      Slog.v("InputMethodManagerService", "Will show input to restore visibility");
      imeTargetWindowState.setRequestedImeVisible(true);
      setWindowStateInner(getWindowTokenFrom(imeTargetWindowState), imeTargetWindowState);
      return new ImeVisibilityResult(7, 23);
    }
    if (i != 0) {
      if (i == 1) {
        Slog.v("InputMethodManagerService", "Window asks to unchanged");
        ImeTargetWindowState windowStateOrNull =
            getWindowStateOrNull(this.mService.mLastImeTargetWindow);
        if (windowStateOrNull != null) {
          imeTargetWindowState.setRequestedImeVisible(windowStateOrNull.mRequestedImeVisible);
        }
      } else if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i == 5) {
              Slog.v("InputMethodManagerService", "Window asks to always show input");
              if (z) {
                if (imeTargetWindowState.hasImeFocusChanged()) {
                  return new ImeVisibilityResult(7, 8);
                }
              } else {
                Slog.e(
                    "InputMethodManagerService",
                    "SOFT_INPUT_STATE_ALWAYS_VISIBLE is ignored because there is no focused view"
                        + " that also returns true from View#onCheckIsTextEditor()");
              }
            }
          } else if (z3) {
            if (z) {
              Slog.v("InputMethodManagerService", "Window asks to show input going forward");
              return new ImeVisibilityResult(7, 7);
            }
            Slog.e(
                "InputMethodManagerService",
                "SOFT_INPUT_STATE_VISIBLE is ignored because there is no focused view that also"
                    + " returns true from View#onCheckIsTextEditor()");
          }
        } else if (imeTargetWindowState.hasImeFocusChanged()) {
          Slog.v("InputMethodManagerService", "Window asks to hide input");
          return new ImeVisibilityResult(5, 14);
        }
      } else if (z3) {
        Slog.v("InputMethodManagerService", "Window asks to hide input going forward");
        return new ImeVisibilityResult(5, 13);
      }
    } else if (imeTargetWindowState.hasImeFocusChanged()
        && (!imeTargetWindowState.hasEditorFocused() || !z2)) {
      if (WindowManager.LayoutParams.mayUseInputMethod(imeTargetWindowState.getWindowFlags())) {
        Slog.v("InputMethodManagerService", "Unspecified window will hide input");
        return new ImeVisibilityResult(6, 12);
      }
    } else if (imeTargetWindowState.hasEditorFocused() && z2 && z3) {
      Slog.v("InputMethodManagerService", "Unspecified window will show input");
      return new ImeVisibilityResult(7, 6);
    }
    if (!imeTargetWindowState.hasImeFocusChanged()
        && imeTargetWindowState.isStartInputByGainFocus()) {
      Slog.v("InputMethodManagerService", "Same window without editor will hide input");
      return new ImeVisibilityResult(5, 21);
    }
    if (imeTargetWindowState.hasEditorFocused()
        || !this.mInputShown
        || !imeTargetWindowState.isStartInputByGainFocus()
        || !this.mService.mInputMethodDeviceConfigs.shouldHideImeWhenNoEditorFocus()) {
      return null;
    }
    Slog.v("InputMethodManagerService", "Window without editor will hide input");
    return new ImeVisibilityResult(5, 33);
  }

  public ImeVisibilityResult onInteractiveChanged(IBinder iBinder, boolean z) {
    ImeTargetWindowState windowStateOrNull = getWindowStateOrNull(iBinder);
    if (windowStateOrNull != null
        && windowStateOrNull.isRequestedImeVisible()
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

  public IBinder getWindowTokenFrom(IBinder iBinder) {
    for (IBinder iBinder2 : this.mRequestWindowStateMap.keySet()) {
      if (((ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder2)).getRequestImeToken()
          == iBinder) {
        return iBinder2;
      }
    }
    return this.mService.mCurFocusedWindow;
  }

  public IBinder getWindowTokenFrom(ImeTargetWindowState imeTargetWindowState) {
    for (IBinder iBinder : this.mRequestWindowStateMap.keySet()) {
      if (((ImeTargetWindowState) this.mRequestWindowStateMap.get(iBinder))
          == imeTargetWindowState) {
        return iBinder;
      }
    }
    return null;
  }

  public boolean shouldRestoreImeVisibility(ImeTargetWindowState imeTargetWindowState) {
    int softInputModeState = imeTargetWindowState.getSoftInputModeState();
    int i = softInputModeState & 15;
    if (i != 2) {
      if (i == 3) {
        return false;
      }
    } else if ((softInputModeState & 256) != 0) {
      return false;
    }
    return this.mWindowManagerInternal.shouldRestoreImeVisibility(
        getWindowTokenFrom(imeTargetWindowState));
  }

  public boolean isInputShown() {
    return this.mInputShown;
  }

  public void setInputShown(boolean z) {
    this.mInputShown = z;
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    protoOutputStream.write(1133871366154L, this.mRequestedShowExplicitly);
    protoOutputStream.write(1133871366155L, this.mShowForced);
    protoOutputStream.write(1133871366168L, this.mPolicy.isA11yRequestNoSoftKeyboard());
    protoOutputStream.write(1133871366156L, this.mInputShown);
  }

  public void dump(PrintWriter printWriter) {
    PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(printWriter);
    printWriterPrinter.println(
        " mRequestedShowExplicitly="
            + this.mRequestedShowExplicitly
            + " mShowForced="
            + this.mShowForced);
    StringBuilder sb = new StringBuilder();
    sb.append("  mImeHiddenByDisplayPolicy=");
    sb.append(this.mPolicy.isImeHiddenByDisplayPolicy());
    printWriterPrinter.println(sb.toString());
    printWriterPrinter.println("  mInputShown=" + this.mInputShown);
  }

  public class ImeVisibilityPolicy {
    public boolean mA11yRequestingNoSoftKeyboard;
    public boolean mImeHiddenByDisplayPolicy;
    public boolean mPendingA11yRequestingHideKeyboard;

    public void setImeHiddenByDisplayPolicy(boolean z) {
      this.mImeHiddenByDisplayPolicy = z;
    }

    public boolean isImeHiddenByDisplayPolicy() {
      return this.mImeHiddenByDisplayPolicy;
    }

    public void setA11yRequestNoSoftKeyboard(int i) {
      boolean z = (i & 3) == 1;
      this.mA11yRequestingNoSoftKeyboard = z;
      if (z) {
        this.mPendingA11yRequestingHideKeyboard = true;
      }
    }

    public boolean isA11yRequestNoSoftKeyboard() {
      return this.mA11yRequestingNoSoftKeyboard;
    }
  }

  public ImeVisibilityPolicy getImePolicy() {
    return this.mPolicy;
  }

  public class ImeTargetWindowState {
    public final boolean mHasFocusedEditor;
    public int mImeDisplayId;
    public final boolean mImeFocusChanged;
    public final boolean mIsStartInputByGainFocus;
    public IBinder mRequestImeToken;
    public boolean mRequestedImeVisible;
    public final int mSoftInputModeState;
    public final int mToolType;
    public final int mWindowFlags;

    public ImeTargetWindowState(int i, int i2, boolean z, boolean z2, boolean z3) {
      this(i, i2, z, z2, z3, 0);
    }

    public ImeTargetWindowState(int i, int i2, boolean z, boolean z2, boolean z3, int i3) {
      this.mImeDisplayId = 0;
      this.mSoftInputModeState = i;
      this.mWindowFlags = i2;
      this.mImeFocusChanged = z;
      this.mHasFocusedEditor = z2;
      this.mIsStartInputByGainFocus = z3;
      this.mToolType = i3;
    }

    public boolean hasImeFocusChanged() {
      return this.mImeFocusChanged;
    }

    public boolean hasEditorFocused() {
      return this.mHasFocusedEditor;
    }

    public boolean isStartInputByGainFocus() {
      return this.mIsStartInputByGainFocus;
    }

    public int getSoftInputModeState() {
      return this.mSoftInputModeState;
    }

    public int getWindowFlags() {
      return this.mWindowFlags;
    }

    public final void setImeDisplayId(int i) {
      this.mImeDisplayId = i;
    }

    public final void setRequestedImeVisible(boolean z) {
      this.mRequestedImeVisible = z;
    }

    public boolean isRequestedImeVisible() {
      return this.mRequestedImeVisible;
    }

    public void setRequestImeToken(IBinder iBinder) {
      this.mRequestImeToken = iBinder;
    }

    public IBinder getRequestImeToken() {
      return this.mRequestImeToken;
    }

    public String toString() {
      return "ImeTargetWindowState{ imeToken "
          + this.mRequestImeToken
          + " imeFocusChanged "
          + this.mImeFocusChanged
          + " hasEditorFocused "
          + this.mHasFocusedEditor
          + " requestedImeVisible "
          + this.mRequestedImeVisible
          + " imeDisplayId "
          + this.mImeDisplayId
          + " softInputModeState "
          + InputMethodDebug.softInputModeToString(this.mSoftInputModeState)
          + " isStartInputByGainFocus "
          + this.mIsStartInputByGainFocus
          + "}";
    }
  }
}
