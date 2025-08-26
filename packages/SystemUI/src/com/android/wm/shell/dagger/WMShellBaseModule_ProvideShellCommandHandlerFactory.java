package com.android.wm.shell.dagger;

import com.android.wm.shell.sysui.ShellCommandHandler;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideShellCommandHandlerFactory implements Provider {
    public static ShellCommandHandler provideShellCommandHandler() {
        return new ShellCommandHandler();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ShellCommandHandler();
    }
}
