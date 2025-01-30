package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.WindowManagerShellWrapper;
import com.android.p038wm.shell.common.ShellExecutor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
