package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.HomeTransitionObserver;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideHomeTransitionObserverFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideHomeTransitionObserverFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.displayInsetsControllerProvider = provider3;
        this.shellInitProvider = provider4;
    }

    public static HomeTransitionObserver provideHomeTransitionObserver(Context context, ShellExecutor shellExecutor, DisplayInsetsController displayInsetsController, ShellInit shellInit) {
        return new HomeTransitionObserver(context, shellExecutor, displayInsetsController, shellInit);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HomeTransitionObserver((Context) this.contextProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (ShellInit) this.shellInitProvider.get());
    }
}
