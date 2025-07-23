package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.OverviewToDesktopTransitionObserver;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
