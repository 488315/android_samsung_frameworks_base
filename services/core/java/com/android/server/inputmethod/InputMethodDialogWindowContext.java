package com.android.server.inputmethod;

import android.R;
import android.app.ActivityThread;
import android.content.Context;
import android.view.ContextThemeWrapper;

/* loaded from: classes2.dex */
public final class InputMethodDialogWindowContext {
    public Context mDialogWindowContext;

    public Context get(int i) {
        Context context = this.mDialogWindowContext;
        if (context == null || context.getDisplayId() != i) {
            this.mDialogWindowContext = new ContextThemeWrapper(ActivityThread.currentActivityThread().getSystemUiContext(i).createWindowContext(2012, null), R.style.Theme.DeviceDefault.Settings);
        }
        return this.mDialogWindowContext;
    }
}
