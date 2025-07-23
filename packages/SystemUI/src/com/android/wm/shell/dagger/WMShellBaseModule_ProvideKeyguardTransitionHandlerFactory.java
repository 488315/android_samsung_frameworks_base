package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideKeyguardTransitionHandlerFactory implements Provider {
    public final Provider displayControllerProvider;
    public final Provider focusTransitionObserverProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider taskStackListenerProvider;
    public final Provider transitionsProvider;

    public WMShellBaseModule_ProvideKeyguardTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.shellInitProvider = provider;
        this.shellControllerProvider = provider2;
        this.displayControllerProvider = provider3;
        this.transitionsProvider = provider4;
        this.taskStackListenerProvider = provider5;
        this.mainHandlerProvider = provider6;
        this.mainExecutorProvider = provider7;
        this.focusTransitionObserverProvider = provider8;
    }

    public static KeyguardTransitionHandler provideKeyguardTransitionHandler(ShellInit shellInit, ShellController shellController, DisplayController displayController, Transitions transitions, TaskStackListenerImpl taskStackListenerImpl, Handler handler, ShellExecutor shellExecutor, FocusTransitionObserver focusTransitionObserver) {
        return new KeyguardTransitionHandler(shellInit, shellController, displayController, transitions, taskStackListenerImpl, handler, shellExecutor, focusTransitionObserver);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new KeyguardTransitionHandler((ShellInit) this.shellInitProvider.get(), (ShellController) this.shellControllerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (Transitions) this.transitionsProvider.get(), (TaskStackListenerImpl) this.taskStackListenerProvider.get(), (Handler) this.mainHandlerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (FocusTransitionObserver) this.focusTransitionObserverProvider.get());
    }
}
