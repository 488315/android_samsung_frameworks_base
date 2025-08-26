package androidx.fragment.app;

import androidx.fragment.app.FragmentActivity;

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
