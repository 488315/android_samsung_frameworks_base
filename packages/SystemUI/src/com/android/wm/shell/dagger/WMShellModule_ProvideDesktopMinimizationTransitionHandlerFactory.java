package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopMinimizationTransitionHandler;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopMinimizationTransitionHandlerFactory implements Provider {
    public final Provider animExecutorProvider;
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;

    public WMShellModule_ProvideDesktopMinimizationTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.mainExecutorProvider = provider;
        this.animExecutorProvider = provider2;
        this.displayControllerProvider = provider3;
        this.mainHandlerProvider = provider4;
    }

    public static DesktopMinimizationTransitionHandler provideDesktopMinimizationTransitionHandler(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, DisplayController displayController, Handler handler) {
        return new DesktopMinimizationTransitionHandler(shellExecutor, shellExecutor2, displayController, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopMinimizationTransitionHandler((ShellExecutor) this.mainExecutorProvider.get(), (ShellExecutor) this.animExecutorProvider.get(), (DisplayController) this.displayControllerProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}
