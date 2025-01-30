package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideKeyguardTransitionHandlerFactory implements Provider {
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider transitionsProvider;

    public WMShellBaseModule_ProvideKeyguardTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.shellInitProvider = provider;
        this.transitionsProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static KeyguardTransitionHandler provideKeyguardTransitionHandler(ShellInit shellInit, Transitions transitions, Handler handler, ShellExecutor shellExecutor) {
        return new KeyguardTransitionHandler(shellInit, transitions, handler, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new KeyguardTransitionHandler((ShellInit) this.shellInitProvider.get(), (Transitions) this.transitionsProvider.get(), (Handler) this.mainHandlerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
