package android.accessibilityservice;

import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;

/* loaded from: classes.dex */
interface AccessibilityInputMethodSession {
  void finishInput();

  void invalidateInput(
      EditorInfo editorInfo,
      IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection,
      int i);

  void setEnabled(boolean z);

  void updateSelection(int i, int i2, int i3, int i4, int i5, int i6);
}
