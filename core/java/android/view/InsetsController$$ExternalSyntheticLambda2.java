package android.view;

import android.app.ActivityThread;
import android.content.Context;
import android.view.inputmethod.ImeTracker;

public final /* synthetic */ class InsetsController$$ExternalSyntheticLambda2
        implements ImeTracker.InputMethodLatencyContext {
    @Override // android.view.inputmethod.ImeTracker.InputMethodLatencyContext
    public final Context getAppContext() {
        return ActivityThread.currentApplication();
    }
}
