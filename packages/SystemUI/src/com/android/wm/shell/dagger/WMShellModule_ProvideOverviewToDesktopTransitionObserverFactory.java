package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.OverviewToDesktopTransitionObserver;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideOverviewToDesktopTransitionObserverFactory implements Provider {
    public final Provider shellInitProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideOverviewToDesktopTransitionObserverFactory(Provider provider, Provider provider2) {
        this.transitionsProvider = provider;
        this.shellInitProvider = provider2;
    }

    public static OverviewToDesktopTransitionObserver provideOverviewToDesktopTransitionObserver(Transitions transitions, ShellInit shellInit) {
        return new OverviewToDesktopTransitionObserver(transitions, shellInit);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new OverviewToDesktopTransitionObserver((Transitions) this.transitionsProvider.get(), (ShellInit) this.shellInitProvider.get());
    }
}
