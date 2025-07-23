package com.android.wm.shell.dagger;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideIndependentShellComponentsToCreateFactory implements Provider {
    public final Provider desktopDisplayEventHandlerProvider;
    public final Provider desktopModeKeyGestureHandlerProvider;
    public final Provider desktopTasksTransitionObserverOptionalProvider;
    public final Provider displayDisconnectTransitionHandlerProvider;
    public final Provider dragAndDropControllerProvider;
    public final Provider letterboxCommandHandlerProvider;
    public final Provider letterboxTransitionObserverProvider;
    public final Provider shellCrashHandlerProvider;
    public final Provider systemModalsTransitionHandlerProvider;

    public WMShellModule_ProvideIndependentShellComponentsToCreateFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.dragAndDropControllerProvider = provider;
        this.letterboxTransitionObserverProvider = provider2;
        this.letterboxCommandHandlerProvider = provider3;
        this.desktopTasksTransitionObserverOptionalProvider = provider4;
        this.desktopDisplayEventHandlerProvider = provider5;
        this.desktopModeKeyGestureHandlerProvider = provider6;
        this.systemModalsTransitionHandlerProvider = provider7;
        this.displayDisconnectTransitionHandlerProvider = provider8;
        this.shellCrashHandlerProvider = provider9;
    }

    public static Object provideIndependentShellComponentsToCreate$1() {
        return new Object();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new Object();
    }
}
