package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvidePipAppOpsListenerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;

    public WMShellBaseModule_ProvidePipAppOpsListenerFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
    }

    public static PipAppOpsListener providePipAppOpsListener(Context context, ShellExecutor shellExecutor) {
        return new PipAppOpsListener(context, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipAppOpsListener((Context) this.contextProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
