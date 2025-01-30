package com.android.systemui.unfold;

import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.util.time.SystemClockImpl;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        return ((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isHingeAngleEnabled$delegate.getValue()).booleanValue() ? Optional.of(new FoldStateLoggingProviderImpl((FoldStateProvider) lazy.get(), new SystemClockImpl())) : Optional.empty();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesFoldStateLoggingProvider(this.module, (UnfoldTransitionConfig) this.configProvider.get(), DoubleCheck.lazy(this.foldStateProvider));
    }
}
