package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeLoggerTransitionObserver;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopModeLoggerTransitionObserverFactory implements Provider {
    public final Provider desktopModeEventLoggerProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopTasksLimiterProvider;
    public final Provider shellInitProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideDesktopModeLoggerTransitionObserverFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.shellInitProvider = provider;
        this.transitionsProvider = provider2;
        this.desktopModeEventLoggerProvider = provider3;
        this.desktopTasksLimiterProvider = provider4;
        this.desktopStateProvider = provider5;
    }

    public static DesktopModeLoggerTransitionObserver provideDesktopModeLoggerTransitionObserver(ShellInit shellInit, Transitions transitions, DesktopModeEventLogger desktopModeEventLogger, Optional optional, DesktopState desktopState) {
        return new DesktopModeLoggerTransitionObserver(shellInit, transitions, desktopModeEventLogger, optional, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopModeLoggerTransitionObserver((ShellInit) this.shellInitProvider.get(), (Transitions) this.transitionsProvider.get(), (DesktopModeEventLogger) this.desktopModeEventLoggerProvider.get(), (Optional) this.desktopTasksLimiterProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
