package com.android.systemui.unfold;

import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.util.time.SystemClockImpl;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;

public final class UnfoldTransitionModule_ProvidesFoldStateLoggingProviderFactory implements Provider {
    public final javax.inject.Provider configProvider;
    public final javax.inject.Provider foldStateProvider;
    public final UnfoldTransitionModule module;

    public UnfoldTransitionModule_ProvidesFoldStateLoggingProviderFactory(UnfoldTransitionModule unfoldTransitionModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.module = unfoldTransitionModule;
        this.configProvider = provider;
        this.foldStateProvider = provider2;
    }

    public static Optional providesFoldStateLoggingProvider(UnfoldTransitionModule unfoldTransitionModule, UnfoldTransitionConfig unfoldTransitionConfig, Lazy lazy) {
        unfoldTransitionModule.getClass();
        if (((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isHingeAngleEnabled$delegate.getValue()).booleanValue()) {
            Optional of = Optional.of(new FoldStateLoggingProviderImpl((FoldStateProvider) lazy.get(), new SystemClockImpl()));
            Intrinsics.checkNotNull(of);
            return of;
        }
        Optional empty = Optional.empty();
        Intrinsics.checkNotNull(empty);
        return empty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesFoldStateLoggingProvider(this.module, (UnfoldTransitionConfig) this.configProvider.get(), DoubleCheck.lazy(this.foldStateProvider));
    }
}
