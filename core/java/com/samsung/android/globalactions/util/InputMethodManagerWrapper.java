package com.samsung.android.globalactions.util;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class InputMethodManagerWrapper {
    private final InputMethodManager mInputMethodManager;

    public InputMethodManagerWrapper(Context context) {
        this.mInputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void hideSoftInputFromWindow(IBinder view) {
        this.mInputMethodManager.hideSoftInputFromWindow(view, 0);
    }
}
