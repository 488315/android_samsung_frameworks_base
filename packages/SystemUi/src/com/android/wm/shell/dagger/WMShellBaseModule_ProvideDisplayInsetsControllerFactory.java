package com.android.wm.shell.dagger;

import android.view.IWindowManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideDisplayInsetsControllerFactory implements Provider {
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellInitProvider;
    public final Provider wmServiceProvider;

    public WMShellBaseModule_ProvideDisplayInsetsControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.wmServiceProvider = provider;
        this.shellInitProvider = provider2;
        this.displayControllerProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static DisplayInsetsController provideDisplayInsetsController(IWindowManager iWindowManager, ShellInit shellInit, DisplayController displayController, ShellExecutor shellExecutor) {
        return new DisplayInsetsController(iWindowManager, shellInit, displayController, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DisplayInsetsController((IWindowManager) this.wmServiceProvider.get(), (ShellInit) this.shellInitProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
