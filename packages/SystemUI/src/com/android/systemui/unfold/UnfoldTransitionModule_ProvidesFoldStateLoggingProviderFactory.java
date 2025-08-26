package com.android.systemui.unfold;

import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.util.time.SystemClockImpl;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class UnfoldTransitionModule_ProvidesFoldStateLoggingProviderFactory implements Provider {
    public final Provider configProvider;
    public final Provider foldStateProvider;
    public final UnfoldTransitionModule module;

    public UnfoldTransitionModule_ProvidesFoldStateLoggingProviderFactory(UnfoldTransitionModule unfoldTransitionModule, Provider provider, Provider provider2) {
        this.module = unfoldTransitionModule;
        this.configProvider = provider;
        this.foldStateProvider = provider2;
    }

    public static Optional providesFoldStateLoggingProvider(UnfoldTransitionModule unfoldTransitionModule, UnfoldTransitionConfig unfoldTransitionConfig, Lazy lazy) {
        unfoldTransitionModule.getClass();
        if (((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isHingeAngleEnabled$delegate.getValue()).booleanValue()) {
            Optional optionalOf = Optional.of(new FoldStateLoggingProviderImpl((FoldStateProvider) lazy.get(), new SystemClockImpl()));
            optionalOf.getClass();
            return optionalOf;
        }
        Optional optionalEmpty = Optional.empty();
        optionalEmpty.getClass();
        return optionalEmpty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesFoldStateLoggingProvider(this.module, (UnfoldTransitionConfig) this.configProvider.get(), DoubleCheck.lazy(this.foldStateProvider));
    }
}
