package androidx.fragment.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes.dex */
public final class FragmentController {
    private final FragmentHostCallback<?> mHost;

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    public static FragmentController createController(FragmentHostCallback<?> fragmentHostCallback) {
        return new FragmentController(fragmentHostCallback);
    }

    public final void attachHost() {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        fragmentHostCallback.mFragmentManager.attachController(fragmentHostCallback, fragmentHostCallback, null);
    }

    public final void dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public final boolean dispatchContextItemSelected() {
        return this.mHost.mFragmentManager.dispatchContextItemSelected();
    }

    public final void dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public final void dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    public final void dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause();
    }

    public final void dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public final void dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public final void dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop();
    }

    public final void execPendingActions() {
        this.mHost.mFragmentManager.execPendingActions(true);
    }

    public final FragmentManager getSupportFragmentManager() {
        return this.mHost.mFragmentManager;
    }

    public final void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return ((FragmentLayoutInflaterFactory) this.mHost.mFragmentManager.getLayoutInflaterFactory()).onCreateView(view, str, context, attributeSet);
    }
}
