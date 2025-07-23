package com.android.wm.shell.dagger;

import com.android.wm.shell.ProtoLogController;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
