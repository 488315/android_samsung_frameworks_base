package com.android.wm.shell.dagger;

import android.view.IWindowManager;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.draganddrop.GlobalDragListener;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideGlobalDragListenerFactory implements Provider {
    public final Provider mainExecutorProvider;
    public final Provider wmServiceProvider;

    public WMShellModule_ProvideGlobalDragListenerFactory(Provider provider, Provider provider2) {
        this.wmServiceProvider = provider;
        this.mainExecutorProvider = provider2;
    }

    public static GlobalDragListener provideGlobalDragListener(IWindowManager iWindowManager, ShellExecutor shellExecutor) {
        return new GlobalDragListener(iWindowManager, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new GlobalDragListener((IWindowManager) this.wmServiceProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
