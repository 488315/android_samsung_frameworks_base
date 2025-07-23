package androidx.fragment.app;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FragmentController {
    public final FragmentHostCallback mHost;

    private FragmentController(FragmentHostCallback fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    public static FragmentController createController(FragmentActivity.HostCallbacks hostCallbacks) {
        return new FragmentController(hostCallbacks);
    }

    public final void noteStateNotSaved() {
        this.mHost.fragmentManager.noteStateNotSaved();
    }
}
