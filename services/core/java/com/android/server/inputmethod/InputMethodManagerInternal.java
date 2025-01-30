package com.android.server.inputmethod;

import android.os.IBinder;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.server.LocalServices;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class InputMethodManagerInternal {
  public static final InputMethodManagerInternal NOP =
      new InputMethodManagerInternal() { // from class:
                                         // com.android.server.inputmethod.InputMethodManagerInternal.1
        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void hideCurrentInputMethod(int i) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void maybeFinishStylusHandwriting() {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onCreateInlineSuggestionsRequest(
            int i,
            InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo,
            IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onImeParentChanged() {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onSessionForAccessibilityCreated(
            int i, IAccessibilityInputMethodSession iAccessibilityInputMethodSession) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void removeImeSurface() {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void reportImeControl(IBinder iBinder) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean setInputMethodEnabled(String str, boolean z, int i) {
          return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void setInteractive(boolean z) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void switchKeyboardLayout(int i) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean switchToInputMethod(String str, int i) {
          return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean transferTouchFocusToImeWindow(IBinder iBinder, int i) {
          return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void unbindAccessibilityFromCurrentClient(int i) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void updateImeWindowStatus(boolean z) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public List getInputMethodListAsUser(int i) {
          return Collections.emptyList();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public List getEnabledInputMethodListAsUser(int i) {
          return Collections.emptyList();
        }
      };

  public interface InputMethodListListener {
    void onInputMethodListUpdated(List list, int i);
  }

  public abstract List getEnabledInputMethodListAsUser(int i);

  public abstract List getInputMethodListAsUser(int i);

  public abstract void hideCurrentInputMethod(int i);

  public abstract void maybeFinishStylusHandwriting();

  public abstract void onCreateInlineSuggestionsRequest(
      int i,
      InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo,
      IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback);

  public abstract void onImeParentChanged();

  public abstract void onSessionForAccessibilityCreated(
      int i, IAccessibilityInputMethodSession iAccessibilityInputMethodSession);

  public abstract void removeImeSurface();

  public abstract void reportImeControl(IBinder iBinder);

  public abstract boolean setInputMethodEnabled(String str, boolean z, int i);

  public abstract void setInteractive(boolean z);

  public abstract void switchKeyboardLayout(int i);

  public abstract boolean switchToInputMethod(String str, int i);

  public abstract boolean transferTouchFocusToImeWindow(IBinder iBinder, int i);

  public abstract void unbindAccessibilityFromCurrentClient(int i);

  public abstract void updateImeWindowStatus(boolean z);

  public static InputMethodManagerInternal get() {
    InputMethodManagerInternal inputMethodManagerInternal =
        (InputMethodManagerInternal) LocalServices.getService(InputMethodManagerInternal.class);
    return inputMethodManagerInternal != null ? inputMethodManagerInternal : NOP;
  }
}
