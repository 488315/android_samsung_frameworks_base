package com.android.server.inputmethod;

import android.os.IBinder;

import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.server.LocalServices;
import com.android.server.autofill.AutofillInlineSuggestionsRequestSession;

import java.util.Collections;
import java.util.List;

public abstract class InputMethodManagerInternal {
    public static final AnonymousClass1 NOP = new AnonymousClass1();

    /* renamed from: com.android.server.inputmethod.InputMethodManagerInternal$1, reason: invalid class name */
    public final class AnonymousClass1 extends InputMethodManagerInternal {
        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final List getEnabledInputMethodListAsUser(int i) {
            return Collections.emptyList();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final List getInputMethodListAsUser(int i) {
            return Collections.emptyList();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void hideAllInputMethods(int i) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void maybeFinishStylusHandwriting() {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onCreateInlineSuggestionsRequest(
                int i,
                InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo,
                AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl
                        inlineSuggestionsRequestCallbackImpl) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onImeParentChanged() {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onSessionForAccessibilityCreated(
                IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onSwitchKeyboardLayoutShortcut(int i) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void removeImeSurface() {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void reportImeControl(IBinder iBinder) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final boolean setInputMethodEnabled(int i, String str, boolean z) {
            return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void setInteractive(boolean z) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void setVirtualDeviceInputMethodForAllUsers(int i, String str) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final boolean switchToInputMethod(int i, String str) {
            return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final boolean transferTouchFocusToImeWindow(int i, IBinder iBinder, int i2) {
            return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void unbindAccessibilityFromCurrentClient(int i) {}

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void updateImeWindowStatus(boolean z) {}
    }

    public static InputMethodManagerInternal get() {
        InputMethodManagerInternal inputMethodManagerInternal =
                (InputMethodManagerInternal)
                        LocalServices.getService(InputMethodManagerInternal.class);
        return inputMethodManagerInternal != null ? inputMethodManagerInternal : NOP;
    }

    public abstract List getEnabledInputMethodListAsUser(int i);

    public abstract List getInputMethodListAsUser(int i);

    public abstract void hideAllInputMethods(int i);

    public abstract void maybeFinishStylusHandwriting();

    public abstract void onCreateInlineSuggestionsRequest(
            int i,
            InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo,
            AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl
                    inlineSuggestionsRequestCallbackImpl);

    public abstract void onImeParentChanged();

    public abstract void onSessionForAccessibilityCreated(
            IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i);

    public abstract void onSwitchKeyboardLayoutShortcut(int i);

    public abstract void removeImeSurface();

    public abstract void reportImeControl(IBinder iBinder);

    public abstract boolean setInputMethodEnabled(int i, String str, boolean z);

    public abstract void setInteractive(boolean z);

    public abstract void setVirtualDeviceInputMethodForAllUsers(int i, String str);

    public abstract boolean switchToInputMethod(int i, String str);

    public abstract boolean transferTouchFocusToImeWindow(int i, IBinder iBinder, int i2);

    public abstract void unbindAccessibilityFromCurrentClient(int i);

    public abstract void updateImeWindowStatus(boolean z);
}
