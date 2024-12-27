package com.android.server.inputmethod;

import android.view.inputmethod.InputMethodInfo;

import java.util.function.Predicate;

public final /* synthetic */ class InputMethodManagerService$$ExternalSyntheticLambda11
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((InputMethodInfo) obj).shouldShowInInputMethodPicker();
    }
}
