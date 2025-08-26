package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideExitDesktopTaskTransitionHandlerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider handlerProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideExitDesktopTaskTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.transitionsProvider = provider;
        this.contextProvider = provider2;
        this.interactionJankMonitorProvider = provider3;
        this.handlerProvider = provider4;
    }

    public static ExitDesktopTaskTransitionHandler provideExitDesktopTaskTransitionHandler(Transitions transitions, Context context, InteractionJankMonitor interactionJankMonitor, Handler handler) {
        return new ExitDesktopTaskTransitionHandler(transitions, context, interactionJankMonitor, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ExitDesktopTaskTransitionHandler((Transitions) this.transitionsProvider.get(), (Context) this.contextProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (Handler) this.handlerProvider.get());
    }
}
