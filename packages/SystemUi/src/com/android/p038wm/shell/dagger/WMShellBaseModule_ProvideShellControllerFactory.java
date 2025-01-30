package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideShellControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideShellControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static ShellController provideShellController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellExecutor shellExecutor) {
        return new ShellController(context, shellInit, shellCommandHandler, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ShellController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
