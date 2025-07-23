package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.freeform.FreeformTaskTransitionObserver;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideFreeformTaskTransitionObserverFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desksTransitionObserverProvider;
    public final Provider desktopImmersiveControllerProvider;
    public final Provider desktopStateProvider;
    public final Provider focusTransitionObserverProvider;
    public final Provider shellInitProvider;
    public final Provider taskChangeListenerProvider;
    public final Provider transitionsProvider;
    public final Provider windowDecorViewModelProvider;

    public WMShellModule_ProvideFreeformTaskTransitionObserverFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.transitionsProvider = provider3;
        this.desktopImmersiveControllerProvider = provider4;
        this.windowDecorViewModelProvider = provider5;
        this.taskChangeListenerProvider = provider6;
        this.focusTransitionObserverProvider = provider7;
        this.desksTransitionObserverProvider = provider8;
        this.desktopStateProvider = provider9;
    }

    public static FreeformTaskTransitionObserver provideFreeformTaskTransitionObserver(Context context, ShellInit shellInit, Transitions transitions, Optional optional, WindowDecorViewModel windowDecorViewModel, Optional optional2, FocusTransitionObserver focusTransitionObserver, Optional optional3, DesktopState desktopState) {
        return new FreeformTaskTransitionObserver(context, shellInit, transitions, optional, windowDecorViewModel, optional2, focusTransitionObserver, optional3, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FreeformTaskTransitionObserver((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (Transitions) this.transitionsProvider.get(), (Optional) this.desktopImmersiveControllerProvider.get(), (WindowDecorViewModel) this.windowDecorViewModelProvider.get(), (Optional) this.taskChangeListenerProvider.get(), (FocusTransitionObserver) this.focusTransitionObserverProvider.get(), (Optional) this.desksTransitionObserverProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
