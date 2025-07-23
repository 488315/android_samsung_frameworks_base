package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideRootTaskDisplayAreaOrganizerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideRootTaskDisplayAreaOrganizerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.mainExecutorProvider = provider;
        this.contextProvider = provider2;
        this.shellInitProvider = provider3;
    }

    public static RootTaskDisplayAreaOrganizer provideRootTaskDisplayAreaOrganizer(Context context, ShellInit shellInit, ShellExecutor shellExecutor) {
        return new RootTaskDisplayAreaOrganizer(shellExecutor, context, shellInit);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new RootTaskDisplayAreaOrganizer((ShellExecutor) this.mainExecutorProvider.get(), (Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get());
    }
}
