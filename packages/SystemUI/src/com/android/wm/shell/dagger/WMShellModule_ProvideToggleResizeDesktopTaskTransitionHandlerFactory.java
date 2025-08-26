package com.android.wm.shell.dagger;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.desktopmode.ToggleResizeDesktopTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideToggleResizeDesktopTaskTransitionHandlerFactory implements Provider {
    public final Provider interactionJankMonitorProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideToggleResizeDesktopTaskTransitionHandlerFactory(Provider provider, Provider provider2) {
        this.transitionsProvider = provider;
        this.interactionJankMonitorProvider = provider2;
    }

    public static ToggleResizeDesktopTaskTransitionHandler provideToggleResizeDesktopTaskTransitionHandler(Transitions transitions, InteractionJankMonitor interactionJankMonitor) {
        return new ToggleResizeDesktopTaskTransitionHandler(transitions, interactionJankMonitor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ToggleResizeDesktopTaskTransitionHandler((Transitions) this.transitionsProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get());
    }
}
