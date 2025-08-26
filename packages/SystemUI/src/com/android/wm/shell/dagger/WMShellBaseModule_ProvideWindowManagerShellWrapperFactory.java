package com.android.wm.shell.dagger;

import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.common.ShellExecutor;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideWindowManagerShellWrapperFactory implements Provider {
    public final Provider mainExecutorProvider;

    public WMShellBaseModule_ProvideWindowManagerShellWrapperFactory(Provider provider) {
        this.mainExecutorProvider = provider;
    }

    public static WindowManagerShellWrapper provideWindowManagerShellWrapper(ShellExecutor shellExecutor) {
        return new WindowManagerShellWrapper(shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new WindowManagerShellWrapper((ShellExecutor) this.mainExecutorProvider.get());
    }
}
