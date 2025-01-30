package androidx.fragment.app;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FragmentController {
    public final FragmentHostCallback mHost;

    private FragmentController(FragmentHostCallback fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    public static FragmentController createController(FragmentActivity.HostCallbacks hostCallbacks) {
        return new FragmentController(hostCallbacks);
    }

    public final void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }
}
