package com.android.systemui.unfold;

import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldProvider;
import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UnfoldTransitionModule_ProvideShellProgressProviderFactory implements Provider {
    public final Provider configProvider;
    public final Provider foldProvider;
    public final UnfoldTransitionModule module;
    public final Provider providerProvider;
    public final Provider unfoldOnlyProvider;

    public UnfoldTransitionModule_ProvideShellProgressProviderFactory(UnfoldTransitionModule unfoldTransitionModule, Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.module = unfoldTransitionModule;
        this.configProvider = provider;
        this.foldProvider = provider2;
        this.providerProvider = provider3;
        this.unfoldOnlyProvider = provider4;
    }

    public static ShellUnfoldProgressProvider provideShellProgressProvider(UnfoldTransitionModule unfoldTransitionModule, UnfoldTransitionConfig unfoldTransitionConfig, FoldProvider foldProvider, javax.inject.Provider provider, javax.inject.Provider provider2) {
        Optional optional;
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider;
        unfoldTransitionModule.getClass();
        if (!((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isEnabled$delegate.getValue()).booleanValue()) {
            provider = null;
        } else if (!UnfoldTransitionModuleKt.ENABLE_FOLD_TASK_ANIMATIONS) {
            provider = provider2;
        }
        ShellUnfoldProgressProvider unfoldProgressProvider = (provider == null || (optional = (Optional) provider.get()) == null || (unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) optional.orElse(null)) == null) ? ShellUnfoldProgressProvider.NO_PROVIDER : new UnfoldProgressProvider(unfoldTransitionProgressProvider, foldProvider);
        unfoldProgressProvider.getClass();
        return unfoldProgressProvider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellProgressProvider(this.module, (UnfoldTransitionConfig) this.configProvider.get(), (FoldProvider) this.foldProvider.get(), this.providerProvider, this.unfoldOnlyProvider);
    }
}
