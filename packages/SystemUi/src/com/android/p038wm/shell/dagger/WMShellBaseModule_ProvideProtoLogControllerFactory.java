package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.ProtoLogController;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideProtoLogControllerFactory implements Provider {
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideProtoLogControllerFactory(Provider provider, Provider provider2) {
        this.shellInitProvider = provider;
        this.shellCommandHandlerProvider = provider2;
    }

    public static ProtoLogController provideProtoLogController(ShellInit shellInit, ShellCommandHandler shellCommandHandler) {
        return new ProtoLogController(shellInit, shellCommandHandler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ProtoLogController((ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get());
    }
}
