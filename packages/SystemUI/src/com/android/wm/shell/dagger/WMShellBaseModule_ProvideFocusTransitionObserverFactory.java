package com.android.wm.shell.dagger;

import com.android.wm.shell.transition.FocusTransitionObserver;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
