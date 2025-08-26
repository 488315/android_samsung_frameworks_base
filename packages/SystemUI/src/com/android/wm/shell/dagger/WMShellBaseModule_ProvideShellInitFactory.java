package com.android.wm.shell.dagger;

import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
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
