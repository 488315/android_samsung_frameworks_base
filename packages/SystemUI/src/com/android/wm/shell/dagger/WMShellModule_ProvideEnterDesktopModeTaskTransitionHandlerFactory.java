package com.android.wm.shell.dagger;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.LatencyTracker;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideEnterDesktopModeTaskTransitionHandlerFactory implements Provider {
    public final Provider desktopTasksLimiterProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider latencyTrackerProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideEnterDesktopModeTaskTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.transitionsProvider = provider;
        this.desktopTasksLimiterProvider = provider2;
        this.interactionJankMonitorProvider = provider3;
        this.latencyTrackerProvider = provider4;
    }

    public static EnterDesktopTaskTransitionHandler provideEnterDesktopModeTaskTransitionHandler(Transitions transitions, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker) {
        return new EnterDesktopTaskTransitionHandler(transitions, interactionJankMonitor, latencyTracker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Transitions transitions = (Transitions) this.transitionsProvider.get();
        return new EnterDesktopTaskTransitionHandler(transitions, (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (LatencyTracker) this.latencyTrackerProvider.get());
    }
}
