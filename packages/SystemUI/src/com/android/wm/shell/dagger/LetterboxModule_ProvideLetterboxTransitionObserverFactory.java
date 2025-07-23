package com.android.wm.shell.dagger;

import com.android.wm.shell.common.transition.TransitionStateHolder;
import com.android.wm.shell.compatui.letterbox.LetterboxController;
import com.android.wm.shell.compatui.letterbox.LetterboxControllerStrategy;
import com.android.wm.shell.compatui.letterbox.LetterboxTransitionObserver;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxModule_ProvideLetterboxTransitionObserverFactory implements Provider {
    public final Provider letterboxControllerProvider;
    public final Provider letterboxControllerStrategyProvider;
    public final Provider shellInitProvider;
    public final Provider transitionStateHolderProvider;
    public final Provider transitionsProvider;

    public LetterboxModule_ProvideLetterboxTransitionObserverFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.shellInitProvider = provider;
        this.transitionsProvider = provider2;
        this.letterboxControllerProvider = provider3;
        this.transitionStateHolderProvider = provider4;
        this.letterboxControllerStrategyProvider = provider5;
    }

    public static LetterboxTransitionObserver provideLetterboxTransitionObserver(ShellInit shellInit, Transitions transitions, LetterboxController letterboxController, TransitionStateHolder transitionStateHolder, LetterboxControllerStrategy letterboxControllerStrategy) {
        return new LetterboxTransitionObserver(shellInit, transitions, letterboxController, transitionStateHolder, letterboxControllerStrategy);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new LetterboxTransitionObserver((ShellInit) this.shellInitProvider.get(), (Transitions) this.transitionsProvider.get(), (LetterboxController) this.letterboxControllerProvider.get(), (TransitionStateHolder) this.transitionStateHolderProvider.get(), (LetterboxControllerStrategy) this.letterboxControllerStrategyProvider.get());
    }
}
