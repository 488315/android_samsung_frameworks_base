package com.android.systemui.unfold;

import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldTransitionModule_ProvideShellProgressProviderFactory implements Provider {
    public final Provider configProvider;
    public final UnfoldTransitionModule module;
    public final Provider providerProvider;
    public final Provider unfoldOnlyProvider;

    public UnfoldTransitionModule_ProvideShellProgressProviderFactory(UnfoldTransitionModule unfoldTransitionModule, Provider provider, Provider provider2, Provider provider3) {
        this.module = unfoldTransitionModule;
        this.configProvider = provider;
        this.providerProvider = provider2;
        this.unfoldOnlyProvider = provider3;
    }

    public static ShellUnfoldProgressProvider provideShellProgressProvider(UnfoldTransitionModule unfoldTransitionModule, UnfoldTransitionConfig unfoldTransitionConfig, Provider provider, Provider provider2) {
        Optional optional;
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider;
        unfoldTransitionModule.getClass();
        if (!((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isEnabled$delegate.getValue()).booleanValue()) {
            provider = null;
        } else if (!UnfoldTransitionModuleKt.ENABLE_FOLD_TASK_ANIMATIONS) {
            provider = provider2;
        }
        return (provider == null || (optional = (Optional) provider.get()) == null || (unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) optional.orElse(null)) == null) ? ShellUnfoldProgressProvider.NO_PROVIDER : new UnfoldProgressProvider(unfoldTransitionProgressProvider);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellProgressProvider(this.module, (UnfoldTransitionConfig) this.configProvider.get(), this.providerProvider, this.unfoldOnlyProvider);
    }
}
