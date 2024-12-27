package com.android.server.inputmethod;

import android.R;
import android.app.ActivityThread;
import android.content.Context;
import android.view.ContextThemeWrapper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InputMethodDialogWindowContext {
    public Context mDialogWindowContext;

    public Context get(int i) {
        Context context = this.mDialogWindowContext;
        if (context == null || context.getDisplayId() != i) {
            this.mDialogWindowContext =
                    new ContextThemeWrapper(
                            ActivityThread.currentActivityThread()
                                    .getSystemUiContext(i)
                                    .createWindowContext(2012, null),
                            R.style.Theme.DeviceDefault.Settings);
        }
        return this.mDialogWindowContext;
    }
}
