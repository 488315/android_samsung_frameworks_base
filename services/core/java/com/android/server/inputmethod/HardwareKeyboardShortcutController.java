package com.android.server.inputmethod;

import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.InputMethodSubtypeHandle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class HardwareKeyboardShortcutController {
  public final ArrayList mSubtypeHandles = new ArrayList();

  public void reset(InputMethodUtils.InputMethodSettings inputMethodSettings) {
    this.mSubtypeHandles.clear();
    Iterator it = inputMethodSettings.getEnabledInputMethodListLocked().iterator();
    while (it.hasNext()) {
      InputMethodInfo inputMethodInfo = (InputMethodInfo) it.next();
      if (inputMethodInfo.shouldShowInInputMethodPicker()) {
        List<InputMethodSubtype> enabledInputMethodSubtypeListLocked =
            inputMethodSettings.getEnabledInputMethodSubtypeListLocked(inputMethodInfo, true);
        if (enabledInputMethodSubtypeListLocked.isEmpty()) {
          this.mSubtypeHandles.add(
              InputMethodSubtypeHandle.of(inputMethodInfo, (InputMethodSubtype) null));
        } else {
          for (InputMethodSubtype inputMethodSubtype : enabledInputMethodSubtypeListLocked) {
            if (inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
              this.mSubtypeHandles.add(
                  InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodSubtype));
            }
          }
        }
      }
    }
  }

  public static Object getNeighborItem(List list, Object obj, boolean z) {
    int size = list.size();
    for (int i = 0; i < size; i++) {
      if (Objects.equals(obj, list.get(i))) {
        return list.get(((i + (z ? 1 : -1)) + size) % size);
      }
    }
    return null;
  }

  public InputMethodSubtypeHandle onSubtypeSwitch(
      InputMethodSubtypeHandle inputMethodSubtypeHandle, boolean z) {
    return (InputMethodSubtypeHandle)
        getNeighborItem(this.mSubtypeHandles, inputMethodSubtypeHandle, z);
  }
}
