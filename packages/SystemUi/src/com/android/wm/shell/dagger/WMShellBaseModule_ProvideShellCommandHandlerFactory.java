package com.android.wm.shell.dagger;

import com.android.wm.shell.sysui.ShellCommandHandler;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideShellCommandHandlerFactory implements Provider {
    public static ShellCommandHandler provideShellCommandHandler() {
        return new ShellCommandHandler();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ShellCommandHandler();
    }
}
