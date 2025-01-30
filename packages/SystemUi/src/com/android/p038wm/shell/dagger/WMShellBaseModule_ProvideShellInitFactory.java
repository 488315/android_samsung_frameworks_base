package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideShellInitFactory implements Provider {
    public final Provider mainExecutorProvider;

    public WMShellBaseModule_ProvideShellInitFactory(Provider provider) {
        this.mainExecutorProvider = provider;
    }

    public static ShellInit provideShellInit(ShellExecutor shellExecutor) {
        return new ShellInit(shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ShellInit((ShellExecutor) this.mainExecutorProvider.get());
    }
}
