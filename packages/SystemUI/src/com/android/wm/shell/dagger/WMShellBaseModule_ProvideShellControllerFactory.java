package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideShellControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideShellControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.displayInsetsControllerProvider = provider4;
        this.mainExecutorProvider = provider5;
    }

    public static ShellController provideShellController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, DisplayInsetsController displayInsetsController, ShellExecutor shellExecutor) {
        return new ShellController(context, shellInit, shellCommandHandler, displayInsetsController, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ShellController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
