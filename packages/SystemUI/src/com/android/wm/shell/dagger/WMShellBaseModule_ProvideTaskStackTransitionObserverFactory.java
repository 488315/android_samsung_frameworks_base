package com.android.wm.shell.dagger;

import com.android.wm.shell.recents.TaskStackTransitionObserver;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideTaskStackTransitionObserverFactory implements Provider {
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider transitionsProvider;

    public WMShellBaseModule_ProvideTaskStackTransitionObserverFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.shellInitProvider = provider;
        this.shellTaskOrganizerProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.transitionsProvider = provider4;
    }

    public static TaskStackTransitionObserver provideTaskStackTransitionObserver(ShellInit shellInit, Lazy lazy, ShellCommandHandler shellCommandHandler, Lazy lazy2) {
        return new TaskStackTransitionObserver(shellInit, lazy, shellCommandHandler, lazy2);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TaskStackTransitionObserver((ShellInit) this.shellInitProvider.get(), DoubleCheck.lazy(this.shellTaskOrganizerProvider), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), DoubleCheck.lazy(this.transitionsProvider));
    }
}
