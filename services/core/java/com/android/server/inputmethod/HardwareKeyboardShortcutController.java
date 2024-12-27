package com.android.server.inputmethod;

import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import com.android.internal.inputmethod.InputMethodSubtypeHandle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HardwareKeyboardShortcutController {
    public final ArrayList mSubtypeHandles = new ArrayList();
    public final int mUserId;

    public HardwareKeyboardShortcutController(InputMethodMap inputMethodMap, int i) {
        this.mUserId = i;
        reset(inputMethodMap);
    }

    public final void reset(InputMethodMap inputMethodMap) {
        this.mSubtypeHandles.clear();
        InputMethodSettings inputMethodSettings =
                new InputMethodSettings(inputMethodMap, this.mUserId);
        ArrayList enabledInputMethodListWithFilter =
                inputMethodSettings.getEnabledInputMethodListWithFilter(null);
        for (int i = 0; i < enabledInputMethodListWithFilter.size(); i++) {
            InputMethodInfo inputMethodInfo =
                    (InputMethodInfo) enabledInputMethodListWithFilter.get(i);
            if (inputMethodInfo.shouldShowInInputMethodPicker()) {
                List<InputMethodSubtype> enabledInputMethodSubtypeList =
                        inputMethodSettings.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
                if (enabledInputMethodSubtypeList.isEmpty()) {
                    this.mSubtypeHandles.add(
                            InputMethodSubtypeHandle.of(
                                    inputMethodInfo, (InputMethodSubtype) null));
                } else {
                    for (InputMethodSubtype inputMethodSubtype : enabledInputMethodSubtypeList) {
                        if (inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
                            this.mSubtypeHandles.add(
                                    InputMethodSubtypeHandle.of(
                                            inputMethodInfo, inputMethodSubtype));
                        }
                    }
                }
            }
        }
    }
}
