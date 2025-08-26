package com.android.wm.shell.dagger;

import com.android.wm.shell.transition.FocusTransitionObserver;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideFocusTransitionObserverFactory implements Provider {
    public static FocusTransitionObserver provideFocusTransitionObserver() {
        return new FocusTransitionObserver();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FocusTransitionObserver();
    }
}
