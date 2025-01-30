package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.systemui.R;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellConcurrencyModule_ProvideShellMainExecutorFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainHandlerProvider;
    public final Provider sysuiMainExecutorProvider;

    public WMShellConcurrencyModule_ProvideShellMainExecutorFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.sysuiMainExecutorProvider = provider3;
    }

    public static ShellExecutor provideShellMainExecutor(Context context, Handler handler, ShellExecutor shellExecutor) {
        if (context.getResources().getBoolean(R.bool.config_enableShellMainThread)) {
            shellExecutor = new HandlerExecutor(handler);
        }
        Preconditions.checkNotNullFromProvides(shellExecutor);
        return shellExecutor;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellMainExecutor((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get(), (ShellExecutor) this.sysuiMainExecutorProvider.get());
    }
}
