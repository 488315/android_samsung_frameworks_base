package com.android.systemui.statusbar.dagger;

import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.wm.shell.shared.ShellTransitions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CentralSurfacesDependenciesModule_ProvideActivityTransitionAnimatorFactory implements Provider {
    public final Provider mainExecutorProvider;
    public final Provider shellTransitionsProvider;

    public CentralSurfacesDependenciesModule_ProvideActivityTransitionAnimatorFactory(Provider provider, Provider provider2) {
        this.mainExecutorProvider = provider;
        this.shellTransitionsProvider = provider2;
    }

    public static ActivityTransitionAnimator provideActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions) {
        return new ActivityTransitionAnimator(executor, shellTransitions);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ActivityTransitionAnimator((Executor) this.mainExecutorProvider.get(), (ShellTransitions) this.shellTransitionsProvider.get());
    }
}
