package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideCloseDesktopTaskTransitionHandlerFactory implements Provider {
    public final Provider animExecutorProvider;
    public final Provider animHandlerProvider;
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;

    public WMShellModule_ProvideCloseDesktopTaskTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.animExecutorProvider = provider3;
        this.animHandlerProvider = provider4;
    }

    public static CloseDesktopTaskTransitionHandler provideCloseDesktopTaskTransitionHandler(Context context, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Handler handler) {
        return new CloseDesktopTaskTransitionHandler(context, shellExecutor, shellExecutor2, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new CloseDesktopTaskTransitionHandler((Context) this.contextProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (ShellExecutor) this.animExecutorProvider.get(), (Handler) this.animHandlerProvider.get());
    }
}
