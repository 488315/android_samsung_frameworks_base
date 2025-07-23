package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideFreeformTaskTransitionHandlerFactory implements Provider {
    public final Provider animExecutorProvider;
    public final Provider animHandlerProvider;
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideFreeformTaskTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.transitionsProvider = provider;
        this.displayControllerProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.animExecutorProvider = provider4;
        this.animHandlerProvider = provider5;
    }

    public static FreeformTaskTransitionHandler provideFreeformTaskTransitionHandler(Transitions transitions, DisplayController displayController, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Handler handler) {
        return new FreeformTaskTransitionHandler(transitions, displayController, shellExecutor, shellExecutor2, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FreeformTaskTransitionHandler((Transitions) this.transitionsProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (ShellExecutor) this.animExecutorProvider.get(), (Handler) this.animHandlerProvider.get());
    }
}
